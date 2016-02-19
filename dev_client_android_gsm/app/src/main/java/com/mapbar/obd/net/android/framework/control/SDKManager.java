package com.mapbar.obd.net.android.framework.control;

import android.os.Environment;

import com.mapbar.obd.Manager;
import com.mapbar.obd.net.android.framework.Configs;
import com.mapbar.obd.net.android.framework.common.GlobalConfig;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by liuyy on 2016/1/22.
 */
public class SDKManager {

    private static SDKManager sdkManager;
    private Manager manager;
    private Manager.Listener listener;
    private ArrayList<WeakReference<SDKListener>> regListeners;

    private SDKManager() {
    }

    public static SDKManager getInstance() {
        if (sdkManager == null) {
            sdkManager = new SDKManager();
        }
        sdkManager.init();
        return sdkManager;
    }

    public void init() {
        manager = Manager.getInstance();
        regListeners = new ArrayList<>();
        listener = new Manager.Listener() {
            @Override
            public void onEvent(int event, Object o) {
                /*for (WeakReference<Manager.Listener> regListener : regListeners) {
                    if (regListener != null && regListener.get() != null) {
                        regListener.get().onEvent(event, o);
                    }
                }*/
                for (int i = regListeners.size() - 1; i >= 0; i--) {

                    if (regListeners.get(i) == null || regListeners.get(i).get() == null || !regListeners.get(i).get().isReged()) {
                        regListeners.remove(i);
                    } else {
                        regListeners.get(i).get().onEvent(event, o);
                    }
                }
            }
        };
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Configs.FILE_PATH;
        manager.init(GlobalConfig.getAppContext(), listener, sdPath, null);
    }

    /**
     * 设置一个监听SDK回调的listener
     *
     * @param listener
     */
    public void setSdkListener(SDKListener listener) {
        listener.setIsReged(true);
        WeakReference<SDKListener> weakListener = new WeakReference<>(listener);
        regListeners.add(weakListener);
    }

    /**
     * 解除SDK回调监听
     *
     * @param listener
     */
    public void releaseSdkListener(SDKListener listener) {
        listener.setIsReged(false);
    }


    public static abstract class SDKListener {
        boolean isReged;

        public abstract void onEvent(int event, Object o);

        protected boolean isReged() {
            return isReged;
        }

        protected void setIsReged(boolean isReged) {
            this.isReged = isReged;
        }
    }

}
