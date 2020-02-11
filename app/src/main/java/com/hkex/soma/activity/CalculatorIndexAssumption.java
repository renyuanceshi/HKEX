package com.hkex.soma.activity;

import android.content.Context;
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
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;

public class CalculatorIndexAssumption extends MasterActivity {
    private EditText dividend_yield;
    private TextView irText;
    private String rate;
    private String yield;

    /* access modifiers changed from: private */
    public void ACfinishWithResult() {
        Intent intent = new Intent();
        intent.putExtra("yield", this.dividend_yield.getText().toString());
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
                    CalculatorIndexAssumption.this.irText.setText(StringFormatter.formatInterestRate((((float) i) * 5.0f) / 500.0f));
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
        this.dividend_yield.setText(this.yield);
    }

    public void hideKeyBoard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
    }

    public void initUI() {
        ImageButton imageButton = (ImageButton) findViewById(R.id.btnReset);
        ImageView imageView = (ImageView) findViewById(R.id.btnLeft);
        if (Commons.language.equals("en_US")) {
            imageButton.setImageResource(R.drawable.btn_reset_red_e);
            imageView.setImageResource(R.drawable.btn_done_e);
        } else if (Commons.language.equals("zh_CN")) {
            imageButton.setImageResource(R.drawable.btn_reset_red_gb);
            imageView.setImageResource(R.drawable.btn_done_gb);
        } else {
            imageButton.setImageResource(R.drawable.btn_reset_red_c);
            imageView.setImageResource(R.drawable.btn_done_c);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CalculatorIndexAssumption.this.ACfinishWithResult();
            }
        });
        initIRateSeekBar(this.rate);
        this.dividend_yield = (EditText) findViewById(R.id.dividend_yield);
        this.dividend_yield.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                CalculatorIndexAssumption.this.dividend_yield.setText("");
                return false;
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CalculatorIndexAssumption.this.resetField();
            }
        });
        this.dividend_yield.setText(this.yield);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cal_index_assumption);
        this.rate = getIntent().getStringExtra("rate");
        this.yield = getIntent().getStringExtra("yield");
        initUI();
    }
}
