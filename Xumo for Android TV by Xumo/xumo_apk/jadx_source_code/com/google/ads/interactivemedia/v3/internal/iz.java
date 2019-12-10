package com.google.ads.interactivemedia.v3.internal;

import android.net.Uri;

/* compiled from: IMASDK */
public class iz {
    public static final a a = a.PROD;
    public static final Uri b = Uri.parse("https://imasdk.googleapis.com/native/sdkloader/native_sdk_v3.html");
    public static final Uri c = Uri.parse("https://imasdk.googleapis.com/native/sdkloader/native_sdk_v3_debug.html");

    /* compiled from: IMASDK */
    public enum a {
        VERBOSE,
        ABRIDGED,
        LIFECYCLE,
        PROD;

        public static boolean a(a aVar) {
            return aVar.ordinal() >= iz.a.ordinal() ? true : null;
        }
    }
}
