package com.free.base.mvp;

import android.os.Bundle;

import com.free.OBDApplication;
import com.free.base.dragger.component.ActivityComponent;
import com.free.base.dragger.component.DaggerActivityComponent;
import com.free.base.dragger.component.MyAppComponent;
import com.free.base.dragger.module.ActivityModule;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpActivity<V, P> {

    private Unbinder unbinder;
    private ActivityComponent mActivityComponent;

    @Override
    public P createPresenter() {
        setupComponent(OBDApplication.getMyAppComponent());
        initInject();
        return presenter;
    }

    protected void setupComponent(MyAppComponent myAppComponent) {
        mActivityComponent = DaggerActivityComponent.builder().myAppComponent(myAppComponent).activityModule(getActivityModule()).build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        setUp(savedInstanceState);
    }

    private void setUp(Bundle savedInstanceState) {
        setupView();
        setupData(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void initInject();

    protected abstract void setupView();

    protected abstract void setupData(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
