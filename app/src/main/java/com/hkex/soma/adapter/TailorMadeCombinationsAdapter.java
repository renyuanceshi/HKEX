package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.dataModel.TailorMadeCombinations_Result;

public class TailorMadeCombinationsAdapter extends ArrayAdapter {
    private Context context;
    private TailorMadeCombinations_Result.mainData[] item = null;

    static class ViewHolder {
        private TextView ask;
        private TextView bid;
        private TextView high;
        private TextView hkats_code;
        private TextView last_traded;
        private TextView legs;
        private TextView low;
        private TextView volume;

        ViewHolder() {
        }
    }

    public TailorMadeCombinationsAdapter(Context context2, int i, TailorMadeCombinations_Result.mainData[] maindataArr) {
        super(context2, i, maindataArr);
        this.context = context2;
        this.item = maindataArr;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_tailor_made_combinations, (ViewGroup) null);
            ViewHolder viewHolder2 = new ViewHolder();
            TextView unused = viewHolder2.hkats_code = (TextView) view.findViewById(R.id.hkats_code);
            TextView unused2 = viewHolder2.legs = (TextView) view.findViewById(R.id.legs);
            TextView unused3 = viewHolder2.bid = (TextView) view.findViewById(R.id.bid);
            TextView unused4 = viewHolder2.ask = (TextView) view.findViewById(R.id.ask);
            TextView unused5 = viewHolder2.last_traded = (TextView) view.findViewById(R.id.last_traded);
            TextView unused6 = viewHolder2.high = (TextView) view.findViewById(R.id.high);
            TextView unused7 = viewHolder2.low = (TextView) view.findViewById(R.id.low);
            TextView unused8 = viewHolder2.volume = (TextView) view.findViewById(R.id.volume);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.hkats_code.setText(this.item[i].getHcode());
        viewHolder.legs.setText(this.item[i].getLegs().replace("<br>", "\n"));
        viewHolder.bid.setText(this.item[i].getBid());
        viewHolder.ask.setText(this.item[i].getAsk());
        viewHolder.last_traded.setText(this.item[i].getTraded());
        viewHolder.high.setText(this.item[i].getHigh());
        viewHolder.low.setText(this.item[i].getLow());
        viewHolder.volume.setText(this.item[i].getVol());
        return view;
    }
}
