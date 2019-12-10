package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.util.Map;

final class zzaw {
    private final SharedPreferences zzdc;
    private final zzy zzdd;
    @GuardedBy("this")
    private final Map<String, zzz> zzde;
    private final Context zzx;

    public zzaw(Context context) {
        this(context, new zzy());
    }

    private zzaw(Context context, zzy com_google_firebase_iid_zzy) {
        this.zzde = new ArrayMap();
        this.zzx = context;
        this.zzdc = context.getSharedPreferences("com.google.android.gms.appid", 0);
        this.zzdd = com_google_firebase_iid_zzy;
        File file = new File(ContextCompat.getNoBackupFilesDir(this.zzx), "com.google.android.gms.appid-no-backup");
        if (file.exists() == null) {
            try {
                if (file.createNewFile() != null && isEmpty() == null) {
                    Log.i("FirebaseInstanceId", "App restored, clearing state");
                    zzal();
                    FirebaseInstanceId.getInstance().zzm();
                }
            } catch (Context context2) {
                if (Log.isLoggable("FirebaseInstanceId", 3) != null) {
                    com_google_firebase_iid_zzy = "FirebaseInstanceId";
                    String str = "Error creating file in no backup dir: ";
                    context2 = String.valueOf(context2.getMessage());
                    Log.d(com_google_firebase_iid_zzy, context2.length() != 0 ? str.concat(context2) : new String(str));
                }
            }
        }
    }

    public final synchronized String zzak() {
        return this.zzdc.getString("topic_operaion_queue", "");
    }

    public final synchronized void zzf(String str) {
        this.zzdc.edit().putString("topic_operaion_queue", str).apply();
    }

    private final synchronized boolean isEmpty() {
        return this.zzdc.getAll().isEmpty();
    }

    private static String zza(String str, String str2, String str3) {
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str).length() + 4) + String.valueOf(str2).length()) + String.valueOf(str3).length());
        stringBuilder.append(str);
        stringBuilder.append("|T|");
        stringBuilder.append(str2);
        stringBuilder.append("|");
        stringBuilder.append(str3);
        return stringBuilder.toString();
    }

    static String zzd(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 3) + String.valueOf(str2).length());
        stringBuilder.append(str);
        stringBuilder.append("|S|");
        stringBuilder.append(str2);
        return stringBuilder.toString();
    }

    public final synchronized void zzal() {
        this.zzde.clear();
        zzy.zza(this.zzx);
        this.zzdc.edit().clear().commit();
    }

    public final synchronized zzax zzb(String str, String str2, String str3) {
        return zzax.zzi(this.zzdc.getString(zza(str, str2, str3), null));
    }

    public final synchronized void zza(String str, String str2, String str3, String str4, String str5) {
        str4 = zzax.zza(str4, str5, System.currentTimeMillis());
        if (str4 != null) {
            str5 = this.zzdc.edit();
            str5.putString(zza(str, str2, str3), str4);
            str5.commit();
        }
    }

    public final synchronized void zzc(String str, String str2, String str3) {
        str = zza(str, str2, str3);
        str2 = this.zzdc.edit();
        str2.remove(str);
        str2.commit();
    }

    public final synchronized com.google.firebase.iid.zzz zzg(java.lang.String r3) {
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
        monitor-enter(r2);
        r0 = r2.zzde;	 Catch:{ all -> 0x0033 }
        r0 = r0.get(r3);	 Catch:{ all -> 0x0033 }
        r0 = (com.google.firebase.iid.zzz) r0;	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x000d;
    L_0x000b:
        monitor-exit(r2);
        return r0;
    L_0x000d:
        r0 = r2.zzdd;	 Catch:{ zzaa -> 0x0016 }
        r1 = r2.zzx;	 Catch:{ zzaa -> 0x0016 }
        r0 = r0.zzb(r1, r3);	 Catch:{ zzaa -> 0x0016 }
        goto L_0x002c;
    L_0x0016:
        r0 = "FirebaseInstanceId";	 Catch:{ all -> 0x0033 }
        r1 = "Stored data is corrupt, generating new identity";	 Catch:{ all -> 0x0033 }
        android.util.Log.w(r0, r1);	 Catch:{ all -> 0x0033 }
        r0 = com.google.firebase.iid.FirebaseInstanceId.getInstance();	 Catch:{ all -> 0x0033 }
        r0.zzm();	 Catch:{ all -> 0x0033 }
        r0 = r2.zzdd;	 Catch:{ all -> 0x0033 }
        r1 = r2.zzx;	 Catch:{ all -> 0x0033 }
        r0 = r0.zzc(r1, r3);	 Catch:{ all -> 0x0033 }
    L_0x002c:
        r1 = r2.zzde;	 Catch:{ all -> 0x0033 }
        r1.put(r3, r0);	 Catch:{ all -> 0x0033 }
        monitor-exit(r2);
        return r0;
    L_0x0033:
        r3 = move-exception;
        monitor-exit(r2);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzaw.zzg(java.lang.String):com.google.firebase.iid.zzz");
    }

    public final synchronized void zzh(String str) {
        str = String.valueOf(str).concat("|T|");
        Editor edit = this.zzdc.edit();
        for (String str2 : this.zzdc.getAll().keySet()) {
            if (str2.startsWith(str)) {
                edit.remove(str2);
            }
        }
        edit.commit();
    }
}
