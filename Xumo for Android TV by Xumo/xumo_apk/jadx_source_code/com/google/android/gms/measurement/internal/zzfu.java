package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.core.app.NotificationCompat;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzef;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.security.auth.x500.X500Principal;

public final class zzfu extends zzcq {
    private static final String[] zzavb = new String[]{"firebase_", "google_", "ga_"};
    private int zzaed;
    private SecureRandom zzavc;
    private final AtomicLong zzavd = new AtomicLong(0);
    private Integer zzave = null;

    zzfu(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
    }

    protected final boolean zzgy() {
        return true;
    }

    @WorkerThread
    protected final void zzgz() {
        zzaf();
        SecureRandom secureRandom = new SecureRandom();
        long nextLong = secureRandom.nextLong();
        if (nextLong == 0) {
            nextLong = secureRandom.nextLong();
            if (nextLong == 0) {
                zzgt().zzjj().zzca("Utils falling back to Random for random id");
            }
        }
        this.zzavd.set(nextLong);
    }

    public final long zzmj() {
        long nextLong;
        if (this.zzavd.get() == 0) {
            synchronized (this.zzavd) {
                nextLong = new Random(System.nanoTime() ^ zzbx().currentTimeMillis()).nextLong();
                int i = this.zzaed + 1;
                this.zzaed = i;
                nextLong += (long) i;
            }
            return nextLong;
        }
        synchronized (this.zzavd) {
            this.zzavd.compareAndSet(-1, 1);
            nextLong = this.zzavd.getAndIncrement();
        }
        return nextLong;
    }

    @WorkerThread
    final SecureRandom zzmk() {
        zzaf();
        if (this.zzavc == null) {
            this.zzavc = new SecureRandom();
        }
        return this.zzavc;
    }

    static boolean zzcv(String str) {
        Preconditions.checkNotEmpty(str);
        if (str.charAt(0) == '_') {
            if (str.equals("_ep") == null) {
                return false;
            }
        }
        return true;
    }

