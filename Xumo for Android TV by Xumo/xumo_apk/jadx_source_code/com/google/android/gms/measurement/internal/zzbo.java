package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzfx;
import com.google.android.gms.internal.measurement.zzfy;
import com.google.android.gms.internal.measurement.zzfz;
import com.google.android.gms.internal.measurement.zzgb;
import com.google.android.gms.internal.measurement.zzgd;
import com.google.android.gms.internal.measurement.zzge;
import com.google.android.gms.internal.measurement.zzgf;
import com.google.android.gms.internal.measurement.zzzi;
import com.google.android.gms.internal.measurement.zzzj;
import com.google.android.gms.internal.measurement.zzzr;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import java.io.IOException;
import java.util.Map;

public final class zzbo extends zzfj implements zzq {
    @VisibleForTesting
    private static int zzaot = 65535;
    @VisibleForTesting
    private static int zzaou = 2;
    private final Map<String, Map<String, String>> zzaov = new ArrayMap();
    private final Map<String, Map<String, Boolean>> zzaow = new ArrayMap();
    private final Map<String, Map<String, Boolean>> zzaox = new ArrayMap();
    private final Map<String, zzge> zzaoy = new ArrayMap();
    private final Map<String, Map<String, Integer>> zzaoz = new ArrayMap();
    private final Map<String, String> zzapa = new ArrayMap();

    zzbo(zzfk com_google_android_gms_measurement_internal_zzfk) {
        super(com_google_android_gms_measurement_internal_zzfk);
    }

    protected final boolean zzgy() {
        return false;
    }

    @WorkerThread
    private final void zzch(String str) {
        zzcl();
        zzaf();
        Preconditions.checkNotEmpty(str);
        if (this.zzaoy.get(str) == null) {
            byte[] zzbq = zzjt().zzbq(str);
            if (zzbq == null) {
                this.zzaov.put(str, null);
                this.zzaow.put(str, null);
                this.zzaox.put(str, null);
                this.zzaoy.put(str, null);
                this.zzapa.put(str, null);
                this.zzaoz.put(str, null);
                return;
            }
            zzge zza = zza(str, zzbq);
            this.zzaov.put(str, zza(zza));
            zza(str, zza);
            this.zzaoy.put(str, zza);
            this.zzapa.put(str, null);
        }
    }

    @WorkerThread
    protected final zzge zzci(String str) {
        zzcl();
        zzaf();
        Preconditions.checkNotEmpty(str);
        zzch(str);
        return (zzge) this.zzaoy.get(str);
    }

    @WorkerThread
    protected final String zzcj(String str) {
        zzaf();
        return (String) this.zzapa.get(str);
    }

    @WorkerThread
    protected final void zzck(String str) {
        zzaf();
        this.zzapa.put(str, null);
    }

    @WorkerThread
    final void zzcl(String str) {
        zzaf();
        this.zzaoy.remove(str);
    }

    @WorkerThread
    public final String zzf(String str, String str2) {
        zzaf();
        zzch(str);
        Map map = (Map) this.zzaov.get(str);
        return map != null ? (String) map.get(str2) : null;
    }

    private static Map<String, String> zza(zzge com_google_android_gms_internal_measurement_zzge) {
        Map<String, String> arrayMap = new ArrayMap();
        if (!(com_google_android_gms_internal_measurement_zzge == null || com_google_android_gms_internal_measurement_zzge.zzawz == null)) {
            for (zzgf com_google_android_gms_internal_measurement_zzgf : com_google_android_gms_internal_measurement_zzge.zzawz) {
                if (com_google_android_gms_internal_measurement_zzgf != null) {
                    arrayMap.put(com_google_android_gms_internal_measurement_zzgf.zzoj, com_google_android_gms_internal_measurement_zzgf.value);
                }
            }
        }
        return arrayMap;
    }

