package com.hkex.soma.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hkex.soma.JSONParser.SO_ResultJSONParser;
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
import com.hkex.soma.utils.StringFormatter;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class Search_Options extends MasterFragment {
    private CallPutButton callputbutton;
    private SO_Result.mainData2[] data2;
    private String expiry;
    private View fragmentView;
    private Handler handler = new Handler();
    private MultiScrollListView listView;
    private String[] mdate_array;
    private String[] num_array = {"0", "9", "11", "13"};
    private MultiScrollView scrollView;
    private Search searchFragmentActivity;
    private SelectionList selectionListCode;
    private SelectionList selectionListExpiry;
    private SelectionList selectionListName;
    private SelectionList selectionListStrike;
    private AdapterView.OnItemClickListener setOnItemClickListener;
    private String strikenum = "11";
    private String ucode;
    private String uname;
    private float underlyingLast = 0.0f;
    private String wtype = "C";
    private SO_Result.underlyingInfo[] underlyingInfos;

    private void runUpdate(SO_Result sO_Result) {
        SO_Result.underlyingInfo[] underlyingInfo = sO_Result.getmainData()[0].getUnderlyingInfo();
        TextView textView = (TextView) this.fragmentView.findViewById(R.id.textView2);
        TextView textView2 = (TextView) this.fragmentView.findViewById(R.id.textView3);
        ImageView imageView = (ImageView) this.fragmentView.findViewById(R.id.updown);
        if (underlyingInfo.length > 0) {
            textView.setText(underlyingInfo[0].getUlast());
            this.underlyingLast = Float.parseFloat(underlyingInfo[0].getUlast());
            textView2.setText(underlyingInfo[0].getUchng() + "(" + underlyingInfo[0].getUpchng() + "%)");
            StringFormatter.formatChng(imageView, underlyingInfo[0].getUchng());
        } else {
            textView.setText("-");
            textView2.setText("-");
            StringFormatter.formatChng(imageView, "-");
        }
        this.data2 = sO_Result.getmainData2();
        if (this.data2.length == 0) {
            initNoDataListView();
        } else {
            int i = -1;
            float f = Float.MAX_VALUE;
            for (int i2 = 0; i2 < this.data2.length; i2++) {
                if (f != Math.min(f, Math.abs(Float.parseFloat(this.data2[i2].getStrike()) - this.underlyingLast))) {
                    f = Math.abs(Float.parseFloat(this.data2[i2].getStrike()) - this.underlyingLast);
                    i = i2;
                }
            }
            this.listView.setAdapter(new SO_ResultAdapter(this.searchFragmentActivity, R.layout.list_so_optionsresult, this.data2, this.ucode, this.wtype, this.expiry, i, sO_Result.getmainData()[0].getUnderlyingInfo()));
            this.listView.setOnItemClickListener(this.setOnItemClickListener);
            this.listView.setDividerHeight(0);
            this.searchFragmentActivity.updateFooterStime(sO_Result.getstime());
        }
        ((Search) getActivity()).dataLoaded();
    }

    private void updateSelectionListCode(String str) {
        this.selectionListCode.setSelectedText(str);
        this.selectionListName.setSelectedText(Commons.MapUnderlyingName(str));
        this.ucode = str;
        loadJSON("info");
    }

    public void dataResult(final SO_Result sO_Result, final String str) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (sO_Result == null) {
                        Search_Options.this.initNoDataListView();
                        ((Search) Search_Options.this.getActivity()).dataLoaded();
                    } else if (str.equals("search")) {
                        Search_Options.this.runUpdate(sO_Result);
                    } else if (sO_Result.getContingency().equals("0")) {
                        Search_Options.this.initNoDataListView("");
                        ((Search) Search_Options.this.getActivity()).dataLoaded();
                        Search_Options.this.searchFragmentActivity.ContingencyMessageBox(Commons.language.equals("en_US") ? sO_Result.getEngmsg() : sO_Result.getChimsg(), true);
                    } else if (sO_Result.getContingency().equals("-1")) {
                        Search_Options.this.initNoDataListView("nodatayet");
                        ((Search) Search_Options.this.getActivity()).dataLoaded();
                        Search_Options.this.searchFragmentActivity.updateFooterStime("nodatayet");
                    } else {
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        SO_Result.expiryOption[] expiryOption = sO_Result.getmainData()[0].getExpiryOption();
                        for (int i = 0; i < expiryOption.length; i++) {
                            arrayList.add(StringFormatter.formatExpiry(expiryOption[i].getMdate()));
                            arrayList2.add(expiryOption[i].getMdate());
                        }
                        if (arrayList2.size() > 0) {
                            String[] unused = Search_Options.this.mdate_array = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
                            Search_Options.this.selectionListExpiry.initItems((String[]) arrayList.toArray(new String[arrayList.size()]), R.string.expiry, SelectionList.PopTypes.LIST, 0);
                            Search_Options.this.selectionListExpiry.setEnabled(true);
                            String unused2 = Search_Options.this.expiry = Search_Options.this.mdate_array[0];
                        } else {
                            String[] unused3 = Search_Options.this.mdate_array = null;
                            Search_Options.this.selectionListExpiry.initItems(new String[]{""}, R.string.expiry, SelectionList.PopTypes.LIST, 0);
                            Search_Options.this.selectionListExpiry.setEnabled(false);
                            String unused4 = Search_Options.this.expiry = "";
                        }
                        String string = Search_Options.this.getString(R.string.all);
                        String unused5 = Search_Options.this.wtype = Search_Options.this.callputbutton.getState() == 1 ? "C" : "P";
                        String unused6 = Search_Options.this.strikenum = Search_Options.this.num_array[2];
                        Search_Options.this.selectionListStrike.initItems(new String[]{string, "9", "11", "13"}, R.string.strikeshown_one_line, SelectionList.PopTypes.LIST, 2);
                        Search_Options.this.runUpdate(sO_Result);
                    }
                    Search_Options.this.scrollView.initMultiScrollView(Search_Options.this.listView, 158.0f, 177.0f);
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void initNoDataListView() {
        initNoDataListView("");
    }

    public void initNoDataListView(String str) {
        this.listView.setAdapter(new ArrayAdapter(this.searchFragmentActivity, R.layout.list_nodata, str.equals("nodatayet") ? new String[]{getString(R.string.nodatayet)} : new String[]{getString(R.string.nooption_message)}));
        this.listView.setOnItemClickListener((AdapterView.OnItemClickListener) null);
        this.listView.setDividerHeight(0);
        this.searchFragmentActivity.updateFooterStime(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
    }

    public void initUI() {
        this.listView = (MultiScrollListView) this.fragmentView.findViewById(R.id.listView);
        this.scrollView = (MultiScrollView) this.fragmentView.findViewById(R.id.scrollView);
        this.selectionListCode = (SelectionList) this.fragmentView.findViewById(R.id.selectionListCode);
        this.selectionListName = (SelectionList) this.fragmentView.findViewById(R.id.selectionListName);
        this.selectionListExpiry = (SelectionList) this.fragmentView.findViewById(R.id.selectionListExpiry);
        this.selectionListStrike = (SelectionList) this.fragmentView.findViewById(R.id.selectionListStrike);
        this.callputbutton = (CallPutButton) this.fragmentView.findViewById(R.id.callput);
        this.setOnItemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Search_Options.this.searchFragmentActivity.dataLoading();
                Search_Options.this.searchFragmentActivity.backFromOptionDetail = true;
                Intent intent = new Intent(Search_Options.this.searchFragmentActivity, OptionDetail.class);
                intent.putExtra("oid", Search_Options.this.data2[i].getOid());
                intent.putExtra("ucode", Search_Options.this.ucode);
                intent.putExtra("mdate", Search_Options.this.expiry);
                intent.putExtra("wtype", Search_Options.this.wtype);
                intent.putExtra("strike", Search_Options.this.data2[i].getStrike());
                Search_Options.this.searchFragmentActivity.startActivity(intent);
            }
        };
        this.listView.setOnItemClickListener(this.setOnItemClickListener);
        this.selectionListCode.initItems(SelectionList.PopTypes.CODE, "code", true);
        this.selectionListName.initItems(SelectionList.PopTypes.NAME, "name", true);
        this.selectionListCode.setSelectedText(this.ucode);
        this.selectionListName.setSelectedText(this.uname);
        this.callputbutton.setOnLayoutClickListener(new CallPutButton.OnLayoutClickListener() {
            public void onLayoutClicked(int i) {
                Commons.LogDebug("Search_Options", "state: " + i);
                LinearLayout linearLayout = (LinearLayout) Search_Options.this.fragmentView.findViewById(R.id.listTitleHeader);
                if (i == 1) {
                    String unused = Search_Options.this.wtype = "C";
                    linearLayout.setBackgroundResource(R.drawable.list_header);
                } else {
                    String unused2 = Search_Options.this.wtype = "P";
                    linearLayout.setBackgroundResource(R.drawable.list_header_red);
                }
                Search_Options.this.loadJSON("search");
            }
        });
        this.selectionListExpiry.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                Commons.LogDebug("Search_Options", "expiry index: " + i);
                String unused = Search_Options.this.expiry = Search_Options.this.mdate_array[i];
                Search_Options.this.loadJSON("search");
            }
        });
        this.selectionListStrike.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                Commons.LogDebug("Search_Options", "num index: " + i);
                String unused = Search_Options.this.strikenum = Search_Options.this.num_array[i];
                Search_Options.this.loadJSON("search");
            }
        });
    }

    public void loadJSON(final String str) {
        SO_ResultJSONParser sO_ResultJSONParser = new SO_ResultJSONParser();
        sO_ResultJSONParser.setOnJSONCompletedListener(new SO_ResultJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(SO_Result sO_Result) {
                Commons.LogDebug("", sO_Result.toString());
                Search_Options.this.dataResult(sO_Result, str);
            }
        });
        sO_ResultJSONParser.setOnJSONFailedListener(new SO_ResultJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                ((Search) Search_Options.this.getActivity()).ShowConnectionErrorDialog(runnable);
                Search_Options.this.dataResult((SO_Result) null, str);
            }
        });
        if (str.equals("search")) {
            sO_ResultJSONParser.loadXML(getString(R.string.url_quote_optionsresult) + "?type=underlyingInfo&ucode=" + this.ucode + "&wtype=" + this.wtype + "&mdate=" + this.expiry + "&strikeNum=" + this.strikenum);
        } else {
            sO_ResultJSONParser.loadXML(getString(R.string.url_quote_optionsresult) + "?type=underlyingInfo&ucode=" + this.ucode + "&wtype=" + this.wtype + "&strikeNum=" + this.strikenum);
        }
        ((Search) getActivity()).dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.searchFragmentActivity = (Search) getActivity();
        this.searchFragmentActivity.setOptionsFragment(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.searchoptions, viewGroup, false);
        if (this.searchFragmentActivity.currenttag.equals("0")) {
            this.ucode = this.searchFragmentActivity.getUcode();
            this.uname = Commons.MapUnderlyingName(this.ucode);
            initUI();
            loadJSON("info");
        }
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
            default:
                return;
        }
    }


    public static int getDaysDifference(Date toDate) {
        if (toDate == null)
            return 0;

        Date today =  Calendar.getInstance().getTime();
        return (int) ((toDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24));
    }
}
