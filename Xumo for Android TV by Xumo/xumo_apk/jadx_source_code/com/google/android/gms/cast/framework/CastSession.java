package com.google.android.gms.cast.framework;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.Cast.ApplicationConnectionResult;
import com.google.android.gms.cast.Cast.CastApi;
import com.google.android.gms.cast.Cast.CastOptions;
import com.google.android.gms.cast.Cast.Listener;
import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.cast.zzai;
import com.google.android.gms.internal.cast.zzdh;
import com.google.android.gms.internal.cast.zzdi;
import com.google.android.gms.internal.cast.zze;
import com.google.android.gms.internal.cast.zzg;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CastSession extends Session {
    private static final zzdh zzbe = new zzdh("CastSession");
    private final Context zzhh;
    private final CastOptions zzhn;
    private final Set<Listener> zzia = new HashSet();
    private final zzl zzib;
    private final CastApi zzic;
    private final zzg zzid;
    private final zzai zzie;
    private GoogleApiClient zzif;
    private RemoteMediaClient zzig;
    private CastDevice zzih;
    private ApplicationConnectionResult zzii;

    private class zza implements ResultCallback<ApplicationConnectionResult> {
        private String command;
        private final /* synthetic */ CastSession zzij;

        zza(CastSession castSession, String str) {
            this.zzij = castSession;
            this.command = str;
        }

        public final /* synthetic */ void onResult(@NonNull Result result) {
            ApplicationConnectionResult applicationConnectionResult = (ApplicationConnectionResult) result;
            this.zzij.zzii = applicationConnectionResult;
            try {
                if (applicationConnectionResult.getStatus().isSuccess()) {
                    CastSession.zzbe.d("%s() -> success result", this.command);
                    this.zzij.zzig = new RemoteMediaClient(new zzdi(null), this.zzij.zzic);
                    try {
                        this.zzij.zzig.zzb(this.zzij.zzif);
                        this.zzij.zzig.zzcb();
                        this.zzij.zzig.requestStatus();
                        this.zzij.zzie.zza(this.zzij.zzig, this.zzij.getCastDevice());
                    } catch (Throwable e) {
                        CastSession.zzbe.zzc(e, "Exception when setting GoogleApiClient.", new Object[0]);
                        this.zzij.zzig = null;
                    }
                    this.zzij.zzib.zza(applicationConnectionResult.getApplicationMetadata(), applicationConnectionResult.getApplicationStatus(), applicationConnectionResult.getSessionId(), applicationConnectionResult.getWasLaunched());
                    return;
                }
                CastSession.zzbe.d("%s() -> failure result", this.command);
                this.zzij.zzib.zzf(applicationConnectionResult.getStatus().getStatusCode());
            } catch (Result result2) {
                CastSession.zzbe.zza(result2, "Unable to call %s on %s.", "methods", zzl.class.getSimpleName());
            }
        }
    }

    private class zzc extends Listener {
        private final /* synthetic */ CastSession zzij;

        private zzc(CastSession castSession) {
            this.zzij = castSession;
        }

        public final void onApplicationDisconnected(int i) {
            this.zzij.zze(i);
            this.zzij.notifySessionEnded(i);
            for (Listener onApplicationDisconnected : new HashSet(this.zzij.zzia)) {
                onApplicationDisconnected.onApplicationDisconnected(i);
            }
        }

        public final void onApplicationStatusChanged() {
            for (Listener onApplicationStatusChanged : new HashSet(this.zzij.zzia)) {
                onApplicationStatusChanged.onApplicationStatusChanged();
            }
        }

        public final void onApplicationMetadataChanged(ApplicationMetadata applicationMetadata) {
            for (Listener onApplicationMetadataChanged : new HashSet(this.zzij.zzia)) {
                onApplicationMetadataChanged.onApplicationMetadataChanged(applicationMetadata);
            }
        }

        public final void onActiveInputStateChanged(int i) {
            for (Listener onActiveInputStateChanged : new HashSet(this.zzij.zzia)) {
                onActiveInputStateChanged.onActiveInputStateChanged(i);
            }
        }

        public final void onStandbyStateChanged(int i) {
            for (Listener onStandbyStateChanged : new HashSet(this.zzij.zzia)) {
                onStandbyStateChanged.onStandbyStateChanged(i);
            }
        }

        public final void onVolumeChanged() {
            for (Listener onVolumeChanged : new HashSet(this.zzij.zzia)) {
                onVolumeChanged.onVolumeChanged();
            }
        }
    }

    private class zzd implements ConnectionCallbacks, OnConnectionFailedListener {
        private final /* synthetic */ CastSession zzij;

        private zzd(CastSession castSession) {
            this.zzij = castSession;
        }

        public final void onConnected(Bundle bundle) {
            try {
                if (this.zzij.zzig != null) {
                    try {
                        this.zzij.zzig.zzcb();
                        this.zzij.zzig.requestStatus();
                    } catch (Throwable e) {
                        CastSession.zzbe.zzc(e, "Exception when setting GoogleApiClient.", new Object[0]);
                        this.zzij.zzig = null;
                    }
                }
                this.zzij.zzib.onConnected(bundle);
            } catch (Bundle bundle2) {
                CastSession.zzbe.zza(bundle2, "Unable to call %s on %s.", "onConnected", zzl.class.getSimpleName());
            }
        }

        public final void onConnectionSuspended(int i) {
            try {
                this.zzij.zzib.onConnectionSuspended(i);
            } catch (int i2) {
                CastSession.zzbe.zza(i2, "Unable to call %s on %s.", "onConnectionSuspended", zzl.class.getSimpleName());
            }
        }

        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            try {
                this.zzij.zzib.onConnectionFailed(connectionResult);
            } catch (ConnectionResult connectionResult2) {
                CastSession.zzbe.zza(connectionResult2, "Unable to call %s on %s.", "onConnectionFailed", zzl.class.getSimpleName());
            }
        }
    }

    private class zzb extends zzi {
        private final /* synthetic */ CastSession zzij;

        private zzb(CastSession castSession) {
            this.zzij = castSession;
        }

        public final int zzm() {
            return 12451009;
        }

        public final void zza(String str, String str2) {
            if (this.zzij.zzif != null) {
                this.zzij.zzic.joinApplication(this.zzij.zzif, str, str2).setResultCallback(new zza(this.zzij, "joinApplication"));
            }
        }

        public final void zza(String str, LaunchOptions launchOptions) {
            if (this.zzij.zzif != null) {
                this.zzij.zzic.launchApplication(this.zzij.zzif, str, launchOptions).setResultCallback(new zza(this.zzij, "launchApplication"));
            }
        }

        public final void zzi(String str) {
            if (this.zzij.zzif != null) {
                this.zzij.zzic.stopApplication(this.zzij.zzif, str);
            }
        }

        public final void zze(int i) {
            this.zzij.zze(i);
        }
    }

    public CastSession(Context context, String str, String str2, CastOptions castOptions, CastApi castApi, zzg com_google_android_gms_internal_cast_zzg, zzai com_google_android_gms_internal_cast_zzai) {
        super(context, str, str2);
        this.zzhh = context.getApplicationContext();
        this.zzhn = castOptions;
        this.zzic = castApi;
        this.zzid = com_google_android_gms_internal_cast_zzg;
        this.zzie = com_google_android_gms_internal_cast_zzai;
        this.zzib = zze.zza(context, castOptions, zzz(), new zzb());
    }

    protected void onStarting(Bundle bundle) {
        this.zzih = CastDevice.getFromBundle(bundle);
    }

    protected void onResuming(Bundle bundle) {
        this.zzih = CastDevice.getFromBundle(bundle);
    }

    protected void start(Bundle bundle) {
        zzb(bundle);
    }

    protected void resume(Bundle bundle) {
        zzb(bundle);
    }

    protected void end(boolean z) {
        try {
            this.zzib.zza(z, 0);
        } catch (boolean z2) {
            zzbe.zza(z2, "Unable to call %s on %s.", "disconnectFromDevice", zzl.class.getSimpleName());
        }
        notifySessionEnded(0);
    }

    public RemoteMediaClient getRemoteMediaClient() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzig;
    }

    public CastDevice getCastDevice() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzih;
    }

    public void requestStatus() throws IOException, IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (this.zzif != null) {
            this.zzic.requestStatus(this.zzif);
        }
    }

    public int getActiveInputState() throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzif != null ? this.zzic.getActiveInputState(this.zzif) : -1;
    }

    public int getStandbyState() throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzif != null ? this.zzic.getStandbyState(this.zzif) : -1;
    }

    public ApplicationMetadata getApplicationMetadata() throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzif != null ? this.zzic.getApplicationMetadata(this.zzif) : null;
    }

    public String getApplicationStatus() throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzif != null ? this.zzic.getApplicationStatus(this.zzif) : null;
    }

    public void setVolume(double d) throws IOException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (this.zzif != null) {
            this.zzic.setVolume(this.zzif, d);
        }
    }

    public double getVolume() throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzif != null ? this.zzic.getVolume(this.zzif) : 0.0d;
    }

    public final zzai zzs() {
        return this.zzie;
    }

    public void setMute(boolean z) throws IOException, IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (this.zzif != null) {
            this.zzic.setMute(this.zzif, z);
        }
    }

    public boolean isMute() throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzif != null ? this.zzic.isMute(this.zzif) : false;
    }

    public ApplicationConnectionResult getApplicationConnectionResult() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzii;
    }

    public void setMessageReceivedCallbacks(String str, MessageReceivedCallback messageReceivedCallback) throws IOException, IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (this.zzif != null) {
            this.zzic.setMessageReceivedCallbacks(this.zzif, str, messageReceivedCallback);
        }
    }

    public void removeMessageReceivedCallbacks(String str) throws IOException, IllegalArgumentException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (this.zzif != null) {
            this.zzic.removeMessageReceivedCallbacks(this.zzif, str);
        }
    }

    public PendingResult<Status> sendMessage(String str, String str2) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzif != null ? this.zzic.sendMessage(this.zzif, str, str2) : null;
    }

    public void addCastListener(Listener listener) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (listener != null) {
            this.zzia.add(listener);
        }
    }

    public void removeCastListener(Listener listener) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (listener != null) {
            this.zzia.remove(listener);
        }
    }

    private final void zzb(Bundle bundle) {
        this.zzih = CastDevice.getFromBundle(bundle);
        if (this.zzih != null) {
            if (this.zzif != null) {
                this.zzif.disconnect();
                this.zzif = null;
            }
            boolean z = true;
            zzbe.d("Acquiring a connection to Google Play Services for %s", this.zzih);
            bundle = new zzd();
            Context context = this.zzhh;
            CastDevice castDevice = this.zzih;
            CastOptions castOptions = this.zzhn;
            Listener com_google_android_gms_cast_framework_CastSession_zzc = new zzc();
            Bundle bundle2 = new Bundle();
            String str = "com.google.android.gms.cast.EXTRA_CAST_FRAMEWORK_NOTIFICATION_ENABLED";
            boolean z2 = (castOptions == null || castOptions.getCastMediaOptions() == null || castOptions.getCastMediaOptions().getNotificationOptions() == null) ? false : true;
            bundle2.putBoolean(str, z2);
            str = "com.google.android.gms.cast.EXTRA_CAST_REMOTE_CONTROL_NOTIFICATION_ENABLED";
            if (castOptions == null || castOptions.getCastMediaOptions() == null || !castOptions.getCastMediaOptions().zzaw()) {
                z = false;
            }
            bundle2.putBoolean(str, z);
            this.zzif = new Builder(context).addApi(Cast.API, new CastOptions.Builder(castDevice, com_google_android_gms_cast_framework_CastSession_zzc).zza(bundle2).build()).addConnectionCallbacks(bundle).addOnConnectionFailedListener(bundle).build();
            this.zzif.connect();
        } else if (isResuming() != null) {
            notifyFailedToResumeSession(8);
        } else {
            notifyFailedToStartSession(8);
        }
    }

    private final void zze(int i) {
        this.zzie.zzi(i);
        if (this.zzif != 0) {
            this.zzif.disconnect();
            this.zzif = null;
        }
        this.zzih = null;
        if (this.zzig != 0) {
            this.zzig.zzb(null);
            this.zzig = null;
        }
        this.zzii = null;
    }

    public long getSessionRemainingTimeMs() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (this.zzig == null) {
            return 0;
        }
        return this.zzig.getStreamDuration() - this.zzig.getApproximateStreamPosition();
    }
}
