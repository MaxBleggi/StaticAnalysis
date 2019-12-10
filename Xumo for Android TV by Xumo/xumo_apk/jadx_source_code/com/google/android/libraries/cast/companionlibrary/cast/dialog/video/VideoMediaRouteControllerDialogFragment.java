package com.google.android.libraries.cast.companionlibrary.cast.dialog.video;

import android.content.Context;
import android.os.Bundle;
import androidx.mediarouter.app.MediaRouteControllerDialogFragment;

public class VideoMediaRouteControllerDialogFragment extends MediaRouteControllerDialogFragment {
    public VideoMediaRouteControllerDialog onCreateControllerDialog(Context context, Bundle bundle) {
        bundle = new VideoMediaRouteControllerDialog(context);
        bundle.setVolumeControlEnabled(null);
        return bundle;
    }
}
