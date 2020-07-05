package com.free.base.mvp;


import androidx.annotation.UiThread;

/**
 * MvpPresenter-Presenter 的基础类，控制的 MvpView 的子类
 * @param <V>
 */
public interface MvpPresenter<V extends MvpView> {
    @UiThread
    void attachView(V view);

    @UiThread
    void detachView();
}
