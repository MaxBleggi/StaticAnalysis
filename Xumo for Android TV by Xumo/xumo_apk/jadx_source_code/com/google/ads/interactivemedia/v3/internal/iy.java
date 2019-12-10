package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.media.MediaCodec.CryptoException;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import androidx.core.view.ViewCompat;
import com.google.ads.interactivemedia.v3.api.player.VideoAdPlayer.VideoAdPlayerCallback;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: IMASDK */
public class iy implements jj {
    private final ba a;
    private final SurfaceView b;
    private final at c;
    private final FrameLayout d;
    private final ViewGroup e;
    private final Context f;
    private final Handler g;
    private final List<VideoAdPlayerCallback> h;
    private final b i;
    private final e j;
    private final c k;
    private final a l;
    private f m;
    private boolean n;
    private bq o;

    /* compiled from: IMASDK */
    enum f {
        IDLE,
        LOADED,
        PLAYING,
        PAUSED
    }

    /* compiled from: IMASDK */
    public enum g {
        TYPE_VIDEO(0),
        TYPE_AUDIO(1);
        
        private final int c;

        public int a() {
            return this.c;
        }

        private g(int i) {
            this.c = i;
        }
    }

    /* compiled from: IMASDK */
    class a implements com.google.ads.interactivemedia.v3.internal.ba.c {
        final /* synthetic */ iy a;

        a(iy iyVar) {
            this.a = iyVar;
        }

        public void a() {
        }

        public void a(az azVar) {
            this.a.g();
            azVar = String.valueOf(azVar);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(azVar).length() + 13);
            stringBuilder.append("Player Error:");
            stringBuilder.append(azVar);
            Log.e("IMA SDK", stringBuilder.toString());
        }

