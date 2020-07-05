package com.free.base.mvp.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.free.base.mvp.MvpPresenter;
import com.free.base.mvp.MvpView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public interface FragmentMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {

    /**
     * Must be called from {@link Fragment#onAttach(Activity)}
     * @param activity The activity the fragment is attached to
     */
    void onAttach(Activity activity);

    /**
     * Must be called from {@link Fragment#onCreate(Bundle)}
     * @param saved The bundle
     */
    void onCreate(Bundle saved);

    /**
     * Must be called from {@link Fragment#onViewCreated(View, Bundle)}
     * @param view The inflated view
     * @param savedInstanceState the bundle with the viewstate
     */
    void onViewCreated(View view, @Nullable Bundle savedInstanceState);

    /**
     * only called upon fragment creation and reattachment, not restar
     * Must be called from {@link Fragment#onActivityCreated(Bundle)}
     * @param savedInstanceState The saved bundle
     */
    void onActivityCreated(Bundle savedInstanceState);

    /**
     * Must be called from {@link Fragment#onStart()}
     */
    void onStart();

    /**
     * Must be called from {@link Fragment#onResume()}
     */
    void onResume();

    /**
     * Must be called from {@link Fragment#onPause()}
     */
    void onPause();

    /**
     * Must be called from {@link Fragment#onSaveInstanceState(Bundle)}
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * Must be called from {@link Fragment#onStop()}
     */
    void onStop();

    /**
     * Must be called from {@link Fragment#onDestroyView()}
     */
    void onDestroyView();

    /**
     * Must be called from {@link Fragment#onDestroy()}
     */
    void onDestroy();

    /**
     * Must be called from {@link Fragment#onDetach()}
     */
    void onDetach();

}
