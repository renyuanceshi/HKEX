package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.dataModel.Dividend_Result;
import com.hkex.soma.utils.Commons;

public class DividendAdapter extends ArrayAdapter {
    private Context context;
    private Dividend_Result.mainData[] data = null;

    static class ViewHolder {
        private TextView announcedate;
        private TextView details;
        private TextView exdate;

        ViewHolder() {
        }
    }

    public DividendAdapter(Context context2, int i, Dividend_Result.mainData[] maindataArr) {
        super(context2, i, maindataArr);
        this.context = context2;
        this.data = maindataArr;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_dividend, (ViewGroup) null);
            ViewHolder viewHolder2 = new ViewHolder();
            TextView unused = viewHolder2.announcedate = (TextView) view.findViewById(R.id.announcedate);
            TextView unused2 = viewHolder2.exdate = (TextView) view.findViewById(R.id.exdate);
            TextView unused3 = viewHolder2.details = (TextView) view.findViewById(R.id.details);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String exDate = this.data[i].getExDate();
        String payDate = this.data[i].getPayDate();
        if (exDate == "") {
            exDate = "-";
        }
        if (payDate == "") {
            payDate = "-";
        }
        viewHolder.announcedate.setText(this.data[i].getAnnounceDate());
        TextView access$100 = viewHolder.exdate;
        access$100.setText(exDate + "\n" + payDate);
        if (Commons.language.equals("en_US")) {
            viewHolder.details.setText(this.data[i].getDetails());
        } else {
            viewHolder.details.setText(this.data[i].getChidetails());
        }
        return view;
    }
}
