package com.hkex.soma.activity.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.JSONParser.MSBannerJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.MSBannerPage;
import com.hkex.soma.activity.MarketSnapshot;
import com.hkex.soma.activity.OptionDetail;
import com.hkex.soma.basic.LoaderImageView;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.dataModel.MSBanner;
import com.hkex.soma.dataModel.MS_Options;
import com.hkex.soma.dataModel.OverviewTop_Result;
import com.hkex.soma.dataModel.Overview_Result;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MS_Overview extends MasterFragment {
    public static final String TAG = "MS_Overview";
    /* access modifiers changed from: private */
    public MarketSnapshot MSFragmentActivity;
    private int bannerIndex = 0;
    /* access modifiers changed from: private */
    public Runnable bannerRunnable = null;
    /* access modifiers changed from: private */
    public ArrayList<MSBanner.banner> banners = new ArrayList<>();
    private ImageView btn_note;
    private WebView chart_webview;
    /* access modifiers changed from: private */
    public LinearLayout datalist1;
    /* access modifiers changed from: private */
    public LinearLayout datalist2;
    private SimpleDateFormat date_formatter;
    /* access modifiers changed from: private */
    public ImageView day_nigth_but;
    /* access modifiers changed from: private */
    public String daynight = "d";
    private SimpleDateFormat formatter;
    /* access modifiers changed from: private */
    public View fragmentView;
    /* access modifiers changed from: private */
    public RelativeLayout futurestime_box;
    /* access modifiers changed from: private */
    public TextView futurestime_tv_delay;
    /* access modifiers changed from: private */
    public TextView futurestime_tv_stime;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    private Handler handler2 = new Handler();
    /* access modifiers changed from: private */
    public Overview_Result.mainData[] indexdata;
    private boolean istickeron = false;
    private LinearLayout listViewHeader_box;
    /* access modifiers changed from: private */
    public LinearLayout listbox1;
    /* access modifiers changed from: private */
    public LinearLayout listbox2;
    /* access modifiers changed from: private */
    public MSBanner msBanenr;
    /* access modifiers changed from: private */
    public boolean noItemClick = false;
    private RelativeLayout optionstime_box;
    private TextView optionstime_tv_delay;
    private TextView optionstime_tv_stime;
    private TextView optionstime_tv_update;
    /* access modifiers changed from: private */
    public LinearLayout price_info_box;
    /* access modifiers changed from: private */
    public LinearLayout product_keylist;
    private MS_Options.mainData[] stockdata;
    private MS_Options.mainData2[] stockdata2;

    static /* synthetic */ int access$608(MS_Overview mS_Overview) {
        int i = mS_Overview.bannerIndex;
        mS_Overview.bannerIndex = i + 1;
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
                        Intent intent = new Intent().setClass(MS_Overview.this.MSFragmentActivity, MSBannerPage.class);
                        intent.putExtra("url", MS_Overview.this.getString(R.string.url_domain) + "" + banner.getUrl());
                        MS_Overview.this.MSFragmentActivity.startActivity(intent);
                    }
                });
            } else if (banner.getType() == 3) {
                loaderImageView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint({"NewApi"})
                    public void onClick(View view) {
                        Intent intent = new Intent().setClass(MS_Overview.this.MSFragmentActivity, MSBannerPage.class);
                        intent.putExtra("url", banner.getUrl());
                        MS_Overview.this.MSFragmentActivity.startActivity(intent);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void init_list1(Overview_Result overview_Result) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (overview_Result.getContingency().equals("0")) {
            this.MSFragmentActivity.ContingencyMessageBox(Commons.language.equals("en_US") ? overview_Result.getEngmsg() : overview_Result.getChimsg(), false);
            View inflate = layoutInflater.inflate(R.layout.list_nodata, this.datalist1, false);
            ((TextView) inflate.findViewById(R.id.simaple_list_text)).setText(getString(R.string.nooption_message));
            this.datalist1.addView(inflate);
            if (!overview_Result.getOptionstime().equals("")) {
                this.optionstime_tv_stime.setText(overview_Result.getOptionstime());
                this.optionstime_tv_delay.setVisibility(View.VISIBLE);
            } else if (overview_Result.getOptionstime().equals(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                this.optionstime_tv_update.setVisibility(View.VISIBLE);
                this.optionstime_tv_stime.setText(R.string.no_data);
                this.optionstime_tv_delay.setVisibility(View.GONE);
            } else if (overview_Result.getOptionstime().equals("")) {
                this.optionstime_box.setVisibility(View.GONE);
            }
        } else if (overview_Result.getContingency().equals("-1")) {
            View inflate2 = layoutInflater.inflate(R.layout.list_nodata, this.datalist1, false);
            ((TextView) inflate2.findViewById(R.id.simaple_list_text)).setText(getString(R.string.nodatayet));
            this.datalist1.addView(inflate2);
            this.optionstime_tv_update.setVisibility(View.GONE);
            this.optionstime_tv_stime.setText(R.string.msg_data_after0945);
            this.optionstime_tv_delay.setVisibility(View.GONE);
        } else {
            this.indexdata = overview_Result.getMainData();
            for (int i = 0; i < overview_Result.getMainData().length; i++) {
                View inflate3 = layoutInflater.inflate(R.layout.list_ms_overview_options, this.datalist1, false);
                try {
                    if (Double.parseDouble(overview_Result.getMainData()[i].getPchng().replace("%", "")) >= 0.0d) {
                        ((TextView) inflate3.findViewById(R.id.uchange)).setTextColor(getResources().getColor(R.color.change_green));
                    } else {
                        ((TextView) inflate3.findViewById(R.id.uchange)).setTextColor(getResources().getColor(R.color.change_red));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ((TextView) inflate3.findViewById(R.id.uchange)).setTextColor(getResources().getColor(R.color.textgrey));
                }
                try {
                    ((TextView) inflate3.findViewById(R.id.name)).setText(Commons.language.equals("en_US") ? overview_Result.getMainData()[i].getUname() : overview_Result.getMainData()[i].getUnmll());
                    TextView textView = (TextView) inflate3.findViewById(R.id.last);
                    StringBuilder sb = new StringBuilder();
                    sb.append(overview_Result.getMainData()[i].getStrike());
                    sb.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    sb.append(overview_Result.getMainData()[i].getType().equals("P") ? getString(R.string.put) : getString(R.string.call));
                    textView.setText(sb.toString());
                    ((TextView) inflate3.findViewById(R.id.ulast)).setText(overview_Result.getMainData()[i].getLast() + " /");
                    TextView textView2 = (TextView) inflate3.findViewById(R.id.uchange);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(overview_Result.getMainData()[i].getPchng());
                    sb2.append(!overview_Result.getMainData()[i].getPchng().equals("--") ? "%" : "");
                    textView2.setText(sb2.toString());
                    ((TextView) inflate3.findViewById(R.id.vol)).setText(overview_Result.getMainData()[i].getVol());
                    inflate3.setTag(Integer.valueOf(i));
                    inflate3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            Intent intent = new Intent(MS_Overview.this.MSFragmentActivity, OptionDetail.class);
                            intent.putExtra("oid", MS_Overview.this.indexdata[Integer.parseInt(view.getTag().toString())].getOid());
                            intent.putExtra("ucode", MS_Overview.this.indexdata[Integer.parseInt(view.getTag().toString())].getUcode());
                            intent.putExtra("mdate", MS_Overview.this.indexdata[Integer.parseInt(view.getTag().toString())].getMdate());
                            intent.putExtra("wtype", MS_Overview.this.indexdata[Integer.parseInt(view.getTag().toString())].getType());
                            intent.putExtra("strike", MS_Overview.this.indexdata[Integer.parseInt(view.getTag().toString())].getStrike());
                            MS_Overview.this.MSFragmentActivity.startActivity(intent);
                        }
                    });
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ((TextView) inflate3.findViewById(R.id.name)).setText("--");
                    ((TextView) inflate3.findViewById(R.id.last)).setText("--");
                    ((TextView) inflate3.findViewById(R.id.ulast)).setText("-- /");
                    ((TextView) inflate3.findViewById(R.id.uchange)).setText("--");
                    ((TextView) inflate3.findViewById(R.id.vol)).setText("--");
                    inflate3.setOnClickListener((View.OnClickListener) null);
                }
                this.datalist1.addView(inflate3);
            }
            if (!overview_Result.getOptionstime().equals("")) {
                this.optionstime_tv_stime.setText(overview_Result.getOptionstime());
                this.optionstime_tv_delay.setVisibility(View.VISIBLE);
            } else if (overview_Result.getOptionstime().equals(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                this.optionstime_tv_update.setVisibility(View.VISIBLE);
                this.optionstime_tv_stime.setText(R.string.no_data);
                this.optionstime_tv_delay.setVisibility(View.GONE);
            } else if (overview_Result.getOptionstime().equals("")) {
                this.optionstime_box.setVisibility(View.GONE);
            }
        }
        if (overview_Result.getMainData() == null || overview_Result.getMainData().length == 0) {
            this.listbox1.setVisibility(View.GONE);
        }
    }

    /* access modifiers changed from: private */
    public void init_list2(Overview_Result overview_Result) {
        String locale = getResources().getConfiguration().locale.toString();
        Log.v("current", "init_list2 " + locale + " : " + Commons.language);
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < overview_Result.getMainData2().length; i++) {
            View inflate = layoutInflater.inflate(R.layout.list_ms_overview_futures, this.datalist2, false);
            ((TextView) inflate.findViewById(R.id.name)).setText(Commons.language.equals("en_US") ? overview_Result.getMainData2()[i].getUname() : overview_Result.getMainData2()[i].getUnmll());
            Date date = null;
            try {
                date = overview_Result.getMainData2()[i].getMdate().length() > 7 ? new SimpleDateFormat("yyyy-MM-dd").parse(overview_Result.getMainData2()[i].getMdate()) : new SimpleDateFormat("yyyy-MM").parse(overview_Result.getMainData2()[i].getMdate());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ((TextView) inflate.findViewById(R.id.datetime)).setText(StringFormatter.formatDatetime(date, getContext()));
                ((TextView) inflate.findViewById(R.id.last)).setText(overview_Result.getMainData2()[i].getLast());
                TextView textView = (TextView) inflate.findViewById(R.id.change);
                StringBuilder sb = new StringBuilder();
                sb.append(overview_Result.getMainData2()[i].getPchng());
                sb.append(!overview_Result.getMainData2()[i].getPchng().equals("--") ? "%" : "");
                textView.setText(sb.toString());
                ((TextView) inflate.findViewById(R.id.bid)).setText(overview_Result.getMainData2()[i].getBid());
                ((TextView) inflate.findViewById(R.id.ask)).setText(overview_Result.getMainData2()[i].getAsk());
                ((TextView) inflate.findViewById(R.id.oivol)).setText(overview_Result.getMainData2()[i].getVol());
            } catch (Exception e2) {
                e2.printStackTrace();
                ((TextView) inflate.findViewById(R.id.datetime)).setText("--");
                ((TextView) inflate.findViewById(R.id.last)).setText("--");
                ((TextView) inflate.findViewById(R.id.change)).setText("--");
                ((TextView) inflate.findViewById(R.id.bid)).setText("--");
                ((TextView) inflate.findViewById(R.id.ask)).setText("--");
                ((TextView) inflate.findViewById(R.id.oivol)).setText("--");
            }
            try {
                if (Double.parseDouble(overview_Result.getMainData2()[i].getPchng().replace("%", "")) >= 0.0d) {
                    ((TextView) inflate.findViewById(R.id.change)).setTextColor(getResources().getColor(R.color.change_green));
                } else {
                    ((TextView) inflate.findViewById(R.id.change)).setTextColor(getResources().getColor(R.color.change_red));
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                ((TextView) inflate.findViewById(R.id.change)).setTextColor(getResources().getColor(R.color.textgrey));
            }
            inflate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MS_Overview.this.getActivity());
                    builder.setCancelable(false);
                    builder.setMessage(((TextView) view.findViewById(R.id.name)).getText() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ((TextView) view.findViewById(R.id.datetime)).getText());
                    builder.setNegativeButton(MS_Overview.this.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            });
            this.datalist2.addView(inflate);
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
                    if (!MS_Overview.this.MSFragmentActivity.leftViewOut && !MS_Overview.this.MSFragmentActivity.rightViewOut) {
                        viewFlipper.showNext();
                        MS_Overview.access$608(MS_Overview.this);
                    }
                    MS_Overview.this.runBanner(false);
                }
            };
            this.handler.postDelayed(this.bannerRunnable, z ? 0 : (long) (Integer.parseInt(this.banners.get(this.bannerIndex).getduration()) * 1000));
        }
    }

    public void dataResultBanner(MSBanner mSBanner) {
        this.msBanenr = mSBanner;
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    ViewFlipper viewFlipper = (ViewFlipper) MS_Overview.this.fragmentView.findViewById(R.id.bannerContainer);
                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(MS_Overview.this.MSFragmentActivity, R.anim.push_left_in));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(MS_Overview.this.MSFragmentActivity, R.anim.push_left_out));
                    viewFlipper.setVisibility(View.VISIBLE);
                    boolean unused = MS_Overview.this.noItemClick = false;
                    if (MS_Overview.this.msBanenr.getbanner_1() == null || !MS_Overview.this.msBanenr.getbanner_1().getpublish().equals("1")) {
                        viewFlipper.removeView((LinearLayout) MS_Overview.this.fragmentView.findViewById(R.id.flipperPage1));
                    } else {
                        MS_Overview.this.banners.add(MS_Overview.this.msBanenr.getbanner_1());
                        MS_Overview.this.initBanner(MS_Overview.this.msBanenr.getbanner_1(), R.id.bannerImage1);
                    }
                    if (MS_Overview.this.msBanenr.getbanner_2() == null || !MS_Overview.this.msBanenr.getbanner_2().getpublish().equals("1")) {
                        viewFlipper.removeView((LinearLayout) MS_Overview.this.fragmentView.findViewById(R.id.flipperPage2));
                    } else {
                        MS_Overview.this.banners.add(MS_Overview.this.msBanenr.getbanner_2());
                        MS_Overview.this.initBanner(MS_Overview.this.msBanenr.getbanner_2(), R.id.bannerImage2);
                    }
                    if (MS_Overview.this.msBanenr.getbanner_3() == null || !MS_Overview.this.msBanenr.getbanner_3().getpublish().equals("1")) {
                        viewFlipper.removeView((LinearLayout) MS_Overview.this.fragmentView.findViewById(R.id.flipperPage3));
                    } else {
                        MS_Overview.this.banners.add(MS_Overview.this.msBanenr.getbanner_3());
                        MS_Overview.this.initBanner(MS_Overview.this.msBanenr.getbanner_3(), R.id.bannerImage3);
                    }
                    if (MS_Overview.this.banners.size() <= 1) {
                        ((LinearLayout) MS_Overview.this.fragmentView.findViewById(R.id.nextBannerlayout)).setVisibility(View.GONE);
                    }
                    MS_Overview.this.runBanner(false);
                    MS_Overview.this.MSFragmentActivity.dataLoaded();
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public void dataResultkeylist(final OverviewTop_Result overviewTop_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                View view;
                try {
                    MS_Overview.this.MSFragmentActivity.dataLoaded();
                    MS_Overview.this.product_keylist.removeAllViews();
                    LayoutInflater layoutInflater = (LayoutInflater) MS_Overview.this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View inflate = layoutInflater.inflate(R.layout.list_topvolkey, MS_Overview.this.product_keylist, false);
                    int i = 0;
                    while (i < overviewTop_Result.getMainData().length) {
                        if (i % 2 == 0) {
                            inflate = layoutInflater.inflate(R.layout.list_topvolkey, MS_Overview.this.product_keylist, false);
                            ((TextView) inflate.findViewById(R.id.name)).setText(Commons.language.equals("en_US") ? overviewTop_Result.getMainData()[i].getUname() : overviewTop_Result.getMainData()[i].getUnmll());
                            ((TextView) inflate.findViewById(R.id.no)).setText(overviewTop_Result.getMainData()[i].getVolume());
                            if (overviewTop_Result.getMainData().length == i + 1) {
                                MS_Overview.this.product_keylist.addView(inflate);
                                view = inflate;
                            }
                            view = inflate;
                        } else {
                            ((TextView) inflate.findViewById(R.id.name2)).setText(Commons.language.equals("en_US") ? overviewTop_Result.getMainData()[i].getUname() : overviewTop_Result.getMainData()[i].getUnmll());
                            ((TextView) inflate.findViewById(R.id.no2)).setText(overviewTop_Result.getMainData()[i].getVolume());
                            MS_Overview.this.product_keylist.addView(inflate);
                            view = inflate;
                        }
                        i++;
                        inflate = view;
                    }
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void dataResultoverviewlist(final Overview_Result overview_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    MS_Overview.this.MSFragmentActivity.dataLoaded();
                    MS_Overview.this.datalist1.removeAllViews();
                    MS_Overview.this.datalist2.removeAllViews();
                    LayoutInflater layoutInflater = (LayoutInflater) MS_Overview.this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    if (overview_Result == null) {
                        MS_Overview.this.price_info_box.setVisibility(View.GONE);
                    } else if ((overview_Result.getMainData() == null || overview_Result.getMainData().length == 0) && (overview_Result.getMainData2() == null || overview_Result.getMainData2().length == 0)) {
                        MS_Overview.this.price_info_box.setVisibility(View.GONE);
                    } else if (overview_Result.getMainData() == null || overview_Result.getMainData().length == 0) {
                        MS_Overview.this.listbox1.setVisibility(View.GONE);
                        MS_Overview.this.init_list2(overview_Result);
                    } else if (overview_Result.getMainData2() == null || overview_Result.getMainData2().length == 0) {
                        MS_Overview.this.init_list1(overview_Result);
                        MS_Overview.this.listbox2.setVisibility(View.GONE);
                    } else {
                        MS_Overview.this.init_list1(overview_Result);
                        MS_Overview.this.init_list2(overview_Result);
                    }
                    if (!overview_Result.getFuturestime().equals("")) {
                        MS_Overview.this.futurestime_tv_stime.setText(overview_Result.getFuturestime());
                        MS_Overview.this.futurestime_tv_delay.setVisibility(View.INVISIBLE);
                    } else if (overview_Result.getFuturestime().equals(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                        MS_Overview.this.futurestime_tv_stime.setText(R.string.no_data);
                        MS_Overview.this.futurestime_tv_delay.setVisibility(View.GONE);
                    } else if (overview_Result.getFuturestime().equals("")) {
                        MS_Overview.this.futurestime_box.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public void initUI() {
        this.date_formatter = new SimpleDateFormat("yyyy-MM");
        if (Commons.language.equals("en_US")) {
            this.formatter = new SimpleDateFormat("MMM yyyy");
        } else {
            this.formatter = new SimpleDateFormat("yyyy" + getActivity().getResources().getString(R.string.year_text) + "MMM");
        }
        ((LinearLayout) this.fragmentView.findViewById(R.id.nextBannerlayout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MS_Overview.this.bannerRunnable != null) {
                    MS_Overview.this.handler.removeCallbacks(MS_Overview.this.bannerRunnable);
                }
                MS_Overview.this.runBanner(true);
            }
        });
        this.day_nigth_but = (ImageView) this.fragmentView.findViewById(R.id.day_nigth_but);
        this.btn_note = (ImageView) this.fragmentView.findViewById(R.id.btn_note);
        if (this.daynight.equals("d")) {
            this.day_nigth_but.setImageResource(R.drawable.day);
        } else {
            this.day_nigth_but.setImageResource(R.drawable.night);
        }
        this.product_keylist = (LinearLayout) this.fragmentView.findViewById(R.id.product_keylist);
        this.day_nigth_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MS_Overview.this.daynight.equals("d")) {
                    MS_Overview.this.day_nigth_but.setImageResource(R.drawable.night);
                    String unused = MS_Overview.this.daynight = "n";
                } else {
                    MS_Overview.this.day_nigth_but.setImageResource(R.drawable.day);
                    String unused2 = MS_Overview.this.daynight = "d";
                }
                MS_Overview.this.load_keylist();
            }
        });
        load_keylist();
        this.btn_note.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MS_Overview.this.getActivity());
                builder.setCancelable(false);
                builder.setMessage(R.string.msg_perform_comp);
                builder.setNegativeButton(MS_Overview.this.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        this.chart_webview = (WebView) this.fragmentView.findViewById(R.id.chart_webview);
        this.chart_webview.getSettings().setJavaScriptEnabled(true);
        this.chart_webview.getSettings().setDisplayZoomControls(false);
        this.chart_webview.getSettings().setBuiltInZoomControls(false);
        this.chart_webview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    MS_Overview.this.MSFragmentActivity.setClickControlCanSwipe(false);
                } else if (motionEvent.getAction() == 1) {
                    MS_Overview.this.MSFragmentActivity.setClickControlCanSwipe(true);
                } else if (motionEvent.getAction() == 2) {
                    MS_Overview.this.MSFragmentActivity.setClickControlCanSwipe(false);
                }
                return false;
            }
        });
        this.chart_webview.loadUrl(getString(R.string.url_overviewChart));
        this.datalist1 = (LinearLayout) this.fragmentView.findViewById(R.id.datalist1);
        this.datalist2 = (LinearLayout) this.fragmentView.findViewById(R.id.datalist2);
        this.listbox1 = (LinearLayout) this.fragmentView.findViewById(R.id.listbox1);
        this.listbox2 = (LinearLayout) this.fragmentView.findViewById(R.id.listbox2);
        this.optionstime_tv_update = (TextView) this.fragmentView.findViewById(R.id.optionstime_tv_update);
        this.optionstime_tv_stime = (TextView) this.fragmentView.findViewById(R.id.optionstime_tv_stime);
        this.futurestime_tv_stime = (TextView) this.fragmentView.findViewById(R.id.futurestime_tv_stime);
        this.optionstime_tv_delay = (TextView) this.fragmentView.findViewById(R.id.optionstime_tv_delay);
        this.futurestime_tv_delay = (TextView) this.fragmentView.findViewById(R.id.futurestime_tv_delay);
        this.price_info_box = (LinearLayout) this.fragmentView.findViewById(R.id.price_info_box);
        this.futurestime_box = (RelativeLayout) this.fragmentView.findViewById(R.id.futurestime_box);
        this.optionstime_box = (RelativeLayout) this.fragmentView.findViewById(R.id.optionstime_box);
        load_overviewlist_data();
        this.MSFragmentActivity.updateFooterStime("");
    }

    public void loadBannerJSON() {
        MSBannerJSONParser mSBannerJSONParser = new MSBannerJSONParser();
        mSBannerJSONParser.setOnJSONCompletedListener(new MSBannerJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(MSBanner mSBanner) {
                MS_Overview.this.dataResultBanner(mSBanner);
            }
        });
        mSBannerJSONParser.setOnJSONFailedListener(new MSBannerJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_Overview.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
            }
        });
        mSBannerJSONParser.loadXML(getString(R.string.url_msbanner));
        this.MSFragmentActivity.dataLoading();
    }

    public void load_keylist() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(OverviewTop_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<OverviewTop_Result>() {
            public void OnJSONCompleted(OverviewTop_Result overviewTop_Result) {
                MS_Overview.this.dataResultkeylist(overviewTop_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_Overview.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
                MS_Overview.this.MSFragmentActivity.dataLoaded();
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_overvier_top) + "?type=" + this.daynight.toUpperCase() + "Vol");
        this.MSFragmentActivity.dataLoading();
    }

    public void load_overviewlist_data() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(Overview_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<Overview_Result>() {
            public void OnJSONCompleted(Overview_Result overview_Result) {
                MS_Overview.this.dataResultoverviewlist(overview_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_Overview.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
                MS_Overview.this.MSFragmentActivity.dataLoaded();
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_overview_data));
        this.MSFragmentActivity.dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.MSFragmentActivity = (MarketSnapshot) getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.msoverview, viewGroup, false);
        initUI();
        loadBannerJSON();
        return this.fragmentView;
    }

    public void onResume() {
        super.onResume();
    }
}
