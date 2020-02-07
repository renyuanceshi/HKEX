package com.hkex.soma.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import com.hkex.soma.adapter.MarginTableAdapter;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.dataModel.MS_IndexOptionsResult;
import com.hkex.soma.dataModel.MarginTable_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.CommonsIndex;
import com.hkex.soma.utils.StringFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MarginTable_Index extends MasterFragment {
    public static final String TAG = "MarginTable_Index";
    private MS_IndexOptionsResult.mainData[] data;
    private HashMap datamap = null;
    private int day = 0;
    private int expiryIndex = 0;
    private View fragmentView;
    private Handler handler = new Handler();
    private String hcode;
    private String[] hcode_array;
    private String[] index_items;
    private String[] index_items_val;
    private SelectionList index_selectionList;
    private MultiScrollListView listView;
    private MarginTable.OnOptionClickListener listener = null;
    private MarginTable marginTable;
    private String mdate = "";
    private String[] mdate_array;
    private MenuContainer menu;
    private int month = 0;
    private int positionIndex = 0;
    private SelectionList positionList;
    private String[] position_items;
    private String[] position_items_val = {"L", "S"};
    private RelativeLayout scrollView;
    private String selectedDate;
    private SelectionList selectionListDate;
    private SelectionList selectionListExpiry;
    private SelectionList selectionListPosition;
    private TextView stock_but;
    private int tagIndex = 0;
    private String tempucode;
    private String ucode;
    private int ucodeIndex = 0;
    private String uname;
    private int year = 0;

    public MarginTable_Index(MarginTable marginTable2) {
        this.marginTable = marginTable2;
    }

    public void dataResult(final MarginTable_Result marginTable_Result, final String str) {
        this.handler.post(new Runnable() {
            public void run() {
                int i = 0;
                try {
                    if (marginTable_Result == null) {
                        MarginTable_Index.this.initNoDataListView();
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
                            String[] unused = MarginTable_Index.this.mdate_array = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
                            MarginTable_Index.this.selectionListExpiry.initItems((String[]) arrayList.toArray(new String[arrayList.size()]), R.string.expiry, SelectionList.PopTypes.LIST, 0);
                            MarginTable_Index.this.selectionListExpiry.setEnabled(true);
                            String unused2 = MarginTable_Index.this.mdate = MarginTable_Index.this.mdate_array[0];
                        } else {
                            String[] unused3 = MarginTable_Index.this.mdate_array = null;
                            String[] strArr = {""};
                            MarginTable_Index.this.selectionListExpiry.initItems(strArr, R.string.expiry, SelectionList.PopTypes.LIST, 0);
                            MarginTable_Index.this.selectionListExpiry.setEnabled(false);
                            String unused4 = MarginTable_Index.this.mdate = "";
                        }
                        MarginTable_Index.this.loadJSON("result");
                    } else if (str.equals("result")) {
                        MarginTable_Result.mainData[] maindataArr = marginTable_Result.getmainData();
                        if (maindataArr.length == 0) {
                            MarginTable_Index.this.initNoDataListView();
                        } else {
                            MarginTable_Index.this.listView.setAdapter(new MarginTableAdapter(MarginTable_Index.this.getActivity(), R.layout.list_margintable, maindataArr));
                            MarginTable_Index.this.listView.setDividerHeight(0);
                        }
                        if (MarginTable_Index.this.marginTable != null) {
                            MarginTable_Index.this.marginTable.dataLoaded();
                            MarginTable_Index.this.marginTable.updateFooterStime(maindataArr[0].getStime());
                        }
                    } else if (str.equals("hcodelist")) {
                        MarginTable_Result.hcodelist[] hcodelistArr = marginTable_Result.gethcodelist();
                        ArrayList arrayList3 = new ArrayList();
                        while (i < hcodelistArr.length) {
                            arrayList3.add(hcodelistArr[i].getHcode());
                            i++;
                        }
                        String[] unused5 = MarginTable_Index.this.hcode_array = (String[]) arrayList3.toArray(new String[arrayList3.size()]);
                        String unused6 = MarginTable_Index.this.hcode = MarginTable_Index.this.hcode_array[0];
                        MarginTable_Index.this.loadJSON("expirylist");
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
        try {
            this.index_items = new String[Commons.indexList.getmainData().length];
            this.index_items_val = new String[Commons.indexList.getmainData().length];
            for (int i = 0; i < Commons.indexList.getmainData().length; i++) {
                String[] strArr = this.index_items;
                StringBuilder sb = new StringBuilder();
                sb.append(Commons.indexList.getmainData()[i].getUcode());
                sb.append(" - ");
                sb.append(Commons.language.equals("en_US") ? Commons.indexList.getmainData()[i].getUname() : Commons.indexList.getmainData()[i].getUnmll());
                strArr[i] = sb.toString();
                this.index_items_val[i] = Commons.indexList.getmainData()[i].getUcode();
            }
            this.hcode = CommonsIndex.defaultUnderlyingCode;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(CommonsIndex.commonList.getIndexcode());
            sb2.append(" - ");
            sb2.append(Commons.language.equals("en_US") ? CommonsIndex.commonList.getIndexname() : CommonsIndex.commonList.getIndexnmll());
            this.tempucode = sb2.toString();
            this.index_selectionList = (SelectionList) this.fragmentView.findViewById(R.id.selectionListCode);
            this.index_selectionList.initItems(SelectionList.PopTypes.INDEX, "code", true);
            if (this.datamap == null || !this.datamap.containsKey("ucode")) {
                this.index_selectionList.setSelectedText(CommonsIndex.commonList.getIndexcode() + " - " + Commons.getIndexName(CommonsIndex.commonList.getIndexcode(), Commons.language.equals("en_US")));
                this.tempucode = CommonsIndex.commonList.getIndexcode() + " - " + Commons.getIndexName(CommonsIndex.commonList.getIndexcode(), Commons.language.equals("en_US"));
            } else {
                this.index_selectionList.setSelectedText(this.datamap.get("ucode").toString());
                this.hcode = this.datamap.get("ucode").toString().split(" - ")[0];
                this.tempucode = this.datamap.get("ucode").toString();
            }
            this.position_items = new String[]{getResources().getString(R.string.buy), getResources().getString(R.string.sell)};
            this.positionList = (SelectionList) this.fragmentView.findViewById(R.id.selectionListPosition);
            if (this.datamap != null && this.datamap.containsKey("positionIndex")) {
                this.positionIndex = ((Integer) this.datamap.get("positionIndex")).intValue();
            }
            this.positionList.initItems(this.position_items, R.string.position, SelectionList.PopTypes.LIST, this.positionIndex);
            this.positionList.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
                public void onListItemChanged(int i) {
                    int unused = MarginTable_Index.this.positionIndex = i;
                    MarginTable_Index.this.loadJSON("result");
                }
            });
            this.ucode = Commons.defaultUnderlyingCode;
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
            this.selectionListDate.setOnDateSelectedListener(new SelectionList.OnDateSelectedListener() {
                public void onDateSelected(String str) {
                    Commons.LogDebug(MarginTable.TAG, "date: " + str);
                    int unused = MarginTable_Index.this.year = Integer.parseInt(str.substring(6, 10));
                    int unused2 = MarginTable_Index.this.month = Integer.parseInt(str.substring(3, 5));
                    int unused3 = MarginTable_Index.this.day = Integer.parseInt(str.substring(0, 2));
                    MarginTable_Index marginTable_Index = MarginTable_Index.this;
                    String unused4 = marginTable_Index.selectedDate = MarginTable_Index.this.year + "-" + MarginTable_Index.this.month + "-" + MarginTable_Index.this.day;
                    MarginTable_Index.this.loadJSON("expirylist");
                }
            });
            this.selectionListExpiry.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
                public void onListItemChanged(int i) {
                    Commons.LogDebug(MarginTable.TAG, "expiry index: " + i);
                    String unused = MarginTable_Index.this.mdate = MarginTable_Index.this.mdate_array[i];
                    MarginTable_Index.this.loadJSON("result");
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
                MarginTable_Index.this.dataResult(marginTable_Result, str);
            }
        });
        marginTableJSONParser.setOnJSONFailedListener(new MarginTableJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MarginTable_Index.this.dataResult((MarginTable_Result) null, str);
                MarginTable_Index.this.marginTable.dataLoaded();
            }
        });
        if (str.equals("expirylist")) {
            marginTableJSONParser.loadXML(getString(R.string.url_margintable) + "?type=expiryindexlist&hcode=" + this.hcode + "&date=" + this.selectedDate);
        } else if (str.equals("result")) {
            marginTableJSONParser.loadXML(getString(R.string.url_margintable) + "?type=indexresult&hcode=" + this.hcode + "&date=" + this.selectedDate + "&expiry=" + this.mdate + "&dir=" + this.position_items_val[this.positionIndex]);
        }
        this.marginTable.dataLoading();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.stock_but = (TextView) getView().findViewById(R.id.stock_but);
        this.stock_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HashMap hashMap = new HashMap();
                hashMap.put("year", Integer.valueOf(MarginTable_Index.this.year));
                hashMap.put("month", Integer.valueOf(MarginTable_Index.this.month));
                hashMap.put("day", Integer.valueOf(MarginTable_Index.this.day));
                hashMap.put("positionIndex", Integer.valueOf(MarginTable_Index.this.positionIndex));
                hashMap.put("ucode", MarginTable_Index.this.tempucode);
                if (MarginTable_Index.this.listener != null) {
                    MarginTable_Index.this.listener.onOptionClicked(hashMap, true);
                }
            }
        });
        initUI();
        loadJSON("expirylist");
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.margintable_index, viewGroup, false);
        return this.fragmentView;
    }

    public void onPause() {
        HashMap hashMap = new HashMap();
        hashMap.put("year", Integer.valueOf(this.year));
        hashMap.put("month", Integer.valueOf(this.month));
        hashMap.put("day", Integer.valueOf(this.day));
        hashMap.put("positionIndex", Integer.valueOf(this.positionIndex));
        hashMap.put("ucode", this.tempucode);
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
        this.hcode = str.split(" - ")[0];
        this.tempucode = str;
        this.index_selectionList.setSelectedText(str);
        loadJSON("expirylist");
    }
}
