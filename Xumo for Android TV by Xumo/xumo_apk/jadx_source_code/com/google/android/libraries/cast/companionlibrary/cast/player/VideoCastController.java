package com.google.android.libraries.cast.companionlibrary.cast.player;

import android.graphics.Bitmap;

public interface VideoCastController {
    public static final int CC_DISABLED = 2;
    public static final int CC_ENABLED = 1;
    public static final int CC_HIDDEN = 3;

    void adjustControllersForLiveStream(boolean z);

    void closeActivity();

    void onQueueItemsUpdated(int i, int i2);

    void setClosedCaptionState(int i);

    void setImage(Bitmap bitmap);

    void setNextPreviousVisibilityPolicy(int i);

    void setOnVideoCastControllerChangedListener(OnVideoCastControllerListener onVideoCastControllerListener);

    void setPlaybackStatus(int i);

    void setStreamType(int i);

    void setSubTitle(String str);

    void setTitle(String str);

    void showLoading(boolean z);

    void updateControllersStatus(boolean z);

    void updateSeekbar(int i, int i2);
}
