package com.google.android.gms.internal.cast;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import com.google.android.gms.cast.framework.media.ImageHints;

public final class zzx implements zzab {
    private final Context zzhh;
    private final ImageHints zzlz;
    private Uri zzqi;
    private zzz zzqj;
    private zzac zzqk;
    private Bitmap zzql;
    private boolean zzqm;
    private zzy zzqn;

    public zzx(Context context) {
        this(context, new ImageHints(-1, 0, 0));
    }

    public zzx(Context context, @NonNull ImageHints imageHints) {
        this.zzhh = context;
        this.zzlz = imageHints;
        this.zzqk = new zzac();
        reset();
    }

    public final void zza(zzy com_google_android_gms_internal_cast_zzy) {
        this.zzqn = com_google_android_gms_internal_cast_zzy;
    }

    public final boolean zza(Uri uri) {
        if (uri == null) {
            reset();
            return true;
        } else if (!uri.equals(this.zzqi)) {
            Uri uri2;
            reset();
            this.zzqi = uri;
            if (this.zzlz.getWidthInPixels() != null) {
                if (this.zzlz.getHeightInPixels() != null) {
                    this.zzqj = new zzz(this.zzhh, this.zzlz.getWidthInPixels(), this.zzlz.getHeightInPixels(), false, this);
                    uri = this.zzqj;
                    uri2 = this.zzqi;
                    uri.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Uri[]{uri2});
                    return false;
                }
            }
            this.zzqj = new zzz(this.zzhh, this);
            uri = this.zzqj;
            uri2 = this.zzqi;
            uri.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Uri[]{uri2});
            return false;
        } else if (this.zzqm != null) {
            return true;
        } else {
            return false;
        }
    }

    public final void clear() {
        reset();
        this.zzqn = null;
    }

    public final void onPostExecute(Bitmap bitmap) {
        this.zzql = bitmap;
        this.zzqm = true;
        if (this.zzqn != null) {
            this.zzqn.zza(this.zzql);
        }
        this.zzqj = null;
    }

    private final void reset() {
        if (this.zzqj != null) {
            this.zzqj.cancel(true);
            this.zzqj = null;
        }
        this.zzqi = null;
        this.zzql = null;
        this.zzqm = false;
    }
}
