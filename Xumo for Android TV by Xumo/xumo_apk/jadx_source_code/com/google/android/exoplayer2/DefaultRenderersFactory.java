package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.metadata.MetadataRenderer;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.text.TextRenderer;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.spherical.CameraMotionRenderer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class DefaultRenderersFactory implements RenderersFactory {
    public static final long DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS = 5000;
    public static final int EXTENSION_RENDERER_MODE_OFF = 0;
    public static final int EXTENSION_RENDERER_MODE_ON = 1;
    public static final int EXTENSION_RENDERER_MODE_PREFER = 2;
    protected static final int MAX_DROPPED_VIDEO_FRAME_COUNT_TO_NOTIFY = 50;
    private static final String TAG = "DefaultRenderersFactory";
    private final long allowedVideoJoiningTimeMs;
    private final Context context;
    @Nullable
    private final DrmSessionManager<FrameworkMediaCrypto> drmSessionManager;
    private final int extensionRendererMode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ExtensionRendererMode {
    }

    protected void buildMiscellaneousRenderers(Context context, Handler handler, int i, ArrayList<Renderer> arrayList) {
    }

    public DefaultRenderersFactory(Context context) {
        this(context, 0);
    }

    @Deprecated
    public DefaultRenderersFactory(Context context, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager) {
        this(context, (DrmSessionManager) drmSessionManager, 0);
    }

    public DefaultRenderersFactory(Context context, int i) {
        this(context, i, 5000);
    }

    @Deprecated
    public DefaultRenderersFactory(Context context, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, int i) {
        this(context, drmSessionManager, i, 5000);
    }

    public DefaultRenderersFactory(Context context, int i, long j) {
        this.context = context;
        this.extensionRendererMode = i;
        this.allowedVideoJoiningTimeMs = j;
        this.drmSessionManager = null;
    }

    @Deprecated
    public DefaultRenderersFactory(Context context, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, int i, long j) {
        this.context = context;
        this.extensionRendererMode = i;
        this.allowedVideoJoiningTimeMs = j;
        this.drmSessionManager = drmSessionManager;
    }

    public Renderer[] createRenderers(Handler handler, VideoRendererEventListener videoRendererEventListener, AudioRendererEventListener audioRendererEventListener, TextOutput textOutput, MetadataOutput metadataOutput, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager) {
        DrmSessionManager drmSessionManager2 = drmSessionManager == null ? this.drmSessionManager : drmSessionManager;
        ArrayList arrayList = new ArrayList();
        DrmSessionManager drmSessionManager3 = drmSessionManager2;
        buildVideoRenderers(r9.context, drmSessionManager3, r9.allowedVideoJoiningTimeMs, handler, videoRendererEventListener, r9.extensionRendererMode, arrayList);
        buildAudioRenderers(r9.context, drmSessionManager3, buildAudioProcessors(), handler, audioRendererEventListener, r9.extensionRendererMode, arrayList);
        ArrayList arrayList2 = arrayList;
        buildTextRenderers(r9.context, textOutput, handler.getLooper(), r9.extensionRendererMode, arrayList2);
        buildMetadataRenderers(r9.context, metadataOutput, handler.getLooper(), r9.extensionRendererMode, arrayList2);
        buildCameraMotionRenderers(r9.context, r9.extensionRendererMode, arrayList);
        Handler handler2 = handler;
        buildMiscellaneousRenderers(r9.context, handler, r9.extensionRendererMode, arrayList);
        return (Renderer[]) arrayList.toArray(new Renderer[arrayList.size()]);
    }

    protected void buildVideoRenderers(android.content.Context r14, @androidx.annotation.Nullable com.google.android.exoplayer2.drm.DrmSessionManager<com.google.android.exoplayer2.drm.FrameworkMediaCrypto> r15, long r16, android.os.Handler r18, com.google.android.exoplayer2.video.VideoRendererEventListener r19, int r20, java.util.ArrayList<com.google.android.exoplayer2.Renderer> r21) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r13 = this;
        r0 = r20;
        r1 = r21;
        r12 = new com.google.android.exoplayer2.video.MediaCodecVideoRenderer;
        r4 = com.google.android.exoplayer2.mediacodec.MediaCodecSelector.DEFAULT;
        r8 = 0;
        r11 = 50;
        r2 = r12;
        r3 = r14;
        r5 = r16;
        r7 = r15;
        r9 = r18;
        r10 = r19;
        r2.<init>(r3, r4, r5, r7, r8, r9, r10, r11);
        r1.add(r12);
        if (r0 != 0) goto L_0x001d;
    L_0x001c:
        return;
    L_0x001d:
        r2 = r21.size();
        r3 = 2;
        if (r0 != r3) goto L_0x0026;
    L_0x0024:
        r2 = r2 + -1;
    L_0x0026:
        r0 = "com.google.android.exoplayer2.ext.vp9.LibvpxVideoRenderer";	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r0 = java.lang.Class.forName(r0);	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r4 = 5;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r5 = new java.lang.Class[r4];	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r6 = java.lang.Boolean.TYPE;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r7 = 0;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r5[r7] = r6;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r6 = java.lang.Long.TYPE;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r8 = 1;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r5[r8] = r6;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r6 = android.os.Handler.class;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r5[r3] = r6;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r6 = com.google.android.exoplayer2.video.VideoRendererEventListener.class;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r9 = 3;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r5[r9] = r6;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r6 = java.lang.Integer.TYPE;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r10 = 4;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r5[r10] = r6;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r0 = r0.getConstructor(r5);	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r4 = new java.lang.Object[r4];	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r5 = java.lang.Boolean.valueOf(r8);	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r4[r7] = r5;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r5 = java.lang.Long.valueOf(r16);	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r4[r8] = r5;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r4[r3] = r18;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r4[r9] = r19;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r3 = 50;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r4[r10] = r3;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r0 = r0.newInstance(r4);	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r0 = (com.google.android.exoplayer2.Renderer) r0;	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r1.add(r2, r0);	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r0 = "DefaultRenderersFactory";	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        r1 = "Loaded LibvpxVideoRenderer.";	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        com.google.android.exoplayer2.util.Log.i(r0, r1);	 Catch:{ ClassNotFoundException -> 0x007f, Exception -> 0x0076 }
        goto L_0x007f;
    L_0x0076:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r2 = "Error instantiating VP9 extension";
        r1.<init>(r2, r0);
        throw r1;
    L_0x007f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.DefaultRenderersFactory.buildVideoRenderers(android.content.Context, com.google.android.exoplayer2.drm.DrmSessionManager, long, android.os.Handler, com.google.android.exoplayer2.video.VideoRendererEventListener, int, java.util.ArrayList):void");
    }

    protected void buildAudioRenderers(android.content.Context r14, @androidx.annotation.Nullable com.google.android.exoplayer2.drm.DrmSessionManager<com.google.android.exoplayer2.drm.FrameworkMediaCrypto> r15, com.google.android.exoplayer2.audio.AudioProcessor[] r16, android.os.Handler r17, com.google.android.exoplayer2.audio.AudioRendererEventListener r18, int r19, java.util.ArrayList<com.google.android.exoplayer2.Renderer> r20) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r13 = this;
        r10 = r19;
        r11 = r20;
        r12 = new com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
        r3 = com.google.android.exoplayer2.mediacodec.MediaCodecSelector.DEFAULT;
        r8 = com.google.android.exoplayer2.audio.AudioCapabilities.getCapabilities(r14);
        r5 = 0;
        r1 = r12;
        r2 = r14;
        r4 = r15;
        r6 = r17;
        r7 = r18;
        r9 = r16;
        r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9);
        r11.add(r12);
        if (r10 != 0) goto L_0x001f;
    L_0x001e:
        return;
    L_0x001f:
        r1 = r20.size();
        r2 = 2;
        if (r10 != r2) goto L_0x0028;
    L_0x0026:
        r1 = r1 + -1;
    L_0x0028:
        r3 = 0;
        r4 = 3;
        r5 = 1;
        r6 = "com.google.android.exoplayer2.ext.opus.LibopusAudioRenderer";	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r6 = java.lang.Class.forName(r6);	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r7 = new java.lang.Class[r4];	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r8 = android.os.Handler.class;	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r7[r3] = r8;	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r8 = com.google.android.exoplayer2.audio.AudioRendererEventListener.class;	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r7[r5] = r8;	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r8 = com.google.android.exoplayer2.audio.AudioProcessor[].class;	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r7[r2] = r8;	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r6 = r6.getConstructor(r7);	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r7 = new java.lang.Object[r4];	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r7[r3] = r17;	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r7[r5] = r18;	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r7[r2] = r16;	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r6 = r6.newInstance(r7);	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r6 = (com.google.android.exoplayer2.Renderer) r6;	 Catch:{ ClassNotFoundException -> 0x0067, Exception -> 0x005e }
        r7 = r1 + 1;
        r11.add(r1, r6);	 Catch:{ ClassNotFoundException -> 0x0068, Exception -> 0x005e }
        r1 = "DefaultRenderersFactory";	 Catch:{ ClassNotFoundException -> 0x0068, Exception -> 0x005e }
        r6 = "Loaded LibopusAudioRenderer.";	 Catch:{ ClassNotFoundException -> 0x0068, Exception -> 0x005e }
        com.google.android.exoplayer2.util.Log.i(r1, r6);	 Catch:{ ClassNotFoundException -> 0x0068, Exception -> 0x005e }
        goto L_0x0068;
    L_0x005e:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r2 = "Error instantiating Opus extension";
        r1.<init>(r2, r0);
        throw r1;
    L_0x0067:
        r7 = r1;
    L_0x0068:
        r1 = "com.google.android.exoplayer2.ext.flac.LibflacAudioRenderer";	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r1 = java.lang.Class.forName(r1);	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r6 = new java.lang.Class[r4];	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r8 = android.os.Handler.class;	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r6[r3] = r8;	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r8 = com.google.android.exoplayer2.audio.AudioRendererEventListener.class;	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r6[r5] = r8;	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r8 = com.google.android.exoplayer2.audio.AudioProcessor[].class;	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r6[r2] = r8;	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r1 = r1.getConstructor(r6);	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r6 = new java.lang.Object[r4];	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r6[r3] = r17;	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r6[r5] = r18;	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r6[r2] = r16;	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r1 = r1.newInstance(r6);	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r1 = (com.google.android.exoplayer2.Renderer) r1;	 Catch:{ ClassNotFoundException -> 0x00a4, Exception -> 0x009b }
        r6 = r7 + 1;
        r11.add(r7, r1);	 Catch:{ ClassNotFoundException -> 0x00a5, Exception -> 0x009b }
        r1 = "DefaultRenderersFactory";	 Catch:{ ClassNotFoundException -> 0x00a5, Exception -> 0x009b }
        r7 = "Loaded LibflacAudioRenderer.";	 Catch:{ ClassNotFoundException -> 0x00a5, Exception -> 0x009b }
        com.google.android.exoplayer2.util.Log.i(r1, r7);	 Catch:{ ClassNotFoundException -> 0x00a5, Exception -> 0x009b }
        goto L_0x00a5;
    L_0x009b:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r2 = "Error instantiating FLAC extension";
        r1.<init>(r2, r0);
        throw r1;
    L_0x00a4:
        r6 = r7;
    L_0x00a5:
        r1 = "com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer";	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r1 = java.lang.Class.forName(r1);	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r7 = new java.lang.Class[r4];	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r8 = android.os.Handler.class;	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r7[r3] = r8;	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r8 = com.google.android.exoplayer2.audio.AudioRendererEventListener.class;	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r7[r5] = r8;	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r8 = com.google.android.exoplayer2.audio.AudioProcessor[].class;	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r7[r2] = r8;	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r1 = r1.getConstructor(r7);	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r4 = new java.lang.Object[r4];	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r4[r3] = r17;	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r4[r5] = r18;	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r4[r2] = r16;	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r0 = r1.newInstance(r4);	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r0 = (com.google.android.exoplayer2.Renderer) r0;	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r11.add(r6, r0);	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r0 = "DefaultRenderersFactory";	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        r1 = "Loaded FfmpegAudioRenderer.";	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        com.google.android.exoplayer2.util.Log.i(r0, r1);	 Catch:{ ClassNotFoundException -> 0x00df, Exception -> 0x00d6 }
        goto L_0x00df;
    L_0x00d6:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r2 = "Error instantiating FFmpeg extension";
        r1.<init>(r2, r0);
        throw r1;
    L_0x00df:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.DefaultRenderersFactory.buildAudioRenderers(android.content.Context, com.google.android.exoplayer2.drm.DrmSessionManager, com.google.android.exoplayer2.audio.AudioProcessor[], android.os.Handler, com.google.android.exoplayer2.audio.AudioRendererEventListener, int, java.util.ArrayList):void");
    }

    protected void buildTextRenderers(Context context, TextOutput textOutput, Looper looper, int i, ArrayList<Renderer> arrayList) {
        arrayList.add(new TextRenderer(textOutput, looper));
    }

    protected void buildMetadataRenderers(Context context, MetadataOutput metadataOutput, Looper looper, int i, ArrayList<Renderer> arrayList) {
        arrayList.add(new MetadataRenderer(metadataOutput, looper));
    }

    protected void buildCameraMotionRenderers(Context context, int i, ArrayList<Renderer> arrayList) {
        arrayList.add(new CameraMotionRenderer());
    }

    protected AudioProcessor[] buildAudioProcessors() {
        return new AudioProcessor[0];
    }
}
