package com.xumo.xumo.service;

import android.app.NotificationManager;
import android.graphics.Bitmap;
import androidx.core.app.NotificationCompat.Builder;
import com.android.volley.Response.Listener;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$XumoFirebaseMessagingService$JySAxYX6YZYmUYaTiDnGHYAClug implements Listener {
    private final /* synthetic */ XumoNotificationRemoteViewFactory f$0;
    private final /* synthetic */ Builder f$1;
    private final /* synthetic */ NotificationManager f$2;
    private final /* synthetic */ OnFinish f$3;

    public /* synthetic */ -$$Lambda$XumoFirebaseMessagingService$JySAxYX6YZYmUYaTiDnGHYAClug(XumoNotificationRemoteViewFactory xumoNotificationRemoteViewFactory, Builder builder, NotificationManager notificationManager, OnFinish onFinish) {
        this.f$0 = xumoNotificationRemoteViewFactory;
        this.f$1 = builder;
        this.f$2 = notificationManager;
        this.f$3 = onFinish;
    }

    public final void onResponse(Object obj) {
        XumoFirebaseMessagingService.lambda$sendNotification$0(this.f$0, this.f$1, this.f$2, this.f$3, (Bitmap) obj);
    }
}
