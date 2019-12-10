package com.google.ads.interactivemedia.v3.internal;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import java.util.ArrayList;
import java.util.List;

/* compiled from: IMASDK */
public abstract class ji implements Callback {
    private final a a;
    protected final long b;
    protected boolean c;
    private List<b> d;

    /* compiled from: IMASDK */
    protected static class a {
        private final Handler a;

        public a(Handler handler) {
            this.a = handler;
        }

        protected void a(int i) {
            this.a.removeMessages(i);
        }

        protected boolean b(int i) {
            return this.a.sendEmptyMessage(i);
        }

        protected boolean a(int i, long j) {
            return this.a.sendEmptyMessageDelayed(i, j);
        }

        protected boolean c(int i) {
            return this.a.sendMessageAtFrontOfQueue(Message.obtain(this.a, i));
        }
    }

    /* compiled from: IMASDK */
    public interface b {
        void a(VideoProgressUpdate videoProgressUpdate);
    }

    ji(long j) {
        this(null, j);
    }

    public abstract VideoProgressUpdate a();

    ji(a aVar, long j) {
        this.c = false;
        this.d = new ArrayList(1);
        this.b = j;
        if (aVar != null) {
            this.a = aVar;
        } else {
            this.a = new a(new Handler(this));
        }
    }

    public void a(b bVar) {
        this.d.add(bVar);
    }

    public void b(b bVar) {
        this.d.remove(bVar);
    }

    public void b() {
        if (!this.c) {
            this.c = true;
            this.a.b(1);
        }
    }

    public void c() {
        if (this.c) {
            this.c = false;
            this.a.c(2);
        }
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1:
                message = a();
                for (b a : this.d) {
                    a.a(message);
                }
                this.a.a(1, this.b);
                break;
            case 2:
                this.a.a(1);
                break;
            default:
                break;
        }
        return true;
    }
}
