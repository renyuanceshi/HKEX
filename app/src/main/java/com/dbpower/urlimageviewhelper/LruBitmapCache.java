package com.dbpower.urlimageviewhelper;

import android.graphics.Bitmap;

public class LruBitmapCache extends LruCache<String, Bitmap> {
    public LruBitmapCache(int i) {
        super(i);
    }

    /* access modifiers changed from: protected */
    public int sizeOf(String str, Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
