package com.google.android.gms.internal.cast;

import android.os.Handler;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.concurrent.atomic.AtomicReference;

@VisibleForTesting
final class zzcq extends zzdd {
    private final Handler handler;
    private final AtomicReference<zzco> zzxg;

    public zzcq(zzco com_google_android_gms_internal_cast_zzco) {
        this.zzxg = new AtomicReference(com_google_android_gms_internal_cast_zzco);
        this.handler = new zzek(com_google_android_gms_internal_cast_zzco.getLooper());
    }

    public final zzco zzdk() {
        zzco com_google_android_gms_internal_cast_zzco = (zzco) this.zzxg.getAndSet(null);
        if (com_google_android_gms_internal_cast_zzco == null) {
            return null;
        }
        com_google_android_gms_internal_cast_zzco.zzdg();
        return com_google_android_gms_internal_cast_zzco;
    }

    public final boolean isDisposed() {
        return this.zzxg.get() == null;
    }

    public final void zzn(int i) {
        zzco zzdk = zzdk();
        if (zzdk != null) {
            zzco.zzbe.d("ICastDeviceControllerListener.onDisconnected: %d", Integer.valueOf(i));
            if (i != 0) {
                zzdk.triggerConnectionSuspended(2);
            }
        }
    }

    public final void zza(ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
        zzco com_google_android_gms_internal_cast_zzco = (zzco) this.zzxg.get();
        if (com_google_android_gms_internal_cast_zzco != null) {
            com_google_android_gms_internal_cast_zzco.zzwi = applicationMetadata;
            com_google_android_gms_internal_cast_zzco.zzwu = applicationMetadata.getApplicationId();
            com_google_android_gms_internal_cast_zzco.zzwv = str2;
            com_google_android_gms_internal_cast_zzco.zzwm = str;
            synchronized (zzco.zzxa) {
                if (com_google_android_gms_internal_cast_zzco.zzwy != null) {
                    com_google_android_gms_internal_cast_zzco.zzwy.setResult(new zzcp(new Status(0), applicationMetadata, str, str2, z));
                    com_google_android_gms_internal_cast_zzco.zzwy = null;
                }
            }
        }
    }

    public final void zzf(int i) {
        zzco com_google_android_gms_internal_cast_zzco = (zzco) this.zzxg.get();
        if (com_google_android_gms_internal_cast_zzco != null) {
            com_google_android_gms_internal_cast_zzco.zzl(i);
        }
    }

    public final void zzo(int i) {
        zzco com_google_android_gms_internal_cast_zzco = (zzco) this.zzxg.get();
        if (com_google_android_gms_internal_cast_zzco != null) {
            com_google_android_gms_internal_cast_zzco.zzm(i);
        }
    }

    public final void zzp(int i) {
        zzco com_google_android_gms_internal_cast_zzco = (zzco) this.zzxg.get();
        if (com_google_android_gms_internal_cast_zzco != null) {
            com_google_android_gms_internal_cast_zzco.zzm(i);
        }
    }

    public final void onApplicationDisconnected(int i) {
        zzco com_google_android_gms_internal_cast_zzco = (zzco) this.zzxg.get();
        if (com_google_android_gms_internal_cast_zzco != null) {
            com_google_android_gms_internal_cast_zzco.zzwu = null;
            com_google_android_gms_internal_cast_zzco.zzwv = null;
            com_google_android_gms_internal_cast_zzco.zzm(i);
            if (com_google_android_gms_internal_cast_zzco.zzaj != null) {
                this.handler.post(new zzcr(this, com_google_android_gms_internal_cast_zzco, i));
            }
        }
    }

    public final void zza(String str, double d, boolean z) {
        zzco.zzbe.d("Deprecated callback: \"onStatusreceived\"", new Object[0]);
    }

    public final void zzb(zzcw com_google_android_gms_internal_cast_zzcw) {
        zzco com_google_android_gms_internal_cast_zzco = (zzco) this.zzxg.get();
        if (com_google_android_gms_internal_cast_zzco != null) {
            zzco.zzbe.d("onDeviceStatusChanged", new Object[0]);
            this.handler.post(new zzcs(this, com_google_android_gms_internal_cast_zzco, com_google_android_gms_internal_cast_zzcw));
        }
    }

    public final void zzb(zzce com_google_android_gms_internal_cast_zzce) {
        zzco com_google_android_gms_internal_cast_zzco = (zzco) this.zzxg.get();
        if (com_google_android_gms_internal_cast_zzco != null) {
            zzco.zzbe.d("onApplicationStatusChanged", new Object[0]);
            this.handler.post(new zzct(this, com_google_android_gms_internal_cast_zzco, com_google_android_gms_internal_cast_zzce));
        }
    }

    public final void zzb(String str, String str2) {
        zzco com_google_android_gms_internal_cast_zzco = (zzco) this.zzxg.get();
        if (com_google_android_gms_internal_cast_zzco != null) {
            zzco.zzbe.d("Receive (type=text, ns=%s) %s", str, str2);
            this.handler.post(new zzcu(this, com_google_android_gms_internal_cast_zzco, str, str2));
        }
    }

    public final void zza(String str, byte[] bArr) {
        if (((zzco) this.zzxg.get()) != null) {
            zzco.zzbe.d("IGNORING: Receive (type=binary, ns=%s) <%d bytes>", str, Integer.valueOf(bArr.length));
        }
    }

    public final void zza(String str, long j, int i) {
        zzco com_google_android_gms_internal_cast_zzco = (zzco) this.zzxg.get();
        if (com_google_android_gms_internal_cast_zzco != null) {
            com_google_android_gms_internal_cast_zzco.zzb(j, i);
        }
    }

    public final void zza(String str, long j) {
        zzco com_google_android_gms_internal_cast_zzco = (zzco) this.zzxg.get();
        if (com_google_android_gms_internal_cast_zzco != null) {
            com_google_android_gms_internal_cast_zzco.zzb(j, 0);
        }
    }
}
