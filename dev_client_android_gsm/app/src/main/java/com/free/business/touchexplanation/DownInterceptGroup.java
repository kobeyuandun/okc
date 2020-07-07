package com.free.business.touchexplanation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.free.business.touchexplanation.PrintUtils.printEvent;


/**
 * @author yuandunbin782
 * @ClassName DownInterceptGroup
 * @Description
 * @date 2020/6/22
 */
public class DownInterceptGroup extends FrameLayout {
    private static final String TAG = "DownInterceptGroup";

    public DownInterceptGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        printEvent(TAG, "dispatchTouchEvent", ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        printEvent(TAG, "onInterceptTouchEvent", ev);
        boolean result = super.onInterceptTouchEvent(ev);
        PrintUtils.printParam(TAG, "onInterceptTouchEvent result is " + result);
        return result;
    }
}
