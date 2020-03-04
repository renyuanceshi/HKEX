package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dipen.pricer.Calculations.ImpliedVol;
import com.hkex.soma.R;
import com.hkex.soma.activity.Portfolio;
import com.hkex.soma.dataModel.SO_Result;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class SO_ResultAdapter extends ArrayAdapter {
    private int atmIndex;
    private Context context;
    private SO_Result.mainData2[] data = null;
    private String expiry;
    private boolean isstock = true;
    private String ucode;
    private String wtype;
    private SO_Result.OptionsInfo[] optionsInfos = null;

    static class ViewHolder {
        private TextView change;
        private RelativeLayout icon;
        private TextView last;
        private ImageView searchImg;
        private TextView strike;
        private TextView vol;

        ViewHolder() {
        }
    }

    public SO_ResultAdapter(Context context2, int i, SO_Result.mainData2[] maindata2Arr, String str, String str2, String str3, int i2, SO_Result.underlyingInfo[] underlyingInfosArr, SO_Result.OptionsInfo[] optionsInfo) {
        super(context2, i, maindata2Arr);
        this.context = context2;
        this.data = maindata2Arr;
        this.ucode = str;
        this.wtype = str2;
        this.expiry = str3;
        this.atmIndex = i2;
        this.optionsInfos = optionsInfo;
    }

    public SO_ResultAdapter(Context context2, int i, SO_Result.mainData2[] maindata2Arr, String str, String str2, String str3, int i2, boolean z, SO_Result.OptionsInfo[] optionsInfo) {
        super(context2, i, maindata2Arr);
        this.context = context2;
        this.data = maindata2Arr;
        this.ucode = str;
        this.wtype = str2;
        this.expiry = str3;
        this.atmIndex = i2;
        this.isstock = z;
        this.optionsInfos = optionsInfo;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_so_optionsresult, (ViewGroup) null);
            ViewHolder viewHolder2 = new ViewHolder();
            TextView unused = viewHolder2.strike = (TextView) view.findViewById(R.id.strike);
            TextView unused2 = viewHolder2.last = (TextView) view.findViewById(R.id.last);
            TextView unused3 = viewHolder2.change = (TextView) view.findViewById(R.id.change);
            TextView unused4 = viewHolder2.vol = (TextView) view.findViewById(R.id.oivol);
            ImageView unused5 = viewHolder2.searchImg = (ImageView) view.findViewById(R.id.icon);
            RelativeLayout unused6 = viewHolder2.icon = (RelativeLayout) view.findViewById(R.id.iconlayout);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String str = "";
        if (this.wtype.contains("C")) {
            str = Commons.callputText(this.context, "Call");
        } else if (this.wtype.contains("P")) {
            str = Commons.callputText(this.context, "Put");
        }
        if (this.atmIndex == i) {
            view.findViewById(R.id.containerlayout).setBackgroundResource(R.drawable.bg_list_2line_atm);
        } else {
            view.findViewById(R.id.containerlayout).setBackgroundResource(R.drawable.bg_list_2line);
        }

        final String str2 = this.ucode;
        final String str3 = (this.isstock ? Commons.MapUnderlyingName(this.ucode) : Commons.MapIndexName(this.ucode)) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.data[i].getStrike() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + str;
        final String str4 = this.wtype.contains("C") ? "Call" : "Put";
        final String strike = this.data[i].getStrike();
        final String str5 = this.expiry;
        if (optionsInfos == null) {
            viewHolder.strike.setText(this.data[i].getStrike());
            viewHolder.last.setText(this.data[i].getLast());
            viewHolder.change = StringFormatter.formatChng(viewHolder.change, this.data[i].getChng());
        } else {
            viewHolder.strike.setText(this.data[i].getStrike() + "\n" + "IV:" + optionsInfos[i].getIv());
            viewHolder.last.setText(this.data[i].getLast() + "\n" + "P:" + optionsInfos[i].getExpectPrice());
            viewHolder.change = StringFormatter.formatChng(viewHolder.change, this.data[i].getChng() + "\n" + "R:" + optionsInfos[i].getTargetRate());
        }
        viewHolder.vol.setText(this.data[i].getVol());
        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Portfolio.AddOptionToPortfolio(SO_ResultAdapter.this.context, str2, SO_ResultAdapter.this.isstock ? Commons.MapUnderlyingName(SO_ResultAdapter.this.ucode, false) : Commons.MapIndexName(SO_ResultAdapter.this.ucode, false), SO_ResultAdapter.this.isstock ? Commons.MapUnderlyingName(SO_ResultAdapter.this.ucode, true) : Commons.MapIndexName(SO_ResultAdapter.this.ucode, true), str4, strike, str5, "0", "0", "0")) {
                    Toast.makeText(SO_ResultAdapter.this.context, String.format(SO_ResultAdapter.this.context.getString(R.string.portfolio_msg_option), new Object[]{str3}), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SO_ResultAdapter.this.context, String.format(SO_ResultAdapter.this.context.getString(R.string.portfolio_msg_error), new Object[]{20}), Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    public void setOptionsInfo(SO_Result.OptionsInfo[] optionsInfos) {
        this.optionsInfos = optionsInfos;
        this.notifyDataSetInvalidated();
    }
}
