package com.google.android.gms.common.api.internal;

import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zaj extends zal {
    private final SparseArray<zaa> zacv = new SparseArray();

    private class zaa implements OnConnectionFailedListener {
        public final int zacw;
        public final GoogleApiClient zacx;
        public final OnConnectionFailedListener zacy;
        private final /* synthetic */ zaj zacz;

        public zaa(zaj com_google_android_gms_common_api_internal_zaj, int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
            this.zacz = com_google_android_gms_common_api_internal_zaj;
            this.zacw = i;
            this.zacx = googleApiClient;
            this.zacy = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            String valueOf = String.valueOf(connectionResult);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 27);
            stringBuilder.append("beginFailureResolution for ");
            stringBuilder.append(valueOf);
            Log.d("AutoManageHelper", stringBuilder.toString());
            this.zacz.zab(connectionResult, this.zacw);
        }
    }

    public static zaj zaa(LifecycleActivity lifecycleActivity) {
        lifecycleActivity = LifecycleCallback.getFragment(lifecycleActivity);
        zaj com_google_android_gms_common_api_internal_zaj = (zaj) lifecycleActivity.getCallbackOrNull("AutoManageHelper", zaj.class);
        if (com_google_android_gms_common_api_internal_zaj != null) {
            return com_google_android_gms_common_api_internal_zaj;
        }
        return new zaj(lifecycleActivity);
    }

    private zaj(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.mLifecycleFragment.addCallback("AutoManageHelper", this);
    }

    public final void zaa(int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
        Preconditions.checkNotNull(googleApiClient, "GoogleApiClient instance cannot be null");
        boolean z = this.zacv.indexOfKey(i) < 0;
        StringBuilder stringBuilder = new StringBuilder(54);
        stringBuilder.append("Already managing a GoogleApiClient with id ");
        stringBuilder.append(i);
        Preconditions.checkState(z, stringBuilder.toString());
        zam com_google_android_gms_common_api_internal_zam = (zam) this.zade.get();
        boolean z2 = this.mStarted;
        String valueOf = String.valueOf(com_google_android_gms_common_api_internal_zam);
        StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(valueOf).length() + 49);
        stringBuilder2.append("starting AutoManage for client ");
        stringBuilder2.append(i);
        stringBuilder2.append(" ");
        stringBuilder2.append(z2);
        stringBuilder2.append(" ");
        stringBuilder2.append(valueOf);
        Log.d("AutoManageHelper", stringBuilder2.toString());
        this.zacv.put(i, new zaa(this, i, googleApiClient, onConnectionFailedListener));
        if (this.mStarted != 0 && com_google_android_gms_common_api_internal_zam == null) {
            onConnectionFailedListener = String.valueOf(googleApiClient);
            StringBuilder stringBuilder3 = new StringBuilder(String.valueOf(onConnectionFailedListener).length() + 11);
            stringBuilder3.append("connecting ");
            stringBuilder3.append(onConnectionFailedListener);
            Log.d("AutoManageHelper", stringBuilder3.toString());
            googleApiClient.connect();
        }
    }

    public final void zaa(int i) {
        zaa com_google_android_gms_common_api_internal_zaj_zaa = (zaa) this.zacv.get(i);
        this.zacv.remove(i);
        if (com_google_android_gms_common_api_internal_zaj_zaa != null) {
            com_google_android_gms_common_api_internal_zaj_zaa.zacx.unregisterConnectionFailedListener(com_google_android_gms_common_api_internal_zaj_zaa);
            com_google_android_gms_common_api_internal_zaj_zaa.zacx.disconnect();
        }
    }

    public void onStart() {
        super.onStart();
        boolean z = this.mStarted;
        String valueOf = String.valueOf(this.zacv);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 14);
        stringBuilder.append("onStart ");
        stringBuilder.append(z);
        stringBuilder.append(" ");
        stringBuilder.append(valueOf);
        Log.d("AutoManageHelper", stringBuilder.toString());
        if (this.zade.get() == null) {
            for (int i = 0; i < this.zacv.size(); i++) {
                zaa zab = zab(i);
                if (zab != null) {
                    zab.zacx.connect();
                }
            }
        }
    }

    public void onStop() {
        super.onStop();
        for (int i = 0; i < this.zacv.size(); i++) {
            zaa zab = zab(i);
            if (zab != null) {
                zab.zacx.disconnect();
            }
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.zacv.size(); i++) {
            zaa zab = zab(i);
            if (zab != null) {
                printWriter.append(str).append("GoogleApiClient #").print(zab.zacw);
                printWriter.println(":");
                zab.zacx.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
            }
        }
    }

    protected final void zaa(ConnectionResult connectionResult, int i) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zaa com_google_android_gms_common_api_internal_zaj_zaa = (zaa) this.zacv.get(i);
        if (com_google_android_gms_common_api_internal_zaj_zaa != null) {
            zaa(i);
            i = com_google_android_gms_common_api_internal_zaj_zaa.zacy;
            if (i != 0) {
                i.onConnectionFailed(connectionResult);
            }
        }
    }

    protected final void zao() {
        for (int i = 0; i < this.zacv.size(); i++) {
            zaa zab = zab(i);
            if (zab != null) {
                zab.zacx.connect();
            }
        }
    }

    @Nullable
    private final zaa zab(int i) {
        if (this.zacv.size() <= i) {
            return 0;
        }
        return (zaa) this.zacv.get(this.zacv.keyAt(i));
    }
}
