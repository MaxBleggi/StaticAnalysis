package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.core.content.ContextCompat;
import com.google.android.gms.internal.firebase_messaging.zzc;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

final class zzy {
    zzy() {
    }

    @WorkerThread
    final zzz zzb(Context context, String str) throws zzaa {
        zzz zzd = zzd(context, str);
        if (zzd != null) {
            return zzd;
        }
        return zzc(context, str);
    }

    @WorkerThread
    final zzz zzc(Context context, String str) {
        zzz com_google_firebase_iid_zzz = new zzz(zza.zzb(), System.currentTimeMillis());
        zzz zza = zza(context, str, com_google_firebase_iid_zzz, true);
        if (zza == null || zza.equals(com_google_firebase_iid_zzz)) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                Log.d("FirebaseInstanceId", "Generated new key");
            }
            zza(context, str, com_google_firebase_iid_zzz);
            return com_google_firebase_iid_zzz;
        }
        if (Log.isLoggable("FirebaseInstanceId", 3) != null) {
            Log.d("FirebaseInstanceId", "Loaded key after generating new one, using loaded one");
        }
        return zza;
    }

    static void zza(Context context) {
        for (File file : zzb(context).listFiles()) {
            if (file.getName().startsWith("com.google.InstanceId")) {
                file.delete();
            }
        }
    }

    @Nullable
    private final zzz zzd(Context context, String str) throws zzaa {
        zzaa com_google_firebase_iid_zzaa;
        try {
            zzz zze = zze(context, str);
            if (zze != null) {
                zza(context, str, zze);
                return zze;
            }
            com_google_firebase_iid_zzaa = null;
            try {
                zzz zza = zza(context.getSharedPreferences("com.google.android.gms.appid", 0), str);
                if (zza != null) {
                    zza(context, str, zza, false);
                    return zza;
                }
            } catch (Context context2) {
                com_google_firebase_iid_zzaa = context2;
            }
            if (com_google_firebase_iid_zzaa == null) {
                return null;
            }
            throw com_google_firebase_iid_zzaa;
        } catch (zzaa e) {
            com_google_firebase_iid_zzaa = e;
        }
    }

    private static KeyPair zzc(String str, String str2) throws zzaa {
        try {
            str = Base64.decode(str, 8);
            str2 = Base64.decode(str2, 8);
            try {
                KeyFactory instance = KeyFactory.getInstance("RSA");
                return new KeyPair(instance.generatePublic(new X509EncodedKeySpec(str)), instance.generatePrivate(new PKCS8EncodedKeySpec(str2)));
            } catch (Exception e) {
                str2 = String.valueOf(e);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 19);
                stringBuilder.append("Invalid key stored ");
                stringBuilder.append(str2);
                Log.w("FirebaseInstanceId", stringBuilder.toString());
                throw new zzaa(e);
            }
        } catch (Exception e2) {
            throw new zzaa(e2);
        }
    }

    @Nullable
    private final zzz zze(Context context, String str) throws zzaa {
        File zzf = zzf(context, str);
        if (zzf.exists() == null) {
            return null;
        }
        try {
            return zza(zzf);
        } catch (String str2) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                str2 = String.valueOf(str2);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 40);
                stringBuilder.append("Failed to read key from file, retrying: ");
                stringBuilder.append(str2);
                Log.d("FirebaseInstanceId", stringBuilder.toString());
            }
            try {
                return zza(zzf);
            } catch (Exception e) {
                str2 = String.valueOf(e);
                StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(str2).length() + 45);
                stringBuilder2.append("IID file exists, but failed to read from it: ");
                stringBuilder2.append(str2);
                Log.w("FirebaseInstanceId", stringBuilder2.toString());
                throw new zzaa(e);
            }
        }
    }

    @Nullable
    private final zzz zza(Context context, String str, zzz com_google_firebase_iid_zzz, boolean z) {
        zzz com_google_firebase_iid_zzz2;
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Writing key to properties file");
        }
        Properties properties = new Properties();
        properties.setProperty("pub", com_google_firebase_iid_zzz.zzv());
        properties.setProperty("pri", com_google_firebase_iid_zzz.zzw());
        properties.setProperty("cre", String.valueOf(com_google_firebase_iid_zzz.zzbs));
        context = zzf(context, str);
        try {
            context.createNewFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile(context, "rw");
            Throwable th;
            try {
                Throwable th2;
                FileChannel channel = randomAccessFile.getChannel();
                try {
                    channel.lock();
                    if (z && channel.size() > 0) {
                        try {
                            channel.position(0);
                            z = zza(channel);
                            if (channel != null) {
                                zza(null, channel);
                            }
                            zza(null, randomAccessFile);
                            return z;
                        } catch (boolean z2) {
                            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                                z2 = String.valueOf(z2);
                                StringBuilder stringBuilder = new StringBuilder(String.valueOf(z2).length() + 64);
                                stringBuilder.append("Tried reading key pair before writing new one, but failed with: ");
                                stringBuilder.append(z2);
                                Log.d("FirebaseInstanceId", stringBuilder.toString());
                            }
                        }
                    }
                    channel.position(0);
                    properties.store(Channels.newOutputStream(channel), null);
                    if (channel != null) {
                        zza(null, channel);
                    }
                    zza(null, randomAccessFile);
                    return com_google_firebase_iid_zzz;
                } catch (boolean z22) {
                    com_google_firebase_iid_zzz2 = z22;
                    th2 = com_google_firebase_iid_zzz;
                    com_google_firebase_iid_zzz = com_google_firebase_iid_zzz2;
                }
                zza(th, randomAccessFile);
                throw context;
                if (channel != null) {
                    zza(th2, channel);
                }
                throw com_google_firebase_iid_zzz;
            } catch (zzz com_google_firebase_iid_zzz3) {
                com_google_firebase_iid_zzz2 = com_google_firebase_iid_zzz3;
                th = context;
                context = com_google_firebase_iid_zzz2;
            }
        } catch (Context context2) {
            context2 = String.valueOf(context2);
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(context2).length() + 21);
            stringBuilder2.append("Failed to write key: ");
            stringBuilder2.append(context2);
            Log.w("FirebaseInstanceId", stringBuilder2.toString());
            return null;
        }
    }

    private static File zzb(Context context) {
        File noBackupFilesDir = ContextCompat.getNoBackupFilesDir(context);
        if (noBackupFilesDir != null && noBackupFilesDir.isDirectory()) {
            return noBackupFilesDir;
        }
        Log.w("FirebaseInstanceId", "noBackupFilesDir doesn't exist, using regular files directory instead");
        return context.getFilesDir();
    }

    private static File zzf(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            str = "com.google.InstanceId.properties";
        } else {
            try {
                str = Base64.encodeToString(str.getBytes("UTF-8"), 11);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 33);
                stringBuilder.append("com.google.InstanceId_");
                stringBuilder.append(str);
                stringBuilder.append(".properties");
                str = stringBuilder.toString();
            } catch (Context context2) {
                throw new AssertionError(context2);
            }
        }
        return new File(zzb(context2), str);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.firebase.iid.zzz zza(java.io.File r10) throws com.google.firebase.iid.zzaa, java.io.IOException {
        /*
        r9 = this;
        r0 = new java.io.FileInputStream;
        r0.<init>(r10);
        r10 = 0;
        r7 = r0.getChannel();	 Catch:{ Throwable -> 0x0034 }
        r2 = 0;
        r4 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
        r6 = 1;
        r1 = r7;
        r1.lock(r2, r4, r6);	 Catch:{ Throwable -> 0x0026, all -> 0x0023 }
        r1 = zza(r7);	 Catch:{ Throwable -> 0x0026, all -> 0x0023 }
        if (r7 == 0) goto L_0x001f;
    L_0x001c:
        zza(r10, r7);	 Catch:{ Throwable -> 0x0034 }
    L_0x001f:
        zza(r10, r0);
        return r1;
    L_0x0023:
        r1 = move-exception;
        r2 = r10;
        goto L_0x002c;
    L_0x0026:
        r1 = move-exception;
        throw r1;	 Catch:{ all -> 0x0028 }
    L_0x0028:
        r2 = move-exception;
        r8 = r2;
        r2 = r1;
        r1 = r8;
    L_0x002c:
        if (r7 == 0) goto L_0x0031;
    L_0x002e:
        zza(r2, r7);	 Catch:{ Throwable -> 0x0034 }
    L_0x0031:
        throw r1;	 Catch:{ Throwable -> 0x0034 }
    L_0x0032:
        r1 = move-exception;
        goto L_0x0036;
    L_0x0034:
        r10 = move-exception;
        throw r10;	 Catch:{ all -> 0x0032 }
    L_0x0036:
        zza(r10, r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzy.zza(java.io.File):com.google.firebase.iid.zzz");
    }

    private static zzz zza(FileChannel fileChannel) throws zzaa, IOException {
        Properties properties = new Properties();
        properties.load(Channels.newInputStream(fileChannel));
        String property = properties.getProperty("pub");
        String property2 = properties.getProperty("pri");
        if (property == null || property2 == null) {
            throw new zzaa("Invalid properties file");
        }
        try {
            return new zzz(zzc(property, property2), Long.parseLong(properties.getProperty("cre")));
        } catch (Exception e) {
            throw new zzaa(e);
        }
    }

    @Nullable
    private static zzz zza(SharedPreferences sharedPreferences, String str) throws zzaa {
        String string = sharedPreferences.getString(zzaw.zzd(str, "|P|"), null);
        String string2 = sharedPreferences.getString(zzaw.zzd(str, "|K|"), null);
        if (string != null) {
            if (string2 != null) {
                return new zzz(zzc(string, string2), zzb(sharedPreferences, str));
            }
        }
        return null;
    }

    private final void zza(android.content.Context r3, java.lang.String r4, com.google.firebase.iid.zzz r5) {
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
        r0 = "com.google.android.gms.appid";
        r1 = 0;
        r3 = r3.getSharedPreferences(r0, r1);
        r0 = zza(r3, r4);	 Catch:{ zzaa -> 0x0012 }
        r0 = r5.equals(r0);	 Catch:{ zzaa -> 0x0012 }
        if (r0 == 0) goto L_0x0012;
    L_0x0011:
        return;
    L_0x0012:
        r0 = "FirebaseInstanceId";
        r1 = 3;
        r0 = android.util.Log.isLoggable(r0, r1);
        if (r0 == 0) goto L_0x0022;
    L_0x001b:
        r0 = "FirebaseInstanceId";
        r1 = "Writing key to shared preferences";
        android.util.Log.d(r0, r1);
    L_0x0022:
        r3 = r3.edit();
        r0 = "|P|";
        r0 = com.google.firebase.iid.zzaw.zzd(r4, r0);
        r1 = r5.zzv();
        r3.putString(r0, r1);
        r0 = "|K|";
        r0 = com.google.firebase.iid.zzaw.zzd(r4, r0);
        r1 = r5.zzw();
        r3.putString(r0, r1);
        r0 = "cre";
        r4 = com.google.firebase.iid.zzaw.zzd(r4, r0);
        r0 = r5.zzbs;
        r5 = java.lang.String.valueOf(r0);
        r3.putString(r4, r5);
        r3.commit();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzy.zza(android.content.Context, java.lang.String, com.google.firebase.iid.zzz):void");
    }

    private static long zzb(android.content.SharedPreferences r1, java.lang.String r2) {
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
        r0 = "cre";
        r2 = com.google.firebase.iid.zzaw.zzd(r2, r0);
        r0 = 0;
        r1 = r1.getString(r2, r0);
        if (r1 == 0) goto L_0x0012;
    L_0x000d:
        r1 = java.lang.Long.parseLong(r1);	 Catch:{ NumberFormatException -> 0x0012 }
        return r1;
    L_0x0012:
        r1 = 0;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzy.zzb(android.content.SharedPreferences, java.lang.String):long");
    }

    private static /* synthetic */ void zza(Throwable th, FileChannel fileChannel) {
        if (th != null) {
            try {
                fileChannel.close();
                return;
            } catch (FileChannel fileChannel2) {
                zzc.zza(th, fileChannel2);
                return;
            }
        }
        fileChannel2.close();
    }

    private static /* synthetic */ void zza(Throwable th, RandomAccessFile randomAccessFile) {
        if (th != null) {
            try {
                randomAccessFile.close();
                return;
            } catch (RandomAccessFile randomAccessFile2) {
                zzc.zza(th, randomAccessFile2);
                return;
            }
        }
        randomAccessFile2.close();
    }

    private static /* synthetic */ void zza(Throwable th, FileInputStream fileInputStream) {
        if (th != null) {
            try {
                fileInputStream.close();
                return;
            } catch (FileInputStream fileInputStream2) {
                zzc.zza(th, fileInputStream2);
                return;
            }
        }
        fileInputStream2.close();
    }
}
