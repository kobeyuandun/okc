package com.free.business.flowlayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

import com.free.base.R;

import java.util.ArrayList;
import java.util.List;

import androidx.core.view.ViewConfigurationCompat;

/**
 * @author yuandunbin
 * @date 2020/6/17
 * <p>
 * 自定义ViewGroup: 则只需要重写onMeasure()和onLayout()
 * <p>
 * <p>
 * onMeasure：
 * 1. 确定自身的大小
 * 2. 确定子view的大小
 * <p>
 * 尺寸： 200dp, match_parent, wrap_content
 * <p>
 * 流程：
 * 1. ViewGroup开始测量自己的尺寸
 * 2. 为每个子View计算测量的限制信息
 * 3. 把上一步确定的限制信息，传递给每一个子View，然后子View开始measure
 * 自己的尺寸
 * 4. 获取子View测量完成后的尺寸
 * 5. ViewGroup根据自身的情况，计算自己的尺寸
 * 6. 保存自身的尺寸
 * <p>
 * onLayout
 * 1. 根据规则确定子view位置
 * <p>
 * 流程：
 * 1. 遍历子View for
 * 2. 确定自己的规则
 * 3. 子View的测量尺寸
 * 4. left,top,right,bottom
 * 6. child.layout
 */
public class FlowLayout1 extends ViewGroup {

    //每一行的子View
    private List<View> lineViews;
    //所有行，一行一行存储
    private List<List<View>> views;
    //每一行的高度存储
    private List<Integer> heights;

    private int measureHeight;

    private float mLastY = 0;
    private int realHeight;
    private boolean scrollable;

    private float mLastInterceptX = 0;
    private float mLastInterceptY = 0;
    //用来判断是不是一次滑动
    private int mTouchSlop;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mOverscrollDistance;
    private boolean mIsBeingDragged = false;

    public FlowLayout1(Context context) {
        this(context, null);
    }

