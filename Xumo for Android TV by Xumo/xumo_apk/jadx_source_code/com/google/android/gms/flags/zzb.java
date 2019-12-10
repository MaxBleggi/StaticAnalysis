package com.google.android.gms.flags;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor;

public final class zzb {
    private boolean zzj = false;
    private zzc zzk = null;

    public final void initialize(Context context) {
        synchronized (this) {
            if (this.zzj) {
                return;
            }
            try {
                this.zzk = zzd.asInterface(DynamiteModule.load(context, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING, ModuleDescriptor.MODULE_ID).instantiate("com.google.android.gms.flags.impl.FlagProviderImpl"));
                this.zzk.init(ObjectWrapper.wrap(context));
                this.zzj = true;
            } catch (Context context2) {
                Log.w("FlagValueProvider", "Failed to initialize flags module.", context2);
            }
        }
    }

    public final <T> T zzb(Flag<T> flag) {
        synchronized (this) {
            if (this.zzj) {
                return flag.zza(this.zzk);
            }
            flag = flag.zzb();
            return flag;
        }
    }
}
