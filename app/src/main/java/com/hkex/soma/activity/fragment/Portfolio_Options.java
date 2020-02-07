package com.hkex.soma.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.activity.Portfolio;
import com.hkex.soma.activity.PortfolioAdd;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.element.CallPutButton;
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;

public class Portfolio_Options extends MasterFragment {
    /* access modifiers changed from: private */
    public Bundle bundle;
    /* access modifiers changed from: private */
    public String[] expiries;
    private View fragmentView;
    /* access modifiers changed from: private */
    public PortfolioAdd portfolioFragmentActivity;
    /* access modifiers changed from: private */
    public SelectionList selectionListCode;
    /* access modifiers changed from: private */
    public SelectionList selectionListExpiry;
    private SelectionList selectionListName;
    /* access modifiers changed from: private */
    public SelectionList selectionListPrice;
    /* access modifiers changed from: private */
    public SelectionList selectionListQuantity;
    /* access modifiers changed from: private */
    public SelectionList selectionListStrike;
    /* access modifiers changed from: private */
    public SelectionList selectionListTradeDirection;
    private String[] strikes;

    private void initSelectionListExpiry() {
        this.selectionListExpiry = (SelectionList) this.fragmentView.findViewById(R.id.selectionListExpiry);
        this.selectionListExpiry.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                Portfolio_Options.this.updateSelectionListExpiry(Portfolio_Options.this.selectionListCode.getSelectedText(), Portfolio_Options.this.expiries[i]);
            }
        });
    }

    private void initSelectionListPrice() {
        this.selectionListPrice = (SelectionList) this.fragmentView.findViewById(R.id.selectionListPrice);
        this.selectionListPrice.initItems(SelectionList.PopTypes.NUMBER, "number", false);
        this.selectionListPrice.setSelectedText(Commons.text_optional);
        this.selectionListPrice.setGreyStyle(true);
        this.selectionListPrice.setOnLayoutClickListener(new SelectionList.OnLayoutClickListener() {
            public void onLayoutClicked() {
                Commons.activeSelectionList = "price";
            }
        });
    }

    private void initSelectionListQuantity() {
        this.selectionListQuantity = (SelectionList) this.fragmentView.findViewById(R.id.selectionListQuantity);
        this.selectionListQuantity.initItems(SelectionList.PopTypes.NUMBER, "number", false);
        this.selectionListQuantity.setSelectedText(Commons.text_optional);
        this.selectionListQuantity.setGreyStyle(true);
        this.selectionListQuantity.setOnLayoutClickListener(new SelectionList.OnLayoutClickListener() {
            public void onLayoutClicked() {
                Commons.activeSelectionList = "quantity";
            }
        });
    }

    private void initSelectionListTradeDirection() {
        final TextView textView = (TextView) this.fragmentView.findViewById(R.id.neg_message);
        String string = getString(R.string.buy);
        String string2 = getString(R.string.sell);
        this.selectionListTradeDirection = (SelectionList) this.fragmentView.findViewById(R.id.selectionListTradeDirection);
        String[] strArr = {string, string2};
        this.selectionListTradeDirection.initItems(strArr, -1, SelectionList.PopTypes.LIST, 0);
        this.selectionListTradeDirection.setOnListItemChangeListener(new SelectionList.OnListItemChangeListener() {
            public void onListItemChanged(int i) {
                if (i == 1) {
                    textView.setVisibility(View.VISIBLE);
                    Portfolio_Options.this.selectionListPrice.setPrefixText("-");
                    return;
                }
                textView.setVisibility(View.GONE);
                Portfolio_Options.this.selectionListPrice.setPrefixText("");
            }
        });
    }

    private void updateSelectionListCode(String str) {
        this.selectionListCode.setSelectedText(str);
        this.selectionListName.setSelectedText(Commons.MapUnderlyingName(str));
        this.expiries = Commons.getExpiries(str, (String) null);
        this.strikes = Commons.getStrikes(str, this.expiries[0]);
        this.selectionListExpiry.initItems(this.expiries, -1, SelectionList.PopTypes.EXPIRY, 0);
        this.selectionListStrike.initItems(this.strikes, -1, SelectionList.PopTypes.LIST, 0);
        this.selectionListExpiry.setSelectedText(StringFormatter.formatExpiry(this.expiries[0]));
    }

    /* access modifiers changed from: private */
    public void updateSelectionListExpiry(String str, String str2) {
        this.selectionListExpiry.setSelectedText(StringFormatter.formatExpiry(str2));
        this.strikes = Commons.getStrikes(str, str2);
        this.selectionListStrike.initItems(this.strikes, -1, SelectionList.PopTypes.LIST, 0);
    }

    public void initUI() {
        initSelectionListPrice();
        initSelectionListQuantity();
        initSelectionListTradeDirection();
        initSelectionListExpiry();
        final CallPutButton callPutButton = (CallPutButton) this.fragmentView.findViewById(R.id.callputbutton);
        this.selectionListStrike = (SelectionList) this.fragmentView.findViewById(R.id.selectionListStrike);
        this.selectionListName = (SelectionList) this.fragmentView.findViewById(R.id.selectionListName);
        this.selectionListName.initItems(SelectionList.PopTypes.NAME, "name", true);
        this.selectionListCode = (SelectionList) this.fragmentView.findViewById(R.id.selectionListCode);
        this.selectionListCode.initItems(SelectionList.PopTypes.CODE, "code", true);
        ImageButton imageButton = (ImageButton) this.fragmentView.findViewById(R.id.confirmButton);
        ImageButton imageButton2 = (ImageButton) this.fragmentView.findViewById(R.id.editButton);
        if (Commons.language.equals("en_US")) {
            imageButton.setImageResource(R.drawable.btn_long_add_e);
            imageButton2.setImageResource(R.drawable.btn_long_done_e);
        } else if (Commons.language.equals("zh_CN")) {
            imageButton.setImageResource(R.drawable.btn_long_add_gb);
            imageButton2.setImageResource(R.drawable.btn_long_done_gb);
        } else {
            imageButton.setImageResource(R.drawable.btn_long_add_c);
            imageButton2.setImageResource(R.drawable.btn_long_done_c);
        }
        if (this.bundle != null) {
            imageButton.setVisibility(View.GONE);
            this.selectionListCode.setSelectedText(this.bundle.getString("ucode"));
            this.selectionListName.setSelectedText(this.bundle.getString("uname"));
            this.selectionListStrike.setSelectedText(this.bundle.getString("strike"));
            this.selectionListExpiry.setSelectedText(StringFormatter.formatExpiry(this.bundle.getString("mdate")));
            SelectionList selectionList = (SelectionList) this.fragmentView.findViewById(R.id.selectionListCPButton);
            selectionList.setVisibility(View.VISIBLE);
            selectionList.setEnabled(false);
            selectionList.setSelectedText(getString(this.bundle.getString("wtype").equals("C") ? R.string.call : R.string.put));
            this.selectionListName.setEnabled(false);
            this.selectionListExpiry.setEnabled(false);
            this.selectionListCode.setEnabled(false);
            this.selectionListStrike.setEnabled(false);
            if (this.bundle.getString("qty").equals("0")) {
                this.selectionListQuantity.setSelectedText(Commons.text_optional);
            } else {
                this.selectionListQuantity.setSelectedText(this.bundle.getString("qty"));
                this.selectionListQuantity.setGreyStyle(false);
            }
            if (this.bundle.getString("price").equals("0")) {
                this.selectionListPrice.setSelectedText(Commons.text_optional);
            } else {
                this.selectionListPrice.setSelectedText(this.bundle.getString("price"));
                this.selectionListPrice.setGreyStyle(false);
            }
            if (this.bundle.getString("direction").equals("0")) {
                this.selectionListTradeDirection.setSelectedIndex(0);
            } else {
                this.selectionListTradeDirection.setSelectedIndex(1);
                ((TextView) this.fragmentView.findViewById(R.id.neg_message)).setVisibility(View.VISIBLE);
                this.selectionListPrice.setPrefixText("-");
            }
            callPutButton.setVisibility(View.GONE);
            imageButton2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Commons.noResumeAction = false;
                    Portfolio_Options.this.portfolioFragmentActivity.dataLoading();
                    String selectedText = Portfolio_Options.this.selectionListQuantity.getSelectedText();
                    String selectedText2 = Portfolio_Options.this.selectionListPrice.getSelectedText();
                    if (selectedText.equals(Commons.text_optional)) {
                        selectedText = "0";
                    }
                    if (selectedText2.equals(Commons.text_optional)) {
                        selectedText2 = "0";
                    }
                    Intent intent = new Intent();
                    intent.putExtra("direction", Portfolio_Options.this.selectionListTradeDirection.getSelectedIndex() + "");
                    intent.putExtra("qty", selectedText);
                    intent.putExtra("price", selectedText2);
                    intent.putExtra("row", Portfolio_Options.this.bundle.getInt("row"));
                    intent.putExtra("backtype", "edit");
                    PortfolioAdd access$000 = Portfolio_Options.this.portfolioFragmentActivity;
                    PortfolioAdd unused = Portfolio_Options.this.portfolioFragmentActivity;
                    access$000.setResult(-1, intent);
                    Portfolio_Options.this.portfolioFragmentActivity.finish();
                }
            });
            return;
        }
        imageButton2.setVisibility(View.GONE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Commons.noResumeAction = false;
                Portfolio_Options.this.portfolioFragmentActivity.dataLoading();
                if (!Portfolio_Options.this.selectionListCode.getSelectedText().equals("")) {
                    String selectedText = Portfolio_Options.this.selectionListQuantity.getSelectedText();
                    String selectedText2 = Portfolio_Options.this.selectionListPrice.getSelectedText();
                    if (selectedText.equals(Commons.text_optional)) {
                        selectedText = "0";
                    }
                    if (selectedText2.equals(Commons.text_optional)) {
                        selectedText2 = "0";
                    }
                    PortfolioAdd access$000 = Portfolio_Options.this.portfolioFragmentActivity;
                    String selectedText3 = Portfolio_Options.this.selectionListCode.getSelectedText();
                    String MapUnderlyingName = Commons.MapUnderlyingName(Portfolio_Options.this.selectionListCode.getSelectedText(), false);
                    String MapUnderlyingName2 = Commons.MapUnderlyingName(Portfolio_Options.this.selectionListCode.getSelectedText(), true);
                    String type = callPutButton.getType();
                    String selectedText4 = Portfolio_Options.this.selectionListStrike.getSelectedText();
                    String selectedItemText = Portfolio_Options.this.selectionListExpiry.getSelectedItemText();
                    Portfolio.AddOptionToPortfolio(access$000, selectedText3, MapUnderlyingName, MapUnderlyingName2, type, selectedText4, selectedItemText, Portfolio_Options.this.selectionListTradeDirection.getSelectedIndex() + "", selectedText, selectedText2);
                    Intent intent = new Intent();
                    intent.putExtra("backtype", "add");
                    PortfolioAdd access$0002 = Portfolio_Options.this.portfolioFragmentActivity;
                    PortfolioAdd unused = Portfolio_Options.this.portfolioFragmentActivity;
                    access$0002.setResult(-1, intent);
                    Portfolio_Options.this.portfolioFragmentActivity.finish();
                }
            }
        });
        updateSelectionListCode(Commons.defaultUnderlyingCode);
    }

    public void onCreate(Bundle bundle2) {
        super.onCreate(bundle2);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle2) {
        this.fragmentView = layoutInflater.inflate(R.layout.portfoliooptions, viewGroup, false);
        this.portfolioFragmentActivity = (PortfolioAdd) getActivity();
        this.bundle = this.portfolioFragmentActivity.setOptionsFragment(this);
        initUI();
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
            case NUMBER:
                if (Commons.activeSelectionList.equals("quantity")) {
                    this.selectionListQuantity.setSelectedText(str);
                    return;
                } else if (Commons.activeSelectionList.equals("price")) {
                    this.selectionListPrice.setSelectedText(str);
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }
}
