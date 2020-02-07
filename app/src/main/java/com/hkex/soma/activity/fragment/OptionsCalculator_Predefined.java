package com.hkex.soma.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.NewOptionsCalculator;
import com.hkex.soma.activity.OptionsCalculatorSearch;
import com.hkex.soma.dataModel.Calculator_Result;
import com.hkex.soma.element.CallPutButton;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.CommonsIndex;
import com.hkex.soma.utils.StringFormatter;
import com.hkex.soma.utils.childFragmentWithCallback;
import java.util.HashMap;

public class OptionsCalculator_Predefined extends Fragment implements childFragmentWithCallback {
    static final String TAG = "OptionsCalculatorSearch";
    /* access modifiers changed from: private */
    public OptionsCalculatorSearch CalculatorFragmentActivity;
    /* access modifiers changed from: private */
    public OptionsCalculatorSearch OptionsCalculatorSearch_activity;
    public boolean backFromOptionDetail = false;
    private CallPutButton callputbutton;
    /* access modifiers changed from: private */
    public ImageButton confirmButton;
    public String currenttag;
    private ImageButton editButton;
    /* access modifiers changed from: private */
    public String[] expiries;
    private View fragmentView;
    /* access modifiers changed from: private */
    public Handler handler = new Handler() {
        public void handleMessage(Message message) {
            OptionsCalculator_Predefined.this.confirmButton.setAlpha(1.0f);
        }
    };
    /* access modifiers changed from: private */
    public TextView index_but;
    /* access modifiers changed from: private */
    public LinearLayout index_code_box;
    private String[] index_items;
    private String[] index_items_val;
    /* access modifiers changed from: private */
    public HashMap indexdata;
    /* access modifiers changed from: private */
    public boolean is0930get = false;
    /* access modifiers changed from: private */
    public boolean is0945get = false;
    /* access modifiers changed from: private */
    public boolean isafter0930 = false;
    /* access modifiers changed from: private */
    public boolean isafter0945 = false;
    /* access modifiers changed from: private */
    public boolean isstocknow = true;
    /* access modifiers changed from: private */
    public TextView msg;
    /* access modifiers changed from: private */
    public SelectionList selectionListCode;
    /* access modifiers changed from: private */
    public SelectionList selectionListExpiry;
    /* access modifiers changed from: private */
    public SelectionList selectionListIndexCode;
    private SelectionList selectionListName;
    /* access modifiers changed from: private */
    public SelectionList selectionListStrike;
    /* access modifiers changed from: private */
    public TextView stock_but;
    /* access modifiers changed from: private */
    public LinearLayout stock_code_box;
    private HashMap stockdata;
    /* access modifiers changed from: private */
    public TextView strike_str;
    private String[] strikes;
    /* access modifiers changed from: private */
    public String ucode;
    private int ucodeIndex = 0;
    /* access modifiers changed from: private */
    public String wtype = "C";

