package com.xumo.xumo.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat.Builder;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.RemoteMessage.Notification;
import com.xumo.xumo.model.DeepLinkKey;
import com.xumo.xumo.repository.UserPreferences;
import com.xumo.xumo.tv.R;
import com.xumo.xumo.util.LogUtil;

public class XumoFirebaseMessagingService extends FirebaseMessagingService {
    public static final String PUSH_NOTIFICATION_ACTION = "com.xumo.xumo.service.XumoFirebaseMessagingService.PUSH_NOTIFICATION_ACTION";

    public void onMessageSent(String str) {
        super.onMessageSent(str);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FCM message sent: ");
        stringBuilder.append(str);
        LogUtil.d(stringBuilder.toString());
    }

    public void onSendError(String str, Exception exception) {
        super.onSendError(str, exception);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FCM message send error: ");
        stringBuilder.append(str);
        stringBuilder.append("\n\tError: ");
        stringBuilder.append(exception.getMessage());
        LogUtil.e(stringBuilder.toString());
    }

    public void onDeletedMessages() {
        super.onDeletedMessages();
        LogUtil.d("FCM messages deleted");
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        LogUtil.d("push notification received.");
        processMessage(this, remoteMessage, null);
    }

    public void processMessage(Context context, RemoteMessage remoteMessage, OnFinish onFinish) {
        String str;
        String body;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        Intent intent;
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(context);
        Notification notification = remoteMessage.getNotification();
        String title = notification != null ? notification.getTitle() : "";
        remoteMessage = remoteMessage.getData();
        if (title != null) {
            if (title.isEmpty()) {
            }
            str = title;
            body = notification == null ? notification.getBody() : "";
            if (body != null) {
                if (body.isEmpty()) {
                }
                str2 = body;
                if (TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) {
                    body = (String) remoteMessage.get(DeepLinkKey.NOTIFICATION_ID);
                    title = (String) remoteMessage.get("channelId");
                    str3 = (String) remoteMessage.get("categoryId");
                    str4 = (String) remoteMessage.get(DeepLinkKey.LIVE_CHANNEL_ID);
                    str5 = (String) remoteMessage.get("assetId");
                    str6 = (String) remoteMessage.get(DeepLinkKey.IMG_URL_ID);
                    if ((body != null || (title == null && str4 == null && str5 == null)) && (body == null || str3 != null)) {
                        intent = null;
                    } else {
                        intent = new Intent();
                        intent.setAction(PUSH_NOTIFICATION_ACTION);
                        intent.putExtra("title", str);
                        intent.putExtra("message", str2);
                        for (String str32 : remoteMessage.keySet()) {
                            intent.putExtra(str32, (String) remoteMessage.get(str32));
                        }
                        instance.sendBroadcast(intent);
                    }
                    sendNotification(context, str, str2, str6, intent, onFinish);
                }
                if (onFinish != null) {
                    onFinish.finished();
                }
                return;
            }
            body = (String) remoteMessage.get("message");
            str2 = body;
            if (TextUtils.isEmpty(str)) {
            }
            body = (String) remoteMessage.get(DeepLinkKey.NOTIFICATION_ID);
            title = (String) remoteMessage.get("channelId");
            str32 = (String) remoteMessage.get("categoryId");
            str4 = (String) remoteMessage.get(DeepLinkKey.LIVE_CHANNEL_ID);
            str5 = (String) remoteMessage.get("assetId");
            str6 = (String) remoteMessage.get(DeepLinkKey.IMG_URL_ID);
            if (body != null) {
            }
            intent = null;
            sendNotification(context, str, str2, str6, intent, onFinish);
        }
        title = (String) remoteMessage.get("title");
        str = title;
        if (notification == null) {
        }
        if (body != null) {
            if (body.isEmpty()) {
            }
            str2 = body;
            if (TextUtils.isEmpty(str)) {
            }
            body = (String) remoteMessage.get(DeepLinkKey.NOTIFICATION_ID);
            title = (String) remoteMessage.get("channelId");
            str32 = (String) remoteMessage.get("categoryId");
            str4 = (String) remoteMessage.get(DeepLinkKey.LIVE_CHANNEL_ID);
            str5 = (String) remoteMessage.get("assetId");
            str6 = (String) remoteMessage.get(DeepLinkKey.IMG_URL_ID);
            if (body != null) {
            }
            intent = null;
            sendNotification(context, str, str2, str6, intent, onFinish);
        }
        body = (String) remoteMessage.get("message");
        str2 = body;
        if (TextUtils.isEmpty(str)) {
        }
        body = (String) remoteMessage.get(DeepLinkKey.NOTIFICATION_ID);
        title = (String) remoteMessage.get("channelId");
        str32 = (String) remoteMessage.get("categoryId");
        str4 = (String) remoteMessage.get(DeepLinkKey.LIVE_CHANNEL_ID);
        str5 = (String) remoteMessage.get("assetId");
        str6 = (String) remoteMessage.get(DeepLinkKey.IMG_URL_ID);
        if (body != null) {
        }
        intent = null;
        sendNotification(context, str, str2, str6, intent, onFinish);
    }

