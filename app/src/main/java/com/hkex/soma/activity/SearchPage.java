package com.hkex.soma.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hkex.soma.JSONParser.UnderlyingListJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.adapter.AutoCompleteListAdapter;
import com.hkex.soma.adapter.Underlying20ListAdapter;
import com.hkex.soma.basic.MasterActivity;
import com.hkex.soma.dataModel.UnderlyingList;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.CommonsIndex;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint({"DefaultLocale"})
public class SearchPage extends MasterActivity {
    private AutoCompleteTextView acTextView;
    private AdapterView.OnItemClickListener onItemClickListener;
    private TextView top20title;
    private int typecode;
    private TextView underlying_no;

    private void ACfinishGoActivity(String str) {
        Commons.noResumeAction = false;
        Intent intent = new Intent().setClass(this, Search.class);
        intent.putExtra("index", "2");
        intent.putExtra("ucode", str.trim());
        startActivity(intent);
    }

    private void ACfinishWithResult(String str) {
        Commons.noResumeAction = true;
        Intent intent = new Intent();
        intent.putExtra("typecode", this.typecode);
        intent.putExtra("searchResult", str.trim());
        setResult(-1, intent);
        finish();
    }

    private void initAutoCompleteTextView(boolean z) {
        this.acTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        if (this.typecode == SelectionList.PopTypes.NUMBER.value) {
            getWindow().setSoftInputMode(5);
            this.acTextView.requestFocus();
        }
        if (this.typecode == SelectionList.PopTypes.CODE.value || this.typecode == SelectionList.PopTypes.SCODE.value || this.typecode == SelectionList.PopTypes.NUMBER.value || this.typecode == -1) {
            this.acTextView.setInputType(3);
        }
        this.acTextView.setOnItemClickListener(this.onItemClickListener);
        if (this.typecode == SelectionList.PopTypes.NUMBER.value) {
            this.acTextView.setText("");
            new Timer().schedule(new TimerTask() {
                public void run() {
                    ((InputMethodManager) SearchPage.this.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(SearchPage.this.acTextView, 1);
                }
            }, 200);
        } else {
            this.acTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View view, boolean z) {
                    if (SearchPage.this.acTextView.getText().toString().equals(SearchPage.this.getString(R.string.search))) {
                        SearchPage.this.acTextView.setText("");
                    }
                }
            });
        }
        this.acTextView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0 || i != 66) {
                    return false;
                }
                String obj = SearchPage.this.acTextView.getText().toString();
                if (SearchPage.this.typecode == SelectionList.PopTypes.NUMBER.value) {
                    obj = obj.replaceAll("[^\\d.]", "");
                } else if (SearchPage.this.typecode == SelectionList.PopTypes.CODE.value || SearchPage.this.typecode == -1) {
                    SearchPage.this.loadUnderlyingList(obj);
                    return true;
                } else if (SearchPage.this.typecode == SelectionList.PopTypes.NAME.value) {
                    SearchPage.this.loadUnderlyingList(obj);
                    return true;
                } else if (SearchPage.this.typecode == SelectionList.PopTypes.SCODE.value) {
                    SearchPage.this.loadJSON(obj);
                    return true;
                } else if (SearchPage.this.typecode == SelectionList.PopTypes.SNAME.value) {
                    SearchPage.this.loadJSON(obj);
                    return true;
                } else if (SearchPage.this.typecode == SelectionList.PopTypes.INDEX.value) {
                    SearchPage.this.loadIndexList(obj);
                    return true;
                }
                if (SearchPage.this.typecode == -1) {
                    SearchPage.this.ACfinishGoActivity(obj);
                } else {
                    SearchPage.this.ACfinishWithResult(obj);
                }
                return true;
            }
        });
    }

    private void initOnItemClickListener() {
        this.onItemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                String str;
                TextView textView = (TextView) view.findViewById(R.id.ac_textview_real);
                if (SearchPage.this.typecode == SelectionList.PopTypes.NAME.value) {
                    str = textView.getText().toString().split(" - ")[1];
                } else if (SearchPage.this.typecode == SelectionList.PopTypes.CODE.value || SearchPage.this.typecode == -1) {
                    str = textView.getText().toString().split(" - ")[0];
                } else if (SearchPage.this.typecode == SelectionList.PopTypes.SNAME.value) {
                    str = textView.getText().toString().split(" - ")[1] + " - " + textView.getText().toString().split(" - ")[0];
                } else if (SearchPage.this.typecode == SelectionList.PopTypes.SCODE.value) {
                    str = textView.getText().toString().split(" - ")[0] + " - " + textView.getText().toString().split(" - ")[1];
                } else if (SearchPage.this.typecode == SelectionList.PopTypes.INDEX.value) {
                    str = textView.getText().toString().split(" - ")[0] + " - " + textView.getText().toString().split(" - ")[1];
                } else {
                    str = "";
                }
                SearchPage.this.acTextView.setText(str);
                if (SearchPage.this.typecode == -1) {
                    SearchPage.this.ACfinishGoActivity(str);
                } else {
                    SearchPage.this.ACfinishWithResult(str);
                }
            }
        };
    }

    private void initPopularIndexlist(boolean z, ListView listView) {
        if (CommonsIndex.commonList != null) {
            int length = CommonsIndex.commonList.getIndexlist().length;
            ((RelativeLayout) findViewById(R.id.mostpopcontainer_box)).setVisibility(View.GONE);
            String[] strArr = new String[length];
            for (int i = 0; i < length; i++) {
                if (Commons.language.equals("en_US") && z) {
                    strArr[i] = CommonsIndex.commonList.getIndexlist()[i].getUcode() + " ~~ ~~ " + CommonsIndex.commonList.getIndexlist()[i].getUname();
                } else if (Commons.language.equals("en_US") && !z) {
                    strArr[i] = CommonsIndex.commonList.getIndexlist()[i].getUname() + " ~~ ~~ " + CommonsIndex.commonList.getIndexlist()[i].getUcode();
                } else if (z) {
                    strArr[i] = CommonsIndex.commonList.getIndexlist()[i].getUcode() + " ~~ ~~ " + CommonsIndex.commonList.getIndexlist()[i].getUnmll();
                } else {
                    strArr[i] = CommonsIndex.commonList.getIndexlist()[i].getUnmll() + " ~~ ~~ " + CommonsIndex.commonList.getIndexlist()[i].getUcode();
                }
            }
            Arrays.sort(strArr);
            listView.setAdapter(new AutoCompleteListAdapter(this, R.layout.list_autocomplete_text, strArr, -1));
        }
    }

    private void initPopularList() {
        if (SelectionList.PopTypes.NUMBER.value != this.typecode) {
            setPopularListVisiblity(0);
            ListView listView = (ListView) findViewById(R.id.mostpoplistView);
            if (SelectionList.PopTypes.SNAME.value == this.typecode) {
                initPopularStocklist(false, listView);
            } else if (SelectionList.PopTypes.SCODE.value == this.typecode) {
                initPopularStocklist(true, listView);
            } else if (SelectionList.PopTypes.NAME.value == this.typecode) {
                initPopularUnderlyinglist(false, listView);
            } else if (SelectionList.PopTypes.INDEX.value == this.typecode) {
                initPopularIndexlist(true, listView);
            } else {
                initPopularUnderlyinglist(true, listView);
            }
            listView.setOnItemClickListener(this.onItemClickListener);
        }
    }

    private void initPopularStocklist(boolean z, ListView listView) {
        if (Commons.commonList != null) {
            int length = Commons.commonList.getstocklist().length;
            String[] strArr = new String[length];
            Log.v("", "" + z);
            for (int i = 0; i < length; i++) {
                if (Commons.language.equals("en_US") && z) {
                    strArr[i] = Commons.commonList.getstocklist()[i].getUcode() + " ~~ ~~ " + Commons.commonList.getstocklist()[i].getUname();
                } else if (Commons.language.equals("en_US") && !z) {
                    strArr[i] = Commons.commonList.getstocklist()[i].getUname() + " ~~ ~~ " + Commons.commonList.getstocklist()[i].getUcode();
                } else if (z) {
                    strArr[i] = Commons.commonList.getstocklist()[i].getUcode() + " ~~ ~~ " + Commons.commonList.getstocklist()[i].getUnmll();
                } else {
                    strArr[i] = Commons.commonList.getstocklist()[i].getUnmll() + " ~~ ~~ " + Commons.commonList.getstocklist()[i].getUcode();
                }
            }
            Arrays.sort(strArr);
            listView.setAdapter(new AutoCompleteListAdapter(this, R.layout.list_autocomplete_text, strArr, -1));
        }
    }

    private void initPopularUnderlyinglist(boolean z, ListView listView) {
        if (Commons.commonList != null) {
            int length = Commons.commonList.getunderlyinglist().length;
            ((TextView) findViewById(R.id.underlying_no)).setText("" + length);
            ((LinearLayout) findViewById(R.id.top20_box)).setVisibility(View.VISIBLE);
            ((RelativeLayout) findViewById(R.id.mostpopcontainer_box)).setVisibility(View.GONE);
            String[] strArr = new String[length];
            for (int i = 0; i < length; i++) {
                if (Commons.language.equals("en_US") && z) {
                    strArr[i] = Commons.commonList.getunderlyinglist()[i].getUcode() + " ~~ ~~ " + Commons.commonList.getunderlyinglist()[i].getUname();
                } else if (Commons.language.equals("en_US") && !z) {
                    strArr[i] = Commons.commonList.getunderlyinglist()[i].getUname() + " ~~ ~~ " + Commons.commonList.getunderlyinglist()[i].getUcode();
                } else if (z) {
                    strArr[i] = Commons.commonList.getunderlyinglist()[i].getUcode() + " ~~ ~~ " + Commons.commonList.getunderlyinglist()[i].getUnmll();
                } else {
                    strArr[i] = Commons.commonList.getunderlyinglist()[i].getUnmll() + " ~~ ~~ " + Commons.commonList.getunderlyinglist()[i].getUcode();
                }
            }
            Arrays.sort(strArr);
            final Underlying20ListAdapter underlying20ListAdapter = new Underlying20ListAdapter(this, R.layout.list_autocomplete_text, strArr, -1);
            listView.setAdapter(underlying20ListAdapter);
            this.onItemClickListener = new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    String str;
                    if (!underlying20ListAdapter.get_islimit20() || i != 20) {
                        TextView textView = (TextView) view.findViewById(R.id.ac_textview_real);
                        if (SearchPage.this.typecode == SelectionList.PopTypes.NAME.value) {
                            str = textView.getText().toString().split(" - ")[1];
                        } else if (SearchPage.this.typecode == SelectionList.PopTypes.CODE.value || SearchPage.this.typecode == -1) {
                            str = textView.getText().toString().split(" - ")[0];
                        } else if (SearchPage.this.typecode == SelectionList.PopTypes.SNAME.value) {
                            str = textView.getText().toString().split(" - ")[1] + " - " + textView.getText().toString().split(" - ")[0];
                        } else if (SearchPage.this.typecode == SelectionList.PopTypes.SCODE.value) {
                            str = textView.getText().toString().split(" - ")[0] + " - " + textView.getText().toString().split(" - ")[1];
                        } else if (SearchPage.this.typecode == SelectionList.PopTypes.INDEX.value) {
                            str = textView.getText().toString().split(" - ")[0] + " - " + textView.getText().toString().split(" - ")[1];
                        } else {
                            str = "";
                        }
                        SearchPage.this.acTextView.setText(str);
                        if (SearchPage.this.typecode == -1) {
                            SearchPage.this.ACfinishGoActivity(str);
                        } else {
                            SearchPage.this.ACfinishWithResult(str);
                        }
                    } else {
                        underlying20ListAdapter.set_islimit20(false);
                        underlying20ListAdapter.notifyDataSetChanged();
                        TextView unused = SearchPage.this.top20title = (TextView) SearchPage.this.findViewById(R.id.top20title);
                        SearchPage.this.top20title.setText(R.string.search_msg_all);
                    }
                }
            };
            listView.setOnItemClickListener(this.onItemClickListener);
        }
    }

    /* access modifiers changed from: private */
    public void setPopularListVisiblity(int i) {
        ((LinearLayout) findViewById(R.id.mostpopcontainer)).setVisibility(i);
    }

    /* access modifiers changed from: private */
    public void setResultListVisiblity(int i) {
        ((LinearLayout) findViewById(R.id.searchresultcontainer)).setVisibility(i);
    }

    public void dataResult(final UnderlyingList underlyingList) {
        this.handler.post(new Runnable() {
            public void run() {
                SearchPage.this.dataLoaded();
                SearchPage.this.setPopularListVisiblity(8);
                SearchPage.this.setResultListVisiblity(0);
                ListView listView = (ListView) SearchPage.this.findViewById(R.id.searchresultlistView);
                int length = underlyingList.getmainData().length;
                String[] strArr = new String[length];
                for (int i = 0; i < length; i++) {
                    if (Commons.language.equals("en_US") && (SelectionList.PopTypes.SCODE.value == SearchPage.this.typecode || SelectionList.PopTypes.CODE.value == SearchPage.this.typecode)) {
                        strArr[i] = underlyingList.getmainData()[i].getUcode() + " ~~ ~~ " + underlyingList.getmainData()[i].getUname();
                    } else if (Commons.language.equals("en_US") && SelectionList.PopTypes.SNAME.value == SearchPage.this.typecode) {
                        strArr[i] = underlyingList.getmainData()[i].getUname() + " ~~ ~~ " + underlyingList.getmainData()[i].getUcode();
                    } else if (SelectionList.PopTypes.SCODE.value == SearchPage.this.typecode || SelectionList.PopTypes.CODE.value == SearchPage.this.typecode) {
                        strArr[i] = underlyingList.getmainData()[i].getUcode() + " ~~ ~~ " + underlyingList.getmainData()[i].getUnmll();
                    } else {
                        strArr[i] = underlyingList.getmainData()[i].getUnmll() + " ~~ ~~ " + underlyingList.getmainData()[i].getUcode();
                    }
                }
                listView.setAdapter(new AutoCompleteListAdapter(SearchPage.this, R.layout.list_autocomplete_text, strArr, -1));
                listView.setOnItemClickListener(SearchPage.this.onItemClickListener);
            }
        });
    }

    public void loadIndexList(String str) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < Commons.indexList.getmainData().length; i++) {
            boolean z = Commons.indexList.getmainData()[i].getUcode().indexOf(str) >= 0;
            boolean z2 = Commons.indexList.getmainData()[i].getUname().toLowerCase().indexOf(str.toLowerCase()) >= 0;
            boolean z3 = Commons.indexList.getmainData()[i].getUnmll().indexOf(str) >= 0;
            if (z || z2 || z3) {
                if (Commons.language.equals("en_US") && (SelectionList.PopTypes.INDEX.value == this.typecode || this.typecode == -1)) {
                    arrayList.add(Commons.indexList.getmainData()[i].getUcode() + " ~~ ~~ " + Commons.indexList.getmainData()[i].getUname());
                } else if (Commons.language.equals("en_US") && SelectionList.PopTypes.NAME.value == this.typecode) {
                    arrayList.add(Commons.indexList.getmainData()[i].getUname() + " ~~ ~~ " + Commons.indexList.getmainData()[i].getUcode());
                } else if (SelectionList.PopTypes.INDEX.value == this.typecode || this.typecode == -1) {
                    arrayList.add(Commons.indexList.getmainData()[i].getUcode() + " ~~ ~~ " + Commons.indexList.getmainData()[i].getUnmll());
                } else {
                    arrayList.add(Commons.indexList.getmainData()[i].getUnmll() + " ~~ ~~ " + Commons.indexList.getmainData()[i].getUcode());
                }
            }
        }
        final String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        this.handler.post(new Runnable() {
            public void run() {
                SearchPage.this.setPopularListVisiblity(8);
                SearchPage.this.setResultListVisiblity(0);
                ListView listView = (ListView) SearchPage.this.findViewById(R.id.searchresultlistView);
                listView.setAdapter(new AutoCompleteListAdapter(SearchPage.this, R.layout.list_autocomplete_text, strArr, -1));
                listView.setOnItemClickListener(SearchPage.this.onItemClickListener);
            }
        });
    }

    public void loadJSON(String str) {
        dataLoading();
        UnderlyingListJSONParser underlyingListJSONParser = new UnderlyingListJSONParser();
        underlyingListJSONParser.setOnJSONCompletedListener(new UnderlyingListJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(UnderlyingList underlyingList) {
                Commons.LogDebug("", underlyingList.toString());
                SearchPage.this.dataResult(underlyingList);
            }
        });
        underlyingListJSONParser.setOnJSONFailedListener(new UnderlyingListJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                SearchPage.this.ShowConnectionErrorDialog(runnable);
            }
        });
        String replace = str.replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "%20");
        underlyingListJSONParser.loadXML(getString(R.string.url_common_list) + "?type=stocklist&key=" + replace);
    }

    public void loadUnderlyingList(String str) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < Commons.underlyingList.getmainData().length; i++) {
            boolean z = Commons.underlyingList.getmainData()[i].getUcode().indexOf(str) >= 0;
            boolean z2 = Commons.underlyingList.getmainData()[i].getUname().toLowerCase().indexOf(str.toLowerCase()) >= 0;
            boolean z3 = Commons.underlyingList.getmainData()[i].getUnmll().indexOf(str) >= 0;
            if (z || z2 || z3) {
                if (Commons.language.equals("en_US") && (SelectionList.PopTypes.CODE.value == this.typecode || this.typecode == -1)) {
                    arrayList.add(Commons.underlyingList.getmainData()[i].getUcode() + " ~~ ~~ " + Commons.underlyingList.getmainData()[i].getUname());
                } else if (Commons.language.equals("en_US") && SelectionList.PopTypes.NAME.value == this.typecode) {
                    arrayList.add(Commons.underlyingList.getmainData()[i].getUname() + " ~~ ~~ " + Commons.underlyingList.getmainData()[i].getUcode());
                } else if (SelectionList.PopTypes.CODE.value == this.typecode || this.typecode == -1) {
                    arrayList.add(Commons.underlyingList.getmainData()[i].getUcode() + " ~~ ~~ " + Commons.underlyingList.getmainData()[i].getUnmll());
                } else {
                    arrayList.add(Commons.underlyingList.getmainData()[i].getUnmll() + " ~~ ~~ " + Commons.underlyingList.getmainData()[i].getUcode());
                }
            }
        }
        final String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        this.handler.post(new Runnable() {
            public void run() {
                SearchPage.this.setPopularListVisiblity(8);
                SearchPage.this.setResultListVisiblity(0);
                ListView listView = (ListView) SearchPage.this.findViewById(R.id.searchresultlistView);
                listView.setAdapter(new AutoCompleteListAdapter(SearchPage.this, R.layout.list_autocomplete_text, strArr, -1));
                listView.setOnItemClickListener(SearchPage.this.onItemClickListener);
            }
        });
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.searchpage);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            Commons.noResumeAction = true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.typecode == -1) {
            finish();
        }
        this.typecode = getIntent().getIntExtra("typecode", 0);
        boolean booleanExtra = getIntent().getBooleanExtra("isAutocomplete", false);
        initOnItemClickListener();
        initAutoCompleteTextView(booleanExtra);
        ((ImageView) findViewById(R.id.searchclose)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"NewApi"})
            public void onClick(View view) {
                ((InputMethodManager) SearchPage.this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(SearchPage.this.acTextView.getWindowToken(), 0);
                new CountDownTimer(300, 300) {
                    public void onFinish() {
                        Commons.noResumeAction = true;
                        SearchPage.this.finish();
                    }

                    public void onTick(long j) {
                    }
                }.start();
            }
        });
        initPopularList();
    }
}
