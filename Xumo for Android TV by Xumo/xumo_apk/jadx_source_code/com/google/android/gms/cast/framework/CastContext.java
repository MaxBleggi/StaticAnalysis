package com.google.android.gms.cast.framework;

import android.content.Context;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.cast.zzci;
import com.google.android.gms.internal.cast.zzdh;
import com.google.android.gms.internal.cast.zze;
import com.google.android.gms.internal.cast.zzf;
import com.google.android.gms.internal.cast.zzw;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CastContext {
    public static final String OPTIONS_PROVIDER_CLASS_NAME_KEY = "com.google.android.gms.cast.framework.OPTIONS_PROVIDER_CLASS_NAME";
    private static final zzdh zzbe = new zzdh("CastContext");
    private static CastContext zzhg;
    private final Context zzhh;
    private final zzj zzhi;
    private final SessionManager zzhj;
    private final zze zzhk;
    private final PrecacheManager zzhl;
    private final MediaNotificationManager zzhm;
    private final CastOptions zzhn;
    private zzw zzho = new zzw(MediaRouter.getInstance(this.zzhh));
    private zzf zzhp;
    private final List<SessionProvider> zzhq;

    public static CastContext getSharedInstance(@NonNull Context context) throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (zzhg == null) {
            OptionsProvider zzc = zzc(context.getApplicationContext());
            zzhg = new CastContext(context, zzc.getCastOptions(context.getApplicationContext()), zzc.getAdditionalSessionProviders(context.getApplicationContext()));
        }
        return zzhg;
    }

    @Nullable
    public static CastContext getSharedInstance() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return zzhg;
    }

    @Nullable
    public static CastContext zzb(@NonNull Context context) throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return getSharedInstance(context);
        } catch (Context context2) {
            zzbe.e("Failed to load module from Google Play services. Cast will not work properly. Might due to outdated Google Play services. Ignoring this failure silently.", context2);
            return null;
        }
    }

    private static OptionsProvider zzc(Context context) throws IllegalStateException {
        try {
            context = Wrappers.packageManager(context).getApplicationInfo(context.getPackageName(), 128).metaData;
            if (context == null) {
                zzbe.e("Bundle is null", new Object[0]);
            }
            context = context.getString(OPTIONS_PROVIDER_CLASS_NAME_KEY);
            if (context != null) {
                return (OptionsProvider) Class.forName(context).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            throw new IllegalStateException("The fully qualified name of the implementation of OptionsProvider must be provided as a metadata in the AndroidManifest.xml with key com.google.android.gms.cast.framework.OPTIONS_PROVIDER_CLASS_NAME.");
        } catch (Context context2) {
            throw new IllegalStateException("Failed to initialize CastContext.", context2);
        }
    }

    private CastContext(Context context, CastOptions castOptions, List<SessionProvider> list) {
        zzp zzx;
        zze com_google_android_gms_cast_framework_zze;
        zzv zzw;
        this.zzhh = context.getApplicationContext();
        this.zzhn = castOptions;
        this.zzhq = list;
        zzp();
        this.zzhi = zze.zza(this.zzhh, castOptions, this.zzho, zzo());
        PrecacheManager precacheManager = null;
        try {
            zzx = this.zzhi.zzx();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "getDiscoveryManagerImpl", zzj.class.getSimpleName());
            zzx = null;
        }
        if (zzx == null) {
            com_google_android_gms_cast_framework_zze = null;
        } else {
            com_google_android_gms_cast_framework_zze = new zze(zzx);
        }
        this.zzhk = com_google_android_gms_cast_framework_zze;
        try {
            zzw = this.zzhi.zzw();
        } catch (Throwable e2) {
            zzbe.zza(e2, "Unable to call %s on %s.", new Object[]{"getSessionManagerImpl", zzj.class.getSimpleName()});
            zzw = null;
        }
        if (zzw == null) {
            context = null;
        } else {
            context = new SessionManager(zzw, this.zzhh);
        }
        this.zzhj = context;
        this.zzhm = new MediaNotificationManager(this.zzhj);
        if (this.zzhj != null) {
            precacheManager = new PrecacheManager(this.zzhn, this.zzhj, new zzci(this.zzhh));
        }
        this.zzhl = precacheManager;
    }

    private final Map<String, IBinder> zzo() {
        Map<String, IBinder> hashMap = new HashMap();
        if (this.zzhp != null) {
            hashMap.put(this.zzhp.getCategory(), this.zzhp.zzaj());
        }
        if (this.zzhq != null) {
            for (SessionProvider sessionProvider : this.zzhq) {
                Preconditions.checkNotNull(sessionProvider, "Additional SessionProvider must not be null.");
                String checkNotEmpty = Preconditions.checkNotEmpty(sessionProvider.getCategory(), "Category for SessionProvider must not be null or empty string.");
                Preconditions.checkArgument(hashMap.containsKey(checkNotEmpty) ^ true, String.format("SessionProvider for category %s already added", new Object[]{checkNotEmpty}));
                hashMap.put(checkNotEmpty, sessionProvider.zzaj());
            }
        }
        return hashMap;
    }

    private final void zzp() {
        if (TextUtils.isEmpty(this.zzhn.getReceiverApplicationId())) {
            this.zzhp = null;
        } else {
            this.zzhp = new zzf(this.zzhh, this.zzhn, this.zzho);
        }
    }

    public CastOptions getCastOptions() throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzhn;
    }

    public SessionManager getSessionManager() throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzhj;
    }

    public MediaRouteSelector getMergedSelector() throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return MediaRouteSelector.fromBundle(this.zzhi.zzv());
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "getMergedSelectorAsBundle", zzj.class.getSimpleName());
            return null;
        }
    }

    public int getCastState() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzhj.getCastState();
    }

    public PrecacheManager getPrecacheManager() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzhl;
    }

    public boolean isAppVisible() throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return this.zzhi.isAppVisible();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "isApplicationVisible", zzj.class.getSimpleName());
            return false;
        }
    }

    public final boolean zzq() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return this.zzhi.zzq();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "hasActivityInRecents", zzj.class.getSimpleName());
            return false;
        }
    }

    public MediaNotificationManager getMediaNotificationManager() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzhm;
    }

    @Deprecated
    public void addAppVisibilityListener(AppVisibilityListener appVisibilityListener) throws IllegalStateException, NullPointerException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        Preconditions.checkNotNull(appVisibilityListener);
        try {
            this.zzhi.zza(new zza(appVisibilityListener));
        } catch (AppVisibilityListener appVisibilityListener2) {
            zzbe.zza(appVisibilityListener2, "Unable to call %s on %s.", "addVisibilityChangeListener", zzj.class.getSimpleName());
        }
    }

    @Deprecated
    public void removeAppVisibilityListener(AppVisibilityListener appVisibilityListener) throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (appVisibilityListener != null) {
            try {
                this.zzhi.zzb(new zza(appVisibilityListener));
            } catch (AppVisibilityListener appVisibilityListener2) {
                zzbe.zza(appVisibilityListener2, "Unable to call %s on %s.", "addVisibilityChangeListener", zzj.class.getSimpleName());
            }
        }
    }

    public void addCastStateListener(CastStateListener castStateListener) throws IllegalStateException, NullPointerException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        Preconditions.checkNotNull(castStateListener);
        this.zzhj.addCastStateListener(castStateListener);
    }

    public void removeCastStateListener(CastStateListener castStateListener) throws IllegalStateException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (castStateListener != null) {
            this.zzhj.removeCastStateListener(castStateListener);
        }
    }

    public void setReceiverApplicationId(String str) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!TextUtils.equals(str, this.zzhn.getReceiverApplicationId())) {
            this.zzhn.setReceiverApplicationId(str);
            zzp();
            try {
                this.zzhi.zza(str, zzo());
            } catch (String str2) {
                zzbe.zza(str2, "Unable to call %s on %s.", "setReceiverApplicationId", zzj.class.getSimpleName());
            }
            CastButtonFactory.zza(this.zzhh);
        }
    }

    public final zze zzr() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzhk;
    }

    public boolean onDispatchVolumeKeyEventBeforeJellyBean(KeyEvent keyEvent) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (PlatformVersion.isAtLeastJellyBean()) {
            return false;
        }
        Session currentCastSession = this.zzhj.getCurrentCastSession();
        if (currentCastSession != null) {
            if (currentCastSession.isConnected()) {
                double volumeDeltaBeforeIceCreamSandwich = getCastOptions().getVolumeDeltaBeforeIceCreamSandwich();
                boolean z = keyEvent.getAction() == 0;
                switch (keyEvent.getKeyCode()) {
                    case 24:
                        zza(currentCastSession, volumeDeltaBeforeIceCreamSandwich, z);
                        return true;
                    case 25:
                        zza(currentCastSession, -volumeDeltaBeforeIceCreamSandwich, z);
                        return true;
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    private static boolean zza(CastSession castSession, double d, boolean z) {
        if (z) {
            try {
                double volume = castSession.getVolume() + d;
                d = 1.0d;
                if (volume <= 1.0d) {
                    d = volume;
                }
                castSession.setVolume(d);
            } catch (CastSession castSession2) {
                zzbe.e("Unable to call CastSession.setVolume(double).", new Object[]{castSession2});
            }
        }
        return true;
    }
}
