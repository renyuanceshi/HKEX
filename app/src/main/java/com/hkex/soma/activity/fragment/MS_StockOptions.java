package com.hkex.soma.activity.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.JSONParser.MSBannerJSONParser;
import com.hkex.soma.JSONParser.MS_StockOptionsJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.MSBannerPage;
import com.hkex.soma.activity.MarketSnapshot;
import com.hkex.soma.activity.OptionDetail;
import com.hkex.soma.activity.Search;
import com.hkex.soma.activity.WebViewPage;
import com.hkex.soma.adapter.MS_IndexOptionsAdapter;
import com.hkex.soma.adapter.MS_StockOptionsAdapter;
import com.hkex.soma.basic.LoaderImageView;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.basic.MultiScrollView;
import com.hkex.soma.dataModel.CurrenyLink_Result;
import com.hkex.soma.dataModel.MSBanner;
import com.hkex.soma.dataModel.MS_IndexOptionsResult;
import com.hkex.soma.dataModel.MS_Options;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;
import java.util.ArrayList;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class MS_StockOptions extends MasterFragment {
    public static final String TAG = "MS_StockOptions";
    /* access modifiers changed from: private */
    public MarketSnapshot MSFragmentActivity;
    private int bannerIndex = 0;
    /* access modifiers changed from: private */
    public Runnable bannerRunnable = null;
    /* access modifiers changed from: private */
    public ArrayList<MSBanner.banner> banners = new ArrayList<>();
    private TextView curreny_opt;
    /* access modifiers changed from: private */
    public String curreny_opt_link = "";
    /* access modifiers changed from: private */
    public String curreny_opt_type = "";
    /* access modifiers changed from: private */
    public boolean data1done = false;
    /* access modifiers changed from: private */
    public boolean data2done = false;
    /* access modifiers changed from: private */
    public View fragmentView;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    private Handler handler2 = new Handler();
    /* access modifiers changed from: private */
    public TextView index_opt_tab;
    private String[] indexcodeTab_array;
    /* access modifiers changed from: private */
    public String[] indexcodeTab_val_array;
    /* access modifiers changed from: private */
    public MS_IndexOptionsResult.mainData[] indexdata;
    /* access modifiers changed from: private */
    public String[] indextop10Tab_array = {"All", "MTCall", "MTPut", "MTGain", "MTLoss"};
    /* access modifiers changed from: private */
    public boolean istickeron = false;
    private String[] items1;
    private String[] items2_stock;
    private String[] items3_index;
    private TextView kill_it_all;
    /* access modifiers changed from: private */
    public MultiScrollListView listView;
    private LinearLayout listViewHeader_box;
    /* access modifiers changed from: private */
    public MSBanner msBanenr;
    /* access modifiers changed from: private */
    public boolean noItemClick = false;
    private Thread runtest = null;
    /* access modifiers changed from: private */
    public MultiScrollView scrollView;
    private SelectionList selectionList2;
    private SelectionList selectionList3;
    /* access modifiers changed from: private */
    public TextView stock_opt_tab;
    /* access modifiers changed from: private */
    public MS_Options.mainData[] stockdata;
    /* access modifiers changed from: private */
    public MS_Options.mainData2[] stockdata2;
    /* access modifiers changed from: private */
    public int stocknindex_indexcodeTab = 0;
    /* access modifiers changed from: private */
    public int stocknindex_indextop10Tab = 0;
    /* access modifiers changed from: private */
    public int stocknindex_stocktop10Tab = 0;
    /* access modifiers changed from: private */
    public int stocknindex_typeTab = 0;
    /* access modifiers changed from: private */
    public String[] stocktop10Tab_array = {"MTC", "MTCall", "MTPut", "MTGain", "MTLoss"};
    /* access modifiers changed from: private */
    public String[] typeTab_array = {"stock", "index"};

    static /* synthetic */ int access$2608(MS_StockOptions mS_StockOptions) {
        int i = mS_StockOptions.bannerIndex;
        mS_StockOptions.bannerIndex = i + 1;
        return i;
    }

    /* access modifiers changed from: private */
    public void initBanner(final MSBanner.banner banner, int i) {
        LoaderImageView loaderImageView = (LoaderImageView) this.fragmentView.findViewById(i);
        loaderImageView.setImageDrawable(getString(R.string.url_domain) + banner.getImg().replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "%20"), ImageView.ScaleType.FIT_XY);
        if (banner.getType() != 1) {
            if (banner.getType() == 2) {
                loaderImageView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint({"NewApi"})
                    public void onClick(View view) {
                        Intent intent = new Intent().setClass(MS_StockOptions.this.MSFragmentActivity, MSBannerPage.class);
                        intent.putExtra("url", MS_StockOptions.this.getString(R.string.url_domain) + "" + banner.getUrl());
                        MS_StockOptions.this.MSFragmentActivity.startActivity(intent);
                    }
                });
            } else if (banner.getType() == 3) {
                loaderImageView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint({"NewApi"})
                    public void onClick(View view) {
                        Intent intent = new Intent().setClass(MS_StockOptions.this.MSFragmentActivity, MSBannerPage.class);
                        intent.putExtra("url", banner.getUrl());
                        MS_StockOptions.this.MSFragmentActivity.startActivity(intent);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void runBanner(boolean z) {
        final ViewFlipper viewFlipper = (ViewFlipper) this.fragmentView.findViewById(R.id.bannerContainer);
        if (this.bannerIndex >= this.banners.size()) {
            this.bannerIndex = 0;
        }
        if (this.banners.size() > 1) {
            this.bannerRunnable = new Runnable() {
                public void run() {
                    if (!MS_StockOptions.this.MSFragmentActivity.leftViewOut && !MS_StockOptions.this.MSFragmentActivity.rightViewOut) {
                        viewFlipper.showNext();
                        MS_StockOptions.access$2608(MS_StockOptions.this);
                    }
                    MS_StockOptions.this.runBanner(false);
                }
            };
            this.handler.postDelayed(this.bannerRunnable, z ? 0 : (long) (Integer.parseInt(this.banners.get(this.bannerIndex).getduration()) * 1000));
        }
    }

    /* access modifiers changed from: private */
    public void set_selectionlist() {
        Log.v("error log", "set_selectionlist");
        float f = getResources().getDisplayMetrics().density;
        if (this.typeTab_array[this.stocknindex_typeTab].equals("stock")) {
            this.listViewHeader_box.setBackgroundResource(R.drawable.list_topheader);
            this.listViewHeader_box.setLayoutParams(new LinearLayout.LayoutParams(-1, Math.round(f * 32.0f)));
            this.selectionList3.setVisibility(View.GONE);
            loadstockJSON(this.stocktop10Tab_array[this.stocknindex_stocktop10Tab]);
            this.selectionList2.initItems(this.items2_stock, R.string.stockoptions, SelectionList.PopTypes.LIST, this.stocknindex_stocktop10Tab);
            updateListHeader();
            this.selectionList2.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
                public void onListItemChanged(int i) {
                    MS_StockOptions mS_StockOptions = MS_StockOptions.this;
                    MS_StockOptions.this.MSFragmentActivity.stocknindex_stocktop10Tab = i;
                    int unused = mS_StockOptions.stocknindex_stocktop10Tab = i;
                    MS_StockOptions.this.updateListHeader();
                    MS_StockOptions.this.loadstockJSON(MS_StockOptions.this.stocktop10Tab_array[MS_StockOptions.this.stocknindex_stocktop10Tab]);
                }
            });
        } else if (this.typeTab_array[this.stocknindex_typeTab].equals("index")) {
            this.listViewHeader_box.setBackgroundResource(R.drawable.bg_landing_dropdown);
            this.listViewHeader_box.setLayoutParams(new LinearLayout.LayoutParams(-1, Math.round(f * 60.0f)));
            this.selectionList3.setVisibility(View.VISIBLE);
            loadindexJSON(this.indextop10Tab_array[this.stocknindex_indextop10Tab], this.indexcodeTab_val_array[this.stocknindex_indexcodeTab]);
            this.selectionList2.initItems(this.indexcodeTab_array, R.string.indexoptions, SelectionList.PopTypes.LIST, this.stocknindex_indexcodeTab);
            this.selectionList2.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
                public void onListItemChanged(int i) {
                    MS_StockOptions mS_StockOptions = MS_StockOptions.this;
                    MS_StockOptions.this.MSFragmentActivity.stocknindex_indexcodeTab = i;
                    int unused = mS_StockOptions.stocknindex_indexcodeTab = i;
                    MS_StockOptions.this.updateListHeader();
                    MS_StockOptions.this.loadindexJSON(MS_StockOptions.this.indextop10Tab_array[MS_StockOptions.this.stocknindex_indextop10Tab], MS_StockOptions.this.indexcodeTab_val_array[MS_StockOptions.this.stocknindex_indexcodeTab]);
                }
            });
            this.selectionList3.initItems(this.items3_index, R.string.indexoptions, SelectionList.PopTypes.LIST, this.stocknindex_indextop10Tab);
            this.selectionList3.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
                public void onListItemChanged(int i) {
                    MS_StockOptions mS_StockOptions = MS_StockOptions.this;
                    MS_StockOptions.this.MSFragmentActivity.stocknindex_indextop10Tab = i;
                    int unused = mS_StockOptions.stocknindex_indextop10Tab = i;
                    MS_StockOptions.this.updateListHeader();
                    MS_StockOptions.this.loadindexJSON(MS_StockOptions.this.indextop10Tab_array[MS_StockOptions.this.stocknindex_indextop10Tab], MS_StockOptions.this.indexcodeTab_val_array[MS_StockOptions.this.stocknindex_indexcodeTab]);
                }
            });
            updateListHeader();
        }
    }

    /* access modifiers changed from: private */
    public void updateListHeader() {
        LinearLayout linearLayout = (LinearLayout) this.fragmentView.findViewById(R.id.listTitleHeader);
        TextView textView = (TextView) this.fragmentView.findViewById(R.id.head_instrument);
        TextView textView2 = (TextView) this.fragmentView.findViewById(R.id.head_lastchange);
        TextView textView3 = (TextView) this.fragmentView.findViewById(R.id.head_oivol);
        if (!this.typeTab_array[this.stocknindex_typeTab].equals("stock")) {
            textView.setText(getString(R.string.tab_option));
            textView2.setText(getString(R.string.head_lastorchange));
            textView3.setText(getString(R.string.volume));
            if (this.stocknindex_indextop10Tab == 1) {
                linearLayout.setBackgroundResource(R.drawable.list_header);
            } else if (this.stocknindex_indextop10Tab == 2) {
                linearLayout.setBackgroundResource(R.drawable.list_header_red);
            } else {
                linearLayout.setBackgroundResource(R.drawable.list_header);
            }
        } else if (this.stocknindex_stocktop10Tab == 1) {
            textView.setText(getString(R.string.head_instrument));
            textView2.setText(getString(R.string.head_lastchange));
            textView3.setText(getString(R.string.head_volume));
            linearLayout.setBackgroundResource(R.drawable.list_header);
        } else if (this.stocknindex_stocktop10Tab == 2) {
            textView.setText(getString(R.string.head_instrument));
            textView2.setText(getString(R.string.head_lastchange));
            textView3.setText(getString(R.string.head_volume));
            linearLayout.setBackgroundResource(R.drawable.list_header_red);
        } else {
            textView.setText(getString(R.string.head_instrument));
            textView2.setText(getString(R.string.head_ulastchange));
            textView3.setText(getString(R.string.head_tvolume));
            linearLayout.setBackgroundResource(R.drawable.list_header);
        }
    }

    public void dataResultBanner(MSBanner mSBanner) {
        this.data2done = true;
        this.msBanenr = mSBanner;
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    ViewFlipper viewFlipper = (ViewFlipper) MS_StockOptions.this.fragmentView.findViewById(R.id.bannerContainer);
                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(MS_StockOptions.this.MSFragmentActivity, R.anim.push_left_in));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(MS_StockOptions.this.MSFragmentActivity, R.anim.push_left_out));
                    viewFlipper.setVisibility(View.VISIBLE);
                    boolean unused = MS_StockOptions.this.noItemClick = false;
                    if (MS_StockOptions.this.msBanenr.getTicker().equals("1")) {
                        MS_StockOptions.this.MSFragmentActivity.setTickerVisibility(0);
                        boolean unused2 = MS_StockOptions.this.istickeron = true;
                        MS_StockOptions.this.scrollView.initMultiScrollView(MS_StockOptions.this.listView, 276.0f, 158.0f);
                    } else {
                        MS_StockOptions.this.MSFragmentActivity.setTickerVisibility(8);
                        boolean unused3 = MS_StockOptions.this.istickeron = false;
                        MS_StockOptions.this.scrollView.initMultiScrollView(MS_StockOptions.this.listView, 244.0f, 158.0f);
                    }
                    if (MS_StockOptions.this.msBanenr.getbanner_1() == null || !MS_StockOptions.this.msBanenr.getbanner_1().getpublish().equals("1")) {
                        viewFlipper.removeView((LinearLayout) MS_StockOptions.this.fragmentView.findViewById(R.id.flipperPage1));
                    } else {
                        MS_StockOptions.this.banners.add(MS_StockOptions.this.msBanenr.getbanner_1());
                        MS_StockOptions.this.initBanner(MS_StockOptions.this.msBanenr.getbanner_1(), R.id.bannerImage1);
                    }
                    if (MS_StockOptions.this.msBanenr.getbanner_2() == null || !MS_StockOptions.this.msBanenr.getbanner_2().getpublish().equals("1")) {
                        viewFlipper.removeView((LinearLayout) MS_StockOptions.this.fragmentView.findViewById(R.id.flipperPage2));
                    } else {
                        MS_StockOptions.this.banners.add(MS_StockOptions.this.msBanenr.getbanner_2());
                        MS_StockOptions.this.initBanner(MS_StockOptions.this.msBanenr.getbanner_2(), R.id.bannerImage2);
                    }
                    if (MS_StockOptions.this.msBanenr.getbanner_3() == null || !MS_StockOptions.this.msBanenr.getbanner_3().getpublish().equals("1")) {
                        viewFlipper.removeView((LinearLayout) MS_StockOptions.this.fragmentView.findViewById(R.id.flipperPage3));
                    } else {
                        MS_StockOptions.this.banners.add(MS_StockOptions.this.msBanenr.getbanner_3());
                        MS_StockOptions.this.initBanner(MS_StockOptions.this.msBanenr.getbanner_3(), R.id.bannerImage3);
                    }
                    if (MS_StockOptions.this.banners.size() <= 1) {
                        ((LinearLayout) MS_StockOptions.this.fragmentView.findViewById(R.id.nextBannerlayout)).setVisibility(View.GONE);
                    }
                    MS_StockOptions.this.runBanner(false);
                    boolean unused4 = MS_StockOptions.this.data2done = true;
                    if (MS_StockOptions.this.data1done && MS_StockOptions.this.data2done) {
                        MS_StockOptions.this.MSFragmentActivity.dataLoaded();
                    }
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void dataResultcurreny_opt(final CurrenyLink_Result currenyLink_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    String unused = MS_StockOptions.this.curreny_opt_type = currenyLink_Result.getType();
                    if (Commons.language.equals("en_US")) {
                        String unused2 = MS_StockOptions.this.curreny_opt_link = currenyLink_Result.getLink_e();
                    } else if (Commons.language.equals("zh_CN")) {
                        String unused3 = MS_StockOptions.this.curreny_opt_link = currenyLink_Result.getLink_gb();
                    } else {
                        String unused4 = MS_StockOptions.this.curreny_opt_link = currenyLink_Result.getLink_c();
                    }
                    if (!MS_StockOptions.this.curreny_opt_type.equals("") && !MS_StockOptions.this.curreny_opt_link.equals("")) {
                        if (MS_StockOptions.this.curreny_opt_type.equals("Inapp")) {
                            Intent intent = new Intent().setClass(MS_StockOptions.this.getActivity(), WebViewPage.class);
                            intent.putExtra("title", MS_StockOptions.this.getString(R.string.curreny_opt));
                            intent.putExtra("url", MS_StockOptions.this.curreny_opt_link);
                            MS_StockOptions.this.startActivity(intent);
                            return;
                        }
                        Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(MS_StockOptions.this.curreny_opt_link));
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        MS_StockOptions.this.startActivity(intent2);
                    }
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void indexdataResult(final MS_IndexOptionsResult mS_IndexOptionsResult) {
        this.data1done = true;
        this.noItemClick = false;
        if (mS_IndexOptionsResult != null) {
            this.indexdata = mS_IndexOptionsResult.getmainData();
        }
        this.handler.post(new Runnable() {
            public void run() {
                String str;
                try {
                    String str2 = mS_IndexOptionsResult == null ? MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR : mS_IndexOptionsResult.getstime();
                    if (MS_StockOptions.this.data1done && MS_StockOptions.this.data2done) {
                        MS_StockOptions.this.MSFragmentActivity.dataLoaded();
                    }
                    ((TextView) MS_StockOptions.this.fragmentView.findViewById(R.id.vol_trad)).setText(" - ");
                    if (mS_IndexOptionsResult == null) {
                        MS_StockOptions.this.listView.setAdapter(new ArrayAdapter(MS_StockOptions.this.MSFragmentActivity, R.layout.list_nodata, MS_StockOptions.this.indextop10Tab_array[MS_StockOptions.this.stocknindex_indextop10Tab] == "MTC" ? new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.nodata_message)} : new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.nooption_message)}));
                        boolean unused = MS_StockOptions.this.noItemClick = true;
                        str = str2;
                    } else if (mS_IndexOptionsResult.getContingency().equals("0")) {
                        MS_StockOptions.this.MSFragmentActivity.ContingencyMessageBox(Commons.language.equals("en_US") ? mS_IndexOptionsResult.getEngmsg() : mS_IndexOptionsResult.getChimsg(), false);
                        MS_StockOptions.this.listView.setAdapter(new ArrayAdapter(MS_StockOptions.this.MSFragmentActivity, R.layout.list_nodata, MS_StockOptions.this.indextop10Tab_array[MS_StockOptions.this.stocknindex_indextop10Tab] == "MTC" ? new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.nodata_message)} : new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.nooption_message)}));
                        boolean unused2 = MS_StockOptions.this.noItemClick = true;
                        str = str2;
                    } else if (mS_IndexOptionsResult.getContingency().equals("-1")) {
                        MS_StockOptions.this.listView.setAdapter(new ArrayAdapter(MS_StockOptions.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.msg_data_after0930)}));
                        str = "msg_data_after0930";
                        boolean unused3 = MS_StockOptions.this.noItemClick = true;
                    } else {
                        MS_StockOptions.this.listView.setAdapter(new MS_IndexOptionsAdapter(MS_StockOptions.this.MSFragmentActivity, R.layout.list_ms_stockoptions, MS_StockOptions.this.indexdata, MS_StockOptions.this.indextop10Tab_array[MS_StockOptions.this.stocknindex_indextop10Tab]));
                        MS_StockOptions.this.listView.setDividerHeight(0);
                        if (MS_StockOptions.this.istickeron) {
                            MS_StockOptions.this.scrollView.initMultiScrollView(MS_StockOptions.this.listView, 304.0f, 158.0f);
                        } else {
                            MS_StockOptions.this.scrollView.initMultiScrollView(MS_StockOptions.this.listView, 271.0f, 158.0f);
                        }
                        ((TextView) MS_StockOptions.this.fragmentView.findViewById(R.id.vol_trad)).setText(mS_IndexOptionsResult.gettraded());
                        str = str2;
                    }
                    MS_StockOptions.this.MSFragmentActivity.updateFooterStime(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initUI() {
        SharedPrefsUtil.putValue((Context) getActivity(), "error_log", "");
        Commons.for_cal_test = 1;
        Commons.context = getActivity();
        Commons.start_test = true;
        this.runtest = new Thread() {
            public void run() {
                try {
                    Commons.start_test = false;
                    Log.v("error log", "error log:" + SharedPrefsUtil.getValue((Context) MS_StockOptions.this.getActivity(), "error_log", ""));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.listView = (MultiScrollListView) this.fragmentView.findViewById(R.id.listView);
        this.scrollView = (MultiScrollView) this.fragmentView.findViewById(2131165570);
        this.selectionList2 = (SelectionList) this.fragmentView.findViewById(R.id.selectionList2);
        this.selectionList3 = (SelectionList) this.fragmentView.findViewById(R.id.selectionList3);
        this.listViewHeader_box = (LinearLayout) this.fragmentView.findViewById(R.id.listViewHeader_box);
        this.items2_stock = new String[5];
        this.items2_stock[0] = getString(R.string.sel_mosttradedclass);
        this.items2_stock[1] = getString(R.string.sel_mosttradedcall);
        this.items2_stock[2] = getString(R.string.sel_mosttradedput);
        this.items2_stock[3] = getString(R.string.top10_gain);
        this.items2_stock[4] = getString(R.string.top10_lose);
        this.items3_index = new String[5];
        this.items3_index[1] = getString(R.string.sel_mosttradedcall);
        this.items3_index[2] = getString(R.string.sel_mosttradedput);
        this.items3_index[0] = getString(R.string.sel_mosttradedoption);
        this.items3_index[3] = getString(R.string.top10_gain);
        this.items3_index[4] = getString(R.string.top10_lose);
        this.indexcodeTab_array = new String[Commons.indexList.getmainData().length];
        this.indexcodeTab_val_array = new String[Commons.indexList.getmainData().length];
        for (int i = 0; i < Commons.indexList.getmainData().length; i++) {
            String[] strArr = this.indexcodeTab_array;
            StringBuilder sb = new StringBuilder();
            sb.append(Commons.indexList.getmainData()[i].getUcode());
            sb.append(" - ");
            sb.append(Commons.language.equals("en_US") ? Commons.indexList.getmainData()[i].getUname() : Commons.indexList.getmainData()[i].getUnmll());
            strArr[i] = sb.toString();
            this.indexcodeTab_val_array[i] = Commons.indexList.getmainData()[i].getUcode();
        }
        this.items1 = new String[2];
        this.items1[0] = getString(R.string.stockoptions);
        this.items1[1] = getString(R.string.indexoptions);
        this.stock_opt_tab = (TextView) this.fragmentView.findViewById(R.id.stock_opt_tab);
        this.index_opt_tab = (TextView) this.fragmentView.findViewById(R.id.index_opt_tab);
        this.curreny_opt = (TextView) this.fragmentView.findViewById(R.id.curreny_opt);
        this.stock_opt_tab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MS_StockOptions.this.stock_opt_tab.setTextColor(MS_StockOptions.this.getResources().getColor(R.color.textwhite));
                MS_StockOptions.this.index_opt_tab.setTextColor(MS_StockOptions.this.getResources().getColor(R.color.bglightlightgrey));
                MS_StockOptions.this.stock_opt_tab.setBackgroundResource(R.drawable.tab_two_on);
                MS_StockOptions.this.index_opt_tab.setBackgroundResource(R.drawable.tab_two_off);
                MS_StockOptions mS_StockOptions = MS_StockOptions.this;
                MS_StockOptions.this.MSFragmentActivity.stocknindex_typeTab = 0;
                int unused = mS_StockOptions.stocknindex_typeTab = 0;
                MS_StockOptions.this.set_selectionlist();
            }
        });
        this.index_opt_tab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MS_StockOptions.this.stock_opt_tab.setTextColor(MS_StockOptions.this.getResources().getColor(R.color.bglightlightgrey));
                MS_StockOptions.this.index_opt_tab.setTextColor(MS_StockOptions.this.getResources().getColor(R.color.textwhite));
                MS_StockOptions.this.stock_opt_tab.setBackgroundResource(R.drawable.tab_two_off);
                MS_StockOptions.this.index_opt_tab.setBackgroundResource(R.drawable.tab_two_on);
                MS_StockOptions mS_StockOptions = MS_StockOptions.this;
                MS_StockOptions.this.MSFragmentActivity.stocknindex_typeTab = 1;
                int unused = mS_StockOptions.stocknindex_typeTab = 1;
                MS_StockOptions.this.set_selectionlist();
            }
        });
        this.curreny_opt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MS_StockOptions.this.loadcurreny_opt();
            }
        });
        if (this.typeTab_array[this.stocknindex_typeTab].equals("stock")) {
            this.stock_opt_tab.setTextColor(getResources().getColor(R.color.textwhite));
            this.index_opt_tab.setTextColor(getResources().getColor(R.color.bglightlightgrey));
            this.stock_opt_tab.setBackgroundResource(R.drawable.tab_two_on);
            this.index_opt_tab.setBackgroundResource(R.drawable.tab_two_off);
        } else if (this.typeTab_array[this.stocknindex_typeTab].equals("index")) {
            this.stock_opt_tab.setTextColor(getResources().getColor(R.color.bglightlightgrey));
            this.index_opt_tab.setTextColor(getResources().getColor(R.color.textwhite));
            this.stock_opt_tab.setBackgroundResource(R.drawable.tab_two_off);
            this.index_opt_tab.setBackgroundResource(R.drawable.tab_two_on);
        }
        set_selectionlist();
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!MS_StockOptions.this.noItemClick) {
                    MS_StockOptions.this.MSFragmentActivity.dataLoading();
                    if (MS_StockOptions.this.typeTab_array[MS_StockOptions.this.stocknindex_typeTab].equals("stock")) {
                        if (MS_StockOptions.this.stocktop10Tab_array[MS_StockOptions.this.stocknindex_stocktop10Tab] == "MTC") {
                            Intent intent = new Intent(MS_StockOptions.this.MSFragmentActivity, Search.class);
                            intent.putExtra("index", "2");
                            intent.putExtra("ucode", MS_StockOptions.this.stockdata[i].getUcode());
                            MS_StockOptions.this.MSFragmentActivity.startActivity(intent);
                            return;
                        }
                        Intent intent2 = new Intent(MS_StockOptions.this.MSFragmentActivity, OptionDetail.class);
                        intent2.putExtra("oid", MS_StockOptions.this.stockdata2[i].getOid());
                        intent2.putExtra("ucode", MS_StockOptions.this.stockdata2[i].getUcode());
                        intent2.putExtra("mdate", MS_StockOptions.this.stockdata2[i].getMdate());
                        intent2.putExtra("wtype", MS_StockOptions.this.stockdata2[i].getType());
                        intent2.putExtra("strike", MS_StockOptions.this.stockdata2[i].getStrike());
                        MS_StockOptions.this.MSFragmentActivity.startActivity(intent2);
                    } else if (MS_StockOptions.this.typeTab_array[MS_StockOptions.this.stocknindex_typeTab].equals("index")) {
                        MS_StockOptions.this.MSFragmentActivity.dataLoading();
                        Intent intent3 = new Intent(MS_StockOptions.this.MSFragmentActivity, OptionDetail.class);
                        intent3.putExtra("oid", MS_StockOptions.this.indexdata[i].getOid());
                        intent3.putExtra("ucode", MS_StockOptions.this.indexdata[i].getUcode());
                        intent3.putExtra("mdate", MS_StockOptions.this.indexdata[i].getMdate());
                        intent3.putExtra("wtype", MS_StockOptions.this.indexdata[i].getType());
                        intent3.putExtra("strike", MS_StockOptions.this.indexdata[i].getStrike());
                        MS_StockOptions.this.MSFragmentActivity.startActivity(intent3);
                    }
                }
            }
        });
        this.listView.setEmptyView(this.fragmentView.findViewById(R.id.simaple_list_text));
        ((LinearLayout) this.fragmentView.findViewById(R.id.nextBannerlayout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MS_StockOptions.this.bannerRunnable != null) {
                    MS_StockOptions.this.handler.removeCallbacks(MS_StockOptions.this.bannerRunnable);
                }
                MS_StockOptions.this.runBanner(true);
            }
        });
    }

    public void loadBannerJSON() {
        this.data2done = false;
        MSBannerJSONParser mSBannerJSONParser = new MSBannerJSONParser();
        mSBannerJSONParser.setOnJSONCompletedListener(new MSBannerJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(MSBanner mSBanner) {
                MS_StockOptions.this.dataResultBanner(mSBanner);
            }
        });
        mSBannerJSONParser.setOnJSONFailedListener(new MSBannerJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_StockOptions.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
            }
        });
        mSBannerJSONParser.loadXML(getString(R.string.url_msbanner));
        this.MSFragmentActivity.dataLoading();
    }

    public void loadcurreny_opt() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(CurrenyLink_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<CurrenyLink_Result>() {
            public void OnJSONCompleted(CurrenyLink_Result currenyLink_Result) {
                Commons.LogDebug("", currenyLink_Result.toString());
                MS_StockOptions.this.dataResultcurreny_opt(currenyLink_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_StockOptions.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
                MS_StockOptions.this.dataResultcurreny_opt((CurrenyLink_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_curreny_opt));
        this.MSFragmentActivity.dataLoading();
    }

    public void loadindexJSON(String str, String str2) {
        this.data1done = false;
        GenericJSONParser genericJSONParser = new GenericJSONParser(MS_IndexOptionsResult.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<MS_IndexOptionsResult>() {
            public void OnJSONCompleted(MS_IndexOptionsResult mS_IndexOptionsResult) {
                Commons.LogDebug("", mS_IndexOptionsResult.toString());
                MS_StockOptions.this.indexdataResult(mS_IndexOptionsResult);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_StockOptions.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
                MS_StockOptions.this.indexdataResult((MS_IndexOptionsResult) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_index_options) + "?type=" + str + "&ucode=" + str2);
        this.MSFragmentActivity.dataLoading();
        switch (this.stocknindex_indextop10Tab) {
            case 0:
                ((TextView) this.fragmentView.findViewById(R.id.vol_trad_str)).setText(String.format(getString(R.string.stock_total_vol), new Object[]{str2}));
                return;
            case 1:
                ((TextView) this.fragmentView.findViewById(R.id.vol_trad_str)).setText(String.format(getString(R.string.stock_total_vol_call), new Object[]{str2}));
                return;
            case 2:
                ((TextView) this.fragmentView.findViewById(R.id.vol_trad_str)).setText(String.format(getString(R.string.stock_total_vol_put), new Object[]{str2}));
                return;
            default:
                ((TextView) this.fragmentView.findViewById(R.id.vol_trad_str)).setText(String.format(getString(R.string.stock_total_vol), new Object[]{str2}));
                return;
        }
    }

    public void loadstockJSON(String str) {
        this.data1done = false;
        MS_StockOptionsJSONParser mS_StockOptionsJSONParser = new MS_StockOptionsJSONParser();
        mS_StockOptionsJSONParser.setOnJSONCompletedListener(new MS_StockOptionsJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(MS_Options mS_Options) {
                Commons.LogDebug("", mS_Options.toString());
                MS_StockOptions.this.stockdataResult(mS_Options);
            }
        });
        mS_StockOptionsJSONParser.setOnJSONFailedListener(new MS_StockOptionsJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_StockOptions.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
                MS_StockOptions.this.stockdataResult((MS_Options) null);
            }
        });
        mS_StockOptionsJSONParser.loadXML(getString(R.string.url_market_options) + "?type=" + str);
        this.MSFragmentActivity.dataLoading();
        switch (this.stocknindex_stocktop10Tab) {
            case 0:
                ((TextView) this.fragmentView.findViewById(R.id.vol_trad_str)).setText(String.format(getString(R.string.stock_total_vol), new Object[]{getString(R.string.tab_stock)}));
                return;
            case 1:
                ((TextView) this.fragmentView.findViewById(R.id.vol_trad_str)).setText(String.format(getString(R.string.stock_total_vol_call), new Object[]{getString(R.string.tab_stock)}));
                return;
            case 2:
                ((TextView) this.fragmentView.findViewById(R.id.vol_trad_str)).setText(String.format(getString(R.string.stock_total_vol_put), new Object[]{getString(R.string.tab_stock)}));
                return;
            default:
                ((TextView) this.fragmentView.findViewById(R.id.vol_trad_str)).setText(String.format(getString(R.string.stock_total_vol), new Object[]{getString(R.string.tab_stock)}));
                return;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.MSFragmentActivity = (MarketSnapshot) getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.msstockoptions, viewGroup, false);
        this.stocknindex_typeTab = this.MSFragmentActivity.stocknindex_typeTab;
        this.stocknindex_stocktop10Tab = this.MSFragmentActivity.stocknindex_stocktop10Tab;
        this.stocknindex_indexcodeTab = this.MSFragmentActivity.stocknindex_indexcodeTab;
        this.stocknindex_indextop10Tab = this.MSFragmentActivity.stocknindex_indextop10Tab;
        initUI();
        loadBannerJSON();
        return this.fragmentView;
    }

    public void stockdataResult(final MS_Options mS_Options) {
        this.data1done = true;
        this.noItemClick = false;
        if (mS_Options != null) {
            this.stockdata = mS_Options.getmainData();
            this.stockdata2 = mS_Options.getmainData2();
        }
        this.handler.post(new Runnable() {
            public void run() {
                String str;
                try {
                    String str2 = mS_Options == null ? MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR : mS_Options.getstime();
                    if (MS_StockOptions.this.data1done && MS_StockOptions.this.data2done) {
                        MS_StockOptions.this.MSFragmentActivity.dataLoaded();
                    }
                    ((TextView) MS_StockOptions.this.fragmentView.findViewById(R.id.vol_trad)).setText(" - ");
                    if (mS_Options == null) {
                        MS_StockOptions.this.listView.setAdapter(new ArrayAdapter(MS_StockOptions.this.MSFragmentActivity, R.layout.list_nodata, MS_StockOptions.this.stocktop10Tab_array[MS_StockOptions.this.stocknindex_stocktop10Tab] == "MTC" ? new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.nodata_message)} : new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.nooption_message)}));
                        boolean unused = MS_StockOptions.this.noItemClick = true;
                        str = str2;
                    } else if (MS_StockOptions.this.stocktop10Tab_array[MS_StockOptions.this.stocknindex_stocktop10Tab] == "MTC") {
                        if (MS_StockOptions.this.stockdata == null || MS_StockOptions.this.stockdata.length == 0) {
                            MS_StockOptions.this.listView.setAdapter(new ArrayAdapter(MS_StockOptions.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.nodata_message)}));
                            boolean unused2 = MS_StockOptions.this.noItemClick = true;
                            str = str2;
                        } else if (mS_Options.getContingency().equals("0")) {
                            MS_StockOptions.this.MSFragmentActivity.ContingencyMessageBox(Commons.language.equals("en_US") ? mS_Options.getEngmsg() : mS_Options.getChimsg(), false);
                            MS_StockOptions.this.listView.setAdapter(new ArrayAdapter(MS_StockOptions.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.nodata_message)}));
                            boolean unused3 = MS_StockOptions.this.noItemClick = true;
                            str = str2;
                        } else if (mS_Options.getContingency().equals("-1")) {
                            MS_StockOptions.this.listView.setAdapter(new ArrayAdapter(MS_StockOptions.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.nodatayet)}));
                            str = "nodatayet";
                            boolean unused4 = MS_StockOptions.this.noItemClick = true;
                        } else {
                            MS_StockOptions.this.listView.setAdapter(new MS_StockOptionsAdapter((Context) MS_StockOptions.this.MSFragmentActivity, (int) R.layout.list_ms_stockoptions, MS_StockOptions.this.stockdata, MS_StockOptions.this.stocktop10Tab_array[MS_StockOptions.this.stocknindex_stocktop10Tab]));
                            MS_StockOptions.this.listView.setDividerHeight(0);
                            if (MS_StockOptions.this.istickeron) {
                                MS_StockOptions.this.scrollView.initMultiScrollView(MS_StockOptions.this.listView, 276.0f, 158.0f);
                            } else {
                                MS_StockOptions.this.scrollView.initMultiScrollView(MS_StockOptions.this.listView, 244.0f, 158.0f);
                            }
                            ((TextView) MS_StockOptions.this.fragmentView.findViewById(R.id.vol_trad)).setText(mS_Options.gettraded());
                            str = str2;
                        }
                    } else if (MS_StockOptions.this.stockdata2 == null || MS_StockOptions.this.stockdata2.length == 0) {
                        MS_StockOptions.this.listView.setAdapter(new ArrayAdapter(MS_StockOptions.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.nooption_message)}));
                        boolean unused5 = MS_StockOptions.this.noItemClick = true;
                        str = str2;
                    } else if (mS_Options.getContingency().equals("0")) {
                        MS_StockOptions.this.MSFragmentActivity.ContingencyMessageBox(Commons.language.equals("en_US") ? mS_Options.getEngmsg() : mS_Options.getChimsg(), false);
                        MS_StockOptions.this.listView.setAdapter(new ArrayAdapter(MS_StockOptions.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.nooption_message)}));
                        boolean unused6 = MS_StockOptions.this.noItemClick = true;
                        str = str2;
                    } else if (mS_Options.getContingency().equals("-1")) {
                        MS_StockOptions.this.listView.setAdapter(new ArrayAdapter(MS_StockOptions.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_StockOptions.this.MSFragmentActivity.getString(R.string.nodatayet)}));
                        str = "nodatayet";
                        boolean unused7 = MS_StockOptions.this.noItemClick = true;
                    } else {
                        MS_StockOptions.this.listView.setAdapter(new MS_StockOptionsAdapter((Context) MS_StockOptions.this.MSFragmentActivity, (int) R.layout.list_ms_stockoptions, MS_StockOptions.this.stockdata2, MS_StockOptions.this.stocktop10Tab_array[MS_StockOptions.this.stocknindex_stocktop10Tab]));
                        MS_StockOptions.this.listView.setDividerHeight(0);
                        if (MS_StockOptions.this.istickeron) {
                            MS_StockOptions.this.scrollView.initMultiScrollView(MS_StockOptions.this.listView, 276.0f, 158.0f);
                        } else {
                            MS_StockOptions.this.scrollView.initMultiScrollView(MS_StockOptions.this.listView, 244.0f, 158.0f);
                        }
                        ((TextView) MS_StockOptions.this.fragmentView.findViewById(R.id.vol_trad)).setText(mS_Options.gettraded());
                        str = str2;
                    }
                    MS_StockOptions.this.MSFragmentActivity.updateFooterStime(str);
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }
}
