package com.google.android.libraries.cast.companionlibrary.cast.reconnection;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.SystemClock;
import com.google.android.libraries.cast.companionlibrary.cast.BaseCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;
import com.google.android.libraries.cast.companionlibrary.utils.Utils;
import java.util.Timer;
import java.util.TimerTask;

public class ReconnectionService extends Service {
    private static final long EPSILON_MS = 500;
    private static final int RECONNECTION_ATTEMPT_PERIOD_S = 15;
    private static final String TAG = LogUtils.makeLogTag(ReconnectionService.class);
    private VideoCastManager mCastManager;
    private Timer mEndTimer;
    private TimerTask mEndTimerTask;
    private BroadcastReceiver mScreenOnOffBroadcastReceiver;
    private BroadcastReceiver mWifiBroadcastReceiver;
    private boolean mWifiConnectivity = true;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        LogUtils.LOGD(TAG, "onStartCommand() is called");
        setUpEndTimer();
        return 1;
    }

    public void onCreate() {
        LogUtils.LOGD(TAG, "onCreate() is called");
        this.mCastManager = VideoCastManager.getInstance();
        if (!(this.mCastManager.isConnected() || this.mCastManager.isConnecting())) {
            this.mCastManager.reconnectSessionIfPossible();
        }
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        this.mScreenOnOffBroadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                context = ReconnectionService.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ScreenOnOffBroadcastReceiver: onReceive(): ");
                stringBuilder.append(intent.getAction());
                LogUtils.LOGD(context, stringBuilder.toString());
                if (ReconnectionService.this.getMediaRemainingTime() < ReconnectionService.EPSILON_MS) {
                    ReconnectionService.this.handleTermination();
                }
            }
        };
        registerReceiver(this.mScreenOnOffBroadcastReceiver, intentFilter);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        this.mWifiBroadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.net.wifi.STATE_CHANGE")) {
                    intent = ((NetworkInfo) intent.getParcelableExtra("networkInfo")).isConnected();
                    ReconnectionService.this.onWifiConnectivityChanged(intent, intent != null ? Utils.getWifiSsid(context) : null);
                }
            }
        };
        registerReceiver(this.mWifiBroadcastReceiver, intentFilter);
        super.onCreate();
    }

    public void onWifiConnectivityChanged(boolean z, String str) {
        String str2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WIFI connectivity changed to ");
        stringBuilder.append(z ? "enabled" : "disabled");
        LogUtils.LOGD(str2, stringBuilder.toString());
        if (!z || this.mWifiConnectivity) {
            this.mWifiConnectivity = z;
            return;
        }
        this.mWifiConnectivity = true;
        if (this.mCastManager.isFeatureEnabled(8)) {
            this.mCastManager.startCastDiscovery();
            this.mCastManager.reconnectSessionIfPossible(15, str);
        }
    }

    public void onDestroy() {
        LogUtils.LOGD(TAG, "onDestroy()");
        if (this.mScreenOnOffBroadcastReceiver != null) {
            unregisterReceiver(this.mScreenOnOffBroadcastReceiver);
            this.mScreenOnOffBroadcastReceiver = null;
        }
        if (this.mWifiBroadcastReceiver != null) {
            unregisterReceiver(this.mWifiBroadcastReceiver);
            this.mWifiBroadcastReceiver = null;
        }
        clearEndTimer();
        super.onDestroy();
    }

    private void setUpEndTimer() {
        LogUtils.LOGD(TAG, "setUpEndTimer(): setting up a timer for the end of current media");
        long mediaRemainingTime = getMediaRemainingTime();
        if (mediaRemainingTime <= 0) {
            stopSelf();
            return;
        }
        clearEndTimer();
        this.mEndTimer = new Timer();
        this.mEndTimerTask = new TimerTask() {
            public void run() {
                LogUtils.LOGD(ReconnectionService.TAG, "setUpEndTimer(): stopping ReconnectionService since reached the end of allotted time");
                ReconnectionService.this.handleTermination();
            }
        };
        this.mEndTimer.schedule(this.mEndTimerTask, mediaRemainingTime);
    }

    private void clearEndTimer() {
        if (this.mEndTimerTask != null) {
            this.mEndTimerTask.cancel();
            this.mEndTimerTask = null;
        }
        if (this.mEndTimer != null) {
            this.mEndTimer.cancel();
            this.mEndTimer = null;
        }
    }

    private long getMediaRemainingTime() {
        return this.mCastManager.getPreferenceAccessor().getLongFromPreference(BaseCastManager.PREFS_KEY_MEDIA_END, 0) - SystemClock.elapsedRealtime();
    }

    private void handleTermination() {
        if (this.mCastManager.isConnected()) {
            long j = 0;
            try {
                if (!this.mCastManager.isRemoteStreamLive()) {
                    j = this.mCastManager.getMediaTimeRemaining();
                }
            } catch (Throwable e) {
                LogUtils.LOGE(TAG, "Failed to calculate the time left for media due to lack of connectivity", e);
            }
            if (j < EPSILON_MS) {
                stopSelf();
                return;
            }
            this.mCastManager.getPreferenceAccessor().saveLongToPreference(BaseCastManager.PREFS_KEY_MEDIA_END, Long.valueOf(j + SystemClock.elapsedRealtime()));
            LogUtils.LOGD(TAG, "handleTermination(): resetting the timer");
            setUpEndTimer();
            return;
        }
        this.mCastManager.clearMediaSession();
        this.mCastManager.clearPersistedConnectionInfo(0);
        stopSelf();
    }
}
