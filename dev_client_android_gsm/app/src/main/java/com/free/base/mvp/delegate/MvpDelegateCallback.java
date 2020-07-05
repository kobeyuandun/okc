package com.free.base.mvp.delegate;


import com.free.base.mvp.MvpPresenter;
import com.free.base.mvp.MvpView;

/**
 * 用于对获取 MvpView、创建以及获取 presenter
 *
 * @param <P>
 * @param <V>
 */
public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {
    /**
     * Sets the presenter instance
     *
     * @param presenter The presenter instance
     */
    void setPresenter(P presenter);

    /**
     * Get the presenter. If null is returned, then a internally a new presenter instance
     * gets created by calling {@link #createPresenter()}
     *
     * @return the presenter instance. can be null.
     */
    P getPresenter();

    /**
     * Creates the presenter instance
     *
     * @return the created presenter instance
     */
    P createPresenter();

    /**
     * Get the MvpView for the presenter
     *
     * @return The view associated with the presenter
     */
    V getMvpView();
}
