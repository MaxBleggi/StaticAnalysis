package com.google.android.gms.cast;

import android.annotation.SuppressLint;
import android.os.RemoteException;
import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.cast.zzcg;
import com.google.android.gms.internal.cast.zzco;
import com.google.android.gms.internal.cast.zzdi;
import com.google.android.gms.internal.cast.zzdm;
import com.google.android.gms.internal.cast.zzdn;
import org.json.JSONObject;

@SuppressLint({"MissingRemoteException"})
@Deprecated
public class RemoteMediaPlayer implements MessageReceivedCallback {
    public static final String NAMESPACE = zzdi.NAMESPACE;
    public static final int RESUME_STATE_PAUSE = 2;
    public static final int RESUME_STATE_PLAY = 1;
    public static final int RESUME_STATE_UNCHANGED = 0;
    public static final int STATUS_CANCELED = 2101;
    public static final int STATUS_FAILED = 2100;
    public static final int STATUS_REPLACED = 2103;
    public static final int STATUS_SUCCEEDED = 0;
    public static final int STATUS_TIMED_OUT = 2102;
    private final Object lock;
    private final zzdi zzfl;
    private final zza zzfm;
    private OnPreloadStatusUpdatedListener zzfn;
    private OnQueueStatusUpdatedListener zzfo;
    private OnMetadataUpdatedListener zzfp;
    private OnStatusUpdatedListener zzfq;

    @Deprecated
    public interface OnMetadataUpdatedListener {
        void onMetadataUpdated();
    }

    @Deprecated
    public interface OnPreloadStatusUpdatedListener {
        void onPreloadStatusUpdated();
    }

    @Deprecated
    public interface OnQueueStatusUpdatedListener {
        void onQueueStatusUpdated();
    }

    @Deprecated
    public interface OnStatusUpdatedListener {
        void onStatusUpdated();
    }

    @Deprecated
    public interface MediaChannelResult extends Result {
        JSONObject getCustomData();
    }

    private class zza implements zzdm {
        final /* synthetic */ RemoteMediaPlayer zzfr;
        private GoogleApiClient zzgn;
        private long zzgo = 0;

        public zza(RemoteMediaPlayer remoteMediaPlayer) {
            this.zzfr = remoteMediaPlayer;
        }

        public final void zza(GoogleApiClient googleApiClient) {
            this.zzgn = googleApiClient;
        }

        public final void zza(String str, String str2, long j, String str3) {
            if (this.zzgn != null) {
                Cast.CastApi.sendMessage(this.zzgn, str, str2).setResultCallback(new zzbq(this, j));
                return;
            }
            throw new IllegalStateException("No GoogleApiClient available");
        }

        public final long zzl() {
            long j = this.zzgo + 1;
            this.zzgo = j;
            return j;
        }
    }

    private static final class zzc implements MediaChannelResult {
        private final Status zzgt;
        private final JSONObject zzp;

        zzc(Status status, JSONObject jSONObject) {
            this.zzgt = status;
            this.zzp = jSONObject;
        }

        public final Status getStatus() {
            return this.zzgt;
        }

        public final JSONObject getCustomData() {
            return this.zzp;
        }
    }

    @VisibleForTesting
    static abstract class zzb extends zzcg<MediaChannelResult> {
        zzdn zzgr = new zzbr(this);

        zzb(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        protected void zza(zzco com_google_android_gms_internal_cast_zzco) {
        }

        protected /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
            zza((zzco) anyClient);
        }

        public /* synthetic */ Result createFailedResult(Status status) {
            return new zzbs(this, status);
        }
    }

    public RemoteMediaPlayer() {
        this(new zzdi(null));
    }

