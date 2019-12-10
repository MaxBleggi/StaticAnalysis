package com.xumo.xumo.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver.PendingResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MessagingAnalytics;
import com.google.firebase.messaging.RemoteMessage;
import com.xumo.xumo.service.XumoFcmAlarmService;
import com.xumo.xumo.service.XumoFcmJobService;
import com.xumo.xumo.util.LogUtil;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class XumoFcmReceiver extends WakefulBroadcastReceiver {
    public void onReceive(final Context context, final Intent intent) {
        abortBroadcast();
        if (sendFcmAck(intent)) {
            sendFcmMessageReceivedEvent(intent);
            if (intent.getExtras() != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Received new PN broadcast intent: action=");
                stringBuilder.append(intent.getAction());
                stringBuilder.append(" categories=");
                stringBuilder.append(intent.getCategories());
                LogUtil.d(stringBuilder.toString());
                final PendingResult goAsync = goAsync();
                new Thread() {
                    public void run() {
                        if (MessagingAnalytics.shouldUploadMetrics(intent)) {
                            MessagingAnalytics.logNotificationReceived(intent);
                            MessagingAnalytics.logNotificationForeground(intent);
                        }
                        if (VERSION.SDK_INT < 21) {
                            Intent intent = new Intent(context, XumoFcmAlarmService.class);
                            intent.putExtras(intent.getExtras());
                            ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(2, SystemClock.elapsedRealtime(), PendingIntent.getService(context, 0, intent, 1073741824));
                        } else {
                            PersistableBundle persistableBundle = new PersistableBundle();
                            Bundle extras = intent.getExtras();
                            for (String str : extras.keySet()) {
                                Object obj = extras.get(str);
                                if (obj != null) {
                                    if (obj instanceof String) {
                                        persistableBundle.putString(str, (String) obj);
                                    } else if (obj instanceof Integer) {
                                        persistableBundle.putInt(str, ((Integer) obj).intValue());
                                    } else if (obj instanceof Long) {
                                        persistableBundle.putLong(str, ((Long) obj).longValue());
                                    }
                                }
                            }
                            ((JobScheduler) context.getSystemService("jobscheduler")).schedule(new Builder(0, new ComponentName(context, XumoFcmJobService.class)).setExtras(persistableBundle).setRequiredNetworkType(1).build());
                        }
                        goAsync.finish();
                    }
                }.start();
            }
        }
    }

    private boolean sendFcmAck(Intent intent) {
        intent = intent.getExtras();
        Object fcmSenderId = getFcmSenderId(intent);
        intent = getFcmMsgId(intent);
        if (TextUtils.isEmpty(fcmSenderId) || TextUtils.isEmpty(intent)) {
            return null;
        }
        FirebaseMessaging instance = FirebaseMessaging.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fcmSenderId);
        stringBuilder.append("@gcm.googleapis.com");
        instance.send(new RemoteMessage.Builder(stringBuilder.toString()).setMessageId(intent).build());
        return true;
    }

    private String getFcmSenderId(Bundle bundle) {
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                if (str.equals(JsonKey.FROM)) {
                    return bundle.getString(str);
                }
            }
        }
        return "";
    }

    private String getFcmMsgId(Bundle bundle) {
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                if (str.equals("google.message_id")) {
                    return bundle.getString(str);
                }
            }
        }
        return "";
    }

    private void sendFcmMessageReceivedEvent(Intent intent) {
        intent = getFcmSenderId(intent.getExtras());
        AtomicInteger atomicInteger = new AtomicInteger();
        FirebaseMessaging instance = FirebaseMessaging.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(intent);
        stringBuilder.append("@gcm.googleapis.com");
        instance.send(new RemoteMessage.Builder(stringBuilder.toString()).setMessageId(Integer.toString(atomicInteger.incrementAndGet())).addData("xumo_fcm_message_received", new Date().toString()).build());
    }
}
