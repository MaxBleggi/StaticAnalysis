package com.google.android.libraries.cast.companionlibrary.cast.callbacks;

import android.view.View;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.List;
import java.util.Locale;

public class VideoCastConsumerImpl extends BaseCastConsumerImpl implements VideoCastConsumer {
    public void onApplicationConnected(ApplicationMetadata applicationMetadata, String str, boolean z) {
    }

    public void onApplicationConnectionFailed(int i) {
    }

    public void onApplicationDisconnected(int i) {
    }

    public void onApplicationStatusChanged(String str) {
    }

    public void onApplicationStopFailed(int i) {
    }

    public void onDataMessageReceived(String str) {
    }

    public void onDataMessageSendFailed(int i) {
    }

    public void onMediaLoadResult(int i) {
    }

    public void onMediaQueueOperationResult(int i, int i2) {
    }

    public void onMediaQueueUpdated(List<MediaQueueItem> list, MediaQueueItem mediaQueueItem, int i, boolean z) {
    }

    public void onNamespaceRemoved() {
    }

    public void onRemoteMediaPlayerMetadataUpdated() {
    }

    public void onRemoteMediaPlayerStatusUpdated() {
    }

    public void onRemoteMediaPreloadStatusUpdated(MediaQueueItem mediaQueueItem) {
    }

    public void onTextTrackEnabledChanged(boolean z) {
    }

    public void onTextTrackLocaleChanged(Locale locale) {
    }

    public void onTextTrackStyleChanged(TextTrackStyle textTrackStyle) {
    }

    public void onUpcomingPlayClicked(View view, MediaQueueItem mediaQueueItem) {
    }

    public void onUpcomingStopClicked(View view, MediaQueueItem mediaQueueItem) {
    }

    public void onVolumeChanged(double d, boolean z) {
    }
}
