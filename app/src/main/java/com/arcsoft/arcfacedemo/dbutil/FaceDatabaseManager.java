package com.arcsoft.arcfacedemo.dbutil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class FaceDatabaseManager {
    //数据库名称
    private static final String DB_NAME = "SQLitefaceDB.db";
    //数据库版本
    private static final int DATABASE_VERSION = 1;
    //数据库
    private SQLiteDatabase db;
    //数据库表
    private FaceDatabaseHelper faceDataBaseTable;

    public FaceDatabaseManager(Context context) {
        //CursorFactory设置为null,使用默认值
        faceDataBaseTable = new FaceDatabaseHelper(context, DB_NAME, null, DATABASE_VERSION);
        db = faceDataBaseTable.getWritableDatabase();
    }

    public long addFace(String faceName, byte[] faceFeature, byte[] facePic) {
        //存人脸
        ContentValues values = new ContentValues();
        long index = -1;
        //写入表
        try {
            values.put("faceName", faceName);
            values.put("faceFeature", faceFeature);
            values.put("facePic", facePic);
            index = db.insert("Face", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public ArrayList<FaceEntity> selectAllFaces() {
        ArrayList<FaceEntity> dataList = new ArrayList<>();
        Cursor cursor = db.query("Face", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            FaceEntity data = new FaceEntity();
            int id = cursor.getInt(cursor.getColumnIndex("id"));
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
        db.execSQL("delete from Face");
    }

    public void deleteFaceById(int id) {
        db.delete("Face", "_id=?", new String[]{"" + id});
    }

    public void release() {
        if (db != null) {
            db.close();
            db = null;
        }
        if (faceDataBaseTable != null) {
            faceDataBaseTable.close();
            faceDataBaseTable = null;
        }
    }
}
