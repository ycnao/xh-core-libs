package com.xcore.app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

/**
 * MyApplication
 * author: Created by 闹闹 on 2019/7/10
 * version: 1.0.0
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    public static Context applicationContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        instance = this;
        applicationContext = this;
        init();
    }

    public static MyApplication instance() {
        return instance;
    }

    public void init() {
//        SerialHandler.init(this, JxConstants.SPORT_NAME_1, 921600);

    }
}
