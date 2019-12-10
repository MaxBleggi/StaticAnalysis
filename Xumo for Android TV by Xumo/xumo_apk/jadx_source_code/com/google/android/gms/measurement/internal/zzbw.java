package com.google.android.gms.measurement.internal;

import android.os.Binder;
import android.text.TextUtils;
import androidx.annotation.BinderThread;
import androidx.annotation.Nullable;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class zzbw extends zzai {
    private final zzfk zzang;
    private Boolean zzaqt;
    @Nullable
    private String zzaqu;

    public zzbw(zzfk com_google_android_gms_measurement_internal_zzfk) {
        this(com_google_android_gms_measurement_internal_zzfk, null);
    }

    private zzbw(zzfk com_google_android_gms_measurement_internal_zzfk, @Nullable String str) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzfk);
        this.zzang = com_google_android_gms_measurement_internal_zzfk;
        this.zzaqu = null;
    }

    @BinderThread
    public final void zzb(zzi com_google_android_gms_measurement_internal_zzi) {
        zzb(com_google_android_gms_measurement_internal_zzi, false);
        zze(new zzbx(this, com_google_android_gms_measurement_internal_zzi));
    }

    @BinderThread
    public final void zza(zzae com_google_android_gms_measurement_internal_zzae, zzi com_google_android_gms_measurement_internal_zzi) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzae);
        zzb(com_google_android_gms_measurement_internal_zzi, false);
        zze(new zzch(this, com_google_android_gms_measurement_internal_zzae, com_google_android_gms_measurement_internal_zzi));
    }

    @VisibleForTesting
    final zzae zzb(zzae com_google_android_gms_measurement_internal_zzae, zzi com_google_android_gms_measurement_internal_zzi) {
        Object obj = null;
        if ("_cmp".equals(com_google_android_gms_measurement_internal_zzae.name) && com_google_android_gms_measurement_internal_zzae.zzaig != null) {
            if (com_google_android_gms_measurement_internal_zzae.zzaig.size() != 0) {
                CharSequence string = com_google_android_gms_measurement_internal_zzae.zzaig.getString("_cis");
                if (!TextUtils.isEmpty(string) && (("referrer broadcast".equals(string) || "referrer API".equals(string)) && this.zzang.zzgv().zzbg(com_google_android_gms_measurement_internal_zzi.packageName) != null)) {
                    obj = 1;
                }
            }
        }
        if (obj == null) {
            return com_google_android_gms_measurement_internal_zzae;
        }
        this.zzang.zzgt().zzjm().zzg("Event has been filtered ", com_google_android_gms_measurement_internal_zzae.toString());
        return new zzae("_cmpx", com_google_android_gms_measurement_internal_zzae.zzaig, com_google_android_gms_measurement_internal_zzae.origin, com_google_android_gms_measurement_internal_zzae.zzais);
    }

    @BinderThread
    public final void zza(zzae com_google_android_gms_measurement_internal_zzae, String str, String str2) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzae);
        Preconditions.checkNotEmpty(str);
        zzc(str, true);
        zze(new zzci(this, com_google_android_gms_measurement_internal_zzae, str));
    }

    @BinderThread
    public final byte[] zza(zzae com_google_android_gms_measurement_internal_zzae, String str) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzae);
        zzc(str, true);
        this.zzang.zzgt().zzjn().zzg("Log and bundle. event", this.zzang.zzgq().zzbv(com_google_android_gms_measurement_internal_zzae.name));
        long nanoTime = this.zzang.zzbx().nanoTime() / 1000000;
        try {
            byte[] bArr = (byte[]) this.zzang.zzgs().zzc(new zzcj(this, com_google_android_gms_measurement_internal_zzae, str)).get();
            if (bArr == null) {
                this.zzang.zzgt().zzjg().zzg("Log and bundle returned null. appId", zzaq.zzby(str));
                bArr = new byte[0];
            }
            this.zzang.zzgt().zzjn().zzd("Log and bundle processed. event, size, time_ms", this.zzang.zzgq().zzbv(com_google_android_gms_measurement_internal_zzae.name), Integer.valueOf(bArr.length), Long.valueOf((this.zzang.zzbx().nanoTime() / 1000000) - nanoTime));
            return bArr;
        } catch (InterruptedException e) {
            this.zzang.zzgt().zzjg().zzd("Failed to log and bundle. appId, event, error", zzaq.zzby(str), this.zzang.zzgq().zzbv(com_google_android_gms_measurement_internal_zzae.name), e);
            return null;
        }
    }

    @BinderThread
    public final void zza(zzfr com_google_android_gms_measurement_internal_zzfr, zzi com_google_android_gms_measurement_internal_zzi) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzfr);
        zzb(com_google_android_gms_measurement_internal_zzi, false);
        if (com_google_android_gms_measurement_internal_zzfr.getValue() == null) {
            zze(new zzck(this, com_google_android_gms_measurement_internal_zzfr, com_google_android_gms_measurement_internal_zzi));
        } else {
            zze(new zzcl(this, com_google_android_gms_measurement_internal_zzfr, com_google_android_gms_measurement_internal_zzi));
        }
    }

    @BinderThread
    public final List<zzfr> zza(zzi com_google_android_gms_measurement_internal_zzi, boolean z) {
        zzb(com_google_android_gms_measurement_internal_zzi, false);
        try {
            List<zzft> list = (List) this.zzang.zzgs().zzb(new zzcm(this, com_google_android_gms_measurement_internal_zzi)).get();
            List<zzfr> arrayList = new ArrayList(list.size());
            for (zzft com_google_android_gms_measurement_internal_zzft : list) {
                if (z || !zzfu.zzda(com_google_android_gms_measurement_internal_zzft.name)) {
                    arrayList.add(new zzfr(com_google_android_gms_measurement_internal_zzft));
                }
            }
            return arrayList;
        } catch (boolean z2) {
            this.zzang.zzgt().zzjg().zze("Failed to get user attributes. appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzi.packageName), z2);
            return null;
        }
    }

    @BinderThread
    public final void zza(zzi com_google_android_gms_measurement_internal_zzi) {
        zzb(com_google_android_gms_measurement_internal_zzi, false);
        zze(new zzcn(this, com_google_android_gms_measurement_internal_zzi));
    }

    @BinderThread
    private final void zzb(zzi com_google_android_gms_measurement_internal_zzi, boolean z) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzi);
        zzc(com_google_android_gms_measurement_internal_zzi.packageName, false);
        this.zzang.zzgr().zzu(com_google_android_gms_measurement_internal_zzi.zzafx, com_google_android_gms_measurement_internal_zzi.zzagk);
    }

    @BinderThread
    private final void zzc(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            this.zzang.zzgt().zzjg().zzca("Measurement Service called without app package");
            throw new SecurityException("Measurement Service called without app package");
        }
        if (z) {
            try {
                if (!this.zzaqt) {
                    if (!("com.google.android.gms".equals(this.zzaqu) || UidVerifier.isGooglePlayServicesUid(this.zzang.getContext(), Binder.getCallingUid()))) {
                        if (!GoogleSignatureVerifier.getInstance(this.zzang.getContext()).isUidGoogleSigned(Binder.getCallingUid())) {
                            z = false;
                            this.zzaqt = Boolean.valueOf(z);
                        }
                    }
                    z = true;
                    this.zzaqt = Boolean.valueOf(z);
                }
                if (this.zzaqt.booleanValue()) {
                    return;
                }
            } catch (boolean z2) {
                this.zzang.zzgt().zzjg().zzg("Measurement Service called with invalid calling package. appId", zzaq.zzby(str));
                throw z2;
            }
        }
        if (!this.zzaqu && GooglePlayServicesUtilLight.uidHasPackageName(this.zzang.getContext(), Binder.getCallingUid(), str)) {
            this.zzaqu = str;
        }
        if (!str.equals(this.zzaqu)) {
            throw new SecurityException(String.format("Unknown calling package name '%s'.", new Object[]{str}));
        }
    }

    @BinderThread
    public final void zza(long j, String str, String str2, String str3) {
        zze(new zzco(this, str2, str3, str, j));
    }

    @BinderThread
    public final String zzc(zzi com_google_android_gms_measurement_internal_zzi) {
        zzb(com_google_android_gms_measurement_internal_zzi, false);
        return this.zzang.zzh(com_google_android_gms_measurement_internal_zzi);
    }

    @BinderThread
    public final void zza(zzm com_google_android_gms_measurement_internal_zzm, zzi com_google_android_gms_measurement_internal_zzi) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzm);
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzm.zzahe);
        zzb(com_google_android_gms_measurement_internal_zzi, false);
        zzm com_google_android_gms_measurement_internal_zzm2 = new zzm(com_google_android_gms_measurement_internal_zzm);
        com_google_android_gms_measurement_internal_zzm2.packageName = com_google_android_gms_measurement_internal_zzi.packageName;
        if (com_google_android_gms_measurement_internal_zzm.zzahe.getValue() == null) {
            zze(new zzby(this, com_google_android_gms_measurement_internal_zzm2, com_google_android_gms_measurement_internal_zzi));
        } else {
            zze(new zzbz(this, com_google_android_gms_measurement_internal_zzm2, com_google_android_gms_measurement_internal_zzi));
        }
    }

    @BinderThread
    public final void zzb(zzm com_google_android_gms_measurement_internal_zzm) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzm);
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzm.zzahe);
        zzc(com_google_android_gms_measurement_internal_zzm.packageName, true);
        zzm com_google_android_gms_measurement_internal_zzm2 = new zzm(com_google_android_gms_measurement_internal_zzm);
        if (com_google_android_gms_measurement_internal_zzm.zzahe.getValue() == null) {
            zze(new zzca(this, com_google_android_gms_measurement_internal_zzm2));
        } else {
            zze(new zzcb(this, com_google_android_gms_measurement_internal_zzm2));
        }
    }

    @BinderThread
    public final List<zzfr> zza(String str, String str2, boolean z, zzi com_google_android_gms_measurement_internal_zzi) {
        zzb(com_google_android_gms_measurement_internal_zzi, false);
        try {
            List<zzft> list = (List) this.zzang.zzgs().zzb(new zzcc(this, com_google_android_gms_measurement_internal_zzi, str, str2)).get();
            str2 = new ArrayList(list.size());
            for (zzft com_google_android_gms_measurement_internal_zzft : list) {
                if (z || !zzfu.zzda(com_google_android_gms_measurement_internal_zzft.name)) {
                    str2.add(new zzfr(com_google_android_gms_measurement_internal_zzft));
                }
            }
            return str2;
        } catch (String str3) {
            this.zzang.zzgt().zzjg().zze("Failed to get user attributes. appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzi.packageName), str3);
            return Collections.emptyList();
        }
    }

    @BinderThread
    public final List<zzfr> zza(String str, String str2, String str3, boolean z) {
        zzc(str, true);
        try {
            List<zzft> list = (List) this.zzang.zzgs().zzb(new zzcd(this, str, str2, str3)).get();
            str3 = new ArrayList(list.size());
            for (zzft com_google_android_gms_measurement_internal_zzft : list) {
                if (z || !zzfu.zzda(com_google_android_gms_measurement_internal_zzft.name)) {
                    str3.add(new zzfr(com_google_android_gms_measurement_internal_zzft));
                }
            }
            return str3;
        } catch (String str22) {
            this.zzang.zzgt().zzjg().zze("Failed to get user attributes. appId", zzaq.zzby(str), str22);
            return Collections.emptyList();
        }
    }

    @BinderThread
    public final List<zzm> zza(String str, String str2, zzi com_google_android_gms_measurement_internal_zzi) {
        zzb(com_google_android_gms_measurement_internal_zzi, false);
        try {
            return (List) this.zzang.zzgs().zzb(new zzce(this, com_google_android_gms_measurement_internal_zzi, str, str2)).get();
        } catch (String str3) {
            this.zzang.zzgt().zzjg().zzg("Failed to get conditional user properties", str3);
            return Collections.emptyList();
        }
    }

    @BinderThread
    public final List<zzm> zze(String str, String str2, String str3) {
        zzc(str, true);
        try {
            return (List) this.zzang.zzgs().zzb(new zzcf(this, str, str2, str3)).get();
        } catch (String str4) {
            this.zzang.zzgt().zzjg().zzg("Failed to get conditional user properties", str4);
            return Collections.emptyList();
        }
    }

    @BinderThread
    public final void zzd(zzi com_google_android_gms_measurement_internal_zzi) {
        zzc(com_google_android_gms_measurement_internal_zzi.packageName, false);
        zze(new zzcg(this, com_google_android_gms_measurement_internal_zzi));
    }

    @VisibleForTesting
    private final void zze(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        if (((Boolean) zzag.zzakz.get()).booleanValue() && this.zzang.zzgs().zzkf()) {
            runnable.run();
        } else {
            this.zzang.zzgs().zzc(runnable);
        }
    }
}
