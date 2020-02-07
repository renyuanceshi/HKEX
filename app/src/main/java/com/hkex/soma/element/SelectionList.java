package com.hkex.soma.element;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.activity.SearchPage;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;

public class SelectionList extends LinearLayout {
    private String[] Items;
    private Context context;
    private DatePickerDialog dateDialog;
    private int index = 0;
    private boolean isEnable = true;
    private OnDateSelectedListener onDateSelectedListener = null;
    private OnLayoutClickListener onLayoutClickListener = null;
    private OnListItemChangeListener onListItemChangeListener = null;
    private SelectionDialog sDialog;
    private View selectionlist;

    public interface OnDateSelectedListener {
        void onDateSelected(String str);
    }

    public interface OnLayoutClickListener {
        void onLayoutClicked();
    }

    public interface OnListItemChangeListener {
        void onListItemChanged(int i);
    }

    public enum PopTypes {
        CODE(0),
        NAME(1),
        NUMBER(2),
        LIST(3),
        DATE(4),
        EXPIRY(5),
        SCODE(6),
        SNAME(7),
        INDEX(8),
        NONE(99);
        
        public final int value;

        private PopTypes(int i) {
            this.value = i;
        }
    }

    public SelectionList(Context context2) {
        super(context2);
        this.context = context2;
        this.selectionlist = ((LayoutInflater) context2.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.selectionlist, this, false);
        addView(this.selectionlist);
    }

    public SelectionList(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        if (!isInEditMode()) {
            this.context = context2;
            this.selectionlist = ((LayoutInflater) context2.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.selectionlist, this, false);
            addView(this.selectionlist);
        }
    }

    public static PopTypes getPopTypes(int i) {
        switch (i) {
            case 0:
                return PopTypes.CODE;
            case 1:
                return PopTypes.NAME;
            case 2:
                return PopTypes.NUMBER;
            case 3:
                return PopTypes.LIST;
            case 4:
                return PopTypes.DATE;
            case 5:
                return PopTypes.EXPIRY;
            case 6:
                return PopTypes.SCODE;
            case 7:
                return PopTypes.SNAME;
            case 8:
                return PopTypes.INDEX;
            default:
                return PopTypes.NONE;
        }
    }

    public int getSelectedIndex() {
        return this.index;
    }

    public String getSelectedItemText() {
        return this.Items[this.index];
    }

    public String getSelectedText() {
        return ((TextView) this.selectionlist.findViewById(R.id.textView)).getText().toString();
    }

    public void initItems(int i, PopTypes popTypes, int i2, int i3, int i4) {
        initLayoutClickListener(popTypes);
        this.dateDialog = new DatePickerDialog(this.context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                String formatDate = StringFormatter.formatDate("dd/MM/yyyy", i, i2, i3);
                SelectionList.this.setSelectedText(formatDate);
                if (SelectionList.this.onDateSelectedListener != null) {
                    SelectionList.this.onDateSelectedListener.onDateSelected(formatDate);
                }
            }
        }, i2, i3 - 1, i4);
    }

    public void initItems(PopTypes popTypes, String str, boolean z) {
        initLayoutClickListener(popTypes, z);
    }

    public void initItems(String[] strArr, int i, PopTypes popTypes, int i2) {
        this.Items = strArr;
        initLayoutClickListener(popTypes);
        this.sDialog = new SelectionDialog(this.context, this.Items);
        if (popTypes == PopTypes.EXPIRY) {
            this.sDialog.isForExpiry();
        }
        if (i > 0) {
            this.sDialog.setTitle(i);
        } else {
            this.sDialog.setTitle(R.string.empty);
        }
        this.sDialog.setOnDialogItemClickListener(new SelectionDialog.OnDialogItemClickListener() {
            public void onDialogItemClicked(int i) {
                SelectionList.this.setSelectedIndex(i);
                SelectionList.this.sDialog.dismiss();
                if (SelectionList.this.onListItemChangeListener != null) {
                    SelectionList.this.onListItemChangeListener.onListItemChanged(i);
                }
            }
        });
        setSelectedIndex(i2);
    }

    public void initLayoutClickListener(final PopTypes popTypes) {
        setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (SelectionList.this.isEnable) {
                    if (popTypes == PopTypes.DATE) {
                        SelectionList.this.dateDialog.show();
                    } else {
                        SelectionList.this.sDialog.show();
                    }
                    if (SelectionList.this.onLayoutClickListener != null) {
                        SelectionList.this.onLayoutClickListener.onLayoutClicked();
                    }
                }
            }
        });
    }

    public void initLayoutClickListener(final PopTypes popTypes, final boolean z) {
        setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (SelectionList.this.isEnable) {
                    SelectionList.this.setGreyStyle(false);
                    Intent intent = new Intent();
                    intent.setClass(SelectionList.this.context, SearchPage.class);
                    intent.putExtra("typecode", popTypes.value);
                    intent.putExtra("isAutocomplete", z);
                    ((FragmentActivity) SelectionList.this.context).startActivityForResult(intent, 0);
                    if (SelectionList.this.onLayoutClickListener != null) {
                        SelectionList.this.onLayoutClickListener.onLayoutClicked();
                    }
                }
            }
        });
    }

    public void setEnabled(boolean z) {
        this.isEnable = z;
        ImageView imageView = (ImageView) this.selectionlist.findViewById(R.id.selectListArrow);
        if (!z) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    public void setGreyStyle(boolean z) {
        TextView textView = (TextView) this.selectionlist.findViewById(R.id.textView);
        ImageView imageView = (ImageView) this.selectionlist.findViewById(R.id.selectListArrow);
        if (z) {
            textView.setTextColor(this.context.getResources().getColor(R.color.textbluegrey));
            imageView.setImageResource(R.drawable.list_arrow_off);
            return;
        }
        textView.setTextColor(this.context.getResources().getColor(R.color.textwhite));
        imageView.setImageResource(R.drawable.list_arrow);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener2) {
        this.onDateSelectedListener = onDateSelectedListener2;
    }

    public void setOnLayoutClickListener(OnLayoutClickListener onLayoutClickListener2) {
        this.onLayoutClickListener = onLayoutClickListener2;
    }

    public void setOnListItemChangeListener(OnListItemChangeListener onListItemChangeListener2) {
        this.onListItemChangeListener = onListItemChangeListener2;
    }

    public void setPrefixText(String str) {
        ((TextView) this.selectionlist.findViewById(R.id.prefixtext)).setText(str);
    }

    public void setSelectedIndex(int i) {
        ((TextView) this.selectionlist.findViewById(R.id.textView)).setText(this.Items[i]);
        this.sDialog.setAdapterSelectedId(i);
        this.index = i;
    }

    public void setSelectedText(String str) {
        TextView textView = (TextView) this.selectionlist.findViewById(R.id.prefixtext);
        TextView textView2 = (TextView) this.selectionlist.findViewById(R.id.textView);
        if (str.equals(Commons.text_optional)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
        textView2.setText(str);
    }

    public void setselected(int i) {
        if (this.onListItemChangeListener != null) {
            this.onListItemChangeListener.onListItemChanged(i);
        }
        setSelectedIndex(i);
    }
}
