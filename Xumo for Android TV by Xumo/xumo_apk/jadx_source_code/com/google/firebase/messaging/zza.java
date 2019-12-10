package com.google.firebase.messaging;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat.BigTextStyle;
import androidx.core.app.NotificationCompat.Builder;
import com.google.android.exoplayer2.C;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.firebase.iid.zzav;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

final class zza {
    private static final AtomicInteger zzdn = new AtomicInteger((int) SystemClock.elapsedRealtime());
    private Bundle zzdo;
    private final Context zzx;

    public zza(Context context) {
        this.zzx = context.getApplicationContext();
    }

    static java.lang.Object[] zzc(android.os.Bundle r1, java.lang.String r2) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.firebase.messaging.zza.zzc(android.os.Bundle, java.lang.String):java.lang.Object[]
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 5 more
*/
        /*
        r0 = java.lang.String.valueOf(r6);
        r1 = "_loc_args";
        r1 = java.lang.String.valueOf(r1);
        r2 = r1.length();
        if (r2 == 0) goto L_0x0015;
        r0 = r0.concat(r1);
        goto L_0x001b;
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1;
        r5 = zza(r5, r0);
        r0 = android.text.TextUtils.isEmpty(r5);
        r1 = 0;
        if (r0 == 0) goto L_0x0027;
        return r1;
        r0 = new org.json.JSONArray;
        r0.<init>(r5);
        r2 = r0.length();
        r2 = new java.lang.String[r2];
        r3 = 0;
        r4 = r2.length;
        if (r3 >= r4) goto L_0x003f;
        r4 = r0.opt(r3);
        r2[r3] = r4;
        r3 = r3 + 1;
        goto L_0x0033;
        return r2;
        r0 = "FirebaseMessaging";
        r6 = java.lang.String.valueOf(r6);
        r2 = "_loc_args";
        r2 = java.lang.String.valueOf(r2);
        r3 = r2.length();
        if (r3 == 0) goto L_0x0058;
        r6 = r6.concat(r2);
        goto L_0x005e;
        r2 = new java.lang.String;
        r2.<init>(r6);
        r6 = r2;
        r2 = 6;
        r6 = r6.substring(r2);
        r2 = java.lang.String.valueOf(r6);
        r2 = r2.length();
        r2 = r2 + 41;
        r3 = java.lang.String.valueOf(r5);
        r3 = r3.length();
        r2 = r2 + r3;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "Malformed ";
        r3.append(r2);
        r3.append(r6);
        r6 = ": ";
        r3.append(r6);
        r3.append(r5);
        r5 = "  Default value will be used.";
        r3.append(r5);
        r5 = r3.toString();
        android.util.Log.w(r0, r5);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zza.zzc(android.os.Bundle, java.lang.String):java.lang.Object[]");
    }

    static boolean zzf(Bundle bundle) {
        if (!"1".equals(zza(bundle, "gcm.n.e"))) {
            if (zza(bundle, "gcm.n.icon") == null) {
                return null;
            }
        }
        return true;
    }

    static String zza(Bundle bundle, String str) {
        String string = bundle.getString(str);
        return string == null ? bundle.getString(str.replace("gcm.n.", "gcm.notification.")) : string;
    }

    static String zzb(Bundle bundle, String str) {
        str = String.valueOf(str);
        String valueOf = String.valueOf("_loc_key");
        return zza(bundle, valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }

    @Nullable
    static Uri zzg(@NonNull Bundle bundle) {
        Object zza = zza(bundle, "gcm.n.link_android");
        if (TextUtils.isEmpty(zza)) {
            zza = zza(bundle, "gcm.n.link");
        }
        return TextUtils.isEmpty(zza) == null ? Uri.parse(zza) : null;
    }

    final boolean zzh(Bundle bundle) {
        zza com_google_firebase_messaging_zza = this;
        Bundle bundle2 = bundle;
        if ("1".equals(zza(bundle2, "gcm.n.noui"))) {
            return true;
        }
        Object obj;
        CharSequence zzd;
        CharSequence zzd2;
        String zza;
        Resources resources;
        int identifier;
        Integer zzl;
        String zzi;
        String str;
        Uri uri;
        Object zza2;
        Uri zzg;
        Intent launchIntentForPackage;
        Bundle bundle3;
        PendingIntent activity;
        boolean equals;
        Intent intent;
        PendingIntent zza3;
        String zza4;
        NotificationManager notificationManager;
        StringBuilder stringBuilder;
        String string;
        Builder smallIcon;
        Notification build;
        String zza5;
        NotificationManager notificationManager2;
        long uptimeMillis;
        StringBuilder stringBuilder2;
        int i;
        if (!((KeyguardManager) com_google_firebase_messaging_zza.zzx.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            if (!PlatformVersion.isAtLeastLollipop()) {
                SystemClock.sleep(10);
            }
            int myPid = Process.myPid();
            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) com_google_firebase_messaging_zza.zzx.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses != null) {
                for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                    if (runningAppProcessInfo.pid == myPid) {
                        if (runningAppProcessInfo.importance == 100) {
                            obj = 1;
                            if (obj != null) {
                                return false;
                            }
                            zzd = zzd(bundle2, "gcm.n.title");
                            if (TextUtils.isEmpty(zzd)) {
                                zzd = com_google_firebase_messaging_zza.zzx.getApplicationInfo().loadLabel(com_google_firebase_messaging_zza.zzx.getPackageManager());
                            }
                            zzd2 = zzd(bundle2, "gcm.n.body");
                            zza = zza(bundle2, "gcm.n.icon");
                            if (!TextUtils.isEmpty(zza)) {
                                resources = com_google_firebase_messaging_zza.zzx.getResources();
                                identifier = resources.getIdentifier(zza, "drawable", com_google_firebase_messaging_zza.zzx.getPackageName());
                                if (identifier == 0 && zzb(identifier)) {
                                    zzl = zzl(zza(bundle2, "gcm.n.color"));
                                    zzi = zzi(bundle);
                                    str = null;
                                    if (TextUtils.isEmpty(zzi)) {
                                        uri = null;
                                    } else {
                                        if (!"default".equals(zzi)) {
                                        }
                                        uri = RingtoneManager.getDefaultUri(2);
                                    }
                                    zza2 = zza(bundle2, "gcm.n.click_action");
                                    if (TextUtils.isEmpty(zza2)) {
                                        zzg = zzg(bundle);
                                        if (zzg == null) {
                                            launchIntentForPackage = com_google_firebase_messaging_zza.zzx.getPackageManager().getLaunchIntentForPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                                            if (launchIntentForPackage == null) {
                                                Log.w("FirebaseMessaging", "No activity found to launch app");
                                            }
                                        } else {
                                            launchIntentForPackage = new Intent("android.intent.action.VIEW");
                                            launchIntentForPackage.setPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                                            launchIntentForPackage.setData(zzg);
                                        }
                                    } else {
                                        launchIntentForPackage = new Intent(zza2);
                                        launchIntentForPackage.setPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                                        launchIntentForPackage.setFlags(C.ENCODING_PCM_MU_LAW);
                                    }
                                    if (launchIntentForPackage != null) {
                                        launchIntentForPackage.addFlags(67108864);
                                        bundle3 = new Bundle(bundle2);
                                        FirebaseMessagingService.zzj(bundle3);
                                        launchIntentForPackage.putExtras(bundle3);
                                        for (String str2 : bundle3.keySet()) {
                                            if (!str2.startsWith("gcm.n.")) {
                                            }
                                            launchIntentForPackage.removeExtra(str2);
                                        }
                                        activity = PendingIntent.getActivity(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), launchIntentForPackage, 1073741824);
                                    } else {
                                        activity = null;
                                    }
                                    if (bundle2 != null) {
                                        equals = "1".equals(bundle2.getString("google.c.a.e"));
                                    } else {
                                        equals = false;
                                    }
                                    if (equals) {
                                        intent = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
                                        zza(intent, bundle2);
                                        intent.putExtra("pending_intent", activity);
                                        activity = zzav.zza(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), intent, 1073741824);
                                        intent = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
                                        zza(intent, bundle2);
                                        zza3 = zzav.zza(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), intent, 1073741824);
                                    } else {
                                        zza3 = null;
                                    }
                                    zza4 = zza(bundle2, "gcm.n.android_channel_id");
                                    if (PlatformVersion.isAtLeastO()) {
                                        if (com_google_firebase_messaging_zza.zzx.getApplicationInfo().targetSdkVersion < 26) {
                                            notificationManager = (NotificationManager) com_google_firebase_messaging_zza.zzx.getSystemService(NotificationManager.class);
                                            if (!TextUtils.isEmpty(zza4)) {
                                                if (notificationManager.getNotificationChannel(zza4) == null) {
                                                    stringBuilder = new StringBuilder(String.valueOf(zza4).length() + 122);
                                                    stringBuilder.append("Notification Channel requested (");
                                                    stringBuilder.append(zza4);
                                                    stringBuilder.append(") has not been created by the app. Manifest configuration, or default, value will be used.");
                                                    Log.w("FirebaseMessaging", stringBuilder.toString());
                                                } else {
                                                    str = zza4;
                                                }
                                            }
                                            string = zzas().getString("com.google.firebase.messaging.default_notification_channel_id");
                                            if (!TextUtils.isEmpty(string)) {
                                                Log.w("FirebaseMessaging", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
                                            } else if (notificationManager.getNotificationChannel(string) == null) {
                                                Log.w("FirebaseMessaging", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
                                            } else {
                                                str = string;
                                            }
                                            if (notificationManager.getNotificationChannel("fcm_fallback_notification_channel") == null) {
                                                notificationManager.createNotificationChannel(new NotificationChannel("fcm_fallback_notification_channel", com_google_firebase_messaging_zza.zzx.getString(R.string.fcm_fallback_notification_channel_label), 3));
                                            }
                                            str = "fcm_fallback_notification_channel";
                                        }
                                    }
                                    smallIcon = new Builder(com_google_firebase_messaging_zza.zzx).setAutoCancel(true).setSmallIcon(identifier);
                                    if (!TextUtils.isEmpty(zzd)) {
                                        smallIcon.setContentTitle(zzd);
                                    }
                                    if (!TextUtils.isEmpty(zzd2)) {
                                        smallIcon.setContentText(zzd2);
                                        smallIcon.setStyle(new BigTextStyle().bigText(zzd2));
                                    }
                                    if (zzl != null) {
                                        smallIcon.setColor(zzl.intValue());
                                    }
                                    if (uri != null) {
                                        smallIcon.setSound(uri);
                                    }
                                    if (activity != null) {
                                        smallIcon.setContentIntent(activity);
                                    }
                                    if (zza3 != null) {
                                        smallIcon.setDeleteIntent(zza3);
                                    }
                                    if (str != null) {
                                        smallIcon.setChannelId(str);
                                    }
                                    build = smallIcon.build();
                                    zza5 = zza(bundle2, "gcm.n.tag");
                                    if (Log.isLoggable("FirebaseMessaging", 3)) {
                                        Log.d("FirebaseMessaging", "Showing notification");
                                    }
                                    notificationManager2 = (NotificationManager) com_google_firebase_messaging_zza.zzx.getSystemService("notification");
                                    if (TextUtils.isEmpty(zza5)) {
                                        uptimeMillis = SystemClock.uptimeMillis();
                                        stringBuilder2 = new StringBuilder(37);
                                        stringBuilder2.append("FCM-Notification:");
                                        stringBuilder2.append(uptimeMillis);
                                        zza5 = stringBuilder2.toString();
                                    }
                                    notificationManager2.notify(zza5, 0, build);
                                    return true;
                                }
                                identifier = resources.getIdentifier(zza, "mipmap", com_google_firebase_messaging_zza.zzx.getPackageName());
                                if (identifier == 0 && zzb(identifier)) {
                                    zzl = zzl(zza(bundle2, "gcm.n.color"));
                                    zzi = zzi(bundle);
                                    str = null;
                                    if (TextUtils.isEmpty(zzi)) {
                                        uri = null;
                                    } else if ("default".equals(zzi) || com_google_firebase_messaging_zza.zzx.getResources().getIdentifier(zzi, "raw", com_google_firebase_messaging_zza.zzx.getPackageName()) == 0) {
                                        uri = RingtoneManager.getDefaultUri(2);
                                    } else {
                                        String packageName = com_google_firebase_messaging_zza.zzx.getPackageName();
                                        StringBuilder stringBuilder3 = new StringBuilder((String.valueOf(packageName).length() + 24) + String.valueOf(zzi).length());
                                        stringBuilder3.append("android.resource://");
                                        stringBuilder3.append(packageName);
                                        stringBuilder3.append("/raw/");
                                        stringBuilder3.append(zzi);
                                        uri = Uri.parse(stringBuilder3.toString());
                                    }
                                    zza2 = zza(bundle2, "gcm.n.click_action");
                                    if (TextUtils.isEmpty(zza2)) {
                                        launchIntentForPackage = new Intent(zza2);
                                        launchIntentForPackage.setPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                                        launchIntentForPackage.setFlags(C.ENCODING_PCM_MU_LAW);
                                    } else {
                                        zzg = zzg(bundle);
                                        if (zzg == null) {
                                            launchIntentForPackage = new Intent("android.intent.action.VIEW");
                                            launchIntentForPackage.setPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                                            launchIntentForPackage.setData(zzg);
                                        } else {
                                            launchIntentForPackage = com_google_firebase_messaging_zza.zzx.getPackageManager().getLaunchIntentForPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                                            if (launchIntentForPackage == null) {
                                                Log.w("FirebaseMessaging", "No activity found to launch app");
                                            }
                                        }
                                    }
                                    if (launchIntentForPackage != null) {
                                        activity = null;
                                    } else {
                                        launchIntentForPackage.addFlags(67108864);
                                        bundle3 = new Bundle(bundle2);
                                        FirebaseMessagingService.zzj(bundle3);
                                        launchIntentForPackage.putExtras(bundle3);
                                        for (String str22 : bundle3.keySet()) {
                                            if (str22.startsWith("gcm.n.") || str22.startsWith("gcm.notification.")) {
                                                launchIntentForPackage.removeExtra(str22);
                                            }
                                        }
                                        activity = PendingIntent.getActivity(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), launchIntentForPackage, 1073741824);
                                    }
                                    if (bundle2 != null) {
                                        equals = false;
                                    } else {
                                        equals = "1".equals(bundle2.getString("google.c.a.e"));
                                    }
                                    if (equals) {
                                        intent = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
                                        zza(intent, bundle2);
                                        intent.putExtra("pending_intent", activity);
                                        activity = zzav.zza(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), intent, 1073741824);
                                        intent = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
                                        zza(intent, bundle2);
                                        zza3 = zzav.zza(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), intent, 1073741824);
                                    } else {
                                        zza3 = null;
                                    }
                                    zza4 = zza(bundle2, "gcm.n.android_channel_id");
                                    if (PlatformVersion.isAtLeastO()) {
                                        if (com_google_firebase_messaging_zza.zzx.getApplicationInfo().targetSdkVersion < 26) {
                                            notificationManager = (NotificationManager) com_google_firebase_messaging_zza.zzx.getSystemService(NotificationManager.class);
                                            if (TextUtils.isEmpty(zza4)) {
                                                if (notificationManager.getNotificationChannel(zza4) == null) {
                                                    str = zza4;
                                                } else {
                                                    stringBuilder = new StringBuilder(String.valueOf(zza4).length() + 122);
                                                    stringBuilder.append("Notification Channel requested (");
                                                    stringBuilder.append(zza4);
                                                    stringBuilder.append(") has not been created by the app. Manifest configuration, or default, value will be used.");
                                                    Log.w("FirebaseMessaging", stringBuilder.toString());
                                                }
                                            }
                                            string = zzas().getString("com.google.firebase.messaging.default_notification_channel_id");
                                            if (!TextUtils.isEmpty(string)) {
                                                Log.w("FirebaseMessaging", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
                                            } else if (notificationManager.getNotificationChannel(string) == null) {
                                                str = string;
                                            } else {
                                                Log.w("FirebaseMessaging", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
                                            }
                                            if (notificationManager.getNotificationChannel("fcm_fallback_notification_channel") == null) {
                                                notificationManager.createNotificationChannel(new NotificationChannel("fcm_fallback_notification_channel", com_google_firebase_messaging_zza.zzx.getString(R.string.fcm_fallback_notification_channel_label), 3));
                                            }
                                            str = "fcm_fallback_notification_channel";
                                        }
                                    }
                                    smallIcon = new Builder(com_google_firebase_messaging_zza.zzx).setAutoCancel(true).setSmallIcon(identifier);
                                    if (TextUtils.isEmpty(zzd)) {
                                        smallIcon.setContentTitle(zzd);
                                    }
                                    if (TextUtils.isEmpty(zzd2)) {
                                        smallIcon.setContentText(zzd2);
                                        smallIcon.setStyle(new BigTextStyle().bigText(zzd2));
                                    }
                                    if (zzl != null) {
                                        smallIcon.setColor(zzl.intValue());
                                    }
                                    if (uri != null) {
                                        smallIcon.setSound(uri);
                                    }
                                    if (activity != null) {
                                        smallIcon.setContentIntent(activity);
                                    }
                                    if (zza3 != null) {
                                        smallIcon.setDeleteIntent(zza3);
                                    }
                                    if (str != null) {
                                        smallIcon.setChannelId(str);
                                    }
                                    build = smallIcon.build();
                                    zza5 = zza(bundle2, "gcm.n.tag");
                                    if (Log.isLoggable("FirebaseMessaging", 3)) {
                                        Log.d("FirebaseMessaging", "Showing notification");
                                    }
                                    notificationManager2 = (NotificationManager) com_google_firebase_messaging_zza.zzx.getSystemService("notification");
                                    if (TextUtils.isEmpty(zza5)) {
                                        uptimeMillis = SystemClock.uptimeMillis();
                                        stringBuilder2 = new StringBuilder(37);
                                        stringBuilder2.append("FCM-Notification:");
                                        stringBuilder2.append(uptimeMillis);
                                        zza5 = stringBuilder2.toString();
                                    }
                                    notificationManager2.notify(zza5, 0, build);
                                    return true;
                                }
                                StringBuilder stringBuilder4 = new StringBuilder(String.valueOf(zza).length() + 61);
                                stringBuilder4.append("Icon resource ");
                                stringBuilder4.append(zza);
                                stringBuilder4.append(" not found. Notification will use default icon.");
                                Log.w("FirebaseMessaging", stringBuilder4.toString());
                            }
                            i = zzas().getInt("com.google.firebase.messaging.default_notification_icon", 0);
                            if (i == 0 || !zzb(i)) {
                                i = com_google_firebase_messaging_zza.zzx.getApplicationInfo().icon;
                            }
                            if (i != 0) {
                                if (!zzb(i)) {
                                    identifier = i;
                                    zzl = zzl(zza(bundle2, "gcm.n.color"));
                                    zzi = zzi(bundle);
                                    str = null;
                                    if (TextUtils.isEmpty(zzi)) {
                                        uri = null;
                                    } else {
                                        if ("default".equals(zzi)) {
                                        }
                                        uri = RingtoneManager.getDefaultUri(2);
                                    }
                                    zza2 = zza(bundle2, "gcm.n.click_action");
                                    if (TextUtils.isEmpty(zza2)) {
                                        launchIntentForPackage = new Intent(zza2);
                                        launchIntentForPackage.setPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                                        launchIntentForPackage.setFlags(C.ENCODING_PCM_MU_LAW);
                                    } else {
                                        zzg = zzg(bundle);
                                        if (zzg == null) {
                                            launchIntentForPackage = new Intent("android.intent.action.VIEW");
                                            launchIntentForPackage.setPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                                            launchIntentForPackage.setData(zzg);
                                        } else {
                                            launchIntentForPackage = com_google_firebase_messaging_zza.zzx.getPackageManager().getLaunchIntentForPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                                            if (launchIntentForPackage == null) {
                                                Log.w("FirebaseMessaging", "No activity found to launch app");
                                            }
                                        }
                                    }
                                    if (launchIntentForPackage != null) {
                                        activity = null;
                                    } else {
                                        launchIntentForPackage.addFlags(67108864);
                                        bundle3 = new Bundle(bundle2);
                                        FirebaseMessagingService.zzj(bundle3);
                                        launchIntentForPackage.putExtras(bundle3);
                                        for (String str222 : bundle3.keySet()) {
                                            if (str222.startsWith("gcm.n.")) {
                                            }
                                            launchIntentForPackage.removeExtra(str222);
                                        }
                                        activity = PendingIntent.getActivity(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), launchIntentForPackage, 1073741824);
                                    }
                                    if (bundle2 != null) {
                                        equals = false;
                                    } else {
                                        equals = "1".equals(bundle2.getString("google.c.a.e"));
                                    }
                                    if (equals) {
                                        intent = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
                                        zza(intent, bundle2);
                                        intent.putExtra("pending_intent", activity);
                                        activity = zzav.zza(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), intent, 1073741824);
                                        intent = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
                                        zza(intent, bundle2);
                                        zza3 = zzav.zza(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), intent, 1073741824);
                                    } else {
                                        zza3 = null;
                                    }
                                    zza4 = zza(bundle2, "gcm.n.android_channel_id");
                                    if (PlatformVersion.isAtLeastO()) {
                                        if (com_google_firebase_messaging_zza.zzx.getApplicationInfo().targetSdkVersion < 26) {
                                            notificationManager = (NotificationManager) com_google_firebase_messaging_zza.zzx.getSystemService(NotificationManager.class);
                                            if (TextUtils.isEmpty(zza4)) {
                                                if (notificationManager.getNotificationChannel(zza4) == null) {
                                                    str = zza4;
                                                } else {
                                                    stringBuilder = new StringBuilder(String.valueOf(zza4).length() + 122);
                                                    stringBuilder.append("Notification Channel requested (");
                                                    stringBuilder.append(zza4);
                                                    stringBuilder.append(") has not been created by the app. Manifest configuration, or default, value will be used.");
                                                    Log.w("FirebaseMessaging", stringBuilder.toString());
                                                }
                                            }
                                            string = zzas().getString("com.google.firebase.messaging.default_notification_channel_id");
                                            if (!TextUtils.isEmpty(string)) {
                                                Log.w("FirebaseMessaging", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
                                            } else if (notificationManager.getNotificationChannel(string) == null) {
                                                str = string;
                                            } else {
                                                Log.w("FirebaseMessaging", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
                                            }
                                            if (notificationManager.getNotificationChannel("fcm_fallback_notification_channel") == null) {
                                                notificationManager.createNotificationChannel(new NotificationChannel("fcm_fallback_notification_channel", com_google_firebase_messaging_zza.zzx.getString(R.string.fcm_fallback_notification_channel_label), 3));
                                            }
                                            str = "fcm_fallback_notification_channel";
                                        }
                                    }
                                    smallIcon = new Builder(com_google_firebase_messaging_zza.zzx).setAutoCancel(true).setSmallIcon(identifier);
                                    if (TextUtils.isEmpty(zzd)) {
                                        smallIcon.setContentTitle(zzd);
                                    }
                                    if (TextUtils.isEmpty(zzd2)) {
                                        smallIcon.setContentText(zzd2);
                                        smallIcon.setStyle(new BigTextStyle().bigText(zzd2));
                                    }
                                    if (zzl != null) {
                                        smallIcon.setColor(zzl.intValue());
                                    }
                                    if (uri != null) {
                                        smallIcon.setSound(uri);
                                    }
                                    if (activity != null) {
                                        smallIcon.setContentIntent(activity);
                                    }
                                    if (zza3 != null) {
                                        smallIcon.setDeleteIntent(zza3);
                                    }
                                    if (str != null) {
                                        smallIcon.setChannelId(str);
                                    }
                                    build = smallIcon.build();
                                    zza5 = zza(bundle2, "gcm.n.tag");
                                    if (Log.isLoggable("FirebaseMessaging", 3)) {
                                        Log.d("FirebaseMessaging", "Showing notification");
                                    }
                                    notificationManager2 = (NotificationManager) com_google_firebase_messaging_zza.zzx.getSystemService("notification");
                                    if (TextUtils.isEmpty(zza5)) {
                                        uptimeMillis = SystemClock.uptimeMillis();
                                        stringBuilder2 = new StringBuilder(37);
                                        stringBuilder2.append("FCM-Notification:");
                                        stringBuilder2.append(uptimeMillis);
                                        zza5 = stringBuilder2.toString();
                                    }
                                    notificationManager2.notify(zza5, 0, build);
                                    return true;
                                }
                            }
                            identifier = 17301651;
                            zzl = zzl(zza(bundle2, "gcm.n.color"));
                            zzi = zzi(bundle);
                            str = null;
                            if (TextUtils.isEmpty(zzi)) {
                                if ("default".equals(zzi)) {
                                }
                                uri = RingtoneManager.getDefaultUri(2);
                            } else {
                                uri = null;
                            }
                            zza2 = zza(bundle2, "gcm.n.click_action");
                            if (TextUtils.isEmpty(zza2)) {
                                zzg = zzg(bundle);
                                if (zzg == null) {
                                    launchIntentForPackage = com_google_firebase_messaging_zza.zzx.getPackageManager().getLaunchIntentForPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                                    if (launchIntentForPackage == null) {
                                        Log.w("FirebaseMessaging", "No activity found to launch app");
                                    }
                                } else {
                                    launchIntentForPackage = new Intent("android.intent.action.VIEW");
                                    launchIntentForPackage.setPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                                    launchIntentForPackage.setData(zzg);
                                }
                            } else {
                                launchIntentForPackage = new Intent(zza2);
                                launchIntentForPackage.setPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                                launchIntentForPackage.setFlags(C.ENCODING_PCM_MU_LAW);
                            }
                            if (launchIntentForPackage != null) {
                                launchIntentForPackage.addFlags(67108864);
                                bundle3 = new Bundle(bundle2);
                                FirebaseMessagingService.zzj(bundle3);
                                launchIntentForPackage.putExtras(bundle3);
                                for (String str2222 : bundle3.keySet()) {
                                    if (str2222.startsWith("gcm.n.")) {
                                    }
                                    launchIntentForPackage.removeExtra(str2222);
                                }
                                activity = PendingIntent.getActivity(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), launchIntentForPackage, 1073741824);
                            } else {
                                activity = null;
                            }
                            if (bundle2 != null) {
                                equals = "1".equals(bundle2.getString("google.c.a.e"));
                            } else {
                                equals = false;
                            }
                            if (equals) {
                                zza3 = null;
                            } else {
                                intent = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
                                zza(intent, bundle2);
                                intent.putExtra("pending_intent", activity);
                                activity = zzav.zza(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), intent, 1073741824);
                                intent = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
                                zza(intent, bundle2);
                                zza3 = zzav.zza(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), intent, 1073741824);
                            }
                            zza4 = zza(bundle2, "gcm.n.android_channel_id");
                            if (PlatformVersion.isAtLeastO()) {
                                if (com_google_firebase_messaging_zza.zzx.getApplicationInfo().targetSdkVersion < 26) {
                                    notificationManager = (NotificationManager) com_google_firebase_messaging_zza.zzx.getSystemService(NotificationManager.class);
                                    if (TextUtils.isEmpty(zza4)) {
                                        if (notificationManager.getNotificationChannel(zza4) == null) {
                                            stringBuilder = new StringBuilder(String.valueOf(zza4).length() + 122);
                                            stringBuilder.append("Notification Channel requested (");
                                            stringBuilder.append(zza4);
                                            stringBuilder.append(") has not been created by the app. Manifest configuration, or default, value will be used.");
                                            Log.w("FirebaseMessaging", stringBuilder.toString());
                                        } else {
                                            str = zza4;
                                        }
                                    }
                                    string = zzas().getString("com.google.firebase.messaging.default_notification_channel_id");
                                    if (!TextUtils.isEmpty(string)) {
                                        Log.w("FirebaseMessaging", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
                                    } else if (notificationManager.getNotificationChannel(string) == null) {
                                        Log.w("FirebaseMessaging", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
                                    } else {
                                        str = string;
                                    }
                                    if (notificationManager.getNotificationChannel("fcm_fallback_notification_channel") == null) {
                                        notificationManager.createNotificationChannel(new NotificationChannel("fcm_fallback_notification_channel", com_google_firebase_messaging_zza.zzx.getString(R.string.fcm_fallback_notification_channel_label), 3));
                                    }
                                    str = "fcm_fallback_notification_channel";
                                }
                            }
                            smallIcon = new Builder(com_google_firebase_messaging_zza.zzx).setAutoCancel(true).setSmallIcon(identifier);
                            if (TextUtils.isEmpty(zzd)) {
                                smallIcon.setContentTitle(zzd);
                            }
                            if (TextUtils.isEmpty(zzd2)) {
                                smallIcon.setContentText(zzd2);
                                smallIcon.setStyle(new BigTextStyle().bigText(zzd2));
                            }
                            if (zzl != null) {
                                smallIcon.setColor(zzl.intValue());
                            }
                            if (uri != null) {
                                smallIcon.setSound(uri);
                            }
                            if (activity != null) {
                                smallIcon.setContentIntent(activity);
                            }
                            if (zza3 != null) {
                                smallIcon.setDeleteIntent(zza3);
                            }
                            if (str != null) {
                                smallIcon.setChannelId(str);
                            }
                            build = smallIcon.build();
                            zza5 = zza(bundle2, "gcm.n.tag");
                            if (Log.isLoggable("FirebaseMessaging", 3)) {
                                Log.d("FirebaseMessaging", "Showing notification");
                            }
                            notificationManager2 = (NotificationManager) com_google_firebase_messaging_zza.zzx.getSystemService("notification");
                            if (TextUtils.isEmpty(zza5)) {
                                uptimeMillis = SystemClock.uptimeMillis();
                                stringBuilder2 = new StringBuilder(37);
                                stringBuilder2.append("FCM-Notification:");
                                stringBuilder2.append(uptimeMillis);
                                zza5 = stringBuilder2.toString();
                            }
                            notificationManager2.notify(zza5, 0, build);
                            return true;
                        }
                    }
                }
            }
        }
        obj = null;
        if (obj != null) {
            return false;
        }
        zzd = zzd(bundle2, "gcm.n.title");
        if (TextUtils.isEmpty(zzd)) {
            zzd = com_google_firebase_messaging_zza.zzx.getApplicationInfo().loadLabel(com_google_firebase_messaging_zza.zzx.getPackageManager());
        }
        zzd2 = zzd(bundle2, "gcm.n.body");
        zza = zza(bundle2, "gcm.n.icon");
        if (TextUtils.isEmpty(zza)) {
            resources = com_google_firebase_messaging_zza.zzx.getResources();
            identifier = resources.getIdentifier(zza, "drawable", com_google_firebase_messaging_zza.zzx.getPackageName());
            if (identifier == 0) {
            }
            identifier = resources.getIdentifier(zza, "mipmap", com_google_firebase_messaging_zza.zzx.getPackageName());
            if (identifier == 0) {
            }
            StringBuilder stringBuilder42 = new StringBuilder(String.valueOf(zza).length() + 61);
            stringBuilder42.append("Icon resource ");
            stringBuilder42.append(zza);
            stringBuilder42.append(" not found. Notification will use default icon.");
            Log.w("FirebaseMessaging", stringBuilder42.toString());
        }
        i = zzas().getInt("com.google.firebase.messaging.default_notification_icon", 0);
        i = com_google_firebase_messaging_zza.zzx.getApplicationInfo().icon;
        if (i != 0) {
            if (!zzb(i)) {
                identifier = i;
                zzl = zzl(zza(bundle2, "gcm.n.color"));
                zzi = zzi(bundle);
                str = null;
                if (TextUtils.isEmpty(zzi)) {
                    uri = null;
                } else {
                    if ("default".equals(zzi)) {
                    }
                    uri = RingtoneManager.getDefaultUri(2);
                }
                zza2 = zza(bundle2, "gcm.n.click_action");
                if (TextUtils.isEmpty(zza2)) {
                    launchIntentForPackage = new Intent(zza2);
                    launchIntentForPackage.setPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                    launchIntentForPackage.setFlags(C.ENCODING_PCM_MU_LAW);
                } else {
                    zzg = zzg(bundle);
                    if (zzg == null) {
                        launchIntentForPackage = new Intent("android.intent.action.VIEW");
                        launchIntentForPackage.setPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                        launchIntentForPackage.setData(zzg);
                    } else {
                        launchIntentForPackage = com_google_firebase_messaging_zza.zzx.getPackageManager().getLaunchIntentForPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                        if (launchIntentForPackage == null) {
                            Log.w("FirebaseMessaging", "No activity found to launch app");
                        }
                    }
                }
                if (launchIntentForPackage != null) {
                    activity = null;
                } else {
                    launchIntentForPackage.addFlags(67108864);
                    bundle3 = new Bundle(bundle2);
                    FirebaseMessagingService.zzj(bundle3);
                    launchIntentForPackage.putExtras(bundle3);
                    for (String str22222 : bundle3.keySet()) {
                        if (str22222.startsWith("gcm.n.")) {
                        }
                        launchIntentForPackage.removeExtra(str22222);
                    }
                    activity = PendingIntent.getActivity(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), launchIntentForPackage, 1073741824);
                }
                if (bundle2 != null) {
                    equals = false;
                } else {
                    equals = "1".equals(bundle2.getString("google.c.a.e"));
                }
                if (equals) {
                    intent = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
                    zza(intent, bundle2);
                    intent.putExtra("pending_intent", activity);
                    activity = zzav.zza(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), intent, 1073741824);
                    intent = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
                    zza(intent, bundle2);
                    zza3 = zzav.zza(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), intent, 1073741824);
                } else {
                    zza3 = null;
                }
                zza4 = zza(bundle2, "gcm.n.android_channel_id");
                if (PlatformVersion.isAtLeastO()) {
                    if (com_google_firebase_messaging_zza.zzx.getApplicationInfo().targetSdkVersion < 26) {
                        notificationManager = (NotificationManager) com_google_firebase_messaging_zza.zzx.getSystemService(NotificationManager.class);
                        if (TextUtils.isEmpty(zza4)) {
                            if (notificationManager.getNotificationChannel(zza4) == null) {
                                str = zza4;
                            } else {
                                stringBuilder = new StringBuilder(String.valueOf(zza4).length() + 122);
                                stringBuilder.append("Notification Channel requested (");
                                stringBuilder.append(zza4);
                                stringBuilder.append(") has not been created by the app. Manifest configuration, or default, value will be used.");
                                Log.w("FirebaseMessaging", stringBuilder.toString());
                            }
                        }
                        string = zzas().getString("com.google.firebase.messaging.default_notification_channel_id");
                        if (!TextUtils.isEmpty(string)) {
                            Log.w("FirebaseMessaging", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
                        } else if (notificationManager.getNotificationChannel(string) == null) {
                            str = string;
                        } else {
                            Log.w("FirebaseMessaging", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
                        }
                        if (notificationManager.getNotificationChannel("fcm_fallback_notification_channel") == null) {
                            notificationManager.createNotificationChannel(new NotificationChannel("fcm_fallback_notification_channel", com_google_firebase_messaging_zza.zzx.getString(R.string.fcm_fallback_notification_channel_label), 3));
                        }
                        str = "fcm_fallback_notification_channel";
                    }
                }
                smallIcon = new Builder(com_google_firebase_messaging_zza.zzx).setAutoCancel(true).setSmallIcon(identifier);
                if (TextUtils.isEmpty(zzd)) {
                    smallIcon.setContentTitle(zzd);
                }
                if (TextUtils.isEmpty(zzd2)) {
                    smallIcon.setContentText(zzd2);
                    smallIcon.setStyle(new BigTextStyle().bigText(zzd2));
                }
                if (zzl != null) {
                    smallIcon.setColor(zzl.intValue());
                }
                if (uri != null) {
                    smallIcon.setSound(uri);
                }
                if (activity != null) {
                    smallIcon.setContentIntent(activity);
                }
                if (zza3 != null) {
                    smallIcon.setDeleteIntent(zza3);
                }
                if (str != null) {
                    smallIcon.setChannelId(str);
                }
                build = smallIcon.build();
                zza5 = zza(bundle2, "gcm.n.tag");
                if (Log.isLoggable("FirebaseMessaging", 3)) {
                    Log.d("FirebaseMessaging", "Showing notification");
                }
                notificationManager2 = (NotificationManager) com_google_firebase_messaging_zza.zzx.getSystemService("notification");
                if (TextUtils.isEmpty(zza5)) {
                    uptimeMillis = SystemClock.uptimeMillis();
                    stringBuilder2 = new StringBuilder(37);
                    stringBuilder2.append("FCM-Notification:");
                    stringBuilder2.append(uptimeMillis);
                    zza5 = stringBuilder2.toString();
                }
                notificationManager2.notify(zza5, 0, build);
                return true;
            }
        }
        identifier = 17301651;
        zzl = zzl(zza(bundle2, "gcm.n.color"));
        zzi = zzi(bundle);
        str = null;
        if (TextUtils.isEmpty(zzi)) {
            if ("default".equals(zzi)) {
            }
            uri = RingtoneManager.getDefaultUri(2);
        } else {
            uri = null;
        }
        zza2 = zza(bundle2, "gcm.n.click_action");
        if (TextUtils.isEmpty(zza2)) {
            zzg = zzg(bundle);
            if (zzg == null) {
                launchIntentForPackage = com_google_firebase_messaging_zza.zzx.getPackageManager().getLaunchIntentForPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                if (launchIntentForPackage == null) {
                    Log.w("FirebaseMessaging", "No activity found to launch app");
                }
            } else {
                launchIntentForPackage = new Intent("android.intent.action.VIEW");
                launchIntentForPackage.setPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
                launchIntentForPackage.setData(zzg);
            }
        } else {
            launchIntentForPackage = new Intent(zza2);
            launchIntentForPackage.setPackage(com_google_firebase_messaging_zza.zzx.getPackageName());
            launchIntentForPackage.setFlags(C.ENCODING_PCM_MU_LAW);
        }
        if (launchIntentForPackage != null) {
            launchIntentForPackage.addFlags(67108864);
            bundle3 = new Bundle(bundle2);
            FirebaseMessagingService.zzj(bundle3);
            launchIntentForPackage.putExtras(bundle3);
            for (String str222222 : bundle3.keySet()) {
                if (str222222.startsWith("gcm.n.")) {
                }
                launchIntentForPackage.removeExtra(str222222);
            }
            activity = PendingIntent.getActivity(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), launchIntentForPackage, 1073741824);
        } else {
            activity = null;
        }
        if (bundle2 != null) {
            equals = "1".equals(bundle2.getString("google.c.a.e"));
        } else {
            equals = false;
        }
        if (equals) {
            zza3 = null;
        } else {
            intent = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
            zza(intent, bundle2);
            intent.putExtra("pending_intent", activity);
            activity = zzav.zza(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), intent, 1073741824);
            intent = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
            zza(intent, bundle2);
            zza3 = zzav.zza(com_google_firebase_messaging_zza.zzx, zzdn.incrementAndGet(), intent, 1073741824);
        }
        zza4 = zza(bundle2, "gcm.n.android_channel_id");
        if (PlatformVersion.isAtLeastO()) {
            if (com_google_firebase_messaging_zza.zzx.getApplicationInfo().targetSdkVersion < 26) {
                notificationManager = (NotificationManager) com_google_firebase_messaging_zza.zzx.getSystemService(NotificationManager.class);
                if (TextUtils.isEmpty(zza4)) {
                    if (notificationManager.getNotificationChannel(zza4) == null) {
                        stringBuilder = new StringBuilder(String.valueOf(zza4).length() + 122);
                        stringBuilder.append("Notification Channel requested (");
                        stringBuilder.append(zza4);
                        stringBuilder.append(") has not been created by the app. Manifest configuration, or default, value will be used.");
                        Log.w("FirebaseMessaging", stringBuilder.toString());
                    } else {
                        str = zza4;
                    }
                }
                string = zzas().getString("com.google.firebase.messaging.default_notification_channel_id");
                if (!TextUtils.isEmpty(string)) {
                    Log.w("FirebaseMessaging", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
                } else if (notificationManager.getNotificationChannel(string) == null) {
                    Log.w("FirebaseMessaging", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
                } else {
                    str = string;
                }
                if (notificationManager.getNotificationChannel("fcm_fallback_notification_channel") == null) {
                    notificationManager.createNotificationChannel(new NotificationChannel("fcm_fallback_notification_channel", com_google_firebase_messaging_zza.zzx.getString(R.string.fcm_fallback_notification_channel_label), 3));
                }
                str = "fcm_fallback_notification_channel";
            }
        }
        smallIcon = new Builder(com_google_firebase_messaging_zza.zzx).setAutoCancel(true).setSmallIcon(identifier);
        if (TextUtils.isEmpty(zzd)) {
            smallIcon.setContentTitle(zzd);
        }
        if (TextUtils.isEmpty(zzd2)) {
            smallIcon.setContentText(zzd2);
            smallIcon.setStyle(new BigTextStyle().bigText(zzd2));
        }
        if (zzl != null) {
            smallIcon.setColor(zzl.intValue());
        }
        if (uri != null) {
            smallIcon.setSound(uri);
        }
        if (activity != null) {
            smallIcon.setContentIntent(activity);
        }
        if (zza3 != null) {
            smallIcon.setDeleteIntent(zza3);
        }
        if (str != null) {
            smallIcon.setChannelId(str);
        }
        build = smallIcon.build();
        zza5 = zza(bundle2, "gcm.n.tag");
        if (Log.isLoggable("FirebaseMessaging", 3)) {
            Log.d("FirebaseMessaging", "Showing notification");
        }
        notificationManager2 = (NotificationManager) com_google_firebase_messaging_zza.zzx.getSystemService("notification");
        if (TextUtils.isEmpty(zza5)) {
            uptimeMillis = SystemClock.uptimeMillis();
            stringBuilder2 = new StringBuilder(37);
            stringBuilder2.append("FCM-Notification:");
            stringBuilder2.append(uptimeMillis);
            zza5 = stringBuilder2.toString();
        }
        notificationManager2.notify(zza5, 0, build);
        return true;
    }

    private final String zzd(Bundle bundle, String str) {
        Object zza = zza(bundle, str);
        if (!TextUtils.isEmpty(zza)) {
            return zza;
        }
        String zzb = zzb(bundle, str);
        if (TextUtils.isEmpty(zzb)) {
            return null;
        }
        Resources resources = this.zzx.getResources();
        int identifier = resources.getIdentifier(zzb, "string", this.zzx.getPackageName());
        if (identifier == 0) {
            bundle = "FirebaseMessaging";
            str = String.valueOf(str);
            String valueOf = String.valueOf("_loc_key");
            str = (valueOf.length() != 0 ? str.concat(valueOf) : new String(str)).substring(6);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 49) + String.valueOf(zzb).length());
            stringBuilder.append(str);
            stringBuilder.append(" resource not found: ");
            stringBuilder.append(zzb);
            stringBuilder.append(" Default value will be used.");
            Log.w(bundle, stringBuilder.toString());
            return null;
        }
        bundle = zzc(bundle, str);
        if (bundle == null) {
            return resources.getString(identifier);
        }
        try {
            return resources.getString(identifier, bundle);
        } catch (String str2) {
            bundle = Arrays.toString(bundle);
            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(zzb).length() + 58) + String.valueOf(bundle).length());
            stringBuilder2.append("Missing format argument for ");
            stringBuilder2.append(zzb);
            stringBuilder2.append(": ");
            stringBuilder2.append(bundle);
            stringBuilder2.append(" Default value will be used.");
            Log.w("FirebaseMessaging", stringBuilder2.toString(), str2);
            return null;
        }
    }

    @android.annotation.TargetApi(26)
    private final boolean zzb(int r5) {
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
        r4 = this;
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 1;
        r2 = 26;
        if (r0 == r2) goto L_0x0008;
    L_0x0007:
        return r1;
    L_0x0008:
        r0 = 0;
        r2 = r4.zzx;	 Catch:{ NotFoundException -> 0x0032 }
        r2 = r2.getResources();	 Catch:{ NotFoundException -> 0x0032 }
        r3 = 0;	 Catch:{ NotFoundException -> 0x0032 }
        r2 = r2.getDrawable(r5, r3);	 Catch:{ NotFoundException -> 0x0032 }
        r2 = r2 instanceof android.graphics.drawable.AdaptiveIconDrawable;	 Catch:{ NotFoundException -> 0x0032 }
        if (r2 == 0) goto L_0x0031;	 Catch:{ NotFoundException -> 0x0032 }
    L_0x0018:
        r1 = "FirebaseMessaging";	 Catch:{ NotFoundException -> 0x0032 }
        r2 = 77;	 Catch:{ NotFoundException -> 0x0032 }
        r3 = new java.lang.StringBuilder;	 Catch:{ NotFoundException -> 0x0032 }
        r3.<init>(r2);	 Catch:{ NotFoundException -> 0x0032 }
        r2 = "Adaptive icons cannot be used in notifications. Ignoring icon id: ";	 Catch:{ NotFoundException -> 0x0032 }
        r3.append(r2);	 Catch:{ NotFoundException -> 0x0032 }
        r3.append(r5);	 Catch:{ NotFoundException -> 0x0032 }
        r5 = r3.toString();	 Catch:{ NotFoundException -> 0x0032 }
        android.util.Log.e(r1, r5);	 Catch:{ NotFoundException -> 0x0032 }
        return r0;
    L_0x0031:
        return r1;
    L_0x0032:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zza.zzb(int):boolean");
    }

    private final java.lang.Integer zzl(java.lang.String r5) {
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
        r4 = this;
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 0;
        r2 = 21;
        if (r0 >= r2) goto L_0x0008;
    L_0x0007:
        return r1;
    L_0x0008:
        r0 = android.text.TextUtils.isEmpty(r5);
        if (r0 != 0) goto L_0x003c;
    L_0x000e:
        r0 = android.graphics.Color.parseColor(r5);	 Catch:{ IllegalArgumentException -> 0x0017 }
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ IllegalArgumentException -> 0x0017 }
        return r0;
    L_0x0017:
        r0 = "FirebaseMessaging";
        r2 = java.lang.String.valueOf(r5);
        r2 = r2.length();
        r2 = r2 + 54;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "Color ";
        r3.append(r2);
        r3.append(r5);
        r5 = " not valid. Notification will use default color.";
        r3.append(r5);
        r5 = r3.toString();
        android.util.Log.w(r0, r5);
    L_0x003c:
        r5 = r4.zzas();
        r0 = "com.google.firebase.messaging.default_notification_color";
        r2 = 0;
        r5 = r5.getInt(r0, r2);
        if (r5 == 0) goto L_0x005b;
    L_0x0049:
        r0 = r4.zzx;	 Catch:{ NotFoundException -> 0x0054 }
        r5 = androidx.core.content.ContextCompat.getColor(r0, r5);	 Catch:{ NotFoundException -> 0x0054 }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ NotFoundException -> 0x0054 }
        return r5;
    L_0x0054:
        r5 = "FirebaseMessaging";
        r0 = "Cannot find the color resource referenced in AndroidManifest.";
        android.util.Log.w(r5, r0);
    L_0x005b:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zza.zzl(java.lang.String):java.lang.Integer");
    }

    static String zzi(Bundle bundle) {
        String zza = zza(bundle, "gcm.n.sound2");
        return TextUtils.isEmpty(zza) ? zza(bundle, "gcm.n.sound") : zza;
    }

    private static void zza(Intent intent, Bundle bundle) {
        for (String str : bundle.keySet()) {
            if (str.startsWith("google.c.a.") || str.equals(JsonKey.FROM)) {
                intent.putExtra(str, bundle.getString(str));
            }
        }
    }

    private final android.os.Bundle zzas() {
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
        r4 = this;
        r0 = r4.zzdo;
        if (r0 == 0) goto L_0x0007;
    L_0x0004:
        r0 = r4.zzdo;
        return r0;
    L_0x0007:
        r0 = 0;
        r1 = r4.zzx;	 Catch:{ NameNotFoundException -> 0x001b }
        r1 = r1.getPackageManager();	 Catch:{ NameNotFoundException -> 0x001b }
        r2 = r4.zzx;	 Catch:{ NameNotFoundException -> 0x001b }
        r2 = r2.getPackageName();	 Catch:{ NameNotFoundException -> 0x001b }
        r3 = 128; // 0x80 float:1.794E-43 double:6.32E-322;	 Catch:{ NameNotFoundException -> 0x001b }
        r1 = r1.getApplicationInfo(r2, r3);	 Catch:{ NameNotFoundException -> 0x001b }
        r0 = r1;
    L_0x001b:
        if (r0 == 0) goto L_0x0028;
    L_0x001d:
        r1 = r0.metaData;
        if (r1 == 0) goto L_0x0028;
    L_0x0021:
        r0 = r0.metaData;
        r4.zzdo = r0;
        r0 = r4.zzdo;
        return r0;
    L_0x0028:
        r0 = android.os.Bundle.EMPTY;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zza.zzas():android.os.Bundle");
    }
}
