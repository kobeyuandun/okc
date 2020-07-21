package com.free.business.viewpagerlazy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author yuandunbin
 * @date 2020/7/13
 */
public class FragmentDelegater extends Fragment {
    private static final String TAG = "FragmentDelegater";

    private final Fragment mFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dumpLifeCycle("onAttach: " + mFragment.hashCode());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dumpLifeCycle("onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        dumpLifeCycle("onCreateView");
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dumpLifeCycle("onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dumpLifeCycle("onActivityCreated");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
//        dumpLifeCycle("onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        dumpLifeCycle("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        dumpLifeCycle("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        dumpLifeCycle("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        dumpLifeCycle("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dumpLifeCycle("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dumpLifeCycle("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dumpLifeCycle("onDetach");
    }

    public FragmentDelegater(Fragment fragment) {
        super();
        mFragment = fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.i(TAG, mFragment.getClass().getSimpleName() + " -> setUserVisibleHint isVisibleToUser: " + isVisibleToUser + " =============");
    }

    /**
     * 第一次进来不会触发
     * 跳到下一个界面会触发：true
     * 在回来的时候会触发：false
     * 返回上一级，不会触发
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, mFragment.getClass().getSimpleName() + " -> onHiddenChanged hidden: " + hidden + " ***************");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.i(TAG, "requestCode: " + requestCode + " resultCode: " + resultCode + " data: " + data);
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
//        dumpLifeCycle("onInflate");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        dumpLifeCycle("onSaveInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        dumpLifeCycle("onConfigurationChanged");
    }

    public void dumpLifeCycle(final String method) {
        Log.i(TAG, "name: " + mFragment.getClass().getSimpleName() + " -> " + method);
    }
}
