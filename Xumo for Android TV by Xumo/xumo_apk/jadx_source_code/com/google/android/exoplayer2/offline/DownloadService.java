package com.google.android.exoplayer2.offline;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.google.android.exoplayer2.offline.DownloadManager.Listener;
import com.google.android.exoplayer2.offline.DownloadManager.TaskState;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.RequirementsWatcher;
import com.google.android.exoplayer2.scheduler.Scheduler;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import java.util.HashMap;

public abstract class DownloadService extends Service {
    public static final String ACTION_ADD = "com.google.android.exoplayer.downloadService.action.ADD";
    public static final String ACTION_INIT = "com.google.android.exoplayer.downloadService.action.INIT";
    public static final String ACTION_RELOAD_REQUIREMENTS = "com.google.android.exoplayer.downloadService.action.RELOAD_REQUIREMENTS";
    private static final String ACTION_RESTART = "com.google.android.exoplayer.downloadService.action.RESTART";
    private static final boolean DEBUG = false;
    public static final long DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL = 1000;
    private static final Requirements DEFAULT_REQUIREMENTS = new Requirements(1, false, false);
    public static final int FOREGROUND_NOTIFICATION_ID_NONE = 0;
    public static final String KEY_DOWNLOAD_ACTION = "download_action";
    public static final String KEY_FOREGROUND = "foreground";
    private static final String TAG = "DownloadService";
    private static final HashMap<Class<? extends DownloadService>, RequirementsHelper> requirementsHelpers = new HashMap();
    @Nullable
    private final String channelId;
    @StringRes
    private final int channelName;
    private DownloadManager downloadManager;
    private DownloadManagerListener downloadManagerListener;
    @Nullable
    private final ForegroundNotificationUpdater foregroundNotificationUpdater;
    private int lastStartId;
    private boolean startedInForeground;
    private boolean taskRemoved;

    private final class ForegroundNotificationUpdater implements Runnable {
        private final Handler handler = new Handler(Looper.getMainLooper());
        private boolean notificationDisplayed;
        private final int notificationId;
        private boolean periodicUpdatesStarted;
        private final long updateInterval;

        public ForegroundNotificationUpdater(int i, long j) {
            this.notificationId = i;
            this.updateInterval = j;
        }

        public void startPeriodicUpdates() {
            this.periodicUpdatesStarted = true;
            update();
        }

        public void stopPeriodicUpdates() {
            this.periodicUpdatesStarted = false;
            this.handler.removeCallbacks(this);
        }

        public void update() {
            DownloadService.this.startForeground(this.notificationId, DownloadService.this.getForegroundNotification(DownloadService.this.downloadManager.getAllTaskStates()));
            this.notificationDisplayed = true;
            if (this.periodicUpdatesStarted) {
                this.handler.removeCallbacks(this);
                this.handler.postDelayed(this, this.updateInterval);
            }
        }

        public void showNotificationIfNotAlready() {
            if (!this.notificationDisplayed) {
                update();
            }
        }

        public void run() {
            update();
        }
    }

    private final class DownloadManagerListener implements Listener {
        private DownloadManagerListener() {
        }

        public void onInitialized(DownloadManager downloadManager) {
            DownloadService.this.maybeStartWatchingRequirements(DownloadService.this.getRequirements());
        }

        public void onTaskStateChanged(DownloadManager downloadManager, TaskState taskState) {
            DownloadService.this.onTaskStateChanged(taskState);
            if (DownloadService.this.foregroundNotificationUpdater == null) {
                return;
            }
            if (taskState.state == 1) {
                DownloadService.this.foregroundNotificationUpdater.startPeriodicUpdates();
            } else {
                DownloadService.this.foregroundNotificationUpdater.update();
            }
        }

        public final void onIdle(DownloadManager downloadManager) {
            DownloadService.this.stop();
        }
    }

    private static final class RequirementsHelper implements RequirementsWatcher.Listener {
        private final Context context;
        private final Requirements requirements;
        private final RequirementsWatcher requirementsWatcher;
        @Nullable
        private final Scheduler scheduler;
        private final Class<? extends DownloadService> serviceClass;

        private RequirementsHelper(Context context, Requirements requirements, @Nullable Scheduler scheduler, Class<? extends DownloadService> cls) {
            this.context = context;
            this.requirements = requirements;
            this.scheduler = scheduler;
            this.serviceClass = cls;
            this.requirementsWatcher = new RequirementsWatcher(context, this, requirements);
        }

