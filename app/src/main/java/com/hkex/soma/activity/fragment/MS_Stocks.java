package com.hkex.soma.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.hkex.soma.JSONParser.MS_StockJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.MarketSnapshot;
import com.hkex.soma.activity.Search;
import com.hkex.soma.adapter.MS_StockAdapter;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.basic.MultiScrollView;
import com.hkex.soma.dataModel.MS_StocksResult;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class MS_Stocks extends MasterFragment {
    /* access modifiers changed from: private */
    public MarketSnapshot MSFragmentActivity;
    /* access modifiers changed from: private */
    public MS_StocksResult.mainData[] data;
    private View fragmentView;
    private Handler handler = new Handler();
    /* access modifiers changed from: private */
    public MultiScrollListView listView;
    /* access modifiers changed from: private */
    public boolean noItemClick = false;
    /* access modifiers changed from: private */
    public MultiScrollView scrollView;
    /* access modifiers changed from: private */
    public int tagIndex = 0;
    /* access modifiers changed from: private */
    public String[] type_array = {"turn", "gain", "loss"};

    public void dataResult(final MS_StocksResult mS_StocksResult) {
        if (mS_StocksResult != null) {
            this.data = mS_StocksResult.getmainData();
        }
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    boolean unused = MS_Stocks.this.noItemClick = false;
                    String str = mS_StocksResult == null ? MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR : mS_StocksResult.getstime();
                    if (mS_StocksResult == null) {
                        MS_Stocks.this.listView.setAdapter(new ArrayAdapter(MS_Stocks.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_Stocks.this.MSFragmentActivity.getString(R.string.nodata_message)}));
                        boolean unused2 = MS_Stocks.this.noItemClick = true;
                    } else if (mS_StocksResult.getContingency().equals("0")) {
                        MS_Stocks.this.MSFragmentActivity.ContingencyMessageBox(Commons.language.equals("en_US") ? mS_StocksResult.getEngmsg() : mS_StocksResult.getChimsg(), true);
                        MS_Stocks.this.listView.setAdapter(new ArrayAdapter(MS_Stocks.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_Stocks.this.MSFragmentActivity.getString(R.string.nodata_message)}));
                        boolean unused3 = MS_Stocks.this.noItemClick = true;
                    } else if (mS_StocksResult.getContingency().equals("-1")) {
                        MS_Stocks.this.listView.setAdapter(new ArrayAdapter(MS_Stocks.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_Stocks.this.MSFragmentActivity.getString(R.string.nodatayet)}));
                        str = "nodatayet";
                        boolean unused4 = MS_Stocks.this.noItemClick = true;
                    } else {
                        MS_Stocks.this.listView.setAdapter(new MS_StockAdapter(MS_Stocks.this.MSFragmentActivity, R.layout.list_ms_stock, MS_Stocks.this.data, MS_Stocks.this.type_array[MS_Stocks.this.tagIndex]));
                        MS_Stocks.this.listView.setDividerHeight(0);
                        MS_Stocks.this.scrollView.initMultiScrollView(MS_Stocks.this.listView, 190.0f, 158.0f);
                    }
                    MS_Stocks.this.MSFragmentActivity.dataLoaded();
                    MS_Stocks.this.MSFragmentActivity.updateFooterStime(str);
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void initUI() {
        this.listView = (MultiScrollListView) this.fragmentView.findViewById(R.id.listView);
        this.scrollView = (MultiScrollView) this.fragmentView.findViewById(2131165570);
        SelectionList selectionList = (SelectionList) this.fragmentView.findViewById(R.id.selectionList);
        String string = getString(R.string.sel_topturnover);
        String string2 = getString(R.string.sel_topgain);
        String string3 = getString(R.string.sel_toploss);
        String[] strArr = {string, string2, string3};
        selectionList.initItems(strArr, R.string.tab_stocks, SelectionList.PopTypes.LIST, this.tagIndex);
        selectionList.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                MS_Stocks mS_Stocks = MS_Stocks.this;
                MS_Stocks.this.MSFragmentActivity.stockTab = i;
                int unused = mS_Stocks.tagIndex = i;
                MS_Stocks.this.loadJSON(MS_Stocks.this.type_array[MS_Stocks.this.tagIndex]);
            }
        });
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!MS_Stocks.this.noItemClick) {
                    MS_Stocks.this.MSFragmentActivity.dataLoading();
                    Intent intent = new Intent(MS_Stocks.this.MSFragmentActivity, Search.class);
                    intent.putExtra("index", "2");
                    if (Commons.language.equals("en_US")) {
                        intent.putExtra("uname", MS_Stocks.this.data[i].getName());
                    } else {
                        intent.putExtra("uname", MS_Stocks.this.data[i].getNmll());
                    }
                    intent.putExtra("ucode", MS_Stocks.this.data[i].getCode());
                    MS_Stocks.this.MSFragmentActivity.startActivity(intent);
                }
            }
        });
    }

    public void loadJSON(String str) {
        MS_StockJSONParser mS_StockJSONParser = new MS_StockJSONParser();
        mS_StockJSONParser.setOnJSONCompletedListener(new MS_StockJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(MS_StocksResult mS_StocksResult) {
                Commons.LogDebug("", mS_StocksResult.toString());
                MS_Stocks.this.dataResult(mS_StocksResult);
            }
        });
        mS_StockJSONParser.setOnJSONFailedListener(new MS_StockJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_Stocks.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
                MS_Stocks.this.dataResult((MS_StocksResult) null);
            }
        });
        mS_StockJSONParser.loadXML(getString(R.string.url_market_stock) + "?type=" + str);
        this.MSFragmentActivity.dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.MSFragmentActivity = (MarketSnapshot) getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.msstock, viewGroup, false);
        this.tagIndex = this.MSFragmentActivity.stockTab;
        initUI();
        loadJSON(this.type_array[this.tagIndex]);
        return this.fragmentView;
    }
}
