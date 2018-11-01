package com.free.business.splash;

import com.free.base.config.MyApi;
import com.free.base.mvp.RxMvpPresenter;

import javax.inject.Inject;

public class SplashPresent extends RxMvpPresenter<SplashView> {
    MyApi mMyApi;

    @Inject
    public SplashPresent(MyApi myApi) {
        mMyApi = myApi;

    }

}
