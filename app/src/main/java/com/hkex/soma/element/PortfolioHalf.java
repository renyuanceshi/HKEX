package com.hkex.soma.element;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hkex.soma.JSONParser.PortfolioJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.OptionDetail;
import com.hkex.soma.activity.Portfolio;
import com.hkex.soma.activity.Portfolio_land;
import com.hkex.soma.activity.Search;
import com.hkex.soma.adapter.Portfolio_Adapter;
import com.hkex.soma.basic.MasterFragmentActivity;
import com.hkex.soma.dataModel.Portfolio_Result;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PortfolioHalf extends LinearLayout {
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public List<Portfolio_Result.mainData> data_array = null;
    private LinearLayout footerContainer;
    private Handler handler = new Handler();
    /* access modifiers changed from: private */
    public ListView listView;
    private View portfolio;
    /* access modifiers changed from: private */
    public String portfolioRawData;

    public PortfolioHalf(Context context2) {
        super(context2);
        this.context = context2;
        this.portfolioRawData = SharedPrefsUtil.getValue(this.context, "portfolio", "");
        this.portfolio = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.portfolio_half, this, false);
        this.footerContainer = (LinearLayout) this.portfolio.findViewById(R.id.footerContainer_portfolio);
        this.listView = (ListView) this.portfolio.findViewById(R.id.portfoliolistView);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ((MasterFragmentActivity) PortfolioHalf.this.context).halfPortfolioUpdated();
                if (((Portfolio_Result.mainData) PortfolioHalf.this.data_array.get(i)).getUcode() == null || ((Portfolio_Result.mainData) PortfolioHalf.this.data_array.get(i)).getUcode().equals("")) {
                    PortfolioHalf.this.goStockDetail(i);
                } else {
                    PortfolioHalf.this.goOptionDetail(i);
                }
            }
        });
        addView(this.portfolio);
        initUI();
    }

    /* access modifiers changed from: private */
    public void goOptionDetail(int i) {
        String str = this.data_array.get(i).getType() == "Call" ? "C" : "P";
        Intent intent = new Intent(this.context, OptionDetail.class);
        intent.putExtra("oid", this.data_array.get(i).getOid());
        intent.putExtra("ucode", this.data_array.get(i).getUcode());
        intent.putExtra("uname", this.data_array.get(i).getUName());
        intent.putExtra("mdate", this.data_array.get(i).getExpiry());
        intent.putExtra("wtype", str);
        intent.putExtra("strike", this.data_array.get(i).getStrike());
        intent.putExtra("index", "0");
        intent.putExtra("row", i);
        ((MasterFragmentActivity) this.context).startActivity(intent);
    }

    /* access modifiers changed from: private */
    public void goStockDetail(int i) {
        Intent intent = new Intent(this.context, Search.class);
        intent.putExtra("uname", this.data_array.get(i).getUName());
        intent.putExtra("ucode", this.data_array.get(i).getCode());
        intent.putExtra("index", "2");
        intent.putExtra("row", i);
        ((MasterFragmentActivity) this.context).startActivity(intent);
    }

    public void dataResult(final Portfolio_Result portfolio_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (portfolio_Result != null) {
                        List unused = PortfolioHalf.this.data_array = new ArrayList(Arrays.asList(portfolio_Result.getmainData()));
                        Portfolio.updatePortfolioResult(PortfolioHalf.this.context, PortfolioHalf.this.portfolioRawData, PortfolioHalf.this.data_array, false);
                    } else {
                        List unused2 = PortfolioHalf.this.data_array = new ArrayList();
                        Portfolio.updatePortfolioResult(PortfolioHalf.this.context, PortfolioHalf.this.portfolioRawData, PortfolioHalf.this.data_array, true);
                    }
                    if (portfolio_Result == null) {
                        PortfolioHalf.this.updateFooterStime("");
                    } else if (portfolio_Result.getContingency().equals("0")) {
                        ((MasterFragmentActivity) PortfolioHalf.this.context).ContingencyMessageBox(Commons.language.equals("en_US") ? portfolio_Result.getEngmsg() : portfolio_Result.getChimsg(), true);
                    } else if (portfolio_Result.getContingency().equals("-1")) {
                        PortfolioHalf.this.updateFooterStime("nodatayet");
                    } else {
                        PortfolioHalf.this.updateFooterStime(portfolio_Result.getstime());
                    }
                    PortfolioHalf.this.listView.setAdapter(new Portfolio_Adapter(PortfolioHalf.this.context, R.layout.list_portfolio_half, PortfolioHalf.this.data_array));
                    ((MasterFragmentActivity) PortfolioHalf.this.context).dataLoaded();
                    ((MasterFragmentActivity) PortfolioHalf.this.context).halfPortfolioUpdated();
                } catch (Exception e) {
                    e.printStackTrace();
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initUI() {
        ImageButton imageButton = (ImageButton) findViewById(R.id.btnRight);
        if (Commons.language.equals("en_US")) {
            imageButton.setImageResource(R.drawable.btn_fullmode_e);
        } else if (Commons.language.equals("zh_CN")) {
            imageButton.setImageResource(R.drawable.btn_fullmode_gb);
        } else {
            imageButton.setImageResource(R.drawable.btn_fullmode_c);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((MasterFragmentActivity) PortfolioHalf.this.context).goActivity(Portfolio_land.class);
            }
        });
        try {
            AppFooter appFooter = new AppFooter(this.context, true);
            this.footerContainer = (LinearLayout) this.portfolio.findViewById(R.id.footerContainer_portfolio);
            this.footerContainer.addView(appFooter);
        } catch (Exception e) {
            Commons.LogDebug("initFooter", e.getMessage());
        }
    }

    public void loadJSON() {
        this.portfolioRawData = SharedPrefsUtil.getValue(this.context, "portfolio", "");
        PortfolioJSONParser portfolioJSONParser = new PortfolioJSONParser();
        portfolioJSONParser.setOnJSONCompletedListener(new PortfolioJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(Portfolio_Result portfolio_Result) {
                PortfolioHalf.this.dataResult(portfolio_Result);
            }
        });
        portfolioJSONParser.setOnJSONFailedListener(new PortfolioJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                exc.printStackTrace();
                ((MasterFragmentActivity) PortfolioHalf.this.context).ShowConnectionErrorDialog(runnable);
                PortfolioHalf.this.dataResult((Portfolio_Result) null);
            }
        });
        String[] split = this.portfolioRawData.split(",");
        String str = "";
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("")) {
                String[] split2 = split[i].split("!");
                str = str + "%7C" + split2[0];
            }
        }
        String replaceFirst = str.replaceFirst("%7C", "");
        Log.v("portfolioRawData", "url " + this.context.getResources().getString(R.string.url_portfolio) + "?codelist=" + replaceFirst);
        StringBuilder sb = new StringBuilder();
        sb.append(this.context.getResources().getString(R.string.url_portfolio));
        sb.append("?codelist=");
        sb.append(replaceFirst);
        portfolioJSONParser.loadXML(sb.toString());
        ((MasterFragmentActivity) this.context).dataLoading();
    }

    /* access modifiers changed from: protected */
    public void updateFooterStime(String str) {
        try {
            TextView textView = (TextView) this.footerContainer.findViewById(R.id.tv_stime);
            TextView textView2 = (TextView) this.footerContainer.findViewById(R.id.tv_delay);
            TextView textView3 = (TextView) this.footerContainer.findViewById(R.id.tv_update);
            RelativeLayout relativeLayout = (RelativeLayout) this.footerContainer.findViewById(R.id.timebar);
            textView2.setVisibility(View.VISIBLE);
            if (str.equals("nodatayet")) {
                textView2.setVisibility(View.GONE);
                textView3.setVisibility(View.GONE);
                relativeLayout.getLayoutParams().height = (int) (20.0f * this.context.getResources().getDisplayMetrics().density);
                textView.setText(R.string.nodatayet);
                return;
            }
            textView.setText(str);
        } catch (Exception e) {
            Commons.LogDebug("updateFooterStime", e.getMessage());
        }
    }
}
