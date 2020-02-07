package com.hkex.soma.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.hkex.soma.R;
import com.hkex.soma.basic.CustomWebViewClient;
import com.hkex.soma.basic.MasterActivity;
import com.hkex.soma.element.SelectionDialog;
import com.hkex.soma.element.TutorialDialog;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.EncryptUtils;
import com.hkex.soma.utils.SharedPrefsUtil;
import java.io.IOException;
import java.io.InputStream;

@SuppressLint({"SetJavaScriptEnabled"})
public class ChartPage extends MasterActivity {
    private String chartHtml;
    /* access modifiers changed from: private */
    public String period = "3";
    /* access modifiers changed from: private */
    public String type = "underlying";
    private WebView webView;

    /* access modifiers changed from: private */
    public void compareChange() {
        final SelectionDialog selectionDialog;
        if (getIntent().getStringExtra("type") == null || !getIntent().getStringExtra("type").equals("index")) {
            selectionDialog = new SelectionDialog(this, new String[]{getString(R.string.uprice), getString(R.string.iv)});
        } else {
            selectionDialog = new SelectionDialog(this, new String[]{getString(R.string.chart_underlying_index), getString(R.string.iv)});
        }
        if (this.type.equals("iv")) {
            selectionDialog.setAdapterSelectedId(1);
        } else {
            selectionDialog.setAdapterSelectedId(0);
        }
        selectionDialog.setOnDialogItemClickListener(new SelectionDialog.OnDialogItemClickListener() {
            public void onDialogItemClicked(int i) {
                String unused = ChartPage.this.type = i == 0 ? "underlying" : "iv";
                ChartPage.this.loadHTMLStringToWebView();
                selectionDialog.dismiss();
            }
        });
        selectionDialog.show();
    }

    private String loadChartHtml() {
        String str;
        try {
            InputStream open = getAssets().open((getIntent().getStringExtra("type") == null || !getIntent().getStringExtra("type").equals("stock")) ? (getIntent().getStringExtra("type") == null || !getIntent().getStringExtra("type").equals("index")) ? getString(R.string.webview_stockchart).replace("file:///android_asset/", "") : getString(R.string.webview_indexchart).replace("file:///android_asset/", "") : getString(R.string.webview_chart).replace("file:///android_asset/", ""));
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            str = new String(bArr);
        } catch (IOException e) {
            e.printStackTrace();
            str = "";
        }
        float f = ((float) getResources().getDisplayMetrics().heightPixels) / getResources().getDisplayMetrics().density;
        float f2 = ((float) getResources().getDisplayMetrics().widthPixels) / getResources().getDisplayMetrics().density;
        float min = Math.min(f / 320.0f, f2 / 480.0f);
        String replace = str.replace("[~oID]", getIntent().getStringExtra("oid")).replace("[~title]", getIntent().getStringExtra("title")).replace("[~upColor]", getString(Commons.getUpColorResourceid()).replace("#ff", "#")).replace("[~downColor]", getString(Commons.getDownColorResourceid()).replace("#ff", "#")).replace("[~domain]", getString(R.string.url_domain));
        String replace2 = replace.replace("[~height]", f + "");
        String replace3 = replace2.replace("[~width]", f2 + "");
        String replace4 = replace3.replace("[~scale]", min + "");
        return replace4.replace("[~offset]", (((f2 / min) - 480.0f) / 2.0f) + "");
    }

    /* access modifiers changed from: private */
    public void loadHTMLStringToWebView() {
        String replace = this.chartHtml.replace("[~period]", this.period).replace("[~type]", this.type);
        Log.v("aaa2", "period " + this.period + " type " + this.type);
        String replace2 = getString(R.string.webview_chart).replace("chart_e.html", "").replace("chart_c.html", "").replace("chart_gb.html", "");
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= 16) {
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        this.webView.loadDataWithBaseURL(replace2, replace, "text/html", EncryptUtils.DEFAULT_ENCODING, "");
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        Commons.noResumeAction = true;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        setContentView(R.layout.chartpage);
        this.webView = (WebView) findViewById(R.id.webView1);
        this.webView.getSettings().setBuiltInZoomControls(false);
        this.webView.setHorizontalScrollBarEnabled(false);
        this.webView.setVerticalScrollBarEnabled(false);
        this.webView.getSettings().setJavaScriptEnabled(true);
        if (Build.BRAND.equals("samsung") && Build.MODEL.equals("GT-I9505")) {
            try {
                this.webView.getClass().getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class}).invoke(this.webView, new Object[]{1, null});
            } catch (Exception e) {
            }
        }
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.setWebViewClient(new CustomWebViewClient(this, this.webView, new CustomWebViewClient.WebViewCallBackHandler() {
            public Boolean webViewCallBack(String str) {
                int indexOf = str.indexOf("webview:") + 8;
                if (indexOf >= 8 && str.substring(indexOf).replace("/", "").equals("back")) {
                    ChartPage.this.finish();
                    return true;
                } else if (indexOf >= 8 && str.substring(indexOf).replace("/", "").equals("loaded")) {
                    Commons.LogDebug("setWebViewClient", "dataLoaded");
                    ChartPage.this.dataLoaded();
                    return true;
                } else if (indexOf >= 8 && str.substring(indexOf).replace("/", "").equals("compare")) {
                    ChartPage.this.compareChange();
                    return true;
                } else if (indexOf < 8) {
                    return false;
                } else {
                    String unused = ChartPage.this.period = str.substring(indexOf).replace("/", "").replace("m", "");
                    ChartPage.this.loadHTMLStringToWebView();
                    return true;
                }
            }
        }) {
        });
        this.webView.setWebChromeClient(new WebChromeClient() {
        });
        this.chartHtml = loadChartHtml();
        loadHTMLStringToWebView();
        if (Commons.tutor_chart == 1) {
            TutorialDialog tutorialDialog = new TutorialDialog(this);
            if (Commons.language == "en_US") {
                tutorialDialog.setTutorialResID(new int[]{R.drawable.tut_chart1_e, R.drawable.tut_chart2_e, R.drawable.tut_chart3_e});
            } else if (Commons.language == "zh_CN") {
                tutorialDialog.setTutorialResID(new int[]{R.drawable.tut_chart1_gb, R.drawable.tut_chart2_gb, R.drawable.tut_chart3_gb});
            } else {
                tutorialDialog.setTutorialResID(new int[]{R.drawable.tut_chart1_c, R.drawable.tut_chart2_c, R.drawable.tut_chart3_c});
            }
            tutorialDialog.show();
            Commons.tutor_chart = 0;
            SharedPrefsUtil.putValue((Context) this, "tutor_chart", Commons.tutor_chart + "");
        }
    }
}
