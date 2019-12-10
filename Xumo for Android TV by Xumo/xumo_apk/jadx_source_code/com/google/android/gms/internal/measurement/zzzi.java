package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzzi {
    private final byte[] buffer;
    private int zzbvc;
    private int zzbvd = 64;
    private int zzbve = 67108864;
    private int zzbvi;
    private int zzbvk;
    private int zzbvl = Integer.MAX_VALUE;
    private final int zzcfq;
    private final int zzcfr;
    private int zzcfs;
    private int zzcft;
    private zzuz zzcfu;

    public static zzzi zzn(byte[] bArr) {
        return zzj(bArr, 0, bArr.length);
    }

    public static zzzi zzj(byte[] bArr, int i, int i2) {
        return new zzzi(bArr, 0, i2);
    }

    public final int zzuq() throws IOException {
        if (this.zzcft == this.zzcfs) {
            this.zzbvk = 0;
            return 0;
        }
        this.zzbvk = zzvi();
        if (this.zzbvk != 0) {
            return this.zzbvk;
        }
        throw new zzzq("Protocol message contained an invalid tag (zero).");
    }

    public final void zzao(int i) throws zzzq {
        if (this.zzbvk != i) {
            throw new zzzq("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzap(int i) throws IOException {
        switch (i & 7) {
            case 0:
                zzvi();
                return true;
            case 1:
                zzvl();
                return true;
            case 2:
                zzat(zzvi());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzvk();
                return true;
            default:
                throw new zzzq("Protocol message tag had invalid wire type.");
        }
        int zzuq;
        do {
            zzuq = zzuq();
            if (zzuq != 0) {
            }
            zzao(((i >>> 3) << 3) | 4);
            return true;
        } while (zzap(zzuq));
        zzao(((i >>> 3) << 3) | 4);
        return true;
    }

    public final boolean zzuw() throws IOException {
        return zzvi() != 0;
    }

    public final String readString() throws IOException {
        int zzvi = zzvi();
        if (zzvi < 0) {
            throw zzzq.zzzl();
        } else if (zzvi <= this.zzcfs - this.zzcft) {
            String str = new String(this.buffer, this.zzcft, zzvi, zzzp.UTF_8);
            this.zzcft += zzvi;
            return str;
        } else {
            throw zzzq.zzzk();
        }
    }

    public final void zza(zzzr com_google_android_gms_internal_measurement_zzzr, int i) throws IOException {
        if (this.zzbvc < this.zzbvd) {
            this.zzbvc++;
            com_google_android_gms_internal_measurement_zzzr.zza(this);
            zzao((i << 3) | 4);
            this.zzbvc--;
            return;
        }
        throw zzzq.zzzn();
    }

    public final void zza(zzzr com_google_android_gms_internal_measurement_zzzr) throws IOException {
        int zzvi = zzvi();
        if (this.zzbvc < this.zzbvd) {
            zzvi = zzar(zzvi);
            this.zzbvc++;
            com_google_android_gms_internal_measurement_zzzr.zza(this);
            zzao(null);
            this.zzbvc--;
            zzas(zzvi);
            return;
        }
        throw zzzq.zzzn();
    }

    public final int zzvi() throws IOException {
        byte zzvn = zzvn();
        if (zzvn >= (byte) 0) {
            return zzvn;
        }
        int i = zzvn & 127;
        byte zzvn2 = zzvn();
        if (zzvn2 >= (byte) 0) {
            i |= zzvn2 << 7;
        } else {
            i |= (zzvn2 & 127) << 7;
            zzvn2 = zzvn();
            if (zzvn2 >= (byte) 0) {
                i |= zzvn2 << 14;
            } else {
                i |= (zzvn2 & 127) << 14;
                zzvn2 = zzvn();
                if (zzvn2 >= (byte) 0) {
                    i |= zzvn2 << 21;
                } else {
                    i |= (zzvn2 & 127) << 21;
                    zzvn2 = zzvn();
                    i |= zzvn2 << 28;
                    if (zzvn2 < (byte) 0) {
                        for (int i2 = 0; i2 < 5; i2++) {
                            if (zzvn() >= (byte) 0) {
                                return i;
                            }
                        }
                        throw zzzq.zzzm();
                    }
                }
            }
        }
        return i;
    }

    public final long zzvj() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzvn = zzvn();
            j |= ((long) (zzvn & 127)) << i;
            if ((zzvn & 128) == 0) {
                return j;
            }
        }
        throw zzzq.zzzm();
    }

    public final int zzvk() throws IOException {
        return (((zzvn() & 255) | ((zzvn() & 255) << 8)) | ((zzvn() & 255) << 16)) | ((zzvn() & 255) << 24);
    }

    public final long zzvl() throws IOException {
        byte zzvn = zzvn();
        byte zzvn2 = zzvn();
        return ((((((((((long) zzvn2) & 255) << 8) | (((long) zzvn) & 255)) | ((((long) zzvn()) & 255) << 16)) | ((((long) zzvn()) & 255) << 24)) | ((((long) zzvn()) & 255) << 32)) | ((((long) zzvn()) & 255) << 40)) | ((((long) zzvn()) & 255) << 48)) | ((((long) zzvn()) & 255) << 56);
    }

    private zzzi(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzcfq = i;
        i2 += i;
        this.zzcfs = i2;
        this.zzcfr = i2;
        this.zzcft = i;
    }

    private final zzuz zzze() throws IOException {
        if (this.zzcfu == null) {
            this.zzcfu = zzuz.zzd(this.buffer, this.zzcfq, this.zzcfr);
        }
        int zzvh = this.zzcfu.zzvh();
        int i = this.zzcft - this.zzcfq;
        if (zzvh <= i) {
            this.zzcfu.zzat(i - zzvh);
            this.zzcfu.zzaq(this.zzbvd - this.zzbvc);
            return this.zzcfu;
        }
        throw new IOException(String.format("CodedInputStream read ahead of CodedInputByteBufferNano: %s > %s", new Object[]{Integer.valueOf(zzvh), Integer.valueOf(i)}));
    }

    public final <T extends zzvx<T, ?>> T zza(zzxo<T> com_google_android_gms_internal_measurement_zzxo_T) throws IOException {
        try {
            zzvx com_google_android_gms_internal_measurement_zzvx = (zzvx) zzze().zza(com_google_android_gms_internal_measurement_zzxo_T, zzvk.zzvz());
            zzap(this.zzbvk);
            return com_google_android_gms_internal_measurement_zzvx;
        } catch (zzxo<T> com_google_android_gms_internal_measurement_zzxo_T2) {
            throw new zzzq("", com_google_android_gms_internal_measurement_zzxo_T2);
        }
    }

    public final int zzar(int i) throws zzzq {
        if (i >= 0) {
            i += this.zzcft;
            int i2 = this.zzbvl;
            if (i <= i2) {
                this.zzbvl = i;
                zzvm();
                return i2;
            }
            throw zzzq.zzzk();
        }
        throw zzzq.zzzl();
    }

    private final void zzvm() {
        this.zzcfs += this.zzbvi;
        int i = this.zzcfs;
        if (i > this.zzbvl) {
            this.zzbvi = i - this.zzbvl;
            this.zzcfs -= this.zzbvi;
            return;
        }
        this.zzbvi = 0;
    }

    public final void zzas(int i) {
        this.zzbvl = i;
        zzvm();
    }

    public final int zzzf() {
        if (this.zzbvl == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzbvl - this.zzcft;
    }

    public final int getPosition() {
        return this.zzcft - this.zzcfq;
    }

    public final byte[] zzs(int i, int i2) {
        if (i2 == 0) {
            return zzzu.zzcgs;
        }
        Object obj = new byte[i2];
        System.arraycopy(this.buffer, this.zzcfq + i, obj, 0, i2);
        return obj;
    }

    public final void zzca(int i) {
        zzt(i, this.zzbvk);
    }

    final void zzt(int i, int i2) {
        if (i > this.zzcft - this.zzcfq) {
            int i3 = this.zzcft - this.zzcfq;
            StringBuilder stringBuilder = new StringBuilder(50);
            stringBuilder.append("Position ");
            stringBuilder.append(i);
            stringBuilder.append(" is beyond current ");
            stringBuilder.append(i3);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (i >= 0) {
            this.zzcft = this.zzcfq + i;
            this.zzbvk = i2;
        } else {
            StringBuilder stringBuilder2 = new StringBuilder(24);
            stringBuilder2.append("Bad position ");
            stringBuilder2.append(i);
            throw new IllegalArgumentException(stringBuilder2.toString());
        }
    }

    private final byte zzvn() throws IOException {
        if (this.zzcft != this.zzcfs) {
            byte[] bArr = this.buffer;
            int i = this.zzcft;
            this.zzcft = i + 1;
            return bArr[i];
        }
        throw zzzq.zzzk();
    }

    private final void zzat(int i) throws IOException {
        if (i < 0) {
            throw zzzq.zzzl();
        } else if (this.zzcft + i > this.zzbvl) {
            zzat(this.zzbvl - this.zzcft);
            throw zzzq.zzzk();
        } else if (i <= this.zzcfs - this.zzcft) {
            this.zzcft += i;
        } else {
            throw zzzq.zzzk();
        }
    }
}
