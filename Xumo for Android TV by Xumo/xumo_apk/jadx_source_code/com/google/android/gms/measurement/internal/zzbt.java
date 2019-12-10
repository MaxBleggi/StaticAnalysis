package com.google.android.gms.measurement.internal;

import android.os.Process;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.BlockingQueue;

final class zzbt extends Thread {
    private final /* synthetic */ zzbp zzapm;
    private final Object zzapp = new Object();
    private final BlockingQueue<zzbs<?>> zzapq;

    public zzbt(zzbp com_google_android_gms_measurement_internal_zzbp, String str, BlockingQueue<zzbs<?>> blockingQueue) {
        this.zzapm = com_google_android_gms_measurement_internal_zzbp;
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(blockingQueue);
        this.zzapq = blockingQueue;
        setName(str);
    }

    public final void run() {
        Object obj = null;
        while (obj == null) {
            try {
                this.zzapm.zzapi.acquire();
                obj = 1;
            } catch (InterruptedException e) {
                zza(e);
            }
        }
        try {
            int threadPriority = Process.getThreadPriority(Process.myTid());
            while (true) {
                zzbs com_google_android_gms_measurement_internal_zzbs = (zzbs) this.zzapq.poll();
                if (com_google_android_gms_measurement_internal_zzbs != null) {
                    Process.setThreadPriority(com_google_android_gms_measurement_internal_zzbs.zzapo ? threadPriority : 10);
                    com_google_android_gms_measurement_internal_zzbs.run();
                } else {
                    synchronized (this.zzapp) {
                        if (this.zzapq.peek() == null && !this.zzapm.zzapj) {
                            try {
                                this.zzapp.wait(30000);
                            } catch (InterruptedException e2) {
                                zza(e2);
                            }
                        }
                    }
                    synchronized (this.zzapm.zzaph) {
                        if (this.zzapq.peek() == null) {
                            break;
                        }
                    }
                }
            }
            synchronized (this.zzapm.zzaph) {
                this.zzapm.zzapi.release();
                this.zzapm.zzaph.notifyAll();
                if (this == this.zzapm.zzapb) {
                    this.zzapm.zzapb = null;
                } else if (this == this.zzapm.zzapc) {
                    this.zzapm.zzapc = null;
                } else {
                    this.zzapm.zzgt().zzjg().zzca("Current scheduler thread is neither worker nor network");
                }
            }
        } catch (Throwable th) {
            synchronized (this.zzapm.zzaph) {
                this.zzapm.zzapi.release();
                this.zzapm.zzaph.notifyAll();
                if (this == this.zzapm.zzapb) {
                    this.zzapm.zzapb = null;
                } else if (this == this.zzapm.zzapc) {
                    this.zzapm.zzapc = null;
                } else {
                    this.zzapm.zzgt().zzjg().zzca("Current scheduler thread is neither worker nor network");
                }
            }
        }
    }

    public final void zzki() {
        synchronized (this.zzapp) {
            this.zzapp.notifyAll();
        }
    }

    private final void zza(InterruptedException interruptedException) {
        this.zzapm.zzgt().zzjj().zzg(String.valueOf(getName()).concat(" was interrupted"), interruptedException);
    }
}
