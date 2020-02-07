package com.hkex.soma.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.hkex.soma.dataModel.Portfolio_Result;
import java.text.DecimalFormat;
import java.util.List;

public class Portfolio_Adapter extends ArrayAdapter<Portfolio_Result.mainData> {
    private Context context;
    DecimalFormat formatter = new DecimalFormat("#,###.##");
    private boolean island = false;
    private int itemLayout;

    static class ViewHolder {
        private CheckBox checkBox;
        private TextView contract;
        private TextView cost;
        private TextView expiry;
        private TextView gainloss;
        private TextView instrument1;
        private TextView instrument2;
        private TextView last;
        private TextView margin;
        private TextView notional;
        private TextView qty;

        ViewHolder() {
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public Portfolio_Adapter(Context context2, int i, List<Portfolio_Result.mainData> list) {
        super(context2, i, list);
        boolean z = false;
        this.itemLayout = i;
        this.context = context2;
        this.island = context2.getResources().getConfiguration().orientation == 2 ? true : z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:67:0x01fa A[Catch:{ Exception -> 0x0387 }] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0211 A[Catch:{ Exception -> 0x0387 }] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0235 A[Catch:{ Exception -> 0x0617 }] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0251 A[Catch:{ Exception -> 0x0617 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r13, android.view.View r14, android.view.ViewGroup r15) {
        /*
            r12 = this;
            java.lang.Object r0 = r12.getItem(r13)
            com.hkex.soma.dataModel.Portfolio_Result$mainData r0 = (com.hkex.soma.dataModel.Portfolio_Result.mainData) r0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "rowItem "
            r1.append(r2)
            java.lang.String r2 = r0.getCode()
            r1.append(r2)
            java.lang.String r2 = "Portfolio_Adapter"
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r2, r1)
            android.content.Context r1 = r12.context     // Catch:{ Exception -> 0x06d2 }
            java.lang.String r2 = "layout_inflater"
            java.lang.Object r1 = r1.getSystemService(r2)     // Catch:{ Exception -> 0x06d2 }
            android.view.LayoutInflater r1 = (android.view.LayoutInflater) r1     // Catch:{ Exception -> 0x06d2 }
            if (r14 != 0) goto L_0x0346
            int r2 = r12.itemLayout     // Catch:{ Exception -> 0x06d2 }
            r3 = 0
            android.view.View r14 = r1.inflate(r2, r3)     // Catch:{ Exception -> 0x06d2 }
            com.hkex.soma.adapter.Portfolio_Adapter$ViewHolder r2 = new com.hkex.soma.adapter.Portfolio_Adapter$ViewHolder     // Catch:{ Exception -> 0x06d5 }
            r2.<init>()     // Catch:{ Exception -> 0x06d5 }
            int r1 = r12.itemLayout     // Catch:{ Exception -> 0x06d5 }
            r3 = 2131296313(0x7f090039, float:1.821054E38)
            if (r1 != r3) goto L_0x004b
            r1 = 2131165284(0x7f070064, float:1.794478E38)
            android.view.View r1 = r14.findViewById(r1)     // Catch:{ Exception -> 0x06d5 }
            android.widget.CheckBox r1 = (android.widget.CheckBox) r1     // Catch:{ Exception -> 0x06d5 }
            android.widget.CheckBox unused = r2.checkBox = r1     // Catch:{ Exception -> 0x06d5 }
        L_0x004b:
            boolean r1 = r12.island     // Catch:{ Exception -> 0x06d5 }
            if (r1 == 0) goto L_0x007f
            r1 = 2131165343(0x7f07009f, float:1.79449E38)
            android.view.View r1 = r14.findViewById(r1)     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView unused = r2.expiry = r1     // Catch:{ Exception -> 0x06d5 }
            r1 = 2131165455(0x7f07010f, float:1.7945128E38)
            android.view.View r1 = r14.findViewById(r1)     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView unused = r2.margin = r1     // Catch:{ Exception -> 0x06d5 }
            r1 = 2131165301(0x7f070075, float:1.7944815E38)
            android.view.View r1 = r14.findViewById(r1)     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView unused = r2.contract = r1     // Catch:{ Exception -> 0x06d5 }
            r1 = 2131165496(0x7f070138, float:1.794521E38)
            android.view.View r1 = r14.findViewById(r1)     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView unused = r2.notional = r1     // Catch:{ Exception -> 0x06d5 }
        L_0x007f:
            r1 = 2131165416(0x7f0700e8, float:1.7945048E38)
            android.view.View r1 = r14.findViewById(r1)     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView unused = r2.instrument1 = r1     // Catch:{ Exception -> 0x06d5 }
            r1 = 2131165417(0x7f0700e9, float:1.794505E38)
            android.view.View r1 = r14.findViewById(r1)     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView unused = r2.instrument2 = r1     // Catch:{ Exception -> 0x06d5 }
            r1 = 2131165423(0x7f0700ef, float:1.7945063E38)
            android.view.View r1 = r14.findViewById(r1)     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView unused = r2.last = r1     // Catch:{ Exception -> 0x06d5 }
            r1 = 2131165557(0x7f070175, float:1.7945334E38)
            android.view.View r1 = r14.findViewById(r1)     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView unused = r2.qty = r1     // Catch:{ Exception -> 0x06d5 }
            r1 = 2131165304(0x7f070078, float:1.7944821E38)
            android.view.View r1 = r14.findViewById(r1)     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView unused = r2.cost = r1     // Catch:{ Exception -> 0x06d5 }
            r1 = 2131165357(0x7f0700ad, float:1.7944929E38)
            android.view.View r1 = r14.findViewById(r1)     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ Exception -> 0x06d5 }
            android.widget.TextView unused = r2.gainloss = r1     // Catch:{ Exception -> 0x06d5 }
            r14.setTag(r2)     // Catch:{ Exception -> 0x06d5 }
            r6 = r2
        L_0x00cb:
            java.lang.String r1 = com.hkex.soma.utils.Commons.language     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = "en_US"
            boolean r1 = r1.equals(r2)     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x0391
            java.lang.String r1 = r0.getCode()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = "_Call_"
            int r1 = r1.indexOf(r2)     // Catch:{ Exception -> 0x0387 }
            if (r1 > 0) goto L_0x00ed
            java.lang.String r1 = r0.getCode()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = "_Put_"
            int r1 = r1.indexOf(r2)     // Catch:{ Exception -> 0x0387 }
            if (r1 <= 0) goto L_0x034f
        L_0x00ed:
            android.widget.TextView r1 = r6.instrument1     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = r0.getUName()     // Catch:{ Exception -> 0x0387 }
            r1.setText(r2)     // Catch:{ Exception -> 0x0387 }
            android.widget.TextView r1 = r6.instrument2     // Catch:{ Exception -> 0x0387 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0387 }
            r2.<init>()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = r0.getStrike()     // Catch:{ Exception -> 0x0387 }
            r2.append(r3)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = " "
            r2.append(r3)     // Catch:{ Exception -> 0x0387 }
            android.content.Context r3 = r12.context     // Catch:{ Exception -> 0x0387 }
            java.lang.String r4 = r0.getType()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = com.hkex.soma.utils.Commons.callputText(r3, r4)     // Catch:{ Exception -> 0x0387 }
            r2.append(r3)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0387 }
            r1.setText(r2)     // Catch:{ Exception -> 0x0387 }
        L_0x0121:
            android.widget.CheckBox r1 = r6.checkBox     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x0137
            android.widget.CheckBox r1 = r6.checkBox     // Catch:{ Exception -> 0x0387 }
            android.widget.CheckBox r2 = r6.checkBox     // Catch:{ Exception -> 0x0387 }
            com.hkex.soma.adapter.Portfolio_Adapter$1 r3 = new com.hkex.soma.adapter.Portfolio_Adapter$1     // Catch:{ Exception -> 0x0387 }
            r3.<init>(r0, r1)     // Catch:{ Exception -> 0x0387 }
            r2.setOnClickListener(r3)     // Catch:{ Exception -> 0x0387 }
        L_0x0137:
            java.lang.String r1 = r0.getLast()     // Catch:{ Exception -> 0x041c }
            java.lang.String r2 = ","
            java.lang.String r3 = ""
            java.lang.String r1 = r1.replace(r2, r3)     // Catch:{ Exception -> 0x041c }
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x041c }
            double r2 = (double) r1
            r4 = r2
        L_0x0149:
            android.widget.TextView r2 = r6.last     // Catch:{ Exception -> 0x0387 }
            java.lang.String r1 = r0.getLast()     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x015f
            java.lang.String r1 = r0.getLast()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = ""
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x0422
        L_0x015f:
            java.lang.String r1 = "--"
        L_0x0161:
            r2.setText(r1)     // Catch:{ Exception -> 0x0387 }
            android.widget.TextView r2 = r6.qty     // Catch:{ Exception -> 0x0387 }
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x0387 }
            if (r1 != 0) goto L_0x0428
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x0387 }
        L_0x0172:
            r2.setText(r1)     // Catch:{ Exception -> 0x0387 }
            boolean r1 = r12.island     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x0279
            java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x0387 }
            java.lang.String r1 = "yyyy-MM-dd"
            r2.<init>(r1)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r1 = r0.getExpiry_str()     // Catch:{ Exception -> 0x0444 }
            if (r1 == 0) goto L_0x0192
            java.lang.String r1 = r0.getExpiry_str()     // Catch:{ Exception -> 0x0444 }
            java.lang.String r3 = ""
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0444 }
            if (r1 == 0) goto L_0x042c
        L_0x0192:
            java.lang.String r1 = r0.getExpiry()     // Catch:{ Exception -> 0x0444 }
        L_0x0196:
            java.util.Date r2 = r2.parse(r1)     // Catch:{ Exception -> 0x0444 }
            int r1 = r1.length()     // Catch:{ Exception -> 0x0444 }
            r3 = 7
            if (r1 <= r3) goto L_0x0432
            android.widget.TextView r1 = r6.expiry     // Catch:{ Exception -> 0x0444 }
            r3 = 1
            android.content.Context r7 = r12.getContext()     // Catch:{ Exception -> 0x0444 }
            java.lang.String r2 = com.hkex.soma.utils.StringFormatter.formatDatetime(r2, r3, r7)     // Catch:{ Exception -> 0x0444 }
            r1.setText(r2)     // Catch:{ Exception -> 0x0444 }
        L_0x01b1:
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x049b }
            java.lang.String r2 = ","
            java.lang.String r3 = ""
            java.lang.String r1 = r1.replace(r2, r3)     // Catch:{ Exception -> 0x049b }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Exception -> 0x049b }
            if (r1 <= 0) goto L_0x04d1
            java.lang.String r1 = r0.getTypename()     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x0472
            java.lang.String r1 = r0.getTypename()     // Catch:{ Exception -> 0x049b }
            java.lang.String r2 = "stock"
            boolean r1 = r1.equals(r2)     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x0472
            android.widget.TextView r2 = r6.margin     // Catch:{ Exception -> 0x049b }
            java.lang.String r1 = r0.getMargin()     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x01eb
            java.lang.String r1 = r0.getMargin()     // Catch:{ Exception -> 0x049b }
            java.lang.String r3 = ""
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x046c
        L_0x01eb:
            java.lang.String r1 = "--"
        L_0x01ed:
            r2.setText(r1)     // Catch:{ Exception -> 0x049b }
        L_0x01f0:
            android.widget.TextView r2 = r6.contract     // Catch:{ Exception -> 0x0387 }
            java.lang.String r1 = r0.getContract()     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x0206
            java.lang.String r1 = r0.getContract()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = ""
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x054f
        L_0x0206:
            java.lang.String r1 = "--"
        L_0x0208:
            r2.setText(r1)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r1 = r0.getOid()     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x021d
            java.lang.String r1 = r0.getOid()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = ""
            boolean r1 = r1.equals(r2)     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x0555
        L_0x021d:
            android.widget.TextView r2 = r6.notional     // Catch:{ Exception -> 0x0617 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0617 }
            r3.<init>()     // Catch:{ Exception -> 0x0617 }
            java.lang.String r1 = ""
            r3.append(r1)     // Catch:{ Exception -> 0x0617 }
            java.text.DecimalFormat r7 = r12.formatter     // Catch:{ Exception -> 0x0617 }
            java.lang.String r1 = r0.getLast()     // Catch:{ Exception -> 0x0617 }
            java.lang.String r8 = "null"
            if (r1 == r8) goto L_0x0243
            java.lang.String r1 = r0.getLast()     // Catch:{ Exception -> 0x0617 }
            if (r1 == 0) goto L_0x0243
            java.lang.String r1 = r0.getLast()     // Catch:{ Exception -> 0x0617 }
            java.lang.String r8 = ""
            if (r1 != r8) goto L_0x0603
        L_0x0243:
            java.lang.String r1 = "0"
        L_0x0245:
            float r8 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x0617 }
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x0617 }
            java.lang.String r9 = "null"
            if (r1 == r9) goto L_0x025f
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x0617 }
            if (r1 == 0) goto L_0x025f
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x0617 }
            java.lang.String r9 = ""
            if (r1 != r9) goto L_0x0611
        L_0x025f:
            java.lang.String r1 = "0"
        L_0x0261:
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x0617 }
            float r1 = r1 * r8
            int r1 = java.lang.Math.round(r1)     // Catch:{ Exception -> 0x0617 }
            long r8 = (long) r1     // Catch:{ Exception -> 0x0617 }
            java.lang.String r1 = r7.format(r8)     // Catch:{ Exception -> 0x0617 }
            r3.append(r1)     // Catch:{ Exception -> 0x0617 }
            java.lang.String r1 = r3.toString()     // Catch:{ Exception -> 0x0617 }
            r2.setText(r1)     // Catch:{ Exception -> 0x0617 }
        L_0x0279:
            int r1 = r12.itemLayout     // Catch:{ Exception -> 0x0639 }
            r2 = 2131296314(0x7f09003a, float:1.8210541E38)
            if (r1 == r2) goto L_0x02b4
            java.lang.String r1 = r0.getDirection()     // Catch:{ Exception -> 0x0639 }
            java.lang.String r2 = "0"
            boolean r1 = r1.equals(r2)     // Catch:{ Exception -> 0x0639 }
            if (r1 == 0) goto L_0x0623
            java.lang.String r1 = r0.getPrice()     // Catch:{ Exception -> 0x0639 }
            java.lang.String r2 = ","
            java.lang.String r3 = ""
            java.lang.String r1 = r1.replace(r2, r3)     // Catch:{ Exception -> 0x0639 }
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x0639 }
            double r2 = (double) r1     // Catch:{ Exception -> 0x0639 }
        L_0x029d:
            android.widget.TextView r1 = r6.cost     // Catch:{ Exception -> 0x0639 }
            java.lang.String r7 = "%.3f"
            r8 = 1
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x0639 }
            r9 = 0
            java.lang.Double r2 = java.lang.Double.valueOf(r2)     // Catch:{ Exception -> 0x0639 }
            r8[r9] = r2     // Catch:{ Exception -> 0x0639 }
            java.lang.String r2 = java.lang.String.format(r7, r8)     // Catch:{ Exception -> 0x0639 }
            r1.setText(r2)     // Catch:{ Exception -> 0x0639 }
        L_0x02b4:
            java.lang.String r1 = r0.getDirection()     // Catch:{ Exception -> 0x069c }
            java.lang.String r2 = "0"
            boolean r1 = r1.equals(r2)     // Catch:{ Exception -> 0x069c }
            if (r1 == 0) goto L_0x0645
            java.lang.String r1 = r0.getPrice()     // Catch:{ Exception -> 0x069c }
            java.lang.String r2 = ","
            java.lang.String r3 = ""
            java.lang.String r1 = r1.replace(r2, r3)     // Catch:{ Exception -> 0x069c }
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x069c }
            double r2 = (double) r1     // Catch:{ Exception -> 0x069c }
            double r2 = r4 - r2
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x069c }
            java.lang.String r4 = ","
            java.lang.String r5 = ""
            java.lang.String r1 = r1.replace(r4, r5)     // Catch:{ Exception -> 0x069c }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Exception -> 0x069c }
            double r4 = (double) r1     // Catch:{ Exception -> 0x069c }
            double r2 = r2 * r4
        L_0x02e5:
            java.lang.String r1 = r0.getOid()     // Catch:{ Exception -> 0x069c }
            if (r1 == 0) goto L_0x030b
            java.lang.String r1 = r0.getOid()     // Catch:{ Exception -> 0x069c }
            java.lang.String r4 = ""
            boolean r1 = r1.equals(r4)     // Catch:{ Exception -> 0x069c }
            if (r1 != 0) goto L_0x030b
            java.lang.String r1 = r0.getContract()     // Catch:{ Exception -> 0x069c }
            if (r1 == 0) goto L_0x0309
            java.lang.String r1 = r0.getContract()     // Catch:{ Exception -> 0x069c }
            java.lang.String r4 = ""
            boolean r1 = r1.equals(r4)     // Catch:{ Exception -> 0x069c }
            if (r1 == 0) goto L_0x066b
        L_0x0309:
            r2 = 0
        L_0x030b:
            r4 = 0
            int r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r1 <= 0) goto L_0x067f
            android.widget.TextView r1 = r6.gainloss     // Catch:{ Exception -> 0x069c }
            android.content.Context r4 = r12.context     // Catch:{ Exception -> 0x069c }
            android.content.res.Resources r4 = r4.getResources()     // Catch:{ Exception -> 0x069c }
            int r5 = com.hkex.soma.utils.Commons.getUpColorResourceid()     // Catch:{ Exception -> 0x069c }
            int r4 = r4.getColor(r5)     // Catch:{ Exception -> 0x069c }
            r1.setTextColor(r4)     // Catch:{ Exception -> 0x069c }
        L_0x0326:
            android.widget.TextView r1 = r6.gainloss     // Catch:{ Exception -> 0x069c }
            java.text.DecimalFormat r4 = r12.formatter     // Catch:{ Exception -> 0x069c }
            java.lang.String r2 = r4.format(r2)     // Catch:{ Exception -> 0x069c }
            r1.setText(r2)     // Catch:{ Exception -> 0x069c }
        L_0x0333:
            int r1 = r12.itemLayout     // Catch:{ Exception -> 0x0387 }
            r2 = 2131296313(0x7f090039, float:1.821054E38)
            if (r1 != r2) goto L_0x0345
            android.widget.CheckBox r1 = r6.checkBox     // Catch:{ Exception -> 0x0387 }
            boolean r0 = r0.getSelected()     // Catch:{ Exception -> 0x0387 }
            r1.setChecked(r0)     // Catch:{ Exception -> 0x0387 }
        L_0x0345:
            return r14
        L_0x0346:
            java.lang.Object r1 = r14.getTag()     // Catch:{ Exception -> 0x06d2 }
            com.hkex.soma.adapter.Portfolio_Adapter$ViewHolder r1 = (com.hkex.soma.adapter.Portfolio_Adapter.ViewHolder) r1     // Catch:{ Exception -> 0x06d2 }
            r6 = r1
            goto L_0x00cb
        L_0x034f:
            android.widget.TextView r1 = r6.instrument1     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = r0.getUName()     // Catch:{ Exception -> 0x0387 }
            r1.setText(r2)     // Catch:{ Exception -> 0x0387 }
            android.widget.TextView r2 = r6.instrument2     // Catch:{ Exception -> 0x0387 }
            java.lang.String r1 = r0.getCode()     // Catch:{ Exception -> 0x0387 }
            boolean r1 = com.hkex.soma.utils.Commons.isNumeric(r1)     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x038c
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0387 }
            r1.<init>()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = "("
            r1.append(r3)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = r0.getCode()     // Catch:{ Exception -> 0x0387 }
            r1.append(r3)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = ")"
            r1.append(r3)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0387 }
        L_0x0382:
            r2.setText(r1)     // Catch:{ Exception -> 0x0387 }
            goto L_0x0121
        L_0x0387:
            r0 = move-exception
        L_0x0388:
            r0.printStackTrace()
            goto L_0x0345
        L_0x038c:
            java.lang.String r1 = r0.getCode()     // Catch:{ Exception -> 0x0387 }
            goto L_0x0382
        L_0x0391:
            java.lang.String r1 = r0.getCode()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = "_Call_"
            int r1 = r1.indexOf(r2)     // Catch:{ Exception -> 0x0387 }
            if (r1 > 0) goto L_0x03a9
            java.lang.String r1 = r0.getCode()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = "_Put_"
            int r1 = r1.indexOf(r2)     // Catch:{ Exception -> 0x0387 }
            if (r1 <= 0) goto L_0x03df
        L_0x03a9:
            android.widget.TextView r1 = r6.instrument1     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = r0.getUnmll()     // Catch:{ Exception -> 0x0387 }
            r1.setText(r2)     // Catch:{ Exception -> 0x0387 }
            android.widget.TextView r1 = r6.instrument2     // Catch:{ Exception -> 0x0387 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0387 }
            r2.<init>()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = r0.getStrike()     // Catch:{ Exception -> 0x0387 }
            r2.append(r3)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = " "
            r2.append(r3)     // Catch:{ Exception -> 0x0387 }
            android.content.Context r3 = r12.context     // Catch:{ Exception -> 0x0387 }
            java.lang.String r4 = r0.getType()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = com.hkex.soma.utils.Commons.callputText(r3, r4)     // Catch:{ Exception -> 0x0387 }
            r2.append(r3)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0387 }
            r1.setText(r2)     // Catch:{ Exception -> 0x0387 }
            goto L_0x0121
        L_0x03df:
            android.widget.TextView r1 = r6.instrument1     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = r0.getUnmll()     // Catch:{ Exception -> 0x0387 }
            r1.setText(r2)     // Catch:{ Exception -> 0x0387 }
            android.widget.TextView r2 = r6.instrument2     // Catch:{ Exception -> 0x0387 }
            java.lang.String r1 = r0.getCode()     // Catch:{ Exception -> 0x0387 }
            boolean r1 = com.hkex.soma.utils.Commons.isNumeric(r1)     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x0417
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0387 }
            r1.<init>()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = "("
            r1.append(r3)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = r0.getCode()     // Catch:{ Exception -> 0x0387 }
            r1.append(r3)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = ")"
            r1.append(r3)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0387 }
        L_0x0412:
            r2.setText(r1)     // Catch:{ Exception -> 0x0387 }
            goto L_0x0121
        L_0x0417:
            java.lang.String r1 = r0.getCode()     // Catch:{ Exception -> 0x0387 }
            goto L_0x0412
        L_0x041c:
            r1 = move-exception
            r2 = 0
            r4 = r2
            goto L_0x0149
        L_0x0422:
            java.lang.String r1 = r0.getLast()     // Catch:{ Exception -> 0x0387 }
            goto L_0x0161
        L_0x0428:
            java.lang.String r1 = "0"
            goto L_0x0172
        L_0x042c:
            java.lang.String r1 = r0.getExpiry_str()     // Catch:{ Exception -> 0x0444 }
            goto L_0x0196
        L_0x0432:
            android.widget.TextView r1 = r6.expiry     // Catch:{ Exception -> 0x0444 }
            r3 = 2
            android.content.Context r7 = r12.getContext()     // Catch:{ Exception -> 0x0444 }
            java.lang.String r2 = com.hkex.soma.utils.StringFormatter.formatDatetime(r2, r3, r7)     // Catch:{ Exception -> 0x0444 }
            r1.setText(r2)     // Catch:{ Exception -> 0x0444 }
            goto L_0x01b1
        L_0x0444:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ Exception -> 0x0387 }
            android.widget.TextView r2 = r6.expiry     // Catch:{ Exception -> 0x0387 }
            java.lang.String r1 = r0.getExpiry_str()     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x045e
            java.lang.String r1 = r0.getExpiry_str()     // Catch:{ Exception -> 0x0387 }
            java.lang.String r3 = ""
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0387 }
            if (r1 == 0) goto L_0x0467
        L_0x045e:
            java.lang.String r1 = r0.getExpiry()     // Catch:{ Exception -> 0x0387 }
        L_0x0462:
            r2.setText(r1)     // Catch:{ Exception -> 0x0387 }
            goto L_0x01b1
        L_0x0467:
            java.lang.String r1 = r0.getExpiry_str()     // Catch:{ Exception -> 0x0387 }
            goto L_0x0462
        L_0x046c:
            java.lang.String r1 = r0.getMargin()     // Catch:{ Exception -> 0x049b }
            goto L_0x01ed
        L_0x0472:
            java.lang.String r1 = r0.getDirection()     // Catch:{ Exception -> 0x049b }
            java.lang.String r2 = "0"
            boolean r1 = r1.equals(r2)     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x04af
            android.widget.TextView r2 = r6.margin     // Catch:{ Exception -> 0x049b }
            java.lang.String r1 = r0.getL_margin()     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x0494
            java.lang.String r1 = r0.getL_margin()     // Catch:{ Exception -> 0x049b }
            java.lang.String r3 = ""
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x04aa
        L_0x0494:
            java.lang.String r1 = "--"
        L_0x0496:
            r2.setText(r1)     // Catch:{ Exception -> 0x049b }
            goto L_0x01f0
        L_0x049b:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ Exception -> 0x0387 }
            android.widget.TextView r1 = r6.margin     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = "--"
            r1.setText(r2)     // Catch:{ Exception -> 0x0387 }
            goto L_0x01f0
        L_0x04aa:
            java.lang.String r1 = r0.getL_margin()     // Catch:{ Exception -> 0x049b }
            goto L_0x0496
        L_0x04af:
            android.widget.TextView r2 = r6.margin     // Catch:{ Exception -> 0x049b }
            java.lang.String r1 = r0.getS_margin()     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x04c5
            java.lang.String r1 = r0.getS_margin()     // Catch:{ Exception -> 0x049b }
            java.lang.String r3 = ""
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x04cc
        L_0x04c5:
            java.lang.String r1 = "--"
        L_0x04c7:
            r2.setText(r1)     // Catch:{ Exception -> 0x049b }
            goto L_0x01f0
        L_0x04cc:
            java.lang.String r1 = r0.getS_margin()     // Catch:{ Exception -> 0x049b }
            goto L_0x04c7
        L_0x04d1:
            java.lang.String r1 = r0.getTypename()     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x0503
            java.lang.String r1 = r0.getTypename()     // Catch:{ Exception -> 0x049b }
            java.lang.String r2 = "stock"
            boolean r1 = r1.equals(r2)     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x0503
            android.widget.TextView r2 = r6.margin     // Catch:{ Exception -> 0x049b }
            java.lang.String r1 = r0.getMargin()     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x04f9
            java.lang.String r1 = r0.getMargin()     // Catch:{ Exception -> 0x049b }
            java.lang.String r3 = ""
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x0500
        L_0x04f9:
            java.lang.String r1 = "--"
        L_0x04fb:
            r2.setText(r1)     // Catch:{ Exception -> 0x049b }
            goto L_0x01f0
        L_0x0500:
            java.lang.String r1 = "0"
            goto L_0x04fb
        L_0x0503:
            java.lang.String r1 = r0.getDirection()     // Catch:{ Exception -> 0x049b }
            java.lang.String r2 = "0"
            boolean r1 = r1.equals(r2)     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x052f
            android.widget.TextView r2 = r6.margin     // Catch:{ Exception -> 0x049b }
            java.lang.String r1 = r0.getL_margin()     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x0525
            java.lang.String r1 = r0.getL_margin()     // Catch:{ Exception -> 0x049b }
            java.lang.String r3 = ""
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x052c
        L_0x0525:
            java.lang.String r1 = "--"
        L_0x0527:
            r2.setText(r1)     // Catch:{ Exception -> 0x049b }
            goto L_0x01f0
        L_0x052c:
            java.lang.String r1 = "0"
            goto L_0x0527
        L_0x052f:
            android.widget.TextView r2 = r6.margin     // Catch:{ Exception -> 0x049b }
            java.lang.String r1 = r0.getS_margin()     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x0545
            java.lang.String r1 = r0.getS_margin()     // Catch:{ Exception -> 0x049b }
            java.lang.String r3 = ""
            boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x049b }
            if (r1 == 0) goto L_0x054c
        L_0x0545:
            java.lang.String r1 = "--"
        L_0x0547:
            r2.setText(r1)     // Catch:{ Exception -> 0x049b }
            goto L_0x01f0
        L_0x054c:
            java.lang.String r1 = "0"
            goto L_0x0547
        L_0x054f:
            java.lang.String r1 = r0.getContract()     // Catch:{ Exception -> 0x0387 }
            goto L_0x0208
        L_0x0555:
            android.widget.TextView r2 = r6.notional     // Catch:{ Exception -> 0x05d0 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05d0 }
            r3.<init>()     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r1 = ""
            r3.append(r1)     // Catch:{ Exception -> 0x05d0 }
            java.text.DecimalFormat r7 = r12.formatter     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r1 = r0.getStrike()     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r8 = "null"
            if (r1 == r8) goto L_0x057b
            java.lang.String r1 = r0.getStrike()     // Catch:{ Exception -> 0x05d0 }
            if (r1 == 0) goto L_0x057b
            java.lang.String r1 = r0.getStrike()     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r8 = ""
            if (r1 != r8) goto L_0x05dc
        L_0x057b:
            java.lang.String r1 = "0"
        L_0x057d:
            float r8 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r1 = r0.getContract()     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r9 = "null"
            if (r1 == r9) goto L_0x0597
            java.lang.String r1 = r0.getContract()     // Catch:{ Exception -> 0x05d0 }
            if (r1 == 0) goto L_0x0597
            java.lang.String r1 = r0.getContract()     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r9 = ""
            if (r1 != r9) goto L_0x05e9
        L_0x0597:
            java.lang.String r1 = "0"
        L_0x0599:
            float r9 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r10 = "null"
            if (r1 == r10) goto L_0x05b3
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x05d0 }
            if (r1 == 0) goto L_0x05b3
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r10 = ""
            if (r1 != r10) goto L_0x05f6
        L_0x05b3:
            java.lang.String r1 = "0"
        L_0x05b5:
            float r8 = r8 * r9
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x05d0 }
            float r1 = r1 * r8
            int r1 = java.lang.Math.round(r1)     // Catch:{ Exception -> 0x05d0 }
            long r8 = (long) r1     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r1 = r7.format(r8)     // Catch:{ Exception -> 0x05d0 }
            r3.append(r1)     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r1 = r3.toString()     // Catch:{ Exception -> 0x05d0 }
            r2.setText(r1)     // Catch:{ Exception -> 0x05d0 }
            goto L_0x0279
        L_0x05d0:
            r1 = move-exception
            android.widget.TextView r1 = r6.notional     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = "0"
            r1.setText(r2)     // Catch:{ Exception -> 0x0387 }
            goto L_0x0279
        L_0x05dc:
            java.lang.String r1 = r0.getStrike()     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r8 = ","
            java.lang.String r9 = ""
            java.lang.String r1 = r1.replace(r8, r9)     // Catch:{ Exception -> 0x05d0 }
            goto L_0x057d
        L_0x05e9:
            java.lang.String r1 = r0.getContract()     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r9 = ","
            java.lang.String r10 = ""
            java.lang.String r1 = r1.replace(r9, r10)     // Catch:{ Exception -> 0x05d0 }
            goto L_0x0599
        L_0x05f6:
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x05d0 }
            java.lang.String r10 = ","
            java.lang.String r11 = ""
            java.lang.String r1 = r1.replace(r10, r11)     // Catch:{ Exception -> 0x05d0 }
            goto L_0x05b5
        L_0x0603:
            java.lang.String r1 = r0.getLast()     // Catch:{ Exception -> 0x0617 }
            java.lang.String r8 = ","
            java.lang.String r9 = ""
            java.lang.String r1 = r1.replace(r8, r9)     // Catch:{ Exception -> 0x0617 }
            goto L_0x0245
        L_0x0611:
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x0617 }
            goto L_0x0261
        L_0x0617:
            r1 = move-exception
            android.widget.TextView r1 = r6.notional     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = "0"
            r1.setText(r2)     // Catch:{ Exception -> 0x0387 }
            goto L_0x0279
        L_0x0623:
            java.lang.String r1 = r0.getPrice()     // Catch:{ Exception -> 0x0639 }
            java.lang.String r2 = ","
            java.lang.String r3 = ""
            java.lang.String r1 = r1.replace(r2, r3)     // Catch:{ Exception -> 0x0639 }
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x0639 }
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r1 = r1 * r2
            double r2 = (double) r1
            goto L_0x029d
        L_0x0639:
            r1 = move-exception
            android.widget.TextView r1 = r6.cost     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = "--"
            r1.setText(r2)     // Catch:{ Exception -> 0x0387 }
            goto L_0x02b4
        L_0x0645:
            java.lang.String r1 = r0.getPrice()     // Catch:{ Exception -> 0x069c }
            java.lang.String r2 = ","
            java.lang.String r3 = ""
            java.lang.String r1 = r1.replace(r2, r3)     // Catch:{ Exception -> 0x069c }
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x069c }
            double r2 = (double) r1     // Catch:{ Exception -> 0x069c }
            java.lang.String r1 = r0.getQty()     // Catch:{ Exception -> 0x069c }
            java.lang.String r7 = ","
            java.lang.String r8 = ""
            java.lang.String r1 = r1.replace(r7, r8)     // Catch:{ Exception -> 0x069c }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Exception -> 0x069c }
            double r8 = (double) r1     // Catch:{ Exception -> 0x069c }
            double r2 = r2 - r4
            double r2 = r2 * r8
            goto L_0x02e5
        L_0x066b:
            java.lang.String r1 = r0.getContract()     // Catch:{ Exception -> 0x069c }
            java.lang.String r4 = ","
            java.lang.String r5 = ""
            java.lang.String r1 = r1.replace(r4, r5)     // Catch:{ Exception -> 0x069c }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Exception -> 0x069c }
            double r4 = (double) r1     // Catch:{ Exception -> 0x069c }
            double r2 = r2 * r4
            goto L_0x030b
        L_0x067f:
            r4 = 0
            int r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r1 >= 0) goto L_0x06bc
            android.widget.TextView r1 = r6.gainloss     // Catch:{ Exception -> 0x069c }
            android.content.Context r4 = r12.context     // Catch:{ Exception -> 0x069c }
            android.content.res.Resources r4 = r4.getResources()     // Catch:{ Exception -> 0x069c }
            int r5 = com.hkex.soma.utils.Commons.getDownColorResourceid()     // Catch:{ Exception -> 0x069c }
            int r4 = r4.getColor(r5)     // Catch:{ Exception -> 0x069c }
            r1.setTextColor(r4)     // Catch:{ Exception -> 0x069c }
            goto L_0x0326
        L_0x069c:
            r1 = move-exception
            android.widget.TextView r1 = r6.gainloss     // Catch:{ Exception -> 0x0387 }
            android.content.Context r2 = r12.context     // Catch:{ Exception -> 0x0387 }
            android.content.res.Resources r2 = r2.getResources()     // Catch:{ Exception -> 0x0387 }
            r3 = 2130968678(0x7f040066, float:1.7546016E38)
            int r2 = r2.getColor(r3)     // Catch:{ Exception -> 0x0387 }
            r1.setTextColor(r2)     // Catch:{ Exception -> 0x0387 }
            android.widget.TextView r1 = r6.gainloss     // Catch:{ Exception -> 0x0387 }
            java.lang.String r2 = "--"
            r1.setText(r2)     // Catch:{ Exception -> 0x0387 }
            goto L_0x0333
        L_0x06bc:
            android.widget.TextView r1 = r6.gainloss     // Catch:{ Exception -> 0x069c }
            android.content.Context r4 = r12.context     // Catch:{ Exception -> 0x069c }
            android.content.res.Resources r4 = r4.getResources()     // Catch:{ Exception -> 0x069c }
            r5 = 2130968678(0x7f040066, float:1.7546016E38)
            int r4 = r4.getColor(r5)     // Catch:{ Exception -> 0x069c }
            r1.setTextColor(r4)     // Catch:{ Exception -> 0x069c }
            goto L_0x0326
        L_0x06d2:
            r0 = move-exception
            goto L_0x0388
        L_0x06d5:
            r0 = move-exception
            goto L_0x0388
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hkex.soma.adapter.Portfolio_Adapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }
}
