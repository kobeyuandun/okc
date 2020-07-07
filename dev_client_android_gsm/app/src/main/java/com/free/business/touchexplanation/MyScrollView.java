package com.free.business.touchexplanation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author yuandunbin782
 * @ClassName MyScrollView
 * @Description
 * @date 2020/6/22
 */
public class MyScrollView extends ScrollView {
    private static final String TAG = "MyScrollView";

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        PrintUtils.printEvent(TAG, "onInterceptTouchEvent", ev);
        boolean intercepted = super.onInterceptTouchEvent(ev);
        PrintUtils.printParam(TAG, "onInterceptTouchEvent intercepted is " + intercepted);
        return intercepted;
    }
}
