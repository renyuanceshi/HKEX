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
import com.hkex.soma.JSONParser.CalculatorJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.basic.MasterActivity;
import com.hkex.soma.dataModel.Calculator_Result;
import com.hkex.soma.element.TutorialDialog;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.SharedPrefsUtil;
import com.hkex.soma.utils.StringFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OptionCalculator extends MasterActivity {
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
    private String originaldate1;
    private String originaldate2;
    private String originalrate;
    private String rate;
    private String strike;
    private String tm;
    private String ucode;
    private String ulast;
    private String wtype;

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

    private void initIVSeekBar() {
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
//                    EditText editText = editText;
                    editText.setText(String.valueOf(i) + ".00");
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
                    OptionCalculator.this.hideKeyBoard();
                } catch (Exception e) {
//                    EditText editText = editText;
                    editText.setText(String.valueOf(seekBar.getProgress()) + ".00");
                }
                return true;
            }
        });
    }

    private void initTMSeekBar() {
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
                    OptionCalculator.this.hideKeyBoard();
                } catch (Exception e) {
                    editText.setText(String.valueOf(parseInt - seekBar.getProgress()));
                }
                return true;
            }
        });
    }

    private void initUnderlyingSeekBar() {
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
                    OptionCalculator.this.hideKeyBoard();
                } catch (Exception e) {
                    editText.setText(Commons.roundSpread((((float) (seekBar.getProgress() + 50)) * parseFloat) / 100.0f));
                }
                return true;
            }
        });
    }

    private void resetField() {
        this.originalrate = this.data_result.getRate();
        this.originaldate1 = this.data_result.getDivDate1();
        this.originaldate2 = this.data_result.getDivDate2();
        this.amount1 = this.data_result.getDivAmount1();
        this.amount2 = this.data_result.getDivAmount2();
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
                        if (!OptionCalculator.this.data_result2.getError().equals("")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(OptionCalculator.this);
                            builder.setCancelable(false);
                            builder.setMessage(OptionCalculator.this.data_result2.getError());
                            builder.setNegativeButton(OptionCalculator.this.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.show();
                        }
                        ((TextView) OptionCalculator.this.findViewById(R.id.calculatedresult)).setText(OptionCalculator.this.data_result2.getPrice());
                        String unused = OptionCalculator.this.initCalculatedValue = OptionCalculator.this.initCalculatedValue.equals("") ? OptionCalculator.this.data_result2.getPrice() : OptionCalculator.this.initCalculatedValue;
                        OptionCalculator.this.dataLoaded();
                    } else if (OptionCalculator.this.data_result.getContingency().equals("0")) {
                        OptionCalculator.this.dataLoaded();
                        OptionCalculator.this.ContingencyMessageBox(Commons.language.equals("en_US") ? OptionCalculator.this.data_result.getEngmsg() : OptionCalculator.this.data_result.getChimsg(), true);
                    } else {
                        OptionCalculator.this.initTMSeekBar();
                        OptionCalculator.this.initIVSeekBar();
                        OptionCalculator.this.initUnderlyingSeekBar();
                        ((TextView) OptionCalculator.this.findViewById(R.id.ucode)).setText(OptionCalculator.this.ucode);
                        ((TextView) OptionCalculator.this.findViewById(R.id.strike)).setText(OptionCalculator.this.strike);
                        ((TextView) OptionCalculator.this.findViewById(R.id.expiry)).setText(StringFormatter.formatExpiry(OptionCalculator.this.mdate));
                        String unused2 = OptionCalculator.this.originalrate = OptionCalculator.this.data_result.getRate();
                        String unused3 = OptionCalculator.this.originaldate1 = OptionCalculator.this.data_result.getDivDate1();
                        String unused4 = OptionCalculator.this.originaldate2 = OptionCalculator.this.data_result.getDivDate2();
                        String unused5 = OptionCalculator.this.amount1 = OptionCalculator.this.data_result.getDivAmount1();
                        String unused6 = OptionCalculator.this.amount2 = OptionCalculator.this.data_result.getDivAmount2();
                        String format = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                        if (OptionCalculator.this.originaldate1.equals("")) {
                            String unused7 = OptionCalculator.this.originaldate1 = format;
                        }
                        if (OptionCalculator.this.originaldate2.equals("")) {
                            String unused8 = OptionCalculator.this.originaldate2 = format;
                        }
                        String[] split = OptionCalculator.this.originaldate1.split("/");
                        String[] split2 = OptionCalculator.this.originaldate2.split("/");
                        OptionCalculator optionCalculator = OptionCalculator.this;
                        String unused9 = optionCalculator.date1 = split[2] + "-" + split[1] + "-" + split[0];
                        OptionCalculator optionCalculator2 = OptionCalculator.this;
                        String unused10 = optionCalculator2.date2 = split2[2] + "-" + split2[1] + "-" + split2[0];
                        double d = 0.0d;
                        try {
                            d = Double.parseDouble(OptionCalculator.this.originalrate) / 100.0d;
                        } catch (Exception e) {
                        }
                        String unused11 = OptionCalculator.this.rate = Double.toString(d);
                        OptionCalculator.this.updateFooterStime(OptionCalculator.this.data_result.getStime());
                        OptionCalculator.this.calculateResultRequest();
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
        ((ImageButton) findViewById(R.id.btnRight)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent().setClass(OptionCalculator.this, CalculatorAssumption.class);
                intent.putExtra("rate", OptionCalculator.this.originalrate);
                intent.putExtra("divDate1", OptionCalculator.this.originaldate1);
                intent.putExtra("divDate2", OptionCalculator.this.originaldate2);
                intent.putExtra("divAmount1", OptionCalculator.this.amount1);
                intent.putExtra("divAmount2", OptionCalculator.this.amount2);
                OptionCalculator.this.startActivityForResult(intent, 0);
            }
        });
        ((ImageButton) findViewById(R.id.btnLeft)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OptionCalculator.this.finish();
            }
        });
        ImageButton imageButton = (ImageButton) findViewById(R.id.btnReset);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.btnCalculate);
        if (Commons.language.equals("en_US")) {
            imageButton.setImageResource(R.drawable.btn_reset_e);
            imageButton2.setImageResource(R.drawable.btn_calculate_e);
        } else if (Commons.language.equals("zh_CN")) {
            imageButton.setImageResource(R.drawable.btn_reset_gb);
            imageButton2.setImageResource(R.drawable.btn_calculate_gb);
        } else {
            imageButton.setImageResource(R.drawable.btn_reset_c);
            imageButton2.setImageResource(R.drawable.btn_calculate_c);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OptionCalculator.this.resetField();
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OptionCalculator.this.calculateResultRequest();
            }
        });
    }

    public void loadJSON(final String str) {
        CalculatorJSONParser calculatorJSONParser = new CalculatorJSONParser();
        calculatorJSONParser.setOnJSONCompletedListener(new CalculatorJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(Calculator_Result calculator_Result) {
                Commons.LogDebug("", calculator_Result.toString());
                OptionCalculator.this.dataResult(calculator_Result, str);
            }
        });
        calculatorJSONParser.setOnJSONFailedListener(new CalculatorJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                OptionCalculator.this.ShowConnectionErrorDialog(runnable);
            }
        });
        Commons.LogDebug("OptionCalculator", "type=" + str + "&ulast=" + this.ulast + "&strike=" + this.strike + "&mdate=" + this.tm + "&rate=" + this.rate + "&iv=" + this.iv + "&wtype=" + this.wtype + "&divDate1=" + this.date1 + "&divDate2=" + this.date2 + "&divAmount1=" + this.amount1 + "&divAmount2=" + this.amount2);
        if (str.equals("getPrice")) {
            String str2 = Commons.language.equals("en_US") ? "en" : "ch";
            calculatorJSONParser.loadXML(getString(R.string.url_calculator) + "?type=" + str + "&lang=" + str2 + "&ulast=" + this.ulast + "&strike=" + this.strike + "&mdate=" + this.tm + "&rate=" + this.rate + "&iv=" + this.iv + "&wtype=" + this.wtype + "&divDate1=" + this.date1 + "&divDate2=" + this.date2 + "&divAmount1=" + this.amount1 + "&divAmount2=" + this.amount2);
        } else {
            calculatorJSONParser.loadXML(getString(R.string.url_calculator) + "?type=" + str + "&underlying=" + this.hcode + "&expiry=" + this.mdate + "&strike=" + this.strike);
        }
        dataLoading();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && intent != null) {
            Bundle extras = intent.getExtras();
            this.originalrate = extras.getString("rate");
            this.originaldate1 = extras.getString("divDate1");
            this.originaldate2 = extras.getString("divDate2");
            this.amount1 = extras.getString("divAmount1");
            this.amount2 = extras.getString("divAmount2");
            String[] split = this.originaldate1.split("/");
            String[] split2 = this.originaldate2.split("/");
            this.date1 = split[2] + "-" + split[1] + "-" + split[0];
            this.date2 = split2[2] + "-" + split2[1] + "-" + split2[0];
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
        this.hcode = getIntent().getStringExtra("hcode");
        this.ucode = getIntent().getStringExtra("ucode");
        this.mdate = getIntent().getStringExtra("mdate");
        this.wtype = getIntent().getStringExtra("wtype");
        this.strike = getIntent().getStringExtra("strike");
        initUI();
        loadJSON("update");
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
