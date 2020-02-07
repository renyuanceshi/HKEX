package com.hkex.soma.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.basic.CustomWebViewClient;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.utils.Commons;

@SuppressLint({"SetJavaScriptEnabled"})
public class Education extends AnimatedFragmentActivity {
   private MenuContainer menu;

   public void initUI() {
      this.setContentView(2131296292);
      this.menu = new MenuContainer(this);
      this.mainContainer = this.findViewById(2131165452);
      this.clickControlContainer = (ClickControlContainer)this.findViewById(2131165294);
      ClickControlContainer var1 = (ClickControlContainer)this.findViewById(2131165459);
      LayoutParams var2 = new LayoutParams(-1, -1);
      var1.addView(this.menu, var2);
      this.initFooter();
      this.updateFooterStime("");
      this.mainView = this.findViewById(2131165234);
      this.leftView = var1;
      final SlideLeftAnimationHandler var3 = new SlideLeftAnimationHandler(this);
      final ImageButton var4 = (ImageButton)this.mainView.findViewById(2131165251);
      final WebView var7 = (WebView)this.findViewById(2131165727);
      final OnClickListener var5 = new OnClickListener() {
         public void onClick(View var1) {
            if (!var7.canGoBackOrForward(-2)) {
               ((TextView)Education.this.findViewById(2131165235)).setText(Education.this.getString(2131362067));
               var4.setImageResource(2131099802);
               var4.setOnClickListener(var3);
               Education.this.clickControlContainer.setCanSwipe(true);
            } else {
               Education.this.clickControlContainer.setCanSwipe(false);
            }

            if (var7.canGoBack()) {
               var7.goBack();
            }

         }
      };
      var4.setOnClickListener(var3);
      this.menu.setSliedBackHandler(var3);
      var1.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
         public void onRightToLeftSwiped() {
            var3.onClick((View)null);
         }
      });
      this.clickControlContainer.setOnLeftToRightSwipeListener(new ClickControlContainer.OnLeftToRightSwipeListener() {
         public void onLeftToRightSwiped() {
            var3.onClick((View)null);
         }
      });
      var7.getSettings().setBuiltInZoomControls(false);
      var7.setHorizontalScrollBarEnabled(false);
      var7.setVerticalScrollBarEnabled(false);
      var7.getSettings().setJavaScriptEnabled(true);
      var7.setWebViewClient(new CustomWebViewClient(this, var7, new CustomWebViewClient.WebViewCallBackHandler() {
         public Boolean webViewCallBack(String var1) {
            int var2 = var1.indexOf("webview:") + 8;
            int var3 = var1.substring(var2).replace("/", "").indexOf("settitle:");
            boolean var4x = false;
            if (var3 >= 0) {
               String var5x = var1.substring(var2).replace("/", "").replace("settitle:", "");
               if (var5x.equals("Options ABC")) {
                  var1 = Education.this.getString(2131361856);
               } else if (var5x.equals("FAQ")) {
                  var1 = Education.this.getString(2131361854);
               } else if (var5x.equals("Options Strategies")) {
                  var1 = Education.this.getString(2131361857);
               } else {
                  var1 = var5x;
                  if (var5x.equals("Glossary")) {
                     var1 = Education.this.getString(2131361855);
                  }
               }

               var4.setImageResource(2131099777);
               var4.setOnClickListener(var5);
               ((TextView)Education.this.findViewById(2131165235)).setText(var1);
               Education.this.clickControlContainer.setCanSwipe(false);
            } else if (var1.substring(var2).replace("/", "").indexOf("tvinterviews") >= 0) {
               Education.this.goActivity(TVInterviews.class);
            } else if (var1.indexOf(".pdf") == var1.length() - 4) {
               StringBuilder var9 = new StringBuilder();
               var9.append("Open PDF: ");
               var9.append(var1);
               Commons.LogDebug("setWebViewClient", var9.toString());
               Commons.noResumeAction = true;
               Intent var8 = new Intent("android.intent.action.VIEW", Uri.parse(var1));
               var8.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               Education.this.startActivity(var8);
               return true;
            }

            StringBuilder var7 = new StringBuilder();
            boolean var6;
            if (var2 >= 8) {
               var6 = true;
            } else {
               var6 = false;
            }

            var7.append(var6);
            var7.append("");
            Commons.LogDebug("Override URL: ", var7.toString());
            var6 = var4x;
            if (var2 >= 8) {
               var6 = true;
            }

            return var6;
         }
      }) {
      });
      StringBuilder var6 = new StringBuilder();
      var6.append(this.getString(2131362150));
      var6.append("?a=b");
      var7.loadUrl(var6.toString());
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.requestWindowFeature(1);
   }

   protected void onResume() {
      super.onResume();
      if (!Commons.noResumeAction) {
         this.leftViewOut = false;
         this.rightViewOut = false;
         this.initUI();
      } else {
         this.isMoving = false;
         Commons.noResumeAction = false;
      }

      this.dataLoaded();
   }
}
