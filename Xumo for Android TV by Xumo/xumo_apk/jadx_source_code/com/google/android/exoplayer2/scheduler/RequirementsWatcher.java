package com.google.android.exoplayer2.scheduler;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.NetworkRequest.Builder;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class RequirementsWatcher {
    private static final String TAG = "RequirementsWatcher";
    private final Context context;
    private final Listener listener;
    private CapabilityValidatedCallback networkCallback;
    private DeviceStatusChangeReceiver receiver;
    private final Requirements requirements;
    private boolean requirementsWereMet;

    @RequiresApi(api = 21)
    private final class CapabilityValidatedCallback extends NetworkCallback {
        private CapabilityValidatedCallback() {
        }

        public void onAvailable(Network network) {
            super.onAvailable(network);
            network = new StringBuilder();
            network.append(RequirementsWatcher.this);
            network.append(" NetworkCallback.onAvailable");
            RequirementsWatcher.logd(network.toString());
            RequirementsWatcher.this.checkRequirements();
        }

        public void onLost(Network network) {
            super.onLost(network);
            network = new StringBuilder();
            network.append(RequirementsWatcher.this);
            network.append(" NetworkCallback.onLost");
            RequirementsWatcher.logd(network.toString());
            RequirementsWatcher.this.checkRequirements();
        }
    }

    private class DeviceStatusChangeReceiver extends BroadcastReceiver {
        private DeviceStatusChangeReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (isInitialStickyBroadcast() == null) {
                context = new StringBuilder();
                context.append(RequirementsWatcher.this);
                context.append(" received ");
                context.append(intent.getAction());
                RequirementsWatcher.logd(context.toString());
                RequirementsWatcher.this.checkRequirements();
            }
        }
    }

    public interface Listener {
        void requirementsMet(RequirementsWatcher requirementsWatcher);

        void requirementsNotMet(RequirementsWatcher requirementsWatcher);
    }

    private static void logd(String str) {
    }

    public RequirementsWatcher(Context context, Listener listener, Requirements requirements) {
        this.requirements = requirements;
        this.listener = listener;
        this.context = context.getApplicationContext();
        context = new StringBuilder();
        context.append(this);
        context.append(" created");
        logd(context.toString());
    }

    public void start() {
        Assertions.checkNotNull(Looper.myLooper());
        this.requirementsWereMet = this.requirements.checkRequirements(this.context);
        IntentFilter intentFilter = new IntentFilter();
        if (this.requirements.getRequiredNetworkType() != 0) {
            if (Util.SDK_INT >= 23) {
                registerNetworkCallbackV23();
            } else {
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            }
        }
        if (this.requirements.isChargingRequired()) {
            intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        }
        if (this.requirements.isIdleRequired()) {
            if (Util.SDK_INT >= 23) {
                intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
            } else {
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
            }
        }
        this.receiver = new DeviceStatusChangeReceiver();
        this.context.registerReceiver(this.receiver, intentFilter, null, new Handler());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this);
        stringBuilder.append(" started");
        logd(stringBuilder.toString());
    }

    public void stop() {
        this.context.unregisterReceiver(this.receiver);
        this.receiver = null;
        if (this.networkCallback != null) {
            unregisterNetworkCallback();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this);
        stringBuilder.append(" stopped");
        logd(stringBuilder.toString());
    }

    public Requirements getRequirements() {
        return this.requirements;
    }

    public String toString() {
        return super.toString();
    }

    @TargetApi(23)
    private void registerNetworkCallbackV23() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService("connectivity");
        NetworkRequest build = new Builder().addCapability(16).build();
        this.networkCallback = new CapabilityValidatedCallback();
        connectivityManager.registerNetworkCallback(build, this.networkCallback);
    }

    private void unregisterNetworkCallback() {
        if (Util.SDK_INT >= 21) {
            ((ConnectivityManager) this.context.getSystemService("connectivity")).unregisterNetworkCallback(this.networkCallback);
            this.networkCallback = null;
        }
    }

    private void checkRequirements() {
        boolean checkRequirements = this.requirements.checkRequirements(this.context);
        if (checkRequirements == this.requirementsWereMet) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("requirementsAreMet is still ");
            stringBuilder.append(checkRequirements);
            logd(stringBuilder.toString());
            return;
        }
        this.requirementsWereMet = checkRequirements;
        if (checkRequirements) {
            logd("start job");
            this.listener.requirementsMet(this);
        } else {
            logd("stop job");
            this.listener.requirementsNotMet(this);
        }
    }
}
