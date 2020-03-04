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
import com.hkex.soma.dataModel.SS_Result;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class SS_ResultAdapter extends ArrayAdapter {
    private String atmStrike;
    private Context context;
    private SS_Result.mainData[] data = null;
    private String ucode;

    static class ViewHolder {
        private TextView expiry;
        private RelativeLayout icon;
        private ImageView portfolioImg;
        private TextView strike;
        private TextView vol;
        private TextView wtype;

        ViewHolder() {
        }
    }

    public SS_ResultAdapter(Context context2, int i, SS_Result.mainData[] maindataArr, String str, String str2) {
        super(context2, i, maindataArr);
        this.context = context2;
        this.data = maindataArr;
        this.ucode = str;
        this.atmStrike = str2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_option_detail, (ViewGroup) null);
            ViewHolder viewHolder2 = new ViewHolder();
            TextView unused = viewHolder2.strike = (TextView) view.findViewById(R.id.strike);
            TextView unused2 = viewHolder2.wtype = (TextView) view.findViewById(R.id.wtype);
            TextView unused3 = viewHolder2.expiry = (TextView) view.findViewById(R.id.expiry);
            TextView unused4 = viewHolder2.vol = (TextView) view.findViewById(R.id.oivol);
            ImageView unused5 = viewHolder2.portfolioImg = (ImageView) view.findViewById(R.id.icon);
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
        String formatExpiry = StringFormatter.formatExpiry(this.data[i].getExpiry());
        viewHolder.strike.setText(this.data[i].getStrike());
        viewHolder.wtype.setText(str);
        StringFormatter.formatExpiry(formatExpiry);
        viewHolder.expiry.setText(StringFormatter.formatExpiry(this.data[i].getExpiry()));
        viewHolder.vol.setText(this.data[i].getVol());
        if (this.atmStrike.equals(this.data[i].getStrike())) {
            view.findViewById(R.id.containerlayout).setBackgroundResource(R.drawable.bg_list_2line_atm);
        }
        final String str2 = this.ucode;
        final String MapUnderlyingName = Commons.MapUnderlyingName(this.ucode, false);
        final String str3 = MapUnderlyingName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.data[i].getStrike() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + str;
        final String str4 = this.data[i].getWtype().contains("C") ? "Call" : "Put";
        final String strike = this.data[i].getStrike();
        final String expiry = this.data[i].getExpiry();
        final String MapUnderlyingName2 = Commons.MapUnderlyingName(this.ucode, true);
        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Portfolio.AddOptionToPortfolio(SS_ResultAdapter.this.context, str2, MapUnderlyingName, MapUnderlyingName2, str4, strike, expiry, "0", "0", "0")) {
                    Toast.makeText(SS_ResultAdapter.this.context, String.format(SS_ResultAdapter.this.context.getString(R.string.portfolio_msg_option), new Object[]{str3}), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(SS_ResultAdapter.this.context, String.format(SS_ResultAdapter.this.context.getString(R.string.portfolio_msg_error), new Object[]{20}), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
