package com.mapbar.obd.net.android.framework.model;

import android.content.Intent;
import android.graphics.Point;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;

public class SampleActivityInterface implements ActivityInterface {

    @Override
    public void showPage(int flag, int index, int dataType, FilterObj filter, int position, Animation in, Animation out) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showPage(int flag, int index, int dataType, FilterObj filter, int position, Animation in,
                         Animation out, boolean outFront, Point outOffset, Point inOffset) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showJumpPage(int flag, int fromPos, int toPos, int dataType, FilterObj filter, int position,
                             Animation in, Animation out) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showPrevious(int index, Animation in, Animation out) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showPrevious(int index, Animation in, Animation out, Point outOffset, Point inOffset) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showPrevious(int index, FilterObj filter, Animation in, Animation out) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showPrevious(int flag, int index, Animation in, Animation out) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showJumpPrevious(int flag, int index, Animation in, Animation out) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showJumpPrevious(int flag, int index, FilterObj filter, Animation in, Animation out) {

    }

    @Override
    public void showJumpPrevious(int flag, int index, FilterObj filter, Animation in, Animation out, int titleAminType) {

    }

    @Override
    public void onPageActivity() {
        // TODO Auto-generated method stub

    }

    @Override
    public void recycle() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hideSoftInput(EditText et) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showSoftInput(EditText et) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setWindowSoftInputMode(boolean resize) {
        // TODO Auto-generated method stub

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void goHome() {
        // TODO Auto-generated method stub

    }

    @Override
    public void goHome(int flag, int position) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setLoginOut() {
        // TODO Auto-generated method stub

    }

    @Override
    public int getCurrentPageIndex() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public PageObject getCurrentPageObj() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageObject getPageObjByPos(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void showAlert(int resId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showAlert(String word) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showDialog(int resIdTitle, String strText, int icon) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showDialog(String strTitle, String strText, int icon) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getScreenWidth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getScreenHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void showProgressDialog(String title, String msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showProgressDialog(int msgId, boolean cancelable) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showProgressDialog(int msgId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void hideProgressDialog() {
        // TODO Auto-generated method stub

    }

    @Override
    public View getTitleView(int viewPos) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onTitleChanged(int viewPos, boolean rightToleft, int titleAminType) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTitleChanged(int viewPos) {
        // TODO Auto-generated method stub

    }

    @Override
    public View getTitleRootView() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onTitleBarAnimationEnd() {
        // TODO Auto-generated method stub

    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShowingPage(boolean isShowingPage) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isShowDialog() {
        return false;
    }
}
