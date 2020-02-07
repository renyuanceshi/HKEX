package com.hkex.soma.slideAnimator;

import android.view.View;
import android.view.animation.TranslateAnimation;

public class SlideRightAnimationHandler implements View.OnClickListener {
    private AnimatedFragmentActivity callerActivity;

    public SlideRightAnimationHandler(AnimatedFragmentActivity animatedFragmentActivity) {
        this.callerActivity = animatedFragmentActivity;
    }

    public void onClick(View view) {
        TranslateAnimation translateAnimation;
        if (!this.callerActivity.isMoving) {
            this.callerActivity.direction = 1;
            int measuredWidth = this.callerActivity.mainView.getMeasuredWidth();
            int measuredHeight = this.callerActivity.mainView.getMeasuredHeight();
            int measuredWidth2 = (int) (((double) this.callerActivity.mainView.getMeasuredWidth()) * 0.8d);
            if (this.callerActivity.leftView != null) {
                this.callerActivity.leftView.setVisibility(View.INVISIBLE);
            }
            if (!this.callerActivity.rightViewOut) {
                this.callerActivity.rightView.setVisibility(View.VISIBLE);
                int i = -measuredWidth2;
                translateAnimation = new TranslateAnimation(0.0f, (float) i, 0.0f, 0.0f);
                this.callerActivity.animParams.init(i, 0, measuredWidth + i, measuredHeight);
            } else {
                translateAnimation = new TranslateAnimation(0.0f, (float) measuredWidth2, 0.0f, 0.0f);
                this.callerActivity.animParams.init(0, 0, measuredWidth, measuredHeight);
            }
            translateAnimation.setDuration(250);
            translateAnimation.setAnimationListener(this.callerActivity);
            translateAnimation.setFillAfter(true);
            this.callerActivity.mainView.startAnimation(translateAnimation);
        }
    }
}
