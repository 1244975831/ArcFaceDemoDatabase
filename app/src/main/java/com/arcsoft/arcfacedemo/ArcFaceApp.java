package com.arcsoft.arcfacedemo;

import android.app.Application;

public class ArcFaceApp extends Application {
    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Application getApplication() {
        return application;
    }
}
