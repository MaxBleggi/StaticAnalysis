package com.google.android.gms.internal.measurement;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

class zzxx<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzbqj;
    private final int zzccv;
    private List<zzye> zzccw;
    private Map<K, V> zzccx;
    private volatile zzyg zzccy;
    private Map<K, V> zzccz;
    private volatile zzya zzcda;

    static <FieldDescriptorType extends zzvq<FieldDescriptorType>> zzxx<FieldDescriptorType, Object> zzbv(int i) {
        return new zzxy(i);
    }

    private zzxx(int i) {
        this.zzccv = i;
        this.zzccw = Collections.emptyList();
        this.zzccx = Collections.emptyMap();
        this.zzccz = Collections.emptyMap();
    }

    public void zzsw() {
        if (!this.zzbqj) {
            Map emptyMap;
            if (this.zzccx.isEmpty()) {
                emptyMap = Collections.emptyMap();
            } else {
                emptyMap = Collections.unmodifiableMap(this.zzccx);
            }
            this.zzccx = emptyMap;
            if (this.zzccz.isEmpty()) {
                emptyMap = Collections.emptyMap();
            } else {
                emptyMap = Collections.unmodifiableMap(this.zzccz);
            }
            this.zzccz = emptyMap;
            this.zzbqj = true;
        }
    }

    public final boolean isImmutable() {
        return this.zzbqj;
    }

    public final int zzyj() {
        return this.zzccw.size();
    }

    public final Entry<K, V> zzbw(int i) {
        return (Entry) this.zzccw.get(i);
    }

    public final Iterable<Entry<K, V>> zzyk() {
        if (this.zzccx.isEmpty()) {
            return zzyb.zzyp();
        }
        return this.zzccx.entrySet();
    }

    public int size() {
        return this.zzccw.size() + this.zzccx.size();
    }

    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        if (zza(comparable) < 0) {
            if (this.zzccx.containsKey(comparable) == null) {
                return null;
            }
        }
        return true;
    }

    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        if (zza >= 0) {
            return ((zzye) this.zzccw.get(zza)).getValue();
        }
        return this.zzccx.get(comparable);
    }

    public final V zza(K k, V v) {
        zzym();
        int zza = zza((Comparable) k);
        if (zza >= 0) {
            return ((zzye) this.zzccw.get(zza)).setValue(v);
        }
        zzym();
        if (this.zzccw.isEmpty() && !(this.zzccw instanceof ArrayList)) {
            this.zzccw = new ArrayList(this.zzccv);
        }
        zza = -(zza + 1);
        if (zza >= this.zzccv) {
            return zzyn().put(k, v);
        }
        if (this.zzccw.size() == this.zzccv) {
            zzye com_google_android_gms_internal_measurement_zzye = (zzye) this.zzccw.remove(this.zzccv - 1);
            zzyn().put((Comparable) com_google_android_gms_internal_measurement_zzye.getKey(), com_google_android_gms_internal_measurement_zzye.getValue());
        }
        this.zzccw.add(zza, new zzye(this, k, v));
        return null;
    }

    public void clear() {
        zzym();
        if (!this.zzccw.isEmpty()) {
            this.zzccw.clear();
        }
        if (!this.zzccx.isEmpty()) {
            this.zzccx.clear();
        }
    }

    public V remove(Object obj) {
        zzym();
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        if (zza >= 0) {
            return zzbx(zza);
        }
        if (this.zzccx.isEmpty()) {
            return null;
        }
        return this.zzccx.remove(comparable);
    }

    private final V zzbx(int i) {
        zzym();
        i = ((zzye) this.zzccw.remove(i)).getValue();
        if (!this.zzccx.isEmpty()) {
            Iterator it = zzyn().entrySet().iterator();
            this.zzccw.add(new zzye(this, (Entry) it.next()));
            it.remove();
        }
        return i;
    }

    private final int zza(K k) {
        int compareTo;
        int size = this.zzccw.size() - 1;
        if (size >= 0) {
            compareTo = k.compareTo((Comparable) ((zzye) this.zzccw.get(size)).getKey());
            if (compareTo > 0) {
                return -(size + 2);
            }
            if (compareTo == 0) {
                return size;
            }
        }
        compareTo = 0;
        while (compareTo <= size) {
            int i = (compareTo + size) / 2;
            int compareTo2 = k.compareTo((Comparable) ((zzye) this.zzccw.get(i)).getKey());
            if (compareTo2 < 0) {
                size = i - 1;
            } else if (compareTo2 <= 0) {
                return i;
            } else {
                compareTo = i + 1;
            }
        }
        return -(compareTo + 1);
    }

    public Set<Entry<K, V>> entrySet() {
        if (this.zzccy == null) {
            this.zzccy = new zzyg();
        }
        return this.zzccy;
    }

    final Set<Entry<K, V>> zzyl() {
        if (this.zzcda == null) {
            this.zzcda = new zzya();
        }
        return this.zzcda;
    }

    private final void zzym() {
        if (this.zzbqj) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzyn() {
        zzym();
        if (this.zzccx.isEmpty() && !(this.zzccx instanceof TreeMap)) {
            this.zzccx = new TreeMap();
            this.zzccz = ((TreeMap) this.zzccx).descendingMap();
        }
        return (SortedMap) this.zzccx;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzxx)) {
            return super.equals(obj);
        }
        zzxx com_google_android_gms_internal_measurement_zzxx = (zzxx) obj;
        int size = size();
        if (size != com_google_android_gms_internal_measurement_zzxx.size()) {
            return false;
        }
        int zzyj = zzyj();
        if (zzyj != com_google_android_gms_internal_measurement_zzxx.zzyj()) {
            return entrySet().equals(com_google_android_gms_internal_measurement_zzxx.entrySet());
        }
        for (int i = 0; i < zzyj; i++) {
            if (!zzbw(i).equals(com_google_android_gms_internal_measurement_zzxx.zzbw(i))) {
                return false;
            }
        }
        if (zzyj != size) {
            return this.zzccx.equals(com_google_android_gms_internal_measurement_zzxx.zzccx);
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < zzyj(); i2++) {
            i += ((zzye) this.zzccw.get(i2)).hashCode();
        }
        return this.zzccx.size() > 0 ? i + this.zzccx.hashCode() : i;
    }

    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((Comparable) obj, obj2);
    }
}
