package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.dynamiclinks.DynamicLink.Builder;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public final class zzts extends FirebaseDynamicLinks {
    private final GoogleApi<NoOptions> zzbug;

    public zzts(@NonNull Context context) {
        this(new zztp(context));
    }

    @VisibleForTesting
    private zzts(@NonNull GoogleApi<NoOptions> googleApi) {
        this.zzbug = googleApi;
    }

    private final void zzuc() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r2 = this;
        r0 = r2.zzbug;	 Catch:{ NoClassDefFoundError -> 0x000a }
        r0 = r0.getApplicationContext();	 Catch:{ NoClassDefFoundError -> 0x000a }
        com.google.android.gms.measurement.AppMeasurement.getInstance(r0);	 Catch:{ NoClassDefFoundError -> 0x000a }
        return;
    L_0x000a:
        r0 = "FDL";
        r1 = "FDL logging failed. Add a dependency for Firebase Analytics to your app to enable logging of Dynamic Link events.";
        android.util.Log.w(r0, r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzts.zzuc():void");
    }

    public final Task<PendingDynamicLinkData> getDynamicLink(@NonNull Intent intent) {
        zzuc();
        Task<PendingDynamicLinkData> doWrite = this.zzbug.doWrite(new zztx(this.zzbug.getApplicationContext(), intent.getDataString()));
        zztn com_google_android_gms_internal_measurement_zztn = (zztn) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.firebase.dynamiclinks.DYNAMIC_LINK_DATA", zztn.CREATOR);
        Object pendingDynamicLinkData = com_google_android_gms_internal_measurement_zztn != null ? new PendingDynamicLinkData(com_google_android_gms_internal_measurement_zztn) : null;
        return pendingDynamicLinkData != null ? Tasks.forResult(pendingDynamicLinkData) : doWrite;
    }

    public final Task<PendingDynamicLinkData> getDynamicLink(@NonNull Uri uri) {
        zzuc();
        return this.zzbug.doWrite(new zztx(this.zzbug.getApplicationContext(), uri.toString()));
    }

    public final Builder createDynamicLink() {
        return new Builder(this);
    }

    public final Task<ShortDynamicLink> zzh(Bundle bundle) {
        zzi(bundle);
        return this.zzbug.doWrite(new zztv(bundle));
    }

    public static void zzi(Bundle bundle) {
        Uri uri = (Uri) bundle.getParcelable("dynamicLink");
        if (TextUtils.isEmpty(bundle.getString("domainUriPrefix")) == null) {
            return;
        }
        if (uri == null) {
            throw new IllegalArgumentException("FDL domain is missing. Set with setDomainUriPrefix() or setDynamicLinkDomain().");
        }
    }
}
