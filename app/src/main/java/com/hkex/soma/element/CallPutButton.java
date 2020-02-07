package com.hkex.soma.element;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.hkex.soma.R;
import com.hkex.soma.utils.Commons;

public class CallPutButton extends ImageView {
    public static final int CALL = 1;
    public static final int PUT = 0;
    private OnLayoutClickListener onLayoutClickListener = null;
    private int state = 1;

    public interface OnLayoutClickListener {
        void onLayoutClicked(int i);
    }

    public CallPutButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int unused = CallPutButton.this.state = Math.abs(CallPutButton.this.state - 1);
                CallPutButton.this.setState(CallPutButton.this.state);
                if (CallPutButton.this.onLayoutClickListener != null) {
                    CallPutButton.this.onLayoutClickListener.onLayoutClicked(CallPutButton.this.getState());
                }
            }
        });
        setState(1);
    }

    public int getState() {
        return this.state;
    }

    public String getType() {
        return this.state == 1 ? "Call" : "Put";
    }

    public void setOnLayoutClickListener(OnLayoutClickListener onLayoutClickListener2) {
        this.onLayoutClickListener = onLayoutClickListener2;
    }

    public void setState(int i) {
        this.state = i;
        if (Commons.language.equals("en_US")) {
            if (i == 1) {
                setImageResource(R.drawable.but_selected_call_e);
            } else {
                setImageResource(R.drawable.but_selected_put_e);
            }
        } else if (Commons.language.equals("zh_CN")) {
            if (i == 1) {
                setImageResource(R.drawable.but_selected_call_gb);
            } else {
                setImageResource(R.drawable.but_selected_put_gb);
            }
        } else if (i == 1) {
            setImageResource(R.drawable.but_selected_call_c);
        } else {
            setImageResource(R.drawable.but_selected_put_c);
        }
    }
}
