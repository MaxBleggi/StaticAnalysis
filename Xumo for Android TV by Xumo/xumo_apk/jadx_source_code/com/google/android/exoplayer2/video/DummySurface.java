package com.google.android.exoplayer2.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Surface;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.EGLSurfaceTexture;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

@TargetApi(17)
public final class DummySurface extends Surface {
    private static final String EXTENSION_PROTECTED_CONTENT = "EGL_EXT_protected_content";
    private static final String EXTENSION_SURFACELESS_CONTEXT = "EGL_KHR_surfaceless_context";
    private static final String TAG = "DummySurface";
    private static int secureMode;
    private static boolean secureModeInitialized;
    public final boolean secure;
    private final DummySurfaceThread thread;
    private boolean threadReleased;

    private static class DummySurfaceThread extends HandlerThread implements Callback {
        private static final int MSG_INIT = 1;
        private static final int MSG_RELEASE = 2;
        private EGLSurfaceTexture eglSurfaceTexture;
        private Handler handler;
        @Nullable
        private Error initError;
        @Nullable
        private RuntimeException initException;
        @Nullable
        private DummySurface surface;

        public DummySurfaceThread() {
            super("dummySurface");
        }

        public com.google.android.exoplayer2.video.DummySurface init(int r4) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r3 = this;
            r3.start();
            r0 = new android.os.Handler;
            r1 = r3.getLooper();
            r0.<init>(r1, r3);
            r3.handler = r0;
            r0 = new com.google.android.exoplayer2.util.EGLSurfaceTexture;
            r1 = r3.handler;
            r0.<init>(r1);
            r3.eglSurfaceTexture = r0;
            monitor-enter(r3);
            r0 = r3.handler;	 Catch:{ all -> 0x0056 }
            r1 = 1;	 Catch:{ all -> 0x0056 }
            r2 = 0;	 Catch:{ all -> 0x0056 }
            r4 = r0.obtainMessage(r1, r4, r2);	 Catch:{ all -> 0x0056 }
            r4.sendToTarget();	 Catch:{ all -> 0x0056 }
        L_0x0023:
            r4 = r3.surface;	 Catch:{ all -> 0x0056 }
            if (r4 != 0) goto L_0x0035;	 Catch:{ all -> 0x0056 }
        L_0x0027:
            r4 = r3.initException;	 Catch:{ all -> 0x0056 }
            if (r4 != 0) goto L_0x0035;	 Catch:{ all -> 0x0056 }
        L_0x002b:
            r4 = r3.initError;	 Catch:{ all -> 0x0056 }
            if (r4 != 0) goto L_0x0035;
        L_0x002f:
            r3.wait();	 Catch:{ InterruptedException -> 0x0033 }
            goto L_0x0023;
        L_0x0033:
            r2 = 1;
            goto L_0x0023;
        L_0x0035:
            monitor-exit(r3);	 Catch:{ all -> 0x0056 }
            if (r2 == 0) goto L_0x003f;
        L_0x0038:
            r4 = java.lang.Thread.currentThread();
            r4.interrupt();
        L_0x003f:
            r4 = r3.initException;
            if (r4 != 0) goto L_0x0053;
        L_0x0043:
            r4 = r3.initError;
            if (r4 != 0) goto L_0x0050;
        L_0x0047:
            r4 = r3.surface;
            r4 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r4);
            r4 = (com.google.android.exoplayer2.video.DummySurface) r4;
            return r4;
        L_0x0050:
            r4 = r3.initError;
            throw r4;
        L_0x0053:
            r4 = r3.initException;
            throw r4;
        L_0x0056:
            r4 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0056 }
            throw r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.DummySurface.DummySurfaceThread.init(int):com.google.android.exoplayer2.video.DummySurface");
        }

        public void release() {
            Assertions.checkNotNull(this.handler);
            this.handler.sendEmptyMessage(2);
        }

        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    try {
                        initInternal(message.arg1);
                        synchronized (this) {
                            notify();
                        }
                    } catch (Message message2) {
                        Log.e(DummySurface.TAG, "Failed to initialize dummy surface", message2);
                        this.initException = message2;
                        synchronized (this) {
                            notify();
                        }
                    } catch (Message message22) {
                        try {
                            Log.e(DummySurface.TAG, "Failed to initialize dummy surface", message22);
                            this.initError = message22;
                            synchronized (this) {
                                notify();
                            }
                        } catch (Throwable th) {
                            synchronized (this) {
                                notify();
                            }
                        }
                    }
                    return true;
                case 2:
                    try {
                        releaseInternal();
                    } catch (Throwable th2) {
                        quit();
                    }
                    quit();
                    return true;
                default:
                    return true;
            }
        }

        private void initInternal(int i) {
            Assertions.checkNotNull(this.eglSurfaceTexture);
            this.eglSurfaceTexture.init(i);
            this.surface = new DummySurface(this, this.eglSurfaceTexture.getSurfaceTexture(), i != 0 ? 1 : 0);
        }

        private void releaseInternal() {
            Assertions.checkNotNull(this.eglSurfaceTexture);
            this.eglSurfaceTexture.release();
        }
    }

    public static synchronized boolean isSecureSupported(Context context) {
        boolean z;
        synchronized (DummySurface.class) {
            z = true;
            if (!secureModeInitialized) {
                secureMode = Util.SDK_INT < 24 ? null : getSecureModeV24(context);
                secureModeInitialized = true;
            }
            if (secureMode == null) {
                z = false;
            }
        }
        return z;
    }

    public static DummySurface newInstanceV17(Context context, boolean z) {
        assertApiLevel17OrHigher();
        int i = 0;
        if (z) {
            if (isSecureSupported(context) == null) {
                context = null;
                Assertions.checkState(context);
                context = new DummySurfaceThread();
                if (z) {
                    i = secureMode;
                }
                return context.init(i);
            }
        }
        context = true;
        Assertions.checkState(context);
        context = new DummySurfaceThread();
        if (z) {
            i = secureMode;
        }
        return context.init(i);
    }

    private DummySurface(DummySurfaceThread dummySurfaceThread, SurfaceTexture surfaceTexture, boolean z) {
        super(surfaceTexture);
        this.thread = dummySurfaceThread;
        this.secure = z;
    }

    public void release() {
        super.release();
        synchronized (this.thread) {
            if (!this.threadReleased) {
                this.thread.release();
                this.threadReleased = true;
            }
        }
    }

    private static void assertApiLevel17OrHigher() {
        if (Util.SDK_INT < 17) {
            throw new UnsupportedOperationException("Unsupported prior to API level 17");
        }
    }

    @TargetApi(24)
    private static int getSecureModeV24(Context context) {
        if (Util.SDK_INT < 26 && ("samsung".equals(Util.MANUFACTURER) || "XT1650".equals(Util.MODEL))) {
            return 0;
        }
        if (Util.SDK_INT < 26 && context.getPackageManager().hasSystemFeature("android.hardware.vr.high_performance") == null) {
            return 0;
        }
        context = EGL14.eglQueryString(EGL14.eglGetDisplay(0), 12373);
        if (context == null || !context.contains(EXTENSION_PROTECTED_CONTENT)) {
            return 0;
        }
        return context.contains(EXTENSION_SURFACELESS_CONTEXT) != null ? true : 2;
    }
}
