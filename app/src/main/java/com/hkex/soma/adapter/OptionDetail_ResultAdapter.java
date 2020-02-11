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

import com.hkex.soma.R;
import com.hkex.soma.activity.Portfolio;
import com.hkex.soma.dataModel.OptionDetail_Result;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class OptionDetail_ResultAdapter extends ArrayAdapter {
    private int atmIndex;
    /* access modifiers changed from: private */
    public Context context;
    private OptionDetail_Result.mainData[] data = null;
    private boolean isindex = false;
    private String ucode;

    static class ViewHolder {
        private RelativeLayout icon;
        private TextView mdate;
        private ImageView searchImg;
        private TextView strike;
        private TextView vol;
        private TextView wtype;

        ViewHolder() {
        }
    }

    public OptionDetail_ResultAdapter(Context context2, int i, OptionDetail_Result.mainData[] maindataArr, String str, int i2) {
        super(context2, i, maindataArr);
        this.context = context2;
        this.data = maindataArr;
        this.ucode = str;
        this.atmIndex = i2;
    }

    public OptionDetail_ResultAdapter(Context context2, int i, OptionDetail_Result.mainData[] maindataArr, String str, int i2, boolean z) {
        super(context2, i, maindataArr);
        this.context = context2;
        this.data = maindataArr;
        this.ucode = str;
        this.atmIndex = i2;
        this.isindex = z;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_option_detail, (ViewGroup) null);
            ViewHolder viewHolder2 = new ViewHolder();
            TextView unused = viewHolder2.strike = (TextView) view.findViewById(R.id.strike);
            TextView unused2 = viewHolder2.wtype = (TextView) view.findViewById(R.id.wtype);
            TextView unused3 = viewHolder2.mdate = (TextView) view.findViewById(R.id.expiry);
            TextView unused4 = viewHolder2.vol = (TextView) view.findViewById(R.id.oivol);
            ImageView unused5 = viewHolder2.searchImg = (ImageView) view.findViewById(R.id.icon);  // !LC
            RelativeLayout unused6 = viewHolder2.icon = (RelativeLayout) view.findViewById(R.id.iconlayout);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String str = "";
        if (this.data[i].getWtype().contains("C")) {
            str = Commons.callputText(this.context, "Call");
        } else if (this.data[i].getWtype().contains("P")) {
            str = Commons.callputText(this.context, "Put");
        }
        final String str2 = this.ucode;
        final String MapIndexName = this.isindex ? Commons.MapIndexName(this.ucode, false) : Commons.MapUnderlyingName(this.ucode, false);
        final String str3 = MapIndexName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.data[i].getStrike() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + str;
        final String str4 = this.data[i].getWtype().contains("C") ? "Call" : "Put";
        final String strike = this.data[i].getStrike();
        final String expiry = this.data[i].getExpiry();
        final String MapIndexName2 = this.isindex ? Commons.MapIndexName(this.ucode, true) : Commons.MapUnderlyingName(this.ucode, true);
        if (this.atmIndex == i) {
            view.findViewById(R.id.containerlayout).setBackgroundResource(R.drawable.bg_list_2line_atm);
        }
        viewHolder.strike.setText(this.data[i].getStrike());
        viewHolder.wtype.setText(str);
        viewHolder.mdate.setText(StringFormatter.formatExpiry(this.data[i].getExpiry()));
        viewHolder.vol.setText(this.data[i].getVol());
        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Portfolio.AddOptionToPortfolio(OptionDetail_ResultAdapter.this.context, str2, MapIndexName, MapIndexName2, str4, strike, expiry, "0", "0", "0")) {
                    Toast.makeText(OptionDetail_ResultAdapter.this.context, String.format(OptionDetail_ResultAdapter.this.context.getString(R.string.portfolio_msg_option), new Object[]{str3}), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(OptionDetail_ResultAdapter.this.context, String.format(OptionDetail_ResultAdapter.this.context.getString(R.string.portfolio_msg_error), new Object[]{20}), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