    private void initSelectionListExpiry() {
        this.selectionListExpiry = (SelectionList) this.fragmentView.findViewById(R.id.selectionListExpiry);
        this.selectionListExpiry.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                if (OptionsCalculator_Predefined.this.isstocknow) {
                    OptionsCalculator_Predefined.this.set_stock_hashmap("expiries_index", Integer.valueOf(i));
                    OptionsCalculator_Predefined.this.updateSelectionListExpiry(OptionsCalculator_Predefined.this.selectionListCode.getSelectedText(), OptionsCalculator_Predefined.this.expiries[i]);
                    return;
                }
                OptionsCalculator_Predefined.this.set_index_hashmap("expiries_index", Integer.valueOf(i));
                OptionsCalculator_Predefined.this.updateSelectionListExpiry(OptionsCalculator_Predefined.this.indexdata.get("ucode").toString(), OptionsCalculator_Predefined.this.expiries[i]);
            }
        });
    }

    /* access modifiers changed from: private */
    public void set_index_data() {
        String obj = this.indexdata.get("ucode").toString();
        this.expiries = CommonsIndex.getExpiries(obj, (String) null);
        this.selectionListExpiry.initItems(this.expiries, -1, SelectionList.PopTypes.EXPIRY, ((Integer) this.indexdata.get("expiries_index")).intValue());
        this.selectionListExpiry.setSelectedText(StringFormatter.formatExpiry(this.expiries[((Integer) this.indexdata.get("expiries_index")).intValue()]));
        this.selectionListExpiry.setEnabled(true);
        this.strikes = CommonsIndex.getStrikes(obj, this.expiries[((Integer) this.indexdata.get("expiries_index")).intValue()]);
        this.selectionListStrike.initItems(this.strikes, -1, SelectionList.PopTypes.LIST, ((Integer) this.indexdata.get("strikes_index")).intValue());
        this.selectionListStrike.setEnabled(true);
        this.ucode = obj;
        if (!this.wtype.equals(this.indexdata.get("wtype").toString())) {
            this.callputbutton.callOnClick();
        }
    }

    /* access modifiers changed from: private */
    public void set_index_hashmap(String str, Object obj) {
        if (this.indexdata == null) {
            this.indexdata = new HashMap();
        }
        this.indexdata.put(str, obj);
    }

    /* access modifiers changed from: private */
    public void set_stock_data() {
        String obj = this.stockdata.get("ucode").toString();
        this.expiries = Commons.getExpiries(obj, (String) null);
        this.selectionListExpiry.initItems(this.expiries, -1, SelectionList.PopTypes.EXPIRY, ((Integer) this.stockdata.get("expiries_index")).intValue());
        this.selectionListExpiry.setSelectedText(StringFormatter.formatExpiry(this.expiries[((Integer) this.stockdata.get("expiries_index")).intValue()]));
        this.selectionListExpiry.setEnabled(true);
        this.strikes = Commons.getStrikes(obj, this.expiries[((Integer) this.stockdata.get("expiries_index")).intValue()]);
        this.selectionListStrike.initItems(this.strikes, -1, SelectionList.PopTypes.LIST, ((Integer) this.stockdata.get("strikes_index")).intValue());
        this.selectionListStrike.setEnabled(true);
        this.ucode = obj;
        if (!this.wtype.equals(this.stockdata.get("wtype").toString())) {
            this.callputbutton.callOnClick();
        }
    }

    /* access modifiers changed from: private */
    public void set_stock_hashmap(String str, Object obj) {
        if (this.stockdata == null) {
            this.stockdata = new HashMap();
        }
        this.stockdata.put(str, obj);
    }

    /* access modifiers changed from: private */
    public void set_value_to_default() {
        updateSelectionListCode(Commons.defaultUnderlyingCode);
        if (!this.wtype.equals("C")) {
            this.callputbutton.callOnClick();
        }
    }

    private void updateSelectionListCode(String str) {
        this.selectionListCode.setSelectedText(str);
        this.selectionListName.setSelectedText(Commons.MapUnderlyingName(str));
        this.expiries = Commons.getExpiries(str, (String) null);
        this.selectionListExpiry.initItems(this.expiries, -1, SelectionList.PopTypes.EXPIRY, 0);
        this.selectionListExpiry.setSelectedText(StringFormatter.formatExpiry(this.expiries[0]));
        this.selectionListExpiry.setEnabled(true);
        this.strikes = Commons.getStrikes(str, this.expiries[0]);
        this.selectionListStrike.initItems(this.strikes, -1, SelectionList.PopTypes.LIST, 0);
        this.selectionListStrike.setEnabled(true);
        this.selectionListStrike.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                if (OptionsCalculator_Predefined.this.isstocknow) {
                    OptionsCalculator_Predefined.this.set_stock_hashmap("strikes_index", Integer.valueOf(i));
                } else {
                    OptionsCalculator_Predefined.this.set_index_hashmap("strikes_index", Integer.valueOf(i));
                }
            }
        });
        this.ucode = str;
    }

    /* access modifiers changed from: private */
    public void updateSelectionListExpiry(String str, String str2) {
        this.selectionListExpiry.setSelectedText(StringFormatter.formatExpiry(str2));
        if (this.isstocknow) {
            this.strikes = Commons.getStrikes(str, str2);
        } else {
            this.strikes = CommonsIndex.getStrikes(str, str2);
        }
        this.selectionListStrike.initItems(this.strikes, -1, SelectionList.PopTypes.LIST, 0);
        this.selectionListStrike.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                if (OptionsCalculator_Predefined.this.isstocknow) {
                    OptionsCalculator_Predefined.this.set_stock_hashmap("strikes_index", Integer.valueOf(i));
                } else {
                    OptionsCalculator_Predefined.this.set_index_hashmap("strikes_index", Integer.valueOf(i));
                }
            }
        });
    }

    public void initUI() {
        this.msg = (TextView) this.fragmentView.findViewById(R.id.msg);
        this.stock_code_box = (LinearLayout) this.fragmentView.findViewById(R.id.stock_code_box);
        this.index_code_box = (LinearLayout) this.fragmentView.findViewById(R.id.index_code_box);
        this.strike_str = (TextView) this.fragmentView.findViewById(R.id.strike_str);
        this.stock_but = (TextView) this.fragmentView.findViewById(R.id.stock_but);
        this.stock_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    boolean unused = OptionsCalculator_Predefined.this.isstocknow = true;
                    OptionsCalculator_Predefined.this.stock_but.setBackgroundResource(R.drawable.long_type_left_off);
                    OptionsCalculator_Predefined.this.stock_but.setTextColor(OptionsCalculator_Predefined.this.CalculatorFragmentActivity.getResources().getColor(R.color.textwhite));
                    OptionsCalculator_Predefined.this.index_but.setBackgroundResource(R.drawable.long_type_right_on);
                    OptionsCalculator_Predefined.this.index_but.setTextColor(OptionsCalculator_Predefined.this.CalculatorFragmentActivity.getResources().getColor(R.color.textdisable));
                    OptionsCalculator_Predefined.this.stock_code_box.setVisibility(View.VISIBLE);
                    OptionsCalculator_Predefined.this.index_code_box.setVisibility(View.GONE);
                    OptionsCalculator_Predefined.this.stock_but.setEnabled(false);
                    OptionsCalculator_Predefined.this.index_but.setEnabled(true);
                    OptionsCalculator_Predefined.this.set_stock_data();
                    OptionsCalculator_Predefined.this.strike_str.setText(R.string.strike);
                    OptionsCalculator_Predefined.this.msg.setText(R.string.oc_own_stock_msg);
                    if (OptionsCalculator_Predefined.this.isafter0945) {
                        OptionsCalculator_Predefined.this.confirmButton.setAlpha(1.0f);
                    } else {
                        OptionsCalculator_Predefined.this.confirmButton.setAlpha(0.5f);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.index_but = (TextView) this.fragmentView.findViewById(R.id.index_but);
        this.index_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    boolean unused = OptionsCalculator_Predefined.this.isstocknow = false;
                    OptionsCalculator_Predefined.this.stock_but.setBackgroundResource(R.drawable.long_type_left_on);
                    OptionsCalculator_Predefined.this.stock_but.setTextColor(OptionsCalculator_Predefined.this.CalculatorFragmentActivity.getResources().getColor(R.color.textdisable));
                    OptionsCalculator_Predefined.this.index_but.setBackgroundResource(R.drawable.long_type_right_off);
                    OptionsCalculator_Predefined.this.index_but.setTextColor(OptionsCalculator_Predefined.this.CalculatorFragmentActivity.getResources().getColor(R.color.textwhite));
                    OptionsCalculator_Predefined.this.stock_code_box.setVisibility(View.GONE);
                    OptionsCalculator_Predefined.this.index_code_box.setVisibility(View.VISIBLE);
                    OptionsCalculator_Predefined.this.stock_but.setEnabled(true);
                    OptionsCalculator_Predefined.this.index_but.setEnabled(false);
                    OptionsCalculator_Predefined.this.set_index_data();
                    OptionsCalculator_Predefined.this.strike_str.setText(R.string.strike4cal);
                    OptionsCalculator_Predefined.this.msg.setText(R.string.oc_own_index_msg);
                    if (OptionsCalculator_Predefined.this.isafter0930) {
                        OptionsCalculator_Predefined.this.confirmButton.setAlpha(1.0f);
                    } else {
                        OptionsCalculator_Predefined.this.confirmButton.setAlpha(0.5f);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        int i = 0;
        this.index_items = new String[Commons.indexList.getmainData().length];
        this.index_items_val = new String[Commons.indexList.getmainData().length];
        while (true) {
            int i2 = i;
            if (i2 < Commons.indexList.getmainData().length) {
                String[] strArr = this.index_items;
                StringBuilder sb = new StringBuilder();
                sb.append(Commons.indexList.getmainData()[i2].getUcode());
                sb.append(" - ");
                sb.append(Commons.language.equals("en_US") ? Commons.indexList.getmainData()[i2].getUname() : Commons.indexList.getmainData()[i2].getUnmll());
                strArr[i2] = sb.toString();
                this.index_items_val[i2] = Commons.indexList.getmainData()[i2].getUcode();
                i = i2 + 1;
            } else {
                this.selectionListIndexCode = (SelectionList) this.fragmentView.findViewById(R.id.selectionListIndexCode);
                this.selectionListIndexCode.initItems(SelectionList.PopTypes.INDEX, "code", true);
                SelectionList selectionList = this.selectionListIndexCode;
                selectionList.setSelectedText(CommonsIndex.commonList.getIndexcode() + " - " + Commons.getIndexName(CommonsIndex.commonList.getIndexcode(), Commons.language.equals("en_US")));
                this.selectionListCode = (SelectionList) this.fragmentView.findViewById(R.id.selectionListCode);
                this.selectionListCode.initItems(SelectionList.PopTypes.CODE, "code", true);
                this.selectionListName = (SelectionList) this.fragmentView.findViewById(R.id.selectionListName);
                this.selectionListName.initItems(SelectionList.PopTypes.NAME, "name", true);
                initSelectionListExpiry();
                this.selectionListStrike = (SelectionList) this.fragmentView.findViewById(R.id.selectionListStrike);
                this.confirmButton = (ImageButton) this.fragmentView.findViewById(R.id.confirmButton);
                this.confirmButton.setAlpha(0.5f);
                this.confirmButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if ((OptionsCalculator_Predefined.this.isafter0945 && OptionsCalculator_Predefined.this.isstocknow) || (OptionsCalculator_Predefined.this.isafter0930 && !OptionsCalculator_Predefined.this.isstocknow)) {
                            OptionsCalculator_Predefined.this.OptionsCalculatorSearch_activity.set_backFromDetail(true);
                            Intent intent = new Intent(OptionsCalculator_Predefined.this.OptionsCalculatorSearch_activity, NewOptionsCalculator.class);
                            intent.putExtra("tab_type", "update");
                            intent.putExtra("mdate", OptionsCalculator_Predefined.this.selectionListExpiry.getSelectedItemText());
                            intent.putExtra("wtype", OptionsCalculator_Predefined.this.wtype);
                            intent.putExtra("strike", "" + OptionsCalculator_Predefined.this.selectionListStrike.getSelectedText());
                            if (OptionsCalculator_Predefined.this.isstocknow) {
                                intent.putExtra("ucode", "" + OptionsCalculator_Predefined.this.ucode);
                                intent.putExtra("hcode", "" + Commons.getHcode(OptionsCalculator_Predefined.this.ucode, OptionsCalculator_Predefined.this.selectionListExpiry.getSelectedItemText(), OptionsCalculator_Predefined.this.selectionListStrike.getSelectedText()));
                                intent.putExtra("optiontype", "stock");
                            } else {
                                intent.putExtra("ucode", "" + OptionsCalculator_Predefined.this.indexdata.get("ucode").toString());
                                intent.putExtra("hcode", "" + CommonsIndex.getHcode(OptionsCalculator_Predefined.this.ucode, OptionsCalculator_Predefined.this.selectionListExpiry.getSelectedItemText(), OptionsCalculator_Predefined.this.selectionListStrike.getSelectedText()));
                                intent.putExtra("optiontype", "index");
                            }
                            intent.putExtra("iv", "");
                            OptionsCalculator_Predefined.this.OptionsCalculatorSearch_activity.startActivity(intent);
                        } else if (OptionsCalculator_Predefined.this.isstocknow) {
                            Toast.makeText(OptionsCalculator_Predefined.this.OptionsCalculatorSearch_activity, OptionsCalculator_Predefined.this.OptionsCalculatorSearch_activity.getResources().getString(R.string.msg_data_after0945), 0).show();
                        } else {
                            Toast.makeText(OptionsCalculator_Predefined.this.OptionsCalculatorSearch_activity, OptionsCalculator_Predefined.this.OptionsCalculatorSearch_activity.getResources().getString(R.string.msg_data_after0930), 0).show();
                        }
                    }
                });
                GenericJSONParser genericJSONParser = new GenericJSONParser(Calculator_Result.class);
                genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<Calculator_Result>() {
                    public void OnJSONCompleted(Calculator_Result calculator_Result) {
                        Commons.LogDebug("GenericJSONParser", "GenericJSONParser" + calculator_Result.toString());
                        if (!calculator_Result.getContingency().equals("-1")) {
                            if (OptionsCalculator_Predefined.this.isstocknow) {
                                OptionsCalculator_Predefined.this.handler.sendEmptyMessage(0);
                            }
                            boolean unused = OptionsCalculator_Predefined.this.isafter0945 = true;
                        }
                        boolean unused2 = OptionsCalculator_Predefined.this.is0945get = true;
                        if (OptionsCalculator_Predefined.this.is0930get) {
                            OptionsCalculator_Predefined.this.OptionsCalculatorSearch_activity.dataLoaded();
                        }
                    }
                });
                genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
                    public void OnJSONFailed(Runnable runnable, Exception exc) {
                        Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                        OptionsCalculator_Predefined.this.OptionsCalculatorSearch_activity.dataLoaded();
                    }
                });
                genericJSONParser.loadXML(getString(R.string.url_calculator) + "?type=contingency");
                GenericJSONParser genericJSONParser2 = new GenericJSONParser(Calculator_Result.class);
                genericJSONParser2.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<Calculator_Result>() {
                    public void OnJSONCompleted(Calculator_Result calculator_Result) {
                        Commons.LogDebug("GenericJSONParser", "GenericJSONParser" + calculator_Result.toString());
                        if (!calculator_Result.getContingency().equals("-1")) {
                            if (!OptionsCalculator_Predefined.this.isstocknow) {
                                OptionsCalculator_Predefined.this.handler.sendEmptyMessage(0);
                            }
                            boolean unused = OptionsCalculator_Predefined.this.isafter0930 = true;
                        }
                        boolean unused2 = OptionsCalculator_Predefined.this.is0930get = true;
                        if (OptionsCalculator_Predefined.this.is0945get) {
                            OptionsCalculator_Predefined.this.OptionsCalculatorSearch_activity.dataLoaded();
                        }
                    }
                });
                genericJSONParser2.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
                    public void OnJSONFailed(Runnable runnable, Exception exc) {
                        Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                        OptionsCalculator_Predefined.this.OptionsCalculatorSearch_activity.dataLoaded();
                    }
                });
                genericJSONParser2.loadXML(getString(R.string.url_calculator_index) + "?type=contingency");
                this.OptionsCalculatorSearch_activity.dataLoading();
                this.editButton = (ImageButton) this.fragmentView.findViewById(R.id.editButton);
                this.editButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (OptionsCalculator_Predefined.this.isstocknow) {
                            OptionsCalculator_Predefined.this.set_stock_hashmap("expiries_index", 0);
                            OptionsCalculator_Predefined.this.set_stock_hashmap("strikes_index", 0);
                            OptionsCalculator_Predefined.this.set_stock_hashmap("wtype", "C");
                            OptionsCalculator_Predefined.this.set_stock_hashmap("ucode", Commons.defaultUnderlyingCode);
                            OptionsCalculator_Predefined.this.set_stock_data();
                            OptionsCalculator_Predefined.this.set_value_to_default();
                            return;
                        }
                        OptionsCalculator_Predefined.this.set_index_hashmap("expiries_index", 0);
                        OptionsCalculator_Predefined.this.set_index_hashmap("strikes_index", 0);
                        OptionsCalculator_Predefined.this.set_index_hashmap("wtype", "C");
                        OptionsCalculator_Predefined.this.set_index_hashmap("ucode", CommonsIndex.defaultUnderlyingCode);
                        OptionsCalculator_Predefined.this.set_index_data();
                        OptionsCalculator_Predefined.this.selectionListIndexCode.initItems(SelectionList.PopTypes.INDEX, "code", true);
                        SelectionList access$2500 = OptionsCalculator_Predefined.this.selectionListIndexCode;
                        access$2500.setSelectedText(CommonsIndex.commonList.getIndexcode() + " - " + Commons.getIndexName(CommonsIndex.commonList.getIndexcode(), Commons.language.equals("en_US")));
                    }
                });
                this.callputbutton = (CallPutButton) this.fragmentView.findViewById(R.id.callputbutton);
                this.callputbutton.setOnLayoutClickListener(new CallPutButton.OnLayoutClickListener() {
                    public void onLayoutClicked(int i) {
                        Commons.LogDebug("Search_Options", "state: " + i);
                        if (i == 1) {
                            String unused = OptionsCalculator_Predefined.this.wtype = "C";
                        } else {
                            String unused2 = OptionsCalculator_Predefined.this.wtype = "P";
                        }
                        if (OptionsCalculator_Predefined.this.isstocknow) {
                            OptionsCalculator_Predefined.this.set_stock_hashmap("wtype", OptionsCalculator_Predefined.this.wtype);
                        } else {
                            OptionsCalculator_Predefined.this.set_index_hashmap("wtype", OptionsCalculator_Predefined.this.wtype);
                        }
                    }
                });
                set_value_to_default();
                return;
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.CalculatorFragmentActivity = (OptionsCalculatorSearch) getActivity();
        this.CalculatorFragmentActivity.setchildFragment(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.options_calculator_predefined, viewGroup, false);
        this.OptionsCalculatorSearch_activity = (OptionsCalculatorSearch) getActivity();
        try {
            set_stock_hashmap("expiries_index", 0);
            set_stock_hashmap("strikes_index", 0);
            set_stock_hashmap("wtype", "C");
            set_stock_hashmap("ucode", Commons.defaultUnderlyingCode);
            set_index_hashmap("expiries_index", 0);
            set_index_hashmap("strikes_index", 0);
            set_index_hashmap("wtype", "C");
            set_index_hashmap("ucode", CommonsIndex.defaultUnderlyingCode);
            initUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.fragmentView;
    }

    public void selectionListCallBack(SelectionList.PopTypes popTypes, String str) {
        Log.v(TAG, str);
        if (popTypes != SelectionList.PopTypes.INDEX) {
            updateSelectionListCode(str);
            return;
        }
        set_index_hashmap("ucode", str.split(" - ")[0]);
        this.selectionListIndexCode.setSelectedText(str);
        set_index_data();
    }
}
