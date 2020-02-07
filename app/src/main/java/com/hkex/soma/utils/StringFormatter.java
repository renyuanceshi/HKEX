package com.hkex.soma.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkex.soma.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class StringFormatter {
    public static final int YM = 2;
    public static final int YMD = 1;
    private static final String[] months_list = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static TextView formatChange(TextView textView, String str, String str2) {
        float f;
        Resources resources = textView.getResources();
        int color = resources.getColor(Commons.getUpColorResourceid());
        int color2 = resources.getColor(Commons.getDownColorResourceid());
        int color3 = resources.getColor(R.color.textblack);
        try {
            f = Float.parseFloat(str);
        } catch (Exception e) {
            f = 0.0f;
        }
        if (f > 0.0f) {
            textView.setTextColor(color);
        } else if (f < 0.0f) {
            textView.setTextColor(color2);
        } else {
            textView.setTextColor(color3);
        }
        textView.setText(str + "(" + str2 + "%)");
        return textView;
    }

    public static TextView formatChange(TextView textView, String str, String str2, String str3) {
        float f;
        Resources resources = textView.getResources();
        int color = resources.getColor(Commons.getUpColorResourceid());
        int color2 = resources.getColor(Commons.getDownColorResourceid());
        int color3 = resources.getColor(R.color.textblack);
        try {
            f = Float.parseFloat(str);
        } catch (Exception e) {
            f = 0.0f;
        }
        int i = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
        if (i > 0) {
            textView.setTextColor(color);
        } else if (f < 0.0f) {
            textView.setTextColor(color2);
        } else {
            textView.setTextColor(color3);
        }
        if (i == 0) {
            textView.setText(str3 + " (" + str3 + ")");
        } else {
            textView.setText(str + " (" + str2 + "%)");
        }
        return textView;
    }

    public static ImageView formatChng(ImageView imageView, String str) {
        float f;
        Resources resources = imageView.getResources();
        Drawable drawable = resources.getDrawable(Commons.getUpArrowResourceid());
        Drawable drawable2 = resources.getDrawable(Commons.getDownArrowResourceid());
        Drawable drawable3 = resources.getDrawable(R.drawable.ic_nochange);
        try {
            f = Float.parseFloat(str);
        } catch (Exception e) {
            f = 0.0f;
        }
        if (f > 0.0f) {
            imageView.setImageDrawable(drawable);
        } else if (f < 0.0f) {
            imageView.setImageDrawable(drawable2);
        } else {
            imageView.setImageDrawable(drawable3);
        }
        return imageView;
    }

    public static TextView formatChng(TextView textView, String str) {
        float f;
        Resources resources = textView.getResources();
        int color = resources.getColor(Commons.getUpColorResourceid());
        int color2 = resources.getColor(Commons.getDownColorResourceid());
        int color3 = resources.getColor(R.color.textblack);
        try {
            f = Float.parseFloat(str);
        } catch (Exception e) {
            f = 0.0f;
        }
        if (f > 0.0f) {
            textView.setTextColor(color);
        } else if (f < 0.0f) {
            textView.setTextColor(color2);
        } else {
            textView.setTextColor(color3);
        }
        textView.setText(str);
        return textView;
    }

    public static String formatCode(String str) {
        try {
            return String.format("%05d", new Object[]{Integer.valueOf(Integer.parseInt(str.trim()))});
        } catch (Exception e) {
            return "00000";
        }
    }

    public static String formatDate(String str, int i, int i2, int i3) {
        Date date = new Date(i - 1900, i2, i3);
        return Commons.language.equals("en_US") ? new SimpleDateFormat(str, Locale.US).format(date).toString() : new SimpleDateFormat(str, Locale.PRC).format(date).toString();
    }

    public static String formatDatetime(Date date, int i, Context context) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        switch (i) {
            case 1:
                if (Commons.language.equals("en_US")) {
                    StringBuilder sb = new StringBuilder();
                    if (instance.get(5) >= 10) {
                        obj3 = Integer.valueOf(instance.get(5));
                    } else {
                        obj3 = "0" + instance.get(5);
                    }
                    sb.append(obj3);
                    sb.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    sb.append(months_list[instance.get(2)]);
                    sb.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    sb.append(instance.get(1));
                    return sb.toString();
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(instance.get(1));
                sb2.append(context.getResources().getString(R.string.year_text));
                if (instance.get(2) + 1 >= 10) {
                    obj = Integer.valueOf(instance.get(2) + 1);
                } else {
                    obj = "0" + (instance.get(2) + 1);
                }
                sb2.append(obj);
                sb2.append(context.getResources().getString(R.string.month_text));
                if (instance.get(5) >= 10) {
                    obj2 = Integer.valueOf(instance.get(5));
                } else {
                    obj2 = "0" + instance.get(5);
                }
                sb2.append(obj2);
                sb2.append(context.getResources().getString(R.string.day_text));
                return sb2.toString();
            case 2:
                if (Commons.language.equals("en_US")) {
                    return months_list[instance.get(2)] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + instance.get(1);
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(instance.get(1));
                sb3.append(context.getResources().getString(R.string.year_text));
                if (instance.get(2) + 1 >= 10) {
                    obj4 = Integer.valueOf(instance.get(2) + 1);
                } else {
                    obj4 = "0" + (instance.get(2) + 1);
                }
                sb3.append(obj4);
                sb3.append(context.getResources().getString(R.string.month_text));
                return sb3.toString();
            default:
                return "";
        }
    }

    public static String formatDatetime(Date date, Context context) {
        Object obj;
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        if (Commons.language.equals("en_US")) {
            return months_list[instance.get(2)] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + instance.get(1);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(instance.get(1));
        sb.append(context.getResources().getString(R.string.year_text));
        if (instance.get(2) + 1 >= 10) {
            obj = Integer.valueOf(instance.get(2) + 1);
        } else {
            obj = "0" + (instance.get(2) + 1);
        }
        sb.append(obj);
        sb.append(context.getResources().getString(R.string.month_text));
        return sb.toString();
    }

    public static String formatExpiry(String str) {
        try {
            if (str.split("-").length >= 3) {
                String str2 = str.split("-")[0];
                String str3 = str.split("-")[1];
                String str4 = str.split("-")[2];
                if (!Commons.language.equals("en_US")) {
                    return formatDate("yyyy\u5e74MM\u6708dd\u65e5", Integer.parseInt(str2), Integer.parseInt(str3) - 1, Integer.parseInt(str4));
                }
                return String.format("%02d", new Object[]{Integer.valueOf(Integer.parseInt(str4))}) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + months_list[Integer.parseInt(str3) - 1] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + Integer.parseInt(str2);
            }
            String str5 = str.split("-")[0];
            String str6 = str.split("-")[1];
            if (!Commons.language.equals("en_US")) {
                return formatDate("yyyy\u5e74MM\u6708", Integer.parseInt(str5), Integer.parseInt(str6) - 1, 1);
            }
            return months_list[Integer.parseInt(str6) - 1] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + Integer.parseInt(str5);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatFullDate(String str) {
        try {
            String str2 = str.split("-")[0];
            String str3 = str.split("-")[1];
            String str4 = str.split("-")[2];
            if (!Commons.language.equals("en_US")) {
                return formatDate("yyyy\u5e74MM\u6708dd\u65e5", Integer.parseInt(str2), Integer.parseInt(str3) - 1, Integer.parseInt(str4));
            }
            return months_list[Integer.parseInt(str3) - 1] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + Integer.parseInt(str2) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + Integer.parseInt(str4);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatInterestRate(float f) {
        return String.format("%.2f", new Object[]{Float.valueOf(f)});
    }

    public static TextView formatPChng(TextView textView, String str) {
        float f;
        Resources resources = textView.getResources();
        int color = resources.getColor(Commons.getUpColorResourceid());
        int color2 = resources.getColor(Commons.getDownColorResourceid());
        int color3 = resources.getColor(R.color.textblack);
        try {
            f = Float.parseFloat(str);
        } catch (Exception e) {
            f = 0.0f;
        }
        if (f > 0.0f) {
            textView.setTextColor(color);
            textView.setText(str + "%");
        } else if (f < 0.0f) {
            textView.setTextColor(color2);
            textView.setText(str + "%");
        } else {
            textView.setTextColor(color3);
            textView.setText(str);
        }
        return textView;
    }

    public static String formatUlast(float f) {
        return String.format("%.2f", new Object[]{Float.valueOf(f)});
    }
}
