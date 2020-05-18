package com.arcsoft.arcfacedemo.faceserver;

import com.arcsoft.arcfacedemo.ArcFaceApp;
import com.arcsoft.arcfacedemo.dbutil.FaceDatabaseManager;
import com.arcsoft.arcfacedemo.dbutil.FaceEntity;

import java.util.List;

public class SQLiteFaceDao implements FaceDatabaseAccessObject {
    private static FaceDatabaseManager faceDataBaseManager;

    @Override
    public void init() {
        faceDataBaseManager = new FaceDatabaseManager(ArcFaceApp.getApplication());
    }

    @Override
    public long insert(FaceEntity userEntity) {
        if (faceDataBaseManager == null) {
            return -1;
        }
        return faceDataBaseManager.addFace(userEntity.getFaceName(), userEntity.getFaceFeature(), userEntity.getFacePic());
    }

    @Override
    public List<FaceEntity> getAll() {
        if (faceDataBaseManager == null) {
            return null;
        }
        return faceDataBaseManager.selectAllFaces();
    }

    @Override
    public void clearAll() {
        if (faceDataBaseManager == null) {
            return;
        }
        faceDataBaseManager.deleteAllFace();
    }

    @Override
    public void release() {
        if (faceDataBaseManager == null) {
            return;
        }
        faceDataBaseManager.release();
    }
}
