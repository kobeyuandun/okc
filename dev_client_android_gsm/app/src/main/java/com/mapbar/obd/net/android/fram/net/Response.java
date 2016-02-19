package com.mapbar.obd.net.android.fram.net;

/**
 * Created by yun on 16/1/12.
 */
public interface Response {
    public interface Listener<T> {
        public void onResponse(T response);
    }

    public interface ErrorListener<T> {
        public void onResponse(T response);
    }
}
