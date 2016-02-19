package com.mapbar.obd.net.android;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.mapbar.obd.net.android.framework.Configs;
import com.mapbar.obd.net.android.framework.control.activity.BaseActivity;
import com.mapbar.obd.net.android.framework.log.LogManager;
import com.mapbar.obd.net.android.framework.model.PageObject;

import static com.mapbar.obd.net.android.framework.control.PageManager.ManagerHolder.pageManager;


public class MainActivity extends BaseActivity {

    private Handler mHandler;
    private boolean isFinishInitView = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        mHandler = new Handler();
        pageManager.init(this);
        LogManager.getInstance().init(this);
    }

    @Override
    public PageObject createPage(int index) {
        return pageManager.createPage(index);
    }

    @Override
    public int getAnimatorResId() {
        return R.id.animator;
    }

    @Override
    public int getMainPosition() {
        return Configs.VIEW_POSITION_MAIN;
    }

    @Override
    public int getNonePositioin() {
        return Configs.VIEW_POSITION_NONE;
    }

    @Override
    public int getViewNoneFlag() {
        return Configs.VIEW_FLAG_NONE;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isBack = pageManager.goBack();
        if (!isBack) {
            System.exit(0);
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onFinishedInit() {
        if (!isFinishInitView) {
            isFinishInitView = true;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initView();
                }
            }, 1000);
        }
    }

    private void initView() {
        pageManager.goPage(Configs.VIEW_POSITION_LOGIN);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View getTitleRootView() {
        return null;
    }

    @Override
    public void onTitleBarAnimationEnd() {

    }


}