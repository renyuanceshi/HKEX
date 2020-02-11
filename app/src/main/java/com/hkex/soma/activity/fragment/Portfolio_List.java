package com.hkex.soma.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.activity.OptionDetail;
import com.hkex.soma.activity.PortfolioAdd;
import com.hkex.soma.activity.Search;
import com.hkex.soma.adapter.Portfolio_Adapter;
import com.hkex.soma.basic.MasterFragmentActivity;
import com.hkex.soma.dataModel.Portfolio_Result;
import com.hkex.soma.element.SelectionDialog;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.interface_changemode;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

import java.text.DecimalFormat;
import java.util.List;

public class Portfolio_List extends ListFragment {
    public Portfolio_Adapter adapter = null;
    /* access modifiers changed from: private */
    public List<Portfolio_Result.mainData> data_array;
    public boolean dragEnabled = true;
    public int dragStartMode = 0;
    /* access modifiers changed from: private */
    public boolean editMode = false;
    private View footerView;
    private boolean island = false;
    private DragSortController mController;
    private DragSortListView mDslv;
    private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
        public void drop(int i, int i2) {
            if (i != i2) {
                try {
                    Portfolio_Result.mainData maindata = (Portfolio_Result.mainData) Portfolio_List.this.adapter.getItem(i);
                    Portfolio_List.this.adapter.remove(maindata);
                    Portfolio_List.this.adapter.insert(maindata, i2);
                } catch (Exception e) {
                    Commons.LogDebug("", e.toString());
                }
            }
        }
    };
    private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
        public void remove(int i) {
            Portfolio_List.this.data_array.remove(i);
            Portfolio_List.this.adapter.notifyDataSetChanged();
        }
    };
    public boolean removeEnabled = false;
    public int removeMode = 1;
    /* access modifiers changed from: private */
    public SelectionDialog sDialog;
    /* access modifiers changed from: private */
    public int selectedindedx = 0;
    public boolean sortEnabled = true;

    /* access modifiers changed from: private */
    public void goOptionDetail(int i) {
        String str = this.data_array.get(i).getType() == "Call" ? "C" : "P";
        Intent intent = new Intent(getActivity(), OptionDetail.class);
        intent.putExtra("oid", this.data_array.get(i).getOid());
        intent.putExtra("ucode", this.data_array.get(i).getUcode());
        intent.putExtra("uname", this.data_array.get(i).getUName());
        intent.putExtra("mdate", this.data_array.get(i).getExpiry());
        intent.putExtra("wtype", str);
        intent.putExtra("strike", this.data_array.get(i).getStrike());
        intent.putExtra("index", "0");
        intent.putExtra("row", i);
        getActivity().startActivityForResult(intent, 0);
    }

    /* access modifiers changed from: private */
    public void goOptionEdit(int i) {
        ((MasterFragmentActivity) getActivity()).dataLoading();
        String str = this.data_array.get(i).getType() == "Call" ? "C" : "P";
        Intent intent = new Intent(getActivity(), PortfolioAdd.class);
        intent.putExtra("EditMode", true);
        intent.putExtra("qty", this.data_array.get(i).getQty());
        intent.putExtra("price", this.data_array.get(i).getPrice());
        intent.putExtra("direction", this.data_array.get(i).getDirection());
        intent.putExtra("oid", this.data_array.get(i).getOid());
        intent.putExtra("ucode", this.data_array.get(i).getUcode());
        if (Commons.language.equals("en_US")) {
            intent.putExtra("uname", this.data_array.get(i).getUName());
        } else {
            intent.putExtra("uname", this.data_array.get(i).getUnmll());
        }
        intent.putExtra("wtype", str);
        intent.putExtra("mdate", this.data_array.get(i).getExpiry());
        intent.putExtra("strike", this.data_array.get(i).getStrike());
        if (this.data_array.get(i).getTypename() != null && this.data_array.get(i).getTypename().equals("stock")) {
            intent.putExtra("index", "0");
        } else if (this.data_array.get(i).getTypename() == null || !this.data_array.get(i).getTypename().equals("index")) {
            intent.putExtra("index", "0");
        } else {
            intent.putExtra("index", "1");
        }
        intent.putExtra("row", i);
        intent.putExtra("isOption", true);
        intent.putExtra("typename", this.data_array.get(i).getTypename());
        getActivity().startActivityForResult(intent, 0);
    }

    /* access modifiers changed from: private */
    public void goStockDetail(int i) {
        Intent intent = new Intent(getActivity(), Search.class);
        intent.putExtra("uname", this.data_array.get(i).getUName());
        intent.putExtra("ucode", this.data_array.get(i).getCode());
        intent.putExtra("index", "2");
        intent.putExtra("row", i);
        getActivity().startActivityForResult(intent, 0);
    }

    /* access modifiers changed from: private */
    public void goStockEdit(int i) {
        Intent intent = new Intent(getActivity(), PortfolioAdd.class);
        intent.putExtra("EditMode", true);
        intent.putExtra("qty", this.data_array.get(i).getQty());
        intent.putExtra("price", this.data_array.get(i).getPrice());
        intent.putExtra("direction", this.data_array.get(i).getDirection());
        if (Commons.language.equals("en_US")) {
            intent.putExtra("uname", this.data_array.get(i).getUName());
        } else {
            intent.putExtra("uname", this.data_array.get(i).getUnmll());
        }
        intent.putExtra("ucode", this.data_array.get(i).getCode());
        intent.putExtra("index", "2");
        intent.putExtra("row", i);
        getActivity().startActivityForResult(intent, 0);
    }

    public DragSortController buildController(DragSortListView dragSortListView) {
        DragSortController dragSortController = new DragSortController(dragSortListView);
        dragSortController.setDragHandleId(R.id.drag_handle);
        dragSortController.setRemoveEnabled(this.removeEnabled);
        dragSortController.setSortEnabled(this.sortEnabled);
        dragSortController.setDragInitMode(this.dragStartMode);
        dragSortController.setRemoveMode(this.removeMode);
        return dragSortController;
    }

    public void calPortfolioTotal() {
        double d;
        double d2;
        TextView textView = (TextView) this.footerView.findViewById(R.id.total);
        TextView textView2 = this.island ? (TextView) this.footerView.findViewById(R.id.margin) : null;
        int i = 0;
        double d3 = 0.0d;
        double d4 = 0.0d;
        while (true) {
            d = d3;
            int i2 = i;
            if (i2 >= this.data_array.size()) {
                break;
            }
            Portfolio_Result.mainData maindata = this.data_array.get(i2);
            try {
                d2 = (double) Float.parseFloat(maindata.getLast().replace(",", ""));
            } catch (Exception e) {
                d2 = 0.0d;
            }
            double parseFloat = maindata.getDirection().equals("0") ? (d2 - ((double) Float.parseFloat(maindata.getPrice().replace(",", "")))) * ((double) Integer.parseInt(maindata.getQty().replace(",", ""))) : (((double) Float.parseFloat(maindata.getPrice().replace(",", ""))) - d2) * ((double) Integer.parseInt(maindata.getQty().replace(",", "")));
            if (maindata.getOid() != null && !maindata.getOid().equals("")) {
                parseFloat = (maindata.getContract() == null || maindata.getContract().equals("")) ? 0.0d : parseFloat * ((double) Integer.parseInt(maindata.getContract().replace(",", "")));
            }
            d3 = parseFloat + d;
            if (Integer.parseInt(maindata.getQty().replace(",", "")) > 0) {
                if (maindata.getTypename() != null && maindata.getTypename().equals("stock")) {
                    d4 += (double) Float.parseFloat((maindata.getMargin() == "null" || maindata.getMargin() == null || maindata.getMargin().equals("")) ? "0" : maindata.getMargin().replace(",", ""));
                } else if (maindata.getDirection().equals("0")) {
                    d4 += (double) Float.parseFloat((maindata.getL_margin() == "null" || maindata.getL_margin() == null || maindata.getL_margin().equals("")) ? "0" : maindata.getL_margin().replace(",", ""));
                } else {
                    d4 += (double) Float.parseFloat((maindata.getS_margin() == "null" || maindata.getS_margin() == null || maindata.getS_margin().equals("")) ? "0" : maindata.getS_margin().replace(",", ""));
                }
            }
            i = i2 + 1;
        }
        int i3 = (d > 0.0d ? 1 : (d == 0.0d ? 0 : -1));
        if (i3 > 0) {
            textView.setTextColor(getResources().getColor(Commons.getUpColorResourceid()));
        } else if (d < 0.0d) {
            textView.setTextColor(getResources().getColor(Commons.getDownColorResourceid()));
        } else {
            textView.setTextColor(getResources().getColor(R.color.textgrey));
        }
        if (i3 != 0) {
            textView.setText(new DecimalFormat("#,###.##").format(d));
        } else if (this.island) {
            textView.setText("--");
        } else {
            textView.setText("--       ");
        }
        if (!this.island) {
            return;
        }
        if (d4 == 0.0d) {
            textView2.setText("--");
        } else {
            textView2.setText(new DecimalFormat("#,###").format(d4));
        }
    }

    public void changeListAdapter(int i) {
        this.editMode = i == R.layout.list_portfolio_edit;
        this.adapter = new Portfolio_Adapter(getActivity(), i, this.data_array);
        setListAdapter(this.adapter);
    }

    public DragSortController getController() {
        return this.mController;
    }

    /* access modifiers changed from: protected */
    public int getLayout() {
        return R.layout.dragsortlistview;
    }

    public String getUpdateString() {
        String str;
        String str2 = "";
        int i = 0;
        while (i < this.data_array.size()) {
            try {
                if (this.data_array.get(i).getUcode() == null || this.data_array.get(i).getUcode().equals("")) {
                    str = str2 + this.data_array.get(i).getCode() + "!" + this.data_array.get(i).getDirection() + "!" + this.data_array.get(i).getQty() + "!" + this.data_array.get(i).getPrice() + ",";
                    i++;
                    str2 = str;
                } else {
                    str = str2 + (this.data_array.get(i).getUcode() + "_" + this.data_array.get(i).getType() + "_" + this.data_array.get(i).getStrike().replace(",", "") + "_" + this.data_array.get(i).getExpiry()) + "!" + this.data_array.get(i).getDirection() + "!" + this.data_array.get(i).getQty() + "!" + this.data_array.get(i).getPrice() + ",";
                    i++;
                    str2 = str;
                }
            } catch (Exception e) {
                Commons.LogDebug("", "" + e.getMessage());
                str = str2;
            }
        }
        return str2;
    }

    public void hidePortfolioTotal() {
        this.footerView.setVisibility(View.GONE);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mDslv = (DragSortListView) getListView();
        this.mDslv.setDropListener(this.onDrop);
        this.mDslv.setRemoveListener(this.onRemove);
        ListView listView = getListView();
        this.footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_portfolio_total, (ViewGroup) null, false);
        this.footerView.setVisibility(View.GONE);
        listView.addFooterView(this.footerView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (((Portfolio_Result.mainData) Portfolio_List.this.data_array.get(i)).getUcode() == null || ((Portfolio_Result.mainData) Portfolio_List.this.data_array.get(i)).getUcode().equals("")) {
                    if (Portfolio_List.this.editMode) {
                        ((Portfolio_Result.mainData) Portfolio_List.this.data_array.get(i)).setSelected(!((Portfolio_Result.mainData) Portfolio_List.this.data_array.get(i)).getSelected());
                        Portfolio_List.this.adapter.notifyDataSetChanged();
                        return;
                    }
                    Portfolio_List.this.goStockDetail(i);
                } else if (Portfolio_List.this.editMode) {
                    ((Portfolio_Result.mainData) Portfolio_List.this.data_array.get(i)).setSelected(!((Portfolio_Result.mainData) Portfolio_List.this.data_array.get(i)).getSelected());
                    Portfolio_List.this.adapter.notifyDataSetChanged();
                } else {
                    Portfolio_List.this.goOptionDetail(i);
                }
            }
        });
        this.sDialog = new SelectionDialog(getActivity(), new String[]{getActivity().getString(R.string.details), getActivity().getString(R.string.edit), getActivity().getString(R.string.delete)});
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (Portfolio_List.this.editMode) {
                    int unused = Portfolio_List.this.selectedindedx = i;
                    Portfolio_List.this.sDialog.show();
                    return true;
                }
                ((interface_changemode) Portfolio_List.this.getActivity()).changeMode("edit");
                int unused2 = Portfolio_List.this.selectedindedx = i;
                Portfolio_List.this.sDialog.show();
                return true;
            }
        });
        this.sDialog.setOnDialogItemClickListener(new SelectionDialog.OnDialogItemClickListener() {
            public void onDialogItemClicked(int i) {
                Portfolio_List.this.sDialog.dismiss();
                if (i == 0) {
                    if (((Portfolio_Result.mainData) Portfolio_List.this.data_array.get(Portfolio_List.this.selectedindedx)).getUcode() == null || ((Portfolio_Result.mainData) Portfolio_List.this.data_array.get(Portfolio_List.this.selectedindedx)).getUcode().equals("")) {
                        Portfolio_List.this.goStockDetail(Portfolio_List.this.selectedindedx);
                    } else {
                        Portfolio_List.this.goOptionDetail(Portfolio_List.this.selectedindedx);
                    }
                } else if (i == 1) {
                    if (((Portfolio_Result.mainData) Portfolio_List.this.data_array.get(Portfolio_List.this.selectedindedx)).getUcode() == null || ((Portfolio_Result.mainData) Portfolio_List.this.data_array.get(Portfolio_List.this.selectedindedx)).getUcode().equals("")) {
                        Portfolio_List.this.goStockEdit(Portfolio_List.this.selectedindedx);
                    } else {
                        Portfolio_List.this.goOptionEdit(Portfolio_List.this.selectedindedx);
                    }
                } else if (i == 2) {
                    Portfolio_List.this.data_array.remove(Portfolio_List.this.selectedindedx);
                    Portfolio_List.this.adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mDslv = (DragSortListView) layoutInflater.inflate(getLayout(), viewGroup, false);
        this.mController = buildController(this.mDslv);
        this.mDslv.setFloatViewManager(this.mController);
        this.mDslv.setOnTouchListener(this.mController);
        this.mDslv.setDragEnabled(this.dragEnabled);
        this.island = getResources().getConfiguration().orientation == 2;
        return this.mDslv;
    }

    public void removeCheckedItem() {
        if (this.adapter != null && this.data_array != null) {
            for (int size = this.data_array.size() - 1; size >= 0; size--) {
                try {
                    if (this.data_array.get(size).getSelected()) {
                        this.data_array.remove(size);
                    }
                } catch (Exception e) {
                    Commons.LogDebug("", "" + e.getMessage());
                }
            }
            this.adapter.notifyDataSetChanged();
        }
    }

    public void setAllItemChecked(boolean z) {
        if (this.adapter != null && this.data_array != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < this.data_array.size()) {
                    try {
                        this.data_array.get(i2).setSelected(z);
                    } catch (Exception e) {
                        Commons.LogDebug("", "" + e.getMessage());
                    }
                    i = i2 + 1;
                } else {
                    this.adapter.notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    public void setDataArray(List<Portfolio_Result.mainData> list, int i) {
        this.editMode = i == R.layout.list_portfolio_edit;
        this.data_array = list;
        this.adapter = new Portfolio_Adapter(getActivity(), i, list);
        setListAdapter(this.adapter);
        showPortfolioTotal();
    }

    public void showPortfolioTotal() {
        if (this.data_array.size() > 0) {
            this.footerView.setVisibility(View.VISIBLE);
            calPortfolioTotal();
            return;
        }
        hidePortfolioTotal();
    }
}