    final Bundle zza(@NonNull Uri uri) {
        if (uri == null) {
            return null;
        }
        try {
            Object queryParameter;
            Object queryParameter2;
            Object queryParameter3;
            Object queryParameter4;
            if (uri.isHierarchical()) {
                queryParameter = uri.getQueryParameter("utm_campaign");
                queryParameter2 = uri.getQueryParameter("utm_source");
                queryParameter3 = uri.getQueryParameter("utm_medium");
                queryParameter4 = uri.getQueryParameter("gclid");
            } else {
                queryParameter = null;
                queryParameter2 = queryParameter;
                queryParameter3 = queryParameter2;
                queryParameter4 = queryParameter3;
            }
            if (TextUtils.isEmpty(queryParameter) && TextUtils.isEmpty(queryParameter2) && TextUtils.isEmpty(queryParameter3)) {
                if (TextUtils.isEmpty(queryParameter4)) {
                    return null;
                }
            }
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString(Param.CAMPAIGN, queryParameter);
            }
            if (!TextUtils.isEmpty(queryParameter2)) {
                bundle.putString(Param.SOURCE, queryParameter2);
            }
            if (!TextUtils.isEmpty(queryParameter3)) {
                bundle.putString("medium", queryParameter3);
            }
            if (!TextUtils.isEmpty(queryParameter4)) {
                bundle.putString("gclid", queryParameter4);
            }
            queryParameter = uri.getQueryParameter("utm_term");
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString(Param.TERM, queryParameter);
            }
            queryParameter = uri.getQueryParameter("utm_content");
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString(Param.CONTENT, queryParameter);
            }
            queryParameter = uri.getQueryParameter(Param.ACLID);
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString(Param.ACLID, queryParameter);
            }
            queryParameter = uri.getQueryParameter(Param.CP1);
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString(Param.CP1, queryParameter);
            }
            uri = uri.getQueryParameter("anid");
            if (!TextUtils.isEmpty(uri)) {
                bundle.putString("anid", uri);
            }
            return bundle;
        } catch (Uri uri2) {
            zzgt().zzjj().zzg("Install referrer url isn't a hierarchical URI", uri2);
            return null;
        }
    }

    static boolean zzd(Intent intent) {
        intent = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        if (!("android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(intent) || "https://www.google.com".equals(intent))) {
            if ("android-app://com.google.appcrawler".equals(intent) == null) {
                return null;
            }
        }
        return true;
    }

    final boolean zzs(String str, String str2) {
        if (str2 == null) {
            zzgt().zzjg().zzg("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.length() == 0) {
            zzgt().zzjg().zzg("Name is required and can't be empty. Type", str);
            return false;
        } else {
            int codePointAt = str2.codePointAt(0);
            if (Character.isLetter(codePointAt)) {
                int length = str2.length();
                codePointAt = Character.charCount(codePointAt);
                while (codePointAt < length) {
                    int codePointAt2 = str2.codePointAt(codePointAt);
                    if (codePointAt2 == 95 || Character.isLetterOrDigit(codePointAt2)) {
                        codePointAt += Character.charCount(codePointAt2);
                    } else {
                        zzgt().zzjg().zze("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                        return false;
                    }
                }
                return true;
            }
            zzgt().zzjg().zze("Name must start with a letter. Type, name", str, str2);
            return false;
        }
    }

    private final boolean zzt(String str, String str2) {
        if (str2 == null) {
            zzgt().zzjg().zzg("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.length() == 0) {
            zzgt().zzjg().zzg("Name is required and can't be empty. Type", str);
            return false;
        } else {
            int codePointAt = str2.codePointAt(0);
            if (Character.isLetter(codePointAt) || codePointAt == 95) {
                int length = str2.length();
                codePointAt = Character.charCount(codePointAt);
                while (codePointAt < length) {
                    int codePointAt2 = str2.codePointAt(codePointAt);
                    if (codePointAt2 == 95 || Character.isLetterOrDigit(codePointAt2)) {
                        codePointAt += Character.charCount(codePointAt2);
                    } else {
                        zzgt().zzjg().zze("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                        return false;
                    }
                }
                return true;
            }
            zzgt().zzjg().zze("Name must start with a letter or _ (underscore). Type, name", str, str2);
            return false;
        }
    }

    final boolean zza(String str, String[] strArr, String str2) {
        if (str2 == null) {
            zzgt().zzjg().zzg("Name is required and can't be null. Type", str);
            return false;
        }
        Object obj;
        Preconditions.checkNotNull(str2);
        for (String startsWith : zzavb) {
            if (str2.startsWith(startsWith)) {
                obj = 1;
                break;
            }
        }
        obj = null;
        if (obj != null) {
            zzgt().zzjg().zze("Name starts with reserved prefix. Type, name", str, str2);
            return false;
        }
        if (strArr != null) {
            Preconditions.checkNotNull(strArr);
            for (String zzv : strArr) {
                if (zzv(str2, zzv)) {
                    strArr = 1;
                    break;
                }
            }
            strArr = null;
            if (strArr != null) {
                zzgt().zzjg().zze("Name is reserved. Type, name", str, str2);
                return false;
            }
        }
        return true;
    }

    final boolean zza(String str, int i, String str2) {
        if (str2 == null) {
            zzgt().zzjg().zzg("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.codePointCount(0, str2.length()) <= i) {
            return true;
        } else {
            zzgt().zzjg().zzd("Name is too long. Type, maximum supported length, name", str, Integer.valueOf(i), str2);
            return false;
        }
    }

    final int zzcw(String str) {
        if (!zzt(NotificationCompat.CATEGORY_EVENT, str)) {
            return 2;
        }
        if (!zza(NotificationCompat.CATEGORY_EVENT, zzcs.zzard, str)) {
            return 13;
        }
        if (zza(NotificationCompat.CATEGORY_EVENT, 40, str) == null) {
            return 2;
        }
        return null;
    }

    final int zzcx(String str) {
        if (!zzt("user property", str)) {
            return 6;
        }
        if (!zza("user property", zzcu.zzarh, str)) {
            return 15;
        }
        if (zza("user property", 24, str) == null) {
            return 6;
        }
        return null;
    }

    private final boolean zza(String str, String str2, int i, Object obj, boolean z) {
        if (!(obj == null || (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Integer) || (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Boolean))) {
            if (!(obj instanceof Double)) {
                if (!((obj instanceof String) || (obj instanceof Character))) {
                    if (!(obj instanceof CharSequence)) {
                        if ((obj instanceof Bundle) != null && z) {
                            return true;
                        }
                        if ((obj instanceof Parcelable[]) != null && z) {
                            Parcelable[] parcelableArr = (Parcelable[]) obj;
                            str = parcelableArr.length;
                            i = 0;
                            while (i < str) {
                                z = parcelableArr[i];
                                if (z instanceof Bundle) {
                                    i++;
                                } else {
                                    zzgt().zzjj().zze("All Parcelable[] elements must be of type Bundle. Value type, name", z.getClass(), str2);
                                    return false;
                                }
                            }
                            return true;
                        } else if ((obj instanceof ArrayList) == null || !z) {
                            return false;
                        } else {
                            ArrayList arrayList = (ArrayList) obj;
                            str = arrayList.size();
                            i = 0;
                            while (i < str) {
                                z = arrayList.get(i);
                                i++;
                                if (!(z instanceof Bundle)) {
                                    zzgt().zzjj().zze("All ArrayList elements must be of type Bundle. Value type, name", z.getClass(), str2);
                                    return false;
                                }
                            }
                            return true;
                        }
                    }
                }
                obj = String.valueOf(obj);
                if (obj.codePointCount(0, obj.length()) <= i) {
                    return true;
                }
                zzgt().zzjj().zzd("Value is too long; discarded. Value kind, name, value length", str, str2, Integer.valueOf(obj.length()));
                return false;
            }
        }
        return true;
    }

    final boolean zzu(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            if (TextUtils.isEmpty(str2) != null) {
                if (this.zzadp.zzkn() != null) {
                    zzgt().zzjg().zzca("Missing google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI");
                }
                return false;
            } else if (zzcy(str2) == null) {
                zzgt().zzjg().zzg("Invalid admob_app_id. Analytics disabled.", zzaq.zzby(str2));
                return false;
            }
        } else if (zzcy(str) == null) {
            if (this.zzadp.zzkn() != null) {
                zzgt().zzjg().zzg("Invalid google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI. provided id", zzaq.zzby(str));
            }
            return false;
        }
        return true;
    }

    static boolean zza(String str, String str2, String str3, String str4) {
        boolean isEmpty = TextUtils.isEmpty(str);
        boolean isEmpty2 = TextUtils.isEmpty(str2);
        if (!isEmpty && !isEmpty2) {
            return str.equals(str2) == null;
        } else {
            if (isEmpty && isEmpty2) {
                return (TextUtils.isEmpty(str3) == null && TextUtils.isEmpty(str4) == null) ? str3.equals(str4) == null : TextUtils.isEmpty(str4) == null;
            } else {
                if (isEmpty || !isEmpty2) {
                    if (TextUtils.isEmpty(str3) == null) {
                        if (str3.equals(str4) != null) {
                            return false;
                        }
                    }
                    return true;
                } else if (TextUtils.isEmpty(str4) != null) {
                    return false;
                } else {
                    if (TextUtils.isEmpty(str3) == null) {
                        if (str3.equals(str4) != null) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
    }

    @VisibleForTesting
    private static boolean zzcy(String str) {
        Preconditions.checkNotNull(str);
        return str.matches("^(1:\\d+:android:[a-f0-9]+|ca-app-pub-.*)$");
    }

    private static Object zza(int i, Object obj, boolean z) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Long)) {
            if (!(obj instanceof Double)) {
                if (obj instanceof Integer) {
                    return Long.valueOf((long) ((Integer) obj).intValue());
                }
                if (obj instanceof Byte) {
                    return Long.valueOf((long) ((Byte) obj).byteValue());
                }
                if (obj instanceof Short) {
                    return Long.valueOf((long) ((Short) obj).shortValue());
                }
                if (obj instanceof Boolean) {
                    return Long.valueOf(((Boolean) obj).booleanValue() != 0 ? 1 : 0);
                } else if (obj instanceof Float) {
                    return Double.valueOf(((Float) obj).doubleValue());
                } else {
                    if (!((obj instanceof String) || (obj instanceof Character))) {
                        if (!(obj instanceof CharSequence)) {
                            return null;
                        }
                    }
                    return zza(String.valueOf(obj), i, z);
                }
            }
        }
        return obj;
    }

    public static String zza(String str, int i, boolean z) {
        if (str.codePointCount(0, str.length()) > i) {
            return z ? String.valueOf(str.substring(0, str.offsetByCodePoints(0, i))).concat("...") : null;
        } else {
            return str;
        }
    }

    final Object zzh(String str, Object obj) {
        int i = 256;
        if ("_ev".equals(str)) {
            return zza(256, obj, true);
        }
        if (zzda(str) == null) {
            i = 100;
        }
        return zza(i, obj, (boolean) null);
    }

    static Bundle[] zzf(Object obj) {
        if (obj instanceof Bundle) {
            return new Bundle[]{(Bundle) obj};
        } else if (obj instanceof Parcelable[]) {
            Parcelable[] parcelableArr = (Parcelable[]) obj;
            return (Bundle[]) Arrays.copyOf(parcelableArr, parcelableArr.length, Bundle[].class);
        } else if (!(obj instanceof ArrayList)) {
            return null;
        } else {
            ArrayList arrayList = (ArrayList) obj;
            return (Bundle[]) arrayList.toArray(new Bundle[arrayList.size()]);
        }
    }

    final Bundle zza(String str, String str2, Bundle bundle, @Nullable List<String> list, boolean z, boolean z2) {
        zzfu com_google_android_gms_measurement_internal_zzfu = this;
        Bundle bundle2 = bundle;
        List<String> list2 = list;
        String[] strArr = null;
        if (bundle2 == null) {
            return null;
        }
        Bundle bundle3 = new Bundle(bundle2);
        int i = 0;
        for (String str3 : bundle.keySet()) {
            int i2;
            Object obj;
            String str4;
            Object obj2;
            int i3;
            boolean z3;
            StringBuilder stringBuilder;
            String str5;
            boolean zza;
            int i4;
            if (list2 != null) {
                if (!list2.contains(str3)) {
                }
                i2 = 0;
                if (i2 == 0) {
                    if (zza(bundle3, i2)) {
                        bundle3.putString("_ev", zza(str3, 40, true));
                        if (i2 == 3) {
                            zza(bundle3, (Object) str3);
                        }
                    }
                    bundle3.remove(str3);
                } else {
                    obj = bundle2.get(str3);
                    zzaf();
                    if (z2) {
                        str4 = "param";
                        if (obj instanceof Parcelable[]) {
                            if (obj instanceof ArrayList) {
                                i2 = ((ArrayList) obj).size();
                            }
                            obj2 = 1;
                            if (obj2 == null) {
                                i3 = 17;
                                z3 = true;
                                if (i3 != 0 || "_ev".equals(str3)) {
                                    if (zzcv(str3)) {
                                        i++;
                                        if (i > 25) {
                                            stringBuilder = new StringBuilder(48);
                                            stringBuilder.append("Event can't contain more than 25 params");
                                            zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                            zza(bundle3, 5);
                                            bundle3.remove(str3);
                                        }
                                    }
                                    str5 = str2;
                                } else {
                                    if (zza(bundle3, i3)) {
                                        bundle3.putString("_ev", zza(str3, 40, z3));
                                        zza(bundle3, bundle2.get(str3));
                                    }
                                    bundle3.remove(str3);
                                }
                            }
                        } else {
                            i2 = ((Parcelable[]) obj).length;
                        }
                        if (i2 > 1000) {
                            zzgt().zzjj().zzd("Parameter array is too long; discarded. Value kind, name, array length", str4, str3, Integer.valueOf(i2));
                            obj2 = null;
                            if (obj2 == null) {
                                i3 = 17;
                                z3 = true;
                                if (i3 != 0) {
                                }
                                if (zzcv(str3)) {
                                    i++;
                                    if (i > 25) {
                                        stringBuilder = new StringBuilder(48);
                                        stringBuilder.append("Event can't contain more than 25 params");
                                        zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                        zza(bundle3, 5);
                                        bundle3.remove(str3);
                                    }
                                }
                                str5 = str2;
                            }
                        }
                        obj2 = 1;
                        if (obj2 == null) {
                            i3 = 17;
                            z3 = true;
                            if (i3 != 0) {
                            }
                            if (zzcv(str3)) {
                                i++;
                                if (i > 25) {
                                    stringBuilder = new StringBuilder(48);
                                    stringBuilder.append("Event can't contain more than 25 params");
                                    zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                    zza(bundle3, 5);
                                    bundle3.remove(str3);
                                }
                            }
                            str5 = str2;
                        }
                    }
                    if ((zzgv().zzax(str) || !zzda(str2)) && !zzda(str3)) {
                        z3 = true;
                        zza = zza("param", str3, 100, obj, z2);
                    } else {
                        z3 = true;
                        zza = zza("param", str3, 256, obj, z2);
                    }
                    i3 = zza ? 0 : 4;
                    if (i3 != 0) {
                    }
                    if (zzcv(str3)) {
                        i++;
                        if (i > 25) {
                            stringBuilder = new StringBuilder(48);
                            stringBuilder.append("Event can't contain more than 25 params");
                            zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                            zza(bundle3, 5);
                            bundle3.remove(str3);
                        }
                    }
                    str5 = str2;
                }
                strArr = null;
            }
            i2 = 14;
            if (z) {
                if (zzs("event param", str3)) {
                    if (!zza("event param", strArr, str3)) {
                        i4 = 14;
                        if (i4 == 0) {
                            if (zzt("event param", str3)) {
                                if (!zza("event param", strArr, str3)) {
                                    if (!zza("event param", 40, str3)) {
                                    }
                                    i2 = 0;
                                }
                            }
                            i2 = 3;
                        } else {
                            i2 = i4;
                        }
                        if (i2 == 0) {
                            obj = bundle2.get(str3);
                            zzaf();
                            if (z2) {
                                str4 = "param";
                                if (obj instanceof Parcelable[]) {
                                    if (obj instanceof ArrayList) {
                                        i2 = ((ArrayList) obj).size();
                                    }
                                    obj2 = 1;
                                    if (obj2 == null) {
                                        i3 = 17;
                                        z3 = true;
                                        if (i3 != 0) {
                                        }
                                        if (zzcv(str3)) {
                                            i++;
                                            if (i > 25) {
                                                stringBuilder = new StringBuilder(48);
                                                stringBuilder.append("Event can't contain more than 25 params");
                                                zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                                zza(bundle3, 5);
                                                bundle3.remove(str3);
                                            }
                                        }
                                        str5 = str2;
                                    }
                                } else {
                                    i2 = ((Parcelable[]) obj).length;
                                }
                                if (i2 > 1000) {
                                    zzgt().zzjj().zzd("Parameter array is too long; discarded. Value kind, name, array length", str4, str3, Integer.valueOf(i2));
                                    obj2 = null;
                                    if (obj2 == null) {
                                        i3 = 17;
                                        z3 = true;
                                        if (i3 != 0) {
                                        }
                                        if (zzcv(str3)) {
                                            i++;
                                            if (i > 25) {
                                                stringBuilder = new StringBuilder(48);
                                                stringBuilder.append("Event can't contain more than 25 params");
                                                zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                                zza(bundle3, 5);
                                                bundle3.remove(str3);
                                            }
                                        }
                                        str5 = str2;
                                    }
                                }
                                obj2 = 1;
                                if (obj2 == null) {
                                    i3 = 17;
                                    z3 = true;
                                    if (i3 != 0) {
                                    }
                                    if (zzcv(str3)) {
                                        i++;
                                        if (i > 25) {
                                            stringBuilder = new StringBuilder(48);
                                            stringBuilder.append("Event can't contain more than 25 params");
                                            zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                            zza(bundle3, 5);
                                            bundle3.remove(str3);
                                        }
                                    }
                                    str5 = str2;
                                }
                            }
                            if (zzgv().zzax(str)) {
                            }
                            z3 = true;
                            zza = zza("param", str3, 100, obj, z2);
                            if (zza) {
                            }
                            if (i3 != 0) {
                            }
                            if (zzcv(str3)) {
                                i++;
                                if (i > 25) {
                                    stringBuilder = new StringBuilder(48);
                                    stringBuilder.append("Event can't contain more than 25 params");
                                    zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                    zza(bundle3, 5);
                                    bundle3.remove(str3);
                                }
                            }
                            str5 = str2;
                        } else {
                            if (zza(bundle3, i2)) {
                                bundle3.putString("_ev", zza(str3, 40, true));
                                if (i2 == 3) {
                                    zza(bundle3, (Object) str3);
                                }
                            }
                            bundle3.remove(str3);
                        }
                        strArr = null;
                    } else if (!zza("event param", 40, str3)) {
                    }
                }
                i4 = 3;
                if (i4 == 0) {
                    i2 = i4;
                } else {
                    if (zzt("event param", str3)) {
                        if (!zza("event param", strArr, str3)) {
                            if (zza("event param", 40, str3)) {
                            }
                            i2 = 0;
                        }
                    }
                    i2 = 3;
                }
                if (i2 == 0) {
                    if (zza(bundle3, i2)) {
                        bundle3.putString("_ev", zza(str3, 40, true));
                        if (i2 == 3) {
                            zza(bundle3, (Object) str3);
                        }
                    }
                    bundle3.remove(str3);
                } else {
                    obj = bundle2.get(str3);
                    zzaf();
                    if (z2) {
                        str4 = "param";
                        if (obj instanceof Parcelable[]) {
                            i2 = ((Parcelable[]) obj).length;
                        } else {
                            if (obj instanceof ArrayList) {
                                i2 = ((ArrayList) obj).size();
                            }
                            obj2 = 1;
                            if (obj2 == null) {
                                i3 = 17;
                                z3 = true;
                                if (i3 != 0) {
                                }
                                if (zzcv(str3)) {
                                    i++;
                                    if (i > 25) {
                                        stringBuilder = new StringBuilder(48);
                                        stringBuilder.append("Event can't contain more than 25 params");
                                        zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                        zza(bundle3, 5);
                                        bundle3.remove(str3);
                                    }
                                }
                                str5 = str2;
                            }
                        }
                        if (i2 > 1000) {
                            zzgt().zzjj().zzd("Parameter array is too long; discarded. Value kind, name, array length", str4, str3, Integer.valueOf(i2));
                            obj2 = null;
                            if (obj2 == null) {
                                i3 = 17;
                                z3 = true;
                                if (i3 != 0) {
                                }
                                if (zzcv(str3)) {
                                    i++;
                                    if (i > 25) {
                                        stringBuilder = new StringBuilder(48);
                                        stringBuilder.append("Event can't contain more than 25 params");
                                        zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                        zza(bundle3, 5);
                                        bundle3.remove(str3);
                                    }
                                }
                                str5 = str2;
                            }
                        }
                        obj2 = 1;
                        if (obj2 == null) {
                            i3 = 17;
                            z3 = true;
                            if (i3 != 0) {
                            }
                            if (zzcv(str3)) {
                                i++;
                                if (i > 25) {
                                    stringBuilder = new StringBuilder(48);
                                    stringBuilder.append("Event can't contain more than 25 params");
                                    zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                    zza(bundle3, 5);
                                    bundle3.remove(str3);
                                }
                            }
                            str5 = str2;
                        }
                    }
                    if (zzgv().zzax(str)) {
                    }
                    z3 = true;
                    zza = zza("param", str3, 100, obj, z2);
                    if (zza) {
                    }
                    if (i3 != 0) {
                    }
                    if (zzcv(str3)) {
                        i++;
                        if (i > 25) {
                            stringBuilder = new StringBuilder(48);
                            stringBuilder.append("Event can't contain more than 25 params");
                            zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                            zza(bundle3, 5);
                            bundle3.remove(str3);
                        }
                    }
                    str5 = str2;
                }
                strArr = null;
            }
            i4 = 0;
            if (i4 == 0) {
                if (zzt("event param", str3)) {
                    if (!zza("event param", strArr, str3)) {
                        if (zza("event param", 40, str3)) {
                        }
                        i2 = 0;
                    }
                }
                i2 = 3;
            } else {
                i2 = i4;
            }
            if (i2 == 0) {
                obj = bundle2.get(str3);
                zzaf();
                if (z2) {
                    str4 = "param";
                    if (obj instanceof Parcelable[]) {
                        if (obj instanceof ArrayList) {
                            i2 = ((ArrayList) obj).size();
                        }
                        obj2 = 1;
                        if (obj2 == null) {
                            i3 = 17;
                            z3 = true;
                            if (i3 != 0) {
                            }
                            if (zzcv(str3)) {
                                i++;
                                if (i > 25) {
                                    stringBuilder = new StringBuilder(48);
                                    stringBuilder.append("Event can't contain more than 25 params");
                                    zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                    zza(bundle3, 5);
                                    bundle3.remove(str3);
                                }
                            }
                            str5 = str2;
                        }
                    } else {
                        i2 = ((Parcelable[]) obj).length;
                    }
                    if (i2 > 1000) {
                        zzgt().zzjj().zzd("Parameter array is too long; discarded. Value kind, name, array length", str4, str3, Integer.valueOf(i2));
                        obj2 = null;
                        if (obj2 == null) {
                            i3 = 17;
                            z3 = true;
                            if (i3 != 0) {
                            }
                            if (zzcv(str3)) {
                                i++;
                                if (i > 25) {
                                    stringBuilder = new StringBuilder(48);
                                    stringBuilder.append("Event can't contain more than 25 params");
                                    zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                    zza(bundle3, 5);
                                    bundle3.remove(str3);
                                }
                            }
                            str5 = str2;
                        }
                    }
                    obj2 = 1;
                    if (obj2 == null) {
                        i3 = 17;
                        z3 = true;
                        if (i3 != 0) {
                        }
                        if (zzcv(str3)) {
                            i++;
                            if (i > 25) {
                                stringBuilder = new StringBuilder(48);
                                stringBuilder.append("Event can't contain more than 25 params");
                                zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                                zza(bundle3, 5);
                                bundle3.remove(str3);
                            }
                        }
                        str5 = str2;
                    }
                }
                if (zzgv().zzax(str)) {
                }
                z3 = true;
                zza = zza("param", str3, 100, obj, z2);
                if (zza) {
                }
                if (i3 != 0) {
                }
                if (zzcv(str3)) {
                    i++;
                    if (i > 25) {
                        stringBuilder = new StringBuilder(48);
                        stringBuilder.append("Event can't contain more than 25 params");
                        zzgt().zzjg().zze(stringBuilder.toString(), zzgq().zzbv(str2), zzgq().zzd(bundle2));
                        zza(bundle3, 5);
                        bundle3.remove(str3);
                    }
                }
                str5 = str2;
            } else {
                if (zza(bundle3, i2)) {
                    bundle3.putString("_ev", zza(str3, 40, true));
                    if (i2 == 3) {
                        zza(bundle3, (Object) str3);
                    }
                }
                bundle3.remove(str3);
            }
            strArr = null;
        }
        return bundle3;
    }

    private static boolean zza(Bundle bundle, int i) {
        if (bundle.getLong("_err") != 0) {
            return null;
        }
        bundle.putLong("_err", (long) i);
        return true;
    }

    private static void zza(Bundle bundle, Object obj) {
        Preconditions.checkNotNull(bundle);
        if (obj == null) {
            return;
        }
        if ((obj instanceof String) || (obj instanceof CharSequence)) {
            bundle.putLong("_el", (long) String.valueOf(obj).length());
        }
    }

    private static int zzcz(String str) {
        if ("_ldl".equals(str)) {
            return 2048;
        }
        return "_id".equals(str) != null ? 256 : 36;
    }

    final int zzi(String str, Object obj) {
        if ("_ldl".equals(str)) {
            str = zza("user property referrer", str, zzcz(str), obj, false);
        } else {
            str = zza("user property", str, zzcz(str), obj, false);
        }
        return str != null ? null : 7;
    }

    final Object zzj(String str, Object obj) {
        if ("_ldl".equals(str)) {
            return zza(zzcz(str), obj, true);
        }
        return zza(zzcz(str), obj, false);
    }

    final void zza(Bundle bundle, String str, Object obj) {
        if (bundle != null) {
            if (obj instanceof Long) {
                bundle.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof String) {
                bundle.putString(str, String.valueOf(obj));
            } else if (obj instanceof Double) {
                bundle.putDouble(str, ((Double) obj).doubleValue());
            } else {
                if (str != null) {
                    zzgt().zzjl().zze("Not putting event parameter. Invalid value type. name, type", zzgq().zzbw(str), obj != null ? obj.getClass().getSimpleName() : null);
                }
            }
        }
    }

    public final void zza(int i, String str, String str2, int i2) {
        zza(null, i, str, str2, i2);
    }

    final void zza(String str, int i, String str2, String str3, int i2) {
        Bundle bundle = new Bundle();
        zza(bundle, i);
        if (!TextUtils.isEmpty(str2)) {
            bundle.putString(str2, str3);
        }
        if (i == 6 || i == 7 || i == 2) {
            bundle.putLong("_el", (long) i2);
        }
        this.zzadp.zzgw();
        this.zzadp.zzgj().logEvent("auto", "_err", bundle);
    }

    static java.security.MessageDigest getMessageDigest() {
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
        r0 = 0;
    L_0x0001:
        r1 = 2;
        if (r0 >= r1) goto L_0x0010;
    L_0x0004:
        r1 = "MD5";	 Catch:{ NoSuchAlgorithmException -> 0x000d }
        r1 = java.security.MessageDigest.getInstance(r1);	 Catch:{ NoSuchAlgorithmException -> 0x000d }
        if (r1 == 0) goto L_0x000d;
    L_0x000c:
        return r1;
    L_0x000d:
        r0 = r0 + 1;
        goto L_0x0001;
    L_0x0010:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfu.getMessageDigest():java.security.MessageDigest");
    }

    @VisibleForTesting
    static long zzc(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        long j = null;
        Preconditions.checkState(bArr.length > 0);
        long j2 = 0;
        int length = bArr.length - 1;
        while (length >= 0 && length >= bArr.length - 8) {
            j2 += (((long) bArr[length]) & 255) << j;
            j += 8;
            length--;
        }
        return j2;
    }

    static boolean zza(Context context, boolean z) {
        Preconditions.checkNotNull(context);
        if (VERSION.SDK_INT >= true) {
            return zzc(context, "com.google.android.gms.measurement.AppMeasurementJobService");
        }
        return zzc(context, "com.google.android.gms.measurement.AppMeasurementService");
    }

    private static boolean zzc(android.content.Context r3, java.lang.String r4) {
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
        r0 = 0;
        r1 = r3.getPackageManager();	 Catch:{ NameNotFoundException -> 0x0019 }
        if (r1 != 0) goto L_0x0008;	 Catch:{ NameNotFoundException -> 0x0019 }
    L_0x0007:
        return r0;	 Catch:{ NameNotFoundException -> 0x0019 }
    L_0x0008:
        r2 = new android.content.ComponentName;	 Catch:{ NameNotFoundException -> 0x0019 }
        r2.<init>(r3, r4);	 Catch:{ NameNotFoundException -> 0x0019 }
        r3 = r1.getServiceInfo(r2, r0);	 Catch:{ NameNotFoundException -> 0x0019 }
        if (r3 == 0) goto L_0x0019;	 Catch:{ NameNotFoundException -> 0x0019 }
    L_0x0013:
        r3 = r3.enabled;	 Catch:{ NameNotFoundException -> 0x0019 }
        if (r3 == 0) goto L_0x0019;
    L_0x0017:
        r3 = 1;
        return r3;
    L_0x0019:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfu.zzc(android.content.Context, java.lang.String):boolean");
    }

    @WorkerThread
    final boolean zzx(String str) {
        zzaf();
        if (Wrappers.packageManager(getContext()).checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        zzgt().zzjn().zzg("Permission not granted", str);
        return null;
    }

    static boolean zzda(String str) {
        return (TextUtils.isEmpty(str) || str.startsWith(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR) == null) ? null : true;
    }

    static boolean zzv(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        return str == null ? null : str.equals(str2);
    }

    final boolean zzdb(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String zzid = zzgv().zzid();
        zzgw();
        return zzid.equals(str);
    }

    final Bundle zze(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                Object zzh = zzh(str, bundle.get(str));
                if (zzh == null) {
                    zzgt().zzjj().zzg("Param value can't be null", zzgq().zzbw(str));
                } else {
                    zza(bundle2, str, zzh);
                }
            }
        }
        return bundle2;
    }

    final zzae zza(String str, String str2, Bundle bundle, String str3, long j, boolean z, boolean z2) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        if (zzcw(str2)) {
            zzgt().zzjg().zzg("Invalid conditional property event name", zzgq().zzbx(str2));
            throw new IllegalArgumentException();
        }
        Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
        bundle2.putString("_o", str3);
        return new zzae(str2, new zzab(zze(zza(str, str2, bundle2, CollectionUtils.listOf((Object) "_o"), false, false))), str3, j);
    }

    @WorkerThread
    final long zzd(Context context, String str) {
        zzaf();
        Preconditions.checkNotNull(context);
        Preconditions.checkNotEmpty(str);
        PackageManager packageManager = context.getPackageManager();
        MessageDigest messageDigest = getMessageDigest();
        if (messageDigest == null) {
            zzgt().zzjg().zzca("Could not get MD5 instance");
            return -1;
        }
        if (packageManager != null) {
            try {
                if (zze(context, str) == null) {
                    context = Wrappers.packageManager(context).getPackageInfo(getContext().getPackageName(), 64);
                    if (context.signatures != null && context.signatures.length > null) {
                        return zzc(messageDigest.digest(context.signatures[null].toByteArray()));
                    }
                    zzgt().zzjj().zzca("Could not get signatures");
                    return -1;
                }
            } catch (Context context2) {
                zzgt().zzjg().zzg("Package name not found", context2);
            }
        }
        return 0;
    }

    @VisibleForTesting
    private final boolean zze(Context context, String str) {
        X500Principal x500Principal = new X500Principal("CN=Android Debug,O=Android,C=US");
        try {
            context = Wrappers.packageManager(context).getPackageInfo(str, 64);
            if (!(context == null || context.signatures == null || context.signatures.length <= null)) {
                return ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(context.signatures[null].toByteArray()))).getSubjectX500Principal().equals(x500Principal);
            }
        } catch (Context context2) {
            zzgt().zzjg().zzg("Error obtaining certificate", context2);
        } catch (Context context22) {
            zzgt().zzjg().zzg("Package name not found", context22);
        }
        return true;
    }

    static byte[] zza(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        try {
            parcelable.writeToParcel(obtain, 0);
            parcelable = obtain.marshall();
            return parcelable;
        } finally {
            obtain.recycle();
        }
    }

    public static Bundle zzf(Bundle bundle) {
        if (bundle == null) {
            return new Bundle();
        }
        Bundle bundle2 = new Bundle(bundle);
        for (String str : bundle2.keySet()) {
            Object obj = bundle2.get(str);
            if (obj instanceof Bundle) {
                bundle2.putBundle(str, new Bundle((Bundle) obj));
            } else {
                int i = 0;
                if (obj instanceof Parcelable[]) {
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    while (i < parcelableArr.length) {
                        if (parcelableArr[i] instanceof Bundle) {
                            parcelableArr[i] = new Bundle((Bundle) parcelableArr[i]);
                        }
                        i++;
                    }
                } else if (obj instanceof List) {
                    List list = (List) obj;
                    while (i < list.size()) {
                        Object obj2 = list.get(i);
                        if (obj2 instanceof Bundle) {
                            list.set(i, new Bundle((Bundle) obj2));
                        }
                        i++;
                    }
                }
            }
        }
        return bundle2;
    }

    public final int zzml() {
        if (this.zzave == null) {
            this.zzave = Integer.valueOf(GoogleApiAvailabilityLight.getInstance().getApkVersion(getContext()) / 1000);
        }
        return this.zzave.intValue();
    }

    public static long zzc(long j, long j2) {
        return (j + (j2 * DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS)) / 86400000;
    }

    @WorkerThread
    final String zzmm() {
        zzmk().nextBytes(new byte[16]);
        return String.format(Locale.US, "%032x", new Object[]{new BigInteger(1, r0)});
    }

    @WorkerThread
    final void zza(Bundle bundle, long j) {
        long j2 = bundle.getLong("_et");
        if (j2 != 0) {
            zzgt().zzjj().zzg("Params already contained engagement", Long.valueOf(j2));
        }
        bundle.putLong("_et", j + j2);
    }

    public final void zzb(zzef com_google_android_gms_internal_measurement_zzef, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("r", str);
        try {
            com_google_android_gms_internal_measurement_zzef.zzb(bundle);
        } catch (zzef com_google_android_gms_internal_measurement_zzef2) {
            this.zzadp.zzgt().zzjj().zzg("Error returning string value to wrapper", com_google_android_gms_internal_measurement_zzef2);
        }
    }

    public final /* bridge */ /* synthetic */ void zzgf() {
        super.zzgf();
    }

    public final /* bridge */ /* synthetic */ void zzgg() {
        super.zzgg();
    }

    public final /* bridge */ /* synthetic */ void zzgh() {
        super.zzgh();
    }

    public final /* bridge */ /* synthetic */ void zzaf() {
        super.zzaf();
    }

    public final /* bridge */ /* synthetic */ zzy zzgp() {
        return super.zzgp();
    }

    public final /* bridge */ /* synthetic */ Clock zzbx() {
        return super.zzbx();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final /* bridge */ /* synthetic */ zzao zzgq() {
        return super.zzgq();
    }

    public final /* bridge */ /* synthetic */ zzfu zzgr() {
        return super.zzgr();
    }

    public final /* bridge */ /* synthetic */ zzbp zzgs() {
        return super.zzgs();
    }

    public final /* bridge */ /* synthetic */ zzaq zzgt() {
        return super.zzgt();
    }

    public final /* bridge */ /* synthetic */ zzbb zzgu() {
        return super.zzgu();
    }

    public final /* bridge */ /* synthetic */ zzo zzgv() {
        return super.zzgv();
    }

    public final /* bridge */ /* synthetic */ zzl zzgw() {
        return super.zzgw();
    }
}