    private final void zza(String str, zzge com_google_android_gms_internal_measurement_zzge) {
        Map arrayMap = new ArrayMap();
        Map arrayMap2 = new ArrayMap();
        Map arrayMap3 = new ArrayMap();
        if (!(com_google_android_gms_internal_measurement_zzge == null || com_google_android_gms_internal_measurement_zzge.zzaxa == null)) {
            for (zzgd com_google_android_gms_internal_measurement_zzgd : com_google_android_gms_internal_measurement_zzge.zzaxa) {
                if (TextUtils.isEmpty(com_google_android_gms_internal_measurement_zzgd.name)) {
                    zzgt().zzjj().zzca("EventConfig contained null event name");
                } else {
                    Object zzcq = zzcs.zzcq(com_google_android_gms_internal_measurement_zzgd.name);
                    if (!TextUtils.isEmpty(zzcq)) {
                        com_google_android_gms_internal_measurement_zzgd.name = zzcq;
                    }
                    arrayMap.put(com_google_android_gms_internal_measurement_zzgd.name, com_google_android_gms_internal_measurement_zzgd.zzawu);
                    arrayMap2.put(com_google_android_gms_internal_measurement_zzgd.name, com_google_android_gms_internal_measurement_zzgd.zzawv);
                    if (com_google_android_gms_internal_measurement_zzgd.zzaww != null) {
                        if (com_google_android_gms_internal_measurement_zzgd.zzaww.intValue() >= zzaou) {
                            if (com_google_android_gms_internal_measurement_zzgd.zzaww.intValue() <= zzaot) {
                                arrayMap3.put(com_google_android_gms_internal_measurement_zzgd.name, com_google_android_gms_internal_measurement_zzgd.zzaww);
                            }
                        }
                        zzgt().zzjj().zze("Invalid sampling rate. Event name, sample rate", com_google_android_gms_internal_measurement_zzgd.name, com_google_android_gms_internal_measurement_zzgd.zzaww);
                    }
                }
            }
        }
        this.zzaow.put(str, arrayMap);
        this.zzaox.put(str, arrayMap2);
        this.zzaoz.put(str, arrayMap3);
    }

