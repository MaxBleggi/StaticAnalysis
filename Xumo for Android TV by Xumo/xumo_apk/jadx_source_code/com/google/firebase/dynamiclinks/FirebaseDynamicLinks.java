package com.google.firebase.dynamiclinks;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.gms.internal.measurement.zzts;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.dynamiclinks.DynamicLink.Builder;
import java.lang.ref.WeakReference;
import javax.annotation.concurrent.GuardedBy;

public abstract class FirebaseDynamicLinks {
    @GuardedBy("FirebaseDynamicLinks.class")
    private static WeakReference<FirebaseDynamicLinks> zzbty;

    public abstract Builder createDynamicLink();

    public abstract Task<PendingDynamicLinkData> getDynamicLink(@NonNull Intent intent);

    public abstract Task<PendingDynamicLinkData> getDynamicLink(@NonNull Uri uri);

    public static synchronized FirebaseDynamicLinks getInstance() {
        FirebaseDynamicLinks firebaseDynamicLinks;
        synchronized (FirebaseDynamicLinks.class) {
            firebaseDynamicLinks = zzbty == null ? null : (FirebaseDynamicLinks) zzbty.get();
            if (firebaseDynamicLinks == null) {
                firebaseDynamicLinks = new zzts(FirebaseApp.getInstance().getApplicationContext());
                zzbty = new WeakReference(firebaseDynamicLinks);
            }
        }
        return firebaseDynamicLinks;
    }
}
