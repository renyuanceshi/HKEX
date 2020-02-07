package com.hkex.soma.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.hkex.soma.JSONParser.SS_ResultJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.ChartPage;
import com.hkex.soma.activity.OptionDetail;
import com.hkex.soma.activity.Portfolio;
import com.hkex.soma.activity.Search;
import com.hkex.soma.adapter.SS_ResultAdapter;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.basic.MultiScrollView;
import com.hkex.soma.dataModel.SS_Result;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class Search_Stocks extends MasterFragment {
    /* access modifiers changed from: private */
    public SS_Result.mainData[] data_array;
    /* access modifiers changed from: private */
    public View fragmentView;
    private Handler handler = new Handler();
    /* access modifiers changed from: private */
    public MultiScrollListView listView;
    /* access modifiers changed from: private */
    public MultiScrollView scrollView;
    /* access modifiers changed from: private */
    public Search searchFragmentActivity;
    /* access modifiers changed from: private */
    public SelectionList selectionListCode;
    /* access modifiers changed from: private */
    public SelectionList selectionListName;
    private String selectionListName_en = "";
    /* access modifiers changed from: private */
    public AdapterView.OnItemClickListener setOnItemClickListener;
    /* access modifiers changed from: private */
    public String ucode;
    private String uname;
    /* access modifiers changed from: private */
    public float underlyingLast = 0.0f;

    private void updateSelectionListCode() {
        this.selectionListCode.setSelectedText(this.ucode);
        this.selectionListName.setSelectedText((this.uname == null || this.uname.equals("")) ? Commons.MapUnderlyingName(this.ucode) : this.uname);
        loadJSON();
    }

    private void updateSelectionListSCode(String str) {
        String str2 = str.split(" - ")[0];
        String str3 = str.split(" - ")[1];
        this.selectionListCode.setSelectedText(str2);
        this.selectionListName.setSelectedText(str3);
        this.ucode = str2;
        loadJSON();
    }

    public void dataResult(final SS_Result sS_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (sS_Result == null) {
                        Search_Stocks.this.initNoDataListView();
                        Search_Stocks.this.searchFragmentActivity.updateFooterStime(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    } else if (sS_Result.getContingency().equals("0")) {
                        Search_Stocks.this.initNoDataListView("");
                        Search_Stocks.this.searchFragmentActivity.ContingencyMessageBox(Commons.language.equals("en_US") ? sS_Result.getEngmsg() : sS_Result.getChimsg(), true);
                    } else if (sS_Result.getContingency().equals("-1")) {
                        Search_Stocks.this.initNoDataListView("nodatayet");
                        Search_Stocks.this.searchFragmentActivity.updateFooterStime("nodatayet");
                    } else if (sS_Result.getstockInfo().length > 0) {
                        SS_Result.stockInfo stockinfo = sS_Result.getstockInfo()[0];
                        Search_Stocks.this.searchFragmentActivity.updateFooterStime(stockinfo.getStime());
                        ((TextView) Search_Stocks.this.fragmentView.findViewById(R.id.stockvalue1)).setText(stockinfo.getBid() + " / " + stockinfo.getAsk());
                        ((TextView) Search_Stocks.this.fragmentView.findViewById(R.id.stockvalue2)).setText(stockinfo.getHigh() + " / " + stockinfo.getLow());
                        ((TextView) Search_Stocks.this.fragmentView.findViewById(R.id.stockvalue3)).setText(stockinfo.getClast());
                        ((TextView) Search_Stocks.this.fragmentView.findViewById(R.id.stockvalue4)).setText(stockinfo.getTurnover() + " / " + stockinfo.getVol());
                        ((TextView) Search_Stocks.this.fragmentView.findViewById(R.id.stockvalue5)).setText(stockinfo.getSize());
                        ((TextView) Search_Stocks.this.fragmentView.findViewById(R.id.lastprice)).setText(stockinfo.getUlast());
                        float unused = Search_Stocks.this.underlyingLast = Float.parseFloat(stockinfo.getUlast());
                        ((TextView) Search_Stocks.this.fragmentView.findViewById(R.id.chngTextView)).setText(stockinfo.getChng() + "(" + stockinfo.getPchng() + "%)");
                        StringFormatter.formatChng((ImageView) Search_Stocks.this.fragmentView.findViewById(R.id.updown), stockinfo.getChng());
                        SS_Result.mainData[] unused2 = Search_Stocks.this.data_array = sS_Result.getmainData();
                        if (Search_Stocks.this.data_array.length == 0) {
                            Search_Stocks.this.initNoDataListView();
                        } else {
                            float f = Float.MAX_VALUE;
                            for (int i = 0; i < Search_Stocks.this.data_array.length; i++) {
                                if (f != Math.min(f, Math.abs(Float.parseFloat(Search_Stocks.this.data_array[i].getStrike()) - Search_Stocks.this.underlyingLast))) {
                                    f = Math.abs(Float.parseFloat(Search_Stocks.this.data_array[i].getStrike()) - Search_Stocks.this.underlyingLast);
                                    Search_Stocks.this.data_array[i].getStrike();
                                }
                            }
                            Search_Stocks.this.listView.setAdapter(new SS_ResultAdapter(Search_Stocks.this.searchFragmentActivity, R.layout.list_option_detail, Search_Stocks.this.data_array, Search_Stocks.this.ucode, MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR));
                            Search_Stocks.this.listView.setOnItemClickListener(Search_Stocks.this.setOnItemClickListener);
                            Search_Stocks.this.listView.setDividerHeight(0);
                        }
                    } else {
                        Search_Stocks.this.initNoDataListView();
                        Search_Stocks.this.searchFragmentActivity.updateFooterStime(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    }
                    Search_Stocks.this.scrollView.initMultiScrollView(Search_Stocks.this.listView, 190.0f, 177.0f);
                    ((Search) Search_Stocks.this.getActivity()).dataLoaded();
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                    Search_Stocks.this.initNoDataListView();
                    ((Search) Search_Stocks.this.getActivity()).dataLoaded();
                }
            }
        });
    }

    public void initNoDataListView() {
        initNoDataListView("");
    }

    public void initNoDataListView(String str) {
        this.listView.setAdapter(new ArrayAdapter(this.searchFragmentActivity, R.layout.list_nodata, str.equals("nodatayet") ? new String[]{getString(R.string.nodatayet)} : new String[]{getString(R.string.nooption_message)}));
        this.listView.setOnItemClickListener((AdapterView.OnItemClickListener) null);
        this.listView.setDividerHeight(0);
    }

    public void initUI() {
        this.listView = (MultiScrollListView) this.fragmentView.findViewById(R.id.listView);
        this.scrollView = (MultiScrollView) this.fragmentView.findViewById(R.id.scrollView);
        this.selectionListCode = (SelectionList) this.fragmentView.findViewById(R.id.selectionListCode);
        this.selectionListName = (SelectionList) this.fragmentView.findViewById(R.id.selectionListName);
        this.setOnItemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Commons.LogDebug("Search_Options", "onItemClick");
                Search_Stocks.this.searchFragmentActivity.dataLoading();
                Search_Stocks.this.searchFragmentActivity.backFromOptionDetail = true;
                Intent intent = new Intent(Search_Stocks.this.searchFragmentActivity, OptionDetail.class);
                intent.putExtra("oid", Search_Stocks.this.data_array[i].getOid());
                intent.putExtra("ucode", Search_Stocks.this.ucode);
                intent.putExtra("mdate", Search_Stocks.this.data_array[i].getExpiry());
                intent.putExtra("wtype", Search_Stocks.this.data_array[i].getWtype());
                intent.putExtra("strike", Search_Stocks.this.data_array[i].getStrike());
                Search_Stocks.this.searchFragmentActivity.startActivity(intent);
            }
        };
        this.listView.setOnItemClickListener(this.setOnItemClickListener);
        ((ImageView) this.fragmentView.findViewById(R.id.chartBut)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Search_Stocks.this.searchFragmentActivity, ChartPage.class);
                intent.putExtra("oid", Search_Stocks.this.ucode);
                intent.putExtra("title", Search_Stocks.this.ucode + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + Search_Stocks.this.selectionListName.getSelectedText());
                Search_Stocks.this.searchFragmentActivity.startActivity(intent);
            }
        });
        ((ImageView) this.fragmentView.findViewById(R.id.portfolioicon)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Portfolio.AddStockToPortfolio(Search_Stocks.this.searchFragmentActivity, Search_Stocks.this.selectionListCode.getSelectedText(), Commons.MapUnderlyingName(Search_Stocks.this.selectionListCode.getSelectedText(), false), Commons.MapUnderlyingName(Search_Stocks.this.selectionListCode.getSelectedText(), true), "0", "0", "0")) {
                    Search access$000 = Search_Stocks.this.searchFragmentActivity;
                    String string = Search_Stocks.this.searchFragmentActivity.getString(R.string.portfolio_msg_stock);
                    Toast.makeText(access$000, String.format(string, new Object[]{Search_Stocks.this.selectionListName.getSelectedText() + "(" + Search_Stocks.this.selectionListCode.getSelectedText() + ")"}), 1).show();
                    return;
                }
                Toast.makeText(Search_Stocks.this.searchFragmentActivity, String.format(Search_Stocks.this.searchFragmentActivity.getString(R.string.portfolio_msg_error), new Object[]{20}), 1).show();
            }
        });
        this.selectionListCode.initItems(SelectionList.PopTypes.SCODE, "code", false);
        this.selectionListName.initItems(SelectionList.PopTypes.SNAME, "name", false);
        updateSelectionListCode();
    }

    public void loadJSON() {
        SS_ResultJSONParser sS_ResultJSONParser = new SS_ResultJSONParser();
        sS_ResultJSONParser.setOnJSONCompletedListener(new SS_ResultJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(SS_Result sS_Result) {
                Commons.LogDebug("", sS_Result.toString());
                Search_Stocks.this.dataResult(sS_Result);
            }
        });
        sS_ResultJSONParser.setOnJSONFailedListener(new SS_ResultJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                ((Search) Search_Stocks.this.getActivity()).ShowConnectionErrorDialog(runnable);
                Search_Stocks.this.dataResult((SS_Result) null);
            }
        });
        sS_ResultJSONParser.loadXML(getString(R.string.url_quote_optionsresult) + "?type=stockInfo&ucode=" + this.ucode);
        ((Search) getActivity()).dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.searchFragmentActivity = (Search) getActivity();
        this.searchFragmentActivity.setStocksFragment(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.searchstocks, viewGroup, false);
        this.ucode = this.searchFragmentActivity.getScode();
        this.uname = this.searchFragmentActivity.getSname();
        initUI();
        return this.fragmentView;
    }

    public void selectionListCallBack(SelectionList.PopTypes popTypes, String str) {
        switch (popTypes) {
            case SCODE:
                updateSelectionListSCode(str);
                return;
            case SNAME:
                updateSelectionListSCode(str);
                return;
            default:
                return;
        }
    }
}
