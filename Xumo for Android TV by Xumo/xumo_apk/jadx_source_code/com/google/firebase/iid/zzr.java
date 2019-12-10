package com.google.firebase.iid;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import java.io.IOException;
import java.util.concurrent.Executor;

final class zzr implements MessagingChannel {
    private final FirebaseApp zzam;
    private final zzan zzan;
    private final zzat zzbi;
    private final Executor zzbj;

    zzr(FirebaseApp firebaseApp, zzan com_google_firebase_iid_zzan, Executor executor) {
        this(firebaseApp, com_google_firebase_iid_zzan, executor, new zzat(firebaseApp.getApplicationContext(), com_google_firebase_iid_zzan));
    }

    public final Task<Void> ackMessage(String str) {
        return null;
    }

    public final boolean isChannelBuilt() {
        return true;
    }

    @VisibleForTesting
    private zzr(FirebaseApp firebaseApp, zzan com_google_firebase_iid_zzan, Executor executor, zzat com_google_firebase_iid_zzat) {
        this.zzam = firebaseApp;
        this.zzan = com_google_firebase_iid_zzan;
        this.zzbi = com_google_firebase_iid_zzat;
        this.zzbj = executor;
    }

    public final boolean isAvailable() {
        return this.zzan.zzac() != 0;
    }

    public final Task<Void> buildChannel(String str, String str2) {
        return Tasks.forResult(null);
    }

    public final Task<String> getToken(String str, String str2, String str3, String str4) {
        return zzc(zza(str, str3, str4, new Bundle()));
    }

    public final Task<Void> deleteToken(String str, String str2, String str3, String str4) {
        str2 = new Bundle();
        str2.putString("delete", "1");
        return zzb(zzc(zza(str, str3, str4, str2)));
    }

    public final Task<Void> deleteInstanceId(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("iid-operation", "delete");
        bundle.putString("delete", "1");
        return zzb(zzc(zza(str, "*", "*", bundle)));
    }

    public final Task<Void> subscribeToTopic(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        String str4 = "gcm.topic";
        String valueOf = String.valueOf("/topics/");
        String valueOf2 = String.valueOf(str3);
        bundle.putString(str4, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        str4 = String.valueOf("/topics/");
        str3 = String.valueOf(str3);
        return zzb(zzc(zza(str, str2, str3.length() != 0 ? str4.concat(str3) : new String(str4), bundle)));
    }

    public final Task<Void> unsubscribeFromTopic(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        String str4 = "gcm.topic";
        String valueOf = String.valueOf("/topics/");
        String valueOf2 = String.valueOf(str3);
        bundle.putString(str4, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        bundle.putString("delete", "1");
        str4 = String.valueOf("/topics/");
        str3 = String.valueOf(str3);
        return zzb(zzc(zza(str, str2, str3.length() != 0 ? str4.concat(str3) : new String(str4), bundle)));
    }

    private final Task<Bundle> zza(String str, String str2, String str3, Bundle bundle) {
        bundle.putString("scope", str3);
        bundle.putString("sender", str2);
        bundle.putString("subtype", str2);
        bundle.putString("appid", str);
        bundle.putString("gmp_app_id", this.zzam.getOptions().getApplicationId());
        bundle.putString("gmsv", Integer.toString(this.zzan.zzaf()));
        bundle.putString("osv", Integer.toString(VERSION.SDK_INT));
        bundle.putString("app_ver", this.zzan.zzad());
        bundle.putString("app_ver_name", this.zzan.zzae());
        bundle.putString("cliv", "fiid-12451000");
        str = new TaskCompletionSource();
        this.zzbj.execute(new zzs(this, bundle, str));
        return str.getTask();
    }

    private static String zza(Bundle bundle) throws IOException {
        if (bundle != null) {
            String string = bundle.getString("registration_id");
            if (string != null) {
                return string;
            }
            string = bundle.getString("unregistered");
            if (string != null) {
                return string;
            }
            string = bundle.getString(MediaRouteProviderProtocol.SERVICE_DATA_ERROR);
            if ("RST".equals(string)) {
                throw new IOException("INSTANCE_ID_RESET");
            } else if (string != null) {
                throw new IOException(string);
            } else {
                bundle = String.valueOf(bundle);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(bundle).length() + 21);
                stringBuilder.append("Unexpected response: ");
                stringBuilder.append(bundle);
                Log.w("FirebaseInstanceId", stringBuilder.toString(), new Throwable());
                throw new IOException("SERVICE_NOT_AVAILABLE");
            }
        }
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    private final <T> Task<Void> zzb(Task<T> task) {
        return task.continueWith(zzi.zze(), new zzt(this));
    }

    private final Task<String> zzc(Task<Bundle> task) {
        return task.continueWith(this.zzbj, new zzu(this));
    }

    final /* synthetic */ void zza(Bundle bundle, TaskCompletionSource taskCompletionSource) {
        try {
            taskCompletionSource.setResult(this.zzbi.zzc(bundle));
        } catch (Bundle bundle2) {
            taskCompletionSource.setException(bundle2);
        }
    }
}
