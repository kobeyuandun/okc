package com.mapbar.obd.net.android.framework.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;


public interface PageInterface {
    public void setTitleAminType(int type);

    public void onSaveInstanceState(Bundle outState);

    public void onRestoreInstanceState(Bundle savedInstanceState);

    public PageRestoreData getRestoreData();

    public void onRestoreData(PageRestoreData data);

    public void onBackrun();

    public void onPause(int flag);

    public void onRestart(int flag);

    public void onResume();

    public void onDestroy();

    public void onAttachedToWindow(int flag, int position);

    public void onDetachedFromWindow(int flag);

    public boolean onKeyDown(int keyCode, KeyEvent event);

    public int getMyViewPosition();

    public void onAnimationEnd(Animation animation, int fromFlag);

    public void setDataType(int type);

    public void setFilterObj(int flag, FilterObj filter);

    public FilterObj getFilterObj();

    public int getFilterFlag();

    public void onActivityResult(int requestCode, int resultCode, Intent data);


    public void obdConnectCallback(int event);

    public void dataCollectCallback(int event, Object data);


    public void onLoginResponse(int event, Object data);

    public void onRegisterResponse(int event, Object data);

    public void logoutResponse(int event, Object data);

    public void queryCarResponse(int event, Object data);

    public void setUserCar(int event, Object data);

    public void getUserPhoto(int event, Object data);

    public void onModifyResponse(int event, Object data);

    public void getUserInfo(int event, Object data);

    public void onRetrieveResponse(int event, Object data);

    public void serverStarted();

    public void tripEnd(int event, Object data);

    public void onTitleBarAnimationEnd();

    public void setSnState(int event, Object data);

    public void dataSyncTrip(int event, Object data);

    public void firmware(int event, Object data);

    public void firmwareBroken(int event, Object data);

    public void checkClear(int event, Object data);

    public void needCloseWindows(int event, Object data);

    public void needCloseDoors(int event, Object data);

    /**
     * 行程结束之后，逆地理查询完成之后的回调
     */
    public void reverseGeocodeEnd(int event, Object data);
}
