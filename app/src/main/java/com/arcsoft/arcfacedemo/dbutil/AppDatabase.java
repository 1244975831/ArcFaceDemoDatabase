package com.arcsoft.arcfacedemo.dbutil;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.BatteryManager;

//注解指定了database的表映射实体数据以及版本等信息
@Database(entities = {UserTable.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    //RoomDatabase提供直接访问底层数据库实现，我们通过定义抽象方法返回具体Dao
    //然后进行数据库增删该查的实现。
    public abstract UserDao userDao();

    private static final Object lock = new Object();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        synchronized (lock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "roomDatabase.db")
                        .build();
            }
            return instance;
        }
    }


}