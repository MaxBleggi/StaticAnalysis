package com.google.android.gms.cast.framework;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.mediarouter.app.MediaRouteActionProvider;
import androidx.mediarouter.app.MediaRouteButton;
import androidx.mediarouter.app.MediaRouteDialogFactory;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.cast.zzdh;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class CastButtonFactory {
    private static final zzdh zzbe = new zzdh("CastButtonFactory");
    private static final List<WeakReference<MenuItem>> zzhe = new ArrayList();
    private static final List<WeakReference<MediaRouteButton>> zzhf = new ArrayList();

    private CastButtonFactory() {
    }

    public static MenuItem setUpMediaRouteButton(Context context, Menu menu, int i) {
        return zza(context, menu, i, null);
    }

    private static android.view.MenuItem zza(android.content.Context r2, android.view.Menu r3, int r4, androidx.mediarouter.app.MediaRouteDialogFactory r5) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r5 = "Must be called from the main thread.";
        com.google.android.gms.common.internal.Preconditions.checkMainThread(r5);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r3);
        r3 = r3.findItem(r4);
        r5 = 0;
        r0 = 1;
        if (r3 == 0) goto L_0x0035;
    L_0x0010:
        r1 = 0;
        zza(r2, r3, r1);	 Catch:{ IllegalArgumentException -> 0x001f }
        r2 = zzhe;	 Catch:{ IllegalArgumentException -> 0x001f }
        r1 = new java.lang.ref.WeakReference;	 Catch:{ IllegalArgumentException -> 0x001f }
        r1.<init>(r3);	 Catch:{ IllegalArgumentException -> 0x001f }
        r2.add(r1);	 Catch:{ IllegalArgumentException -> 0x001f }
        return r3;
    L_0x001f:
        r2 = new java.lang.IllegalArgumentException;
        r3 = java.util.Locale.ROOT;
        r0 = new java.lang.Object[r0];
        r4 = java.lang.Integer.valueOf(r4);
        r0[r5] = r4;
        r4 = "menu item with ID %d doesn't have a MediaRouteActionProvider.";
        r3 = java.lang.String.format(r3, r4, r0);
        r2.<init>(r3);
        throw r2;
    L_0x0035:
        r2 = new java.lang.IllegalArgumentException;
        r3 = java.util.Locale.ROOT;
        r0 = new java.lang.Object[r0];
        r4 = java.lang.Integer.valueOf(r4);
        r0[r5] = r4;
        r4 = "menu doesn't contain a menu item whose ID is %d.";
        r3 = java.lang.String.format(r3, r4, r0);
        r2.<init>(r3);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.CastButtonFactory.zza(android.content.Context, android.view.Menu, int, androidx.mediarouter.app.MediaRouteDialogFactory):android.view.MenuItem");
    }

    public static void setUpMediaRouteButton(Context context, MediaRouteButton mediaRouteButton) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (mediaRouteButton != null) {
            zza(context, mediaRouteButton, null);
            zzhf.add(new WeakReference(mediaRouteButton));
        }
    }

    public static void zza(Context context) {
        for (WeakReference weakReference : zzhe) {
            try {
                if (weakReference.get() != null) {
                    zza(context, (MenuItem) weakReference.get(), null);
                }
            } catch (IllegalArgumentException e) {
                zzbe.w("Unexpected exception when refreshing MediaRouteSelectors for Cast buttons", e);
            }
        }
        for (WeakReference weakReference2 : zzhf) {
            if (weakReference2.get() != null) {
                zza(context, (MediaRouteButton) weakReference2.get(), null);
            }
        }
    }

    private static void zza(Context context, @NonNull MenuItem menuItem, MediaRouteDialogFactory mediaRouteDialogFactory) throws IllegalArgumentException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaRouteActionProvider mediaRouteActionProvider = (MediaRouteActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (mediaRouteActionProvider != null) {
            context = CastContext.zzb(context);
            if (context != null) {
                mediaRouteActionProvider.setRouteSelector(context.getMergedSelector());
                return;
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    private static void zza(Context context, @NonNull MediaRouteButton mediaRouteButton, MediaRouteDialogFactory mediaRouteDialogFactory) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        context = CastContext.zzb(context);
        if (context != null) {
            mediaRouteButton.setRouteSelector(context.getMergedSelector());
        }
    }
}
