package com.google.android.libraries.cast.companionlibrary.cast.dialog.video;

import androidx.annotation.NonNull;
import androidx.mediarouter.app.MediaRouteDialogFactory;

public class VideoMediaRouteDialogFactory extends MediaRouteDialogFactory {
    @NonNull
    public VideoMediaRouteControllerDialogFragment onCreateControllerDialogFragment() {
        return new VideoMediaRouteControllerDialogFragment();
    }
}
