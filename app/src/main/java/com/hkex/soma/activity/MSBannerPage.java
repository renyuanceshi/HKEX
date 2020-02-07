package com.hkex.soma.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.hkex.soma.R;
import com.hkex.soma.basic.MasterActivity;

public class MSBannerPage extends MasterActivity {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.msbannerpage);
        ((ImageView) findViewById(R.id.btnLeft)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MSBannerPage.this.finish();
            }
        });
        String stringExtra = getIntent().getStringExtra("url");
        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(stringExtra);
        initFooter();
        updateFooterStime("");
    }
}
