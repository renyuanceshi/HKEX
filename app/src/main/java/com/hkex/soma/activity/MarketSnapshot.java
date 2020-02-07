package com.hkex.soma.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.activity.fragment.MS_Futures;
import com.hkex.soma.activity.fragment.MS_Indices;
import com.hkex.soma.activity.fragment.MS_Overview;
import com.hkex.soma.activity.fragment.MS_StockOptions;
import com.hkex.soma.activity.fragment.MS_Stocks;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.basic.CustomWebViewClient;
import com.hkex.soma.basic.TabManager;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.element.PortfolioHalf;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.slideAnimator.SlideRightAnimationHandler;
import com.hkex.soma.utils.Commons;

public class MarketSnapshot extends AnimatedFragmentActivity {
    public int currentTab = 0;
    public int indexOptionTab = 0;
    public int index_stockOptionTab = 0;
    private TabHost mTabHost;
    private TabManager mTabManager;
    private MenuContainer menu;
    private PortfolioHalf portfolio = null;
    private SlideRightAnimationHandler slideRightAnimationHandler;
    public int stockOptionTab = 0;
    public int stockTab = 0;
    public int stocknindex_indexcodeTab = 0;
    public int stocknindex_indextop10Tab = 0;
    public int stocknindex_stocktop10Tab = 0;
    public int stocknindex_typeTab = 0;
    private WebView tickerWebView;

