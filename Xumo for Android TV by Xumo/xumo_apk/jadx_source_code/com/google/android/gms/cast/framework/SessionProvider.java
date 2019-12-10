package com.google.android.gms.cast.framework;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;

public abstract class SessionProvider {
    private final String category;
    private final Context zzjd;
    private final zza zzje = new zza();

    private class zza extends zzaa {
        private final /* synthetic */ SessionProvider zzjf;

        private zza(SessionProvider sessionProvider) {
            this.zzjf = sessionProvider;
        }

        public final int zzm() {
            return 12451009;
        }

        public final IObjectWrapper zzj(String str) {
            str = this.zzjf.createSession(str);
            if (str == null) {
                return null;
            }
            return str.zzz();
        }

        public final boolean isSessionRecoverable() {
            return this.zzjf.isSessionRecoverable();
        }

        public final String getCategory() {
            return this.zzjf.getCategory();
        }
    }

    protected SessionProvider(Context context, String str) {
        this.zzjd = ((Context) Preconditions.checkNotNull(context)).getApplicationContext();
        this.category = Preconditions.checkNotEmpty(str);
    }

    public abstract Session createSession(String str);

    public abstract boolean isSessionRecoverable();

    public final Context getContext() {
        return this.zzjd;
    }

    public final String getCategory() {
        return this.category;
    }

    public final IBinder zzaj() {
        return this.zzje;
    }
}
