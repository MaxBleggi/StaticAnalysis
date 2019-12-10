package com.google.android.gms.internal.measurement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

public abstract class zzsx<T> {
    private static final Object zzbrx = new Object();
    private static boolean zzbry = false;
    private static final AtomicInteger zzbsb = new AtomicInteger();
    @SuppressLint({"StaticFieldLeak"})
    private static Context zzri = null;
    private final String name;
    private volatile T zzalu;
    private final zztd zzbrz;
    private final T zzbsa;
    private volatile int zzbsc;

    public static void zzae(Context context) {
        synchronized (zzbrx) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                context = applicationContext;
            }
            if (zzri != context) {
                synchronized (zzsm.class) {
                    zzsm.zzbrm.clear();
                }
                synchronized (zzte.class) {
                    zzte.zzbsk.clear();
                }
                synchronized (zzst.class) {
                    zzst.zzbru = null;
                }
                zzbsb.incrementAndGet();
                zzri = context;
            }
        }
    }

    abstract T zzs(Object obj);

    static void zztq() {
        zzbsb.incrementAndGet();
    }

    private zzsx(zztd com_google_android_gms_internal_measurement_zztd, String str, T t) {
        this.zzbsc = -1;
        if (com_google_android_gms_internal_measurement_zztd.zzbse != null) {
            this.zzbrz = com_google_android_gms_internal_measurement_zztd;
            this.name = str;
            this.zzbsa = t;
            return;
        }
        throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
    }

    private final String zzft(String str) {
        if (str != null && str.isEmpty()) {
            return this.name;
        }
        str = String.valueOf(str);
        String valueOf = String.valueOf(this.name);
        return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
    }

    public final String zztr() {
        return zzft(this.zzbrz.zzbsg);
    }

    public final T getDefaultValue() {
        return this.zzbsa;
    }

    public final T get() {
        int i = zzbsb.get();
        if (this.zzbsc < i) {
            synchronized (this) {
                if (this.zzbsc < i) {
                    if (zzri != null) {
                        zztd com_google_android_gms_internal_measurement_zztd = this.zzbrz;
                        Object zzts = zzts();
                        if (zzts == null) {
                            zzts = zztt();
                            if (zzts == null) {
                                zzts = this.zzbsa;
                            }
                        }
                        this.zzalu = zzts;
                        this.zzbsc = i;
                    } else {
                        throw new IllegalStateException("Must call PhenotypeFlag.init() first");
                    }
                }
            }
        }
        return this.zzalu;
    }

    @Nullable
    private final T zzts() {
        zztd com_google_android_gms_internal_measurement_zztd = this.zzbrz;
        String str = (String) zzst.zzad(zzri).zzfp("gms:phenotype:phenotype_flag:debug_bypass_phenotype");
        Object obj = (str == null || !zzsj.zzbqz.matcher(str).matches()) ? null : 1;
        if (obj == null) {
            zzsq zza;
            if (this.zzbrz.zzbse != null) {
                zza = zzsm.zza(zzri.getContentResolver(), this.zzbrz.zzbse);
            } else {
                Context context = zzri;
                zztd com_google_android_gms_internal_measurement_zztd2 = this.zzbrz;
                zza = zzte.zzi(context, null);
            }
            if (zza != null) {
                obj = zza.zzfp(zztr());
                if (obj != null) {
                    return zzs(obj);
                }
            }
        }
        str = "PhenotypeFlag";
        String str2 = "Bypass reading Phenotype values for flag: ";
        String valueOf = String.valueOf(zztr());
        Log.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        return null;
    }

    @Nullable
    private final T zztt() {
        zztd com_google_android_gms_internal_measurement_zztd = this.zzbrz;
        zzsq zzad = zzst.zzad(zzri);
        zztd com_google_android_gms_internal_measurement_zztd2 = this.zzbrz;
        Object zzfp = zzad.zzfp(zzft(this.zzbrz.zzbsf));
        return zzfp != null ? zzs(zzfp) : null;
    }

    private static zzsx<Long> zza(zztd com_google_android_gms_internal_measurement_zztd, String str, long j) {
        return new zzsy(com_google_android_gms_internal_measurement_zztd, str, Long.valueOf(j));
    }

    private static zzsx<Integer> zza(zztd com_google_android_gms_internal_measurement_zztd, String str, int i) {
        return new zzsz(com_google_android_gms_internal_measurement_zztd, str, Integer.valueOf(i));
    }

    private static zzsx<Boolean> zza(zztd com_google_android_gms_internal_measurement_zztd, String str, boolean z) {
        return new zzta(com_google_android_gms_internal_measurement_zztd, str, Boolean.valueOf(z));
    }

    private static zzsx<Double> zza(zztd com_google_android_gms_internal_measurement_zztd, String str, double d) {
        return new zztb(com_google_android_gms_internal_measurement_zztd, str, Double.valueOf(d));
    }

    private static zzsx<String> zza(zztd com_google_android_gms_internal_measurement_zztd, String str, String str2) {
        return new zztc(com_google_android_gms_internal_measurement_zztd, str, str2);
    }
}