    public FlowLayout1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        //获取最小滑动距离
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
        mScroller = new OverScroller(context);
        mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mOverscrollDistance = viewConfiguration.getScaledOverscrollDistance();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        float xInterceptedX = ev.getX();
        float yInterceptedY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastInterceptX = xInterceptedX;
                mLastInterceptY = yInterceptedY;
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = xInterceptedX - mLastInterceptX;
                float dy = yInterceptedY - mLastInterceptY;
                if (Math.abs(dy) > Math.abs(dx) && Math.abs(dy) > mTouchSlop) {
                    intercepted = true;
                    mIsBeingDragged = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        mLastInterceptX = xInterceptedX;
        mLastInterceptY = yInterceptedY;

        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
                break;
            case MotionEvent.ACTION_MOVE:
                //本次手势滑动了多大距离
                float dy = mLastY - currY;
                int oldScrollY = getScrollY();
//                int scrollY = oldScrollY + (int) dy;
//                if (scrollY < 0) {
//                    scrollY = 0;
//                }
//                if (scrollY > realHeight - measureHeight) {
//                    scrollY = realHeight - measureHeight;
//                }
//                scrollTo(0, scrollY);
                if (mIsBeingDragged) {
                    if (dy > 0) {
                        dy += mTouchSlop;
                    } else {
                        dy -= mTouchSlop;
                    }
                }
                mScroller.startScroll(0, mScroller.getFinalY(), 0, (int) dy);
                invalidate();
                mLastY = currY;
                break;
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int initialVelocity = (int) velocityTracker.getYVelocity();
                if (Math.abs(initialVelocity) > mMinimumVelocity) {
                    fling(-initialVelocity);
                } else if (mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, realHeight - measureHeight)) {
                    postInvalidate();
                }
                break;
        }
        return super.onTouchEvent(event);

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }

    private void fling(int velocityY) {
        if (getChildCount() > 0) {
            int height = measureHeight;
            int bottom = realHeight;
            mScroller.fling(getScrollX(), getScrollY(), 0, velocityY, 0, 0, 0, Math.max(0, bottom - height), 0, height / 2);
            postInvalidate();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    /**
     * 流程：
     * 1. ViewGroup开始测量自己的尺寸
     * 2. 为每个子View计算测量的限制信息
     * 3. 把上一步确定的限制信息，传递给每一个子View，然后子View开始measure
     * 自己的尺寸
     * 4. 获取子View测量完成后的尺寸
     * 5. ViewGroup根据自身的情况，计算自己的尺寸
     * 6. 保存自身的尺寸
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureHeight = heightSize;
        //记录当前行的宽度和高度
        //宽度是当前行子view宽度之和
        int lineWidth = 0;
        //高度是当前行子view中高度的最大值
        int lineHeight = 0;

        //整个流式布局的宽度和高度
        //所有行中宽度的最大值
        int flowWidth = 0;
        // 所以行的高度的累加
        int flowHeight = 0;

        //初始化参数列表
        init();

        //遍历所有子View,对子View进行测量，分配到具体行
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            //测量子View，测量当前子View的宽度和高度
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //获取到当前子View的测量的宽度和高度
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            //换行
            if (lineWidth + childWidth > widthSize) {
                //如果一行只有一个元素
                if (lineViews.size() == 1 && lineViews.get(0).getLayoutParams().height == LayoutParams.MATCH_PARENT) {
                    lineHeight = dp2px(150);
                }
                views.add(lineViews);
                //创建新的一行
                lineViews = new ArrayList<>();
                flowWidth = Math.max(flowWidth, lineWidth);
                flowHeight += lineHeight;
                heights.add(lineHeight);
                lineWidth = 0;
                lineHeight = 0;

            }
            lineViews.add(child);
            lineWidth += childWidth;

            //暂不处理layout_height = match_parent
            if (lp.height != LayoutParams.MATCH_PARENT) {
                lineHeight = Math.max(lineHeight, childHeight);
            }

            //最后一行     没明白，这里应该是最后一个元素吧
            if (i == childCount - 1) {
                flowHeight += lineHeight;
                flowWidth = Math.max(flowWidth, lineWidth);
                heights.add(lineHeight);
                views.add(lineViews);
            }

            //重新测量一次  layout_height = match_parent
            remeasureChild(widthMeasureSpec, heightMeasureSpec);

            if (heightMode == MeasureSpec.EXACTLY) {
                flowHeight = Math.max(flowHeight, heightSize);
            }
            realHeight = flowHeight;
            scrollable = realHeight > measureHeight;

            //最终宽高
            setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : flowWidth, realHeight);
        }

    }

    private void remeasureChild(int widthMeasureSpec, int heightMeasureSpec) {
        //获取所有行数
        int lineSizes = views.size();
        for (int i = 0; i < lineSizes; i++) {
            //每一行的子View
            List<View> lineViews = views.get(i);
            //每一行行高
            Integer lineHeight = heights.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                //获取每个子View
                View child = lineViews.get(j);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.height == LayoutParams.MATCH_PARENT) {
                    int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
                    int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, lineHeight);
                    child.measure(childWidthSpec, childHeightSpec);
                }
            }
        }
    }

    private void init() {
        lineViews = new ArrayList<>();
        views = new ArrayList<>();
        heights = new ArrayList<>();
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //获取所有行数
        int lineSizes = views.size();
        int currX = 0;
        int currY = 0;
        //大循环，所有的子View 一行一行的布局
        for (int i = 0; i < lineSizes; i++) {
            //取出一行
            List<View> lineViews = this.views.get(i);
            // 取出这一行的高度值
            Integer lineHeight = heights.get(i);
            //布局当前行的每一个view
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                int left = currX;
                int top = currY;
                int right = left + child.getMeasuredWidth();
                int bottom = top + child.getMeasuredHeight();
                child.layout(left, top, right, bottom);
                //确定下一个view的left
                currX += child.getMeasuredWidth();
            }
            currY += lineHeight;
            currX = 0;
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new FlowLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new FlowLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new FlowLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return super.checkLayoutParams(p) && p instanceof FlowLayoutParams;
    }

    public static class FlowLayoutParams extends MarginLayoutParams {
        private int gavity = -1;

        public FlowLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.FlowLayout_Layout);
            try {
                gavity = a.getInt(R.styleable.FlowLayout_Layout_android_layout_gravity, -1);
            } finally {
                a.recycle();
            }

        }

        public FlowLayoutParams(int width, int height) {
            super(width, height);
        }

        public FlowLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public FlowLayoutParams(LayoutParams source) {
            super(source);
        }
    }
}
