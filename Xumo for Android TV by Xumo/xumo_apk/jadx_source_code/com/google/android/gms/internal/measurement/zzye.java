package com.google.android.gms.internal.measurement;

import java.util.Map.Entry;

final class zzye implements Comparable<zzye>, Entry<K, V> {
    private V value;
    private final /* synthetic */ zzxx zzcdc;
    private final K zzcdf;

    zzye(zzxx com_google_android_gms_internal_measurement_zzxx, Entry<K, V> entry) {
        this(com_google_android_gms_internal_measurement_zzxx, (Comparable) entry.getKey(), entry.getValue());
    }

    zzye(zzxx com_google_android_gms_internal_measurement_zzxx, K k, V v) {
        this.zzcdc = com_google_android_gms_internal_measurement_zzxx;
        this.zzcdf = k;
        this.value = v;
    }

    public final V getValue() {
        return this.value;
    }

    public final V setValue(V v) {
        this.zzcdc.zzym();
        V v2 = this.value;
        this.value = v;
        return v2;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Entry)) {
            return false;
        }
        Entry entry = (Entry) obj;
        return equals(this.zzcdf, entry.getKey()) && equals(this.value, entry.getValue()) != null;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = this.zzcdf == null ? 0 : this.zzcdf.hashCode();
        if (this.value != null) {
            i = this.value.hashCode();
        }
        return hashCode ^ i;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzcdf);
        String valueOf2 = String.valueOf(this.value);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(valueOf2).length());
        stringBuilder.append(valueOf);
        stringBuilder.append("=");
        stringBuilder.append(valueOf2);
        return stringBuilder.toString();
    }

    private static boolean equals(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null ? true : null;
        } else {
            return obj.equals(obj2);
        }
    }

    public final /* synthetic */ Object getKey() {
        return this.zzcdf;
    }

    public final /* synthetic */ int compareTo(Object obj) {
        return ((Comparable) getKey()).compareTo((Comparable) ((zzye) obj).getKey());
    }
}
