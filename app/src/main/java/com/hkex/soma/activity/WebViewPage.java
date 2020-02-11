package com.hkex.soma.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.basic.MasterActivity;

public class WebViewPage extends MasterActivity {
    public String ptitle = "";

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.webviewpage);
        ((ImageView) findViewById(R.id.btnLeft)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                WebViewPage.this.finish();
            }
        });
        this.ptitle = getIntent().getStringExtra("title");
        String stringExtra = getIntent().getStringExtra("url");
        ((TextView) findViewById(R.id.webviewPageTitle)).setText(this.ptitle);
        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(stringExtra);
        initFooter();
        updateFooterStime("");
    }
}
