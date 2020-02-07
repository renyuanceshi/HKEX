package com.hkex.soma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hkex.soma.R;
import com.hkex.soma.activity.Portfolio;
import com.hkex.soma.dataModel.MS_StocksResult;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;

public class MS_StockAdapter extends ArrayAdapter {
    /* access modifiers changed from: private */
    public Context context;
    private MS_StocksResult.mainData[] data = null;
    private String type;

    static class ViewHolder {
        private RelativeLayout icon;
        private TextView last;
        private TextView pchng;
        private ImageView searchImg;
        private TextView stock;
        private TextView turnover;

        ViewHolder() {
        }
    }

    public MS_StockAdapter(Context context2, int i, MS_StocksResult.mainData[] maindataArr, String str) {
        super(context2, i, maindataArr);
        this.context = context2;
        this.data = maindataArr;
        this.type = str;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_ms_stock, (ViewGroup) null);
            ViewHolder viewHolder2 = new ViewHolder();
            TextView unused = viewHolder2.stock = (TextView) view.findViewById(R.id.stock);
            TextView unused2 = viewHolder2.last = (TextView) view.findViewById(R.id.last);
            TextView unused3 = viewHolder2.pchng = (TextView) view.findViewById(R.id.pchng);
            TextView unused4 = viewHolder2.turnover = (TextView) view.findViewById(R.id.turnover);
            ImageView unused5 = viewHolder2.searchImg = (ImageView) view.findViewById(2131165401); // !LC
            RelativeLayout unused6 = viewHolder2.icon = (RelativeLayout) view.findViewById(R.id.iconlayout);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final String code = this.data[i].getCode();
        final String name = Commons.language.equals("en_US") ? this.data[i].getName() : this.data[i].getNmll();
        final String nmll = this.data[i].getNmll();
        final String name2 = this.data[i].getName();
        viewHolder.stock.setText(name + "\n(" + code + ")");
        viewHolder.last.setText(this.data[i].getLast());
        TextView unused7 = viewHolder.pchng = StringFormatter.formatPChng(viewHolder.pchng, this.data[i].getPchng());
        viewHolder.turnover.setText(this.data[i].getTurnover());
        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String str = name + "(" + code + ")";
                if (Portfolio.AddStockToPortfolio(MS_StockAdapter.this.context, code, nmll, name2, "0", "0", "0")) {
                    Toast.makeText(MS_StockAdapter.this.context, String.format(MS_StockAdapter.this.context.getString(R.string.portfolio_msg_stock), new Object[]{str}), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(MS_StockAdapter.this.context, String.format(MS_StockAdapter.this.context.getString(R.string.portfolio_msg_error), new Object[]{20}), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
