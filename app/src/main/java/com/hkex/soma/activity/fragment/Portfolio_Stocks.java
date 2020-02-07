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
import com.hkex.soma.element.SelectionList;
import com.hkex.soma.utils.Commons;

public class Portfolio_Stocks extends MasterFragment {
    /* access modifiers changed from: private */
    public Bundle bundle;
    private View fragmentView;
    /* access modifiers changed from: private */
    public PortfolioAdd portfolioFragmentActivity;
    /* access modifiers changed from: private */
    public SelectionList selectionListCode;
    private SelectionList selectionListName;
    /* access modifiers changed from: private */
    public SelectionList selectionListPrice;
    /* access modifiers changed from: private */
    public SelectionList selectionListQuantity;
    /* access modifiers changed from: private */
    public SelectionList selectionListTradeDirection;

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
                    Portfolio_Stocks.this.selectionListPrice.setPrefixText("-");
                    return;
                }
                textView.setVisibility(View.GONE);
                Portfolio_Stocks.this.selectionListPrice.setPrefixText("");
            }
        });
    }

    private void updateSelectionListCode(String str, String str2) {
        this.selectionListCode.setSelectedText(str);
        SelectionList selectionList = this.selectionListName;
        if (str2 == null || str2.equals("")) {
            str2 = Commons.MapUnderlyingName(str);
        }
        selectionList.setSelectedText(str2);
    }

    private void updateSelectionListSCode(String str) {
        String str2 = str.split(" - ")[0];
        String str3 = str.split(" - ")[1];
        this.selectionListCode.setSelectedText(str2);
        this.selectionListName.setSelectedText(str3);
    }

    public void initUI() {
        initSelectionListPrice();
        initSelectionListQuantity();
        initSelectionListTradeDirection();
        this.selectionListName = (SelectionList) this.fragmentView.findViewById(R.id.selectionListName);
        this.selectionListName.initItems(SelectionList.PopTypes.SNAME, "name", false);
        this.selectionListCode = (SelectionList) this.fragmentView.findViewById(R.id.selectionListCode);
        this.selectionListCode.initItems(SelectionList.PopTypes.SCODE, "code", false);
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
            updateSelectionListCode(this.bundle.getString("ucode"), this.bundle.getString("uname"));
            this.selectionListName.setEnabled(false);
            this.selectionListCode.setEnabled(false);
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
            imageButton2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Commons.noResumeAction = false;
                    Portfolio_Stocks.this.portfolioFragmentActivity.dataLoading();
                    String selectedText = Portfolio_Stocks.this.selectionListQuantity.getSelectedText();
                    String selectedText2 = Portfolio_Stocks.this.selectionListPrice.getSelectedText();
                    if (selectedText.equals(Commons.text_optional)) {
                        selectedText = "0";
                    }
                    if (selectedText2.equals(Commons.text_optional)) {
                        selectedText2 = "0";
                    }
                    Intent intent = new Intent();
                    intent.putExtra("direction", Portfolio_Stocks.this.selectionListTradeDirection.getSelectedIndex() + "");
                    intent.putExtra("qty", selectedText);
                    intent.putExtra("price", selectedText2);
                    intent.putExtra("row", Portfolio_Stocks.this.bundle.getInt("row"));
                    PortfolioAdd access$000 = Portfolio_Stocks.this.portfolioFragmentActivity;
                    PortfolioAdd unused = Portfolio_Stocks.this.portfolioFragmentActivity;
                    access$000.setResult(-1, intent);
                    Portfolio_Stocks.this.portfolioFragmentActivity.finish();
                }
            });
            return;
        }
        imageButton2.setVisibility(View.GONE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Commons.noResumeAction = false;
                Portfolio_Stocks.this.portfolioFragmentActivity.dataLoading();
                if (!Portfolio_Stocks.this.selectionListCode.getSelectedText().equals("")) {
                    String selectedText = Portfolio_Stocks.this.selectionListQuantity.getSelectedText();
                    String selectedText2 = Portfolio_Stocks.this.selectionListPrice.getSelectedText();
                    if (selectedText.equals(Commons.text_optional)) {
                        selectedText = "0";
                    }
                    if (selectedText2.equals(Commons.text_optional)) {
                        selectedText2 = "0";
                    }
                    PortfolioAdd access$000 = Portfolio_Stocks.this.portfolioFragmentActivity;
                    String selectedText3 = Portfolio_Stocks.this.selectionListCode.getSelectedText();
                    String MapUnderlyingName = Commons.MapUnderlyingName(Portfolio_Stocks.this.selectionListCode.getSelectedText(), false);
                    String MapUnderlyingName2 = Commons.MapUnderlyingName(Portfolio_Stocks.this.selectionListCode.getSelectedText(), true);
                    Portfolio.AddStockToPortfolio(access$000, selectedText3, MapUnderlyingName, MapUnderlyingName2, Portfolio_Stocks.this.selectionListTradeDirection.getSelectedIndex() + "", selectedText, selectedText2);
                    Intent intent = new Intent();
                    intent.putExtra("backtype", "add");
                    PortfolioAdd access$0002 = Portfolio_Stocks.this.portfolioFragmentActivity;
                    PortfolioAdd unused = Portfolio_Stocks.this.portfolioFragmentActivity;
                    access$0002.setResult(-1, intent);
                    Portfolio_Stocks.this.portfolioFragmentActivity.finish();
                }
            }
        });
        updateSelectionListCode(Commons.defaultStockCode, Commons.defaultStockName);
    }

    public void onCreate(Bundle bundle2) {
        super.onCreate(bundle2);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle2) {
        this.fragmentView = layoutInflater.inflate(R.layout.portfoliostocks, viewGroup, false);
        this.portfolioFragmentActivity = (PortfolioAdd) getActivity();
        this.bundle = this.portfolioFragmentActivity.setStocksFragment(this);
        initUI();
        return this.fragmentView;
    }

    public void selectionListCallBack(SelectionList.PopTypes popTypes, String str) {
        switch (popTypes) {
            case SCODE:
                updateSelectionListSCode(str);
                return;
            case SNAME:
                updateSelectionListSCode(str);
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
