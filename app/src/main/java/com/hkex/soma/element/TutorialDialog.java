package com.hkex.soma.element;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hkex.soma.R;

public class TutorialDialog extends CustomDialog {
    private Context context;
    private FrameLayout mFrameLayout;

    public TutorialDialog(Context context2) {
        super(context2);
        this.context = context2;
        init();
    }

    private void initViewSwitcher(int[] iArr) {
        RealViewSwitcher realViewSwitcher = (RealViewSwitcher) findViewById(R.id.viewSwitcher);
        ((ImageView) findViewById(R.id.tut_close)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TutorialDialog.this.dismiss();
            }
        });
        for (int imageResource : iArr) {
            View inflate = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tutorialframe, (ViewGroup) null);
            ((ImageView) inflate.findViewById(R.id.webimg)).setImageResource(imageResource);
            realViewSwitcher.addView(inflate);
        }
        realViewSwitcher.setOnScreenClickListener(new RealViewSwitcher.OnScreenClickListener() {
            public void onScreenClicked(int i) {
            }
        });
        realViewSwitcher.setOnScreenSwitchListener(new RealViewSwitcher.OnScreenSwitchListener() {
            public void onScreenSwitched(int i) {
            }
        });
        realViewSwitcher.setVisibility(View.VISIBLE);
    }

    @SuppressLint({"NewApi"})
    public void init() {
        this.mFrameLayout = new FrameLayout(this.context);
        setContentView(((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tutorial, this.mFrameLayout, false));
    }

    public void setTutorialResID(int[] iArr) {
        initViewSwitcher(iArr);
    }
}
