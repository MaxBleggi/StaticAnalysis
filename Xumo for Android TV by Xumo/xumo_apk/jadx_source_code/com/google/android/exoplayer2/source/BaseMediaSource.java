package com.google.android.exoplayer2.source;

import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSource.SourceInfoRefreshListener;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class BaseMediaSource implements MediaSource {
    private final EventDispatcher eventDispatcher = new EventDispatcher();
    @Nullable
    private Object manifest;
    @Nullable
    private ExoPlayer player;
    private final ArrayList<SourceInfoRefreshListener> sourceInfoListeners = new ArrayList(1);
    @Nullable
    private Timeline timeline;

    protected abstract void prepareSourceInternal(ExoPlayer exoPlayer, boolean z, @Nullable TransferListener transferListener);

    protected abstract void releaseSourceInternal();

    protected final void refreshSourceInfo(Timeline timeline, @Nullable Object obj) {
        this.timeline = timeline;
        this.manifest = obj;
        Iterator it = this.sourceInfoListeners.iterator();
        while (it.hasNext()) {
            ((SourceInfoRefreshListener) it.next()).onSourceInfoRefreshed(this, timeline, obj);
        }
    }

    protected final EventDispatcher createEventDispatcher(@Nullable MediaPeriodId mediaPeriodId) {
        return this.eventDispatcher.withParameters(0, mediaPeriodId, 0);
    }

    protected final EventDispatcher createEventDispatcher(MediaPeriodId mediaPeriodId, long j) {
        Assertions.checkArgument(mediaPeriodId != null);
        return this.eventDispatcher.withParameters(0, mediaPeriodId, j);
    }

    protected final EventDispatcher createEventDispatcher(int i, @Nullable MediaPeriodId mediaPeriodId, long j) {
        return this.eventDispatcher.withParameters(i, mediaPeriodId, j);
    }

    public final void addEventListener(Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        this.eventDispatcher.addEventListener(handler, mediaSourceEventListener);
    }

    public final void removeEventListener(MediaSourceEventListener mediaSourceEventListener) {
        this.eventDispatcher.removeEventListener(mediaSourceEventListener);
    }

    public final void prepareSource(ExoPlayer exoPlayer, boolean z, SourceInfoRefreshListener sourceInfoRefreshListener) {
        prepareSource(exoPlayer, z, sourceInfoRefreshListener, null);
    }

    public final void prepareSource(ExoPlayer exoPlayer, boolean z, SourceInfoRefreshListener sourceInfoRefreshListener, @Nullable TransferListener transferListener) {
        boolean z2;
        if (this.player != null) {
            if (this.player != exoPlayer) {
                z2 = false;
                Assertions.checkArgument(z2);
                this.sourceInfoListeners.add(sourceInfoRefreshListener);
                if (this.player == null) {
                    this.player = exoPlayer;
                    prepareSourceInternal(exoPlayer, z, transferListener);
                } else if (this.timeline != null) {
                    sourceInfoRefreshListener.onSourceInfoRefreshed(this, this.timeline, this.manifest);
                }
            }
        }
        z2 = true;
        Assertions.checkArgument(z2);
        this.sourceInfoListeners.add(sourceInfoRefreshListener);
        if (this.player == null) {
            this.player = exoPlayer;
            prepareSourceInternal(exoPlayer, z, transferListener);
        } else if (this.timeline != null) {
            sourceInfoRefreshListener.onSourceInfoRefreshed(this, this.timeline, this.manifest);
        }
    }

    public final void releaseSource(SourceInfoRefreshListener sourceInfoRefreshListener) {
        this.sourceInfoListeners.remove(sourceInfoRefreshListener);
        if (this.sourceInfoListeners.isEmpty() != null) {
            this.player = null;
            this.timeline = null;
            this.manifest = null;
            releaseSourceInternal();
        }
    }
}
