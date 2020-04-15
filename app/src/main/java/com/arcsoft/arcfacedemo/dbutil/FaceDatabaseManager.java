package com.arcsoft.arcfacedemo.dbutil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.arcsoft.arcfacedemo.dbutil.bean.FaceDataBaseInfo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FaceDatabaseManager {
    //数据库名称
    private static final String DATA_NAME = "faceData.bd";
    //数据库版本
    private static final int DATABASE_VERSION = 1;
    //数据库
    private SQLiteDatabase db;
    //数据库表
    private FaceDatabaseTable faceDataBaseTable;

    public FaceDatabaseManager(Context context) {
        //CursorFactory设置为null,使用默认值
        faceDataBaseTable = new FaceDatabaseTable(context, DATA_NAME, null, DATABASE_VERSION);
        db = faceDataBaseTable.getWritableDatabase();
    }

    public void addFace(String faceName, byte[] faceFeature, Bitmap faceImg) {
        //存人脸
        ContentValues values = new ContentValues();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        faceImg.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] facePic = baos.toByteArray();
        //写入User表
        try {
            values.put("faceName", faceName);
            values.put("faceFeature", faceFeature);
            values.put("facePic", facePic);
            db.insert("User", null, values);
            values.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<FaceDataBaseInfo> selectAllFaces() {
        ArrayList<FaceDataBaseInfo> dataList = new ArrayList<>();
        Cursor cursor = db.query("User", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            FaceDataBaseInfo data = new FaceDataBaseInfo();
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String faceName = cursor.getString(cursor.getColumnIndex("faceName"));
            byte[] facePic = cursor.getBlob(cursor.getColumnIndex("facePic"));
            byte[] faceFeature = cursor.getBlob(cursor.getColumnIndex("faceFeature"));
            data.setId(id);
            data.setFaceName(faceName);
            data.setFacePic(facePic);
            data.setFaceFeature(faceFeature);
            dataList.add(data);
        }
        cursor.close();
        return dataList;
    }

    public void deleteAllFace() {
        db.execSQL("delete from User");
    }

    public void deleteFaceById(int id) {
        db.delete("User", "_id=?", new String[]{"" + id});
    }
}
