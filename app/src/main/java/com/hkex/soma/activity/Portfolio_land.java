package com.hkex.soma.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.hkex.soma.JSONParser.PortfolioJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.fragment.Portfolio_List;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.dataModel.Portfolio_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;
import com.hkex.soma.utils.StringFormatter;
import com.hkex.soma.utils.interface_changemode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class Portfolio_land extends AnimatedFragmentActivity implements interface_changemode {
    public static final String TAG = "Portfolio_land";
    private List<Portfolio_Result.mainData> data_array = null;
    private boolean island = false;
    private Portfolio_List listFragment;
    private MenuContainer menu;
    private View.OnClickListener onAddButtonClickListener;
    private String portfolioRawData;
    private SlideLeftAnimationHandler slideLeftAnimationHandler;
    private int status;

    public static boolean AddOptionToPortfolio(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        String value = SharedPrefsUtil.getValue(context, "portfolio", "");
        if (value.split(",").length >= 20) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(StringFormatter.formatCode(str) + "_" + str3 + "_" + str4.replace(",", "") + "_" + str5);
        sb.append("!");
        sb.append(str6.equals("1") ? "1" : "0");
        sb.append("!");
        sb.append(str7);
        sb.append("!");
        sb.append(str8.replace(",", ""));
        sb.append(",");
        sb.append(value);
        SharedPrefsUtil.putValue(context, "portfolio", sb.toString());
        SharedPrefsUtil.putPortfolioValue(context, str, str2.replace(",", ""));
        return true;
    }

    public static boolean AddStockToPortfolio(Context context, String str, String str2, String str3, String str4, String str5) {
        String value = SharedPrefsUtil.getValue(context, "portfolio", "");
        if (value.split(",").length >= 20) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(StringFormatter.formatCode(str));
        sb.append("!");
        sb.append(str3.equals("1") ? "1" : "0");
        sb.append("!");
        sb.append(str4);
        sb.append("!");
        sb.append(str5.replace(",", ""));
        sb.append(",");
        sb.append(value);
        SharedPrefsUtil.putValue(context, "portfolio", sb.toString());
        SharedPrefsUtil.putPortfolioValue(context, str, str2.replace(",", ""));
        return true;
    }

    private static String convertstr(String str) {
        return str == null ? "" : str;
    }

    private void initDslvFragment() {
        this.listFragment = new Portfolio_List();
        this.listFragment.removeMode = 1;
        this.listFragment.removeEnabled = false;
        this.listFragment.dragStartMode = 1;
        this.listFragment.sortEnabled = true;
        this.listFragment.dragEnabled = true;
    }

    public static void updatePortfolioResult(Context context, String str, List<Portfolio_Result.mainData> list, boolean z) {
        Portfolio_Result.mainData maindata;
        String[] split = str.split(",");
        if (list.size() == 0) {
            z = true;
        }
        for (int i = 0; i < split.length; i++) {
            try {
                if (!split[i].equals("")) {
                    String[] split2 = split[i].split("!");
                    String[] split3 = split2[0].split("_");
                    if (z) {
                        maindata = new Portfolio_Result.mainData();
                        if (split3.length > 1) {
                            String portfolioValue = SharedPrefsUtil.getPortfolioValue(context, split3[0], "");
                            String portfolioEnValue = SharedPrefsUtil.getPortfolioEnValue(context, split3[0], "");
                            maindata.setUcode(split3[0]);
                            maindata.setStrike(split3[2]);
                            maindata.setUName(portfolioEnValue);
                            maindata.setUnmll(portfolioValue);
                            maindata.setType(split3[1]);
                            maindata.setExpiry(split3[3]);
                        } else {
                            String portfolioValue2 = SharedPrefsUtil.getPortfolioValue(context, split2[0], "");
                            maindata.setUName(SharedPrefsUtil.getPortfolioEnValue(context, split2[0], ""));
                            maindata.setUnmll(portfolioValue2);
                        }
                        list.add(maindata);
                    } else if (list.get(i).getUnmll() == null) {
                        maindata = list.get(i);
                        if (split3.length > 1) {
                            String portfolioValue3 = SharedPrefsUtil.getPortfolioValue(context, split3[0], "");
                            String portfolioEnValue2 = SharedPrefsUtil.getPortfolioEnValue(context, split3[0], "");
                            maindata.setUcode(split3[0]);
                            maindata.setStrike(split3[2]);
                            maindata.setUName(portfolioEnValue2);
                            maindata.setUnmll(portfolioValue3);
                            maindata.setType(split3[1]);
                            maindata.setExpiry(split3[3]);
                        } else {
                            String portfolioValue4 = SharedPrefsUtil.getPortfolioValue(context, split2[0], "");
                            maindata.setUName(SharedPrefsUtil.getPortfolioEnValue(context, split2[0], ""));
                            maindata.setUnmll(portfolioValue4);
                        }
                    } else {
                        maindata = list.get(i);
                        maindata.setType((maindata.getType() == null || !maindata.getType().equals("C")) ? "Put" : "Call");
                        if (Commons.language.equals("en_US")) {
                            if (maindata.getUName().equals("")) {
                                maindata.setUName(Commons.MapUnderlyingName(convertstr(maindata.getUcode() == null ? maindata.getCode() : maindata.getUcode())));
                            }
                            if (split3.length > 1) {
                                SharedPrefsUtil.putPortfolioValue(context, split3[0], convertstr(maindata.getUName()));
                            } else {
                                SharedPrefsUtil.putPortfolioValue(context, split2[0], convertstr(maindata.getUName()));
                            }
                        } else {
                            if (maindata.getUnmll().equals("")) {
                                maindata.setUnmll(Commons.MapUnderlyingName(maindata.getUcode() == null ? convertstr(maindata.getCode()) : convertstr(maindata.getUcode())));
                            }
                            if (split3.length > 1) {
                                SharedPrefsUtil.putPortfolioValue(context, split3[0], convertstr(maindata.getUnmll()));
                            } else {
                                SharedPrefsUtil.putPortfolioValue(context, split2[0], convertstr(maindata.getUnmll()));
                            }
                        }
                    }
                    maindata.setcode(split2[0]);
                    maindata.setDirection(split2[1]);
                    maindata.setQty(split2[2]);
                    maindata.setPrice(split2[3]);
                }
            } catch (Exception e) {
                if (!z) {
                    list.remove(i);
                }
                Commons.LogDebug("updatePortfolioResult", e.getMessage());
            }
        }
    }

    public void changeMode(String str) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.controlPanel);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.list_header1);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.list_header2);
        ImageButton imageButton = (ImageButton) findViewById(R.id.btnRight);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.btnLeft);
        if (str.equals("edit")) {
            this.status = 1;
            this.listFragment.getController().setRemoveEnabled(true);
            relativeLayout.setVisibility(View.VISIBLE);
            imageButton2.setOnClickListener(this.onAddButtonClickListener);
            imageButton2.setImageResource(R.drawable.btn_add);
            if (Commons.language.equals("en_US")) {
                imageButton.setImageResource(R.drawable.btn_done_e);
            } else if (Commons.language.equals("zh_CN")) {
                imageButton.setImageResource(R.drawable.btn_done_gb);
            } else {
                imageButton.setImageResource(R.drawable.btn_done_c);
            }
            imageButton2.setVisibility(View.VISIBLE);
            this.listFragment.changeListAdapter(R.layout.list_portfolio_edit);
            linearLayout.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.VISIBLE);
            this.listFragment.hidePortfolioTotal();
        } else if (str.equals("list")) {
            this.status = 0;
            this.listFragment.getController().setRemoveEnabled(false);
            relativeLayout.setVisibility(View.GONE);
            imageButton2.setImageResource(R.drawable.btn_back);
            if (Commons.language.equals("en_US")) {
                imageButton.setImageResource(R.drawable.btn_edit_e);
            } else if (Commons.language.equals("zh_CN")) {
                imageButton.setImageResource(R.drawable.btn_edit_gb);
            } else {
                imageButton.setImageResource(R.drawable.btn_edit_c);
            }
            imageButton2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Portfolio_land.this.finish();
                    Intent intent = new Intent();
                    intent.setClass(Portfolio_land.this, MarketSnapshot.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    Portfolio_land.this.startActivity(intent);
                }
            });
            this.listFragment.changeListAdapter(R.layout.list_portfolio);
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.GONE);
            SharedPrefsUtil.putValue((Context) this, "portfolio", this.listFragment.getUpdateString());
            this.listFragment.showPortfolioTotal();
        }
    }

    public void dataResult(final Portfolio_Result portfolio_Result) {
        if (portfolio_Result != null) {
            this.data_array = new ArrayList(Arrays.asList(portfolio_Result.getmainData()));
            Portfolio.updatePortfolioResult(this, this.portfolioRawData, this.data_array, false);
        } else {
            this.data_array = new ArrayList();
            Portfolio.updatePortfolioResult(this, this.portfolioRawData, this.data_array, true);
        }
        Log.v(TAG, "data_array " + this.data_array.size());
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (portfolio_Result != null) {
                        if (portfolio_Result.getContingency().equals("0")) {
                            Portfolio_land.this.ContingencyMessageBox(Commons.language.equals("en_US") ? portfolio_Result.getEngmsg() : portfolio_Result.getChimsg(), true);
                        } else if (portfolio_Result.getContingency().equals("-1")) {
                            if (!Portfolio_land.this.island) {
                                Portfolio_land.this.updateFooterStime("nodatayet");
                            }
                        } else if (!Portfolio_land.this.island) {
                            Portfolio_land.this.updateFooterStime(portfolio_Result.getstime());
                        }
                    } else if (!Portfolio_land.this.island) {
                        Portfolio_land.this.updateFooterStime(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    }
                    if (Portfolio_land.this.status == 0) {
                        Portfolio_land.this.listFragment.setDataArray(Portfolio_land.this.data_array, R.layout.list_portfolio);
                    } else {
                        Portfolio_land.this.listFragment.setDataArray(Portfolio_land.this.data_array, R.layout.list_portfolio_edit);
                    }
                    Portfolio_land.this.dataLoaded();
                } catch (Exception e) {
                    Portfolio_land.this.dataLoaded();
                    Commons.LogDebug(Portfolio_land.TAG, e.getMessage() == null ? "" : e.getMessage());
                }
            }
        });
    }

    public void initUI() {
        this.mainContainer = findViewById(R.id.mainContainer);
        this.clickControlContainer = (ClickControlContainer) findViewById(R.id.clickControlContainer);
        new LinearLayout.LayoutParams(-1, -1);
        this.mainView = findViewById(R.id.appContainer);
        ImageButton imageButton = (ImageButton) findViewById(R.id.btn_selectall);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.btn_selectnone);
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.btn_delete);
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.btnRight);
        ImageButton imageButton5 = (ImageButton) findViewById(R.id.btnLeft);
        if (Commons.language.equals("en_US")) {
            imageButton.setImageResource(R.drawable.btn_selectall_e);
            imageButton2.setImageResource(R.drawable.btn_selectnone_e);
            imageButton3.setImageResource(R.drawable.btn_delete_e);
            imageButton4.setImageResource(R.drawable.btn_edit_e);
        } else if (Commons.language.equals("zh_CN")) {
            imageButton.setImageResource(R.drawable.btn_selectall_gb);
            imageButton2.setImageResource(R.drawable.btn_selectnone_gb);
            imageButton3.setImageResource(R.drawable.btn_delete_gb);
            imageButton4.setImageResource(R.drawable.btn_edit_gb);
        } else {
            imageButton.setImageResource(R.drawable.btn_selectall_c);
            imageButton2.setImageResource(R.drawable.btn_selectnone_c);
            imageButton3.setImageResource(R.drawable.btn_delete_c);
            imageButton4.setImageResource(R.drawable.btn_edit_c);
        }
        this.onAddButtonClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                SharedPrefsUtil.putValue((Context) Portfolio_land.this, "portfolio", Portfolio_land.this.listFragment.getUpdateString());
                Intent intent = new Intent();
                intent.setClass(Portfolio_land.this, PortfolioAdd.class);
                Portfolio_land.this.startActivityForResult(intent, 0);
            }
        };
        imageButton5.setImageResource(R.drawable.btn_back);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Portfolio_land.this.finish();
                Intent intent = new Intent();
                intent.setClass(Portfolio_land.this, MarketSnapshot.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                Portfolio_land.this.startActivity(intent);
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Portfolio_land.this.status == 0) {
                    Portfolio_land.this.changeMode("edit");
                } else {
                    Portfolio_land.this.changeMode("list");
                }
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Portfolio_land.this.listFragment.setAllItemChecked(true);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Portfolio_land.this.listFragment.setAllItemChecked(false);
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Portfolio_land.this.listFragment.removeCheckedItem();
            }
        });
    }

    public void loadJSON() {
        PortfolioJSONParser portfolioJSONParser = new PortfolioJSONParser();
        portfolioJSONParser.setOnJSONCompletedListener(new PortfolioJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(Portfolio_Result portfolio_Result) {
                Portfolio_land.this.dataResult(portfolio_Result);
            }
        });
        portfolioJSONParser.setOnJSONFailedListener(new PortfolioJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                Portfolio_land.this.dataResult((Portfolio_Result) null);
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
        Log.v(TAG, "url " + getString(R.string.url_portfolio) + "?codelist=" + replaceFirst);
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.url_portfolio));
        sb.append("?codelist=");
        sb.append(replaceFirst);
        portfolioJSONParser.loadXML(sb.toString());
        dataLoading();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && intent != null) {
            Bundle extras = intent.getExtras();
            int i3 = extras.getInt("row", -1);
            if (i3 >= 0) {
                this.data_array.get(i3).setDirection(extras.getString("direction"));
                this.data_array.get(i3).setQty(extras.getString("qty"));
                this.data_array.get(i3).setPrice(extras.getString("price"));
                this.data_array.get(i3).setDirection(extras.getString("direction"));
                this.listFragment.adapter.notifyDataSetChanged();
                SharedPrefsUtil.putValue((Context) this, "portfolio", this.listFragment.getUpdateString());
            }
            this.portfolioRawData = SharedPrefsUtil.getValue((Context) this, "portfolio", "");
            Commons.noResumeAction = false;
            loadJSON();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.island = getResources().getConfiguration().orientation == 2;
        Commons.noResumeAction = false;
        Commons.need2reflash = true;
        Log.v(TAG, TAG);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            finish();
            Intent intent = new Intent();
            intent.setClass(this, MarketSnapshot.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            startActivity(intent);
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Commons.need2reflash = true;
        this.island = getResources().getConfiguration().orientation == 2;
        Commons.initStaticText(this);
        String locale = getResources().getConfiguration().locale.toString();
        Log.v("current", "Portfolio_land onCreate " + locale + " : " + Commons.language);
        if (!locale.equals(Commons.language)) {
            String[] split = Commons.language.split("_");
            Locale locale2 = new Locale(split[0], split[1]);
            Locale.setDefault(locale2);
            Configuration configuration = new Configuration();
            configuration.locale = locale2;
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        }
        if (!Commons.noResumeAction) {
            this.status = 0;
            this.leftViewOut = false;
            this.rightViewOut = false;
            this.portfolioRawData = SharedPrefsUtil.getValue((Context) this, "portfolio", "");
            setContentView(R.layout.portfolio);
            initDslvFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.clickControlContainer, this.listFragment, "dslvTag").commit();
            initUI();
            loadJSON();
            return;
        }
        this.isMoving = false;
        Commons.noResumeAction = false;
    }
}
