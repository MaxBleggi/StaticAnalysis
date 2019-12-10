package com.google.android.gms.cast.framework.media;

import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
import android.util.SparseIntArray;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.cast.zzcv;
import com.google.android.gms.internal.cast.zzdh;
import com.google.android.gms.internal.cast.zzek;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

@MainThread
public class MediaQueue {
    private final Handler handler;
    @VisibleForTesting
    long zzer;
    private final RemoteMediaClient zzig;
    private final zzdh zzmp;
    @VisibleForTesting
    private boolean zzmq;
    @VisibleForTesting
    List<Integer> zzmr;
    @VisibleForTesting
    final SparseIntArray zzms;
    @VisibleForTesting
    LruCache<Integer, MediaQueueItem> zzmt;
    @VisibleForTesting
    final List<Integer> zzmu;
    @VisibleForTesting
    final Deque<Integer> zzmv;
    private final int zzmw;
    private TimerTask zzmx;
    @VisibleForTesting
    PendingResult<MediaChannelResult> zzmy;
    @VisibleForTesting
    PendingResult<MediaChannelResult> zzmz;
    @VisibleForTesting
    private ResultCallback<MediaChannelResult> zzna;
    @VisibleForTesting
    private ResultCallback<MediaChannelResult> zznb;
    @VisibleForTesting
    private zzd zznc;
    @VisibleForTesting
    private SessionManagerListener<CastSession> zznd;
    private Set<Callback> zzne;

    public static abstract class Callback {
        public void itemsInsertedInRange(int i, int i2) {
        }

        public void itemsReloaded() {
        }

        public void itemsRemovedAtIndexes(int[] iArr) {
        }

        public void itemsUpdatedAtIndexes(int[] iArr) {
        }

        public void mediaQueueChanged() {
        }

        public void mediaQueueWillChange() {
        }
    }

    private class zza implements ResultCallback<MediaChannelResult> {
        private final /* synthetic */ MediaQueue zznf;

        private zza(MediaQueue mediaQueue) {
            this.zznf = mediaQueue;
        }

        public final /* synthetic */ void onResult(@NonNull Result result) {
            if (((MediaChannelResult) result).getStatus().getStatusCode() != 0) {
                this.zznf.zzmp.w(String.format("Error fetching queue item ids, statusCode=%s, statusMessage=%s", new Object[]{Integer.valueOf(r0), result.getStatusMessage()}), new Object[0]);
            }
            this.zznf.zzmz = null;
            if (this.zznf.zzmv.isEmpty() == null) {
                this.zznf.zzbb();
            }
        }
    }

    private class zzb implements ResultCallback<MediaChannelResult> {
        private final /* synthetic */ MediaQueue zznf;

        private zzb(MediaQueue mediaQueue) {
            this.zznf = mediaQueue;
        }

        public final /* synthetic */ void onResult(@NonNull Result result) {
            if (((MediaChannelResult) result).getStatus().getStatusCode() != 0) {
                this.zznf.zzmp.w(String.format("Error fetching queue items, statusCode=%s, statusMessage=%s", new Object[]{Integer.valueOf(r0), result.getStatusMessage()}), new Object[0]);
            }
            this.zznf.zzmy = null;
            if (this.zznf.zzmv.isEmpty() == null) {
                this.zznf.zzbb();
            }
        }
    }

    private class zzc implements SessionManagerListener<CastSession> {
        private final /* synthetic */ MediaQueue zznf;

        private zzc(MediaQueue mediaQueue) {
            this.zznf = mediaQueue;
        }

        public final /* bridge */ /* synthetic */ void onSessionResumeFailed(Session session, int i) {
        }

        public final /* bridge */ /* synthetic */ void onSessionResuming(Session session, String str) {
        }

        public final /* bridge */ /* synthetic */ void onSessionStartFailed(Session session, int i) {
        }

        public final /* bridge */ /* synthetic */ void onSessionStarting(Session session) {
        }

        public final /* synthetic */ void onSessionSuspended(Session session, int i) {
            this.zznf.zzbf();
        }

