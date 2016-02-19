package com.mapbar.obd.net.android.framework;

public class Configs {
    public final static int VIEW_POSITION_NONE = -1;
    public final static int VIEW_POSITION_SPLASH = 0;
    public final static int VIEW_POSITION_MAIN = 1;
    public final static int VIEW_POSITION_LOGIN = 200;
    public final static int VIEW_POSITION_REGISTER = 201;
    public final static int VIEW_POSITION_RETRIEVE_PASSWORD = 202;
    public final static int VIEW_POSITION_FILL_IN_VIN = 203;
    public final static int VIEW_POSITION_ADD_CAR_INFO = 204;
    public final static int VIEW_POSITION_BINDING_DEVICE = 205;
    public final static int VIEW_POSITION_CAR_INFO = 206;
    public final static int VIEW_POSITION_SETTING = 207;
    public final static int VIEW_POSITION_BINDING_DEVICE_INFO = 208;
    public final static int VIEW_POSITION_TEST = 9001;


    public final static int VIEW_FLAG_NONE = -1;
    public final static int DATA_TYPE_NONE = -1;

    //
    public final static String FILE_PATH = "/mapbar/obd_gsm";

    /**
     * 在com.mapbar.obd.Config.DEBUG = false时，此开关无效。只用于专项测试全数据
     */
    public static boolean AlwaysOBDDetailMode = false;
}
