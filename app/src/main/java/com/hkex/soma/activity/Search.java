package com.hkex.soma.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.activity.fragment.Search_Index;
import com.hkex.soma.activity.fragment.Search_Options;
import com.hkex.soma.activity.fragment.Search_Stocks;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.basic.TabManager;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.element.TutorialDialog;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;

public class Search extends AnimatedFragmentActivity {
    public boolean backFromOptionDetail = false;
    public String currenttag;
    private Search_Index indexFragment;
    private TabHost mTabHost;
    /* access modifiers changed from: private */
    public TabManager mTabManager;
    private MenuContainer menu;
    private Search_Options optionsFragment;
    private String scode = "";
    private String sname = "";
    private Search_Stocks stocksFragment;
    private String ucode = "";
    private String uname = "";

    public String getScode() {
        return this.scode;
    }

    public String getSname() {
        return this.sname;
    }

    public String getUcode() {
        return this.ucode;
    }

    public String getUname() {
        return this.uname;
    }

    public void initTab() {
        this.mTabHost = (TabHost) findViewById(16908306);
        this.mTabHost.setup();
        this.mTabHost.getTabWidget().setDividerDrawable((Drawable) null);
        this.mTabManager = new TabManager(this, this.mTabHost, R.id.realtabcontent);
        View view = setupTabLayout(Integer.valueOf(R.drawable.tab2), Integer.valueOf(R.string.stockoptions));
        View view2 = setupTabLayout(Integer.valueOf(R.drawable.tab2), Integer.valueOf(R.string.indexoptions));
        View view3 = setupTabLayout(Integer.valueOf(R.drawable.tab2_last), Integer.valueOf(R.string.tab_stocks));
        this.mTabManager.addTab(this.mTabHost.newTabSpec("Options").setIndicator(view), Search_Options.class, (Bundle) null);
        this.mTabManager.addTab(this.mTabHost.newTabSpec("Index").setIndicator(view2), Search_Index.class, (Bundle) null);
        this.mTabManager.addTab(this.mTabHost.newTabSpec("Stock").setIndicator(view3), Search_Stocks.class, (Bundle) null);
        final TextView textView = (TextView) view.findViewById(R.id.textView);
        final TextView textView2 = (TextView) view2.findViewById(R.id.textView);
        final TextView textView3 = (TextView) view3.findViewById(R.id.textView);
        this.mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String str) {
                Commons.LogDebug("Search", "OnClickListener");
                if (str.equals("Options")) {
                    textView.setTextColor(Search.this.getResources().getColor(R.color.textwhite));
                    textView2.setTextColor(Search.this.getResources().getColor(R.color.textnonselect));
                    textView3.setTextColor(Search.this.getResources().getColor(R.color.textnonselect));
                    Search.this.currenttag = "0";
                } else if (str.equals("Index")) {
                    textView.setTextColor(Search.this.getResources().getColor(R.color.textnonselect));
                    textView2.setTextColor(Search.this.getResources().getColor(R.color.textwhite));
                    textView3.setTextColor(Search.this.getResources().getColor(R.color.textnonselect));
                    Search.this.currenttag = "1";
                } else {
                    textView.setTextColor(Search.this.getResources().getColor(R.color.textnonselect));
                    textView2.setTextColor(Search.this.getResources().getColor(R.color.textnonselect));
                    textView3.setTextColor(Search.this.getResources().getColor(R.color.textwhite));
                    Search.this.currenttag = "2";
                }
                Search.this.mTabManager.onTabChanged(str);
            }
        });
        divideTabWidth(this.mTabHost);
        if (this.currenttag.equals("0")) {
            textView.setTextColor(getResources().getColor(R.color.textwhite));
        } else if (this.currenttag.equals("1")) {
            textView2.setTextColor(getResources().getColor(R.color.textwhite));
        } else {
            textView3.setTextColor(getResources().getColor(R.color.textwhite));
        }
        this.mTabHost.setCurrentTab(Integer.parseInt(this.currenttag));
    }

    public void initUI() {
        setContentView(R.layout.search);
        initFooter();
        ImageButton imageButton = (ImageButton) findViewById(R.id.btnLeft);
        this.currenttag = this.currenttag == null ? "0" : this.currenttag;
        if (this.currenttag.equals("0")) {
            this.ucode = getIntent().getStringExtra("ucode");
            this.uname = Commons.MapUnderlyingName(this.ucode);
            this.scode = Commons.defaultStockCode;
            this.sname = Commons.defaultStockName;
        } else {
            this.scode = getIntent().getStringExtra("ucode");
            this.sname = Commons.MapUnderlyingName(this.scode);
            this.ucode = Commons.defaultUnderlyingCode;
        }
        if (getIntent().getStringExtra("ucode") == null) {
            this.ucode = Commons.defaultUnderlyingCode;
            this.scode = Commons.defaultStockCode;
            this.sname = Commons.defaultStockName;
            this.mainContainer = findViewById(R.id.mainContainer);
            this.menu = new MenuContainer(this);
            ClickControlContainer clickControlContainer = (ClickControlContainer) findViewById(R.id.menuContainer);
            this.clickControlContainer = (ClickControlContainer) findViewById(R.id.clickControlContainer);
            clickControlContainer.addView(this.menu, new LinearLayout.LayoutParams(-1, -1));
            this.leftView = clickControlContainer;
            this.mainView = findViewById(R.id.appContainer);
            final SlideLeftAnimationHandler slideLeftAnimationHandler = new SlideLeftAnimationHandler(this);
            imageButton.setOnClickListener(slideLeftAnimationHandler);
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
            return;
        }
        this.canBack = true;
        imageButton.setImageResource(R.drawable.btn_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"NewApi"})
            public void onClick(View view) {
                Search.this.finish();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && intent != null) {
            Bundle extras = intent.getExtras();
            String string = extras.getString("searchResult");
            SelectionList.PopTypes popTypes = SelectionList.getPopTypes(extras.getInt("typecode"));
            if (this.stocksFragment != null) {
                this.stocksFragment.selectionListCallBack(popTypes, string);
            } else if (this.optionsFragment != null) {
                this.optionsFragment.selectionListCallBack(popTypes, string);
            } else if (this.indexFragment != null) {
                this.indexFragment.selectionListCallBack(popTypes, string);
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.currenttag = getIntent().getStringExtra("index");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.backFromOptionDetail) {
            dataLoaded();
        }
        if (Commons.noResumeAction || this.backFromOptionDetail) {
            this.isMoving = false;
            Commons.noResumeAction = false;
            this.backFromOptionDetail = false;
        } else {
            this.leftViewOut = false;
            this.rightViewOut = false;
            initUI();
            initTab();
        }
        if (Commons.tutor_search == 1) {
            TutorialDialog tutorialDialog = new TutorialDialog(this);
            if (Commons.language == "en_US") {
                tutorialDialog.setTutorialResID(new int[]{R.drawable.tut_search1_e, R.drawable.tut_search2_e, R.drawable.tut_search3_e});
            } else if (Commons.language == "zh_CN") {
                tutorialDialog.setTutorialResID(new int[]{R.drawable.tut_search1_gb, R.drawable.tut_search2_gb, R.drawable.tut_search3_gb});
            } else {
                tutorialDialog.setTutorialResID(new int[]{R.drawable.tut_search1_c, R.drawable.tut_search2_c, R.drawable.tut_search3_c});
            }
            tutorialDialog.show();
            Commons.tutor_search = 0;
            SharedPrefsUtil.putValue((Context) this, "tutor_search", Commons.tutor_search + "");
        }
    }

    public void setIndexFragment(Search_Index search_Index) {
        this.optionsFragment = null;
        this.indexFragment = search_Index;
        this.stocksFragment = null;
    }

    public void setOptionsFragment(Search_Options search_Options) {
        this.optionsFragment = search_Options;
        this.indexFragment = null;
        this.stocksFragment = null;
    }

    public void setStocksFragment(Search_Stocks search_Stocks) {
        this.stocksFragment = search_Stocks;
        this.indexFragment = null;
        this.optionsFragment = null;
    }
}
