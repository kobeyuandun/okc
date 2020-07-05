package com.free.base.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.free.base.mvp.delegate.FragmentMvpDelegate;
import com.free.base.mvp.delegate.FragmentMvpDelegateImpl;
import com.free.base.mvp.delegate.MvpDelegateCallback;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class MvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends RxFragment implements MvpDelegateCallback<V, P>, MvpView {

    protected FragmentMvpDelegate<V, P> mvpDelegate;

    @Inject
    protected P presenter;

    /**
     * Creates a new presenter instance,This method will be
     * called from {@link #onViewCreated(View, Bundle)}
     */
    public abstract P createPresenter();

    @NonNull
    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public V getMvpView() {
        return (V) this;
    }

    /**
     * Get the mvp delegate. This is internally used for creating presenter, attaching
     * and detaching view from presenter.
     * @return {@link FragmentMvpDelegateImpl}
     */
    @NonNull
    protected FragmentMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new FragmentMvpDelegateImpl<>(this, this);
        }
        return mvpDelegate;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMvpDelegate().onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMvpDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMvpDelegate().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getMvpDelegate().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getMvpDelegate().onDetach();
    }

}

