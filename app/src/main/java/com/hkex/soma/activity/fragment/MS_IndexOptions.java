package com.hkex.soma.activity.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.hkex.soma.R;
import com.hkex.soma.activity.MSBannerPage;
import com.hkex.soma.activity.MarketSnapshot;
import com.hkex.soma.activity.OptionDetail;
import com.hkex.soma.adapter.MS_IndexOptionsAdapter;
import com.hkex.soma.basic.LoaderImageView;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.basic.MultiScrollView;
import com.hkex.soma.dataModel.MSBanner;
import com.hkex.soma.dataModel.MS_IndexOptionsResult;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;
import java.util.ArrayList;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class MS_IndexOptions extends MasterFragment {
    public static final String TAG = "MS_IndexOptions";
    /* access modifiers changed from: private */
    public MarketSnapshot MSFragmentActivity;
    private int bannerIndex = 0;
    /* access modifiers changed from: private */
    public Runnable bannerRunnable = null;
    /* access modifiers changed from: private */
    public ArrayList<MSBanner.banner> banners = new ArrayList<>();
    /* access modifiers changed from: private */
    public MS_IndexOptionsResult.mainData[] data;
    /* access modifiers changed from: private */
    public boolean data1done = false;
    /* access modifiers changed from: private */
    public boolean data2done = false;
    /* access modifiers changed from: private */
    public View fragmentView;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    private String[] index_items;
    /* access modifiers changed from: private */
    public String[] index_items_val;
    /* access modifiers changed from: private */
    public boolean istickeron = false;
    /* access modifiers changed from: private */
    public MultiScrollListView listView;
    /* access modifiers changed from: private */
    public MSBanner msBanenr;
    /* access modifiers changed from: private */
    public boolean noItemClick = false;
    /* access modifiers changed from: private */
    public MultiScrollView scrollView;
    /* access modifiers changed from: private */
    public int tagIndex = 0;
    /* access modifiers changed from: private */
    public String[] type_array = {"All", "MTCall", "MTPut"};
    /* access modifiers changed from: private */
    public int ucodeIndex = 0;

    static /* synthetic */ int access$1708(MS_IndexOptions mS_IndexOptions) {
        int i = mS_IndexOptions.bannerIndex;
        mS_IndexOptions.bannerIndex = i + 1;
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
                        Intent intent = new Intent().setClass(MS_IndexOptions.this.MSFragmentActivity, MSBannerPage.class);
                        intent.putExtra("url", MS_IndexOptions.this.getString(R.string.url_domain) + "" + banner.getUrl());
                        MS_IndexOptions.this.MSFragmentActivity.startActivity(intent);
                    }
                });
            } else if (banner.getType() == 3) {
                loaderImageView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint({"NewApi"})
                    public void onClick(View view) {
                        Intent intent = new Intent().setClass(MS_IndexOptions.this.MSFragmentActivity, MSBannerPage.class);
                        intent.putExtra("url", banner.getUrl());
                        MS_IndexOptions.this.MSFragmentActivity.startActivity(intent);
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
                    if (!MS_IndexOptions.this.MSFragmentActivity.leftViewOut && !MS_IndexOptions.this.MSFragmentActivity.rightViewOut) {
                        viewFlipper.showNext();
                        MS_IndexOptions.access$1708(MS_IndexOptions.this);
                    }
                    MS_IndexOptions.this.runBanner(false);
                }
            };
            this.handler.postDelayed(this.bannerRunnable, z ? 0 : (long) (Integer.parseInt(this.banners.get(this.bannerIndex).getduration()) * 1000));
        }
    }

    /* access modifiers changed from: private */
    public void updateListHeader() {
        LinearLayout linearLayout = (LinearLayout) this.fragmentView.findViewById(R.id.listTitleHeader);
        if (this.tagIndex == 2) {
            linearLayout.setBackgroundResource(R.drawable.list_header_red);
        } else {
            linearLayout.setBackgroundResource(R.drawable.list_header);
        }
    }

    public void dataResult(final MS_IndexOptionsResult mS_IndexOptionsResult) {
        this.data1done = true;
        if (mS_IndexOptionsResult != null) {
            this.data = mS_IndexOptionsResult.getmainData();
        }
        this.handler.post(new Runnable() {
            public void run() {
                String str;
                try {
                    String str2 = mS_IndexOptionsResult == null ? MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR : mS_IndexOptionsResult.getstime();
                    if (MS_IndexOptions.this.data1done && MS_IndexOptions.this.data2done) {
                        MS_IndexOptions.this.MSFragmentActivity.dataLoaded();
                    }
                    ((TextView) MS_IndexOptions.this.fragmentView.findViewById(R.id.vol_trad)).setText(" - ");
                    if (mS_IndexOptionsResult == null) {
                        MS_IndexOptions.this.listView.setAdapter(new ArrayAdapter(MS_IndexOptions.this.MSFragmentActivity, R.layout.list_nodata, MS_IndexOptions.this.type_array[MS_IndexOptions.this.tagIndex] == "MTC" ? new String[]{MS_IndexOptions.this.MSFragmentActivity.getString(R.string.nodata_message)} : new String[]{MS_IndexOptions.this.MSFragmentActivity.getString(R.string.nooption_message)}));
                        boolean unused = MS_IndexOptions.this.noItemClick = true;
                        str = str2;
                    } else if (mS_IndexOptionsResult.getContingency().equals("0")) {
                        MS_IndexOptions.this.MSFragmentActivity.ContingencyMessageBox(Commons.language.equals("en_US") ? mS_IndexOptionsResult.getEngmsg() : mS_IndexOptionsResult.getChimsg(), false);
                        MS_IndexOptions.this.listView.setAdapter(new ArrayAdapter(MS_IndexOptions.this.MSFragmentActivity, R.layout.list_nodata, MS_IndexOptions.this.type_array[MS_IndexOptions.this.tagIndex] == "MTC" ? new String[]{MS_IndexOptions.this.MSFragmentActivity.getString(R.string.nodata_message)} : new String[]{MS_IndexOptions.this.MSFragmentActivity.getString(R.string.nooption_message)}));
                        boolean unused2 = MS_IndexOptions.this.noItemClick = true;
                        str = str2;
                    } else if (mS_IndexOptionsResult.getContingency().equals("-1")) {
                        MS_IndexOptions.this.listView.setAdapter(new ArrayAdapter(MS_IndexOptions.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_IndexOptions.this.MSFragmentActivity.getString(R.string.msg_data_after0930)}));
                        str = "msg_data_after0930";
                        boolean unused3 = MS_IndexOptions.this.noItemClick = true;
                    } else {
                        MS_IndexOptions.this.listView.setAdapter(new MS_IndexOptionsAdapter(MS_IndexOptions.this.MSFragmentActivity, R.layout.list_ms_stockoptions, MS_IndexOptions.this.data, MS_IndexOptions.this.type_array[MS_IndexOptions.this.tagIndex]));
                        MS_IndexOptions.this.listView.setDividerHeight(0);
                        if (MS_IndexOptions.this.istickeron) {
                            MS_IndexOptions.this.scrollView.initMultiScrollView(MS_IndexOptions.this.listView, 274.0f, 158.0f);
                        } else {
                            MS_IndexOptions.this.scrollView.initMultiScrollView(MS_IndexOptions.this.listView, 241.0f, 158.0f);
                        }
                        ((TextView) MS_IndexOptions.this.fragmentView.findViewById(R.id.vol_trad)).setText(mS_IndexOptionsResult.gettraded());
                        str = str2;
                    }
                    MS_IndexOptions.this.MSFragmentActivity.updateFooterStime(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void dataResultBanner(MSBanner mSBanner) {
        this.data2done = true;
        this.msBanenr = mSBanner;
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    ViewFlipper viewFlipper = (ViewFlipper) MS_IndexOptions.this.fragmentView.findViewById(R.id.bannerContainer);
                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(MS_IndexOptions.this.MSFragmentActivity, R.anim.push_left_in));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(MS_IndexOptions.this.MSFragmentActivity, R.anim.push_left_out));
                    viewFlipper.setVisibility(View.VISIBLE);
                    boolean unused = MS_IndexOptions.this.noItemClick = false;
                    if (MS_IndexOptions.this.msBanenr.getTicker().equals("1")) {
                        MS_IndexOptions.this.MSFragmentActivity.setTickerVisibility(0);
                        boolean unused2 = MS_IndexOptions.this.istickeron = true;
                        MS_IndexOptions.this.scrollView.initMultiScrollView(MS_IndexOptions.this.listView, 274.0f, 158.0f);
                    } else {
                        MS_IndexOptions.this.MSFragmentActivity.setTickerVisibility(8);
                        boolean unused3 = MS_IndexOptions.this.istickeron = false;
                        MS_IndexOptions.this.scrollView.initMultiScrollView(MS_IndexOptions.this.listView, 241.0f, 158.0f);
                    }
                    if (MS_IndexOptions.this.msBanenr.getbanner_1() == null || !MS_IndexOptions.this.msBanenr.getbanner_1().getpublish().equals("1")) {
                        viewFlipper.removeView((LinearLayout) MS_IndexOptions.this.fragmentView.findViewById(R.id.flipperPage1));
                    } else {
                        MS_IndexOptions.this.banners.add(MS_IndexOptions.this.msBanenr.getbanner_1());
                        MS_IndexOptions.this.initBanner(MS_IndexOptions.this.msBanenr.getbanner_1(), R.id.bannerImage1);
                    }
                    if (MS_IndexOptions.this.msBanenr.getbanner_2() == null || !MS_IndexOptions.this.msBanenr.getbanner_2().getpublish().equals("1")) {
                        viewFlipper.removeView((LinearLayout) MS_IndexOptions.this.fragmentView.findViewById(R.id.flipperPage2));
                    } else {
                        MS_IndexOptions.this.banners.add(MS_IndexOptions.this.msBanenr.getbanner_2());
                        MS_IndexOptions.this.initBanner(MS_IndexOptions.this.msBanenr.getbanner_2(), R.id.bannerImage2);
                    }
                    if (MS_IndexOptions.this.msBanenr.getbanner_3() == null || !MS_IndexOptions.this.msBanenr.getbanner_3().getpublish().equals("1")) {
                        viewFlipper.removeView((LinearLayout) MS_IndexOptions.this.fragmentView.findViewById(R.id.flipperPage3));
                    } else {
                        MS_IndexOptions.this.banners.add(MS_IndexOptions.this.msBanenr.getbanner_3());
                        MS_IndexOptions.this.initBanner(MS_IndexOptions.this.msBanenr.getbanner_3(), R.id.bannerImage3);
                    }
                    if (MS_IndexOptions.this.banners.size() <= 1) {
                        ((LinearLayout) MS_IndexOptions.this.fragmentView.findViewById(R.id.nextBannerlayout)).setVisibility(View.GONE);
                    }
                    MS_IndexOptions.this.runBanner(false);
                    boolean unused4 = MS_IndexOptions.this.data2done = true;
                    if (MS_IndexOptions.this.data1done && MS_IndexOptions.this.data2done) {
                        MS_IndexOptions.this.MSFragmentActivity.dataLoaded();
                    }
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void initUI() {
        this.listView = (MultiScrollListView) this.fragmentView.findViewById(R.id.listView);
        this.scrollView = (MultiScrollView) this.fragmentView.findViewById(2131165570);
        SelectionList selectionList = (SelectionList) this.fragmentView.findViewById(R.id.selectionList);
        SelectionList selectionList2 = (SelectionList) this.fragmentView.findViewById(R.id.index_selectionList);
        String string = getString(R.string.sel_mosttradedcall);
        String string2 = getString(R.string.sel_mosttradedput);
        String string3 = getString(R.string.sel_mosttradedoption);
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
        selectionList.initItems(new String[]{string3, string, string2}, R.string.indexoptions, SelectionList.PopTypes.LIST, this.tagIndex);
        updateListHeader();
        selectionList.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                MS_IndexOptions mS_IndexOptions = MS_IndexOptions.this;
                MS_IndexOptions.this.MSFragmentActivity.index_stockOptionTab = i;
                int unused = mS_IndexOptions.tagIndex = i;
                MS_IndexOptions.this.updateListHeader();
                MS_IndexOptions.this.loadJSON(MS_IndexOptions.this.type_array[i], MS_IndexOptions.this.index_items_val[MS_IndexOptions.this.ucodeIndex]);
            }
        });
        selectionList2.initItems(this.index_items, R.string.indexoptions, SelectionList.PopTypes.LIST, this.ucodeIndex);
        selectionList2.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                MS_IndexOptions mS_IndexOptions = MS_IndexOptions.this;
                MS_IndexOptions.this.MSFragmentActivity.indexOptionTab = i;
                int unused = mS_IndexOptions.ucodeIndex = i;
                MS_IndexOptions.this.updateListHeader();
                MS_IndexOptions.this.loadJSON(MS_IndexOptions.this.type_array[MS_IndexOptions.this.tagIndex], MS_IndexOptions.this.index_items_val[i]);
            }
        });
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!MS_IndexOptions.this.noItemClick) {
                    MS_IndexOptions.this.MSFragmentActivity.dataLoading();
                    Intent intent = new Intent(MS_IndexOptions.this.MSFragmentActivity, OptionDetail.class);
                    intent.putExtra("oid", MS_IndexOptions.this.data[i].getOid());
                    intent.putExtra("ucode", MS_IndexOptions.this.data[i].getUcode());
                    intent.putExtra("mdate", MS_IndexOptions.this.data[i].getMdate());
                    intent.putExtra("wtype", MS_IndexOptions.this.data[i].getType());
                    intent.putExtra("strike", MS_IndexOptions.this.data[i].getStrike());
                    MS_IndexOptions.this.MSFragmentActivity.startActivity(intent);
                }
            }
        });
        ((LinearLayout) this.fragmentView.findViewById(R.id.nextBannerlayout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MS_IndexOptions.this.bannerRunnable != null) {
                    MS_IndexOptions.this.handler.removeCallbacks(MS_IndexOptions.this.bannerRunnable);
                }
                MS_IndexOptions.this.runBanner(true);
            }
        });
    }

    public void loadBannerJSON() {
        this.data2done = false;
        MSBannerJSONParser mSBannerJSONParser = new MSBannerJSONParser();
        mSBannerJSONParser.setOnJSONCompletedListener(new MSBannerJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(MSBanner mSBanner) {
                MS_IndexOptions.this.dataResultBanner(mSBanner);
            }
        });
        mSBannerJSONParser.setOnJSONFailedListener(new MSBannerJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_IndexOptions.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
            }
        });
        mSBannerJSONParser.loadXML(getString(R.string.url_msbanner));
        this.MSFragmentActivity.dataLoading();
    }

    public void loadJSON(String str, String str2) {
        this.data1done = false;
        GenericJSONParser genericJSONParser = new GenericJSONParser(MS_IndexOptionsResult.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<MS_IndexOptionsResult>() {
            public void OnJSONCompleted(MS_IndexOptionsResult mS_IndexOptionsResult) {
                Commons.LogDebug("", mS_IndexOptionsResult.toString());
                MS_IndexOptions.this.dataResult(mS_IndexOptionsResult);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_IndexOptions.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
                MS_IndexOptions.this.dataResult((MS_IndexOptionsResult) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_index_options) + "?type=" + str + "&ucode=" + str2);
        this.MSFragmentActivity.dataLoading();
        switch (this.tagIndex) {
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
                return;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.MSFragmentActivity = (MarketSnapshot) getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.msindexoptions, viewGroup, false);
        this.tagIndex = this.MSFragmentActivity.index_stockOptionTab;
        this.ucodeIndex = this.MSFragmentActivity.indexOptionTab;
        initUI();
        loadJSON(this.type_array[this.tagIndex], this.index_items_val[this.ucodeIndex]);
        loadBannerJSON();
        return this.fragmentView;
    }
}