    @WorkerThread
    protected final boolean zza(String str, byte[] bArr, String str2) {
        zzbo com_google_android_gms_measurement_internal_zzbo = this;
        String str3 = str;
        zzcl();
        zzaf();
        Preconditions.checkNotEmpty(str);
        zzge zza = zza(str, bArr);
        if (zza == null) {
            return false;
        }
        byte[] bArr2;
        zza(str3, zza);
        com_google_android_gms_measurement_internal_zzbo.zzaoy.put(str3, zza);
        com_google_android_gms_measurement_internal_zzbo.zzapa.put(str3, str2);
        com_google_android_gms_measurement_internal_zzbo.zzaov.put(str3, zza(zza));
        zzfi zzjs = zzjs();
        zzfx[] com_google_android_gms_internal_measurement_zzfxArr = zza.zzaxb;
        Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzfxArr);
        for (zzfx com_google_android_gms_internal_measurement_zzfx : com_google_android_gms_internal_measurement_zzfxArr) {
            for (zzfy com_google_android_gms_internal_measurement_zzfy : com_google_android_gms_internal_measurement_zzfx.zzavt) {
                String zzcq = zzcs.zzcq(com_google_android_gms_internal_measurement_zzfy.zzavy);
                if (zzcq != null) {
                    com_google_android_gms_internal_measurement_zzfy.zzavy = zzcq;
                }
                for (zzfz com_google_android_gms_internal_measurement_zzfz : com_google_android_gms_internal_measurement_zzfy.zzavz) {
                    String zzcq2 = zzct.zzcq(com_google_android_gms_internal_measurement_zzfz.zzawg);
                    if (zzcq2 != null) {
                        com_google_android_gms_internal_measurement_zzfz.zzawg = zzcq2;
                    }
                }
            }
            for (zzgb com_google_android_gms_internal_measurement_zzgb : com_google_android_gms_internal_measurement_zzfx.zzavs) {
                String zzcq3 = zzcu.zzcq(com_google_android_gms_internal_measurement_zzgb.zzawn);
                if (zzcq3 != null) {
                    com_google_android_gms_internal_measurement_zzgb.zzawn = zzcq3;
                }
            }
        }
        zzjs.zzjt().zza(str3, com_google_android_gms_internal_measurement_zzfxArr);
        try {
            zza.zzaxb = null;
            bArr2 = new byte[zza.zzwe()];
            zza.zza(zzzj.zzk(bArr2, 0, bArr2.length));
        } catch (IOException e) {
            zzgt().zzjj().zze("Unable to serialize reduced-size config. Storing full config instead. appId", zzaq.zzby(str), e);
            bArr2 = bArr;
        }
        zzcp zzjt = zzjt();
        Preconditions.checkNotEmpty(str);
        zzjt.zzaf();
        zzjt.zzcl();
        ContentValues contentValues = new ContentValues();
        contentValues.put("remote_config", bArr2);
        try {
            if (((long) zzjt.getWritableDatabase().update("apps", contentValues, "app_id = ?", new String[]{str3})) == 0) {
                zzjt.zzgt().zzjg().zzg("Failed to update remote config (got 0). appId", zzaq.zzby(str));
            }
        } catch (SQLiteException e2) {
            zzjt.zzgt().zzjg().zze("Error storing remote config. appId", zzaq.zzby(str), e2);
        }
        return true;
    }

    @WorkerThread
    final boolean zzo(String str, String str2) {
        zzaf();
        zzch(str);
        if (zzcn(str) && zzfu.zzda(str2)) {
            return true;
        }
        if (zzco(str) && zzfu.zzcv(str2)) {
            return true;
        }
        Map map = (Map) this.zzaow.get(str);
        if (map == null) {
            return false;
        }
        Boolean bool = (Boolean) map.get(str2);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @WorkerThread
    final boolean zzp(String str, String str2) {
        zzaf();
        zzch(str);
        if (Event.ECOMMERCE_PURCHASE.equals(str2)) {
            return true;
        }
        Map map = (Map) this.zzaox.get(str);
        if (map == null) {
            return false;
        }
        Boolean bool = (Boolean) map.get(str2);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @WorkerThread
    final int zzq(String str, String str2) {
        zzaf();
        zzch(str);
        Map map = (Map) this.zzaoz.get(str);
        if (map == null) {
            return 1;
        }
        Integer num = (Integer) map.get(str2);
        if (num == null) {
            return 1;
        }
        return num.intValue();
    }

    @WorkerThread
    final long zzcm(String str) {
        Object zzf = zzf(str, "measurement.account.time_zone_offset_minutes");
        if (!TextUtils.isEmpty(zzf)) {
            try {
                return Long.parseLong(zzf);
            } catch (NumberFormatException e) {
                zzgt().zzjj().zze("Unable to parse timezone offset. appId", zzaq.zzby(str), e);
            }
        }
        return 0;
    }

    @WorkerThread
    private final zzge zza(String str, byte[] bArr) {
        if (bArr == null) {
            return new zzge();
        }
        zzzi zzj = zzzi.zzj(bArr, 0, bArr.length);
        zzzr com_google_android_gms_internal_measurement_zzge = new zzge();
        try {
            com_google_android_gms_internal_measurement_zzge.zza(zzj);
            zzgt().zzjo().zze("Parsed config. version, gmp_app_id", com_google_android_gms_internal_measurement_zzge.zzawx, com_google_android_gms_internal_measurement_zzge.zzafx);
            return com_google_android_gms_internal_measurement_zzge;
        } catch (byte[] bArr2) {
            zzgt().zzjj().zze("Unable to merge remote config. appId", zzaq.zzby(str), bArr2);
            return new zzge();
        }
    }

    final boolean zzcn(String str) {
        return "1".equals(zzf(str, "measurement.upload.blacklist_internal"));
    }

    final boolean zzco(String str) {
        return "1".equals(zzf(str, "measurement.upload.blacklist_public"));
    }

    public final /* bridge */ /* synthetic */ zzfq zzjr() {
        return super.zzjr();
    }

    public final /* bridge */ /* synthetic */ zzk zzjs() {
        return super.zzjs();
    }

    public final /* bridge */ /* synthetic */ zzr zzjt() {
        return super.zzjt();
    }

    public final /* bridge */ /* synthetic */ void zzgf() {
        super.zzgf();
    }

    public final /* bridge */ /* synthetic */ void zzgg() {
        super.zzgg();
    }

    public final /* bridge */ /* synthetic */ void zzgh() {
        super.zzgh();
    }

    public final /* bridge */ /* synthetic */ void zzaf() {
        super.zzaf();
    }

    public final /* bridge */ /* synthetic */ zzy zzgp() {
        return super.zzgp();
    }

    public final /* bridge */ /* synthetic */ Clock zzbx() {
        return super.zzbx();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final /* bridge */ /* synthetic */ zzao zzgq() {
        return super.zzgq();
    }

    public final /* bridge */ /* synthetic */ zzfu zzgr() {
        return super.zzgr();
    }

    public final /* bridge */ /* synthetic */ zzbp zzgs() {
        return super.zzgs();
    }

    public final /* bridge */ /* synthetic */ zzaq zzgt() {
        return super.zzgt();
    }

    public final /* bridge */ /* synthetic */ zzbb zzgu() {
        return super.zzgu();
    }

    public final /* bridge */ /* synthetic */ zzo zzgv() {
        return super.zzgv();
    }

    public final /* bridge */ /* synthetic */ zzl zzgw() {
        return super.zzgw();
    }
}
