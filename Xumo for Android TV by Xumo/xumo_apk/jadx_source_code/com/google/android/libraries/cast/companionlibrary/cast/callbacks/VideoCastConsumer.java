package com.google.android.libraries.cast.companionlibrary.cast.callbacks;

import android.view.View;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.List;
import java.util.Locale;

public interface VideoCastConsumer extends BaseCastConsumer {
    void onApplicationConnected(ApplicationMetadata applicationMetadata, String str, boolean z);

    void onApplicationConnectionFailed(int i);

    void onApplicationDisconnected(int i);

    void onApplicationStatusChanged(String str);

    void onApplicationStopFailed(int i);

    void onDataMessageReceived(String str);

    void onDataMessageSendFailed(int i);

    void onMediaLoadResult(int i);

    void onMediaQueueOperationResult(int i, int i2);

    void onMediaQueueUpdated(List<MediaQueueItem> list, MediaQueueItem mediaQueueItem, int i, boolean z);

    void onNamespaceRemoved();

    void onRemoteMediaPlayerMetadataUpdated();

    void onRemoteMediaPlayerStatusUpdated();

    void onRemoteMediaPreloadStatusUpdated(MediaQueueItem mediaQueueItem);

    void onTextTrackEnabledChanged(boolean z);

    void onTextTrackLocaleChanged(Locale locale);

    void onTextTrackStyleChanged(TextTrackStyle textTrackStyle);

    void onUpcomingPlayClicked(View view, MediaQueueItem mediaQueueItem);

    void onUpcomingStopClicked(View view, MediaQueueItem mediaQueueItem);

    void onVolumeChanged(double d, boolean z);
}
