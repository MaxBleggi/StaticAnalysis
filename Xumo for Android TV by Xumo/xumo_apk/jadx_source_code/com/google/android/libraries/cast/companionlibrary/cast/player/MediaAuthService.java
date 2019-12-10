package com.google.android.libraries.cast.companionlibrary.cast.player;

import com.google.android.gms.cast.MediaInfo;

public interface MediaAuthService {
    void abortAuthorization(MediaAuthStatus mediaAuthStatus);

    MediaInfo getMediaInfo();

    String getPendingMessage();

    MediaAuthStatus getStatus();

    long getTimeout();

    void setMediaAuthListener(MediaAuthListener mediaAuthListener);

    void startAuthorization();
}
