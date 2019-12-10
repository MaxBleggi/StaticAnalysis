package com.google.android.gms.measurement.internal;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzft.zza;
import com.google.android.gms.internal.measurement.zzft.zzb;
import com.google.android.gms.internal.measurement.zzgi;
import com.google.android.gms.internal.measurement.zzgj;
import com.google.android.gms.internal.measurement.zzgk;
import com.google.android.gms.internal.measurement.zzgl;
import com.google.android.gms.internal.measurement.zzgo;
import com.google.android.gms.internal.measurement.zzvx;
import com.google.android.gms.internal.measurement.zzzj;
import com.google.android.gms.internal.measurement.zzzr;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

final class zzdt extends zzfj {
    public zzdt(zzfk com_google_android_gms_measurement_internal_zzfk) {
        super(com_google_android_gms_measurement_internal_zzfk);
    }

    protected final boolean zzgy() {
        return false;
    }

    @WorkerThread
    public final byte[] zzb(@NonNull zzae com_google_android_gms_measurement_internal_zzae, @Size(min = 1) String str) {
        byte[] bArr;
        zzae com_google_android_gms_measurement_internal_zzae2 = com_google_android_gms_measurement_internal_zzae;
        String str2 = str;
        zzaf();
        this.zzadp.zzgf();
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzae);
        Preconditions.checkNotEmpty(str);
        if (!zzgv().zze(str2, zzag.zzalr)) {
            zzgt().zzjn().zzg("Generating ScionPayload disabled. packageName", str2);
            return new byte[0];
        } else if ("_iap".equals(com_google_android_gms_measurement_internal_zzae2.name) || "_iapx".equals(com_google_android_gms_measurement_internal_zzae2.name)) {
            zzzr com_google_android_gms_internal_measurement_zzgk = new zzgk();
            zzjt().beginTransaction();
            try {
                zzg zzbo = zzjt().zzbo(str2);
                if (zzbo == null) {
                    zzgt().zzjn().zzg("Log and bundle not available. package_name", str2);
                    bArr = new byte[0];
                    return bArr;
                } else if (zzbo.isMeasurementEnabled()) {
                    Integer num;
                    zzgl com_google_android_gms_internal_measurement_zzgl = new zzgl();
                    com_google_android_gms_internal_measurement_zzgk.zzaxr = new zzgl[]{com_google_android_gms_internal_measurement_zzgl};
                    com_google_android_gms_internal_measurement_zzgl.zzaxt = Integer.valueOf(1);
                    com_google_android_gms_internal_measurement_zzgl.zzayb = "android";
                    com_google_android_gms_internal_measurement_zzgl.zztt = zzbo.zzal();
                    com_google_android_gms_internal_measurement_zzgl.zzage = zzbo.zzhg();
                    com_google_android_gms_internal_measurement_zzgl.zzts = zzbo.zzak();
                    long zzhf = zzbo.zzhf();
                    if (zzhf == -2147483648L) {
                        num = null;
                    } else {
                        num = Integer.valueOf((int) zzhf);
                    }
                    com_google_android_gms_internal_measurement_zzgl.zzayn = num;
                    com_google_android_gms_internal_measurement_zzgl.zzayf = Long.valueOf(zzbo.zzhh());
                    com_google_android_gms_internal_measurement_zzgl.zzafx = zzbo.getGmpAppId();
                    if (TextUtils.isEmpty(com_google_android_gms_internal_measurement_zzgl.zzafx)) {
                        com_google_android_gms_internal_measurement_zzgl.zzaxc = zzbo.zzhb();
                    }
                    com_google_android_gms_internal_measurement_zzgl.zzayj = Long.valueOf(zzbo.zzhi());
                    if (r1.zzadp.isEnabled() && zzo.zzie() && zzgv().zzau(com_google_android_gms_internal_measurement_zzgl.zztt)) {
                        com_google_android_gms_internal_measurement_zzgl.zzayt = null;
                    }
                    Pair zzcb = zzgu().zzcb(zzbo.zzal());
                    if (!(!zzbo.zzhw() || zzcb == null || TextUtils.isEmpty((CharSequence) zzcb.first))) {
                        com_google_android_gms_internal_measurement_zzgl.zzayh = zzr((String) zzcb.first, Long.toString(com_google_android_gms_measurement_internal_zzae2.zzais));
                        com_google_android_gms_internal_measurement_zzgl.zzayi = (Boolean) zzcb.second;
                    }
                    zzgp().zzcl();
                    com_google_android_gms_internal_measurement_zzgl.zzayd = Build.MODEL;
                    zzgp().zzcl();
                    com_google_android_gms_internal_measurement_zzgl.zzayc = VERSION.RELEASE;
                    com_google_android_gms_internal_measurement_zzgl.zzaye = Integer.valueOf((int) zzgp().zziw());
                    com_google_android_gms_internal_measurement_zzgl.zzaid = zzgp().zzix();
                    try {
                        Bundle bundle;
                        zzgl com_google_android_gms_internal_measurement_zzgl2;
                        zzzr com_google_android_gms_internal_measurement_zzzr;
                        zzg com_google_android_gms_measurement_internal_zzg;
                        zzaa com_google_android_gms_measurement_internal_zzaa;
                        long j;
                        com_google_android_gms_internal_measurement_zzgl.zzafw = zzr(zzbo.getAppInstanceId(), Long.toString(com_google_android_gms_measurement_internal_zzae2.zzais));
                        com_google_android_gms_internal_measurement_zzgl.zzafz = zzbo.getFirebaseInstanceId();
                        String str3 = com_google_android_gms_internal_measurement_zzgl.zztt;
                        List<zzft> zzbn = zzjt().zzbn(str3);
                        if (zzgv().zzaw(str2)) {
                            for (zzft com_google_android_gms_measurement_internal_zzft : zzbn) {
                                if ("_lte".equals(com_google_android_gms_measurement_internal_zzft.name)) {
                                    break;
                                }
                            }
                            zzft com_google_android_gms_measurement_internal_zzft2 = null;
                            if (com_google_android_gms_measurement_internal_zzft2 == null || com_google_android_gms_measurement_internal_zzft2.value == null) {
                                zzft com_google_android_gms_measurement_internal_zzft3 = new zzft(str3, "auto", "_lte", zzbx().currentTimeMillis(), Long.valueOf(0));
                                zzbn.add(com_google_android_gms_measurement_internal_zzft3);
                                zzjt().zza(com_google_android_gms_measurement_internal_zzft3);
                            }
                        }
                        zzgo[] com_google_android_gms_internal_measurement_zzgoArr = new zzgo[zzbn.size()];
                        for (int i = 0; i < zzbn.size(); i++) {
                            zzgo com_google_android_gms_internal_measurement_zzgo = new zzgo();
                            com_google_android_gms_internal_measurement_zzgoArr[i] = com_google_android_gms_internal_measurement_zzgo;
                            com_google_android_gms_internal_measurement_zzgo.name = ((zzft) zzbn.get(i)).name;
                            com_google_android_gms_internal_measurement_zzgo.zzazg = Long.valueOf(((zzft) zzbn.get(i)).zzaux);
                            zzjr().zza(com_google_android_gms_internal_measurement_zzgo, ((zzft) zzbn.get(i)).value);
                        }
                        com_google_android_gms_internal_measurement_zzgl.zzaxv = com_google_android_gms_internal_measurement_zzgoArr;
                        Bundle zziy = com_google_android_gms_measurement_internal_zzae2.zzaig.zziy();
                        zziy.putLong("_c", 1);
                        zzgt().zzjn().zzca("Marking in-app purchase as real-time");
                        zziy.putLong("_r", 1);
                        zziy.putString("_o", com_google_android_gms_measurement_internal_zzae2.origin);
                        if (zzgr().zzdb(com_google_android_gms_internal_measurement_zzgl.zztt)) {
                            zzgr().zza(zziy, "_dbg", Long.valueOf(1));
                            zzgr().zza(zziy, "_r", Long.valueOf(1));
                        }
                        zzaa zzg = zzjt().zzg(str2, com_google_android_gms_measurement_internal_zzae2.name);
                        if (zzg == null) {
                            bundle = zziy;
                            com_google_android_gms_internal_measurement_zzgl2 = com_google_android_gms_internal_measurement_zzgl;
                            com_google_android_gms_internal_measurement_zzzr = com_google_android_gms_internal_measurement_zzgk;
                            com_google_android_gms_measurement_internal_zzg = zzbo;
                            com_google_android_gms_measurement_internal_zzaa = new zzaa(str, com_google_android_gms_measurement_internal_zzae2.name, 0, 0, com_google_android_gms_measurement_internal_zzae2.zzais, 0, null, null, null, null);
                            j = 0;
                        } else {
                            bundle = zziy;
                            com_google_android_gms_internal_measurement_zzgl2 = com_google_android_gms_internal_measurement_zzgl;
                            com_google_android_gms_internal_measurement_zzzr = com_google_android_gms_internal_measurement_zzgk;
                            com_google_android_gms_measurement_internal_zzg = zzbo;
                            j = zzg.zzaij;
                            com_google_android_gms_measurement_internal_zzaa = zzg.zzai(com_google_android_gms_measurement_internal_zzae2.zzais);
                        }
                        zzjt().zza(com_google_android_gms_measurement_internal_zzaa);
                        zzz com_google_android_gms_measurement_internal_zzz = new zzz(r1.zzadp, com_google_android_gms_measurement_internal_zzae2.origin, str, com_google_android_gms_measurement_internal_zzae2.name, com_google_android_gms_measurement_internal_zzae2.zzais, j, bundle);
                        zzgi com_google_android_gms_internal_measurement_zzgi = new zzgi();
                        zzgl com_google_android_gms_internal_measurement_zzgl3 = com_google_android_gms_internal_measurement_zzgl2;
                        com_google_android_gms_internal_measurement_zzgl3.zzaxu = new zzgi[]{com_google_android_gms_internal_measurement_zzgi};
                        com_google_android_gms_internal_measurement_zzgi.zzaxn = Long.valueOf(com_google_android_gms_measurement_internal_zzz.timestamp);
                        com_google_android_gms_internal_measurement_zzgi.name = com_google_android_gms_measurement_internal_zzz.name;
                        com_google_android_gms_internal_measurement_zzgi.zzaxo = Long.valueOf(com_google_android_gms_measurement_internal_zzz.zzaif);
                        com_google_android_gms_internal_measurement_zzgi.zzaxm = new zzgj[com_google_android_gms_measurement_internal_zzz.zzaig.size()];
                        Iterator it = com_google_android_gms_measurement_internal_zzz.zzaig.iterator();
                        int i2 = 0;
                        while (it.hasNext()) {
                            String str4 = (String) it.next();
                            zzgj com_google_android_gms_internal_measurement_zzgj = new zzgj();
                            int i3 = i2 + 1;
                            com_google_android_gms_internal_measurement_zzgi.zzaxm[i2] = com_google_android_gms_internal_measurement_zzgj;
                            com_google_android_gms_internal_measurement_zzgj.name = str4;
                            zzjr().zza(com_google_android_gms_internal_measurement_zzgj, com_google_android_gms_measurement_internal_zzz.zzaig.get(str4));
                            i2 = i3;
                        }
                        com_google_android_gms_internal_measurement_zzgl3.zzayw = (zzb) ((zzvx) zzb.zzmp().zzb((zza) ((zzvx) zza.zzmn().zzar(com_google_android_gms_measurement_internal_zzaa.zzaih).zzdc(com_google_android_gms_measurement_internal_zzae2.name).zzwv())).zzwv());
                        com_google_android_gms_internal_measurement_zzgl3.zzaym = zzjs().zza(com_google_android_gms_measurement_internal_zzg.zzal(), null, com_google_android_gms_internal_measurement_zzgl3.zzaxv);
                        com_google_android_gms_internal_measurement_zzgl3.zzaxx = com_google_android_gms_internal_measurement_zzgi.zzaxn;
                        com_google_android_gms_internal_measurement_zzgl3.zzaxy = com_google_android_gms_internal_measurement_zzgi.zzaxn;
                        zzhf = com_google_android_gms_measurement_internal_zzg.zzhe();
                        com_google_android_gms_internal_measurement_zzgl3.zzaya = zzhf != 0 ? Long.valueOf(zzhf) : null;
                        long zzhd = com_google_android_gms_measurement_internal_zzg.zzhd();
                        if (zzhd != 0) {
                            zzhf = zzhd;
                        }
                        com_google_android_gms_internal_measurement_zzgl3.zzaxz = zzhf != 0 ? Long.valueOf(zzhf) : null;
                        com_google_android_gms_measurement_internal_zzg.zzhm();
                        com_google_android_gms_internal_measurement_zzgl3.zzayk = Integer.valueOf((int) com_google_android_gms_measurement_internal_zzg.zzhj());
                        com_google_android_gms_internal_measurement_zzgl3.zzayg = Long.valueOf(zzgv().zzhh());
                        com_google_android_gms_internal_measurement_zzgl3.zzaxw = Long.valueOf(zzbx().currentTimeMillis());
                        com_google_android_gms_internal_measurement_zzgl3.zzayl = Boolean.TRUE;
                        zzg com_google_android_gms_measurement_internal_zzg2 = com_google_android_gms_measurement_internal_zzg;
                        com_google_android_gms_measurement_internal_zzg2.zzs(com_google_android_gms_internal_measurement_zzgl3.zzaxx.longValue());
                        com_google_android_gms_measurement_internal_zzg2.zzt(com_google_android_gms_internal_measurement_zzgl3.zzaxy.longValue());
                        zzjt().zza(com_google_android_gms_measurement_internal_zzg2);
                        zzjt().setTransactionSuccessful();
                        zzjt().endTransaction();
                        try {
                            bArr = new byte[com_google_android_gms_internal_measurement_zzzr.zzwe()];
                            zzzj zzk = zzzj.zzk(bArr, 0, bArr.length);
                            com_google_android_gms_internal_measurement_zzzr.zza(zzk);
                            zzk.zzzh();
                            return zzjr().zzb(bArr);
                        } catch (IOException e) {
                            zzgt().zzjg().zze("Data loss. Failed to bundle and serialize. appId", zzaq.zzby(str), e);
                            return null;
                        }
                    } catch (SecurityException e2) {
                        zzgt().zzjn().zzg("app instance id encryption failed", e2.getMessage());
                        bArr = new byte[0];
                        zzjt().endTransaction();
                        return bArr;
                    }
                } else {
                    zzgt().zzjn().zzg("Log and bundle disabled. package_name", str2);
                    bArr = new byte[0];
                    zzjt().endTransaction();
                    return bArr;
                }
            } catch (SecurityException e22) {
                zzgt().zzjn().zzg("Resettable device id encryption failed", e22.getMessage());
                bArr = new byte[0];
                return bArr;
            } finally {
                zzjt().endTransaction();
            }
        } else {
            zzgt().zzjn().zze("Generating a payload for this event is not available. package_name, event_name", str2, com_google_android_gms_measurement_internal_zzae2.name);
            return null;
        }
    }

    private static String zzr(String str, String str2) {
        throw new SecurityException("This implementation should not be used.");
    }
}
