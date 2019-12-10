package com.google.android.gms.internal.measurement;

import java.io.PrintStream;

final class zztk extends zzth {
    private final zzti zzbsu = new zzti();

    zztk() {
    }

    public final void zza(Throwable th, PrintStream printStream) {
        th.printStackTrace(printStream);
        Throwable<Throwable> zza = this.zzbsu.zza(th, false);
        if (zza != null) {
            synchronized (zza) {
                for (Throwable th2 : zza) {
                    printStream.print("Suppressed: ");
                    th2.printStackTrace(printStream);
                }
            }
        }
    }
}
