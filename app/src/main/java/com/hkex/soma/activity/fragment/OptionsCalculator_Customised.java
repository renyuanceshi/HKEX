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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hkex.soma.R;
import com.hkex.soma.activity.MarginTable;
import com.hkex.soma.activity.NewOptionsCalculator;
import com.hkex.soma.activity.OptionsCalculatorSearch;
import com.hkex.soma.element.CallPutButton;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.childFragmentWithCallback;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OptionsCalculator_Customised extends Fragment implements childFragmentWithCallback {
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
    private View fragmentView;
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            OptionsCalculator_Customised.this.confirmButton.setAlpha(1.0f);
        }
    };
    /* access modifiers changed from: private */
    public TextView index_but;
    /* access modifiers changed from: private */
    public TextView msg;
    private ImageButton resetButton;
    /* access modifiers changed from: private */
    public String selectedDate;
    /* access modifiers changed from: private */
    public EditText selectionListCal_iv;
    /* access modifiers changed from: private */
    public SelectionList selectionListExpiry;
    /* access modifiers changed from: private */
    public String selectionListExpiry_date;
    /* access modifiers changed from: private */
    public EditText selectionListStrike;
    /* access modifiers changed from: private */
    public EditText selectionListUlast;
    /* access modifiers changed from: private */
    public TextView stock_but;
    /* access modifiers changed from: private */
    public TextView strike_str;
    /* access modifiers changed from: private */
    public String type = "stock";
    /* access modifiers changed from: private */
    public String ucode;
    /* access modifiers changed from: private */
    public String wtype = "C";

    private void initSelectionListExpiry() {
        this.selectionListExpiry = (SelectionList) this.fragmentView.findViewById(R.id.selectionListExpiry);
        String format = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        int parseInt = Integer.parseInt(format.substring(6, 10));
        int parseInt2 = Integer.parseInt(format.substring(3, 5));
        int parseInt3 = Integer.parseInt(format.substring(0, 2));
        this.selectedDate = parseInt + "-" + parseInt2 + "-" + parseInt3;
        this.selectionListExpiry_date = format;
        this.selectionListExpiry.initItems(R.string.set_date, SelectionList.PopTypes.DATE, parseInt, parseInt2, parseInt3);
        this.selectionListExpiry.setSelectedText(format);
        this.selectionListExpiry.setOnDateSelectedListener(new SelectionList.OnDateSelectedListener() {
            public void onDateSelected(String str) {
                Commons.LogDebug(MarginTable.TAG, "date: " + str);
                String format = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                String substring = str.substring(6, 10);
                String substring2 = str.substring(3, 5);
                String substring3 = str.substring(0, 2);
                if ((substring + "/" + substring2 + "/" + substring3).compareTo(format) >= 0) {
                    OptionsCalculator_Customised optionsCalculator_Customised = OptionsCalculator_Customised.this;
                    String unused = optionsCalculator_Customised.selectedDate = Integer.parseInt(substring) + "-" + Integer.parseInt(substring2) + "-" + Integer.parseInt(substring3);
                    OptionsCalculator_Customised optionsCalculator_Customised2 = OptionsCalculator_Customised.this;
                    String unused2 = optionsCalculator_Customised2.selectionListExpiry_date = substring3 + "/" + substring2 + "/" + substring;
                } else {
                    Toast.makeText(OptionsCalculator_Customised.this.OptionsCalculatorSearch_activity, OptionsCalculator_Customised.this.OptionsCalculatorSearch_activity.getResources().getString(R.string.msg_invalid_date), 0).show();
                    OptionsCalculator_Customised.this.selectionListExpiry.initItems(R.string.set_date, SelectionList.PopTypes.DATE, Integer.parseInt(OptionsCalculator_Customised.this.selectionListExpiry_date.substring(6, 10)), Integer.parseInt(OptionsCalculator_Customised.this.selectionListExpiry_date.substring(3, 5)), Integer.parseInt(OptionsCalculator_Customised.this.selectionListExpiry_date.substring(0, 2)));
                }
                OptionsCalculator_Customised.this.selectionListExpiry.setSelectedText(OptionsCalculator_Customised.this.selectionListExpiry_date);
            }
        });
    }

    /* access modifiers changed from: private */
    public void set_value_to_default() {
        this.selectionListStrike.setText("");
        this.selectionListCal_iv.setText("");
        this.selectionListUlast.setText("");
        String format = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        int parseInt = Integer.parseInt(format.substring(6, 10));
        int parseInt2 = Integer.parseInt(format.substring(3, 5));
        int parseInt3 = Integer.parseInt(format.substring(0, 2));
        this.selectedDate = parseInt + "-" + parseInt2 + "-" + parseInt3;
        this.selectionListExpiry.setSelectedText(format);
        this.selectionListExpiry.initItems(R.string.set_date, SelectionList.PopTypes.DATE, parseInt, parseInt2, parseInt3);
        if (!this.wtype.equals("C")) {
            this.callputbutton.callOnClick();
        }
    }

    public void initUI() {
        initSelectionListExpiry();
        this.strike_str = (TextView) this.fragmentView.findViewById(R.id.strike_str);
        this.msg = (TextView) this.fragmentView.findViewById(R.id.msg);
        this.stock_but = (TextView) this.fragmentView.findViewById(R.id.stock_but);
        this.stock_but.setEnabled(true);
        this.stock_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OptionsCalculator_Customised.this.stock_but.setBackgroundResource(R.drawable.long_type_left_off);
                OptionsCalculator_Customised.this.stock_but.setTextColor(OptionsCalculator_Customised.this.CalculatorFragmentActivity.getResources().getColor(R.color.textwhite));
                OptionsCalculator_Customised.this.index_but.setBackgroundResource(R.drawable.long_type_right_on);
                OptionsCalculator_Customised.this.index_but.setTextColor(OptionsCalculator_Customised.this.CalculatorFragmentActivity.getResources().getColor(R.color.textdisable));
                String unused = OptionsCalculator_Customised.this.type = "stock";
                OptionsCalculator_Customised.this.strike_str.setText(R.string.strike);
                OptionsCalculator_Customised.this.msg.setText(R.string.oc_own_stock_msg);
            }
        });
        this.index_but = (TextView) this.fragmentView.findViewById(R.id.index_but);
        this.index_but.setEnabled(true);
        this.index_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OptionsCalculator_Customised.this.stock_but.setBackgroundResource(R.drawable.long_type_left_on);
                OptionsCalculator_Customised.this.stock_but.setTextColor(OptionsCalculator_Customised.this.CalculatorFragmentActivity.getResources().getColor(R.color.textdisable));
                OptionsCalculator_Customised.this.index_but.setBackgroundResource(R.drawable.long_type_right_off);
                OptionsCalculator_Customised.this.index_but.setTextColor(OptionsCalculator_Customised.this.CalculatorFragmentActivity.getResources().getColor(R.color.textwhite));
                String unused = OptionsCalculator_Customised.this.type = "index";
                OptionsCalculator_Customised.this.strike_str.setText(R.string.strike4cal);
                OptionsCalculator_Customised.this.msg.setText(R.string.oc_own_index_msg);
            }
        });
        this.selectionListStrike = (EditText) this.fragmentView.findViewById(R.id.strike);
        this.selectionListCal_iv = (EditText) this.fragmentView.findViewById(R.id.cal_iv);
        this.selectionListUlast = (EditText) this.fragmentView.findViewById(R.id.ulast);
        this.confirmButton = (ImageButton) this.fragmentView.findViewById(R.id.confirmButton);
        this.confirmButton.setAlpha(0.5f);
        this.confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!OptionsCalculator_Customised.this.selectionListStrike.getText().toString().equals("") && OptionsCalculator_Customised.this.selectionListStrike.getText().charAt(0) != '.' && Float.parseFloat(OptionsCalculator_Customised.this.selectionListStrike.getText().toString()) > 0.0f && !OptionsCalculator_Customised.this.selectionListCal_iv.getText().toString().equals("") && OptionsCalculator_Customised.this.selectionListCal_iv.getText().charAt(0) != '.' && Float.parseFloat(OptionsCalculator_Customised.this.selectionListCal_iv.getText().toString()) > 0.0f && !OptionsCalculator_Customised.this.selectionListUlast.getText().toString().equals("") && OptionsCalculator_Customised.this.selectionListUlast.getText().charAt(0) != '.' && Float.parseFloat(OptionsCalculator_Customised.this.selectionListUlast.getText().toString()) > 0.0f) {
                    OptionsCalculator_Customised.this.OptionsCalculatorSearch_activity.set_backFromDetail(true);
                    Intent intent = new Intent(OptionsCalculator_Customised.this.OptionsCalculatorSearch_activity, NewOptionsCalculator.class);
                    intent.putExtra("tab_type", "search");
                    intent.putExtra("mdate", OptionsCalculator_Customised.this.selectedDate);
                    intent.putExtra("wtype", OptionsCalculator_Customised.this.wtype);
                    intent.putExtra("strike", "" + OptionsCalculator_Customised.this.selectionListStrike.getText());
                    intent.putExtra("ucode", "" + OptionsCalculator_Customised.this.ucode);
                    intent.putExtra("iv", "" + OptionsCalculator_Customised.this.selectionListCal_iv.getText());
                    intent.putExtra("hcode", "" + OptionsCalculator_Customised.this.selectionListUlast.getText());
                    intent.putExtra("optiontype", OptionsCalculator_Customised.this.type);
                    OptionsCalculator_Customised.this.OptionsCalculatorSearch_activity.startActivity(intent);
                } else if (OptionsCalculator_Customised.this.selectionListStrike.getText().toString().equals("") || OptionsCalculator_Customised.this.selectionListStrike.getText().charAt(0) == '.' || Float.parseFloat(OptionsCalculator_Customised.this.selectionListStrike.getText().toString()) <= 0.0f) {
                    Toast.makeText(OptionsCalculator_Customised.this.OptionsCalculatorSearch_activity, OptionsCalculator_Customised.this.OptionsCalculatorSearch_activity.getResources().getString(R.string.msg_strike_zero), 0).show();
                } else if (OptionsCalculator_Customised.this.selectionListCal_iv.getText().toString().equals("") || OptionsCalculator_Customised.this.selectionListCal_iv.getText().charAt(0) == '.' || Float.parseFloat(OptionsCalculator_Customised.this.selectionListCal_iv.getText().toString()) <= 0.0f) {
                    Toast.makeText(OptionsCalculator_Customised.this.OptionsCalculatorSearch_activity, OptionsCalculator_Customised.this.OptionsCalculatorSearch_activity.getResources().getString(R.string.msg_iv_zero), 0).show();
                } else if (OptionsCalculator_Customised.this.selectionListUlast.getText().toString().equals("") || OptionsCalculator_Customised.this.selectionListUlast.getText().charAt(0) == '.' || Float.parseFloat(OptionsCalculator_Customised.this.selectionListUlast.getText().toString()) <= 0.0f) {
                    Toast.makeText(OptionsCalculator_Customised.this.OptionsCalculatorSearch_activity, OptionsCalculator_Customised.this.OptionsCalculatorSearch_activity.getResources().getString(R.string.msg_ulast_zero), 0).show();
                }
            }
        });
        this.handler.sendEmptyMessage(0);
        this.resetButton = (ImageButton) this.fragmentView.findViewById(R.id.resetButton);
        this.resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OptionsCalculator_Customised.this.set_value_to_default();
            }
        });
        this.callputbutton = (CallPutButton) this.fragmentView.findViewById(R.id.callputbutton);
        this.callputbutton.setOnLayoutClickListener(new CallPutButton.OnLayoutClickListener() {
            public void onLayoutClicked(int i) {
                Commons.LogDebug("Search_Options", "state: " + i);
                if (i == 1) {
                    String unused = OptionsCalculator_Customised.this.wtype = "C";
                } else {
                    String unused2 = OptionsCalculator_Customised.this.wtype = "P";
                }
            }
        });
        set_value_to_default();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.CalculatorFragmentActivity = (OptionsCalculatorSearch) getActivity();
        this.CalculatorFragmentActivity.setchildFragment(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.options_calculator_customised, viewGroup, false);
        this.OptionsCalculatorSearch_activity = (OptionsCalculatorSearch) getActivity();
        initUI();
        return this.fragmentView;
    }

    public void selectionListCallBack(SelectionList.PopTypes popTypes, String str) {
        Log.v(TAG, str);
    }
}
