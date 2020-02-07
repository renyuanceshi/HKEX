package com.hkex.soma.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.basic.LoaderImageView;
import com.hkex.soma.dataModel.OptionsVolume_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.element.PortfolioHalf;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.slideAnimator.SlideRightAnimationHandler;
import com.hkex.soma.utils.Commons;

public class OptionsVolume extends AnimatedFragmentActivity {
    public static final String TAG = "TailorMadeCombinations";
    private Activity _self = this;
    private TextView advdata_mvol;
    private TextView advdata_time;
    private TextView advdata_yvol;
    private LoaderImageView chart;
    private String[] index_items;
    public String[] index_items_val;
    private SelectionList index_selectionList;
    private ImageButton leftbtn;
    private ListView listView;
    private MenuContainer menu;
    private String[] months = {"January", "February", "March", "Apri", "May", "June", "July", "August", "September", "October", "November", "December"};
    private PortfolioHalf portfolio;
    private ImageButton rightbtn;
    private SlideRightAnimationHandler slideRightAnimationHandler;
    private TextView totaldata_time;
    private TextView totaldata_vol;
    private int ucodeIndex = 0;
    private TextView update_datetime;

    private void getview() {
        String str;
        String str2;
        String str3;
        this.leftbtn = (ImageButton) findViewById(R.id.btnLeft);
        this.rightbtn = (ImageButton) findViewById(R.id.btnright);
        this.chart = (LoaderImageView) findViewById(R.id.chart);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.chart.setLayoutParams(new RelativeLayout.LayoutParams(-1, Math.round((float) ((displayMetrics.widthPixels * 616) / 916))));
        this.index_selectionList = (SelectionList) findViewById(R.id.index_selectionList);
        this.totaldata_time = (TextView) findViewById(R.id.totaldata_time);
        this.totaldata_vol = (TextView) findViewById(R.id.totaldata_vol);
        this.advdata_time = (TextView) findViewById(R.id.advdata_time);
        this.advdata_mvol = (TextView) findViewById(R.id.advdata_mvol);
        this.advdata_yvol = (TextView) findViewById(R.id.advdata_yvol);
        this.update_datetime = (TextView) findViewById(R.id.update_datetime);
        String string = getResources().getString(R.string.stockoptions);
        StringBuilder sb = new StringBuilder();
        sb.append("HHI");
        if (Commons.MapIndexName("HHI").equals("")) {
            str = "";
        } else {
            str = " - " + Commons.MapIndexName("HHI");
        }
        sb.append(str);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append("HSI");
        if (Commons.MapIndexName("HSI").equals("")) {
            str2 = "";
        } else {
            str2 = " - " + Commons.MapIndexName("HSI");
        }
        sb3.append(str2);
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append("MHI");
        if (Commons.MapIndexName("MHI").equals("")) {
            str3 = "";
        } else {
            str3 = " - " + Commons.MapIndexName("MHI");
        }
        sb5.append(str3);
        this.index_items = new String[]{string, sb2, sb4, sb5.toString()};
        this.index_items_val = new String[]{"STOCK", "hhi", "hsi", "mhi"};
    }

