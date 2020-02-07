package com.hkex.soma.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MultiScrollListView extends ListView {
    private float prevoiusY = 0.0f;
    private MultiScrollView scrollView;

    public MultiScrollListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setDividerHeight(0);
    }

    public void initMultiScrollListView(MultiScrollView multiScrollView, float f) {
        this.scrollView = multiScrollView;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float rawY = motionEvent.getRawY();
        if (this.scrollView == null || motionEvent.getAction() != 2) {
            this.prevoiusY = rawY;
            return super.onTouchEvent(motionEvent);
        }
        int i = (int) (this.prevoiusY - rawY);
        this.prevoiusY = rawY;
        if (i > 0) {
            this.scrollView.scrollTo(0, i + this.scrollView.getScrollY());
        } else if (i < 0 && getChildAt(0).getTop() == 0) {
            this.scrollView.scrollTo(0, i + this.scrollView.getScrollY());
        }
        return super.onTouchEvent(motionEvent);
    }
}
