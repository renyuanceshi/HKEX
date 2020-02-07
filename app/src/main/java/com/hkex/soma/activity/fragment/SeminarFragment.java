package com.hkex.soma.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.SeminarDetail;
import com.hkex.soma.adapter.SeminarListAdapter;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.dataModel.Seminar_Result;
import com.hkex.soma.element.AppFooter;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideRightAnimationHandler;
import com.hkex.soma.utils.Commons;
import java.util.ArrayList;

public class SeminarFragment extends MasterFragment {
    public static final String TAG = "SeminarFragment";
    private static SeminarFragment fragment;
    /* access modifiers changed from: private */
    public AnimatedFragmentActivity _self;
    private AppFooter appfooter;
    /* access modifiers changed from: private */
    public ArrayList<Seminar_Result.mainData> data = new ArrayList<>();
    private LinearLayout footerContainer;
    private View fragmentView;
    private Handler handler = new Handler();
    private ImageButton leftbtn;
    /* access modifiers changed from: private */
    public ListView listview;
    private MenuContainer menu;
    private ImageButton rightbtn;
    /* access modifiers changed from: private */
    public SeminarListAdapter seminarlistadapter;
    private SlideRightAnimationHandler slideRightAnimationHandler;

    private void getview() {
        this.listview = (ListView) this.fragmentView.findViewById(R.id.listview);
        this.seminarlistadapter = new SeminarListAdapter(this._self, this.data);
        this.listview.setEmptyView(this.fragmentView.findViewById(R.id.simaple_list_text));
        this.listview.setAdapter(this.seminarlistadapter);
    }

    public static SeminarFragment newInstance() {
        fragment = new SeminarFragment();
        return fragment;
    }

    private void setlistener() {
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(SeminarFragment.this._self, SeminarDetail.class);
                intent.putExtra("id", ((Seminar_Result.mainData) SeminarFragment.this.data.get(i)).getSid());
                SeminarFragment.this._self.startActivity(intent);
            }
        });
    }

    public void dataResult(final Seminar_Result seminar_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    SeminarFragment.this.data.clear();
                    for (Seminar_Result.mainData add : seminar_Result.getMainData()) {
                        SeminarFragment.this.data.add(add);
                    }
                    SeminarFragment.this.seminarlistadapter.setdata(SeminarFragment.this.data);
                    SeminarFragment.this.listview.setAdapter(SeminarFragment.this.seminarlistadapter);
                    SeminarFragment.this.seminarlistadapter.notifyDataSetChanged();
                    SeminarFragment.this._self.dataLoaded();
                } catch (Exception e) {
                    SeminarFragment.this._self.dataLoaded();
                    e.printStackTrace();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initFooter() {
        try {
            this.appfooter = new AppFooter(this._self, false);
            this.footerContainer = (LinearLayout) this.fragmentView.findViewById(R.id.footerContainer);
            this.footerContainer.addView(this.appfooter);
        } catch (Exception e) {
            Commons.LogDebug("initFooter", e.getMessage());
        }
    }

    public void initNoDataListView() {
        getString(R.string.noseminar_message);
        this.listview.setEmptyView(this.fragmentView.findViewById(R.id.simaple_list_text));
        this.listview.setOnItemClickListener((AdapterView.OnItemClickListener) null);
        this.listview.setDividerHeight(0);
    }

    public void loadJSON() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(Seminar_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<Seminar_Result>() {
            public void OnJSONCompleted(Seminar_Result seminar_Result) {
                SeminarFragment.this.dataResult(seminar_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                SeminarFragment.this.dataResult((Seminar_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_seminar));
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
        this.fragmentView = layoutInflater.inflate(R.layout.seminar_list_frag, viewGroup, false);
        try {
            getview();
            setlistener();
            loadJSON();
            initFooter();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.fragmentView;
    }
}
