package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.element.SelectionList;

public class Underlying20ListAdapter extends ArrayAdapter<String> {
    private Context context;
    private boolean islimit20 = true;
    private String[] txts;
    private int typecode;

    public Underlying20ListAdapter(Context context2, int i, String[] strArr, int i2) {
        super(context2, i, strArr);
        this.context = context2;
        this.typecode = i2;
        this.txts = strArr;
    }

    public int getCount() {
        if (this.txts.length <= 20 || !this.islimit20) {
            return this.txts.length;
        }
        return 21;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        String str;
        View view2 = super.getView(i, (View) null, viewGroup);
        this.context.getClass();
        View inflate = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_autocomplete, (ViewGroup) null);
        TextView textView = (TextView) view2.findViewById(R.id.ac_textview);
        TextView textView2 = (TextView) inflate.findViewById(R.id.ac_textview_real);
        if (!this.islimit20 || i != 20) {
            String[] split = textView.getText().toString().split("~~");
            if (this.typecode == SelectionList.PopTypes.NAME.value) {
                str = split[0] + "-" + split[2];
            } else {
                str = split[0] + "-" + split[2];
            }
            textView2.setText(str);
        } else {
            textView2.setText(R.string.others);
        }
        return inflate;
    }

    public boolean get_islimit20() {
        return this.islimit20;
    }

    public void set_islimit20(boolean z) {
        this.islimit20 = z;
    }
}
