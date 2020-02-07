package com.hkex.soma.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.activity.fragment.Portfolio_Index;
import com.hkex.soma.activity.fragment.Portfolio_Options;
import com.hkex.soma.activity.fragment.Portfolio_Stocks;
import com.hkex.soma.basic.MasterFragmentActivity;
import com.hkex.soma.basic.TabManager;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;
import java.util.Locale;

public class PortfolioAdd extends MasterFragmentActivity {
    private Bundle bundle;
    private boolean editMode = false;
    private Portfolio_Index indexFragment;
    private TabHost mTabHost;
    public TabManager mTabManager;
    private Portfolio_Options optionsFragment;
    private Portfolio_Stocks stocksFragment;

    public void initTab() {
        this.mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        this.mTabHost.setup();
        this.mTabHost.getTabWidget().setDividerDrawable((Drawable) null);
        this.mTabManager = new TabManager(this, this.mTabHost, R.id.realtabcontent);
        View view = setupTabLayout(Integer.valueOf(R.drawable.tab2), Integer.valueOf(R.string.tab_stockoptions));
        View view2 = setupTabLayout(Integer.valueOf(R.drawable.tab2_last), Integer.valueOf(R.string.tab_stocks));
        View view3 = setupTabLayout(Integer.valueOf(R.drawable.tab2), Integer.valueOf(R.string.tab_indexoptions));
        this.mTabManager.addTab(this.mTabHost.newTabSpec("Options").setIndicator(view), Portfolio_Options.class, (Bundle) null);
        this.mTabManager.addTab(this.mTabHost.newTabSpec("Index").setIndicator(view3), Portfolio_Index.class, (Bundle) null);
        this.mTabManager.addTab(this.mTabHost.newTabSpec("Stock").setIndicator(view2), Portfolio_Stocks.class, (Bundle) null);
        final TextView textView = (TextView) view.findViewById(R.id.textView);
        final TextView textView2 = (TextView) view2.findViewById(R.id.textView);
        final TextView textView3 = (TextView) view3.findViewById(R.id.textView);
        this.mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String str) {
                Commons.LogDebug("PortfolioAdd", "OnClickListener");
                if (str.equals("Options")) {
                    textView.setTextColor(PortfolioAdd.this.getResources().getColor(R.color.textwhite));
                    textView2.setTextColor(PortfolioAdd.this.getResources().getColor(R.color.textnonselect));
                    textView3.setTextColor(PortfolioAdd.this.getResources().getColor(R.color.textnonselect));
                } else if (str.equals("Index")) {
                    textView.setTextColor(PortfolioAdd.this.getResources().getColor(R.color.textnonselect));
                    textView2.setTextColor(PortfolioAdd.this.getResources().getColor(R.color.textnonselect));
                    textView3.setTextColor(PortfolioAdd.this.getResources().getColor(R.color.textwhite));
                } else {
                    textView.setTextColor(PortfolioAdd.this.getResources().getColor(R.color.textnonselect));
                    textView2.setTextColor(PortfolioAdd.this.getResources().getColor(R.color.textwhite));
                    textView3.setTextColor(PortfolioAdd.this.getResources().getColor(R.color.textnonselect));
                }
                PortfolioAdd.this.mTabManager.onTabChanged(str);
            }
        });
        divideTabWidth(this.mTabHost);
        if (this.editMode) {
            Log.v("PortfolioAdd", "no bug");
            String string = this.bundle.getString("oid");
            Log.v("add", "oid " + string + this.bundle.getBoolean("isOption", false));
            ((TextView) findViewById(R.id.portfolioTitle)).setText(R.string.title_portfolioedit);
            this.mTabHost.setCurrentTab(Integer.parseInt(this.bundle.getString("index")));
            if (!this.bundle.getString("typename", "stock").equals("stock")) {
                Log.v("t3", "t3");
                this.mTabHost.getTabWidget().getChildTabViewAt(0).setEnabled(false);
                this.mTabHost.getTabWidget().getChildTabViewAt(2).setEnabled(false);
                textView.setTextColor(getResources().getColor(R.color.textdisable));
                textView2.setTextColor(getResources().getColor(R.color.textdisable));
                textView3.setTextColor(getResources().getColor(R.color.textwhite));
            } else if (this.bundle.getBoolean("isOption", false)) {
                Log.v("t3", "t0");
                this.mTabHost.getTabWidget().getChildTabViewAt(1).setEnabled(false);
                this.mTabHost.getTabWidget().getChildTabViewAt(2).setEnabled(false);
                textView.setTextColor(getResources().getColor(R.color.textwhite));
                textView2.setTextColor(getResources().getColor(R.color.textdisable));
                textView3.setTextColor(getResources().getColor(R.color.textdisable));
            } else if (string == null) {
                Log.v("t3", "t1");
                this.mTabHost.getTabWidget().getChildTabViewAt(0).setEnabled(false);
                this.mTabHost.getTabWidget().getChildTabViewAt(1).setEnabled(false);
                textView.setTextColor(getResources().getColor(R.color.textdisable));
                textView2.setTextColor(getResources().getColor(R.color.textwhite));
                textView3.setTextColor(getResources().getColor(R.color.textdisable));
            } else {
                Log.v("t3", "t2");
                this.mTabHost.getTabWidget().getChildTabViewAt(1).setEnabled(false);
                this.mTabHost.getTabWidget().getChildTabViewAt(2).setEnabled(false);
                textView.setTextColor(getResources().getColor(R.color.textwhite));
                textView2.setTextColor(getResources().getColor(R.color.textdisable));
                textView3.setTextColor(getResources().getColor(R.color.textdisable));
            }
        } else {
            this.bundle = null;
            this.mTabHost.setCurrentTab(0);
            textView.setTextColor(getResources().getColor(R.color.textwhite));
        }
    }

    public void initUI() {
        setContentView(R.layout.portfolioadd);
        this.mainContainer = findViewById(R.id.mainContainer);
        initFooter();
        updateFooterStime("");
        ((ImageButton) findViewById(R.id.btnLeft)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"NewApi"})
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("backtype", "button");
                PortfolioAdd.this.setResult(-1, intent);
                PortfolioAdd.this.finish();
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

    public void onCreate(Bundle bundle2) {
        super.onCreate(bundle2);
        String[] split = SharedPrefsUtil.getValue((Context) this, "language", Locale.getDefault().toString()).split("_");
        Locale locale = new Locale(split[0], split[1]);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        this.editMode = getIntent().getBooleanExtra("EditMode", false);
        this.bundle = getIntent().getExtras();
        initUI();
        initTab();
    }

    public Bundle setIndexFragment(Portfolio_Index portfolio_Index) {
        this.indexFragment = portfolio_Index;
        this.stocksFragment = null;
        this.optionsFragment = null;
        return this.bundle;
    }

    public Bundle setOptionsFragment(Portfolio_Options portfolio_Options) {
        this.optionsFragment = portfolio_Options;
        this.stocksFragment = null;
        this.indexFragment = null;
        return this.bundle;
    }

    public Bundle setStocksFragment(Portfolio_Stocks portfolio_Stocks) {
        this.stocksFragment = portfolio_Stocks;
        this.optionsFragment = null;
        this.indexFragment = null;
        return this.bundle;
    }
}
