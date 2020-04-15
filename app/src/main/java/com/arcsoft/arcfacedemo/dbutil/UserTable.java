package com.arcsoft.arcfacedemo.dbutil;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//entity声明定义，并且指定了映射数据表明
@Entity(tableName = "User")
public class UserTable {
    //设置主键，并且定义自增增
    @PrimaryKey(autoGenerate = true)
    public int uid;
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "faceName")
    private String faceName;

    @ColumnInfo(name = "faceFeature")
    private byte[] faceFeature;

    @ColumnInfo(name = "facePic")
    private byte[] facePic;

    public String getFaceName() {
        return faceName;
    }

    public void setFaceName(String faceName) {
        this.faceName = faceName;
    }

    public byte[] getFaceFeature() {
        return faceFeature;
    }

    public void setFaceFeature(byte[] faceFeature) {
        this.faceFeature = faceFeature;
    }

    public byte[] getFacePic() {
        return facePic;
    }

    public void setFacePic(byte[] facePic) {
        this.facePic = facePic;
    }
}
