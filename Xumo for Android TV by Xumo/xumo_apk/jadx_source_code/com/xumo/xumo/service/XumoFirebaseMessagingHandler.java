package com.xumo.xumo.service;

import android.content.Context;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.RemoteMessage.Builder;
import com.xumo.xumo.repository.UserPreferences;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class XumoFirebaseMessagingHandler {
    private XumoFirebaseMessagingService xumoFirebaseMessagingService;

    interface OnFinish {
        void finished();
    }

    XumoFirebaseMessagingHandler() {
    }

    void handleFirebaseMessaging(Context context, Map<String, Object> map, OnFinish onFinish) {
        if (this.xumoFirebaseMessagingService == null) {
            this.xumoFirebaseMessagingService = new XumoFirebaseMessagingService();
        }
        String fcmToken = UserPreferences.getInstance().getFcmToken();
        if (fcmToken != null) {
            if (!fcmToken.isEmpty()) {
                processFirebaseMessage(context, map, fcmToken, onFinish);
                return;
            }
        }
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new -$$Lambda$XumoFirebaseMessagingHandler$em-vQsoD5j4BpycyKfCKAndkrrU(this, context, map, onFinish));
    }

    public static /* synthetic */ void lambda$handleFirebaseMessaging$0(XumoFirebaseMessagingHandler xumoFirebaseMessagingHandler, Context context, Map map, OnFinish onFinish, Task task) {
        if (task.isSuccessful() && task.getResult() != null) {
            task = ((InstanceIdResult) task.getResult()).getToken();
            UserPreferences.getInstance().setFcmToken(task);
            xumoFirebaseMessagingHandler.processFirebaseMessage(context, map, task, onFinish);
        }
    }

    void processFirebaseMessage(Context context, Map<String, Object> map, String str, OnFinish onFinish) {
        Set<String> keySet = map.keySet();
        Map hashMap = new HashMap();
        Builder builder = new Builder(str);
        for (String str2 : keySet) {
            Object obj = map.get(str2);
            if (obj != null) {
                if (str2.equals("gcm.notification.title") && (obj instanceof String)) {
                    hashMap.put("title", (String) obj);
                } else if (str2.equals("gcm.notification.body") && (obj instanceof String)) {
                    hashMap.put("message", (String) obj);
                } else if (str2.contains("google.message_id") && (obj instanceof String)) {
                    builder.setMessageId((String) obj);
                } else if (obj instanceof String) {
                    hashMap.put(str2, (String) obj);
                } else {
                    hashMap.put(str2, String.valueOf(obj));
                }
            }
        }
        builder.setData(hashMap);
        this.xumoFirebaseMessagingService.processMessage(context, builder.build(), onFinish);
    }
}
