package com.google.android.gms.internal.cast;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.ImageHints;
import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.cast.framework.media.MediaUtils;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;
import com.google.android.gms.common.images.WebImage;

public final class zzap extends UIController {
    private final ImagePicker zzln;
    private final zzx zzly;
    private final ImageHints zzlz;
    private final ImageView zzrj;
    private final Bitmap zzrk;

    public zzap(ImageView imageView, Context context, @NonNull ImageHints imageHints, int i) {
        this.zzrj = imageView;
        this.zzlz = imageHints;
        this.zzrk = BitmapFactory.decodeResource(context.getResources(), i);
        imageView = CastContext.zzb(context);
        imageHints = null;
        if (imageView != null) {
            imageView = imageView.getCastOptions().getCastMediaOptions();
            if (imageView != null) {
                imageHints = imageView.getImagePicker();
            }
            this.zzln = imageHints;
        } else {
            this.zzln = null;
        }
        this.zzly = new zzx(context.getApplicationContext());
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        this.zzly.zza(new zzaq(this));
        this.zzrj.setImageBitmap(this.zzrk);
        zzcp();
    }

    public final void onSessionEnded() {
        this.zzly.clear();
        this.zzrj.setImageBitmap(this.zzrk);
        super.onSessionEnded();
    }

    public final void onMediaStatusUpdated() {
        zzcp();
    }

    private final void zzcp() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null) {
            if (remoteMediaClient.hasMediaSession()) {
                MediaQueueItem preloadedItem = remoteMediaClient.getPreloadedItem();
                Uri uri = null;
                if (preloadedItem != null) {
                    MediaInfo media = preloadedItem.getMedia();
                    if (media != null) {
                        if (this.zzln != null) {
                            WebImage onPickImage = this.zzln.onPickImage(media.getMetadata(), this.zzlz);
                            if (!(onPickImage == null || onPickImage.getUrl() == null)) {
                                uri = onPickImage.getUrl();
                            }
                        }
                        uri = MediaUtils.getImageUri(media, 0);
                    }
                }
                if (uri == null) {
                    this.zzrj.setImageBitmap(this.zzrk);
                    return;
                } else {
                    this.zzly.zza(uri);
                    return;
                }
            }
        }
        this.zzrj.setImageBitmap(this.zzrk);
    }
}
