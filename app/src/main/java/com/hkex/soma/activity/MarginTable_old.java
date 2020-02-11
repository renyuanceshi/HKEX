package com.hkex.soma.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import com.hkex.soma.JSONParser.MarginTableJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.adapter.MarginTableAdapter;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.basic.MultiScrollView;
import com.hkex.soma.dataModel.MarginTable_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.element.TutorialDialog;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;
import com.hkex.soma.utils.StringFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class MarginTable_old extends AnimatedFragmentActivity {
    /* access modifiers changed from: private */
    public String hcode;
    /* access modifiers changed from: private */
    public String[] hcode_array;
    /* access modifiers changed from: private */
    public MultiScrollListView listView;
    /* access modifiers changed from: private */
    public String mdate;
    /* access modifiers changed from: private */
    public String[] mdate_array;
    private MenuContainer menu;
    /* access modifiers changed from: private */
    public MultiScrollView scrollView;
    /* access modifiers changed from: private */
    public String selectedDate;
    private SelectionList selectionListCode;
    private SelectionList selectionListDate;
    /* access modifiers changed from: private */
    public SelectionList selectionListExpiry;
    /* access modifiers changed from: private */
    public SelectionList selectionListHKATS;
    private SelectionList selectionListName;
    private String ucode;
    private String uname;

    private void updateSelectionListCode(String str) {
        this.selectionListCode.setSelectedText(str);
        this.selectionListName.setSelectedText(Commons.MapUnderlyingName(str));
        this.ucode = str;
        loadJSON("hcodelist");
    }

    public void dataResult(final MarginTable_Result marginTable_Result, final String str) {
        this.handler.post(new Runnable() {
            public void run() {
                int i = 0;
                try {
                    if (marginTable_Result == null) {
                        MarginTable_old.this.initNoDataListView();
                    } else if (str.equals("expirylist")) {
                        MarginTable_Result.expirylist[] expirylistArr = marginTable_Result.getexpirylist();
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        while (i < expirylistArr.length) {
                            arrayList.add(StringFormatter.formatExpiry(expirylistArr[i].getExpiry()));
                            arrayList2.add(expirylistArr[i].getExpiry());
                            i++;
                        }
                        if (arrayList2.size() > 0) {
                            String[] unused = MarginTable_old.this.mdate_array = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
                            MarginTable_old.this.selectionListExpiry.initItems((String[]) arrayList.toArray(new String[arrayList.size()]), -1, SelectionList.PopTypes.LIST, 0);
                            MarginTable_old.this.selectionListExpiry.setEnabled(true);
                            String unused2 = MarginTable_old.this.mdate = MarginTable_old.this.mdate_array[0];
                        } else {
                            String[] unused3 = MarginTable_old.this.mdate_array = null;
                            String[] strArr = {""};
                            MarginTable_old.this.selectionListExpiry.initItems(strArr, -1, SelectionList.PopTypes.LIST, 0);
                            MarginTable_old.this.selectionListExpiry.setEnabled(false);
                            String unused4 = MarginTable_old.this.mdate = "";
                        }
                        MarginTable_old.this.loadJSON("result");
                    } else if (str.equals("result")) {
                        MarginTable_Result.mainData[] maindataArr = marginTable_Result.getmainData();
                        if (maindataArr.length == 0) {
                            MarginTable_old.this.initNoDataListView();
                        } else {
                            MarginTable_old.this.listView.setAdapter(new MarginTableAdapter(MarginTable_old.this, R.layout.list_margintable, maindataArr));
                            MarginTable_old.this.listView.setDividerHeight(0);
                        }
                        MarginTable_old.this.dataLoaded();
                        MarginTable_old.this.scrollView.initMultiScrollView(MarginTable_old.this.listView, 127.0f, 0.0f);
                        MarginTable_old.this.updateFooterStime(maindataArr[0].getStime());
                    } else if (str.equals("hcodelist")) {
                        MarginTable_Result.hcodelist[] hcodelistArr = marginTable_Result.gethcodelist();
                        ArrayList arrayList3 = new ArrayList();
                        while (i < hcodelistArr.length) {
                            arrayList3.add(hcodelistArr[i].getHcode());
                            i++;
                        }
                        String[] unused5 = MarginTable_old.this.hcode_array = (String[]) arrayList3.toArray(new String[arrayList3.size()]);
                        MarginTable_old.this.selectionListHKATS.initItems(MarginTable_old.this.hcode_array, -1, SelectionList.PopTypes.LIST, 0);
                        String unused6 = MarginTable_old.this.hcode = MarginTable_old.this.hcode_array[0];
                        MarginTable_old.this.loadJSON("expirylist");
                    }
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
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
        this.listView = (MultiScrollListView) findViewById(R.id.listView);
        this.scrollView = (MultiScrollView) findViewById(2131165570);
        String format = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        int parseInt = Integer.parseInt(format.substring(6, 10));
        int parseInt2 = Integer.parseInt(format.substring(3, 5));
        int parseInt3 = Integer.parseInt(format.substring(0, 2));
        this.selectedDate = parseInt + "-" + parseInt2 + "-" + parseInt3;
        this.selectionListDate = (SelectionList) findViewById(R.id.selectionListDate);
        this.selectionListDate.initItems(R.string.set_date, SelectionList.PopTypes.DATE, parseInt, parseInt2, parseInt3);
        this.selectionListDate.setSelectedText(format);
        this.selectionListExpiry = (SelectionList) findViewById(R.id.selectionListExpiry);
        this.selectionListHKATS = (SelectionList) findViewById(R.id.selectionListHKATS);
        this.selectionListCode = (SelectionList) findViewById(R.id.selectionListCode);
        this.selectionListName = (SelectionList) findViewById(R.id.selectionListName);
        this.selectionListCode.initItems(SelectionList.PopTypes.CODE, "code", true);
        this.selectionListName.initItems(SelectionList.PopTypes.NAME, "name", true);
        this.selectionListCode.setSelectedText(this.ucode);
        this.selectionListName.setSelectedText(this.uname);
        this.selectionListHKATS.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                Commons.LogDebug(MarginTable.TAG, "hcode index: " + i + ", date: " + MarginTable_old.this.selectedDate);
                String unused = MarginTable_old.this.hcode = MarginTable_old.this.hcode_array[i];
                MarginTable_old.this.loadJSON("expirylist");
            }
        });
        this.selectionListDate.setOnDateSelectedListener(new SelectionList.OnDateSelectedListener() {
            public void onDateSelected(String str) {
                Commons.LogDebug(MarginTable.TAG, "date: " + str);
                int parseInt = Integer.parseInt(str.substring(6, 10));
                int parseInt2 = Integer.parseInt(str.substring(3, 5));
                int parseInt3 = Integer.parseInt(str.substring(0, 2));
                MarginTable_old marginTable_old = MarginTable_old.this;
                String unused = marginTable_old.selectedDate = parseInt + "-" + parseInt2 + "-" + parseInt3;
                MarginTable_old.this.loadJSON("expirylist");
            }
        });
        this.selectionListExpiry.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                Commons.LogDebug(MarginTable.TAG, "expiry index: " + i);
                String unused = MarginTable_old.this.mdate = MarginTable_old.this.mdate_array[i];
                MarginTable_old.this.loadJSON("result");
            }
        });
    }

    public void loadJSON(final String str) {
        MarginTableJSONParser marginTableJSONParser = new MarginTableJSONParser();
        marginTableJSONParser.setOnJSONCompletedListener(new MarginTableJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(MarginTable_Result marginTable_Result) {
                Commons.LogDebug("", marginTable_Result.toString());
                MarginTable_old.this.dataResult(marginTable_Result, str);
            }
        });
        marginTableJSONParser.setOnJSONFailedListener(new MarginTableJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MarginTable_old.this.ShowConnectionErrorDialog(runnable);
                MarginTable_old.this.dataResult((MarginTable_Result) null, str);
            }
        });
        if (str.equals("expirylist")) {
            marginTableJSONParser.loadXML(getString(R.string.url_margintable) + "?type=expirylist&hcode=" + this.hcode + "&date=" + this.selectedDate);
        } else if (str.equals("result")) {
            marginTableJSONParser.loadXML(getString(R.string.url_margintable) + "?type=result&hcode=" + this.hcode + "&date=" + this.selectedDate + "&expiry=" + this.mdate);
        } else {
            marginTableJSONParser.loadXML(getString(R.string.url_margintable) + "?type=hcodelist&ucode=" + this.ucode);
        }
        dataLoading();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && intent != null) {
            updateSelectionListCode(intent.getExtras().getString("searchResult"));
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (!Commons.noResumeAction) {
            this.leftViewOut = false;
            this.rightViewOut = false;
            this.ucode = Commons.defaultUnderlyingCode;
            this.uname = Commons.MapUnderlyingName(this.ucode);
            initUI();
            loadJSON("hcodelist");
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
