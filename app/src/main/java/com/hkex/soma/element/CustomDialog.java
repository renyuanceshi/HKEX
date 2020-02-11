package com.hkex.soma.element;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

public class CustomDialog extends Dialog {
    protected Context context;
    protected float dimAmount = 0.5f;

    public CustomDialog(Context context2) {
        super(context2);
        this.context = context2;
        init();
    }

    public CustomDialog(Context context2, float f) {
        super(context2);
        this.context = context2;
        this.dimAmount = f;
        init();
    }

    private void init() {
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.dimAmount = this.dimAmount;
        attributes.width = -1;
        attributes.height = -1;
        getWindow().setAttributes(attributes);
        getWindow().setBackgroundDrawableResource(android.R.color.holo_red_dark);
    }
}
