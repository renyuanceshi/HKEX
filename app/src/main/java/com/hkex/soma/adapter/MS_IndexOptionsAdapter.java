package com.hkex.soma.adapter;

import android.content.Context;
import android.util.Log;
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
import com.hkex.soma.dataModel.MS_IndexOptionsResult;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MS_IndexOptionsAdapter extends ArrayAdapter {
    private Context context;
    private MS_IndexOptionsResult.mainData[] data2 = null;
    private SimpleDateFormat date_formatter;
    private SimpleDateFormat formatter;
    private String type;

    public MS_IndexOptionsAdapter(Context var1, int var2, MS_IndexOptionsResult.mainData[] var3, String var4) {
        super(var1, var2, var3);
        this.context = var1;
        this.data2 = var3;
        this.type = var4;
        if (Commons.language.equals("en_US")) {
            this.formatter = new SimpleDateFormat("MMM yyyy");
        } else {
            StringBuilder var5 = new StringBuilder();
            var5.append("yyyy");
            var5.append(var1.getResources().getString(R.string.year_text));
            var5.append("MMM");
            this.formatter = new SimpleDateFormat(var5.toString());
        }

    }

    public View getView(int var1, View var2, ViewGroup var3) {
        LayoutInflater var18 = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View var4;
        MS_IndexOptionsAdapter.ViewHolder var19;
        if (var2 == null) {
            var4 = var18.inflate(R.layout.list_ms_stockoptions, (ViewGroup) null);
            var19 = new MS_IndexOptionsAdapter.ViewHolder();
            var19.instrument = (TextView) var4.findViewById(R.id.instrument);
            var19.last = (TextView) var4.findViewById(R.id.last);
            var19.pchng = (TextView) var4.findViewById(R.id.change);
            var19.vol = (TextView) var4.findViewById(R.id.oivol);
            var19.searchImg = (ImageView) var4.findViewById(2131165401);        // !LC
            var19.icon = (RelativeLayout) var4.findViewById(R.id.iconlayout);
            var4.setTag(var19);
        } else {
            var19 = (MS_IndexOptionsAdapter.ViewHolder) var2.getTag();
            var4 = var2;
        }

        String var17 = "";
        if (this.data2[var1].getType().contains("C")) {
            var17 = Commons.callputText(this.context, "Call");
        } else if (this.data2[var1].getType().contains("P")) {
            var17 = Commons.callputText(this.context, "Put");
        }

        final String var5 = this.data2[var1].getUcode();
        final String var6;
        if (this.data2[var1].getType().contains("C")) {
            var6 = "Call";
        } else {
            var6 = "Put";
        }

        final String var7 = this.data2[var1].getStrike();
        final String var8 = this.data2[var1].getMdate();
        final String var9 = this.data2[var1].getUnmll();
        final String var10 = this.data2[var1].getUname();
        StringBuilder var11 = new StringBuilder();
        var11.append("Commons.language ");
        var11.append(Commons.language);
        var11.append(" _uname_en ");
        var11.append(var10);
        var11.append(" uname ");
        var11.append(var9);
        Log.v("MS_IndexOptionsAdapter", var11.toString());
        StringBuilder var12 = new StringBuilder();
        String var22;
        if (Commons.language.equals("en_US")) {
            var22 = var10;
        } else {
            var22 = var9;
        }

        var12.append(var22);
        var12.append(" ");
        var12.append(this.data2[var1].getStrike());
        var12.append(" ");
        var12.append(var17);
        var22 = var12.toString();
        final String var = var22;
        label58:
        {
            Exception var20;
            label57:
            {
                Exception var10000;
                label56:
                {
                    StringBuilder var13;
                    boolean var10001;
                    SimpleDateFormat var23;
                    Date var24;
                    label55:
                    {
                        try {
                            if (this.data2[var1].getMdate().toString().length() <= 7) {
                                break label55;
                            }

                            var23 = new SimpleDateFormat("yyyy-MM-dd");
                            this.date_formatter = var23;
                            var24 = this.date_formatter.parse(this.data2[var1].getMdate().toString());
                            var13 = new StringBuilder();
                        } catch (Exception var16) {
                            var20 = var16;
                            break label57;
                        }

                        try {
                            var13.append(StringFormatter.formatDatetime(var24, 1, this.getContext()));
                            var13.append("\n");
                            var13.append(this.data2[var1].getStrike());
                            var13.append(" ");
                            var13.append(var17);
                            var17 = var13.toString();
                            break label58;
                        } catch (Exception var14) {
                            var10000 = var14;
                            var10001 = false;
                            break label56;
                        }
                    }

                    try {
                        var23 = new SimpleDateFormat("yyyy-MM");
                        this.date_formatter = var23;
                        var24 = this.date_formatter.parse(this.data2[var1].getMdate().toString());
                        var13 = new StringBuilder();
                        var13.append(StringFormatter.formatDatetime(var24, 2, this.getContext()));
                        var13.append("\n");
                        var13.append(this.data2[var1].getStrike());
                        var13.append(" ");
                        var13.append(var17);
                        var17 = var13.toString();
                        break label58;
                    } catch (Exception var15) {
                        var10000 = var15;
                        var10001 = false;
                    }
                }

                var20 = var10000;
            }

            var20.printStackTrace();
            var17 = "";
        }

        var19.instrument.setText(var17);
        TextView var21 = var19.last;
        var12 = new StringBuilder();
        var12.append(this.data2[var1].getLast());
        var12.append("/");
        var21.setText(var12.toString());
        var19.pchng = StringFormatter.formatPChng(var19.pchng, this.data2[var1].getPchng());
        var19.vol.setText(this.data2[var1].getVol());
        var19.icon.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {

                if (Portfolio.AddOptionToPortfolio(MS_IndexOptionsAdapter.this.context, var5, var9, var10, var6, var7, var8, "0", "0", "0")) {
                    Toast.makeText(MS_IndexOptionsAdapter.this.context, String.format(MS_IndexOptionsAdapter.this.context.getString(R.string.portfolio_msg_option), var), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MS_IndexOptionsAdapter.this.context, String.format(MS_IndexOptionsAdapter.this.context.getString(R.string.portfolio_msg_error), 20), Toast.LENGTH_LONG).show();
                }

            }
        });
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
