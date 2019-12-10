package com.amazon.device.ads.aftv;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import androidx.core.os.EnvironmentCompat;
import java.util.List;

class KSOMessageProcessor {
    private static final String ERROR_MSG = "errorMsg";
    private static final String KEY_APP_ID = "appId";
    private static final String KEY_CUSTOM_JSON = "customJSON";
    private static final String KEY_IS_FOREGROUND = "isForeground";
    private static final String KEY_IS_TEST = "isTest";
    private static final String KEY_PACKAGE_INFO = "pkg";
    private static final String KEY_SLOTS_LIST = "slots";
    private static final String KEY_TIMEOUT = "timeout";
    private static final String KVP = "kvp";
    private static final String LOGTAG = "KSOMessageProcessor";

    KSOMessageProcessor() {
    }

    static void prepareRequestMessageObjectForKSO(Bundle bundle, AmazonFireTVAdRequest amazonFireTVAdRequest) {
        String bool;
        Boolean valueOf = Boolean.valueOf(isForegroundProcess(amazonFireTVAdRequest.getContext()));
        String str = LOGTAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" APS: App foreground status is ");
        stringBuilder.append(valueOf);
        Log.i(str, stringBuilder.toString());
        bundle.putString(KEY_APP_ID, amazonFireTVAdRequest.getAppId());
        bundle.putParcelableArrayList(KEY_SLOTS_LIST, amazonFireTVAdRequest.getSlots());
        str = KEY_IS_TEST;
        if (amazonFireTVAdRequest.getTestFlag() == null) {
            bool = Boolean.FALSE.toString();
        } else {
            bool = amazonFireTVAdRequest.getTestFlag().toString();
        }
        bundle.putString(str, bool);
        bundle.putBundle(KEY_PACKAGE_INFO, AmazonFireTVAdsPackageData.getAppBundle(amazonFireTVAdRequest.getContext()));
        if (amazonFireTVAdRequest.getTimeout() != null) {
            bundle.putLong(KEY_TIMEOUT, amazonFireTVAdRequest.getTimeout().longValue());
        }
        bundle.putBoolean(KEY_IS_FOREGROUND, valueOf.booleanValue());
        bundle.putString(KEY_CUSTOM_JSON, amazonFireTVAdRequest.getJsonString());
    }

    static com.amazon.device.ads.aftv.AmazonFireTVAdResponse handleKSOResponseMessage(android.os.Message r9) {
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
        r0 = new com.amazon.device.ads.aftv.AmazonFireTVAdResponse;
        r0.<init>();
        r1 = new java.util.ArrayList;
        r1.<init>();
        if (r9 != 0) goto L_0x0019;
    L_0x000c:
        r9 = "Did not receive a response from amazon";
        r0.setReasonString(r9);
        r9 = LOGTAG;
        r1 = "APS: Message object received from kso was null";
        android.util.Log.e(r9, r1);
        return r0;
    L_0x0019:
        r2 = r9.getData();
        if (r2 != 0) goto L_0x002c;
    L_0x001f:
        r9 = "Did not receive a valid response (payload) from amazon";
        r0.setReasonString(r9);
        r9 = LOGTAG;
        r1 = "APS: message.getData() was null in the response received from kso";
        android.util.Log.e(r9, r1);
        return r0;
    L_0x002c:
        r2 = r9.getData();
        r3 = "errorMsg";
        r2 = r2.getString(r3);
        if (r2 != 0) goto L_0x007d;
    L_0x0038:
        r2 = r9.getData();
        r3 = "kvp";
        r2 = r2.getParcelableArrayList(r3);
        if (r2 == 0) goto L_0x007d;
    L_0x0044:
        r3 = r2.isEmpty();
        if (r3 != 0) goto L_0x007d;
    L_0x004a:
        r2 = r2.iterator();
    L_0x004e:
        r3 = r2.hasNext();
        if (r3 == 0) goto L_0x007d;
    L_0x0054:
        r3 = r2.next();
        r3 = (android.os.Parcelable) r3;
        r3 = (android.os.Bundle) r3;
        r4 = r3.keySet();
        r4 = r4.iterator();
    L_0x0064:
        r5 = r4.hasNext();
        if (r5 == 0) goto L_0x004e;
    L_0x006a:
        r5 = r4.next();
        r5 = (java.lang.String) r5;
        r6 = new com.amazon.device.ads.aftv.AmazonFireTVAdsKeyValuePair;
        r7 = r3.getString(r5);
        r6.<init>(r5, r7);
        r1.add(r6);
        goto L_0x0064;
    L_0x007d:
        logBidStatus(r9, r1);
        r2 = r1.isEmpty();
        r3 = 1;
        r2 = r2 ^ r3;
        r4 = r9.getData();
        r5 = r4.keySet();
        r5 = r5.iterator();
    L_0x0092:
        r6 = r5.hasNext();
        if (r6 == 0) goto L_0x00b1;
    L_0x0098:
        r6 = r5.next();
        r6 = (java.lang.String) r6;
        r7 = r4.getString(r6);	 Catch:{ ClassCastException -> 0x0092 }
        if (r7 == 0) goto L_0x0092;	 Catch:{ ClassCastException -> 0x0092 }
    L_0x00a4:
        r7 = new com.amazon.device.ads.aftv.AmazonFireTVAdsKeyValuePair;	 Catch:{ ClassCastException -> 0x0092 }
        r8 = r4.getString(r6);	 Catch:{ ClassCastException -> 0x0092 }
        r7.<init>(r6, r8);	 Catch:{ ClassCastException -> 0x0092 }
        r1.add(r7);	 Catch:{ ClassCastException -> 0x0092 }
        goto L_0x0092;
    L_0x00b1:
        r4 = r1.isEmpty();
        if (r4 != 0) goto L_0x00c1;
    L_0x00b7:
        if (r2 != 0) goto L_0x00ba;
    L_0x00b9:
        goto L_0x00c1;
    L_0x00ba:
        r0.setAdServerTargetingParams(r1);
        r0.setContainsBids(r3);
        goto L_0x00e3;
    L_0x00c1:
        r1 = r9.getData();
        if (r1 == 0) goto L_0x00de;
    L_0x00c7:
        r1 = r9.getData();
        r2 = "errorMsg";
        r1 = r1.getString(r2);
        if (r1 == 0) goto L_0x00de;
    L_0x00d3:
        r9 = r9.getData();
        r1 = "errorMsg";
        r9 = r9.getString(r1);
        goto L_0x00e0;
    L_0x00de:
        r9 = "unknown error message";
    L_0x00e0:
        r0.setReasonString(r9);
    L_0x00e3:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazon.device.ads.aftv.KSOMessageProcessor.handleKSOResponseMessage(android.os.Message):com.amazon.device.ads.aftv.AmazonFireTVAdResponse");
    }

    private static void logBidStatus(Message message, List<AmazonFireTVAdsKeyValuePair> list) {
        if (list.isEmpty()) {
            message = message.getData().getString(ERROR_MSG) != null ? message.getData().getString(ERROR_MSG) : EnvironmentCompat.MEDIA_UNKNOWN;
            list = LOGTAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("APS: No bids were returned from amazon. Reason: ");
            stringBuilder.append(message);
            Log.i(list, stringBuilder.toString());
            return;
        }
        message = LOGTAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("APS: Bids received from amazon : ");
        stringBuilder.append(list);
        Log.i(message, stringBuilder.toString());
    }

    private static boolean isForegroundProcess(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        boolean z = false;
        if (activityManager == null) {
            return false;
        }
        Context<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        if (!(runningAppProcesses == null || runningAppProcesses.isEmpty())) {
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == Process.myPid()) {
                    if (runningAppProcessInfo.importance == 100) {
                        z = true;
                    }
                    return z;
                }
            }
        }
        return false;
    }
}
