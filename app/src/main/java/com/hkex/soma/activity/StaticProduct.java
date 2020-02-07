package com.hkex.soma.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.adapter.StaticProductListAdapter;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.dataModel.StaticProductTable_Result;
import com.hkex.soma.dataModel.StaticProductType_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.slideAnimator.SlideRightAnimationHandler;
import com.hkex.soma.utils.Commons;
import java.util.ArrayList;

public class StaticProduct extends AnimatedFragmentActivity {
    public static final String TAG = "StaticProduct";
    private Activity _self = this;
    private String[] index_items;
    private SelectionList index_selectionList;
    private ImageButton leftbtn;
    private ListView listview;
    private MenuContainer menu;
    private ImageButton rightbtn;
    private SlideRightAnimationHandler slideRightAnimationHandler;
    private StaticProductListAdapter staticproductlistadapter;
    private ArrayList<StaticProductTable_Result.mainData> table_data = new ArrayList<>();
    private ArrayList<StaticProductType_Result.mainData> type_data = new ArrayList<>();
    private int ucodeIndex = 0;

    private void getview() {
        this.leftbtn = (ImageButton) findViewById(R.id.btnLeft);
        this.rightbtn = (ImageButton) findViewById(R.id.btnright);
        this.index_selectionList = (SelectionList) findViewById(R.id.index_selectionList);
        this.index_items = new String[0];
        this.listview = (ListView) findViewById(R.id.listview);
        this.staticproductlistadapter = new StaticProductListAdapter(this._self, this.table_data);
        this.listview.setAdapter(this.staticproductlistadapter);
    }

    private void setlistener() {
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (((StaticProductTable_Result.mainData) StaticProduct.this.table_data.get(i)).getType().equals("Inapp")) {
                    Intent intent = new Intent().setClass(StaticProduct.this._self, WebViewPage.class);
                    intent.putExtra("title", "");
                    intent.putExtra("url", ((StaticProductTable_Result.mainData) StaticProduct.this.table_data.get(i)).getLink());
                    StaticProduct.this.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(((StaticProductTable_Result.mainData) StaticProduct.this.table_data.get(i)).getLink()));
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                StaticProduct.this.startActivity(intent2);
            }
        });
    }

    public void dataResult(final StaticProductType_Result staticProductType_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    StaticProduct.this.type_data.clear();
                    for (StaticProductType_Result.mainData add : staticProductType_Result.getMainData()) {
                        StaticProduct.this.type_data.add(add);
                    }
                    String[] unused = StaticProduct.this.index_items = new String[StaticProduct.this.type_data.size()];
                    for (int i = 0; i < StaticProduct.this.index_items.length; i++) {
                        StaticProduct.this.index_items[i] = ((StaticProductType_Result.mainData) StaticProduct.this.type_data.get(i)).getName();
                    }
                    StaticProduct.this.index_selectionList.initItems(StaticProduct.this.index_items, 0, SelectionList.PopTypes.LIST, StaticProduct.this.ucodeIndex);
                    StaticProduct.this.index_selectionList.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
                        public void onListItemChanged(int i) {
                            int unused = StaticProduct.this.ucodeIndex = i;
                            StaticProduct.this.loadJSON_detail(((StaticProductType_Result.mainData) StaticProduct.this.type_data.get(i)).getTid());
                        }
                    });
                    StaticProduct.this.index_selectionList.setselected(0);
                    StaticProduct.this.dataLoaded();
                } catch (Exception e) {
                    Exception exc = e;
                    Commons.LogDebug(StaticProduct.TAG, exc.getMessage() == null ? "" : exc.getMessage());
                    StaticProduct.this.dataLoaded();
                    exc.printStackTrace();
                }
            }
        });
    }

    public void dataResult_detail(final StaticProductTable_Result staticProductTable_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    StaticProduct.this.table_data.clear();
                    for (StaticProductTable_Result.mainData add : staticProductTable_Result.getMainData()) {
                        StaticProduct.this.table_data.add(add);
                    }
                    StaticProduct.this.staticproductlistadapter.notifyDataSetChanged();
                    StaticProduct.this.dataLoaded();
                } catch (Exception e) {
                    Exception exc = e;
                    Commons.LogDebug(StaticProduct.TAG, exc.getMessage() == null ? "" : exc.getMessage());
                    StaticProduct.this.dataLoaded();
                    exc.printStackTrace();
                }
            }
        });
    }

    public void halfPortfolioUpdated() {
        this.slideRightAnimationHandler.onClick((View) null);
    }

    public void loadJSON() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(StaticProductType_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<StaticProductType_Result>() {
            public void OnJSONCompleted(StaticProductType_Result staticProductType_Result) {
                StaticProduct.this.dataResult(staticProductType_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                StaticProduct.this.dataResult((StaticProductType_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_static_product_type));
        dataLoading();
    }

    public void loadJSON_detail(String str) {
        GenericJSONParser genericJSONParser = new GenericJSONParser(StaticProductTable_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<StaticProductTable_Result>() {
            public void OnJSONCompleted(StaticProductTable_Result staticProductTable_Result) {
                StaticProduct.this.dataResult_detail(staticProductTable_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                StaticProduct.this.dataResult_detail((StaticProductTable_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_static_product_table) + "&id=" + str);
        dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.static_product);
        try {
            getview();
            setlistener();
            setmenu();
            loadJSON();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (Commons.noResumeAction) {
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
        initFooter();
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
                if (StaticProduct.this.leftViewOut || StaticProduct.this.rightViewOut) {
                    StaticProduct.this.slideRightAnimationHandler.onClick((View) null);
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
