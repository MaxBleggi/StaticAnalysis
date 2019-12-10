package com.google.android.gms.cast.framework.media;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.google.android.gms.cast.AdBreakInfo;
import com.google.android.gms.cast.Cast.CastApi;
import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLoadOptions;
import com.google.android.gms.cast.MediaLoadOptions.Builder;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.cast.zzbt;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.cast.zzcg;
import com.google.android.gms.internal.cast.zzco;
import com.google.android.gms.internal.cast.zzdi;
import com.google.android.gms.internal.cast.zzdm;
import com.google.android.gms.internal.cast.zzdn;
import com.google.android.gms.internal.cast.zzek;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONObject;

public class RemoteMediaClient implements MessageReceivedCallback {
    public static final String NAMESPACE = zzdi.NAMESPACE;
    public static final int RESUME_STATE_PAUSE = 2;
    public static final int RESUME_STATE_PLAY = 1;
    public static final int RESUME_STATE_UNCHANGED = 0;
    public static final int STATUS_FAILED = 2100;
    public static final int STATUS_REPLACED = 2103;
    public static final int STATUS_SUCCEEDED = 0;
    private final Handler handler = new zzek(Looper.getMainLooper());
    private final Object lock = new Object();
    private final zzdi zzfl;
    private final CastApi zzic;
    private final MediaQueue zzng;
    private final zza zzox = new zza(this);
    private GoogleApiClient zzoy;
    private final List<Listener> zzoz = new CopyOnWriteArrayList();
    @VisibleForTesting
    final List<Callback> zzpa = new CopyOnWriteArrayList();
    private final Map<ProgressListener, zze> zzpb = new ConcurrentHashMap();
    private final Map<Long, zze> zzpc = new ConcurrentHashMap();
    private ParseAdsInfoCallback zzpd;

    public static abstract class Callback {
        public void onAdBreakStatusUpdated() {
        }

        public void onMetadataUpdated() {
        }

        public void onPreloadStatusUpdated() {
        }

        public void onQueueStatusUpdated() {
        }

        public void onSendingRemoteMediaRequest() {
        }

        public void onStatusUpdated() {
        }

        public void zza(int[] iArr) {
        }

        public void zza(int[] iArr, int i) {
        }

        public void zzb(int[] iArr) {
        }

        public void zzb(MediaQueueItem[] mediaQueueItemArr) {
        }

        public void zzc(int[] iArr) {
        }
    }

    @Deprecated
    public interface Listener {
        void onAdBreakStatusUpdated();

        void onMetadataUpdated();

        void onPreloadStatusUpdated();

        void onQueueStatusUpdated();

        void onSendingRemoteMediaRequest();

        void onStatusUpdated();
    }

    public interface ParseAdsInfoCallback {
        List<AdBreakInfo> parseAdBreaksFromMediaStatus(MediaStatus mediaStatus);

        boolean parseIsPlayingAdFromMediaStatus(MediaStatus mediaStatus);
    }

    public interface ProgressListener {
        void onProgressUpdated(long j, long j2);
    }

    private class zze {
        final /* synthetic */ RemoteMediaClient zzpe;
        private final Set<ProgressListener> zzpr = new HashSet();
        private final long zzps;
        private final Runnable zzpt;
        private boolean zzpu;

        public zze(RemoteMediaClient remoteMediaClient, long j) {
            this.zzpe = remoteMediaClient;
            this.zzps = j;
            this.zzpt = new zzay(this, remoteMediaClient);
        }

        public final long zzcg() {
            return this.zzps;
        }

        public final void zza(ProgressListener progressListener) {
            this.zzpr.add(progressListener);
        }

        public final void zzb(ProgressListener progressListener) {
            this.zzpr.remove(progressListener);
        }

        public final boolean hasListener() {
            return !this.zzpr.isEmpty();
        }

