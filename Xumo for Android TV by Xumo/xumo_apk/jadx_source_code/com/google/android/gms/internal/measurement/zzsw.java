package com.google.android.gms.internal.measurement;

import android.net.Uri;

public final class zzsw {
    public static Uri zzfs(String str) {
        String str2 = "content://com.google.android.gms.phenotype/";
        str = String.valueOf(Uri.encode(str));
        return Uri.parse(str.length() != 0 ? str2.concat(str) : new String(str2));
    }
}
