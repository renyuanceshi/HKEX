package com.hkex.soma.activity.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dipen.pricer.Calculations.American;
import com.dipen.pricer.Calculations.ImpliedVol;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.OptionDetail;
import com.hkex.soma.activity.Search;
import com.hkex.soma.adapter.SO_ResultAdapter;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.basic.MultiScrollView;
import com.hkex.soma.dataModel.SO_Result;
import com.hkex.soma.element.CallPutButton;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.CommonsIndex;
import com.hkex.soma.utils.StringFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class Search_Index extends MasterFragment {
    public static final String TAG = "Search_Index";
    private CallPutButton callputbutton;
    private SO_Result.mainData2[] data2;
    private String expiry;
    private View fragmentView;
    private Handler handler = new Handler();
    private String[] index_items;
    private MultiScrollListView listView;
    private String[] mdate_array;
    private String[] num_array = {"0", "9", "11", "13"};
    private MultiScrollView scrollView;
    private Search searchFragmentActivity;
    private SelectionList selectionListCode;
    private SelectionList selectionListExpiry;
    private SelectionList selectionListStrike;
    private AdapterView.OnItemClickListener setOnItemClickListener;
    private String strikenum = "11";
    private String ucode = "";
    private String uname;
    private float underlyingLast = 0.0f;
    private TextView underlying_name;
    private String wtype = "C";

    private SO_Result.indexInfo[] indexInfo = null;
    private SO_Result.OptionsInfo[] optionsInfos = null;
    private SO_ResultAdapter resultAdapter = null;

    private void runUpdate(SO_Result sO_Result) {
        indexInfo = sO_Result.getmainData()[0].getIndexInfo();
        final TextView target_date = (TextView) this.fragmentView.findViewById(R.id.target_date);
        final TextView target_price = (TextView) this.fragmentView.findViewById(R.id.target_price);
        target_price.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                target_price.setText("");
                return false;
            }
        });
        target_price.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
