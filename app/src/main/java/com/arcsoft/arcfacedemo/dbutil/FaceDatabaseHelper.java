package com.arcsoft.arcfacedemo.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class FaceDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "FaceDatabaseHelper";

    public FaceDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建Face表 ID 名字 照片 特征
        db.execSQL("CREATE TABLE IF NOT EXISTS Face" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, faceName TEXT, facePic Blob , faceFeature Blob)");
        //建库成功后给出提示
        Log.i(TAG, "dataBase Create Success");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
