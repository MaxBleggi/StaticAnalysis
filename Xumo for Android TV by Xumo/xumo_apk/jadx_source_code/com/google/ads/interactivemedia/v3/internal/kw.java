package com.google.ads.interactivemedia.v3.internal;

import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: IMASDK */
public final class kw {
    public static boolean a(String str) {
        return ku.a(str);
    }

    public static String a(String str, Object... objArr) {
        str = String.valueOf(str);
        int i = 0;
        if (objArr == null) {
            objArr = new Object[]{"(Object[])null"};
        } else {
            for (int i2 = 0; i2 < objArr.length; i2++) {
                objArr[i2] = a(objArr[i2]);
            }
        }
        StringBuilder stringBuilder = new StringBuilder(str.length() + (objArr.length * 16));
        int i3 = 0;
        while (i < objArr.length) {
            int indexOf = str.indexOf("%s", i3);
            if (indexOf == -1) {
                break;
            }
            stringBuilder.append(str, i3, indexOf);
            i3 = i + 1;
            stringBuilder.append(objArr[i]);
            int i4 = i3;
            i3 = indexOf + 2;
            i = i4;
        }
        stringBuilder.append(str, i3, str.length());
        if (i < objArr.length) {
            stringBuilder.append(" [");
            str = i + 1;
            stringBuilder.append(objArr[i]);
            while (str < objArr.length) {
                stringBuilder.append(", ");
                i = str + 1;
                stringBuilder.append(objArr[str]);
                str = i;
            }
            stringBuilder.append(']');
        }
        return stringBuilder.toString();
    }

    private static String a(Object obj) {
        try {
            return String.valueOf(obj);
        } catch (Throwable e) {
            String name = obj.getClass().getName();
            obj = Integer.toHexString(System.identityHashCode(obj));
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(name).length() + 1) + String.valueOf(obj).length());
            stringBuilder.append(name);
            stringBuilder.append('@');
            stringBuilder.append(obj);
            obj = stringBuilder.toString();
            Logger logger = Logger.getLogger("com.google.common.base.Strings");
            Level level = Level.WARNING;
            String str = "com.google.common.base.Strings";
            String str2 = "lenientToString";
            String str3 = "Exception during lenientFormat for ";
            String valueOf = String.valueOf(obj);
            logger.logp(level, str, str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3), e);
            String name2 = e.getClass().getName();
            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(obj).length() + 9) + String.valueOf(name2).length());
            stringBuilder2.append("<");
            stringBuilder2.append(obj);
            stringBuilder2.append(" threw ");
            stringBuilder2.append(name2);
            stringBuilder2.append(">");
            return stringBuilder2.toString();
        }
    }
}
