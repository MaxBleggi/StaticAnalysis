package com.google.ads.interactivemedia.v3.internal;

import android.annotation.TargetApi;
import android.media.MediaCodecInfo.CodecCapabilities;

@TargetApi(16)
/* compiled from: IMASDK */
public final class ay {
    public final String a;
    public final CodecCapabilities b;
    public final boolean c;

    ay(String str, CodecCapabilities codecCapabilities) {
        this.a = str;
        this.b = codecCapabilities;
        this.c = a(codecCapabilities);
    }

    private static boolean a(CodecCapabilities codecCapabilities) {
        return (codecCapabilities == null || ft.a < 19 || b(codecCapabilities) == null) ? null : true;
    }

    @TargetApi(19)
    private static boolean b(CodecCapabilities codecCapabilities) {
        return codecCapabilities.isFeatureSupported("adaptive-playback");
    }
}
