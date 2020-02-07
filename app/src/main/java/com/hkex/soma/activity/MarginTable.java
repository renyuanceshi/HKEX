package com.hkex.soma.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.hkex.soma.R;
import com.hkex.soma.activity.fragment.MarginTable_Index;
import com.hkex.soma.activity.fragment.MarginTable_Stock;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.basic.MultiScrollView;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.element.TutorialDialog;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;
import java.util.HashMap;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class MarginTable extends AnimatedFragmentActivity {
    public static final String TAG = "MarginTable";
    private String hcode;
    private String[] hcode_array;
    /* access modifiers changed from: private */
    public HashMap index_data = null;
    /* access modifiers changed from: private */
    public MarginTable_Index index_fragment;
    public boolean isstocknow = true;
    private MultiScrollListView listView;
    private Fragment mContent;
    private String mdate;
    private String[] mdate_array;
    private MenuContainer menu;
    private MultiScrollView scrollView;
    private String selectedDate;
    private SelectionList selectionListCode;
    private SelectionList selectionListDate;
    private SelectionList selectionListExpiry;
    private SelectionList selectionListHKATS;
    private SelectionList selectionListName;
    /* access modifiers changed from: private */
    public HashMap stock_data = null;
    /* access modifiers changed from: private */
    public MarginTable_Stock stock_fragment;
    private String ucode;
    private String uname;

    public interface OnOptionClickListener {
        void onOptionClicked(HashMap hashMap, boolean z);
    }

    public void initNoDataListView() {
        String string = getString(R.string.nodata_message);
        this.listView.setAdapter(new ArrayAdapter(this, R.layout.list_nodata, new String[]{string}));
        this.listView.setOnItemClickListener((AdapterView.OnItemClickListener) null);
        this.listView.setDividerHeight(0);
        updateFooterStime(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
    }

    @SuppressLint({"SimpleDateFormat"})
    public void initUI() {
        setContentView(R.layout.margintable);
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
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && intent != null) {
            Bundle extras = intent.getExtras();
            String string = extras.getString("searchResult");
            if (SelectionList.getPopTypes(extras.getInt("typecode")) != SelectionList.PopTypes.INDEX) {
                this.stock_fragment.updateSelectionListCode(string);
            } else {
                this.index_fragment.updateSelectionListCode(string);
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.margintable);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (!Commons.noResumeAction) {
            this.stock_fragment = new MarginTable_Stock(this);
            this.index_fragment = new MarginTable_Index(this);
            this.stock_fragment.setmapdata(this.stock_data);
            this.index_fragment.setmapdata(this.index_data);
            this.stock_fragment.setOnOptionClickListener(new OnOptionClickListener() {
                public void onOptionClicked(HashMap hashMap, boolean z) {
                    HashMap unused = MarginTable.this.stock_data = hashMap;
                    MarginTable.this.index_fragment.setmapdata(MarginTable.this.index_data);
                    if (z) {
                        MarginTable.this.isstocknow = false;
                        MarginTable.this.getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, MarginTable.this.index_fragment).commit();
                    }
                }
            });
            this.index_fragment.setOnOptionClickListener(new OnOptionClickListener() {
                public void onOptionClicked(HashMap hashMap, boolean z) {
                    HashMap unused = MarginTable.this.index_data = hashMap;
                    MarginTable.this.stock_fragment.setmapdata(MarginTable.this.stock_data);
                    if (z) {
                        MarginTable.this.isstocknow = true;
                        MarginTable.this.getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, MarginTable.this.stock_fragment).commit();
                    }
                }
            });
            this.leftViewOut = false;
            this.rightViewOut = false;
            this.ucode = Commons.defaultUnderlyingCode;
            this.uname = Commons.MapUnderlyingName(this.ucode);
            try {
                if (this.isstocknow) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, this.stock_fragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, this.index_fragment).commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
                getSupportFragmentManager().beginTransaction().add((int) R.id.maincontent, (Fragment) this.stock_fragment).commit();
            }
            initUI();
        } else {
            this.isMoving = false;
            Commons.noResumeAction = false;
        }
        if (Commons.tutor_margin == 1) {
            TutorialDialog tutorialDialog = new TutorialDialog(this);
            if (Commons.language == "en_US") {
                tutorialDialog.setTutorialResID(new int[]{R.drawable.tut_margin1_e, R.drawable.tut_margin2_e});
            } else if (Commons.language == "zh_CN") {
                tutorialDialog.setTutorialResID(new int[]{R.drawable.tut_margin1_gb, R.drawable.tut_margin2_gb});
            } else {
                tutorialDialog.setTutorialResID(new int[]{R.drawable.tut_margin1_c, R.drawable.tut_margin2_c});
            }
            tutorialDialog.show();
            Commons.tutor_margin = 0;
            SharedPrefsUtil.putValue((Context) this, "tutor_margin", Commons.tutor_margin + "");
        }
    }
}
