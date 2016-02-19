package com.mapbar.obd.net.android;

import android.app.Application;

import com.mapbar.obd.net.android.framework.common.GlobalConfig;


/**
 * Created by yun on 16/1/7.
 */
public class OBDApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalConfig.setAppContext(this);
    }
}
