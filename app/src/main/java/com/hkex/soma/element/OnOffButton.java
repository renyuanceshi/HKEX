package com.hkex.soma.element;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.hkex.soma.R;
import com.hkex.soma.utils.Commons;

public class OnOffButton extends ImageView {
    public static final int OFF = 0;
    public static final int ON = 1;
    /* access modifiers changed from: private */
    public OnLayoutClickListener onLayoutClickListener = null;
    /* access modifiers changed from: private */
    public int state = 1;

    public interface OnLayoutClickListener {
        void onLayoutClicked(int i);
    }

    public OnOffButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int unused = OnOffButton.this.state = Math.abs(OnOffButton.this.state - 1);
                OnOffButton.this.setState(OnOffButton.this.state);
                if (OnOffButton.this.onLayoutClickListener != null) {
                    OnOffButton.this.onLayoutClickListener.onLayoutClicked(OnOffButton.this.getState());
                }
            }
        });
        setState(1);
    }

    public int getState() {
        return this.state;
    }

    public String getType() {
        return this.state == 1 ? "On" : "Off";
    }

    public void setOnLayoutClickListener(OnLayoutClickListener onLayoutClickListener2) {
        this.onLayoutClickListener = onLayoutClickListener2;
    }

    public void setState(int i) {
        this.state = i;
        if (Commons.language.equals("en_US")) {
            if (i == 1) {
                setImageResource(R.drawable.but_on_e);
            } else {
                setImageResource(R.drawable.but_off_e);
            }
        } else if (Commons.language.equals("zh_CN")) {
            if (i == 1) {
                setImageResource(R.drawable.but_on_gb);
            } else {
                setImageResource(R.drawable.but_off_gb);
            }
        } else if (i == 1) {
            setImageResource(R.drawable.but_on_c);
        } else {
            setImageResource(R.drawable.but_off_c);
        }
    }
}
