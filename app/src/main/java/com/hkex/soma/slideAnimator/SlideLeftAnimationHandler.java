package com.hkex.soma.slideAnimator;

import android.view.View;
import android.view.animation.TranslateAnimation;

import com.hkex.soma.utils.Commons;

public class SlideLeftAnimationHandler implements View.OnClickListener {
    private AnimatedFragmentActivity callerActivity;

    public SlideLeftAnimationHandler(AnimatedFragmentActivity animatedFragmentActivity) {
        this.callerActivity = animatedFragmentActivity;
    }

    public void onClick(View view) {
        TranslateAnimation translateAnimation;
        if (!this.callerActivity.isMoving) {
            this.callerActivity.direction = -1;
            int measuredWidth = this.callerActivity.mainView.getMeasuredWidth();
            int measuredHeight = this.callerActivity.mainView.getMeasuredHeight();
            if (measuredWidth == 0) {
                measuredWidth = this.callerActivity.getResources().getDisplayMetrics().widthPixels;
                measuredHeight = (int) (Commons.getSrceenHeightinDP(this.callerActivity) * this.callerActivity.getResources().getDisplayMetrics().density);
            }
            int i = (int) (((double) measuredWidth) * 0.8d);
            if (this.callerActivity.rightView != null) {
                this.callerActivity.rightView.setVisibility(View.INVISIBLE);
            }
            if (!this.callerActivity.leftViewOut) {
                this.callerActivity.leftView.setVisibility(View.VISIBLE);
                TranslateAnimation translateAnimation2 = new TranslateAnimation(0.0f, (float) i, 0.0f, 0.0f);
                this.callerActivity.animParams.init(i, 0, measuredWidth + i, measuredHeight);
                translateAnimation = translateAnimation2;
            } else {
                TranslateAnimation translateAnimation3 = new TranslateAnimation(0.0f, (float) (-i), 0.0f, 0.0f);
                this.callerActivity.animParams.init(0, 0, measuredWidth, measuredHeight);
                translateAnimation = translateAnimation3;
            }
            translateAnimation.setDuration(250);
            translateAnimation.setAnimationListener(this.callerActivity);
            translateAnimation.setFillAfter(true);
            this.callerActivity.mainView.startAnimation(translateAnimation);
        }
    }
}