//                Toast.makeText(getContext(), "KeyCode"+keyCode, Toast.LENGTH_SHORT).show();
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 2);
                    updateOptionsInfos(target_price.getText().toString(), target_date.getText().toString());
                    resultAdapter.setOptionsInfo(optionsInfos);
                    return true;
                }
                return false;
            }
        });

        TextView textView = (TextView) this.fragmentView.findViewById(R.id.textView2);
        TextView textView2 = (TextView) this.fragmentView.findViewById(R.id.textView3);
        ImageView imageView = (ImageView) this.fragmentView.findViewById(R.id.updown);
        if (indexInfo.length > 0) {
            textView.setText(indexInfo[0].getUlast());
            this.underlyingLast = Float.parseFloat(indexInfo[0].getUlast().replace(",", ""));
            textView2.setText(indexInfo[0].getUchng() + "(" + indexInfo[0].getUpchng() + "%)");
            StringFormatter.formatChng(imageView, indexInfo[0].getUchng());
            target_price.setText(indexInfo[0].getUlast());
            target_date.setText("-");
        } else {
            textView.setText("-");
            textView2.setText("-");
            StringFormatter.formatChng(imageView, "-");
            target_price.setText("-");
            target_date.setText("-");
        }
        this.data2 = sO_Result.getmainData2();
        if (this.data2.length == 0) {
            initNoDataListView();
        } else {
            int i = -1;
            float f = Float.MAX_VALUE;
            this.optionsInfos = new SO_Result.OptionsInfo[data2.length];
            updateOptionsInfos(indexInfo[0].getUlast(), null);
            for (int i2 = 0; i2 < this.data2.length; i2++) {
                if (f != Math.min(f, Math.abs(Float.parseFloat(this.data2[i2].getStrike().replace(",", "")) - this.underlyingLast))) {
                    f = Math.abs(Float.parseFloat(this.data2[i2].getStrike().replace(",", "")) - this.underlyingLast);
                    i = i2;
                }
            }
            target_price.setText(indexInfo[0].getUlast());
            target_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            resultAdapter = new SO_ResultAdapter(this.searchFragmentActivity, R.layout.list_so_optionsresult, this.data2, this.ucode, this.wtype, this.expiry, i, false, optionsInfos);
            this.listView.setAdapter(resultAdapter);
            this.listView.setOnItemClickListener(this.setOnItemClickListener);
            this.listView.setDividerHeight(0);
            this.searchFragmentActivity.updateFooterStime(sO_Result.getstime());
        }

        final Calendar c = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        target_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        if (resultAdapter != null) {
                            updateOptionsInfos(target_price.getText().toString(), target_date.getText().toString());
                            resultAdapter.setOptionsInfo(optionsInfos);
                        }
                    }

                    ;
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        target_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        ((Search) getActivity()).dataLoaded();
    }

    /**
     * upate optionsInfo base on the input targetPrice and targetDate
     *
     * @param targetPrice
     * @param targetDate
     */
    private void updateOptionsInfos(String targetPrice, String targetDate) {
        this.optionsInfos = new SO_Result.OptionsInfo[data2.length];
        for (int i2 = 0; i2 < this.data2.length; i2++) {
            optionsInfos[i2] = new SO_Result.OptionsInfo();
            if (data2[i2].getVol().equals("0")) {
                optionsInfos[i2].setIv("-");
                optionsInfos[i2].setExpectPrice("-");
                optionsInfos[i2].setTargetRate("-");
            } else {
                ImpliedVol impliedVol = new ImpliedVol(new double[]{
                        Double.parseDouble(indexInfo[0].getUlast()),
                        Double.parseDouble(data2[i2].getStrike()),
                        getDaysDifference(null, expiry),
                        0,
                        0,
                        Double.parseDouble(data2[i2].getLast())});
                double iv = wtype.equals("C") ? impliedVol.callAm() : impliedVol.putAm();
                American american = new American(new double[]{
                        Double.parseDouble(targetPrice),
                        Double.parseDouble(data2[i2].getStrike()),
                        getDaysDifference(targetDate, expiry),
                        0,
                        0,
                        iv});
                optionsInfos[i2].setIv(new String().format("%.2f", iv));
                double expect_price = wtype.equals("C") ? american.callPrice() : american.putPrice();
                optionsInfos[i2].setExpectPrice(new String().format("%.2f", expect_price));
                double target_rate = expect_price / Double.parseDouble(data2[i2].getLast());
                optionsInfos[i2].setTargetRate(new String().format("%.2f", target_rate));
                Log.d("updateOptionsInfos", targetPrice + ":" + data2[i2].getStrike() + ":" + targetDate);
            }
        }
    }

    /**
     * @param from from date YYYY-MM-DD
     * @param to   to date YYYY-MM-DD
     * @return
     */
    public static double getDaysDifference(String from, String to) {
        Date fromDate, toDate;
        try {
            if (from == null) {
                fromDate = Calendar.getInstance().getTime();
            } else {
                fromDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(from);
            }
            if (to == null) {
                toDate = Calendar.getInstance().getTime();
            } else {
                toDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(to);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

        double days = ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
        return days / 365;
    }

    private void updateSelectionListCode(String str) {
        this.ucode = str;
        loadJSON("all");
    }

    public void dataResult(final SO_Result sO_Result, final String str) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (sO_Result == null) {
                        Search_Index.this.initNoDataListView();
                        ((Search) Search_Index.this.getActivity()).dataLoaded();
                    }
                    if (sO_Result.getContingency().equals("0")) {
                        Search_Index.this.initNoDataListView("");
                        ((Search) Search_Index.this.getActivity()).dataLoaded();
                        Search_Index.this.searchFragmentActivity.ContingencyMessageBox(Commons.language.equals("en_US") ? sO_Result.getEngmsg() : sO_Result.getChimsg(), true);
                    } else if (sO_Result.getContingency().equals("-1")) {
                        Search_Index.this.initNoDataListView("msg_data_after0930");
                        ((Search) Search_Index.this.getActivity()).dataLoaded();
                        Search_Index.this.searchFragmentActivity.updateFooterStime("msg_data_after0930");
                    } else if (str.equals("list")) {
                        Search_Index.this.runUpdate(sO_Result);
                    } else {
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        SO_Result.expiryOption[] expiryOption = sO_Result.getmainData()[0].getExpiryOption();
                        for (int i = 0; i < expiryOption.length; i++) {
                            arrayList.add(StringFormatter.formatExpiry(expiryOption[i].getMdate()));
                            arrayList2.add(expiryOption[i].getMdate());
                        }
                        if (arrayList2.size() > 0) {
                            String[] unused = Search_Index.this.mdate_array = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
                            Search_Index.this.selectionListExpiry.initItems((String[]) arrayList.toArray(new String[arrayList.size()]), R.string.expiry, SelectionList.PopTypes.LIST, 0);
                            Search_Index.this.selectionListExpiry.setEnabled(true);
                            String unused2 = Search_Index.this.expiry = Search_Index.this.mdate_array[0];
                        } else {
                            String[] unused3 = Search_Index.this.mdate_array = null;
                            Search_Index.this.selectionListExpiry.initItems(new String[]{""}, R.string.expiry, SelectionList.PopTypes.LIST, 0);
                            Search_Index.this.selectionListExpiry.setEnabled(false);
                            String unused4 = Search_Index.this.expiry = "";
                        }
                        String string = Search_Index.this.getString(R.string.all);
                        String unused5 = Search_Index.this.wtype = Search_Index.this.callputbutton.getState() == 1 ? "C" : "P";
                        if (sO_Result.getmainData()[0].getIndexInfo().length > 0) {
                            Search_Index.this.underlying_name.setText(Commons.language.equals("en_US") ? sO_Result.getmainData()[0].getIndexInfo()[0].getUname() : sO_Result.getmainData()[0].getIndexInfo()[0].getUnmll());
                        } else {
                            Search_Index.this.underlying_name.setText("-");
                        }
                        String unused6 = Search_Index.this.strikenum = Search_Index.this.num_array[2];
                        Search_Index.this.selectionListStrike.initItems(new String[]{string, "9", "11", "13"}, R.string.strikeshown_one_line, SelectionList.PopTypes.LIST, 2);
                        Search_Index.this.runUpdate(sO_Result);
                    }
                    Search_Index.this.scrollView.initMultiScrollView(Search_Index.this.listView, 158.0f, 177.0f);
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                    e.printStackTrace();
                    ((Search) Search_Index.this.getActivity()).dataLoaded();
                }
            }
        });
    }

    public void initNoDataListView() {
        initNoDataListView("");
    }

    public void initNoDataListView(String str) {
        this.listView.setAdapter(new ArrayAdapter(this.searchFragmentActivity, R.layout.list_nodata, str.equals("msg_data_after0930") ? new String[]{getString(R.string.msg_data_after0930)} : new String[]{getString(R.string.nooption_message)}));
        this.listView.setOnItemClickListener((AdapterView.OnItemClickListener) null);
        this.listView.setDividerHeight(0);
        this.searchFragmentActivity.updateFooterStime(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
    }

    public void initUI() {
        this.listView = (MultiScrollListView) this.fragmentView.findViewById(R.id.listView);
        this.scrollView = (MultiScrollView) this.fragmentView.findViewById(R.id.scrollView);
        this.selectionListExpiry = (SelectionList) this.fragmentView.findViewById(R.id.selectionListExpiry);
        this.selectionListStrike = (SelectionList) this.fragmentView.findViewById(R.id.selectionListStrike);
        this.callputbutton = (CallPutButton) this.fragmentView.findViewById(R.id.callput);
        this.underlying_name = (TextView) this.fragmentView.findViewById(R.id.underlying);
        this.setOnItemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Search_Index.this.searchFragmentActivity.dataLoading();
                Search_Index.this.searchFragmentActivity.backFromOptionDetail = true;
                Intent intent = new Intent(Search_Index.this.searchFragmentActivity, OptionDetail.class);
                intent.putExtra("oid", Search_Index.this.data2[i].getOid());
                intent.putExtra("ucode", Search_Index.this.ucode);
                intent.putExtra("mdate", Search_Index.this.expiry);
                intent.putExtra("wtype", Search_Index.this.wtype);
                intent.putExtra("strike", Search_Index.this.data2[i].getStrike());
                Search_Index.this.searchFragmentActivity.startActivity(intent);
            }
        };
        this.listView.setOnItemClickListener(this.setOnItemClickListener);
        this.index_items = new String[Commons.indexList.getmainData().length];
        this.ucode = CommonsIndex.defaultUnderlyingCode;
        this.selectionListCode = (SelectionList) this.fragmentView.findViewById(R.id.selectionListCode);
        this.selectionListCode.initItems(SelectionList.PopTypes.INDEX, "code", true);
        SelectionList selectionList = this.selectionListCode;
        selectionList.setSelectedText(CommonsIndex.commonList.getIndexcode() + " - " + Commons.getIndexName(CommonsIndex.commonList.getIndexcode(), Commons.language.equals("en_US")));
        this.callputbutton.setOnLayoutClickListener(new CallPutButton.OnLayoutClickListener() {
            public void onLayoutClicked(int i) {
                Commons.LogDebug("Search_Options", "state: " + i);
                LinearLayout linearLayout = (LinearLayout) Search_Index.this.fragmentView.findViewById(R.id.listTitleHeader);
                if (i == 1) {
                    String unused = Search_Index.this.wtype = "C";
                    linearLayout.setBackgroundResource(R.drawable.list_header);
                } else {
                    String unused2 = Search_Index.this.wtype = "P";
                    linearLayout.setBackgroundResource(R.drawable.list_header_red);
                }
                Search_Index.this.loadJSON("list");
            }
        });
        this.selectionListExpiry.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                Commons.LogDebug("Search_Options", "expiry index: " + i);
                String unused = Search_Index.this.expiry = Search_Index.this.mdate_array[i];
                Search_Index.this.loadJSON("list");
            }
        });
        this.selectionListStrike.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                Commons.LogDebug("Search_Options", "num index: " + i);
                String unused = Search_Index.this.strikenum = Search_Index.this.num_array[i];
                Search_Index.this.loadJSON("list");
            }
        });
    }

    public void loadJSON(final String str) {
        GenericJSONParser genericJSONParser = new GenericJSONParser(SO_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<SO_Result>() {
            public void OnJSONCompleted(SO_Result sO_Result) {
                Commons.LogDebug("", sO_Result.toString());
                Search_Index.this.dataResult(sO_Result, str);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                ((Search) Search_Index.this.getActivity()).ShowConnectionErrorDialog(runnable);
                Search_Index.this.dataResult((SO_Result) null, str);
            }
        });
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.url_quote_optionsresult));
        sb.append("?type=indexInfo&ucode=");
        sb.append(this.ucode);
        sb.append("&mdate=");
        sb.append(this.expiry == null ? "" : this.expiry);
        sb.append("&wtype=");
        sb.append(this.wtype);
        sb.append("&strikeNum=");
        sb.append(this.strikenum);
        genericJSONParser.loadXML(sb.toString());
        ((Search) getActivity()).dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.searchFragmentActivity = (Search) getActivity();
        this.searchFragmentActivity.setIndexFragment(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.searchindexoptions, viewGroup, false);
        initUI();
        loadJSON("all");
        return this.fragmentView;
    }

    public void selectionListCallBack(SelectionList.PopTypes popTypes, String str) {
        switch (popTypes) {
            case CODE:
                updateSelectionListCode(str);
                return;
            case NAME:
                updateSelectionListCode(str);
                return;
            case INDEX:
                this.ucode = str.split(" - ")[0];
                this.selectionListCode.setSelectedText(str);
                loadJSON("all");
                return;
            default:
                return;
        }
    }
}
