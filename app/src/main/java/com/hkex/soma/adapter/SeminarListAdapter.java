package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.dataModel.Seminar_Result;
import java.util.ArrayList;

public class SeminarListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Seminar_Result.mainData> data = null;

    static class ViewHolder {
        private TextView language;
        private TextView seminar;
        private TextView speakers;

        ViewHolder() {
        }
    }

    public SeminarListAdapter() {
    }

    public SeminarListAdapter(Context context2, ArrayList<Seminar_Result.mainData> arrayList) {
        this.context = context2;
        this.data = arrayList;
    }

    public int getCount() {
        if (this.data == null || this.data.size() <= 0) {
            return 0;
        }
        return this.data.size();
    }

    public Seminar_Result.mainData getItem(int i) {
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
            view = layoutInflater.inflate(R.layout.list_seminar, (ViewGroup) null);
            viewHolder = new ViewHolder();
            TextView unused = viewHolder.seminar = (TextView) view.findViewById(R.id.seminar);
            TextView unused2 = viewHolder.speakers = (TextView) view.findViewById(R.id.speakers);
            TextView unused3 = viewHolder.language = (TextView) view.findViewById(R.id.language);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.seminar.setText(this.data.get(i).getTitle());
        viewHolder.speakers.setText(this.data.get(i).getDate());
        viewHolder.language.setText(this.data.get(i).getLanguage());
        return view;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public void setdata(ArrayList<Seminar_Result.mainData> arrayList) {
        this.data = arrayList;
    }
}
