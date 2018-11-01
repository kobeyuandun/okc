package com.free.base.dragger.component;

import android.app.Activity;

import com.free.base.dragger.module.ActivityModule;
import com.free.base.dragger.scope.ActivityScope;
import com.free.business.main.MainActivity;
import com.free.business.splash.SplashActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = MyAppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);
}
