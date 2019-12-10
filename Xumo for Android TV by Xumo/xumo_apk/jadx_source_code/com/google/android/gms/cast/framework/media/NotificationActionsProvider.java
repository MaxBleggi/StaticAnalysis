package com.google.android.gms.cast.framework.media;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.util.List;

public abstract class NotificationActionsProvider {
    private final Context zzhh;
    private final zzd zznn = new zza();

    private class zza extends com.google.android.gms.cast.framework.media.zzd.zza {
        private final /* synthetic */ NotificationActionsProvider zzno;

        private zza(NotificationActionsProvider notificationActionsProvider) {
            this.zzno = notificationActionsProvider;
        }

        public final int zzm() {
            return 12451009;
        }

        public final IObjectWrapper zzax() {
            return ObjectWrapper.wrap(this.zzno);
        }

        public final List<NotificationAction> getNotificationActions() {
            return this.zzno.getNotificationActions();
        }

        public final int[] getCompactViewActionIndices() {
            return this.zzno.getCompactViewActionIndices();
        }
    }

    public NotificationActionsProvider(@NonNull Context context) {
        this.zzhh = context.getApplicationContext();
    }

    public abstract int[] getCompactViewActionIndices();

    public abstract List<NotificationAction> getNotificationActions();

    public Context getApplicationContext() {
        return this.zzhh;
    }

    public final zzd zzbl() {
        return this.zznn;
    }
}
