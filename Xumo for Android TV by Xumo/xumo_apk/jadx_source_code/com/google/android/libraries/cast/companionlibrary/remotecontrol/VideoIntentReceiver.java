package com.google.android.libraries.cast.companionlibrary.remotecontrol;

import android.content.BroadcastReceiver;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;

public class VideoIntentReceiver extends BroadcastReceiver {
    private static final String TAG = LogUtils.makeLogTag(VideoIntentReceiver.class);

    public void onReceive(android.content.Context r7, android.content.Intent r8) {
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
        r6 = this;
        r0 = r8.getAction();
        if (r0 != 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r1 = com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager.getInstance();
        r2 = -1;
        r3 = r0.hashCode();
        r4 = 0;
        r5 = 1;
        switch(r3) {
            case -1659235314: goto L_0x0052;
            case -1532067277: goto L_0x0048;
            case -1531995789: goto L_0x003e;
            case -1134516793: goto L_0x0034;
            case 1153919611: goto L_0x002a;
            case 1706654521: goto L_0x0020;
            case 1997055314: goto L_0x0016;
            default: goto L_0x0015;
        };
    L_0x0015:
        goto L_0x005b;
    L_0x0016:
        r3 = "android.intent.action.MEDIA_BUTTON";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x005b;
    L_0x001e:
        r2 = 6;
        goto L_0x005b;
    L_0x0020:
        r3 = "com.google.android.libraries.cast.companionlibrary.action.forward";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x005b;
    L_0x0028:
        r2 = 3;
        goto L_0x005b;
    L_0x002a:
        r3 = "com.google.android.libraries.cast.companionlibrary.action.toggleplayback";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x005b;
    L_0x0032:
        r2 = 0;
        goto L_0x005b;
    L_0x0034:
        r3 = "com.google.android.libraries.cast.companionlibrary.action.rewind";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x005b;
    L_0x003c:
        r2 = 4;
        goto L_0x005b;
    L_0x003e:
        r3 = "com.google.android.libraries.cast.companionlibrary.action.playprev";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x005b;
    L_0x0046:
        r2 = 2;
        goto L_0x005b;
    L_0x0048:
        r3 = "com.google.android.libraries.cast.companionlibrary.action.playnext";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x005b;
    L_0x0050:
        r2 = 1;
        goto L_0x005b;
    L_0x0052:
        r3 = "com.google.android.libraries.cast.companionlibrary.action.stop";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x005b;
    L_0x005a:
        r2 = 5;
    L_0x005b:
        r0 = 0;
        switch(r2) {
            case 0: goto L_0x00ee;
            case 1: goto L_0x00e2;
            case 2: goto L_0x00d6;
            case 3: goto L_0x00c4;
            case 4: goto L_0x00b2;
            case 5: goto L_0x0095;
            case 6: goto L_0x0061;
            default: goto L_0x005f;
        };
    L_0x005f:
        goto L_0x00f9;
    L_0x0061:
        r7 = "android.intent.extra.KEY_EVENT";
        r7 = r8.hasExtra(r7);
        if (r7 != 0) goto L_0x006a;
    L_0x0069:
        return;
    L_0x006a:
        r7 = r8.getExtras();
        r8 = "android.intent.extra.KEY_EVENT";
        r7 = r7.get(r8);
        r7 = (android.view.KeyEvent) r7;
        if (r7 == 0) goto L_0x0094;
    L_0x0078:
        r8 = r7.getAction();
        if (r8 == 0) goto L_0x007f;
    L_0x007e:
        goto L_0x0094;
    L_0x007f:
        r7 = r7.getKeyCode();
        r8 = 85;
        if (r7 != r8) goto L_0x00f9;
    L_0x0087:
        r1.togglePlayback();	 Catch:{ CastException -> 0x008c, CastException -> 0x008c, CastException -> 0x008c }
        goto L_0x00f9;
    L_0x008c:
        r7 = TAG;
        r8 = "onReceive() Failed to toggle playback ";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGE(r7, r8);
        goto L_0x00f9;
    L_0x0094:
        return;
    L_0x0095:
        r8 = TAG;
        r0 = "Calling stopApplication from intent";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGD(r8, r0);
        r1.disconnectDevice(r5, r5, r5);
        r8 = r1.getNotificationServiceClass();
        if (r8 == 0) goto L_0x00f9;
    L_0x00a5:
        r8 = new android.content.Intent;
        r0 = r1.getNotificationServiceClass();
        r8.<init>(r7, r0);
        r7.stopService(r8);
        goto L_0x00f9;
    L_0x00b2:
        r7 = "ccl_extra_forward_step_ms";
        r7 = r8.getIntExtra(r7, r4);
        r1.forward(r7);	 Catch:{ TransientNetworkDisconnectionException -> 0x00bc, TransientNetworkDisconnectionException -> 0x00bc }
        goto L_0x00f9;
    L_0x00bc:
        r7 = TAG;
        r8 = "onReceive() Failed to rewind the media";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGE(r7, r8);
        goto L_0x00f9;
    L_0x00c4:
        r7 = "ccl_extra_forward_step_ms";
        r7 = r8.getIntExtra(r7, r4);
        r1.forward(r7);	 Catch:{ TransientNetworkDisconnectionException -> 0x00ce, TransientNetworkDisconnectionException -> 0x00ce }
        goto L_0x00f9;
    L_0x00ce:
        r7 = TAG;
        r8 = "onReceive() Failed to forward the media";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGE(r7, r8);
        goto L_0x00f9;
    L_0x00d6:
        r1.queuePrev(r0);	 Catch:{ TransientNetworkDisconnectionException -> 0x00da, TransientNetworkDisconnectionException -> 0x00da }
        goto L_0x00f9;
    L_0x00da:
        r7 = TAG;
        r8 = "onReceive() Failed to skip to the previous in queue";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGE(r7, r8);
        goto L_0x00f9;
    L_0x00e2:
        r1.queueNext(r0);	 Catch:{ TransientNetworkDisconnectionException -> 0x00e6, TransientNetworkDisconnectionException -> 0x00e6 }
        goto L_0x00f9;
    L_0x00e6:
        r7 = TAG;
        r8 = "onReceive() Failed to skip to the next in queue";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGE(r7, r8);
        goto L_0x00f9;
    L_0x00ee:
        r1.togglePlayback();	 Catch:{ CastException -> 0x00f2, CastException -> 0x00f2, CastException -> 0x00f2 }
        goto L_0x00f9;
    L_0x00f2:
        r7 = TAG;
        r8 = "onReceive() Failed to toggle playback";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGE(r7, r8);
    L_0x00f9:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.cast.companionlibrary.remotecontrol.VideoIntentReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }
}
