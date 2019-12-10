package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.iid.zzl.zza;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.concurrent.GuardedBy;

final class zzat {
    private static int zzcf;
    private static PendingIntent zzcr;
    private final zzan zzan;
    @GuardedBy("responseCallbacks")
    private final SimpleArrayMap<String, TaskCompletionSource<Bundle>> zzcs = new SimpleArrayMap();
    private Messenger zzct;
    private Messenger zzcu;
    private zzl zzcv;
    private final Context zzx;

    public zzat(Context context, zzan com_google_firebase_iid_zzan) {
        this.zzx = context;
        this.zzan = com_google_firebase_iid_zzan;
        this.zzct = new Messenger(new zzau(this, Looper.getMainLooper()));
    }

    private final android.os.Bundle zze(android.os.Bundle r1) throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.firebase.iid.zzat.zze(android.os.Bundle):android.os.Bundle
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 5 more
*/
        /*
        r0 = this;
        r0 = zzah();
        r1 = new com.google.android.gms.tasks.TaskCompletionSource;
        r1.<init>();
        r2 = r8.zzcs;
        monitor-enter(r2);
        r3 = r8.zzcs;
        r3.put(r0, r1);
        monitor-exit(r2);
        r2 = r8.zzan;
        r2 = r2.zzac();
        if (r2 == 0) goto L_0x011e;
        r2 = new android.content.Intent;
        r2.<init>();
        r3 = "com.google.android.gms";
        r2.setPackage(r3);
        r3 = r8.zzan;
        r3 = r3.zzac();
        r4 = 2;
        if (r3 != r4) goto L_0x0033;
        r3 = "com.google.iid.TOKEN_REQUEST";
        r2.setAction(r3);
        goto L_0x0038;
        r3 = "com.google.android.c2dm.intent.REGISTER";
        r2.setAction(r3);
        r2.putExtras(r9);
        r9 = r8.zzx;
        zza(r9, r2);
        r9 = "kid";
        r3 = java.lang.String.valueOf(r0);
        r3 = r3.length();
        r3 = r3 + 5;
        r5 = new java.lang.StringBuilder;
        r5.<init>(r3);
        r3 = "|ID|";
        r5.append(r3);
        r5.append(r0);
        r3 = "|";
        r5.append(r3);
        r3 = r5.toString();
        r2.putExtra(r9, r3);
        r9 = "FirebaseInstanceId";
        r3 = 3;
        r9 = android.util.Log.isLoggable(r9, r3);
        if (r9 == 0) goto L_0x0096;
        r9 = "FirebaseInstanceId";
        r5 = r2.getExtras();
        r5 = java.lang.String.valueOf(r5);
        r6 = java.lang.String.valueOf(r5);
        r6 = r6.length();
        r6 = r6 + 8;
        r7 = new java.lang.StringBuilder;
        r7.<init>(r6);
        r6 = "Sending ";
        r7.append(r6);
        r7.append(r5);
        r5 = r7.toString();
        android.util.Log.d(r9, r5);
        r9 = "google.messenger";
        r5 = r8.zzct;
        r2.putExtra(r9, r5);
        r9 = r8.zzcu;
        if (r9 != 0) goto L_0x00a5;
        r9 = r8.zzcv;
        if (r9 == 0) goto L_0x00cb;
        r9 = android.os.Message.obtain();
        r9.obj = r2;
        r5 = r8.zzcu;
        if (r5 == 0) goto L_0x00b5;
        r5 = r8.zzcu;
        r5.send(r9);
        goto L_0x00de;
        r5 = r8.zzcv;
        r5.send(r9);
        goto L_0x00de;
        r9 = "FirebaseInstanceId";
        r9 = android.util.Log.isLoggable(r9, r3);
        if (r9 == 0) goto L_0x00cb;
        r9 = "FirebaseInstanceId";
        r3 = "Messenger failed, fallback to startService";
        android.util.Log.d(r9, r3);
        r9 = r8.zzan;
        r9 = r9.zzac();
        if (r9 != r4) goto L_0x00d9;
        r9 = r8.zzx;
        r9.sendBroadcast(r2);
        goto L_0x00de;
        r9 = r8.zzx;
        r9.startService(r2);
        r9 = r1.getTask();
        r1 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r3 = java.util.concurrent.TimeUnit.MILLISECONDS;
        r9 = com.google.android.gms.tasks.Tasks.await(r9, r1, r3);
        r9 = (android.os.Bundle) r9;
        r1 = r8.zzcs;
        monitor-enter(r1);
        r2 = r8.zzcs;
        r2.remove(r0);
        monitor-exit(r1);
        return r9;
        r9 = move-exception;
        monitor-exit(r1);
        throw r9;
        r9 = move-exception;
        goto L_0x0111;
        r9 = move-exception;
        r1 = new java.io.IOException;
        r1.<init>(r9);
        throw r1;
        r9 = "FirebaseInstanceId";
        r1 = "No response";
        android.util.Log.w(r9, r1);
        r9 = new java.io.IOException;
        r1 = "TIMEOUT";
        r9.<init>(r1);
        throw r9;
        r1 = r8.zzcs;
        monitor-enter(r1);
        r2 = r8.zzcs;
        r2.remove(r0);
        monitor-exit(r1);
        throw r9;
        r9 = move-exception;
        monitor-exit(r1);
        throw r9;
        r9 = new java.io.IOException;
        r0 = "MISSING_INSTANCEID_SERVICE";
        r9.<init>(r0);
        throw r9;
    L_0x0126:
        r9 = move-exception;
        monitor-exit(r2);
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzat.zze(android.os.Bundle):android.os.Bundle");
    }

    private final void zzb(Message message) {
        if (message == null || !(message.obj instanceof Intent)) {
            Log.w("FirebaseInstanceId", "Dropping invalid message");
        } else {
            Intent intent = (Intent) message.obj;
            intent.setExtrasClassLoader(new zza());
            if (intent.hasExtra("google.messenger")) {
                Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
                if (parcelableExtra instanceof zzl) {
                    this.zzcv = (zzl) parcelableExtra;
                }
                if (parcelableExtra instanceof Messenger) {
                    this.zzcu = (Messenger) parcelableExtra;
                }
            }
            Intent intent2 = (Intent) message.obj;
            String action = intent2.getAction();
            String str;
            if ("com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
                CharSequence stringExtra = intent2.getStringExtra("registration_id");
                if (stringExtra == null) {
                    stringExtra = intent2.getStringExtra("unregistered");
                }
                if (stringExtra == null) {
                    action = intent2.getStringExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR);
                    if (action == null) {
                        message = String.valueOf(intent2.getExtras());
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(message).length() + 49);
                        stringBuilder.append("Unexpected response, no error or registration id ");
                        stringBuilder.append(message);
                        Log.w("FirebaseInstanceId", stringBuilder.toString());
                    } else {
                        if (Log.isLoggable("FirebaseInstanceId", 3)) {
                            String str2 = "FirebaseInstanceId";
                            String str3 = "Received InstanceID error ";
                            String valueOf = String.valueOf(action);
                            Log.d(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                        }
                        if (action.startsWith("|")) {
                            String[] split = action.split("\\|");
                            if (split.length > 2) {
                                if ("ID".equals(split[1])) {
                                    action = split[2];
                                    str = split[3];
                                    if (str.startsWith(":")) {
                                        str = str.substring(1);
                                    }
                                    zza(action, intent2.putExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR, str).getExtras());
                                }
                            }
                            message = "FirebaseInstanceId";
                            str = "Unexpected structured response ";
                            action = String.valueOf(action);
                            Log.w(message, action.length() != 0 ? str.concat(action) : new String(str));
                        } else {
                            synchronized (this.zzcs) {
                                for (int i = 0; i < this.zzcs.size(); i++) {
                                    zza((String) this.zzcs.keyAt(i), intent2.getExtras());
                                }
                            }
                        }
                    }
                } else {
                    Matcher matcher = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.*)").matcher(stringExtra);
                    if (matcher.matches()) {
                        action = matcher.group(1);
                        str = matcher.group(2);
                        Bundle extras = intent2.getExtras();
                        extras.putString("registration_id", str);
                        zza(action, extras);
                        return;
                    }
                    if (Log.isLoggable("FirebaseInstanceId", 3) != null) {
                        message = "FirebaseInstanceId";
                        str = "Unexpected response string: ";
                        action = String.valueOf(stringExtra);
                        Log.d(message, action.length() != 0 ? str.concat(action) : new String(str));
                    }
                    return;
                }
            }
            if (Log.isLoggable("FirebaseInstanceId", 3) != null) {
                message = "FirebaseInstanceId";
                str = "Unexpected response action: ";
                action = String.valueOf(action);
                Log.d(message, action.length() != 0 ? str.concat(action) : new String(str));
            }
        }
    }

    private static synchronized void zza(Context context, Intent intent) {
        synchronized (zzat.class) {
            if (zzcr == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                zzcr = PendingIntent.getBroadcast(context, 0, intent2, 0);
            }
            intent.putExtra(SettingsJsonConstants.APP_KEY, zzcr);
        }
    }

    private final void zza(String str, Bundle bundle) {
        synchronized (this.zzcs) {
            TaskCompletionSource taskCompletionSource = (TaskCompletionSource) this.zzcs.remove(str);
            if (taskCompletionSource == null) {
                bundle = "FirebaseInstanceId";
                String str2 = "Missing callback for ";
                str = String.valueOf(str);
                Log.w(bundle, str.length() != 0 ? str2.concat(str) : new String(str2));
                return;
            }
            taskCompletionSource.setResult(bundle);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final android.os.Bundle zzc(android.os.Bundle r6) throws java.io.IOException {
        /*
        r5 = this;
        r0 = r5.zzan;
        r0 = r0.zzaf();
        r1 = 12000000; // 0xb71b00 float:1.6815582E-38 double:5.9287878E-317;
        if (r0 < r1) goto L_0x0067;
    L_0x000b:
        r0 = r5.zzx;
        r0 = com.google.firebase.iid.zzab.zzc(r0);
        r1 = 1;
        r0 = r0.zzb(r1, r6);
        r0 = com.google.android.gms.tasks.Tasks.await(r0);	 Catch:{ InterruptedException -> 0x001d, InterruptedException -> 0x001d }
        r0 = (android.os.Bundle) r0;	 Catch:{ InterruptedException -> 0x001d, InterruptedException -> 0x001d }
        return r0;
    L_0x001d:
        r0 = move-exception;
        r1 = "FirebaseInstanceId";
        r2 = 3;
        r1 = android.util.Log.isLoggable(r1, r2);
        if (r1 == 0) goto L_0x004b;
    L_0x0027:
        r1 = "FirebaseInstanceId";
        r2 = java.lang.String.valueOf(r0);
        r3 = java.lang.String.valueOf(r2);
        r3 = r3.length();
        r3 = r3 + 22;
        r4 = new java.lang.StringBuilder;
        r4.<init>(r3);
        r3 = "Error making request: ";
        r4.append(r3);
        r4.append(r2);
        r2 = r4.toString();
        android.util.Log.d(r1, r2);
    L_0x004b:
        r1 = r0.getCause();
        r1 = r1 instanceof com.google.firebase.iid.zzal;
        if (r1 == 0) goto L_0x0065;
    L_0x0053:
        r0 = r0.getCause();
        r0 = (com.google.firebase.iid.zzal) r0;
        r0 = r0.getErrorCode();
        r1 = 4;
        if (r0 != r1) goto L_0x0065;
    L_0x0060:
        r6 = r5.zzd(r6);
        return r6;
    L_0x0065:
        r6 = 0;
        return r6;
    L_0x0067:
        r6 = r5.zzd(r6);
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzat.zzc(android.os.Bundle):android.os.Bundle");
    }

    private final Bundle zzd(Bundle bundle) throws IOException {
        Bundle zze = zze(bundle);
        if (zze == null || !zze.containsKey("google.messenger")) {
            return zze;
        }
        zze = zze(bundle);
        return (zze == null || zze.containsKey("google.messenger") == null) ? zze : null;
    }

    private static synchronized String zzah() {
        String num;
        synchronized (zzat.class) {
            int i = zzcf;
            zzcf = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }
}
