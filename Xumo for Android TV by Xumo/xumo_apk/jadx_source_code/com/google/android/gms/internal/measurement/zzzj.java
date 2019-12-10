package com.google.android.gms.internal.measurement;

import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

public final class zzzj {
    private final ByteBuffer zzbvv;
    private zzve zzcfv;
    private int zzcfw;

    private zzzj(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    public static int zzbk(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (i & -268435456) == 0 ? 4 : 5;
    }

    public static int zzbl(long j) {
        return (-128 & j) == 0 ? 1 : (-16384 & j) == 0 ? 2 : (-2097152 & j) == 0 ? 3 : (-268435456 & j) == 0 ? 4 : (-34359738368L & j) == 0 ? 5 : (-4398046511104L & j) == 0 ? 6 : (-562949953421312L & j) == 0 ? 7 : (-72057594037927936L & j) == 0 ? 8 : (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    private zzzj(ByteBuffer byteBuffer) {
        this.zzbvv = byteBuffer;
        this.zzbvv.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static zzzj zzo(byte[] bArr) {
        return zzk(bArr, 0, bArr.length);
    }

    public static zzzj zzk(byte[] bArr, int i, int i2) {
        return new zzzj(bArr, 0, i2);
    }

    private final zzve zzzg() throws IOException {
        if (this.zzcfv == null) {
            this.zzcfv = zzve.zza(this.zzbvv);
            this.zzcfw = this.zzbvv.position();
        } else if (this.zzcfw != this.zzbvv.position()) {
            this.zzcfv.write(this.zzbvv.array(), this.zzcfw, this.zzbvv.position() - this.zzcfw);
            this.zzcfw = this.zzbvv.position();
        }
        return this.zzcfv;
    }

    public final void zza(int i, double d) throws IOException {
        zzc(i, 1);
        i = Double.doubleToLongBits(d);
        if (this.zzbvv.remaining() >= 8) {
            this.zzbvv.putLong(i);
            return;
        }
        throw new zzzk(this.zzbvv.position(), this.zzbvv.limit());
    }

    public final void zza(int i, float f) throws IOException {
        zzc(i, 5);
        i = Float.floatToIntBits(f);
        if (this.zzbvv.remaining() >= 4) {
            this.zzbvv.putInt(i);
            return;
        }
        throw new zzzk(this.zzbvv.position(), this.zzbvv.limit());
    }

    public final void zza(int i, long j) throws IOException {
        zzc(i, 0);
        zzbk(j);
    }

    public final void zzi(int i, long j) throws IOException {
        zzc(i, 0);
        zzbk(j);
    }

    public final void zzd(int i, int i2) throws IOException {
        zzc(i, 0);
        if (i2 >= 0) {
            zzcc(i2);
        } else {
            zzbk((long) i2);
        }
    }

    public final void zzb(int i, boolean z) throws IOException {
        zzc(i, 0);
        i = (byte) z;
        if (this.zzbvv.hasRemaining()) {
            this.zzbvv.put(i);
            return;
        }
        throw new zzzk(this.zzbvv.position(), this.zzbvv.limit());
    }

    public final void zzb(int i, String str) throws IOException {
        zzc(i, 2);
        try {
            i = zzbk(str.length());
            if (i == zzbk(str.length() * 3)) {
                int position = this.zzbvv.position();
                if (this.zzbvv.remaining() >= i) {
                    this.zzbvv.position(position + i);
                    zzd((CharSequence) str, this.zzbvv);
                    str = this.zzbvv.position();
                    this.zzbvv.position(position);
                    zzcc((str - position) - i);
                    this.zzbvv.position(str);
                    return;
                }
                throw new zzzk(position + i, this.zzbvv.limit());
            }
            zzcc(zza(str));
            zzd((CharSequence) str, this.zzbvv);
        } catch (int i2) {
            str = new zzzk(this.zzbvv.position(), this.zzbvv.limit());
            str.initCause(i2);
            throw str;
        }
    }

    public final void zza(int i, zzzr com_google_android_gms_internal_measurement_zzzr) throws IOException {
        zzc(i, 2);
        zzb(com_google_android_gms_internal_measurement_zzzr);
    }

    public final void zze(int i, zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException {
        zzve zzzg = zzzg();
        zzzg.zza(i, com_google_android_gms_internal_measurement_zzxe);
        zzzg.flush();
        this.zzcfw = this.zzbvv.position();
    }

    private static int zza(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < '') {
            i2++;
        }
        int i3 = length;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            if (charAt < 'ࠀ') {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt2 = charSequence.charAt(i2);
                    if (charAt2 < 'ࠀ') {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if ('?' <= charAt2 && charAt2 <= '?') {
                            if (Character.codePointAt(charSequence, i2) >= 65536) {
                                i2++;
                            } else {
                                StringBuilder stringBuilder = new StringBuilder(39);
                                stringBuilder.append("Unpaired surrogate at index ");
                                stringBuilder.append(i2);
                                throw new IllegalArgumentException(stringBuilder.toString());
                            }
                        }
                    }
                    i2++;
                }
                i3 += i;
                if (i3 >= length) {
                    return i3;
                }
                long j = ((long) i3) + 4294967296L;
                StringBuilder stringBuilder2 = new StringBuilder(54);
                stringBuilder2.append("UTF-8 length does not fit in int: ");
                stringBuilder2.append(j);
                throw new IllegalArgumentException(stringBuilder2.toString());
            }
        }
        if (i3 >= length) {
            return i3;
        }
        long j2 = ((long) i3) + 4294967296L;
        StringBuilder stringBuilder22 = new StringBuilder(54);
        stringBuilder22.append("UTF-8 length does not fit in int: ");
        stringBuilder22.append(j2);
        throw new IllegalArgumentException(stringBuilder22.toString());
    }

    private static void zzd(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        int i = 0;
        int remaining;
        char charAt;
        if (byteBuffer.hasArray()) {
            try {
                int i2;
                byte[] array = byteBuffer.array();
                int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
                remaining = byteBuffer.remaining();
                int length = charSequence.length();
                remaining += arrayOffset;
                while (i < length) {
                    i2 = i + arrayOffset;
                    if (i2 >= remaining) {
                        break;
                    }
                    char charAt2 = charSequence.charAt(i);
                    if (charAt2 >= '') {
                        break;
                    }
                    array[i2] = (byte) charAt2;
                    i++;
                }
                if (i == length) {
                    arrayOffset += length;
                } else {
                    arrayOffset += i;
                    while (i < length) {
                        int i3;
                        char charAt3 = charSequence.charAt(i);
                        if (charAt3 < '' && arrayOffset < remaining) {
                            i3 = arrayOffset + 1;
                            array[arrayOffset] = (byte) charAt3;
                        } else if (charAt3 < 'ࠀ' && arrayOffset <= remaining - 2) {
                            i3 = arrayOffset + 1;
                            array[arrayOffset] = (byte) ((charAt3 >>> 6) | 960);
                            arrayOffset = i3 + 1;
                            array[i3] = (byte) ((charAt3 & 63) | 128);
                            i++;
                        } else if ((charAt3 < '?' || '?' < charAt3) && arrayOffset <= remaining - 3) {
                            i3 = arrayOffset + 1;
                            array[arrayOffset] = (byte) ((charAt3 >>> 12) | 480);
                            arrayOffset = i3 + 1;
                            array[i3] = (byte) (((charAt3 >>> 6) & 63) | 128);
                            i3 = arrayOffset + 1;
                            array[arrayOffset] = (byte) ((charAt3 & 63) | 128);
                        } else if (arrayOffset <= remaining - 4) {
                            i3 = i + 1;
                            if (i3 != charSequence.length()) {
                                charAt = charSequence.charAt(i3);
                                if (Character.isSurrogatePair(charAt3, charAt)) {
                                    i = Character.toCodePoint(charAt3, charAt);
                                    i2 = arrayOffset + 1;
                                    array[arrayOffset] = (byte) ((i >>> 18) | PsExtractor.VIDEO_STREAM_MASK);
                                    arrayOffset = i2 + 1;
                                    array[i2] = (byte) (((i >>> 12) & 63) | 128);
                                    i2 = arrayOffset + 1;
                                    array[arrayOffset] = (byte) (((i >>> 6) & 63) | 128);
                                    arrayOffset = i2 + 1;
                                    array[i2] = (byte) ((i & 63) | 128);
                                    i = i3;
                                    i++;
                                } else {
                                    i = i3;
                                }
                            }
                            i--;
                            byteBuffer = new StringBuilder(39);
                            byteBuffer.append("Unpaired surrogate at index ");
                            byteBuffer.append(i);
                            throw new IllegalArgumentException(byteBuffer.toString());
                        } else {
                            StringBuilder stringBuilder = new StringBuilder(37);
                            stringBuilder.append("Failed writing ");
                            stringBuilder.append(charAt3);
                            stringBuilder.append(" at index ");
                            stringBuilder.append(arrayOffset);
                            throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
                        }
                        arrayOffset = i3;
                        i++;
                    }
                }
                byteBuffer.position(arrayOffset - byteBuffer.arrayOffset());
                return;
            } catch (CharSequence charSequence2) {
                byteBuffer = new BufferOverflowException();
                byteBuffer.initCause(charSequence2);
                throw byteBuffer;
            }
        }
        int length2 = charSequence2.length();
        while (i < length2) {
            char charAt4 = charSequence2.charAt(i);
            if (charAt4 < '') {
                byteBuffer.put((byte) charAt4);
            } else if (charAt4 < 'ࠀ') {
                byteBuffer.put((byte) ((charAt4 >>> 6) | 960));
                byteBuffer.put((byte) ((charAt4 & 63) | 128));
            } else {
                if (charAt4 >= '?') {
                    if ('?' >= charAt4) {
                        remaining = i + 1;
                        if (remaining != charSequence2.length()) {
                            charAt = charSequence2.charAt(remaining);
                            if (Character.isSurrogatePair(charAt4, charAt)) {
                                i = Character.toCodePoint(charAt4, charAt);
                                byteBuffer.put((byte) ((i >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                                byteBuffer.put((byte) (((i >>> 12) & 63) | 128));
                                byteBuffer.put((byte) (((i >>> 6) & 63) | 128));
                                byteBuffer.put((byte) ((i & 63) | 128));
                                i = remaining;
                            } else {
                                i = remaining;
                            }
                        }
                        i--;
                        byteBuffer = new StringBuilder(39);
                        byteBuffer.append("Unpaired surrogate at index ");
                        byteBuffer.append(i);
                        throw new IllegalArgumentException(byteBuffer.toString());
                    }
                }
                byteBuffer.put((byte) ((charAt4 >>> 12) | 480));
                byteBuffer.put((byte) (((charAt4 >>> 6) & 63) | 128));
                byteBuffer.put((byte) ((charAt4 & 63) | 128));
            }
            i++;
        }
    }

    public final void zzb(zzzr com_google_android_gms_internal_measurement_zzzr) throws IOException {
        zzcc(com_google_android_gms_internal_measurement_zzzr.zzzo());
        com_google_android_gms_internal_measurement_zzzr.zza(this);
    }

    public static int zzd(int i, long j) {
        return zzbc(i) + zzbl(j);
    }

    public static int zzh(int i, int i2) {
        return zzbc(i) + zzbd(i2);
    }

    public static int zzc(int i, String str) {
        return zzbc(i) + zzge(str);
    }

    public static int zzb(int i, zzzr com_google_android_gms_internal_measurement_zzzr) {
        i = zzbc(i);
        int zzwe = com_google_android_gms_internal_measurement_zzzr.zzwe();
        return i + (zzbk(zzwe) + zzwe);
    }

    public static int zzbd(int i) {
        return i >= 0 ? zzbk(i) : 10;
    }

    public static int zzge(String str) {
        int zza = zza(str);
        return zzbk(zza) + zza;
    }

    public final void zzzh() {
        if (this.zzbvv.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", new Object[]{Integer.valueOf(this.zzbvv.remaining())}));
        }
    }

    private final void zzcb(int i) throws IOException {
        i = (byte) i;
        if (this.zzbvv.hasRemaining()) {
            this.zzbvv.put(i);
            return;
        }
        throw new zzzk(this.zzbvv.position(), this.zzbvv.limit());
    }

    public final void zzp(byte[] bArr) throws IOException {
        int length = bArr.length;
        if (this.zzbvv.remaining() >= length) {
            this.zzbvv.put(bArr, 0, length);
            return;
        }
        throw new zzzk(this.zzbvv.position(), this.zzbvv.limit());
    }

    public final void zzc(int i, int i2) throws IOException {
        zzcc((i << 3) | i2);
    }

    public static int zzbc(int i) {
        return zzbk(i << 3);
    }

    public final void zzcc(int i) throws IOException {
        while ((i & -128) != 0) {
            zzcb((i & 127) | 128);
            i >>>= 7;
        }
        zzcb(i);
    }

    private final void zzbk(long j) throws IOException {
        while ((-128 & j) != 0) {
            zzcb((((int) j) & 127) | 128);
            j >>>= 7;
        }
        zzcb((int) j);
    }
}
