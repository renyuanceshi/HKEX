package com.dbpower.urlimageviewhelper;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = i;
        this.map = new LinkedHashMap<>(0, 0.75f, true);
    }

    private int safeSizeOf(K k, V v) {
        int sizeOf = sizeOf(k, v);
        if (sizeOf >= 0) {
            return sizeOf;
        }
        throw new IllegalStateException("Negative size: " + k + "=" + v);
    }

    private void trimToSize(int var1) {
        while(true) {
            synchronized(this){}

            Throwable var10000;
            boolean var10001;
            label463: {
                label465: {
                    try {
                        if (this.size >= 0 && (!this.map.isEmpty() || this.size == 0)) {
                            break label465;
                        }
                    } catch (Throwable var45) {
                        var10000 = var45;
                        var10001 = false;
                        break label463;
                    }

                    try {
                        StringBuilder var3 = new StringBuilder(String.valueOf(this.getClass().getName()));
                        var3.append(".sizeOf() is reporting inconsistent results!");
                        IllegalStateException var2 = new IllegalStateException(var3.toString());
                        throw var2;
                    } catch (Throwable var44) {
                        var10000 = var44;
                        var10001 = false;
                        break label463;
                    }
                }

                label466: {
                    try {
                        if (this.size > var1 && !this.map.isEmpty()) {
                            break label466;
                        }
                    } catch (Throwable var43) {
                        var10000 = var43;
                        var10001 = false;
                        break label463;
                    }

                    try {
                        return;
                    } catch (Throwable var42) {
                        var10000 = var42;
                        var10001 = false;
                        break label463;
                    }
                }

                Object var47;
                Object var49;
                try {
                    Map.Entry var46 = (Map.Entry)this.map.entrySet().iterator().next();
                    var49 = var46.getKey();
                    var47 = var46.getValue();
                    this.map.remove(var49);
                    this.size -= this.safeSizeOf((K)var49, (V)var47);
                    ++this.evictionCount;
                } catch (Throwable var41) {
                    var10000 = var41;
                    var10001 = false;
                    break label463;
                }

                this.entryRemoved(true, (K)var49, (V)var47, (V)null);
                continue;
            }

            while(true) {
                Throwable var48 = var10000;

                try {
                    throw var48;
                } catch (Throwable var40) {
                    var10000 = var40;
                    var10001 = false;
                    continue;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public V create(K k) {
        return null;
    }

    public final int createCount() {
        int i;
        synchronized (this) {
            i = this.createCount;
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public void entryRemoved(boolean z, K k, V v, V v2) {
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final int evictionCount() {
        int i;
        synchronized (this) {
            i = this.evictionCount;
        }
        return i;
    }

    public final Object get(Object var1) {
        if (var1 == null) {
            throw new NullPointerException("key == null");
        } else {
            synchronized(this){}

            Throwable var10000;
            boolean var10001;
            Throwable var94;
            label843: {
                Object var2;
                try {
                    var2 = this.map.get(var1);
                } catch (Throwable var93) {
                    var10000 = var93;
                    var10001 = false;
                    break label843;
                }

                if (var2 != null) {
                    label836:
                    try {
                        ++this.hitCount;
                        return var2;
                    } catch (Throwable var91) {
                        var10000 = var91;
                        var10001 = false;
                        break label836;
                    }
                } else {
                    label849: {
                        try {
                            ++this.missCount;
                        } catch (Throwable var92) {
                            var10000 = var92;
                            var10001 = false;
                            break label849;
                        }

                        Object var3 = this.create((K)var1);
                        if (var3 == null) {
                            return null;
                        }

                        synchronized(this){}

                        label850: {
                            label851: {
                                try {
                                    ++this.createCount;
                                    var2 = this.map.put((K)var1, (V)var3);
                                } catch (Throwable var90) {
                                    var10000 = var90;
                                    var10001 = false;
                                    break label851;
                                }

                                if (var2 != null) {
                                    try {
                                        this.map.put((K)var1, (V)var2);
                                    } catch (Throwable var89) {
                                        var10000 = var89;
                                        var10001 = false;
                                        break label851;
                                    }
                                } else {
                                    try {
                                        this.size += this.safeSizeOf((K)var1, (V)var3);
                                    } catch (Throwable var88) {
                                        var10000 = var88;
                                        var10001 = false;
                                        break label851;
                                    }
                                }

                                label819:
                                try {
                                    break label850;
                                } catch (Throwable var87) {
                                    var10000 = var87;
                                    var10001 = false;
                                    break label819;
                                }
                            }

                            while(true) {
                                var94 = var10000;

                                try {
                                    throw var94;
                                } catch (Throwable var86) {
                                    var10000 = var86;
                                    var10001 = false;
                                    continue;
                                }
                            }
                        }

                        if (var2 != null) {
                            this.entryRemoved(false, (K)var1, (V)var3, (V)var2);
                            return var2;
                        }

                        this.trimToSize(this.maxSize);
                        return var3;
                    }
                }
            }

            while(true) {
                var94 = var10000;

                try {
                    throw var94;
                } catch (Throwable var85) {
                    var10000 = var85;
                    var10001 = false;
                    continue;
                }
            }
        }
    }

    public final int hitCount() {
        int i;
        synchronized (this) {
            i = this.hitCount;
        }
        return i;
    }

    public final int maxSize() {
        int i;
        synchronized (this) {
            i = this.maxSize;
        }
        return i;
    }

    public final int missCount() {
        int i;
        synchronized (this) {
            i = this.missCount;
        }
        return i;
    }

    public final V put(K k, V v) {
        V put;
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.putCount++;
            this.size += safeSizeOf(k, v);
            put = this.map.put(k, v);
            if (put != null) {
                this.size -= safeSizeOf(k, put);
            }
        }
        if (put != null) {
            entryRemoved(false, k, put, v);
        }
        trimToSize(this.maxSize);
        return put;
    }

    public final int putCount() {
        int i;
        synchronized (this) {
            i = this.putCount;
        }
        return i;
    }

    public final V remove(K k) {
        V remove;
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            remove = this.map.remove(k);
            if (remove != null) {
                this.size -= safeSizeOf(k, remove);
            }
        }
        if (remove != null) {
            entryRemoved(false, k, remove, (V) null);
        }
        return remove;
    }

    public final int size() {
        int i;
        synchronized (this) {
            i = this.size;
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public int sizeOf(K k, V v) {
        return 1;
    }

    public final Map<K, V> snapshot() {
        LinkedHashMap linkedHashMap;
        synchronized (this) {
            linkedHashMap = new LinkedHashMap(this.map);
        }
        return linkedHashMap;
    }

    public final String toString() {
        String format;
        int i = 0;
        synchronized (this) {
            int i2 = this.hitCount + this.missCount;
            if (i2 != 0) {
                i = (this.hitCount * 100) / i2;
            }
            format = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(i)});
        }
        return format;
    }
}
