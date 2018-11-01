package com.free.business.main;

import android.view.View;

import com.free.base.config.MyApi;
import com.free.base.mvp.RxMvpPresenter;

import javax.inject.Inject;

public class MainPresent extends RxMvpPresenter<MainView> {
    MyApi mMyApi;

    @Inject
    public MainPresent(MyApi myApi) {
        mMyApi = myApi;

    }

}
