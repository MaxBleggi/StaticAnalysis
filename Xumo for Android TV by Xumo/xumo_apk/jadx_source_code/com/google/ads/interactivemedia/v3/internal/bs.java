package com.google.ads.interactivemedia.v3.internal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.Arrays;

@TargetApi(21)
/* compiled from: IMASDK */
public final class bs {
    public static final bs a = new bs(new int[]{2}, 2);
    private final int[] b;
    private final int c;

    public static bs a(Context context) {
        return a(context.registerReceiver(null, new IntentFilter("android.media.action.HDMI_AUDIO_PLUG")));
    }

    @SuppressLint({"InlinedApi"})
    static bs a(Intent intent) {
        if (intent != null) {
            if (intent.getIntExtra("android.media.extra.AUDIO_PLUG_STATE", 0) != 0) {
                return new bs(intent.getIntArrayExtra("android.media.extra.ENCODINGS"), intent.getIntExtra("android.media.extra.MAX_CHANNEL_COUNT", 0));
            }
        }
        return a;
    }

    bs(int[] iArr, int i) {
        if (iArr != null) {
            this.b = Arrays.copyOf(iArr, iArr.length);
            Arrays.sort(this.b);
        } else {
            this.b = new int[null];
        }
        this.c = i;
    }

    public boolean a(int i) {
        return Arrays.binarySearch(this.b, i) >= 0;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof bs)) {
            return false;
        }
        bs bsVar = (bs) obj;
        if (!Arrays.equals(this.b, bsVar.b) || this.c != bsVar.c) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return this.c + (Arrays.hashCode(this.b) * 31);
    }

    public String toString() {
        int i = this.c;
        String arrays = Arrays.toString(this.b);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(arrays).length() + 67);
        stringBuilder.append("AudioCapabilities[maxChannelCount=");
        stringBuilder.append(i);
        stringBuilder.append(", supportedEncodings=");
        stringBuilder.append(arrays);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
