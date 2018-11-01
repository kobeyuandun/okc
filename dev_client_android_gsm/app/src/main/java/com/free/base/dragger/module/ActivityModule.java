package com.free.base.dragger.module;

import android.app.Activity;

import com.free.base.dragger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }
     @Provides
     @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
