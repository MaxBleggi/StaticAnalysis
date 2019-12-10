package com.google.ads.interactivemedia.v3.internal;

import android.util.Base64;

/* compiled from: IMASDK */
class jz implements kh {
    jz() {
    }

    public String a(byte[] bArr, boolean z) {
        return Base64.encodeToString(bArr, z ? true : true);
    }

    public byte[] a(String str, boolean z) throws IllegalArgumentException {
        return Base64.decode(str, z ? true : true);
    }
}
