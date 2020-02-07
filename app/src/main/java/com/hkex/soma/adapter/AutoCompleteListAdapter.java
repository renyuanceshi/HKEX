package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.element.SelectionList;

public class AutoCompleteListAdapter extends ArrayAdapter<String> {
    private Context context;
    private int typecode;

    public AutoCompleteListAdapter(Context context2, int i, String[] strArr, int i2) {
        super(context2, i, strArr);
        this.context = context2;
        this.typecode = i2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        String str;
        View view2 = super.getView(i, (View) null, viewGroup);
        this.context.getClass();
        View inflate = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_autocomplete, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.ac_textview_real);
        String[] split = ((TextView) view2.findViewById(R.id.ac_textview)).getText().toString().split("~~");
        if (this.typecode == SelectionList.PopTypes.NAME.value) {
            str = split[0] + "-" + split[2];
        } else {
            str = split[0] + "-" + split[2];
        }
        textView.setText(str);
        return inflate;
    }
}
