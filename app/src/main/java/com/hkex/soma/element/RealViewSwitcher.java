package com.hkex.soma.element;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class RealViewSwitcher extends ViewGroup {
    private static final int INVALID_SCREEN = -1;
    private static final int SNAP_VELOCITY = 1000;
    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_SCROLLING = 1;
    private int mCurrentScreen;
    private boolean mFirstLayout = true;
    private float mLastMotionX;
    private int mMaximumVelocity;
    private int mNextScreen = -1;
    private OnScreenClickListener mOnScreenClickListener;
    private OnScreenSwitchListener mOnScreenSwitchListener;
    private Scroller mScroller;
    private int mTouchSlop;
    private int mTouchState = 0;
    private VelocityTracker mVelocityTracker;

    public interface OnScreenClickListener {
        void onScreenClicked(int i);
    }

    public interface OnScreenSwitchListener {
        void onScreenSwitched(int i);
    }

    public RealViewSwitcher(Context context) {
        super(context);
        init();
    }

    public RealViewSwitcher(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.mScroller = new Scroller(getContext());
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    private void snapToDestination() {
        int width = getWidth();
        snapToScreen((getScrollX() + (width / 2)) / width);
    }

    private void snapToScreen(int i) {
        if (this.mScroller.isFinished()) {
            int max = Math.max(0, Math.min(i, getChildCount() - 1));
            this.mNextScreen = max;
            int width = (max * getWidth()) - getScrollX();
            this.mScroller.startScroll(getScrollX(), 0, width, 0, Math.abs(width) * 2);
            invalidate();
        }
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            postInvalidate();
        } else if (this.mNextScreen != -1) {
            this.mCurrentScreen = Math.max(0, Math.min(this.mNextScreen, getChildCount() - 1));
            if (this.mOnScreenSwitchListener != null) {
                this.mOnScreenSwitchListener.onScreenSwitched(this.mCurrentScreen);
            }
            this.mNextScreen = -1;
        }
    }

    public int getCurrentScreen() {
        return this.mCurrentScreen;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int childCount = getChildCount();
        int i6 = 0;
        int i7 = 0;
        while (i7 < childCount) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != View.GONE) {
                i5 = childAt.getMeasuredWidth() + i6;
                childAt.layout(i6, 0, i5, childAt.getMeasuredHeight());
            } else {
                i5 = i6;
            }
            i7++;
            i6 = i5;
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            getChildAt(i3).measure(i, i2);
        }
        if (this.mFirstLayout) {
            scrollTo(this.mCurrentScreen * size, 0);
            this.mFirstLayout = false;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int right;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        switch (action) {
            case 0:
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                this.mLastMotionX = x;
                this.mTouchState = this.mScroller.isFinished() ^ true ? 1 : 0;
                break;
            case 1:
                if (this.mTouchState == 1) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                    int xVelocity = (int) velocityTracker.getXVelocity();
                    if (xVelocity > 1000 && this.mCurrentScreen > 0) {
                        snapToScreen(this.mCurrentScreen - 1);
                    } else if (xVelocity >= -1000 || this.mCurrentScreen >= getChildCount() - 1) {
                        snapToDestination();
                    } else {
                        snapToScreen(this.mCurrentScreen + 1);
                    }
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }
                } else if (this.mOnScreenClickListener != null) {
                    this.mOnScreenClickListener.onScreenClicked(this.mCurrentScreen);
                }
                this.mTouchState = 0;
                break;
            case 2:
                if (((int) Math.abs(x - this.mLastMotionX)) > this.mTouchSlop) {
                    this.mTouchState = 1;
                }
                if (this.mTouchState == 1) {
                    int i = (int) (this.mLastMotionX - x);
                    this.mLastMotionX = x;
                    int scrollX = getScrollX();
                    if (i >= 0) {
                        if (i > 0 && (right = (getChildAt(getChildCount() - 1).getRight() - scrollX) - getWidth()) > 0) {
                            scrollBy(Math.min(right, i), 0);
                            break;
                        }
                    } else if (scrollX > 0) {
                        scrollBy(Math.max(-scrollX, i), 0);
                        break;
                    }
                }
                break;
            case 3:
                this.mTouchState = 0;
                break;
        }
        return true;
    }

    public void setCurrentScreen(int i) {
        this.mCurrentScreen = Math.max(0, Math.min(i, getChildCount() - 1));
        scrollTo(this.mCurrentScreen * getWidth(), 0);
        invalidate();
    }

    public void setOnScreenClickListener(OnScreenClickListener onScreenClickListener) {
        this.mOnScreenClickListener = onScreenClickListener;
    }

    public void setOnScreenSwitchListener(OnScreenSwitchListener onScreenSwitchListener) {
        this.mOnScreenSwitchListener = onScreenSwitchListener;
    }
}