    private void initMenu() {
        this.leftViewOut = false;
        this.rightViewOut = false;
        this.menu = new MenuContainer(this);
        this.portfolio = new PortfolioHalf(this);
        this.mainContainer = findViewById(R.id.mainContainer);
        ClickControlContainer clickControlContainer = (ClickControlContainer) findViewById(R.id.menuContainer);
        ClickControlContainer clickControlContainer2 = (ClickControlContainer) findViewById(R.id.portfolioContainer);
        this.clickControlContainer = (ClickControlContainer) findViewById(R.id.clickControlContainer);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        clickControlContainer.addView(this.menu, layoutParams);
        clickControlContainer2.addView(this.portfolio, layoutParams);
        this.leftView = clickControlContainer;
        this.rightView = clickControlContainer2;
        this.mainView = findViewById(R.id.appContainer);
        this.slideRightAnimationHandler = new SlideRightAnimationHandler(this);
        final SlideLeftAnimationHandler slideLeftAnimationHandler = new SlideLeftAnimationHandler(this);
        this.mainView.findViewById(R.id.btnRight).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MarketSnapshot.this.leftViewOut || MarketSnapshot.this.rightViewOut) {
                    MarketSnapshot.this.slideRightAnimationHandler.onClick((View) null);
                    return;
                }
                MarketSnapshot.this.portfolio.loadJSON();
                ((LinearLayout) MarketSnapshot.this.rightView).removeAllViews();
                ((LinearLayout) MarketSnapshot.this.rightView).addView(MarketSnapshot.this.portfolio, new LinearLayout.LayoutParams(-1, -1));
            }
        });
        this.mainView.findViewById(R.id.btnLeft).setOnClickListener(slideLeftAnimationHandler);
        this.clickControlContainer.setOnLeftToRightSwipeListener(new ClickControlContainer.OnLeftToRightSwipeListener() {
            public void onLeftToRightSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        this.clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                if (MarketSnapshot.this.leftViewOut || MarketSnapshot.this.rightViewOut) {
                    MarketSnapshot.this.slideRightAnimationHandler.onClick((View) null);
                } else {
                    MarketSnapshot.this.portfolio.loadJSON();
                }
            }
        });
        clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        clickControlContainer2.setOnLeftToRightSwipeListener(new ClickControlContainer.OnLeftToRightSwipeListener() {
            public void onLeftToRightSwiped() {
                MarketSnapshot.this.slideRightAnimationHandler.onClick((View) null);
            }
        });
        this.menu.setSliedBackHandler(slideLeftAnimationHandler);
        dataLoaded();
    }

    public void halfPortfolioUpdated() {
        this.slideRightAnimationHandler.onClick((View) null);
    }

    public void initTab() {
        this.mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        this.mTabHost.setup();
        this.mTabHost.getTabWidget().setDividerDrawable((Drawable) null);
        this.mTabHost.setCurrentTab(this.currentTab);
        View view = setupTabLayout(Integer.valueOf(R.drawable.tab3), Integer.valueOf(R.string.tab_overview));
        View view2 = setupTabLayout(Integer.valueOf(R.drawable.tab3), Integer.valueOf(R.string.tab_options));
        View view3 = setupTabLayout(Integer.valueOf(R.drawable.tab3), Integer.valueOf(R.string.future));
        View view4 = setupTabLayout(Integer.valueOf(R.drawable.tab3), Integer.valueOf(R.string.tab_indices));
        View view5 = setupTabLayout(Integer.valueOf(R.drawable.tab3_last), Integer.valueOf(R.string.tab_stocks));
        this.mTabManager = new TabManager(this, this.mTabHost, R.id.realtabcontent);
        if (this.currentTab == 0) {
            this.mTabManager.setcanshowpage(true);
        } else {
            this.mTabManager.setcanshowpage(false);
        }
        this.mTabManager.addTab(this.mTabHost.newTabSpec("Overview").setIndicator(view), MS_Overview.class, (Bundle) null);
        this.mTabManager.addTab(this.mTabHost.newTabSpec("StockOptions").setIndicator(view2), MS_StockOptions.class, (Bundle) null);
        this.mTabManager.addTab(this.mTabHost.newTabSpec("IndexOptions").setIndicator(view3), MS_Futures.class, (Bundle) null);
        this.mTabManager.addTab(this.mTabHost.newTabSpec("Indices").setIndicator(view4), MS_Indices.class, (Bundle) null);
        this.mTabManager.addTab(this.mTabHost.newTabSpec("Stocks").setIndicator(view5), MS_Stocks.class, (Bundle) null);
        final TextView textView = (TextView) view.findViewById(R.id.textView);
        final TextView textView2 = (TextView) view2.findViewById(R.id.textView);
        final TextView textView3 = (TextView) view3.findViewById(R.id.textView);
        final TextView textView4 = (TextView) view4.findViewById(R.id.textView);
        final TextView textView5 = (TextView) view5.findViewById(R.id.textView);
        this.mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String str) {
                Commons.LogDebug("MainFragmentTabs", "OnClickListener");
                if (str.equals("Overview")) {
                    textView.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textwhite));
                    textView2.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView3.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView4.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView5.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    MarketSnapshot.this.currentTab = 0;
                } else if (str.equals("StockOptions")) {
                    textView.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView2.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textwhite));
                    textView3.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView4.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView5.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    MarketSnapshot.this.currentTab = 1;
                } else if (str.equals("IndexOptions")) {
                    textView.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView2.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView3.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textwhite));
                    textView4.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView5.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    MarketSnapshot.this.currentTab = 2;
                } else if (str.equals("Indices")) {
                    textView.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView2.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView3.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView4.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textwhite));
                    textView5.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    MarketSnapshot.this.currentTab = 3;
                } else {
                    textView.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView2.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView3.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView4.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textnonselect));
                    textView5.setTextColor(MarketSnapshot.this.getResources().getColor(R.color.textwhite));
                    MarketSnapshot.this.currentTab = 4;
                }
                MarketSnapshot.this.mTabManager.onTabChanged(str);
            }
        });
        divideTabWidth(this.mTabHost);
        if (this.currentTab == 0) {
            textView.setTextColor(getResources().getColor(R.color.textwhite));
        } else if (this.currentTab == 1) {
            textView2.setTextColor(getResources().getColor(R.color.textwhite));
        } else if (this.currentTab == 2) {
            textView3.setTextColor(getResources().getColor(R.color.textwhite));
        } else if (this.currentTab == 3) {
            textView4.setTextColor(getResources().getColor(R.color.textwhite));
        } else if (this.currentTab == 4) {
            textView5.setTextColor(getResources().getColor(R.color.textwhite));
        }
        this.mTabManager.setcanshowpage(true);
        this.mTabHost.setCurrentTab(this.currentTab);
    }

    public void initUI() {
        this.leftViewOut = false;
        this.rightViewOut = false;
        this.menu = new MenuContainer(this);
        setContentView(R.layout.marketsnapshot);
        this.tickerWebView = (WebView) findViewById(R.id.tickerWebView);
        this.tickerWebView.setWebViewClient(new CustomWebViewClient(this, this.tickerWebView, new CustomWebViewClient.WebViewCallBackHandler() {
            public Boolean webViewCallBack(String str) {
                Commons.LogDebug("tickerWebView", "Open URL: " + str);
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MarketSnapshot.this.startActivity(intent);
                return true;
            }
        }) {
        });
        initFooter();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initUI();
        initMenu();
        initTab();
    }

    protected void onResume() {
        super.onResume();
        if (Commons.need2reflash) {
            initUI();
            initTab();
            Commons.need2reflash = false;
        }
        if (!Commons.noResumeAction) {
            initMenu();
            return;
        }
        this.isMoving = false;
        Commons.noResumeAction = false;
    }

    public void setTickerVisibility(int i) {
        this.tickerWebView.setVisibility(i);
        if (i == 0) {
            this.tickerWebView.loadUrl(getString(R.string.url_ticker));
        } else {
            this.tickerWebView.loadUrl((String) null);
        }
    }
}
