package com.hkex.soma.utils;

import android.util.Base64;

public class EncryptUtils {
    public static final String DEFAULT_ENCODING = "UTF-8";

    public static String base64decode(String str) {
        try {
            return new String(Base64.decode(str.getBytes(), 0));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String base64encode(String str) {
        try {
            return new String(Base64.encode(str.getBytes(), 0));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String xorMessage(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        try {
            char[] charArray = str2.toCharArray();
            char[] charArray2 = str.toCharArray();
            int length = charArray2.length;
            int length2 = charArray.length;
            char[] cArr = new char[length];
            for (int i = 0; i < length; i++) {
                cArr[i] = (char) ((char) (charArray2[i] ^ charArray[i % length2]));
            }
            return new String(cArr);
        } catch (Exception e) {
            return null;
        }
    }
}
