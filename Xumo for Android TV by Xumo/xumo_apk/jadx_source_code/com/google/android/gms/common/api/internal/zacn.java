package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Result;

final class zacn implements Runnable {
    private final /* synthetic */ Result zaku;
    private final /* synthetic */ zacm zakv;

    zacn(zacm com_google_android_gms_common_api_internal_zacm, Result result) {
        this.zakv = com_google_android_gms_common_api_internal_zacm;
        this.zaku = result;
    }

    @androidx.annotation.WorkerThread
    public final void run() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0087 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r5 = this;
        r0 = 1;
        r1 = 0;
        r2 = com.google.android.gms.common.api.internal.BasePendingResult.zadm;	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r3 = java.lang.Boolean.valueOf(r0);	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r2.set(r3);	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r2 = r5.zakv;	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r2 = r2.zakn;	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r3 = r5.zaku;	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r2 = r2.onSuccess(r3);	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r3 = r5.zakv;	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r3 = r3.zaks;	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r4 = r5.zakv;	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r4 = r4.zaks;	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r2 = r4.obtainMessage(r1, r2);	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r3.sendMessage(r2);	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r0 = com.google.android.gms.common.api.internal.BasePendingResult.zadm;
        r1 = java.lang.Boolean.valueOf(r1);
        r0.set(r1);
        r0 = r5.zakv;
        r1 = r5.zaku;
        com.google.android.gms.common.api.internal.zacm.zab(r1);
        r0 = r5.zakv;
        r0 = r0.zadp;
        r0 = r0.get();
        r0 = (com.google.android.gms.common.api.GoogleApiClient) r0;
        if (r0 == 0) goto L_0x004d;
    L_0x0048:
        r1 = r5.zakv;
        r0.zab(r1);
    L_0x004d:
        return;
    L_0x004e:
        r0 = move-exception;
        goto L_0x0088;
    L_0x0050:
        r2 = move-exception;
        r3 = r5.zakv;	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r3 = r3.zaks;	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r4 = r5.zakv;	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r4 = r4.zaks;	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r0 = r4.obtainMessage(r0, r2);	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r3.sendMessage(r0);	 Catch:{ RuntimeException -> 0x0050, all -> 0x004e }
        r0 = com.google.android.gms.common.api.internal.BasePendingResult.zadm;
        r1 = java.lang.Boolean.valueOf(r1);
        r0.set(r1);
        r0 = r5.zakv;
        r1 = r5.zaku;
        com.google.android.gms.common.api.internal.zacm.zab(r1);
        r0 = r5.zakv;
        r0 = r0.zadp;
        r0 = r0.get();
        r0 = (com.google.android.gms.common.api.GoogleApiClient) r0;
        if (r0 == 0) goto L_0x0087;
    L_0x0082:
        r1 = r5.zakv;
        r0.zab(r1);
    L_0x0087:
        return;
    L_0x0088:
        r2 = com.google.android.gms.common.api.internal.BasePendingResult.zadm;
        r1 = java.lang.Boolean.valueOf(r1);
        r2.set(r1);
        r1 = r5.zakv;
        r2 = r5.zaku;
        com.google.android.gms.common.api.internal.zacm.zab(r2);
        r1 = r5.zakv;
        r1 = r1.zadp;
        r1 = r1.get();
        r1 = (com.google.android.gms.common.api.GoogleApiClient) r1;
        if (r1 == 0) goto L_0x00ab;
    L_0x00a6:
        r2 = r5.zakv;
        r1.zab(r2);
    L_0x00ab:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zacn.run():void");
    }
}
