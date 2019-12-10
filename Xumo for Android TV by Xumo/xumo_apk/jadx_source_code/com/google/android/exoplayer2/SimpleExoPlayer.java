package com.google.android.exoplayer2;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.PlaybackParams;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlayer.ExoPlayerMessage;
import com.google.android.exoplayer2.Player.AudioComponent;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.Player.TextComponent;
import com.google.android.exoplayer2.Player.VideoComponent;
import com.google.android.exoplayer2.PlayerMessage.Target;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.analytics.AnalyticsCollector.Factory;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioAttributes.Builder;
import com.google.android.exoplayer2.audio.AudioFocusManager;
import com.google.android.exoplayer2.audio.AudioFocusManager.PlayerControl;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AuxEffectInfo;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@TargetApi(16)
public class SimpleExoPlayer implements ExoPlayer, AudioComponent, VideoComponent, TextComponent {
    private static final String TAG = "SimpleExoPlayer";
    private final AnalyticsCollector analyticsCollector;
    private AudioAttributes audioAttributes;
    private final CopyOnWriteArraySet<AudioRendererEventListener> audioDebugListeners;
    private DecoderCounters audioDecoderCounters;
    private final AudioFocusManager audioFocusManager;
    private Format audioFormat;
    private final CopyOnWriteArraySet<AudioListener> audioListeners;
    private int audioSessionId;
    private float audioVolume;
    private final BandwidthMeter bandwidthMeter;
    private CameraMotionListener cameraMotionListener;
    private final ComponentListener componentListener;
    private List<Cue> currentCues;
    private final Handler eventHandler;
    private boolean hasNotifiedFullWrongThreadWarning;
    private MediaSource mediaSource;
    private final CopyOnWriteArraySet<MetadataOutput> metadataOutputs;
    private boolean ownsSurface;
    private final ExoPlayerImpl player;
    protected final Renderer[] renderers;
    private Surface surface;
    private int surfaceHeight;
    private SurfaceHolder surfaceHolder;
    private int surfaceWidth;
    private final CopyOnWriteArraySet<TextOutput> textOutputs;
    private TextureView textureView;
    private final CopyOnWriteArraySet<VideoRendererEventListener> videoDebugListeners;
    private DecoderCounters videoDecoderCounters;
    private Format videoFormat;
    private VideoFrameMetadataListener videoFrameMetadataListener;
    private final CopyOnWriteArraySet<com.google.android.exoplayer2.video.VideoListener> videoListeners;
    private int videoScalingMode;

    private final class ComponentListener implements VideoRendererEventListener, AudioRendererEventListener, TextOutput, MetadataOutput, Callback, SurfaceTextureListener, PlayerControl {
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        private ComponentListener() {
        }

        public void onVideoEnabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer.this.videoDecoderCounters = decoderCounters;
            Iterator it = SimpleExoPlayer.this.videoDebugListeners.iterator();
            while (it.hasNext()) {
                ((VideoRendererEventListener) it.next()).onVideoEnabled(decoderCounters);
            }
        }

        public void onVideoDecoderInitialized(String str, long j, long j2) {
            Iterator it = SimpleExoPlayer.this.videoDebugListeners.iterator();
            while (it.hasNext()) {
                ((VideoRendererEventListener) it.next()).onVideoDecoderInitialized(str, j, j2);
            }
        }

        public void onVideoInputFormatChanged(Format format) {
            SimpleExoPlayer.this.videoFormat = format;
            Iterator it = SimpleExoPlayer.this.videoDebugListeners.iterator();
            while (it.hasNext()) {
                ((VideoRendererEventListener) it.next()).onVideoInputFormatChanged(format);
            }
        }

