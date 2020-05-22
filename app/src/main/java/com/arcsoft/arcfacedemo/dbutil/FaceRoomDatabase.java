package com.arcsoft.arcfacedemo.dbutil;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

//注解指定了database的表映射实体数据以及版本等信息
@Database(entities = {FaceEntity.class}, version = 1)
public abstract class FaceRoomDatabase extends RoomDatabase {
    //RoomDatabase提供直接访问底层数据库实现，我们通过定义抽象方法返回具体Dao
    //然后进行数据库增删该查的实现。
    public abstract FaceDao userDao();

    private static volatile FaceRoomDatabase instance;

    public static FaceRoomDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (FaceRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), FaceRoomDatabase.class, "RoomFaceDB.db")
                            .build();
                }
            }
        }
        return instance;
    }

    public void release(){
        if (instance != null){
            instance.release();
            instance = null;
        }
    }
}