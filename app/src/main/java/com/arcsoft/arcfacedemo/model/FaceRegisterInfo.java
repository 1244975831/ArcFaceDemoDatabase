package com.arcsoft.arcfacedemo.model;

public class FaceRegisterInfo {
    private byte[] featureData;
    private String name;
    private byte[] facePic;

    public FaceRegisterInfo(byte[] faceFeature, String name) {
        this.featureData = faceFeature;
        this.name = name;
    }

    public FaceRegisterInfo(byte[] faceFeature, String name, byte[] facePic) {
        this.featureData = faceFeature;
        this.name = name;
        this.facePic = facePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFeatureData() {
        return featureData;
    }

    public void setFeatureData(byte[] featureData) {
        this.featureData = featureData;
    }

    public byte[] getFacePic() {
        return facePic;
    }

    public void setFacePic(byte[] facePic) {
        this.facePic = facePic;
    }
}
