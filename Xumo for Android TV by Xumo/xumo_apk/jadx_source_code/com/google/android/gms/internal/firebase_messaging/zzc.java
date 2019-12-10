package com.google.android.gms.internal.firebase_messaging;

import java.io.PrintStream;

public final class zzc {
    private static final zzd zzb;
    private static final int zzc;

    static final class zza extends zzd {
        zza() {
        }

        public final void zza(Throwable th, Throwable th2) {
        }
    }

    public static void zza(Throwable th, Throwable th2) {
        zzb.zza(th, th2);
    }

    private static Integer zza() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    static {
        Integer zza;
        zzd com_google_android_gms_internal_firebase_messaging_zzh;
        Throwable th;
        PrintStream printStream;
        String name;
        StringBuilder stringBuilder;
        int i = 1;
        try {
            zza = zza();
            if (zza != null) {
                try {
                    if (zza.intValue() >= 19) {
                        com_google_android_gms_internal_firebase_messaging_zzh = new zzh();
                        zzb = com_google_android_gms_internal_firebase_messaging_zzh;
                        if (zza != null) {
                            i = zza.intValue();
                        }
                        zzc = i;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    printStream = System.err;
                    name = zza.class.getName();
                    stringBuilder = new StringBuilder(String.valueOf(name).length() + 132);
                    stringBuilder.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
                    stringBuilder.append(name);
                    stringBuilder.append("will be used. The error is: ");
                    printStream.println(stringBuilder.toString());
                    th.printStackTrace(System.err);
                    com_google_android_gms_internal_firebase_messaging_zzh = new zza();
                    zzb = com_google_android_gms_internal_firebase_messaging_zzh;
                    if (zza != null) {
                        i = zza.intValue();
                    }
                    zzc = i;
                }
            }
            if ((Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ^ 1) != 0) {
                com_google_android_gms_internal_firebase_messaging_zzh = new zzg();
            } else {
                com_google_android_gms_internal_firebase_messaging_zzh = new zza();
            }
        } catch (Throwable th3) {
            th = th3;
            zza = null;
            printStream = System.err;
            name = zza.class.getName();
            stringBuilder = new StringBuilder(String.valueOf(name).length() + 132);
            stringBuilder.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
            stringBuilder.append(name);
            stringBuilder.append("will be used. The error is: ");
            printStream.println(stringBuilder.toString());
            th.printStackTrace(System.err);
            com_google_android_gms_internal_firebase_messaging_zzh = new zza();
            zzb = com_google_android_gms_internal_firebase_messaging_zzh;
            if (zza != null) {
                i = zza.intValue();
            }
            zzc = i;
        }
        zzb = com_google_android_gms_internal_firebase_messaging_zzh;
        if (zza != null) {
            i = zza.intValue();
        }
        zzc = i;
    }
}
