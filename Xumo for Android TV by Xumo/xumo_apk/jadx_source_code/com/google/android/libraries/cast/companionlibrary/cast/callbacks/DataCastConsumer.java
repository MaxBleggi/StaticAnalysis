package com.google.android.libraries.cast.companionlibrary.cast.callbacks;

import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.common.api.Status;

public interface DataCastConsumer extends BaseCastConsumer {
    void onApplicationConnected(ApplicationMetadata applicationMetadata, String str, String str2, boolean z);

    void onApplicationConnectionFailed(int i);

    void onApplicationDisconnected(int i);

    void onApplicationStatusChanged(String str);

    void onApplicationStopFailed(int i);

    void onMessageReceived(CastDevice castDevice, String str, String str2);

    void onMessageSendFailed(Status status);

    void onRemoved(CastDevice castDevice, String str);

    void onVolumeChanged(double d, boolean z);
}
