package com.xumo.xumo.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import com.xumo.xumo.tv.R;

public class XumoNotificationRemoteViewFactory {
    private RemoteViews mCustomView;
    private RemoteViews mLargeCustomView;

    public XumoNotificationRemoteViewFactory(Context context) {
        this.mCustomView = new RemoteViews(context.getPackageName(), R.layout.notification_remoteview);
        this.mLargeCustomView = new RemoteViews(context.getPackageName(), R.layout.notification_large_remoteview);
    }

    public RemoteViews getCustomView() {
        return this.mCustomView;
    }

    public RemoteViews getBigCustomView() {
        return this.mLargeCustomView;
    }

    public void setTitleText(String str) {
        if (str != null && !str.isEmpty()) {
            this.mCustomView.setTextViewText(R.id.notificationTitle, new StringBuilder(str));
            this.mLargeCustomView.setTextViewText(R.id.notificationTitleBig, new StringBuilder(str));
        }
    }

    public void setBodyText(String str) {
        if (str != null && !str.isEmpty()) {
            this.mCustomView.setTextViewText(R.id.notificationBody, new StringBuilder(str));
            this.mLargeCustomView.setTextViewText(R.id.notificationBodyBig, new StringBuilder(str));
        }
    }

    public void setLargeImage(Bitmap bitmap) {
        this.mCustomView.setImageViewBitmap(R.id.notificationLargeImage, bitmap);
        this.mLargeCustomView.setImageViewBitmap(R.id.notificationLargeImageBig, bitmap);
    }
}
