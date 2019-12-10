package com.google.android.gms.internal.cast;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Locale;

public final class zzdh {
    private static boolean zzxv = false;
    private final String mTag;
    private final boolean zzxw;
    private boolean zzxx;
    private boolean zzxy;
    private String zzxz;

    private zzdh(String str, boolean z) {
        Preconditions.checkNotEmpty(str, "The log tag cannot be null or empty.");
        this.mTag = str;
        this.zzxw = str.length() <= 23 ? true : null;
        this.zzxx = false;
        this.zzxy = false;
    }

    public zzdh(String str) {
        this(str, false);
    }

    public final void zzt(String str) {
        if (TextUtils.isEmpty(str)) {
            str = null;
        } else {
            str = String.format("[%s] ", new Object[]{str});
        }
        this.zzxz = str;
    }

    private final boolean zzdo() {
        if (!this.zzxx) {
            if (!this.zzxw || !Log.isLoggable(this.mTag, 3)) {
                return false;
            }
        }
        return true;
    }

    public final void zzl(boolean z) {
        this.zzxx = true;
    }

    public final void d(String str, Object... objArr) {
        if (zzdo()) {
            Log.d(this.mTag, zza(str, objArr));
        }
    }

    public final void zza(Throwable th, String str, Object... objArr) {
        if (zzdo()) {
            Log.d(this.mTag, zza(str, objArr), th);
        }
    }

    public final void i(String str, Object... objArr) {
        Log.i(this.mTag, zza(str, objArr));
    }

    public final void w(String str, Object... objArr) {
        Log.w(this.mTag, zza(str, objArr));
    }

    public final void zzb(Throwable th, String str, Object... objArr) {
        Log.w(this.mTag, zza(str, objArr), th);
    }

    public final void e(String str, Object... objArr) {
        Log.e(this.mTag, zza(str, objArr));
    }

    public final void zzc(Throwable th, String str, Object... objArr) {
        Log.e(this.mTag, zza(str, objArr), th);
    }

    private final String zza(String str, Object... objArr) {
        if (objArr.length != 0) {
            str = String.format(Locale.ROOT, str, objArr);
        }
        if (TextUtils.isEmpty(this.zzxz) != null) {
            return str;
        }
        objArr = String.valueOf(this.zzxz);
        str = String.valueOf(str);
        return str.length() != 0 ? objArr.concat(str) : new String(objArr);
    }
}
