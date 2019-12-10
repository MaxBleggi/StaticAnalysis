package com.google.android.gms.internal.measurement;

import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.gms.internal.measurement.zzft.zzb;
import com.google.android.gms.internal.measurement.zzft.zzb.zza;
import java.io.IOException;

public final class zzgl extends zzzl<zzgl> {
    private static volatile zzgl[] zzaxs;
    public String zzafw;
    public String zzafx;
    public String zzafz;
    public String zzage;
    public String zzagy;
    public String zzaid;
    public String zzaxc;
    public Integer zzaxt;
    public zzgi[] zzaxu;
    public zzgo[] zzaxv;
    public Long zzaxw;
    public Long zzaxx;
    public Long zzaxy;
    public Long zzaxz;
    public Long zzaya;
    public String zzayb;
    public String zzayc;
    public String zzayd;
    public Integer zzaye;
    public Long zzayf;
    public Long zzayg;
    public String zzayh;
    public Boolean zzayi;
    public Long zzayj;
    public Integer zzayk;
    public Boolean zzayl;
    public zzgg[] zzaym;
    public Integer zzayn;
    private Integer zzayo;
    private Integer zzayp;
    public String zzayq;
    public Long zzayr;
    public Long zzays;
    public String zzayt;
    private String zzayu;
    public Integer zzayv;
    public zzb zzayw;
    public int[] zzayx;
    private Long zzayy;
    public String zzts;
    public String zztt;

    public static zzgl[] zznb() {
        if (zzaxs == null) {
            synchronized (zzzp.zzcgg) {
                if (zzaxs == null) {
                    zzaxs = new zzgl[0];
                }
            }
        }
        return zzaxs;
    }

