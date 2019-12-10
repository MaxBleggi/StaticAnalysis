package com.google.android.gms.common.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@KeepForSdk
public class LibraryVersion {
    private static final GmsLogger zzel = new GmsLogger("LibraryVersion", "");
    private static LibraryVersion zzem = new LibraryVersion();
    private ConcurrentHashMap<String, String> zzen = new ConcurrentHashMap();

    @KeepForSdk
    public static LibraryVersion getInstance() {
        return zzem;
    }

    @VisibleForTesting
    protected LibraryVersion() {
    }

    @KeepForSdk
    public String getVersion(@NonNull String str) {
        Throwable th;
        GmsLogger gmsLogger;
        String valueOf;
        Preconditions.checkNotEmpty(str, "Please provide a valid libraryName");
        if (this.zzen.containsKey(str)) {
            return (String) this.zzen.get(str);
        }
        Properties properties = new Properties();
        String str2 = null;
        String str3;
        String str4;
        try {
            InputStream resourceAsStream = LibraryVersion.class.getResourceAsStream(String.format("/%s.properties", new Object[]{str}));
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
                String property = properties.getProperty("version", null);
                try {
                    StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 12) + String.valueOf(property).length());
                    stringBuilder.append(str);
                    stringBuilder.append(" version is ");
                    stringBuilder.append(property);
                    zzel.v("LibraryVersion", stringBuilder.toString());
                    str2 = property;
                } catch (Throwable e) {
                    Throwable th2 = e;
                    str2 = property;
                    th = th2;
                    gmsLogger = zzel;
                    str3 = "LibraryVersion";
                    str4 = "Failed to get app version for libraryName: ";
                    valueOf = String.valueOf(str);
                    gmsLogger.e(str3, valueOf.length() == 0 ? new String(str4) : str4.concat(valueOf), th);
                    if (str2 == null) {
                        str2 = "UNKNOWN";
                        zzel.d("LibraryVersion", ".properties file is dropped during release process. Failure to read app version isexpected druing Google internal testing where locally-built libraries are used");
                    }
                    this.zzen.put(str, str2);
                    return str2;
                }
            }
            GmsLogger gmsLogger2 = zzel;
            String str5 = "LibraryVersion";
            str3 = "Failed to get app version for libraryName: ";
            str4 = String.valueOf(str);
            gmsLogger2.e(str5, str4.length() != 0 ? str3.concat(str4) : new String(str3));
        } catch (IOException e2) {
            th = e2;
            gmsLogger = zzel;
            str3 = "LibraryVersion";
            str4 = "Failed to get app version for libraryName: ";
            valueOf = String.valueOf(str);
            if (valueOf.length() == 0) {
            }
            gmsLogger.e(str3, valueOf.length() == 0 ? new String(str4) : str4.concat(valueOf), th);
            if (str2 == null) {
                str2 = "UNKNOWN";
                zzel.d("LibraryVersion", ".properties file is dropped during release process. Failure to read app version isexpected druing Google internal testing where locally-built libraries are used");
            }
            this.zzen.put(str, str2);
            return str2;
        }
        if (str2 == null) {
            str2 = "UNKNOWN";
            zzel.d("LibraryVersion", ".properties file is dropped during release process. Failure to read app version isexpected druing Google internal testing where locally-built libraries are used");
        }
        this.zzen.put(str, str2);
        return str2;
    }
}
