package com.google.android.gms.common.api.internal;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;
import java.util.Map;

final class zaaa implements OnCompleteListener<Map<zai<?>, String>> {
    private final /* synthetic */ zax zafh;
    private SignInConnectionListener zafi;

    zaaa(zax com_google_android_gms_common_api_internal_zax, SignInConnectionListener signInConnectionListener) {
        this.zafh = com_google_android_gms_common_api_internal_zax;
        this.zafi = signInConnectionListener;
    }

    final void cancel() {
        this.zafi.onComplete();
    }

    public final void onComplete(@NonNull Task<Map<zai<?>, String>> task) {
        this.zafh.zaen.lock();
        try {
            if (this.zafh.zafc) {
                if (task.isSuccessful()) {
                    this.zafh.zafe = new ArrayMap(this.zafh.zaeu.size());
                    task = this.zafh.zaeu.values().iterator();
                    while (task.hasNext()) {
                        this.zafh.zafe.put(((zaw) task.next()).zak(), ConnectionResult.RESULT_SUCCESS);
                    }
                } else if (task.getException() instanceof AvailabilityException) {
                    AvailabilityException availabilityException = (AvailabilityException) task.getException();
                    if (this.zafh.zafa) {
                        this.zafh.zafe = new ArrayMap(this.zafh.zaeu.size());
                        for (zaw com_google_android_gms_common_api_internal_zaw : this.zafh.zaeu.values()) {
                            zai zak = com_google_android_gms_common_api_internal_zaw.zak();
                            ConnectionResult connectionResult = availabilityException.getConnectionResult(com_google_android_gms_common_api_internal_zaw);
                            if (this.zafh.zaa(com_google_android_gms_common_api_internal_zaw, connectionResult)) {
                                this.zafh.zafe.put(zak, new ConnectionResult(16));
                            } else {
                                this.zafh.zafe.put(zak, connectionResult);
                            }
                        }
                    } else {
                        this.zafh.zafe = availabilityException.zaj();
                    }
                } else {
                    Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                    this.zafh.zafe = Collections.emptyMap();
                }
                if (this.zafh.isConnected() != null) {
                    this.zafh.zafd.putAll(this.zafh.zafe);
                    if (this.zafh.zaaf() == null) {
                        this.zafh.zaad();
                        this.zafh.zaae();
                        this.zafh.zaey.signalAll();
                    }
                }
                this.zafi.onComplete();
                this.zafh.zaen.unlock();
                return;
            }
            this.zafi.onComplete();
        } finally {
            this.zafh.zaen.unlock();
        }
    }
}
