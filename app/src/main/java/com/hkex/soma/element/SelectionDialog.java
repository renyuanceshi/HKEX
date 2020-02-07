package com.hkex.soma.element;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.adapter.SimpleDialogAdapter;

public class SelectionDialog extends CustomDialog {
    protected Context context;
    private View dialoglayout;
    private OnDialogItemClickListener onDialogItemClickListener = null;
    private SimpleDialogAdapter simpleDialogAdapter;

    public interface OnDateSelectedListener {
        void onDateSelected(int i, int i2, int i3);
    }

    public interface OnDialogItemClickListener {
        void onDialogItemClicked(int i);
    }

    public SelectionDialog(Context context2, String[] strArr) {
        super(context2);
        this.context = context2;
        initListLayout(strArr);
    }

    public void initListLayout(String[] strArr) {
        RelativeLayout relativeLayout = new RelativeLayout(this.context);
        this.dialoglayout = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_list, relativeLayout, false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        if ((((double) this.context.getResources().getDisplayMetrics().widthPixels) * 0.8d) / ((double) this.context.getResources().getDisplayMetrics().density) < 270.0d) {
            layoutParams.width = (int) Math.min(((double) this.context.getResources().getDisplayMetrics().widthPixels) * 0.9d, (double) (this.context.getResources().getDisplayMetrics().density * 320.0f));
        } else {
            layoutParams.width = (int) Math.min(((double) this.context.getResources().getDisplayMetrics().widthPixels) * 0.8d, (double) (this.context.getResources().getDisplayMetrics().density * 320.0f));
        }
        layoutParams.height = (int) (360.0f * this.context.getResources().getDisplayMetrics().density);
        layoutParams.addRule(13, this.dialoglayout.getId());
        relativeLayout.addView(this.dialoglayout, layoutParams);
        setContentView(relativeLayout);
        getWindow().setLayout(-1, -1);
        ListView listView = (ListView) this.dialoglayout.findViewById(R.id.dialog_list);
        this.simpleDialogAdapter = new SimpleDialogAdapter(this.context, R.layout.list_dialog_simaple, strArr, 0);
        listView.setAdapter(this.simpleDialogAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (SelectionDialog.this.onDialogItemClickListener != null) {
                    SelectionDialog.this.simpleDialogAdapter.setSelectedItem(i);
                    SelectionDialog.this.onDialogItemClickListener.onDialogItemClicked(i);
                }
            }
        });
    }

    public void isForExpiry() {
        this.simpleDialogAdapter.isForExpiry();
    }

    public void setAdapterSelectedId(int i) {
        this.simpleDialogAdapter.setSelectedItem(i);
    }

    public void setOnDialogItemClickListener(OnDialogItemClickListener onDialogItemClickListener2) {
        this.onDialogItemClickListener = onDialogItemClickListener2;
    }

    public void setTitle(int i) {
        ((RelativeLayout) this.dialoglayout.findViewById(R.id.dialog_titlelayout)).setVisibility(View.VISIBLE);
        ((TextView) this.dialoglayout.findViewById(R.id.dialog_title)).setText(i);
    }
}
