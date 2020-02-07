package com.hkex.soma.basic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hkex.soma.BuildConfig;
import com.hkex.soma.R;
import com.hkex.soma.activity.MarketSnapshot;
import com.hkex.soma.element.AppFooter;
import com.hkex.soma.element.LoadingDialog;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

import java.util.Locale;

public class MasterActivity extends Activity {
    protected boolean canBackWhenLoading = true;
    private AlertDialog.Builder errorAlertDialog;
    private boolean errorDialogShowing = false;
    protected LinearLayout footerContainer = null;
    public Handler handler = new Handler();
    protected LoadingDialog loadingDialog;
    public View mainContainer = null;

    public void ContingencyMessageBox(String str, final boolean z) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.hint));
        builder.setMessage(str);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (z) {
                    MasterActivity.this.goActivity(MarketSnapshot.class);
                }
            }
        });
        this.handler.post(new Runnable() {
            public void run() {
                MasterActivity.this.loadingDialog.dismiss();
                builder.show();
            }
        });
    }

    public void ShowConnectionErrorDialog(Runnable runnable) {
        if (!this.errorDialogShowing) {
            this.errorDialogShowing = true;
            this.handler.post(new Runnable() {
                public void run() {
                    MasterActivity.this.loadingDialog.dismiss();
                    MasterActivity.this.errorAlertDialog.show();
                }
            });
        }
    }

    public void VersionMessageBox(String str) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.hint));
        builder.setMessage(str);
        builder.setPositiveButton(getString(R.string.update), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    MasterActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
                } catch (ActivityNotFoundException e) {
                    MasterActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                }
            }
        });
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        this.handler.post(new Runnable() {
            public void run() {
                MasterActivity.this.loadingDialog.dismiss();
                builder.show();
            }
        });
    }

    public void dataLoaded() {
        Commons.setStatusBarHeight(this, this.mainContainer);
        this.loadingDialog.dismiss();
    }

    public void dataLoading() {
        if (!this.errorDialogShowing) {
            this.loadingDialog.show();
        }
    }

    public void goActivity(Class<?> cls) {
        startActivity(new Intent().setClass(this, cls));
    }

    /* access modifiers changed from: protected */
    public void initFooter() {
        try {
            AppFooter appFooter = new AppFooter(this, false);
            this.footerContainer = (LinearLayout) findViewById(R.id.footerContainer);
            this.footerContainer.addView(appFooter);
        } catch (Exception e) {
            Commons.LogDebug("initFooter", e.getMessage());
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        this.loadingDialog = new LoadingDialog(this);
        this.loadingDialog.setOnLoadingBackPressListener(new LoadingDialog.OnLoadingBackPressListener() {
            public void backPress() {
                if (MasterActivity.this.canBackWhenLoading) {
                    MasterActivity.this.finish();
                }
            }
        });
        Commons.context = this;
    }

    protected void onResume() {
        String locale = getResources().getConfiguration().locale.toString();
        if (SharedPrefsUtil.getValue((Context) this, "language", "").equals("")) {
            Commons.language = locale;
        } else {
            Commons.language = SharedPrefsUtil.getValue((Context) this, "language", "");
            String[] split = Commons.language.split("_");
            Locale locale2 = new Locale(split[0], split[1]);
            Locale.setDefault(locale2);
            Configuration configuration = new Configuration();
            configuration.locale = locale2;
            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        }
        this.errorAlertDialog = new AlertDialog.Builder(this);
        this.errorAlertDialog.setCancelable(false);
        this.errorAlertDialog.setTitle(getString(R.string.failHead));
        this.errorAlertDialog.setMessage(getString(R.string.dataFail));
        this.errorAlertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean unused = MasterActivity.this.errorDialogShowing = false;
            }
        });
        if (Commons.CommonsListRequireUpdate) {
            new CommonsDataHandler(this).startLoad();
        }
        super.onResume();
    }

    protected void updateFooterStime(String str) {
        try {
            RelativeLayout relativeLayout = (RelativeLayout) this.footerContainer.findViewById(R.id.timebar);
            TextView textView = (TextView) this.footerContainer.findViewById(R.id.tv_stime);
            TextView textView2 = (TextView) this.footerContainer.findViewById(R.id.tv_delay);
            TextView textView3 = (TextView) this.footerContainer.findViewById(R.id.tv_update);
            if (str.equals("")) {
                relativeLayout.setVisibility(View.GONE);
            } else if (str.equals(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                relativeLayout.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.GONE);
                textView.setText(R.string.no_data);
            } else if (str.equals("nodatayet")) {
                textView2.setVisibility(View.GONE);
                textView3.setVisibility(View.GONE);
                textView.setText(R.string.nodatayet);
            } else {
                relativeLayout.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                textView.setText(str);
            }
        } catch (Exception e) {
            Commons.LogDebug("updateFooterStime", e.getMessage());
        }
    }
}
