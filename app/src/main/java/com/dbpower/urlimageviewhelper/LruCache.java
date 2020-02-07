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

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0030, code lost:
        throw new java.lang.IllegalStateException(java.lang.String.valueOf(getClass().getName()) + ".sizeOf() is reporting inconsistent results!");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void trimToSize(int r5) {
        /*
            r4 = this;
        L_0x0000:
            monitor-enter(r4)
            int r0 = r4.size     // Catch:{ all -> 0x0031 }
            if (r0 < 0) goto L_0x0011
            java.util.LinkedHashMap<K, V> r0 = r4.map     // Catch:{ all -> 0x0031 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0031 }
            if (r0 == 0) goto L_0x0034
            int r0 = r4.size     // Catch:{ all -> 0x0031 }
            if (r0 == 0) goto L_0x0034
        L_0x0011:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0031 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0031 }
            java.lang.Class r2 = r4.getClass()     // Catch:{ all -> 0x0031 }
            java.lang.String r2 = r2.getName()     // Catch:{ all -> 0x0031 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x0031 }
            r1.<init>(r2)     // Catch:{ all -> 0x0031 }
            java.lang.String r2 = ".sizeOf() is reporting inconsistent results!"
            r1.append(r2)     // Catch:{ all -> 0x0031 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0031 }
            r0.<init>(r1)     // Catch:{ all -> 0x0031 }
            throw r0     // Catch:{ all -> 0x0031 }
        L_0x0031:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0031 }
            throw r0
        L_0x0034:
            int r0 = r4.size     // Catch:{ all -> 0x0031 }
            if (r0 <= r5) goto L_0x0040
            java.util.LinkedHashMap<K, V> r0 = r4.map     // Catch:{ all -> 0x0031 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0031 }
            if (r0 == 0) goto L_0x0042
        L_0x0040:
            monitor-exit(r4)     // Catch:{ all -> 0x0031 }
            return
        L_0x0042:
            java.util.LinkedHashMap<K, V> r0 = r4.map     // Catch:{ all -> 0x0031 }
            java.util.Set r0 = r0.entrySet()     // Catch:{ all -> 0x0031 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0031 }
            java.lang.Object r0 = r0.next()     // Catch:{ all -> 0x0031 }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ all -> 0x0031 }
            java.lang.Object r1 = r0.getKey()     // Catch:{ all -> 0x0031 }
            java.lang.Object r0 = r0.getValue()     // Catch:{ all -> 0x0031 }
            java.util.LinkedHashMap<K, V> r2 = r4.map     // Catch:{ all -> 0x0031 }
            r2.remove(r1)     // Catch:{ all -> 0x0031 }
            int r2 = r4.size     // Catch:{ all -> 0x0031 }
            int r3 = r4.safeSizeOf(r1, r0)     // Catch:{ all -> 0x0031 }
            int r2 = r2 - r3
            r4.size = r2     // Catch:{ all -> 0x0031 }
            int r2 = r4.evictionCount     // Catch:{ all -> 0x0031 }
            int r2 = r2 + 1
            r4.evictionCount = r2     // Catch:{ all -> 0x0031 }
            monitor-exit(r4)     // Catch:{ all -> 0x0031 }
            r2 = 1
            r3 = 0
            r4.entryRemoved(r2, r1, r0, r3)
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: com.dbpower.urlimageviewhelper.LruCache.trimToSize(int):void");
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

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0022, code lost:
        r1 = create(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0026, code lost:
        if (r1 != null) goto L_0x002a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0028, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r4.createCount++;
        r0 = r4.map.put(r5, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0037, code lost:
        if (r0 == null) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0039, code lost:
        r4.map.put(r5, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003e, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003f, code lost:
        if (r0 == null) goto L_0x0053;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0041, code lost:
        entryRemoved(false, r5, r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r4.size += safeSizeOf(r5, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0053, code lost:
        trimToSize(r4.maxSize);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V get(K r5) {
        /*
            r4 = this;
            if (r5 != 0) goto L_0x000a
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "key == null"
            r0.<init>(r1)
            throw r0
        L_0x000a:
            monitor-enter(r4)
            java.util.LinkedHashMap<K, V> r0 = r4.map     // Catch:{ all -> 0x005a }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x005a }
            if (r0 == 0) goto L_0x001b
            int r1 = r4.hitCount     // Catch:{ all -> 0x005a }
            int r1 = r1 + 1
            r4.hitCount = r1     // Catch:{ all -> 0x005a }
            monitor-exit(r4)     // Catch:{ all -> 0x005a }
        L_0x001a:
            return r0
        L_0x001b:
            int r0 = r4.missCount     // Catch:{ all -> 0x005a }
            int r0 = r0 + 1
            r4.missCount = r0     // Catch:{ all -> 0x005a }
            monitor-exit(r4)     // Catch:{ all -> 0x005a }
            java.lang.Object r1 = r4.create(r5)
            if (r1 != 0) goto L_0x002a
            r0 = 0
            goto L_0x001a
        L_0x002a:
            monitor-enter(r4)
            int r0 = r4.createCount     // Catch:{ all -> 0x0050 }
            int r0 = r0 + 1
            r4.createCount = r0     // Catch:{ all -> 0x0050 }
            java.util.LinkedHashMap<K, V> r0 = r4.map     // Catch:{ all -> 0x0050 }
            java.lang.Object r0 = r0.put(r5, r1)     // Catch:{ all -> 0x0050 }
            if (r0 == 0) goto L_0x0046
            java.util.LinkedHashMap<K, V> r2 = r4.map     // Catch:{ all -> 0x0050 }
            r2.put(r5, r0)     // Catch:{ all -> 0x0050 }
        L_0x003e:
            monitor-exit(r4)     // Catch:{ all -> 0x0050 }
            if (r0 == 0) goto L_0x0053
            r2 = 0
            r4.entryRemoved(r2, r5, r1, r0)
            goto L_0x001a
        L_0x0046:
            int r2 = r4.size     // Catch:{ all -> 0x0050 }
            int r3 = r4.safeSizeOf(r5, r1)     // Catch:{ all -> 0x0050 }
            int r2 = r2 + r3
            r4.size = r2     // Catch:{ all -> 0x0050 }
            goto L_0x003e
        L_0x0050:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0050 }
            throw r0
        L_0x0053:
            int r0 = r4.maxSize
            r4.trimToSize(r0)
            r0 = r1
            goto L_0x001a
        L_0x005a:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x005a }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.dbpower.urlimageviewhelper.LruCache.get(java.lang.Object):java.lang.Object");
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
