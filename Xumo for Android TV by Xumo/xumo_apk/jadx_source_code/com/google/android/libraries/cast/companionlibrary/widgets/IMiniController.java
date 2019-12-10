package com.google.android.libraries.cast.companionlibrary.widgets;

import android.graphics.Bitmap;
import android.net.Uri;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.libraries.cast.companionlibrary.widgets.MiniController.OnMiniControllerChangedListener;

public interface IMiniController {
    boolean isVisible();

    void setCurrentVisibility(boolean z);

    void setIcon(Bitmap bitmap);

    void setIcon(Uri uri);

    void setOnMiniControllerChangedListener(OnMiniControllerChangedListener onMiniControllerChangedListener);

    void setPlayPauseVisibility(boolean z);

    void setPlaybackStatus(int i, int i2);

    void setProgress(int i, int i2);

    void setProgressVisibility(boolean z);

    void setStreamType(int i);

    void setSubtitle(String str);

    void setTitle(String str);

    void setUpcomingItem(MediaQueueItem mediaQueueItem);

    void setUpcomingVisibility(boolean z);

    void setVisibility(int i);
}
