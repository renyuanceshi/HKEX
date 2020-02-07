package com.hkex.soma.slideAnimator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;

import com.hkex.soma.R;
import com.hkex.soma.activity.MarketSnapshot;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.basic.MasterFragmentActivity;
import com.hkex.soma.utils.Commons;
import java.io.PrintStream;

public class AnimatedFragmentActivity extends MasterFragmentActivity implements Animation.AnimationListener {
    protected AnimParams animParams = new AnimParams();
    protected boolean canBack = false;
    protected ClickControlContainer clickControlContainer;
    protected int direction = 0;
    public boolean isMoving = false;
    protected View leftView = null;
    public boolean leftViewOut = false;
    protected View mainView;
    protected View rightView = null;
    public boolean rightViewOut = false;

    static class AnimParams {
        int bottom;
        int left;
        int right;
        int top;

        AnimParams() {
        }

        /* access modifiers changed from: package-private */
        public void init(int i, int i2, int i3, int i4) {
            this.left = i;
            this.top = i2;
            this.right = i3;
            this.bottom = i4;
        }
    }

    public void dataLoaded() {
        super.dataLoaded();
        this.isMoving = false;
    }

    public void onAnimationEnd(Animation animation) {
        this.isMoving = false;
        this.clickControlContainer.setClickable(false);
        if (this.direction == -1) {
            onLeftAnimationEnd();
        } else if (this.direction == 1) {
            onRightAnimationEnd();
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
        this.isMoving = true;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || this.canBack) {
            return super.onKeyDown(i, keyEvent);
        }
        if (getClass() == MarketSnapshot.class) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage(getResources().getString(R.string.msg_close_program));
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    AnimatedFragmentActivity.this.finish();
                    AnimatedFragmentActivity.this.moveTaskToBack(true);
                }
            });
            builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        } else {
            dataLoading();
            Intent intent = new Intent();
            intent.setClass(this, MarketSnapshot.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            startActivity(intent);
            finish();
        }
        return true;
    }

    public void onLeftAnimationEnd() {
        Commons.printView("menu", this.leftView);
        Commons.printView("app", this.mainView);
        this.leftViewOut = !this.leftViewOut;
        if (!this.leftViewOut) {
            this.leftView.setVisibility(View.INVISIBLE);
            this.clickControlContainer.setClickable(true);
        }
        PrintStream printStream = System.out;
        printStream.println(this.leftView.getVisibility() + "layout [" + this.animParams.left + "," + this.animParams.top + "," + this.animParams.right + "," + this.animParams.bottom + "]");
        this.mainView.layout(this.animParams.left, this.animParams.top, this.animParams.right, this.animParams.bottom);
        this.mainView.clearAnimation();
    }

    protected void onPause() {
        super.onPause();
        this.isMoving = true;
    }

    public void onRightAnimationEnd() {
        Commons.printView("menu", this.rightView);
        Commons.printView("app", this.mainView);
        this.rightViewOut = !this.rightViewOut;
        if (!this.rightViewOut) {
            this.rightView.setVisibility(View.INVISIBLE);
            this.clickControlContainer.setClickable(true);
        }
        PrintStream printStream = System.out;
        printStream.println(this.rightView.getVisibility() + "layout [" + this.animParams.left + "," + this.animParams.top + "," + this.animParams.right + "," + this.animParams.bottom + "]");
        this.mainView.layout(this.animParams.left, this.animParams.top, this.animParams.right, this.animParams.bottom);
        this.mainView.clearAnimation();
    }

    public void setClickControlCanSwipe(boolean z) {
        this.clickControlContainer.setCanSwipe(z);
    }

    public void setClickControlEnabled(boolean z) {
        this.clickControlContainer.setEnabled(z);
    }
}
