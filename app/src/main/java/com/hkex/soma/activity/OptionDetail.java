package com.hkex.soma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hkex.soma.JSONParser.OptionDetailJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.adapter.OptionDetail_ResultAdapter;
import com.hkex.soma.basic.MasterActivity;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.basic.MultiScrollView;
import com.hkex.soma.dataModel.OptionDetail_Result;
import com.hkex.soma.element.RealTimeQuoteDialog;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class OptionDetail extends MasterActivity {
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String KEY_ALGORITHM = "AES";
    private static final String TAG = "RealTimeQuoteDialog";
    private ArrayList<String> codeStack = new ArrayList<>();
    /* access modifiers changed from: private */
    public OptionDetail_Result.mainData[] data;
    private Handler handler = new Handler();
    /* access modifiers changed from: private */
    public String hcode;
    /* access modifiers changed from: private */
    public int headerOffset = 0;
    /* access modifiers changed from: private */
    public boolean infoloaded = false;
    /* access modifiers changed from: private */
    public boolean isShowMore = false;
    /* access modifiers changed from: private */
    public boolean isindex = false;
    /* access modifiers changed from: private */
    public MultiScrollListView listView;
    /* access modifiers changed from: private */
    public String mdate;
    /* access modifiers changed from: private */
    public String oid;
    /* access modifiers changed from: private */
    public ImageButton real_time_but;
    /* access modifiers changed from: private */
    public boolean relatedloaded = false;
    /* access modifiers changed from: private */
    public RealTimeQuoteDialog rt_dialog;
    /* access modifiers changed from: private */
    public MultiScrollView scrollView;
    /* access modifiers changed from: private */
    public String strike;
    /* access modifiers changed from: private */
    public String ucode;
    /* access modifiers changed from: private */
    public String uname = Commons.MapUnderlyingName(this.ucode);
    /* access modifiers changed from: private */
    public String wtype;

    public static byte[] decrypt(byte[] bArr, Key key) throws Exception {
        return decrypt(bArr, key, DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] decrypt(byte[] bArr, Key key, String str) throws Exception {
        Cipher instance = Cipher.getInstance(str);
        instance.init(2, key);
        return instance.doFinal(bArr);
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2) throws Exception {
        return decrypt(bArr, bArr2, DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2, String str) throws Exception {
        return decrypt(bArr, toKey(bArr2), str);
    }

    public static byte[] encrypt(byte[] bArr, Key key) throws Exception {
        return encrypt(bArr, key, DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] encrypt(byte[] bArr, Key key, String str) throws Exception {
        Cipher instance = Cipher.getInstance(str);
        instance.init(1, key);
        return instance.doFinal(bArr);
    }

    public static byte[] encrypt(byte[] bArr, byte[] bArr2) throws Exception {
        return encrypt(bArr, bArr2, DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] encrypt(byte[] bArr, byte[] bArr2, String str) throws Exception {
        return encrypt(bArr, toKey(bArr2), str);
    }

    public static byte[] initSecretKey() {
        try {
            KeyGenerator instance = KeyGenerator.getInstance(KEY_ALGORITHM);
            instance.init(128);
            return instance.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private static String showByteArray(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        for (byte append : bArr) {
            sb.append(append);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }

    private static Key toKey(byte[] bArr) {
        return new SecretKeySpec(bArr, KEY_ALGORITHM);
    }

    public void backtoPreviousOption() {
        if (this.codeStack.size() == 0) {
            finish();
            return;
        }
        String[] split = this.codeStack.get(this.codeStack.size() - 1).split("~~");
        this.oid = split[0];
        this.ucode = split[1];
        this.mdate = split[2];
        this.wtype = split[3];
        this.strike = split[4];
        this.codeStack.remove(this.codeStack.size() - 1);
        this.infoloaded = false;
        this.relatedloaded = false;
        loadJSON("info");
        loadJSON("related");
    }

    public void dataResult(final OptionDetail_Result optionDetail_Result, final String str1) {
        this.handler.post(new Runnable() {
            public void run() {
                String str = str1;
                String str2;
                if (str.equals("related") && optionDetail_Result == null) {
                    OptionDetail.this.initNoDataListView();
                    OptionDetail.this.updateFooterStime(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    boolean unused = OptionDetail.this.relatedloaded = true;
                } else if (str.equals("info") && optionDetail_Result == null) {
                    OptionDetail.this.initNoDataListView();
                    OptionDetail.this.updateFooterStime(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    boolean unused2 = OptionDetail.this.infoloaded = true;
                } else if (str.equals("related")) {
                    OptionDetail_Result.mainData[] unused3 = OptionDetail.this.data = optionDetail_Result.getmainData();
                    boolean unused4 = OptionDetail.this.relatedloaded = true;
                } else if (optionDetail_Result.getContingency().equals("0")) {
                    OptionDetail.this.initNoDataListView();
                    OptionDetail.this.dataLoaded();
                    OptionDetail.this.ContingencyMessageBox(Commons.language.equals("en_US") ? optionDetail_Result.getEngmsg() : optionDetail_Result.getChimsg(), true);
                    boolean unused5 = OptionDetail.this.infoloaded = true;
                } else if (optionDetail_Result.getContingency().equals("-1")) {
                    OptionDetail.this.initNoDataListView("nodatayet");
                    OptionDetail.this.dataLoaded();
                    OptionDetail.this.updateFooterStime("nodatayet");
                    boolean unused6 = OptionDetail.this.infoloaded = true;
                } else {
                    String str3 = optionDetail_Result.getWtype().equals("P") ? "Put" : "Call";
                    String unused7 = OptionDetail.this.hcode = optionDetail_Result.getHcode();
                    Log.v("OptionDetail", "hcode : " + OptionDetail.this.hcode);
                    TextView textView = (TextView) OptionDetail.this.findViewById(R.id.uname);
                    TextView textView2 = (TextView) OptionDetail.this.findViewById(R.id.last);
                    TextView textView3 = (TextView) OptionDetail.this.findViewById(R.id.change);
                    TextView textView4 = (TextView) OptionDetail.this.findViewById(R.id.bid);
                    TextView textView5 = (TextView) OptionDetail.this.findViewById(R.id.ask);
                    TextView textView6 = (TextView) OptionDetail.this.findViewById(R.id.high);
                    TextView textView7 = (TextView) OptionDetail.this.findViewById(R.id.low);
                    TextView textView8 = (TextView) OptionDetail.this.findViewById(R.id.vol);
                    TextView textView9 = (TextView) OptionDetail.this.findViewById(R.id.currency);
                    TextView textView10 = (TextView) OptionDetail.this.findViewById(R.id.ulast);
                    TextView textView11 = (TextView) OptionDetail.this.findViewById(R.id.uchange);
                    TextView textView12 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue1);
                    TextView textView13 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue2);
                    TextView textView14 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue3);
                    TextView textView15 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue4);
                    TextView textView16 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue5);
                    TextView textView17 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue6);
                    TextView textView18 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue7);
                    TextView textView19 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue8);
                    TextView textView20 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue9);
                    TextView textView21 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue10);
                    TextView textView22 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue11);
                    TextView textView23 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue12);
                    TextView textView24 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue13);
                    TextView textView25 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue14);
                    TextView textView26 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue15);
                    TextView textView27 = (TextView) OptionDetail.this.findViewById(R.id.optionvalue16);
                    TextView textView28 = (TextView) OptionDetail.this.findViewById(R.id.optiontitle2);
                    StringBuilder sb = new StringBuilder();
                    sb.append(StringFormatter.formatExpiry(optionDetail_Result.getCexpiry()));
                    sb.append(" - ");
                    sb.append(optionDetail_Result.getStrike());
                    sb.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    sb.append(Commons.callputText(OptionDetail.this, str3));
                    ((TextView) OptionDetail.this.findViewById(R.id.optiontitle)).setText(sb.toString());
                    if (optionDetail_Result.getType().equals("index")) {
                        textView.setText(Commons.MapIndexName(OptionDetail.this.ucode) + " (" + optionDetail_Result.getHcode() + ")");
                        textView28.setText(R.string.odlist2index);
                        ((TextView) OptionDetail.this.findViewById(R.id.currency_str)).setVisibility(View.INVISIBLE);
                        textView9.setVisibility(View.INVISIBLE);
                        String unused8 = OptionDetail.this.uname = Commons.MapIndexName(OptionDetail.this.ucode);
                        ((TextView) OptionDetail.this.findViewById(R.id.optiontitle3)).setText(R.string.strike4cal);
                        ((TextView) OptionDetail.this.findViewById(R.id.head_strike)).setText(R.string.strike4cal);
                        ((TextView) OptionDetail.this.findViewById(R.id.textView9)).setText(R.string.option_detail_ucode);
                        boolean unused9 = OptionDetail.this.isindex = true;
                    } else {
                        textView.setText(Commons.MapUnderlyingName(OptionDetail.this.ucode) + " (" + optionDetail_Result.getHcode() + ")");
                        String unused10 = OptionDetail.this.uname = Commons.MapUnderlyingName(OptionDetail.this.ucode);
                        boolean unused11 = OptionDetail.this.isindex = false;
                    }
                    try {
                        OptionDetail.this.rt_dialog.setwtype(OptionDetail.this.isindex ? "index" : "stock");
                        StringBuilder sb2 = new StringBuilder();
                        if (optionDetail_Result.getCurrency().equals("")) {
                            str = "";
                        } else {
                            str = optionDetail_Result.getCurrency() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                        }
                        sb2.append(str);
                        sb2.append(optionDetail_Result.getLast());
                        textView2.setText(sb2.toString());
                        StringFormatter.formatChange(textView3, optionDetail_Result.getChng(), optionDetail_Result.getPchng(), "--");
                        textView4.setText(optionDetail_Result.getBid());
                        textView5.setText(optionDetail_Result.getAsk());
                        textView6.setText(optionDetail_Result.getHigh());
                        textView7.setText(optionDetail_Result.getLow());
                        textView8.setText(optionDetail_Result.getVol());
                        textView9.setText(optionDetail_Result.getCurrency());
                        StringBuilder sb3 = new StringBuilder();
                        if (optionDetail_Result.getCurrency().equals("")) {
                            str2 = "";
                        } else {
                            str2 = optionDetail_Result.getCurrency() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                        }
                        sb3.append(str2);
                        sb3.append(optionDetail_Result.getUlast());
                        textView10.setText(sb3.toString());
                        StringFormatter.formatChange(textView11, optionDetail_Result.getUchng(), optionDetail_Result.getUpchng(), "--");
                        textView12.setText(optionDetail_Result.getNotional());
                        textView13.setText(optionDetail_Result.getSize());
                        textView14.setText(optionDetail_Result.getStrike());
                        textView15.setText(optionDetail_Result.getExpiry());
                        textView16.setText(optionDetail_Result.getEgear());
                        textView17.setText(optionDetail_Result.getIv());
                        textView18.setText(optionDetail_Result.getPremium());
                        textView19.setText(optionDetail_Result.getNetoi());
                        textView20.setText(optionDetail_Result.getGrossoi());
                        textView21.setText(optionDetail_Result.getBoard());
                        if (Commons.language.equals("en_US")) {
                            textView22.setText(optionDetail_Result.getMoneyness());
                        } else {
                            textView22.setText(optionDetail_Result.getCmoneyness());
                        }
                        textView23.setText(optionDetail_Result.getTm());
                        textView24.setText(optionDetail_Result.getGear());
                        textView25.setText(optionDetail_Result.getDelta());
                        textView26.setText(optionDetail_Result.getTheta());
                        textView27.setText(optionDetail_Result.getVega());
                        OptionDetail.this.updateFooterStime(optionDetail_Result.getStime());
                        boolean unused12 = OptionDetail.this.infoloaded = true;
                    } catch (Exception e) {
                        OptionDetail.this.initNoDataListView();
                        OptionDetail.this.dataLoaded();
                        Commons.LogDebug(toString(), e.getMessage());
                        e.printStackTrace();
                        return;
                    }
                }
                if (OptionDetail.this.relatedloaded && OptionDetail.this.infoloaded) {
                    if (OptionDetail.this.data.length == 0) {
                        OptionDetail.this.initNoDataListView();
                    } else {
                        OptionDetail.this.listView.setAdapter(new OptionDetail_ResultAdapter(OptionDetail.this, R.layout.list_option_detail, OptionDetail.this.data, OptionDetail.this.ucode, -1, OptionDetail.this.isindex));
                    }
                    OptionDetail.this.dataLoaded();
                    OptionDetail.this.listView.setDividerHeight(0);
                    OptionDetail.this.scrollView.initMultiScrollView(OptionDetail.this.listView, 160.0f, (float) OptionDetail.this.headerOffset);
                    OptionDetail.this.rt_dialog.set_oid(OptionDetail.this.oid);
                    OptionDetail.this.real_time_but.setEnabled(true);
                }
            }
        });
    }

    public void gotoNewOption(String str, String str2, String str3, String str4, String str5) {
        ArrayList<String> arrayList = this.codeStack;
        arrayList.add(this.oid + "~~" + this.ucode + "~~" + this.mdate + "~~" + this.wtype + "~~" + this.strike);
        this.oid = str;
        this.ucode = str2;
        this.mdate = str3;
        this.wtype = str4;
        this.strike = str5;
        this.infoloaded = false;
        this.relatedloaded = false;
        loadJSON("info");
        loadJSON("related");
    }

    public void initNoDataListView() {
        initNoDataListView("");
    }

    public void initNoDataListView(String str) {
        this.listView.setAdapter(new ArrayAdapter(this, R.layout.list_nodata, str.equals("nodatayet") ? new String[]{getString(R.string.nodatayet)} : new String[]{getString(R.string.nooption_message)}));
        this.listView.setOnItemClickListener((AdapterView.OnItemClickListener) null);
        this.listView.setDividerHeight(0);
        this.scrollView.initMultiScrollView(this.listView, 190.0f, 177.0f);
    }

    public void initUI() {
        setContentView(R.layout.optiondetail);
        this.mainContainer = findViewById(R.id.mainContainer);
        initFooter();
        final TextView textView = (TextView) findViewById(R.id.moretv);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.moreContainer);
        ((ImageView) findViewById(R.id.chartBut)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(OptionDetail.this, ChartPage.class);
                TextView textView = (TextView) OptionDetail.this.findViewById(R.id.optiontitle);
                TextView textView2 = (TextView) OptionDetail.this.findViewById(R.id.uname);
                intent.putExtra("oid", OptionDetail.this.oid);
                Log.v("RealTimeQuoteDialog", "oid " + OptionDetail.this.oid);
                intent.putExtra("type", OptionDetail.this.isindex ? "index" : "stock");
                intent.putExtra("title", textView2.getText().toString() + "<br/> " + textView.getText().toString());
                OptionDetail.this.startActivity(intent);
            }
        });
        ((ImageButton) findViewById(R.id.btnLeft)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OptionDetail.this.backtoPreviousOption();
            }
        });
        ((ImageButton) findViewById(R.id.btnRight)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                if (OptionDetail.this.isindex) {
                    intent = new Intent(OptionDetail.this, NewOptionsCalculator.class);
                    intent.putExtra("tab_type", "update");
                    intent.putExtra("hcode", OptionDetail.this.hcode);
                    intent.putExtra("ucode", OptionDetail.this.ucode);
                    intent.putExtra("mdate", OptionDetail.this.mdate);
                    intent.putExtra("wtype", OptionDetail.this.wtype);
                    intent.putExtra("strike", OptionDetail.this.strike);
                    intent.putExtra("iv", "");
                    intent.putExtra("optiontype", "index");
                    intent.putExtra("iv", "");
                } else {
                    intent = new Intent(OptionDetail.this, OptionCalculator.class);
                    intent.putExtra("hcode", OptionDetail.this.hcode);
                    intent.putExtra("ucode", OptionDetail.this.ucode);
                    intent.putExtra("mdate", OptionDetail.this.mdate);
                    intent.putExtra("wtype", OptionDetail.this.wtype);
                    intent.putExtra("strike", OptionDetail.this.strike);
                }
                OptionDetail.this.startActivity(intent);
            }
        });
        ((RelativeLayout) findViewById(R.id.morelayout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean unused = OptionDetail.this.isShowMore = !OptionDetail.this.isShowMore;
                if (OptionDetail.this.isShowMore) {
                    textView.setText(R.string.less);
                    linearLayout.setVisibility(View.VISIBLE);
                    OptionDetail.this.scrollView.initMultiScrollView(OptionDetail.this.listView, 160.0f, (float) OptionDetail.this.headerOffset);
                    return;
                }
                textView.setText(R.string.more);
                linearLayout.setVisibility(View.GONE);
                OptionDetail.this.scrollView.initMultiScrollView(OptionDetail.this.listView, 160.0f, (float) OptionDetail.this.headerOffset);
            }
        });
        this.listView = (MultiScrollListView) findViewById(R.id.listView);
        this.scrollView = (MultiScrollView) findViewById(2131165570);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                OptionDetail.this.gotoNewOption(OptionDetail.this.data[i].getOid(), OptionDetail.this.ucode, OptionDetail.this.mdate, OptionDetail.this.wtype, OptionDetail.this.data[i].getStrike());
            }
        });
        ((ImageView) findViewById(R.id.portfolioicon)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OptionDetail optionDetail;
                String str;
                if (OptionDetail.this.wtype.contains("C")) {
                    optionDetail = OptionDetail.this;
                    str = "Call";
                } else {
                    optionDetail = OptionDetail.this;
                    str = "Put";
                }
                String callputText = Commons.callputText(optionDetail, str);
                String str2 = OptionDetail.this.uname + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + OptionDetail.this.strike + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + callputText;
                if (Portfolio.AddOptionToPortfolio(OptionDetail.this, OptionDetail.this.ucode, Commons.MapUnderlyingName(OptionDetail.this.ucode, false), Commons.MapUnderlyingName(OptionDetail.this.ucode, true), callputText, OptionDetail.this.strike, OptionDetail.this.mdate, "0", "0", "0")) {
                    Toast.makeText(OptionDetail.this, String.format(OptionDetail.this.getString(R.string.portfolio_msg_option), new Object[]{str2}), 1).show();
                    return;
                }
                Toast.makeText(OptionDetail.this, String.format(OptionDetail.this.getString(R.string.portfolio_msg_error), new Object[]{20}), 1).show();
            }
        });
        this.rt_dialog = new RealTimeQuoteDialog(this, this.oid, this.isindex ? "index" : "stock");
        this.rt_dialog.setCanceledOnTouchOutside(false);
        this.real_time_but = (ImageButton) findViewById(R.id.real_time_but);
        this.real_time_but.setEnabled(false);
        this.real_time_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OptionDetail.this.rt_dialog.show();
            }
        });
    }

    public void loadJSON(final String str) {
        OptionDetailJSONParser optionDetailJSONParser = new OptionDetailJSONParser();
        optionDetailJSONParser.setOnJSONCompletedListener(new OptionDetailJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(OptionDetail_Result optionDetail_Result) {
                Commons.LogDebug("", optionDetail_Result.toString());
                OptionDetail.this.dataResult(optionDetail_Result, str);
            }
        });
        optionDetailJSONParser.setOnJSONFailedListener(new OptionDetailJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                OptionDetail.this.ShowConnectionErrorDialog(runnable);
                OptionDetail.this.dataResult((OptionDetail_Result) null, str);
            }
        });
        if (str.equals("related")) {
            optionDetailJSONParser.loadXML(getString(R.string.url_quote_optionsdetail) + "?type=related&ucode=" + this.ucode + "&wtype=" + this.wtype + "&mdate=" + this.mdate + "&strike=" + this.strike);
        } else {
            optionDetailJSONParser.loadXML(getString(R.string.url_quote_optionsdetail) + "?type=Info&oid=" + this.oid);
        }
        dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.oid = getIntent().getStringExtra("oid");
        this.ucode = getIntent().getStringExtra("ucode");
        this.mdate = getIntent().getStringExtra("mdate");
        this.wtype = getIntent().getStringExtra("wtype");
        this.strike = getIntent().getStringExtra("strike");
        if (this.oid == null) {
            this.oid = "";
        }
        if (this.ucode == null) {
            this.ucode = "";
        }
        if (this.mdate == null) {
            this.mdate = "";
        }
        if (this.wtype == null) {
            this.wtype = "";
        }
        if (this.strike == null) {
            this.strike = "";
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (!Commons.noResumeAction) {
            initUI();
            this.infoloaded = false;
            this.relatedloaded = false;
            loadJSON("info");
            loadJSON("related");
            return;
        }
        Commons.noResumeAction = false;
    }
}
