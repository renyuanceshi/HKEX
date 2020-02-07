package com.hkex.soma.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.hkex.soma.R;
import com.hkex.soma.dataModel.CommonList;
import com.hkex.soma.dataModel.FuturesList_Result;
import com.hkex.soma.dataModel.FuturesPList_Result;
import com.hkex.soma.dataModel.IndexList;
import com.hkex.soma.dataModel.UnderlyingList;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.http.client.methods.HttpGet;

public class Commons {
    public static boolean CommonsListRequireUpdate = true;
    private static final boolean DEBUG = true;
    public static String activeSelectionList = "";
    public static CommonList commonList = null;
    public static Context context = null;
    public static String defaultStockCode = "";
    public static String defaultStockName = "";
    public static String defaultUnderlyingCode = "";
    public static int for_cal_test = 1;
    public static FuturesList_Result futures_mlist = null;
    public static FuturesPList_Result futures_plist = null;
    public static IndexList indexList = null;
    public static String language = null;
    public static boolean need2reflash = false;
    public static boolean noResumeAction = false;
    public static String notation = null;
    public static final int portfolioLimit = 20;
    public static boolean start_test = false;
    private static int statusBarHeight = 0;
    public static String text_optional = null;
    public static int timeoutConnection = 30000;
    public static int timeoutSocket = 30000;
    public static int tutor_cal = 1;
    public static int tutor_chart = 1;
    public static int tutor_margin = 1;
    public static int tutor_search = 1;
    public static UnderlyingList underlyingList;

