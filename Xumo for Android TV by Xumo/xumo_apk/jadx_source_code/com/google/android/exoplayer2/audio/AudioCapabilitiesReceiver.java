package com.google.android.exoplayer2.audio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class AudioCapabilitiesReceiver {
    @Nullable
    AudioCapabilities audioCapabilities;
    private final Context context;
    @Nullable
    private final Handler handler;
    private final Listener listener;
    @Nullable
    private final BroadcastReceiver receiver;

    private final class HdmiAudioPlugBroadcastReceiver extends BroadcastReceiver {
        private HdmiAudioPlugBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (isInitialStickyBroadcast() == null) {
                context = AudioCapabilities.getCapabilities(intent);
                if (context.equals(AudioCapabilitiesReceiver.this.audioCapabilities) == null) {
                    AudioCapabilitiesReceiver.this.audioCapabilities = context;
                    AudioCapabilitiesReceiver.this.listener.onAudioCapabilitiesChanged(context);
                }
            }
        }
    }

    public interface Listener {
        void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities);
    }

    public AudioCapabilitiesReceiver(Context context, Listener listener) {
        this(context, null, listener);
    }

    public AudioCapabilitiesReceiver(Context context, @Nullable Handler handler, Listener listener) {
        this.context = (Context) Assertions.checkNotNull(context);
        this.handler = handler;
        this.listener = (Listener) Assertions.checkNotNull(listener);
        this.receiver = Util.SDK_INT >= 21 ? new HdmiAudioPlugBroadcastReceiver() : null;
    }

    public AudioCapabilities register() {
        Intent intent = null;
        if (this.receiver != null) {
            IntentFilter intentFilter = new IntentFilter("android.media.action.HDMI_AUDIO_PLUG");
            if (this.handler != null) {
                intent = this.context.registerReceiver(this.receiver, intentFilter, null, this.handler);
            } else {
                intent = this.context.registerReceiver(this.receiver, intentFilter);
            }
        }
        this.audioCapabilities = AudioCapabilities.getCapabilities(intent);
        return this.audioCapabilities;
    }

    public void unregister() {
        if (this.receiver != null) {
            this.context.unregisterReceiver(this.receiver);
        }
    }
}
