package com.xumo.xumo.service;

import android.app.NotificationManager;
import androidx.core.app.NotificationCompat.Builder;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$XumoFirebaseMessagingService$al4oA8SKgLJrkB5ZEGXH1_B2cHI implements ErrorListener {
    private final /* synthetic */ Builder f$0;
    private final /* synthetic */ XumoNotificationRemoteViewFactory f$1;
    private final /* synthetic */ NotificationManager f$2;
    private final /* synthetic */ OnFinish f$3;

    public /* synthetic */ -$$Lambda$XumoFirebaseMessagingService$al4oA8SKgLJrkB5ZEGXH1_B2cHI(Builder builder, XumoNotificationRemoteViewFactory xumoNotificationRemoteViewFactory, NotificationManager notificationManager, OnFinish onFinish) {
        this.f$0 = builder;
        this.f$1 = xumoNotificationRemoteViewFactory;
        this.f$2 = notificationManager;
        this.f$3 = onFinish;
    }

    public final void onErrorResponse(VolleyError volleyError) {
        XumoFirebaseMessagingService.lambda$sendNotification$1(this.f$0, this.f$1, this.f$2, this.f$3, volleyError);
    }
}
