package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.GuardedBy;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.AppMeasurement;

public final class zzaq extends zzcq {
    private long zzadt = -1;
    private char zzamd = '\u0000';
    @GuardedBy("this")
    private String zzame;
    private final zzas zzamf = new zzas(this, 6, false, false);
    private final zzas zzamg = new zzas(this, 6, true, false);
    private final zzas zzamh = new zzas(this, 6, false, true);
    private final zzas zzami = new zzas(this, 5, false, false);
    private final zzas zzamj = new zzas(this, 5, true, false);
    private final zzas zzamk = new zzas(this, 5, false, true);
    private final zzas zzaml = new zzas(this, 4, false, false);
    private final zzas zzamm = new zzas(this, 3, false, false);
    private final zzas zzamn = new zzas(this, 2, false, false);

    zzaq(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
    }

    protected final boolean zzgy() {
        return false;
    }

    public final zzas zzjg() {
        return this.zzamf;
    }

    public final zzas zzjh() {
        return this.zzamg;
    }

    public final zzas zzji() {
        return this.zzamh;
    }

    public final zzas zzjj() {
        return this.zzami;
    }

    public final zzas zzjk() {
        return this.zzamj;
    }

    public final zzas zzjl() {
        return this.zzamk;
    }

    public final zzas zzjm() {
        return this.zzaml;
    }

    public final zzas zzjn() {
        return this.zzamm;
    }

    public final zzas zzjo() {
        return this.zzamn;
    }

    protected static Object zzby(String str) {
        return str == null ? null : new zzat(str);
    }

    protected final void zza(int i, boolean z, boolean z2, String str, Object obj, Object obj2, Object obj3) {
        if (!z && isLoggable(i)) {
            zza(i, zza(false, str, obj, obj2, obj3));
        }
        if (!z2 && i >= true) {
            Preconditions.checkNotNull(str);
            z = this.zzadp.zzkl();
            if (!z) {
                zza(6, "Scheduler not set. Not logging error/warn");
            } else if (z.isInitialized()) {
                boolean z3;
                if (i < 0) {
                    z3 = false;
                }
                z.zzc(new zzar(this, z3 >= true ? 8 : z3, str, obj, obj2, obj3));
            } else {
                zza(6, "Scheduler not initialized. Not logging error/warn");
            }
        }
    }

    @VisibleForTesting
    protected final boolean isLoggable(int i) {
        return Log.isLoggable(zzjp(), i);
    }

    @VisibleForTesting
    protected final void zza(int i, String str) {
        Log.println(i, zzjp(), str);
    }

    @VisibleForTesting
    private final String zzjp() {
        String str;
        synchronized (this) {
            if (this.zzame == null) {
                if (this.zzadp.zzkq() != null) {
                    this.zzame = this.zzadp.zzkq();
                } else {
                    this.zzame = zzo.zzhy();
                }
            }
            str = this.zzame;
        }
        return str;
    }

    static String zza(boolean z, String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            str = "";
        }
        obj = zza(z, obj);
        obj2 = zza(z, obj2);
        z = zza(z, obj3);
        obj3 = new StringBuilder();
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            obj3.append(str);
            str2 = ": ";
        }
        if (TextUtils.isEmpty(obj) == null) {
            obj3.append(str2);
            obj3.append(obj);
            str2 = ", ";
        }
        if (TextUtils.isEmpty(obj2) == null) {
            obj3.append(str2);
            obj3.append(obj2);
            str2 = ", ";
        }
        if (TextUtils.isEmpty(z) == null) {
            obj3.append(str2);
            obj3.append(z);
        }
        return obj3.toString();
    }

    @VisibleForTesting
    private static String zza(boolean z, Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof Integer) {
            obj = Long.valueOf((long) ((Integer) obj).intValue());
        }
        int i = 0;
        if (obj instanceof Long) {
            if (!z) {
                return String.valueOf(obj);
            }
            Long l = (Long) obj;
            if (Math.abs(l.longValue()) < 100) {
                return String.valueOf(obj);
            }
            obj = String.valueOf(obj).charAt(0) == 45 ? "-" : "";
            z = String.valueOf(Math.abs(l.longValue()));
            long round = Math.round(Math.pow(10.0d, (double) (z.length() - 1)));
            long round2 = Math.round(Math.pow(10.0d, (double) z.length()) - 1.0d);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(obj).length() + 43) + String.valueOf(obj).length());
            stringBuilder.append(obj);
            stringBuilder.append(round);
            stringBuilder.append("...");
            stringBuilder.append(obj);
            stringBuilder.append(round2);
            return stringBuilder.toString();
        } else if (obj instanceof Boolean) {
            return String.valueOf(obj);
        } else {
            if (obj instanceof Throwable) {
                Throwable th = (Throwable) obj;
                StringBuilder stringBuilder2 = new StringBuilder(z ? th.getClass().getName() : th.toString());
                z = zzbz(AppMeasurement.class.getCanonicalName());
                String zzbz = zzbz(zzbu.class.getCanonicalName());
                obj = th.getStackTrace();
                int length = obj.length;
                while (i < length) {
                    StackTraceElement stackTraceElement = obj[i];
                    if (!stackTraceElement.isNativeMethod()) {
                        String className = stackTraceElement.getClassName();
                        if (className != null) {
                            className = zzbz(className);
                            if (className.equals(z) || className.equals(zzbz)) {
                                stringBuilder2.append(": ");
                                stringBuilder2.append(stackTraceElement);
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                    i++;
                }
                return stringBuilder2.toString();
            } else if (obj instanceof zzat) {
                return ((zzat) obj).zzamw;
            } else {
                if (z) {
                    return "-";
                }
                return String.valueOf(obj);
            }
        }
    }

    private static String zzbz(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf == -1) {
            return str;
        }
        return str.substring(0, lastIndexOf);
    }

    public final String zzjq() {
        Pair zzfm = zzgu().zzank.zzfm();
        if (zzfm != null) {
            if (zzfm != zzbb.zzanj) {
                String valueOf = String.valueOf(zzfm.second);
                String str = (String) zzfm.first;
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(str).length());
                stringBuilder.append(valueOf);
                stringBuilder.append(":");
                stringBuilder.append(str);
                return stringBuilder.toString();
            }
        }
        return null;
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
