// 
// Decompiled by Procyon v0.5.30
// 

package com.dbpower.urlimageviewhelper;

import java.util.*;
import java.lang.ref.*;

public class SoftReferenceHashTable<K, V>
{
    Hashtable<K, SoftReference<V>> mTable;
    
    public SoftReferenceHashTable() {
        this.mTable = new Hashtable<K, SoftReference<V>>();
    }
    
    public V get(final K k) {
        final SoftReference<V> softReference = this.mTable.get(k);
        if (softReference == null) {
            return null;
        }
        final V value = softReference.get();
        if (value == null) {
            this.mTable.remove(k);
        }
        return value;
    }
    
    public V put(final K k, final V v) {
        final SoftReference<V> softReference = this.mTable.put(k, new SoftReference<V>(v));
        if (softReference == null) {
            return null;
        }
        return softReference.get();
    }
    
    public V remove(final K k) {
        final SoftReference<V> softReference = this.mTable.remove(k);
        if (softReference == null) {
            return null;
        }
        return softReference.get();
    }
}
