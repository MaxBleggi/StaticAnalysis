package com.google.android.gms.tasks;

final class zzp implements Runnable {
    private final /* synthetic */ Task zzg;
    private final /* synthetic */ zzo zzs;

    zzp(zzo com_google_android_gms_tasks_zzo, Task task) {
        this.zzs = com_google_android_gms_tasks_zzo;
        this.zzg = task;
    }

    public final void run() {
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
        r3 = this;
        r0 = r3.zzs;	 Catch:{ RuntimeExecutionException -> 0x0042, CancellationException -> 0x003c, Exception -> 0x0035 }
        r0 = r0.zzr;	 Catch:{ RuntimeExecutionException -> 0x0042, CancellationException -> 0x003c, Exception -> 0x0035 }
        r1 = r3.zzg;	 Catch:{ RuntimeExecutionException -> 0x0042, CancellationException -> 0x003c, Exception -> 0x0035 }
        r1 = r1.getResult();	 Catch:{ RuntimeExecutionException -> 0x0042, CancellationException -> 0x003c, Exception -> 0x0035 }
        r0 = r0.then(r1);	 Catch:{ RuntimeExecutionException -> 0x0042, CancellationException -> 0x003c, Exception -> 0x0035 }
        if (r0 != 0) goto L_0x001f;
    L_0x0012:
        r0 = r3.zzs;
        r1 = new java.lang.NullPointerException;
        r2 = "Continuation returned null";
        r1.<init>(r2);
        r0.onFailure(r1);
        return;
    L_0x001f:
        r1 = com.google.android.gms.tasks.TaskExecutors.zzw;
        r2 = r3.zzs;
        r0.addOnSuccessListener(r1, r2);
        r1 = com.google.android.gms.tasks.TaskExecutors.zzw;
        r2 = r3.zzs;
        r0.addOnFailureListener(r1, r2);
        r1 = com.google.android.gms.tasks.TaskExecutors.zzw;
        r2 = r3.zzs;
        r0.addOnCanceledListener(r1, r2);
        return;
    L_0x0035:
        r0 = move-exception;
        r1 = r3.zzs;
        r1.onFailure(r0);
        return;
    L_0x003c:
        r0 = r3.zzs;
        r0.onCanceled();
        return;
    L_0x0042:
        r0 = move-exception;
        r1 = r0.getCause();
        r1 = r1 instanceof java.lang.Exception;
        if (r1 == 0) goto L_0x0057;
    L_0x004b:
        r1 = r3.zzs;
        r0 = r0.getCause();
        r0 = (java.lang.Exception) r0;
        r1.onFailure(r0);
        return;
    L_0x0057:
        r1 = r3.zzs;
        r1.onFailure(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tasks.zzp.run():void");
    }
}