    private void setlistener() {
        this.index_selectionList.initItems(this.index_items, R.string.contracts, SelectionList.PopTypes.LIST, this.ucodeIndex);
        this.index_selectionList.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                int unused = OptionsVolume.this.ucodeIndex = i;
                OptionsVolume.this.loadJSON(OptionsVolume.this.index_items_val[i]);
                LoaderImageView access$200 = OptionsVolume.this.chart;
                StringBuilder sb = new StringBuilder();
                sb.append(OptionsVolume.this.getResources().getText(R.string.url_options_volume_chart).toString());
                sb.append("?type=getChart&ucode=");
                sb.append(OptionsVolume.this.index_items_val[i]);
                sb.append("&lang=");
                sb.append(Commons.language.equals("en_US") ? "en" : "ch");
                access$200.setImageDrawable(sb.toString(), ImageView.ScaleType.FIT_XY);
            }
        });
    }

    private void setview() {
        LoaderImageView loaderImageView = this.chart;
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getText(R.string.url_options_volume_chart).toString());
        sb.append("?type=getChart&ucode=");
        sb.append(this.index_items_val[this.ucodeIndex]);
        sb.append("&lang=");
        sb.append(Commons.language.equals("en_US") ? "en" : "ch");
        loaderImageView.setImageDrawable(sb.toString(), ImageView.ScaleType.FIT_XY);
    }

    public void dataResult(final OptionsVolume_Result optionsVolume_Result) {
        this.handler.post(new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:16:0x00ba A[Catch:{ Exception -> 0x02ed }] */
            /* JADX WARNING: Removed duplicated region for block: B:19:0x00f3 A[Catch:{ Exception -> 0x02ed }] */
            /* JADX WARNING: Removed duplicated region for block: B:41:0x02a7 A[SYNTHETIC, Splitter:B:41:0x02a7] */
            /* JADX WARNING: Removed duplicated region for block: B:46:0x02f3 A[SYNTHETIC, Splitter:B:46:0x02f3] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r8 = this;
                    java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x01e4 }
                    java.lang.String r0 = "yyyy-MM-dd"
                    r2.<init>(r0)     // Catch:{ Exception -> 0x01e4 }
                    java.util.Calendar r3 = java.util.Calendar.getInstance()     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.dataModel.OptionsVolume_Result r0 = r3     // Catch:{ Exception -> 0x02a3 }
                    com.hkex.soma.dataModel.OptionsVolume_Result$totalData[] r0 = r0.getTotalData()     // Catch:{ Exception -> 0x02a3 }
                    r1 = 0
                    r0 = r0[r1]     // Catch:{ Exception -> 0x02a3 }
                    java.lang.String r0 = r0.getTime()     // Catch:{ Exception -> 0x02a3 }
                    int r0 = r0.length()     // Catch:{ Exception -> 0x02a3 }
                    r1 = 7
                    if (r0 <= r1) goto L_0x01fc
                    java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x02a3 }
                    java.lang.String r0 = "yyyy-MM-dd"
                    r1.<init>(r0)     // Catch:{ Exception -> 0x02a3 }
                    com.hkex.soma.dataModel.OptionsVolume_Result r0 = r3     // Catch:{ Exception -> 0x01de }
                    com.hkex.soma.dataModel.OptionsVolume_Result$totalData[] r0 = r0.getTotalData()     // Catch:{ Exception -> 0x01de }
                    r2 = 0
                    r0 = r0[r2]     // Catch:{ Exception -> 0x01de }
                    java.lang.String r0 = r0.getTime()     // Catch:{ Exception -> 0x01de }
                    java.util.Date r0 = r1.parse(r0)     // Catch:{ Exception -> 0x01de }
                    java.lang.String r2 = com.hkex.soma.utils.Commons.language     // Catch:{ Exception -> 0x01de }
                    java.lang.String r4 = "en_US"
                    boolean r2 = r2.equals(r4)     // Catch:{ Exception -> 0x01de }
                    if (r2 == 0) goto L_0x0180
                    com.hkex.soma.activity.OptionsVolume r2 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01de }
                    android.widget.TextView r2 = r2.totaldata_time     // Catch:{ Exception -> 0x01de }
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01de }
                    r4.<init>()     // Catch:{ Exception -> 0x01de }
                    r5 = 5
                    int r5 = r3.get(r5)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    java.lang.String r5 = " "
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    com.hkex.soma.activity.OptionsVolume r5 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01de }
                    java.lang.String[] r5 = r5.months     // Catch:{ Exception -> 0x01de }
                    r6 = 2
                    int r6 = r3.get(r6)     // Catch:{ Exception -> 0x01de }
                    r5 = r5[r6]     // Catch:{ Exception -> 0x01de }
                    r6 = 0
                    r7 = 3
                    java.lang.String r5 = r5.substring(r6, r7)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    java.lang.String r5 = " "
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    r5 = 1
                    int r5 = r3.get(r5)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01de }
                    r2.setText(r4)     // Catch:{ Exception -> 0x01de }
                L_0x0083:
                    r3.setTime(r0)     // Catch:{ Exception -> 0x01de }
                L_0x0086:
                    com.hkex.soma.activity.OptionsVolume r0 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01e4 }
                    android.widget.TextView r0 = r0.totaldata_vol     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.dataModel.OptionsVolume_Result r2 = r3     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.dataModel.OptionsVolume_Result$totalData[] r2 = r2.getTotalData()     // Catch:{ Exception -> 0x01e4 }
                    r4 = 0
                    r2 = r2[r4]     // Catch:{ Exception -> 0x01e4 }
                    java.lang.String r2 = r2.getVol()     // Catch:{ Exception -> 0x01e4 }
                    r0.setText(r2)     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.dataModel.OptionsVolume_Result r0 = r3     // Catch:{ Exception -> 0x02ed }
                    com.hkex.soma.dataModel.OptionsVolume_Result$advData[] r0 = r0.getAdvData()     // Catch:{ Exception -> 0x02ed }
                    r2 = 0
                    r0 = r0[r2]     // Catch:{ Exception -> 0x02ed }
                    java.lang.String r0 = r0.getTime()     // Catch:{ Exception -> 0x02ed }
                    java.util.Date r0 = r1.parse(r0)     // Catch:{ Exception -> 0x02ed }
                    r3.setTime(r0)     // Catch:{ Exception -> 0x02ed }
                    java.lang.String r0 = com.hkex.soma.utils.Commons.language     // Catch:{ Exception -> 0x02ed }
                    java.lang.String r1 = "en_US"
                    boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x02ed }
                    if (r0 == 0) goto L_0x02a7
                    com.hkex.soma.activity.OptionsVolume r0 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x02ed }
                    android.widget.TextView r0 = r0.advdata_time     // Catch:{ Exception -> 0x02ed }
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ed }
                    r1.<init>()     // Catch:{ Exception -> 0x02ed }
                    com.hkex.soma.activity.OptionsVolume r2 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x02ed }
                    java.lang.String[] r2 = r2.months     // Catch:{ Exception -> 0x02ed }
                    r4 = 2
                    int r4 = r3.get(r4)     // Catch:{ Exception -> 0x02ed }
                    r2 = r2[r4]     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    java.lang.String r2 = " "
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    r2 = 1
                    int r2 = r3.get(r2)     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x02ed }
                    r0.setText(r1)     // Catch:{ Exception -> 0x02ed }
                L_0x00e9:
                    java.lang.String r0 = com.hkex.soma.utils.Commons.language     // Catch:{ Exception -> 0x02ed }
                    java.lang.String r1 = "en_US"
                    boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x02ed }
                    if (r0 == 0) goto L_0x02f3
                    com.hkex.soma.activity.OptionsVolume r0 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x02ed }
                    android.widget.TextView r0 = r0.update_datetime     // Catch:{ Exception -> 0x02ed }
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ed }
                    r1.<init>()     // Catch:{ Exception -> 0x02ed }
                    r2 = 5
                    int r2 = r3.get(r2)     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    java.lang.String r2 = " "
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    com.hkex.soma.activity.OptionsVolume r2 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x02ed }
                    java.lang.String[] r2 = r2.months     // Catch:{ Exception -> 0x02ed }
                    r4 = 2
                    int r4 = r3.get(r4)     // Catch:{ Exception -> 0x02ed }
                    r2 = r2[r4]     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    java.lang.String r2 = " "
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    r2 = 1
                    int r2 = r3.get(r2)     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x02ed }
                    r0.setText(r1)     // Catch:{ Exception -> 0x02ed }
                L_0x012f:
                    com.hkex.soma.activity.OptionsVolume r0 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01e4 }
                    android.widget.TextView r0 = r0.advdata_mvol     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.dataModel.OptionsVolume_Result r1 = r3     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.dataModel.OptionsVolume_Result$advData[] r1 = r1.getAdvData()     // Catch:{ Exception -> 0x01e4 }
                    r2 = 0
                    r1 = r1[r2]     // Catch:{ Exception -> 0x01e4 }
                    java.lang.String r1 = r1.getMvol()     // Catch:{ Exception -> 0x01e4 }
                    r0.setText(r1)     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.activity.OptionsVolume r0 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01e4 }
                    android.widget.TextView r0 = r0.advdata_yvol     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.dataModel.OptionsVolume_Result r1 = r3     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.dataModel.OptionsVolume_Result$advData[] r1 = r1.getAdvData()     // Catch:{ Exception -> 0x01e4 }
                    r2 = 0
                    r1 = r1[r2]     // Catch:{ Exception -> 0x01e4 }
                    java.lang.String r1 = r1.getYvol()     // Catch:{ Exception -> 0x01e4 }
                    r0.setText(r1)     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.activity.OptionsVolume r0 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.dataModel.OptionsVolume_Result r1 = r3     // Catch:{ Exception -> 0x01e4 }
                    java.lang.String r1 = r1.getStime()     // Catch:{ Exception -> 0x01e4 }
                    r0.updateFooterStime(r1)     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.activity.OptionsVolume r0 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01e4 }
                    android.widget.LinearLayout r0 = r0.footerContainer     // Catch:{ Exception -> 0x01e4 }
                    r1 = 2131165697(0x7f070201, float:1.7945618E38)
                    android.view.View r0 = r0.findViewById(r1)     // Catch:{ Exception -> 0x01e4 }
                    android.widget.TextView r0 = (android.widget.TextView) r0     // Catch:{ Exception -> 0x01e4 }
                    r1 = 8
                    r0.setVisibility(r1)     // Catch:{ Exception -> 0x01e4 }
                    com.hkex.soma.activity.OptionsVolume r0 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01e4 }
                    r0.dataLoaded()     // Catch:{ Exception -> 0x01e4 }
                L_0x017f:
                    return
                L_0x0180:
                    com.hkex.soma.activity.OptionsVolume r2 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01de }
                    android.widget.TextView r2 = r2.totaldata_time     // Catch:{ Exception -> 0x01de }
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01de }
                    r4.<init>()     // Catch:{ Exception -> 0x01de }
                    r5 = 1
                    int r5 = r3.get(r5)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    com.hkex.soma.activity.OptionsVolume r5 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01de }
                    android.content.res.Resources r5 = r5.getResources()     // Catch:{ Exception -> 0x01de }
                    r6 = 2131362162(0x7f0a0172, float:1.8344097E38)
                    java.lang.String r5 = r5.getString(r6)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    r5 = 2
                    int r5 = r3.get(r5)     // Catch:{ Exception -> 0x01de }
                    int r5 = r5 + 1
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    com.hkex.soma.activity.OptionsVolume r5 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01de }
                    android.content.res.Resources r5 = r5.getResources()     // Catch:{ Exception -> 0x01de }
                    r6 = 2131361931(0x7f0a008b, float:1.8343628E38)
                    java.lang.String r5 = r5.getString(r6)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    r5 = 5
                    int r5 = r3.get(r5)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    com.hkex.soma.activity.OptionsVolume r5 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01de }
                    android.content.res.Resources r5 = r5.getResources()     // Catch:{ Exception -> 0x01de }
                    r6 = 2131361847(0x7f0a0037, float:1.8343458E38)
                    java.lang.String r5 = r5.getString(r6)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01de }
                    r2.setText(r4)     // Catch:{ Exception -> 0x01de }
                    goto L_0x0083
                L_0x01de:
                    r0 = move-exception
                L_0x01df:
                    r0.printStackTrace()     // Catch:{ Exception -> 0x01e4 }
                    goto L_0x0086
                L_0x01e4:
                    r0 = move-exception
                    r1 = r0
                    java.lang.String r0 = r1.getMessage()
                    if (r0 != 0) goto L_0x0351
                    java.lang.String r0 = ""
                L_0x01ee:
                    java.lang.String r2 = "TailorMadeCombinations"
                    com.hkex.soma.utils.Commons.LogDebug(r2, r0)
                    com.hkex.soma.activity.OptionsVolume r0 = com.hkex.soma.activity.OptionsVolume.this
                    r0.dataLoaded()
                    r1.printStackTrace()
                    goto L_0x017f
                L_0x01fc:
                    java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x02a3 }
                    java.lang.String r0 = "yyyy-MM"
                    r1.<init>(r0)     // Catch:{ Exception -> 0x02a3 }
                    com.hkex.soma.dataModel.OptionsVolume_Result r0 = r3     // Catch:{ Exception -> 0x01de }
                    com.hkex.soma.dataModel.OptionsVolume_Result$totalData[] r0 = r0.getTotalData()     // Catch:{ Exception -> 0x01de }
                    r2 = 0
                    r0 = r0[r2]     // Catch:{ Exception -> 0x01de }
                    java.lang.String r0 = r0.getTime()     // Catch:{ Exception -> 0x01de }
                    java.util.Date r0 = r1.parse(r0)     // Catch:{ Exception -> 0x01de }
                    java.lang.String r2 = com.hkex.soma.utils.Commons.language     // Catch:{ Exception -> 0x01de }
                    java.lang.String r4 = "en_US"
                    boolean r2 = r2.equals(r4)     // Catch:{ Exception -> 0x01de }
                    if (r2 == 0) goto L_0x0255
                    com.hkex.soma.activity.OptionsVolume r2 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01de }
                    android.widget.TextView r2 = r2.totaldata_time     // Catch:{ Exception -> 0x01de }
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01de }
                    r4.<init>()     // Catch:{ Exception -> 0x01de }
                    com.hkex.soma.activity.OptionsVolume r5 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01de }
                    java.lang.String[] r5 = r5.months     // Catch:{ Exception -> 0x01de }
                    r6 = 2
                    int r6 = r3.get(r6)     // Catch:{ Exception -> 0x01de }
                    r5 = r5[r6]     // Catch:{ Exception -> 0x01de }
                    r6 = 0
                    r7 = 3
                    java.lang.String r5 = r5.substring(r6, r7)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    java.lang.String r5 = " "
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    r5 = 1
                    int r5 = r3.get(r5)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01de }
                    r2.setText(r4)     // Catch:{ Exception -> 0x01de }
                    goto L_0x0083
                L_0x0255:
                    com.hkex.soma.activity.OptionsVolume r2 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01de }
                    android.widget.TextView r2 = r2.totaldata_time     // Catch:{ Exception -> 0x01de }
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01de }
                    r4.<init>()     // Catch:{ Exception -> 0x01de }
                    r5 = 1
                    int r5 = r3.get(r5)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    com.hkex.soma.activity.OptionsVolume r5 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01de }
                    android.content.res.Resources r5 = r5.getResources()     // Catch:{ Exception -> 0x01de }
                    r6 = 2131362162(0x7f0a0172, float:1.8344097E38)
                    java.lang.String r5 = r5.getString(r6)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    r5 = 2
                    int r5 = r3.get(r5)     // Catch:{ Exception -> 0x01de }
                    int r5 = r5 + 1
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    com.hkex.soma.activity.OptionsVolume r5 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x01de }
                    android.content.res.Resources r5 = r5.getResources()     // Catch:{ Exception -> 0x01de }
                    r6 = 2131361931(0x7f0a008b, float:1.8343628E38)
                    java.lang.String r5 = r5.getString(r6)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    r5 = 5
                    int r5 = r3.get(r5)     // Catch:{ Exception -> 0x01de }
                    r4.append(r5)     // Catch:{ Exception -> 0x01de }
                    java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01de }
                    r2.setText(r4)     // Catch:{ Exception -> 0x01de }
                    goto L_0x0083
                L_0x02a3:
                    r0 = move-exception
                    r1 = r2
                    goto L_0x01df
                L_0x02a7:
                    com.hkex.soma.activity.OptionsVolume r0 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x02ed }
                    android.widget.TextView r0 = r0.advdata_time     // Catch:{ Exception -> 0x02ed }
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ed }
                    r1.<init>()     // Catch:{ Exception -> 0x02ed }
                    r2 = 1
                    int r2 = r3.get(r2)     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    com.hkex.soma.activity.OptionsVolume r2 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x02ed }
                    android.content.res.Resources r2 = r2.getResources()     // Catch:{ Exception -> 0x02ed }
                    r4 = 2131362162(0x7f0a0172, float:1.8344097E38)
                    java.lang.String r2 = r2.getString(r4)     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    r2 = 2
                    int r2 = r3.get(r2)     // Catch:{ Exception -> 0x02ed }
                    int r2 = r2 + 1
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    com.hkex.soma.activity.OptionsVolume r2 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x02ed }
                    android.content.res.Resources r2 = r2.getResources()     // Catch:{ Exception -> 0x02ed }
                    r4 = 2131361931(0x7f0a008b, float:1.8343628E38)
                    java.lang.String r2 = r2.getString(r4)     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x02ed }
                    r0.setText(r1)     // Catch:{ Exception -> 0x02ed }
                    goto L_0x00e9
                L_0x02ed:
                    r0 = move-exception
                    r0.printStackTrace()     // Catch:{ Exception -> 0x01e4 }
                    goto L_0x012f
                L_0x02f3:
                    com.hkex.soma.activity.OptionsVolume r0 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x02ed }
                    android.widget.TextView r0 = r0.update_datetime     // Catch:{ Exception -> 0x02ed }
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ed }
                    r1.<init>()     // Catch:{ Exception -> 0x02ed }
                    r2 = 1
                    int r2 = r3.get(r2)     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    com.hkex.soma.activity.OptionsVolume r2 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x02ed }
                    android.content.res.Resources r2 = r2.getResources()     // Catch:{ Exception -> 0x02ed }
                    r4 = 2131362162(0x7f0a0172, float:1.8344097E38)
                    java.lang.String r2 = r2.getString(r4)     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    r2 = 2
                    int r2 = r3.get(r2)     // Catch:{ Exception -> 0x02ed }
                    int r2 = r2 + 1
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    com.hkex.soma.activity.OptionsVolume r2 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x02ed }
                    android.content.res.Resources r2 = r2.getResources()     // Catch:{ Exception -> 0x02ed }
                    r4 = 2131361931(0x7f0a008b, float:1.8343628E38)
                    java.lang.String r2 = r2.getString(r4)     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    r2 = 5
                    int r2 = r3.get(r2)     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    com.hkex.soma.activity.OptionsVolume r2 = com.hkex.soma.activity.OptionsVolume.this     // Catch:{ Exception -> 0x02ed }
                    android.content.res.Resources r2 = r2.getResources()     // Catch:{ Exception -> 0x02ed }
                    r3 = 2131361847(0x7f0a0037, float:1.8343458E38)
                    java.lang.String r2 = r2.getString(r3)     // Catch:{ Exception -> 0x02ed }
                    r1.append(r2)     // Catch:{ Exception -> 0x02ed }
                    java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x02ed }
                    r0.setText(r1)     // Catch:{ Exception -> 0x02ed }
                    goto L_0x012f
                L_0x0351:
                    java.lang.String r0 = r1.getMessage()
                    goto L_0x01ee
                */
                throw new UnsupportedOperationException("Method not decompiled: com.hkex.soma.activity.OptionsVolume.AnonymousClass7.run():void");
            }
        });
    }

    public void halfPortfolioUpdated() {
        this.slideRightAnimationHandler.onClick((View) null);
    }

    public void loadJSON(String str) {
        GenericJSONParser genericJSONParser = new GenericJSONParser(OptionsVolume_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<OptionsVolume_Result>() {
            public void OnJSONCompleted(OptionsVolume_Result optionsVolume_Result) {
                OptionsVolume.this.dataResult(optionsVolume_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                OptionsVolume.this.dataResult((OptionsVolume_Result) null);
            }
        });
        Log.v("TailorMadeCombinations", "url " + getString(R.string.url_options_volume) + "?ucode=" + str);
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.url_options_volume));
        sb.append("?ucode=");
        sb.append(str);
        genericJSONParser.loadXML(sb.toString());
        dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (!Commons.noResumeAction) {
            setContentView(R.layout.options_volume);
            try {
                getview();
                setview();
                setlistener();
                setmenu();
                loadJSON(this.index_items_val[this.ucodeIndex]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.isMoving = false;
            Commons.noResumeAction = false;
        }
    }

    public void setmenu() {
        this.rightbtn.setVisibility(View.INVISIBLE);
        this.leftViewOut = false;
        this.rightViewOut = false;
        this.menu = new MenuContainer(this);
        this.mainContainer = findViewById(R.id.mainContainer);
        ClickControlContainer clickControlContainer = (ClickControlContainer) findViewById(R.id.menuContainer);
        this.clickControlContainer = (ClickControlContainer) findViewById(R.id.clickControlContainer);
        clickControlContainer.addView(this.menu, new LinearLayout.LayoutParams(-1, -1));
        initFooter();
        this.leftView = clickControlContainer;
        this.mainView = findViewById(R.id.appContainer);
        this.slideRightAnimationHandler = new SlideRightAnimationHandler(this);
        final SlideLeftAnimationHandler slideLeftAnimationHandler = new SlideLeftAnimationHandler(this);
        this.leftbtn.setOnClickListener(slideLeftAnimationHandler);
        this.clickControlContainer.setOnLeftToRightSwipeListener(new ClickControlContainer.OnLeftToRightSwipeListener() {
            public void onLeftToRightSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        this.clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                if (OptionsVolume.this.leftViewOut || OptionsVolume.this.rightViewOut) {
                    OptionsVolume.this.slideRightAnimationHandler.onClick((View) null);
                }
            }
        });
        clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        this.menu.setSliedBackHandler(slideLeftAnimationHandler);
    }
}
