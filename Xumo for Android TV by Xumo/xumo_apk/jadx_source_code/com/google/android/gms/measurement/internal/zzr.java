package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.WorkerThread;
import com.google.android.exoplayer2.upstream.DataSchemeDataSource;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzfx;
import com.google.android.gms.internal.measurement.zzfy;
import com.google.android.gms.internal.measurement.zzgb;
import com.google.android.gms.internal.measurement.zzgi;
import com.google.android.gms.internal.measurement.zzgj;
import com.google.android.gms.internal.measurement.zzgl;
import com.google.android.gms.internal.measurement.zzzi;
import com.google.android.gms.internal.measurement.zzzj;
import com.google.android.gms.internal.measurement.zzzr;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

final class zzr extends zzfj {
    private static final String[] zzahl = new String[]{"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_bundled_day", "ALTER TABLE events ADD COLUMN last_bundled_day INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;"};
    private static final String[] zzahm = new String[]{"origin", "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    private static final String[] zzahn = new String[]{"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;", "ssaid_reporting_enabled", "ALTER TABLE apps ADD COLUMN ssaid_reporting_enabled INTEGER;", "admob_app_id", "ALTER TABLE apps ADD COLUMN admob_app_id TEXT;", "linked_admob_app_id", "ALTER TABLE apps ADD COLUMN linked_admob_app_id TEXT;"};
    private static final String[] zzaho = new String[]{"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    private static final String[] zzahp = new String[]{"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;", "retry_count", "ALTER TABLE queue ADD COLUMN retry_count INTEGER;"};
    private static final String[] zzahq = new String[]{"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzu zzahr = new zzu(this, getContext(), "google_app_measurement.db");
    private final zzff zzahs = new zzff(zzbx());

    zzr(zzfk com_google_android_gms_measurement_internal_zzfk) {
        super(com_google_android_gms_measurement_internal_zzfk);
    }

    protected final boolean zzgy() {
        return false;
    }

    @WorkerThread
    public final void beginTransaction() {
        zzcl();
        getWritableDatabase().beginTransaction();
    }

    @WorkerThread
    public final void setTransactionSuccessful() {
        zzcl();
        getWritableDatabase().setTransactionSuccessful();
    }

    @WorkerThread
    public final void endTransaction() {
        zzcl();
        getWritableDatabase().endTransaction();
    }

    @WorkerThread
    private final long zza(String str, String[] strArr) {
        Object e;
        String[] strArr2 = null;
        try {
            strArr = getWritableDatabase().rawQuery(str, strArr);
            try {
                if (strArr.moveToFirst()) {
                    long j = strArr.getLong(0);
                    if (strArr != null) {
                        strArr.close();
                    }
                    return j;
                }
                throw new SQLiteException("Database returned empty set");
            } catch (SQLiteException e2) {
                e = e2;
                strArr2 = strArr;
                try {
                    zzgt().zzjg().zze("Database error", str, e);
                    throw e;
                } catch (Throwable th) {
                    str = th;
                    strArr = strArr2;
                    if (strArr != null) {
                        strArr.close();
                    }
                    throw str;
                }
            } catch (Throwable th2) {
                str = th2;
                if (strArr != null) {
                    strArr.close();
                }
                throw str;
            }
        } catch (SQLiteException e3) {
            e = e3;
            zzgt().zzjg().zze("Database error", str, e);
            throw e;
        }
    }

    @WorkerThread
    private final long zza(String str, String[] strArr, long j) {
        Cursor cursor = null;
        try {
            strArr = getWritableDatabase().rawQuery(str, strArr);
            try {
                if (strArr.moveToFirst()) {
                    j = strArr.getLong(0);
                    if (strArr != null) {
                        strArr.close();
                    }
                    return j;
                }
                if (strArr != null) {
                    strArr.close();
                }
                return j;
            } catch (SQLiteException e) {
                j = e;
                cursor = strArr;
                try {
                    zzgt().zzjg().zze("Database error", str, j);
                    throw j;
                } catch (Throwable th) {
                    str = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw str;
                }
            } catch (Throwable th2) {
                str = th2;
                cursor = strArr;
                if (cursor != null) {
                    cursor.close();
                }
                throw str;
            }
        } catch (SQLiteException e2) {
            j = e2;
            zzgt().zzjg().zze("Database error", str, j);
            throw j;
        }
    }

    @WorkerThread
    @VisibleForTesting
    final SQLiteDatabase getWritableDatabase() {
        zzaf();
        try {
            return this.zzahr.getWritableDatabase();
        } catch (SQLiteException e) {
            zzgt().zzjj().zzg("Error opening database", e);
            throw e;
        }
    }

    @WorkerThread
    public final zzaa zzg(String str, String str2) {
        Cursor cursor;
        Object e;
        Throwable th;
        String str3 = str2;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzaf();
        zzcl();
        try {
            r5 = new String[2];
            boolean z = false;
            r5[0] = str;
            r5[1] = str3;
            Cursor query = getWritableDatabase().query("events", new String[]{"lifetime_count", "current_bundle_count", "last_fire_timestamp", "last_bundled_timestamp", "last_bundled_day", "last_sampled_complex_event_id", "last_sampling_rate", "last_exempt_from_sampling"}, "app_id=? and name=?", r5, null, null, null);
            try {
                if (query.moveToFirst()) {
                    Boolean bool;
                    long j = query.getLong(0);
                    long j2 = query.getLong(1);
                    long j3 = query.getLong(2);
                    long j4 = query.isNull(3) ? 0 : query.getLong(3);
                    Long valueOf = query.isNull(4) ? null : Long.valueOf(query.getLong(4));
                    Long valueOf2 = query.isNull(5) ? null : Long.valueOf(query.getLong(5));
                    zzaa valueOf3 = query.isNull(6) ? null : Long.valueOf(query.getLong(6));
                    if (query.isNull(7)) {
                        bool = null;
                    } else {
                        if (query.getLong(7) == 1) {
                            z = true;
                        }
                        bool = Boolean.valueOf(z);
                    }
                    zzaa com_google_android_gms_measurement_internal_zzaa = com_google_android_gms_measurement_internal_zzaa;
                    long j5 = j4;
                    cursor = query;
                    try {
                        com_google_android_gms_measurement_internal_zzaa = new zzaa(str, str2, j, j2, j3, j5, valueOf, valueOf2, valueOf3, bool);
                        if (cursor.moveToNext()) {
                            zzgt().zzjg().zzg("Got multiple records for event aggregates, expected one. appId", zzaq.zzby(str));
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                        return com_google_android_gms_measurement_internal_zzaa;
                    } catch (SQLiteException e2) {
                        e = e2;
                        try {
                            zzgt().zzjg().zzd("Error querying events. appId", zzaq.zzby(str), zzgq().zzbv(str2), e);
                            if (cursor != null) {
                                cursor.close();
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th;
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e3) {
                e = e3;
                cursor = query;
                zzgt().zzjg().zzd("Error querying events. appId", zzaq.zzby(str), zzgq().zzbv(str2), e);
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                cursor = query;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException e4) {
            e = e4;
            cursor = null;
            zzgt().zzjg().zzd("Error querying events. appId", zzaq.zzby(str), zzgq().zzbv(str2), e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final void zza(zzaa com_google_android_gms_measurement_internal_zzaa) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzaa);
        zzaf();
        zzcl();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", com_google_android_gms_measurement_internal_zzaa.zztt);
        contentValues.put(JsonKey.NAME, com_google_android_gms_measurement_internal_zzaa.name);
        contentValues.put("lifetime_count", Long.valueOf(com_google_android_gms_measurement_internal_zzaa.zzaih));
        contentValues.put("current_bundle_count", Long.valueOf(com_google_android_gms_measurement_internal_zzaa.zzaii));
        contentValues.put("last_fire_timestamp", Long.valueOf(com_google_android_gms_measurement_internal_zzaa.zzaij));
        contentValues.put("last_bundled_timestamp", Long.valueOf(com_google_android_gms_measurement_internal_zzaa.zzaik));
        contentValues.put("last_bundled_day", com_google_android_gms_measurement_internal_zzaa.zzail);
        contentValues.put("last_sampled_complex_event_id", com_google_android_gms_measurement_internal_zzaa.zzaim);
        contentValues.put("last_sampling_rate", com_google_android_gms_measurement_internal_zzaa.zzain);
        Long valueOf = (com_google_android_gms_measurement_internal_zzaa.zzaio == null || !com_google_android_gms_measurement_internal_zzaa.zzaio.booleanValue()) ? null : Long.valueOf(1);
        contentValues.put("last_exempt_from_sampling", valueOf);
        try {
            if (getWritableDatabase().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                zzgt().zzjg().zzg("Failed to insert/update event aggregates (got -1). appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzaa.zztt));
            }
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Error storing event aggregates. appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzaa.zztt), e);
        }
    }

    @WorkerThread
    public final void zzh(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzaf();
        zzcl();
        try {
            zzgt().zzjo().zzg("Deleted user attribute rows", Integer.valueOf(getWritableDatabase().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2})));
        } catch (SQLiteException e) {
            zzgt().zzjg().zzd("Error deleting user attribute. appId", zzaq.zzby(str), zzgq().zzbx(str2), e);
        }
    }

    @WorkerThread
    public final boolean zza(zzft com_google_android_gms_measurement_internal_zzft) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzft);
        zzaf();
        zzcl();
        if (zzi(com_google_android_gms_measurement_internal_zzft.zztt, com_google_android_gms_measurement_internal_zzft.name) == null) {
            if (zzfu.zzcv(com_google_android_gms_measurement_internal_zzft.name)) {
                if (zza("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{com_google_android_gms_measurement_internal_zzft.zztt}) >= 25) {
                    return false;
                }
            }
            if (zza("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{com_google_android_gms_measurement_internal_zzft.zztt, com_google_android_gms_measurement_internal_zzft.origin}) >= 25) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", com_google_android_gms_measurement_internal_zzft.zztt);
        contentValues.put("origin", com_google_android_gms_measurement_internal_zzft.origin);
        contentValues.put(JsonKey.NAME, com_google_android_gms_measurement_internal_zzft.name);
        contentValues.put("set_timestamp", Long.valueOf(com_google_android_gms_measurement_internal_zzft.zzaux));
        zza(contentValues, "value", com_google_android_gms_measurement_internal_zzft.value);
        try {
            if (getWritableDatabase().insertWithOnConflict("user_attributes", null, contentValues, 5) == -1) {
                zzgt().zzjg().zzg("Failed to insert/update user property (got -1). appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzft.zztt));
            }
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Error storing user property. appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzft.zztt), e);
        }
        return true;
    }

    @WorkerThread
    public final zzft zzi(String str, String str2) {
        Object e;
        Throwable th;
        zzr com_google_android_gms_measurement_internal_zzr;
        String str3 = str2;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzaf();
        zzcl();
        Cursor query;
        try {
            query = getWritableDatabase().query("user_attributes", new String[]{"set_timestamp", "value", "origin"}, "app_id=? and name=?", new String[]{str, str3}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    long j = query.getLong(0);
                    try {
                        String str4 = str;
                        zzft com_google_android_gms_measurement_internal_zzft = new zzft(str4, query.getString(2), str2, j, zza(query, 1));
                        if (query.moveToNext()) {
                            zzgt().zzjg().zzg("Got multiple records for user property, expected one. appId", zzaq.zzby(str));
                        }
                        if (query != null) {
                            query.close();
                        }
                        return com_google_android_gms_measurement_internal_zzft;
                    } catch (SQLiteException e2) {
                        e = e2;
                        try {
                            zzgt().zzjg().zzd("Error querying user property. appId", zzaq.zzby(str), zzgq().zzbx(str3), e);
                            if (query != null) {
                                query.close();
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                            if (query != null) {
                                query.close();
                            }
                            throw th;
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e3) {
                e = e3;
                com_google_android_gms_measurement_internal_zzr = this;
                zzgt().zzjg().zzd("Error querying user property. appId", zzaq.zzby(str), zzgq().zzbx(str3), e);
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                com_google_android_gms_measurement_internal_zzr = this;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (SQLiteException e4) {
            e = e4;
            com_google_android_gms_measurement_internal_zzr = this;
            query = null;
            zzgt().zzjg().zzd("Error querying user property. appId", zzaq.zzby(str), zzgq().zzbx(str3), e);
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            com_google_android_gms_measurement_internal_zzr = this;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final List<zzft> zzbn(String str) {
        Object e;
        Preconditions.checkNotEmpty(str);
        zzaf();
        zzcl();
        List<zzft> arrayList = new ArrayList();
        Cursor query;
        try {
            query = getWritableDatabase().query("user_attributes", new String[]{JsonKey.NAME, "origin", "set_timestamp", "value"}, "app_id=?", new String[]{str}, null, null, "rowid", "1000");
            try {
                if (query.moveToFirst()) {
                    do {
                        String string = query.getString(0);
                        String string2 = query.getString(1);
                        if (string2 == null) {
                            string2 = "";
                        }
                        String str2 = string2;
                        long j = query.getLong(2);
                        Object zza = zza(query, 3);
                        if (zza == null) {
                            zzgt().zzjg().zzg("Read invalid user property value, ignoring it. appId", zzaq.zzby(str));
                        } else {
                            arrayList.add(new zzft(str, str2, string, j, zza));
                        }
                    } while (query.moveToNext());
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                }
                if (query != null) {
                    query.close();
                }
                return arrayList;
            } catch (SQLiteException e2) {
                e = e2;
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            try {
                zzgt().zzjg().zze("Error querying user properties. appId", zzaq.zzby(str), e);
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (Throwable th) {
                str = th;
                if (query != null) {
                    query.close();
                }
                throw str;
            }
        } catch (Throwable th2) {
            str = th2;
            query = null;
            if (query != null) {
                query.close();
            }
            throw str;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @androidx.annotation.WorkerThread
    public final java.util.List<com.google.android.gms.measurement.internal.zzft> zzb(java.lang.String r22, java.lang.String r23, java.lang.String r24) {
        /*
        r21 = this;
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r22);
        r21.zzaf();
        r21.zzcl();
        r0 = new java.util.ArrayList;
        r0.<init>();
        r1 = 0;
        r2 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
        r3 = 3;
        r2.<init>(r3);	 Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
        r11 = r22;
        r2.add(r11);	 Catch:{ SQLiteException -> 0x00fd, all -> 0x0101 }
        r4 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x00fd, all -> 0x0101 }
        r5 = "app_id=?";
        r4.<init>(r5);	 Catch:{ SQLiteException -> 0x00fd, all -> 0x0101 }
        r5 = android.text.TextUtils.isEmpty(r23);	 Catch:{ SQLiteException -> 0x00fd, all -> 0x0101 }
        if (r5 != 0) goto L_0x0037;
    L_0x0027:
        r5 = r23;
        r2.add(r5);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        r6 = " and origin=?";
        r4.append(r6);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        goto L_0x0039;
    L_0x0032:
        r0 = move-exception;
        r12 = r21;
        goto L_0x010c;
    L_0x0037:
        r5 = r23;
    L_0x0039:
        r6 = android.text.TextUtils.isEmpty(r24);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        if (r6 != 0) goto L_0x0051;
    L_0x003f:
        r6 = java.lang.String.valueOf(r24);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        r7 = "*";
        r6 = r6.concat(r7);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        r2.add(r6);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        r6 = " and name glob ?";
        r4.append(r6);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
    L_0x0051:
        r6 = r2.size();	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        r6 = new java.lang.String[r6];	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        r2 = r2.toArray(r6);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        r16 = r2;
        r16 = (java.lang.String[]) r16;	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        r12 = r21.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        r13 = "user_attributes";
        r2 = "name";
        r6 = "set_timestamp";
        r7 = "value";
        r8 = "origin";
        r14 = new java.lang.String[]{r2, r6, r7, r8};	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        r15 = r4.toString();	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        r17 = 0;
        r18 = 0;
        r19 = "rowid";
        r20 = "1001";
        r2 = r12.query(r13, r14, r15, r16, r17, r18, r19, r20);	 Catch:{ SQLiteException -> 0x0032, all -> 0x0101 }
        r4 = r2.moveToFirst();	 Catch:{ SQLiteException -> 0x00f9, all -> 0x00f5 }
        if (r4 != 0) goto L_0x008d;
    L_0x0087:
        if (r2 == 0) goto L_0x008c;
    L_0x0089:
        r2.close();
    L_0x008c:
        return r0;
    L_0x008d:
        r4 = r0.size();	 Catch:{ SQLiteException -> 0x00f9, all -> 0x00f5 }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        if (r4 < r6) goto L_0x00a9;
    L_0x0095:
        r3 = r21.zzgt();	 Catch:{ SQLiteException -> 0x00f9, all -> 0x00f5 }
        r3 = r3.zzjg();	 Catch:{ SQLiteException -> 0x00f9, all -> 0x00f5 }
        r4 = "Read more than the max allowed user properties, ignoring excess";
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ SQLiteException -> 0x00f9, all -> 0x00f5 }
        r3.zzg(r4, r6);	 Catch:{ SQLiteException -> 0x00f9, all -> 0x00f5 }
        r12 = r21;
        goto L_0x00eb;
    L_0x00a9:
        r4 = 0;
        r7 = r2.getString(r4);	 Catch:{ SQLiteException -> 0x00f9, all -> 0x00f5 }
        r4 = 1;
        r8 = r2.getLong(r4);	 Catch:{ SQLiteException -> 0x00f9, all -> 0x00f5 }
        r4 = 2;
        r12 = r21;
        r10 = r12.zza(r2, r4);	 Catch:{ SQLiteException -> 0x00f3 }
        r13 = r2.getString(r3);	 Catch:{ SQLiteException -> 0x00f3 }
        if (r10 != 0) goto L_0x00d7;
    L_0x00c0:
        r4 = r21.zzgt();	 Catch:{ SQLiteException -> 0x00d4 }
        r4 = r4.zzjg();	 Catch:{ SQLiteException -> 0x00d4 }
        r5 = "(2)Read invalid user property value, ignoring it";
        r6 = com.google.android.gms.measurement.internal.zzaq.zzby(r22);	 Catch:{ SQLiteException -> 0x00d4 }
        r14 = r24;
        r4.zzd(r5, r6, r13, r14);	 Catch:{ SQLiteException -> 0x00d4 }
        goto L_0x00e5;
    L_0x00d4:
        r0 = move-exception;
        r5 = r13;
        goto L_0x010d;
    L_0x00d7:
        r14 = r24;
        r15 = new com.google.android.gms.measurement.internal.zzft;	 Catch:{ SQLiteException -> 0x00d4 }
        r4 = r15;
        r5 = r22;
        r6 = r13;
        r4.<init>(r5, r6, r7, r8, r10);	 Catch:{ SQLiteException -> 0x00d4 }
        r0.add(r15);	 Catch:{ SQLiteException -> 0x00d4 }
    L_0x00e5:
        r4 = r2.moveToNext();	 Catch:{ SQLiteException -> 0x00d4 }
        if (r4 != 0) goto L_0x00f1;
    L_0x00eb:
        if (r2 == 0) goto L_0x00f0;
    L_0x00ed:
        r2.close();
    L_0x00f0:
        return r0;
    L_0x00f1:
        r5 = r13;
        goto L_0x008d;
    L_0x00f3:
        r0 = move-exception;
        goto L_0x010d;
    L_0x00f5:
        r0 = move-exception;
        r12 = r21;
        goto L_0x0125;
    L_0x00f9:
        r0 = move-exception;
        r12 = r21;
        goto L_0x010d;
    L_0x00fd:
        r0 = move-exception;
        r12 = r21;
        goto L_0x010a;
    L_0x0101:
        r0 = move-exception;
        r12 = r21;
        goto L_0x0126;
    L_0x0105:
        r0 = move-exception;
        r12 = r21;
        r11 = r22;
    L_0x010a:
        r5 = r23;
    L_0x010c:
        r2 = r1;
    L_0x010d:
        r3 = r21.zzgt();	 Catch:{ all -> 0x0124 }
        r3 = r3.zzjg();	 Catch:{ all -> 0x0124 }
        r4 = "(2)Error querying user properties";
        r6 = com.google.android.gms.measurement.internal.zzaq.zzby(r22);	 Catch:{ all -> 0x0124 }
        r3.zzd(r4, r6, r5, r0);	 Catch:{ all -> 0x0124 }
        if (r2 == 0) goto L_0x0123;
    L_0x0120:
        r2.close();
    L_0x0123:
        return r1;
    L_0x0124:
        r0 = move-exception;
    L_0x0125:
        r1 = r2;
    L_0x0126:
        if (r1 == 0) goto L_0x012b;
    L_0x0128:
        r1.close();
    L_0x012b:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzr.zzb(java.lang.String, java.lang.String, java.lang.String):java.util.List<com.google.android.gms.measurement.internal.zzft>");
    }

    @WorkerThread
    public final boolean zza(zzm com_google_android_gms_measurement_internal_zzm) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzm);
        zzaf();
        zzcl();
        if (zzi(com_google_android_gms_measurement_internal_zzm.packageName, com_google_android_gms_measurement_internal_zzm.zzahe.name) == null) {
            if (zza("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{com_google_android_gms_measurement_internal_zzm.packageName}) >= 1000) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", com_google_android_gms_measurement_internal_zzm.packageName);
        contentValues.put("origin", com_google_android_gms_measurement_internal_zzm.origin);
        contentValues.put(JsonKey.NAME, com_google_android_gms_measurement_internal_zzm.zzahe.name);
        zza(contentValues, "value", com_google_android_gms_measurement_internal_zzm.zzahe.getValue());
        contentValues.put("active", Boolean.valueOf(com_google_android_gms_measurement_internal_zzm.active));
        contentValues.put("trigger_event_name", com_google_android_gms_measurement_internal_zzm.triggerEventName);
        contentValues.put("trigger_timeout", Long.valueOf(com_google_android_gms_measurement_internal_zzm.triggerTimeout));
        zzgr();
        contentValues.put("timed_out_event", zzfu.zza(com_google_android_gms_measurement_internal_zzm.zzahf));
        contentValues.put("creation_timestamp", Long.valueOf(com_google_android_gms_measurement_internal_zzm.creationTimestamp));
        zzgr();
        contentValues.put("triggered_event", zzfu.zza(com_google_android_gms_measurement_internal_zzm.zzahg));
        contentValues.put("triggered_timestamp", Long.valueOf(com_google_android_gms_measurement_internal_zzm.zzahe.zzaux));
        contentValues.put("time_to_live", Long.valueOf(com_google_android_gms_measurement_internal_zzm.timeToLive));
        zzgr();
        contentValues.put("expired_event", zzfu.zza(com_google_android_gms_measurement_internal_zzm.zzahh));
        try {
            if (getWritableDatabase().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1) {
                zzgt().zzjg().zzg("Failed to insert/update conditional user property (got -1)", zzaq.zzby(com_google_android_gms_measurement_internal_zzm.packageName));
            }
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Error storing conditional user property", zzaq.zzby(com_google_android_gms_measurement_internal_zzm.packageName), e);
        }
        return true;
    }

    @WorkerThread
    public final zzm zzj(String str, String str2) {
        Cursor query;
        Object e;
        Throwable th;
        zzr com_google_android_gms_measurement_internal_zzr;
        String str3 = str2;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzaf();
        zzcl();
        try {
            query = getWritableDatabase().query("conditional_properties", new String[]{"origin", "value", "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, "app_id=? and name=?", new String[]{str, str3}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    String string = query.getString(0);
                    try {
                        Object zza = zza(query, 1);
                        boolean z = query.getInt(2) != 0;
                        String string2 = query.getString(3);
                        long j = query.getLong(4);
                        zzae com_google_android_gms_measurement_internal_zzae = (zzae) zzjr().zza(query.getBlob(5), zzae.CREATOR);
                        String str4 = str;
                        zzm com_google_android_gms_measurement_internal_zzm = new zzm(str4, string, new zzfr(str2, query.getLong(8), zza, string), query.getLong(6), z, string2, com_google_android_gms_measurement_internal_zzae, j, (zzae) zzjr().zza(query.getBlob(7), zzae.CREATOR), query.getLong(9), (zzae) zzjr().zza(query.getBlob(10), zzae.CREATOR));
                        if (query.moveToNext()) {
                            zzgt().zzjg().zze("Got multiple records for conditional property, expected one", zzaq.zzby(str), zzgq().zzbx(str3));
                        }
                        if (query != null) {
                            query.close();
                        }
                        return com_google_android_gms_measurement_internal_zzm;
                    } catch (SQLiteException e2) {
                        e = e2;
                        try {
                            zzgt().zzjg().zzd("Error querying conditional property", zzaq.zzby(str), zzgq().zzbx(str3), e);
                            if (query != null) {
                                query.close();
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                            if (query != null) {
                                query.close();
                            }
                            throw th;
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e3) {
                e = e3;
                com_google_android_gms_measurement_internal_zzr = this;
                zzgt().zzjg().zzd("Error querying conditional property", zzaq.zzby(str), zzgq().zzbx(str3), e);
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                com_google_android_gms_measurement_internal_zzr = this;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (SQLiteException e4) {
            e = e4;
            com_google_android_gms_measurement_internal_zzr = this;
            query = null;
            zzgt().zzjg().zzd("Error querying conditional property", zzaq.zzby(str), zzgq().zzbx(str3), e);
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            com_google_android_gms_measurement_internal_zzr = this;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final int zzk(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzaf();
        zzcl();
        try {
            return getWritableDatabase().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            zzgt().zzjg().zzd("Error deleting conditional property", zzaq.zzby(str), zzgq().zzbx(str2), e);
            return 0;
        }
    }

    @WorkerThread
    public final List<zzm> zzc(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzaf();
        zzcl();
        List arrayList = new ArrayList(3);
        arrayList.add(str);
        str = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            str.append(" and origin=?");
        }
        if (TextUtils.isEmpty(str3) == null) {
            arrayList.add(String.valueOf(str3).concat("*"));
            str.append(" and name glob ?");
        }
        return zzb(str.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    public final List<zzm> zzb(String str, String[] strArr) {
        Object e;
        Throwable th;
        zzaf();
        zzcl();
        List<zzm> arrayList = new ArrayList();
        Cursor cursor = null;
        Cursor query;
        try {
            query = getWritableDatabase().query("conditional_properties", new String[]{"app_id", "origin", JsonKey.NAME, "value", "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, str, strArr, null, null, "rowid", "1001");
            try {
                if (query.moveToFirst()) {
                    do {
                        if (arrayList.size() >= 1000) {
                            zzgt().zzjg().zzg("Read more than the max allowed conditional properties, ignoring extra", Integer.valueOf(1000));
                            break;
                        }
                        boolean z = false;
                        String string = query.getString(0);
                        String string2 = query.getString(1);
                        String string3 = query.getString(2);
                        Object zza = zza(query, 3);
                        if (query.getInt(4) != 0) {
                            z = true;
                        }
                        String string4 = query.getString(5);
                        long j = query.getLong(6);
                        zzae com_google_android_gms_measurement_internal_zzae = (zzae) zzjr().zza(query.getBlob(7), zzae.CREATOR);
                        long j2 = query.getLong(8);
                        zzae com_google_android_gms_measurement_internal_zzae2 = (zzae) zzjr().zza(query.getBlob(9), zzae.CREATOR);
                        long j3 = query.getLong(10);
                        long j4 = query.getLong(11);
                        zzae com_google_android_gms_measurement_internal_zzae3 = (zzae) zzjr().zza(query.getBlob(12), zzae.CREATOR);
                        zzfr com_google_android_gms_measurement_internal_zzfr = new zzfr(string3, j3, zza, string2);
                        boolean z2 = z;
                        zzm com_google_android_gms_measurement_internal_zzm = r3;
                        zzm com_google_android_gms_measurement_internal_zzm2 = new zzm(string, string2, com_google_android_gms_measurement_internal_zzfr, j2, z2, string4, com_google_android_gms_measurement_internal_zzae, j, com_google_android_gms_measurement_internal_zzae2, j4, com_google_android_gms_measurement_internal_zzae3);
                        arrayList.add(com_google_android_gms_measurement_internal_zzm);
                    } while (query.moveToNext());
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                }
                if (query != null) {
                    query.close();
                }
                return arrayList;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = query;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (SQLiteException e3) {
            e = e3;
            try {
                zzgt().zzjg().zzg("Error querying conditional user property value", e);
                arrayList = Collections.emptyList();
                if (cursor != null) {
                    cursor.close();
                }
                return arrayList;
            } catch (Throwable th3) {
                th = th3;
                query = cursor;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        }
    }

    @WorkerThread
    public final zzg zzbo(String str) {
        Object e;
        Throwable th;
        zzr com_google_android_gms_measurement_internal_zzr;
        String str2 = str;
        Preconditions.checkNotEmpty(str);
        zzaf();
        zzcl();
        Cursor query;
        try {
            boolean z = true;
            query = getWritableDatabase().query("apps", new String[]{"app_instance_id", "gmp_app_id", "resettable_device_id_hash", "last_bundle_index", "last_bundle_start_timestamp", "last_bundle_end_timestamp", "app_version", "app_store", "gmp_version", "dev_cert_hash", "measurement_enabled", "day", "daily_public_events_count", "daily_events_count", "daily_conversions_count", "config_fetched_time", "failed_config_fetch_time", "app_version_int", "firebase_instance_id", "daily_error_events_count", "daily_realtime_events_count", "health_monitor_sample", "android_id", "adid_reporting_enabled", "ssaid_reporting_enabled", "admob_app_id"}, "app_id=?", new String[]{str2}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    try {
                        boolean z2;
                        zzg com_google_android_gms_measurement_internal_zzg = new zzg(this.zzang.zzmh(), str2);
                        com_google_android_gms_measurement_internal_zzg.zzal(query.getString(0));
                        com_google_android_gms_measurement_internal_zzg.zzam(query.getString(1));
                        com_google_android_gms_measurement_internal_zzg.zzao(query.getString(2));
                        com_google_android_gms_measurement_internal_zzg.zzx(query.getLong(3));
                        com_google_android_gms_measurement_internal_zzg.zzs(query.getLong(4));
                        com_google_android_gms_measurement_internal_zzg.zzt(query.getLong(5));
                        com_google_android_gms_measurement_internal_zzg.setAppVersion(query.getString(6));
                        com_google_android_gms_measurement_internal_zzg.zzaq(query.getString(7));
                        com_google_android_gms_measurement_internal_zzg.zzv(query.getLong(8));
                        com_google_android_gms_measurement_internal_zzg.zzw(query.getLong(9));
                        if (!query.isNull(10)) {
                            if (query.getInt(10) == 0) {
                                z2 = false;
                                com_google_android_gms_measurement_internal_zzg.setMeasurementEnabled(z2);
                                com_google_android_gms_measurement_internal_zzg.zzaa(query.getLong(11));
                                com_google_android_gms_measurement_internal_zzg.zzab(query.getLong(12));
                                com_google_android_gms_measurement_internal_zzg.zzac(query.getLong(13));
                                com_google_android_gms_measurement_internal_zzg.zzad(query.getLong(14));
                                com_google_android_gms_measurement_internal_zzg.zzy(query.getLong(15));
                                com_google_android_gms_measurement_internal_zzg.zzz(query.getLong(16));
                                com_google_android_gms_measurement_internal_zzg.zzu(query.isNull(17) ? -2147483648L : (long) query.getInt(17));
                                com_google_android_gms_measurement_internal_zzg.zzap(query.getString(18));
                                com_google_android_gms_measurement_internal_zzg.zzaf(query.getLong(19));
                                com_google_android_gms_measurement_internal_zzg.zzae(query.getLong(20));
                                com_google_android_gms_measurement_internal_zzg.zzar(query.getString(21));
                                com_google_android_gms_measurement_internal_zzg.zzag(query.isNull(22) ? 0 : query.getLong(22));
                                if (!query.isNull(23)) {
                                    if (query.getInt(23) != 0) {
                                        z2 = false;
                                        com_google_android_gms_measurement_internal_zzg.zze(z2);
                                        if (!query.isNull(24)) {
                                            if (query.getInt(24) == 0) {
                                                z = false;
                                            }
                                        }
                                        com_google_android_gms_measurement_internal_zzg.zzf(z);
                                        com_google_android_gms_measurement_internal_zzg.zzan(query.getString(25));
                                        com_google_android_gms_measurement_internal_zzg.zzha();
                                        if (query.moveToNext()) {
                                            zzgt().zzjg().zzg("Got multiple records for app, expected one. appId", zzaq.zzby(str));
                                        }
                                        if (query != null) {
                                            query.close();
                                        }
                                        return com_google_android_gms_measurement_internal_zzg;
                                    }
                                }
                                z2 = true;
                                com_google_android_gms_measurement_internal_zzg.zze(z2);
                                if (query.isNull(24)) {
                                    if (query.getInt(24) == 0) {
                                        z = false;
                                    }
                                }
                                com_google_android_gms_measurement_internal_zzg.zzf(z);
                                com_google_android_gms_measurement_internal_zzg.zzan(query.getString(25));
                                com_google_android_gms_measurement_internal_zzg.zzha();
                                if (query.moveToNext()) {
                                    zzgt().zzjg().zzg("Got multiple records for app, expected one. appId", zzaq.zzby(str));
                                }
                                if (query != null) {
                                    query.close();
                                }
                                return com_google_android_gms_measurement_internal_zzg;
                            }
                        }
                        z2 = true;
                        com_google_android_gms_measurement_internal_zzg.setMeasurementEnabled(z2);
                        com_google_android_gms_measurement_internal_zzg.zzaa(query.getLong(11));
                        com_google_android_gms_measurement_internal_zzg.zzab(query.getLong(12));
                        com_google_android_gms_measurement_internal_zzg.zzac(query.getLong(13));
                        com_google_android_gms_measurement_internal_zzg.zzad(query.getLong(14));
                        com_google_android_gms_measurement_internal_zzg.zzy(query.getLong(15));
                        com_google_android_gms_measurement_internal_zzg.zzz(query.getLong(16));
                        if (query.isNull(17)) {
                        }
                        com_google_android_gms_measurement_internal_zzg.zzu(query.isNull(17) ? -2147483648L : (long) query.getInt(17));
                        com_google_android_gms_measurement_internal_zzg.zzap(query.getString(18));
                        com_google_android_gms_measurement_internal_zzg.zzaf(query.getLong(19));
                        com_google_android_gms_measurement_internal_zzg.zzae(query.getLong(20));
                        com_google_android_gms_measurement_internal_zzg.zzar(query.getString(21));
                        if (query.isNull(22)) {
                        }
                        com_google_android_gms_measurement_internal_zzg.zzag(query.isNull(22) ? 0 : query.getLong(22));
                        if (query.isNull(23)) {
                            if (query.getInt(23) != 0) {
                                z2 = false;
                                com_google_android_gms_measurement_internal_zzg.zze(z2);
                                if (query.isNull(24)) {
                                    if (query.getInt(24) == 0) {
                                        z = false;
                                    }
                                }
                                com_google_android_gms_measurement_internal_zzg.zzf(z);
                                com_google_android_gms_measurement_internal_zzg.zzan(query.getString(25));
                                com_google_android_gms_measurement_internal_zzg.zzha();
                                if (query.moveToNext()) {
                                    zzgt().zzjg().zzg("Got multiple records for app, expected one. appId", zzaq.zzby(str));
                                }
                                if (query != null) {
                                    query.close();
                                }
                                return com_google_android_gms_measurement_internal_zzg;
                            }
                        }
                        z2 = true;
                        com_google_android_gms_measurement_internal_zzg.zze(z2);
                        if (query.isNull(24)) {
                            if (query.getInt(24) == 0) {
                                z = false;
                            }
                        }
                        com_google_android_gms_measurement_internal_zzg.zzf(z);
                        com_google_android_gms_measurement_internal_zzg.zzan(query.getString(25));
                        com_google_android_gms_measurement_internal_zzg.zzha();
                        if (query.moveToNext()) {
                            zzgt().zzjg().zzg("Got multiple records for app, expected one. appId", zzaq.zzby(str));
                        }
                        if (query != null) {
                            query.close();
                        }
                        return com_google_android_gms_measurement_internal_zzg;
                    } catch (SQLiteException e2) {
                        e = e2;
                        try {
                            zzgt().zzjg().zze("Error querying app. appId", zzaq.zzby(str), e);
                            if (query != null) {
                                query.close();
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                            if (query != null) {
                                query.close();
                            }
                            throw th;
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e3) {
                e = e3;
                com_google_android_gms_measurement_internal_zzr = this;
                zzgt().zzjg().zze("Error querying app. appId", zzaq.zzby(str), e);
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                com_google_android_gms_measurement_internal_zzr = this;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (SQLiteException e4) {
            e = e4;
            com_google_android_gms_measurement_internal_zzr = this;
            query = null;
            zzgt().zzjg().zze("Error querying app. appId", zzaq.zzby(str), e);
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            com_google_android_gms_measurement_internal_zzr = this;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final void zza(zzg com_google_android_gms_measurement_internal_zzg) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzg);
        zzaf();
        zzcl();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", com_google_android_gms_measurement_internal_zzg.zzal());
        contentValues.put("app_instance_id", com_google_android_gms_measurement_internal_zzg.getAppInstanceId());
        contentValues.put("gmp_app_id", com_google_android_gms_measurement_internal_zzg.getGmpAppId());
        contentValues.put("resettable_device_id_hash", com_google_android_gms_measurement_internal_zzg.zzhc());
        contentValues.put("last_bundle_index", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhj()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhd()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhe()));
        contentValues.put("app_version", com_google_android_gms_measurement_internal_zzg.zzak());
        contentValues.put("app_store", com_google_android_gms_measurement_internal_zzg.zzhg());
        contentValues.put("gmp_version", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhh()));
        contentValues.put("dev_cert_hash", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhi()));
        contentValues.put("measurement_enabled", Boolean.valueOf(com_google_android_gms_measurement_internal_zzg.isMeasurementEnabled()));
        contentValues.put("day", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhn()));
        contentValues.put("daily_public_events_count", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzho()));
        contentValues.put("daily_events_count", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhp()));
        contentValues.put("daily_conversions_count", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhq()));
        contentValues.put("config_fetched_time", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhk()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhl()));
        contentValues.put("app_version_int", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhf()));
        contentValues.put("firebase_instance_id", com_google_android_gms_measurement_internal_zzg.getFirebaseInstanceId());
        contentValues.put("daily_error_events_count", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhs()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhr()));
        contentValues.put("health_monitor_sample", com_google_android_gms_measurement_internal_zzg.zzht());
        contentValues.put("android_id", Long.valueOf(com_google_android_gms_measurement_internal_zzg.zzhv()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(com_google_android_gms_measurement_internal_zzg.zzhw()));
        contentValues.put("ssaid_reporting_enabled", Boolean.valueOf(com_google_android_gms_measurement_internal_zzg.zzhx()));
        contentValues.put("admob_app_id", com_google_android_gms_measurement_internal_zzg.zzhb());
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (((long) writableDatabase.update("apps", contentValues, "app_id = ?", new String[]{com_google_android_gms_measurement_internal_zzg.zzal()})) == 0 && writableDatabase.insertWithOnConflict("apps", null, contentValues, 5) == -1) {
                zzgt().zzjg().zzg("Failed to insert/update app (got -1). appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzg.zzal()));
            }
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Error storing app. appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzg.zzal()), e);
        }
    }

    public final long zzbp(String str) {
        Preconditions.checkNotEmpty(str);
        zzaf();
        zzcl();
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String valueOf = String.valueOf(Math.max(0, Math.min(1000000, zzgv().zzb(str, zzag.zzajv))));
            return (long) writableDatabase.delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str, valueOf});
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Error deleting over the limit events. appId", zzaq.zzby(str), e);
            return 0;
        }
    }

    @WorkerThread
    public final zzs zza(long j, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        Object e;
        Throwable th;
        Preconditions.checkNotEmpty(str);
        zzaf();
        zzcl();
        String[] strArr = new String[]{str};
        zzs com_google_android_gms_measurement_internal_zzs = new zzs();
        Cursor cursor = null;
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            SQLiteDatabase sQLiteDatabase = writableDatabase;
            Cursor query = sQLiteDatabase.query("apps", new String[]{"day", "daily_events_count", "daily_public_events_count", "daily_conversions_count", "daily_error_events_count", "daily_realtime_events_count"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    if (query.getLong(0) == j) {
                        com_google_android_gms_measurement_internal_zzs.zzahu = query.getLong(1);
                        com_google_android_gms_measurement_internal_zzs.zzaht = query.getLong(2);
                        com_google_android_gms_measurement_internal_zzs.zzahv = query.getLong(3);
                        com_google_android_gms_measurement_internal_zzs.zzahw = query.getLong(4);
                        com_google_android_gms_measurement_internal_zzs.zzahx = query.getLong(5);
                    }
                    if (z) {
                        com_google_android_gms_measurement_internal_zzs.zzahu++;
                    }
                    if (z2) {
                        com_google_android_gms_measurement_internal_zzs.zzaht++;
                    }
                    if (z3) {
                        com_google_android_gms_measurement_internal_zzs.zzahv++;
                    }
                    if (z4) {
                        com_google_android_gms_measurement_internal_zzs.zzahw++;
                    }
                    if (z5) {
                        com_google_android_gms_measurement_internal_zzs.zzahx++;
                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("day", Long.valueOf(j));
                    contentValues.put("daily_public_events_count", Long.valueOf(com_google_android_gms_measurement_internal_zzs.zzaht));
                    contentValues.put("daily_events_count", Long.valueOf(com_google_android_gms_measurement_internal_zzs.zzahu));
                    contentValues.put("daily_conversions_count", Long.valueOf(com_google_android_gms_measurement_internal_zzs.zzahv));
                    contentValues.put("daily_error_events_count", Long.valueOf(com_google_android_gms_measurement_internal_zzs.zzahw));
                    contentValues.put("daily_realtime_events_count", Long.valueOf(com_google_android_gms_measurement_internal_zzs.zzahx));
                    writableDatabase.update("apps", contentValues, "app_id=?", strArr);
                    if (query != null) {
                        query.close();
                    }
                    return com_google_android_gms_measurement_internal_zzs;
                }
                zzgt().zzjj().zzg("Not updating daily counts, app is not known. appId", zzaq.zzby(str));
                if (query != null) {
                    query.close();
                }
                return com_google_android_gms_measurement_internal_zzs;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = query;
                try {
                    zzgt().zzjg().zze("Error updating daily counts. appId", zzaq.zzby(str), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return com_google_android_gms_measurement_internal_zzs;
                } catch (Throwable th2) {
                    th = th2;
                    query = cursor;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            zzgt().zzjg().zze("Error updating daily counts. appId", zzaq.zzby(str), e);
            if (cursor != null) {
                cursor.close();
            }
            return com_google_android_gms_measurement_internal_zzs;
        }
    }

    @WorkerThread
    public final byte[] zzbq(String str) {
        Cursor query;
        Object e;
        Preconditions.checkNotEmpty(str);
        zzaf();
        zzcl();
        try {
            query = getWritableDatabase().query("apps", new String[]{"remote_config"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    byte[] blob = query.getBlob(0);
                    if (query.moveToNext()) {
                        zzgt().zzjg().zzg("Got multiple records for app config, expected one. appId", zzaq.zzby(str));
                    }
                    if (query != null) {
                        query.close();
                    }
                    return blob;
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzgt().zzjg().zze("Error querying remote config. appId", zzaq.zzby(str), e);
                    if (query != null) {
                        query.close();
                    }
                    return null;
                } catch (Throwable th) {
                    str = th;
                    if (query != null) {
                        query.close();
                    }
                    throw str;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            zzgt().zzjg().zze("Error querying remote config. appId", zzaq.zzby(str), e);
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th2) {
            str = th2;
            query = null;
            if (query != null) {
                query.close();
            }
            throw str;
        }
    }

    @WorkerThread
    public final boolean zza(zzgl com_google_android_gms_internal_measurement_zzgl, boolean z) {
        zzaf();
        zzcl();
        Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzgl);
        Preconditions.checkNotEmpty(com_google_android_gms_internal_measurement_zzgl.zztt);
        Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzgl.zzaxy);
        zzij();
        long currentTimeMillis = zzbx().currentTimeMillis();
        if (com_google_android_gms_internal_measurement_zzgl.zzaxy.longValue() < currentTimeMillis - zzo.zzib() || com_google_android_gms_internal_measurement_zzgl.zzaxy.longValue() > zzo.zzib() + currentTimeMillis) {
            zzgt().zzjj().zzd("Storing bundle outside of the max uploading time span. appId, now, timestamp", zzaq.zzby(com_google_android_gms_internal_measurement_zzgl.zztt), Long.valueOf(currentTimeMillis), com_google_android_gms_internal_measurement_zzgl.zzaxy);
        }
        try {
            byte[] bArr = new byte[com_google_android_gms_internal_measurement_zzgl.zzwe()];
            zzzj zzk = zzzj.zzk(bArr, 0, bArr.length);
            com_google_android_gms_internal_measurement_zzgl.zza(zzk);
            zzk.zzzh();
            bArr = zzjr().zzb(bArr);
            zzgt().zzjo().zzg("Saving bundle, size", Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", com_google_android_gms_internal_measurement_zzgl.zztt);
            contentValues.put("bundle_end_timestamp", com_google_android_gms_internal_measurement_zzgl.zzaxy);
            contentValues.put(DataSchemeDataSource.SCHEME_DATA, bArr);
            contentValues.put("has_realtime", Integer.valueOf(z));
            if (com_google_android_gms_internal_measurement_zzgl.zzayv) {
                contentValues.put("retry_count", com_google_android_gms_internal_measurement_zzgl.zzayv);
            }
            try {
                if (getWritableDatabase().insert("queue", null, contentValues) != -1) {
                    return true;
                }
                zzgt().zzjg().zzg("Failed to insert bundle (got -1). appId", zzaq.zzby(com_google_android_gms_internal_measurement_zzgl.zztt));
                return false;
            } catch (boolean z2) {
                zzgt().zzjg().zze("Error storing bundle. appId", zzaq.zzby(com_google_android_gms_internal_measurement_zzgl.zztt), z2);
                return false;
            }
        } catch (boolean z22) {
            zzgt().zzjg().zze("Data loss. Failed to serialize bundle. appId", zzaq.zzby(com_google_android_gms_internal_measurement_zzgl.zztt), z22);
            return false;
        }
    }

    @WorkerThread
    public final String zzih() {
        Cursor rawQuery;
        Object e;
        Throwable th;
        try {
            rawQuery = getWritableDatabase().rawQuery("select app_id from queue order by has_realtime desc, rowid asc limit 1;", null);
            try {
                if (rawQuery.moveToFirst()) {
                    String string = rawQuery.getString(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return string;
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzgt().zzjg().zzg("Database error getting next bundle app id", e);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            rawQuery = null;
            zzgt().zzjg().zzg("Database error getting next bundle app id", e);
            if (rawQuery != null) {
                rawQuery.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            rawQuery = null;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
    }

    public final boolean zzii() {
        return zza("select count(1) > 0 from queue where has_realtime = 1", null) != 0;
    }

    @WorkerThread
    public final List<Pair<zzgl, Long>> zzb(String str, int i, int i2) {
        zzaf();
        zzcl();
        Preconditions.checkArgument(i > 0);
        Preconditions.checkArgument(i2 > 0);
        Preconditions.checkNotEmpty(str);
        Cursor cursor = null;
        try {
            i = getWritableDatabase().query("queue", new String[]{"rowid", DataSchemeDataSource.SCHEME_DATA, "retry_count"}, "app_id=?", new String[]{str}, null, null, "rowid", String.valueOf(i));
            try {
                if (i.moveToFirst()) {
                    List<Pair<zzgl, Long>> arrayList = new ArrayList();
                    int i3 = 0;
                    do {
                        long j = i.getLong(0);
                        try {
                            byte[] zza = zzjr().zza(i.getBlob(1));
                            if (!arrayList.isEmpty() && zza.length + i3 > i2) {
                                break;
                            }
                            zzzi zzj = zzzi.zzj(zza, 0, zza.length);
                            zzzr com_google_android_gms_internal_measurement_zzgl = new zzgl();
                            try {
                                com_google_android_gms_internal_measurement_zzgl.zza(zzj);
                                if (!i.isNull(2)) {
                                    com_google_android_gms_internal_measurement_zzgl.zzayv = Integer.valueOf(i.getInt(2));
                                }
                                i3 += zza.length;
                                arrayList.add(Pair.create(com_google_android_gms_internal_measurement_zzgl, Long.valueOf(j)));
                            } catch (IOException e) {
                                zzgt().zzjg().zze("Failed to merge queued bundle. appId", zzaq.zzby(str), e);
                            }
                            if (!i.moveToNext()) {
                                break;
                            }
                        } catch (IOException e2) {
                            zzgt().zzjg().zze("Failed to unzip queued bundle. appId", zzaq.zzby(str), e2);
                        }
                    } while (i3 <= i2);
                    if (i != 0) {
                        i.close();
                    }
                    return arrayList;
                }
                i2 = Collections.emptyList();
                if (i != 0) {
                    i.close();
                }
                return i2;
            } catch (SQLiteException e3) {
                i2 = e3;
                cursor = i;
            } catch (Throwable th) {
                str = th;
            }
        } catch (SQLiteException e4) {
            i2 = e4;
            try {
                zzgt().zzjg().zze("Error querying bundles. appId", zzaq.zzby(str), i2);
                str = Collections.emptyList();
                if (cursor != null) {
                    cursor.close();
                }
                return str;
            } catch (Throwable th2) {
                str = th2;
                i = cursor;
                if (i != 0) {
                    i.close();
                }
                throw str;
            }
        }
    }

    @WorkerThread
    final void zzij() {
        zzaf();
        zzcl();
        if (zzip()) {
            long j = zzgu().zzano.get();
            long elapsedRealtime = zzbx().elapsedRealtime();
            if (Math.abs(elapsedRealtime - j) > ((Long) zzag.zzake.get()).longValue()) {
                zzgu().zzano.set(elapsedRealtime);
                zzaf();
                zzcl();
                if (zzip()) {
                    int delete = getWritableDatabase().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(zzbx().currentTimeMillis()), String.valueOf(zzo.zzib())});
                    if (delete > 0) {
                        zzgt().zzjo().zzg("Deleted stale rows. rowsDeleted", Integer.valueOf(delete));
                    }
                }
            }
        }
    }

    @WorkerThread
    @VisibleForTesting
    final void zzc(List<Long> list) {
        zzaf();
        zzcl();
        Preconditions.checkNotNull(list);
        Preconditions.checkNotZero(list.size());
        if (zzip()) {
            list = TextUtils.join(",", list);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(list).length() + 2);
            stringBuilder.append("(");
            stringBuilder.append(list);
            stringBuilder.append(")");
            list = stringBuilder.toString();
            stringBuilder = new StringBuilder(String.valueOf(list).length() + 80);
            stringBuilder.append("SELECT COUNT(1) FROM queue WHERE rowid IN ");
            stringBuilder.append(list);
            stringBuilder.append(" AND retry_count =  2147483647 LIMIT 1");
            if (zza(stringBuilder.toString(), null) > 0) {
                zzgt().zzjj().zzca("The number of upload retries exceeds the limit. Will remain unchanged.");
            }
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(list).length() + 127);
                stringBuilder2.append("UPDATE queue SET retry_count = IFNULL(retry_count, 0) + 1 WHERE rowid IN ");
                stringBuilder2.append(list);
                stringBuilder2.append(" AND (retry_count IS NULL OR retry_count < 2147483647)");
                writableDatabase.execSQL(stringBuilder2.toString());
            } catch (List<Long> list2) {
                zzgt().zzjg().zzg("Error incrementing retry count. error", list2);
            }
        }
    }

    @WorkerThread
    final void zza(String str, zzfx[] com_google_android_gms_internal_measurement_zzfxArr) {
        zzcl();
        zzaf();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzfxArr);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            zzcl();
            zzaf();
            Preconditions.checkNotEmpty(str);
            SQLiteDatabase writableDatabase2 = getWritableDatabase();
            String[] strArr = new String[1];
            int i = 0;
            strArr[0] = str;
            writableDatabase2.delete("property_filters", "app_id=?", strArr);
            writableDatabase2.delete("event_filters", "app_id=?", new String[]{str});
            for (zzfx com_google_android_gms_internal_measurement_zzfx : com_google_android_gms_internal_measurement_zzfxArr) {
                zzcl();
                zzaf();
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzfx);
                Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzfx.zzavt);
                Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzfx.zzavs);
                if (com_google_android_gms_internal_measurement_zzfx.zzavr == null) {
                    zzgt().zzjj().zzg("Audience with no ID. appId", zzaq.zzby(str));
                } else {
                    Object obj;
                    int intValue = com_google_android_gms_internal_measurement_zzfx.zzavr.intValue();
                    for (zzfy com_google_android_gms_internal_measurement_zzfy : com_google_android_gms_internal_measurement_zzfx.zzavt) {
                        if (com_google_android_gms_internal_measurement_zzfy.zzavx == null) {
                            zzgt().zzjj().zze("Event filter with no ID. Audience definition ignored. appId, audienceId", zzaq.zzby(str), com_google_android_gms_internal_measurement_zzfx.zzavr);
                            break;
                        }
                    }
                    for (zzgb com_google_android_gms_internal_measurement_zzgb : com_google_android_gms_internal_measurement_zzfx.zzavs) {
                        if (com_google_android_gms_internal_measurement_zzgb.zzavx == null) {
                            zzgt().zzjj().zze("Property filter with no ID. Audience definition ignored. appId, audienceId", zzaq.zzby(str), com_google_android_gms_internal_measurement_zzfx.zzavr);
                            break;
                        }
                    }
                    for (zzfy com_google_android_gms_internal_measurement_zzfy2 : com_google_android_gms_internal_measurement_zzfx.zzavt) {
                        if (!zza(str, intValue, com_google_android_gms_internal_measurement_zzfy2)) {
                            obj = null;
                            break;
                        }
                    }
                    obj = 1;
                    if (obj != null) {
                        for (zzgb com_google_android_gms_internal_measurement_zzgb2 : com_google_android_gms_internal_measurement_zzfx.zzavs) {
                            if (!zza(str, intValue, com_google_android_gms_internal_measurement_zzgb2)) {
                                obj = null;
                                break;
                            }
                        }
                    }
                    if (obj == null) {
                        zzcl();
                        zzaf();
                        Preconditions.checkNotEmpty(str);
                        SQLiteDatabase writableDatabase3 = getWritableDatabase();
                        writableDatabase3.delete("property_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(intValue)});
                        writableDatabase3.delete("event_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(intValue)});
                    }
                }
            }
            List arrayList = new ArrayList();
            int length = com_google_android_gms_internal_measurement_zzfxArr.length;
            while (i < length) {
                arrayList.add(com_google_android_gms_internal_measurement_zzfxArr[i].zzavr);
                i++;
            }
            zza(str, arrayList);
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i, zzfy com_google_android_gms_internal_measurement_zzfy) {
        zzcl();
        zzaf();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzfy);
        if (TextUtils.isEmpty(com_google_android_gms_internal_measurement_zzfy.zzavy)) {
            zzgt().zzjj().zzd("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", zzaq.zzby(str), Integer.valueOf(i), String.valueOf(com_google_android_gms_internal_measurement_zzfy.zzavx));
            return false;
        }
        try {
            byte[] bArr = new byte[com_google_android_gms_internal_measurement_zzfy.zzwe()];
            zzzj zzk = zzzj.zzk(bArr, 0, bArr.length);
            com_google_android_gms_internal_measurement_zzfy.zza(zzk);
            zzk.zzzh();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", com_google_android_gms_internal_measurement_zzfy.zzavx);
            contentValues.put("event_name", com_google_android_gms_internal_measurement_zzfy.zzavy);
            contentValues.put(DataSchemeDataSource.SCHEME_DATA, bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("event_filters", null, contentValues, 5) == -1) {
                    zzgt().zzjg().zzg("Failed to insert event filter (got -1). appId", zzaq.zzby(str));
                }
                return true;
            } catch (int i2) {
                zzgt().zzjg().zze("Error storing event filter. appId", zzaq.zzby(str), i2);
                return false;
            }
        } catch (int i22) {
            zzgt().zzjg().zze("Configuration loss. Failed to serialize event filter. appId", zzaq.zzby(str), i22);
            return false;
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i, zzgb com_google_android_gms_internal_measurement_zzgb) {
        zzcl();
        zzaf();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzgb);
        if (TextUtils.isEmpty(com_google_android_gms_internal_measurement_zzgb.zzawn)) {
            zzgt().zzjj().zzd("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", zzaq.zzby(str), Integer.valueOf(i), String.valueOf(com_google_android_gms_internal_measurement_zzgb.zzavx));
            return false;
        }
        try {
            byte[] bArr = new byte[com_google_android_gms_internal_measurement_zzgb.zzwe()];
            zzzj zzk = zzzj.zzk(bArr, 0, bArr.length);
            com_google_android_gms_internal_measurement_zzgb.zza(zzk);
            zzk.zzzh();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", com_google_android_gms_internal_measurement_zzgb.zzavx);
            contentValues.put("property_name", com_google_android_gms_internal_measurement_zzgb.zzawn);
            contentValues.put(DataSchemeDataSource.SCHEME_DATA, bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("property_filters", null, contentValues, 5) != -1) {
                    return true;
                }
                zzgt().zzjg().zzg("Failed to insert property filter (got -1). appId", zzaq.zzby(str));
                return false;
            } catch (int i2) {
                zzgt().zzjg().zze("Error storing property filter. appId", zzaq.zzby(str), i2);
                return false;
            }
        } catch (int i22) {
            zzgt().zzjg().zze("Configuration loss. Failed to serialize property filter. appId", zzaq.zzby(str), i22);
            return false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.measurement.zzfy>> zzl(java.lang.String r13, java.lang.String r14) {
        /*
        r12 = this;
        r12.zzcl();
        r12.zzaf();
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r13);
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r14);
        r0 = new androidx.collection.ArrayMap;
        r0.<init>();
        r1 = r12.getWritableDatabase();
        r9 = 0;
        r2 = "event_filters";
        r3 = "audience_id";
        r4 = "data";
        r3 = new java.lang.String[]{r3, r4};	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r4 = "app_id=? AND event_name=?";
        r5 = 2;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r10 = 0;
        r5[r10] = r13;	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r11 = 1;
        r5[r11] = r14;	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r14 = r1.query(r2, r3, r4, r5, r6, r7, r8);	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r1 = r14.moveToFirst();	 Catch:{ SQLiteException -> 0x0091 }
        if (r1 != 0) goto L_0x0042;
    L_0x0038:
        r0 = java.util.Collections.emptyMap();	 Catch:{ SQLiteException -> 0x0091 }
        if (r14 == 0) goto L_0x0041;
    L_0x003e:
        r14.close();
    L_0x0041:
        return r0;
    L_0x0042:
        r1 = r14.getBlob(r11);	 Catch:{ SQLiteException -> 0x0091 }
        r2 = r1.length;	 Catch:{ SQLiteException -> 0x0091 }
        r1 = com.google.android.gms.internal.measurement.zzzi.zzj(r1, r10, r2);	 Catch:{ SQLiteException -> 0x0091 }
        r2 = new com.google.android.gms.internal.measurement.zzfy;	 Catch:{ SQLiteException -> 0x0091 }
        r2.<init>();	 Catch:{ SQLiteException -> 0x0091 }
        r2.zza(r1);	 Catch:{ IOException -> 0x0073 }
        r1 = r14.getInt(r10);	 Catch:{ SQLiteException -> 0x0091 }
        r3 = java.lang.Integer.valueOf(r1);	 Catch:{ SQLiteException -> 0x0091 }
        r3 = r0.get(r3);	 Catch:{ SQLiteException -> 0x0091 }
        r3 = (java.util.List) r3;	 Catch:{ SQLiteException -> 0x0091 }
        if (r3 != 0) goto L_0x006f;
    L_0x0063:
        r3 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x0091 }
        r3.<init>();	 Catch:{ SQLiteException -> 0x0091 }
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ SQLiteException -> 0x0091 }
        r0.put(r1, r3);	 Catch:{ SQLiteException -> 0x0091 }
    L_0x006f:
        r3.add(r2);	 Catch:{ SQLiteException -> 0x0091 }
        goto L_0x0085;
    L_0x0073:
        r1 = move-exception;
        r2 = r12.zzgt();	 Catch:{ SQLiteException -> 0x0091 }
        r2 = r2.zzjg();	 Catch:{ SQLiteException -> 0x0091 }
        r3 = "Failed to merge filter. appId";
        r4 = com.google.android.gms.measurement.internal.zzaq.zzby(r13);	 Catch:{ SQLiteException -> 0x0091 }
        r2.zze(r3, r4, r1);	 Catch:{ SQLiteException -> 0x0091 }
    L_0x0085:
        r1 = r14.moveToNext();	 Catch:{ SQLiteException -> 0x0091 }
        if (r1 != 0) goto L_0x0042;
    L_0x008b:
        if (r14 == 0) goto L_0x0090;
    L_0x008d:
        r14.close();
    L_0x0090:
        return r0;
    L_0x0091:
        r0 = move-exception;
        goto L_0x0098;
    L_0x0093:
        r13 = move-exception;
        r14 = r9;
        goto L_0x00b0;
    L_0x0096:
        r0 = move-exception;
        r14 = r9;
    L_0x0098:
        r1 = r12.zzgt();	 Catch:{ all -> 0x00af }
        r1 = r1.zzjg();	 Catch:{ all -> 0x00af }
        r2 = "Database error querying filters. appId";
        r13 = com.google.android.gms.measurement.internal.zzaq.zzby(r13);	 Catch:{ all -> 0x00af }
        r1.zze(r2, r13, r0);	 Catch:{ all -> 0x00af }
        if (r14 == 0) goto L_0x00ae;
    L_0x00ab:
        r14.close();
    L_0x00ae:
        return r9;
    L_0x00af:
        r13 = move-exception;
    L_0x00b0:
        if (r14 == 0) goto L_0x00b5;
    L_0x00b2:
        r14.close();
    L_0x00b5:
        throw r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzr.zzl(java.lang.String, java.lang.String):java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.measurement.zzfy>>");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.measurement.zzgb>> zzm(java.lang.String r13, java.lang.String r14) {
        /*
        r12 = this;
        r12.zzcl();
        r12.zzaf();
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r13);
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r14);
        r0 = new androidx.collection.ArrayMap;
        r0.<init>();
        r1 = r12.getWritableDatabase();
        r9 = 0;
        r2 = "property_filters";
        r3 = "audience_id";
        r4 = "data";
        r3 = new java.lang.String[]{r3, r4};	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r4 = "app_id=? AND property_name=?";
        r5 = 2;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r10 = 0;
        r5[r10] = r13;	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r11 = 1;
        r5[r11] = r14;	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r14 = r1.query(r2, r3, r4, r5, r6, r7, r8);	 Catch:{ SQLiteException -> 0x0096, all -> 0x0093 }
        r1 = r14.moveToFirst();	 Catch:{ SQLiteException -> 0x0091 }
        if (r1 != 0) goto L_0x0042;
    L_0x0038:
        r0 = java.util.Collections.emptyMap();	 Catch:{ SQLiteException -> 0x0091 }
        if (r14 == 0) goto L_0x0041;
    L_0x003e:
        r14.close();
    L_0x0041:
        return r0;
    L_0x0042:
        r1 = r14.getBlob(r11);	 Catch:{ SQLiteException -> 0x0091 }
        r2 = r1.length;	 Catch:{ SQLiteException -> 0x0091 }
        r1 = com.google.android.gms.internal.measurement.zzzi.zzj(r1, r10, r2);	 Catch:{ SQLiteException -> 0x0091 }
        r2 = new com.google.android.gms.internal.measurement.zzgb;	 Catch:{ SQLiteException -> 0x0091 }
        r2.<init>();	 Catch:{ SQLiteException -> 0x0091 }
        r2.zza(r1);	 Catch:{ IOException -> 0x0073 }
        r1 = r14.getInt(r10);	 Catch:{ SQLiteException -> 0x0091 }
        r3 = java.lang.Integer.valueOf(r1);	 Catch:{ SQLiteException -> 0x0091 }
        r3 = r0.get(r3);	 Catch:{ SQLiteException -> 0x0091 }
        r3 = (java.util.List) r3;	 Catch:{ SQLiteException -> 0x0091 }
        if (r3 != 0) goto L_0x006f;
    L_0x0063:
        r3 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x0091 }
        r3.<init>();	 Catch:{ SQLiteException -> 0x0091 }
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ SQLiteException -> 0x0091 }
        r0.put(r1, r3);	 Catch:{ SQLiteException -> 0x0091 }
    L_0x006f:
        r3.add(r2);	 Catch:{ SQLiteException -> 0x0091 }
        goto L_0x0085;
    L_0x0073:
        r1 = move-exception;
        r2 = r12.zzgt();	 Catch:{ SQLiteException -> 0x0091 }
        r2 = r2.zzjg();	 Catch:{ SQLiteException -> 0x0091 }
        r3 = "Failed to merge filter";
        r4 = com.google.android.gms.measurement.internal.zzaq.zzby(r13);	 Catch:{ SQLiteException -> 0x0091 }
        r2.zze(r3, r4, r1);	 Catch:{ SQLiteException -> 0x0091 }
    L_0x0085:
        r1 = r14.moveToNext();	 Catch:{ SQLiteException -> 0x0091 }
        if (r1 != 0) goto L_0x0042;
    L_0x008b:
        if (r14 == 0) goto L_0x0090;
    L_0x008d:
        r14.close();
    L_0x0090:
        return r0;
    L_0x0091:
        r0 = move-exception;
        goto L_0x0098;
    L_0x0093:
        r13 = move-exception;
        r14 = r9;
        goto L_0x00b0;
    L_0x0096:
        r0 = move-exception;
        r14 = r9;
    L_0x0098:
        r1 = r12.zzgt();	 Catch:{ all -> 0x00af }
        r1 = r1.zzjg();	 Catch:{ all -> 0x00af }
        r2 = "Database error querying filters. appId";
        r13 = com.google.android.gms.measurement.internal.zzaq.zzby(r13);	 Catch:{ all -> 0x00af }
        r1.zze(r2, r13, r0);	 Catch:{ all -> 0x00af }
        if (r14 == 0) goto L_0x00ae;
    L_0x00ab:
        r14.close();
    L_0x00ae:
        return r9;
    L_0x00af:
        r13 = move-exception;
    L_0x00b0:
        if (r14 == 0) goto L_0x00b5;
    L_0x00b2:
        r14.close();
    L_0x00b5:
        throw r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzr.zzm(java.lang.String, java.lang.String):java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.measurement.zzgb>>");
    }

    private final boolean zza(String str, List<Integer> list) {
        Preconditions.checkNotEmpty(str);
        zzcl();
        zzaf();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            if (zza("select count(1) from audience_filter_values where app_id=?", new String[]{str}) <= ((long) Math.max(0, Math.min(2000, zzgv().zzb(str, zzag.zzakl))))) {
                return false;
            }
            Iterable arrayList = new ArrayList();
            int i = 0;
            while (i < list.size()) {
                Integer num = (Integer) list.get(i);
                if (num != null) {
                    if (num instanceof Integer) {
                        arrayList.add(Integer.toString(num.intValue()));
                        i++;
                    }
                }
                return false;
            }
            list = TextUtils.join(",", arrayList);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(list).length() + 2);
            stringBuilder.append("(");
            stringBuilder.append(list);
            stringBuilder.append(")");
            list = stringBuilder.toString();
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(list).length() + 140);
            stringBuilder2.append("audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in ");
            stringBuilder2.append(list);
            stringBuilder2.append(" order by rowid desc limit -1 offset ?)");
            return writableDatabase.delete("audience_filter_values", stringBuilder2.toString(), new String[]{str, Integer.toString(r2)}) > null;
        } catch (List<Integer> list2) {
            zzgt().zzjg().zze("Database error querying filters. appId", zzaq.zzby(str), list2);
            return false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final java.util.Map<java.lang.Integer, com.google.android.gms.internal.measurement.zzgm> zzbr(java.lang.String r12) {
        /*
        r11 = this;
        r11.zzcl();
        r11.zzaf();
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r12);
        r0 = r11.getWritableDatabase();
        r8 = 0;
        r1 = "audience_filter_values";
        r2 = "audience_id";
        r3 = "current_results";
        r2 = new java.lang.String[]{r2, r3};	 Catch:{ SQLiteException -> 0x007c, all -> 0x0079 }
        r3 = "app_id=?";
        r9 = 1;
        r4 = new java.lang.String[r9];	 Catch:{ SQLiteException -> 0x007c, all -> 0x0079 }
        r10 = 0;
        r4[r10] = r12;	 Catch:{ SQLiteException -> 0x007c, all -> 0x0079 }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r0 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ SQLiteException -> 0x007c, all -> 0x0079 }
        r1 = r0.moveToFirst();	 Catch:{ SQLiteException -> 0x0077 }
        if (r1 != 0) goto L_0x0033;
    L_0x002d:
        if (r0 == 0) goto L_0x0032;
    L_0x002f:
        r0.close();
    L_0x0032:
        return r8;
    L_0x0033:
        r1 = new androidx.collection.ArrayMap;	 Catch:{ SQLiteException -> 0x0077 }
        r1.<init>();	 Catch:{ SQLiteException -> 0x0077 }
    L_0x0038:
        r2 = r0.getInt(r10);	 Catch:{ SQLiteException -> 0x0077 }
        r3 = r0.getBlob(r9);	 Catch:{ SQLiteException -> 0x0077 }
        r4 = r3.length;	 Catch:{ SQLiteException -> 0x0077 }
        r3 = com.google.android.gms.internal.measurement.zzzi.zzj(r3, r10, r4);	 Catch:{ SQLiteException -> 0x0077 }
        r4 = new com.google.android.gms.internal.measurement.zzgm;	 Catch:{ SQLiteException -> 0x0077 }
        r4.<init>();	 Catch:{ SQLiteException -> 0x0077 }
        r4.zza(r3);	 Catch:{ IOException -> 0x0055 }
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ SQLiteException -> 0x0077 }
        r1.put(r2, r4);	 Catch:{ SQLiteException -> 0x0077 }
        goto L_0x006b;
    L_0x0055:
        r3 = move-exception;
        r4 = r11.zzgt();	 Catch:{ SQLiteException -> 0x0077 }
        r4 = r4.zzjg();	 Catch:{ SQLiteException -> 0x0077 }
        r5 = "Failed to merge filter results. appId, audienceId, error";
        r6 = com.google.android.gms.measurement.internal.zzaq.zzby(r12);	 Catch:{ SQLiteException -> 0x0077 }
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ SQLiteException -> 0x0077 }
        r4.zzd(r5, r6, r2, r3);	 Catch:{ SQLiteException -> 0x0077 }
    L_0x006b:
        r2 = r0.moveToNext();	 Catch:{ SQLiteException -> 0x0077 }
        if (r2 != 0) goto L_0x0038;
    L_0x0071:
        if (r0 == 0) goto L_0x0076;
    L_0x0073:
        r0.close();
    L_0x0076:
        return r1;
    L_0x0077:
        r1 = move-exception;
        goto L_0x007e;
    L_0x0079:
        r12 = move-exception;
        r0 = r8;
        goto L_0x0096;
    L_0x007c:
        r1 = move-exception;
        r0 = r8;
    L_0x007e:
        r2 = r11.zzgt();	 Catch:{ all -> 0x0095 }
        r2 = r2.zzjg();	 Catch:{ all -> 0x0095 }
        r3 = "Database error querying filter results. appId";
        r12 = com.google.android.gms.measurement.internal.zzaq.zzby(r12);	 Catch:{ all -> 0x0095 }
        r2.zze(r3, r12, r1);	 Catch:{ all -> 0x0095 }
        if (r0 == 0) goto L_0x0094;
    L_0x0091:
        r0.close();
    L_0x0094:
        return r8;
    L_0x0095:
        r12 = move-exception;
    L_0x0096:
        if (r0 == 0) goto L_0x009b;
    L_0x0098:
        r0.close();
    L_0x009b:
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzr.zzbr(java.lang.String):java.util.Map<java.lang.Integer, com.google.android.gms.internal.measurement.zzgm>");
    }

    @WorkerThread
    private static void zza(ContentValues contentValues, String str, Object obj) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(obj);
        if (obj instanceof String) {
            contentValues.put(str, (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put(str, (Long) obj);
        } else if (obj instanceof Double) {
            contentValues.put(str, (Double) obj);
        } else {
            throw new IllegalArgumentException("Invalid value type");
        }
    }

    @WorkerThread
    @VisibleForTesting
    private final Object zza(Cursor cursor, int i) {
        int type = cursor.getType(i);
        switch (type) {
            case 0:
                zzgt().zzjg().zzca("Loaded invalid null value from database");
                return null;
            case 1:
                return Long.valueOf(cursor.getLong(i));
            case 2:
                return Double.valueOf(cursor.getDouble(i));
            case 3:
                return cursor.getString(i);
            case 4:
                zzgt().zzjg().zzca("Loaded invalid blob type value, ignoring it");
                return null;
            default:
                zzgt().zzjg().zzg("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
                return null;
        }
    }

    @WorkerThread
    public final long zzik() {
        return zza("select max(bundle_end_timestamp) from queue", null, 0);
    }

    @WorkerThread
    @VisibleForTesting
    protected final long zzn(String str, String str2) {
        Object e;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzaf();
        zzcl();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        long zza;
        try {
            ContentValues contentValues;
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 32);
            stringBuilder.append("select ");
            stringBuilder.append(str2);
            stringBuilder.append(" from app2 where app_id=?");
            zza = zza(stringBuilder.toString(), new String[]{str}, -1);
            if (zza == -1) {
                contentValues = new ContentValues();
                contentValues.put("app_id", str);
                contentValues.put("first_open_count", Integer.valueOf(0));
                contentValues.put("previous_install_count", Integer.valueOf(0));
                if (writableDatabase.insertWithOnConflict("app2", null, contentValues, 5) == -1) {
                    zzgt().zzjg().zze("Failed to insert column (got -1). appId", zzaq.zzby(str), str2);
                    writableDatabase.endTransaction();
                    return -1;
                }
                zza = 0;
            }
            try {
                contentValues = new ContentValues();
                contentValues.put("app_id", str);
                contentValues.put(str2, Long.valueOf(1 + zza));
                if (((long) writableDatabase.update("app2", contentValues, "app_id = ?", new String[]{str})) == 0) {
                    zzgt().zzjg().zze("Failed to update column (got 0). appId", zzaq.zzby(str), str2);
                    writableDatabase.endTransaction();
                    return -1;
                }
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
                return zza;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzgt().zzjg().zzd("Error inserting column. appId", zzaq.zzby(str), str2, e);
                    return zza;
                } finally {
                    writableDatabase.endTransaction();
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            zza = 0;
            zzgt().zzjg().zzd("Error inserting column. appId", zzaq.zzby(str), str2, e);
            return zza;
        }
    }

    @WorkerThread
    public final long zzil() {
        return zza("select max(timestamp) from raw_events", null, 0);
    }

    public final long zza(zzgl com_google_android_gms_internal_measurement_zzgl) throws IOException {
        zzaf();
        zzcl();
        Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzgl);
        Preconditions.checkNotEmpty(com_google_android_gms_internal_measurement_zzgl.zztt);
        try {
            long j;
            Object obj = new byte[com_google_android_gms_internal_measurement_zzgl.zzwe()];
            zzzj zzk = zzzj.zzk(obj, 0, obj.length);
            com_google_android_gms_internal_measurement_zzgl.zza(zzk);
            zzk.zzzh();
            zzcp zzjr = zzjr();
            Preconditions.checkNotNull(obj);
            zzjr.zzgr().zzaf();
            MessageDigest messageDigest = zzfu.getMessageDigest();
            if (messageDigest == null) {
                zzjr.zzgt().zzjg().zzca("Failed to get MD5");
                j = 0;
            } else {
                j = zzfu.zzc(messageDigest.digest(obj));
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", com_google_android_gms_internal_measurement_zzgl.zztt);
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put(TtmlNode.TAG_METADATA, obj);
            try {
                getWritableDatabase().insertWithOnConflict("raw_events_metadata", null, contentValues, 4);
                return j;
            } catch (SQLiteException e) {
                zzgt().zzjg().zze("Error storing raw event metadata. appId", zzaq.zzby(com_google_android_gms_internal_measurement_zzgl.zztt), e);
                throw e;
            }
        } catch (IOException e2) {
            zzgt().zzjg().zze("Data loss. Failed to serialize event metadata. appId", zzaq.zzby(com_google_android_gms_internal_measurement_zzgl.zztt), e2);
            throw e2;
        }
    }

    public final boolean zzim() {
        return zza("select count(1) > 0 from raw_events", null) != 0;
    }

    public final boolean zzin() {
        return zza("select count(1) > 0 from raw_events where realtime = 1", null) != 0;
    }

    public final long zzbs(String str) {
        Preconditions.checkNotEmpty(str);
        return zza("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0);
    }

    public final String zzah(long j) {
        Object e;
        Throwable th;
        zzaf();
        zzcl();
        try {
            j = getWritableDatabase().rawQuery("select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;", new String[]{String.valueOf(j)});
            try {
                if (j.moveToFirst()) {
                    String string = j.getString(0);
                    if (j != null) {
                        j.close();
                    }
                    return string;
                }
                zzgt().zzjo().zzca("No expired configs for apps with pending events");
                if (j != null) {
                    j.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzgt().zzjg().zzg("Error selecting expired configs", e);
                    if (j != null) {
                        j.close();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (j != null) {
                        j.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            j = 0;
            zzgt().zzjg().zzg("Error selecting expired configs", e);
            if (j != null) {
                j.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            j = 0;
            if (j != null) {
                j.close();
            }
            throw th;
        }
    }

    public final long zzio() {
        Object obj;
        Throwable th;
        Cursor cursor = null;
        try {
            Cursor rawQuery = getWritableDatabase().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
            try {
                if (rawQuery.moveToFirst()) {
                    long j = rawQuery.getLong(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return j;
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return -1;
            } catch (SQLiteException e) {
                Cursor cursor2 = rawQuery;
                obj = e;
                cursor = cursor2;
                try {
                    zzgt().zzjg().zzg("Error querying raw events", obj);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return -1;
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                cursor = rawQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            obj = e2;
            zzgt().zzjg().zzg("Error querying raw events", obj);
            if (cursor != null) {
                cursor.close();
            }
            return -1;
        }
    }

    public final Pair<zzgi, Long> zza(String str, Long l) {
        Cursor rawQuery;
        zzaf();
        zzcl();
        try {
            rawQuery = getWritableDatabase().rawQuery("select main_event, children_to_process from main_event_params where app_id=? and event_id=?", new String[]{str, String.valueOf(l)});
            try {
                if (rawQuery.moveToFirst()) {
                    byte[] blob = rawQuery.getBlob(0);
                    Long valueOf = Long.valueOf(rawQuery.getLong(1));
                    zzzi zzj = zzzi.zzj(blob, 0, blob.length);
                    zzzr com_google_android_gms_internal_measurement_zzgi = new zzgi();
                    try {
                        com_google_android_gms_internal_measurement_zzgi.zza(zzj);
                        str = Pair.create(com_google_android_gms_internal_measurement_zzgi, valueOf);
                        if (rawQuery != null) {
                            rawQuery.close();
                        }
                        return str;
                    } catch (IOException e) {
                        zzgt().zzjg().zzd("Failed to merge main event. appId, eventId", zzaq.zzby(str), l, e);
                        if (rawQuery != null) {
                            rawQuery.close();
                        }
                        return null;
                    }
                }
                zzgt().zzjo().zzca("Main event not found");
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return null;
            } catch (SQLiteException e2) {
                str = e2;
                try {
                    zzgt().zzjg().zzg("Error selecting main event", str);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return null;
                } catch (Throwable th) {
                    str = th;
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    throw str;
                }
            }
        } catch (SQLiteException e3) {
            str = e3;
            rawQuery = null;
            zzgt().zzjg().zzg("Error selecting main event", str);
            if (rawQuery != null) {
                rawQuery.close();
            }
            return null;
        } catch (Throwable th2) {
            str = th2;
            rawQuery = null;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw str;
        }
    }

    public final boolean zza(String str, Long l, long j, zzgi com_google_android_gms_internal_measurement_zzgi) {
        zzaf();
        zzcl();
        Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzgi);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(l);
        try {
            byte[] bArr = new byte[com_google_android_gms_internal_measurement_zzgi.zzwe()];
            zzzj zzk = zzzj.zzk(bArr, 0, bArr.length);
            com_google_android_gms_internal_measurement_zzgi.zza(zzk);
            zzk.zzzh();
            zzgt().zzjo().zze("Saving complex main event, appId, data size", zzgq().zzbv(str), Integer.valueOf(bArr.length));
            com_google_android_gms_internal_measurement_zzgi = new ContentValues();
            com_google_android_gms_internal_measurement_zzgi.put("app_id", str);
            com_google_android_gms_internal_measurement_zzgi.put("event_id", l);
            com_google_android_gms_internal_measurement_zzgi.put("children_to_process", Long.valueOf(j));
            com_google_android_gms_internal_measurement_zzgi.put("main_event", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("main_event_params", null, com_google_android_gms_internal_measurement_zzgi, 5) != -1) {
                    return true;
                }
                zzgt().zzjg().zzg("Failed to insert complex main event (got -1). appId", zzaq.zzby(str));
                return false;
            } catch (Long l2) {
                zzgt().zzjg().zze("Error storing complex main event. appId", zzaq.zzby(str), l2);
                return false;
            }
        } catch (long j2) {
            zzgt().zzjg().zzd("Data loss. Failed to serialize event params/data. appId, eventId", zzaq.zzby(str), l2, j2);
            return false;
        }
    }

    public final boolean zza(zzz com_google_android_gms_measurement_internal_zzz, long j, boolean z) {
        zzaf();
        zzcl();
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzz);
        Preconditions.checkNotEmpty(com_google_android_gms_measurement_internal_zzz.zztt);
        zzzr com_google_android_gms_internal_measurement_zzgi = new zzgi();
        com_google_android_gms_internal_measurement_zzgi.zzaxo = Long.valueOf(com_google_android_gms_measurement_internal_zzz.zzaif);
        com_google_android_gms_internal_measurement_zzgi.zzaxm = new zzgj[com_google_android_gms_measurement_internal_zzz.zzaig.size()];
        Iterator it = com_google_android_gms_measurement_internal_zzz.zzaig.iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            zzgj com_google_android_gms_internal_measurement_zzgj = new zzgj();
            int i2 = i + 1;
            com_google_android_gms_internal_measurement_zzgi.zzaxm[i] = com_google_android_gms_internal_measurement_zzgj;
            com_google_android_gms_internal_measurement_zzgj.name = str;
            zzjr().zza(com_google_android_gms_internal_measurement_zzgj, com_google_android_gms_measurement_internal_zzz.zzaig.get(str));
            i = i2;
        }
        try {
            byte[] bArr = new byte[com_google_android_gms_internal_measurement_zzgi.zzwe()];
            zzzj zzk = zzzj.zzk(bArr, 0, bArr.length);
            com_google_android_gms_internal_measurement_zzgi.zza(zzk);
            zzk.zzzh();
            zzgt().zzjo().zze("Saving event, name, data size", zzgq().zzbv(com_google_android_gms_measurement_internal_zzz.name), Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", com_google_android_gms_measurement_internal_zzz.zztt);
            contentValues.put(JsonKey.NAME, com_google_android_gms_measurement_internal_zzz.name);
            contentValues.put(Param.TIMESTAMP, Long.valueOf(com_google_android_gms_measurement_internal_zzz.timestamp));
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put(DataSchemeDataSource.SCHEME_DATA, bArr);
            contentValues.put("realtime", Integer.valueOf(z));
            try {
                if (getWritableDatabase().insert("raw_events", false, contentValues) != -1) {
                    return true;
                }
                zzgt().zzjg().zzg("Failed to insert raw event (got -1). appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzz.zztt));
                return false;
            } catch (long j2) {
                zzgt().zzjg().zze("Error storing raw event. appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzz.zztt), j2);
                return false;
            }
        } catch (long j22) {
            zzgt().zzjg().zze("Data loss. Failed to serialize event params/data. appId", zzaq.zzby(com_google_android_gms_measurement_internal_zzz.zztt), j22);
            return false;
        }
    }

    private final boolean zzip() {
        return getContext().getDatabasePath("google_app_measurement.db").exists();
    }
}
