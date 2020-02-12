package com.hkex.soma.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dipen.pricer.Calculations.American;
import com.dipen.pricer.Calculations.BSM;
import com.dipen.pricer.Calculations.ImpliedVol;
import com.hkex.soma.JSONParser.BannerJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.basic.CommonsDataHandler;
import com.hkex.soma.basic.CustomWebViewClient;
import com.hkex.soma.basic.MasterActivity;
import com.hkex.soma.dataModel.Banner_Result;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LandingScreen extends MasterActivity {
    private String url_bannerimage = "";
    private String url_landingdisclaimer = "";

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initWebView() {
        ((RelativeLayout) findViewById(R.id.landingcover)).setVisibility(View.GONE);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new CustomWebViewClient(this, webView, new CustomWebViewClient.WebViewCallBackHandler() {
            public Boolean webViewCallBack(String str) {
                int indexOf = str.indexOf("webview:") + 8;
                if (indexOf != 8 || !str.substring(indexOf).replace("/", "").equals("agree")) {
                    return false;
                }
                if (LandingScreen.this.url_bannerimage != "") {
                    LandingScreen.this.goActivity(BannerPage.class);
                } else {
                    LandingScreen.this.goActivity(MarketSnapshot.class);
                }
                return true;
            }
        }) {
        });
        webView.loadUrl(this.url_landingdisclaimer);
    }

    public void dataResult(final Banner_Result banner_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (banner_Result == null) {
                        String unused = LandingScreen.this.url_bannerimage = "";
                    } else {
                        String unused2 = LandingScreen.this.url_bannerimage = banner_Result.getImg();
                    }
                    LandingScreen.this.dataLoaded();
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void initUI() {
        Commons.initStaticText(this);
        ImageView imageView = (ImageView) findViewById(R.id.logoimage);
        String locale = getResources().getConfiguration().locale.toString();
        Log.v("current2", "current2 " + locale + " : " + Commons.language);
        if (Commons.language.equals("en_US")) {
            imageView.setImageResource(R.drawable.landing_back);
//            imageView.setImageResource(R.drawable.landing_logo_e);
            this.url_landingdisclaimer = getString(R.string.webview_landingdisclaimer_en);
            SharedPrefsUtil.putValue((Context) this, "language", "en_US");
            Commons.language = "en_US";
        } else if (Commons.language.equals("zh_CN")) {
            imageView.setImageResource(R.drawable.landing_back);
//            imageView.setImageResource(R.drawable.landing_logo_e);
            this.url_landingdisclaimer = getString(R.string.webview_landingdisclaimer_gb);
            SharedPrefsUtil.putValue((Context) this, "language", "zh_CN");
            Commons.language = "zh_CN";
        } else {
            imageView.setImageResource(R.drawable.landing_back);
//            imageView.setImageResource(R.drawable.landing_logo_e);
            this.url_landingdisclaimer = getString(R.string.webview_landingdisclaimer_ch);
            SharedPrefsUtil.putValue((Context) this, "language", "zh_TW");
            Commons.language = "zh_TW";
        }
        CommonsDataHandler commonsDataHandler = new CommonsDataHandler(this);
        commonsDataHandler.setOnDataCompletedListener(new CommonsDataHandler.OnDataCompletedListener() {
            public void onDataCompleted() {
                LandingScreen.this.handler.post(new Runnable() {
                    public void run() {
                        // skip disclaimer and banner message, goto marketsnapshot
                        LandingScreen.this.goActivity(MarketSnapshot.class);
//                        LandingScreen.this.initWebView();
//                        LandingScreen.this.loadJSON();
                    }
                });
            }
        });
        commonsDataHandler.setOnFailedToLoadListener(new CommonsDataHandler.OnFailedToLoadListener() {
            public void onFailed(Runnable runnable, Exception exc) {
                LandingScreen.this.ShowConnectionErrorDialog(runnable);
            }
        });
        commonsDataHandler.startLoad();
        String[] split = SharedPrefsUtil.getValue((Context) this, "language", "en_US").split("_");
        Locale locale2 = new Locale(split[0], split[1]);
        Locale.setDefault(locale2);
        Configuration configuration = new Configuration();
        configuration.locale = locale2;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    public void loadJSON() {
        BannerJSONParser bannerJSONParser = new BannerJSONParser();
        bannerJSONParser.setOnJSONCompletedListener(new BannerJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(Banner_Result banner_Result) {
                LandingScreen.this.dataResult(banner_Result);
            }
        });
        bannerJSONParser.setOnJSONFailedListener(new BannerJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                LandingScreen.this.ShowConnectionErrorDialog(runnable);
                LandingScreen.this.dataResult((Banner_Result) null);
            }
        });
        if (Commons.language.equals("en_US")) {
            bannerJSONParser.loadXML(getString(R.string.url_banner_en) + "&id=1");
        } else if (Commons.language.equals("zh_CN")) {
            bannerJSONParser.loadXML(getString(R.string.url_banner_gb) + "&id=1");
        } else {
            bannerJSONParser.loadXML(getString(R.string.url_banner_ch) + "&id=1");
        }
        dataLoading();
    }

    public void onCreate(Bundle bundle) {
        Commons.notation = SharedPrefsUtil.getValue((Context) this, "notation", "hk");
        Commons.tutor_cal = Integer.parseInt(SharedPrefsUtil.getValue((Context) this, "tutor_cal", "1"));
        Commons.tutor_chart = Integer.parseInt(SharedPrefsUtil.getValue((Context) this, "tutor_chart", "1"));
        Commons.tutor_search = Integer.parseInt(SharedPrefsUtil.getValue((Context) this, "tutor_search", "1"));
        Commons.tutor_margin = Integer.parseInt(SharedPrefsUtil.getValue((Context) this, "tutor_margin", "1"));
        super.onCreate(bundle);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        Commons.timeoutConnection = 30000;
        Commons.timeoutSocket = 30000;
    }

    protected void onResume() {
        Commons.CommonsListRequireUpdate = false;
        super.onResume();
        setContentView(R.layout.landingscreen);
        American america = new American(new double[]{58.65, 57.5, 16.0 / 365, 1.63, 0.0, 33.28});
        Log.d("Options", "AmCallPrice:" + america.callPrice());
        Log.d("Options", "AmCPPrice:" + america.callPerpetualPrice());
        Log.d("Options", "AmPutPrice:" + america.putPrice());
        Log.d("Options", "AmPPPrice" + america.putPerpetualPrice());
        BSM bsm = new BSM(new double[]{58.65, 57.5, 16.0 / 365, 1.63, 0.0, 33.28});
        Log.d("Options", "BSMCallPrice:" + bsm.callPrice());
        Log.d("Options", "BSMPutPrice:" + bsm.putPrice());
        ImpliedVol impliedVol = new ImpliedVol(new double[] {58.65, 57.5, 16.0/365, 1.63, 0, 2.27});
        Log.d("Options", "Call AM:" + impliedVol.callAm());
        Log.d("Options", "Put AM:" + impliedVol.putAm());
        Log.d("Options", "Call EU:" + impliedVol.callEu());
        Log.d("Options", "Put EU:" + impliedVol.putEu());

        Date sdate = null;
        try {
            sdate = (new SimpleDateFormat("yyyy-MM-dd")).parse("2020-02-27");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("Options", "Days:" + getDaysDifference(sdate));
        initUI();
    }

    public static int getDaysDifference(Date toDate) {
        if (toDate == null)
            return 0;

        Date today =  Calendar.getInstance().getTime();
        return (int) ((toDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24));
    }

}
