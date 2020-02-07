package com.hkex.soma.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsUtil {
    public static final String SETTING = "HKEX_SOMA";

    public static void clearPortfolioValue(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("HKEX_SOMA_PORTFOLIO", 0);
        SharedPreferences sharedPreferences2 = context.getSharedPreferences("HKEX_SOMA_PORTFOLIO_EN", 0);
        sharedPreferences.edit().clear().commit();
        sharedPreferences2.edit().clear().commit();
    }

    public static String getPortfolioEnValue(Context context, String str, String str2) {
        return context.getSharedPreferences("HKEX_SOMA_PORTFOLIO_EN", 0).getString(str, str2);
    }

    public static String getPortfolioValue(Context context, String str, String str2) {
        return context.getSharedPreferences("HKEX_SOMA_PORTFOLIO", 0).getString(str, str2);
    }

    public static int getValue(Context context, String str, int i) {
        return context.getSharedPreferences(SETTING, 0).getInt(str, i);
    }

    public static String getValue(Context context, String str, String str2) {
        return context.getSharedPreferences(SETTING, 0).getString(str, str2);
    }

    public static boolean getValue(Context context, String str, boolean z) {
        return context.getSharedPreferences(SETTING, 0).getBoolean(str, z);
    }

    public static void putPortfolioEnValue(Context context, String str, String str2) {
        SharedPreferences.Editor edit = context.getSharedPreferences("HKEX_SOMA_PORTFOLIO_EN", 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static void putPortfolioValue(Context context, String str, String str2) {
        SharedPreferences.Editor edit = context.getSharedPreferences("HKEX_SOMA_PORTFOLIO", 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static void putValue(Context context, String str, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(SETTING, 0).edit();
        edit.putInt(str, i);
        edit.commit();
    }

    public static void putValue(Context context, String str, String str2) {
        SharedPreferences.Editor edit = context.getSharedPreferences(SETTING, 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static void putValue(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(SETTING, 0).edit();
        edit.putBoolean(str, z);
        edit.commit();
    }
}
