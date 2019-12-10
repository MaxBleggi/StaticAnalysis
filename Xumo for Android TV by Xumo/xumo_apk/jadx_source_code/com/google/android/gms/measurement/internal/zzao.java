package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.util.concurrent.atomic.AtomicReference;

public final class zzao extends zzcq {
    private static final AtomicReference<String[]> zzama = new AtomicReference();
    private static final AtomicReference<String[]> zzamb = new AtomicReference();
    private static final AtomicReference<String[]> zzamc = new AtomicReference();

    zzao(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
    }

    protected final boolean zzgy() {
        return false;
    }

    private final boolean zzjf() {
        zzgw();
        return this.zzadp.zzkn() && this.zzadp.zzgt().isLoggable(3);
    }

    @Nullable
    protected final String zzbv(String str) {
        if (str == null) {
            return null;
        }
        if (zzjf()) {
            return zza(str, zzcs.zzare, zzcs.zzard, zzama);
        }
        return str;
    }

    @Nullable
    protected final String zzbw(String str) {
        if (str == null) {
            return null;
        }
        if (zzjf()) {
            return zza(str, zzct.zzarg, zzct.zzarf, zzamb);
        }
        return str;
    }

    @Nullable
    protected final String zzbx(String str) {
        if (str == null) {
            return null;
        }
        if (!zzjf()) {
            return str;
        }
        if (!str.startsWith("_exp_")) {
            return zza(str, zzcu.zzari, zzcu.zzarh, zzamc);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("experiment_id");
        stringBuilder.append("(");
        stringBuilder.append(str);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    private static String zza(String str, String[] strArr, String[] strArr2, AtomicReference<String[]> atomicReference) {
        Preconditions.checkNotNull(strArr);
        Preconditions.checkNotNull(strArr2);
        Preconditions.checkNotNull(atomicReference);
        Preconditions.checkArgument(strArr.length == strArr2.length);
        for (int i = 0; i < strArr.length; i++) {
            if (zzfu.zzv(str, strArr[i])) {
                synchronized (atomicReference) {
                    str = (String[]) atomicReference.get();
                    if (str == null) {
                        str = new String[strArr2.length];
                        atomicReference.set(str);
                    }
                    if (str[i] == null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(strArr2[i]);
                        stringBuilder.append("(");
                        stringBuilder.append(strArr[i]);
                        stringBuilder.append(")");
                        str[i] = stringBuilder.toString();
                    }
                    str = str[i];
                }
                return str;
            }
        }
        return str;
    }

    @Nullable
    protected final String zzb(zzae com_google_android_gms_measurement_internal_zzae) {
        if (com_google_android_gms_measurement_internal_zzae == null) {
            return null;
        }
        if (!zzjf()) {
            return com_google_android_gms_measurement_internal_zzae.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("origin=");
        stringBuilder.append(com_google_android_gms_measurement_internal_zzae.origin);
        stringBuilder.append(",name=");
        stringBuilder.append(zzbv(com_google_android_gms_measurement_internal_zzae.name));
        stringBuilder.append(",params=");
        stringBuilder.append(zzb(com_google_android_gms_measurement_internal_zzae.zzaig));
        return stringBuilder.toString();
    }

    @Nullable
    protected final String zza(zzz com_google_android_gms_measurement_internal_zzz) {
        if (com_google_android_gms_measurement_internal_zzz == null) {
            return null;
        }
        if (!zzjf()) {
            return com_google_android_gms_measurement_internal_zzz.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Event{appId='");
        stringBuilder.append(com_google_android_gms_measurement_internal_zzz.zztt);
        stringBuilder.append("', name='");
        stringBuilder.append(zzbv(com_google_android_gms_measurement_internal_zzz.name));
        stringBuilder.append("', params=");
        stringBuilder.append(zzb(com_google_android_gms_measurement_internal_zzz.zzaig));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Nullable
    private final String zzb(zzab com_google_android_gms_measurement_internal_zzab) {
        if (com_google_android_gms_measurement_internal_zzab == null) {
            return null;
        }
        if (zzjf()) {
            return zzd(com_google_android_gms_measurement_internal_zzab.zziy());
        }
        return com_google_android_gms_measurement_internal_zzab.toString();
    }

    @Nullable
    protected final String zzd(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        if (!zzjf()) {
            return bundle.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : bundle.keySet()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            } else {
                stringBuilder.append("Bundle[{");
            }
            stringBuilder.append(zzbw(str));
            stringBuilder.append("=");
            stringBuilder.append(bundle.get(str));
        }
        stringBuilder.append("}]");
        return stringBuilder.toString();
    }

    public final /* bridge */ /* synthetic */ void zzgf() {
        super.zzgf();
    }

    public final /* bridge */ /* synthetic */ void zzgg() {
        super.zzgg();
    }

    public final /* bridge */ /* synthetic */ void zzgh() {
        super.zzgh();
    }

    public final /* bridge */ /* synthetic */ void zzaf() {
        super.zzaf();
    }

    public final /* bridge */ /* synthetic */ zzy zzgp() {
        return super.zzgp();
    }

    public final /* bridge */ /* synthetic */ Clock zzbx() {
        return super.zzbx();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final /* bridge */ /* synthetic */ zzao zzgq() {
        return super.zzgq();
    }

    public final /* bridge */ /* synthetic */ zzfu zzgr() {
        return super.zzgr();
    }

    public final /* bridge */ /* synthetic */ zzbp zzgs() {
        return super.zzgs();
    }

    public final /* bridge */ /* synthetic */ zzaq zzgt() {
        return super.zzgt();
    }

    public final /* bridge */ /* synthetic */ zzbb zzgu() {
        return super.zzgu();
    }

    public final /* bridge */ /* synthetic */ zzo zzgv() {
        return super.zzgv();
    }

    public final /* bridge */ /* synthetic */ zzl zzgw() {
        return super.zzgw();
    }
}