        public void a(boolean z, int i) {
            if (i == true) {
                for (VideoAdPlayerCallback onEnded : this.a.h) {
                    onEnded.onEnded();
                }
            }
            if (i == true) {
                for (VideoAdPlayerCallback onLoaded : this.a.h) {
                    onLoaded.onLoaded();
                }
            }
        }
    }

    /* compiled from: IMASDK */
    class b implements com.google.ads.interactivemedia.v3.internal.cf.a {
        final /* synthetic */ iy a;

        b(iy iyVar) {
            this.a = iyVar;
        }

        public void a(int i, IOException iOException) {
            this.a.g();
            iOException = String.valueOf(iOException);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(iOException).length() + 41);
            stringBuilder.append("Load Error from SampleSource:");
            stringBuilder.append(i);
            stringBuilder.append(":");
            stringBuilder.append(iOException);
            Log.e("IMA SDK", stringBuilder.toString());
        }
    }

    /* compiled from: IMASDK */
    class d implements com.google.ads.interactivemedia.v3.internal.bg.b {
        final /* synthetic */ iy a;

        d(iy iyVar) {
            this.a = iyVar;
        }

        public void a(String str, long j, long j2) {
        }

        public void a(com.google.ads.interactivemedia.v3.internal.bg.a aVar) {
            this.a.g();
        }

        public void a(CryptoException cryptoException) {
            this.a.g();
        }
    }

    /* compiled from: IMASDK */
    class c extends d implements com.google.ads.interactivemedia.v3.internal.be.a {
        c(iy iyVar) {
            super(iyVar);
        }

        public void a(int i, long j, long j2) {
        }

        public void a(com.google.ads.interactivemedia.v3.internal.bt.d dVar) {
        }

        public void a(com.google.ads.interactivemedia.v3.internal.bt.f fVar) {
        }
    }

    /* compiled from: IMASDK */
    class e extends d implements com.google.ads.interactivemedia.v3.internal.bi.a {
        final /* synthetic */ iy a;

        e(iy iyVar) {
            this.a = iyVar;
            super(iyVar);
        }

        public void a(int i, long j) {
        }

        public void a(Surface surface) {
        }

        public void a(int i, int i2, int i3, float f) {
            this.a.c.a(i2 == 0 ? 1065353216 : (((float) i) * f) / ((float) i2));
        }
    }

    public iy(Context context, ViewGroup viewGroup) {
        this(context, viewGroup, com.google.ads.interactivemedia.v3.internal.ba.b.a(2));
    }

    public int getVolume() {
        return 100;
    }

    iy(Context context, ViewGroup viewGroup, final ba baVar) {
        this.f = context;
        this.e = viewGroup;
        this.a = baVar;
        this.i = new b(this);
        this.k = new c(this);
        this.j = new e(this);
        this.l = new a(this);
        baVar.a(this.l);
        this.g = new Handler();
        this.h = new ArrayList(1);
        this.d = new FrameLayout(context);
        this.d.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.c = new at(context);
        viewGroup = new LayoutParams(-1, -1);
        viewGroup.gravity = 17;
        this.c.setLayoutParams(viewGroup);
        this.m = f.IDLE;
        this.b = new SurfaceView(context);
        this.b.setZOrderMediaOverlay(true);
        this.b.getHolder().addCallback(new Callback(this) {
            final /* synthetic */ iy b;

            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            }

            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                this.b.n = true;
                if (this.b.m == f.PLAYING || this.b.m == f.PAUSED) {
                    this.b.a((Surface) surfaceHolder.getSurface(), false);
                }
                if (this.b.m == f.PLAYING) {
                    baVar.a(true);
                }
            }

            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                this.b.a(null, true);
                baVar.a(false);
                this.b.n = false;
            }
        });
        this.c.addView(this.b);
        this.d.addView(this.c);
        this.e.addView(this.d, new ViewGroup.LayoutParams(-1, -1));
    }

    public void playAd() {
        switch (this.m) {
            case LOADED:
                for (VideoAdPlayerCallback onPlay : this.h) {
                    onPlay.onPlay();
                }
                a(this.b.getHolder().getSurface(), false);
                break;
            case PAUSED:
                for (VideoAdPlayerCallback onPlay2 : this.h) {
                    onPlay2.onResume();
                }
                break;
            case PLAYING:
                return;
            default:
                String valueOf = String.valueOf(this.m);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 53);
                stringBuilder.append("Ignoring call to playAd during invalid player state: ");
                stringBuilder.append(valueOf);
                Log.w("IMA SDK", stringBuilder.toString());
                return;
        }
        this.m = f.PLAYING;
        if (this.n) {
            this.a.a(true);
        }
    }

    public void loadAd(String str) {
        this.a.b();
        this.a.a(0);
        bq[] a = new jp(this.f, ft.a(this.f, "IMA SDK ExoPlayer"), Uri.parse(str)).a(this, this.g);
        this.o = a[g.TYPE_VIDEO.a()];
        this.a.a(a);
        this.m = f.LOADED;
    }

    public void stopAd() {
        this.m = f.IDLE;
        this.a.b();
        a(null, false);
    }

    public void pauseAd() {
        this.m = f.PAUSED;
        this.a.a(false);
        for (VideoAdPlayerCallback onPause : this.h) {
            onPause.onPause();
        }
    }

    public void resumeAd() {
        playAd();
    }

    public void addCallback(VideoAdPlayerCallback videoAdPlayerCallback) {
        this.h.add(videoAdPlayerCallback);
    }

    public void removeCallback(VideoAdPlayerCallback videoAdPlayerCallback) {
        this.h.remove(videoAdPlayerCallback);
    }

    public VideoProgressUpdate getAdProgress() {
        if ((this.a.a() == 3 || this.a.a() == 4) && this.a.d() > 0) {
            return new VideoProgressUpdate(this.a.e(), this.a.d());
        }
        return VideoProgressUpdate.VIDEO_TIME_NOT_READY;
    }

    private void a(Surface surface, boolean z) {
        if (this.a != null) {
            if (this.o != null) {
                if (z) {
                    this.a.b(this.o, 1, surface);
                } else {
                    this.a.a(this.o, 1, surface);
                }
            }
        }
    }

    public void a() {
        this.d.setVisibility(0);
        this.b.setVisibility(0);
    }

    public void b() {
        this.d.setVisibility(8);
        this.b.setVisibility(4);
    }

    public void c() {
        this.a.b(this.l);
        this.a.c();
        this.e.removeView(this.d);
    }

    private void g() {
        for (VideoAdPlayerCallback onError : this.h) {
            onError.onError();
        }
    }

    public b d() {
        return this.i;
    }

    public e e() {
        return this.j;
    }

    public c f() {
        return this.k;
    }
}
