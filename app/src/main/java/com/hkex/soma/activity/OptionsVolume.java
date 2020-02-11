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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        this.handler.post((Runnable)new Runnable() {
            @Override
            public void run() {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    final Calendar instance = Calendar.getInstance();
                    Label_0602: {
                        SimpleDateFormat simpleDateFormat2 = null;
                        Label_0598: {
                            try {
                                Date time = null;
                                Label_0584: {
                                    if (optionsVolume_Result.getTotalData()[0].getTime().length() > 7) {
                                        simpleDateFormat2 = (simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"));
                                        try {
                                            time = simpleDateFormat2.parse(optionsVolume_Result.getTotalData()[0].getTime());
                                            simpleDateFormat = simpleDateFormat2;
                                            if (Commons.language.equals("en_US")) {
                                                simpleDateFormat = simpleDateFormat2;
                                                final TextView access$500 = OptionsVolume.this.totaldata_time;
                                                simpleDateFormat = simpleDateFormat2;
                                                simpleDateFormat = simpleDateFormat2;
                                                final StringBuilder sb = new StringBuilder();
                                                simpleDateFormat = simpleDateFormat2;
                                                sb.append(instance.get(5));
                                                simpleDateFormat = simpleDateFormat2;
                                                sb.append(" ");
                                                simpleDateFormat = simpleDateFormat2;
                                                sb.append(OptionsVolume.this.months[instance.get(2)].substring(0, 3));
                                                simpleDateFormat = simpleDateFormat2;
                                                sb.append(" ");
                                                simpleDateFormat = simpleDateFormat2;
                                                sb.append(instance.get(1));
                                                simpleDateFormat = simpleDateFormat2;
                                                access$500.setText((CharSequence)sb.toString());
                                                break Label_0584;
                                            }
                                            simpleDateFormat = simpleDateFormat2;
                                            final TextView access$501 = OptionsVolume.this.totaldata_time;
                                            simpleDateFormat = simpleDateFormat2;
                                            simpleDateFormat = simpleDateFormat2;
                                            final StringBuilder sb2 = new StringBuilder();
                                            simpleDateFormat = simpleDateFormat2;
                                            sb2.append(instance.get(1));
                                            simpleDateFormat = simpleDateFormat2;
                                            sb2.append(OptionsVolume.this.getResources().getString(R.string.year_text));
                                            simpleDateFormat = simpleDateFormat2;
                                            sb2.append(instance.get(2) + 1);
                                            simpleDateFormat = simpleDateFormat2;
                                            sb2.append(OptionsVolume.this.getResources().getString(R.string.month_text));
                                            simpleDateFormat = simpleDateFormat2;
                                            sb2.append(instance.get(5));
                                            simpleDateFormat = simpleDateFormat2;
                                            sb2.append(OptionsVolume.this.getResources().getString(R.string.day_text));
                                            simpleDateFormat = simpleDateFormat2;
                                            access$501.setText((CharSequence)sb2.toString());
                                            break Label_0584;
                                        }
                                        catch (Exception e) {
                                            break Label_0598;
                                        }
                                    }
                                    simpleDateFormat2 = (simpleDateFormat = new SimpleDateFormat("yyyy-MM"));
                                    time = simpleDateFormat2.parse(optionsVolume_Result.getTotalData()[0].getTime());
                                    simpleDateFormat = simpleDateFormat2;
                                    if (Commons.language.equals("en_US")) {
                                        simpleDateFormat = simpleDateFormat2;
                                        final TextView access$502 = OptionsVolume.this.totaldata_time;
                                        simpleDateFormat = simpleDateFormat2;
                                        simpleDateFormat = simpleDateFormat2;
                                        final StringBuilder sb3 = new StringBuilder();
                                        simpleDateFormat = simpleDateFormat2;
                                        sb3.append(OptionsVolume.this.months[instance.get(2)].substring(0, 3));
                                        simpleDateFormat = simpleDateFormat2;
                                        sb3.append(" ");
                                        simpleDateFormat = simpleDateFormat2;
                                        sb3.append(instance.get(1));
                                        simpleDateFormat = simpleDateFormat2;
                                        access$502.setText((CharSequence)sb3.toString());
                                    }
                                    else {
                                        simpleDateFormat = simpleDateFormat2;
                                        final TextView access$503 = OptionsVolume.this.totaldata_time;
                                        simpleDateFormat = simpleDateFormat2;
                                        simpleDateFormat = simpleDateFormat2;
                                        final StringBuilder sb4 = new StringBuilder();
                                        simpleDateFormat = simpleDateFormat2;
                                        sb4.append(instance.get(1));
                                        simpleDateFormat = simpleDateFormat2;
                                        sb4.append(OptionsVolume.this.getResources().getString(R.string.year_text));
                                        simpleDateFormat = simpleDateFormat2;
                                        sb4.append(instance.get(2) + 1);
                                        simpleDateFormat = simpleDateFormat2;
                                        sb4.append(OptionsVolume.this.getResources().getString(R.string.month_text));
                                        simpleDateFormat = simpleDateFormat2;
                                        sb4.append(instance.get(5));
                                        simpleDateFormat = simpleDateFormat2;
                                        access$503.setText((CharSequence)sb4.toString());
                                    }
                                }
                                simpleDateFormat = simpleDateFormat2;
                                instance.setTime(time);
                                simpleDateFormat = simpleDateFormat2;
                                break Label_0602;
                            }
                            catch (Exception ex3) {}
                        }
//                        ((Throwable)simpleDateFormat2).printStackTrace();
                    }
                    OptionsVolume.this.totaldata_vol.setText((CharSequence)optionsVolume_Result.getTotalData()[0].getVol());
                    try {
                        instance.setTime(simpleDateFormat.parse(optionsVolume_Result.getAdvData()[0].getTime()));
                        if (Commons.language.equals("en_US")) {
                            final TextView access$504 = OptionsVolume.this.advdata_time;
                            final StringBuilder sb5 = new StringBuilder();
                            sb5.append(OptionsVolume.this.months[instance.get(2)]);
                            sb5.append(" ");
                            sb5.append(instance.get(1));
                            access$504.setText((CharSequence)sb5.toString());
                        }
                        else {
                            final TextView access$505 = OptionsVolume.this.advdata_time;
                            final StringBuilder sb6 = new StringBuilder();
                            sb6.append(instance.get(1));
                            sb6.append(OptionsVolume.this.getResources().getString(R.string.year_text));
                            sb6.append(instance.get(2) + 1);
                            sb6.append(OptionsVolume.this.getResources().getString(R.string.month_text));
                            access$505.setText((CharSequence)sb6.toString());
                        }
                        if (Commons.language.equals("en_US")) {
                            final TextView access$506 = OptionsVolume.this.update_datetime;
                            final StringBuilder sb7 = new StringBuilder();
                            sb7.append(instance.get(5));
                            sb7.append(" ");
                            sb7.append(OptionsVolume.this.months[instance.get(2)]);
                            sb7.append(" ");
                            sb7.append(instance.get(1));
                            access$506.setText((CharSequence)sb7.toString());
                        }
                        else {
                            final TextView access$507 = OptionsVolume.this.update_datetime;
                            final StringBuilder sb8 = new StringBuilder();
                            sb8.append(instance.get(1));
                            sb8.append(OptionsVolume.this.getResources().getString(R.string.year_text));
                            sb8.append(instance.get(2) + 1);
                            sb8.append(OptionsVolume.this.getResources().getString(R.string.month_text));
                            sb8.append(instance.get(5));
                            sb8.append(OptionsVolume.this.getResources().getString(R.string.day_text));
                            access$507.setText((CharSequence)sb8.toString());
                        }
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    OptionsVolume.this.advdata_mvol.setText((CharSequence)optionsVolume_Result.getAdvData()[0].getMvol());
                    OptionsVolume.this.advdata_yvol.setText((CharSequence)optionsVolume_Result.getAdvData()[0].getYvol());
                    OptionsVolume.this.updateFooterStime(optionsVolume_Result.getStime());
                    ((TextView)OptionsVolume.this.footerContainer.findViewById(2131165697)).setVisibility(8);
                    OptionsVolume.this.dataLoaded();
                }
                catch (Exception ex2) {
                    String message;
                    if (ex2.getMessage() == null) {
                        message = "";
                    }
                    else {
                        message = ex2.getMessage();
                    }
                    Commons.LogDebug("TailorMadeCombinations", message);
                    OptionsVolume.this.dataLoaded();
                    ex2.printStackTrace();
                }
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
