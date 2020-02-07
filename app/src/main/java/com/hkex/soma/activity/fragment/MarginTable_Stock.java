package com.hkex.soma.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hkex.soma.JSONParser.MarginTableJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.MarginTable;
import com.hkex.soma.activity.MarketSnapshot;
import com.hkex.soma.adapter.MarginTableAdapter;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.dataModel.MSBanner;
import com.hkex.soma.dataModel.MS_IndexOptionsResult;
import com.hkex.soma.dataModel.MarginTable_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.element.SelectionListShort;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MarginTable_Stock extends MasterFragment {
    private MarketSnapshot MSFragmentActivity;
    private int bannerIndex = 0;
    private Runnable bannerRunnable = null;
    private ArrayList<MSBanner.banner> banners = new ArrayList<>();
    private MS_IndexOptionsResult.mainData[] data;
    private boolean data1done = false;
    private boolean data2done = false;
    private HashMap datamap = null;
    private int day;
    private View fragmentView;
    private Handler handler = new Handler();
    private String hcode;
    private String[] hcode_array;
    private int hcode_index = 0;
    private TextView index_but;
    private MultiScrollListView listView;
    private MarginTable.OnOptionClickListener listener = null;
    private MarginTable marginTable;
    private String mdate;
    private String[] mdate_array;
    private MenuContainer menu;
    private int month;
    private MSBanner msBanenr;
    private RelativeLayout scrollView;
    private String selectedDate;
    private SelectionList selectionListCode;
    private SelectionList selectionListDate;
    private SelectionList selectionListExpiry;
    private SelectionList selectionListHKATS;
    private SelectionListShort selectionListName;
    private int tagIndex = 0;
    private String[] type_array = {"MTCall", "MTPut", "All"};
    public String ucode;
    private int ucodeIndex = 0;
    private String uname;
    private int year;

    public MarginTable_Stock(MarginTable marginTable2) {
        this.marginTable = marginTable2;
    }

    public void dataResult(final MarginTable_Result marginTable_Result, final String str) {
        this.handler.post(new Runnable() {
            public void run() {
                int i = 0;
                try {
                    if (marginTable_Result == null) {
                        MarginTable_Stock.this.initNoDataListView();
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
                            String[] unused = MarginTable_Stock.this.mdate_array = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
                            MarginTable_Stock.this.selectionListExpiry.initItems((String[]) arrayList.toArray(new String[arrayList.size()]), R.string.expiry, SelectionList.PopTypes.LIST, 0);
                            MarginTable_Stock.this.selectionListExpiry.setEnabled(true);
                            String unused2 = MarginTable_Stock.this.mdate = MarginTable_Stock.this.mdate_array[0];
                        } else {
                            String[] unused3 = MarginTable_Stock.this.mdate_array = null;
                            String[] strArr = {""};
                            MarginTable_Stock.this.selectionListExpiry.initItems(strArr, R.string.expiry, SelectionList.PopTypes.LIST, 0);
                            MarginTable_Stock.this.selectionListExpiry.setEnabled(false);
                            String unused4 = MarginTable_Stock.this.mdate = "";
                        }
                        MarginTable_Stock.this.loadJSON("result");
                    } else if (str.equals("result")) {
                        MarginTable_Result.mainData[] maindataArr = marginTable_Result.getmainData();
                        if (maindataArr.length == 0) {
                            MarginTable_Stock.this.initNoDataListView();
                        } else {
                            MarginTable_Stock.this.listView.setAdapter(new MarginTableAdapter(MarginTable_Stock.this.getActivity(), R.layout.list_margintable, maindataArr));
                            MarginTable_Stock.this.listView.setDividerHeight(0);
                        }
                        if (MarginTable_Stock.this.marginTable != null) {
                            MarginTable_Stock.this.marginTable.dataLoaded();
                            MarginTable_Stock.this.marginTable.updateFooterStime(maindataArr[0].getStime());
                        }
                    } else if (str.equals("hcodelist")) {
                        MarginTable_Result.hcodelist[] hcodelistArr = marginTable_Result.gethcodelist();
                        ArrayList arrayList3 = new ArrayList();
                        while (i < hcodelistArr.length) {
                            arrayList3.add(hcodelistArr[i].getHcode());
                            i++;
                        }
                        String[] unused5 = MarginTable_Stock.this.hcode_array = (String[]) arrayList3.toArray(new String[arrayList3.size()]);
                        if (MarginTable_Stock.this.datamap != null && MarginTable_Stock.this.datamap.containsKey("hcode_index")) {
                            int unused6 = MarginTable_Stock.this.hcode_index = ((Integer) MarginTable_Stock.this.datamap.get("hcode_index")).intValue();
                        }
                        MarginTable_Stock.this.selectionListHKATS.initItems(MarginTable_Stock.this.hcode_array, R.string.hkats_code, SelectionList.PopTypes.LIST, MarginTable_Stock.this.hcode_index);
                        String unused7 = MarginTable_Stock.this.hcode = MarginTable_Stock.this.hcode_array[MarginTable_Stock.this.hcode_index];
                        MarginTable_Stock.this.loadJSON("expirylist");
                    }
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void initNoDataListView() {
        String string = getString(R.string.nodata_message);
        this.listView.setAdapter(new ArrayAdapter(getActivity(), R.layout.list_nodata, new String[]{string}));
        this.listView.setOnItemClickListener((AdapterView.OnItemClickListener) null);
        this.listView.setDividerHeight(0);
    }

    public void initUI() {
        boolean z = true;
        try {
            Log.v("MarginTable_Stock", "MarginTable_Stock");
            this.ucode = Commons.defaultUnderlyingCode;
            if (this.datamap != null && this.datamap.containsKey("ucode")) {
                this.ucode = this.datamap.get("ucode").toString();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("");
            if (this.datamap == null) {
                z = false;
            }
            sb.append(z);
            sb.append(" ucode : ");
            sb.append(this.ucode);
            Log.v("MarginTable_Stock", sb.toString());
            this.uname = Commons.MapUnderlyingName(this.ucode);
            this.listView = (MultiScrollListView) getView().findViewById(R.id.listView);
            this.scrollView = (RelativeLayout) getView().findViewById(R.id.scrollView);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar instance = Calendar.getInstance();
            if (this.datamap != null && this.datamap.containsKey("year") && this.datamap.containsKey("month") && this.datamap.containsKey("day")) {
                this.year = ((Integer) this.datamap.get("year")).intValue();
                this.month = ((Integer) this.datamap.get("month")).intValue();
                this.day = ((Integer) this.datamap.get("day")).intValue();
                instance.set(this.year, this.month - 1, this.day);
            }
            String format = simpleDateFormat.format(instance.getTime());
            this.year = Integer.parseInt(format.substring(6, 10));
            this.month = Integer.parseInt(format.substring(3, 5));
            this.day = Integer.parseInt(format.substring(0, 2));
            this.selectedDate = this.year + "-" + this.month + "-" + this.day;
            this.selectionListDate = (SelectionList) getView().findViewById(R.id.selectionListDate);
            this.selectionListDate.initItems(R.string.set_date, SelectionList.PopTypes.DATE, this.year, this.month, this.day);
            this.selectionListDate.setSelectedText(format);
            this.selectionListExpiry = (SelectionList) getView().findViewById(R.id.selectionListExpiry);
            this.selectionListHKATS = (SelectionList) getView().findViewById(R.id.selectionListHKATS);
            this.selectionListCode = (SelectionList) getView().findViewById(R.id.selectionListCode);
            this.selectionListName = (SelectionListShort) getView().findViewById(R.id.selectionListName);
            this.selectionListCode.initItems(SelectionList.PopTypes.CODE, "code", true);
            this.selectionListName.initItems(SelectionListShort.PopTypes.NAME, "name", true);
            this.selectionListCode.setSelectedText(this.ucode);
            this.selectionListName.setSelectedText(this.uname);
            this.selectionListHKATS.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
                public void onListItemChanged(int i) {
                    Commons.LogDebug(MarginTable.TAG, "hcode index: " + i + ", date: " + MarginTable_Stock.this.selectedDate);
                    int unused = MarginTable_Stock.this.hcode_index = i;
                    String unused2 = MarginTable_Stock.this.hcode = MarginTable_Stock.this.hcode_array[i];
                    MarginTable_Stock.this.loadJSON("expirylist");
                }
            });
            this.selectionListDate.setOnDateSelectedListener(new SelectionList.OnDateSelectedListener() {
                public void onDateSelected(String str) {
                    Commons.LogDebug(MarginTable.TAG, "date: " + str);
                    int unused = MarginTable_Stock.this.year = Integer.parseInt(str.substring(6, 10));
                    int unused2 = MarginTable_Stock.this.month = Integer.parseInt(str.substring(3, 5));
                    int unused3 = MarginTable_Stock.this.day = Integer.parseInt(str.substring(0, 2));
                    MarginTable_Stock marginTable_Stock = MarginTable_Stock.this;
                    String unused4 = marginTable_Stock.selectedDate = MarginTable_Stock.this.year + "-" + MarginTable_Stock.this.month + "-" + MarginTable_Stock.this.day;
                    MarginTable_Stock.this.loadJSON("expirylist");
                }
            });
            this.selectionListExpiry.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
                public void onListItemChanged(int i) {
                    Commons.LogDebug(MarginTable.TAG, "expiry index: " + i);
                    String unused = MarginTable_Stock.this.mdate = MarginTable_Stock.this.mdate_array[i];
                    MarginTable_Stock.this.loadJSON("result");
                }
            });
            this.index_but = (TextView) getView().findViewById(R.id.index_but);
            this.index_but.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("year", Integer.valueOf(MarginTable_Stock.this.year));
                    hashMap.put("month", Integer.valueOf(MarginTable_Stock.this.month));
                    hashMap.put("day", Integer.valueOf(MarginTable_Stock.this.day));
                    hashMap.put("hcode_index", Integer.valueOf(MarginTable_Stock.this.hcode_index));
                    hashMap.put("ucode", MarginTable_Stock.this.ucode);
                    if (MarginTable_Stock.this.listener != null) {
                        MarginTable_Stock.this.listener.onOptionClicked(hashMap, true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadJSON(final String str) {
        MarginTableJSONParser marginTableJSONParser = new MarginTableJSONParser();
        marginTableJSONParser.setOnJSONCompletedListener(new MarginTableJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(MarginTable_Result marginTable_Result) {
                Commons.LogDebug("", marginTable_Result.toString());
                MarginTable_Stock.this.dataResult(marginTable_Result, str);
            }
        });
        marginTableJSONParser.setOnJSONFailedListener(new MarginTableJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MarginTable_Stock.this.dataResult((MarginTable_Result) null, str);
                MarginTable_Stock.this.marginTable.dataLoaded();
            }
        });
        if (str.equals("expirylist")) {
            marginTableJSONParser.loadXML(getString(R.string.url_margintable) + "?type=expirylist&hcode=" + this.hcode + "&date=" + this.selectedDate);
        } else if (str.equals("result")) {
            marginTableJSONParser.loadXML(getString(R.string.url_margintable) + "?type=result&hcode=" + this.hcode + "&date=" + this.selectedDate + "&expiry=" + this.mdate);
        } else {
            marginTableJSONParser.loadXML(getString(R.string.url_margintable) + "?type=hcodelist&ucode=" + this.ucode);
        }
        this.marginTable.dataLoading();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        try {
            initUI();
            loadJSON("hcodelist");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.margintable_stock, viewGroup, false);
        return this.fragmentView;
    }

    public void onPause() {
        HashMap hashMap = new HashMap();
        hashMap.put("year", Integer.valueOf(this.year));
        hashMap.put("month", Integer.valueOf(this.month));
        hashMap.put("day", Integer.valueOf(this.day));
        hashMap.put("hcode_index", Integer.valueOf(this.hcode_index));
        hashMap.put("ucode", this.ucode);
        if (this.listener != null) {
            this.listener.onOptionClicked(hashMap, false);
        }
        super.onPause();
    }

    public void setOnOptionClickListener(MarginTable.OnOptionClickListener onOptionClickListener) {
        this.listener = onOptionClickListener;
    }

    public void setmapdata(HashMap hashMap) {
        this.datamap = hashMap;
    }

    public void updateSelectionListCode(String str) {
        this.selectionListCode.setSelectedText(str);
        this.selectionListName.setSelectedText(Commons.MapUnderlyingName(str));
        this.ucode = str;
        loadJSON("hcodelist");
    }
}
