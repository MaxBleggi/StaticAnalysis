package com.google.firebase.iid;

import android.content.Intent;
import android.util.Log;
import androidx.annotation.WorkerThread;

@Deprecated
public class FirebaseInstanceIdService extends zzb {
    @WorkerThread
    @Deprecated
    public void onTokenRefresh() {
    }

    protected final Intent zzb(Intent intent) {
        return (Intent) zzav.zzai().zzda.poll();
    }

    public final void zzd(Intent intent) {
        if ("com.google.firebase.iid.TOKEN_REFRESH".equals(intent.getAction())) {
            onTokenRefresh();
            return;
        }
        String stringExtra = intent.getStringExtra("CMD");
        if (stringExtra != null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                intent = String.valueOf(intent.getExtras());
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(stringExtra).length() + 21) + String.valueOf(intent).length());
                stringBuilder.append("Received command: ");
                stringBuilder.append(stringExtra);
                stringBuilder.append(" - ");
                stringBuilder.append(intent);
                Log.d("FirebaseInstanceId", stringBuilder.toString());
            }
            if ("RST".equals(stringExtra) == null) {
                if ("RST_FULL".equals(stringExtra) == null) {
                    if ("SYNC".equals(stringExtra) != null) {
                        FirebaseInstanceId.getInstance().zzq();
                    }
                }
            }
            FirebaseInstanceId.getInstance().zzm();
        }
    }
}
