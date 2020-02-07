package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.dataModel.StaticProductTable_Result;
import java.util.ArrayList;

public class StaticProductListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<StaticProductTable_Result.mainData> data = null;

    static class ViewHolder {
        private TextView textview;

        ViewHolder() {
        }
    }

    public StaticProductListAdapter() {
    }

    public StaticProductListAdapter(Context context2, ArrayList<StaticProductTable_Result.mainData> arrayList) {
        this.context = context2;
        this.data = arrayList;
    }

    public int getCount() {
        if (this.data == null || this.data.size() <= 0) {
            return 0;
        }
        return this.data.size();
    }

    public StaticProductTable_Result.mainData getItem(int i) {
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
            view = layoutInflater.inflate(R.layout.list_static_product, (ViewGroup) null);
            ViewHolder viewHolder2 = new ViewHolder();
            TextView unused = viewHolder2.textview = (TextView) view.findViewById(R.id.textview);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textview.setText(this.data.get(i).getTitle());
        return view;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public void setdata(ArrayList<StaticProductTable_Result.mainData> arrayList) {
        this.data = arrayList;
    }
}
