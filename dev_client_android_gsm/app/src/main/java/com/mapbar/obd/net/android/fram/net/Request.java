package com.mapbar.obd.net.android.fram.net;


import com.mapbar.obd.net.android.framework.common.MainThreadPostUtils;

/**
 * Created by yun on 16/1/12.
 */
public class Request<T> {
    private DataCallback<T> mCallback;
    Response.Listener<T> listener = new Response.Listener<T>() {
        @Override
        public void onResponse(final T response) {
            MainThreadPostUtils.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onDataCallback(response);
                }
            });
        }
    };
    Response.ErrorListener<T> errorListener = new Response.ErrorListener<T>() {
        @Override
        public void onResponse(final T response) {
            MainThreadPostUtils.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onDataCallback(response);
                }
            });
        }
    };

    public DataCallback<T> getmCallback() {
        return mCallback;
    }

    protected void setmCallback(DataCallback<T> mCallback) {
        this.mCallback = mCallback;
    }
}