    public zzgl() {
        this.zzaxt = null;
        this.zzaxu = zzgi.zzmz();
        this.zzaxv = zzgo.zznd();
        this.zzaxw = null;
        this.zzaxx = null;
        this.zzaxy = null;
        this.zzaxz = null;
        this.zzaya = null;
        this.zzayb = null;
        this.zzayc = null;
        this.zzayd = null;
        this.zzaid = null;
        this.zzaye = null;
        this.zzage = null;
        this.zztt = null;
        this.zzts = null;
        this.zzayf = null;
        this.zzayg = null;
        this.zzayh = null;
        this.zzayi = null;
        this.zzafw = null;
        this.zzayj = null;
        this.zzayk = null;
        this.zzagy = null;
        this.zzafx = null;
        this.zzayl = null;
        this.zzaym = zzgg.zzmx();
        this.zzafz = null;
        this.zzayn = null;
        this.zzayo = null;
        this.zzayp = null;
        this.zzayq = null;
        this.zzayr = null;
        this.zzays = null;
        this.zzayt = null;
        this.zzayu = null;
        this.zzayv = null;
        this.zzaxc = null;
        this.zzayw = null;
        this.zzayx = zzzu.zzcbs;
        this.zzayy = null;
        this.zzcfx = null;
        this.zzcgh = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgl)) {
            return false;
        }
        zzgl com_google_android_gms_internal_measurement_zzgl = (zzgl) obj;
        if (this.zzaxt == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzaxt != null) {
                return false;
            }
        } else if (!this.zzaxt.equals(com_google_android_gms_internal_measurement_zzgl.zzaxt)) {
            return false;
        }
        if (!zzzp.equals(this.zzaxu, com_google_android_gms_internal_measurement_zzgl.zzaxu) || !zzzp.equals(this.zzaxv, com_google_android_gms_internal_measurement_zzgl.zzaxv)) {
            return false;
        }
        if (this.zzaxw == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzaxw != null) {
                return false;
            }
        } else if (!this.zzaxw.equals(com_google_android_gms_internal_measurement_zzgl.zzaxw)) {
            return false;
        }
        if (this.zzaxx == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzaxx != null) {
                return false;
            }
        } else if (!this.zzaxx.equals(com_google_android_gms_internal_measurement_zzgl.zzaxx)) {
            return false;
        }
        if (this.zzaxy == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzaxy != null) {
                return false;
            }
        } else if (!this.zzaxy.equals(com_google_android_gms_internal_measurement_zzgl.zzaxy)) {
            return false;
        }
        if (this.zzaxz == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzaxz != null) {
                return false;
            }
        } else if (!this.zzaxz.equals(com_google_android_gms_internal_measurement_zzgl.zzaxz)) {
            return false;
        }
        if (this.zzaya == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzaya != null) {
                return false;
            }
        } else if (!this.zzaya.equals(com_google_android_gms_internal_measurement_zzgl.zzaya)) {
            return false;
        }
        if (this.zzayb == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayb != null) {
                return false;
            }
        } else if (!this.zzayb.equals(com_google_android_gms_internal_measurement_zzgl.zzayb)) {
            return false;
        }
        if (this.zzayc == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayc != null) {
                return false;
            }
        } else if (!this.zzayc.equals(com_google_android_gms_internal_measurement_zzgl.zzayc)) {
            return false;
        }
        if (this.zzayd == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayd != null) {
                return false;
            }
        } else if (!this.zzayd.equals(com_google_android_gms_internal_measurement_zzgl.zzayd)) {
            return false;
        }
        if (this.zzaid == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzaid != null) {
                return false;
            }
        } else if (!this.zzaid.equals(com_google_android_gms_internal_measurement_zzgl.zzaid)) {
            return false;
        }
        if (this.zzaye == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzaye != null) {
                return false;
            }
        } else if (!this.zzaye.equals(com_google_android_gms_internal_measurement_zzgl.zzaye)) {
            return false;
        }
        if (this.zzage == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzage != null) {
                return false;
            }
        } else if (!this.zzage.equals(com_google_android_gms_internal_measurement_zzgl.zzage)) {
            return false;
        }
        if (this.zztt == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zztt != null) {
                return false;
            }
        } else if (!this.zztt.equals(com_google_android_gms_internal_measurement_zzgl.zztt)) {
            return false;
        }
        if (this.zzts == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzts != null) {
                return false;
            }
        } else if (!this.zzts.equals(com_google_android_gms_internal_measurement_zzgl.zzts)) {
            return false;
        }
        if (this.zzayf == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayf != null) {
                return false;
            }
        } else if (!this.zzayf.equals(com_google_android_gms_internal_measurement_zzgl.zzayf)) {
            return false;
        }
        if (this.zzayg == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayg != null) {
                return false;
            }
        } else if (!this.zzayg.equals(com_google_android_gms_internal_measurement_zzgl.zzayg)) {
            return false;
        }
        if (this.zzayh == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayh != null) {
                return false;
            }
        } else if (!this.zzayh.equals(com_google_android_gms_internal_measurement_zzgl.zzayh)) {
            return false;
        }
        if (this.zzayi == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayi != null) {
                return false;
            }
        } else if (!this.zzayi.equals(com_google_android_gms_internal_measurement_zzgl.zzayi)) {
            return false;
        }
        if (this.zzafw == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzafw != null) {
                return false;
            }
        } else if (!this.zzafw.equals(com_google_android_gms_internal_measurement_zzgl.zzafw)) {
            return false;
        }
        if (this.zzayj == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayj != null) {
                return false;
            }
        } else if (!this.zzayj.equals(com_google_android_gms_internal_measurement_zzgl.zzayj)) {
            return false;
        }
        if (this.zzayk == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayk != null) {
                return false;
            }
        } else if (!this.zzayk.equals(com_google_android_gms_internal_measurement_zzgl.zzayk)) {
            return false;
        }
        if (this.zzagy == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzagy != null) {
                return false;
            }
        } else if (!this.zzagy.equals(com_google_android_gms_internal_measurement_zzgl.zzagy)) {
            return false;
        }
        if (this.zzafx == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzafx != null) {
                return false;
            }
        } else if (!this.zzafx.equals(com_google_android_gms_internal_measurement_zzgl.zzafx)) {
            return false;
        }
        if (this.zzayl == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayl != null) {
                return false;
            }
        } else if (!this.zzayl.equals(com_google_android_gms_internal_measurement_zzgl.zzayl)) {
            return false;
        }
        if (!zzzp.equals(this.zzaym, com_google_android_gms_internal_measurement_zzgl.zzaym)) {
            return false;
        }
        if (this.zzafz == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzafz != null) {
                return false;
            }
        } else if (!this.zzafz.equals(com_google_android_gms_internal_measurement_zzgl.zzafz)) {
            return false;
        }
        if (this.zzayn == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayn != null) {
                return false;
            }
        } else if (!this.zzayn.equals(com_google_android_gms_internal_measurement_zzgl.zzayn)) {
            return false;
        }
        if (this.zzayo == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayo != null) {
                return false;
            }
        } else if (!this.zzayo.equals(com_google_android_gms_internal_measurement_zzgl.zzayo)) {
            return false;
        }
        if (this.zzayp == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayp != null) {
                return false;
            }
        } else if (!this.zzayp.equals(com_google_android_gms_internal_measurement_zzgl.zzayp)) {
            return false;
        }
        if (this.zzayq == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayq != null) {
                return false;
            }
        } else if (!this.zzayq.equals(com_google_android_gms_internal_measurement_zzgl.zzayq)) {
            return false;
        }
        if (this.zzayr == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayr != null) {
                return false;
            }
        } else if (!this.zzayr.equals(com_google_android_gms_internal_measurement_zzgl.zzayr)) {
            return false;
        }
        if (this.zzays == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzays != null) {
                return false;
            }
        } else if (!this.zzays.equals(com_google_android_gms_internal_measurement_zzgl.zzays)) {
            return false;
        }
        if (this.zzayt == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayt != null) {
                return false;
            }
        } else if (!this.zzayt.equals(com_google_android_gms_internal_measurement_zzgl.zzayt)) {
            return false;
        }
        if (this.zzayu == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayu != null) {
                return false;
            }
        } else if (!this.zzayu.equals(com_google_android_gms_internal_measurement_zzgl.zzayu)) {
            return false;
        }
        if (this.zzayv == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayv != null) {
                return false;
            }
        } else if (!this.zzayv.equals(com_google_android_gms_internal_measurement_zzgl.zzayv)) {
            return false;
        }
        if (this.zzaxc == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzaxc != null) {
                return false;
            }
        } else if (!this.zzaxc.equals(com_google_android_gms_internal_measurement_zzgl.zzaxc)) {
            return false;
        }
        if (this.zzayw == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayw != null) {
                return false;
            }
        } else if (!this.zzayw.equals(com_google_android_gms_internal_measurement_zzgl.zzayw)) {
            return false;
        }
        if (!zzzp.equals(this.zzayx, com_google_android_gms_internal_measurement_zzgl.zzayx)) {
            return false;
        }
        if (this.zzayy == null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzayy != null) {
                return false;
            }
        } else if (!this.zzayy.equals(com_google_android_gms_internal_measurement_zzgl.zzayy)) {
            return false;
        }
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                return this.zzcfx.equals(com_google_android_gms_internal_measurement_zzgl.zzcfx);
            }
        }
        if (com_google_android_gms_internal_measurement_zzgl.zzcfx != null) {
            if (com_google_android_gms_internal_measurement_zzgl.zzcfx.isEmpty() == null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2 = 0;
        int hashCode = ((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zzaxt == null ? 0 : this.zzaxt.hashCode())) * 31) + zzzp.hashCode(this.zzaxu)) * 31) + zzzp.hashCode(this.zzaxv)) * 31) + (this.zzaxw == null ? 0 : this.zzaxw.hashCode())) * 31) + (this.zzaxx == null ? 0 : this.zzaxx.hashCode())) * 31) + (this.zzaxy == null ? 0 : this.zzaxy.hashCode())) * 31) + (this.zzaxz == null ? 0 : this.zzaxz.hashCode())) * 31) + (this.zzaya == null ? 0 : this.zzaya.hashCode())) * 31) + (this.zzayb == null ? 0 : this.zzayb.hashCode())) * 31) + (this.zzayc == null ? 0 : this.zzayc.hashCode())) * 31) + (this.zzayd == null ? 0 : this.zzayd.hashCode())) * 31) + (this.zzaid == null ? 0 : this.zzaid.hashCode())) * 31) + (this.zzaye == null ? 0 : this.zzaye.hashCode())) * 31) + (this.zzage == null ? 0 : this.zzage.hashCode())) * 31) + (this.zztt == null ? 0 : this.zztt.hashCode())) * 31) + (this.zzts == null ? 0 : this.zzts.hashCode())) * 31) + (this.zzayf == null ? 0 : this.zzayf.hashCode())) * 31) + (this.zzayg == null ? 0 : this.zzayg.hashCode())) * 31) + (this.zzayh == null ? 0 : this.zzayh.hashCode())) * 31) + (this.zzayi == null ? 0 : this.zzayi.hashCode())) * 31) + (this.zzafw == null ? 0 : this.zzafw.hashCode())) * 31) + (this.zzayj == null ? 0 : this.zzayj.hashCode())) * 31) + (this.zzayk == null ? 0 : this.zzayk.hashCode())) * 31) + (this.zzagy == null ? 0 : this.zzagy.hashCode())) * 31) + (this.zzafx == null ? 0 : this.zzafx.hashCode())) * 31) + (this.zzayl == null ? 0 : this.zzayl.hashCode())) * 31) + zzzp.hashCode(this.zzaym)) * 31) + (this.zzafz == null ? 0 : this.zzafz.hashCode())) * 31) + (this.zzayn == null ? 0 : this.zzayn.hashCode())) * 31) + (this.zzayo == null ? 0 : this.zzayo.hashCode())) * 31) + (this.zzayp == null ? 0 : this.zzayp.hashCode())) * 31) + (this.zzayq == null ? 0 : this.zzayq.hashCode())) * 31) + (this.zzayr == null ? 0 : this.zzayr.hashCode())) * 31) + (this.zzays == null ? 0 : this.zzays.hashCode())) * 31) + (this.zzayt == null ? 0 : this.zzayt.hashCode())) * 31) + (this.zzayu == null ? 0 : this.zzayu.hashCode())) * 31) + (this.zzayv == null ? 0 : this.zzayv.hashCode())) * 31) + (this.zzaxc == null ? 0 : this.zzaxc.hashCode());
        zzvx com_google_android_gms_internal_measurement_zzvx = this.zzayw;
        hashCode *= 31;
        if (com_google_android_gms_internal_measurement_zzvx == null) {
            i = 0;
        } else {
            i = com_google_android_gms_internal_measurement_zzvx.hashCode();
        }
        hashCode = (((((hashCode + i) * 31) + zzzp.hashCode(this.zzayx)) * 31) + (this.zzayy == null ? 0 : this.zzayy.hashCode())) * 31;
        if (this.zzcfx != null) {
            if (!this.zzcfx.isEmpty()) {
                i2 = this.zzcfx.hashCode();
            }
        }
        return hashCode + i2;
    }

    public final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzaxt != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(1, this.zzaxt.intValue());
        }
        if (this.zzaxu != null && this.zzaxu.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzaxu) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(2, com_google_android_gms_internal_measurement_zzzr);
                }
            }
        }
        if (this.zzaxv != null && this.zzaxv.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr2 : this.zzaxv) {
                if (com_google_android_gms_internal_measurement_zzzr2 != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(3, com_google_android_gms_internal_measurement_zzzr2);
                }
            }
        }
        if (this.zzaxw != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(4, this.zzaxw.longValue());
        }
        if (this.zzaxx != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(5, this.zzaxx.longValue());
        }
        if (this.zzaxy != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(6, this.zzaxy.longValue());
        }
        if (this.zzaya != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(7, this.zzaya.longValue());
        }
        if (this.zzayb != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(8, this.zzayb);
        }
        if (this.zzayc != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(9, this.zzayc);
        }
        if (this.zzayd != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(10, this.zzayd);
        }
        if (this.zzaid != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(11, this.zzaid);
        }
        if (this.zzaye != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(12, this.zzaye.intValue());
        }
        if (this.zzage != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(13, this.zzage);
        }
        if (this.zztt != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(14, this.zztt);
        }
        if (this.zzts != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(16, this.zzts);
        }
        if (this.zzayf != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(17, this.zzayf.longValue());
        }
        if (this.zzayg != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(18, this.zzayg.longValue());
        }
        if (this.zzayh != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(19, this.zzayh);
        }
        if (this.zzayi != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(20, this.zzayi.booleanValue());
        }
        if (this.zzafw != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(21, this.zzafw);
        }
        if (this.zzayj != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(22, this.zzayj.longValue());
        }
        if (this.zzayk != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(23, this.zzayk.intValue());
        }
        if (this.zzagy != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(24, this.zzagy);
        }
        if (this.zzafx != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(25, this.zzafx);
        }
        if (this.zzaxz != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(26, this.zzaxz.longValue());
        }
        if (this.zzayl != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(28, this.zzayl.booleanValue());
        }
        if (this.zzaym != null && this.zzaym.length > 0) {
            for (zzzr com_google_android_gms_internal_measurement_zzzr22 : this.zzaym) {
                if (com_google_android_gms_internal_measurement_zzzr22 != null) {
                    com_google_android_gms_internal_measurement_zzzj.zza(29, com_google_android_gms_internal_measurement_zzzr22);
                }
            }
        }
        if (this.zzafz != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(30, this.zzafz);
        }
        if (this.zzayn != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(31, this.zzayn.intValue());
        }
        if (this.zzayo != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(32, this.zzayo.intValue());
        }
        if (this.zzayp != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(33, this.zzayp.intValue());
        }
        if (this.zzayq != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(34, this.zzayq);
        }
        if (this.zzayr != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(35, this.zzayr.longValue());
        }
        if (this.zzays != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(36, this.zzays.longValue());
        }
        if (this.zzayt != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(37, this.zzayt);
        }
        if (this.zzayu != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(38, this.zzayu);
        }
        if (this.zzayv != null) {
            com_google_android_gms_internal_measurement_zzzj.zzd(39, this.zzayv.intValue());
        }
        if (this.zzaxc != null) {
            com_google_android_gms_internal_measurement_zzzj.zzb(41, this.zzaxc);
        }
        if (this.zzayw != null) {
            com_google_android_gms_internal_measurement_zzzj.zze(44, this.zzayw);
        }
        if (this.zzayx != null && this.zzayx.length > 0) {
            for (int i : this.zzayx) {
                com_google_android_gms_internal_measurement_zzzj.zzc(45, 0);
                com_google_android_gms_internal_measurement_zzzj.zzcc(i);
            }
        }
        if (this.zzayy != null) {
            com_google_android_gms_internal_measurement_zzzj.zzi(46, this.zzayy.longValue());
        }
        super.zza(com_google_android_gms_internal_measurement_zzzj);
    }

    protected final int zzf() {
        int i;
        int zzf = super.zzf();
        if (this.zzaxt != null) {
            zzf += zzzj.zzh(1, this.zzaxt.intValue());
        }
        if (this.zzaxu != null && this.zzaxu.length > 0) {
            i = zzf;
            for (zzzr com_google_android_gms_internal_measurement_zzzr : this.zzaxu) {
                if (com_google_android_gms_internal_measurement_zzzr != null) {
                    i += zzzj.zzb(2, com_google_android_gms_internal_measurement_zzzr);
                }
            }
            zzf = i;
        }
        if (this.zzaxv != null && this.zzaxv.length > 0) {
            i = zzf;
            for (zzzr com_google_android_gms_internal_measurement_zzzr2 : this.zzaxv) {
                if (com_google_android_gms_internal_measurement_zzzr2 != null) {
                    i += zzzj.zzb(3, com_google_android_gms_internal_measurement_zzzr2);
                }
            }
            zzf = i;
        }
        if (this.zzaxw != null) {
            zzf += zzzj.zzd(4, this.zzaxw.longValue());
        }
        if (this.zzaxx != null) {
            zzf += zzzj.zzd(5, this.zzaxx.longValue());
        }
        if (this.zzaxy != null) {
            zzf += zzzj.zzd(6, this.zzaxy.longValue());
        }
        if (this.zzaya != null) {
            zzf += zzzj.zzd(7, this.zzaya.longValue());
        }
        if (this.zzayb != null) {
            zzf += zzzj.zzc(8, this.zzayb);
        }
        if (this.zzayc != null) {
            zzf += zzzj.zzc(9, this.zzayc);
        }
        if (this.zzayd != null) {
            zzf += zzzj.zzc(10, this.zzayd);
        }
        if (this.zzaid != null) {
            zzf += zzzj.zzc(11, this.zzaid);
        }
        if (this.zzaye != null) {
            zzf += zzzj.zzh(12, this.zzaye.intValue());
        }
        if (this.zzage != null) {
            zzf += zzzj.zzc(13, this.zzage);
        }
        if (this.zztt != null) {
            zzf += zzzj.zzc(14, this.zztt);
        }
        if (this.zzts != null) {
            zzf += zzzj.zzc(16, this.zzts);
        }
        if (this.zzayf != null) {
            zzf += zzzj.zzd(17, this.zzayf.longValue());
        }
        if (this.zzayg != null) {
            zzf += zzzj.zzd(18, this.zzayg.longValue());
        }
        if (this.zzayh != null) {
            zzf += zzzj.zzc(19, this.zzayh);
        }
        if (this.zzayi != null) {
            this.zzayi.booleanValue();
            zzf += zzzj.zzbc(20) + 1;
        }
        if (this.zzafw != null) {
            zzf += zzzj.zzc(21, this.zzafw);
        }
        if (this.zzayj != null) {
            zzf += zzzj.zzd(22, this.zzayj.longValue());
        }
        if (this.zzayk != null) {
            zzf += zzzj.zzh(23, this.zzayk.intValue());
        }
        if (this.zzagy != null) {
            zzf += zzzj.zzc(24, this.zzagy);
        }
        if (this.zzafx != null) {
            zzf += zzzj.zzc(25, this.zzafx);
        }
        if (this.zzaxz != null) {
            zzf += zzzj.zzd(26, this.zzaxz.longValue());
        }
        if (this.zzayl != null) {
            this.zzayl.booleanValue();
            zzf += zzzj.zzbc(28) + 1;
        }
        if (this.zzaym != null && this.zzaym.length > 0) {
            i = zzf;
            for (zzzr com_google_android_gms_internal_measurement_zzzr3 : this.zzaym) {
                if (com_google_android_gms_internal_measurement_zzzr3 != null) {
                    i += zzzj.zzb(29, com_google_android_gms_internal_measurement_zzzr3);
                }
            }
            zzf = i;
        }
        if (this.zzafz != null) {
            zzf += zzzj.zzc(30, this.zzafz);
        }
        if (this.zzayn != null) {
            zzf += zzzj.zzh(31, this.zzayn.intValue());
        }
        if (this.zzayo != null) {
            zzf += zzzj.zzh(32, this.zzayo.intValue());
        }
        if (this.zzayp != null) {
            zzf += zzzj.zzh(33, this.zzayp.intValue());
        }
        if (this.zzayq != null) {
            zzf += zzzj.zzc(34, this.zzayq);
        }
        if (this.zzayr != null) {
            zzf += zzzj.zzd(35, this.zzayr.longValue());
        }
        if (this.zzays != null) {
            zzf += zzzj.zzd(36, this.zzays.longValue());
        }
        if (this.zzayt != null) {
            zzf += zzzj.zzc(37, this.zzayt);
        }
        if (this.zzayu != null) {
            zzf += zzzj.zzc(38, this.zzayu);
        }
        if (this.zzayv != null) {
            zzf += zzzj.zzh(39, this.zzayv.intValue());
        }
        if (this.zzaxc != null) {
            zzf += zzzj.zzc(41, this.zzaxc);
        }
        if (this.zzayw != null) {
            zzf += zzve.zzc(44, this.zzayw);
        }
        if (this.zzayx != null && this.zzayx.length > 0) {
            i = 0;
            for (int zzbk : this.zzayx) {
                i += zzzj.zzbk(zzbk);
            }
            zzf = (zzf + i) + (this.zzayx.length * 2);
        }
        return this.zzayy != null ? zzf + zzzj.zzd(46, this.zzayy.longValue()) : zzf;
    }

    public final /* synthetic */ zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException {
        while (true) {
            int zzuq = com_google_android_gms_internal_measurement_zzzi.zzuq();
            int length;
            Object obj;
            switch (zzuq) {
                case 0:
                    return this;
                case 8:
                    this.zzaxt = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
                    break;
                case 18:
                    zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 18);
                    length = this.zzaxu == null ? 0 : this.zzaxu.length;
                    obj = new zzgi[(zzuq + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzaxu, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzgi();
                        com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                        com_google_android_gms_internal_measurement_zzzi.zzuq();
                        length++;
                    }
                    obj[length] = new zzgi();
                    com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                    this.zzaxu = obj;
                    break;
                case 26:
                    zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 26);
                    length = this.zzaxv == null ? 0 : this.zzaxv.length;
                    obj = new zzgo[(zzuq + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzaxv, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzgo();
                        com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                        com_google_android_gms_internal_measurement_zzzi.zzuq();
                        length++;
                    }
                    obj[length] = new zzgo();
                    com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                    this.zzaxv = obj;
                    break;
                case 32:
                    this.zzaxw = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
                    break;
                case 40:
                    this.zzaxx = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
                    break;
                case 48:
                    this.zzaxy = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
                    break;
                case 56:
                    this.zzaya = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
                    break;
                case 66:
                    this.zzayb = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 74:
                    this.zzayc = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 82:
                    this.zzayd = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 90:
                    this.zzaid = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 96:
                    this.zzaye = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
                    break;
                case 106:
                    this.zzage = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 114:
                    this.zztt = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case TsExtractor.TS_STREAM_TYPE_HDMV_DTS /*130*/:
                    this.zzts = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 136:
                    this.zzayf = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
                    break;
                case 144:
                    this.zzayg = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
                    break;
                case 154:
                    this.zzayh = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 160:
                    this.zzayi = Boolean.valueOf(com_google_android_gms_internal_measurement_zzzi.zzuw());
                    break;
                case 170:
                    this.zzafw = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 176:
                    this.zzayj = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
                    break;
                case 184:
                    this.zzayk = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
                    break;
                case 194:
                    this.zzagy = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 202:
                    this.zzafx = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 208:
                    this.zzaxz = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
                    break;
                case 224:
                    this.zzayl = Boolean.valueOf(com_google_android_gms_internal_measurement_zzzi.zzuw());
                    break;
                case 234:
                    zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 234);
                    length = this.zzaym == null ? 0 : this.zzaym.length;
                    obj = new zzgg[(zzuq + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzaym, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzgg();
                        com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                        com_google_android_gms_internal_measurement_zzzi.zzuq();
                        length++;
                    }
                    obj[length] = new zzgg();
                    com_google_android_gms_internal_measurement_zzzi.zza(obj[length]);
                    this.zzaym = obj;
                    break;
                case 242:
                    this.zzafz = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 248:
                    this.zzayn = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
                    break;
                case 256:
                    this.zzayo = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
                    break;
                case 264:
                    this.zzayp = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
                    break;
                case 274:
                    this.zzayq = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 280:
                    this.zzayr = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
                    break;
                case 288:
                    this.zzays = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
                    break;
                case 298:
                    this.zzayt = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 306:
                    this.zzayu = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 312:
                    this.zzayv = Integer.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvi());
                    break;
                case 330:
                    this.zzaxc = com_google_android_gms_internal_measurement_zzzi.readString();
                    break;
                case 354:
                    zzb com_google_android_gms_internal_measurement_zzft_zzb = (zzb) com_google_android_gms_internal_measurement_zzzi.zza(zzb.zza());
                    if (this.zzayw != null) {
                        com_google_android_gms_internal_measurement_zzft_zzb = (zzb) ((zzvx) ((zza) ((zza) this.zzayw.zzwm()).zza((zzvx) com_google_android_gms_internal_measurement_zzft_zzb)).zzwv());
                    }
                    this.zzayw = com_google_android_gms_internal_measurement_zzft_zzb;
                    break;
                case 360:
                    zzuq = zzzu.zzb(com_google_android_gms_internal_measurement_zzzi, 360);
                    length = this.zzayx == null ? 0 : this.zzayx.length;
                    obj = new int[(zzuq + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzayx, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = com_google_android_gms_internal_measurement_zzzi.zzvi();
                        com_google_android_gms_internal_measurement_zzzi.zzuq();
                        length++;
                    }
                    obj[length] = com_google_android_gms_internal_measurement_zzzi.zzvi();
                    this.zzayx = obj;
                    break;
                case 362:
                    zzuq = com_google_android_gms_internal_measurement_zzzi.zzar(com_google_android_gms_internal_measurement_zzzi.zzvi());
                    length = com_google_android_gms_internal_measurement_zzzi.getPosition();
                    int i = 0;
                    while (com_google_android_gms_internal_measurement_zzzi.zzzf() > 0) {
                        com_google_android_gms_internal_measurement_zzzi.zzvi();
                        i++;
                    }
                    com_google_android_gms_internal_measurement_zzzi.zzca(length);
                    length = this.zzayx == null ? 0 : this.zzayx.length;
                    Object obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzayx, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = com_google_android_gms_internal_measurement_zzzi.zzvi();
                        length++;
                    }
                    this.zzayx = obj2;
                    com_google_android_gms_internal_measurement_zzzi.zzas(zzuq);
                    break;
                case 368:
                    this.zzayy = Long.valueOf(com_google_android_gms_internal_measurement_zzzi.zzvj());
                    break;
                default:
                    if (super.zza(com_google_android_gms_internal_measurement_zzzi, zzuq)) {
                        break;
                    }
                    return this;
            }
        }
    }
}
