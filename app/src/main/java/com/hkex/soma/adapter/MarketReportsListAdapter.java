package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.dataModel.MarketReport_Result;
import java.util.ArrayList;

public class MarketReportsListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MarketReport_Result.mainData> data = null;

    static class ViewHolder {
        private TextView date;
        private ImageView download;
        private TextView title;

        ViewHolder() {
        }
    }

    public MarketReportsListAdapter() {
    }

    public MarketReportsListAdapter(Context context2, ArrayList<MarketReport_Result.mainData> arrayList) {
        this.context = context2;
        this.data = arrayList;
    }

    public int getCount() {
        if (this.data == null || this.data.size() <= 0) {
            return 0;
        }
        return this.data.size();
    }

    public MarketReport_Result.mainData getItem(int i) {
        return this.data.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_market_reports, (ViewGroup) null);
            viewHolder = new ViewHolder();
            TextView unused = viewHolder.title = (TextView) view.findViewById(R.id.title);
            TextView unused2 = viewHolder.date = (TextView) view.findViewById(R.id.date);
            ImageView unused3 = viewHolder.download = (ImageView) view.findViewById(R.id.download);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(this.data.get(i).getTitle());
        viewHolder.date.setText(this.data.get(i).getDate());
        if (this.data.get(i).getFile().equals("")) {
            viewHolder.download.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.download.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public void setdata(ArrayList<MarketReport_Result.mainData> arrayList) {
        this.data = arrayList;
    }
}
