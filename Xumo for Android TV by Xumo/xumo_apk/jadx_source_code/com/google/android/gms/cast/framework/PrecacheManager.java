package com.google.android.gms.cast.framework;

import androidx.annotation.NonNull;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.internal.cast.zzci;
import com.google.android.gms.internal.cast.zzdh;

public class PrecacheManager {
    private final zzdh zzbe = new zzdh("PrecacheManager");
    private final SessionManager zzhj;
    private final CastOptions zzhn;
    private final zzci zziu;

    public PrecacheManager(@NonNull CastOptions castOptions, @NonNull SessionManager sessionManager, @NonNull zzci com_google_android_gms_internal_cast_zzci) {
        this.zzhn = castOptions;
        this.zzhj = sessionManager;
        this.zziu = com_google_android_gms_internal_cast_zzci;
    }

    public void precache(@NonNull String str) {
        Session currentSession = this.zzhj.getCurrentSession();
        if (str == null) {
            throw new IllegalArgumentException("No precache data found to be precached");
        } else if (currentSession == null) {
            this.zziu.zza(new String[]{this.zzhn.getReceiverApplicationId()}, str, null);
        } else if (currentSession instanceof CastSession) {
            RemoteMediaClient remoteMediaClient = ((CastSession) currentSession).getRemoteMediaClient();
            if (remoteMediaClient != null) {
                remoteMediaClient.zza(str, null);
            } else {
                this.zzbe.e("Failed to get RemoteMediaClient from current cast session.", new Object[0]);
            }
        } else {
            this.zzbe.e("Current session is not a CastSession. Precache is not supported.", new Object[0]);
        }
    }
}
