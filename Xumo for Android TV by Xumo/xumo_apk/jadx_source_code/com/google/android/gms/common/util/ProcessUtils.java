package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Nullable;

@KeepForSdk
public class ProcessUtils {
    private static String zzhd;
    private static int zzhe;

    private ProcessUtils() {
    }

    @KeepForSdk
    @Nullable
    public static String getMyProcessName() {
        if (zzhd == null) {
            if (zzhe == 0) {
                zzhe = Process.myPid();
            }
            zzhd = zzd(zzhe);
        }
        return zzhd;
    }

    @javax.annotation.Nullable
    private static java.lang.String zzd(int r4) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = 0;
        if (r4 > 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = 25;
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0037, all -> 0x0032 }
        r2.<init>(r1);	 Catch:{ IOException -> 0x0037, all -> 0x0032 }
        r1 = "/proc/";	 Catch:{ IOException -> 0x0037, all -> 0x0032 }
        r2.append(r1);	 Catch:{ IOException -> 0x0037, all -> 0x0032 }
        r2.append(r4);	 Catch:{ IOException -> 0x0037, all -> 0x0032 }
        r4 = "/cmdline";	 Catch:{ IOException -> 0x0037, all -> 0x0032 }
        r2.append(r4);	 Catch:{ IOException -> 0x0037, all -> 0x0032 }
        r4 = r2.toString();	 Catch:{ IOException -> 0x0037, all -> 0x0032 }
        r4 = zzj(r4);	 Catch:{ IOException -> 0x0037, all -> 0x0032 }
        r1 = r4.readLine();	 Catch:{ IOException -> 0x0038, all -> 0x002d }
        r1 = r1.trim();	 Catch:{ IOException -> 0x0038, all -> 0x002d }
        com.google.android.gms.common.util.IOUtils.closeQuietly(r4);
        r0 = r1;
        goto L_0x003b;
    L_0x002d:
        r0 = move-exception;
        r3 = r0;
        r0 = r4;
        r4 = r3;
        goto L_0x0033;
    L_0x0032:
        r4 = move-exception;
    L_0x0033:
        com.google.android.gms.common.util.IOUtils.closeQuietly(r0);
        throw r4;
    L_0x0037:
        r4 = r0;
    L_0x0038:
        com.google.android.gms.common.util.IOUtils.closeQuietly(r4);
    L_0x003b:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.ProcessUtils.zzd(int):java.lang.String");
    }

    private static BufferedReader zzj(String str) throws IOException {
        ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            return bufferedReader;
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }
}
