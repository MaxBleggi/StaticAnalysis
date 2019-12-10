package com.google.android.exoplayer2.upstream;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

public final class Loader implements LoaderErrorThrower {
    private static final int ACTION_TYPE_DONT_RETRY = 2;
    private static final int ACTION_TYPE_DONT_RETRY_FATAL = 3;
    private static final int ACTION_TYPE_RETRY = 0;
    private static final int ACTION_TYPE_RETRY_AND_RESET_ERROR_COUNT = 1;
    public static final LoadErrorAction DONT_RETRY = new LoadErrorAction(2, C.TIME_UNSET);
    public static final LoadErrorAction DONT_RETRY_FATAL = new LoadErrorAction(3, C.TIME_UNSET);
    public static final LoadErrorAction RETRY = createRetryAction(false, C.TIME_UNSET);
    public static final LoadErrorAction RETRY_RESET_ERROR_COUNT = createRetryAction(true, C.TIME_UNSET);
    private LoadTask<? extends Loadable> currentTask;
    private final ExecutorService downloadExecutorService;
    private IOException fatalError;

    public interface Callback<T extends Loadable> {
        void onLoadCanceled(T t, long j, long j2, boolean z);

        void onLoadCompleted(T t, long j, long j2);

        LoadErrorAction onLoadError(T t, long j, long j2, IOException iOException, int i);
    }

    public static final class LoadErrorAction {
        private final long retryDelayMillis;
        private final int type;

        private LoadErrorAction(int i, long j) {
            this.type = i;
            this.retryDelayMillis = j;
        }

        public boolean isRetry() {
            if (this.type != 0) {
                return this.type == 1;
            } else {
                return true;
            }
        }
    }

    @SuppressLint({"HandlerLeak"})
    private final class LoadTask<T extends Loadable> extends Handler implements Runnable {
        private static final int MSG_CANCEL = 1;
        private static final int MSG_END_OF_SOURCE = 2;
        private static final int MSG_FATAL_ERROR = 4;
        private static final int MSG_IO_EXCEPTION = 3;
        private static final int MSG_START = 0;
        private static final String TAG = "LoadTask";
        @Nullable
        private Callback<T> callback;
        private volatile boolean canceled;
        private IOException currentError;
        public final int defaultMinRetryCount;
        private int errorCount;
        private volatile Thread executorThread;
        private final T loadable;
        private volatile boolean released;
        private final long startTimeMs;

