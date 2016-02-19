package com.mapbar.obd.net.android.framework.common;

/**
 * Created by liuyy on 2016/1/17.
 */
public class LayoutUtils {

    public static int getDimenPx(int id) {
        return (int) GlobalConfig.getAppContext().getResources().getDimension(id);
    }


}
