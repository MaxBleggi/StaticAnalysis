package com.google.firebase.iid;

import android.util.Pair;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

final /* synthetic */ class zzar implements Continuation {
    private final zzaq zzcp;
    private final Pair zzcq;

    zzar(zzaq com_google_firebase_iid_zzaq, Pair pair) {
        this.zzcp = com_google_firebase_iid_zzaq;
        this.zzcq = pair;
    }

    public final Object then(Task task) {
        return this.zzcp.zza(this.zzcq, task);
    }
}
