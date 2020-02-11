package com.hkex.soma.element;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.OptionDetail;
import com.hkex.soma.dataModel.OptionDetail_Result;
import com.hkex.soma.dataModel.Real_Timestamp;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.EncryptUtils;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

public class RealTimeQuoteDialog extends Dialog implements DialogInterface.OnShowListener {
    public static final String TAG = "RealTimeQuoteDialog";
    private GenericJSONParser<OptionDetail_Result> JSONParser_data;
    private GenericJSONParser<Real_Timestamp> JSONParser_timestamp;
    private TextView bid_ask;
    private Context context;
    private final String encode_key = "A01dbpoweripad3rtery";
    private Handler handler = new Handler();
    private TextView high;
    private TextView last1;
    private TextView last2;
    public TextView low;
    private FrameLayout mFrameLayout;
    private String oid = "";
    private TextView update_time;
    private TextView vol;
    private String wtype = "";

    public RealTimeQuoteDialog(Context context2, String str, String str2) {
        super(context2);
        this.context = context2;
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        this.oid = str;
        this.wtype = str2;
        init();
        setOnShowListener(this);
    }

    private void dataResult(final OptionDetail_Result optionDetail_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                double d;
                try {
                    d = (double) Float.parseFloat(optionDetail_Result.getChng());
                    try {
                        if (RealTimeQuoteDialog.this.wtype.equals("stock")) {
                            RealTimeQuoteDialog.this.last1.setText("HKD " + optionDetail_Result.getLast());
                        } else {
                            RealTimeQuoteDialog.this.last1.setText("" + optionDetail_Result.getLast());
                        }
                        int i = (d > 0.0d ? 1 : (d == 0.0d ? 0 : -1));
                        if (i == 0) {
                            RealTimeQuoteDialog.this.last2.setTextColor(RealTimeQuoteDialog.this.context.getResources().getColor(R.color.black));
                            RealTimeQuoteDialog.this.last2.setText(" -- (--)");
                        } else if (i > 0) {
                            RealTimeQuoteDialog.this.last2.setTextColor(RealTimeQuoteDialog.this.context.getResources().getColor(R.color.change_green));
                            RealTimeQuoteDialog.this.last2.setText("+" + optionDetail_Result.getChng() + " (+" + optionDetail_Result.getPchng() + "%)");
                        } else {
                            RealTimeQuoteDialog.this.last2.setTextColor(RealTimeQuoteDialog.this.context.getResources().getColor(R.color.change_red));
                            RealTimeQuoteDialog.this.last2.setText(optionDetail_Result.getChng() + " (" + optionDetail_Result.getPchng() + "%)");
                        }
                        RealTimeQuoteDialog.this.bid_ask.setText(optionDetail_Result.getBid() + " / " + optionDetail_Result.getAsk());
                        RealTimeQuoteDialog.this.high.setText(optionDetail_Result.getHigh());
                        RealTimeQuoteDialog.this.low.setText(optionDetail_Result.getLow());
                        RealTimeQuoteDialog.this.vol.setText(optionDetail_Result.getVol());
                        RealTimeQuoteDialog.this.update_time.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + optionDetail_Result.getStime());
                        ((OptionDetail) RealTimeQuoteDialog.this.context).dataLoaded();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e2) {
                    d = 0.0d;
                }
            }
        });
    }

    private void load_and_set_data() {
        GenericJSONParser<Real_Timestamp> genericJSONParser = this.JSONParser_timestamp;
        genericJSONParser.loadXML(this.context.getString(R.string.url_quote_optionsdetail) + "?type=gettime");
        ((OptionDetail) this.context).dataLoading();
    }

    @SuppressLint({"NewApi"})
    public void init() {
        this.mFrameLayout = new FrameLayout(this.context);
        setContentView(((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_real_time_quote, this.mFrameLayout, false));
        this.last1 = (TextView) findViewById(R.id.last1);
        this.last2 = (TextView) findViewById(R.id.last2);
        this.bid_ask = (TextView) findViewById(R.id.bid_ask);
        this.high = (TextView) findViewById(R.id.high);
        this.low = (TextView) findViewById(R.id.low);
        this.vol = (TextView) findViewById(R.id.vol);
        this.update_time = (TextView) findViewById(R.id.update_time);
        this.JSONParser_timestamp = new GenericJSONParser<>(Real_Timestamp.class);
        this.JSONParser_timestamp.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<Real_Timestamp>() {
            public void OnJSONCompleted(Real_Timestamp real_Timestamp) {
                String str;
                Commons.LogDebug("GenericJSONParser", "GenericJSONParser" + real_Timestamp.toString());
                Random random = new Random();
                String time = real_Timestamp.getTime();
                int nextInt = random.nextInt(9999) + 1;
                if (nextInt < 10) {
                    str = time + "_000" + nextInt;
                } else if (nextInt < 100) {
                    str = time + "_00" + nextInt;
                } else if (nextInt < 1000) {
                    str = time + "_0" + nextInt;
                } else {
                    str = time + "_" + nextInt;
                }
                try {
                    String encode = URLEncoder.encode(EncryptUtils.base64encode(EncryptUtils.xorMessage(str, "A01dbpoweripad3rtery")), "ISO-8859-1");
                    RealTimeQuoteDialog.this.JSONParser_data.loadXML(RealTimeQuoteDialog.this.context.getString(R.string.url_quote_optionsdetail) + "?type=realtime&oid=" + RealTimeQuoteDialog.this.oid + "&key=" + encode + "&wtype=" + RealTimeQuoteDialog.this.wtype);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        this.JSONParser_timestamp.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                ((OptionDetail) RealTimeQuoteDialog.this.context).dataLoaded();
            }
        });
        this.JSONParser_data = new GenericJSONParser<>(OptionDetail_Result.class);
        this.JSONParser_data.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<OptionDetail_Result>() {
            public void OnJSONCompleted(OptionDetail_Result optionDetail_Result) {
                Commons.LogDebug("GenericJSONParser JSONParser_data", "GenericJSONParser JSONParser_data" + optionDetail_Result.toString());
                RealTimeQuoteDialog.this.dataResult(optionDetail_Result);
            }
        });
        this.JSONParser_data.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener JSONParser_data", exc.getMessage());
                ((OptionDetail) RealTimeQuoteDialog.this.context).dataLoaded();
            }
        });
        ((ImageButton) findViewById(R.id.close_but)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RealTimeQuoteDialog.this.dismiss();
            }
        });
        ((ImageButton) findViewById(R.id.reload_but)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RealTimeQuoteDialog.this.load_and_set_data();
            }
        });
    }

    public void onShow(DialogInterface dialogInterface) {
        load_and_set_data();
    }

    public void set_oid(String str) {
        this.oid = str;
    }

    public void setwtype(String str) {
        this.wtype = str;
    }
}