        public void onDroppedFrames(int i, long j) {
            Iterator it = SimpleExoPlayer.this.videoDebugListeners.iterator();
            while (it.hasNext()) {
                ((VideoRendererEventListener) it.next()).onDroppedFrames(i, j);
            }
        }

        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
            Iterator it = SimpleExoPlayer.this.videoListeners.iterator();
            while (it.hasNext()) {
                com.google.android.exoplayer2.video.VideoListener videoListener = (com.google.android.exoplayer2.video.VideoListener) it.next();
                if (!SimpleExoPlayer.this.videoDebugListeners.contains(videoListener)) {
                    videoListener.onVideoSizeChanged(i, i2, i3, f);
                }
            }
            it = SimpleExoPlayer.this.videoDebugListeners.iterator();
            while (it.hasNext()) {
                ((VideoRendererEventListener) it.next()).onVideoSizeChanged(i, i2, i3, f);
            }
        }

        public void onRenderedFirstFrame(Surface surface) {
            Iterator it;
            if (SimpleExoPlayer.this.surface == surface) {
                it = SimpleExoPlayer.this.videoListeners.iterator();
                while (it.hasNext()) {
                    ((com.google.android.exoplayer2.video.VideoListener) it.next()).onRenderedFirstFrame();
                }
            }
            it = SimpleExoPlayer.this.videoDebugListeners.iterator();
            while (it.hasNext()) {
                ((VideoRendererEventListener) it.next()).onRenderedFirstFrame(surface);
            }
        }

        public void onVideoDisabled(DecoderCounters decoderCounters) {
            Iterator it = SimpleExoPlayer.this.videoDebugListeners.iterator();
            while (it.hasNext()) {
                ((VideoRendererEventListener) it.next()).onVideoDisabled(decoderCounters);
            }
            SimpleExoPlayer.this.videoFormat = null;
            SimpleExoPlayer.this.videoDecoderCounters = null;
        }

        public void onAudioEnabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer.this.audioDecoderCounters = decoderCounters;
            Iterator it = SimpleExoPlayer.this.audioDebugListeners.iterator();
            while (it.hasNext()) {
                ((AudioRendererEventListener) it.next()).onAudioEnabled(decoderCounters);
            }
        }

        public void onAudioSessionId(int i) {
            if (SimpleExoPlayer.this.audioSessionId != i) {
                SimpleExoPlayer.this.audioSessionId = i;
                Iterator it = SimpleExoPlayer.this.audioListeners.iterator();
                while (it.hasNext()) {
                    AudioListener audioListener = (AudioListener) it.next();
                    if (!SimpleExoPlayer.this.audioDebugListeners.contains(audioListener)) {
                        audioListener.onAudioSessionId(i);
                    }
                }
                it = SimpleExoPlayer.this.audioDebugListeners.iterator();
                while (it.hasNext()) {
                    ((AudioRendererEventListener) it.next()).onAudioSessionId(i);
                }
            }
        }

        public void onAudioDecoderInitialized(String str, long j, long j2) {
            Iterator it = SimpleExoPlayer.this.audioDebugListeners.iterator();
            while (it.hasNext()) {
                ((AudioRendererEventListener) it.next()).onAudioDecoderInitialized(str, j, j2);
            }
        }

        public void onAudioInputFormatChanged(Format format) {
            SimpleExoPlayer.this.audioFormat = format;
            Iterator it = SimpleExoPlayer.this.audioDebugListeners.iterator();
            while (it.hasNext()) {
                ((AudioRendererEventListener) it.next()).onAudioInputFormatChanged(format);
            }
        }

        public void onAudioSinkUnderrun(int i, long j, long j2) {
            Iterator it = SimpleExoPlayer.this.audioDebugListeners.iterator();
            while (it.hasNext()) {
                ((AudioRendererEventListener) it.next()).onAudioSinkUnderrun(i, j, j2);
            }
        }

        public void onAudioDisabled(DecoderCounters decoderCounters) {
            Iterator it = SimpleExoPlayer.this.audioDebugListeners.iterator();
            while (it.hasNext()) {
                ((AudioRendererEventListener) it.next()).onAudioDisabled(decoderCounters);
            }
            SimpleExoPlayer.this.audioFormat = null;
            SimpleExoPlayer.this.audioDecoderCounters = null;
            SimpleExoPlayer.this.audioSessionId = 0;
        }

        public void onCues(List<Cue> list) {
            SimpleExoPlayer.this.currentCues = list;
            Iterator it = SimpleExoPlayer.this.textOutputs.iterator();
            while (it.hasNext()) {
                ((TextOutput) it.next()).onCues(list);
            }
        }

        public void onMetadata(Metadata metadata) {
            Iterator it = SimpleExoPlayer.this.metadataOutputs.iterator();
            while (it.hasNext()) {
                ((MetadataOutput) it.next()).onMetadata(metadata);
            }
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            SimpleExoPlayer.this.setVideoSurfaceInternal(surfaceHolder.getSurface(), false);
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(i2, i3);
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            SimpleExoPlayer.this.setVideoSurfaceInternal(null, false);
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(0, 0);
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            SimpleExoPlayer.this.setVideoSurfaceInternal(new Surface(surfaceTexture), true);
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(i, i2);
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(i, i2);
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            SimpleExoPlayer.this.setVideoSurfaceInternal(null, true);
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(0, 0);
            return true;
        }

        public void setVolumeMultiplier(float f) {
            SimpleExoPlayer.this.sendVolumeToRenderers();
        }

        public void executePlayerCommand(int i) {
            SimpleExoPlayer.this.updatePlayWhenReady(SimpleExoPlayer.this.getPlayWhenReady(), i);
        }
    }

    @Deprecated
    public interface VideoListener extends com.google.android.exoplayer2.video.VideoListener {
    }

    public AudioComponent getAudioComponent() {
        return this;
    }

    public TextComponent getTextComponent() {
        return this;
    }

    public VideoComponent getVideoComponent() {
        return this;
    }

    protected SimpleExoPlayer(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, BandwidthMeter bandwidthMeter, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, Looper looper) {
        this(context, renderersFactory, trackSelector, loadControl, drmSessionManager, bandwidthMeter, new Factory(), looper);
    }

    protected SimpleExoPlayer(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, BandwidthMeter bandwidthMeter, Factory factory, Looper looper) {
        this(context, renderersFactory, trackSelector, loadControl, drmSessionManager, bandwidthMeter, factory, Clock.DEFAULT, looper);
    }

    protected SimpleExoPlayer(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, BandwidthMeter bandwidthMeter, Factory factory, Clock clock, Looper looper) {
        DrmSessionManager<FrameworkMediaCrypto> drmSessionManager2 = drmSessionManager;
        BandwidthMeter bandwidthMeter2 = bandwidthMeter;
        this.bandwidthMeter = bandwidthMeter2;
        this.componentListener = new ComponentListener();
        this.videoListeners = new CopyOnWriteArraySet();
        this.audioListeners = new CopyOnWriteArraySet();
        this.textOutputs = new CopyOnWriteArraySet();
        this.metadataOutputs = new CopyOnWriteArraySet();
        this.videoDebugListeners = new CopyOnWriteArraySet();
        this.audioDebugListeners = new CopyOnWriteArraySet();
        this.eventHandler = new Handler(looper);
        this.renderers = renderersFactory.createRenderers(this.eventHandler, this.componentListener, this.componentListener, this.componentListener, this.componentListener, drmSessionManager);
        this.audioVolume = 1.0f;
        this.audioSessionId = 0;
        this.audioAttributes = AudioAttributes.DEFAULT;
        this.videoScalingMode = 1;
        this.currentCues = Collections.emptyList();
        this.player = new ExoPlayerImpl(this.renderers, trackSelector, loadControl, bandwidthMeter, clock, looper);
        this.analyticsCollector = factory.createAnalyticsCollector(this.player, clock);
        addListener(this.analyticsCollector);
        this.videoDebugListeners.add(this.analyticsCollector);
        this.videoListeners.add(this.analyticsCollector);
        this.audioDebugListeners.add(this.analyticsCollector);
        this.audioListeners.add(this.analyticsCollector);
        addMetadataOutput(this.analyticsCollector);
        bandwidthMeter2.addEventListener(this.eventHandler, this.analyticsCollector);
        if (drmSessionManager2 instanceof DefaultDrmSessionManager) {
            ((DefaultDrmSessionManager) drmSessionManager2).addListener(r0.eventHandler, r0.analyticsCollector);
        }
        Context context2 = context;
        r0.audioFocusManager = new AudioFocusManager(context, r0.componentListener);
    }

    public void setVideoScalingMode(int i) {
        verifyApplicationThread();
        this.videoScalingMode = i;
        for (Target target : this.renderers) {
            if (target.getTrackType() == 2) {
                this.player.createMessage(target).setType(4).setPayload(Integer.valueOf(i)).send();
            }
        }
    }

    public int getVideoScalingMode() {
        return this.videoScalingMode;
    }

    public void clearVideoSurface() {
        verifyApplicationThread();
        setVideoSurface(null);
    }

    public void clearVideoSurface(Surface surface) {
        verifyApplicationThread();
        if (surface != null && surface == this.surface) {
            setVideoSurface(null);
        }
    }

    public void setVideoSurface(@Nullable Surface surface) {
        verifyApplicationThread();
        removeSurfaceCallbacks();
        int i = 0;
        setVideoSurfaceInternal(surface, false);
        if (surface != null) {
            i = -1;
        }
        maybeNotifySurfaceSizeChanged(i, i);
    }

    public void setVideoSurfaceHolder(SurfaceHolder surfaceHolder) {
        verifyApplicationThread();
        removeSurfaceCallbacks();
        this.surfaceHolder = surfaceHolder;
        if (surfaceHolder == null) {
            setVideoSurfaceInternal(null, false);
            maybeNotifySurfaceSizeChanged(0, 0);
            return;
        }
        surfaceHolder.addCallback(this.componentListener);
        Surface surface = surfaceHolder.getSurface();
        if (surface == null || !surface.isValid()) {
            setVideoSurfaceInternal(null, false);
            maybeNotifySurfaceSizeChanged(0, 0);
            return;
        }
        setVideoSurfaceInternal(surface, false);
        surfaceHolder = surfaceHolder.getSurfaceFrame();
        maybeNotifySurfaceSizeChanged(surfaceHolder.width(), surfaceHolder.height());
    }

    public void clearVideoSurfaceHolder(SurfaceHolder surfaceHolder) {
        verifyApplicationThread();
        if (surfaceHolder != null && surfaceHolder == this.surfaceHolder) {
            setVideoSurfaceHolder(null);
        }
    }

    public void setVideoSurfaceView(SurfaceView surfaceView) {
        setVideoSurfaceHolder(surfaceView == null ? null : surfaceView.getHolder());
    }

    public void clearVideoSurfaceView(SurfaceView surfaceView) {
        clearVideoSurfaceHolder(surfaceView == null ? null : surfaceView.getHolder());
    }

    public void setVideoTextureView(TextureView textureView) {
        verifyApplicationThread();
        removeSurfaceCallbacks();
        this.textureView = textureView;
        if (textureView == null) {
            setVideoSurfaceInternal(null, true);
            maybeNotifySurfaceSizeChanged(0, 0);
            return;
        }
        if (textureView.getSurfaceTextureListener() != null) {
            Log.w(TAG, "Replacing existing SurfaceTextureListener.");
        }
        textureView.setSurfaceTextureListener(this.componentListener);
        SurfaceTexture surfaceTexture = textureView.isAvailable() ? textureView.getSurfaceTexture() : null;
        if (surfaceTexture == null) {
            setVideoSurfaceInternal(null, true);
            maybeNotifySurfaceSizeChanged(0, 0);
            return;
        }
        setVideoSurfaceInternal(new Surface(surfaceTexture), true);
        maybeNotifySurfaceSizeChanged(textureView.getWidth(), textureView.getHeight());
    }

    public void clearVideoTextureView(TextureView textureView) {
        verifyApplicationThread();
        if (textureView != null && textureView == this.textureView) {
            setVideoTextureView(null);
        }
    }

    public void addAudioListener(AudioListener audioListener) {
        this.audioListeners.add(audioListener);
    }

    public void removeAudioListener(AudioListener audioListener) {
        this.audioListeners.remove(audioListener);
    }

    public void setAudioAttributes(AudioAttributes audioAttributes) {
        setAudioAttributes(audioAttributes, false);
    }

    public void setAudioAttributes(AudioAttributes audioAttributes, boolean z) {
        verifyApplicationThread();
        if (!Util.areEqual(this.audioAttributes, audioAttributes)) {
            this.audioAttributes = audioAttributes;
            for (Target target : this.renderers) {
                if (target.getTrackType() == 1) {
                    this.player.createMessage(target).setType(3).setPayload(audioAttributes).send();
                }
            }
            Iterator it = this.audioListeners.iterator();
            while (it.hasNext()) {
                ((AudioListener) it.next()).onAudioAttributesChanged(audioAttributes);
            }
        }
        AudioFocusManager audioFocusManager = this.audioFocusManager;
        if (!z) {
            audioAttributes = null;
        }
        updatePlayWhenReady(getPlayWhenReady(), audioFocusManager.setAudioAttributes(audioAttributes, getPlayWhenReady(), getPlaybackState()));
    }

    public AudioAttributes getAudioAttributes() {
        return this.audioAttributes;
    }

    public int getAudioSessionId() {
        return this.audioSessionId;
    }

    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        verifyApplicationThread();
        for (Target target : this.renderers) {
            if (target.getTrackType() == 1) {
                this.player.createMessage(target).setType(5).setPayload(auxEffectInfo).send();
            }
        }
    }

    public void clearAuxEffectInfo() {
        setAuxEffectInfo(new AuxEffectInfo(0, 0.0f));
    }

    public void setVolume(float f) {
        verifyApplicationThread();
        f = Util.constrainValue(f, 0.0f, 1.0f);
        if (this.audioVolume != f) {
            this.audioVolume = f;
            sendVolumeToRenderers();
            Iterator it = this.audioListeners.iterator();
            while (it.hasNext()) {
                ((AudioListener) it.next()).onVolumeChanged(f);
            }
        }
    }

    public float getVolume() {
        return this.audioVolume;
    }

    @Deprecated
    public void setAudioStreamType(int i) {
        int audioUsageForStreamType = Util.getAudioUsageForStreamType(i);
        setAudioAttributes(new Builder().setUsage(audioUsageForStreamType).setContentType(Util.getAudioContentTypeForStreamType(i)).build());
    }

    @Deprecated
    public int getAudioStreamType() {
        return Util.getStreamTypeForAudioUsage(this.audioAttributes.usage);
    }

    public AnalyticsCollector getAnalyticsCollector() {
        return this.analyticsCollector;
    }

    public void addAnalyticsListener(AnalyticsListener analyticsListener) {
        verifyApplicationThread();
        this.analyticsCollector.addListener(analyticsListener);
    }

    public void removeAnalyticsListener(AnalyticsListener analyticsListener) {
        verifyApplicationThread();
        this.analyticsCollector.removeListener(analyticsListener);
    }

    @TargetApi(23)
    @Deprecated
    public void setPlaybackParams(@Nullable PlaybackParams playbackParams) {
        PlaybackParameters playbackParameters;
        if (playbackParams != null) {
            playbackParams.allowDefaults();
            playbackParameters = new PlaybackParameters(playbackParams.getSpeed(), playbackParams.getPitch());
        } else {
            playbackParameters = null;
        }
        setPlaybackParameters(playbackParameters);
    }

    public Format getVideoFormat() {
        return this.videoFormat;
    }

    public Format getAudioFormat() {
        return this.audioFormat;
    }

    public DecoderCounters getVideoDecoderCounters() {
        return this.videoDecoderCounters;
    }

    public DecoderCounters getAudioDecoderCounters() {
        return this.audioDecoderCounters;
    }

    public void addVideoListener(com.google.android.exoplayer2.video.VideoListener videoListener) {
        this.videoListeners.add(videoListener);
    }

    public void removeVideoListener(com.google.android.exoplayer2.video.VideoListener videoListener) {
        this.videoListeners.remove(videoListener);
    }

    public void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        verifyApplicationThread();
        this.videoFrameMetadataListener = videoFrameMetadataListener;
        for (Target target : this.renderers) {
            if (target.getTrackType() == 2) {
                this.player.createMessage(target).setType(6).setPayload(videoFrameMetadataListener).send();
            }
        }
    }

    public void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        verifyApplicationThread();
        if (this.videoFrameMetadataListener == videoFrameMetadataListener) {
            for (Target target : this.renderers) {
                if (target.getTrackType() == 2) {
                    this.player.createMessage(target).setType(6).setPayload(null).send();
                }
            }
        }
    }

    public void setCameraMotionListener(CameraMotionListener cameraMotionListener) {
        verifyApplicationThread();
        this.cameraMotionListener = cameraMotionListener;
        for (Target target : this.renderers) {
            if (target.getTrackType() == 5) {
                this.player.createMessage(target).setType(7).setPayload(cameraMotionListener).send();
            }
        }
    }

    public void clearCameraMotionListener(CameraMotionListener cameraMotionListener) {
        verifyApplicationThread();
        if (this.cameraMotionListener == cameraMotionListener) {
            for (Target target : this.renderers) {
                if (target.getTrackType() == 5) {
                    this.player.createMessage(target).setType(7).setPayload(null).send();
                }
            }
        }
    }

    @Deprecated
    public void setVideoListener(VideoListener videoListener) {
        this.videoListeners.clear();
        if (videoListener != null) {
            addVideoListener(videoListener);
        }
    }

    @Deprecated
    public void clearVideoListener(VideoListener videoListener) {
        removeVideoListener(videoListener);
    }

    public void addTextOutput(TextOutput textOutput) {
        if (!this.currentCues.isEmpty()) {
            textOutput.onCues(this.currentCues);
        }
        this.textOutputs.add(textOutput);
    }

    public void removeTextOutput(TextOutput textOutput) {
        this.textOutputs.remove(textOutput);
    }

    @Deprecated
    public void setTextOutput(TextOutput textOutput) {
        this.textOutputs.clear();
        if (textOutput != null) {
            addTextOutput(textOutput);
        }
    }

    @Deprecated
    public void clearTextOutput(TextOutput textOutput) {
        removeTextOutput(textOutput);
    }

    public void addMetadataOutput(MetadataOutput metadataOutput) {
        this.metadataOutputs.add(metadataOutput);
    }

    public void removeMetadataOutput(MetadataOutput metadataOutput) {
        this.metadataOutputs.remove(metadataOutput);
    }

    @Deprecated
    public void setMetadataOutput(MetadataOutput metadataOutput) {
        this.metadataOutputs.retainAll(Collections.singleton(this.analyticsCollector));
        if (metadataOutput != null) {
            addMetadataOutput(metadataOutput);
        }
    }

    @Deprecated
    public void clearMetadataOutput(MetadataOutput metadataOutput) {
        removeMetadataOutput(metadataOutput);
    }

    @Deprecated
    public void setVideoDebugListener(VideoRendererEventListener videoRendererEventListener) {
        this.videoDebugListeners.retainAll(Collections.singleton(this.analyticsCollector));
        if (videoRendererEventListener != null) {
            addVideoDebugListener(videoRendererEventListener);
        }
    }

    @Deprecated
    public void addVideoDebugListener(VideoRendererEventListener videoRendererEventListener) {
        this.videoDebugListeners.add(videoRendererEventListener);
    }

    @Deprecated
    public void removeVideoDebugListener(VideoRendererEventListener videoRendererEventListener) {
        this.videoDebugListeners.remove(videoRendererEventListener);
    }

    @Deprecated
    public void setAudioDebugListener(AudioRendererEventListener audioRendererEventListener) {
        this.audioDebugListeners.retainAll(Collections.singleton(this.analyticsCollector));
        if (audioRendererEventListener != null) {
            addAudioDebugListener(audioRendererEventListener);
        }
    }

    @Deprecated
    public void addAudioDebugListener(AudioRendererEventListener audioRendererEventListener) {
        this.audioDebugListeners.add(audioRendererEventListener);
    }

    @Deprecated
    public void removeAudioDebugListener(AudioRendererEventListener audioRendererEventListener) {
        this.audioDebugListeners.remove(audioRendererEventListener);
    }

    public Looper getPlaybackLooper() {
        return this.player.getPlaybackLooper();
    }

    public Looper getApplicationLooper() {
        return this.player.getApplicationLooper();
    }

    public void addListener(EventListener eventListener) {
        verifyApplicationThread();
        this.player.addListener(eventListener);
    }

    public void removeListener(EventListener eventListener) {
        verifyApplicationThread();
        this.player.removeListener(eventListener);
    }

    public int getPlaybackState() {
        verifyApplicationThread();
        return this.player.getPlaybackState();
    }

    @Nullable
    public ExoPlaybackException getPlaybackError() {
        verifyApplicationThread();
        return this.player.getPlaybackError();
    }

    public void retry() {
        verifyApplicationThread();
        if (this.mediaSource == null) {
            return;
        }
        if (getPlaybackError() != null || getPlaybackState() == 1) {
            prepare(this.mediaSource, false, false);
        }
    }

    public void prepare(MediaSource mediaSource) {
        prepare(mediaSource, true, true);
    }

    public void prepare(MediaSource mediaSource, boolean z, boolean z2) {
        verifyApplicationThread();
        if (this.mediaSource != null) {
            this.mediaSource.removeEventListener(this.analyticsCollector);
            this.analyticsCollector.resetForNewMediaSource();
        }
        this.mediaSource = mediaSource;
        mediaSource.addEventListener(this.eventHandler, this.analyticsCollector);
        updatePlayWhenReady(getPlayWhenReady(), this.audioFocusManager.handlePrepare(getPlayWhenReady()));
        this.player.prepare(mediaSource, z, z2);
    }

    public void setPlayWhenReady(boolean z) {
        verifyApplicationThread();
        updatePlayWhenReady(z, this.audioFocusManager.handleSetPlayWhenReady(z, getPlaybackState()));
    }

    public boolean getPlayWhenReady() {
        verifyApplicationThread();
        return this.player.getPlayWhenReady();
    }

    public int getRepeatMode() {
        verifyApplicationThread();
        return this.player.getRepeatMode();
    }

    public void setRepeatMode(int i) {
        verifyApplicationThread();
        this.player.setRepeatMode(i);
    }

    public void setShuffleModeEnabled(boolean z) {
        verifyApplicationThread();
        this.player.setShuffleModeEnabled(z);
    }

    public boolean getShuffleModeEnabled() {
        verifyApplicationThread();
        return this.player.getShuffleModeEnabled();
    }

    public boolean isLoading() {
        verifyApplicationThread();
        return this.player.isLoading();
    }

    public void seekToDefaultPosition() {
        verifyApplicationThread();
        this.analyticsCollector.notifySeekStarted();
        this.player.seekToDefaultPosition();
    }

    public void seekToDefaultPosition(int i) {
        verifyApplicationThread();
        this.analyticsCollector.notifySeekStarted();
        this.player.seekToDefaultPosition(i);
    }

    public void seekTo(long j) {
        verifyApplicationThread();
        this.analyticsCollector.notifySeekStarted();
        this.player.seekTo(j);
    }

    public void seekTo(int i, long j) {
        verifyApplicationThread();
        this.analyticsCollector.notifySeekStarted();
        this.player.seekTo(i, j);
    }

    public void setPlaybackParameters(@Nullable PlaybackParameters playbackParameters) {
        verifyApplicationThread();
        this.player.setPlaybackParameters(playbackParameters);
    }

    public PlaybackParameters getPlaybackParameters() {
        verifyApplicationThread();
        return this.player.getPlaybackParameters();
    }

    public void setSeekParameters(@Nullable SeekParameters seekParameters) {
        verifyApplicationThread();
        this.player.setSeekParameters(seekParameters);
    }

    public SeekParameters getSeekParameters() {
        verifyApplicationThread();
        return this.player.getSeekParameters();
    }

    @Nullable
    public Object getCurrentTag() {
        verifyApplicationThread();
        return this.player.getCurrentTag();
    }

    public void stop() {
        stop(false);
    }

    public void stop(boolean z) {
        verifyApplicationThread();
        this.player.stop(z);
        if (this.mediaSource != null) {
            this.mediaSource.removeEventListener(this.analyticsCollector);
            this.analyticsCollector.resetForNewMediaSource();
            if (z) {
                this.mediaSource = false;
            }
        }
        this.audioFocusManager.handleStop();
        this.currentCues = Collections.emptyList();
    }

    public void release() {
        this.audioFocusManager.handleStop();
        this.player.release();
        removeSurfaceCallbacks();
        if (this.surface != null) {
            if (this.ownsSurface) {
                this.surface.release();
            }
            this.surface = null;
        }
        if (this.mediaSource != null) {
            this.mediaSource.removeEventListener(this.analyticsCollector);
            this.mediaSource = null;
        }
        this.bandwidthMeter.removeEventListener(this.analyticsCollector);
        this.currentCues = Collections.emptyList();
    }

    @Deprecated
    public void sendMessages(ExoPlayerMessage... exoPlayerMessageArr) {
        this.player.sendMessages(exoPlayerMessageArr);
    }

    public PlayerMessage createMessage(Target target) {
        verifyApplicationThread();
        return this.player.createMessage(target);
    }

    @Deprecated
    public void blockingSendMessages(ExoPlayerMessage... exoPlayerMessageArr) {
        this.player.blockingSendMessages(exoPlayerMessageArr);
    }

    public int getRendererCount() {
        verifyApplicationThread();
        return this.player.getRendererCount();
    }

    public int getRendererType(int i) {
        verifyApplicationThread();
        return this.player.getRendererType(i);
    }

    public TrackGroupArray getCurrentTrackGroups() {
        verifyApplicationThread();
        return this.player.getCurrentTrackGroups();
    }

    public TrackSelectionArray getCurrentTrackSelections() {
        verifyApplicationThread();
        return this.player.getCurrentTrackSelections();
    }

    public Timeline getCurrentTimeline() {
        verifyApplicationThread();
        return this.player.getCurrentTimeline();
    }

    @Nullable
    public Object getCurrentManifest() {
        verifyApplicationThread();
        return this.player.getCurrentManifest();
    }

    public int getCurrentPeriodIndex() {
        verifyApplicationThread();
        return this.player.getCurrentPeriodIndex();
    }

    public int getCurrentWindowIndex() {
        verifyApplicationThread();
        return this.player.getCurrentWindowIndex();
    }

    public int getNextWindowIndex() {
        verifyApplicationThread();
        return this.player.getNextWindowIndex();
    }

    public int getPreviousWindowIndex() {
        verifyApplicationThread();
        return this.player.getPreviousWindowIndex();
    }

    public long getDuration() {
        verifyApplicationThread();
        return this.player.getDuration();
    }

    public long getCurrentPosition() {
        verifyApplicationThread();
        return this.player.getCurrentPosition();
    }

    public long getBufferedPosition() {
        verifyApplicationThread();
        return this.player.getBufferedPosition();
    }

    public int getBufferedPercentage() {
        verifyApplicationThread();
        return this.player.getBufferedPercentage();
    }

    public long getTotalBufferedDuration() {
        verifyApplicationThread();
        return this.player.getTotalBufferedDuration();
    }

    public boolean isCurrentWindowDynamic() {
        verifyApplicationThread();
        return this.player.isCurrentWindowDynamic();
    }

    public boolean isCurrentWindowSeekable() {
        verifyApplicationThread();
        return this.player.isCurrentWindowSeekable();
    }

    public boolean isPlayingAd() {
        verifyApplicationThread();
        return this.player.isPlayingAd();
    }

    public int getCurrentAdGroupIndex() {
        verifyApplicationThread();
        return this.player.getCurrentAdGroupIndex();
    }

    public int getCurrentAdIndexInAdGroup() {
        verifyApplicationThread();
        return this.player.getCurrentAdIndexInAdGroup();
    }

    public long getContentDuration() {
        verifyApplicationThread();
        return this.player.getContentDuration();
    }

    public long getContentPosition() {
        verifyApplicationThread();
        return this.player.getContentPosition();
    }

    public long getContentBufferedPosition() {
        verifyApplicationThread();
        return this.player.getContentBufferedPosition();
    }

    private void removeSurfaceCallbacks() {
        if (this.textureView != null) {
            if (this.textureView.getSurfaceTextureListener() != this.componentListener) {
                Log.w(TAG, "SurfaceTextureListener already unset or replaced.");
            } else {
                this.textureView.setSurfaceTextureListener(null);
            }
            this.textureView = null;
        }
        if (this.surfaceHolder != null) {
            this.surfaceHolder.removeCallback(this.componentListener);
            this.surfaceHolder = null;
        }
    }

    private void setVideoSurfaceInternal(@androidx.annotation.Nullable android.view.Surface r8, boolean r9) {
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
        r7 = this;
        r0 = new java.util.ArrayList;
        r0.<init>();
        r1 = r7.renderers;
        r2 = r1.length;
        r3 = 0;
    L_0x0009:
        if (r3 >= r2) goto L_0x002d;
    L_0x000b:
        r4 = r1[r3];
        r5 = r4.getTrackType();
        r6 = 2;
        if (r5 != r6) goto L_0x002a;
    L_0x0014:
        r5 = r7.player;
        r4 = r5.createMessage(r4);
        r5 = 1;
        r4 = r4.setType(r5);
        r4 = r4.setPayload(r8);
        r4 = r4.send();
        r0.add(r4);
    L_0x002a:
        r3 = r3 + 1;
        goto L_0x0009;
    L_0x002d:
        r1 = r7.surface;
        if (r1 == 0) goto L_0x0059;
    L_0x0031:
        r1 = r7.surface;
        if (r1 == r8) goto L_0x0059;
    L_0x0035:
        r0 = r0.iterator();	 Catch:{ InterruptedException -> 0x0049 }
    L_0x0039:
        r1 = r0.hasNext();	 Catch:{ InterruptedException -> 0x0049 }
        if (r1 == 0) goto L_0x0050;	 Catch:{ InterruptedException -> 0x0049 }
    L_0x003f:
        r1 = r0.next();	 Catch:{ InterruptedException -> 0x0049 }
        r1 = (com.google.android.exoplayer2.PlayerMessage) r1;	 Catch:{ InterruptedException -> 0x0049 }
        r1.blockUntilDelivered();	 Catch:{ InterruptedException -> 0x0049 }
        goto L_0x0039;
    L_0x0049:
        r0 = java.lang.Thread.currentThread();
        r0.interrupt();
    L_0x0050:
        r0 = r7.ownsSurface;
        if (r0 == 0) goto L_0x0059;
    L_0x0054:
        r0 = r7.surface;
        r0.release();
    L_0x0059:
        r7.surface = r8;
        r7.ownsSurface = r9;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.SimpleExoPlayer.setVideoSurfaceInternal(android.view.Surface, boolean):void");
    }

    private void maybeNotifySurfaceSizeChanged(int i, int i2) {
        if (i != this.surfaceWidth || i2 != this.surfaceHeight) {
            this.surfaceWidth = i;
            this.surfaceHeight = i2;
            Iterator it = this.videoListeners.iterator();
            while (it.hasNext()) {
                ((com.google.android.exoplayer2.video.VideoListener) it.next()).onSurfaceSizeChanged(i, i2);
            }
        }
    }

    private void sendVolumeToRenderers() {
        float volumeMultiplier = this.audioVolume * this.audioFocusManager.getVolumeMultiplier();
        for (Target target : this.renderers) {
            if (target.getTrackType() == 1) {
                this.player.createMessage(target).setType(2).setPayload(Float.valueOf(volumeMultiplier)).send();
            }
        }
    }

    private void updatePlayWhenReady(boolean z, int i) {
        ExoPlayerImpl exoPlayerImpl = this.player;
        boolean z2 = false;
        z = z && i != true;
        if (i != 1) {
            z2 = true;
        }
        exoPlayerImpl.setPlayWhenReady(z, z2);
    }

    private void verifyApplicationThread() {
        if (Looper.myLooper() != getApplicationLooper()) {
            Log.w(TAG, "Player is accessed on the wrong thread. See https://google.github.io/ExoPlayer/faqs.html#what-do-player-is-accessed-on-the-wrong-thread-warnings-mean", this.hasNotifiedFullWrongThreadWarning ? null : new IllegalStateException());
            this.hasNotifiedFullWrongThreadWarning = true;
        }
    }
}
