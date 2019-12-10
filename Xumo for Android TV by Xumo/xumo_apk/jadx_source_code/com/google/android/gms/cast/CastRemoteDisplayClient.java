package com.google.android.gms.cast;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.hardware.display.VirtualDisplay;
import android.os.RemoteException;
import android.view.Display;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.cast.CastRemoteDisplay.Configuration;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.Settings;
import com.google.android.gms.internal.cast.zzdg;
import com.google.android.gms.internal.cast.zzdh;
import com.google.android.gms.internal.cast.zzeb;
import com.google.android.gms.internal.cast.zzef;
import com.google.android.gms.tasks.Task;

@TargetApi(19)
public class CastRemoteDisplayClient extends GoogleApi<NoOptions> {
    private static final Api<NoOptions> API = new Api("CastRemoteDisplay.API", CLIENT_BUILDER, zzdg.zzxq);
    private static final AbstractClientBuilder<zzeb, NoOptions> CLIENT_BUILDER = new zzp();
    private final zzdh zzbe = new zzdh("CastRemoteDisplay");
    private VirtualDisplay zzbf;

    private static class zza extends zzef {
        private zza() {
        }

        public void zza(int i, int i2, Surface surface) throws RemoteException {
            throw new UnsupportedOperationException();
        }

        public void onError(int i) throws RemoteException {
            throw new UnsupportedOperationException();
        }

        public void onDisconnected() throws RemoteException {
            throw new UnsupportedOperationException();
        }

        public void zzc() throws RemoteException {
            throw new UnsupportedOperationException();
        }
    }

    CastRemoteDisplayClient(@NonNull Context context) {
        super(context, API, null, Settings.DEFAULT_SETTINGS);
    }

    public Task<Display> startRemoteDisplay(@NonNull CastDevice castDevice, @NonNull String str, @Configuration int i, @Nullable PendingIntent pendingIntent) {
        return doWrite(new zzq(this, i, pendingIntent, castDevice, str));
    }

    private static int zza(int i, int i2) {
        return (Math.min(i, i2) * 320) / 1080;
    }

    @TargetApi(19)
    private final void zzb() {
        if (this.zzbf != null) {
            if (this.zzbf.getDisplay() != null) {
                zzdh com_google_android_gms_internal_cast_zzdh = this.zzbe;
                int displayId = this.zzbf.getDisplay().getDisplayId();
                StringBuilder stringBuilder = new StringBuilder(38);
                stringBuilder.append("releasing virtual display: ");
                stringBuilder.append(displayId);
                com_google_android_gms_internal_cast_zzdh.d(stringBuilder.toString(), new Object[0]);
            }
            this.zzbf.release();
            this.zzbf = null;
        }
    }

    public Task<Void> stopRemoteDisplay() {
        return doWrite(new zzs(this));
    }
}
