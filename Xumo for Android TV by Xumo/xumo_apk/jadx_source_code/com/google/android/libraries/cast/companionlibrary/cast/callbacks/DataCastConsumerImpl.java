package com.google.android.libraries.cast.companionlibrary.cast.callbacks;

import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.common.api.Status;

public class DataCastConsumerImpl extends BaseCastConsumerImpl implements DataCastConsumer {
    public void onApplicationConnected(ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
    }

    public void onApplicationConnectionFailed(int i) {
    }

    public void onApplicationDisconnected(int i) {
    }

    public void onApplicationStatusChanged(String str) {
    }

    public void onApplicationStopFailed(int i) {
    }

    public void onMessageReceived(CastDevice castDevice, String str, String str2) {
    }

    public void onMessageSendFailed(Status status) {
    }

    public void onRemoved(CastDevice castDevice, String str) {
    }

    public void onVolumeChanged(double d, boolean z) {
    }
}
