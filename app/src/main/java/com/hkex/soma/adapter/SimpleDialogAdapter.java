package com.hkex.soma.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.utils.StringFormatter;

public class SimpleDialogAdapter extends ArrayAdapter<String> {
    private Context context;
    private boolean isExpiry = false;
    private int selecteditem = 0;

    public SimpleDialogAdapter(Context context2, int i, String[] strArr, int i2) {
        super(context2, i, strArr);
        this.context = context2;
        this.selecteditem = i2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = super.getView(i, view, viewGroup);
        TextView textView = (TextView) view2.findViewById(R.id.simaple_list_text);
        Resources resources = this.context.getResources();
        if (this.selecteditem == i) {
            textView.setTextColor(resources.getColor(R.color.textlightblue));
        } else {
            textView.setTextColor(resources.getColor(R.color.textdeepblue));
        }
        if (this.isExpiry) {
            textView.setText(StringFormatter.formatExpiry(textView.getText().toString()));
        }
        textView.setTextSize(18.0f);
        textView.setTypeface((Typeface) null, 1);
        return view2;
    }

    public void isForExpiry() {
        this.isExpiry = true;
    }

    public void setSelectedItem(int i) {
        this.selecteditem = i;
    }
}
