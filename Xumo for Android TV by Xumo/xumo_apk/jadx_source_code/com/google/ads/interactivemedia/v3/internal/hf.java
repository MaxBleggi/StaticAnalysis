package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.io.Writer;

/* compiled from: IMASDK */
public final class hf {

    /* compiled from: IMASDK */
    private static final class a extends Writer {
        private final Appendable a;
        private final a b = new a();

        /* compiled from: IMASDK */
        static class a implements CharSequence {
            char[] a;

            a() {
            }

            public int length() {
                return this.a.length;
            }

            public char charAt(int i) {
                return this.a[i];
            }

            public CharSequence subSequence(int i, int i2) {
                return new String(this.a, i, i2 - i);
            }
        }

        a(Appendable appendable) {
            this.a = appendable;
        }

        public void close() {
        }

        public void flush() {
        }

        public void write(char[] cArr, int i, int i2) throws IOException {
            this.b.a = cArr;
            this.a.append(this.b, i, i2 + i);
        }

        public void write(int i) throws IOException {
            this.a.append((char) i);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.ads.interactivemedia.v3.internal.gf a(com.google.ads.interactivemedia.v3.internal.hx r2) throws com.google.ads.interactivemedia.v3.internal.gj {
        /*
        r2.f();	 Catch:{ EOFException -> 0x0024, ia -> 0x001d, IOException -> 0x0016, NumberFormatException -> 0x000f }
        r0 = 0;
        r1 = com.google.ads.interactivemedia.v3.internal.hu.X;	 Catch:{ EOFException -> 0x000d, ia -> 0x001d, IOException -> 0x0016, NumberFormatException -> 0x000f }
        r2 = r1.read(r2);	 Catch:{ EOFException -> 0x000d, ia -> 0x001d, IOException -> 0x0016, NumberFormatException -> 0x000f }
        r2 = (com.google.ads.interactivemedia.v3.internal.gf) r2;	 Catch:{ EOFException -> 0x000d, ia -> 0x001d, IOException -> 0x0016, NumberFormatException -> 0x000f }
        return r2;
    L_0x000d:
        r2 = move-exception;
        goto L_0x0026;
    L_0x000f:
        r2 = move-exception;
        r0 = new com.google.ads.interactivemedia.v3.internal.gn;
        r0.<init>(r2);
        throw r0;
    L_0x0016:
        r2 = move-exception;
        r0 = new com.google.ads.interactivemedia.v3.internal.gg;
        r0.<init>(r2);
        throw r0;
    L_0x001d:
        r2 = move-exception;
        r0 = new com.google.ads.interactivemedia.v3.internal.gn;
        r0.<init>(r2);
        throw r0;
    L_0x0024:
        r2 = move-exception;
        r0 = 1;
    L_0x0026:
        if (r0 == 0) goto L_0x002b;
    L_0x0028:
        r2 = com.google.ads.interactivemedia.v3.internal.gh.a;
        return r2;
    L_0x002b:
        r0 = new com.google.ads.interactivemedia.v3.internal.gn;
        r0.<init>(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.hf.a(com.google.ads.interactivemedia.v3.internal.hx):com.google.ads.interactivemedia.v3.internal.gf");
    }

    public static void a(gf gfVar, hz hzVar) throws IOException {
        hu.X.write(hzVar, gfVar);
    }

    public static Writer a(Appendable appendable) {
        return appendable instanceof Writer ? (Writer) appendable : new a(appendable);
    }
}
