package com.hkex.soma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.basic.MasterActivity;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;

public class CalculatorAssumption extends MasterActivity {
    private EditText cal_dividend1;
    private EditText cal_dividend2;
    private String divAmount1;
    private String divAmount2;
    private String divDate1;
    private String divDate2;
    private TextView irText;
    private String rate;
    private SelectionList selectionListDivideDate1;
    private SelectionList selectionListDivideDate2;

    /* access modifiers changed from: private */
    public void ACfinishWithResult() {
        Intent intent = new Intent();
        intent.putExtra("divDate1", this.selectionListDivideDate1.getSelectedText());
        intent.putExtra("divDate2", this.selectionListDivideDate2.getSelectedText());
        intent.putExtra("divAmount1", this.cal_dividend1.getText().toString());
        intent.putExtra("divAmount2", this.cal_dividend2.getText().toString());
        intent.putExtra("rate", this.irText.getText().toString());
        setResult(-1, intent);
        finish();
    }

    private void initIRateSeekBar(String str) {
        float parseFloat = Float.parseFloat(str);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_irate);
        this.irText = (TextView) findViewById(R.id.cal_rate);
        this.irText.setText(String.valueOf(parseFloat));
        seekBar.setMax(500);
        if (parseFloat > 5.0f) {
            seekBar.setProgress(500);
        } else {
            seekBar.setProgress(Math.round((parseFloat / 5.0f) * 500.0f));
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    CalculatorAssumption.this.irText.setText(StringFormatter.formatInterestRate((((float) i) * 5.0f) / 500.0f));
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    /* access modifiers changed from: private */
    public void resetField() {
        initIRateSeekBar(this.rate);
        this.cal_dividend1.setText(this.divAmount1);
        this.cal_dividend2.setText(this.divAmount2);
        this.selectionListDivideDate1.setSelectedText(this.divDate1);
        this.selectionListDivideDate2.setSelectedText(this.divDate2);
    }

    public void hideKeyBoard() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
    }

    public void initUI() {
        ImageButton imageButton = (ImageButton) findViewById(R.id.btnReset);
        ImageView imageView = (ImageView) findViewById(R.id.btnLeft);
        ImageView imageView2 = (ImageView) findViewById(R.id.divideDate1Label);
        ImageView imageView3 = (ImageView) findViewById(R.id.divideDate2Label);
        ImageView imageView4 = (ImageView) findViewById(R.id.divideAmount1Label);
        ImageView imageView5 = (ImageView) findViewById(R.id.divideAmount2Label);
        if (Commons.language.equals("en_US")) {
            imageButton.setImageResource(R.drawable.btn_reset_red_e);
            imageView.setImageResource(R.drawable.btn_done_e);
            imageView2.setImageResource(R.drawable.txt_dividedate1_e);
            imageView3.setImageResource(R.drawable.txt_dividedate2_e);
            imageView4.setImageResource(R.drawable.txt_divideamount1_e);
            imageView5.setImageResource(R.drawable.txt_divideamount2_e);
        } else if (Commons.language.equals("zh_CN")) {
            imageButton.setImageResource(R.drawable.btn_reset_red_gb);
            imageView.setImageResource(R.drawable.btn_done_gb);
            imageView2.setImageResource(R.drawable.txt_dividedate1_gb);
            imageView3.setImageResource(R.drawable.txt_dividedate2_gb);
            imageView4.setImageResource(R.drawable.txt_divideamount1_gb);
            imageView5.setImageResource(R.drawable.txt_divideamount2_gb);
        } else {
            imageButton.setImageResource(R.drawable.btn_reset_red_c);
            imageView.setImageResource(R.drawable.btn_done_c);
            imageView2.setImageResource(R.drawable.txt_dividedate1_c);
            imageView3.setImageResource(R.drawable.txt_dividedate2_c);
            imageView4.setImageResource(R.drawable.txt_divideamount1_c);
            imageView5.setImageResource(R.drawable.txt_divideamount2_c);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CalculatorAssumption.this.ACfinishWithResult();
            }
        });
        initIRateSeekBar(this.rate);
        this.selectionListDivideDate1 = (SelectionList) findViewById(R.id.selectionListDivideDate1);
        this.selectionListDivideDate2 = (SelectionList) findViewById(R.id.selectionListDivideDate2);
        String[] split = this.divDate1.split("/");
        String[] split2 = this.divDate2.split("/");
        try {
            this.selectionListDivideDate1.initItems(R.string.set_date, SelectionList.PopTypes.DATE, Integer.parseInt(split[2]), Integer.parseInt(split[1]), Integer.parseInt(split[0]));
            this.selectionListDivideDate1.setSelectedText(this.divDate1);
            this.selectionListDivideDate2.initItems(R.string.set_date, SelectionList.PopTypes.DATE, Integer.parseInt(split2[2]), Integer.parseInt(split2[1]), Integer.parseInt(split2[0]));
            this.selectionListDivideDate2.setSelectedText(this.divDate2);
        } catch (Exception e) {
            Commons.LogDebug("CalculatorAssumption", "selectionListDivideDate Error");
        }
        this.cal_dividend1 = (EditText) findViewById(R.id.cal_dividend1);
        this.cal_dividend1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                CalculatorAssumption.this.cal_dividend1.setText("");
                return false;
            }
        });
        this.cal_dividend2 = (EditText) findViewById(R.id.cal_dividend2);
        this.cal_dividend2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                CalculatorAssumption.this.cal_dividend2.setText("");
                return false;
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CalculatorAssumption.this.resetField();
            }
        });
        this.cal_dividend1.setText(this.divAmount1);
        this.cal_dividend2.setText(this.divAmount2);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cal_assumption);
        this.rate = getIntent().getStringExtra("rate");
        this.divDate1 = getIntent().getStringExtra("divDate1");
        this.divDate2 = getIntent().getStringExtra("divDate2");
        this.divAmount1 = getIntent().getStringExtra("divAmount1");
        this.divAmount2 = getIntent().getStringExtra("divAmount2");
        initUI();
    }
}
