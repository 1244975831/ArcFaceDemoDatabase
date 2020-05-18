package com.arcsoft.arcfacedemo.dbutil;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FaceDao {
    //返回Long数据表示，插入条目的主键值（uid）
    @Insert
    Long addFace(FaceEntity user);

    //获取所有人脸数据
    @Query("SELECT * FROM Face")
    List<FaceEntity> getAllFace();

    //删除所有人脸数据
    @Query("DELETE FROM Face")
    int deleteAll();

    //删除指定人脸
    @Delete
    int delete(FaceEntity user);
}
