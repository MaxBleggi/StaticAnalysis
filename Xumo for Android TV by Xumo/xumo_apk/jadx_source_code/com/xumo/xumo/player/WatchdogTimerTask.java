package com.xumo.xumo.player;

import java.util.TimerTask;

public class WatchdogTimerTask extends TimerTask {
    static final int RESET = 0;
    static final int STOP = 1;
    private boolean mCancelled = false;
    private OnWatchdogEventListener mWatchdogEventListener;
    @WatchdogStatus
    private volatile int mWatchdogStatus = 1;

    public interface OnWatchdogEventListener {
        void reset();

        void stop();
    }

    @interface WatchdogStatus {
    }

    public WatchdogTimerTask(OnWatchdogEventListener onWatchdogEventListener) {
        this.mWatchdogEventListener = onWatchdogEventListener;
    }

    public void run() {
        if (this.mWatchdogEventListener != null) {
            if (this.mWatchdogStatus != 0) {
                this.mWatchdogEventListener.stop();
            } else {
                this.mWatchdogStatus = 1;
                this.mWatchdogEventListener.reset();
            }
        }
        this.mCancelled = true;
    }

    public void setWatchdogStatus(int i) {
        this.mWatchdogStatus = i;
    }

    public boolean isCancelled() {
        return this.mCancelled;
    }
}
