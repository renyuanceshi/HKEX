package com.hkex.soma.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.basic.MasterActivity;
import com.hkex.soma.dataModel.Calculator_Result;
import com.hkex.soma.element.TutorialDialog;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;
import com.hkex.soma.utils.StringFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewOptionsCalculator extends MasterActivity {
    private String amount1;
    private String amount2;
    private Calculator_Result data_result;
    private Calculator_Result data_result2;
    private String date1;
    private String date2;
    private String hcode;
    private String initCalculatedValue = "";
    private String iv;
    private String mdate;
    private String oid;
    private String optiontype = "stock";
    private String originaldate1;
    private String originaldate2;
    private String originalrate;
    private String rate;
    private String strike;
    private String tab_type;
    private String tm;
    public String ucode;
    private String ulast;
    private String wtype;
    public String yield;

    private void calculateResultRequest() {
        double d;
        EditText editText = (EditText) findViewById(R.id.cal_iv);
        this.ulast = ((EditText) findViewById(R.id.cal_ulast)).getText().toString();
        this.tm = ((EditText) findViewById(R.id.cal_tm)).getText().toString();
        try {
            d = Double.parseDouble(editText.getText().toString()) / 100.0d;
        } catch (Exception e) {
            d = 0.0d;
        }
        this.iv = Double.toString(d);
        loadJSON("getPrice");
    }

    /* access modifiers changed from: private */
    public void initIVSeekBar() {
        float parseFloat = Float.parseFloat(this.data_result.getIv());
        final int max = Math.max(50, Math.round(parseFloat) * 2);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_iv);
        final EditText editText = (EditText) findViewById(R.id.cal_iv);
        editText.setText(String.valueOf(parseFloat));
        seekBar.setMax(max);
        seekBar.setProgress(Math.round(parseFloat));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    EditText editText1 = editText;
                    editText1.setText(String.valueOf(i) + ".00");
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        editText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                editText.setText("");
                return false;
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0 || i != 66) {
                    return false;
                }
                try {
                    seekBar.setProgress(Math.min(max, Math.round(Float.parseFloat(editText.getText().toString()))));
                    NewOptionsCalculator.this.hideKeyBoard();
                } catch (Exception e) {
                    EditText editText1 = editText;
                    editText1.setText(String.valueOf(seekBar.getProgress()) + ".00");
                }
                return true;
            }
        });
    }

    /* access modifiers changed from: private */
    public void initTMSeekBar() {
        final int parseInt = Integer.parseInt(this.data_result.getTm());
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_tm);
        final EditText editText = (EditText) findViewById(R.id.cal_tm);
        editText.setText(String.valueOf(parseInt));
        seekBar.setMax(parseInt);
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    editText.setText(String.valueOf(parseInt - i));
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        editText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                editText.setText("");
                return false;
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0 || i != 66) {
                    return false;
                }
                try {
                    seekBar.setProgress(Math.max(0, parseInt - Integer.parseInt(editText.getText().toString())));
                    NewOptionsCalculator.this.hideKeyBoard();
                } catch (Exception e) {
                    editText.setText(String.valueOf(parseInt - seekBar.getProgress()));
                }
                return true;
            }
        });
    }

    /* access modifiers changed from: private */
    public void initUnderlyingSeekBar() {
        final float parseFloat = Float.parseFloat(this.data_result.getUlast());
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_ulast);
        final EditText editText = (EditText) findViewById(R.id.cal_ulast);
        editText.setText(String.valueOf(parseFloat));
        seekBar.setMax(100);
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    editText.setText(Commons.roundSpread((((float) (i + 50)) * parseFloat) / 100.0f));
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        editText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                editText.setText("");
                return false;
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0 || i != 66) {
                    return false;
                }
                try {
                    seekBar.setProgress(((int) Math.max(Math.min((float) Math.round(((Float.parseFloat(editText.getText().toString()) - parseFloat) / parseFloat) * 100.0f), 50.0f), -50.0f)) + 50);
                    NewOptionsCalculator.this.hideKeyBoard();
                } catch (Exception e) {
                    editText.setText(Commons.roundSpread((((float) (seekBar.getProgress() + 50)) * parseFloat) / 100.0f));
                }
                return true;
            }
        });
    }

    /* access modifiers changed from: private */
    public void resetField() {
        if (this.optiontype.equals("stock")) {
            this.originalrate = this.data_result.getRate();
            this.originaldate1 = this.data_result.getDivDate1();
            this.originaldate2 = this.data_result.getDivDate2();
            this.amount1 = this.data_result.getDivAmount1();
            this.amount2 = this.data_result.getDivAmount2();
            ((TextView) findViewById(R.id.calculatedresult)).setText(this.initCalculatedValue);
            initTMSeekBar();
            initIVSeekBar();
            initUnderlyingSeekBar();
            return;
        }
        this.originalrate = this.data_result.getRate();
        this.originaldate1 = "";
        this.originaldate2 = "";
        this.amount1 = "";
        this.amount2 = "";
        this.yield = this.data_result.getYield();
        ((TextView) findViewById(R.id.calculatedresult)).setText(this.initCalculatedValue);
        initTMSeekBar();
        initIVSeekBar();
        initUnderlyingSeekBar();
    }

    public void dataResult(Calculator_Result calculator_Result, final String str) {
        if (str.equals("getPrice")) {
            this.data_result2 = calculator_Result;
        } else {
            this.data_result = calculator_Result;
        }
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (str.equals("getPrice")) {
                        if (!NewOptionsCalculator.this.data_result2.getError().equals("")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NewOptionsCalculator.this);
                            builder.setCancelable(false);
                            builder.setMessage(NewOptionsCalculator.this.data_result2.getError());
                            builder.setNegativeButton(NewOptionsCalculator.this.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.show();
                        }
                        ((TextView) NewOptionsCalculator.this.findViewById(R.id.calculatedresult)).setText(NewOptionsCalculator.this.data_result2.getPrice());
                        String unused = NewOptionsCalculator.this.initCalculatedValue = NewOptionsCalculator.this.initCalculatedValue.equals("") ? NewOptionsCalculator.this.data_result2.getPrice() : NewOptionsCalculator.this.initCalculatedValue;
                        NewOptionsCalculator.this.dataLoaded();
                    } else if (NewOptionsCalculator.this.data_result.getContingency().equals("0")) {
                        NewOptionsCalculator.this.dataLoaded();
                        NewOptionsCalculator.this.ContingencyMessageBox(Commons.language.equals("en_US") ? NewOptionsCalculator.this.data_result.getEngmsg() : NewOptionsCalculator.this.data_result.getChimsg(), true);
                    } else {
                        NewOptionsCalculator.this.initTMSeekBar();
                        NewOptionsCalculator.this.initIVSeekBar();
                        NewOptionsCalculator.this.initUnderlyingSeekBar();
                        TextView textView = (TextView) NewOptionsCalculator.this.findViewById(R.id.expiry);
                        ((TextView) NewOptionsCalculator.this.findViewById(R.id.ucode)).setText(NewOptionsCalculator.this.ucode);
                        ((TextView) NewOptionsCalculator.this.findViewById(R.id.strike)).setText(NewOptionsCalculator.this.strike);
                        if (NewOptionsCalculator.this.tab_type.equals("search")) {
                            textView.setText(NewOptionsCalculator.this.data_result.getExpiry());
                        } else {
                            textView.setText(StringFormatter.formatExpiry(NewOptionsCalculator.this.mdate));
                        }
                        String unused2 = NewOptionsCalculator.this.originalrate = NewOptionsCalculator.this.data_result.getRate();
                        if (NewOptionsCalculator.this.optiontype.equals("stock")) {
                            String unused3 = NewOptionsCalculator.this.originaldate1 = NewOptionsCalculator.this.data_result.getDivDate1();
                            String unused4 = NewOptionsCalculator.this.originaldate2 = NewOptionsCalculator.this.data_result.getDivDate2();
                            String unused5 = NewOptionsCalculator.this.amount1 = NewOptionsCalculator.this.data_result.getDivAmount1();
                            String unused6 = NewOptionsCalculator.this.amount2 = NewOptionsCalculator.this.data_result.getDivAmount2();
                        } else {
                            String unused7 = NewOptionsCalculator.this.originaldate1 = "";
                            String unused8 = NewOptionsCalculator.this.originaldate2 = "";
                            String unused9 = NewOptionsCalculator.this.amount1 = "";
                            String unused10 = NewOptionsCalculator.this.amount2 = "";
                            String unused11 = NewOptionsCalculator.this.yield = NewOptionsCalculator.this.data_result.getYield();
                        }
                        String format = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                        if (NewOptionsCalculator.this.originaldate1.equals("")) {
                            String unused12 = NewOptionsCalculator.this.originaldate1 = format;
                        }
                        if (NewOptionsCalculator.this.originaldate2.equals("")) {
                            String unused13 = NewOptionsCalculator.this.originaldate2 = format;
                        }
                        String[] split = NewOptionsCalculator.this.originaldate1.split("/");
                        String[] split2 = NewOptionsCalculator.this.originaldate2.split("/");
                        NewOptionsCalculator newOptionsCalculator = NewOptionsCalculator.this;
                        String unused14 = newOptionsCalculator.date1 = split[2] + "-" + split[1] + "-" + split[0];
                        NewOptionsCalculator newOptionsCalculator2 = NewOptionsCalculator.this;
                        String unused15 = newOptionsCalculator2.date2 = split2[2] + "-" + split2[1] + "-" + split2[0];
                        double d = 0.0d;
                        try {
                            d = Double.parseDouble(NewOptionsCalculator.this.originalrate) / 100.0d;
                        } catch (Exception e) {
                        }
                        String unused16 = NewOptionsCalculator.this.rate = Double.toString(d);
                        NewOptionsCalculator.this.updateFooterStime(NewOptionsCalculator.this.data_result.getStime());
                        NewOptionsCalculator.this.calculateResultRequest();
                    }
                } catch (Exception e2) {
                    Commons.LogDebug(toString(), e2.getMessage());
                }
            }
        });
    }

    public void hideKeyBoard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
    }

    public void initUI() {
        initFooter();
        ImageButton imageButton = (ImageButton) findViewById(R.id.btnRight);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.btnLeft);
        if (this.tab_type.equals("search")) {
            ((TextView) findViewById(R.id.strike_str)).setText(R.string.strike4cal);
            ((TextView) findViewById(R.id.expiry_str)).setText(R.string.expiry4cal);
            ((TextView) findViewById(R.id.ulast)).setText(R.string.ulast4cal);
            ((TextView) findViewById(R.id.calculatedhkd)).setVisibility(View.GONE);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NewOptionsCalculator.this.optiontype.equals("stock")) {
                    Intent intent = new Intent().setClass(NewOptionsCalculator.this, CalculatorAssumption.class);
                    intent.putExtra("rate", NewOptionsCalculator.this.originalrate);
                    intent.putExtra("divDate1", NewOptionsCalculator.this.originaldate1);
                    intent.putExtra("divDate2", NewOptionsCalculator.this.originaldate2);
                    intent.putExtra("divAmount1", NewOptionsCalculator.this.amount1);
                    intent.putExtra("divAmount2", NewOptionsCalculator.this.amount2);
                    NewOptionsCalculator.this.startActivityForResult(intent, 0);
                    return;
                }
                Intent intent2 = new Intent().setClass(NewOptionsCalculator.this, CalculatorIndexAssumption.class);
                intent2.putExtra("rate", NewOptionsCalculator.this.originalrate);
                intent2.putExtra("yield", NewOptionsCalculator.this.yield);
                NewOptionsCalculator.this.startActivityForResult(intent2, 0);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewOptionsCalculator.this.finish();
            }
        });
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.btnReset);
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.btnCalculate);
        if (Commons.language.equals("en_US")) {
            imageButton3.setImageResource(R.drawable.btn_reset_e);
            imageButton4.setImageResource(R.drawable.btn_calculate_e);
        } else if (Commons.language.equals("zh_CN")) {
            imageButton3.setImageResource(R.drawable.btn_reset_gb);
            imageButton4.setImageResource(R.drawable.btn_calculate_gb);
        } else {
            imageButton3.setImageResource(R.drawable.btn_reset_c);
            imageButton4.setImageResource(R.drawable.btn_calculate_c);
        }
        imageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewOptionsCalculator.this.resetField();
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewOptionsCalculator.this.calculateResultRequest();
            }
        });
        if (!this.optiontype.equals("stock")) {
            ((TextView) findViewById(R.id.calculatedhkd)).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.strike_str)).setText(R.string.strike4cal);
            ((TextView) findViewById(R.id.ucode_str)).setText(R.string.index_underlying_code);
            ((TextView) findViewById(R.id.ulast)).setText(R.string.index_underlying_price);
        }
    }

    public void loadJSON(final String str) {
        GenericJSONParser genericJSONParser = new GenericJSONParser(Calculator_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<Calculator_Result>() {
            public void OnJSONCompleted(Calculator_Result calculator_Result) {
                Commons.LogDebug("", calculator_Result.toString());
                String unused = NewOptionsCalculator.this.ucode = calculator_Result.getUcode();
                NewOptionsCalculator.this.dataResult(calculator_Result, str);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                NewOptionsCalculator.this.ShowConnectionErrorDialog(runnable);
            }
        });
        Commons.LogDebug("OptionCalculator", "type=" + str + "&ulast=" + this.ulast + "&strike=" + this.strike + "&mdate=" + this.tm + "&rate=" + this.rate + "&iv=" + this.iv + "&wtype=" + this.wtype + "&divDate1=" + this.date1 + "&divDate2=" + this.date2 + "&divAmount1=" + this.amount1 + "&divAmount2=" + this.amount2);
        String string = this.optiontype.equals("stock") ? getString(R.string.url_calculator) : getString(R.string.url_calculator_index);
        if (str.equals("getPrice")) {
            String str2 = Commons.language.equals("en_US") ? "en" : "ch";
            if (this.optiontype.equals("stock")) {
                genericJSONParser.loadXML(string + "?type=" + str + "&lang=" + str2 + "&ulast=" + this.ulast + "&strike=" + this.strike + "&mdate=" + this.tm + "&rate=" + this.rate + "&iv=" + this.iv + "&wtype=" + this.wtype + "&divDate1=" + this.date1 + "&divDate2=" + this.date2 + "&divAmount1=" + this.amount1 + "&divAmount2=" + this.amount2);
            } else {
                double d = 0.0d;
                try {
                    d = Double.parseDouble(this.yield) / 100.0d;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                genericJSONParser.loadXML(string + "?type=" + str + "&lang=" + str2 + "&ulast=" + this.ulast + "&strike=" + this.strike + "&mdate=" + this.tm + "&rate=" + this.rate + "&iv=" + this.iv + "&wtype=" + this.wtype + "&dividendYield=" + Double.toString(d));
            }
        } else if (this.tab_type.equals("search")) {
            genericJSONParser.loadXML(string + "?type=" + this.tab_type + "&underlying=" + this.hcode + "&iv=" + this.iv + "&expiry=" + this.mdate + "&strike=" + this.strike);
        } else {
            genericJSONParser.loadXML(string + "?type=" + this.tab_type + "&underlying=" + this.hcode + "&expiry=" + this.mdate + "&strike=" + this.strike);
        }
        dataLoading();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && intent != null) {
            Bundle extras = intent.getExtras();
            if (extras.containsKey("yield")) {
                this.originalrate = extras.getString("rate");
                this.yield = extras.getString("yield");
            } else {
                this.originalrate = extras.getString("rate");
                this.originaldate1 = extras.getString("divDate1");
                this.originaldate2 = extras.getString("divDate2");
                this.amount1 = extras.getString("divAmount1");
                this.amount2 = extras.getString("divAmount2");
                String[] split = this.originaldate1.split("/");
                String[] split2 = this.originaldate2.split("/");
                this.date1 = split[2] + "-" + split[1] + "-" + split[0];
                this.date2 = split2[2] + "-" + split2[1] + "-" + split2[0];
            }
            double d = 0.0d;
            try {
                d = Double.parseDouble(this.originalrate) / 100.0d;
            } catch (Exception e) {
            }
            this.rate = Double.toString(d);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.optioncalculator);
        this.tab_type = getIntent().getStringExtra("tab_type");
        this.ucode = getIntent().getStringExtra("ucode");
        this.hcode = getIntent().getStringExtra("hcode");
        this.mdate = getIntent().getStringExtra("mdate");
        this.wtype = getIntent().getStringExtra("wtype");
        this.strike = getIntent().getStringExtra("strike");
        this.iv = getIntent().getStringExtra("iv");
        this.optiontype = getIntent().getStringExtra("optiontype");
        initUI();
        loadJSON("search");
        if (Commons.tutor_cal == 1) {
            TutorialDialog tutorialDialog = new TutorialDialog(this);
            if (Commons.language == "en_US") {
                tutorialDialog.setTutorialResID(new int[]{R.drawable.tut_calculator1_e, R.drawable.tut_calculator2_e, R.drawable.tut_calculator3_e});
            } else if (Commons.language == "zh_CN") {
                tutorialDialog.setTutorialResID(new int[]{R.drawable.tut_calculator1_gb, R.drawable.tut_calculator2_gb, R.drawable.tut_calculator3_gb});
            } else {
                tutorialDialog.setTutorialResID(new int[]{R.drawable.tut_calculator1_c, R.drawable.tut_calculator2_c, R.drawable.tut_calculator3_c});
            }
            tutorialDialog.show();
            Commons.tutor_cal = 0;
            SharedPrefsUtil.putValue((Context) this, "tutor_cal", Commons.tutor_cal + "");
        }
    }
}
