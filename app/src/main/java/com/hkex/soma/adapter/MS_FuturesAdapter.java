package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.dataModel.FuturesCList_Result;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MS_FuturesAdapter extends ArrayAdapter {
    private Context context;
    private FuturesCList_Result.mainData[] data2 = null;
    private SimpleDateFormat date_formatter;
    private SimpleDateFormat formatter;
    private String type;

    static class ViewHolder {
        private TextView ask;
        private TextView bid;
        private TextView datetime;
        private TextView last;
        private TextView pchng;
        private TextView vol;

        ViewHolder() {
        }
    }

    public MS_FuturesAdapter(Context context2, int i, FuturesCList_Result.mainData[] maindataArr, String str) {
        super(context2, i, maindataArr);
        this.context = context2;
        this.data2 = maindataArr;
        this.type = str;
        this.date_formatter = new SimpleDateFormat("yyyy-MM");
        if (Commons.language.equals("en_US")) {
            this.formatter = new SimpleDateFormat("MMM yyyy");
            return;
        }
        this.formatter = new SimpleDateFormat("yyyy" + context2.getResources().getString(R.string.year_text) + "MMM");
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        Date date;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_ms_futures, (ViewGroup) null);
            viewHolder = new ViewHolder();
            TextView unused = viewHolder.datetime = (TextView) view.findViewById(R.id.datetime);
            TextView unused2 = viewHolder.last = (TextView) view.findViewById(R.id.last);
            TextView unused3 = viewHolder.pchng = (TextView) view.findViewById(R.id.change);
            TextView unused4 = viewHolder.vol = (TextView) view.findViewById(R.id.oivol);
            TextView unused5 = viewHolder.bid = (TextView) view.findViewById(R.id.bid);
            TextView unused6 = viewHolder.ask = (TextView) view.findViewById(R.id.ask);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        try {
            date = this.date_formatter.parse(this.data2[i].getMdate().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        viewHolder.datetime.setText(StringFormatter.formatDatetime(date, getContext()));
        viewHolder.last.setText(this.data2[i].getLast() + "/");
        TextView unused7 = viewHolder.pchng = StringFormatter.formatPChng(viewHolder.pchng, this.data2[i].getPchng());
        viewHolder.vol.setText(this.data2[i].getVol());
        viewHolder.bid.setText(this.data2[i].getBid());
        viewHolder.ask.setText(this.data2[i].getAsk());
        return view;
    }
}