    private void sendNotification(Context context, String str, String str2, String str3, Intent intent, OnFinish onFinish) {
        Context context2 = context;
        OnFinish onFinish2 = onFinish;
        Intent intent2 = intent != null ? (Intent) intent.clone() : null;
        intent2.setAction("android.intent.action.VIEW");
        intent2.addFlags(67108864);
        intent2.putExtra("FCM_APP_OPEN", true);
        PendingIntent activity = PendingIntent.getActivity(context2, 0, intent2, 1073741824);
        String string = context2.getString(R.string.default_notification_channel_id);
        NotificationManager notificationManager = (NotificationManager) context2.getSystemService("notification");
        XumoNotificationRemoteViewFactory xumoNotificationRemoteViewFactory = new XumoNotificationRemoteViewFactory(context2);
        xumoNotificationRemoteViewFactory.setTitleText(str);
        xumoNotificationRemoteViewFactory.setBodyText(str2);
        Builder contentIntent = new Builder(context2, string).setSmallIcon(R.drawable.ic_xumo_android_icon).setAutoCancel(true).setSound(System.DEFAULT_NOTIFICATION_URI).setVibrate(new long[]{0, 500}).setContentIntent(activity);
        if (VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel(string, context2.getString(R.string.app_name), 3));
        }
        if (str3 == null || str3.isEmpty()) {
            contentIntent.setCustomContentView(xumoNotificationRemoteViewFactory.getCustomView());
            contentIntent.setCustomBigContentView(xumoNotificationRemoteViewFactory.getBigCustomView());
            notificationManager.notify(0, contentIntent.build());
            if (onFinish2 != null) {
                onFinish.finished();
                return;
            }
            return;
        }
        try {
            Volley.newRequestQueue(context).add(new ImageRequest(str3, new -$$Lambda$XumoFirebaseMessagingService$JySAxYX6YZYmUYaTiDnGHYAClug(xumoNotificationRemoteViewFactory, contentIntent, notificationManager, onFinish2), 0, 0, null, null, new -$$Lambda$XumoFirebaseMessagingService$al4oA8SKgLJrkB5ZEGXH1_B2cHI(contentIntent, xumoNotificationRemoteViewFactory, notificationManager, onFinish2)));
            if (onFinish2 == null) {
                return;
            }
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
            if (onFinish2 == null) {
                return;
            }
        } catch (Throwable th) {
            if (onFinish2 != null) {
                onFinish.finished();
            }
        }
        onFinish.finished();
    }

    static /* synthetic */ void lambda$sendNotification$0(XumoNotificationRemoteViewFactory xumoNotificationRemoteViewFactory, Builder builder, NotificationManager notificationManager, OnFinish onFinish, Bitmap bitmap) {
        xumoNotificationRemoteViewFactory.setLargeImage(bitmap);
        builder.setCustomContentView(xumoNotificationRemoteViewFactory.getCustomView());
        builder.setCustomBigContentView(xumoNotificationRemoteViewFactory.getBigCustomView());
        builder.setLargeIcon(bitmap);
        notificationManager.notify(null, builder.build());
        if (onFinish != null) {
            onFinish.finished();
        }
    }

    static /* synthetic */ void lambda$sendNotification$1(Builder builder, XumoNotificationRemoteViewFactory xumoNotificationRemoteViewFactory, NotificationManager notificationManager, OnFinish onFinish, VolleyError volleyError) {
        builder.setCustomContentView(xumoNotificationRemoteViewFactory.getCustomView());
        builder.setCustomBigContentView(xumoNotificationRemoteViewFactory.getBigCustomView());
        notificationManager.notify(null, builder.build());
        if (onFinish != null) {
            onFinish.finished();
        }
    }

    public void onNewToken(String str) {
        super.onNewToken(str);
        UserPreferences.getInstance().setFcmToken(str);
    }
}
