package com.mapbar.obd.net.android.framework.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class NoResizeRelativeLayout extends RelativeLayout {
    private int mHeight;
    // private int mTmpHeight;
    private Handler mHandler;
    private boolean mCanNotResize = false;
    private OnSoftInputListener mOnSoftInputListener;

    public NoResizeRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0x101008a);
    }

    public NoResizeRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        mHandler = new Handler();
    }

    public void setCanNotResize(boolean notResize) {
        this.mCanNotResize = notResize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h < mHeight) {
            if (mCanNotResize) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ViewGroup.LayoutParams fl = (ViewGroup.LayoutParams) getLayoutParams();
                        fl.height = mHeight;
                        requestLayout();
                    }
                });
            }
            if (mOnSoftInputListener != null)
                mOnSoftInputListener.onSoftInputChanged(true, mHeight - h);
        } else {
            if (mHeight != 0) {
                if (mOnSoftInputListener != null)
                    mOnSoftInputListener.onSoftInputChanged(false, h - mHeight);
            }
            mHeight = h;
        }
    }

    public void setOnSoftInputListener(OnSoftInputListener listener) {
        this.mOnSoftInputListener = listener;
    }

    public static interface OnSoftInputListener {
        void onSoftInputChanged(boolean show, int h);
    }
}
