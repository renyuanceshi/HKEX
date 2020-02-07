package com.hkex.soma.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hkex.soma.R;
import com.hkex.soma.basic.CommonsDataHandler;
import com.hkex.soma.basic.MasterActivity;
import com.hkex.soma.element.OnOffButton;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;

import java.util.Locale;

public class SettingPage extends MasterActivity {
    private CheckBox langBox1;
    private CheckBox langBox2;
    private CheckBox langBox3;
    private CheckBox notaBox1;
    private CheckBox notaBox2;
    private OnOffButton onoff1;
    private OnOffButton onoff2;
    private OnOffButton onoff3;
    private OnOffButton onoff4;

    private void initClickBox() {
        this.langBox1 = (CheckBox) findViewById(R.id.checkBox1);
        this.langBox2 = (CheckBox) findViewById(R.id.checkBox2);
        this.langBox3 = (CheckBox) findViewById(R.id.checkBox3);
        this.notaBox1 = (CheckBox) findViewById(R.id.checkBox4);
        this.notaBox2 = (CheckBox) findViewById(R.id.checkBox5);
        this.onoff1 = (OnOffButton) findViewById(R.id.onoffbutton1);
        this.onoff2 = (OnOffButton) findViewById(R.id.onoffbutton2);
        this.onoff3 = (OnOffButton) findViewById(R.id.onoffbutton3);
        this.onoff4 = (OnOffButton) findViewById(R.id.onoffbutton4);
        String str = Commons.language;
        String str2 = Commons.notation;
        if (Commons.language.equals("en_US")) {
            this.langBox1.setChecked(true);
        } else if (Commons.language.equals("zh_CN")) {
            this.langBox3.setChecked(true);
        } else {
            this.langBox2.setChecked(true);
        }
        if (str2.equals("hk")) {
            this.notaBox1.setChecked(true);
        } else if (str2.equals("ch")) {
            this.notaBox2.setChecked(true);
        }
        this.onoff1.setState(Commons.tutor_cal);
        this.onoff2.setState(Commons.tutor_chart);
        this.onoff3.setState(Commons.tutor_search);
        this.onoff4.setState(Commons.tutor_margin);
        this.onoff1.setOnLayoutClickListener(new OnOffButton.OnLayoutClickListener() {
            public void onLayoutClicked(int i) {
                Commons.tutor_cal = SettingPage.this.onoff1.getState();
            }
        });
        this.onoff2.setOnLayoutClickListener(new OnOffButton.OnLayoutClickListener() {
            public void onLayoutClicked(int i) {
                Commons.tutor_chart = SettingPage.this.onoff2.getState();
            }
        });
        this.onoff3.setOnLayoutClickListener(new OnOffButton.OnLayoutClickListener() {
            public void onLayoutClicked(int i) {
                Commons.tutor_search = SettingPage.this.onoff3.getState();
            }
        });
        this.onoff4.setOnLayoutClickListener(new OnOffButton.OnLayoutClickListener() {
            public void onLayoutClicked(int i) {
                Commons.tutor_margin = SettingPage.this.onoff4.getState();
            }
        });
        this.langBox1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingPage.this.langBox1.setChecked(true);
                SettingPage.this.langBox2.setChecked(false);
                SettingPage.this.langBox3.setChecked(false);
                Commons.CommonsListRequireUpdate = true;
            }
        });
        this.langBox2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingPage.this.langBox1.setChecked(false);
                SettingPage.this.langBox2.setChecked(true);
                SettingPage.this.langBox3.setChecked(false);
                Commons.CommonsListRequireUpdate = true;
            }
        });
        this.langBox3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingPage.this.langBox1.setChecked(false);
                SettingPage.this.langBox2.setChecked(false);
                SettingPage.this.langBox3.setChecked(true);
                Commons.CommonsListRequireUpdate = true;
            }
        });
        this.notaBox1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingPage.this.notaBox1.setChecked(true);
                SettingPage.this.notaBox2.setChecked(false);
            }
        });
        this.notaBox2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingPage.this.notaBox1.setChecked(false);
                SettingPage.this.notaBox2.setChecked(true);
            }
        });
    }

    private void initClickHandler() {
        ImageView imageView = (ImageView) findViewById(R.id.btnLeft);
        if (Commons.language.equals("en_US")) {
            imageView.setImageResource(R.drawable.btn_done_e);
        } else if (Commons.language.equals("zh_CN")) {
            imageView.setImageResource(R.drawable.btn_done_gb);
        } else {
            imageView.setImageResource(R.drawable.btn_done_c);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingPage.this.dataLoading();
                SettingPage.this.updateSetting();
                Commons.need2reflash = true;
                if (Commons.CommonsListRequireUpdate) {
                    CommonsDataHandler commonsDataHandler = new CommonsDataHandler(SettingPage.this);
                    commonsDataHandler.setOnDataCompletedListener(new CommonsDataHandler.OnDataCompletedListener() {
                        public void onDataCompleted() {
                            SettingPage.this.handler.post(new Runnable() {
                                public void run() {
                                    SettingPage.this.finish();
                                }
                            });
                        }
                    });
                    commonsDataHandler.setOnFailedToLoadListener(new CommonsDataHandler.OnFailedToLoadListener() {
                        public void onFailed(Runnable runnable, Exception exc) {
                            SettingPage.this.ShowConnectionErrorDialog(runnable);
                            SettingPage.this.finish();
                        }
                    });
                    commonsDataHandler.startLoad();
                    return;
                }
                SettingPage.this.finish();
            }
        });
        ((RelativeLayout) findViewById(R.id.privacylayout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent().setClass(SettingPage.this, WebViewPage.class);
                intent.putExtra("title", SettingPage.this.getString(R.string.title_privacy));
                intent.putExtra("url", SettingPage.this.getString(R.string.webview_privacy));
                SettingPage.this.startActivity(intent);
            }
        });
        ((RelativeLayout) findViewById(R.id.disclaimerlayout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent().setClass(SettingPage.this, WebViewPage.class);
                intent.putExtra("title", SettingPage.this.getString(R.string.title_disclaimer));
                intent.putExtra("url", SettingPage.this.getString(R.string.webview_disclaimer));
                SettingPage.this.startActivity(intent);
            }
        });
    }

    private void updateSetting() {
        if (this.langBox1.isChecked()) {
            SharedPrefsUtil.putValue((Context) this, "language", "en_US");
        } else if (this.langBox2.isChecked()) {
            SharedPrefsUtil.putValue((Context) this, "language", "zh_TW");
        } else if (this.langBox3.isChecked()) {
            SharedPrefsUtil.putValue((Context) this, "language", "zh_CN");
        }
        if (this.notaBox1.isChecked()) {
            SharedPrefsUtil.putValue((Context) this, "notation", "hk");
        } else if (this.notaBox2.isChecked()) {
            SharedPrefsUtil.putValue((Context) this, "notation", "ch");
        }
        String[] split = SharedPrefsUtil.getValue((Context) this, "language", "en_US").split("_");
        Locale locale = new Locale(split[0], split[1]);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        Commons.initStaticText(this);
        Commons.language = SharedPrefsUtil.getValue((Context) this, "language", "en_US");
        Commons.notation = SharedPrefsUtil.getValue((Context) this, "notation", "hk");
        SharedPrefsUtil.putValue((Context) this, "tutor_cal", Commons.tutor_cal + "");
        SharedPrefsUtil.putValue((Context) this, "tutor_chart", Commons.tutor_chart + "");
        SharedPrefsUtil.putValue((Context) this, "tutor_search", Commons.tutor_search + "");
        SharedPrefsUtil.putValue((Context) this, "tutor_margin", Commons.tutor_margin + "");
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.setting);
        initClickBox();
        initClickHandler();
    }
}
