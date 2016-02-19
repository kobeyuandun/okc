package com.mapbar.obd.net.android.framework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Button;

public class MSectorButton extends Button {
    private int mButtonType = 0;
    private Region myRegion = new Region();
    private boolean isTouchMe = false;

    public MSectorButton(Context context) {
        this(context, null, 0x101008a);
    }

    public MSectorButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0x101008a);
    }

    public MSectorButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        super.setGravity(Gravity.CENTER);

        int buttonTypeId = getResId(":attr/buttonType");
        int[] arrIds = new int[]{buttonTypeId};
        TypedArray a = context.obtainStyledAttributes(attrs, arrIds);
        mButtonType = a.getInt(0, 0);
        // System.out.println("mButtonType="+mButtonType);
        a.recycle();
        super.setFocusable(true);
        super.setClickable(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        switch (mButtonType) {
            case 0: {
                Path path = getSectorPath(w, h / 2, w - 15, 135, 225);
                myRegion.setPath(path, new Region(0, 0, w, h));
                break;
            }
            case 1: {
                Path path = getSectorPath(w / 2, h, h - 15, 225, 315);
                myRegion.setPath(path, new Region(0, 0, w, h));
                break;
            }
            case 2: {
                Path path = getSectorPath(0, h / 2, w - 15, -45, 45);
                myRegion.setPath(path, new Region(0, 0, w, h));
                break;
            }
            case 3: {
                Path path = getSectorPath(w / 2, 0, h - 15, 45, 135);
                myRegion.setPath(path, new Region(0, 0, w, h));
                break;
            }
        }
    }

    private Path getSectorPath(float center_X, float center_Y, float r, float startAngle, float sweepAngle) {
        Path path = new Path();
        // 下面是获得一个三角形的剪裁区
        path.moveTo(center_X, center_Y); // 圆心
        path.lineTo((float) (center_X + r * Math.cos(startAngle * Math.PI / 180)), // 起始点角度在圆上对应的横坐标
                (float) (center_Y + r * Math.sin(startAngle * Math.PI / 180))); // 起始点角度在圆上对应的纵坐标

        path.lineTo((float) (center_X + r * Math.cos(sweepAngle * Math.PI / 180)), // 终点角度在圆上对应的横坐标
                (float) (center_Y + r * Math.sin(sweepAngle * Math.PI / 180))); // 终点点角度在圆上对应的纵坐标

        path.close();

        // 设置一个正方形，内切圆
        RectF rectF = new RectF(center_X - r, center_Y - r, center_X + r, center_Y + r);

        // 下面是获得弧形剪裁区的方法
        path.addArc(rectF, startAngle, sweepAngle - startAngle);

        return path;
    }

    private int getResId(String str) {
        return this.getResources().getIdentifier(this.getContext().getPackageName() + str, null, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (isTouchMe) {
            if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP)
                isTouchMe = false;
            return super.onTouchEvent(event);
        }
        if (action == MotionEvent.ACTION_DOWN) {
            isTouchMe = false;
            if (myRegion.contains((int) event.getX(), (int) event.getY())) {
                isTouchMe = true;
                return super.onTouchEvent(event);
            }
        }
        return false;
    }
}
