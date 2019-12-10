package com.google.android.gms.internal.cast;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

public final class zzz extends AsyncTask<Uri, Long, Bitmap> {
    private static final zzdh zzbe = new zzdh("FetchBitmapTask");
    private final zzae zzqo;
    private final zzab zzqp;

    public zzz(Context context, zzab com_google_android_gms_internal_cast_zzab) {
        this(context, 0, 0, false, 2097152, 5, 333, 10000, com_google_android_gms_internal_cast_zzab);
    }

    public zzz(Context context, int i, int i2, boolean z, zzab com_google_android_gms_internal_cast_zzab) {
        this(context, i, i2, false, 2097152, 5, 333, 10000, com_google_android_gms_internal_cast_zzab);
    }

    private zzz(Context context, int i, int i2, boolean z, long j, int i3, int i4, int i5, zzab com_google_android_gms_internal_cast_zzab) {
        this.zzqo = zze.zza(context.getApplicationContext(), this, new zzad(), i, i2, z, 2097152, 5, 333, 10000);
        this.zzqp = com_google_android_gms_internal_cast_zzab;
    }

    private final Bitmap doInBackground(Uri... uriArr) {
        if (uriArr.length == 1) {
            if (uriArr[0] != null) {
                try {
                    return this.zzqo.zzb(uriArr[0]);
                } catch (Uri[] uriArr2) {
                    zzbe.zza(uriArr2, "Unable to call %s on %s.", "doFetch", zzae.class.getSimpleName());
                    return null;
                }
            }
        }
        return null;
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        Bitmap bitmap = (Bitmap) obj;
        if (this.zzqp != null) {
            this.zzqp.onPostExecute(bitmap);
        }
    }
}
