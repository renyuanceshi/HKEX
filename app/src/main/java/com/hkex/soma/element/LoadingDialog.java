package com.hkex.soma.element;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class LoadingDialog extends CustomDialog {
    private static int WRAP_CONTENT = -2;
    private FrameLayout mFrameLayout;
    private ProgressBar mProgressBar;
    private OnLoadingBackPressListener onLoadingBackPressListener = null;

    public interface OnLoadingBackPressListener {
        void backPress();
    }

    public LoadingDialog(Context context) {
        super(context);
        init();
    }

    @SuppressLint({"NewApi"})
    public void init() {
        this.mFrameLayout = new FrameLayout(this.context);
        this.mProgressBar = new ProgressBar(this.context);
        this.mProgressBar.setIndeterminate(true);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layoutParams.gravity = 17;
        this.mFrameLayout.addView(this.mProgressBar, layoutParams);
        setContentView(this.mFrameLayout);
    }

    public void onBackPressed() {
        if (this.onLoadingBackPressListener != null) {
            this.onLoadingBackPressListener.backPress();
        }
    }

    public void setOnLoadingBackPressListener(OnLoadingBackPressListener onLoadingBackPressListener2) {
        this.onLoadingBackPressListener = onLoadingBackPressListener2;
    }
}
