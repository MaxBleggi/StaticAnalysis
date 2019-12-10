package com.google.firebase.iid;

import android.os.Handler.Callback;
import android.os.Message;

final /* synthetic */ class zzae implements Callback {
    private final zzad zzcc;

    zzae(zzad com_google_firebase_iid_zzad) {
        this.zzcc = com_google_firebase_iid_zzad;
    }

    public final boolean handleMessage(Message message) {
        return this.zzcc.zza(message);
    }
}
