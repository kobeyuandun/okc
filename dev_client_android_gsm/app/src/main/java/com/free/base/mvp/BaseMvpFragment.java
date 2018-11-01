package com.free.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.free.OBDApplication;
import com.free.base.dragger.component.DaggerFragmentComponent;
import com.free.base.dragger.component.FragmentComponent;
import com.free.base.dragger.component.MyAppComponent;
import com.free.base.dragger.module.FragmentModule;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends MvpFragment<V, P> {
    protected Unbinder mUnbinder;

    private FragmentComponent mFragmentComponent;

    @Override
    public P createPresenter() {
        setupComponent(OBDApplication.getMyAppComponent());
        initInject();
        return presenter;
    }


    protected void setupComponent(MyAppComponent myAppComponent) {
        mFragmentComponent = DaggerFragmentComponent.builder().myAppComponent(myAppComponent).fragmentModule(getFragmentModule()).build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup(savedInstanceState, view);
    }

    private void setup(Bundle savedInstanceState, View rootView) {
        setupView(rootView);
        setupData(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void initInject();

    protected abstract void setupView(View rootView);

    protected abstract void setupData(Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
