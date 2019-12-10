package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.util.VisibleForTesting;

@VisibleForTesting
final class zzan extends SQLiteOpenHelper {
    private final /* synthetic */ zzam zzalz;

    zzan(zzam com_google_android_gms_measurement_internal_zzam, Context context, String str) {
        this.zzalz = com_google_android_gms_measurement_internal_zzam;
        super(context, str, null, 1);
    }

    @WorkerThread
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    @WorkerThread
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    @androidx.annotation.WorkerThread
    public final android.database.sqlite.SQLiteDatabase getWritableDatabase() throws android.database.sqlite.SQLiteException {
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
        r3 = this;
        r0 = super.getWritableDatabase();	 Catch:{ SQLiteDatabaseLockedException -> 0x004c, SQLiteException -> 0x0005 }
        return r0;
    L_0x0005:
        r0 = r3.zzalz;
        r0 = r0.zzgt();
        r0 = r0.zzjg();
        r1 = "Opening the local database failed, dropping and recreating it";
        r0.zzca(r1);
        r0 = "google_app_measurement_local.db";
        r1 = r3.zzalz;
        r1 = r1.getContext();
        r1 = r1.getDatabasePath(r0);
        r1 = r1.delete();
        if (r1 != 0) goto L_0x0035;
    L_0x0026:
        r1 = r3.zzalz;
        r1 = r1.zzgt();
        r1 = r1.zzjg();
        r2 = "Failed to delete corrupted local db file";
        r1.zzg(r2, r0);
    L_0x0035:
        r0 = super.getWritableDatabase();	 Catch:{ SQLiteException -> 0x003a }
        return r0;
    L_0x003a:
        r0 = move-exception;
        r1 = r3.zzalz;
        r1 = r1.zzgt();
        r1 = r1.zzjg();
        r2 = "Failed to open local database. Events will bypass local storage";
        r1.zzg(r2, r0);
        r0 = 0;
        return r0;
    L_0x004c:
        r0 = move-exception;
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzan.getWritableDatabase():android.database.sqlite.SQLiteDatabase");
    }

    @WorkerThread
    public final void onOpen(SQLiteDatabase sQLiteDatabase) {
        if (VERSION.SDK_INT < 15) {
            Cursor cursor = null;
            try {
                Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
                try {
                    rawQuery.moveToFirst();
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                } catch (Throwable th) {
                    sQLiteDatabase = th;
                    cursor = rawQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw sQLiteDatabase;
                }
            } catch (Throwable th2) {
                sQLiteDatabase = th2;
                if (cursor != null) {
                    cursor.close();
                }
                throw sQLiteDatabase;
            }
        }
        zzv.zza(this.zzalz.zzgt(), sQLiteDatabase, "messages", "create table if not exists messages ( type INTEGER NOT NULL, entry BLOB NOT NULL)", "type,entry", null);
    }

    @WorkerThread
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        zzv.zza(this.zzalz.zzgt(), sQLiteDatabase);
    }
}
