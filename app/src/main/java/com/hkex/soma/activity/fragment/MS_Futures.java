package com.hkex.soma.activity.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.MSBannerPage;
import com.hkex.soma.activity.MarketSnapshot;
import com.hkex.soma.activity.WebViewPage2;
import com.hkex.soma.adapter.MS_FuturesAdapter;
import com.hkex.soma.basic.LoaderImageView;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.dataModel.FuturesBanner_Result;
import com.hkex.soma.dataModel.FuturesCList_Result;
import com.hkex.soma.dataModel.FuturesList_Result;
import com.hkex.soma.dataModel.FuturesPList_Result;
import com.hkex.soma.dataModel.MSBanner;
import com.hkex.soma.dataModel.MS_IndexOptionsResult;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.element.SelectionList2line;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class MS_Futures extends MasterFragment {
    public static final String TAG = "MS_Futures";
    private MarketSnapshot MSFragmentActivity;
    private boolean aht = false;
    private int bannerIndex = 0;
    private Runnable bannerRunnable = null;
    private ArrayList<MSBanner.banner> banners = new ArrayList<>();
    private TextView chart_title;
    private WebView chart_webview;
    /* access modifiers changed from: private */
    private int clist_index = 0;
    /* access modifiers changed from: private */
    private FuturesCList_Result.mainData[] clistdata;
    private MS_IndexOptionsResult.mainData[] data;
    /* access modifiers changed from: private */
    private boolean data1done = false;
    /* access modifiers changed from: private */
    private boolean data2done = false;
    private ImageView day_nigth_but;
    private String daynight = "d";
    private View fragmentView;
    private Handler handler = new Handler();
    private RelativeLayout head_ask_box;
    private RelativeLayout head_bid_box;
    private String[] index_items;
    private String[] index_items_val;
    private boolean isshowdnmsg = true;
    private boolean istickeron = false;
    private MultiScrollListView listView;
    private int mlist_index = 0;
    public FuturesList_Result.mainData[] mlistdata;
    private MSBanner msBanenr;
    private boolean noItemClick = false;
    private ImageView note_but;
    private int plist_index = 0;
    private FuturesPList_Result.mainData[] plistdata;
    private ImageView reload;
    private String[] select_list_array1;
    private String[] select_list_array2;
    private SelectionList selectionList1;
    private TextView selectionList1_str;
    private SelectionList2line selectionList2;
    private TextView selectionList2_str;
    private int tagIndex = 0;
    private String[] type_array = {"All", "MTCall", "MTPut"};
    private int ucodeIndex = 0;

    private void initBanner(final MSBanner.banner banner, int i) {
        LoaderImageView loaderImageView = (LoaderImageView) this.fragmentView.findViewById(i);
        loaderImageView.setImageDrawable(getString(R.string.url_domain) + banner.getImg().replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "%20"), ImageView.ScaleType.FIT_XY);
        if (banner.getType() != 1) {
            if (banner.getType() == 2) {
                loaderImageView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint({"NewApi"})
                    public void onClick(View view) {
                        Intent intent = new Intent().setClass(MS_Futures.this.MSFragmentActivity, MSBannerPage.class);
                        intent.putExtra("url", MS_Futures.this.getString(R.string.url_domain) + "" + banner.getUrl());
                        MS_Futures.this.MSFragmentActivity.startActivity(intent);
                    }
                });
            } else if (banner.getType() == 3) {
                loaderImageView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint({"NewApi"})
                    public void onClick(View view) {
                        Intent intent = new Intent().setClass(MS_Futures.this.MSFragmentActivity, MSBannerPage.class);
                        intent.putExtra("url", banner.getUrl());
                        MS_Futures.this.MSFragmentActivity.startActivity(intent);
                    }
                });
            }
        }
    }

    private void load_clist(String str, boolean z, String str2) {
        String str3;
        this.data1done = false;
        GenericJSONParser genericJSONParser = new GenericJSONParser(FuturesCList_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<FuturesCList_Result>() {
            public void OnJSONCompleted(FuturesCList_Result futuresCList_Result) {
                Commons.LogDebug("", futuresCList_Result.toString());
                MS_Futures.this.clistdataResult(futuresCList_Result);
                MS_Futures.this.MSFragmentActivity.dataLoaded();
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_Futures.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
                MS_Futures.this.clistdataResult((FuturesCList_Result) null);
                MS_Futures.this.MSFragmentActivity.dataLoaded();
            }
        });
        String str4 = getString(R.string.url_futures) + "&type=CList&id=" + str;
        if (z) {
            str3 = str4 + "&aht=y&daynight=" + str2;
        } else {
            str3 = str4 + "&aht=n";
        }
        Log.v(TAG, "load_clist");
        genericJSONParser.loadXML(str3);
        this.MSFragmentActivity.dataLoading();
    }

    private void load_mlist() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(FuturesList_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<FuturesList_Result>() {
            public void OnJSONCompleted(FuturesList_Result futuresList_Result) {
                Commons.LogDebug("", futuresList_Result.toString());
                MS_Futures.this.mlistdataResult(futuresList_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_Futures.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
                MS_Futures.this.MSFragmentActivity.dataLoaded();
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_futures) + "&type=MList");
        this.MSFragmentActivity.dataLoading();
    }

    private void load_plist(String str) {
        GenericJSONParser genericJSONParser = new GenericJSONParser(FuturesPList_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<FuturesPList_Result>() {
            public void OnJSONCompleted(FuturesPList_Result futuresPList_Result) {
                Commons.LogDebug("", futuresPList_Result.toString());
                MS_Futures.this.plistdataResult(futuresPList_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_Futures.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
                MS_Futures.this.MSFragmentActivity.dataLoaded();
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_futures) + "&type=PList&id=" + str);
        this.MSFragmentActivity.dataLoading();
    }

    private void updateListHeader() {
        LinearLayout linearLayout = (LinearLayout) this.fragmentView.findViewById(R.id.listTitleHeader);
        if (this.tagIndex == 2) {
            linearLayout.setBackgroundResource(R.drawable.list_header_red);
        } else {
            linearLayout.setBackgroundResource(R.drawable.list_header);
        }
    }

    private void update_title_chart(int i) {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        if (Commons.language.equals("en_US")) {
            new SimpleDateFormat("MMM yyyy");
        } else {
            new SimpleDateFormat("yyyy" + getResources().getString(R.string.year_text) + "MMM");
        }
        try {
            date = simpleDateFormat.parse(this.clistdata[i].getMdate());
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        String str = "";
        if (this.aht && this.daynight.equals("d")) {
            str = getString(R.string.day_session);
            this.isshowdnmsg = false;
        } else if (this.aht && this.daynight.equals("n")) {
            str = getString(R.string.night_session);
            this.isshowdnmsg = true;
        }
        TextView textView = this.chart_title;
        textView.setText(StringFormatter.formatDatetime(date, getContext()) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + str);
    }

    public void clistdataResult(final FuturesCList_Result futuresCList_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    FuturesCList_Result.mainData[] unused = MS_Futures.this.clistdata = futuresCList_Result.getMainData();
                    String stime = futuresCList_Result.getStime();
                    if (futuresCList_Result.getContingency().equals("0")) {
                        MS_Futures.this.MSFragmentActivity.ContingencyMessageBox(Commons.language.equals("en_US") ? futuresCList_Result.getEngmsg() : futuresCList_Result.getChimsg(), false);
                        MS_Futures.this.listView.setAdapter(new ArrayAdapter(MS_Futures.this.MSFragmentActivity, R.layout.list_nodata, MS_Futures.this.type_array[MS_Futures.this.tagIndex] == "MTC" ? new String[]{MS_Futures.this.MSFragmentActivity.getString(R.string.nodata_message)} : new String[]{MS_Futures.this.MSFragmentActivity.getString(R.string.nooption_message)}));
                        boolean unused2 = MS_Futures.this.noItemClick = true;
                    } else {
                        MS_Futures.this.listView.setAdapter(new MS_FuturesAdapter(MS_Futures.this.MSFragmentActivity, R.layout.list_ms_futures, MS_Futures.this.clistdata, MS_Futures.this.type_array[MS_Futures.this.tagIndex]));
                        MS_Futures.this.listView.setDividerHeight(0);
                        MS_Futures.this.update_title_chart(0);
                        MS_Futures.this.loadBannerJSON(MS_Futures.this.clistdata[0].getCid(), MS_Futures.this.daynight);
                    }
                    boolean unused3 = MS_Futures.this.data1done = true;
                    if (MS_Futures.this.data1done && MS_Futures.this.data2done) {
                        MS_Futures.this.MSFragmentActivity.dataLoaded();
                    }
                    MS_Futures.this.MSFragmentActivity.updateFooterStime_nodelay(stime);
                } catch (Exception e) {
                    e.printStackTrace();
                    MS_Futures.this.listView.setAdapter(new ArrayAdapter(MS_Futures.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_Futures.this.MSFragmentActivity.getString(R.string.nodata_message)}));
                    boolean unused4 = MS_Futures.this.noItemClick = true;
                }
            }
        });
    }

    public void dataResultBanner(final FuturesBanner_Result futuresBanner_Result) {
        this.data2done = true;
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    MS_Futures.this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
                    if (!futuresBanner_Result.getImgdata().equals("")) {
                        byte[] decode = Base64.decode(futuresBanner_Result.getImgdata(), 0);
                        BitmapFactory.decodeByteArray(decode, 0, decode.length);
                    }
                    boolean unused = MS_Futures.this.data2done = true;
                    if (MS_Futures.this.data1done && MS_Futures.this.data2done) {
                        MS_Futures.this.MSFragmentActivity.dataLoaded();
                    }
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void initUI() {
        this.chart_webview = (WebView) this.fragmentView.findViewById(R.id.chart_webview);
        this.chart_webview.getSettings().setJavaScriptEnabled(true);
        this.chart_webview.getSettings().setDisplayZoomControls(false);
        this.chart_webview.getSettings().setBuiltInZoomControls(false);
        this.chart_webview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    MS_Futures.this.MSFragmentActivity.setClickControlCanSwipe(false);
                } else if (motionEvent.getAction() == 1) {
                    MS_Futures.this.MSFragmentActivity.setClickControlCanSwipe(true);
                } else if (motionEvent.getAction() == 2) {
                    MS_Futures.this.MSFragmentActivity.setClickControlCanSwipe(false);
                }
                return false;
            }
        });
        this.listView = (MultiScrollListView) this.fragmentView.findViewById(R.id.listView);
        this.day_nigth_but = (ImageView) this.fragmentView.findViewById(R.id.day_nigth_but);
        this.selectionList1 = (SelectionList) this.fragmentView.findViewById(R.id.selectionList1);
        this.selectionList2 = (SelectionList2line) this.fragmentView.findViewById(R.id.selectionList2);
        this.selectionList1_str = (TextView) this.fragmentView.findViewById(R.id.selectionList1_str);
        this.selectionList2_str = (TextView) this.fragmentView.findViewById(R.id.selectionList2_str);
        this.chart_title = (TextView) this.fragmentView.findViewById(R.id.chart_title);
        this.note_but = (ImageView) this.fragmentView.findViewById(R.id.note_but);
        this.head_bid_box = (RelativeLayout) this.fragmentView.findViewById(R.id.head_bid_box);
        this.head_ask_box = (RelativeLayout) this.fragmentView.findViewById(R.id.head_ask_box);
        this.reload = (ImageView) this.fragmentView.findViewById(R.id.reload);
        if (this.daynight.equals("d")) {
            this.day_nigth_but.setImageResource(R.drawable.day);
            this.isshowdnmsg = false;
        } else {
            this.day_nigth_but.setImageResource(R.drawable.night);
            this.isshowdnmsg = true;
        }
        if (this.aht) {
            this.day_nigth_but.setVisibility(View.VISIBLE);
        } else {
            this.day_nigth_but.setVisibility(View.INVISIBLE);
            this.isshowdnmsg = false;
        }
        getString(R.string.sel_mosttradedcall);
        getString(R.string.sel_mosttradedput);
        getString(R.string.sel_mosttradedoption);
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
        this.day_nigth_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MS_Futures.this.daynight.equals("d")) {
                    MS_Futures.this.day_nigth_but.setImageResource(R.drawable.night);
                    String unused = MS_Futures.this.daynight = "n";
                    boolean unused2 = MS_Futures.this.isshowdnmsg = true;
                } else {
                    MS_Futures.this.day_nigth_but.setImageResource(R.drawable.day);
                    String unused3 = MS_Futures.this.daynight = "d";
                    boolean unused4 = MS_Futures.this.isshowdnmsg = false;
                }
                MS_Futures.this.load_clist(MS_Futures.this.plistdata[MS_Futures.this.plist_index].getPid(), MS_Futures.this.aht, MS_Futures.this.daynight);
            }
        });
        this.note_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent().setClass(MS_Futures.this.getActivity(), WebViewPage2.class);
                intent.putExtra("title", MS_Futures.this.getActivity().getString(R.string.note));
                intent.putExtra("url", MS_Futures.this.getActivity().getString(R.string.webview_futures_note));
                intent.putExtra("daynight", MS_Futures.this.isshowdnmsg);
                MS_Futures.this.getActivity().startActivity(intent);
            }
        });
        this.selectionList1.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                int unused = MS_Futures.this.mlist_index = i;
                MS_Futures.this.load_plist(MS_Futures.this.mlistdata[MS_Futures.this.mlist_index].getMid());
            }
        });
        this.selectionList2.setOnListItemChangeListener(new SelectionList2line.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                if (MS_Futures.this.plistdata[i].getAhtflag().equals("N")) {
                    boolean unused = MS_Futures.this.aht = false;
                    MS_Futures.this.day_nigth_but.setVisibility(View.INVISIBLE);
                    boolean unused2 = MS_Futures.this.isshowdnmsg = false;
                } else {
                    boolean unused3 = MS_Futures.this.aht = true;
                    MS_Futures.this.day_nigth_but.setVisibility(View.VISIBLE);
                }
                int unused4 = MS_Futures.this.plist_index = i;
                MS_Futures.this.load_clist(MS_Futures.this.plistdata[MS_Futures.this.plist_index].getPid(), MS_Futures.this.aht, MS_Futures.this.daynight);
            }
        });
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                int unused = MS_Futures.this.clist_index = i;
                MS_Futures.this.update_title_chart(MS_Futures.this.clist_index);
                MS_Futures.this.loadBannerJSON(MS_Futures.this.clistdata[MS_Futures.this.clist_index].getCid(), MS_Futures.this.daynight);
            }
        });
        this.reload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MS_Futures.this.load_clist(MS_Futures.this.plistdata[MS_Futures.this.plist_index].getPid(), MS_Futures.this.aht, MS_Futures.this.daynight);
            }
        });
    }

    public void loadBannerJSON(String str, String str2) {
        this.data2done = true;
        WebView webView = this.chart_webview;
        webView.loadUrl(getString(R.string.url_futures_chart_webview) + "&type=getChartData&id=" + str + "&daynight=" + str2 + "&mode=phone");
        Log.v(TAG, "chart_webview " + getString(R.string.url_futures_chart_webview) + "&type=getChartData&id=" + str + "&daynight=" + str2 + "&mode=phone");
    }

    public void mlistdataResult(final FuturesList_Result futuresList_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    FuturesList_Result.mainData[] unused = MS_Futures.this.mlistdata = futuresList_Result.getMainData();
                    MS_Futures.this.day_nigth_but.setImageResource(R.drawable.day);
                    String unused2 = MS_Futures.this.daynight = "d";
                    boolean unused3 = MS_Futures.this.aht = false;
                    MS_Futures.this.day_nigth_but.setVisibility(View.INVISIBLE);
                    boolean unused4 = MS_Futures.this.isshowdnmsg = false;
                    String[] unused5 = MS_Futures.this.select_list_array1 = new String[MS_Futures.this.mlistdata.length];
                    for (int i = 0; i < MS_Futures.this.select_list_array1.length; i++) {
                        MS_Futures.this.select_list_array1[i] = Commons.language.equals("en_US") ? MS_Futures.this.mlistdata[i].getName() : MS_Futures.this.mlistdata[i].getNmll();
                    }
                    MS_Futures.this.selectionList1.initItems(MS_Futures.this.select_list_array1, R.string.future, SelectionList.PopTypes.LIST, 0);
                    MS_Futures.this.selectionList1.setselected(0);
                    MS_Futures.this.selectionList1_str.setVisibility(View.GONE);
                    MS_Futures.this.selectionList2_str.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    MS_Futures.this.selectionList1_str.setVisibility(View.VISIBLE);
                    MS_Futures.this.selectionList2_str.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void mlistdataResult2(FuturesList_Result futuresList_Result) {
        this.mlistdata = futuresList_Result.getMainData();
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    MS_Futures.this.day_nigth_but.setImageResource(R.drawable.day);
                    String unused = MS_Futures.this.daynight = "d";
                    boolean unused2 = MS_Futures.this.aht = false;
                    MS_Futures.this.day_nigth_but.setVisibility(View.INVISIBLE);
                    boolean unused3 = MS_Futures.this.isshowdnmsg = false;
                    String[] unused4 = MS_Futures.this.select_list_array1 = new String[MS_Futures.this.mlistdata.length];
                    for (int i = 0; i < MS_Futures.this.select_list_array1.length; i++) {
                        MS_Futures.this.select_list_array1[i] = Commons.language.equals("en_US") ? MS_Futures.this.mlistdata[i].getName() : MS_Futures.this.mlistdata[i].getNmll();
                    }
                    MS_Futures.this.selectionList1.initItems(MS_Futures.this.select_list_array1, R.string.future, SelectionList.PopTypes.LIST, 0);
                    MS_Futures.this.selectionList1_str.setVisibility(View.GONE);
                    MS_Futures.this.selectionList2_str.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    MS_Futures.this.selectionList1_str.setVisibility(View.VISIBLE);
                    MS_Futures.this.selectionList2_str.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.MSFragmentActivity = (MarketSnapshot) getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.futures_page, viewGroup, false);
        initUI();
        if (Commons.futures_mlist == null) {
            load_mlist();
        } else if (Commons.futures_plist == null) {
            mlistdataResult(Commons.futures_mlist);
        } else {
            mlistdataResult2(Commons.futures_mlist);
            plistdataResult(Commons.futures_plist);
        }
        return this.fragmentView;
    }

    public void plistdataResult(FuturesPList_Result futuresPList_Result) {
        this.plistdata = futuresPList_Result.getMainData();
        if (this.mlist_index == 0) {
            Commons.futures_plist = futuresPList_Result;
        }
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    String[] unused = MS_Futures.this.select_list_array2 = new String[MS_Futures.this.plistdata.length];
                    for (int i = 0; i < MS_Futures.this.select_list_array2.length; i++) {
                        MS_Futures.this.select_list_array2[i] = Commons.language.equals("en_US") ? MS_Futures.this.plistdata[i].getName() : MS_Futures.this.plistdata[i].getNmll();
                    }
                    MS_Futures.this.selectionList2.initItems(MS_Futures.this.select_list_array2, R.string.future, SelectionList2line.PopTypes.LIST, 0);
                    MS_Futures.this.selectionList2.setselected(0);
                    MS_Futures.this.selectionList2_str.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    MS_Futures.this.selectionList2_str.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
