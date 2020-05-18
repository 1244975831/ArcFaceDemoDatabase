package com.arcsoft.arcfacedemo.faceserver;

import com.arcsoft.arcfacedemo.dbutil.FaceEntity;

import java.util.List;

public interface FaceDatabaseAccessObject {
    /**
     * 初始化
     */
    void init();

    /**
     * 插入一个人脸
     *
     * @param userEntity 人脸信息
     * @return index
     */
    long insert(FaceEntity userEntity);

    /**
     * 获取所有人脸
     *
     * @return 所有人脸
     */
    List<FaceEntity> getAll();

    /**
     * 清空所有人脸
     */
    void clearAll();

    /**
     * 回收资源操作
     */
    void release();
}
