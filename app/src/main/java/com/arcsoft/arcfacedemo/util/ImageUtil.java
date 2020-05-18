package com.arcsoft.arcfacedemo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {
    public static final int DEFAULT_MAX_WIDTH = 500;
    public static final int DEFAULT_MAX_HEIGHT = 500;


    private static final int MASK_A = 0xFF000000;
    private static final int MASK_R = 0x00FF0000;
    private static final int MASK_G = 0x0000FF00;
    private static final int MASK_B = 0x000000FF;

    public static int rgbToY(int r, int g, int b) {
        return (((66 * r + 129 * g + 25 * b + 128) >> 8) + 16);
    }

    public static int rgbToU(int r, int g, int b) {
        return (((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128);
    }

    public static int rgbToV(int r, int g, int b) {
        return (((112 * r - 94 * g - 18 * b + 128) >> 8) + 128);
    }

    public static void drawRectOnNv21(byte[] nv21, int width, int height, int color, int strokeWidth, Rect rect) {
        if (rect == null || rect.isEmpty()) {
            return;
        }
        drawRectOnNv21(nv21, width, height, color, strokeWidth, rect.left, rect.top, rect.right, rect.bottom);
    }

    public static void drawRectOnNv21(byte[] nv21, int width, int height, int color, int strokeWidth, int left, int top,
                                      int right, int bottom) {
        if ((strokeWidth & 1) == 1) {
            strokeWidth += 1;
        }
        // 确保边界是4的倍数
        left &= ~0b11;
        top &= ~0b11;
        right &= ~0b11;
        bottom &= ~0b11;
        // 对于溢出图像的边，不绘制
        boolean drawLeft = true, drawTop = true, drawRight = true, drawBottom = true;
        if (left <= 0) {
            left = 0;
            drawLeft = false;
        }
        if (top <= 0) {
            top = 0;
            drawTop = false;
        }
        if (right >= width) {
            right = width;
            drawRight = false;
        }
        if (bottom >= height) {
            bottom = height;
            drawBottom = false;
        }

        // 取出R G B的值，并转换为Y U V
        int r = (color & MASK_R) >> 16;
        int g = (color & MASK_G) >> 8;
        int b = color & MASK_B;
        int y = rgbToY(r, g, b);
        int u = rgbToU(r, g, b);
        int v = rgbToV(r, g, b);

        // 根据边框的strokeWidth确定内边界
        int innerTop = top + strokeWidth;
        int innerBottom = bottom - strokeWidth;
        int innerRight = right - strokeWidth;

        int horizontalPixels = right - left;
        int yStartIndex;
        int uvStartIndex;
        boolean drawUV;
        if (drawTop) {
            yStartIndex = top * width + left;
            uvStartIndex = width * height + ((top / 2 * width) + left);
            drawUV = false;
            for (int i = top; i < innerTop; i++) {
                for (int j = 0; j < horizontalPixels; j++) {
                    nv21[yStartIndex + j] = (byte) y;
                }
                yStartIndex += width;
                if (drawUV = !drawUV) {
                    for (int j = 0; j < horizontalPixels; j += 2) {
                        nv21[uvStartIndex + j] = (byte) v;
                        nv21[uvStartIndex + j + 1] = (byte) u;
                    }
                    uvStartIndex += width;
                }
            }
        }

        if (drawLeft) {
            //左边
            yStartIndex = innerTop * width + left;
            uvStartIndex = width * height + (innerTop / 2 * width + left);
            drawUV = false;
            for (int i = innerTop; i < innerBottom; i++) {
                for (int j = 0; j < strokeWidth; j++) {
                    nv21[yStartIndex + j] = (byte) y;
                }
                yStartIndex += width;
                if (drawUV = !drawUV) {
                    for (int j = 0; j < strokeWidth; j += 2) {
                        nv21[uvStartIndex + j] = (byte) v;
                        nv21[uvStartIndex + j + 1] = (byte) u;
                    }
                    uvStartIndex += width;
                }
            }
        }
        if (drawRight) {
            //右边
            yStartIndex = innerTop * width + innerRight;
            uvStartIndex = width * height + (innerTop / 2 * width + innerRight);
            drawUV = false;
            for (int i = innerTop; i < innerBottom; i++) {
                for (int j = 0; j < strokeWidth; j++) {
                    nv21[yStartIndex + j] = (byte) y;
                }
                yStartIndex += width;
                if (drawUV = !drawUV) {
                    for (int j = 0; j < strokeWidth; j += 2) {
                        nv21[uvStartIndex + j] = (byte) v;
                        nv21[uvStartIndex + j + 1] = (byte) u;
                    }
                    uvStartIndex += width;
                }
            }
        }

        if (drawBottom) {
            //下边
            yStartIndex = innerBottom * width + left;
            uvStartIndex = width * height + ((innerBottom / 2 * width) + left);
            drawUV = false;
            for (int i = innerBottom; i < bottom; i++) {
                for (int j = 0; j < horizontalPixels; j++) {
                    nv21[yStartIndex + j] = (byte) y;
                }
                yStartIndex += width;
                if (drawUV = !drawUV) {
                    for (int j = 0; j < horizontalPixels; j += 2) {
                        nv21[uvStartIndex + j] = (byte) v;
                        nv21[uvStartIndex + j + 1] = (byte) u;
                    }
                    uvStartIndex += width;
                }
            }
        }
    }

    /**
     * 缩放图像，如果需要缩放，就顺便把宽高对齐给做了
     *
     * @param bitmap    原图
     * @param maxWidth  最大目标宽度
     * @param maxHeight 最大目标高度
     * @return 缩放后的图像
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        float horizontalScale = ((float) bitmap.getWidth()) / maxWidth;
        float verticalScale = ((float) bitmap.getHeight()) / maxHeight;
        if (horizontalScale < 1 || verticalScale < 1) {
            return bitmap;
        }
        float maxScale = Math.max(horizontalScale, verticalScale);
        // 确保为4的倍数
        int newWidth = (int) (bitmap.getWidth() / maxScale) & ~0b11;
        int newHeight = (int) (bitmap.getHeight() / maxScale) & ~0b11;

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }

    /**
     * 将Uri转换为Bitmap，并限制最大宽高
     */
    public static Bitmap uriToScaledBitmap(Context context, Uri uri, int maxWidth, int maxHeight) {
        ContentResolver contentResolver = context.getContentResolver();
        byte[] data;
        try {
            InputStream input = null;
            input = contentResolver.openInputStream(uri);
            data = new byte[input.available()];
            input.read(data);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jpegToScaledBitmap(data, maxWidth, maxHeight);
    }

    /**
     * 将文件转换为Bitmap，并限制最大宽高
     */
    public static Bitmap fileToScaledBitmap(File imgFile, int maxWidth, int maxHeight) {
        byte[] data;
        try {
            FileInputStream fis = new FileInputStream(imgFile);
            data = new byte[fis.available()];
            fis.read(data);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jpegToScaledBitmap(data, maxWidth, maxHeight);
    }

    /**
     * 将jpeg形式的压缩图像转换为Bitmap，并限制最大宽高
     *
     * @param jpeg      jpeg图像数据
     * @param maxWidth  限制的最大宽度
     * @param maxHeight 限制的最大高度
     * @return 宽高小于限制值的Bitmap对象
     */
    public static Bitmap jpegToScaledBitmap(byte[] jpeg, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length, options);

        int inSampleSize = 1;
        while (options.outWidth / inSampleSize > maxWidth || options.outHeight / inSampleSize > maxHeight) {
            inSampleSize++;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length, options);
    }
}
