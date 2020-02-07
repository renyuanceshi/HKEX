package com.hkex.soma.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.activity.fragment.OptionsCalculator_Customised;
import com.hkex.soma.activity.fragment.OptionsCalculator_Predefined;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.basic.TabManager;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.childFragmentWithCallback;
import java.util.HashMap;

public class OptionsCalculatorSearch extends AnimatedFragmentActivity {
    public boolean backFromDetail = false;
    private Bundle bundle;
    private childFragmentWithCallback childFragment;
    public String currenttag;
    private TabHost mTabHost;
    private TabManager mTabManager;
    private MenuContainer menu;

    public interface OnCalOptionClickListener {
        void onOptionClicked(HashMap hashMap);
    }

    public void initTab() {
        this.mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        this.mTabHost.setup();
        this.mTabHost.getTabWidget().setDividerDrawable((Drawable) null);
        this.mTabManager = new TabManager(this, this.mTabHost, R.id.realtabcontent);
        View view = setupTabLayout(Integer.valueOf(R.drawable.tab2), Integer.valueOf(R.string.tab_predefined));
        View view2 = setupTabLayout(Integer.valueOf(R.drawable.tab2_last), Integer.valueOf(R.string.tab_customised));
        this.mTabManager.addTab(this.mTabHost.newTabSpec("Predefined").setIndicator(view), OptionsCalculator_Predefined.class, (Bundle) null);
        this.mTabManager.addTab(this.mTabHost.newTabSpec("Customised").setIndicator(view2), OptionsCalculator_Customised.class, (Bundle) null);
        final TextView textView = (TextView) view.findViewById(R.id.textView);
        final TextView textView2 = (TextView) view2.findViewById(R.id.textView);
        this.mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String str) {
                Commons.LogDebug("PortfolioAdd", "OnClickListener");
                if (str.equals("Predefined")) {
                    textView.setTextColor(OptionsCalculatorSearch.this.getResources().getColor(R.color.textwhite));
                    textView2.setTextColor(OptionsCalculatorSearch.this.getResources().getColor(R.color.textnonselect));
                } else {
                    textView.setTextColor(OptionsCalculatorSearch.this.getResources().getColor(R.color.textnonselect));
                    textView2.setTextColor(OptionsCalculatorSearch.this.getResources().getColor(R.color.textwhite));
                }
                OptionsCalculatorSearch.this.mTabManager.onTabChanged(str);
            }
        });
        divideTabWidth(this.mTabHost);
        this.bundle = null;
        this.mTabHost.setCurrentTab(0);
        textView.setTextColor(getResources().getColor(R.color.textwhite));
    }

    public void initUI() {
        setContentView(R.layout.options_calculator_search);
        this.menu = new MenuContainer(this);
        this.mainContainer = findViewById(R.id.mainContainer);
        this.clickControlContainer = (ClickControlContainer) findViewById(R.id.clickControlContainer);
        ClickControlContainer clickControlContainer = (ClickControlContainer) findViewById(R.id.menuContainer);
        clickControlContainer.addView(this.menu, new LinearLayout.LayoutParams(-1, -1));
        this.mainView = findViewById(R.id.appContainer);
        this.leftView = clickControlContainer;
        final SlideLeftAnimationHandler slideLeftAnimationHandler = new SlideLeftAnimationHandler(this);
        ((ImageButton) findViewById(R.id.btnLeft)).setOnClickListener(slideLeftAnimationHandler);
        this.menu.setSliedBackHandler(slideLeftAnimationHandler);
        clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        this.clickControlContainer.setOnLeftToRightSwipeListener(new ClickControlContainer.OnLeftToRightSwipeListener() {
            public void onLeftToRightSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        initFooter();
        updateFooterStime("");
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && intent != null) {
            Bundle extras = intent.getExtras();
            String string = extras.getString("searchResult");
            this.childFragment.selectionListCallBack(SelectionList.getPopTypes(extras.getInt("typecode")), string);
        }
    }

    public void onCreate(Bundle bundle2) {
        super.onCreate(bundle2);
        this.currenttag = getIntent().getStringExtra("index");
        this.bundle = getIntent().getExtras();
        initUI();
        initTab();
    }

    protected void onResume() {
        super.onResume();
        if (this.backFromDetail) {
            dataLoaded();
        }
        if (Commons.noResumeAction || this.backFromDetail) {
            this.isMoving = false;
            Commons.noResumeAction = false;
            this.backFromDetail = false;
            return;
        }
        this.leftViewOut = false;
        this.rightViewOut = false;
        initUI();
        initTab();
    }

    public void set_backFromDetail(boolean z) {
        this.backFromDetail = z;
    }

    public Bundle setchildFragment(childFragmentWithCallback childfragmentwithcallback) {
        this.childFragment = childfragmentwithcallback;
        return this.bundle;
    }
}