        public final void start() {
            this.zzpe.handler.removeCallbacks(this.zzpt);
            this.zzpu = true;
            this.zzpe.handler.postDelayed(this.zzpt, this.zzps);
        }

        public final void stop() {
            this.zzpe.handler.removeCallbacks(this.zzpt);
            this.zzpu = false;
        }

        public final boolean isStarted() {
            return this.zzpu;
        }
    }

    public interface MediaChannelResult extends Result {
        JSONObject getCustomData();
    }

    private class zza implements zzdm {
        private GoogleApiClient zzgn;
        private long zzgo = 0;
        final /* synthetic */ RemoteMediaClient zzpe;

        public zza(RemoteMediaClient remoteMediaClient) {
            this.zzpe = remoteMediaClient;
        }

        public final void zza(GoogleApiClient googleApiClient) {
            this.zzgn = googleApiClient;
        }

        public final void zza(String str, String str2, long j, String str3) {
            if (this.zzgn != null) {
                this.zzpe.zzic.sendMessage(this.zzgn, str, str2).setResultCallback(new zzau(this, j));
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

    private static class zzb extends BasePendingResult<MediaChannelResult> {
        zzb() {
            super(null);
        }

        @NonNull
        protected final MediaChannelResult zza(Status status) {
            return new zzav(this, status);
        }

        @NonNull
        protected final /* synthetic */ Result createFailedResult(Status status) {
            return zza(status);
        }
    }

    private static final class zzd implements MediaChannelResult {
        private final Status zzgt;
        private final JSONObject zzp;

        zzd(Status status, JSONObject jSONObject) {
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
    abstract class zzc extends zzcg<MediaChannelResult> {
        zzdn zzgr;
        private final /* synthetic */ RemoteMediaClient zzpe;
        private final boolean zzpo;

        zzc(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient) {
            this(remoteMediaClient, googleApiClient, false);
        }

        abstract void zzb(zzco com_google_android_gms_internal_cast_zzco);

        zzc(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, boolean z) {
            this.zzpe = remoteMediaClient;
            super(googleApiClient);
            this.zzpo = z;
            this.zzgr = new zzaw(this, remoteMediaClient);
        }

        protected /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
            zzco com_google_android_gms_internal_cast_zzco = (zzco) anyClient;
            if (!this.zzpo) {
                for (Listener onSendingRemoteMediaRequest : this.zzpe.zzoz) {
                    onSendingRemoteMediaRequest.onSendingRemoteMediaRequest();
                }
                for (Callback onSendingRemoteMediaRequest2 : this.zzpe.zzpa) {
                    onSendingRemoteMediaRequest2.onSendingRemoteMediaRequest();
                }
            }
            zzb(com_google_android_gms_internal_cast_zzco);
        }

        public /* synthetic */ Result createFailedResult(Status status) {
            return new zzax(this, status);
        }
    }

    public RemoteMediaClient(@NonNull zzdi com_google_android_gms_internal_cast_zzdi, @NonNull CastApi castApi) {
        this.zzic = castApi;
        this.zzfl = (zzdi) Preconditions.checkNotNull(com_google_android_gms_internal_cast_zzdi);
        this.zzfl.zza(new zzr(this));
        this.zzfl.zza(this.zzox);
        this.zzng = new MediaQueue(this);
    }

    public final void zzb(com.google.android.gms.common.api.GoogleApiClient r4) {
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
        r3 = this;
        r0 = r3.zzoy;
        if (r0 != r4) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r0 = r3.zzoy;
        if (r0 == 0) goto L_0x0024;
    L_0x0009:
        r0 = r3.zzfl;
        r0.zzdd();
        r0 = r3.zzic;	 Catch:{ IOException -> 0x0019 }
        r1 = r3.zzoy;	 Catch:{ IOException -> 0x0019 }
        r2 = r3.getNamespace();	 Catch:{ IOException -> 0x0019 }
        r0.removeMessageReceivedCallbacks(r1, r2);	 Catch:{ IOException -> 0x0019 }
    L_0x0019:
        r0 = r3.zzox;
        r1 = 0;
        r0.zza(r1);
        r0 = r3.handler;
        r0.removeCallbacksAndMessages(r1);
    L_0x0024:
        r3.zzoy = r4;
        r4 = r3.zzoy;
        if (r4 == 0) goto L_0x0031;
    L_0x002a:
        r4 = r3.zzox;
        r0 = r3.zzoy;
        r4.zza(r0);
    L_0x0031:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.RemoteMediaClient.zzb(com.google.android.gms.common.api.GoogleApiClient):void");
    }

    public final void zzcb() throws IOException {
        if (this.zzoy != null) {
            this.zzic.setMessageReceivedCallbacks(this.zzoy, getNamespace(), this);
        }
    }

    @Deprecated
    public PendingResult<MediaChannelResult> load(MediaInfo mediaInfo) {
        return load(mediaInfo, new Builder().build());
    }

    @Deprecated
    public PendingResult<MediaChannelResult> load(MediaInfo mediaInfo, boolean z) {
        return load(mediaInfo, new Builder().setAutoplay(z).build());
    }

    @Deprecated
    public PendingResult<MediaChannelResult> load(MediaInfo mediaInfo, boolean z, long j) {
        return load(mediaInfo, new Builder().setAutoplay(z).setPlayPosition(j).build());
    }

    @Deprecated
    public PendingResult<MediaChannelResult> load(MediaInfo mediaInfo, boolean z, long j, JSONObject jSONObject) {
        return load(mediaInfo, new Builder().setAutoplay(z).setPlayPosition(j).setCustomData(jSONObject).build());
    }

    @Deprecated
    public PendingResult<MediaChannelResult> load(MediaInfo mediaInfo, boolean z, long j, long[] jArr, JSONObject jSONObject) {
        return load(mediaInfo, new Builder().setAutoplay(z).setPlayPosition(j).setActiveTrackIds(jArr).setCustomData(jSONObject).build());
    }

    public PendingResult<MediaChannelResult> load(MediaInfo mediaInfo, MediaLoadOptions mediaLoadOptions) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzac(this, this.zzoy, mediaInfo, mediaLoadOptions));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> pause() {
        return pause(null);
    }

    public PendingResult<MediaChannelResult> pause(JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzao(this, this.zzoy, jSONObject));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> stop() {
        return stop(null);
    }

    public PendingResult<MediaChannelResult> stop(JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzap(this, this.zzoy, jSONObject));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> play() {
        return play(null);
    }

    public PendingResult<MediaChannelResult> play(JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzaq(this, this.zzoy, jSONObject));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> seek(long j) {
        return seek(j, 0, null);
    }

    public PendingResult<MediaChannelResult> seek(long j, int i) {
        return seek(j, i, null);
    }

    public PendingResult<MediaChannelResult> seek(long j, int i, JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzar(this, this.zzoy, j, i, jSONObject));
        }
        return zza((int) 17, null);
    }

