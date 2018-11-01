package com.free.base;

import android.app.Application;
import android.content.Context;

import com.free.base.dragger.component.AppComponent;
import com.free.base.dragger.component.DaggerAppComponent;
import com.free.base.dragger.module.AppModule;
import com.free.base.utils.AndroidUtils;

public class BaseApplication extends Application {

    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppInject(this);
        AndroidUtils.init(getApplicationContext());
    }

    public void initAppInject(Context context) {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule((Application) context))
                .build();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

}
