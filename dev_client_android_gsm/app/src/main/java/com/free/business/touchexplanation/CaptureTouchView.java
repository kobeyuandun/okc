package com.free.business.touchexplanation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author yuandunbin782
 * @ClassName CaptureTouchView
 * @Description
 * @date 2020/6/22
 */
public class CaptureTouchView extends View {
    private static final String TAG = "CaptureTouchView";

    public CaptureTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // setOnTouchListener(new OnTouchListener() {
        //     @Override
        //     public boolean onTouch(View v, MotionEvent event) {
        //         return true;
        //     }
        // });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        PrintUtils.printEvent(TAG, "dispatchTouchEvent", event);
        boolean result = super.dispatchTouchEvent(event);
        PrintUtils.printParam(TAG, "dispatchTouchEvent result is " + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PrintUtils.printEvent(TAG, "onTouchEvent", event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(500, 600);
    }
}
