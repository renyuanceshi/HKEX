package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.dataModel.MS_IndicesResult;
import com.hkex.soma.utils.StringFormatter;

public class MS_IndicesAdapter extends ArrayAdapter {
    private Context context;
    private MS_IndicesResult.mainData[] item = null;

    static class ViewHolder {
        private TextView list_chng;
        private TextView list_index;
        private TextView list_last;
        private TextView list_pchng;

        ViewHolder() {
        }
    }

    public MS_IndicesAdapter(Context context2, int i, MS_IndicesResult.mainData[] maindataArr) {
        super(context2, i, maindataArr);
        this.context = context2;
        this.item = maindataArr;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_ms_indices, (ViewGroup) null);
            ViewHolder viewHolder2 = new ViewHolder();
            TextView unused = viewHolder2.list_index = (TextView) view.findViewById(R.id.list_index);
            TextView unused2 = viewHolder2.list_last = (TextView) view.findViewById(R.id.list_last);
            TextView unused3 = viewHolder2.list_chng = (TextView) view.findViewById(R.id.list_chng);
            TextView unused4 = viewHolder2.list_pchng = (TextView) view.findViewById(R.id.list_pchng);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.list_index.setText(this.item[i].getName());
        viewHolder.list_last.setText(this.item[i].getLast());
        TextView unused5 = viewHolder.list_chng = StringFormatter.formatChng(viewHolder.list_chng, this.item[i].getChng());
        TextView unused6 = viewHolder.list_pchng = StringFormatter.formatPChng(viewHolder.list_pchng, this.item[i].getPchng());
        return view;
    }
}
