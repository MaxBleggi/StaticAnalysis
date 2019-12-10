package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class zzve extends zzum {
    private static final Logger logger = Logger.getLogger(zzve.class.getName());
    private static final boolean zzbvq = zzys.zzyv();
    zzvg zzbvr = this;

    public static class zzc extends IOException {
        zzc() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        zzc(String str) {
            String valueOf = String.valueOf("CodedOutputStream was writing to a flat byte array and ran out of space.: ");
            str = String.valueOf(str);
            super(str.length() != 0 ? valueOf.concat(str) : new String(valueOf));
        }

        zzc(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }

        zzc(String str, Throwable th) {
            String valueOf = String.valueOf("CodedOutputStream was writing to a flat byte array and ran out of space.: ");
            str = String.valueOf(str);
            super(str.length() != 0 ? valueOf.concat(str) : new String(valueOf), th);
        }
    }

    static class zza extends zzve {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        zza(byte[] bArr, int i, int i2) {
            super();
            if (bArr != null) {
                int i3 = i + i2;
                if (((i | i2) | (bArr.length - i3)) >= 0) {
                    this.buffer = bArr;
                    this.offset = i;
                    this.position = i;
                    this.limit = i3;
                    return;
                }
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)}));
            }
            throw new NullPointerException("buffer");
        }

        public void flush() {
        }

        public final void zzc(int i, int i2) throws IOException {
            zzaz((i << 3) | i2);
        }

        public final void zzd(int i, int i2) throws IOException {
            zzc(i, 0);
            zzay(i2);
        }

        public final void zze(int i, int i2) throws IOException {
            zzc(i, 0);
            zzaz(i2);
        }

        public final void zzg(int i, int i2) throws IOException {
            zzc(i, 5);
            zzbb(i2);
        }

        public final void zza(int i, long j) throws IOException {
            zzc(i, 0);
            zzay(j);
        }

        public final void zzc(int i, long j) throws IOException {
            zzc(i, 1);
            zzba(j);
        }

        public final void zzb(int i, boolean z) throws IOException {
            zzc(i, 0);
            zzc((byte) z);
        }

        public final void zzb(int i, String str) throws IOException {
            zzc(i, 2);
            zzgd(str);
        }

        public final void zza(int i, zzun com_google_android_gms_internal_measurement_zzun) throws IOException {
            zzc(i, 2);
            zza(com_google_android_gms_internal_measurement_zzun);
        }

        public final void zza(zzun com_google_android_gms_internal_measurement_zzun) throws IOException {
            zzaz(com_google_android_gms_internal_measurement_zzun.size());
            com_google_android_gms_internal_measurement_zzun.zza((zzum) this);
        }

        public final void zze(byte[] bArr, int i, int i2) throws IOException {
            zzaz(i2);
            write(bArr, 0, i2);
        }

        public final void zza(int i, zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException {
            zzc(i, 2);
            zzb(com_google_android_gms_internal_measurement_zzxe);
        }

        final void zza(int i, zzxe com_google_android_gms_internal_measurement_zzxe, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException {
            zzc(i, 2);
            zzug com_google_android_gms_internal_measurement_zzug = (zzug) com_google_android_gms_internal_measurement_zzxe;
            int zzue = com_google_android_gms_internal_measurement_zzug.zzue();
            if (zzue == -1) {
                zzue = com_google_android_gms_internal_measurement_zzxu.zzai(com_google_android_gms_internal_measurement_zzug);
                com_google_android_gms_internal_measurement_zzug.zzah(zzue);
            }
            zzaz(zzue);
            com_google_android_gms_internal_measurement_zzxu.zza(com_google_android_gms_internal_measurement_zzxe, this.zzbvr);
        }

        public final void zzb(int i, zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException {
            zzc(1, 3);
            zze(2, i);
            zza(3, com_google_android_gms_internal_measurement_zzxe);
            zzc(1, 4);
        }

        public final void zzb(int i, zzun com_google_android_gms_internal_measurement_zzun) throws IOException {
            zzc(1, 3);
            zze(2, i);
            zza(3, com_google_android_gms_internal_measurement_zzun);
            zzc(1, 4);
        }

        public final void zzb(zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException {
            zzaz(com_google_android_gms_internal_measurement_zzxe.zzwe());
            com_google_android_gms_internal_measurement_zzxe.zzb(this);
        }

        final void zza(zzxe com_google_android_gms_internal_measurement_zzxe, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException {
            zzug com_google_android_gms_internal_measurement_zzug = (zzug) com_google_android_gms_internal_measurement_zzxe;
            int zzue = com_google_android_gms_internal_measurement_zzug.zzue();
            if (zzue == -1) {
                zzue = com_google_android_gms_internal_measurement_zzxu.zzai(com_google_android_gms_internal_measurement_zzug);
                com_google_android_gms_internal_measurement_zzug.zzah(zzue);
            }
            zzaz(zzue);
            com_google_android_gms_internal_measurement_zzxu.zza(com_google_android_gms_internal_measurement_zzxe, this.zzbvr);
        }

        public final void zzc(byte b) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = b;
            } catch (byte b2) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), b2);
            }
        }

        public final void zzay(int i) throws IOException {
            if (i >= 0) {
                zzaz(i);
            } else {
                zzay((long) i);
            }
        }

        public final void zzaz(int i) throws IOException {
            byte[] bArr;
            int i2;
            if (!zzve.zzbvq || zzvq() < 10) {
                while ((i & -128) != 0) {
                    bArr = this.buffer;
                    i2 = this.position;
                    this.position = i2 + 1;
                    bArr[i2] = (byte) ((i & 127) | 128);
                    i >>>= 7;
                }
                try {
                    bArr = this.buffer;
                    i2 = this.position;
                    this.position = i2 + 1;
                    bArr[i2] = (byte) i;
                    return;
                } catch (int i3) {
                    throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), i3);
                }
            }
            while ((i3 & -128) != 0) {
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                zzys.zza(bArr, (long) i2, (byte) ((i3 & 127) | 128));
                i3 >>>= 7;
            }
            bArr = this.buffer;
            i2 = this.position;
            this.position = i2 + 1;
            zzys.zza(bArr, (long) i2, (byte) i3);
        }

        public final void zzbb(int i) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) i;
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) (i >> 8);
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) (i >> 16);
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = i >> 24;
            } catch (int i3) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), i3);
            }
        }

        public final void zzay(long j) throws IOException {
            byte[] bArr;
            if (!zzve.zzbvq || zzvq() < 10) {
                while ((j & -128) != 0) {
                    bArr = this.buffer;
                    int i = this.position;
                    this.position = i + 1;
                    bArr[i] = (byte) ((((int) j) & 127) | 128);
                    j >>>= 7;
                }
                try {
                    bArr = this.buffer;
                    int i2 = this.position;
                    this.position = i2 + 1;
                    bArr[i2] = (byte) ((int) j);
                    return;
                } catch (long j2) {
                    throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), j2);
                }
            }
            while ((j2 & -128) != 0) {
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                zzys.zza(bArr, (long) i, (byte) ((((int) j2) & 127) | 128));
                j2 >>>= 7;
            }
            bArr = this.buffer;
            i2 = this.position;
            this.position = i2 + 1;
            zzys.zza(bArr, (long) i2, (byte) ((int) j2));
        }

        public final void zzba(long j) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) j);
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 8));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 16));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 24));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 32));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 40));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 48));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 56));
            } catch (long j2) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), j2);
            }
        }

        public final void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } catch (byte[] bArr2) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i2)}), bArr2);
            }
        }

        public final void zza(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        public final void zzgd(String str) throws IOException {
            int i = this.position;
            try {
                int zzbe = zzve.zzbe(str.length() * 3);
                int zzbe2 = zzve.zzbe(str.length());
                if (zzbe2 == zzbe) {
                    this.position = i + zzbe2;
                    zzbe = zzyu.zza(str, this.buffer, this.position, zzvq());
                    this.position = i;
                    zzaz((zzbe - i) - zzbe2);
                    this.position = zzbe;
                    return;
                }
                zzaz(zzyu.zza(str));
                this.position = zzyu.zza(str, this.buffer, this.position, zzvq());
            } catch (zzyy e) {
                this.position = i;
                zza(str, e);
            } catch (Throwable e2) {
                throw new zzc(e2);
            }
        }

        public final int zzvq() {
            return this.limit - this.position;
        }

        public final int zzvs() {
            return this.position - this.offset;
        }
    }

    static final class zzd extends zzve {
        private final int zzbvt;
        private final ByteBuffer zzbvu;
        private final ByteBuffer zzbvv;

        zzd(ByteBuffer byteBuffer) {
            super();
            this.zzbvu = byteBuffer;
            this.zzbvv = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.zzbvt = byteBuffer.position();
        }

        public final void zzc(int i, int i2) throws IOException {
            zzaz((i << 3) | i2);
        }

        public final void zzd(int i, int i2) throws IOException {
            zzc(i, 0);
            zzay(i2);
        }

        public final void zze(int i, int i2) throws IOException {
            zzc(i, 0);
            zzaz(i2);
        }

        public final void zzg(int i, int i2) throws IOException {
            zzc(i, 5);
            zzbb(i2);
        }

        public final void zza(int i, long j) throws IOException {
            zzc(i, 0);
            zzay(j);
        }

        public final void zzc(int i, long j) throws IOException {
            zzc(i, 1);
            zzba(j);
        }

        public final void zzb(int i, boolean z) throws IOException {
            zzc(i, 0);
            zzc((byte) z);
        }

        public final void zzb(int i, String str) throws IOException {
            zzc(i, 2);
            zzgd(str);
        }

        public final void zza(int i, zzun com_google_android_gms_internal_measurement_zzun) throws IOException {
            zzc(i, 2);
            zza(com_google_android_gms_internal_measurement_zzun);
        }

        public final void zza(int i, zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException {
            zzc(i, 2);
            zzb(com_google_android_gms_internal_measurement_zzxe);
        }

        final void zza(int i, zzxe com_google_android_gms_internal_measurement_zzxe, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException {
            zzc(i, 2);
            zza(com_google_android_gms_internal_measurement_zzxe, com_google_android_gms_internal_measurement_zzxu);
        }

        public final void zzb(int i, zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException {
            zzc(1, 3);
            zze(2, i);
            zza(3, com_google_android_gms_internal_measurement_zzxe);
            zzc(1, 4);
        }

        public final void zzb(int i, zzun com_google_android_gms_internal_measurement_zzun) throws IOException {
            zzc(1, 3);
            zze(2, i);
            zza(3, com_google_android_gms_internal_measurement_zzun);
            zzc(1, 4);
        }

        public final void zzb(zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException {
            zzaz(com_google_android_gms_internal_measurement_zzxe.zzwe());
            com_google_android_gms_internal_measurement_zzxe.zzb(this);
        }

        final void zza(zzxe com_google_android_gms_internal_measurement_zzxe, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException {
            zzug com_google_android_gms_internal_measurement_zzug = (zzug) com_google_android_gms_internal_measurement_zzxe;
            int zzue = com_google_android_gms_internal_measurement_zzug.zzue();
            if (zzue == -1) {
                zzue = com_google_android_gms_internal_measurement_zzxu.zzai(com_google_android_gms_internal_measurement_zzug);
                com_google_android_gms_internal_measurement_zzug.zzah(zzue);
            }
            zzaz(zzue);
            com_google_android_gms_internal_measurement_zzxu.zza(com_google_android_gms_internal_measurement_zzxe, this.zzbvr);
        }

        public final void zzc(byte b) throws IOException {
            try {
                this.zzbvv.put(b);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void zza(zzun com_google_android_gms_internal_measurement_zzun) throws IOException {
            zzaz(com_google_android_gms_internal_measurement_zzun.size());
            com_google_android_gms_internal_measurement_zzun.zza((zzum) this);
        }

        public final void zze(byte[] bArr, int i, int i2) throws IOException {
            zzaz(i2);
            write(bArr, 0, i2);
        }

        public final void zzay(int i) throws IOException {
            if (i >= 0) {
                zzaz(i);
            } else {
                zzay((long) i);
            }
        }

        public final void zzaz(int i) throws IOException {
            while ((i & -128) != 0) {
                this.zzbvv.put((byte) ((i & 127) | 128));
                i >>>= 7;
            }
            try {
                this.zzbvv.put((byte) i);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void zzbb(int i) throws IOException {
            try {
                this.zzbvv.putInt(i);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void zzay(long j) throws IOException {
            while ((-128 & j) != 0) {
                this.zzbvv.put((byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            try {
                this.zzbvv.put((byte) ((int) j));
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void zzba(long j) throws IOException {
            try {
                this.zzbvv.putLong(j);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }

        public final void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                this.zzbvv.put(bArr, i, i2);
            } catch (Throwable e) {
                throw new zzc(e);
            } catch (Throwable e2) {
                throw new zzc(e2);
            }
        }

        public final void zza(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        public final void zzgd(String str) throws IOException {
            int position = this.zzbvv.position();
            try {
                int zzbe = zzve.zzbe(str.length() * 3);
                int zzbe2 = zzve.zzbe(str.length());
                if (zzbe2 == zzbe) {
                    zzbe = this.zzbvv.position() + zzbe2;
                    this.zzbvv.position(zzbe);
                    zzgf(str);
                    zzbe2 = this.zzbvv.position();
                    this.zzbvv.position(position);
                    zzaz(zzbe2 - zzbe);
                    this.zzbvv.position(zzbe2);
                    return;
                }
                zzaz(zzyu.zza(str));
                zzgf(str);
            } catch (zzyy e) {
                this.zzbvv.position(position);
                zza(str, e);
            } catch (Throwable e2) {
                throw new zzc(e2);
            }
        }

        public final void flush() {
            this.zzbvu.position(this.zzbvv.position());
        }

        public final int zzvq() {
            return this.zzbvv.remaining();
        }

        private final void zzgf(String str) throws IOException {
            try {
                zzyu.zza(str, this.zzbvv);
            } catch (Throwable e) {
                throw new zzc(e);
            }
        }
    }

    static final class zze extends zzve {
        private final ByteBuffer zzbvu;
        private final ByteBuffer zzbvv;
        private final long zzbvw;
        private final long zzbvx;
        private final long zzbvy;
        private final long zzbvz = (this.zzbvy - 10);
        private long zzbwa = this.zzbvx;

        zze(ByteBuffer byteBuffer) {
            super();
            this.zzbvu = byteBuffer;
            this.zzbvv = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.zzbvw = zzys.zzb(byteBuffer);
            this.zzbvx = this.zzbvw + ((long) byteBuffer.position());
            this.zzbvy = this.zzbvw + ((long) byteBuffer.limit());
        }

        public final void zzc(int i, int i2) throws IOException {
            zzaz((i << 3) | i2);
        }

        public final void zzd(int i, int i2) throws IOException {
            zzc(i, 0);
            zzay(i2);
        }

        public final void zze(int i, int i2) throws IOException {
            zzc(i, 0);
            zzaz(i2);
        }

        public final void zzg(int i, int i2) throws IOException {
            zzc(i, 5);
            zzbb(i2);
        }

        public final void zza(int i, long j) throws IOException {
            zzc(i, 0);
            zzay(j);
        }

        public final void zzc(int i, long j) throws IOException {
            zzc(i, 1);
            zzba(j);
        }

        public final void zzb(int i, boolean z) throws IOException {
            zzc(i, 0);
            zzc((byte) z);
        }

        public final void zzb(int i, String str) throws IOException {
            zzc(i, 2);
            zzgd(str);
        }

        public final void zza(int i, zzun com_google_android_gms_internal_measurement_zzun) throws IOException {
            zzc(i, 2);
            zza(com_google_android_gms_internal_measurement_zzun);
        }

        public final void zza(int i, zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException {
            zzc(i, 2);
            zzb(com_google_android_gms_internal_measurement_zzxe);
        }

        final void zza(int i, zzxe com_google_android_gms_internal_measurement_zzxe, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException {
            zzc(i, 2);
            zza(com_google_android_gms_internal_measurement_zzxe, com_google_android_gms_internal_measurement_zzxu);
        }

        public final void zzb(int i, zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException {
            zzc(1, 3);
            zze(2, i);
            zza(3, com_google_android_gms_internal_measurement_zzxe);
            zzc(1, 4);
        }

        public final void zzb(int i, zzun com_google_android_gms_internal_measurement_zzun) throws IOException {
            zzc(1, 3);
            zze(2, i);
            zza(3, com_google_android_gms_internal_measurement_zzun);
            zzc(1, 4);
        }

        public final void zzb(zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException {
            zzaz(com_google_android_gms_internal_measurement_zzxe.zzwe());
            com_google_android_gms_internal_measurement_zzxe.zzb(this);
        }

        final void zza(zzxe com_google_android_gms_internal_measurement_zzxe, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException {
            zzug com_google_android_gms_internal_measurement_zzug = (zzug) com_google_android_gms_internal_measurement_zzxe;
            int zzue = com_google_android_gms_internal_measurement_zzug.zzue();
            if (zzue == -1) {
                zzue = com_google_android_gms_internal_measurement_zzxu.zzai(com_google_android_gms_internal_measurement_zzug);
                com_google_android_gms_internal_measurement_zzug.zzah(zzue);
            }
            zzaz(zzue);
            com_google_android_gms_internal_measurement_zzxu.zza(com_google_android_gms_internal_measurement_zzxe, this.zzbvr);
        }

        public final void zzc(byte b) throws IOException {
            if (this.zzbwa < this.zzbvy) {
                long j = this.zzbwa;
                this.zzbwa = 1 + j;
                zzys.zza(j, b);
                return;
            }
            throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.zzbwa), Long.valueOf(this.zzbvy), Integer.valueOf(1)}));
        }

        public final void zza(zzun com_google_android_gms_internal_measurement_zzun) throws IOException {
            zzaz(com_google_android_gms_internal_measurement_zzun.size());
            com_google_android_gms_internal_measurement_zzun.zza((zzum) this);
        }

        public final void zze(byte[] bArr, int i, int i2) throws IOException {
            zzaz(i2);
            write(bArr, 0, i2);
        }

        public final void zzay(int i) throws IOException {
            if (i >= 0) {
                zzaz(i);
            } else {
                zzay((long) i);
            }
        }

        public final void zzaz(int i) throws IOException {
            long j;
            if (this.zzbwa <= this.zzbvz) {
                while ((i & -128) != 0) {
                    j = this.zzbwa;
                    this.zzbwa = j + 1;
                    zzys.zza(j, (byte) ((i & 127) | 128));
                    i >>>= 7;
                }
                j = this.zzbwa;
                this.zzbwa = 1 + j;
                zzys.zza(j, (byte) i);
                return;
            }
            while (this.zzbwa < this.zzbvy) {
                if ((i & -128) == 0) {
                    j = this.zzbwa;
                    this.zzbwa = 1 + j;
                    zzys.zza(j, (byte) i);
                    return;
                }
                j = this.zzbwa;
                this.zzbwa = j + 1;
                zzys.zza(j, (byte) ((i & 127) | 128));
                i >>>= 7;
            }
            throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.zzbwa), Long.valueOf(this.zzbvy), Integer.valueOf(1)}));
        }

        public final void zzbb(int i) throws IOException {
            this.zzbvv.putInt((int) (this.zzbwa - this.zzbvw), i);
            this.zzbwa += 4;
        }

        public final void zzay(long j) throws IOException {
            long j2;
            if (this.zzbwa <= this.zzbvz) {
                while ((j & -128) != 0) {
                    j2 = this.zzbwa;
                    this.zzbwa = j2 + 1;
                    zzys.zza(j2, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
                j2 = this.zzbwa;
                this.zzbwa = 1 + j2;
                zzys.zza(j2, (byte) ((int) j));
                return;
            }
            while (this.zzbwa < this.zzbvy) {
                if ((j & -128) == 0) {
                    j2 = this.zzbwa;
                    this.zzbwa = 1 + j2;
                    zzys.zza(j2, (byte) ((int) j));
                    return;
                }
                j2 = this.zzbwa;
                this.zzbwa = j2 + 1;
                zzys.zza(j2, (byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.zzbwa), Long.valueOf(this.zzbvy), Integer.valueOf(1)}));
        }

        public final void zzba(long j) throws IOException {
            this.zzbvv.putLong((int) (this.zzbwa - this.zzbvw), j);
            this.zzbwa += 8;
        }

        public final void write(byte[] bArr, int i, int i2) throws IOException {
            if (bArr != null && i >= 0 && i2 >= 0 && bArr.length - i2 >= i) {
                long j = (long) i2;
                if (this.zzbvy - j >= this.zzbwa) {
                    zzys.zza(bArr, (long) i, this.zzbwa, j);
                    this.zzbwa += j;
                    return;
                }
            }
            if (bArr == null) {
                throw new NullPointerException("value");
            }
            throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.zzbwa), Long.valueOf(this.zzbvy), Integer.valueOf(i2)}));
        }

        public final void zza(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        public final void zzgd(String str) throws IOException {
            long j = this.zzbwa;
            try {
                int zzbe = zzve.zzbe(str.length() * 3);
                int zzbe2 = zzve.zzbe(str.length());
                if (zzbe2 == zzbe) {
                    zzbe = ((int) (this.zzbwa - this.zzbvw)) + zzbe2;
                    this.zzbvv.position(zzbe);
                    zzyu.zza(str, this.zzbvv);
                    zzbe2 = this.zzbvv.position() - zzbe;
                    zzaz(zzbe2);
                    this.zzbwa += (long) zzbe2;
                    return;
                }
                zzbe = zzyu.zza(str);
                zzaz(zzbe);
                zzbh(this.zzbwa);
                zzyu.zza(str, this.zzbvv);
                this.zzbwa += (long) zzbe;
            } catch (zzyy e) {
                this.zzbwa = j;
                zzbh(this.zzbwa);
                zza(str, e);
            } catch (Throwable e2) {
                throw new zzc(e2);
            } catch (Throwable e22) {
                throw new zzc(e22);
            }
        }

        public final void flush() {
            this.zzbvu.position((int) (this.zzbwa - this.zzbvw));
        }

        public final int zzvq() {
            return (int) (this.zzbvy - this.zzbwa);
        }

        private final void zzbh(long j) {
            this.zzbvv.position((int) (j - this.zzbvw));
        }
    }

    static final class zzb extends zza {
        private final ByteBuffer zzbvs;
        private int zzbvt;

        zzb(ByteBuffer byteBuffer) {
            super(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            this.zzbvs = byteBuffer;
            this.zzbvt = byteBuffer.position();
        }

        public final void flush() {
            this.zzbvs.position(this.zzbvt + zzvs());
        }
    }

    public static int zzb(float f) {
        return 4;
    }

    public static int zzbc(long j) {
        if ((-128 & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        int i;
        if ((-34359738368L & j) != 0) {
            i = 6;
            j >>>= 28;
        } else {
            i = 2;
        }
        if ((-2097152 & j) != 0) {
            i += 2;
            j >>>= 14;
        }
        if ((j & -16384) != 0) {
            i++;
        }
        return i;
    }

    public static int zzbe(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (i & -268435456) == 0 ? 4 : 5;
    }

    public static int zzbe(long j) {
        return 8;
    }

    public static int zzbf(long j) {
        return 8;
    }

    public static int zzbg(int i) {
        return 4;
    }

    private static long zzbg(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public static int zzbh(int i) {
        return 4;
    }

    private static int zzbj(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static int zzc(double d) {
        return 8;
    }

    public static zzve zzj(byte[] bArr) {
        return new zza(bArr, 0, bArr.length);
    }

    public static int zzt(boolean z) {
        return 1;
    }

    public abstract void flush() throws IOException;

    public abstract void write(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zza(int i, long j) throws IOException;

    public abstract void zza(int i, zzun com_google_android_gms_internal_measurement_zzun) throws IOException;

    public abstract void zza(int i, zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException;

    abstract void zza(int i, zzxe com_google_android_gms_internal_measurement_zzxe, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException;

    public abstract void zza(zzun com_google_android_gms_internal_measurement_zzun) throws IOException;

    abstract void zza(zzxe com_google_android_gms_internal_measurement_zzxe, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException;

    public abstract void zzay(int i) throws IOException;

    public abstract void zzay(long j) throws IOException;

    public abstract void zzaz(int i) throws IOException;

    public abstract void zzb(int i, zzun com_google_android_gms_internal_measurement_zzun) throws IOException;

    public abstract void zzb(int i, zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException;

    public abstract void zzb(int i, String str) throws IOException;

    public abstract void zzb(int i, boolean z) throws IOException;

    public abstract void zzb(zzxe com_google_android_gms_internal_measurement_zzxe) throws IOException;

    public abstract void zzba(long j) throws IOException;

    public abstract void zzbb(int i) throws IOException;

    public abstract void zzc(byte b) throws IOException;

    public abstract void zzc(int i, int i2) throws IOException;

    public abstract void zzc(int i, long j) throws IOException;

    public abstract void zzd(int i, int i2) throws IOException;

    public abstract void zze(int i, int i2) throws IOException;

    abstract void zze(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zzg(int i, int i2) throws IOException;

    public abstract void zzgd(String str) throws IOException;

    public abstract int zzvq();

    public static zzve zza(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return new zzb(byteBuffer);
        }
        if (!byteBuffer.isDirect() || byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("ByteBuffer is read-only");
        } else if (zzys.zzyw()) {
            return new zze(byteBuffer);
        } else {
            return new zzd(byteBuffer);
        }
    }

    private zzve() {
    }

    public final void zzf(int i, int i2) throws IOException {
        zze(i, zzbj(i2));
    }

    public final void zzb(int i, long j) throws IOException {
        zza(i, zzbg(j));
    }

    public final void zza(int i, float f) throws IOException {
        zzg(i, Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) throws IOException {
        zzc(i, Double.doubleToRawLongBits(d));
    }

    public final void zzba(int i) throws IOException {
        zzaz(zzbj(i));
    }

    public final void zzaz(long j) throws IOException {
        zzay(zzbg(j));
    }

    public final void zza(float f) throws IOException {
        zzbb(Float.floatToRawIntBits(f));
    }

    public final void zzb(double d) throws IOException {
        zzba(Double.doubleToRawLongBits(d));
    }

    public final void zzs(boolean z) throws IOException {
        zzc((byte) z);
    }

    public static int zzh(int i, int i2) {
        return zzbc(i) + zzbd(i2);
    }

    public static int zzi(int i, int i2) {
        return zzbc(i) + zzbe(i2);
    }

    public static int zzj(int i, int i2) {
        return zzbc(i) + zzbe(zzbj(i2));
    }

    public static int zzk(int i, int i2) {
        return zzbc(i) + 4;
    }

    public static int zzl(int i, int i2) {
        return zzbc(i) + 4;
    }

    public static int zzd(int i, long j) {
        return zzbc(i) + zzbc(j);
    }

    public static int zze(int i, long j) {
        return zzbc(i) + zzbc(j);
    }

    public static int zzf(int i, long j) {
        return zzbc(i) + zzbc(zzbg(j));
    }

    public static int zzg(int i, long j) {
        return zzbc(i) + 8;
    }

    public static int zzh(int i, long j) {
        return zzbc(i) + 8;
    }

    public static int zzb(int i, float f) {
        return zzbc(i) + 4;
    }

    public static int zzb(int i, double d) {
        return zzbc(i) + 8;
    }

    public static int zzc(int i, boolean z) {
        return zzbc(i) + 1;
    }

    public static int zzm(int i, int i2) {
        return zzbc(i) + zzbd(i2);
    }

    public static int zzc(int i, String str) {
        return zzbc(i) + zzge(str);
    }

    public static int zzc(int i, zzun com_google_android_gms_internal_measurement_zzun) {
        i = zzbc(i);
        int size = com_google_android_gms_internal_measurement_zzun.size();
        return i + (zzbe(size) + size);
    }

    public static int zza(int i, zzwl com_google_android_gms_internal_measurement_zzwl) {
        i = zzbc(i);
        int zzwe = com_google_android_gms_internal_measurement_zzwl.zzwe();
        return i + (zzbe(zzwe) + zzwe);
    }

    public static int zzc(int i, zzxe com_google_android_gms_internal_measurement_zzxe) {
        return zzbc(i) + zzc(com_google_android_gms_internal_measurement_zzxe);
    }

    static int zzb(int i, zzxe com_google_android_gms_internal_measurement_zzxe, zzxu com_google_android_gms_internal_measurement_zzxu) {
        return zzbc(i) + zzb(com_google_android_gms_internal_measurement_zzxe, com_google_android_gms_internal_measurement_zzxu);
    }

    public static int zzd(int i, zzxe com_google_android_gms_internal_measurement_zzxe) {
        return ((zzbc(1) << 1) + zzi(2, i)) + zzc(3, com_google_android_gms_internal_measurement_zzxe);
    }

    public static int zzd(int i, zzun com_google_android_gms_internal_measurement_zzun) {
        return ((zzbc(1) << 1) + zzi(2, i)) + zzc(3, com_google_android_gms_internal_measurement_zzun);
    }

    public static int zzb(int i, zzwl com_google_android_gms_internal_measurement_zzwl) {
        return ((zzbc(1) << 1) + zzi(2, i)) + zza(3, com_google_android_gms_internal_measurement_zzwl);
    }

    public static int zzbc(int i) {
        return zzbe(i << 3);
    }

    public static int zzbd(int i) {
        return i >= 0 ? zzbe(i) : 10;
    }

    public static int zzbf(int i) {
        return zzbe(zzbj(i));
    }

    public static int zzbb(long j) {
        return zzbc(j);
    }

    public static int zzbd(long j) {
        return zzbc(zzbg(j));
    }

    public static int zzbi(int i) {
        return zzbd(i);
    }

    public static int zzge(java.lang.String r1) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = com.google.android.gms.internal.measurement.zzyu.zza(r1);	 Catch:{ zzyy -> 0x0005 }
        goto L_0x000c;
    L_0x0005:
        r0 = com.google.android.gms.internal.measurement.zzvz.UTF_8;
        r1 = r1.getBytes(r0);
        r0 = r1.length;
    L_0x000c:
        r1 = zzbe(r0);
        r1 = r1 + r0;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzve.zzge(java.lang.String):int");
    }

    public static int zza(zzwl com_google_android_gms_internal_measurement_zzwl) {
        int zzwe = com_google_android_gms_internal_measurement_zzwl.zzwe();
        return zzbe(zzwe) + zzwe;
    }

    public static int zzb(zzun com_google_android_gms_internal_measurement_zzun) {
        int size = com_google_android_gms_internal_measurement_zzun.size();
        return zzbe(size) + size;
    }

    public static int zzk(byte[] bArr) {
        int length = bArr.length;
        return zzbe(length) + length;
    }

    public static int zzc(zzxe com_google_android_gms_internal_measurement_zzxe) {
        int zzwe = com_google_android_gms_internal_measurement_zzxe.zzwe();
        return zzbe(zzwe) + zzwe;
    }

    static int zzb(zzxe com_google_android_gms_internal_measurement_zzxe, zzxu com_google_android_gms_internal_measurement_zzxu) {
        zzug com_google_android_gms_internal_measurement_zzug = (zzug) com_google_android_gms_internal_measurement_zzxe;
        int zzue = com_google_android_gms_internal_measurement_zzug.zzue();
        if (zzue == -1) {
            zzue = com_google_android_gms_internal_measurement_zzxu.zzai(com_google_android_gms_internal_measurement_zzug);
            com_google_android_gms_internal_measurement_zzug.zzah(zzue);
        }
        return zzbe(zzue) + zzue;
    }

    final void zza(String str, zzyy com_google_android_gms_internal_measurement_zzyy) throws IOException {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", com_google_android_gms_internal_measurement_zzyy);
        str = str.getBytes(zzvz.UTF_8);
        try {
            zzaz(str.length);
            zza(str, null, str.length);
        } catch (Throwable e) {
            throw new zzc(e);
        } catch (String str2) {
            throw str2;
        }
    }

    @Deprecated
    static int zzc(int i, zzxe com_google_android_gms_internal_measurement_zzxe, zzxu com_google_android_gms_internal_measurement_zzxu) {
        i = zzbc(i) << 1;
        zzug com_google_android_gms_internal_measurement_zzug = (zzug) com_google_android_gms_internal_measurement_zzxe;
        int zzue = com_google_android_gms_internal_measurement_zzug.zzue();
        if (zzue == -1) {
            zzue = com_google_android_gms_internal_measurement_zzxu.zzai(com_google_android_gms_internal_measurement_zzug);
            com_google_android_gms_internal_measurement_zzug.zzah(zzue);
        }
        return i + zzue;
    }

    @Deprecated
    public static int zzd(zzxe com_google_android_gms_internal_measurement_zzxe) {
        return com_google_android_gms_internal_measurement_zzxe.zzwe();
    }

    @Deprecated
    public static int zzbk(int i) {
        return zzbe(i);
    }
}