        public final /* synthetic */ void onSessionResumed(Session session, boolean z) {
            CastSession castSession = (CastSession) session;
            if (castSession.getRemoteMediaClient()) {
                this.zznf.zza(castSession.getRemoteMediaClient());
            }
        }

        public final /* synthetic */ void onSessionEnded(Session session, int i) {
            this.zznf.zzbf();
            this.zznf.clear();
        }

        public final /* synthetic */ void onSessionEnding(Session session) {
            this.zznf.zzbf();
            this.zznf.clear();
        }

        public final /* synthetic */ void onSessionStarted(Session session, String str) {
            this.zznf.zza(((CastSession) session).getRemoteMediaClient());
        }
    }

    @VisibleForTesting
    class zzd extends com.google.android.gms.cast.framework.media.RemoteMediaClient.Callback {
        private final /* synthetic */ MediaQueue zznf;

        zzd(MediaQueue mediaQueue) {
            this.zznf = mediaQueue;
        }

        public final void zza(int[] iArr) {
            iArr = zzcv.zzg(iArr);
            if (!this.zznf.zzmr.equals(iArr)) {
                this.zznf.zzbi();
                this.zznf.zzmt.evictAll();
                this.zznf.zzmu.clear();
                this.zznf.zzmr = iArr;
                this.zznf.zzbh();
                this.zznf.zzbk();
                this.zznf.zzbj();
            }
        }

        public final void zza(int[] iArr, int i) {
            int length = iArr.length;
            if (i == 0) {
                i = this.zznf.zzmr.size();
            } else {
                i = this.zznf.zzms.get(i, -1);
                if (i == -1) {
                    this.zznf.reload();
                    return;
                }
            }
            this.zznf.zzbi();
            this.zznf.zzmr.addAll(i, zzcv.zzg(iArr));
            this.zznf.zzbh();
            this.zznf.zzb(i, length);
            this.zznf.zzbj();
        }

        public final void zzb(int[] iArr) {
            Collection arrayList = new ArrayList();
            int length = iArr.length;
            int i = 0;
            while (i < length) {
                int i2 = iArr[i];
                this.zznf.zzmt.remove(Integer.valueOf(i2));
                i2 = this.zznf.zzms.get(i2, -1);
                if (i2 == -1) {
                    this.zznf.reload();
                    return;
                } else {
                    arrayList.add(Integer.valueOf(i2));
                    i++;
                }
            }
            Collections.sort(arrayList);
            this.zznf.zzbi();
            this.zznf.zzd(zzcv.zza(arrayList));
            this.zznf.zzbj();
        }

        public final void zzc(int[] iArr) {
            Collection arrayList = new ArrayList();
            for (int i : iArr) {
                this.zznf.zzmt.remove(Integer.valueOf(i));
                int i2 = this.zznf.zzms.get(i, -1);
                if (i2 == -1) {
                    this.zznf.reload();
                    return;
                }
                this.zznf.zzms.delete(i);
                arrayList.add(Integer.valueOf(i2));
            }
            if (!arrayList.isEmpty()) {
                Collections.sort(arrayList);
                this.zznf.zzbi();
                this.zznf.zzmr.removeAll(zzcv.zzg(iArr));
                this.zznf.zzbh();
                this.zznf.zze(zzcv.zza(arrayList));
                this.zznf.zzbj();
            }
        }

        public final void zzb(MediaQueueItem[] mediaQueueItemArr) {
            Collection hashSet = new HashSet();
            this.zznf.zzmu.clear();
            int length = mediaQueueItemArr.length;
            int i = 0;
            while (i < length) {
                MediaQueueItem mediaQueueItem = mediaQueueItemArr[i];
                int itemId = mediaQueueItem.getItemId();
                this.zznf.zzmt.put(Integer.valueOf(itemId), mediaQueueItem);
                int i2 = this.zznf.zzms.get(itemId, -1);
                if (i2 == -1) {
                    this.zznf.reload();
                    return;
                } else {
                    hashSet.add(Integer.valueOf(i2));
                    i++;
                }
            }
            for (Integer intValue : this.zznf.zzmu) {
                length = this.zznf.zzms.get(intValue.intValue(), -1);
                if (length != -1) {
                    hashSet.add(Integer.valueOf(length));
                }
            }
            this.zznf.zzmu.clear();
            Collection arrayList = new ArrayList(hashSet);
            Collections.sort(arrayList);
            this.zznf.zzbi();
            this.zznf.zzd(zzcv.zza(arrayList));
            this.zznf.zzbj();
        }

