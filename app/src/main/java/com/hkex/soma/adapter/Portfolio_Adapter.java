package com.hkex.soma.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.dataModel.Portfolio_Result;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public View getView(int var1, View var2, ViewGroup var3) {
        final Portfolio_Result.mainData var4 = (Portfolio_Result.mainData)this.getItem(var1);
        StringBuilder var96 = new StringBuilder();
        var96.append("rowItem ");
        var96.append(var4.getCode());
        Log.v("Portfolio_Adapter", var96.toString());

        View var113;
        label896: {
            Exception var101;
            label837: {
                Exception var100;
                label843: {
                    Exception var10000;
                    boolean var10001;
                    Portfolio_Adapter.ViewHolder var99;
                    label835: {
                        label834: {
                            LayoutInflater var97;
                            try {
                                var97 = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            } catch (Exception var94) {
                                var10000 = var94;
                                var10001 = false;
                                break label834;
                            }

                            if (var2 == null) {
                                label844: {
                                    View var98;
                                    try {
                                        var98 = var97.inflate(this.itemLayout, (ViewGroup)null);
                                    } catch (Exception var92) {
                                        var10000 = var92;
                                        var10001 = false;
                                        break label844;
                                    }

                                    Portfolio_Adapter.ViewHolder var5;
                                    label824: {
                                        label845: {
                                            try {
                                                var5 = new Portfolio_Adapter.ViewHolder();
                                                if (this.itemLayout == R.layout.list_portfolio_edit) {
                                                    var5.checkBox = (CheckBox)var98.findViewById(R.id.checkBox);
                                                }
                                            } catch (Exception var91) {
                                                var10000 = var91;
                                                var10001 = false;
                                                break label845;
                                            }

                                            try {
                                                if (this.island) {
                                                    var5.expiry = (TextView)var98.findViewById(R.id.expiry);
                                                    var5.margin = (TextView)var98.findViewById(R.id.margin);
                                                    var5.contract = (TextView)var98.findViewById(R.id.contract);
                                                    var5.notional = (TextView)var98.findViewById(R.id.notional);
                                                }
                                            } catch (Exception var90) {
                                                var10000 = var90;
                                                var10001 = false;
                                                break label845;
                                            }

                                            try {
                                                var5.instrument1 = (TextView)var98.findViewById(R.id.instrument1);
                                                var5.instrument2 = (TextView)var98.findViewById(R.id.instrument2);
                                                var5.last = (TextView)var98.findViewById(R.id.last);
                                                var5.qty = (TextView)var98.findViewById(R.id.qty);
                                                var5.cost = (TextView)var98.findViewById(R.id.cost);
                                                var5.gainloss = (TextView)var98.findViewById(R.id.gainloss);
                                                var98.setTag(var5);
                                                break label824;
                                            } catch (Exception var89) {
                                                var10000 = var89;
                                                var10001 = false;
                                            }
                                        }

                                        Exception var95 = var10000;
                                        var101 = var95;
                                        var2 = var98;
                                        break label837;
                                    }

                                    var2 = var98;
                                    var99 = var5;
                                    break label835;
                                }
                            } else {
                                try {
                                    var99 = (Portfolio_Adapter.ViewHolder)var2.getTag();
                                    break label835;
                                } catch (Exception var93) {
                                    var10000 = var93;
                                    var10001 = false;
                                }
                            }
                        }

                        var100 = var10000;
                        break label843;
                    }

                    label882: {
                        String var103;
                        TextView var104;
                        label883: {
                            StringBuilder var105;
                            label848: {
                                label886: {
                                    try {
                                        if (Commons.language.equals("en_US")) {
                                            if (var4.getCode().indexOf("_Call_") <= 0 && var4.getCode().indexOf("_Put_") <= 0) {
                                                break label848;
                                            }
                                            break label886;
                                        }
                                    } catch (Exception var88) {
                                        var10000 = var88;
                                        var10001 = false;
                                        break label882;
                                    }

                                    label884: {
                                        try {
                                            if (var4.getCode().indexOf("_Call_") <= 0 && var4.getCode().indexOf("_Put_") <= 0) {
                                                break label884;
                                            }
                                        } catch (Exception var87) {
                                            var10000 = var87;
                                            var10001 = false;
                                            break label882;
                                        }

                                        try {
                                            var99.instrument1.setText(var4.getUnmll());
                                            var104 = var99.instrument2;
                                            var105 = new StringBuilder();
                                            var105.append(var4.getStrike());
                                            var105.append(" ");
                                            var105.append(Commons.callputText(this.context, var4.getType()));
                                            var104.setText(var105.toString());
                                            break label883;
                                        } catch (Exception var79) {
                                            var10000 = var79;
                                            var10001 = false;
                                            break label882;
                                        }
                                    }

                                    label887: {
                                        try {
                                            var99.instrument1.setText(var4.getUnmll());
                                            var104 = var99.instrument2;
                                            if (Commons.isNumeric(var4.getCode())) {
                                                var105 = new StringBuilder();
                                                var105.append("(");
                                                var105.append(var4.getCode());
                                                var105.append(")");
                                                var103 = var105.toString();
                                                break label887;
                                            }
                                        } catch (Exception var86) {
                                            var10000 = var86;
                                            var10001 = false;
                                            break label882;
                                        }

                                        try {
                                            var103 = var4.getCode();
                                        } catch (Exception var81) {
                                            var10000 = var81;
                                            var10001 = false;
                                            break label882;
                                        }
                                    }

                                    try {
                                        var104.setText(var103);
                                        break label883;
                                    } catch (Exception var80) {
                                        var10000 = var80;
                                        var10001 = false;
                                        break label882;
                                    }
                                }

                                try {
                                    var99.instrument1.setText(var4.getUName());
                                    TextView var102 = var99.instrument2;
                                    StringBuilder var6 = new StringBuilder();
                                    var6.append(var4.getStrike());
                                    var6.append(" ");
                                    var6.append(Commons.callputText(this.context, var4.getType()));
                                    var102.setText(var6.toString());
                                    break label883;
                                } catch (Exception var82) {
                                    var10000 = var82;
                                    var10001 = false;
                                    break label882;
                                }
                            }

                            label774: {
                                try {
                                    var99.instrument1.setText(var4.getUName());
                                    var104 = var99.instrument2;
                                    if (Commons.isNumeric(var4.getCode())) {
                                        var105 = new StringBuilder();
                                        var105.append("(");
                                        var105.append(var4.getCode());
                                        var105.append(")");
                                        var103 = var105.toString();
                                        break label774;
                                    }
                                } catch (Exception var85) {
                                    var10000 = var85;
                                    var10001 = false;
                                    break label882;
                                }

                                try {
                                    var103 = var4.getCode();
                                } catch (Exception var84) {
                                    var10000 = var84;
                                    var10001 = false;
                                    break label882;
                                }
                            }

                            try {
                                var104.setText(var103);
                            } catch (Exception var83) {
                                var10000 = var83;
                                var10001 = false;
                                break label882;
                            }
                        }

                        try {
                            if (var99.checkBox != null) {
                                final CheckBox var7 = var99.checkBox;
                                CheckBox var106 = var99.checkBox;
                                View.OnClickListener var111 = new View.OnClickListener() {
                                    public void onClick(View var1) {
                                        var4.setSelected(var7.isChecked());
                                    }
                                };
                                var106.setOnClickListener(var111);
                            }
                        } catch (Exception var78) {
                            var10000 = var78;
                            var10001 = false;
                            break label882;
                        }

                        float var8;
                        double var9;
                        label386: {
                            try {
                                var8 = Float.parseFloat(var4.getLast().replace(",", ""));
                            } catch (Exception var16) {
                                var9 = 0.0D;
                                break label386;
                            }

                            var9 = (double)var8;
                        }

                        label851: {
                            label749: {
                                try {
                                    var104 = var99.last;
                                    if (var4.getLast() != null && !var4.getLast().equals("")) {
                                        break label749;
                                    }
                                } catch (Exception var77) {
                                    var10000 = var77;
                                    var10001 = false;
                                    break label882;
                                }

                                var103 = "--";
                                break label851;
                            }

                            try {
                                var103 = var4.getLast();
                            } catch (Exception var76) {
                                var10000 = var76;
                                var10001 = false;
                                break label882;
                            }
                        }

                        label736: {
                            try {
                                var104.setText(var103);
                                var104 = var99.qty;
                                if (var4.getQty() == null) {
                                    var103 = var4.getQty();
                                    break label736;
                                }
                            } catch (Exception var75) {
                                var10000 = var75;
                                var10001 = false;
                                break label882;
                            }

                            var103 = "0";
                        }

                        label852: {
                            SimpleDateFormat var109;
                            try {
                                var104.setText(var103);
                                if (!this.island) {
                                    break label852;
                                }

                                var109 = new SimpleDateFormat("yyyy-MM-dd");
                            } catch (Exception var74) {
                                var10000 = var74;
                                var10001 = false;
                                break label882;
                            }

                            label853: {
                                label854: {
                                    label719: {
                                        label888: {
                                            label716:
                                            try {
                                                if (var4.getExpiry_str() != null && !var4.getExpiry_str().equals("")) {
                                                    break label716;
                                                }
                                                break label888;
                                            } catch (Exception var73) {
                                                var10000 = var73;
                                                var10001 = false;
                                                break label854;
                                            }

                                            try {
                                                var103 = var4.getExpiry_str();
                                                break label719;
                                            } catch (Exception var71) {
                                                var10000 = var71;
                                                var10001 = false;
                                                break label854;
                                            }
                                        }

                                        try {
                                            var103 = var4.getExpiry();
                                        } catch (Exception var70) {
                                            var10000 = var70;
                                            var10001 = false;
                                            break label854;
                                        }
                                    }

                                    Date var110;
                                    try {
                                        var110 = var109.parse(var103);
                                        if (var103.length() > 7) {
                                            var99.expiry.setText(StringFormatter.formatDatetime(var110, 1, this.getContext()));
                                            break label853;
                                        }
                                    } catch (Exception var72) {
                                        var10000 = var72;
                                        var10001 = false;
                                        break label854;
                                    }

                                    try {
                                        var99.expiry.setText(StringFormatter.formatDatetime(var110, 2, this.getContext()));
                                        break label853;
                                    } catch (Exception var69) {
                                        var10000 = var69;
                                        var10001 = false;
                                    }
                                }

                                var101 = var10000;

                                label695: {
                                    label889: {
                                        label692:
                                        try {
                                            var101.printStackTrace();
                                            var104 = var99.expiry;
                                            if (var4.getExpiry_str() != null && !var4.getExpiry_str().equals("")) {
                                                break label692;
                                            }
                                            break label889;
                                        } catch (Exception var68) {
                                            var10000 = var68;
                                            var10001 = false;
                                            break label882;
                                        }

                                        try {
                                            var103 = var4.getExpiry_str();
                                            break label695;
                                        } catch (Exception var38) {
                                            var10000 = var38;
                                            var10001 = false;
                                            break label882;
                                        }
                                    }

                                    try {
                                        var103 = var4.getExpiry();
                                    } catch (Exception var37) {
                                        var10000 = var37;
                                        var10001 = false;
                                        break label882;
                                    }
                                }

                                try {
                                    var104.setText(var103);
                                } catch (Exception var36) {
                                    var10000 = var36;
                                    var10001 = false;
                                    break label882;
                                }
                            }

                            label857: {
                                label682: {
                                    label858: {
                                        label859: {
                                            label860: {
                                                label678: {
                                                    try {
                                                        if (Integer.parseInt(var4.getQty().replace(",", "")) <= 0) {
                                                            break label860;
                                                        }

                                                        if (var4.getTypename() == null || !var4.getTypename().equals("stock")) {
                                                            break label859;
                                                        }

                                                        var104 = var99.margin;
                                                        if (var4.getMargin() != null && !var4.getMargin().equals("")) {
                                                            break label678;
                                                        }
                                                    } catch (Exception var67) {
                                                        var10000 = var67;
                                                        var10001 = false;
                                                        break label682;
                                                    }

                                                    var103 = "--";
                                                    break label858;
                                                }

                                                try {
                                                    var103 = var4.getMargin();
                                                    break label858;
                                                } catch (Exception var66) {
                                                    var10000 = var66;
                                                    var10001 = false;
                                                    break label682;
                                                }
                                            }

                                            label658: {
                                                label657: {
                                                    label656: {
                                                        try {
                                                            if (var4.getTypename() == null || !var4.getTypename().equals("stock")) {
                                                                break label658;
                                                            }

                                                            var104 = var99.margin;
                                                            if (var4.getMargin() != null && !var4.getMargin().equals("")) {
                                                                break label656;
                                                            }
                                                        } catch (Exception var65) {
                                                            var10000 = var65;
                                                            var10001 = false;
                                                            break label682;
                                                        }

                                                        var103 = "--";
                                                        break label657;
                                                    }

                                                    var103 = "0";
                                                }

                                                try {
                                                    var104.setText(var103);
                                                    break label857;
                                                } catch (Exception var60) {
                                                    var10000 = var60;
                                                    var10001 = false;
                                                    break label682;
                                                }
                                            }

                                            label641: {
                                                label640: {
                                                    label639: {
                                                        try {
                                                            if (!var4.getDirection().equals("0")) {
                                                                break label641;
                                                            }

                                                            var104 = var99.margin;
                                                            if (var4.getL_margin() != null && !var4.getL_margin().equals("")) {
                                                                break label639;
                                                            }
                                                        } catch (Exception var64) {
                                                            var10000 = var64;
                                                            var10001 = false;
                                                            break label682;
                                                        }

                                                        var103 = "--";
                                                        break label640;
                                                    }

                                                    var103 = "0";
                                                }

                                                try {
                                                    var104.setText(var103);
                                                    break label857;
                                                } catch (Exception var61) {
                                                    var10000 = var61;
                                                    var10001 = false;
                                                    break label682;
                                                }
                                            }

                                            label626: {
                                                label625: {
                                                    try {
                                                        var104 = var99.margin;
                                                        if (var4.getS_margin() != null && !var4.getS_margin().equals("")) {
                                                            break label625;
                                                        }
                                                    } catch (Exception var63) {
                                                        var10000 = var63;
                                                        var10001 = false;
                                                        break label682;
                                                    }

                                                    var103 = "--";
                                                    break label626;
                                                }

                                                var103 = "0";
                                            }

                                            try {
                                                var104.setText(var103);
                                                break label857;
                                            } catch (Exception var62) {
                                                var10000 = var62;
                                                var10001 = false;
                                                break label682;
                                            }
                                        }

                                        label609: {
                                            label864: {
                                                label607: {
                                                    try {
                                                        if (!var4.getDirection().equals("0")) {
                                                            break label609;
                                                        }

                                                        var104 = var99.margin;
                                                        if (var4.getL_margin() != null && !var4.getL_margin().equals("")) {
                                                            break label607;
                                                        }
                                                    } catch (Exception var59) {
                                                        var10000 = var59;
                                                        var10001 = false;
                                                        break label682;
                                                    }

                                                    var103 = "--";
                                                    break label864;
                                                }

                                                try {
                                                    var103 = var4.getL_margin();
                                                } catch (Exception var58) {
                                                    var10000 = var58;
                                                    var10001 = false;
                                                    break label682;
                                                }
                                            }

                                            try {
                                                var104.setText(var103);
                                                break label857;
                                            } catch (Exception var54) {
                                                var10000 = var54;
                                                var10001 = false;
                                                break label682;
                                            }
                                        }

                                        label592: {
                                            label866: {
                                                try {
                                                    var104 = var99.margin;
                                                    if (var4.getS_margin() == null || var4.getS_margin().equals("")) {
                                                        break label866;
                                                    }
                                                } catch (Exception var57) {
                                                    var10000 = var57;
                                                    var10001 = false;
                                                    break label682;
                                                }

                                                try {
                                                    var103 = var4.getS_margin();
                                                    break label592;
                                                } catch (Exception var56) {
                                                    var10000 = var56;
                                                    var10001 = false;
                                                    break label682;
                                                }
                                            }

                                            var103 = "--";
                                        }

                                        try {
                                            var104.setText(var103);
                                            break label857;
                                        } catch (Exception var55) {
                                            var10000 = var55;
                                            var10001 = false;
                                            break label682;
                                        }
                                    }

                                    try {
                                        var104.setText(var103);
                                        break label857;
                                    } catch (Exception var53) {
                                        var10000 = var53;
                                        var10001 = false;
                                    }
                                }

                                var101 = var10000;

                                try {
                                    var101.printStackTrace();
                                    var99.margin.setText("--");
                                } catch (Exception var35) {
                                    var10000 = var35;
                                    var10001 = false;
                                    break label882;
                                }
                            }

                            label573: {
                                label867: {
                                    try {
                                        var104 = var99.contract;
                                        if (var4.getContract() == null || var4.getContract().equals("")) {
                                            break label867;
                                        }
                                    } catch (Exception var52) {
                                        var10000 = var52;
                                        var10001 = false;
                                        break label882;
                                    }

                                    try {
                                        var103 = var4.getContract();
                                        break label573;
                                    } catch (Exception var34) {
                                        var10000 = var34;
                                        var10001 = false;
                                        break label882;
                                    }
                                }

                                var103 = "--";
                            }

                            StringBuilder var12;
                            label564: {
                                boolean var11;
                                try {
                                    var104.setText(var103);
                                    if (var4.getOid() == null) {
                                        break label564;
                                    }

                                    var11 = var4.getOid().equals("");
                                } catch (Exception var51) {
                                    var10000 = var51;
                                    var10001 = false;
                                    break label882;
                                }

                                if (!var11) {
                                    label869: {
                                        TextView var107;
                                        DecimalFormat var112;
                                        label870: {
                                            label554: {
                                                try {
                                                    var107 = var99.notional;
                                                    var12 = new StringBuilder();
                                                    var12.append("");
                                                    var112 = this.formatter;
                                                    if (var4.getStrike() != "null" && var4.getStrike() != null && var4.getStrike() != "") {
                                                        break label554;
                                                    }
                                                } catch (Exception var50) {
                                                    var10001 = false;
                                                    break label869;
                                                }

                                                var103 = "0";
                                                break label870;
                                            }

                                            try {
                                                var103 = var4.getStrike().replace(",", "");
                                            } catch (Exception var49) {
                                                var10001 = false;
                                                break label869;
                                            }
                                        }

                                        float var13;
                                        label871: {
                                            label540: {
                                                try {
                                                    var13 = Float.parseFloat(var103);
                                                    if (var4.getContract() != "null" && var4.getContract() != null && var4.getContract() != "") {
                                                        break label540;
                                                    }
                                                } catch (Exception var48) {
                                                    var10001 = false;
                                                    break label869;
                                                }

                                                var103 = "0";
                                                break label871;
                                            }

                                            try {
                                                var103 = var4.getContract().replace(",", "");
                                            } catch (Exception var47) {
                                                var10001 = false;
                                                break label869;
                                            }
                                        }

                                        label872: {
                                            label526: {
                                                try {
                                                    var8 = Float.parseFloat(var103);
                                                    if (var4.getQty() != "null" && var4.getQty() != null && var4.getQty() != "") {
                                                        break label526;
                                                    }
                                                } catch (Exception var46) {
                                                    var10001 = false;
                                                    break label869;
                                                }

                                                var103 = "0";
                                                break label872;
                                            }

                                            try {
                                                var103 = var4.getQty().replace(",", "");
                                            } catch (Exception var45) {
                                                var10001 = false;
                                                break label869;
                                            }
                                        }

                                        try {
                                            var12.append(var112.format((long)Math.round(var13 * var8 * Float.parseFloat(var103))));
                                            var107.setText(var12.toString());
                                            break label852;
                                        } catch (Exception var44) {
                                            var10001 = false;
                                        }
                                    }

                                    try {
                                        var99.notional.setText("0");
                                        break label852;
                                    } catch (Exception var33) {
                                        var10000 = var33;
                                        var10001 = false;
                                        break label882;
                                    }
                                }
                            }

                            label873: {
                                DecimalFormat var108;
                                label874: {
                                    label508: {
                                        try {
                                            var104 = var99.notional;
                                            var12 = new StringBuilder();
                                            var12.append("");
                                            var108 = this.formatter;
                                            if (var4.getLast() != "null" && var4.getLast() != null && var4.getLast() != "") {
                                                break label508;
                                            }
                                        } catch (Exception var43) {
                                            var10001 = false;
                                            break label873;
                                        }

                                        var103 = "0";
                                        break label874;
                                    }

                                    try {
                                        var103 = var4.getLast().replace(",", "");
                                    } catch (Exception var42) {
                                        var10001 = false;
                                        break label873;
                                    }
                                }

                                label875: {
                                    label494: {
                                        try {
                                            var8 = Float.parseFloat(var103);
                                            if (var4.getQty() != "null" && var4.getQty() != null && var4.getQty() != "") {
                                                break label494;
                                            }
                                        } catch (Exception var41) {
                                            var10001 = false;
                                            break label873;
                                        }

                                        var103 = "0";
                                        break label875;
                                    }

                                    try {
                                        var103 = var4.getQty();
                                    } catch (Exception var40) {
                                        var10001 = false;
                                        break label873;
                                    }
                                }

                                try {
                                    var12.append(var108.format((long)Math.round(var8 * Float.parseFloat(var103))));
                                    var104.setText(var12.toString());
                                    break label852;
                                } catch (Exception var39) {
                                    var10001 = false;
                                }
                            }

                            try {
                                var99.notional.setText("0");
                            } catch (Exception var32) {
                                var10000 = var32;
                                var10001 = false;
                                break label882;
                            }
                        }

                        double var14;
                        label876: {
                            label885: {
                                label462: {
                                    try {
                                        if (this.itemLayout == 2131296314) {
                                            break label876;
                                        }

                                        if (var4.getDirection().equals("0")) {
                                            var14 = (double)Float.parseFloat(var4.getPrice().replace(",", ""));
                                            break label462;
                                        }
                                    } catch (Exception var31) {
                                        var10001 = false;
                                        break label885;
                                    }

                                    try {
                                        var14 = (double)(Float.parseFloat(var4.getPrice().replace(",", "")) * -1.0F);
                                    } catch (Exception var30) {
                                        var10001 = false;
                                        break label885;
                                    }
                                }

                                try {
                                    var99.cost.setText(String.format("%.3f", var14));
                                    break label876;
                                } catch (Exception var29) {
                                    var10001 = false;
                                }
                            }

                            try {
                                var99.cost.setText("--");
                            } catch (Exception var28) {
                                var10000 = var28;
                                var10001 = false;
                                break label882;
                            }
                        }

                        label878: {
                            label879: {
                                label442: {
                                    try {
                                        if (var4.getDirection().equals("0")) {
                                            var14 = (var9 - (double)Float.parseFloat(var4.getPrice().replace(",", ""))) * (double)Integer.parseInt(var4.getQty().replace(",", ""));
                                            break label442;
                                        }
                                    } catch (Exception var27) {
                                        var10001 = false;
                                        break label879;
                                    }

                                    try {
                                        var14 = (double)Float.parseFloat(var4.getPrice().replace(",", ""));
                                        var14 = (double)Integer.parseInt(var4.getQty().replace(",", "")) * (var14 - var9);
                                    } catch (Exception var26) {
                                        var10001 = false;
                                        break label879;
                                    }
                                }

                                var9 = var14;

                                label880: {
                                    try {
                                        if (var4.getOid() == null) {
                                            break label880;
                                        }
                                    } catch (Exception var25) {
                                        var10001 = false;
                                        break label879;
                                    }

                                    var9 = var14;

                                    label426: {
                                        try {
                                            if (var4.getOid().equals("")) {
                                                break label880;
                                            }

                                            if (var4.getContract() != null && !var4.getContract().equals("")) {
                                                break label426;
                                            }
                                        } catch (Exception var24) {
                                            var10001 = false;
                                            break label879;
                                        }

                                        var9 = 0.0D;
                                        break label880;
                                    }

                                    try {
                                        var9 = (double)Integer.parseInt(var4.getContract().replace(",", "")) * var14;
                                    } catch (Exception var23) {
                                        var10001 = false;
                                        break label879;
                                    }
                                }

                                if (var9 > 0.0D) {
                                    try {
                                        var99.gainloss.setTextColor(this.context.getResources().getColor(Commons.getUpColorResourceid()));
                                    } catch (Exception var22) {
                                        var10001 = false;
                                        break label879;
                                    }
                                } else if (var9 < 0.0D) {
                                    try {
                                        var99.gainloss.setTextColor(this.context.getResources().getColor(Commons.getDownColorResourceid()));
                                    } catch (Exception var21) {
                                        var10001 = false;
                                        break label879;
                                    }
                                } else {
                                    try {
                                        var99.gainloss.setTextColor(this.context.getResources().getColor(R.color.textgrey));
                                    } catch (Exception var20) {
                                        var10001 = false;
                                        break label879;
                                    }
                                }

                                try {
                                    var99.gainloss.setText(this.formatter.format(var9));
                                    break label878;
                                } catch (Exception var19) {
                                    var10001 = false;
                                }
                            }

                            try {
                                var99.gainloss.setTextColor(this.context.getResources().getColor(R.color.textgrey));
                                var99.gainloss.setText("--");
                            } catch (Exception var18) {
                                var10000 = var18;
                                var10001 = false;
                                break label882;
                            }
                        }

                        var113 = var2;

                        try {
                            if (this.itemLayout != R.layout.list_portfolio_edit) {
                                return var113;
                            }

                            var99.checkBox.setChecked(var4.getSelected());
                            break label896;
                        } catch (Exception var17) {
                            var10000 = var17;
                            var10001 = false;
                        }
                    }

                    var100 = var10000;
                }

                var101 = var100;
            }

            var101.printStackTrace();
            var113 = var2;
            return var113;
        }

        var113 = var2;
        return var113;
    }

}
