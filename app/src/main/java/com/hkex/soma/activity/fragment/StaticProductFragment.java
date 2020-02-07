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
import android.widget.ListView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.WebViewPage;
import com.hkex.soma.adapter.StaticProductListAdapter;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.dataModel.StaticProductTable_Result;
import com.hkex.soma.dataModel.StaticProductType_Result;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideRightAnimationHandler;
import com.hkex.soma.utils.Commons;
import java.util.ArrayList;

public class StaticProductFragment extends MasterFragment {
    public static final String TAG = "StaticProductFragment";
    private static StaticProductFragment fragment;
    private AnimatedFragmentActivity animatedfragmentactivity;
    private View fragmentView;
    private Handler handler = new Handler();
    private String[] index_items;
    private SelectionList index_selectionList;
    private ListView listview;
    private SlideRightAnimationHandler slideRightAnimationHandler;
    private StaticProductListAdapter staticproductlistadapter;
    private ArrayList<StaticProductTable_Result.mainData> table_data = new ArrayList<>();
    private ArrayList<StaticProductType_Result.mainData> type_data = new ArrayList<>();
    private int ucodeIndex = 0;

    private void getview() {
        this.index_selectionList = (SelectionList) this.fragmentView.findViewById(R.id.index_selectionList);
        this.index_items = new String[0];
        this.listview = (ListView) this.fragmentView.findViewById(R.id.listview);
        this.staticproductlistadapter = new StaticProductListAdapter(this.animatedfragmentactivity, this.table_data);
        this.listview.setAdapter(this.staticproductlistadapter);
    }

    public static StaticProductFragment newInstance() {
        fragment = new StaticProductFragment();
        return fragment;
    }

    private void setlistener() {
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (((StaticProductTable_Result.mainData) StaticProductFragment.this.table_data.get(i)).getType().equals("Inapp")) {
                    Intent intent = new Intent().setClass(StaticProductFragment.this.animatedfragmentactivity, WebViewPage.class);
                    intent.putExtra("title", StaticProductFragment.this.getString(R.string.products));
                    intent.putExtra("url", ((StaticProductTable_Result.mainData) StaticProductFragment.this.table_data.get(i)).getLink());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Commons.noResumeAction = true;
                    StaticProductFragment.this.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(((StaticProductTable_Result.mainData) StaticProductFragment.this.table_data.get(i)).getLink()));
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Commons.noResumeAction = true;
                StaticProductFragment.this.startActivity(intent2);
            }
        });
    }

    public void dataResult(final StaticProductType_Result staticProductType_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    StaticProductFragment.this.type_data.clear();
                    for (StaticProductType_Result.mainData add : staticProductType_Result.getMainData()) {
                        StaticProductFragment.this.type_data.add(add);
                    }
                    String[] unused = StaticProductFragment.this.index_items = new String[StaticProductFragment.this.type_data.size()];
                    for (int i = 0; i < StaticProductFragment.this.index_items.length; i++) {
                        StaticProductFragment.this.index_items[i] = ((StaticProductType_Result.mainData) StaticProductFragment.this.type_data.get(i)).getName();
                    }
                    StaticProductFragment.this.index_selectionList.initItems(StaticProductFragment.this.index_items, R.string.products_type, SelectionList.PopTypes.LIST, StaticProductFragment.this.ucodeIndex);
                    StaticProductFragment.this.index_selectionList.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
                        public void onListItemChanged(int i) {
                            int unused = StaticProductFragment.this.ucodeIndex = i;
                            StaticProductFragment.this.loadJSON_detail(((StaticProductType_Result.mainData) StaticProductFragment.this.type_data.get(i)).getTid());
                        }
                    });
                    StaticProductFragment.this.index_selectionList.setselected(0);
                    StaticProductFragment.this.animatedfragmentactivity.dataLoaded();
                } catch (Exception e) {
                    Exception exc = e;
                    Commons.LogDebug(StaticProductFragment.TAG, exc.getMessage() == null ? "" : exc.getMessage());
                    StaticProductFragment.this.animatedfragmentactivity.dataLoaded();
                    exc.printStackTrace();
                }
            }
        });
    }

    public void dataResult_detail(final StaticProductTable_Result staticProductTable_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    StaticProductFragment.this.table_data.clear();
                    for (StaticProductTable_Result.mainData add : staticProductTable_Result.getMainData()) {
                        StaticProductFragment.this.table_data.add(add);
                    }
                    StaticProductFragment.this.staticproductlistadapter.notifyDataSetChanged();
                    StaticProductFragment.this.animatedfragmentactivity.dataLoaded();
                } catch (Exception e) {
                    Exception exc = e;
                    Commons.LogDebug(StaticProductFragment.TAG, exc.getMessage() == null ? "" : exc.getMessage());
                    StaticProductFragment.this.animatedfragmentactivity.dataLoaded();
                    exc.printStackTrace();
                }
            }
        });
    }

    public void loadJSON() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(StaticProductType_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<StaticProductType_Result>() {
            public void OnJSONCompleted(StaticProductType_Result staticProductType_Result) {
                StaticProductFragment.this.dataResult(staticProductType_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                StaticProductFragment.this.dataResult((StaticProductType_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_static_product_type));
        this.animatedfragmentactivity.dataLoading();
    }

    public void loadJSON_detail(String str) {
        GenericJSONParser genericJSONParser = new GenericJSONParser(StaticProductTable_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<StaticProductTable_Result>() {
            public void OnJSONCompleted(StaticProductTable_Result staticProductTable_Result) {
                StaticProductFragment.this.dataResult_detail(staticProductTable_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                StaticProductFragment.this.dataResult_detail((StaticProductTable_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_static_product_table) + "&id=" + str);
        this.animatedfragmentactivity.dataLoading();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.animatedfragmentactivity = (AnimatedFragmentActivity) getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.static_product_frag, viewGroup, false);
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
