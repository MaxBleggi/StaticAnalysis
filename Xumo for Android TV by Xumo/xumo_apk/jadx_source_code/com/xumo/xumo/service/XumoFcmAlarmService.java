package com.xumo.xumo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class XumoFcmAlarmService extends Service {
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(android.content.Intent r4, int r5, int r6) {
        /* JADX: method processing error */
/*
Error: java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:410)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.fixIterableType(LoopRegionVisitor.java:308)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkIterableForEach(LoopRegionVisitor.java:249)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.processLoopRegion(LoopRegionVisitor.java:68)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.enterRegion(LoopRegionVisitor.java:52)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:56)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:18)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.visit(LoopRegionVisitor.java:46)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r3 = this;
        if (r4 == 0) goto L_0x003c;
    L_0x0002:
        r5 = new com.xumo.xumo.service.XumoFirebaseMessagingHandler;
        r5.<init>();
        r4 = r4.getExtras();
        if (r4 == 0) goto L_0x003c;
    L_0x000d:
        r6 = r4.keySet();
        r0 = new java.util.HashMap;
        r0.<init>();
        r6 = r6.iterator();
    L_0x001a:
        r1 = r6.hasNext();
        if (r1 == 0) goto L_0x0030;
    L_0x0020:
        r1 = r6.next();
        r1 = (java.lang.String) r1;
        r2 = r4.get(r1);
        if (r2 == 0) goto L_0x001a;
    L_0x002c:
        r0.put(r1, r2);
        goto L_0x001a;
    L_0x0030:
        r4 = r3.getApplicationContext();
        r6 = new com.xumo.xumo.service.-$$Lambda$-GYCPM_cnAjiQZptV6lOI8SiyWs;
        r6.<init>(r3);
        r5.handleFirebaseMessaging(r4, r0, r6);
    L_0x003c:
        r4 = 0;
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.service.XumoFcmAlarmService.onStartCommand(android.content.Intent, int, int):int");
    }
}
