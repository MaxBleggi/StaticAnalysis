package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@VisibleForTesting
public class zzch extends zzcn {
    private final List<zzdo> zzwd = Collections.synchronizedList(new ArrayList());

    public zzch(String str, String str2, String str3) {
        super(str, str2, null);
    }

    public void zzdd() {
        synchronized (this.zzwd) {
            for (zzdo zzq : this.zzwd) {
                zzq.zzq(CastStatusCodes.CANCELED);
            }
        }
    }

    protected final List<zzdo> zzde() {
        return this.zzwd;
    }

    protected final void zza(zzdo com_google_android_gms_internal_cast_zzdo) {
        this.zzwd.add(com_google_android_gms_internal_cast_zzdo);
    }
}