        public void run() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.android.exoplayer2.upstream.Loader.LoadTask.run():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
*/
            /*
            r0 = this;
            r0 = 2;
            r1 = 3;
            r2 = java.lang.Thread.currentThread();
            r4.executorThread = r2;
            r2 = r4.canceled;
            if (r2 != 0) goto L_0x0038;
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "load:";
            r2.append(r3);
            r3 = r4.loadable;
            r3 = r3.getClass();
            r3 = r3.getSimpleName();
            r2.append(r3);
            r2 = r2.toString();
            com.google.android.exoplayer2.util.TraceUtil.beginSection(r2);
            r2 = r4.loadable;
            r2.load();
            com.google.android.exoplayer2.util.TraceUtil.endSection();
            goto L_0x0038;
            r2 = move-exception;
            com.google.android.exoplayer2.util.TraceUtil.endSection();
            throw r2;
            r2 = r4.released;
            if (r2 != 0) goto L_0x00a1;
            r4.sendEmptyMessage(r0);
            goto L_0x00a1;
            r0 = move-exception;
            r1 = "LoadTask";
            r2 = "Unexpected error loading stream";
            com.google.android.exoplayer2.util.Log.e(r1, r2, r0);
            r1 = r4.released;
            if (r1 != 0) goto L_0x0054;
            r1 = 4;
            r1 = r4.obtainMessage(r1, r0);
            r1.sendToTarget();
            throw r0;
            r0 = move-exception;
            r2 = "LoadTask";
            r3 = "OutOfMemory error loading stream";
            com.google.android.exoplayer2.util.Log.e(r2, r3, r0);
            r2 = r4.released;
            if (r2 != 0) goto L_0x00a1;
            r2 = new com.google.android.exoplayer2.upstream.Loader$UnexpectedLoaderException;
            r2.<init>(r0);
            r0 = r4.obtainMessage(r1, r2);
            r0.sendToTarget();
            goto L_0x00a1;
            r0 = move-exception;
            r2 = "LoadTask";
            r3 = "Unexpected exception loading stream";
            com.google.android.exoplayer2.util.Log.e(r2, r3, r0);
            r2 = r4.released;
            if (r2 != 0) goto L_0x00a1;
            r2 = new com.google.android.exoplayer2.upstream.Loader$UnexpectedLoaderException;
            r2.<init>(r0);
            r0 = r4.obtainMessage(r1, r2);
            r0.sendToTarget();
            goto L_0x00a1;
            r1 = r4.canceled;
            com.google.android.exoplayer2.util.Assertions.checkState(r1);
            r1 = r4.released;
            if (r1 != 0) goto L_0x00a1;
            r4.sendEmptyMessage(r0);
            goto L_0x00a1;
        L_0x0095:
            r0 = move-exception;
            r2 = r4.released;
            if (r2 != 0) goto L_0x00a1;
            r0 = r4.obtainMessage(r1, r0);
            r0.sendToTarget();
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.Loader.LoadTask.run():void");
        }

        public LoadTask(Looper looper, T t, Callback<T> callback, int i, long j) {
            super(looper);
            this.loadable = t;
            this.callback = callback;
            this.defaultMinRetryCount = i;
            this.startTimeMs = j;
        }

        public void maybeThrowError(int i) throws IOException {
            if (this.currentError == null) {
                return;
            }
            if (this.errorCount > i) {
                throw this.currentError;
            }
        }

        public void start(long j) {
            Assertions.checkState(Loader.this.currentTask == null);
            Loader.this.currentTask = this;
            if (j > 0) {
                sendEmptyMessageDelayed(0, j);
            } else {
                execute();
            }
        }

        public void cancel(boolean z) {
            this.released = z;
            this.currentError = null;
            if (hasMessages(0)) {
                removeMessages(0);
                if (!z) {
                    sendEmptyMessage(1);
                }
            } else {
                this.canceled = true;
                this.loadable.cancelLoad();
                if (this.executorThread != null) {
                    this.executorThread.interrupt();
                }
            }
            if (z) {
                finish();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                this.callback.onLoadCanceled(this.loadable, elapsedRealtime, elapsedRealtime - this.startTimeMs, true);
                this.callback = null;
            }
        }

        public void handleMessage(Message message) {
            if (!this.released) {
                if (message.what == 0) {
                    execute();
                } else if (message.what != 4) {
                    finish();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    long j = elapsedRealtime - this.startTimeMs;
                    if (this.canceled) {
                        this.callback.onLoadCanceled(this.loadable, elapsedRealtime, j, false);
                        return;
                    }
                    switch (message.what) {
                        case 1:
                            this.callback.onLoadCanceled(this.loadable, elapsedRealtime, j, false);
                            break;
                        case 2:
                            try {
                                this.callback.onLoadCompleted(this.loadable, elapsedRealtime, j);
                                break;
                            } catch (Message message2) {
                                Log.e(TAG, "Unexpected exception handling load completed", message2);
                                Loader.this.fatalError = new UnexpectedLoaderException(message2);
                                break;
                            }
                        case 3:
                            this.currentError = (IOException) message2.obj;
                            this.errorCount += 1;
                            message2 = this.callback.onLoadError(this.loadable, elapsedRealtime, j, this.currentError, this.errorCount);
                            if (message2.type != 3) {
                                if (message2.type != 2) {
                                    long access$400;
                                    if (message2.type == 1) {
                                        this.errorCount = 1;
                                    }
                                    if (message2.retryDelayMillis != C.TIME_UNSET) {
                                        access$400 = message2.retryDelayMillis;
                                    } else {
                                        access$400 = getRetryDelayMillis();
                                    }
                                    start(access$400);
                                    break;
                                }
                            }
                            Loader.this.fatalError = this.currentError;
                            break;
                            break;
                        default:
                            break;
                    }
                } else {
                    throw ((Error) message2.obj);
                }
            }
        }

        private void execute() {
            this.currentError = null;
            Loader.this.downloadExecutorService.execute(Loader.this.currentTask);
        }

        private void finish() {
            Loader.this.currentTask = null;
        }

        private long getRetryDelayMillis() {
            return (long) Math.min((this.errorCount - 1) * 1000, 5000);
        }
    }

    public interface Loadable {
        void cancelLoad();

        void load() throws IOException, InterruptedException;
    }

    public interface ReleaseCallback {
        void onLoaderReleased();
    }

    private static final class ReleaseTask implements Runnable {
        private final ReleaseCallback callback;

        public ReleaseTask(ReleaseCallback releaseCallback) {
            this.callback = releaseCallback;
        }

        public void run() {
            this.callback.onLoaderReleased();
        }
    }

    public static final class UnexpectedLoaderException extends IOException {
        public UnexpectedLoaderException(Throwable th) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected ");
            stringBuilder.append(th.getClass().getSimpleName());
            stringBuilder.append(": ");
            stringBuilder.append(th.getMessage());
            super(stringBuilder.toString(), th);
        }
    }

    public Loader(String str) {
        this.downloadExecutorService = Util.newSingleThreadExecutor(str);
    }

    public static LoadErrorAction createRetryAction(boolean z, long j) {
        return new LoadErrorAction(z, j);
    }

    public <T extends Loadable> long startLoading(T t, Callback<T> callback, int i) {
        Looper myLooper = Looper.myLooper();
        Assertions.checkState(myLooper != null);
        this.fatalError = null;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        new LoadTask(myLooper, t, callback, i, elapsedRealtime).start(null);
        return elapsedRealtime;
    }

    public boolean isLoading() {
        return this.currentTask != null;
    }

    public void cancelLoading() {
        this.currentTask.cancel(false);
    }

    public void release() {
        release(null);
    }

    public void release(@Nullable ReleaseCallback releaseCallback) {
        if (this.currentTask != null) {
            this.currentTask.cancel(true);
        }
        if (releaseCallback != null) {
            this.downloadExecutorService.execute(new ReleaseTask(releaseCallback));
        }
        this.downloadExecutorService.shutdown();
    }

    public void maybeThrowError() throws IOException {
        maybeThrowError(Integer.MIN_VALUE);
    }

    public void maybeThrowError(int i) throws IOException {
        if (this.fatalError != null) {
            throw this.fatalError;
        } else if (this.currentTask != null) {
            LoadTask loadTask = this.currentTask;
            if (i == Integer.MIN_VALUE) {
                i = this.currentTask.defaultMinRetryCount;
            }
            loadTask.maybeThrowError(i);
        }
    }
}
