package com.google.ads.interactivemedia.v3.internal;

import android.net.Uri;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: IMASDK */
public class jy {
    public static Map<String, String> a(Uri uri) {
        if (uri == null || uri.isOpaque()) {
            throw new UnsupportedOperationException("This isn't a hierarchical URI.");
        }
        uri = uri.getEncodedQuery();
        if (uri != null) {
            if (uri.length() != 0) {
                Map linkedHashMap = new LinkedHashMap();
                int i = 0;
                int indexOf = uri.indexOf(35);
                if (indexOf == -1) {
                    indexOf = uri.length();
                }
                do {
                    int indexOf2 = uri.indexOf(38, i);
                    if (indexOf2 == -1) {
                        indexOf2 = indexOf;
                    }
                    int indexOf3 = uri.indexOf(61, i);
                    if (indexOf3 > indexOf2 || indexOf3 == -1) {
                        indexOf3 = indexOf2;
                    }
                    String substring = uri.substring(i, indexOf3);
                    Object obj = "";
                    if (indexOf3 < indexOf2) {
                        obj = uri.substring(indexOf3 + 1, indexOf2);
                    }
                    linkedHashMap.put(substring, obj);
                    i = indexOf2 + 1;
                } while (i < indexOf);
                return Collections.unmodifiableMap(linkedHashMap);
            }
        }
        return Collections.emptyMap();
    }

    public static String a(Map<String, String> map) {
        if (map != null) {
            if (map.size() != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                int i = 0;
                for (Entry entry : map.entrySet()) {
                    String str = (String) entry.getKey();
                    String str2 = (String) entry.getValue();
                    stringBuilder.append(str);
                    stringBuilder.append("=");
                    stringBuilder.append(str2);
                    if (i < map.size() - 1) {
                        stringBuilder.append("&");
                    }
                    i++;
                }
                return stringBuilder.toString();
            }
        }
        return "";
    }
}
