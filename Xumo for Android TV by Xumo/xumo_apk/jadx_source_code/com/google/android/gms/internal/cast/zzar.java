package com.google.android.gms.internal.cast;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.ImageHints;
import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.cast.framework.media.MediaUtils;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;
import com.google.android.gms.common.images.WebImage;

public final class zzar extends UIController {
    private final ImagePicker zzln;
    private final zzx zzly;
    private final ImageHints zzlz;
    private final ImageView zzrj;
    private final Bitmap zzrm;
    private final View zzrn;

    public zzar(ImageView imageView, Context context, @NonNull ImageHints imageHints, int i, View view) {
        this.zzrj = imageView;
        this.zzlz = imageHints;
        imageView = null;
        this.zzrm = i != 0 ? BitmapFactory.decodeResource(context.getResources(), i) : null;
        this.zzrn = view;
        imageHints = CastContext.zzb(context);
        if (imageHints != null) {
            imageHints = imageHints.getCastOptions().getCastMediaOptions();
            if (imageHints != null) {
                imageView = imageHints.getImagePicker();
            }
            this.zzln = imageView;
        } else {
            this.zzln = null;
        }
        this.zzly = new zzx(context.getApplicationContext());
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        this.zzly.zza(new zzas(this));
        zzcq();
        zzcp();
    }

    public final void onSessionEnded() {
        this.zzly.clear();
        zzcq();
        super.onSessionEnded();
    }

    public final void onMediaStatusUpdated() {
        zzcp();
    }

    private final void zzcp() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null) {
            if (remoteMediaClient.hasMediaSession()) {
                Uri uri;
                MediaInfo mediaInfo = remoteMediaClient.getMediaInfo();
                if (mediaInfo == null) {
                    uri = null;
                } else {
                    if (this.zzln != null) {
                        WebImage onPickImage = this.zzln.onPickImage(mediaInfo.getMetadata(), this.zzlz);
                        if (!(onPickImage == null || onPickImage.getUrl() == null)) {
                            uri = onPickImage.getUrl();
                        }
                    }
                    uri = MediaUtils.getImageUri(mediaInfo, 0);
                }
                if (uri == null) {
                    zzcq();
                    return;
                } else {
                    this.zzly.zza(uri);
                    return;
                }
            }
        }
        zzcq();
    }

    private final void zzcq() {
        if (this.zzrn != null) {
            this.zzrn.setVisibility(0);
            this.zzrj.setVisibility(4);
        }
        if (this.zzrm != null) {
            this.zzrj.setImageBitmap(this.zzrm);
        }
    }
}
