package com.arcsoft.arcfacedemo.faceserver;

import com.arcsoft.arcfacedemo.ArcFaceApp;
import com.arcsoft.arcfacedemo.dbutil.FaceDao;
import com.arcsoft.arcfacedemo.dbutil.FaceRoomDatabase;
import com.arcsoft.arcfacedemo.dbutil.FaceEntity;

import java.util.List;

public class RoomFaceDao implements FaceDatabaseAccessObject {
    private static FaceDao dao;
    private FaceRoomDatabase faceRoomDatabase;

    @Override
    public void init() {
        faceRoomDatabase = FaceRoomDatabase.getInstance(ArcFaceApp.getApplication());
        dao = faceRoomDatabase.userDao();
    }

    @Override
    public long insert(FaceEntity userEntity) {
        return dao == null ? -1 : dao.addFace(userEntity);
    }

    @Override
    public List<FaceEntity> getAll() {
        return dao == null ? null : dao.getAllFace();
    }

    @Override
    public void clearAll() {
        if (dao == null) {
            return;
        }
        dao.deleteAll();
    }

    @Override
    public void release() {
        if (faceRoomDatabase != null) {
            faceRoomDatabase.close();
            faceRoomDatabase = null;
        }
    }
}
