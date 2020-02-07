package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.dataModel.MarginTable_Result;

public class MarginTableAdapter extends ArrayAdapter {
    private Context context;
    private MarginTable_Result.mainData[] data = null;

    static class ViewHolder {
        private TextView call;
        private TextView put;
        private TextView strike;

        ViewHolder() {
        }
    }

    public MarginTableAdapter(Context context2, int i, MarginTable_Result.mainData[] maindataArr) {
        super(context2, i, maindataArr);
        this.context = context2;
        this.data = maindataArr;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_margintable, (ViewGroup) null);
            ViewHolder viewHolder2 = new ViewHolder();
            TextView unused = viewHolder2.call = (TextView) view.findViewById(R.id.call);
            TextView unused2 = viewHolder2.strike = (TextView) view.findViewById(R.id.strike);
            TextView unused3 = viewHolder2.put = (TextView) view.findViewById(R.id.put);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.call.setText(this.data[i].getCvalue());
        viewHolder.strike.setText(this.data[i].getStrike());
        viewHolder.put.setText(this.data[i].getPvalue());
        return view;
    }
}
