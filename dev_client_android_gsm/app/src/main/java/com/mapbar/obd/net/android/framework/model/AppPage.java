package com.mapbar.obd.net.android.framework.model;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;

import com.mapbar.obd.net.android.framework.control.PageManager;
import com.mapbar.obd.net.android.framework.inject.ViewInjectTool;


public abstract class AppPage implements PageInterface {
    private AppPage mParent;

    private int mTitleAnimType = 0;
    private FilterObj mFilter = new FilterObj();
    private int mFlag = -1;

    public AppPage(Context context, View rootview) {
        ViewInjectTool.inject(this, rootview);
    }

    public AppPage(Context context, View rootview, AppPage parent) {
        ViewInjectTool.inject(this, rootview);
        mParent = parent;
    }

    public AppPage getParent() {
        return this.mParent;
    }

    public int getTitleAnimType() {
        return mTitleAnimType;
    }

    @Override
    public void setTitleAminType(int type) {
        mTitleAnimType = type;
    }

    @Override
    public void setDataType(int type) {
    }

    @Override
    public int getMyViewPosition() {
        return 0;
    }

    @Override
    public void onAttachedToWindow(int flag, int position) {
    }

    public void onAttachedForUmeng(int flag, int position) {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onDetachedFromWindow(int flag) {
    }

    public void onDetachedForUmeng(int flag) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            PageManager.getInstance().showPrevious();
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
    }

    public void onSensorChanged(SensorEvent event) {
    }

    @Override
    public void onAnimationEnd(Animation animation, int fromFlag) {
    }

    @Override
    public void setFilterObj(int flag, FilterObj filter) {
        mFlag = flag;
        mFilter = filter;
    }

    @Override
    public FilterObj getFilterObj() {
        return mFilter;
    }

    @Override
    public int getFilterFlag() {
        return mFlag;
    }

    @Override
    public void onPause(int flag) {
    }

    @Override
    public void onRestart(int flag) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void showPage(int viewPos, FilterObj filter) {
        if (this.mParent != null)
            this.mParent.showPage(viewPos, filter);
    }

    public void showPrevious(int viewPos, FilterObj filter) {
        if (this.mParent != null)
            this.mParent.showPrevious(viewPos, filter);
    }

    public View getTitleView(int viewPos) {
        if (this.mParent != null)
            return this.mParent.getTitleView(viewPos);
        return null;
    }

    public void onTitleChanged(int viewPos, boolean rightToleft) {
        if (this.mParent != null)
            this.mParent.onTitleChanged(viewPos, rightToleft);
    }

    public void showDialog(int titleResId, int contentResId, int okResId, int cancelResId, View.OnClickListener listener) {
        if (this.mParent != null)
            this.mParent.showDialog(titleResId, contentResId, okResId, cancelResId, listener);
    }

    public void showDialog(String title, String msg, int okResId, int cancelResId, View.OnClickListener listener) {
        if (this.mParent != null)
            this.mParent.showDialog(title, msg, okResId, cancelResId, listener);
    }

    public void hideDialog() {
        if (this.mParent != null)
            this.mParent.hideDialog();
    }

    public boolean isDialogShow() {
        if (this.mParent != null)
            return this.mParent.isDialogShow();
        return false;
    }

    public void Exit() {
        if (this.mParent != null)
            this.mParent.Exit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public void onBackrun() {
    }

    @Override
    public PageRestoreData getRestoreData() {
        return null;
    }

    @Override
    public void onRestoreData(PageRestoreData data) {
    }

    public int getTitlePos(int viewPos) {
        return viewPos;
    }

    public void onWindowFocusChanged(boolean hasFocus) {

    }


    @Override
    public void obdConnectCallback(int event) {

    }

    @Override
    public void dataCollectCallback(int event, Object data) {

    }


    @Override
    public void onLoginResponse(int event, Object data) {

    }

    @Override
    public void onRegisterResponse(int event, Object data) {

    }

    @Override
    public void logoutResponse(int event, Object data) {

    }

    @Override
    public void queryCarResponse(int event, Object data) {

    }

    @Override
    public void setUserCar(int event, Object data) {

    }

    @Override
    public void getUserPhoto(int event, Object data) {

    }

    @Override
    public void onModifyResponse(int event, Object data) {

    }

    @Override
    public void getUserInfo(int event, Object data) {

    }

    @Override
    public void onRetrieveResponse(int event, Object data) {

    }

    @Override
    public void serverStarted() {

    }

    @Override
    public void tripEnd(int event, Object data) {
    }

    @Override
    public void onTitleBarAnimationEnd() {

    }

    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void setSnState(int event, Object data) {

    }

    @Override
    public void dataSyncTrip(int event, Object data) {

    }

    @Override
    public void firmware(int event, Object data) {
        // TODO Auto-generated method stub

    }

    @Override
    public void firmwareBroken(int event, Object data) {

    }

    @Override
    public void checkClear(int event, Object data) {

    }

    @Override
    public void needCloseWindows(int event, Object data) {

    }

    @Override
    public void needCloseDoors(int event, Object data) {

    }

    @Override
    public void reverseGeocodeEnd(int event, Object data) {

    }
}
