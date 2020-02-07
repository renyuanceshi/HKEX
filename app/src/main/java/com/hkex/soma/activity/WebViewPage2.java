package com.hkex.soma.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.basic.MasterActivity;

public class WebViewPage2 extends MasterActivity {
    private boolean daynight;
    public String ptitle = "";

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.webviewpage);
        ((ImageView) findViewById(R.id.btnLeft)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                WebViewPage2.this.finish();
            }
        });
        this.ptitle = getIntent().getStringExtra("title");
        String stringExtra = getIntent().getStringExtra("url");
        this.daynight = getIntent().getBooleanExtra("daynight", true);
        ((TextView) findViewById(R.id.webviewPageTitle)).setText(this.ptitle);
        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (!WebViewPage2.this.daynight) {
                    webView.loadUrl("javascript:(function() {document.getElementById('night_msg').style.display = 'none';})()");
                }
            }
        });
        webView.loadUrl(stringExtra);
        initFooter();
        updateFooterStime("");
    }
}
