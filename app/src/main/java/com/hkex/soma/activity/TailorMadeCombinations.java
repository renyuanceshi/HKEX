package com.hkex.soma.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.adapter.TailorMadeCombinationsAdapter;
import com.hkex.soma.dataModel.TailorMadeCombinations_Result;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;
import java.util.Locale;

public class TailorMadeCombinations extends AnimatedFragmentActivity {
    public static final String TAG = "TailorMadeCombinations";
    private Activity _self = this;
    private String[] index_items;
    private String[] index_items_name;
    private SelectionList index_selectionList;
    private ImageButton leftbtn;
    private ListView listView;
    private int ucodeIndex = 0;

    private void getview() {
        this.leftbtn = (ImageButton) findViewById(R.id.btnLeft);
        this.listView = (ListView) findViewById(R.id.listview);
        this.index_selectionList = (SelectionList) findViewById(R.id.index_selectionList);
    }

    private void setlistener() {
        this.leftbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TailorMadeCombinations.this._self.finish();
            }
        });
    }

    public void dataResult(final TailorMadeCombinations_Result tailorMadeCombinations_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    TailorMadeCombinations.this.listView.setAdapter(new TailorMadeCombinationsAdapter(TailorMadeCombinations.this, R.layout.list_tailor_made_combinations, tailorMadeCombinations_Result.getmainData()));
                    String[] unused = TailorMadeCombinations.this.index_items = new String[tailorMadeCombinations_Result.getOptionlist().length];
                    for (int i = 0; i < tailorMadeCombinations_Result.getOptionlist().length; i++) {
                        TailorMadeCombinations.this.index_items[i] = tailorMadeCombinations_Result.getOptionlist()[i].getHcode();
                    }
                    String[] unused2 = TailorMadeCombinations.this.index_items_name = new String[tailorMadeCombinations_Result.getOptionlist().length];
                    for (int i2 = 0; i2 < tailorMadeCombinations_Result.getOptionlist().length; i2++) {
                        TailorMadeCombinations.this.index_items_name[i2] = tailorMadeCombinations_Result.getOptionlist()[i2].getName();
                    }
                    TailorMadeCombinations.this.index_selectionList.initItems(TailorMadeCombinations.this.index_items_name, R.string.contracts, SelectionList.PopTypes.LIST, TailorMadeCombinations.this.ucodeIndex);
                    TailorMadeCombinations.this.index_selectionList.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
                        public void onListItemChanged(int i) {
                            int unused = TailorMadeCombinations.this.ucodeIndex = i;
                            TailorMadeCombinations.this.loadJSON(TailorMadeCombinations.this.index_items[i]);
                        }
                    });
                    TailorMadeCombinations.this.dataLoaded();
                } catch (Exception e) {
                    Commons.LogDebug("TailorMadeCombinations", e.getMessage() == null ? "" : e.getMessage());
                    TailorMadeCombinations.this.dataLoaded();
                }
            }
        });
    }

    public void loadJSON(String str) {
        try {
            GenericJSONParser genericJSONParser = new GenericJSONParser(TailorMadeCombinations_Result.class);
            genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<TailorMadeCombinations_Result>() {
                public void OnJSONCompleted(TailorMadeCombinations_Result tailorMadeCombinations_Result) {
                    TailorMadeCombinations.this.dataResult(tailorMadeCombinations_Result);
                }
            });
            genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
                public void OnJSONFailed(Runnable runnable, Exception exc) {
                    exc.printStackTrace();
                    TailorMadeCombinations.this.dataResult((TailorMadeCombinations_Result) null);
                    TailorMadeCombinations.this.ShowConnectionErrorDialog(runnable);
                }
            });
            Log.v("TailorMadeCombinations", "url " + getString(R.string.url_tmc) + "&type=" + str);
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.url_tmc));
            sb.append("&type=");
            sb.append(str);
            genericJSONParser.loadXML(sb.toString());
            dataLoading();
        } catch (Exception e) {
            Log.v("TailorMadeCombinations", "tttttttttttt1");
            e.printStackTrace();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String[] split = SharedPrefsUtil.getValue((Context) this, "language", Locale.getDefault().toString()).split("_");
        Locale locale = new Locale(split[0], split[1]);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.tailor_made_combinations);
        getview();
        setlistener();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            finish();
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        loadJSON("");
    }
}
