package com.google.android.gms.cast.framework.media;

import android.annotation.TargetApi;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.util.PlatformVersion;
import java.util.Locale;

public class MediaUtils {
    public static String getImageUrl(MediaInfo mediaInfo, int i) {
        mediaInfo = getImageUri(mediaInfo, i);
        if (mediaInfo == null) {
            return null;
        }
        return mediaInfo.toString();
    }

    public static Uri getImageUri(MediaInfo mediaInfo, int i) {
        if (mediaInfo == null) {
            return null;
        }
        mediaInfo = mediaInfo.getMetadata();
        if (mediaInfo == null || mediaInfo.getImages() == null || mediaInfo.getImages().size() <= i) {
            return null;
        }
        return ((WebImage) mediaInfo.getImages().get(i)).getUrl();
    }

    @TargetApi(21)
    public static Locale getTrackLanguage(@NonNull MediaTrack mediaTrack) {
        if (mediaTrack.getLanguage() == null) {
            return null;
        }
        if (PlatformVersion.isAtLeastLollipop()) {
            return Locale.forLanguageTag(mediaTrack.getLanguage());
        }
        mediaTrack = mediaTrack.getLanguage().split("-");
        if (mediaTrack.length == 1) {
            return new Locale(mediaTrack[0]);
        }
        return new Locale(mediaTrack[0], mediaTrack[1]);
    }

    private MediaUtils() {
    }
}
