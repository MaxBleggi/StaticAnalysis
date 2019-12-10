package com.xumo.xumo.player;

import com.xumo.xumo.player.WatchdogTimerTask.OnWatchdogEventListener;
import java.util.Timer;

public class WatchdogTimer extends Timer implements OnWatchdogEventListener {
    public static final long STOP_TIME_MSEC = 5000;
    private boolean mCancelStopListener;
    private long mStopTimeMsec;
    private OnWatchdogStopListener mWatchdogStopListener;
    private WatchdogTimerTask mWatchdogTimerTask;

    public interface OnWatchdogStopListener {
        void stop();
    }

    public WatchdogTimer(OnWatchdogStopListener onWatchdogStopListener) {
        this(onWatchdogStopListener, 5000);
    }

    public WatchdogTimer(OnWatchdogStopListener onWatchdogStopListener, long j) {
        this.mCancelStopListener = false;
        this.mStopTimeMsec = 0;
        this.mWatchdogStopListener = onWatchdogStopListener;
        this.mStopTimeMsec = j;
        start();
    }

    private void start() {
        this.mWatchdogTimerTask = new WatchdogTimerTask(this);
        schedule(this.mWatchdogTimerTask, this.mStopTimeMsec);
    }

    public void reset() {
        start();
    }

    public void stop() {
        if (!(this.mWatchdogStopListener == null || this.mCancelStopListener)) {
            this.mWatchdogStopListener.stop();
        }
        this.mCancelStopListener = false;
    }

    public void resetWatchdog() {
        if (this.mWatchdogTimerTask != null) {
            if (!this.mWatchdogTimerTask.isCancelled()) {
                if (this.mWatchdogTimerTask != null) {
                    this.mWatchdogTimerTask.setWatchdogStatus(0);
                    return;
                }
                return;
            }
        }
        start();
    }

    public void stopWatchdog() {
        if (this.mWatchdogTimerTask != null && !this.mWatchdogTimerTask.isCancelled()) {
            this.mCancelStopListener = true;
            this.mWatchdogTimerTask.setWatchdogStatus(1);
        }
    }

    public boolean isRunning() {
        return (this.mWatchdogTimerTask == null || this.mWatchdogTimerTask.isCancelled()) ? false : true;
    }
}
