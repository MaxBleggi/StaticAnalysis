package com.google.android.gms.internal.cast;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.core.view.PointerIconCompat;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.Cast.ApplicationConnectionResult;
import com.google.android.gms.cast.Cast.Listener;
import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.cast.zzad;
import com.google.android.gms.cast.zzag;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.common.internal.BinderWrapper;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs.CastExtraArgs;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public final class zzco extends GmsClient<zzda> {
    private static final zzdh zzbe = new zzdh("CastClientImpl");
    private static final Object zzxa = new Object();
    private static final Object zzxb = new Object();
    private final Bundle extras;
    private final Listener zzaj;
    private double zzex;
    private boolean zzey;
    private final CastDevice zzih;
    private ApplicationMetadata zzwi;
    private final Map<String, MessageReceivedCallback> zzwj = new HashMap();
    private final long zzwk;
    private zzcq zzwl;
    private String zzwm;
    private boolean zzwn;
    private boolean zzwo;
    private boolean zzwp;
    private zzad zzwq;
    private int zzwr;
    private int zzws;
    private final AtomicLong zzwt = new AtomicLong(0);
    private String zzwu;
    private String zzwv;
    private Bundle zzww;
    private final Map<Long, ResultHolder<Status>> zzwx = new HashMap();
    private ResultHolder<ApplicationConnectionResult> zzwy;
    private ResultHolder<Status> zzwz;

    public zzco(Context context, Looper looper, ClientSettings clientSettings, CastDevice castDevice, long j, Listener listener, Bundle bundle, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 10, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzih = castDevice;
        this.zzaj = listener;
        this.zzwk = j;
        this.extras = bundle;
        zzdg();
    }

    public final int getMinApkVersion() {
        return 12800000;
    }

    @NonNull
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.cast.internal.ICastDeviceController";
    }

    @NonNull
    protected final String getStartServiceAction() {
        return "com.google.android.gms.cast.service.BIND_CAST_DEVICE_CONTROLLER_SERVICE";
    }

    private final void zzdg() {
        this.zzwp = false;
        this.zzwr = -1;
        this.zzws = -1;
        this.zzwi = null;
        this.zzwm = null;
        this.zzex = 0.0d;
        this.zzey = false;
        this.zzwq = null;
    }

    protected final void onPostInitHandler(int i, IBinder iBinder, Bundle bundle, int i2) {
        zzbe.d("in onPostInitHandler; statusCode=%d", Integer.valueOf(i));
        if (i != 0) {
            if (i != PointerIconCompat.TYPE_CONTEXT_MENU) {
                this.zzwp = false;
                if (i == PointerIconCompat.TYPE_CONTEXT_MENU) {
                    this.zzww = new Bundle();
                    this.zzww.putBoolean(Cast.EXTRA_APP_NO_LONGER_RUNNING, true);
                    i = 0;
                }
                super.onPostInitHandler(i, iBinder, bundle, i2);
            }
        }
        this.zzwp = true;
        this.zzwn = true;
        this.zzwo = true;
        if (i == PointerIconCompat.TYPE_CONTEXT_MENU) {
            this.zzww = new Bundle();
            this.zzww.putBoolean(Cast.EXTRA_APP_NO_LONGER_RUNNING, true);
            i = 0;
        }
        super.onPostInitHandler(i, iBinder, bundle, i2);
    }

    public final void disconnect() {
        zzbe.d("disconnect(); ServiceListener=%s, isConnected=%b", this.zzwl, Boolean.valueOf(isConnected()));
        zzcq com_google_android_gms_internal_cast_zzcq = this.zzwl;
        this.zzwl = null;
        if (com_google_android_gms_internal_cast_zzcq != null) {
            if (com_google_android_gms_internal_cast_zzcq.zzdk() != null) {
                zzdh();
                try {
                    ((zzda) getService()).disconnect();
                    return;
                } catch (Throwable e) {
                    zzbe.zza(e, "Error while disconnecting the controller interface: %s", e.getMessage());
                    return;
                } finally {
                    super.disconnect();
                }
            }
        }
        zzbe.d("already disposed, so short-circuiting", new Object[0]);
    }

    public final Bundle getConnectionHint() {
        if (this.zzww == null) {
            return super.getConnectionHint();
        }
        Bundle bundle = this.zzww;
        this.zzww = null;
        return bundle;
    }

    protected final Bundle getGetServiceRequestExtraArgs() {
        Bundle bundle = new Bundle();
        zzbe.d("getRemoteService(): mLastApplicationId=%s, mLastSessionId=%s", this.zzwu, this.zzwv);
        this.zzih.putInBundle(bundle);
        bundle.putLong("com.google.android.gms.cast.EXTRA_CAST_FLAGS", this.zzwk);
        if (this.extras != null) {
            bundle.putAll(this.extras);
        }
        this.zzwl = new zzcq(this);
        bundle.putParcelable(CastExtraArgs.LISTENER, new BinderWrapper(this.zzwl.asBinder()));
        if (this.zzwu != null) {
            bundle.putString("last_application_id", this.zzwu);
            if (this.zzwv != null) {
                bundle.putString("last_session_id", this.zzwv);
            }
        }
        return bundle;
    }

    public final void zza(String str, String str2, ResultHolder<Status> resultHolder) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("The message payload cannot be null or empty");
        } else if (str2.length() <= 524288) {
            zzcv.zzo(str);
            long incrementAndGet = this.zzwt.incrementAndGet();
            try {
                this.zzwx.put(Long.valueOf(incrementAndGet), resultHolder);
                zzda com_google_android_gms_internal_cast_zzda = (zzda) getService();
                if (zzdi()) {
                    com_google_android_gms_internal_cast_zzda.zza(str, str2, incrementAndGet);
                } else {
                    zzb(incrementAndGet, (int) CastStatusCodes.DEVICE_CONNECTION_SUSPENDED);
                }
            } catch (Throwable th) {
                this.zzwx.remove(Long.valueOf(incrementAndGet));
            }
        } else {
            zzbe.w("Message send failed. Message exceeds maximum size", new Object[null]);
            throw new IllegalArgumentException("Message exceeds maximum size");
        }
    }

    public final void zza(String str, LaunchOptions launchOptions, ResultHolder<ApplicationConnectionResult> resultHolder) throws IllegalStateException, RemoteException {
        zza((ResultHolder) resultHolder);
        zzda com_google_android_gms_internal_cast_zzda = (zzda) getService();
        if (zzdi()) {
            com_google_android_gms_internal_cast_zzda.zzb(str, launchOptions);
        } else {
            zzl(CastStatusCodes.DEVICE_CONNECTION_SUSPENDED);
        }
    }

    public final void zza(String str, String str2, zzag com_google_android_gms_cast_zzag, ResultHolder<ApplicationConnectionResult> resultHolder) throws IllegalStateException, RemoteException {
        zza((ResultHolder) resultHolder);
        if (com_google_android_gms_cast_zzag == null) {
            com_google_android_gms_cast_zzag = new zzag();
        }
        zzda com_google_android_gms_internal_cast_zzda = (zzda) getService();
        if (zzdi()) {
            com_google_android_gms_internal_cast_zzda.zza(str, str2, com_google_android_gms_cast_zzag);
        } else {
            zzl(CastStatusCodes.DEVICE_CONNECTION_SUSPENDED);
        }
    }

    private final void zza(ResultHolder<ApplicationConnectionResult> resultHolder) {
        synchronized (zzxa) {
            if (this.zzwy != null) {
                this.zzwy.setResult(new zzcp(new Status(CastStatusCodes.CANCELED)));
            }
            this.zzwy = resultHolder;
        }
    }

    public final void zzb(ResultHolder<Status> resultHolder) throws IllegalStateException, RemoteException {
        zzc((ResultHolder) resultHolder);
        zzda com_google_android_gms_internal_cast_zzda = (zzda) getService();
        if (zzdi()) {
            com_google_android_gms_internal_cast_zzda.zzdn();
        } else {
            zzm(CastStatusCodes.DEVICE_CONNECTION_SUSPENDED);
        }
    }

    public final void zza(String str, ResultHolder<Status> resultHolder) throws IllegalStateException, RemoteException {
        zzc((ResultHolder) resultHolder);
        zzda com_google_android_gms_internal_cast_zzda = (zzda) getService();
        if (zzdi()) {
            com_google_android_gms_internal_cast_zzda.zzi(str);
        } else {
            zzm(CastStatusCodes.DEVICE_CONNECTION_SUSPENDED);
        }
    }

    private final void zzc(ResultHolder<Status> resultHolder) {
        synchronized (zzxb) {
            if (this.zzwz != null) {
                resultHolder.setResult(new Status(CastStatusCodes.INVALID_REQUEST));
                return;
            }
            this.zzwz = resultHolder;
        }
    }

    public final void requestStatus() throws IllegalStateException, RemoteException {
        zzda com_google_android_gms_internal_cast_zzda = (zzda) getService();
        if (zzdi()) {
            com_google_android_gms_internal_cast_zzda.requestStatus();
        }
    }

    public final void setVolume(double d) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            StringBuilder stringBuilder = new StringBuilder(41);
            stringBuilder.append("Volume cannot be ");
            stringBuilder.append(d);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        zzda com_google_android_gms_internal_cast_zzda = (zzda) getService();
        if (zzdi()) {
            com_google_android_gms_internal_cast_zzda.zza(d, this.zzex, this.zzey);
        }
    }

    public final void setMute(boolean z) throws IllegalStateException, RemoteException {
        zzda com_google_android_gms_internal_cast_zzda = (zzda) getService();
        if (zzdi()) {
            com_google_android_gms_internal_cast_zzda.zza(z, this.zzex, this.zzey);
        }
    }

    public final double getVolume() throws IllegalStateException {
        checkConnected();
        return this.zzex;
    }

    public final boolean isMute() throws IllegalStateException {
        checkConnected();
        return this.zzey;
    }

    public final int getActiveInputState() throws IllegalStateException {
        checkConnected();
        return this.zzwr;
    }

    public final int getStandbyState() throws IllegalStateException {
        checkConnected();
        return this.zzws;
    }

    public final void setMessageReceivedCallbacks(String str, MessageReceivedCallback messageReceivedCallback) throws IllegalArgumentException, IllegalStateException, RemoteException {
        zzcv.zzo(str);
        removeMessageReceivedCallbacks(str);
        if (messageReceivedCallback != null) {
            synchronized (this.zzwj) {
                this.zzwj.put(str, messageReceivedCallback);
            }
            zzda com_google_android_gms_internal_cast_zzda = (zzda) getService();
            if (zzdi()) {
                com_google_android_gms_internal_cast_zzda.zzr(str);
            }
        }
    }

    public final void removeMessageReceivedCallbacks(String str) throws IllegalArgumentException, RemoteException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Channel namespace cannot be null or empty");
        }
        synchronized (this.zzwj) {
            MessageReceivedCallback messageReceivedCallback = (MessageReceivedCallback) this.zzwj.remove(str);
        }
        if (messageReceivedCallback != null) {
            try {
                ((zzda) getService()).zzs(str);
            } catch (Throwable e) {
                zzbe.zza(e, "Error unregistering namespace (%s): %s", str, e.getMessage());
            }
        }
    }

    public final ApplicationMetadata getApplicationMetadata() throws IllegalStateException {
        checkConnected();
        return this.zzwi;
    }

    public final String getApplicationStatus() throws IllegalStateException {
        checkConnected();
        return this.zzwm;
    }

    public final void onConnectionFailed(ConnectionResult connectionResult) {
        super.onConnectionFailed(connectionResult);
        zzdh();
    }

    private final void zzdh() {
        zzbe.d("removing all MessageReceivedCallbacks", new Object[0]);
        synchronized (this.zzwj) {
            this.zzwj.clear();
        }
    }

    private final void zza(zzcw com_google_android_gms_internal_cast_zzcw) {
        boolean z;
        ApplicationMetadata applicationMetadata = com_google_android_gms_internal_cast_zzcw.getApplicationMetadata();
        if (!zzcv.zza(applicationMetadata, this.zzwi)) {
            this.zzwi = applicationMetadata;
            this.zzaj.onApplicationMetadataChanged(this.zzwi);
        }
        double volume = com_google_android_gms_internal_cast_zzcw.getVolume();
        if (Double.isNaN(volume) || Math.abs(volume - this.zzex) <= 1.0E-7d) {
            z = false;
        } else {
            this.zzex = volume;
            z = true;
        }
        boolean zzdl = com_google_android_gms_internal_cast_zzcw.zzdl();
        if (zzdl != this.zzey) {
            this.zzey = zzdl;
            z = true;
        }
        zzbe.d("hasVolumeChanged=%b, mFirstDeviceStatusUpdate=%b", Boolean.valueOf(z), Boolean.valueOf(this.zzwo));
        if (this.zzaj != null && (z || this.zzwo)) {
            this.zzaj.onVolumeChanged();
        }
        int activeInputState = com_google_android_gms_internal_cast_zzcw.getActiveInputState();
        if (activeInputState != this.zzwr) {
            this.zzwr = activeInputState;
            z = true;
        } else {
            z = false;
        }
        zzbe.d("hasActiveInputChanged=%b, mFirstDeviceStatusUpdate=%b", Boolean.valueOf(z), Boolean.valueOf(this.zzwo));
        if (this.zzaj != null && (z || this.zzwo)) {
            this.zzaj.onActiveInputStateChanged(this.zzwr);
        }
        activeInputState = com_google_android_gms_internal_cast_zzcw.getStandbyState();
        if (activeInputState != this.zzws) {
            this.zzws = activeInputState;
            z = true;
        } else {
            z = false;
        }
        zzbe.d("hasStandbyStateChanged=%b, mFirstDeviceStatusUpdate=%b", Boolean.valueOf(z), Boolean.valueOf(this.zzwo));
        if (this.zzaj != null && (z || this.zzwo)) {
            this.zzaj.onStandbyStateChanged(this.zzws);
        }
        if (!zzcv.zza(this.zzwq, com_google_android_gms_internal_cast_zzcw.zzdm())) {
            this.zzwq = com_google_android_gms_internal_cast_zzcw.zzdm();
        }
        this.zzwo = false;
    }

    private final void zza(zzce com_google_android_gms_internal_cast_zzce) {
        com_google_android_gms_internal_cast_zzce = com_google_android_gms_internal_cast_zzce.zzdc();
        if (zzcv.zza(com_google_android_gms_internal_cast_zzce, this.zzwm)) {
            com_google_android_gms_internal_cast_zzce = null;
        } else {
            this.zzwm = com_google_android_gms_internal_cast_zzce;
            com_google_android_gms_internal_cast_zzce = true;
        }
        zzbe.d("hasChanged=%b, mFirstApplicationStatusUpdate=%b", Boolean.valueOf(com_google_android_gms_internal_cast_zzce), Boolean.valueOf(this.zzwn));
        if (!(this.zzaj == null || (com_google_android_gms_internal_cast_zzce == null && this.zzwn == null))) {
            this.zzaj.onApplicationStatusChanged();
        }
        this.zzwn = false;
    }

    @VisibleForTesting
    private final boolean zzdi() {
        return (!this.zzwp || this.zzwl == null || this.zzwl.isDisposed()) ? false : true;
    }

    private final void zzb(long j, int i) {
        synchronized (this.zzwx) {
            ResultHolder resultHolder = (ResultHolder) this.zzwx.remove(Long.valueOf(j));
        }
        if (resultHolder != null) {
            resultHolder.setResult(new Status(i));
        }
    }

    public final void zzl(int i) {
        synchronized (zzxa) {
            if (this.zzwy != null) {
                this.zzwy.setResult(new zzcp(new Status(i)));
                this.zzwy = 0;
            }
        }
    }

    private final void zzm(int i) {
        synchronized (zzxb) {
            if (this.zzwz != null) {
                this.zzwz.setResult(new Status(i));
                this.zzwz = 0;
            }
        }
    }

    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.cast.internal.ICastDeviceController");
        if (queryLocalInterface instanceof zzda) {
            return (zzda) queryLocalInterface;
        }
        return new zzdb(iBinder);
    }
}
