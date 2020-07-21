package com.free.business.viewpagerlazy;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.free.base.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Fragment1 extends LazyFragment2 {
    private static final String TAG = "Fragment1";

    public static Fragment newIntance() {
        Fragment1 fragment = new Fragment1();
        fragment.setFragmentDelegater(new FragmentDelegater(fragment));
        return fragment;
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_1;
    }

    @Override
    protected void initView(View view) {
        Log.d(TAG, "initView: ");
    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
