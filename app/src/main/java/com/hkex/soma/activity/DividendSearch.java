package com.hkex.soma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import com.hkex.soma.JSONParser.DividendJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.adapter.DividendAdapter;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.basic.MultiScrollView;
import com.hkex.soma.dataModel.Dividend_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.utils.Commons;

public class DividendSearch extends AnimatedFragmentActivity {
    private Dividend_Result.mainData[] data;
    private MultiScrollListView listView;
    private MenuContainer menu;
    private MultiScrollView scrollView;
    private SelectionList selectionListCode;
    private SelectionList selectionListName;
    private String ucode;
    private String uname;

    private void updateSelectionListSCode(String str) {
        String str2 = str.split(" - ")[0];
        String str3 = str.split(" - ")[1];
        this.selectionListCode.setSelectedText(str2);
        this.selectionListName.setSelectedText(str3);
        this.ucode = str2;
        loadJSON();
    }

    public void dataResult(final Dividend_Result dividend_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (dividend_Result == null) {
                        String string = DividendSearch.this.getString(R.string.nodata_message);
                        DividendSearch.this.listView.setAdapter(new ArrayAdapter(DividendSearch.this, R.layout.list_nodata, new String[]{string}));
                    } else {
                        Dividend_Result.mainData[] unused = DividendSearch.this.data = dividend_Result.getmainData();
                        DividendSearch.this.listView.setAdapter(new DividendAdapter(DividendSearch.this, R.layout.list_dividend, DividendSearch.this.data));
                    }
                    DividendSearch.this.dataLoaded();
                    DividendSearch.this.listView.setDividerHeight(0);
                    DividendSearch.this.scrollView.initMultiScrollView(DividendSearch.this.listView, 127.0f, 0.0f);
                    DividendSearch.this.updateFooterStime(DividendSearch.this.data[0].getAnnounceDate());
                    DividendSearch.this.showFooterImportantNote();
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void initUI() {
        setContentView(R.layout.dividendsearch);
        this.menu = new MenuContainer(this);
        this.mainContainer = findViewById(R.id.mainContainer);
        this.clickControlContainer = (ClickControlContainer) findViewById(R.id.clickControlContainer);
        ClickControlContainer clickControlContainer = (ClickControlContainer) findViewById(R.id.menuContainer);
        clickControlContainer.addView(this.menu, new LinearLayout.LayoutParams(-1, -1));
        initFooter();
        this.mainView = findViewById(R.id.appContainer);
        this.leftView = clickControlContainer;
        final SlideLeftAnimationHandler slideLeftAnimationHandler = new SlideLeftAnimationHandler(this);
        this.mainView.findViewById(R.id.btnLeft).setOnClickListener(slideLeftAnimationHandler);
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
        this.listView = (MultiScrollListView) findViewById(R.id.listView);
        this.scrollView = (MultiScrollView) findViewById(R.id.scrollView);
        this.selectionListCode = (SelectionList) findViewById(R.id.selectionListCode);
        this.selectionListName = (SelectionList) findViewById(R.id.selectionListName);
        this.selectionListCode.initItems(SelectionList.PopTypes.SCODE, "code", true);
        this.selectionListName.initItems(SelectionList.PopTypes.SNAME, "name", true);
        this.selectionListCode.setSelectedText(this.ucode);
        this.selectionListName.setSelectedText(this.uname);
    }

    public void loadJSON() {
        DividendJSONParser dividendJSONParser = new DividendJSONParser();
        dividendJSONParser.setOnJSONCompletedListener(new DividendJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(Dividend_Result dividend_Result) {
                Commons.LogDebug("", dividend_Result.toString());
                DividendSearch.this.dataResult(dividend_Result);
            }
        });
        dividendJSONParser.setOnJSONFailedListener(new DividendJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                DividendSearch.this.ShowConnectionErrorDialog(runnable);
                DividendSearch.this.dataResult((Dividend_Result) null);
            }
        });
        dividendJSONParser.loadXML(getString(R.string.url_dividend) + "?ucode=" + this.ucode);
        dataLoading();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && intent != null) {
            updateSelectionListSCode(intent.getExtras().getString("searchResult"));
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
    }

    protected void onResume() {
        super.onResume();
        if (!Commons.noResumeAction) {
            this.leftViewOut = false;
            this.rightViewOut = false;
            this.ucode = Commons.defaultUnderlyingCode;
            this.uname = Commons.MapUnderlyingName(this.ucode);
            initUI();
            loadJSON();
            return;
        }
        this.isMoving = false;
        Commons.noResumeAction = false;
    }
}
