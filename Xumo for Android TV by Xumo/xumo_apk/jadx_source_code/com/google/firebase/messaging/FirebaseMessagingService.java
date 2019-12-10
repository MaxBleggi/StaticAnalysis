package com.google.firebase.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.WorkerThread;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.iid.zzab;
import com.google.firebase.iid.zzav;
import com.google.firebase.iid.zzb;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class FirebaseMessagingService extends zzb {
    private static final Queue<String> zzdr = new ArrayDeque(10);

    @WorkerThread
    public void onDeletedMessages() {
    }

    @WorkerThread
    public void onMessageReceived(RemoteMessage remoteMessage) {
    }

    @WorkerThread
    public void onMessageSent(String str) {
    }

    @WorkerThread
    public void onNewToken(String str) {
    }

    @WorkerThread
    public void onSendError(String str, Exception exception) {
    }

    protected final Intent zzb(Intent intent) {
        return zzav.zzai().zzaj();
    }

    public final boolean zzc(android.content.Intent r3) {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r2 = this;
        r0 = "com.google.firebase.messaging.NOTIFICATION_OPEN";
        r1 = r3.getAction();
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x002c;
    L_0x000c:
        r0 = "pending_intent";
        r0 = r3.getParcelableExtra(r0);
        r0 = (android.app.PendingIntent) r0;
        if (r0 == 0) goto L_0x0021;
    L_0x0016:
        r0.send();	 Catch:{ CanceledException -> 0x001a }
        goto L_0x0021;
    L_0x001a:
        r0 = "FirebaseMessaging";
        r1 = "Notification pending intent canceled";
        android.util.Log.e(r0, r1);
    L_0x0021:
        r0 = com.google.firebase.messaging.MessagingAnalytics.shouldUploadMetrics(r3);
        if (r0 == 0) goto L_0x002a;
    L_0x0027:
        com.google.firebase.messaging.MessagingAnalytics.logNotificationOpen(r3);
    L_0x002a:
        r3 = 1;
        return r3;
    L_0x002c:
        r3 = 0;
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.FirebaseMessagingService.zzc(android.content.Intent):boolean");
    }

    public final void zzd(Intent intent) {
        Task forResult;
        Object obj;
        int hashCode;
        Bundle extras;
        String str;
        String action = intent.getAction();
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(action)) {
            if (!"com.google.firebase.messaging.RECEIVE_DIRECT_BOOT".equals(action)) {
                if ("com.google.firebase.messaging.NOTIFICATION_DISMISS".equals(action)) {
                    if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                        MessagingAnalytics.logNotificationDismiss(intent);
                        return;
                    }
                } else if ("com.google.firebase.messaging.NEW_TOKEN".equals(action)) {
                    onNewToken(intent.getStringExtra("token"));
                    return;
                } else {
                    action = "FirebaseMessaging";
                    String str2 = "Unknown intent action: ";
                    intent = String.valueOf(intent.getAction());
                    Log.d(action, intent.length() != 0 ? str2.concat(intent) : new String(str2));
                }
                return;
            }
        }
        CharSequence stringExtra = intent.getStringExtra("google.message_id");
        int i = 2;
        if (TextUtils.isEmpty(stringExtra)) {
            forResult = Tasks.forResult(null);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("google.message_id", stringExtra);
            forResult = zzab.zzc(this).zza(2, bundle);
        }
        if (!TextUtils.isEmpty(stringExtra)) {
            if (zzdr.contains(stringExtra)) {
                if (Log.isLoggable("FirebaseMessaging", 3)) {
                    String str3 = "FirebaseMessaging";
                    String str4 = "Received duplicate message: ";
                    action = String.valueOf(stringExtra);
                    Log.d(str3, action.length() != 0 ? str4.concat(action) : new String(str4));
                }
                obj = 1;
                if (obj == null) {
                    action = intent.getStringExtra("message_type");
                    if (action == null) {
                        action = "gcm";
                    }
                    hashCode = action.hashCode();
                    if (hashCode != -2062414158) {
                        if (action.equals("deleted_messages")) {
                            i = 1;
                            switch (i) {
                                case 0:
                                    if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                        MessagingAnalytics.logNotificationReceived(intent);
                                    }
                                    extras = intent.getExtras();
                                    if (extras == null) {
                                        extras = new Bundle();
                                    }
                                    extras.remove("androidx.contentpager.content.wakelockid");
                                    if (zza.zzf(extras)) {
                                        if (!new zza(this).zzh(extras)) {
                                            if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                                MessagingAnalytics.logNotificationForeground(intent);
                                            }
                                        }
                                    }
                                    onMessageReceived(new RemoteMessage(extras));
                                    break;
                                case 1:
                                    onDeletedMessages();
                                    break;
                                case 2:
                                    onMessageSent(intent.getStringExtra("google.message_id"));
                                    break;
                                case 3:
                                    action = intent.getStringExtra("google.message_id");
                                    if (action == null) {
                                        action = intent.getStringExtra("message_id");
                                    }
                                    onSendError(action, new SendException(intent.getStringExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR)));
                                    break;
                                default:
                                    intent = "FirebaseMessaging";
                                    str = "Received message with unknown type: ";
                                    action = String.valueOf(action);
                                    if (action.length() == 0) {
                                        action = new String(str);
                                    } else {
                                        action = str.concat(action);
                                    }
                                    Log.w(intent, action);
                                    break;
                            }
                        }
                    } else if (hashCode != 102161) {
                        if (action.equals("gcm")) {
                            i = 0;
                            switch (i) {
                                case 0:
                                    if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                        MessagingAnalytics.logNotificationReceived(intent);
                                    }
                                    extras = intent.getExtras();
                                    if (extras == null) {
                                        extras = new Bundle();
                                    }
                                    extras.remove("androidx.contentpager.content.wakelockid");
                                    if (zza.zzf(extras)) {
                                        if (new zza(this).zzh(extras)) {
                                            if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                                MessagingAnalytics.logNotificationForeground(intent);
                                            }
                                        }
                                    }
                                    onMessageReceived(new RemoteMessage(extras));
                                    break;
                                case 1:
                                    onDeletedMessages();
                                    break;
                                case 2:
                                    onMessageSent(intent.getStringExtra("google.message_id"));
                                    break;
                                case 3:
                                    action = intent.getStringExtra("google.message_id");
                                    if (action == null) {
                                        action = intent.getStringExtra("message_id");
                                    }
                                    onSendError(action, new SendException(intent.getStringExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR)));
                                    break;
                                default:
                                    intent = "FirebaseMessaging";
                                    str = "Received message with unknown type: ";
                                    action = String.valueOf(action);
                                    if (action.length() == 0) {
                                        action = str.concat(action);
                                    } else {
                                        action = new String(str);
                                    }
                                    Log.w(intent, action);
                                    break;
                            }
                        }
                    } else if (hashCode != 814694033) {
                        if (action.equals("send_error")) {
                            i = 3;
                            switch (i) {
                                case 0:
                                    if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                        MessagingAnalytics.logNotificationReceived(intent);
                                    }
                                    extras = intent.getExtras();
                                    if (extras == null) {
                                        extras = new Bundle();
                                    }
                                    extras.remove("androidx.contentpager.content.wakelockid");
                                    if (zza.zzf(extras)) {
                                        if (new zza(this).zzh(extras)) {
                                            if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                                MessagingAnalytics.logNotificationForeground(intent);
                                            }
                                        }
                                    }
                                    onMessageReceived(new RemoteMessage(extras));
                                    break;
                                case 1:
                                    onDeletedMessages();
                                    break;
                                case 2:
                                    onMessageSent(intent.getStringExtra("google.message_id"));
                                    break;
                                case 3:
                                    action = intent.getStringExtra("google.message_id");
                                    if (action == null) {
                                        action = intent.getStringExtra("message_id");
                                    }
                                    onSendError(action, new SendException(intent.getStringExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR)));
                                    break;
                                default:
                                    intent = "FirebaseMessaging";
                                    str = "Received message with unknown type: ";
                                    action = String.valueOf(action);
                                    if (action.length() == 0) {
                                        action = new String(str);
                                    } else {
                                        action = str.concat(action);
                                    }
                                    Log.w(intent, action);
                                    break;
                            }
                        }
                    } else if (hashCode != 814800675) {
                        if (action.equals("send_event")) {
                            switch (i) {
                                case 0:
                                    if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                        MessagingAnalytics.logNotificationReceived(intent);
                                    }
                                    extras = intent.getExtras();
                                    if (extras == null) {
                                        extras = new Bundle();
                                    }
                                    extras.remove("androidx.contentpager.content.wakelockid");
                                    if (zza.zzf(extras)) {
                                        if (new zza(this).zzh(extras)) {
                                            if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                                MessagingAnalytics.logNotificationForeground(intent);
                                            }
                                        }
                                    }
                                    onMessageReceived(new RemoteMessage(extras));
                                    break;
                                case 1:
                                    onDeletedMessages();
                                    break;
                                case 2:
                                    onMessageSent(intent.getStringExtra("google.message_id"));
                                    break;
                                case 3:
                                    action = intent.getStringExtra("google.message_id");
                                    if (action == null) {
                                        action = intent.getStringExtra("message_id");
                                    }
                                    onSendError(action, new SendException(intent.getStringExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR)));
                                    break;
                                default:
                                    intent = "FirebaseMessaging";
                                    str = "Received message with unknown type: ";
                                    action = String.valueOf(action);
                                    if (action.length() == 0) {
                                        action = str.concat(action);
                                    } else {
                                        action = new String(str);
                                    }
                                    Log.w(intent, action);
                                    break;
                            }
                        }
                    }
                    i = -1;
                    switch (i) {
                        case 0:
                            if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                MessagingAnalytics.logNotificationReceived(intent);
                            }
                            extras = intent.getExtras();
                            if (extras == null) {
                                extras = new Bundle();
                            }
                            extras.remove("androidx.contentpager.content.wakelockid");
                            if (zza.zzf(extras)) {
                                if (new zza(this).zzh(extras)) {
                                    if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                        MessagingAnalytics.logNotificationForeground(intent);
                                    }
                                }
                            }
                            onMessageReceived(new RemoteMessage(extras));
                            break;
                        case 1:
                            onDeletedMessages();
                            break;
                        case 2:
                            onMessageSent(intent.getStringExtra("google.message_id"));
                            break;
                        case 3:
                            action = intent.getStringExtra("google.message_id");
                            if (action == null) {
                                action = intent.getStringExtra("message_id");
                            }
                            onSendError(action, new SendException(intent.getStringExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR)));
                            break;
                        default:
                            intent = "FirebaseMessaging";
                            str = "Received message with unknown type: ";
                            action = String.valueOf(action);
                            if (action.length() == 0) {
                                action = str.concat(action);
                            } else {
                                action = new String(str);
                            }
                            Log.w(intent, action);
                            break;
                    }
                }
                Tasks.await(forResult, 1, TimeUnit.SECONDS);
            }
            if (zzdr.size() >= 10) {
                zzdr.remove();
            }
            zzdr.add(stringExtra);
        }
        obj = null;
        if (obj == null) {
            action = intent.getStringExtra("message_type");
            if (action == null) {
                action = "gcm";
            }
            hashCode = action.hashCode();
            if (hashCode != -2062414158) {
                if (action.equals("deleted_messages")) {
                    i = 1;
                    switch (i) {
                        case 0:
                            if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                MessagingAnalytics.logNotificationReceived(intent);
                            }
                            extras = intent.getExtras();
                            if (extras == null) {
                                extras = new Bundle();
                            }
                            extras.remove("androidx.contentpager.content.wakelockid");
                            if (zza.zzf(extras)) {
                                if (new zza(this).zzh(extras)) {
                                    if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                        MessagingAnalytics.logNotificationForeground(intent);
                                    }
                                }
                            }
                            onMessageReceived(new RemoteMessage(extras));
                            break;
                        case 1:
                            onDeletedMessages();
                            break;
                        case 2:
                            onMessageSent(intent.getStringExtra("google.message_id"));
                            break;
                        case 3:
                            action = intent.getStringExtra("google.message_id");
                            if (action == null) {
                                action = intent.getStringExtra("message_id");
                            }
                            onSendError(action, new SendException(intent.getStringExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR)));
                            break;
                        default:
                            intent = "FirebaseMessaging";
                            str = "Received message with unknown type: ";
                            action = String.valueOf(action);
                            if (action.length() == 0) {
                                action = new String(str);
                            } else {
                                action = str.concat(action);
                            }
                            Log.w(intent, action);
                            break;
                    }
                }
            } else if (hashCode != 102161) {
                if (action.equals("gcm")) {
                    i = 0;
                    switch (i) {
                        case 0:
                            if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                MessagingAnalytics.logNotificationReceived(intent);
                            }
                            extras = intent.getExtras();
                            if (extras == null) {
                                extras = new Bundle();
                            }
                            extras.remove("androidx.contentpager.content.wakelockid");
                            if (zza.zzf(extras)) {
                                if (new zza(this).zzh(extras)) {
                                    if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                        MessagingAnalytics.logNotificationForeground(intent);
                                    }
                                }
                            }
                            onMessageReceived(new RemoteMessage(extras));
                            break;
                        case 1:
                            onDeletedMessages();
                            break;
                        case 2:
                            onMessageSent(intent.getStringExtra("google.message_id"));
                            break;
                        case 3:
                            action = intent.getStringExtra("google.message_id");
                            if (action == null) {
                                action = intent.getStringExtra("message_id");
                            }
                            onSendError(action, new SendException(intent.getStringExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR)));
                            break;
                        default:
                            intent = "FirebaseMessaging";
                            str = "Received message with unknown type: ";
                            action = String.valueOf(action);
                            if (action.length() == 0) {
                                action = str.concat(action);
                            } else {
                                action = new String(str);
                            }
                            Log.w(intent, action);
                            break;
                    }
                }
            } else if (hashCode != 814694033) {
                if (action.equals("send_error")) {
                    i = 3;
                    switch (i) {
                        case 0:
                            if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                MessagingAnalytics.logNotificationReceived(intent);
                            }
                            extras = intent.getExtras();
                            if (extras == null) {
                                extras = new Bundle();
                            }
                            extras.remove("androidx.contentpager.content.wakelockid");
                            if (zza.zzf(extras)) {
                                if (new zza(this).zzh(extras)) {
                                    if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                        MessagingAnalytics.logNotificationForeground(intent);
                                    }
                                }
                            }
                            onMessageReceived(new RemoteMessage(extras));
                            break;
                        case 1:
                            onDeletedMessages();
                            break;
                        case 2:
                            onMessageSent(intent.getStringExtra("google.message_id"));
                            break;
                        case 3:
                            action = intent.getStringExtra("google.message_id");
                            if (action == null) {
                                action = intent.getStringExtra("message_id");
                            }
                            onSendError(action, new SendException(intent.getStringExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR)));
                            break;
                        default:
                            intent = "FirebaseMessaging";
                            str = "Received message with unknown type: ";
                            action = String.valueOf(action);
                            if (action.length() == 0) {
                                action = new String(str);
                            } else {
                                action = str.concat(action);
                            }
                            Log.w(intent, action);
                            break;
                    }
                }
            } else if (hashCode != 814800675) {
                if (action.equals("send_event")) {
                    switch (i) {
                        case 0:
                            if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                MessagingAnalytics.logNotificationReceived(intent);
                            }
                            extras = intent.getExtras();
                            if (extras == null) {
                                extras = new Bundle();
                            }
                            extras.remove("androidx.contentpager.content.wakelockid");
                            if (zza.zzf(extras)) {
                                if (new zza(this).zzh(extras)) {
                                    if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                        MessagingAnalytics.logNotificationForeground(intent);
                                    }
                                }
                            }
                            onMessageReceived(new RemoteMessage(extras));
                            break;
                        case 1:
                            onDeletedMessages();
                            break;
                        case 2:
                            onMessageSent(intent.getStringExtra("google.message_id"));
                            break;
                        case 3:
                            action = intent.getStringExtra("google.message_id");
                            if (action == null) {
                                action = intent.getStringExtra("message_id");
                            }
                            onSendError(action, new SendException(intent.getStringExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR)));
                            break;
                        default:
                            intent = "FirebaseMessaging";
                            str = "Received message with unknown type: ";
                            action = String.valueOf(action);
                            if (action.length() == 0) {
                                action = str.concat(action);
                            } else {
                                action = new String(str);
                            }
                            Log.w(intent, action);
                            break;
                    }
                }
            }
            i = -1;
            switch (i) {
                case 0:
                    if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                        MessagingAnalytics.logNotificationReceived(intent);
                    }
                    extras = intent.getExtras();
                    if (extras == null) {
                        extras = new Bundle();
                    }
                    extras.remove("androidx.contentpager.content.wakelockid");
                    if (zza.zzf(extras)) {
                        if (new zza(this).zzh(extras)) {
                            if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                                MessagingAnalytics.logNotificationForeground(intent);
                            }
                        }
                    }
                    onMessageReceived(new RemoteMessage(extras));
                    break;
                case 1:
                    onDeletedMessages();
                    break;
                case 2:
                    onMessageSent(intent.getStringExtra("google.message_id"));
                    break;
                case 3:
                    action = intent.getStringExtra("google.message_id");
                    if (action == null) {
                        action = intent.getStringExtra("message_id");
                    }
                    onSendError(action, new SendException(intent.getStringExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR)));
                    break;
                default:
                    intent = "FirebaseMessaging";
                    str = "Received message with unknown type: ";
                    action = String.valueOf(action);
                    if (action.length() == 0) {
                        action = new String(str);
                    } else {
                        action = str.concat(action);
                    }
                    Log.w(intent, action);
                    break;
            }
        }
        try {
            Tasks.await(forResult, 1, TimeUnit.SECONDS);
        } catch (Intent intent2) {
            intent2 = String.valueOf(intent2);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(intent2).length() + 20);
            stringBuilder.append("Message ack failed: ");
            stringBuilder.append(intent2);
            Log.w("FirebaseMessaging", stringBuilder.toString());
        }
    }

    static void zzj(Bundle bundle) {
        bundle = bundle.keySet().iterator();
        while (bundle.hasNext()) {
            String str = (String) bundle.next();
            if (str != null && str.startsWith("google.c.")) {
                bundle.remove();
            }
        }
    }
}
