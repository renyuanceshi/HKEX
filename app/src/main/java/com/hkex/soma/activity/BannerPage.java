package com.hkex.soma.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.hkex.soma.JSONParser.BannerJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.basic.LoaderImageView;
import com.hkex.soma.basic.MasterActivity;
import com.hkex.soma.dataModel.Banner_Result;
import com.hkex.soma.utils.Commons;

public class BannerPage extends MasterActivity {
    /* access modifiers changed from: private */
    public String url_bannerimage = "";
    /* access modifiers changed from: private */
    public String url_bannerurl = "";

    private void initClickHandler() {
        ((ImageView) findViewById(R.id.btnLeft)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BannerPage.this.goActivity(MarketSnapshot.class);
                BannerPage.this.finish();
            }
        });
    }

    public void dataResult(final Banner_Result banner_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (banner_Result == null) {
                        String unused = BannerPage.this.url_bannerimage = "";
                        String unused2 = BannerPage.this.url_bannerurl = "";
                    } else {
                        String unused3 = BannerPage.this.url_bannerimage = banner_Result.getImg();
                        String unused4 = BannerPage.this.url_bannerurl = banner_Result.getUrl();
                        Log.v("aaa", "url_bannerurl " + BannerPage.this.url_bannerurl);
                        LoaderImageView loaderImageView = (LoaderImageView) BannerPage.this.findViewById(R.id.logoimage);
                        loaderImageView.setImageDrawable(BannerPage.this.getString(R.string.url_domain) + BannerPage.this.url_bannerimage, ImageView.ScaleType.FIT_XY);
                        loaderImageView.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                if (BannerPage.this.url_bannerurl.contains("http") || BannerPage.this.url_bannerurl.contains("https")) {
                                    BannerPage.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(BannerPage.this.url_bannerurl)));
                                }
                            }
                        });
                    }
                    BannerPage.this.dataLoaded();
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void loadJSON() {
        BannerJSONParser bannerJSONParser = new BannerJSONParser();
        bannerJSONParser.setOnJSONCompletedListener(new BannerJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(Banner_Result banner_Result) {
                BannerPage.this.dataResult(banner_Result);
            }
        });
        bannerJSONParser.setOnJSONFailedListener(new BannerJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                BannerPage.this.ShowConnectionErrorDialog(runnable);
                BannerPage.this.dataResult((Banner_Result) null);
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
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.banner);
        initClickHandler();
        loadJSON();
    }
}
