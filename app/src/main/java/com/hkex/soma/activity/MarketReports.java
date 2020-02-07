package com.hkex.soma.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.adapter.MarketReportsListAdapter;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.dataModel.MarketReport_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.slideAnimator.SlideRightAnimationHandler;
import com.hkex.soma.utils.Commons;
import java.util.ArrayList;

public class MarketReports extends AnimatedFragmentActivity {
    public static final String TAG = "MarketReports";
    private Activity _self = this;
    private ArrayList<MarketReport_Result.mainData> data = new ArrayList<>();
    private Handler handler = new Handler();
    private ImageButton leftbtn;
    private ListView listview;
    /* access modifiers changed from: private */
    public MarketReportsListAdapter marketreportslistadapter;
    private MenuContainer menu;
    private ImageButton rightbtn;
    /* access modifiers changed from: private */
    public SlideRightAnimationHandler slideRightAnimationHandler;

    private void getview() {
        this.leftbtn = (ImageButton) findViewById(R.id.btnLeft);
        this.rightbtn = (ImageButton) findViewById(R.id.btnright);
        this.listview = (ListView) findViewById(R.id.listview);
        this.marketreportslistadapter = new MarketReportsListAdapter(this._self, this.data);
        this.listview.setAdapter(this.marketreportslistadapter);
    }

    private void setlistener() {
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Commons.LogDebug("setWebViewClient", "Open PDF: " + ((MarketReport_Result.mainData) MarketReports.this.data.get(i)).getFile());
                Commons.noResumeAction = true;
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(((MarketReport_Result.mainData) MarketReports.this.data.get(i)).getFile()));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MarketReports.this._self.startActivity(intent);
            }
        });
    }

    public void dataResult(final MarketReport_Result marketReport_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    MarketReports.this.data.clear();
                    for (MarketReport_Result.mainData add : marketReport_Result.getMainData()) {
                        MarketReports.this.data.add(add);
                    }
                    MarketReports.this.marketreportslistadapter.setdata(MarketReports.this.data);
                    MarketReports.this.marketreportslistadapter.notifyDataSetChanged();
                } catch (Exception e) {
                    MarketReports.this.dataLoaded();
                    e.printStackTrace();
                }
            }
        });
    }

    public void halfPortfolioUpdated() {
        this.slideRightAnimationHandler.onClick((View) null);
    }

    public void loadJSON() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(MarketReport_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<MarketReport_Result>() {
            public void OnJSONCompleted(MarketReport_Result marketReport_Result) {
                MarketReports.this.dataResult(marketReport_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                MarketReports.this.dataResult((MarketReport_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_market_report));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (!Commons.noResumeAction) {
            setContentView(R.layout.market_reports_list);
            try {
                getview();
                setlistener();
                setmenu();
                loadJSON();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.isMoving = false;
            Commons.noResumeAction = false;
        }
    }

    public void setmenu() {
        this.rightbtn.setVisibility(View.INVISIBLE);
        this.leftViewOut = false;
        this.rightViewOut = false;
        this.menu = new MenuContainer(this);
        this.mainContainer = findViewById(R.id.mainContainer);
        ClickControlContainer clickControlContainer = (ClickControlContainer) findViewById(R.id.menuContainer);
        this.clickControlContainer = (ClickControlContainer) findViewById(R.id.clickControlContainer);
        clickControlContainer.addView(this.menu, new LinearLayout.LayoutParams(-1, -1));
        this.leftView = clickControlContainer;
        this.mainView = findViewById(R.id.appContainer);
        this.slideRightAnimationHandler = new SlideRightAnimationHandler(this);
        final SlideLeftAnimationHandler slideLeftAnimationHandler = new SlideLeftAnimationHandler(this);
        this.leftbtn.setOnClickListener(slideLeftAnimationHandler);
        this.clickControlContainer.setOnLeftToRightSwipeListener(new ClickControlContainer.OnLeftToRightSwipeListener() {
            public void onLeftToRightSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        this.clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                if (MarketReports.this.leftViewOut || MarketReports.this.rightViewOut) {
                    MarketReports.this.slideRightAnimationHandler.onClick((View) null);
                }
            }
        });
        clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        this.menu.setSliedBackHandler(slideLeftAnimationHandler);
    }
}
