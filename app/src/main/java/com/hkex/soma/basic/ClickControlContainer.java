package com.hkex.soma.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ClickControlContainer extends LinearLayout {
    static final int MIN_DISTANCE = 100;
    private boolean canSwipe = true;
    private boolean clickable = true;
    private float downX;
    private float downY;
    private OnLeftToRightSwipeListener onLeftToRightSwipeListener = null;
    private OnRightToLeftSwipeListener onRightToLeftSwipeListener = null;
    private float upX;
    private float upY;

    public interface OnLeftToRightSwipeListener {
        void onLeftToRightSwiped();
    }

    public interface OnRightToLeftSwipeListener {
        void onRightToLeftSwiped();
    }

    public ClickControlContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean handleSwipeEvent(MotionEvent motionEvent) {
        if (this.canSwipe) {
            switch (motionEvent.getAction()) {
                case 0:
                    this.downX = motionEvent.getX();
                    this.downY = motionEvent.getY();
                    break;
                case 1:
                    this.upX = motionEvent.getX();
                    this.upY = motionEvent.getY();
                    float f = this.downX - this.upX;
                    float f2 = this.downY;
                    float f3 = this.upY;
                    if (Math.abs(f) > 100.0f && Math.abs(f) > Math.abs(f2 - f3) * 2.0f) {
                        if (f < 0.0f) {
                            onLeftToRightSwipe();
                            return true;
                        } else if (f > 0.0f) {
                            onRightToLeftSwipe();
                            return true;
                        }
                    }
                    break;
                case 2:
                    this.upX = motionEvent.getX();
                    this.upY = motionEvent.getY();
                    break;
            }
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.clickable ? handleSwipeEvent(motionEvent) : !this.clickable;
    }

    public void onLeftToRightSwipe() {
        if (this.onLeftToRightSwipeListener != null) {
            this.onLeftToRightSwipeListener.onLeftToRightSwiped();
        }
    }

    public final void onRightToLeftSwipe() {
        if (this.onRightToLeftSwipeListener != null) {
            this.onRightToLeftSwipeListener.onRightToLeftSwiped();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.clickable) {
            return false;
        }
        handleSwipeEvent(motionEvent);
        return true;
    }

    public void setCanSwipe(boolean z) {
        this.canSwipe = z;
    }

    public void setClickable(boolean z) {
        this.clickable = z;
    }

    public void setOnLeftToRightSwipeListener(OnLeftToRightSwipeListener onLeftToRightSwipeListener2) {
        this.onLeftToRightSwipeListener = onLeftToRightSwipeListener2;
    }

    public void setOnRightToLeftSwipeListener(OnRightToLeftSwipeListener onRightToLeftSwipeListener2) {
        this.onRightToLeftSwipeListener = onRightToLeftSwipeListener2;
    }
}
