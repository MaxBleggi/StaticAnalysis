package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import androidx.annotation.GuardedBy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzte implements zzsq {
    @GuardedBy("SharedPreferencesLoader.class")
    static final Map<String, zzte> zzbsk = new HashMap();
    private final Object zzbro = new Object();
    private volatile Map<String, ?> zzbrp;
    @GuardedBy("this")
    private final List<zzsp> zzbrq = new ArrayList();
    private final SharedPreferences zzbsl;
    private final OnSharedPreferenceChangeListener zzbsm = new zztf(this);

    static zzte zzi(Context context, String str) {
        boolean isUserUnlocked = (!zzsl.zztj() || str.startsWith("direct_boot:")) ? true : zzsl.isUserUnlocked(context);
        if (!isUserUnlocked) {
            return null;
        }
        zzte com_google_android_gms_internal_measurement_zzte;
        synchronized (zzte.class) {
            com_google_android_gms_internal_measurement_zzte = (zzte) zzbsk.get(str);
            if (com_google_android_gms_internal_measurement_zzte == null) {
                if (str.startsWith("direct_boot:")) {
                    if (zzsl.zztj()) {
                        context = context.createDeviceProtectedStorageContext();
                    }
                    context = context.getSharedPreferences(str.substring(12), 0);
                } else {
                    context = context.getSharedPreferences(str, 0);
                }
                com_google_android_gms_internal_measurement_zzte = new zzte(context);
                zzbsk.put(str, com_google_android_gms_internal_measurement_zzte);
            }
        }
        return com_google_android_gms_internal_measurement_zzte;
    }

    private zzte(SharedPreferences sharedPreferences) {
        this.zzbsl = sharedPreferences;
        this.zzbsl.registerOnSharedPreferenceChangeListener(this.zzbsm);
    }

    public final Object zzfp(String str) {
        Map map = this.zzbrp;
        if (map == null) {
            synchronized (this.zzbro) {
                map = this.zzbrp;
                if (map == null) {
                    map = this.zzbsl.getAll();
                    this.zzbrp = map;
                }
            }
        }
        return map != null ? map.get(str) : null;
    }

    final /* synthetic */ void zza(SharedPreferences sharedPreferences, String str) {
        synchronized (this.zzbro) {
            this.zzbrp = null;
            zzsx.zztq();
        }
        synchronized (this) {
            for (zzsp zztp : this.zzbrq) {
                zztp.zztp();
            }
        }
    }
}
