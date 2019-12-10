package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.core.content.PermissionChecker;

final class zzst implements zzsq {
    @GuardedBy("GservicesLoader.class")
    static zzst zzbru;
    private final Context zzri;

    static zzst zzad(Context context) {
        synchronized (zzst.class) {
            if (zzbru == null) {
                zzbru = (PermissionChecker.checkSelfPermission(context, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0 ? 1 : null) != null ? new zzst(context) : new zzst();
            }
            context = zzbru;
        }
        return context;
    }

    private zzst(Context context) {
        this.zzri = context;
        this.zzri.getContentResolver().registerContentObserver(zzsj.CONTENT_URI, true, new zzsv(this, null));
    }

    private zzst() {
        this.zzri = null;
    }

    private final String zzfq(String str) {
        if (this.zzri == null) {
            return null;
        }
        try {
            return (String) zzsr.zza(new zzsu(this, str));
        } catch (Throwable e) {
            String str2 = "GservicesLoader";
            String str3 = "Unable to read GServices for: ";
            str = String.valueOf(str);
            Log.e(str2, str.length() != 0 ? str3.concat(str) : new String(str3), e);
            return null;
        }
    }

    public final /* synthetic */ Object zzfp(String str) {
        return zzfq(str);
    }

    final /* synthetic */ String zzfr(String str) {
        return zzsj.zza(this.zzri.getContentResolver(), str, null);
    }
}
