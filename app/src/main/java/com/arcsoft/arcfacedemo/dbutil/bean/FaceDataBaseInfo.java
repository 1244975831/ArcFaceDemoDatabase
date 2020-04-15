package com.arcsoft.arcfacedemo.dbutil.bean;

public class FaceDataBaseInfo {
    private int id;
    private String faceName;
    private byte[] facePic;
    private byte[] faceFeature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFaceName() {
        return faceName;
    }

    public void setFaceName(String faceName) {
        this.faceName = faceName;
    }

    public byte[] getFacePic() {
        return facePic;
    }

    public void setFacePic(byte[] facePic) {
        this.facePic = facePic;
    }

    public byte[] getFaceFeature() {
        return faceFeature;
    }

    public void setFaceFeature(byte[] faceFeature) {
        this.faceFeature = faceFeature;
    }
}
