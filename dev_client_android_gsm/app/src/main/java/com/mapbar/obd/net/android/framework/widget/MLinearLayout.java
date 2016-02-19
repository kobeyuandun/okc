package com.mapbar.obd.net.android.framework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


public class MLinearLayout extends LinearLayout {
    private int mCurrentTab = 0;
    private Paint mPaint;
    private Point mStartPt;

    private Drawable mSelectedBgDrawable;
    private boolean isDrawSelectedBg = true;
    private int mTop = 0;
    private boolean isAnimating = false;
    private MyAnimation mMyAnimation = null;

    public MLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0x101008a);
    }

    public MLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        super.setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(3);
    }

	/*
     * @Override protected void onFocusChanged(boolean gainFocus, int direction,
	 * Rect previouslyFocusedRect) {
	 * System.out.println("----------------onFocusChanged------------"
	 * +gainFocus); super.onFocusChanged(gainFocus, direction,
	 * previouslyFocusedRect); }
	 * 
	 * @Override public void onWindowFocusChanged(boolean hasWindowFocus) {
	 * System.out.println("----------------onWindowFocusChanged------------"+
	 * hasWindowFocus); super.onWindowFocusChanged(hasWindowFocus); }
	 * 
	 * @Override protected void onAttachedToWindow() {
	 * System.out.println("----------------onAttachedToWindow------------");
	 * super.onAttachedToWindow(); }
	 * 
	 * @Override protected void onDetachedFromWindow() {
	 * System.out.println("----------------onDetachedFromWindow------------");
	 * super.onDetachedFromWindow(); }
	 * 
	 * @Override protected void onWindowVisibilityChanged(int visibility) {
	 * super.onWindowVisibilityChanged(visibility); if(visibility ==
	 * View.VISIBLE) { setDefaultSelected(this.mCurrentTab);
	 * System.out.println("["
	 * +this.mCurrentTab+"]----------------onWindowVisibilityChanged------------"
	 * +visibility); } }
	 */

    public void setDefaultSelected(int position) {
        if (this.getChildCount() == 0)
            return;
        setSelected(position);
    }

    public void setDrawSelectedBg(boolean draw) {
        isDrawSelectedBg = draw;
    }

    private void setSelected(int position) {
        if (mCurrentTab < this.getChildCount()) {
            View child = this.getChildAt(mCurrentTab);
            child.clearFocus();
            child.setSelected(false);
        }
        mCurrentTab = position;
        if (position < this.getChildCount()) {
            View child = this.getChildAt(position);
            child.requestFocus();
            child.setSelected(true);
        }
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        return false;
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        boolean drawAgain = false;
        if (isDrawSelectedBg && this.getChildCount() != 0 && mStartPt != null) {
            int x = mStartPt.x;
            int w = mStartPt.y;
            if (isAnimating && mMyAnimation != null) {
                if (!mMyAnimation.hasEnded()) {
                    MyAnimation.MyAnimaValue value = new MyAnimation.MyAnimaValue();
                    if (mMyAnimation.getTransformation(this.getDrawingTime(), value)) {
                        x = (int) value.valueX;
                        w = (int) value.valueY;
                        drawAgain = true;
                    }
                }
            }
            if (!drawAgain) {
                isAnimating = false;
                mMyAnimation = null;
            }
            if (mSelectedBgDrawable != null) {
                mSelectedBgDrawable.setBounds(x, getPaddingTop(), x + w, this.getHeight() - getPaddingBottom());
                mSelectedBgDrawable.draw(canvas);
            } else {
                canvas.drawLine(x, mTop, x + w, mTop, mPaint);
            }
        }
        super.dispatchDraw(canvas);
        if (drawAgain) {
            this.invalidate();
        }
    }

    public void setSelectedBgDrawable(Drawable drawable) {
        mSelectedBgDrawable = drawable;
    }

    public void setPaint(Paint paint) {
        this.mPaint = paint;
    }

    public void setSelection(int position) {
        setSelection(position, 350);
    }

    public void setSelection(int position, int time) {
        if (this.mCurrentTab == position)
            return;
        int count = this.getChildCount();
        if (count == 0)
            return;
        if (position < 0 || position >= count)
            return;
        setSelected(position);
        if (this.mStartPt != null) {
            View child = this.getChildAt(this.mCurrentTab);
            int w = child.getMeasuredWidth();
            int left = child.getLeft();

            MyAnimation.MyAnimaParam param = new MyAnimation.MyAnimaParam();
            param.fromX = mStartPt.x;
            param.toX = left;
            param.fromY = mStartPt.y;
            param.toY = w;
            mMyAnimation = new MyAnimation(param);
            mMyAnimation.setDuration(time);
            mMyAnimation.startNow();
            isAnimating = true;
            mStartPt.x = left;
            mStartPt.y = w;
        }
        this.postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int count = this.getChildCount();
        if (count > 0) {
            if (this.mStartPt == null)
                mStartPt = new Point();
            if (this.mCurrentTab >= count)
                this.mCurrentTab = 0;
            mTop = 0;
            for (int i = 0; i < count; i++) {
                View child = this.getChildAt(i);
                int ih = child.getTop() + child.getMeasuredHeight();
                mTop = Math.max(mTop, ih);
            }
            View child = this.getChildAt(this.mCurrentTab);
            int iw = child.getMeasuredWidth();
            int ileft = child.getLeft();
            mStartPt.x = ileft;
            mStartPt.y = iw;
        }
    }
}
