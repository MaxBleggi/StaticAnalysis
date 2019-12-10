package com.google.android.gms.internal.cast;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.zzab;
import com.google.android.gms.cast.framework.zzh;
import com.google.android.gms.cast.framework.zzj;
import com.google.android.gms.cast.framework.zzl;
import com.google.android.gms.cast.framework.zzr;
import com.google.android.gms.cast.framework.zzt;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import java.util.Map;

public final class zze {
    private static final zzdh zzbe = new zzdh("CastDynamiteModule");

    public static zzj zza(Context context, CastOptions castOptions, zzj com_google_android_gms_internal_cast_zzj, Map<String, IBinder> map) {
        try {
            return zzf(context).zza(ObjectWrapper.wrap(context.getApplicationContext()), castOptions, com_google_android_gms_internal_cast_zzj, map);
        } catch (Context context2) {
            zzbe.zza(context2, "Unable to call %s on %s.", new Object[]{"newCastContextImpl", zzh.class.getSimpleName()});
            return null;
        }
    }

    public static zzt zza(Context context, String str, String str2, zzab com_google_android_gms_cast_framework_zzab) {
        try {
            return zzf(context).zza(str, str2, com_google_android_gms_cast_framework_zzab);
        } catch (Context context2) {
            zzbe.zza(context2, "Unable to call %s on %s.", new Object[]{"newSessionImpl", zzh.class.getSimpleName()});
            return null;
        }
    }

    public static zzl zza(Context context, CastOptions castOptions, IObjectWrapper iObjectWrapper, zzh com_google_android_gms_cast_framework_zzh) {
        try {
            return zzf(context).zza(castOptions, iObjectWrapper, com_google_android_gms_cast_framework_zzh);
        } catch (Context context2) {
            zzbe.zza(context2, "Unable to call %s on %s.", new Object[]{"newCastSessionImpl", zzh.class.getSimpleName()});
            return null;
        }
    }

    public static zzr zza(Service service, IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2) {
        try {
            return zzf(service.getApplicationContext()).zza(ObjectWrapper.wrap(service), iObjectWrapper, iObjectWrapper2);
        } catch (Service service2) {
            zzbe.zza(service2, "Unable to call %s on %s.", "newReconnectionServiceImpl", zzh.class.getSimpleName());
            return null;
        }
    }

    public static zzae zza(Context context, AsyncTask<Uri, Long, Bitmap> asyncTask, zzag com_google_android_gms_internal_cast_zzag, int i, int i2, boolean z, long j, int i3, int i4, int i5) {
        try {
            return zzf(context.getApplicationContext()).zza(ObjectWrapper.wrap(asyncTask), com_google_android_gms_internal_cast_zzag, i, i2, z, 2097152, 5, 333, 10000);
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "newFetchBitmapTaskImpl", zzh.class.getSimpleName());
            return null;
        }
    }

    private static zzh zzf(Context context) {
        try {
            context = DynamiteModule.load(context, DynamiteModule.PREFER_REMOTE, "com.google.android.gms.cast.framework.dynamite").instantiate("com.google.android.gms.cast.framework.internal.CastDynamiteModuleImpl");
            if (context == null) {
                return null;
            }
            IInterface queryLocalInterface = context.queryLocalInterface("com.google.android.gms.cast.framework.internal.ICastDynamiteModule");
            if (queryLocalInterface instanceof zzh) {
                return (zzh) queryLocalInterface;
            }
            return new zzi(context);
        } catch (Context context2) {
            throw new RuntimeException(context2);
        }
    }
}
