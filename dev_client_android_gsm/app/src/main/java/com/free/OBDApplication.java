package com.free;

import android.content.Context;

import com.free.base.BaseApplication;
import com.free.base.dragger.component.DaggerMyAppComponent;
import com.free.base.dragger.component.MyAppComponent;
import com.free.base.dragger.module.MyAppModule;


/**
 * Created by yun on 16/1/7.
 */
public class OBDApplication extends BaseApplication {

    private static MyAppComponent mMyAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initMyAppInject(this);
    }

    public void initMyAppInject(Context context) {
        mMyAppComponent = DaggerMyAppComponent.builder().appComponent(getAppComponent()).myAppModule(new MyAppModule()).build();
    }

    public static MyAppComponent getMyAppComponent() {
        return mMyAppComponent;
    }
}
