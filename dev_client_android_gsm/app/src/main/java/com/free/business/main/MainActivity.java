package com.free.business.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.free.base.R;
import com.free.base.mvp.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity<MainView, MainPresent> implements MainView {
    private static final String TAG = "MainActivity";
    private boolean isyou = false;
    private int you = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
//        if (isyou == false) {
//            return;
//        }
//        you++;
        String test = "";
        if (TextUtils.isEmpty(test)) {
            test = "hh";
            Log.e(TAG, "setupData1: " + test);
        } else {
            switch (test) {
                case "1":
                    test = "you";
                    Log.e(TAG, "setupData: " + test);
                    break;
                case "2":
                    test = "me";
                    Log.e(TAG, "setupData: " + test);
                    break;
                default:
                    test = "hh";
                    Log.e(TAG, "setupData: " + test);
                    break;
            }
        }
//        Log.e(TAG, "setupData: " + you);
    }
}
