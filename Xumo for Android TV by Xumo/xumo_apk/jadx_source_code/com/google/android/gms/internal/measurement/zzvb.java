package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Arrays;

final class zzvb extends zzuz {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzbvh;
    private int zzbvi;
    private int zzbvj;
    private int zzbvk;
    private int zzbvl;

    private zzvb(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzbvl = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i2 + i;
        this.pos = i;
        this.zzbvj = this.pos;
        this.zzbvh = z;
    }

    public final int zzuq() throws IOException {
        if (zzvg()) {
            this.zzbvk = 0;
            return 0;
        }
        this.zzbvk = zzvi();
        if ((this.zzbvk >>> 3) != 0) {
            return this.zzbvk;
        }
        throw new zzwe("Protocol message contained an invalid tag (zero).");
    }

    public final void zzao(int i) throws zzwe {
        if (this.zzbvk != i) {
            throw zzwe.zzxa();
        }
    }

    public final boolean zzap(int i) throws IOException {
        int i2 = 0;
        switch (i & 7) {
            case 0:
                if (this.limit - this.pos >= 10) {
                    while (i2 < 10) {
                        i = this.buffer;
                        int i3 = this.pos;
                        this.pos = i3 + 1;
                        if (i[i3] < 0) {
                            i2++;
                        }
                    }
                    throw zzwe.zzwz();
                }
                while (i2 < 10) {
                    if (zzvn() < 0) {
                        i2++;
                    }
                }
                throw zzwe.zzwz();
                return true;
            case 1:
                zzat(8);
                return true;
            case 2:
                zzat(zzvi());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzat(4);
                return true;
            default:
                throw zzwe.zzxb();
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

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(zzvl());
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(zzvk());
    }

    public final long zzur() throws IOException {
        return zzvj();
    }

    public final long zzus() throws IOException {
        return zzvj();
    }

    public final int zzut() throws IOException {
        return zzvi();
    }

    public final long zzuu() throws IOException {
        return zzvl();
    }

    public final int zzuv() throws IOException {
        return zzvk();
    }

    public final boolean zzuw() throws IOException {
        return zzvj() != 0;
    }

    public final String readString() throws IOException {
        int zzvi = zzvi();
        if (zzvi > 0 && zzvi <= this.limit - this.pos) {
            String str = new String(this.buffer, this.pos, zzvi, zzvz.UTF_8);
            this.pos += zzvi;
            return str;
        } else if (zzvi == 0) {
            return "";
        } else {
            if (zzvi < 0) {
                throw zzwe.zzwy();
            }
            throw zzwe.zzwx();
        }
    }

    public final String zzux() throws IOException {
        int zzvi = zzvi();
        if (zzvi > 0 && zzvi <= this.limit - this.pos) {
            String zzh = zzyu.zzh(this.buffer, this.pos, zzvi);
            this.pos += zzvi;
            return zzh;
        } else if (zzvi == 0) {
            return "";
        } else {
            if (zzvi <= 0) {
                throw zzwe.zzwy();
            }
            throw zzwe.zzwx();
        }
    }

    public final <T extends zzxe> T zza(zzxo<T> com_google_android_gms_internal_measurement_zzxo_T, zzvk com_google_android_gms_internal_measurement_zzvk) throws IOException {
        int zzvi = zzvi();
        if (this.zzbvc < this.zzbvd) {
            zzvi = zzar(zzvi);
            this.zzbvc++;
            zzxe com_google_android_gms_internal_measurement_zzxe = (zzxe) com_google_android_gms_internal_measurement_zzxo_T.zza(this, com_google_android_gms_internal_measurement_zzvk);
            zzao(null);
            this.zzbvc--;
            zzas(zzvi);
            return com_google_android_gms_internal_measurement_zzxe;
        }
        throw zzwe.zzxc();
    }

    public final zzun zzuy() throws IOException {
        int zzvi = zzvi();
        if (zzvi > 0 && zzvi <= this.limit - this.pos) {
            zzun zzb = zzun.zzb(this.buffer, this.pos, zzvi);
            this.pos += zzvi;
            return zzb;
        } else if (zzvi == 0) {
            return zzun.zzbuu;
        } else {
            byte[] copyOfRange;
            if (zzvi > 0 && zzvi <= this.limit - this.pos) {
                int i = this.pos;
                this.pos += zzvi;
                copyOfRange = Arrays.copyOfRange(this.buffer, i, this.pos);
            } else if (zzvi > 0) {
                throw zzwe.zzwx();
            } else if (zzvi == 0) {
                copyOfRange = zzvz.zzcae;
            } else {
                throw zzwe.zzwy();
            }
            return zzun.zzi(copyOfRange);
        }
    }

    public final int zzuz() throws IOException {
        return zzvi();
    }

    public final int zzva() throws IOException {
        return zzvi();
    }

    public final int zzvb() throws IOException {
        return zzvk();
    }

    public final long zzvc() throws IOException {
        return zzvl();
    }

    public final int zzvd() throws IOException {
        int zzvi = zzvi();
        return (-(zzvi & 1)) ^ (zzvi >>> 1);
    }

    public final long zzve() throws IOException {
        long zzvj = zzvj();
        return (-(zzvj & 1)) ^ (zzvj >>> 1);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zzvi() throws java.io.IOException {
        /*
        r5 = this;
        r0 = r5.pos;
        r1 = r5.limit;
        if (r1 == r0) goto L_0x006d;
    L_0x0006:
        r1 = r5.buffer;
        r2 = r0 + 1;
        r0 = r1[r0];
        if (r0 < 0) goto L_0x0011;
    L_0x000e:
        r5.pos = r2;
        return r0;
    L_0x0011:
        r3 = r5.limit;
        r3 = r3 - r2;
        r4 = 9;
        if (r3 < r4) goto L_0x006d;
    L_0x0018:
        r3 = r2 + 1;
        r2 = r1[r2];
        r2 = r2 << 7;
        r0 = r0 ^ r2;
        if (r0 >= 0) goto L_0x0024;
    L_0x0021:
        r0 = r0 ^ -128;
        goto L_0x006a;
    L_0x0024:
        r2 = r3 + 1;
        r3 = r1[r3];
        r3 = r3 << 14;
        r0 = r0 ^ r3;
        if (r0 < 0) goto L_0x0031;
    L_0x002d:
        r0 = r0 ^ 16256;
    L_0x002f:
        r3 = r2;
        goto L_0x006a;
    L_0x0031:
        r3 = r2 + 1;
        r2 = r1[r2];
        r2 = r2 << 21;
        r0 = r0 ^ r2;
        if (r0 >= 0) goto L_0x003f;
    L_0x003a:
        r1 = -2080896; // 0xffffffffffe03f80 float:NaN double:NaN;
        r0 = r0 ^ r1;
        goto L_0x006a;
    L_0x003f:
        r2 = r3 + 1;
        r3 = r1[r3];
        r4 = r3 << 28;
        r0 = r0 ^ r4;
        r4 = 266354560; // 0xfe03f80 float:2.2112565E-29 double:1.315966377E-315;
        r0 = r0 ^ r4;
        if (r3 >= 0) goto L_0x002f;
    L_0x004c:
        r3 = r2 + 1;
        r2 = r1[r2];
        if (r2 >= 0) goto L_0x006a;
    L_0x0052:
        r2 = r3 + 1;
        r3 = r1[r3];
        if (r3 >= 0) goto L_0x002f;
    L_0x0058:
        r3 = r2 + 1;
        r2 = r1[r2];
        if (r2 >= 0) goto L_0x006a;
    L_0x005e:
        r2 = r3 + 1;
        r3 = r1[r3];
        if (r3 >= 0) goto L_0x002f;
    L_0x0064:
        r3 = r2 + 1;
        r1 = r1[r2];
        if (r1 < 0) goto L_0x006d;
    L_0x006a:
        r5.pos = r3;
        return r0;
    L_0x006d:
        r0 = r5.zzvf();
        r0 = (int) r0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzvb.zzvi():int");
    }

    private final long zzvj() throws IOException {
        int i = this.pos;
        if (this.limit != i) {
            byte[] bArr = this.buffer;
            int i2 = i + 1;
            byte b = bArr[i];
            if (b >= (byte) 0) {
                this.pos = i2;
                return (long) b;
            } else if (this.limit - i2 >= 9) {
                long j;
                long j2;
                long j3;
                int i3 = i2 + 1;
                i = b ^ (bArr[i2] << 7);
                if (i < 0) {
                    j = (long) (i ^ -128);
                } else {
                    i2 = i3 + 1;
                    i ^= bArr[i3] << 14;
                    if (i >= 0) {
                        j2 = (long) (i ^ 16256);
                        i = i2;
                        j3 = j2;
                        this.pos = i;
                        return j3;
                    }
                    i3 = i2 + 1;
                    i ^= bArr[i2] << 21;
                    if (i < 0) {
                        j = (long) (i ^ -2080896);
                    } else {
                        long j4 = (long) i;
                        i = i3 + 1;
                        j3 = (((long) bArr[i3]) << 28) ^ j4;
                        if (j3 >= 0) {
                            j3 ^= 266354560;
                        } else {
                            int i4 = i + 1;
                            j3 ^= ((long) bArr[i]) << 35;
                            if (j3 < 0) {
                                j = -34093383808L ^ j3;
                            } else {
                                i = i4 + 1;
                                j3 ^= ((long) bArr[i4]) << 42;
                                if (j3 >= 0) {
                                    j3 ^= 4363953127296L;
                                } else {
                                    i4 = i + 1;
                                    j3 ^= ((long) bArr[i]) << 49;
                                    if (j3 < 0) {
                                        j = -558586000294016L ^ j3;
                                    } else {
                                        i = i4 + 1;
                                        j3 = (j3 ^ (((long) bArr[i4]) << 56)) ^ 71499008037633920L;
                                        if (j3 < 0) {
                                            i4 = i + 1;
                                            if (((long) bArr[i]) >= 0) {
                                                i = i4;
                                            }
                                        }
                                    }
                                }
                            }
                            j3 = j;
                            i = i4;
                        }
                        this.pos = i;
                        return j3;
                    }
                }
                j2 = j;
                i = i3;
                j3 = j2;
                this.pos = i;
                return j3;
            }
        }
        return zzvf();
    }

    final long zzvf() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzvn = zzvn();
            j |= ((long) (zzvn & 127)) << i;
            if ((zzvn & 128) == 0) {
                return j;
            }
        }
        throw zzwe.zzwz();
    }

    private final int zzvk() throws IOException {
        int i = this.pos;
        if (this.limit - i >= 4) {
            byte[] bArr = this.buffer;
            this.pos = i + 4;
            return ((bArr[i + 3] & 255) << 24) | (((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)) | ((bArr[i + 2] & 255) << 16));
        }
        throw zzwe.zzwx();
    }

    private final long zzvl() throws IOException {
        int i = this.pos;
        if (this.limit - i >= 8) {
            byte[] bArr = this.buffer;
            this.pos = i + 8;
            return ((((long) bArr[i + 7]) & 255) << 56) | (((((((((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8)) | ((((long) bArr[i + 2]) & 255) << 16)) | ((((long) bArr[i + 3]) & 255) << 24)) | ((((long) bArr[i + 4]) & 255) << 32)) | ((((long) bArr[i + 5]) & 255) << 40)) | ((((long) bArr[i + 6]) & 255) << 48));
        }
        throw zzwe.zzwx();
    }

    public final int zzar(int i) throws zzwe {
        if (i >= 0) {
            i += zzvh();
            int i2 = this.zzbvl;
            if (i <= i2) {
                this.zzbvl = i;
                zzvm();
                return i2;
            }
            throw zzwe.zzwx();
        }
        throw zzwe.zzwy();
    }

    private final void zzvm() {
        this.limit += this.zzbvi;
        int i = this.limit - this.zzbvj;
        if (i > this.zzbvl) {
            this.zzbvi = i - this.zzbvl;
            this.limit -= this.zzbvi;
            return;
        }
        this.zzbvi = 0;
    }

    public final void zzas(int i) {
        this.zzbvl = i;
        zzvm();
    }

    public final boolean zzvg() throws IOException {
        return this.pos == this.limit;
    }

    public final int zzvh() {
        return this.pos - this.zzbvj;
    }

    private final byte zzvn() throws IOException {
        if (this.pos != this.limit) {
            byte[] bArr = this.buffer;
            int i = this.pos;
            this.pos = i + 1;
            return bArr[i];
        }
        throw zzwe.zzwx();
    }

    public final void zzat(int i) throws IOException {
        if (i >= 0 && i <= this.limit - this.pos) {
            this.pos += i;
        } else if (i < 0) {
            throw zzwe.zzwy();
        } else {
            throw zzwe.zzwx();
        }
    }
}
