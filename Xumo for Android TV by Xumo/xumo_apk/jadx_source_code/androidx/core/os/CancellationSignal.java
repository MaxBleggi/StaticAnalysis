package androidx.core.os;

import android.os.Build.VERSION;

public final class CancellationSignal {
    private boolean mCancelInProgress;
    private Object mCancellationSignalObj;
    private boolean mIsCanceled;
    private OnCancelListener mOnCancelListener;

    public interface OnCancelListener {
        void onCancel();
    }

    private void waitForCancelFinishedLocked() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: androidx.core.os.CancellationSignal.waitForCancelFinishedLocked():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 7 more
*/
        /*
        r0 = this;
        r0 = r1.mCancelInProgress;
        if (r0 == 0) goto L_0x000a;
        r1.wait();
        goto L_0x0000;
        goto L_0x0000;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.os.CancellationSignal.waitForCancelFinishedLocked():void");
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this) {
            z = this.mIsCanceled;
        }
        return z;
    }

    public void throwIfCanceled() {
        if (isCanceled()) {
            throw new OperationCanceledException();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.mIsCanceled;	 Catch:{ all -> 0x003e }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r4);	 Catch:{ all -> 0x003e }
        return;
    L_0x0007:
        r0 = 1;
        r4.mIsCanceled = r0;	 Catch:{ all -> 0x003e }
        r4.mCancelInProgress = r0;	 Catch:{ all -> 0x003e }
        r0 = r4.mOnCancelListener;	 Catch:{ all -> 0x003e }
        r1 = r4.mCancellationSignalObj;	 Catch:{ all -> 0x003e }
        monitor-exit(r4);	 Catch:{ all -> 0x003e }
        r2 = 0;
        if (r0 == 0) goto L_0x001a;
    L_0x0014:
        r0.onCancel();	 Catch:{ all -> 0x0018 }
        goto L_0x001a;
    L_0x0018:
        r0 = move-exception;
        goto L_0x0028;
    L_0x001a:
        if (r1 == 0) goto L_0x0033;
    L_0x001c:
        r0 = android.os.Build.VERSION.SDK_INT;	 Catch:{ all -> 0x0018 }
        r3 = 16;
        if (r0 < r3) goto L_0x0033;
    L_0x0022:
        r1 = (android.os.CancellationSignal) r1;	 Catch:{ all -> 0x0018 }
        r1.cancel();	 Catch:{ all -> 0x0018 }
        goto L_0x0033;
    L_0x0028:
        monitor-enter(r4);
        r4.mCancelInProgress = r2;	 Catch:{ all -> 0x0030 }
        r4.notifyAll();	 Catch:{ all -> 0x0030 }
        monitor-exit(r4);	 Catch:{ all -> 0x0030 }
        throw r0;
    L_0x0030:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0030 }
        throw r0;
    L_0x0033:
        monitor-enter(r4);
        r4.mCancelInProgress = r2;	 Catch:{ all -> 0x003b }
        r4.notifyAll();	 Catch:{ all -> 0x003b }
        monitor-exit(r4);	 Catch:{ all -> 0x003b }
        return;
    L_0x003b:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x003b }
        throw r0;
    L_0x003e:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x003e }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.os.CancellationSignal.cancel():void");
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        synchronized (this) {
            waitForCancelFinishedLocked();
            if (this.mOnCancelListener == onCancelListener) {
                return;
            }
            this.mOnCancelListener = onCancelListener;
            if (this.mIsCanceled) {
                if (onCancelListener != null) {
                    onCancelListener.onCancel();
                    return;
                }
            }
        }
    }

    public Object getCancellationSignalObject() {
        if (VERSION.SDK_INT < 16) {
            return null;
        }
        Object obj;
        synchronized (this) {
            if (this.mCancellationSignalObj == null) {
                this.mCancellationSignalObj = new android.os.CancellationSignal();
                if (this.mIsCanceled) {
                    ((android.os.CancellationSignal) this.mCancellationSignalObj).cancel();
                }
            }
            obj = this.mCancellationSignalObj;
        }
        return obj;
    }
}
