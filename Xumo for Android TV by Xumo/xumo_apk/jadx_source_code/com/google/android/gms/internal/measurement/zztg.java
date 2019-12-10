package com.google.android.gms.internal.measurement;

import java.io.PrintStream;

public final class zztg {
    private static final zzth zzbso;
    private static final int zzbsp;

    static final class zza extends zzth {
        zza() {
        }

        public final void zza(Throwable th, PrintStream printStream) {
            th.printStackTrace(printStream);
        }
    }

    public static void zza(Throwable th, PrintStream printStream) {
        zzbso.zza(th, printStream);
    }

    private static Integer zztu() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    static {
        Integer zztu;
        zzth com_google_android_gms_internal_measurement_zztl;
        Throwable th;
        PrintStream printStream;
        String name;
        StringBuilder stringBuilder;
        int i = 1;
        try {
            zztu = zztu();
            if (zztu != null) {
                try {
                    if (zztu.intValue() >= 19) {
                        com_google_android_gms_internal_measurement_zztl = new zztl();
                        zzbso = com_google_android_gms_internal_measurement_zztl;
                        if (zztu != null) {
                            i = zztu.intValue();
                        }
                        zzbsp = i;
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
                    com_google_android_gms_internal_measurement_zztl = new zza();
                    zzbso = com_google_android_gms_internal_measurement_zztl;
                    if (zztu != null) {
                        i = zztu.intValue();
                    }
                    zzbsp = i;
                }
            }
            if ((Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ^ 1) != 0) {
                com_google_android_gms_internal_measurement_zztl = new zztk();
            } else {
                com_google_android_gms_internal_measurement_zztl = new zza();
            }
        } catch (Throwable th3) {
            th = th3;
            zztu = null;
            printStream = System.err;
            name = zza.class.getName();
            stringBuilder = new StringBuilder(String.valueOf(name).length() + 132);
            stringBuilder.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
            stringBuilder.append(name);
            stringBuilder.append("will be used. The error is: ");
            printStream.println(stringBuilder.toString());
            th.printStackTrace(System.err);
            com_google_android_gms_internal_measurement_zztl = new zza();
            zzbso = com_google_android_gms_internal_measurement_zztl;
            if (zztu != null) {
                i = zztu.intValue();
            }
            zzbsp = i;
        }
        zzbso = com_google_android_gms_internal_measurement_zztl;
        if (zztu != null) {
            i = zztu.intValue();
        }
        zzbsp = i;
    }
}
