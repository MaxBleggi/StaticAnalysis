package com.google.ads.interactivemedia.v3.internal;

import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

/* compiled from: IMASDK */
public final class lv {
    private final ByteBuffer a;

    /* compiled from: IMASDK */
    public static class a extends IOException {
        a(int i, int i2) {
            StringBuilder stringBuilder = new StringBuilder(108);
            stringBuilder.append("CodedOutputStream was writing to a flat byte array and ran out of space (pos ");
            stringBuilder.append(i);
            stringBuilder.append(" limit ");
            stringBuilder.append(i2);
            stringBuilder.append(").");
            super(stringBuilder.toString());
        }
    }

    private lv(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    public static int c(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (i & -268435456) == 0 ? 4 : 5;
    }

    private lv(ByteBuffer byteBuffer) {
        this.a = byteBuffer;
        this.a.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static lv a(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    public static lv a(byte[] bArr, int i, int i2) {
        return new lv(bArr, i, i2);
    }

    public void a(int i, long j) throws IOException {
        a(i, 0);
        a(j);
    }

    public void a(int i, String str) throws IOException {
        a(i, 2);
        a(str);
    }

    public void a(long j) throws IOException {
        b(j);
    }

    public void a(String str) throws IOException {
        try {
            int c = c(str.length());
            if (c == c(str.length() * 3)) {
                int position = this.a.position();
                if (this.a.remaining() >= c) {
                    this.a.position(position + c);
                    a((CharSequence) str, this.a);
                    str = this.a.position();
                    this.a.position(position);
                    b((str - position) - c);
                    this.a.position(str);
                    return;
                }
                throw new a(position + c, this.a.limit());
            }
            b(a((CharSequence) str));
            a((CharSequence) str, this.a);
        } catch (String str2) {
            a aVar = new a(this.a.position(), this.a.limit());
            aVar.initCause(str2);
            throw aVar;
        }
    }

    private static int a(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) < '') {
            i++;
        }
        int i2 = length;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt >= 'ࠀ') {
                i2 += a(charSequence, i);
                break;
            }
            i2 += (127 - charAt) >>> 31;
            i++;
        }
        if (i2 >= length) {
            return i2;
        }
        long j = ((long) i2) + 4294967296L;
        StringBuilder stringBuilder = new StringBuilder(54);
        stringBuilder.append("UTF-8 length does not fit in int: ");
        stringBuilder.append(j);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static int a(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 'ࠀ') {
                i2 += (127 - charAt) >>> 31;
            } else {
                i2 += 2;
                if ('?' <= charAt && charAt <= '?') {
                    if (Character.codePointAt(charSequence, i) >= 65536) {
                        i++;
                    } else {
                        StringBuilder stringBuilder = new StringBuilder(39);
                        stringBuilder.append("Unpaired surrogate at index ");
                        stringBuilder.append(i);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                }
            }
            i++;
        }
        return i2;
    }

    private static void a(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(a(charSequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
            } catch (CharSequence charSequence2) {
                byteBuffer = new BufferOverflowException();
                byteBuffer.initCause(charSequence2);
                throw byteBuffer;
            }
        } else {
            b(charSequence2, byteBuffer);
        }
    }

    private static void b(CharSequence charSequence, ByteBuffer byteBuffer) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < '') {
                byteBuffer.put((byte) charAt);
            } else if (charAt < 'ࠀ') {
                byteBuffer.put((byte) ((charAt >>> 6) | 960));
                byteBuffer.put((byte) ((charAt & 63) | 128));
            } else {
                if (charAt >= '?') {
                    if ('?' >= charAt) {
                        int i2 = i + 1;
                        if (i2 != charSequence.length()) {
                            char charAt2 = charSequence.charAt(i2);
                            if (Character.isSurrogatePair(charAt, charAt2)) {
                                i = Character.toCodePoint(charAt, charAt2);
                                byteBuffer.put((byte) ((i >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                                byteBuffer.put((byte) (((i >>> 12) & 63) | 128));
                                byteBuffer.put((byte) (((i >>> 6) & 63) | 128));
                                byteBuffer.put((byte) ((i & 63) | 128));
                                i = i2;
                            } else {
                                i = i2;
                            }
                        }
                        i--;
                        StringBuilder stringBuilder = new StringBuilder(39);
                        stringBuilder.append("Unpaired surrogate at index ");
                        stringBuilder.append(i);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                }
                byteBuffer.put((byte) ((charAt >>> 12) | 480));
                byteBuffer.put((byte) (((charAt >>> 6) & 63) | 128));
                byteBuffer.put((byte) ((charAt & 63) | 128));
            }
            i++;
        }
    }

    private static int a(CharSequence charSequence, byte[] bArr, int i, int i2) {
        int length = charSequence.length();
        i2 += i;
        int i3 = 0;
        while (i3 < length) {
            int i4 = i3 + i;
            if (i4 >= i2) {
                break;
            }
            char charAt = charSequence.charAt(i3);
            if (charAt >= '') {
                break;
            }
            bArr[i4] = (byte) charAt;
            i3++;
        }
        if (i3 == length) {
            return i + length;
        }
        i += i3;
        while (i3 < length) {
            int i5;
            char charAt2 = charSequence.charAt(i3);
            if (charAt2 < '' && i < i2) {
                i5 = i + 1;
                bArr[i] = (byte) charAt2;
            } else if (charAt2 < 'ࠀ' && i <= i2 - 2) {
                i5 = i + 1;
                bArr[i] = (byte) ((charAt2 >>> 6) | 960);
                i = i5 + 1;
                bArr[i5] = (byte) ((charAt2 & 63) | 128);
                i3++;
            } else if ((charAt2 < '?' || '?' < charAt2) && i <= i2 - 3) {
                i5 = i + 1;
                bArr[i] = (byte) ((charAt2 >>> 12) | 480);
                i = i5 + 1;
                bArr[i5] = (byte) (((charAt2 >>> 6) & 63) | 128);
                i5 = i + 1;
                bArr[i] = (byte) ((charAt2 & 63) | 128);
            } else if (i <= i2 - 4) {
                i5 = i3 + 1;
                if (i5 != charSequence.length()) {
                    char charAt3 = charSequence.charAt(i5);
                    if (Character.isSurrogatePair(charAt2, charAt3)) {
                        i3 = Character.toCodePoint(charAt2, charAt3);
                        i4 = i + 1;
                        bArr[i] = (byte) ((i3 >>> 18) | PsExtractor.VIDEO_STREAM_MASK);
                        i = i4 + 1;
                        bArr[i4] = (byte) (((i3 >>> 12) & 63) | 128);
                        i4 = i + 1;
                        bArr[i] = (byte) (((i3 >>> 6) & 63) | 128);
                        i = i4 + 1;
                        bArr[i4] = (byte) ((i3 & 63) | 128);
                        i3 = i5;
                        i3++;
                    } else {
                        i3 = i5;
                    }
                }
                i3--;
                i = new StringBuilder(39);
                i.append("Unpaired surrogate at index ");
                i.append(i3);
                throw new IllegalArgumentException(i.toString());
            } else {
                i2 = new StringBuilder(37);
                i2.append("Failed writing ");
                i2.append(charAt2);
                i2.append(" at index ");
                i2.append(i);
                throw new ArrayIndexOutOfBoundsException(i2.toString());
            }
            i = i5;
            i3++;
        }
        return i;
    }

    public int a() {
        return this.a.remaining();
    }

    public void a(byte b) throws IOException {
        if (this.a.hasRemaining()) {
            this.a.put(b);
            return;
        }
        throw new a(this.a.position(), this.a.limit());
    }

    public void a(int i) throws IOException {
        a((byte) i);
    }

    public void a(int i, int i2) throws IOException {
        b(lw.a(i, i2));
    }

    public void b(int i) throws IOException {
        while ((i & -128) != 0) {
            a((i & 127) | 128);
            i >>>= 7;
        }
        a(i);
    }

    public void b(long j) throws IOException {
        while ((-128 & j) != 0) {
            a((((int) j) & 127) | 128);
            j >>>= 7;
        }
        a((int) j);
    }
}
