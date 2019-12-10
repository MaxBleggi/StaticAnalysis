package com.xumo.xumo.util;

import android.app.Notification;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadManager.TaskState;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.scheduler.PlatformScheduler;
import com.google.android.exoplayer2.ui.DownloadNotificationUtil;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import com.xumo.xumo.application.XumoApplication;
import com.xumo.xumo.tv.R;

public class DemoDownloadService extends DownloadService {
    private static final String CHANNEL_ID = "download_channel";
    private static final int FOREGROUND_NOTIFICATION_ID = 1;
    private static final int JOB_ID = 1;

    public DemoDownloadService() {
        super(1, 1000, CHANNEL_ID, R.string.exo_download_notification_channel_name);
    }

    protected DownloadManager getDownloadManager() {
        return ((XumoApplication) getApplication()).getDownloadManager();
    }

    protected PlatformScheduler getScheduler() {
        return Util.SDK_INT >= 21 ? new PlatformScheduler(this, 1) : null;
    }

    protected Notification getForegroundNotification(TaskState[] taskStateArr) {
        return DownloadNotificationUtil.buildProgressNotification(this, R.drawable.exo_controls_play, CHANNEL_ID, null, null, taskStateArr);
    }

    protected void onTaskStateChanged(TaskState taskState) {
        if (!taskState.action.isRemoveAction) {
            Notification notification = null;
            if (taskState.state == 2) {
                notification = DownloadNotificationUtil.buildDownloadCompletedNotification(this, R.drawable.exo_controls_play, CHANNEL_ID, null, Util.fromUtf8Bytes(taskState.action.data));
            } else if (taskState.state == 4) {
                notification = DownloadNotificationUtil.buildDownloadFailedNotification(this, R.drawable.exo_controls_play, CHANNEL_ID, null, Util.fromUtf8Bytes(taskState.action.data));
            }
            NotificationUtil.setNotification(this, taskState.taskId + 2, notification);
        }
    }
}
