package com.google.android.exoplayer2.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat.BigTextStyle;
import androidx.core.app.NotificationCompat.Builder;
import com.google.android.exoplayer2.offline.DownloadManager.TaskState;

public final class DownloadNotificationUtil {
    @StringRes
    private static final int NULL_STRING_ID = 0;

    private DownloadNotificationUtil() {
    }

    public static Notification buildProgressNotification(Context context, @DrawableRes int i, String str, @Nullable PendingIntent pendingIntent, @Nullable String str2, TaskState[] taskStateArr) {
        int i2;
        int i3;
        int i4;
        Builder newNotificationBuilder;
        boolean z;
        Object obj = null;
        Object obj2 = null;
        float f = 0.0f;
        int i5 = 0;
        int i6 = 0;
        Object obj3 = 1;
        for (TaskState taskState : taskStateArr) {
            if (taskState.state == 1 || taskState.state == 2) {
                if (taskState.action.isRemoveAction) {
                    obj2 = 1;
                } else {
                    if (taskState.downloadPercentage != -1.0f) {
                        f += taskState.downloadPercentage;
                        obj3 = null;
                    }
                    i6++;
                    i5 = (taskState.downloadedBytes > 0 ? 1 : 0) | i5;
                    obj = 1;
                }
            }
        }
        if (obj != null) {
            i3 = R.string.exo_download_downloading;
        } else if (obj2 != null) {
            i3 = R.string.exo_download_removing;
        } else {
            i4 = 0;
            newNotificationBuilder = newNotificationBuilder(context, i, str, pendingIntent, str2, i4);
            if (obj == null) {
                i2 = (int) (f / ((float) i6));
                if (obj3 != null || i5 == 0) {
                    z = false;
                    newNotificationBuilder.setProgress(100, i2, z);
                    newNotificationBuilder.setOngoing(true);
                    newNotificationBuilder.setShowWhen(false);
                    return newNotificationBuilder.build();
                }
            } else {
                i2 = 0;
            }
            z = true;
            newNotificationBuilder.setProgress(100, i2, z);
            newNotificationBuilder.setOngoing(true);
            newNotificationBuilder.setShowWhen(false);
            return newNotificationBuilder.build();
        }
        i4 = i3;
        newNotificationBuilder = newNotificationBuilder(context, i, str, pendingIntent, str2, i4);
        if (obj == null) {
            i2 = 0;
        } else {
            i2 = (int) (f / ((float) i6));
            if (obj3 != null) {
            }
            z = false;
            newNotificationBuilder.setProgress(100, i2, z);
            newNotificationBuilder.setOngoing(true);
            newNotificationBuilder.setShowWhen(false);
            return newNotificationBuilder.build();
        }
        z = true;
        newNotificationBuilder.setProgress(100, i2, z);
        newNotificationBuilder.setOngoing(true);
        newNotificationBuilder.setShowWhen(false);
        return newNotificationBuilder.build();
    }

    public static Notification buildDownloadCompletedNotification(Context context, @DrawableRes int i, String str, @Nullable PendingIntent pendingIntent, @Nullable String str2) {
        return newNotificationBuilder(context, i, str, pendingIntent, str2, R.string.exo_download_completed).build();
    }

    public static Notification buildDownloadFailedNotification(Context context, @DrawableRes int i, String str, @Nullable PendingIntent pendingIntent, @Nullable String str2) {
        return newNotificationBuilder(context, i, str, pendingIntent, str2, R.string.exo_download_failed).build();
    }

    private static Builder newNotificationBuilder(Context context, @DrawableRes int i, String str, @Nullable PendingIntent pendingIntent, @Nullable String str2, @StringRes int i2) {
        i = new Builder(context, str).setSmallIcon(i);
        if (i2 != 0) {
            i.setContentTitle(context.getResources().getString(i2));
        }
        if (pendingIntent != null) {
            i.setContentIntent(pendingIntent);
        }
        if (str2 != null) {
            i.setStyle(new BigTextStyle().bigText(str2));
        }
        return i;
    }
}
