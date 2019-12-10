package com.google.ads.interactivemedia.v3.internal;

import android.text.TextUtils;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* compiled from: IMASDK */
public interface ez extends fc {
    public static final fq<String> a = new fq<String>() {
        public boolean a(String str) {
            str = ft.b(str);
            return (TextUtils.isEmpty(str) || ((str.contains(MimeTypes.BASE_TYPE_TEXT) && !str.contains(MimeTypes.TEXT_VTT)) || str.contains("html") || str.contains("xml") != null)) ? null : true;
        }
    };

    /* compiled from: IMASDK */
    public static class a extends IOException {
        public final int a;
        public final eu b;

        public a(String str, eu euVar, int i) {
            super(str);
            this.b = euVar;
            this.a = i;
        }

        public a(IOException iOException, eu euVar, int i) {
            super(iOException);
            this.b = euVar;
            this.a = i;
        }

        public a(String str, IOException iOException, eu euVar, int i) {
            super(str, iOException);
            this.b = euVar;
            this.a = i;
        }
    }

    /* compiled from: IMASDK */
    public static final class b extends a {
        public final String c;

        public b(String str, eu euVar) {
            String str2 = "Invalid content type: ";
            String valueOf = String.valueOf(str);
            super(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), euVar, 1);
            this.c = str;
        }
    }

    /* compiled from: IMASDK */
    public static final class c extends a {
        public final int c;
        public final Map<String, List<String>> d;

        public c(int i, Map<String, List<String>> map, eu euVar) {
            StringBuilder stringBuilder = new StringBuilder(26);
            stringBuilder.append("Response code: ");
            stringBuilder.append(i);
            super(stringBuilder.toString(), euVar, 1);
            this.c = i;
            this.d = map;
        }
    }
}
