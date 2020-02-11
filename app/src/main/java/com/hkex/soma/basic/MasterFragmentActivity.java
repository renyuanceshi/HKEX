package com.hkex.soma.basic;

import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.activity.MarketSnapshot;
import com.hkex.soma.element.AppFooter;
import com.hkex.soma.element.LoadingDialog;
import com.hkex.soma.utils.Commons;

public class MasterFragmentActivity extends FragmentActivity {
   private AppFooter appfooter;
   protected boolean canBackWhenLoading = true;
   private Builder errorAlertDialog;
   private boolean errorDialogShowing = false;
   protected LinearLayout footerContainer = null;
   public Handler handler = new Handler();
   protected LoadingDialog loadingDialog;
   public View mainContainer = null;

   public void ContingencyMessageBox(String var1, final boolean var2) {
      final Builder var3 = new Builder(this);
      var3.setCancelable(false);
      var3.setTitle(this.getString(R.string.hint));
      var3.setMessage(var1);
      var3.setPositiveButton(this.getString(R.string.ok), new OnClickListener() {
         public void onClick(DialogInterface var1, int var2x) {
            if (var2) {
               if (MasterFragmentActivity.this instanceof MarketSnapshot) {
                  MasterFragmentActivity.this.finish();
                  MasterFragmentActivity.this.startActivity(MasterFragmentActivity.this.getIntent());
               } else {
                  MasterFragmentActivity.this.goActivity(MarketSnapshot.class);
               }

            }
         }
      });
      this.handler.post(new Runnable() {
         public void run() {
            MasterFragmentActivity.this.dataLoaded();
            var3.show();
         }
      });
   }

   public void ShowConnectionErrorDialog(Runnable var1) {
      if (!this.errorDialogShowing) {
         this.errorDialogShowing = true;
         this.handler.post(new Runnable() {
            public void run() {
               MasterFragmentActivity.this.dataLoaded();
               MasterFragmentActivity.this.errorAlertDialog.show();
            }
         });
      }
   }

