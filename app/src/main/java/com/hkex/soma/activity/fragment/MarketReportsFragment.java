package com.hkex.soma.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.adapter.MarketReportsListAdapter;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.dataModel.MarketReport_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideRightAnimationHandler;
import com.hkex.soma.utils.Commons;
import java.util.ArrayList;

public class MarketReportsFragment extends MasterFragment {
    public static final String TAG = "MarketReportsFragment";
    private static MarketReportsFragment fragment;
    /* access modifiers changed from: private */
    public AnimatedFragmentActivity _self;
    /* access modifiers changed from: private */
    public ArrayList<MarketReport_Result.mainData> data = new ArrayList<>();
    private View fragmentView;
    private Handler handler = new Handler();
    private ImageButton leftbtn;
    private ListView listview;
    /* access modifiers changed from: private */
    public MarketReportsListAdapter marketreportslistadapter;
    private MenuContainer menu;
    private ImageButton rightbtn;
    private SlideRightAnimationHandler slideRightAnimationHandler;

    private void getview() {
        this.listview = (ListView) this.fragmentView.findViewById(R.id.listview);
        this.marketreportslistadapter = new MarketReportsListAdapter(this._self, this.data);
        this.listview.setEmptyView(this.fragmentView.findViewById(R.id.simaple_list_text));
        this.listview.setAdapter(this.marketreportslistadapter);
    }

    public static MarketReportsFragment newInstance() {
        fragment = new MarketReportsFragment();
        return fragment;
    }

    private void setlistener() {
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Commons.LogDebug("setWebViewClient", "Open PDF: " + ((MarketReport_Result.mainData) MarketReportsFragment.this.data.get(i)).getFile());
                Commons.noResumeAction = true;
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(((MarketReport_Result.mainData) MarketReportsFragment.this.data.get(i)).getFile()));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MarketReportsFragment.this._self.startActivity(intent);
            }
        });
    }

    public void dataResult(final MarketReport_Result marketReport_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    MarketReportsFragment.this._self.dataLoaded();
                    MarketReportsFragment.this.data.clear();
                    for (MarketReport_Result.mainData add : marketReport_Result.getMainData()) {
                        MarketReportsFragment.this.data.add(add);
                    }
                    MarketReportsFragment.this.marketreportslistadapter.setdata(MarketReportsFragment.this.data);
                    MarketReportsFragment.this.marketreportslistadapter.notifyDataSetChanged();
                } catch (Exception e) {
                    MarketReportsFragment.this._self.dataLoaded();
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadJSON() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(MarketReport_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<MarketReport_Result>() {
            public void OnJSONCompleted(MarketReport_Result marketReport_Result) {
                MarketReportsFragment.this.dataResult(marketReport_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                MarketReportsFragment.this.dataResult((MarketReport_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_market_report));
        this._self.dataLoading();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._self = (AnimatedFragmentActivity) getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.market_reports_list_frag, viewGroup, false);
        try {
            getview();
            setlistener();
            loadJSON();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.fragmentView;
    }
}