        public void start() {
            this.requirementsWatcher.start();
        }

        public void stop() {
            this.requirementsWatcher.stop();
            if (this.scheduler != null) {
                this.scheduler.cancel();
            }
        }

        public void requirementsMet(com.google.android.exoplayer2.scheduler.RequirementsWatcher r1) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = this;
            r0.notifyService();	 Catch:{ Exception -> 0x000d }
            r1 = r0.scheduler;
            if (r1 == 0) goto L_0x000c;
        L_0x0007:
            r1 = r0.scheduler;
            r1.cancel();
        L_0x000c:
            return;
        L_0x000d:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DownloadService.RequirementsHelper.requirementsMet(com.google.android.exoplayer2.scheduler.RequirementsWatcher):void");
        }

        public void requirementsNotMet(com.google.android.exoplayer2.scheduler.RequirementsWatcher r4) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r3 = this;
            r3.notifyService();	 Catch:{ Exception -> 0x0003 }
        L_0x0003:
            r4 = r3.scheduler;
            if (r4 == 0) goto L_0x0020;
        L_0x0007:
            r4 = r3.context;
            r4 = r4.getPackageName();
            r0 = r3.scheduler;
            r1 = r3.requirements;
            r2 = "com.google.android.exoplayer.downloadService.action.RESTART";
            r4 = r0.schedule(r1, r4, r2);
            if (r4 != 0) goto L_0x0020;
        L_0x0019:
            r4 = "DownloadService";
            r0 = "Scheduling downloads failed.";
            com.google.android.exoplayer2.util.Log.e(r4, r0);
        L_0x0020:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DownloadService.RequirementsHelper.requirementsNotMet(com.google.android.exoplayer2.scheduler.RequirementsWatcher):void");
        }

        private void notifyService() throws Exception {
            try {
                this.context.startService(DownloadService.getIntent(this.context, this.serviceClass, DownloadService.ACTION_INIT));
            } catch (Throwable e) {
                throw new Exception(e);
            }
        }
    }

    private void logd(String str) {
    }

    protected abstract DownloadManager getDownloadManager();

    @Nullable
    protected abstract Scheduler getScheduler();

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void onTaskStateChanged(TaskState taskState) {
    }

    protected DownloadService(int i) {
        this(i, 1000);
    }

    protected DownloadService(int i, long j) {
        this(i, j, null, 0);
    }

    protected DownloadService(int i, long j, @Nullable String str, @StringRes int i2) {
        if (i == 0) {
            i = 0;
        } else {
            i = new ForegroundNotificationUpdater(i, j);
        }
        this.foregroundNotificationUpdater = i;
        this.channelId = str;
        this.channelName = i2;
    }

    public static Intent buildAddActionIntent(Context context, Class<? extends DownloadService> cls, DownloadAction downloadAction, boolean z) {
        return getIntent(context, cls, ACTION_ADD).putExtra(KEY_DOWNLOAD_ACTION, downloadAction.toByteArray()).putExtra(KEY_FOREGROUND, z);
    }

    public static void startWithAction(Context context, Class<? extends DownloadService> cls, DownloadAction downloadAction, boolean z) {
        cls = buildAddActionIntent(context, cls, downloadAction, z);
        if (z) {
            Util.startForegroundService(context, cls);
        } else {
            context.startService(cls);
        }
    }

    public static void start(Context context, Class<? extends DownloadService> cls) {
        context.startService(getIntent(context, cls, ACTION_INIT));
    }

    public static void startForeground(Context context, Class<? extends DownloadService> cls) {
        Util.startForegroundService(context, getIntent(context, cls, ACTION_INIT).putExtra(KEY_FOREGROUND, true));
    }

    public void onCreate() {
        logd("onCreate");
        if (this.channelId != null) {
            NotificationUtil.createNotificationChannel(this, this.channelId, this.channelName, 2);
        }
        this.downloadManager = getDownloadManager();
        this.downloadManagerListener = new DownloadManagerListener();
        this.downloadManager.addListener(this.downloadManagerListener);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        String action;
        byte[] byteArrayExtra;
        this.lastStartId = i2;
        i = 0;
        this.taskRemoved = false;
        if (intent != null) {
            int i3;
            action = intent.getAction();
            boolean z = this.startedInForeground;
            if (!intent.getBooleanExtra(KEY_FOREGROUND, false)) {
                if (!ACTION_RESTART.equals(action)) {
                    i3 = 0;
                    this.startedInForeground = z | i3;
                }
            }
            i3 = 1;
            this.startedInForeground = z | i3;
        } else {
            action = null;
        }
        if (action == null) {
            action = ACTION_INIT;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onStartCommand action: ");
        stringBuilder.append(action);
        stringBuilder.append(" startId: ");
        stringBuilder.append(i2);
        logd(stringBuilder.toString());
        int hashCode = action.hashCode();
        if (hashCode != -871181424) {
            if (hashCode != -608867945) {
                if (hashCode != -382886238) {
                    if (hashCode == 1015676687) {
                        if (action.equals(ACTION_INIT)) {
                            switch (i) {
                                case 0:
                                case 1:
                                    break;
                                case 2:
                                    byteArrayExtra = intent.getByteArrayExtra(KEY_DOWNLOAD_ACTION);
                                    if (byteArrayExtra != null) {
                                        try {
                                            this.downloadManager.handleAction(byteArrayExtra);
                                            break;
                                        } catch (Intent intent2) {
                                            Log.e(TAG, "Failed to handle ADD action", intent2);
                                            break;
                                        }
                                    }
                                    Log.e(TAG, "Ignoring ADD action with no action data");
                                    break;
                                case 3:
                                    stopWatchingRequirements();
                                    break;
                                default:
                                    intent2 = TAG;
                                    i = new StringBuilder();
                                    i.append("Ignoring unrecognized action: ");
                                    i.append(action);
                                    Log.e(intent2, i.toString());
                                    break;
                            }
                            intent2 = getRequirements();
                            if (intent2.checkRequirements(this) != 0) {
                                this.downloadManager.startDownloads();
                            } else {
                                this.downloadManager.stopDownloads();
                            }
                            maybeStartWatchingRequirements(intent2);
                            if (this.downloadManager.isIdle() != null) {
                                stop();
                            }
                            return 1;
                        }
                    }
                } else if (action.equals(ACTION_ADD) != 0) {
                    i = 2;
                    switch (i) {
                        case 0:
                        case 1:
                            break;
                        case 2:
                            byteArrayExtra = intent2.getByteArrayExtra(KEY_DOWNLOAD_ACTION);
                            if (byteArrayExtra != null) {
                                Log.e(TAG, "Ignoring ADD action with no action data");
                                break;
                            }
                            this.downloadManager.handleAction(byteArrayExtra);
                            break;
                        case 3:
                            stopWatchingRequirements();
                            break;
                        default:
                            intent2 = TAG;
                            i = new StringBuilder();
                            i.append("Ignoring unrecognized action: ");
                            i.append(action);
                            Log.e(intent2, i.toString());
                            break;
                    }
                    intent2 = getRequirements();
                    if (intent2.checkRequirements(this) != 0) {
                        this.downloadManager.stopDownloads();
                    } else {
                        this.downloadManager.startDownloads();
                    }
                    maybeStartWatchingRequirements(intent2);
                    if (this.downloadManager.isIdle() != null) {
                        stop();
                    }
                    return 1;
                }
            } else if (action.equals(ACTION_RELOAD_REQUIREMENTS) != 0) {
                i = 3;
                switch (i) {
                    case 0:
                    case 1:
                        break;
                    case 2:
                        byteArrayExtra = intent2.getByteArrayExtra(KEY_DOWNLOAD_ACTION);
                        if (byteArrayExtra != null) {
                            this.downloadManager.handleAction(byteArrayExtra);
                            break;
                        }
                        Log.e(TAG, "Ignoring ADD action with no action data");
                        break;
                    case 3:
                        stopWatchingRequirements();
                        break;
                    default:
                        intent2 = TAG;
                        i = new StringBuilder();
                        i.append("Ignoring unrecognized action: ");
                        i.append(action);
                        Log.e(intent2, i.toString());
                        break;
                }
                intent2 = getRequirements();
                if (intent2.checkRequirements(this) != 0) {
                    this.downloadManager.startDownloads();
                } else {
                    this.downloadManager.stopDownloads();
                }
                maybeStartWatchingRequirements(intent2);
                if (this.downloadManager.isIdle() != null) {
                    stop();
                }
                return 1;
            }
        } else if (action.equals(ACTION_RESTART) != 0) {
            i = 1;
            switch (i) {
                case 0:
                case 1:
                    break;
                case 2:
                    byteArrayExtra = intent2.getByteArrayExtra(KEY_DOWNLOAD_ACTION);
                    if (byteArrayExtra != null) {
                        Log.e(TAG, "Ignoring ADD action with no action data");
                        break;
                    }
                    this.downloadManager.handleAction(byteArrayExtra);
                    break;
                case 3:
                    stopWatchingRequirements();
                    break;
                default:
                    intent2 = TAG;
                    i = new StringBuilder();
                    i.append("Ignoring unrecognized action: ");
                    i.append(action);
                    Log.e(intent2, i.toString());
                    break;
            }
            intent2 = getRequirements();
            if (intent2.checkRequirements(this) != 0) {
                this.downloadManager.stopDownloads();
            } else {
                this.downloadManager.startDownloads();
            }
            maybeStartWatchingRequirements(intent2);
            if (this.downloadManager.isIdle() != null) {
                stop();
            }
            return 1;
        }
        i = -1;
        switch (i) {
            case 0:
            case 1:
                break;
            case 2:
                byteArrayExtra = intent2.getByteArrayExtra(KEY_DOWNLOAD_ACTION);
                if (byteArrayExtra != null) {
                    this.downloadManager.handleAction(byteArrayExtra);
                    break;
                }
                Log.e(TAG, "Ignoring ADD action with no action data");
                break;
            case 3:
                stopWatchingRequirements();
                break;
            default:
                intent2 = TAG;
                i = new StringBuilder();
                i.append("Ignoring unrecognized action: ");
                i.append(action);
                Log.e(intent2, i.toString());
                break;
        }
        intent2 = getRequirements();
        if (intent2.checkRequirements(this) != 0) {
            this.downloadManager.startDownloads();
        } else {
            this.downloadManager.stopDownloads();
        }
        maybeStartWatchingRequirements(intent2);
        if (this.downloadManager.isIdle() != null) {
            stop();
        }
        return 1;
    }

    public void onTaskRemoved(Intent intent) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onTaskRemoved rootIntent: ");
        stringBuilder.append(intent);
        logd(stringBuilder.toString());
        this.taskRemoved = true;
    }

    public void onDestroy() {
        logd("onDestroy");
        if (this.foregroundNotificationUpdater != null) {
            this.foregroundNotificationUpdater.stopPeriodicUpdates();
        }
        this.downloadManager.removeListener(this.downloadManagerListener);
        maybeStopWatchingRequirements();
    }

    protected Requirements getRequirements() {
        return DEFAULT_REQUIREMENTS;
    }

    protected Notification getForegroundNotification(TaskState[] taskStateArr) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getName());
        stringBuilder.append(" is started in the foreground but getForegroundNotification() is not implemented.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    private void maybeStartWatchingRequirements(Requirements requirements) {
        if (this.downloadManager.getDownloadCount() != 0) {
            Class cls = getClass();
            if (((RequirementsHelper) requirementsHelpers.get(cls)) == null) {
                RequirementsHelper requirementsHelper = new RequirementsHelper(this, requirements, getScheduler(), cls);
                requirementsHelpers.put(cls, requirementsHelper);
                requirementsHelper.start();
                logd("started watching requirements");
            }
        }
    }

    private void maybeStopWatchingRequirements() {
        if (this.downloadManager.getDownloadCount() <= 0) {
            stopWatchingRequirements();
        }
    }

    private void stopWatchingRequirements() {
        RequirementsHelper requirementsHelper = (RequirementsHelper) requirementsHelpers.remove(getClass());
        if (requirementsHelper != null) {
            requirementsHelper.stop();
            logd("stopped watching requirements");
        }
    }

    private void stop() {
        if (this.foregroundNotificationUpdater != null) {
            this.foregroundNotificationUpdater.stopPeriodicUpdates();
            if (this.startedInForeground && Util.SDK_INT >= 26) {
                this.foregroundNotificationUpdater.showNotificationIfNotAlready();
            }
        }
        if (Util.SDK_INT >= 28 || !this.taskRemoved) {
            boolean stopSelfResult = stopSelfResult(this.lastStartId);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("stopSelf(");
            stringBuilder.append(this.lastStartId);
            stringBuilder.append(") result: ");
            stringBuilder.append(stopSelfResult);
            logd(stringBuilder.toString());
            return;
        }
        stopSelf();
        logd("stopSelf()");
    }

    private static Intent getIntent(Context context, Class<? extends DownloadService> cls, String str) {
        return new Intent(context, cls).setAction(str);
    }
}
