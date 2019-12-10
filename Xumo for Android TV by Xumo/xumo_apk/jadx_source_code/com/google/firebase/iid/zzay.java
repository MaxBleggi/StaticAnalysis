package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.IOException;

final class zzay implements Runnable {
    private final zzan zzan;
    private final zzba zzaq;
    private final long zzdh;
    private final WakeLock zzdi = ((PowerManager) getContext().getSystemService("power")).newWakeLock(1, "fiid-sync");
    private final FirebaseInstanceId zzdj;

    @VisibleForTesting
    zzay(FirebaseInstanceId firebaseInstanceId, zzan com_google_firebase_iid_zzan, zzba com_google_firebase_iid_zzba, long j) {
        this.zzdj = firebaseInstanceId;
        this.zzan = com_google_firebase_iid_zzan;
        this.zzaq = com_google_firebase_iid_zzba;
        this.zzdh = j;
        this.zzdi.setReferenceCounted(null);
    }

    public final void run() {
        this.zzdi.acquire();
        try {
            this.zzdj.zza(true);
            if (!this.zzdj.zzn()) {
                this.zzdj.zza(false);
            } else if (zzao()) {
                if (zzam() && zzan() && this.zzaq.zzc(this.zzdj)) {
                    this.zzdj.zza(false);
                } else {
                    this.zzdj.zza(this.zzdh);
                }
                this.zzdi.release();
            } else {
                new zzaz(this).zzap();
                this.zzdi.release();
            }
        } finally {
            this.zzdi.release();
        }
    }

    @VisibleForTesting
    private final boolean zzam() {
        try {
            if (!this.zzdj.zzo()) {
                this.zzdj.zzp();
            }
            return true;
        } catch (IOException e) {
            String str = "FirebaseInstanceId";
            String str2 = "Build channel failed: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return false;
        }
    }

    @VisibleForTesting
    private final boolean zzan() {
        String zzk;
        zzax zzj = this.zzdj.zzj();
        if (zzj != null && !zzj.zzj(this.zzan.zzad())) {
            return true;
        }
        try {
            zzk = this.zzdj.zzk();
            if (zzk == null) {
                Log.e("FirebaseInstanceId", "Token retrieval failed: null");
                return false;
            }
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                Log.d("FirebaseInstanceId", "Token successfully retrieved");
            }
            if (zzj == null || !(zzj == null || zzk.equals(zzj.zzbq))) {
                Context context = getContext();
                Intent intent = new Intent("com.google.firebase.messaging.NEW_TOKEN");
                intent.putExtra("token", zzk);
                zzav.zzc(context, intent);
                zzav.zzb(context, new Intent("com.google.firebase.iid.TOKEN_REFRESH"));
            }
            return true;
        } catch (Exception e) {
            String str = "FirebaseInstanceId";
            zzk = "Token retrieval failed: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.e(str, valueOf.length() == 0 ? new String(zzk) : zzk.concat(valueOf));
            return false;
        }
    }

    final Context getContext() {
        return this.zzdj.zzh().getApplicationContext();
    }

    final boolean zzao() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