   public void VersionMessageBox(String var1) {
      final Builder var2 = new Builder(this);
      var2.setCancelable(false);
      var2.setTitle(this.getString(R.string.hint));
      var2.setMessage(var1);
      var2.setPositiveButton(this.getString(R.string.update), new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            StringBuilder var5;
            try {
               var5 = new StringBuilder();
               var5.append("market://details?id=");
               var5.append("com.hkex.soma");
               Intent var3 = new Intent("android.intent.action.VIEW", Uri.parse(var5.toString()));
               MasterFragmentActivity.this.startActivity(var3);
            } catch (ActivityNotFoundException var4) {
               var5 = new StringBuilder();
               var5.append("http://play.google.com/store/apps/details?id=");
               var5.append("com.hkex.soma");
               Intent var6 = new Intent("android.intent.action.VIEW", Uri.parse(var5.toString()));
               MasterFragmentActivity.this.startActivity(var6);
            }

         }
      });
      var2.setNegativeButton(this.getString(R.string.ok), new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
         }
      });
      this.handler.post(new Runnable() {
         public void run() {
            MasterFragmentActivity.this.loadingDialog.dismiss();
            var2.show();
         }
      });
   }

   public void dataLoaded() {
      Commons.setStatusBarHeight(this, this.mainContainer);
      this.loadingDialog.dismiss();
   }

   public void dataLoading() {
      if (!this.errorDialogShowing && !this.loadingDialog.isShowing()) {
         this.loadingDialog.show();
      }

   }

   protected void divideTabWidth(TabHost var1) {
      DisplayMetrics var2 = new DisplayMetrics();
      this.getWindowManager().getDefaultDisplay().getMetrics(var2);
      int var3 = var2.widthPixels;
      TabWidget var6 = var1.getTabWidget();
      int var4 = var6.getChildCount();
      int var5 = var3 / var4;

      for(var3 = 0; var3 < var4; ++var3) {
         var6.getChildTabViewAt(var3).setMinimumWidth(var5);
      }

   }

   public void goActivity(Class var1) {
      this.startActivity((new Intent()).setClass(this, var1));
   }

   public void halfPortfolioUpdated() {
   }

   protected void initFooter() {
      Log.v("main", "initFooter ");

      try {
         AppFooter var1 = new AppFooter(this, false);
         this.appfooter = var1;
         this.footerContainer = (LinearLayout)this.findViewById(R.id.footerContainer);
         this.footerContainer.removeAllViews();
         this.footerContainer.addView(this.appfooter);
      } catch (Exception var2) {
         Commons.LogDebug("initFooter", var2.getMessage());
      }

   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
      Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
      this.loadingDialog = new LoadingDialog(this);
      this.loadingDialog.setOnLoadingBackPressListener(new LoadingDialog.OnLoadingBackPressListener() {
         public void backPress() {
            if (MasterFragmentActivity.this.canBackWhenLoading && MasterFragmentActivity.this.getClass() != MarketSnapshot.class) {
               MasterFragmentActivity.this.finish();
            }

         }
      });
      Commons.context = this;
   }

   protected void onResume() {
      this.errorAlertDialog = new Builder(this);
      this.errorAlertDialog.setCancelable(false);
      this.errorAlertDialog.setTitle(this.getString(R.string.failHead));
      this.errorAlertDialog.setMessage(this.getString(R.string.dataFail));
      this.errorAlertDialog.setPositiveButton(this.getString(R.string.ok), new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            MasterFragmentActivity.this.errorDialogShowing = false;
         }
      });
      if (Commons.CommonsListRequireUpdate) {
         (new CommonsDataHandler(this)).startLoad();
      }

      super.onResume();
   }

   protected View setupTabLayout(Integer var1, Integer var2) {
      View var3 = LayoutInflater.from(this).inflate(R.layout.tab_layout, (ViewGroup)null);
      LinearLayout var4 = (LinearLayout)var3.findViewById(R.id.taplayout);
      TextView var5 = (TextView)var3.findViewById(R.id.textView);
      if (var4 != null) {
         var4.setBackgroundResource(var1);
         var5.setText(var2);
      }

      return var3;
   }

   public void showFooterImportantNote() {
      TextView var1 = (TextView)this.footerContainer.findViewById(R.id.tv_disclaimer);
      TextView var2 = (TextView)this.footerContainer.findViewById(R.id.tv_delay);
      this.appfooter.addImportantNoteClickListener(this);
      var1.setText(R.string.important_note);
      var1.setVisibility(View.VISIBLE);
      var2.setVisibility(View.GONE);
   }

   public void showFooterIndicesDisclaimer() {
      TextView var1 = (TextView)this.footerContainer.findViewById(R.id.tv_disclaimer);
      TextView var2 = (TextView)this.footerContainer.findViewById(R.id.tv_delay);
      this.appfooter.addIndicesDisclaimerClickListener(this);
      var1.setVisibility(View.VISIBLE);
      var2.setVisibility(View.GONE);
   }

   public void updateFooterStime(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("updateFooterStime ");
      var2.append(var1);
      var2.append(";");
      Log.v("main", var2.toString());

      try {
         RelativeLayout var3 = (RelativeLayout)this.footerContainer.findViewById(R.id.timebar);
         TextView var4 = (TextView)this.footerContainer.findViewById(R.id.tv_stime);
         TextView var5 = (TextView)this.footerContainer.findViewById(R.id.tv_delay);
         TextView var6 = (TextView)this.footerContainer.findViewById(R.id.tv_disclaimer);
         TextView var8 = (TextView)this.footerContainer.findViewById(R.id.tv_update);
         var3.setVisibility(View.VISIBLE);
         var4.setVisibility(View.VISIBLE);
         var5.setVisibility(View.VISIBLE);
         var6.setVisibility(View.VISIBLE);
         var8.setVisibility(View.VISIBLE);
         if (var1.equals("")) {
            var3.setVisibility(View.GONE);
         } else if (var1.equals(" ")) {
            var3.setVisibility(View.VISIBLE);
            var5.setVisibility(View.GONE);
            var6.setVisibility(View.GONE);
            var4.setText(R.string.no_data);
         } else if (var1.equals("nodatayet")) {
            var5.setVisibility(View.GONE);
            var6.setVisibility(View.GONE);
            var8.setVisibility(View.GONE);
            var4.setText(R.string.nodatayet);
         } else if (var1.equals("msg_data_after0930")) {
            var5.setVisibility(View.GONE);
            var6.setVisibility(View.GONE);
            var8.setVisibility(View.GONE);
            var4.setText(R.string.msg_data_after0930);
         } else {
            if (var1.length() > 12) {
               var5.setVisibility(View.VISIBLE);
            }

            var3.setVisibility(View.VISIBLE);
            var6.setVisibility(View.GONE);
            var4.setText(var1);
         }
      } catch (Exception var7) {
         Commons.LogDebug("updateFooterStime", var7.getMessage());
      }

   }

   public void updateFooterStime_nodelay(String string2) {
      try {
         RelativeLayout relativeLayout = (RelativeLayout)this.footerContainer.findViewById(R.id.timebar);
         TextView textView = (TextView)this.footerContainer.findViewById(R.id.tv_stime);
         TextView textView2 = (TextView)this.footerContainer.findViewById(R.id.tv_delay);
         TextView textView3 = (TextView)this.footerContainer.findViewById(R.id.tv_disclaimer);
         TextView textView4 = (TextView)this.footerContainer.findViewById(R.id.tv_update);
         relativeLayout.setVisibility(View.VISIBLE);
         textView.setVisibility(View.VISIBLE);
         textView2.setVisibility(View.VISIBLE);
         textView3.setVisibility(View.VISIBLE);
         if (string2.equals("")) {
            relativeLayout.setVisibility(View.GONE);
         } else if (string2.equals(" ")) {
            relativeLayout.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
            textView.setText(R.string.no_data);
         } else if (string2.equals("nodatayet")) {
            textView2.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
            textView4.setVisibility(View.GONE);
            textView.setText(R.string.nodatayet);
         } else if (string2.equals("msg_data_after0930")) {
            textView2.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
            textView4.setVisibility(View.GONE);
            textView.setText(R.string.msg_data_after0930);
         } else {
            if (string2.length() > 12) {
               textView2.setVisibility(View.VISIBLE);
            }
            relativeLayout.setVisibility(View.VISIBLE);
            textView3.setVisibility(View.GONE);
            textView.setText((CharSequence)string2);
         }
         textView2.setVisibility(View.GONE);
         return;
      }
      catch (Exception exception) {
         Commons.LogDebug("updateFooterStime", exception.getMessage());
         return;
      }
   }
}
