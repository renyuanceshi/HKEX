package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hkex.soma.R;
import com.hkex.soma.activity.Portfolio;
import com.hkex.soma.dataModel.MS_Options;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;

public class MS_StockOptionsAdapter extends ArrayAdapter {
   private Context context;
   private MS_Options.mainData[] data = null;
   private MS_Options.mainData2[] data2 = null;
   private String type;

   public MS_StockOptionsAdapter(Context var1, int var2, MS_Options.mainData2[] var3, String var4) {
      super(var1, var2, var3);
      this.context = var1;
      this.data2 = var3;
      this.type = var4;
   }

   public MS_StockOptionsAdapter(Context var1, int var2, MS_Options.mainData[] var3, String var4) {
      super(var1, var2, var3);
      this.context = var1;
      this.data = var3;
      this.type = var4;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      LayoutInflater var15 = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View var4;
      MS_StockOptionsAdapter.ViewHolder var16;
      if (var2 == null) {
         var4 = var15.inflate(R.layout.list_ms_stockoptions, (ViewGroup)null);
         var16 = new MS_StockOptionsAdapter.ViewHolder();
         var16.instrument = (TextView)var4.findViewById(R.id.instrument);
         var16.last = (TextView)var4.findViewById(R.id.last);
         var16.pchng = (TextView)var4.findViewById(R.id.change);
         var16.vol = (TextView)var4.findViewById(R.id.oivol);
         var16.searchImg = (ImageView)var4.findViewById(2131165401);    //!LC
         var16.icon = (RelativeLayout)var4.findViewById(R.id.iconlayout);
         var4.setTag(var16);
      } else {
         var16 = (MS_StockOptionsAdapter.ViewHolder)var2.getTag();
         var4 = var2;
      }

      final String var5;
      final String var6;
      final String var7;
      StringBuilder var8;
      String var14;
      TextView var17;
      if (this.type == "MTC") {
         var5 = this.data[var1].getUcode();
         var6 = this.data[var1].getUnmll();
         var7 = this.data[var1].getUname();
         var8 = new StringBuilder();
         if (Commons.language.equals("en_US")) {
            var14 = var7;
         } else {
            var14 = var6;
         }

         var8.append(var14);
         var8.append("\n(");
         var8.append(var5);
         var8.append(")");
         var14 = var8.toString();
         var16.instrument.setText(var14);
         var17 = var16.last;
         var8 = new StringBuilder();
         var8.append(this.data[var1].getLast());
         var8.append("/");
         var17.setText(var8.toString());
         var16.pchng = StringFormatter.formatPChng(var16.pchng, this.data[var1].getPchng());
         if (this.type == "MTC") {
            var16.vol.setText(this.data[var1].getVol());
         } else {
            var16.vol.setText(this.data[var1].getOi());
         }

         var16.icon.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               boolean var2 = Portfolio.AddStockToPortfolio(MS_StockOptionsAdapter.this.context, var5, var6, var7, "0", "0", "0");
               StringBuilder var4 = new StringBuilder();
               var4.append(var6);
               var4.append("(");
               var4.append(var5);
               var4.append(")");
               var4.toString();
               StringBuilder var3 = new StringBuilder();
               String var5x;
               if (Commons.language.equals("en_US")) {
                  var5x = var7;
               } else {
                  var5x = var6;
               }

               var3.append(var5x);
               var3.append("(");
               var3.append(var5);
               var3.append(")");
               var5x = var3.toString();
               if (var2) {
                  Toast.makeText(MS_StockOptionsAdapter.this.context, String.format(MS_StockOptionsAdapter.this.context.getString(R.string.portfolio_msg_stock), var5x), Toast.LENGTH_LONG).show();
               } else {
                  Toast.makeText(MS_StockOptionsAdapter.this.context, String.format(MS_StockOptionsAdapter.this.context.getString(R.string.portfolio_msg_error), 20), Toast.LENGTH_LONG).show();
               }

            }
         });
      } else {
         var14 = "";
         if (this.data2[var1].getType().contains("C")) {
            var14 = Commons.callputText(this.context, "Call");
         } else if (this.data2[var1].getType().contains("P")) {
            var14 = Commons.callputText(this.context, "Put");
         }

         final String var9 = this.data2[var1].getUcode();
         if (this.data2[var1].getType().contains("C")) {
            var6 = "Call";
         } else {
            var6 = "Put";
         }

         final String var10 = this.data2[var1].getStrike();
         final String var11 = this.data2[var1].getMdate();
         var5 = this.data2[var1].getUnmll();
         var7 = this.data2[var1].getUname();
         StringBuilder var12 = new StringBuilder();
         String var18;
         if (Commons.language.equals("en_US")) {
            var18 = var7;
         } else {
            var18 = var5;
         }

         var12.append(var18);
         var12.append(" ");
         var12.append(this.data2[var1].getStrike());
         var12.append(" ");
         var12.append(var14);
         final String var19 = var12.toString();
         StringBuilder var13 = new StringBuilder();
         if (Commons.language.equals("en_US")) {
            var18 = var7;
         } else {
            var18 = var5;
         }

         var13.append(var18);
         var13.append("\n");
         var13.append(this.data2[var1].getStrike());
         var13.append(" ");
         var13.append(var14);
         var14 = var13.toString();
         var16.instrument.setText(var14);
         var17 = var16.last;
         var8 = new StringBuilder();
         var8.append(this.data2[var1].getLast());
         var8.append("/");
         var17.setText(var8.toString());
         var16.pchng = StringFormatter.formatPChng(var16.pchng, this.data2[var1].getPchng());
         if (this.type != "MTCall" && this.type != "MTPut") {
            var16.vol.setText(this.data2[var1].getOi());
         } else {
            var16.vol.setText(this.data2[var1].getVol());
         }

         var16.icon.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               if (Portfolio.AddOptionToPortfolio(MS_StockOptionsAdapter.this.context, var9, var5, var7, var6, var10, var11, "0", "0", "0")) {
                  Toast.makeText(MS_StockOptionsAdapter.this.context, String.format(MS_StockOptionsAdapter.this.context.getString(R.string.portfolio_msg_option), var19), Toast.LENGTH_LONG).show();
               } else {
                  Toast.makeText(MS_StockOptionsAdapter.this.context, String.format(MS_StockOptionsAdapter.this.context.getString(R.string.portfolio_msg_error), 20), Toast.LENGTH_LONG).show();
               }

            }
         });
      }

      return var4;
   }

   static class ViewHolder {
      private RelativeLayout icon;
      private TextView instrument;
      private TextView last;
      private TextView pchng;
      private ImageView searchImg;
      private TextView vol;
   }
}