    public PendingResult<MediaChannelResult> setStreamVolume(double d) throws IllegalArgumentException {
        return setStreamVolume(d, null);
    }

    public PendingResult<MediaChannelResult> setStreamVolume(double d, JSONObject jSONObject) throws IllegalArgumentException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzcd()) {
            return zza((int) 8.4E-323d, null);
        }
        if (!Double.isInfinite(d) && !Double.isNaN(d)) {
            return zza(new zzas(this, this.zzoy, d, jSONObject));
        }
        StringBuilder stringBuilder = new StringBuilder(41);
        stringBuilder.append("Volume cannot be ");
        stringBuilder.append(d);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public PendingResult<MediaChannelResult> setStreamMute(boolean z) {
        return setStreamMute(z, null);
    }

    public PendingResult<MediaChannelResult> setStreamMute(boolean z, JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzat(this, this.zzoy, z, jSONObject));
        }
        return zza((int) true, null);
    }

    public PendingResult<MediaChannelResult> setPlaybackRate(double d) {
        return setPlaybackRate(d, null);
    }

    public PendingResult<MediaChannelResult> setPlaybackRate(double d, JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzcd()) {
            return zza((int) 8.4E-323d, null);
        }
        if (Double.compare(d, MediaLoadOptions.PLAYBACK_RATE_MAX) <= 0 && Double.compare(d, MediaLoadOptions.PLAYBACK_RATE_MIN) >= 0) {
            return zza(new zzs(this, this.zzoy, d, jSONObject));
        }
        throw new IllegalArgumentException("playbackRate must be between PLAYBACK_RATE_MIN and PLAYBACK_RATE_MAX");
    }

    public PendingResult<MediaChannelResult> skipAd() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzt(this, this.zzoy));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> requestStatus() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzu(this, this.zzoy));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> setActiveMediaTracks(long[] jArr) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzcd()) {
            return zza((int) 17, null);
        }
        if (jArr != null) {
            return zza(new zzv(this, this.zzoy, jArr));
        }
        throw new IllegalArgumentException("trackIds cannot be null");
    }

    public PendingResult<MediaChannelResult> setTextTrackStyle(TextTrackStyle textTrackStyle) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzcd()) {
            return zza(17, null);
        }
        if (textTrackStyle != null) {
            return zza(new zzw(this, this.zzoy, textTrackStyle));
        }
        throw new IllegalArgumentException("trackStyle cannot be null");
    }

    public PendingResult<MediaChannelResult> queueLoad(MediaQueueItem[] mediaQueueItemArr, int i, int i2, JSONObject jSONObject) throws IllegalArgumentException {
        return queueLoad(mediaQueueItemArr, i, i2, -1, jSONObject);
    }

    public PendingResult<MediaChannelResult> queueLoad(MediaQueueItem[] mediaQueueItemArr, int i, int i2, long j, JSONObject jSONObject) throws IllegalArgumentException {
        RemoteMediaClient remoteMediaClient = this;
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzx(this, remoteMediaClient.zzoy, mediaQueueItemArr, i, i2, j, jSONObject));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> queueInsertItems(MediaQueueItem[] mediaQueueItemArr, int i, JSONObject jSONObject) throws IllegalArgumentException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzy(this, this.zzoy, mediaQueueItemArr, i, jSONObject));
        }
        return zza((int) 17, null);
    }

    public PendingResult<MediaChannelResult> queueAppendItem(MediaQueueItem mediaQueueItem, JSONObject jSONObject) throws IllegalArgumentException {
        return queueInsertItems(new MediaQueueItem[]{mediaQueueItem}, 0, jSONObject);
    }

    public PendingResult<MediaChannelResult> queueInsertAndPlayItem(MediaQueueItem mediaQueueItem, int i, JSONObject jSONObject) {
        return queueInsertAndPlayItem(mediaQueueItem, i, -1, jSONObject);
    }

    public PendingResult<MediaChannelResult> queueInsertAndPlayItem(MediaQueueItem mediaQueueItem, int i, long j, JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzz(this, this.zzoy, mediaQueueItem, i, j, jSONObject));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> queueUpdateItems(MediaQueueItem[] mediaQueueItemArr, JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzaa(this, this.zzoy, mediaQueueItemArr, jSONObject));
        }
        return zza((int) 17, null);
    }

    public PendingResult<MediaChannelResult> queueRemoveItems(int[] iArr, JSONObject jSONObject) throws IllegalArgumentException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzab(this, this.zzoy, iArr, jSONObject));
        }
        return zza((int) 17, null);
    }

    public PendingResult<MediaChannelResult> queueReorderItems(int[] iArr, int i, JSONObject jSONObject) throws IllegalArgumentException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzad(this, this.zzoy, iArr, i, jSONObject));
        }
        return zza((int) 17, null);
    }

    public PendingResult<MediaChannelResult> queuePrev(JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzae(this, this.zzoy, jSONObject));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> queueNext(JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzaf(this, this.zzoy, jSONObject));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> queueSetRepeatMode(int i, JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzag(this, this.zzoy, i, jSONObject));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> queueRemoveItem(int i, JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzah(this, this.zzoy, i, jSONObject));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> queueJumpToItem(int i, JSONObject jSONObject) {
        return queueJumpToItem(i, -1, jSONObject);
    }

    public PendingResult<MediaChannelResult> queueJumpToItem(int i, long j, JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzai(this, this.zzoy, i, j, jSONObject));
        }
        return zza(17, null);
    }

    public PendingResult<MediaChannelResult> queueMoveItemToNewIndex(int i, int i2, JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzaj(this, this.zzoy, i, i2, jSONObject));
        }
        return zza(17, null);
    }

    public final PendingResult<MediaChannelResult> zzcc() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzak(this, this.zzoy, true));
        }
        return zza(17, null);
    }

    public final PendingResult<MediaChannelResult> zzf(int[] iArr) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzal(this, this.zzoy, true, iArr));
        }
        return zza((int) 17, null);
    }

    public final PendingResult<MediaChannelResult> zza(int i, int i2, int i3) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd()) {
            return zza(new zzam(this, this.zzoy, true, i, i2, i3));
        }
        return zza(17, null);
    }

    public final PendingResult<MediaChannelResult> zza(String str, List<zzbt> list) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzcd() == null) {
            return zza(17, null);
        }
        return zza(new zzan(this, this.zzoy, true, str, null));
    }

    private final int zzc(int i) {
        Preconditions.checkMainThread("Must be called from the main thread.");
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
            Preconditions.checkMainThread("Must be called from the main thread.");
            approximateStreamPosition = this.zzfl.getApproximateStreamPosition();
        }
        return approximateStreamPosition;
    }

    public long getApproximateAdBreakClipPositionMs() {
        long approximateAdBreakClipPositionMs;
        synchronized (this.lock) {
            Preconditions.checkMainThread("Must be called from the main thread.");
            approximateAdBreakClipPositionMs = this.zzfl.getApproximateAdBreakClipPositionMs();
        }
        return approximateAdBreakClipPositionMs;
    }

    public long getStreamDuration() {
        long streamDuration;
        synchronized (this.lock) {
            Preconditions.checkMainThread("Must be called from the main thread.");
            streamDuration = this.zzfl.getStreamDuration();
        }
        return streamDuration;
    }

    public MediaStatus getMediaStatus() {
        MediaStatus mediaStatus;
        synchronized (this.lock) {
            Preconditions.checkMainThread("Must be called from the main thread.");
            mediaStatus = this.zzfl.getMediaStatus();
        }
        return mediaStatus;
    }

    public MediaQueue getMediaQueue() {
        MediaQueue mediaQueue;
        synchronized (this.lock) {
            Preconditions.checkMainThread("Must be called from the main thread.");
            mediaQueue = this.zzng;
        }
        return mediaQueue;
    }

    public MediaInfo getMediaInfo() {
        MediaInfo mediaInfo;
        synchronized (this.lock) {
            Preconditions.checkMainThread("Must be called from the main thread.");
            mediaInfo = this.zzfl.getMediaInfo();
        }
        return mediaInfo;
    }

    public int getPlayerState() {
        int playerState;
        synchronized (this.lock) {
            Preconditions.checkMainThread("Must be called from the main thread.");
            MediaStatus mediaStatus = getMediaStatus();
            playerState = mediaStatus != null ? mediaStatus.getPlayerState() : 1;
        }
        return playerState;
    }

    public int getIdleReason() {
        int idleReason;
        synchronized (this.lock) {
            Preconditions.checkMainThread("Must be called from the main thread.");
            MediaStatus mediaStatus = getMediaStatus();
            idleReason = mediaStatus != null ? mediaStatus.getIdleReason() : 0;
        }
        return idleReason;
    }

    public boolean isLiveStream() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaInfo mediaInfo = getMediaInfo();
        return mediaInfo != null && mediaInfo.getStreamType() == 2;
    }

    public boolean isPlaying() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        return mediaStatus != null && mediaStatus.getPlayerState() == 2;
    }

    public boolean isPaused() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        return mediaStatus != null && (mediaStatus.getPlayerState() == 3 || (isLiveStream() && getIdleReason() == 2));
    }

    public boolean isBuffering() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        return mediaStatus != null && mediaStatus.getPlayerState() == 4;
    }

    public boolean isLoadingNextItem() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        return (mediaStatus == null || mediaStatus.getLoadingItemId() == 0) ? false : true;
    }

    public MediaQueueItem getCurrentItem() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        if (mediaStatus == null) {
            return null;
        }
        return mediaStatus.getQueueItemById(mediaStatus.getCurrentItemId());
    }

    public MediaQueueItem getLoadingItem() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        if (mediaStatus == null) {
            return null;
        }
        return mediaStatus.getQueueItemById(mediaStatus.getLoadingItemId());
    }

    public MediaQueueItem getPreloadedItem() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        if (mediaStatus == null) {
            return null;
        }
        return mediaStatus.getQueueItemById(mediaStatus.getPreloadedItemId());
    }

    public void togglePlayback() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        int playerState = getPlayerState();
        if (playerState != 4) {
            if (playerState != 2) {
                play();
                return;
            }
        }
        pause();
    }

    public boolean hasMediaSession() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!(isBuffering() || isPlaying() || isPaused())) {
            if (!isLoadingNextItem()) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public void addListener(Listener listener) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (listener != null) {
            this.zzoz.add(listener);
        }
    }

    @Deprecated
    public void removeListener(Listener listener) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (listener != null) {
            this.zzoz.remove(listener);
        }
    }

    public void registerCallback(Callback callback) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (callback != null) {
            this.zzpa.add(callback);
        }
    }

    public void unregisterCallback(Callback callback) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (callback != null) {
            this.zzpa.remove(callback);
        }
    }

    public boolean addProgressListener(ProgressListener progressListener, long j) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (progressListener != null) {
            if (!this.zzpb.containsKey(progressListener)) {
                zze com_google_android_gms_cast_framework_media_RemoteMediaClient_zze = (zze) this.zzpc.get(Long.valueOf(j));
                if (com_google_android_gms_cast_framework_media_RemoteMediaClient_zze == null) {
                    com_google_android_gms_cast_framework_media_RemoteMediaClient_zze = new zze(this, j);
                    this.zzpc.put(Long.valueOf(j), com_google_android_gms_cast_framework_media_RemoteMediaClient_zze);
                }
                com_google_android_gms_cast_framework_media_RemoteMediaClient_zze.zza(progressListener);
                this.zzpb.put(progressListener, com_google_android_gms_cast_framework_media_RemoteMediaClient_zze);
                if (hasMediaSession() != null) {
                    com_google_android_gms_cast_framework_media_RemoteMediaClient_zze.start();
                }
                return true;
            }
        }
        return null;
    }

    public void removeProgressListener(ProgressListener progressListener) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zze com_google_android_gms_cast_framework_media_RemoteMediaClient_zze = (zze) this.zzpb.remove(progressListener);
        if (com_google_android_gms_cast_framework_media_RemoteMediaClient_zze != null) {
            com_google_android_gms_cast_framework_media_RemoteMediaClient_zze.zzb(progressListener);
            if (com_google_android_gms_cast_framework_media_RemoteMediaClient_zze.hasListener() == null) {
                this.zzpc.remove(Long.valueOf(com_google_android_gms_cast_framework_media_RemoteMediaClient_zze.zzcg()));
                com_google_android_gms_cast_framework_media_RemoteMediaClient_zze.stop();
            }
        }
    }

    public void setParseAdsInfoCallback(ParseAdsInfoCallback parseAdsInfoCallback) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        this.zzpd = parseAdsInfoCallback;
    }

    public boolean isPlayingAd() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        return mediaStatus != null && mediaStatus.isPlayingAd();
    }

    public String getNamespace() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzfl.getNamespace();
    }

    public void onMessageReceived(CastDevice castDevice, String str, String str2) {
        this.zzfl.zzn(str2);
    }

    private final boolean zzcd() {
        return this.zzoy != null;
    }

    public static PendingResult<MediaChannelResult> zza(int i, String str) {
        PendingResult com_google_android_gms_cast_framework_media_RemoteMediaClient_zzb = new zzb();
        com_google_android_gms_cast_framework_media_RemoteMediaClient_zzb.setResult(com_google_android_gms_cast_framework_media_RemoteMediaClient_zzb.zza(new Status(i, str)));
        return com_google_android_gms_cast_framework_media_RemoteMediaClient_zzb;
    }

    private final com.google.android.gms.cast.framework.media.RemoteMediaClient.zzc zza(com.google.android.gms.cast.framework.media.RemoteMediaClient.zzc r3) {
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
        r2 = this;
        r0 = r2.zzoy;	 Catch:{ IllegalStateException -> 0x0006 }
        r0.execute(r3);	 Catch:{ IllegalStateException -> 0x0006 }
        return r3;
    L_0x0006:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x0016 }
        r1 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;	 Catch:{ all -> 0x0016 }
        r0.<init>(r1);	 Catch:{ all -> 0x0016 }
        r0 = r3.createFailedResult(r0);	 Catch:{ all -> 0x0016 }
        r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0;	 Catch:{ all -> 0x0016 }
        r3.setResult(r0);	 Catch:{ all -> 0x0016 }
    L_0x0016:
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.RemoteMediaClient.zza(com.google.android.gms.cast.framework.media.RemoteMediaClient$zzc):com.google.android.gms.cast.framework.media.RemoteMediaClient$zzc");
    }

    private final void zza(Set<ProgressListener> set) {
        Set<ProgressListener> hashSet = new HashSet(set);
        if (isPlaying() == null && isPaused() == null) {
            if (isBuffering() == null) {
                if (isLoadingNextItem() != null) {
                    set = getLoadingItem();
                    if (!(set == null || set.getMedia() == null)) {
                        for (ProgressListener onProgressUpdated : hashSet) {
                            onProgressUpdated.onProgressUpdated(0, set.getMedia().getStreamDuration());
                        }
                    }
                    return;
                }
                for (ProgressListener onProgressUpdated2 : hashSet) {
                    onProgressUpdated2.onProgressUpdated(0, 0);
                }
                return;
            }
        }
        for (ProgressListener onProgressUpdated22 : hashSet) {
            onProgressUpdated22.onProgressUpdated(getApproximateStreamPosition(), getStreamDuration());
        }
    }

    private final void zzce() {
        for (zze com_google_android_gms_cast_framework_media_RemoteMediaClient_zze : this.zzpc.values()) {
            if (hasMediaSession() && !com_google_android_gms_cast_framework_media_RemoteMediaClient_zze.isStarted()) {
                com_google_android_gms_cast_framework_media_RemoteMediaClient_zze.start();
            } else if (!hasMediaSession() && com_google_android_gms_cast_framework_media_RemoteMediaClient_zze.isStarted()) {
                com_google_android_gms_cast_framework_media_RemoteMediaClient_zze.stop();
            }
            if (com_google_android_gms_cast_framework_media_RemoteMediaClient_zze.isStarted() && (isBuffering() || isPaused() || isLoadingNextItem())) {
                zza(com_google_android_gms_cast_framework_media_RemoteMediaClient_zze.zzpr);
            }
        }
    }
}
