package com.google.android.gms.measurement.internal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import androidx.annotation.WorkerThread;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class zzv {
    @WorkerThread
    private static Set<String> zzb(SQLiteDatabase sQLiteDatabase, String str) {
        Object hashSet = new HashSet();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 22);
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(str);
        stringBuilder.append(" LIMIT 0");
        sQLiteDatabase = sQLiteDatabase.rawQuery(stringBuilder.toString(), null);
        try {
            Collections.addAll(hashSet, sQLiteDatabase.getColumnNames());
            return hashSet;
        } finally {
            sQLiteDatabase.close();
        }
    }

    @WorkerThread
    static void zza(zzaq com_google_android_gms_measurement_internal_zzaq, SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String[] strArr) throws SQLiteException {
        if (com_google_android_gms_measurement_internal_zzaq != null) {
            if (!zza(com_google_android_gms_measurement_internal_zzaq, sQLiteDatabase, str)) {
                sQLiteDatabase.execSQL(str2);
            }
            if (com_google_android_gms_measurement_internal_zzaq != null) {
                try {
                    str2 = zzb(sQLiteDatabase, str);
                    str3 = str3.split(",");
                    int length = str3.length;
                    int i = 0;
                    while (i < length) {
                        String str4 = str3[i];
                        if (str2.remove(str4)) {
                            i++;
                        } else {
                            str3 = new StringBuilder((String.valueOf(str).length() + 35) + String.valueOf(str4).length());
                            str3.append("Table ");
                            str3.append(str);
                            str3.append(" is missing required column: ");
                            str3.append(str4);
                            throw new SQLiteException(str3.toString());
                        }
                    }
                    if (strArr != null) {
                        for (int i2 = 0; i2 < strArr.length; i2 += 2) {
                            if (str2.remove(strArr[i2]) == null) {
                                sQLiteDatabase.execSQL(strArr[i2 + 1]);
                            }
                        }
                    }
                    if (str2.isEmpty() == null) {
                        com_google_android_gms_measurement_internal_zzaq.zzjj().zze("Table has extra columns. table, columns", str, TextUtils.join(", ", str2));
                        return;
                    }
                    return;
                } catch (SQLiteDatabase sQLiteDatabase2) {
                    com_google_android_gms_measurement_internal_zzaq.zzjg().zzg("Failed to verify columns on table that was just created", str);
                    throw sQLiteDatabase2;
                }
            }
            throw new IllegalArgumentException("Monitor must not be null");
        }
        throw new IllegalArgumentException("Monitor must not be null");
    }

    @WorkerThread
    private static boolean zza(zzaq com_google_android_gms_measurement_internal_zzaq, SQLiteDatabase sQLiteDatabase, String str) {
        if (com_google_android_gms_measurement_internal_zzaq != null) {
            Cursor cursor = null;
            try {
                SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
                sQLiteDatabase = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{JsonKey.NAME}, "name=?", new String[]{str}, null, null, null);
                try {
                    boolean moveToFirst = sQLiteDatabase.moveToFirst();
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                    return moveToFirst;
                } catch (SQLiteException e) {
                    SQLiteException sQLiteException = e;
                    cursor = sQLiteDatabase;
                    sQLiteDatabase = sQLiteException;
                    try {
                        com_google_android_gms_measurement_internal_zzaq.zzjj().zze("Error querying for table", str, sQLiteDatabase);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return false;
                    } catch (Throwable th) {
                        com_google_android_gms_measurement_internal_zzaq = th;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw com_google_android_gms_measurement_internal_zzaq;
                    }
                } catch (Throwable th2) {
                    com_google_android_gms_measurement_internal_zzaq = th2;
                    cursor = sQLiteDatabase;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw com_google_android_gms_measurement_internal_zzaq;
                }
            } catch (SQLiteException e2) {
                sQLiteDatabase = e2;
                com_google_android_gms_measurement_internal_zzaq.zzjj().zze("Error querying for table", str, sQLiteDatabase);
                if (cursor != null) {
                    cursor.close();
                }
                return false;
            }
        }
        throw new IllegalArgumentException("Monitor must not be null");
    }

    static void zza(zzaq com_google_android_gms_measurement_internal_zzaq, SQLiteDatabase sQLiteDatabase) {
        if (com_google_android_gms_measurement_internal_zzaq != null) {
            File file = new File(sQLiteDatabase.getPath());
            if (!file.setReadable(false, false)) {
                com_google_android_gms_measurement_internal_zzaq.zzjj().zzca("Failed to turn off database read permission");
            }
            if (file.setWritable(false, false) == null) {
                com_google_android_gms_measurement_internal_zzaq.zzjj().zzca("Failed to turn off database write permission");
            }
            if (!file.setReadable(true, true)) {
                com_google_android_gms_measurement_internal_zzaq.zzjj().zzca("Failed to turn on database read permission for owner");
            }
            if (file.setWritable(true, true) == null) {
                com_google_android_gms_measurement_internal_zzaq.zzjj().zzca("Failed to turn on database write permission for owner");
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Monitor must not be null");
    }
}
