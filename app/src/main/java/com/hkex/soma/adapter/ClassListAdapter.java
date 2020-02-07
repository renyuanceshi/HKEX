package com.hkex.soma.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.activity.Search;
import com.hkex.soma.basic.MasterFragmentActivity;
import com.hkex.soma.dataModel.ClassList;
import com.hkex.soma.utils.Commons;

public class ClassListAdapter extends ArrayAdapter {
    private Context context;
    private ClassList.mainData[] data = null;

    static class ViewHolder {
        private TextView board;
        private TextView csize;
        private TextView hcode;
        private TextView instrument;
        private LinearLayout list_ly;
        private ImageView searchImg;

        ViewHolder() {
        }
    }

    public ClassListAdapter(Context context2, int i, ClassList.mainData[] maindataArr) {
        super(context2, i, maindataArr);
        this.context = context2;
        this.data = maindataArr;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_classlist, (ViewGroup) null);
            ViewHolder viewHolder2 = new ViewHolder();
            TextView unused = viewHolder2.instrument = (TextView) view.findViewById(R.id.instrument);
            TextView unused2 = viewHolder2.hcode = (TextView) view.findViewById(R.id.hcode);
            TextView unused3 = viewHolder2.csize = (TextView) view.findViewById(R.id.csize);
            TextView unused4 = viewHolder2.board = (TextView) view.findViewById(R.id.board);
            ImageView unused5 = viewHolder2.searchImg = (ImageView) view.findViewById(2131165401);  //!LC
            LinearLayout unused6 = viewHolder2.list_ly = (LinearLayout) view.findViewById(R.id.list_ly);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final String ucode = this.data[i].getUcode();
        viewHolder.instrument.setText("(" + ucode + ")\n" + Commons.MapUnderlyingName(ucode));
        viewHolder.hcode.setText(this.data[i].getHcode());
        viewHolder.csize.setText(this.data[i].getSize());
        try {
            TextView access$300 = viewHolder.board;
            access$300.setText((Integer.parseInt(this.data[i].getSize().replaceAll(",", "")) / Integer.parseInt(this.data[i].getBoard().replaceAll(",", ""))) + "");
        } catch (Exception e) {
            viewHolder.board.setText("");
        }
        viewHolder.list_ly.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((MasterFragmentActivity) ClassListAdapter.this.context).dataLoading();
                Intent intent = new Intent(ClassListAdapter.this.context, Search.class);
                intent.putExtra("ucode", ucode);
                ClassListAdapter.this.context.startActivity(intent);
            }
        });
        return view;
    }
}
