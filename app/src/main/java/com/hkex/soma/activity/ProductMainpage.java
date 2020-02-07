package com.hkex.soma.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.fragment.MarketReportsFragment;
import com.hkex.soma.activity.fragment.SeminarFragment;
import com.hkex.soma.activity.fragment.StaticProductFragment;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.basic.TabManager;
import com.hkex.soma.dataModel.ProductLink_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.element.PortfolioHalf;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.utils.Commons;

public class ProductMainpage extends AnimatedFragmentActivity {
    private FrameLayout container;
    public int currentTab = 0;
    public Fragment fragment;
    private ImageButton leftbtn;
    private TabHost mTabHost;
    private TabManager mTabManager;
    private Button market_report;
    private MenuContainer menu;
    private PortfolioHalf portfolio = null;
    private Button prod_update;
    private Button seminar;
    private SlideLeftAnimationHandler slideLeftAnimationHandler;
    private Button static_product;
    private WebView webview;

    private void initMenu() {
        this.leftViewOut = false;
        this.rightViewOut = false;
        this.leftbtn = (ImageButton) findViewById(R.id.btnLeft);
        this.menu = new MenuContainer(this);
        this.mainContainer = findViewById(R.id.mainContainer);
        ClickControlContainer clickControlContainer = (ClickControlContainer) findViewById(R.id.menuContainer);
        this.clickControlContainer = (ClickControlContainer) findViewById(R.id.clickControlContainer);
        clickControlContainer.addView(this.menu, new LinearLayout.LayoutParams(-1, -1));
        initFooter();
        updateFooterStime("");
        this.leftView = clickControlContainer;
        this.mainView = findViewById(R.id.appContainer);
        this.slideLeftAnimationHandler = new SlideLeftAnimationHandler(this);
        this.leftbtn.setOnClickListener(this.slideLeftAnimationHandler);
        this.clickControlContainer.setOnLeftToRightSwipeListener(new ClickControlContainer.OnLeftToRightSwipeListener() {
            public void onLeftToRightSwiped() {
                ProductMainpage.this.slideLeftAnimationHandler.onClick((View) null);
            }
        });
        clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                ProductMainpage.this.slideLeftAnimationHandler.onClick((View) null);
            }
        });
        this.menu.setSliedBackHandler(this.slideLeftAnimationHandler);
        dataLoaded();
    }

    public void dataResult(final ProductLink_Result productLink_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    ProductMainpage.this.dataLoaded();
                    if (productLink_Result == null) {
                        ProductMainpage.this.webview.loadUrl(ProductMainpage.this.getString(R.string.url_prod_update));
                    } else if (productLink_Result.getType().equals("Inapp")) {
                        ProductMainpage.this.webview.loadUrl(productLink_Result.getLink().equals("") ? ProductMainpage.this.getString(R.string.url_prod_update) : productLink_Result.getLink());
                    } else {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(productLink_Result.getLink().equals("") ? ProductMainpage.this.getString(R.string.url_prod_update) : productLink_Result.getLink()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ProductMainpage.this.startActivity(intent);
                    }
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                    ProductMainpage.this.dataLoaded();
                }
            }
        });
    }

    public void initUI() {
        setContentView(R.layout.product_mainpage);
        this.prod_update = (Button) findViewById(R.id.prod_update);
        this.market_report = (Button) findViewById(R.id.market_report);
        this.seminar = (Button) findViewById(R.id.seminar);
        this.static_product = (Button) findViewById(R.id.static_product);
        this.market_report.setBackgroundResource(R.drawable.tab_but_on);
        this.webview = (WebView) findViewById(R.id.webview);
        this.webview.getSettings().setJavaScriptEnabled(true);
        this.webview.getSettings().setDisplayZoomControls(false);
        this.webview.getSettings().setBuiltInZoomControls(false);
        this.webview.getSettings().setLoadWithOverviewMode(true);
        this.webview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    ProductMainpage.this.clickControlContainer.setCanSwipe(false);
                } else if (motionEvent.getAction() == 1) {
                    ProductMainpage.this.clickControlContainer.setCanSwipe(true);
                } else if (motionEvent.getAction() == 2) {
                    ProductMainpage.this.clickControlContainer.setCanSwipe(false);
                }
                return false;
            }
        });
        this.container = (FrameLayout) findViewById(R.id.container);
        this.prod_update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProductMainpage.this.webview.setVisibility(View.VISIBLE);
                ProductMainpage.this.container.setVisibility(View.GONE);
                ProductMainpage.this.prod_update.setBackgroundResource(R.drawable.tab_but_on);
                ProductMainpage.this.market_report.setBackgroundResource(R.drawable.tab_but);
                ProductMainpage.this.seminar.setBackgroundResource(R.drawable.tab_but);
                ProductMainpage.this.static_product.setBackgroundResource(R.drawable.tab_last_but);
                ProductMainpage.this.loadJSON();
            }
        });
        this.market_report.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProductMainpage.this.webview.setVisibility(View.GONE);
                ProductMainpage.this.container.setVisibility(View.VISIBLE);
                ProductMainpage.this.prod_update.setBackgroundResource(R.drawable.tab_but);
                ProductMainpage.this.market_report.setBackgroundResource(R.drawable.tab_but_on);
                ProductMainpage.this.seminar.setBackgroundResource(R.drawable.tab_but);
                ProductMainpage.this.static_product.setBackgroundResource(R.drawable.tab_last_but);
                ProductMainpage.this.fragment = MarketReportsFragment.newInstance();
                FragmentTransaction beginTransaction = ProductMainpage.this.getSupportFragmentManager().beginTransaction();
                beginTransaction.add((int) R.id.container, ProductMainpage.this.fragment);
                beginTransaction.commit();
            }
        });
        this.seminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProductMainpage.this.webview.setVisibility(View.GONE);
                ProductMainpage.this.container.setVisibility(View.VISIBLE);
                ProductMainpage.this.prod_update.setBackgroundResource(R.drawable.tab_but);
                ProductMainpage.this.market_report.setBackgroundResource(R.drawable.tab_but);
                ProductMainpage.this.seminar.setBackgroundResource(R.drawable.tab_but_on);
                ProductMainpage.this.static_product.setBackgroundResource(R.drawable.tab_last_but);
                ProductMainpage.this.fragment = SeminarFragment.newInstance();
                FragmentTransaction beginTransaction = ProductMainpage.this.getSupportFragmentManager().beginTransaction();
                beginTransaction.add((int) R.id.container, ProductMainpage.this.fragment);
                beginTransaction.commit();
            }
        });
        this.static_product.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProductMainpage.this.webview.setVisibility(View.GONE);
                ProductMainpage.this.container.setVisibility(View.VISIBLE);
                ProductMainpage.this.prod_update.setBackgroundResource(R.drawable.tab_but);
                ProductMainpage.this.market_report.setBackgroundResource(R.drawable.tab_but);
                ProductMainpage.this.seminar.setBackgroundResource(R.drawable.tab_but);
                ProductMainpage.this.static_product.setBackgroundResource(R.drawable.tab_last_but_on);
                ProductMainpage.this.fragment = StaticProductFragment.newInstance();
                FragmentTransaction beginTransaction = ProductMainpage.this.getSupportFragmentManager().beginTransaction();
                beginTransaction.add((int) R.id.container, ProductMainpage.this.fragment);
                beginTransaction.commit();
            }
        });
        this.market_report.callOnClick();
    }

    public void loadJSON() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(ProductLink_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<ProductLink_Result>() {
            public void OnJSONCompleted(ProductLink_Result productLink_Result) {
                ProductMainpage.this.dataResult(productLink_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                ProductMainpage.this.dataResult((ProductLink_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_subscription_opt));
        dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.product_mainpage);
    }

    protected void onResume() {
        super.onResume();
        if (!Commons.noResumeAction) {
            initUI();
            initMenu();
            return;
        }
        this.isMoving = false;
        Commons.noResumeAction = false;
    }
}