    public static final Bitmap Bytes2Bimap(byte[] bArr) {
        if (bArr.length != 0) {
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        return null;
    }

    public static final void LogDebug(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        try {
            Log.d(str, str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final String MapIndexName(String str) {
        if (indexList == null) {
            return "";
        }
        int length = indexList.getmainData().length;
        for (int i = 0; i < length; i++) {
            if (indexList.getmainData()[i].getUcode().equals(str)) {
                return language.equals("en_US") ? indexList.getmainData()[i].getUname() : indexList.getmainData()[i].getUnmll();
            }
        }
        return "";
    }

    public static final String MapIndexName(String str, boolean z) {
        if (indexList == null) {
            return "";
        }
        int length = indexList.getmainData().length;
        for (int i = 0; i < length; i++) {
            if (indexList.getmainData()[i].getUcode().equals(str)) {
                return z ? indexList.getmainData()[i].getUname() : indexList.getmainData()[i].getUnmll();
            }
        }
        return "";
    }

    public static final String MapUnderlyingName(String str) {
        if (underlyingList == null) {
            return "";
        }
        int length = underlyingList.getmainData().length;
        for (int i = 0; i < length; i++) {
            if (underlyingList.getmainData()[i].getUcode().equals(str)) {
                return language.equals("en_US") ? underlyingList.getmainData()[i].getUname() : underlyingList.getmainData()[i].getUnmll();
            }
        }
        return "";
    }

    public static final String MapUnderlyingName(String str, boolean z) {
        if (underlyingList == null) {
            return "";
        }
        int length = underlyingList.getmainData().length;
        for (int i = 0; i < length; i++) {
            if (underlyingList.getmainData()[i].getUcode().equals(str)) {
                return z ? underlyingList.getmainData()[i].getUname() : underlyingList.getmainData()[i].getUnmll();
            }
        }
        return "";
    }

    public static final String callputText(Context context2, String str) {
        return str.toLowerCase().equals(NotificationCompat.CATEGORY_CALL) ? context2.getResources().getText(R.string.call).toString() : context2.getResources().getText(R.string.put).toString();
    }

    public static final String[] getAutoCompleteCodeList() {
        int i = 0;
        if (commonList == null) {
            return new String[]{" ~~ ~~ ~~"};
        }
        int length = commonList.getmainData().length;
        String[] strArr = new String[length];
        if (language.equals("en_US")) {
            while (i < length) {
                strArr[i] = commonList.getmainData()[i].getUcode() + " ~~ " + Integer.parseInt(commonList.getmainData()[i].getUcode()) + " ~~ " + underlyingList.getmainData()[i].getUname() + " ~~ " + underlyingList.getmainData()[i].getUnmll();
                i++;
            }
            return strArr;
        }
        while (i < length) {
            strArr[i] = commonList.getmainData()[i].getUcode() + " ~~ " + Integer.parseInt(commonList.getmainData()[i].getUcode()) + " ~~ " + underlyingList.getmainData()[i].getUnmll() + " ~~ " + underlyingList.getmainData()[i].getUname();
            i++;
        }
        return strArr;
    }

    public static final String[] getAutoCompleteNameList() {
        if (commonList == null) {
            return new String[]{" ~~ ~~ ~~ ~~"};
        }
        int length = commonList.getmainData().length;
        ArrayList arrayList = new ArrayList();
        if (language.equals("en_US")) {
            for (int i = 0; i < length; i++) {
                arrayList.add(underlyingList.getmainData()[i].getUname() + " ~~ " + Integer.parseInt(commonList.getmainData()[i].getUcode()) + " ~~ " + commonList.getmainData()[i].getUcode() + " ~~ " + underlyingList.getmainData()[i].getUnmll());
            }
        } else {
            for (int i2 = 0; i2 < length; i2++) {
                arrayList.add(underlyingList.getmainData()[i2].getUnmll() + " ~~ " + Integer.parseInt(commonList.getmainData()[i2].getUcode()) + " ~~ " + commonList.getmainData()[i2].getUcode() + " ~~ " + underlyingList.getmainData()[i2].getUname());
            }
        }
        Collections.sort(arrayList);
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static final int getDownArrowResourceid() {
        try {
            return notation.equals("ch") ? R.drawable.ic_green_down : R.drawable.ic_red_down;
        } catch (Exception e) {
            e.printStackTrace();
            return R.drawable.ic_red_down;
        }
    }

    public static final int getDownColorResourceid() {
        try {
            return notation.equals("ch") ? R.color.change_green : R.color.change_red;
        } catch (Exception e) {
            e.printStackTrace();
            return R.color.change_red;
        }
    }

    public static final String[] getExpiries(String str, String str2) {
        if (commonList == null) {
            return new String[]{""};
        }
        int length = commonList.getmainData().length;
        ArrayList arrayList = new ArrayList();
        CommonList.options[] optionsArr = null;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (commonList.getmainData()[i].getUcode().equals(str)) {
                optionsArr = commonList.getmainData()[i].getOptions();
                break;
            } else {
                i++;
            }
        }
        if (optionsArr == null) {
            arrayList.add("N/A");
        } else {
            for (int i2 = 0; i2 < optionsArr.length; i2++) {
                if ((str2 == null || optionsArr[i2].getStrike().equals(str2)) && !arrayList.contains(optionsArr[i2].getMdate())) {
                    arrayList.add(optionsArr[i2].getMdate());
                }
            }
        }
        Collections.sort(arrayList);
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static final String getHcode(String str, String str2, String str3) {
        if (commonList == null) {
            return "";
        }
        for (CommonList.mainData maindata : commonList.getmainData()) {
            if (maindata.getUcode().equals(str)) {
                for (CommonList.options options : maindata.getOptions()) {
                    if (options.getMdate().equals(str2) && options.getStrike().equals(str3)) {
                        return options.getHcode();
                    }
                }
                continue;
            }
        }
        return "";
    }

    public static final HttpGet getHttpRequest(String str) {
        String str2 = language.equals("en_US") ? "EN" : language.equals("zh_CN") ? "SC" : "TC";
        HttpGet httpGet = new HttpGet(str);
        String format = String.format("%s", new Object[]{Build.VERSION.RELEASE});
        String format2 = String.format("%s(%s) %s", new Object[]{Build.BRAND, Build.MANUFACTURER, Build.MODEL});
        httpGet.addHeader("Accept-Encoding", "gzip");
        httpGet.setHeader("User-Agent", "SOMA_ANDROID_" + str2 + "_" + format + "_" + format2.replace("_", ","));
        return httpGet;
    }

    public static final Bitmap getImageBitmap(String str) {
        return Bytes2Bimap(Base64.decode(str, 0));
    }

    public static final String getIndexName(String str, boolean z) {
        if (indexList == null) {
            return "";
        }
        for (IndexList.mainData maindata : indexList.getmainData()) {
            if (maindata.getUcode().equals(str)) {
                return z ? maindata.getUname() : maindata.getUnmll();
            }
        }
        return "";
    }

    public static final int getOffsetHeight(Context context2, int[] iArr) {
        return 0;
    }

    public static final float getSrceenHeightinDP(Context context2) {
        return statusBarHeight > 0 ? ((float) (context2.getResources().getDisplayMetrics().heightPixels - statusBarHeight)) / context2.getResources().getDisplayMetrics().density : (((float) (context2.getResources().getDisplayMetrics().heightPixels - statusBarHeight)) / context2.getResources().getDisplayMetrics().density) - 26.0f;
    }

    public static final float getSrceenWidthinDP(Context context2) {
        return ((float) context2.getResources().getDisplayMetrics().widthPixels) / context2.getResources().getDisplayMetrics().density;
    }

    public static final String[] getStrikes(String str, String str2) {
        if (commonList == null) {
            return new String[]{""};
        }
        int length = commonList.getmainData().length;
        ArrayList arrayList = new ArrayList();
        CommonList.options[] optionsArr = null;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (commonList.getmainData()[i].getUcode().equals(str)) {
                optionsArr = commonList.getmainData()[i].getOptions();
                break;
            } else {
                i++;
            }
        }
        if (optionsArr == null) {
            arrayList.add("N/A");
        } else {
            for (int i2 = 0; i2 < optionsArr.length; i2++) {
                if ((str2 == null || optionsArr[i2].getMdate().equals(str2)) && !arrayList.contains(optionsArr[i2].getStrike())) {
                    arrayList.add(optionsArr[i2].getStrike());
                }
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static final int getUpArrowResourceid() {
        try {
            return notation.equals("ch") ? R.drawable.ic_red_up : R.drawable.ic_green_up;
        } catch (Exception e) {
            e.printStackTrace();
            return R.drawable.ic_green_up;
        }
    }

    public static final int getUpColorResourceid() {
        try {
            return notation.equals("ch") ? R.color.change_red : R.color.change_green;
        } catch (Exception e) {
            e.printStackTrace();
            return R.color.change_green;
        }
    }

    public static final void initStaticText(Context context2) {
        if (language.equals("en_US")) {
            text_optional = context2.getResources().getText(R.string.optinoal_en).toString();
        } else if (language.equals("zh_CN")) {
            text_optional = context2.getResources().getText(R.string.optinoal_sc).toString();
        } else {
            text_optional = context2.getResources().getText(R.string.optinoal_ch).toString();
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return DEBUG;
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
    }

    public static final void printView(String str, View view) {
        PrintStream printStream = System.out;
        printStream.println(str + "=" + view);
        if (view != null) {
            PrintStream printStream2 = System.out;
            printStream2.print("[" + view.getLeft());
            PrintStream printStream3 = System.out;
            printStream3.print(", " + view.getTop());
            PrintStream printStream4 = System.out;
            printStream4.print(", w=" + view.getWidth());
            PrintStream printStream5 = System.out;
            printStream5.println(", h=" + view.getHeight() + "]");
            PrintStream printStream6 = System.out;
            printStream6.println("mw=" + view.getMeasuredWidth() + ", mh=" + view.getMeasuredHeight());
            PrintStream printStream7 = System.out;
            printStream7.println("scroll [" + view.getScrollX() + "," + view.getScrollY() + "]");
        }
    }

    public static final String roundSpread(float f) {
        double d = 0.5d;
        double d2 = (double) f;
        if (d2 < 0.25d) {
            d = 0.001d;
        } else if (d2 > 0.25d && d2 <= 0.5d) {
            d = 0.005d;
        } else if (d2 > 0.5d && f <= 10.0f) {
            d = 0.01d;
        } else if (f > 10.0f && f <= 20.0f) {
            d = 0.02d;
        } else if (f > 20.0f && f <= 100.0f) {
            d = 0.05d;
        } else if (f > 100.0f && f <= 200.0f) {
            d = 0.1d;
        } else if (f > 200.0f && f <= 500.0f) {
            d = 0.2d;
        } else if (f <= 500.0f || f > 1000.0f) {
            d = (f <= 1000.0f || f > 2000.0f) ? f > 2000.0f ? 2.0d : 5.0d : 1.0d;
        }
        return StringFormatter.formatUlast((float) (d * ((double) Math.round(d2 / d))));
    }

    public static final void setStatusBarHeight(Context context2, View view) {
        if (view != null) {
            statusBarHeight = context2.getResources().getDisplayMetrics().heightPixels - view.getMeasuredHeight();
            LogDebug("Commons", "Init Status Bar Height to " + statusBarHeight + "px");
        }
    }
}
