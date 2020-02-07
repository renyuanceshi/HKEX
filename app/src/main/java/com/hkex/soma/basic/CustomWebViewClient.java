package com.hkex.soma.basic;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient {
    private WebViewCallBackHandler webViewCallBack;

    public interface WebViewCallBackHandler {
        Boolean webViewCallBack(String str);
    }

    public CustomWebViewClient(Context context, WebView webView, WebViewCallBackHandler webViewCallBackHandler) {
        this.webViewCallBack = webViewCallBackHandler;
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        webView.loadDataWithBaseURL((String) null, "<html></html>", "text/html", "utf8", (String) null);
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        return this.webViewCallBack.webViewCallBack(str).booleanValue();
    }
}
