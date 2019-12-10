package com.xumo.xumo.service;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;

@TargetApi(21)
public class XumoFcmJobService extends JobService {
    private boolean waiting;

    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public boolean onStartJob(final JobParameters jobParameters) {
        new Thread() {
            public void run() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.xumo.xumo.service.XumoFcmJobService.1.run():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
*/
                /*
                r0 = this;
                r0 = com.xumo.xumo.service.XumoFcmJobService.this;
                r1 = 1;
                r0.waiting = r1;
                r0 = new com.xumo.xumo.service.XumoFirebaseMessagingHandler;
                r0.<init>();
                r1 = r2;
                r1 = r1.getExtras();
                r2 = r1.keySet();
                r3 = new java.util.HashMap;
                r3.<init>();
                r2 = r2.iterator();
                r4 = r2.hasNext();
                if (r4 == 0) goto L_0x0034;
                r4 = r2.next();
                r4 = (java.lang.String) r4;
                r5 = r1.get(r4);
                if (r5 == 0) goto L_0x001e;
                r3.put(r4, r5);
                goto L_0x001e;
                r1 = com.xumo.xumo.service.XumoFcmJobService.this;
                r1 = r1.getApplicationContext();
                r2 = new com.xumo.xumo.service.-$$Lambda$XumoFcmJobService$1$MxeZQsXEc00FduD5k1JjtjAQU0o;
                r2.<init>(r6);
                r0.handleFirebaseMessaging(r1, r3, r2);
                r0 = com.xumo.xumo.service.XumoFcmJobService.this;
                r0 = r0.waiting;
                if (r0 == 0) goto L_0x0052;
                r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
                java.lang.Thread.sleep(r0);
                goto L_0x0042;
                goto L_0x0042;
                r0 = com.xumo.xumo.service.XumoFcmJobService.this;
                r0.stopSelf();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.service.XumoFcmJobService.1.run():void");
            }
        }.run();
        return true;
    }
}
