package com.google.android.exoplayer2.util;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Handler;
import androidx.annotation.Nullable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@TargetApi(17)
public final class EGLSurfaceTexture implements OnFrameAvailableListener, Runnable {
    private static final int[] EGL_CONFIG_ATTRIBUTES = new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12327, 12344, 12339, 4, 12344};
    private static final int EGL_PROTECTED_CONTENT_EXT = 12992;
    private static final int EGL_SURFACE_HEIGHT = 1;
    private static final int EGL_SURFACE_WIDTH = 1;
    public static final int SECURE_MODE_NONE = 0;
    public static final int SECURE_MODE_PROTECTED_PBUFFER = 2;
    public static final int SECURE_MODE_SURFACELESS_CONTEXT = 1;
    @Nullable
    private final TextureImageListener callback;
    @Nullable
    private EGLContext context;
    @Nullable
    private EGLDisplay display;
    private final Handler handler;
    @Nullable
    private EGLSurface surface;
    @Nullable
    private SurfaceTexture texture;
    private final int[] textureIdHolder;

    public static final class GlException extends RuntimeException {
        private GlException(String str) {
            super(str);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SecureMode {
    }

    public interface TextureImageListener {
        void onFrameAvailable();
    }

    public EGLSurfaceTexture(Handler handler) {
        this(handler, null);
    }

    public EGLSurfaceTexture(Handler handler, @Nullable TextureImageListener textureImageListener) {
        this.handler = handler;
        this.callback = textureImageListener;
        this.textureIdHolder = new int[1];
    }

    public void init(int i) {
        this.display = getDefaultDisplay();
        EGLConfig chooseEGLConfig = chooseEGLConfig(this.display);
        this.context = createEGLContext(this.display, chooseEGLConfig, i);
        this.surface = createEGLSurface(this.display, chooseEGLConfig, this.context, i);
        generateTextureIds(this.textureIdHolder);
        this.texture = new SurfaceTexture(this.textureIdHolder[0]);
        this.texture.setOnFrameAvailableListener(this);
    }

    public void release() {
        this.handler.removeCallbacks(this);
        try {
            if (this.texture != null) {
                this.texture.release();
                GLES20.glDeleteTextures(1, this.textureIdHolder, 0);
            }
            if (!(this.display == null || this.display.equals(EGL14.EGL_NO_DISPLAY))) {
                EGL14.eglMakeCurrent(this.display, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            }
            if (!(this.surface == null || this.surface.equals(EGL14.EGL_NO_SURFACE))) {
                EGL14.eglDestroySurface(this.display, this.surface);
            }
            if (this.context != null) {
                EGL14.eglDestroyContext(this.display, this.context);
            }
            if (Util.SDK_INT >= 19) {
                EGL14.eglReleaseThread();
            }
            if (!(this.display == null || this.display.equals(EGL14.EGL_NO_DISPLAY))) {
                EGL14.eglTerminate(this.display);
            }
            this.display = null;
            this.context = null;
            this.surface = null;
            this.texture = null;
        } catch (Throwable th) {
            if (!(this.display == null || this.display.equals(EGL14.EGL_NO_DISPLAY))) {
                EGL14.eglMakeCurrent(this.display, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            }
            if (!(this.surface == null || this.surface.equals(EGL14.EGL_NO_SURFACE))) {
                EGL14.eglDestroySurface(this.display, this.surface);
            }
            if (this.context != null) {
                EGL14.eglDestroyContext(this.display, this.context);
            }
            if (Util.SDK_INT >= 19) {
                EGL14.eglReleaseThread();
            }
            if (!(this.display == null || this.display.equals(EGL14.EGL_NO_DISPLAY))) {
                EGL14.eglTerminate(this.display);
            }
            this.display = null;
            this.context = null;
            this.surface = null;
            this.texture = null;
        }
    }

    public SurfaceTexture getSurfaceTexture() {
        return (SurfaceTexture) Assertions.checkNotNull(this.texture);
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.handler.post(this);
    }

    public void run() {
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
        r1 = this;
        r1.dispatchOnFrameAvailable();
        r0 = r1.texture;
        if (r0 == 0) goto L_0x000c;
    L_0x0007:
        r0 = r1.texture;	 Catch:{ RuntimeException -> 0x000c }
        r0.updateTexImage();	 Catch:{ RuntimeException -> 0x000c }
    L_0x000c:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.EGLSurfaceTexture.run():void");
    }

    private void dispatchOnFrameAvailable() {
        if (this.callback != null) {
            this.callback.onFrameAvailable();
        }
    }

    private static EGLDisplay getDefaultDisplay() {
        EGLDisplay eglGetDisplay = EGL14.eglGetDisplay(0);
        if (eglGetDisplay != null) {
            int[] iArr = new int[2];
            if (EGL14.eglInitialize(eglGetDisplay, iArr, 0, iArr, 1)) {
                return eglGetDisplay;
            }
            throw new GlException("eglInitialize failed");
        }
        throw new GlException("eglGetDisplay failed");
    }

    private static EGLConfig chooseEGLConfig(EGLDisplay eGLDisplay) {
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        int[] iArr = new int[1];
        if (EGL14.eglChooseConfig(eGLDisplay, EGL_CONFIG_ATTRIBUTES, 0, eGLConfigArr, 0, 1, iArr, 0) != null && iArr[0] > 0 && eGLConfigArr[0] != null) {
            return eGLConfigArr[0];
        }
        throw new GlException(Util.formatInvariant("eglChooseConfig failed: success=%b, numConfigs[0]=%d, configs[0]=%s", Boolean.valueOf(eGLDisplay), Integer.valueOf(iArr[0]), eGLConfigArr[0]));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.opengl.EGLContext createEGLContext(android.opengl.EGLDisplay r2, android.opengl.EGLConfig r3, int r4) {
        /*
        if (r4 != 0) goto L_0x0009;
    L_0x0002:
        r4 = 3;
        r4 = new int[r4];
        r4 = {12440, 2, 12344};
        goto L_0x000f;
    L_0x0009:
        r4 = 5;
        r4 = new int[r4];
        r4 = {12440, 2, 12992, 1, 12344};
    L_0x000f:
        r0 = android.opengl.EGL14.EGL_NO_CONTEXT;
        r1 = 0;
        r2 = android.opengl.EGL14.eglCreateContext(r2, r3, r0, r4, r1);
        if (r2 == 0) goto L_0x0019;
    L_0x0018:
        return r2;
    L_0x0019:
        r2 = new com.google.android.exoplayer2.util.EGLSurfaceTexture$GlException;
        r3 = 0;
        r4 = "eglCreateContext failed";
        r2.<init>(r4);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.EGLSurfaceTexture.createEGLContext(android.opengl.EGLDisplay, android.opengl.EGLConfig, int):android.opengl.EGLContext");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.opengl.EGLSurface createEGLSurface(android.opengl.EGLDisplay r2, android.opengl.EGLConfig r3, android.opengl.EGLContext r4, int r5) {
        /*
        r0 = 0;
        r1 = 1;
        if (r5 != r1) goto L_0x0007;
    L_0x0004:
        r3 = android.opengl.EGL14.EGL_NO_SURFACE;
        goto L_0x001e;
    L_0x0007:
        r1 = 2;
        if (r5 != r1) goto L_0x0011;
    L_0x000a:
        r5 = 7;
        r5 = new int[r5];
        r5 = {12375, 1, 12374, 1, 12992, 1, 12344};
        goto L_0x0017;
    L_0x0011:
        r5 = 5;
        r5 = new int[r5];
        r5 = {12375, 1, 12374, 1, 12344};
    L_0x0017:
        r1 = 0;
        r3 = android.opengl.EGL14.eglCreatePbufferSurface(r2, r3, r5, r1);
        if (r3 == 0) goto L_0x002d;
    L_0x001e:
        r2 = android.opengl.EGL14.eglMakeCurrent(r2, r3, r3, r4);
        if (r2 == 0) goto L_0x0025;
    L_0x0024:
        return r3;
    L_0x0025:
        r2 = new com.google.android.exoplayer2.util.EGLSurfaceTexture$GlException;
        r3 = "eglMakeCurrent failed";
        r2.<init>(r3);
        throw r2;
    L_0x002d:
        r2 = new com.google.android.exoplayer2.util.EGLSurfaceTexture$GlException;
        r3 = "eglCreatePbufferSurface failed";
        r2.<init>(r3);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.EGLSurfaceTexture.createEGLSurface(android.opengl.EGLDisplay, android.opengl.EGLConfig, android.opengl.EGLContext, int):android.opengl.EGLSurface");
    }

    private static void generateTextureIds(int[] iArr) {
        GLES20.glGenTextures(1, iArr, 0);
        iArr = GLES20.glGetError();
        if (iArr != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("glGenTextures failed. Error: ");
            stringBuilder.append(Integer.toHexString(iArr));
            throw new GlException(stringBuilder.toString());
        }
    }
}
