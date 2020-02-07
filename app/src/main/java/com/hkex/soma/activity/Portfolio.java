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
import java.util.regex.Pattern;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class Portfolio extends AnimatedFragmentActivity implements interface_changemode {
    public static final String TAG = "Portfolio";
    /* access modifiers changed from: private */
    public List<Portfolio_Result.mainData> data_array = null;
    /* access modifiers changed from: private */
    public boolean island = false;
    /* access modifiers changed from: private */
    public Portfolio_List listFragment;
    private MenuContainer menu;
    private View.OnClickListener onAddButtonClickListener;
    private String portfolioRawData;
    /* access modifiers changed from: private */
    public SlideLeftAnimationHandler slideLeftAnimationHandler;
    /* access modifiers changed from: private */
    public int status;

    public static boolean AddOptionToPortfolio(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        String value = SharedPrefsUtil.getValue(context, "portfolio", "");
        if (value.split(",").length >= 20) {
            return false;
        }
        Pattern compile = Pattern.compile("[a-zA-Z]");
        StringBuilder sb = new StringBuilder();
        sb.append(compile.matcher(str).find() ? str : StringFormatter.formatCode(str));
        sb.append("_");
        sb.append(str4);
        sb.append("_");
        sb.append(str5.replace(",", ""));
        sb.append("_");
        sb.append(str6);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append("!");
        sb3.append(str7.equals("1") ? "1" : "0");
        sb3.append("!");
        sb3.append(str8);
        sb3.append("!");
        sb3.append(str9.replace(",", ""));
        sb3.append(",");
        sb3.append(value);
        String sb4 = sb3.toString();
        Log.v(TAG, "rawData " + sb4);
        SharedPrefsUtil.putValue(context, "portfolio", sb4);
        SharedPrefsUtil.putPortfolioValue(context, str, str2.replace(",", ""));
        SharedPrefsUtil.putPortfolioEnValue(context, str, str3.replace(",", ""));
        return true;
    }

    public static boolean AddStockToPortfolio(Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        String value = SharedPrefsUtil.getValue(context, "portfolio", "");
        if (value.split(",").length >= 20) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(StringFormatter.formatCode(str));
        sb.append("!");
        sb.append(str4.equals("1") ? "1" : "0");
        sb.append("!");
        sb.append(str5);
        sb.append("!");
        sb.append(str6.replace(",", ""));
        sb.append(",");
        sb.append(value);
        SharedPrefsUtil.putValue(context, "portfolio", sb.toString());
        SharedPrefsUtil.putPortfolioValue(context, str, str2.replace(",", ""));
        SharedPrefsUtil.putPortfolioEnValue(context, str, str3.replace(",", ""));
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
                        Portfolio_Result.mainData maindata = new Portfolio_Result.mainData();
                        if (split3.length > 1) {
                            String portfolioValue = SharedPrefsUtil.getPortfolioValue(context, split3[0], "");
                            String portfolioEnValue = SharedPrefsUtil.getPortfolioEnValue(context, split3[0], "");
                            maindata.setUcode(split3[0]);
                            maindata.setStrike(split3[2]);
                            maindata.setUName(portfolioEnValue);
                            maindata.setUnmll(portfolioValue);
                            maindata.setType(split3[1]);
                            maindata.setExpiry(split3[3]);
                            maindata.setExpiry_str(split3[3]);
                        } else {
                            String portfolioValue2 = SharedPrefsUtil.getPortfolioValue(context, split2[0], "");
                            maindata.setUName(portfolioValue2);
                            maindata.setUnmll(portfolioValue2);
                        }
                        list.add(maindata);
                        maindata.setcode(split2[0]);
                        maindata.setDirection(split2[1]);
                        maindata.setQty(split2[2]);
                        maindata.setPrice(split2[3]);
                    } else if (list.get(i).getUnmll() == null) {
                        Portfolio_Result.mainData maindata2 = list.get(i);
                        if (split3.length > 1) {
                            String portfolioValue3 = SharedPrefsUtil.getPortfolioValue(context, split3[0], "");
                            String portfolioEnValue2 = SharedPrefsUtil.getPortfolioEnValue(context, split3[0], "");
                            maindata2.setUcode(split3[0]);
                            maindata2.setStrike(split3[2]);
                            maindata2.setUName(portfolioEnValue2);
                            maindata2.setUnmll(portfolioValue3);
                            maindata2.setType(split3[1]);
                            maindata2.setExpiry(split3[3]);
                            maindata2.setExpiry_str(context.getString(R.string.expired));
                        } else {
                            SharedPrefsUtil.getPortfolioValue(context, split2[0], "");
                            maindata2.setUName("");
                            maindata2.setUnmll("");
                            maindata2.setExpiry("");
                            maindata2.setExpiry_str("");
                        }
                        maindata2.setcode(split2[0]);
                        maindata2.setDirection(split2[1]);
                        maindata2.setQty(split2[2]);
                        maindata2.setPrice(split2[3]);
                    } else {
                        Portfolio_Result.mainData maindata3 = list.get(i);
                        maindata3.setType((maindata3.getType() == null || !maindata3.getType().equals("C")) ? "Put" : "Call");
                        if (Commons.language.equals("en_US")) {
                            if (maindata3.getUName().equals("")) {
                                maindata3.setUName(Commons.MapUnderlyingName(convertstr(maindata3.getUcode() == null ? maindata3.getCode() : maindata3.getUcode())));
                            }
                            if (split3.length > 1) {
                                SharedPrefsUtil.putPortfolioValue(context, split3[0], convertstr(maindata3.getUName()));
                            } else {
                                SharedPrefsUtil.putPortfolioValue(context, split2[0], convertstr(maindata3.getUName()));
                            }
                        } else {
                            if (maindata3.getUnmll().equals("")) {
                                maindata3.setUnmll(Commons.MapUnderlyingName(maindata3.getUcode() == null ? convertstr(maindata3.getCode()) : convertstr(maindata3.getUcode())));
                            }
                            if (split3.length > 1) {
                                SharedPrefsUtil.putPortfolioValue(context, split3[0], convertstr(maindata3.getUnmll()));
                            } else {
                                SharedPrefsUtil.putPortfolioValue(context, split2[0], convertstr(maindata3.getUnmll()));
                            }
                        }
                        maindata3.setcode(split2[0]);
                        maindata3.setDirection(split2[1]);
                        maindata3.setQty(split2[2]);
                        maindata3.setPrice(split2[3]);
                    }
                }
            } catch (Exception e) {
                if (!z) {
                    list.remove(i);
                }
                Commons.LogDebug("updatePortfolioResult", e.getMessage());
                e.printStackTrace();
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
            imageButton2.setImageResource(R.drawable.btn_menu);
            if (Commons.language.equals("en_US")) {
                imageButton.setImageResource(R.drawable.btn_edit_e);
            } else if (Commons.language.equals("zh_CN")) {
                imageButton.setImageResource(R.drawable.btn_edit_gb);
            } else {
                imageButton.setImageResource(R.drawable.btn_edit_c);
            }
            imageButton2.setOnClickListener(this.slideLeftAnimationHandler);
            if (this.island) {
                imageButton2.setVisibility(View.INVISIBLE);
            }
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
            updatePortfolioResult(this, this.portfolioRawData, this.data_array, false);
        } else {
            this.data_array = new ArrayList();
            updatePortfolioResult(this, this.portfolioRawData, this.data_array, true);
        }
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (portfolio_Result != null) {
                        if (portfolio_Result.getContingency().equals("0")) {
                            Portfolio.this.ContingencyMessageBox(Commons.language.equals("en_US") ? portfolio_Result.getEngmsg() : portfolio_Result.getChimsg(), true);
                        } else if (portfolio_Result.getContingency().equals("-1")) {
                            if (!Portfolio.this.island) {
                                Portfolio.this.updateFooterStime("nodatayet");
                            }
                        } else if (!Portfolio.this.island) {
                            Portfolio.this.updateFooterStime(portfolio_Result.getstime());
                        }
                    } else if (!Portfolio.this.island) {
                        Portfolio.this.updateFooterStime(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    }
                    if (Portfolio.this.status == 0) {
                        Portfolio.this.listFragment.setDataArray(Portfolio.this.data_array, R.layout.list_portfolio);
                    } else {
                        Portfolio.this.listFragment.setDataArray(Portfolio.this.data_array, R.layout.list_portfolio_edit);
                    }
                    Portfolio.this.dataLoaded();
                } catch (Exception e) {
                    Exception exc = e;
                    Portfolio.this.dataLoaded();
                    Commons.LogDebug(Portfolio.TAG, exc.getMessage() == null ? "" : exc.getMessage());
                    exc.printStackTrace();
                }
            }
        });
    }

    public void initUI() {
        this.mainContainer = findViewById(R.id.mainContainer);
        this.menu = new MenuContainer(this);
        this.mainContainer = findViewById(R.id.mainContainer);
        ClickControlContainer clickControlContainer = (ClickControlContainer) findViewById(R.id.menuContainer);
        this.clickControlContainer = (ClickControlContainer) findViewById(R.id.clickControlContainer);
        clickControlContainer.addView(this.menu, new LinearLayout.LayoutParams(-1, -1));
        initFooter();
        this.leftView = clickControlContainer;
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
        this.slideLeftAnimationHandler = new SlideLeftAnimationHandler(this);
        this.onAddButtonClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                SharedPrefsUtil.putValue((Context) Portfolio.this, "portfolio", Portfolio.this.listFragment.getUpdateString());
                Intent intent = new Intent();
                intent.setClass(Portfolio.this, PortfolioAdd.class);
                Portfolio.this.startActivityForResult(intent, 0);
            }
        };
        if (this.island) {
            imageButton5.setVisibility(View.INVISIBLE);
        } else {
            imageButton5.setOnClickListener(this.slideLeftAnimationHandler);
        }
        this.menu.setSliedBackHandler(this.slideLeftAnimationHandler);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Portfolio.this.status == 0) {
                    Portfolio.this.changeMode("edit");
                } else {
                    Portfolio.this.changeMode("list");
                }
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Portfolio.this.listFragment.setAllItemChecked(true);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Portfolio.this.listFragment.setAllItemChecked(false);
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Portfolio.this.listFragment.removeCheckedItem();
            }
        });
        clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                if (!Portfolio.this.island) {
                    Portfolio.this.slideLeftAnimationHandler.onClick((View) null);
                }
            }
        });
        this.clickControlContainer.setOnLeftToRightSwipeListener(new ClickControlContainer.OnLeftToRightSwipeListener() {
            public void onLeftToRightSwiped() {
                if (Portfolio.this.status == 0 && !Portfolio.this.island) {
                    Portfolio.this.slideLeftAnimationHandler.onClick((View) null);
                }
            }
        });
    }

    public void loadJSON() {
        PortfolioJSONParser portfolioJSONParser = new PortfolioJSONParser();
        portfolioJSONParser.setOnJSONCompletedListener(new PortfolioJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(Portfolio_Result portfolio_Result) {
                Portfolio.this.dataResult(portfolio_Result);
            }
        });
        portfolioJSONParser.setOnJSONFailedListener(new PortfolioJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                Portfolio.this.dataResult((Portfolio_Result) null);
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
        Log.v(TAG, TAG);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.status != 1) {
            return super.onKeyDown(i, keyEvent);
        }
        onResume();
        return false;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.island = getResources().getConfiguration().orientation == 2;
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
