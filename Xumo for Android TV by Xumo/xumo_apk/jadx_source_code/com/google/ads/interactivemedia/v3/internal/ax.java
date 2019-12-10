package com.google.ads.interactivemedia.v3.internal;

import android.annotation.TargetApi;
import android.media.MediaCodec.CryptoInfo;

/* compiled from: IMASDK */
public final class ax {
    public byte[] a;
    public byte[] b;
    public int c;
    public int[] d;
    public int[] e;
    public int f;
    private final CryptoInfo g;

    public ax() {
        this.g = ft.a >= 16 ? b() : null;
    }

    public void a(int i, int[] iArr, int[] iArr2, byte[] bArr, byte[] bArr2, int i2) {
        this.f = i;
        this.d = iArr;
        this.e = iArr2;
        this.b = bArr;
        this.a = bArr2;
        this.c = i2;
        if (ft.a >= 16) {
            c();
        }
    }

    @TargetApi(16)
    public CryptoInfo a() {
        return this.g;
    }

    @TargetApi(16)
    private CryptoInfo b() {
        return new CryptoInfo();
    }

    @TargetApi(16)
    private void c() {
        this.g.set(this.f, this.d, this.e, this.b, this.a, this.c);
    }
}
