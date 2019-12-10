package com.google.firebase.iid;

import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;

final /* synthetic */ class zzq implements EventHandler {
    private final zza zzbe;

    zzq(zza com_google_firebase_iid_FirebaseInstanceId_zza) {
        this.zzbe = com_google_firebase_iid_FirebaseInstanceId_zza;
    }

    public final void handle(Event event) {
        event = this.zzbe;
        synchronized (event) {
            if (event.isEnabled()) {
                event.zzbd.zzg();
            }
        }
    }
}