        public final void onStatusUpdated() {
            long zza = MediaQueue.zzb(this.zznf.zzig);
            if (zza != this.zznf.zzer) {
                this.zznf.zzer = zza;
                this.zznf.clear();
                if (this.zznf.zzer != 0) {
                    this.zznf.reload();
                }
            }
        }
    }

    MediaQueue(@NonNull RemoteMediaClient remoteMediaClient) {
        this(remoteMediaClient, 20, 20);
    }

    @VisibleForTesting
    private MediaQueue(@NonNull RemoteMediaClient remoteMediaClient, int i, int i2) {
        this.zzne = new HashSet();
        this.zzmp = new zzdh("MediaQueue");
        this.zzig = remoteMediaClient;
        this.zzmw = Math.max(20, 1);
        i = CastContext.getSharedInstance().getSessionManager().getCurrentCastSession();
        this.zzmr = new ArrayList();
        this.zzms = new SparseIntArray();
        this.zzmu = new ArrayList();
        this.zzmv = new ArrayDeque(20);
        this.handler = new zzek(Looper.getMainLooper());
        zzh(20);
        this.zzmx = new zzk(this);
        this.zzna = new zzb();
        this.zznb = new zza();
        this.zznc = new zzd(this);
        this.zznd = new zzc();
        CastContext.getSharedInstance().getSessionManager().addSessionManagerListener(this.zznd, CastSession.class);
        if (i != 0 && i.isConnected() != null) {
            zza(i.getRemoteMediaClient());
        }
    }

    private final void zzh(int i) {
        this.zzmt = new zzl(this, i);
    }

