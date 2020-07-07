package com.free.business.flowlayout;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewConfigurationCompat;

/**
 * @author yuandunbin
 * @date 2020/3/17
 */
public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";
    public static final int DEFAULT_SPACING = 20;
    private int mHorizontalSpacing = DEFAULT_SPACING;
    private int mVerticalSpacing = DEFAULT_SPACING;
    private int mUsedWidth = 0;
    private final List<Line> mLines = new ArrayList<Line>();
    private Line mLine = null;
    private int mMaxLinesCount = Integer.MAX_VALUE;


    private boolean scrollable = false;
    private int measureHeight;//代表本身的测量高度
    private int realHeight;//表示内容的高度
    
    private int mTouchSlop;//用来判断是不是一次滑动
    private float mLastInterceptX = 0;
    private float mLastInterceptY = 0;
    private float mLastY = 0;

    private OverScroller mScroller;

    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    private int mOverscrollDistance;

    private boolean mIsBeingDragged = false;

    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);//获取最小滑动距离
        Log.i(TAG, "FlowLayout: mTouchSlop= " + mTouchSlop);
        mScroller = new OverScroller(context);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mOverscrollDistance = configuration.getScaledOverscrollDistance();
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    public void fling(int velocityY) {
        if (getChildCount() > 0) {
            int height = measureHeight;
            int bottom = realHeight;

            mScroller.fling(getScrollX(), getScrollY(), 0, velocityY, 0, 0, 0,
                    Math.max(0, bottom - height), 0, height / 2);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                postInvalidateOnAnimation();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        float xInterceptX = ev.getX();
        float yInterceptY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastInterceptX = xInterceptX;
                mLastInterceptY = yInterceptY;
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = xInterceptX - mLastInterceptX;
                float dy = yInterceptY - mLastInterceptY;
                if (Math.abs(dy) > Math.abs(dx) && Math.abs(dy) > mTouchSlop) {
                    intercepted = true;//表示本身需要拦截处理
                    mIsBeingDragged = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        mLastInterceptX = xInterceptX;
        mLastInterceptY = yInterceptY;

        return intercepted;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onTouchEvent(MotionEvent event) {//处理滑动
        if (!scrollable) {
            return super.onTouchEvent(event);
        }
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        float currY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastY = currY;
                mIsBeingDragged = !mScroller.isFinished();
//                return mIsBeingDragged;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = mLastY - currY;//本次手势滑动了多大距离
                int oldScrollY = getScrollY();//已经偏移了的距离
//                int scrollY = oldScrollY + (int)dy;//这是本次需要偏移的距离 = 之前已经偏移了的距离 + 本次手势滑动的距离
//                if(scrollY < 0){
//                    scrollY = 0;
//                }
//                if(scrollY > realHeight - measureHeight){
//                    scrollY = realHeight - measureHeight;
//                }
//                scrollTo(0,scrollY);
                if(mIsBeingDragged){
                    if (dy > 0) {
                        dy += mTouchSlop;
                    } else {
                        dy -= mTouchSlop;
                    }
                }

                Log.i(TAG, "onTouchEvent: dy= " + dy);
                mScroller.startScroll(0, mScroller.getFinalY(), 0, (int) dy);//mCurrY = oldScrollY + dy*scale;
                invalidate();
                mLastY = currY;
                break;
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int initialVelocity = (int) velocityTracker.getYVelocity();

                if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
                    fling(-initialVelocity);
                } else if (mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0,
                        (realHeight - measureHeight))) {
                    postInvalidateOnAnimation();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {//mCurrY = oldScrollY + dy*scale;
            //int scrollY = oldScrollY + (int)dy;
//            int currY = mScroller.getCurrY();
//            if(currY < 0){
//                currY = 0;
//            }
//            if(currY > realHeight - measureHeight){
//                currY = realHeight - measureHeight;
//            }
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }

    }

    /**
     * 负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight() - getPaddingLeft();
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        measureHeight = sizeHeight;
        mLines.clear();
        mLine = new Line();
        mUsedWidth = 0;
        final int count = getChildCount();
        // 遍历每个子元素
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(sizeWidth,
                    modeWidth == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeWidth);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(sizeHeight,
                    modeHeight == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeHeight);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            if (mLine == null) {
                mLine = new Line();
            }
            int childWidth = child.getMeasuredWidth();
            // 增加使用的宽度
            mUsedWidth += childWidth;
            // 使用宽度小于总宽度，该 child 属于这一行。
            if (mUsedWidth <= sizeWidth) {
                // 添加 child
                mLine.addView(child);
                // 加上间隔
                mUsedWidth += mHorizontalSpacing;
                // 加上间隔后如果大于等于总宽度，需要换行
                if (mUsedWidth >= sizeWidth) {
                    if (!newLine()) {
                        break;
                    }
                }
            } else {
                if (mLine.getViewCount() == 0) {
                    mLine.addView(child);
                    if (!newLine()) {
                        break;
                    }
                } else {
                    if (!newLine()) {
                        break;
                    }
                    mLine.addView(child);
                    mUsedWidth += childWidth + mHorizontalSpacing;
                }
            }
        }

        if (mLine != null && mLine.getViewCount() > 0 && !mLines.contains(mLine)) {
            mLines.add(mLine);
        }

        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int totalHeight = 0;
        final int linesCount = mLines.size();
        for (int i = 0; i < linesCount; i++) {
            totalHeight += mLines.get(i).mHeight;
        }
        totalHeight += mVerticalSpacing * (linesCount - 1);
        totalHeight += getPaddingTop() + getPaddingBottom();
//        if (modeHeight == MeasureSpec.EXACTLY) {
//            totalHeight = Math.max(sizeHeight, totalHeight);
//        }
        realHeight = totalHeight;

        scrollable = realHeight > measureHeight;
        // 设置布局的宽高，宽度直接采用父 view 传递过来的最大宽度，而不用考虑子 view 是否填满宽度，因为该布局的特性就是填满一行后，再换行
        // 高度根据设置的模式来决定采用所有子 View 的高度之和还是采用父 view 传递过来的高度
        setMeasuredDimension(totalWidth, resolveSize(realHeight, heightMeasureSpec));
//        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : totalWidth, realHeight);
    }

    /**
     * 规划好每一行的子 View 的开始摆放的位置 (也就是每一行左上角的坐标), 然后遍历每一行，让每一行布局好自己的子 View，整个控件的布局也就完成了。
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int left = getPaddingLeft();
            int top = getPaddingTop();
            final int linesCount = mLines.size();
            for (int i = 0; i < linesCount; i++) {
                final Line oneLine = mLines.get(i);
                oneLine.layoutView(left, top);
                top += oneLine.mHeight + mVerticalSpacing;
            }
        }
    }

    /**
     * 新增加一行
     */
    private boolean newLine() {
        mLines.add(mLine);
        if (mLines.size() < mMaxLinesCount) {
            mLine = new Line();
            mUsedWidth = 0;
            return true;
        }
        return false;
    }

    /**
     * 代表着一行，封装了一行所占高度，该行子 View 的集合，以及所有 View 的宽度总和
     */
    class Line {
        int mWidth = 0;
        int mHeight = 0;
        List<View> views = new ArrayList<View>();
        // 往该行中添加一个
        public void addView(View view) {
            views.add(view);
            mWidth += view.getMeasuredWidth();
            int childHeight = view.getMeasuredHeight();
            // 高度等于一行中最高的 View
            mHeight = mHeight < childHeight ? childHeight : mHeight;
        }

        public int getViewCount() {
            return views.size();
        }

        /**
         * 下面的方法是给子 View 布局摆放，注意多余的空间应该平均分配给其他的控件
         *
         * @param l
         * @param t
         */
        public void layoutView(int l, int t) {
            int left = l;
            int top = t;
            int count = getViewCount();
            int layoutWidth = getMeasuredWidth() - getPaddingLeft()
                    - getPaddingRight();
            int surplusWidth = layoutWidth - mWidth - mHorizontalSpacing * (count - 1);
            if (surplusWidth >= 0) {
                int splitSpacing = (int) (surplusWidth / count + 0.5);
                for (int i = 0; i < count; i++) {
                    final View view = views.get(i);
                    int childWidth = view.getMeasuredWidth();
                    int childHeight = view.getMeasuredHeight();
                    int topOffset = (int) ((mHeight - childHeight) / 2.0 + 0.5);
                    if (topOffset < 0) {
                        topOffset = 0;
                    }
                    childWidth = childWidth + splitSpacing;
                    view.getLayoutParams().width = childWidth;
                    if (splitSpacing > 0) {
                        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
                        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
                        view.measure(widthMeasureSpec, heightMeasureSpec);
                    }
                    // 布局 View
                    view.layout(left, top + topOffset, left + childWidth, top + topOffset + childHeight);
                    left += childWidth + mHorizontalSpacing;
                }
            } else {
                //最后一行要单独处理
                View view = views.get(0);
                view.layout(left, top, left + view.getMeasuredWidth(), top + view.getMeasuredHeight());
            }
        }
    }
}