package com.google.android.gms.internal.cast;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.core.internal.view.SupportMenu;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public final class zzcv {
    private static final Pattern zzxm = Pattern.compile("urn:x-cast:[-A-Za-z0-9_]+(\\.[-A-Za-z0-9_]+)*");

    public static <T> boolean zza(T t, T t2) {
        return (!(t == null && t2 == null) && (t == null || t2 == null || t.equals(t2) == null)) ? null : true;
    }

    public static void zzo(String str) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Namespace cannot be null or empty");
        } else if (str.length() > 128) {
            throw new IllegalArgumentException("Invalid namespace length");
        } else if (!str.startsWith("urn:x-cast:")) {
            throw new IllegalArgumentException("Namespace must begin with the prefix \"urn:x-cast:\"");
        } else if (str.length() == 11) {
            throw new IllegalArgumentException("Namespace must begin with the prefix \"urn:x-cast:\" and have non-empty suffix");
        }
    }

    public static String zzp(String str) {
        String valueOf = String.valueOf("urn:x-cast:");
        str = String.valueOf(str);
        return str.length() != 0 ? valueOf.concat(str) : new String(valueOf);
    }

    public static String zzq(String str) {
        if (zzxm.matcher(str).matches()) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder(str.length());
        int i = 0;
        while (i < str.length()) {
            Object obj;
            char charAt = str.charAt(i);
            if ((charAt < 'A' || charAt > 'Z') && ((charAt < 'a' || charAt > 'z') && ((charAt < '0' || charAt > '9') && charAt != '_'))) {
                if (charAt != '-') {
                    obj = null;
                    if (obj == null && charAt != '.') {
                        if (charAt == ':') {
                            stringBuilder.append(String.format("%%%04x", new Object[]{Integer.valueOf(charAt & SupportMenu.USER_MASK)}));
                            i++;
                        }
                    }
                    stringBuilder.append(charAt);
                    i++;
                }
            }
            obj = 1;
            if (charAt == ':') {
                stringBuilder.append(String.format("%%%04x", new Object[]{Integer.valueOf(charAt & SupportMenu.USER_MASK)}));
                i++;
            } else {
                stringBuilder.append(charAt);
                i++;
            }
        }
        return stringBuilder.toString();
    }

    public static String zza(Locale locale) {
        StringBuilder stringBuilder = new StringBuilder(20);
        stringBuilder.append(locale.getLanguage());
        Object country = locale.getCountry();
        if (!TextUtils.isEmpty(country)) {
            stringBuilder.append('-');
            stringBuilder.append(country);
        }
        locale = locale.getVariant();
        if (!TextUtils.isEmpty(locale)) {
            stringBuilder.append('-');
            stringBuilder.append(locale);
        }
        return stringBuilder.toString();
    }

    @NonNull
    public static int[] zza(@NonNull Collection<Integer> collection) {
        int[] iArr = new int[collection.size()];
        int i = 0;
        for (Integer intValue : collection) {
            int i2 = i + 1;
            iArr[i] = intValue.intValue();
            i = i2;
        }
        return iArr;
    }

    @NonNull
    public static List<Integer> zzg(@NonNull int[] iArr) {
        List<Integer> arrayList = new ArrayList();
        for (int valueOf : iArr) {
            arrayList.add(Integer.valueOf(valueOf));
        }
        return arrayList;
    }
}
