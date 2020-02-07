package com.hkex.soma.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.hkex.soma.utils.Commons;

public class MultiScrollView extends ScrollView {
    private Context context;
    /* access modifiers changed from: private */
    public MultiScrollListView listView;

    public MultiScrollView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        this.context = context2;
    }

    public void initMultiScrollView(MultiScrollListView multiScrollListView, float f, float f2) {
        this.listView = multiScrollListView;
        this.listView.setVisibility(View.GONE);
        ViewGroup.LayoutParams layoutParams = multiScrollListView.getLayoutParams();
        if (((double) this.context.getResources().getDisplayMetrics().density) == 1.5d) {
            f += 1.0f;
        }
        layoutParams.height = (int) Math.floor((double) ((Commons.getSrceenHeightinDP(this.context) - f) * this.context.getResources().getDisplayMetrics().density));
        multiScrollListView.initMultiScrollListView(this, 0.0f);
        post(new Runnable() {
            public void run() {
                MultiScrollView.this.scrollTo(0, 0);
                MultiScrollView.this.listView.setVisibility(View.VISIBLE);
            }
        });
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }
}
