package com.arcsoft.arcfacedemo.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class FaceDatabaseTable extends SQLiteOpenHelper {

    private Context mContext;

    public FaceDatabaseTable(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建User表 用户ID 名字 照片
        db.execSQL("CREATE TABLE IF NOT EXISTS User" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, faceName TEXT, facePic Blob , faceFeature Blob)");
        //建库成功后给出提示
        Toast.makeText(mContext, "dataBase Create Success", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