    @VisibleForTesting
    private RemoteMediaPlayer(zzdi com_google_android_gms_internal_cast_zzdi) {
        this.lock = new Object();
        this.zzfl = com_google_android_gms_internal_cast_zzdi;
        this.zzfl.zza(new zzat(this));
        this.zzfm = new zza(this);
        this.zzfl.zza(this.zzfm);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient googleApiClient, MediaInfo mediaInfo) {
        return load(googleApiClient, mediaInfo, true, 0, null, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient googleApiClient, MediaInfo mediaInfo, boolean z) {
        return load(googleApiClient, mediaInfo, z, 0, null, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient googleApiClient, MediaInfo mediaInfo, boolean z, long j) {
        return load(googleApiClient, mediaInfo, z, j, null, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient googleApiClient, MediaInfo mediaInfo, boolean z, long j, JSONObject jSONObject) {
        return load(googleApiClient, mediaInfo, z, j, null, jSONObject);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient googleApiClient, MediaInfo mediaInfo, boolean z, long j, long[] jArr, JSONObject jSONObject) {
        ApiMethodImpl com_google_android_gms_cast_zzbe = new zzbe(this, googleApiClient, googleApiClient, z, j, jArr, jSONObject, mediaInfo);
        GoogleApiClient googleApiClient2 = googleApiClient;
        return googleApiClient.execute(com_google_android_gms_cast_zzbe);
    }

    public PendingResult<MediaChannelResult> pause(GoogleApiClient googleApiClient) {
        return pause(googleApiClient, null);
    }

    public PendingResult<MediaChannelResult> pause(GoogleApiClient googleApiClient, JSONObject jSONObject) {
        return googleApiClient.execute(new zzbj(this, googleApiClient, googleApiClient, jSONObject));
    }

    public PendingResult<MediaChannelResult> stop(GoogleApiClient googleApiClient) {
        return stop(googleApiClient, null);
    }

    public PendingResult<MediaChannelResult> stop(GoogleApiClient googleApiClient, JSONObject jSONObject) {
        return googleApiClient.execute(new zzbk(this, googleApiClient, googleApiClient, jSONObject));
    }

    public PendingResult<MediaChannelResult> play(GoogleApiClient googleApiClient) {
        return play(googleApiClient, null);
    }

    public PendingResult<MediaChannelResult> play(GoogleApiClient googleApiClient, JSONObject jSONObject) {
        return googleApiClient.execute(new zzbl(this, googleApiClient, googleApiClient, jSONObject));
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient googleApiClient, long j) {
        return seek(googleApiClient, j, 0, null);
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient googleApiClient, long j, int i) {
        return seek(googleApiClient, j, i, null);
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient googleApiClient, long j, int i, JSONObject jSONObject) {
        return googleApiClient.execute(new zzbm(this, googleApiClient, googleApiClient, j, i, jSONObject));
    }

    public PendingResult<MediaChannelResult> setStreamVolume(GoogleApiClient googleApiClient, double d) throws IllegalArgumentException {
        return setStreamVolume(googleApiClient, d, null);
    }

    public PendingResult<MediaChannelResult> setStreamVolume(GoogleApiClient googleApiClient, double d, JSONObject jSONObject) throws IllegalArgumentException {
        if (!Double.isInfinite(d) && !Double.isNaN(d)) {
            return googleApiClient.execute(new zzbn(this, googleApiClient, googleApiClient, d, jSONObject));
        }
        StringBuilder stringBuilder = new StringBuilder(41);
        stringBuilder.append("Volume cannot be ");
        stringBuilder.append(d);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public PendingResult<MediaChannelResult> setStreamMute(GoogleApiClient googleApiClient, boolean z) {
        return setStreamMute(googleApiClient, z, null);
    }

    public PendingResult<MediaChannelResult> setStreamMute(GoogleApiClient googleApiClient, boolean z, JSONObject jSONObject) {
        return googleApiClient.execute(new zzbo(this, googleApiClient, googleApiClient, z, jSONObject));
    }

    public PendingResult<MediaChannelResult> requestStatus(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzbp(this, googleApiClient, googleApiClient));
    }

    public PendingResult<MediaChannelResult> setActiveMediaTracks(GoogleApiClient googleApiClient, long[] jArr) {
        if (jArr != null) {
            return googleApiClient.execute(new zzau(this, googleApiClient, googleApiClient, jArr));
        }
        throw new IllegalArgumentException("trackIds cannot be null");
    }

    public PendingResult<MediaChannelResult> setTextTrackStyle(GoogleApiClient googleApiClient, TextTrackStyle textTrackStyle) {
        if (textTrackStyle != null) {
            return googleApiClient.execute(new zzav(this, googleApiClient, googleApiClient, textTrackStyle));
        }
        throw new IllegalArgumentException("trackStyle cannot be null");
    }

    public PendingResult<MediaChannelResult> queueLoad(GoogleApiClient googleApiClient, MediaQueueItem[] mediaQueueItemArr, int i, int i2, JSONObject jSONObject) throws IllegalArgumentException {
        return queueLoad(googleApiClient, mediaQueueItemArr, i, i2, -1, jSONObject);
    }

    public PendingResult<MediaChannelResult> queueLoad(GoogleApiClient googleApiClient, MediaQueueItem[] mediaQueueItemArr, int i, int i2, long j, JSONObject jSONObject) throws IllegalArgumentException {
        ApiMethodImpl com_google_android_gms_cast_zzaw = new zzaw(this, googleApiClient, googleApiClient, mediaQueueItemArr, i, i2, j, jSONObject);
        GoogleApiClient googleApiClient2 = googleApiClient;
        return googleApiClient.execute(com_google_android_gms_cast_zzaw);
    }

    public PendingResult<MediaChannelResult> queueInsertItems(GoogleApiClient googleApiClient, MediaQueueItem[] mediaQueueItemArr, int i, JSONObject jSONObject) throws IllegalArgumentException {
        return googleApiClient.execute(new zzax(this, googleApiClient, googleApiClient, mediaQueueItemArr, i, jSONObject));
    }

    public PendingResult<MediaChannelResult> queueAppendItem(GoogleApiClient googleApiClient, MediaQueueItem mediaQueueItem, JSONObject jSONObject) throws IllegalArgumentException {
        return queueInsertItems(googleApiClient, new MediaQueueItem[]{mediaQueueItem}, 0, jSONObject);
    }

    public PendingResult<MediaChannelResult> queueInsertAndPlayItem(GoogleApiClient googleApiClient, MediaQueueItem mediaQueueItem, int i, JSONObject jSONObject) {
        return queueInsertAndPlayItem(googleApiClient, mediaQueueItem, i, -1, jSONObject);
    }

    public PendingResult<MediaChannelResult> queueInsertAndPlayItem(GoogleApiClient googleApiClient, MediaQueueItem mediaQueueItem, int i, long j, JSONObject jSONObject) {
        ApiMethodImpl com_google_android_gms_cast_zzay = new zzay(this, googleApiClient, googleApiClient, mediaQueueItem, i, j, jSONObject);
        GoogleApiClient googleApiClient2 = googleApiClient;
        return googleApiClient.execute(com_google_android_gms_cast_zzay);
    }

    public PendingResult<MediaChannelResult> queueUpdateItems(GoogleApiClient googleApiClient, MediaQueueItem[] mediaQueueItemArr, JSONObject jSONObject) {
        return googleApiClient.execute(new zzaz(this, googleApiClient, googleApiClient, mediaQueueItemArr, jSONObject));
    }

    public PendingResult<MediaChannelResult> queueRemoveItems(GoogleApiClient googleApiClient, int[] iArr, JSONObject jSONObject) throws IllegalArgumentException {
        return googleApiClient.execute(new zzba(this, googleApiClient, googleApiClient, iArr, jSONObject));
    }

    public PendingResult<MediaChannelResult> queueReorderItems(GoogleApiClient googleApiClient, int[] iArr, int i, JSONObject jSONObject) throws IllegalArgumentException {
        return googleApiClient.execute(new zzbb(this, googleApiClient, googleApiClient, iArr, i, jSONObject));
    }

    public PendingResult<MediaChannelResult> queuePrev(GoogleApiClient googleApiClient, JSONObject jSONObject) {
        return googleApiClient.execute(new zzbc(this, googleApiClient, googleApiClient, jSONObject));
    }

    public PendingResult<MediaChannelResult> queueNext(GoogleApiClient googleApiClient, JSONObject jSONObject) {
        return googleApiClient.execute(new zzbd(this, googleApiClient, googleApiClient, jSONObject));
    }

    public PendingResult<MediaChannelResult> queueSetRepeatMode(GoogleApiClient googleApiClient, int i, JSONObject jSONObject) {
        return googleApiClient.execute(new zzbf(this, googleApiClient, googleApiClient, i, jSONObject));
    }

    public PendingResult<MediaChannelResult> queueRemoveItem(GoogleApiClient googleApiClient, int i, JSONObject jSONObject) {
        return googleApiClient.execute(new zzbg(this, googleApiClient, i, googleApiClient, jSONObject));
    }

    public PendingResult<MediaChannelResult> queueJumpToItem(GoogleApiClient googleApiClient, int i, JSONObject jSONObject) {
        return queueJumpToItem(googleApiClient, i, -1, jSONObject);
    }

    public PendingResult<MediaChannelResult> queueJumpToItem(GoogleApiClient googleApiClient, int i, long j, JSONObject jSONObject) {
        return googleApiClient.execute(new zzbh(this, googleApiClient, i, googleApiClient, j, jSONObject));
    }

    public PendingResult<MediaChannelResult> queueMoveItemToNewIndex(GoogleApiClient googleApiClient, int i, int i2, JSONObject jSONObject) {
        return googleApiClient.execute(new zzbi(this, googleApiClient, i, i2, googleApiClient, jSONObject));
    }

    private final int zzc(int i) {
        MediaStatus mediaStatus = getMediaStatus();
        for (int i2 = 0; i2 < mediaStatus.getQueueItemCount(); i2++) {
            if (mediaStatus.getQueueItem(i2).getItemId() == i) {
                return i2;
            }
        }
        return -1;
    }

    public long getApproximateStreamPosition() {
        long approximateStreamPosition;
        synchronized (this.lock) {
            approximateStreamPosition = this.zzfl.getApproximateStreamPosition();
        }
        return approximateStreamPosition;
    }

    public long getStreamDuration() {
        long streamDuration;
        synchronized (this.lock) {
            streamDuration = this.zzfl.getStreamDuration();
        }
        return streamDuration;
    }

    public MediaStatus getMediaStatus() {
        MediaStatus mediaStatus;
        synchronized (this.lock) {
            mediaStatus = this.zzfl.getMediaStatus();
        }
        return mediaStatus;
    }

    public MediaInfo getMediaInfo() {
        MediaInfo mediaInfo;
        synchronized (this.lock) {
            mediaInfo = this.zzfl.getMediaInfo();
        }
        return mediaInfo;
    }

    public void setOnStatusUpdatedListener(OnStatusUpdatedListener onStatusUpdatedListener) {
        this.zzfq = onStatusUpdatedListener;
    }

    private final void onStatusUpdated() {
        if (this.zzfq != null) {
            this.zzfq.onStatusUpdated();
        }
    }

    public void setOnMetadataUpdatedListener(OnMetadataUpdatedListener onMetadataUpdatedListener) {
        this.zzfp = onMetadataUpdatedListener;
    }

    private final void onMetadataUpdated() {
        if (this.zzfp != null) {
            this.zzfp.onMetadataUpdated();
        }
    }

    public void setOnQueueStatusUpdatedListener(OnQueueStatusUpdatedListener onQueueStatusUpdatedListener) {
        this.zzfo = onQueueStatusUpdatedListener;
    }

    private final void onQueueStatusUpdated() {
        if (this.zzfo != null) {
            this.zzfo.onQueueStatusUpdated();
        }
    }

    public void setOnPreloadStatusUpdatedListener(OnPreloadStatusUpdatedListener onPreloadStatusUpdatedListener) {
        this.zzfn = onPreloadStatusUpdatedListener;
    }

    private final void onPreloadStatusUpdated() {
        if (this.zzfn != null) {
            this.zzfn.onPreloadStatusUpdated();
        }
    }

    public String getNamespace() {
        return this.zzfl.getNamespace();
    }

    public void onMessageReceived(CastDevice castDevice, String str, String str2) {
        this.zzfl.zzn(str2);
    }
}
