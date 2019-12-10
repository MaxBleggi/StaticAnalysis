package com.google.android.gms.internal.cast;

import android.widget.TextView;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;
import java.util.ArrayList;
import java.util.List;

public final class zzay extends UIController {
    private final TextView zzrr;
    private final List<String> zzrs = new ArrayList();

    public zzay(TextView textView, List<String> list) {
        this.zzrr = textView;
        this.zzrs.addAll(list);
    }

    public final void onMediaStatusUpdated() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null) {
            if (remoteMediaClient.hasMediaSession()) {
                MediaInfo mediaInfo = remoteMediaClient.getMediaStatus().getMediaInfo();
                if (mediaInfo != null) {
                    MediaMetadata metadata = mediaInfo.getMetadata();
                    if (metadata != null) {
                        for (String str : this.zzrs) {
                            if (metadata.containsKey(str)) {
                                this.zzrr.setText(metadata.getString(str));
                                return;
                            }
                        }
                        this.zzrr.setText("");
                    }
                }
            }
        }
    }
}
