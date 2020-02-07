package com.hkex.soma.activity.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hkex.soma.JSONParser.MS_IndicsJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.MarketSnapshot;
import com.hkex.soma.adapter.MS_IndicesAdapter;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.dataModel.MS_IndicesResult;
import com.hkex.soma.utils.Commons;
import java.net.URL;
import java.util.concurrent.Executors;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class MS_Indices extends MasterFragment {
    /* access modifiers changed from: private */
    public MarketSnapshot MSFragmentActivity;
    /* access modifiers changed from: private */
    public Bitmap chartImageBmp;
    /* access modifiers changed from: private */
    public TextView chartTitle;
    /* access modifiers changed from: private */
    public WebView chart_webview;
    /* access modifiers changed from: private */
    public TextView chng;
    /* access modifiers changed from: private */
    public MS_IndicesResult.mainData[] data;
    /* access modifiers changed from: private */
    public View fragmentView;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    /* access modifiers changed from: private */
    public String indexName = "";
    /* access modifiers changed from: private */
    public TextView last;
    /* access modifiers changed from: private */
    public ListView listView;
    /* access modifiers changed from: private */
    public String ucode = "hsi";

    private void loadImageFromWeb(final String str) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                synchronized (this) {
                    try {
                        Bitmap unused = MS_Indices.this.chartImageBmp = BitmapFactory.decodeStream(new URL(str).openConnection().getInputStream());
                    } catch (Exception e) {
                        Bitmap unused2 = MS_Indices.this.chartImageBmp = null;
                        Commons.LogDebug(toString(), e.getMessage());
                    }
                    MS_Indices.this.handler.post(new Runnable() {
                        public void run() {
                            try {
                                ((ImageView) MS_Indices.this.fragmentView.findViewById(R.id.chartImage)).setImageBitmap(MS_Indices.this.chartImageBmp);
                                ((TextView) MS_Indices.this.fragmentView.findViewById(R.id.chartTitle)).setText(MS_Indices.this.indexName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + MS_Indices.this.getString(R.string.chart));
                            } catch (Exception e) {
                                Commons.LogDebug(toString(), e.getMessage());
                            }
                        }
                    });
                }
                return;
            }
        });
    }

    public void dataResult(final MS_IndicesResult mS_IndicesResult) {
        if (mS_IndicesResult != null) {
            this.data = mS_IndicesResult.getmainData();
        }
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (mS_IndicesResult == null) {
                        MS_Indices.this.MSFragmentActivity.updateFooterStime(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                        MS_Indices.this.listView.setAdapter(new ArrayAdapter(MS_Indices.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_Indices.this.MSFragmentActivity.getString(R.string.nodata_message)}));
                    } else if (mS_IndicesResult.getContingency().equals("0")) {
                        MS_Indices.this.MSFragmentActivity.ContingencyMessageBox(Commons.language.equals("en_US") ? mS_IndicesResult.getEngmsg() : mS_IndicesResult.getChimsg(), true);
                        MS_Indices.this.listView.setAdapter(new ArrayAdapter(MS_Indices.this.MSFragmentActivity, R.layout.list_nodata, new String[]{MS_Indices.this.MSFragmentActivity.getString(R.string.nodata_message)}));
                    } else {
                        ((RelativeLayout) MS_Indices.this.fragmentView.findViewById(R.id.chartContainer)).setVisibility(View.VISIBLE);
                        MS_Indices.this.listView.setAdapter(new MS_IndicesAdapter(MS_Indices.this.MSFragmentActivity, R.layout.list_ms_stockoptions, MS_Indices.this.data));
                        MS_Indices.this.listView.setDividerHeight(0);
                        MS_Indices.this.MSFragmentActivity.updateFooterStime(mS_IndicesResult.getstime());
                        String unused = MS_Indices.this.indexName = mS_IndicesResult.getName();
                        String chartText = mS_IndicesResult.getChartText();
                        ((TextView) MS_Indices.this.fragmentView.findViewById(R.id.indexMsg)).setText("");
                        ((ImageView) MS_Indices.this.fragmentView.findViewById(R.id.chartImage)).setImageBitmap((Bitmap) null);
                        if (chartText.equals("")) {
                            ((TextView) MS_Indices.this.fragmentView.findViewById(R.id.chartTitle)).setText(MS_Indices.this.indexName);
                            Log.v("", "chart_webview " + MS_Indices.this.getString(R.string.url_indices_chart_webview) + "&index=" + MS_Indices.this.ucode);
                        } else {
                            ((TextView) MS_Indices.this.fragmentView.findViewById(R.id.indexMsg)).setText(chartText);
                            ((TextView) MS_Indices.this.fragmentView.findViewById(R.id.chartTitle)).setText(MS_Indices.this.indexName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + MS_Indices.this.getString(R.string.chart));
                        }
                        MS_Indices.this.chartTitle.setText(MS_Indices.this.data[0].getName());
                        MS_Indices.this.last.setText(MS_Indices.this.data[0].getLast());
                        TextView access$400 = MS_Indices.this.chng;
                        access$400.setText(MS_Indices.this.data[0].getChng() + "(" + MS_Indices.this.data[0].getPchng() + "%)");
                        try {
                            if (Double.parseDouble(MS_Indices.this.data[0].getChng()) >= 0.0d) {
                                MS_Indices.this.chng.setTextColor(MS_Indices.this.getResources().getColor(R.color.change_green));
                            } else {
                                MS_Indices.this.chng.setTextColor(MS_Indices.this.getResources().getColor(R.color.change_red));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            MS_Indices.this.chng.setText("-(-)");
                            MS_Indices.this.chng.setTextColor(MS_Indices.this.getResources().getColor(R.color.textblue));
                        }
                        WebView access$500 = MS_Indices.this.chart_webview;
                        access$500.loadUrl(MS_Indices.this.getString(R.string.url_domain) + MS_Indices.this.data[0].getChart());
                    }
                    MS_Indices.this.MSFragmentActivity.showFooterIndicesDisclaimer();
                    MS_Indices.this.MSFragmentActivity.dataLoaded();
                } catch (Exception e2) {
                    Commons.LogDebug(toString(), e2.getMessage());
                }
            }
        });
    }

    public void initUI() {
        this.chart_webview = (WebView) this.fragmentView.findViewById(R.id.chart_webview);
        this.chart_webview.getSettings().setJavaScriptEnabled(true);
        this.chart_webview.getSettings().setDisplayZoomControls(false);
        this.chart_webview.getSettings().setBuiltInZoomControls(false);
        this.chart_webview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    MS_Indices.this.MSFragmentActivity.setClickControlCanSwipe(false);
                } else if (motionEvent.getAction() == 1) {
                    MS_Indices.this.MSFragmentActivity.setClickControlCanSwipe(true);
                } else if (motionEvent.getAction() == 2) {
                    MS_Indices.this.MSFragmentActivity.setClickControlCanSwipe(false);
                }
                return false;
            }
        });
        this.chartTitle = (TextView) this.fragmentView.findViewById(R.id.chartTitle);
        this.last = (TextView) this.fragmentView.findViewById(R.id.last);
        this.chng = (TextView) this.fragmentView.findViewById(R.id.chng);
        this.listView = (ListView) this.fragmentView.findViewById(R.id.listView);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                MS_Indices.this.chartTitle.setText(MS_Indices.this.data[i].getName());
                MS_Indices.this.last.setText(MS_Indices.this.data[i].getLast());
                TextView access$400 = MS_Indices.this.chng;
                access$400.setText(MS_Indices.this.data[i].getChng() + "(" + MS_Indices.this.data[i].getPchng() + "%)");
                try {
                    if (Double.parseDouble(MS_Indices.this.data[i].getChng()) >= 0.0d) {
                        MS_Indices.this.chng.setTextColor(MS_Indices.this.getResources().getColor(R.color.change_green));
                    } else {
                        MS_Indices.this.chng.setTextColor(MS_Indices.this.getResources().getColor(R.color.change_red));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    MS_Indices.this.chng.setText("-(-)");
                    MS_Indices.this.chng.setTextColor(MS_Indices.this.getResources().getColor(R.color.textblue));
                }
                WebView access$500 = MS_Indices.this.chart_webview;
                access$500.loadUrl(MS_Indices.this.getString(R.string.url_domain) + MS_Indices.this.data[i].getChart());
            }
        });
    }

    public void loadJSON(String str) {
        this.ucode = str;
        MS_IndicsJSONParser mS_IndicsJSONParser = new MS_IndicsJSONParser();
        mS_IndicsJSONParser.setOnJSONCompletedListener(new MS_IndicsJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(MS_IndicesResult mS_IndicesResult) {
                MS_Indices.this.dataResult(mS_IndicesResult);
            }
        });
        mS_IndicsJSONParser.setOnJSONFailedListener(new MS_IndicsJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                MS_Indices.this.MSFragmentActivity.ShowConnectionErrorDialog(runnable);
                MS_Indices.this.dataResult((MS_IndicesResult) null);
            }
        });
        mS_IndicsJSONParser.loadXML(getString(R.string.url_market_indices) + "&type=getTable&index=" + str);
        Commons.LogDebug("MS_Indices", "loadJSON Path:" + getString(R.string.url_market_indices) + "&type=getHtml&index=" + str);
        this.MSFragmentActivity.dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.MSFragmentActivity = (MarketSnapshot) getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.msindex, viewGroup, false);
        initUI();
        loadJSON("hsi");
        return this.fragmentView;
    }
}
