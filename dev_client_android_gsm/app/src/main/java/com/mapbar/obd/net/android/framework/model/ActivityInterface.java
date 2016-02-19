package com.mapbar.obd.net.android.framework.model;

import android.content.Intent;
import android.graphics.Point;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;

public interface ActivityInterface {
    void showPage(int flag, int index, int dataType, FilterObj filter, int position, Animation in, Animation out);

    void showPage(int flag, int index, int dataType, FilterObj filter, int position, Animation in,
                  Animation out, boolean outFront, Point outOffset, Point inOffset);

    void showJumpPage(int flag, int fromPos, int toPos, int dataType, FilterObj filter, int position,
                      Animation in, Animation out);

    void showPrevious(int index, Animation in, Animation out);

    void showPrevious(int index, Animation in, Animation out, Point outOffset, Point inOffset);

    void showPrevious(int index, FilterObj filter, Animation in, Animation out);

    void showPrevious(int flag, int index, Animation in, Animation out);

    void showJumpPrevious(int flag, int index, Animation in, Animation out);

    void showJumpPrevious(int flag, int index, FilterObj filter, Animation in, Animation out);

    void showJumpPrevious(int flag, int index, FilterObj filter, Animation in, Animation out, int titleAminType);

    void onPageActivity();

    void recycle();

    void hideSoftInput(EditText et);

    void showSoftInput(EditText et);

    void setWindowSoftInputMode(boolean resize);

    void startActivityForResult(Intent intent, int requestCode);

    void goHome();

    void goHome(int flag, int position);

    void setLoginOut();

    int getCurrentPageIndex();

    PageObject getCurrentPageObj();

    PageObject getPageObjByPos(int position);

    void showAlert(int resId);

    void showAlert(String word);

    void showDialog(int resIdTitle, String strText, int icon);

    void showDialog(String strTitle, String strText, int icon);

    int getScreenWidth();

    int getScreenHeight();

    void showProgressDialog(String title, String msg);

    void showProgressDialog(int msgId, boolean cancelable);

    void showProgressDialog(int msgId);

    void hideProgressDialog();

    View getTitleView(int viewPos);

    View getTitleRootView();

    void onTitleChanged(int viewPos, boolean rightToleft, int titleAminType);

    void onTitleChanged(int viewPos);

    void onTitleBarAnimationEnd();

    void exit();

    void setShowingPage(boolean isShowingPage);

    boolean isShowDialog();
}