    public void registerCallback(Callback callback) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        this.zzne.add(callback);
    }

    public void unregisterCallback(Callback callback) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        this.zzne.remove(callback);
    }

    public int getItemCount() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzmr.size();
    }

    public int[] getItemIds() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return zzcv.zza(this.zzmr);
    }

    public void setCacheCapacity(int i) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        LruCache lruCache = this.zzmt;
        Collection arrayList = new ArrayList();
        zzh(i);
        int size = lruCache.size();
        for (Entry entry : lruCache.snapshot().entrySet()) {
            if (size > i) {
                int i2 = this.zzms.get(((Integer) entry.getKey()).intValue(), -1);
                if (i2 != -1) {
                    arrayList.add(Integer.valueOf(i2));
                }
            } else {
                this.zzmt.put((Integer) entry.getKey(), (MediaQueueItem) entry.getValue());
            }
            size--;
        }
        if (arrayList.isEmpty() == 0) {
            Collections.sort(arrayList);
            zzbi();
            zzd(zzcv.zza(arrayList));
            zzbj();
        }
    }

    @Nullable
    public MediaQueueItem getItemAtIndex(int i) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return getItemAtIndex(i, true);
    }

    @Nullable
    public MediaQueueItem getItemAtIndex(int i, boolean z) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (i >= 0) {
            if (i < this.zzmr.size()) {
                i = ((Integer) this.zzmr.get(i)).intValue();
                MediaQueueItem mediaQueueItem = (MediaQueueItem) this.zzmt.get(Integer.valueOf(i));
                if (mediaQueueItem == null && z && !this.zzmv.contains(Integer.valueOf(i))) {
                    while (this.zzmv.size() >= this.zzmw) {
                        this.zzmv.removeFirst();
                    }
                    this.zzmv.add(Integer.valueOf(i));
                    zzbb();
                }
                return mediaQueueItem;
            }
        }
        return 0;
    }

    public int itemIdAtIndex(int i) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return (i < 0 || i >= this.zzmr.size()) ? 0 : ((Integer) this.zzmr.get(i)).intValue();
    }

    public int indexOfItemWithId(int i) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzms.get(i, -1);
    }

    public PendingResult<MediaChannelResult> fetchMoreItemsRelativeToIndex(int i, int i2, int i3) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (this.zzmq) {
            if (this.zzer != 0) {
                i = itemIdAtIndex(i);
                if (i == 0) {
                    return RemoteMediaClient.zza((int) CastStatusCodes.INVALID_REQUEST, "index out of bound");
                }
                return this.zzig.zza(i, i2, i3);
            }
        }
        return RemoteMediaClient.zza(2100, "No active media session");
    }

    @VisibleForTesting
    public final void clear() {
        zzbi();
        this.zzmr.clear();
        this.zzms.clear();
        this.zzmt.evictAll();
        this.zzmu.clear();
        zzbc();
        this.zzmv.clear();
        zzbd();
        zzbe();
        zzbk();
        zzbj();
    }

    public final void reload() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (this.zzmq) {
            if (this.zzer != 0) {
                if (this.zzmz == null) {
                    zzbd();
                    zzbe();
                    this.zzmz = this.zzig.zzcc();
                    this.zzmz.setResultCallback(this.zznb);
                }
            }
        }
    }

    public final void zzbb() {
        zzbc();
        this.handler.postDelayed(this.zzmx, 500);
    }

    private final void zzbc() {
        this.handler.removeCallbacks(this.zzmx);
    }

    private final void zzbd() {
        if (this.zzmz != null) {
            this.zzmz.cancel();
            this.zzmz = null;
        }
    }

    private final void zzbe() {
        if (this.zzmy != null) {
            this.zzmy.cancel();
            this.zzmy = null;
        }
    }

    @VisibleForTesting
    final void zza(RemoteMediaClient remoteMediaClient) {
        if (remoteMediaClient != null) {
            if (this.zzig == remoteMediaClient) {
                this.zzmq = true;
                remoteMediaClient.registerCallback(this.zznc);
                long zzb = zzb(remoteMediaClient);
                this.zzer = zzb;
                if (zzb != 0) {
                    reload();
                }
            }
        }
    }

    private static long zzb(RemoteMediaClient remoteMediaClient) {
        remoteMediaClient = remoteMediaClient.getMediaStatus();
        if (remoteMediaClient != null) {
            if (!remoteMediaClient.zzk()) {
                return remoteMediaClient.zzj();
            }
        }
        return 0;
    }

    @VisibleForTesting
    final void zzbf() {
        this.zzig.unregisterCallback(this.zznc);
        this.zzmq = false;
    }

    @VisibleForTesting
    final void zzbg() {
        if (!this.zzmv.isEmpty() && this.zzmy == null && this.zzmq) {
            if (this.zzer != 0) {
                this.zzmy = this.zzig.zzf(zzcv.zza(this.zzmv));
                this.zzmy.setResultCallback(this.zzna);
                this.zzmv.clear();
            }
        }
    }

    private final void zzbh() {
        this.zzms.clear();
        for (int i = 0; i < this.zzmr.size(); i++) {
            this.zzms.put(((Integer) this.zzmr.get(i)).intValue(), i);
        }
    }

    private final void zzbi() {
        for (Callback mediaQueueWillChange : this.zzne) {
            mediaQueueWillChange.mediaQueueWillChange();
        }
    }

    private final void zzbj() {
        for (Callback mediaQueueChanged : this.zzne) {
            mediaQueueChanged.mediaQueueChanged();
        }
    }

    private final void zzbk() {
        for (Callback itemsReloaded : this.zzne) {
            itemsReloaded.itemsReloaded();
        }
    }

    private final void zzb(int i, int i2) {
        for (Callback itemsInsertedInRange : this.zzne) {
            itemsInsertedInRange.itemsInsertedInRange(i, i2);
        }
    }

    private final void zzd(int[] iArr) {
        for (Callback itemsUpdatedAtIndexes : this.zzne) {
            itemsUpdatedAtIndexes.itemsUpdatedAtIndexes(iArr);
        }
    }

    private final void zze(int[] iArr) {
        for (Callback itemsRemovedAtIndexes : this.zzne) {
            itemsRemovedAtIndexes.itemsRemovedAtIndexes(iArr);
        }
    }
}
