package com.google.android.libraries.cast.companionlibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaQueueItem.Builder;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.images.WebImage;
import com.google.android.libraries.cast.companionlibrary.utils.CustomData.PLAYSUBTYPE;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public final class Utils {
    private static final String AD_THUMBNAIL_FORMAT = "https://image.xumo.com/v1/channels/channel/%s/480x300.png?type=channelTile";
    private static final String DEFAULT_THUMBNAIL_CHANNEL_ID = "9999998";
    public static final boolean IS_KITKAT_OR_ABOVE = (VERSION.SDK_INT >= 19);
    private static final String KEY_CONTENT_TYPE = "content-type";
    private static final String KEY_CUSTOM_DATA = "custom-data";
    private static final String KEY_IMAGES = "images";
    private static final String KEY_MEDIA_TYPE = "media-type";
    private static final String KEY_STREAM_DURATION = "stream-duration";
    private static final String KEY_STREAM_TYPE = "stream-type";
    private static final String KEY_TRACKS_DATA = "track-data";
    private static final String KEY_TRACK_CONTENT_ID = "track-custom-id";
    private static final String KEY_TRACK_CONTENT_TYPE = "track-content-type";
    private static final String KEY_TRACK_CUSTOM_DATA = "track-custom-data";
    private static final String KEY_TRACK_ID = "track-id";
    private static final String KEY_TRACK_LANGUAGE = "track-language";
    private static final String KEY_TRACK_NAME = "track-name";
    private static final String KEY_TRACK_SUBTYPE = "track-subtype";
    private static final String KEY_TRACK_TYPE = "track-type";
    private static final String KEY_URL = "movie-urls";
    private static final String TAG = LogUtils.makeLogTag(Utils.class);

    private Utils() {
    }

    public static String formatMillis(int i) {
        return DateUtils.formatElapsedTime((long) (i / 1000));
    }

    public static void showToast(Context context, int i) {
        Toast.makeText(context, context.getString(i), 1).show();
    }

    public static String getImageUrl(MediaInfo mediaInfo, int i) {
        mediaInfo = getImageUri(mediaInfo, i);
        return mediaInfo != null ? mediaInfo.toString() : null;
    }

    public static Uri getImageUri(MediaInfo mediaInfo, int i) {
        mediaInfo = mediaInfo.getMetadata();
        return (mediaInfo == null || mediaInfo.getImages().size() <= i) ? null : ((WebImage) mediaInfo.getImages().get(i)).getUrl();
    }

    public static boolean checkGooglePlayServices(final Activity activity) {
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (isGooglePlayServicesAvailable == 0) {
            return true;
        }
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(isGooglePlayServicesAvailable, activity, 0);
        errorDialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                activity.finish();
            }
        });
        errorDialog.show();
        return false;
    }

    public static Bundle mediaInfoToBundle(MediaInfo mediaInfo) {
        if (mediaInfo == null) {
            return null;
        }
        MediaMetadata metadata = mediaInfo.getMetadata();
        if (metadata == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString(MediaMetadata.KEY_TITLE, metadata.getString(MediaMetadata.KEY_TITLE));
        bundle.putString(MediaMetadata.KEY_SUBTITLE, metadata.getString(MediaMetadata.KEY_SUBTITLE));
        bundle.putString(MediaMetadata.KEY_ALBUM_TITLE, metadata.getString(MediaMetadata.KEY_ALBUM_TITLE));
        bundle.putString(MediaMetadata.KEY_ALBUM_ARTIST, metadata.getString(MediaMetadata.KEY_ALBUM_ARTIST));
        bundle.putString(MediaMetadata.KEY_COMPOSER, metadata.getString(MediaMetadata.KEY_COMPOSER));
        bundle.putString(MediaMetadata.KEY_SERIES_TITLE, metadata.getString(MediaMetadata.KEY_SERIES_TITLE));
        bundle.putInt(MediaMetadata.KEY_SEASON_NUMBER, metadata.getInt(MediaMetadata.KEY_SEASON_NUMBER));
        bundle.putInt(MediaMetadata.KEY_EPISODE_NUMBER, metadata.getInt(MediaMetadata.KEY_EPISODE_NUMBER));
        Calendar date = metadata.getDate(MediaMetadata.KEY_RELEASE_DATE);
        if (date != null) {
            bundle.putLong(MediaMetadata.KEY_RELEASE_DATE, date.getTimeInMillis());
        }
        bundle.putString(KEY_URL, mediaInfo.getContentId());
        bundle.putString(MediaMetadata.KEY_STUDIO, metadata.getString(MediaMetadata.KEY_STUDIO));
        bundle.putString(KEY_CONTENT_TYPE, mediaInfo.getContentType());
        bundle.putInt(KEY_STREAM_TYPE, mediaInfo.getStreamType());
        bundle.putLong(KEY_STREAM_DURATION, mediaInfo.getStreamDuration());
        if (!metadata.getImages().isEmpty()) {
            ArrayList arrayList = new ArrayList();
            for (WebImage url : metadata.getImages()) {
                arrayList.add(url.getUrl().toString());
            }
            bundle.putStringArrayList(KEY_IMAGES, arrayList);
        }
        JSONObject customData = mediaInfo.getCustomData();
        if (customData != null) {
            bundle.putString(KEY_CUSTOM_DATA, customData.toString());
        }
        if (!(mediaInfo.getMediaTracks() == null || mediaInfo.getMediaTracks().isEmpty())) {
            try {
                JSONArray jSONArray = new JSONArray();
                for (MediaTrack mediaTrack : mediaInfo.getMediaTracks()) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(KEY_TRACK_NAME, mediaTrack.getName());
                    jSONObject.put(KEY_TRACK_CONTENT_ID, mediaTrack.getContentId());
                    jSONObject.put(KEY_TRACK_ID, mediaTrack.getId());
                    jSONObject.put(KEY_TRACK_LANGUAGE, mediaTrack.getLanguage());
                    jSONObject.put(KEY_TRACK_TYPE, mediaTrack.getType());
                    jSONObject.put(KEY_TRACK_CONTENT_TYPE, mediaTrack.getContentType());
                    if (mediaTrack.getSubtype() != -1) {
                        jSONObject.put(KEY_TRACK_SUBTYPE, mediaTrack.getSubtype());
                    }
                    if (mediaTrack.getCustomData() != null) {
                        jSONObject.put(KEY_TRACK_CUSTOM_DATA, mediaTrack.getCustomData().toString());
                    }
                    jSONArray.put(jSONObject);
                }
                bundle.putString(KEY_TRACKS_DATA, jSONArray.toString());
            } catch (MediaInfo mediaInfo2) {
                LogUtils.LOGE(TAG, "mediaInfoToBundle(): Failed to convert Tracks data to json", mediaInfo2);
            }
        }
        return bundle;
    }

    public static com.google.android.gms.cast.MediaInfo bundleToMediaInfo(android.os.Bundle r12) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = 0;
        if (r12 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = new com.google.android.gms.cast.MediaMetadata;
        r2 = "media-type";
        r2 = r12.getInt(r2);
        r1.<init>(r2);
        r2 = "com.google.android.gms.cast.metadata.SUBTITLE";
        r3 = "com.google.android.gms.cast.metadata.SUBTITLE";
        r3 = r12.getString(r3);
        r1.putString(r2, r3);
        r2 = "com.google.android.gms.cast.metadata.TITLE";
        r3 = "com.google.android.gms.cast.metadata.TITLE";
        r3 = r12.getString(r3);
        r1.putString(r2, r3);
        r2 = "com.google.android.gms.cast.metadata.STUDIO";
        r3 = "com.google.android.gms.cast.metadata.STUDIO";
        r3 = r12.getString(r3);
        r1.putString(r2, r3);
        r2 = "com.google.android.gms.cast.metadata.ALBUM_ARTIST";
        r3 = "com.google.android.gms.cast.metadata.ALBUM_ARTIST";
        r3 = r12.getString(r3);
        r1.putString(r2, r3);
        r2 = "com.google.android.gms.cast.metadata.ALBUM_TITLE";
        r3 = "com.google.android.gms.cast.metadata.ALBUM_TITLE";
        r3 = r12.getString(r3);
        r1.putString(r2, r3);
        r2 = "com.google.android.gms.cast.metadata.COMPOSER";
        r3 = "com.google.android.gms.cast.metadata.COMPOSER";
        r3 = r12.getString(r3);
        r1.putString(r2, r3);
        r2 = "com.google.android.gms.cast.metadata.SERIES_TITLE";
        r3 = "com.google.android.gms.cast.metadata.SERIES_TITLE";
        r3 = r12.getString(r3);
        r1.putString(r2, r3);
        r2 = "com.google.android.gms.cast.metadata.SEASON_NUMBER";
        r3 = "com.google.android.gms.cast.metadata.SEASON_NUMBER";
        r3 = r12.getInt(r3);
        r1.putInt(r2, r3);
        r2 = "com.google.android.gms.cast.metadata.EPISODE_NUMBER";
        r3 = "com.google.android.gms.cast.metadata.EPISODE_NUMBER";
        r3 = r12.getInt(r3);
        r1.putInt(r2, r3);
        r2 = "com.google.android.gms.cast.metadata.RELEASE_DATE";
        r3 = 0;
        r5 = r12.getLong(r2, r3);
        r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r2 <= 0) goto L_0x008a;
    L_0x007e:
        r2 = java.util.Calendar.getInstance();
        r2.setTimeInMillis(r5);
        r5 = "com.google.android.gms.cast.metadata.RELEASE_DATE";
        r1.putDate(r5, r2);
    L_0x008a:
        r2 = "images";
        r2 = r12.getStringArrayList(r2);
        if (r2 == 0) goto L_0x00b5;
    L_0x0092:
        r5 = r2.isEmpty();
        if (r5 != 0) goto L_0x00b5;
    L_0x0098:
        r2 = r2.iterator();
    L_0x009c:
        r5 = r2.hasNext();
        if (r5 == 0) goto L_0x00b5;
    L_0x00a2:
        r5 = r2.next();
        r5 = (java.lang.String) r5;
        r5 = android.net.Uri.parse(r5);
        r6 = new com.google.android.gms.common.images.WebImage;
        r6.<init>(r5);
        r1.addImage(r6);
        goto L_0x009c;
    L_0x00b5:
        r2 = "custom-data";
        r2 = r12.getString(r2);
        r5 = android.text.TextUtils.isEmpty(r2);
        if (r5 != 0) goto L_0x00dd;
    L_0x00c1:
        r5 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x00c7 }
        r5.<init>(r2);	 Catch:{ JSONException -> 0x00c7 }
        goto L_0x00de;
    L_0x00c7:
        r5 = TAG;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Failed to deserialize the custom data string: custom data= ";
        r6.append(r7);
        r6.append(r2);
        r2 = r6.toString();
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGE(r5, r2);
    L_0x00dd:
        r5 = r0;
    L_0x00de:
        r2 = "track-data";
        r2 = r12.getString(r2);
        if (r2 == 0) goto L_0x019d;
    L_0x00e6:
        r2 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x0192 }
        r6 = "track-data";	 Catch:{ JSONException -> 0x0192 }
        r6 = r12.getString(r6);	 Catch:{ JSONException -> 0x0192 }
        r2.<init>(r6);	 Catch:{ JSONException -> 0x0192 }
        r6 = new java.util.ArrayList;	 Catch:{ JSONException -> 0x0192 }
        r6.<init>();	 Catch:{ JSONException -> 0x0192 }
        r0 = r2.length();	 Catch:{ JSONException -> 0x0190 }
        if (r0 <= 0) goto L_0x019e;	 Catch:{ JSONException -> 0x0190 }
    L_0x00fc:
        r0 = 0;	 Catch:{ JSONException -> 0x0190 }
    L_0x00fd:
        r7 = r2.length();	 Catch:{ JSONException -> 0x0190 }
        if (r0 >= r7) goto L_0x019e;	 Catch:{ JSONException -> 0x0190 }
    L_0x0103:
        r7 = r2.get(r0);	 Catch:{ JSONException -> 0x0190 }
        r7 = (org.json.JSONObject) r7;	 Catch:{ JSONException -> 0x0190 }
        r8 = new com.google.android.gms.cast.MediaTrack$Builder;	 Catch:{ JSONException -> 0x0190 }
        r9 = "track-id";	 Catch:{ JSONException -> 0x0190 }
        r9 = r7.getLong(r9);	 Catch:{ JSONException -> 0x0190 }
        r11 = "track-type";	 Catch:{ JSONException -> 0x0190 }
        r11 = r7.getInt(r11);	 Catch:{ JSONException -> 0x0190 }
        r8.<init>(r9, r11);	 Catch:{ JSONException -> 0x0190 }
        r9 = "track-name";	 Catch:{ JSONException -> 0x0190 }
        r9 = r7.has(r9);	 Catch:{ JSONException -> 0x0190 }
        if (r9 == 0) goto L_0x012b;	 Catch:{ JSONException -> 0x0190 }
    L_0x0122:
        r9 = "track-name";	 Catch:{ JSONException -> 0x0190 }
        r9 = r7.getString(r9);	 Catch:{ JSONException -> 0x0190 }
        r8.setName(r9);	 Catch:{ JSONException -> 0x0190 }
    L_0x012b:
        r9 = "track-subtype";	 Catch:{ JSONException -> 0x0190 }
        r9 = r7.has(r9);	 Catch:{ JSONException -> 0x0190 }
        if (r9 == 0) goto L_0x013c;	 Catch:{ JSONException -> 0x0190 }
    L_0x0133:
        r9 = "track-subtype";	 Catch:{ JSONException -> 0x0190 }
        r9 = r7.getInt(r9);	 Catch:{ JSONException -> 0x0190 }
        r8.setSubtype(r9);	 Catch:{ JSONException -> 0x0190 }
    L_0x013c:
        r9 = "track-custom-id";	 Catch:{ JSONException -> 0x0190 }
        r9 = r7.has(r9);	 Catch:{ JSONException -> 0x0190 }
        if (r9 == 0) goto L_0x014d;	 Catch:{ JSONException -> 0x0190 }
    L_0x0144:
        r9 = "track-custom-id";	 Catch:{ JSONException -> 0x0190 }
        r9 = r7.getString(r9);	 Catch:{ JSONException -> 0x0190 }
        r8.setContentId(r9);	 Catch:{ JSONException -> 0x0190 }
    L_0x014d:
        r9 = "track-content-type";	 Catch:{ JSONException -> 0x0190 }
        r9 = r7.has(r9);	 Catch:{ JSONException -> 0x0190 }
        if (r9 == 0) goto L_0x015e;	 Catch:{ JSONException -> 0x0190 }
    L_0x0155:
        r9 = "track-content-type";	 Catch:{ JSONException -> 0x0190 }
        r9 = r7.getString(r9);	 Catch:{ JSONException -> 0x0190 }
        r8.setContentType(r9);	 Catch:{ JSONException -> 0x0190 }
    L_0x015e:
        r9 = "track-language";	 Catch:{ JSONException -> 0x0190 }
        r9 = r7.has(r9);	 Catch:{ JSONException -> 0x0190 }
        if (r9 == 0) goto L_0x016f;	 Catch:{ JSONException -> 0x0190 }
    L_0x0166:
        r9 = "track-language";	 Catch:{ JSONException -> 0x0190 }
        r9 = r7.getString(r9);	 Catch:{ JSONException -> 0x0190 }
        r8.setLanguage(r9);	 Catch:{ JSONException -> 0x0190 }
    L_0x016f:
        r9 = "track-data";	 Catch:{ JSONException -> 0x0190 }
        r9 = r7.has(r9);	 Catch:{ JSONException -> 0x0190 }
        if (r9 == 0) goto L_0x0185;	 Catch:{ JSONException -> 0x0190 }
    L_0x0177:
        r9 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0190 }
        r10 = "track-data";	 Catch:{ JSONException -> 0x0190 }
        r7 = r7.getString(r10);	 Catch:{ JSONException -> 0x0190 }
        r9.<init>(r7);	 Catch:{ JSONException -> 0x0190 }
        r8.setCustomData(r9);	 Catch:{ JSONException -> 0x0190 }
    L_0x0185:
        r7 = r8.build();	 Catch:{ JSONException -> 0x0190 }
        r6.add(r7);	 Catch:{ JSONException -> 0x0190 }
        r0 = r0 + 1;
        goto L_0x00fd;
    L_0x0190:
        r0 = move-exception;
        goto L_0x0195;
    L_0x0192:
        r2 = move-exception;
        r6 = r0;
        r0 = r2;
    L_0x0195:
        r2 = TAG;
        r7 = "Failed to build media tracks from the wrapper bundle";
        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGE(r2, r7, r0);
        goto L_0x019e;
    L_0x019d:
        r6 = r0;
    L_0x019e:
        r0 = new com.google.android.gms.cast.MediaInfo$Builder;
        r2 = "https://www.google.com";
        r0.<init>(r2);
        r2 = 1;
        r0 = r0.setStreamType(r2);
        r2 = "video/mp4";
        r0 = r0.setContentType(r2);
        r0 = r0.setMetadata(r1);
        r0 = r0.setCustomData(r5);
        r0 = r0.setMediaTracks(r6);
        r1 = "stream-duration";
        r1 = r12.containsKey(r1);
        if (r1 == 0) goto L_0x01d7;
    L_0x01c4:
        r1 = "stream-duration";
        r1 = r12.getLong(r1);
        r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
        if (r5 < 0) goto L_0x01d7;
    L_0x01ce:
        r1 = "stream-duration";
        r1 = r12.getLong(r1);
        r0.setStreamDuration(r1);
    L_0x01d7:
        r12 = r0.build();
        return r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.cast.companionlibrary.utils.Utils.bundleToMediaInfo(android.os.Bundle):com.google.android.gms.cast.MediaInfo");
    }

    public static String getWifiSsid(Context context) {
        context = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        return context != null ? context.getSSID() : null;
    }

    public static Bitmap scaleAndCenterCropBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        float f = (float) i2;
        float width = (float) bitmap.getWidth();
        float f2 = (float) i;
        float height = (float) bitmap.getHeight();
        float max = Math.max(f / width, f2 / height);
        width *= max;
        max *= height;
        f = (f - width) / ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT;
        f2 = (f2 - max) / ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT;
        RectF rectF = new RectF(f, f2, width + f, max + f2);
        i = Bitmap.createBitmap(i2, i, bitmap.getConfig());
        new Canvas(i).drawBitmap(bitmap, null, rectF, null);
        return i;
    }

    public static int convertDpToPixel(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    public static MediaQueueItem[] rebuildQueue(List<MediaQueueItem> list) {
        if (list != null) {
            if (!list.isEmpty()) {
                MediaQueueItem[] mediaQueueItemArr = new MediaQueueItem[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    mediaQueueItemArr[i] = rebuildQueueItem((MediaQueueItem) list.get(i));
                }
                return mediaQueueItemArr;
            }
        }
        return null;
    }

    public static MediaQueueItem[] rebuildQueueAndAppend(List<MediaQueueItem> list, MediaQueueItem mediaQueueItem) {
        int i = 0;
        if (list != null) {
            if (!list.isEmpty()) {
                MediaQueueItem[] mediaQueueItemArr = new MediaQueueItem[(list.size() + 1)];
                while (i < list.size()) {
                    mediaQueueItemArr[i] = rebuildQueueItem((MediaQueueItem) list.get(i));
                    i++;
                }
                mediaQueueItemArr[list.size()] = mediaQueueItem;
                return mediaQueueItemArr;
            }
        }
        return new MediaQueueItem[]{mediaQueueItem};
    }

    public static MediaQueueItem rebuildQueueItem(MediaQueueItem mediaQueueItem) {
        return new Builder(mediaQueueItem).clearItemId().build();
    }

    public static boolean isUiThread() {
        return Looper.getMainLooper().equals(Looper.myLooper());
    }

    public static void assertUiThread() {
        if (!isUiThread()) {
            throw new IllegalStateException("Not a UI thread");
        }
    }

    public static void assertNonUiThread() {
        if (isUiThread()) {
            throw new IllegalStateException("Not a non-UI thread");
        }
    }

    public static <T> T assertNotNull(T t, String str) {
        if (t != null) {
            return t;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" cannot be null");
        throw new NullPointerException(stringBuilder.toString());
    }

    public static String assertNotEmpty(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(" cannot be null or empty");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static Point getDisplaySize(Context context) {
        context = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (VERSION.SDK_INT < 13) {
            return new Point(context.getWidth(), context.getHeight());
        }
        Point point = new Point();
        context.getSize(point);
        return point;
    }

    public static boolean hasAudioOrTextTrack(List<MediaTrack> list) {
        if (list != null) {
            if (!list.isEmpty()) {
                for (MediaTrack mediaTrack : list) {
                    if (mediaTrack.getType() != 2) {
                        if (mediaTrack.getType() == 1) {
                        }
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static Uri getAdThumbnailUri(String str, String str2) {
        String str3 = DEFAULT_THUMBNAIL_CHANNEL_ID;
        if (TextUtils.isEmpty(str2) || TextUtils.equals(PLAYSUBTYPE.MYFEED, str) || TextUtils.equals(PLAYSUBTYPE.SEARCH, str) || TextUtils.equals(PLAYSUBTYPE.LIKE, str) != null) {
            str2 = str3;
        }
        return Uri.parse(String.format(AD_THUMBNAIL_FORMAT, new Object[]{str2}));
    }
}
