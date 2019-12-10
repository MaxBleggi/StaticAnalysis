package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzfy;
import com.google.android.gms.internal.measurement.zzfz;
import com.google.android.gms.internal.measurement.zzgb;
import com.google.android.gms.internal.measurement.zzgc;
import com.google.android.gms.internal.measurement.zzgg;
import com.google.android.gms.internal.measurement.zzgh;
import com.google.android.gms.internal.measurement.zzgi;
import com.google.android.gms.internal.measurement.zzgj;
import com.google.android.gms.internal.measurement.zzgm;
import com.google.android.gms.internal.measurement.zzgn;
import com.google.android.gms.internal.measurement.zzgo;
import com.google.android.gms.internal.measurement.zzzj;
import com.google.android.gms.internal.measurement.zzzr;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

final class zzk extends zzfj {
    zzk(zzfk com_google_android_gms_measurement_internal_zzfk) {
        super(com_google_android_gms_measurement_internal_zzfk);
    }

    protected final boolean zzgy() {
        return false;
    }

    @WorkerThread
    final zzgg[] zza(String str, zzgi[] com_google_android_gms_internal_measurement_zzgiArr, zzgo[] com_google_android_gms_internal_measurement_zzgoArr) {
        int intValue;
        int length;
        HashSet hashSet;
        int i;
        Map map;
        Map map2;
        Map map3;
        zzgg com_google_android_gms_internal_measurement_zzgg;
        int length2;
        String str2;
        Long l;
        Object e;
        zzgi com_google_android_gms_internal_measurement_zzgi;
        Map map4;
        zzgi com_google_android_gms_internal_measurement_zzgi2;
        boolean z;
        zzgj[] com_google_android_gms_internal_measurement_zzgjArr;
        zzgj[] com_google_android_gms_internal_measurement_zzgjArr2;
        int i2;
        zzgi com_google_android_gms_internal_measurement_zzgi3;
        String str3;
        zzgj[] com_google_android_gms_internal_measurement_zzgjArr3;
        Long l2;
        zzaa zzg;
        ArrayMap arrayMap;
        Map map5;
        HashSet hashSet2;
        String str4;
        zzaa com_google_android_gms_measurement_internal_zzaa;
        long j;
        Map map6;
        Map map7;
        Iterator it;
        HashSet hashSet3;
        Map map8;
        BitSet bitSet;
        BitSet bitSet2;
        Map map9;
        BitSet bitSet3;
        Map map10;
        ArrayMap arrayMap2;
        ArrayMap arrayMap3;
        BitSet bitSet4;
        ArrayMap arrayMap4;
        ArrayMap arrayMap5;
        BitSet bitSet5;
        Map map11;
        Map map12;
        Map map13;
        String str5;
        Map map14;
        Map map15;
        zzgi com_google_android_gms_internal_measurement_zzgi4;
        BitSet bitSet6;
        Map map16;
        Boolean zza;
        Object obj;
        zzfy com_google_android_gms_internal_measurement_zzfy;
        zzgo[] com_google_android_gms_internal_measurement_zzgoArr2;
        ArrayMap arrayMap6;
        Map map17;
        Iterator it2;
        Map map18;
        Map map19;
        Map map20;
        Map map21;
        Map map22;
        Iterator it3;
        Map map23;
        zzk com_google_android_gms_measurement_internal_zzk = this;
        String str6 = str;
        zzgi[] com_google_android_gms_internal_measurement_zzgiArr2 = com_google_android_gms_internal_measurement_zzgiArr;
        zzgo[] com_google_android_gms_internal_measurement_zzgoArr3 = com_google_android_gms_internal_measurement_zzgoArr;
        Preconditions.checkNotEmpty(str);
        HashSet hashSet4 = new HashSet();
        Map arrayMap7 = new ArrayMap();
        Map arrayMap8 = new ArrayMap();
        Map arrayMap9 = new ArrayMap();
        Map arrayMap10 = new ArrayMap();
        Map arrayMap11 = new ArrayMap();
        boolean zzbd = zzgv().zzbd(str6);
        Map zzbr = zzjt().zzbr(str6);
        if (zzbr != null) {
            Iterator it4 = zzbr.keySet().iterator();
            while (it4.hasNext()) {
                Map map24;
                Iterator it5;
                BitSet bitSet7;
                intValue = ((Integer) it4.next()).intValue();
                zzgm com_google_android_gms_internal_measurement_zzgm = (zzgm) zzbr.get(Integer.valueOf(intValue));
                BitSet bitSet8 = (BitSet) arrayMap8.get(Integer.valueOf(intValue));
                BitSet bitSet9 = (BitSet) arrayMap9.get(Integer.valueOf(intValue));
                if (zzbd) {
                    map24 = zzbr;
                    zzbr = new ArrayMap();
                    if (com_google_android_gms_internal_measurement_zzgm != null) {
                        it5 = it4;
                        if (com_google_android_gms_internal_measurement_zzgm.zzazb != null) {
                            zzgh[] com_google_android_gms_internal_measurement_zzghArr = com_google_android_gms_internal_measurement_zzgm.zzazb;
                            bitSet7 = bitSet9;
                            length = com_google_android_gms_internal_measurement_zzghArr.length;
                            hashSet = hashSet4;
                            int i3 = 0;
                            while (i3 < length) {
                                int i4 = length;
                                zzgh com_google_android_gms_internal_measurement_zzgh = com_google_android_gms_internal_measurement_zzghArr[i3];
                                zzgh[] com_google_android_gms_internal_measurement_zzghArr2 = com_google_android_gms_internal_measurement_zzghArr;
                                if (com_google_android_gms_internal_measurement_zzgh.zzaxj != null) {
                                    zzbr.put(com_google_android_gms_internal_measurement_zzgh.zzaxj, com_google_android_gms_internal_measurement_zzgh.zzaxk);
                                }
                                i3++;
                                length = i4;
                                com_google_android_gms_internal_measurement_zzghArr = com_google_android_gms_internal_measurement_zzghArr2;
                            }
                            arrayMap10.put(Integer.valueOf(intValue), zzbr);
                        }
                    } else {
                        it5 = it4;
                    }
                    bitSet7 = bitSet9;
                    hashSet = hashSet4;
                    arrayMap10.put(Integer.valueOf(intValue), zzbr);
                } else {
                    map24 = zzbr;
                    it5 = it4;
                    bitSet7 = bitSet9;
                    hashSet = hashSet4;
                    zzbr = null;
                }
                if (bitSet8 == null) {
                    bitSet8 = new BitSet();
                    arrayMap8.put(Integer.valueOf(intValue), bitSet8);
                    bitSet9 = new BitSet();
                    arrayMap9.put(Integer.valueOf(intValue), bitSet9);
                } else {
                    bitSet9 = bitSet7;
                }
                i = 0;
                while (i < (com_google_android_gms_internal_measurement_zzgm.zzayz.length << 6)) {
                    Object obj2;
                    if (zzfq.zza(com_google_android_gms_internal_measurement_zzgm.zzayz, i)) {
                        map = arrayMap10;
                        map2 = arrayMap9;
                        map3 = arrayMap8;
                        zzgt().zzjo().zze("Filter already evaluated. audience ID, filter ID", Integer.valueOf(intValue), Integer.valueOf(i));
                        bitSet9.set(i);
                        if (zzfq.zza(com_google_android_gms_internal_measurement_zzgm.zzaza, i)) {
                            bitSet8.set(i);
                            obj2 = 1;
                            if (zzbr != null && r8 == null) {
                                zzbr.remove(Integer.valueOf(i));
                            }
                            i++;
                            arrayMap10 = map;
                            arrayMap9 = map2;
                            arrayMap8 = map3;
                        }
                    } else {
                        map = arrayMap10;
                        map2 = arrayMap9;
                        map3 = arrayMap8;
                    }
                    obj2 = null;
                    zzbr.remove(Integer.valueOf(i));
                    i++;
                    arrayMap10 = map;
                    arrayMap9 = map2;
                    arrayMap8 = map3;
                }
                map = arrayMap10;
                map2 = arrayMap9;
                map3 = arrayMap8;
                com_google_android_gms_internal_measurement_zzgg = new zzgg();
                arrayMap7.put(Integer.valueOf(intValue), com_google_android_gms_internal_measurement_zzgg);
                com_google_android_gms_internal_measurement_zzgg.zzaxh = Boolean.valueOf(false);
                com_google_android_gms_internal_measurement_zzgg.zzaxg = com_google_android_gms_internal_measurement_zzgm;
                com_google_android_gms_internal_measurement_zzgg.zzaxf = new zzgm();
                com_google_android_gms_internal_measurement_zzgg.zzaxf.zzaza = zzfq.zza(bitSet8);
                com_google_android_gms_internal_measurement_zzgg.zzaxf.zzayz = zzfq.zza(bitSet9);
                if (zzbd) {
                    com_google_android_gms_internal_measurement_zzgg.zzaxf.zzazb = zzd(zzbr);
                    arrayMap11.put(Integer.valueOf(intValue), new ArrayMap());
                }
                zzbr = map24;
                it4 = it5;
                hashSet4 = hashSet;
                arrayMap10 = map;
                arrayMap9 = map2;
                arrayMap8 = map3;
            }
        }
        map = arrayMap10;
        map2 = arrayMap9;
        map3 = arrayMap8;
        hashSet = hashSet4;
        if (com_google_android_gms_internal_measurement_zzgiArr2 != null) {
            ArrayMap arrayMap12 = new ArrayMap();
            length2 = com_google_android_gms_internal_measurement_zzgiArr2.length;
            long j2 = 0;
            zzgi com_google_android_gms_internal_measurement_zzgi5 = null;
            Long l3 = null;
            int i5 = 0;
            while (i5 < length2) {
                int i6;
                Map map25;
                Map map26;
                Map map27;
                zzgi com_google_android_gms_internal_measurement_zzgi6;
                zzgo[] com_google_android_gms_internal_measurement_zzgoArr4;
                int intValue2;
                zzgi com_google_android_gms_internal_measurement_zzgi7;
                Map map28;
                BitSet bitSet10;
                Iterator it6;
                Map map29;
                Map map30;
                Map map31;
                zzfy com_google_android_gms_internal_measurement_zzfy2;
                BitSet bitSet11;
                Object obj3;
                ArrayMap arrayMap13;
                zzgi com_google_android_gms_internal_measurement_zzgi8 = com_google_android_gms_internal_measurement_zzgiArr2[i5];
                str2 = com_google_android_gms_internal_measurement_zzgi8.name;
                zzgj[] com_google_android_gms_internal_measurement_zzgjArr4 = com_google_android_gms_internal_measurement_zzgi8.zzaxm;
                long j3 = j2;
                if (zzgv().zzd(str6, zzag.zzaku)) {
                    int i7;
                    Object obj4;
                    zzgi com_google_android_gms_internal_measurement_zzgi9;
                    long j4;
                    long j5;
                    zzcp zzjt;
                    SQLiteDatabase writableDatabase;
                    String str7;
                    zzgi com_google_android_gms_internal_measurement_zzgi10;
                    String[] strArr;
                    zzgj com_google_android_gms_internal_measurement_zzgj;
                    zzgi com_google_android_gms_internal_measurement_zzgi11;
                    Pair zza2;
                    long longValue;
                    Long l4;
                    zzjr();
                    Long l5 = (Long) zzfq.zzb(com_google_android_gms_internal_measurement_zzgi8, "_eid");
                    Object obj5 = l5 != null ? 1 : null;
                    if (obj5 != null) {
                        i7 = i5;
                        if (str2.equals("_ep")) {
                            obj4 = 1;
                            if (obj4 == null) {
                                zzjr();
                                str2 = (String) zzfq.zzb(com_google_android_gms_internal_measurement_zzgi8, "_en");
                                if (!TextUtils.isEmpty(str2)) {
                                    if (!(com_google_android_gms_internal_measurement_zzgi5 == null || l3 == null)) {
                                        if (l5.longValue() != l3.longValue()) {
                                            com_google_android_gms_internal_measurement_zzgi9 = com_google_android_gms_internal_measurement_zzgi5;
                                            l = l3;
                                            j4 = j3;
                                            j5 = j4 - 1;
                                            if (j5 > 0) {
                                                zzjt = zzjt();
                                                zzjt.zzaf();
                                                zzjt.zzgt().zzjo().zzg("Clearing complex main event info. appId", str6);
                                                try {
                                                    writableDatabase = zzjt.getWritableDatabase();
                                                    str7 = "delete from main_event_params where app_id=?";
                                                    com_google_android_gms_internal_measurement_zzgi10 = com_google_android_gms_internal_measurement_zzgi9;
                                                    try {
                                                        strArr = new String[1];
                                                        try {
                                                            strArr[0] = str6;
                                                            writableDatabase.execSQL(str7, strArr);
                                                        } catch (SQLiteException e2) {
                                                            e = e2;
                                                            zzjt.zzgt().zzjg().zzg("Error clearing complex main event", e);
                                                            com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                                                            map4 = arrayMap11;
                                                            i6 = i7;
                                                            com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi10;
                                                            z = true;
                                                            com_google_android_gms_internal_measurement_zzgjArr = new zzgj[(com_google_android_gms_internal_measurement_zzgi2.zzaxm.length + com_google_android_gms_internal_measurement_zzgjArr4.length)];
                                                            com_google_android_gms_internal_measurement_zzgjArr2 = com_google_android_gms_internal_measurement_zzgi2.zzaxm;
                                                            length = com_google_android_gms_internal_measurement_zzgjArr2.length;
                                                            i5 = 0;
                                                            i2 = 0;
                                                            while (i5 < length) {
                                                                com_google_android_gms_internal_measurement_zzgj = com_google_android_gms_internal_measurement_zzgjArr2[i5];
                                                                zzjr();
                                                                com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                                                if (zzfq.zza(com_google_android_gms_internal_measurement_zzgi, com_google_android_gms_internal_measurement_zzgj.name) != null) {
                                                                    i = i2 + 1;
                                                                    com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgj;
                                                                    i2 = i;
                                                                }
                                                                i5++;
                                                                com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi3;
                                                            }
                                                            com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                                            if (i2 > 0) {
                                                                zzgt().zzjj().zzg("No unique parameters in main event. eventName", str2);
                                                                str3 = str2;
                                                                com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr4;
                                                            } else {
                                                                i = com_google_android_gms_internal_measurement_zzgjArr4.length;
                                                                intValue = 0;
                                                                while (intValue < i) {
                                                                    i5 = i2 + 1;
                                                                    com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgjArr4[intValue];
                                                                    intValue++;
                                                                    i2 = i5;
                                                                }
                                                                if (i2 == com_google_android_gms_internal_measurement_zzgjArr.length) {
                                                                    com_google_android_gms_internal_measurement_zzgjArr = (zzgj[]) Arrays.copyOf(com_google_android_gms_internal_measurement_zzgjArr, i2);
                                                                }
                                                                com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr;
                                                                str3 = str2;
                                                            }
                                                            l2 = l;
                                                            j3 = j5;
                                                            zzg = zzjt().zzg(str6, com_google_android_gms_internal_measurement_zzgi.name);
                                                            if (zzg != null) {
                                                                zzgt().zzjj().zze("Event aggregate wasn't created during raw event logging. appId, event", zzaq.zzby(str), zzgq().zzbv(str3));
                                                                i4 = length2;
                                                                arrayMap = arrayMap12;
                                                                map25 = map2;
                                                                map26 = map3;
                                                                map5 = map;
                                                                map27 = arrayMap7;
                                                                hashSet2 = hashSet;
                                                                com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi;
                                                                com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr3;
                                                                str4 = str6;
                                                                com_google_android_gms_measurement_internal_zzaa = new zzaa(str, com_google_android_gms_internal_measurement_zzgi.name, 1, 1, com_google_android_gms_internal_measurement_zzgi.zzaxn.longValue(), 0, null, null, null, null);
                                                            } else {
                                                                i4 = length2;
                                                                arrayMap = arrayMap12;
                                                                map27 = arrayMap7;
                                                                com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi;
                                                                com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr3;
                                                                str4 = str6;
                                                                hashSet2 = hashSet;
                                                                map5 = map;
                                                                map25 = map2;
                                                                map26 = map3;
                                                                zzg = new zzaa(zzg.zztt, zzg.name, zzg.zzaih + 1, zzg.zzaii + 1, zzg.zzaij, zzg.zzaik, zzg.zzail, zzg.zzaim, zzg.zzain, zzg.zzaio);
                                                            }
                                                            zzjt().zza(zzg);
                                                            j = zzg.zzaih;
                                                            arrayMap8 = arrayMap;
                                                            map6 = (Map) arrayMap8.get(str3);
                                                            if (map6 == null) {
                                                                map6 = zzjt().zzl(str4, str3);
                                                                if (map6 == null) {
                                                                    map6 = new ArrayMap();
                                                                }
                                                                arrayMap8.put(str3, map6);
                                                            }
                                                            map7 = map6;
                                                            it = map7.keySet().iterator();
                                                            while (it.hasNext()) {
                                                                intValue2 = ((Integer) it.next()).intValue();
                                                                hashSet3 = hashSet2;
                                                                if (hashSet3.contains(Integer.valueOf(intValue2))) {
                                                                    map8 = map27;
                                                                    com_google_android_gms_internal_measurement_zzgg = (zzgg) map8.get(Integer.valueOf(intValue2));
                                                                    arrayMap11 = map26;
                                                                    bitSet = (BitSet) arrayMap11.get(Integer.valueOf(intValue2));
                                                                    com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi6;
                                                                    map28 = arrayMap8;
                                                                    arrayMap8 = map25;
                                                                    bitSet2 = (BitSet) arrayMap8.get(Integer.valueOf(intValue2));
                                                                    if (zzbd) {
                                                                        bitSet10 = bitSet2;
                                                                        it6 = it;
                                                                        map29 = map4;
                                                                        arrayMap7 = map5;
                                                                        map9 = null;
                                                                        map30 = null;
                                                                    } else {
                                                                        bitSet10 = bitSet2;
                                                                        it6 = it;
                                                                        arrayMap7 = map5;
                                                                        map30 = (Map) arrayMap7.get(Integer.valueOf(intValue2));
                                                                        map29 = map4;
                                                                        map9 = (Map) map29.get(Integer.valueOf(intValue2));
                                                                    }
                                                                    if (com_google_android_gms_internal_measurement_zzgg != null) {
                                                                        com_google_android_gms_internal_measurement_zzgg = new zzgg();
                                                                        map8.put(Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzgg);
                                                                        com_google_android_gms_internal_measurement_zzgg.zzaxh = Boolean.valueOf(z);
                                                                        bitSet = new BitSet();
                                                                        arrayMap11.put(Integer.valueOf(intValue2), bitSet);
                                                                        bitSet3 = new BitSet();
                                                                        map31 = map9;
                                                                        arrayMap8.put(Integer.valueOf(intValue2), bitSet3);
                                                                        if (zzbd) {
                                                                            map10 = map29;
                                                                            arrayMap2 = map30;
                                                                            map29 = map31;
                                                                            bitSet2 = bitSet3;
                                                                        } else {
                                                                            arrayMap3 = new ArrayMap();
                                                                            bitSet4 = bitSet3;
                                                                            arrayMap7.put(Integer.valueOf(intValue2), arrayMap3);
                                                                            arrayMap4 = new ArrayMap();
                                                                            arrayMap5 = arrayMap3;
                                                                            map29.put(Integer.valueOf(intValue2), arrayMap4);
                                                                            map10 = map29;
                                                                            bitSet2 = bitSet4;
                                                                            arrayMap2 = arrayMap5;
                                                                            map29 = arrayMap4;
                                                                        }
                                                                    } else {
                                                                        map31 = map9;
                                                                        map10 = map29;
                                                                        bitSet2 = bitSet10;
                                                                        arrayMap2 = map30;
                                                                        map29 = map31;
                                                                    }
                                                                    for (zzfy com_google_android_gms_internal_measurement_zzfy3 : (List) r11.get(Integer.valueOf(intValue2))) {
                                                                        bitSet5 = bitSet2;
                                                                        map11 = map7;
                                                                        if (zzgt().isLoggable(2)) {
                                                                            map12 = arrayMap11;
                                                                            map13 = arrayMap7;
                                                                        } else {
                                                                            map12 = arrayMap11;
                                                                            map13 = arrayMap7;
                                                                            zzgt().zzjo().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy3.zzavx, zzgq().zzbv(com_google_android_gms_internal_measurement_zzfy3.zzavy));
                                                                            zzgt().zzjo().zzg("Filter definition", zzjr().zza(com_google_android_gms_internal_measurement_zzfy3));
                                                                        }
                                                                        if (com_google_android_gms_internal_measurement_zzfy3.zzavx != null) {
                                                                            if (com_google_android_gms_internal_measurement_zzfy3.zzavx.intValue() > 256) {
                                                                                if (zzbd) {
                                                                                    str5 = str3;
                                                                                    com_google_android_gms_internal_measurement_zzfy2 = com_google_android_gms_internal_measurement_zzfy3;
                                                                                    map14 = arrayMap8;
                                                                                    map15 = map8;
                                                                                    com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                                                                    arrayMap7 = arrayMap2;
                                                                                    bitSet6 = bitSet5;
                                                                                    map16 = map12;
                                                                                    bitSet11 = bitSet;
                                                                                    if (bitSet11.get(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue())) {
                                                                                        zza = zza(com_google_android_gms_internal_measurement_zzfy2, str5, com_google_android_gms_internal_measurement_zzgjArr3, j);
                                                                                        zzgt().zzjo().zzg("Event filter result", zza != null ? "null" : zza);
                                                                                        if (zza != null) {
                                                                                            hashSet3.add(Integer.valueOf(intValue2));
                                                                                        } else {
                                                                                            bitSet6.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                                            if (zza.booleanValue()) {
                                                                                                bitSet11.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                                            }
                                                                                        }
                                                                                    } else {
                                                                                        zzgt().zzjo().zze("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy2.zzavx);
                                                                                    }
                                                                                } else {
                                                                                    if (com_google_android_gms_internal_measurement_zzfy3 == null) {
                                                                                    }
                                                                                    if (com_google_android_gms_internal_measurement_zzfy3 == null) {
                                                                                    }
                                                                                    if (!bitSet.get(com_google_android_gms_internal_measurement_zzfy3.zzavx.intValue())) {
                                                                                    }
                                                                                    com_google_android_gms_internal_measurement_zzfy = com_google_android_gms_internal_measurement_zzfy3;
                                                                                    bitSet6 = bitSet5;
                                                                                    map14 = arrayMap8;
                                                                                    map15 = map8;
                                                                                    com_google_android_gms_internal_measurement_zzgoArr2 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                                    com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                                                                    bitSet11 = bitSet;
                                                                                    str5 = str3;
                                                                                    com_google_android_gms_internal_measurement_zzfy2 = com_google_android_gms_internal_measurement_zzfy;
                                                                                    arrayMap6 = arrayMap2;
                                                                                    map16 = map12;
                                                                                    zza = zza(com_google_android_gms_internal_measurement_zzfy, str3, com_google_android_gms_internal_measurement_zzgjArr3, j);
                                                                                    zzgt().zzjo().zzg("Event filter result", zza != null ? "null" : zza);
                                                                                    if (zza != null) {
                                                                                        hashSet3.add(Integer.valueOf(intValue2));
                                                                                    } else {
                                                                                        bitSet6.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                                        if (zza.booleanValue()) {
                                                                                            bitSet11.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                                            if (obj3 == null) {
                                                                                                zzb(map29, com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgi4.zzaxn.longValue());
                                                                                            } else {
                                                                                                arrayMap7 = arrayMap6;
                                                                                                zza(arrayMap7, com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgi4.zzaxn.longValue());
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    bitSet = bitSet11;
                                                                                    bitSet2 = bitSet6;
                                                                                    com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                                                                    map7 = map11;
                                                                                    arrayMap7 = map13;
                                                                                    arrayMap8 = map14;
                                                                                    map8 = map15;
                                                                                    arrayMap11 = map16;
                                                                                    str3 = str5;
                                                                                    arrayMap2 = arrayMap6;
                                                                                    com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                                    str4 = str;
                                                                                }
                                                                                bitSet = bitSet11;
                                                                                bitSet2 = bitSet6;
                                                                                arrayMap2 = arrayMap7;
                                                                                com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                                                                map7 = map11;
                                                                                arrayMap7 = map13;
                                                                                arrayMap8 = map14;
                                                                                map8 = map15;
                                                                                arrayMap11 = map16;
                                                                                str3 = str5;
                                                                                com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                                str4 = str;
                                                                            }
                                                                        }
                                                                        str5 = str3;
                                                                        map14 = arrayMap8;
                                                                        map15 = map8;
                                                                        com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                                                        arrayMap7 = arrayMap2;
                                                                        bitSet6 = bitSet5;
                                                                        map16 = map12;
                                                                        bitSet11 = bitSet;
                                                                        zzgt().zzjj().zze("Invalid event filter ID. appId, id", zzaq.zzby(str), String.valueOf(com_google_android_gms_internal_measurement_zzfy3.zzavx));
                                                                        bitSet = bitSet11;
                                                                        bitSet2 = bitSet6;
                                                                        arrayMap2 = arrayMap7;
                                                                        com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                                                        map7 = map11;
                                                                        arrayMap7 = map13;
                                                                        arrayMap8 = map14;
                                                                        map8 = map15;
                                                                        arrayMap11 = map16;
                                                                        str3 = str5;
                                                                        com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                        str4 = str;
                                                                    }
                                                                    map26 = arrayMap11;
                                                                    map25 = arrayMap8;
                                                                    map5 = arrayMap7;
                                                                    hashSet2 = hashSet3;
                                                                    com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi7;
                                                                    arrayMap8 = map28;
                                                                    it = it6;
                                                                    map4 = map10;
                                                                    map27 = map8;
                                                                    com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                    str4 = str;
                                                                    com_google_android_gms_measurement_internal_zzk = this;
                                                                } else {
                                                                    zzgt().zzjo().zzg("Skipping failed audience ID", Integer.valueOf(intValue2));
                                                                    hashSet2 = hashSet3;
                                                                }
                                                            }
                                                            arrayMap13 = arrayMap8;
                                                            map10 = map4;
                                                            map14 = map25;
                                                            map16 = map26;
                                                            hashSet3 = hashSet2;
                                                            map15 = map27;
                                                            map13 = map5;
                                                            l3 = l2;
                                                            j2 = j3;
                                                            com_google_android_gms_internal_measurement_zzgi5 = com_google_android_gms_internal_measurement_zzgi3;
                                                            i5 = i6 + 1;
                                                            com_google_android_gms_internal_measurement_zzgiArr2 = com_google_android_gms_internal_measurement_zzgiArr;
                                                            hashSet = hashSet3;
                                                            length2 = i4;
                                                            arrayMap12 = arrayMap13;
                                                            arrayMap11 = map10;
                                                            map = map13;
                                                            map2 = map14;
                                                            arrayMap7 = map15;
                                                            map3 = map16;
                                                            com_google_android_gms_measurement_internal_zzk = this;
                                                            com_google_android_gms_internal_measurement_zzgoArr3 = com_google_android_gms_internal_measurement_zzgoArr;
                                                            str6 = str;
                                                        }
                                                    } catch (SQLiteException e3) {
                                                        e = e3;
                                                        zzjt.zzgt().zzjg().zzg("Error clearing complex main event", e);
                                                        com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                                                        map4 = arrayMap11;
                                                        i6 = i7;
                                                        com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi10;
                                                        z = true;
                                                        com_google_android_gms_internal_measurement_zzgjArr = new zzgj[(com_google_android_gms_internal_measurement_zzgi2.zzaxm.length + com_google_android_gms_internal_measurement_zzgjArr4.length)];
                                                        com_google_android_gms_internal_measurement_zzgjArr2 = com_google_android_gms_internal_measurement_zzgi2.zzaxm;
                                                        length = com_google_android_gms_internal_measurement_zzgjArr2.length;
                                                        i5 = 0;
                                                        i2 = 0;
                                                        while (i5 < length) {
                                                            com_google_android_gms_internal_measurement_zzgj = com_google_android_gms_internal_measurement_zzgjArr2[i5];
                                                            zzjr();
                                                            com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                                            if (zzfq.zza(com_google_android_gms_internal_measurement_zzgi, com_google_android_gms_internal_measurement_zzgj.name) != null) {
                                                                i = i2 + 1;
                                                                com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgj;
                                                                i2 = i;
                                                            }
                                                            i5++;
                                                            com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi3;
                                                        }
                                                        com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                                        if (i2 > 0) {
                                                            i = com_google_android_gms_internal_measurement_zzgjArr4.length;
                                                            intValue = 0;
                                                            while (intValue < i) {
                                                                i5 = i2 + 1;
                                                                com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgjArr4[intValue];
                                                                intValue++;
                                                                i2 = i5;
                                                            }
                                                            if (i2 == com_google_android_gms_internal_measurement_zzgjArr.length) {
                                                                com_google_android_gms_internal_measurement_zzgjArr = (zzgj[]) Arrays.copyOf(com_google_android_gms_internal_measurement_zzgjArr, i2);
                                                            }
                                                            com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr;
                                                            str3 = str2;
                                                        } else {
                                                            zzgt().zzjj().zzg("No unique parameters in main event. eventName", str2);
                                                            str3 = str2;
                                                            com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr4;
                                                        }
                                                        l2 = l;
                                                        j3 = j5;
                                                        zzg = zzjt().zzg(str6, com_google_android_gms_internal_measurement_zzgi.name);
                                                        if (zzg != null) {
                                                            i4 = length2;
                                                            arrayMap = arrayMap12;
                                                            map27 = arrayMap7;
                                                            com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi;
                                                            com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr3;
                                                            str4 = str6;
                                                            hashSet2 = hashSet;
                                                            map5 = map;
                                                            map25 = map2;
                                                            map26 = map3;
                                                            zzg = new zzaa(zzg.zztt, zzg.name, zzg.zzaih + 1, zzg.zzaii + 1, zzg.zzaij, zzg.zzaik, zzg.zzail, zzg.zzaim, zzg.zzain, zzg.zzaio);
                                                        } else {
                                                            zzgt().zzjj().zze("Event aggregate wasn't created during raw event logging. appId, event", zzaq.zzby(str), zzgq().zzbv(str3));
                                                            i4 = length2;
                                                            arrayMap = arrayMap12;
                                                            map25 = map2;
                                                            map26 = map3;
                                                            map5 = map;
                                                            map27 = arrayMap7;
                                                            hashSet2 = hashSet;
                                                            com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi;
                                                            com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr3;
                                                            str4 = str6;
                                                            com_google_android_gms_measurement_internal_zzaa = new zzaa(str, com_google_android_gms_internal_measurement_zzgi.name, 1, 1, com_google_android_gms_internal_measurement_zzgi.zzaxn.longValue(), 0, null, null, null, null);
                                                        }
                                                        zzjt().zza(zzg);
                                                        j = zzg.zzaih;
                                                        arrayMap8 = arrayMap;
                                                        map6 = (Map) arrayMap8.get(str3);
                                                        if (map6 == null) {
                                                            map6 = zzjt().zzl(str4, str3);
                                                            if (map6 == null) {
                                                                map6 = new ArrayMap();
                                                            }
                                                            arrayMap8.put(str3, map6);
                                                        }
                                                        map7 = map6;
                                                        it = map7.keySet().iterator();
                                                        while (it.hasNext()) {
                                                            intValue2 = ((Integer) it.next()).intValue();
                                                            hashSet3 = hashSet2;
                                                            if (hashSet3.contains(Integer.valueOf(intValue2))) {
                                                                map8 = map27;
                                                                com_google_android_gms_internal_measurement_zzgg = (zzgg) map8.get(Integer.valueOf(intValue2));
                                                                arrayMap11 = map26;
                                                                bitSet = (BitSet) arrayMap11.get(Integer.valueOf(intValue2));
                                                                com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi6;
                                                                map28 = arrayMap8;
                                                                arrayMap8 = map25;
                                                                bitSet2 = (BitSet) arrayMap8.get(Integer.valueOf(intValue2));
                                                                if (zzbd) {
                                                                    bitSet10 = bitSet2;
                                                                    it6 = it;
                                                                    map29 = map4;
                                                                    arrayMap7 = map5;
                                                                    map9 = null;
                                                                    map30 = null;
                                                                } else {
                                                                    bitSet10 = bitSet2;
                                                                    it6 = it;
                                                                    arrayMap7 = map5;
                                                                    map30 = (Map) arrayMap7.get(Integer.valueOf(intValue2));
                                                                    map29 = map4;
                                                                    map9 = (Map) map29.get(Integer.valueOf(intValue2));
                                                                }
                                                                if (com_google_android_gms_internal_measurement_zzgg != null) {
                                                                    map31 = map9;
                                                                    map10 = map29;
                                                                    bitSet2 = bitSet10;
                                                                    arrayMap2 = map30;
                                                                    map29 = map31;
                                                                } else {
                                                                    com_google_android_gms_internal_measurement_zzgg = new zzgg();
                                                                    map8.put(Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzgg);
                                                                    com_google_android_gms_internal_measurement_zzgg.zzaxh = Boolean.valueOf(z);
                                                                    bitSet = new BitSet();
                                                                    arrayMap11.put(Integer.valueOf(intValue2), bitSet);
                                                                    bitSet3 = new BitSet();
                                                                    map31 = map9;
                                                                    arrayMap8.put(Integer.valueOf(intValue2), bitSet3);
                                                                    if (zzbd) {
                                                                        map10 = map29;
                                                                        arrayMap2 = map30;
                                                                        map29 = map31;
                                                                        bitSet2 = bitSet3;
                                                                    } else {
                                                                        arrayMap3 = new ArrayMap();
                                                                        bitSet4 = bitSet3;
                                                                        arrayMap7.put(Integer.valueOf(intValue2), arrayMap3);
                                                                        arrayMap4 = new ArrayMap();
                                                                        arrayMap5 = arrayMap3;
                                                                        map29.put(Integer.valueOf(intValue2), arrayMap4);
                                                                        map10 = map29;
                                                                        bitSet2 = bitSet4;
                                                                        arrayMap2 = arrayMap5;
                                                                        map29 = arrayMap4;
                                                                    }
                                                                }
                                                                for (zzfy com_google_android_gms_internal_measurement_zzfy32 : (List) r11.get(Integer.valueOf(intValue2))) {
                                                                    bitSet5 = bitSet2;
                                                                    map11 = map7;
                                                                    if (zzgt().isLoggable(2)) {
                                                                        map12 = arrayMap11;
                                                                        map13 = arrayMap7;
                                                                    } else {
                                                                        map12 = arrayMap11;
                                                                        map13 = arrayMap7;
                                                                        zzgt().zzjo().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy32.zzavx, zzgq().zzbv(com_google_android_gms_internal_measurement_zzfy32.zzavy));
                                                                        zzgt().zzjo().zzg("Filter definition", zzjr().zza(com_google_android_gms_internal_measurement_zzfy32));
                                                                    }
                                                                    if (com_google_android_gms_internal_measurement_zzfy32.zzavx != null) {
                                                                        if (com_google_android_gms_internal_measurement_zzfy32.zzavx.intValue() > 256) {
                                                                            if (zzbd) {
                                                                                str5 = str3;
                                                                                com_google_android_gms_internal_measurement_zzfy2 = com_google_android_gms_internal_measurement_zzfy32;
                                                                                map14 = arrayMap8;
                                                                                map15 = map8;
                                                                                com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                                                                arrayMap7 = arrayMap2;
                                                                                bitSet6 = bitSet5;
                                                                                map16 = map12;
                                                                                bitSet11 = bitSet;
                                                                                if (bitSet11.get(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue())) {
                                                                                    zza = zza(com_google_android_gms_internal_measurement_zzfy2, str5, com_google_android_gms_internal_measurement_zzgjArr3, j);
                                                                                    if (zza != null) {
                                                                                    }
                                                                                    zzgt().zzjo().zzg("Event filter result", zza != null ? "null" : zza);
                                                                                    if (zza != null) {
                                                                                        bitSet6.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                                        if (zza.booleanValue()) {
                                                                                            bitSet11.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                                        }
                                                                                    } else {
                                                                                        hashSet3.add(Integer.valueOf(intValue2));
                                                                                    }
                                                                                } else {
                                                                                    zzgt().zzjo().zze("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy2.zzavx);
                                                                                }
                                                                            } else {
                                                                                if (com_google_android_gms_internal_measurement_zzfy32 == null) {
                                                                                }
                                                                                if (com_google_android_gms_internal_measurement_zzfy32 == null) {
                                                                                }
                                                                                if (!bitSet.get(com_google_android_gms_internal_measurement_zzfy32.zzavx.intValue())) {
                                                                                }
                                                                                com_google_android_gms_internal_measurement_zzfy = com_google_android_gms_internal_measurement_zzfy32;
                                                                                bitSet6 = bitSet5;
                                                                                map14 = arrayMap8;
                                                                                map15 = map8;
                                                                                com_google_android_gms_internal_measurement_zzgoArr2 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                                com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                                                                bitSet11 = bitSet;
                                                                                str5 = str3;
                                                                                com_google_android_gms_internal_measurement_zzfy2 = com_google_android_gms_internal_measurement_zzfy;
                                                                                arrayMap6 = arrayMap2;
                                                                                map16 = map12;
                                                                                zza = zza(com_google_android_gms_internal_measurement_zzfy, str3, com_google_android_gms_internal_measurement_zzgjArr3, j);
                                                                                if (zza != null) {
                                                                                }
                                                                                zzgt().zzjo().zzg("Event filter result", zza != null ? "null" : zza);
                                                                                if (zza != null) {
                                                                                    bitSet6.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                                    if (zza.booleanValue()) {
                                                                                        bitSet11.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                                        if (obj3 == null) {
                                                                                            arrayMap7 = arrayMap6;
                                                                                            zza(arrayMap7, com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgi4.zzaxn.longValue());
                                                                                        } else {
                                                                                            zzb(map29, com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgi4.zzaxn.longValue());
                                                                                        }
                                                                                    }
                                                                                } else {
                                                                                    hashSet3.add(Integer.valueOf(intValue2));
                                                                                }
                                                                                bitSet = bitSet11;
                                                                                bitSet2 = bitSet6;
                                                                                com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                                                                map7 = map11;
                                                                                arrayMap7 = map13;
                                                                                arrayMap8 = map14;
                                                                                map8 = map15;
                                                                                arrayMap11 = map16;
                                                                                str3 = str5;
                                                                                arrayMap2 = arrayMap6;
                                                                                com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                                str4 = str;
                                                                            }
                                                                            bitSet = bitSet11;
                                                                            bitSet2 = bitSet6;
                                                                            arrayMap2 = arrayMap7;
                                                                            com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                                                            map7 = map11;
                                                                            arrayMap7 = map13;
                                                                            arrayMap8 = map14;
                                                                            map8 = map15;
                                                                            arrayMap11 = map16;
                                                                            str3 = str5;
                                                                            com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                            str4 = str;
                                                                        }
                                                                    }
                                                                    str5 = str3;
                                                                    map14 = arrayMap8;
                                                                    map15 = map8;
                                                                    com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                                                    arrayMap7 = arrayMap2;
                                                                    bitSet6 = bitSet5;
                                                                    map16 = map12;
                                                                    bitSet11 = bitSet;
                                                                    zzgt().zzjj().zze("Invalid event filter ID. appId, id", zzaq.zzby(str), String.valueOf(com_google_android_gms_internal_measurement_zzfy32.zzavx));
                                                                    bitSet = bitSet11;
                                                                    bitSet2 = bitSet6;
                                                                    arrayMap2 = arrayMap7;
                                                                    com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                                                    map7 = map11;
                                                                    arrayMap7 = map13;
                                                                    arrayMap8 = map14;
                                                                    map8 = map15;
                                                                    arrayMap11 = map16;
                                                                    str3 = str5;
                                                                    com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                    str4 = str;
                                                                }
                                                                map26 = arrayMap11;
                                                                map25 = arrayMap8;
                                                                map5 = arrayMap7;
                                                                hashSet2 = hashSet3;
                                                                com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi7;
                                                                arrayMap8 = map28;
                                                                it = it6;
                                                                map4 = map10;
                                                                map27 = map8;
                                                                com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                str4 = str;
                                                                com_google_android_gms_measurement_internal_zzk = this;
                                                            } else {
                                                                zzgt().zzjo().zzg("Skipping failed audience ID", Integer.valueOf(intValue2));
                                                                hashSet2 = hashSet3;
                                                            }
                                                        }
                                                        arrayMap13 = arrayMap8;
                                                        map10 = map4;
                                                        map14 = map25;
                                                        map16 = map26;
                                                        hashSet3 = hashSet2;
                                                        map15 = map27;
                                                        map13 = map5;
                                                        l3 = l2;
                                                        j2 = j3;
                                                        com_google_android_gms_internal_measurement_zzgi5 = com_google_android_gms_internal_measurement_zzgi3;
                                                        i5 = i6 + 1;
                                                        com_google_android_gms_internal_measurement_zzgiArr2 = com_google_android_gms_internal_measurement_zzgiArr;
                                                        hashSet = hashSet3;
                                                        length2 = i4;
                                                        arrayMap12 = arrayMap13;
                                                        arrayMap11 = map10;
                                                        map = map13;
                                                        map2 = map14;
                                                        arrayMap7 = map15;
                                                        map3 = map16;
                                                        com_google_android_gms_measurement_internal_zzk = this;
                                                        com_google_android_gms_internal_measurement_zzgoArr3 = com_google_android_gms_internal_measurement_zzgoArr;
                                                        str6 = str;
                                                    }
                                                } catch (SQLiteException e4) {
                                                    e = e4;
                                                    com_google_android_gms_internal_measurement_zzgi10 = com_google_android_gms_internal_measurement_zzgi9;
                                                    zzjt.zzgt().zzjg().zzg("Error clearing complex main event", e);
                                                    com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                                                    map4 = arrayMap11;
                                                    i6 = i7;
                                                    com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi10;
                                                    z = true;
                                                    com_google_android_gms_internal_measurement_zzgjArr = new zzgj[(com_google_android_gms_internal_measurement_zzgi2.zzaxm.length + com_google_android_gms_internal_measurement_zzgjArr4.length)];
                                                    com_google_android_gms_internal_measurement_zzgjArr2 = com_google_android_gms_internal_measurement_zzgi2.zzaxm;
                                                    length = com_google_android_gms_internal_measurement_zzgjArr2.length;
                                                    i5 = 0;
                                                    i2 = 0;
                                                    while (i5 < length) {
                                                        com_google_android_gms_internal_measurement_zzgj = com_google_android_gms_internal_measurement_zzgjArr2[i5];
                                                        zzjr();
                                                        com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                                        if (zzfq.zza(com_google_android_gms_internal_measurement_zzgi, com_google_android_gms_internal_measurement_zzgj.name) != null) {
                                                            i = i2 + 1;
                                                            com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgj;
                                                            i2 = i;
                                                        }
                                                        i5++;
                                                        com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi3;
                                                    }
                                                    com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                                    if (i2 > 0) {
                                                        i = com_google_android_gms_internal_measurement_zzgjArr4.length;
                                                        intValue = 0;
                                                        while (intValue < i) {
                                                            i5 = i2 + 1;
                                                            com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgjArr4[intValue];
                                                            intValue++;
                                                            i2 = i5;
                                                        }
                                                        if (i2 == com_google_android_gms_internal_measurement_zzgjArr.length) {
                                                            com_google_android_gms_internal_measurement_zzgjArr = (zzgj[]) Arrays.copyOf(com_google_android_gms_internal_measurement_zzgjArr, i2);
                                                        }
                                                        com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr;
                                                        str3 = str2;
                                                    } else {
                                                        zzgt().zzjj().zzg("No unique parameters in main event. eventName", str2);
                                                        str3 = str2;
                                                        com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr4;
                                                    }
                                                    l2 = l;
                                                    j3 = j5;
                                                    zzg = zzjt().zzg(str6, com_google_android_gms_internal_measurement_zzgi.name);
                                                    if (zzg != null) {
                                                        i4 = length2;
                                                        arrayMap = arrayMap12;
                                                        map27 = arrayMap7;
                                                        com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi;
                                                        com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr3;
                                                        str4 = str6;
                                                        hashSet2 = hashSet;
                                                        map5 = map;
                                                        map25 = map2;
                                                        map26 = map3;
                                                        zzg = new zzaa(zzg.zztt, zzg.name, zzg.zzaih + 1, zzg.zzaii + 1, zzg.zzaij, zzg.zzaik, zzg.zzail, zzg.zzaim, zzg.zzain, zzg.zzaio);
                                                    } else {
                                                        zzgt().zzjj().zze("Event aggregate wasn't created during raw event logging. appId, event", zzaq.zzby(str), zzgq().zzbv(str3));
                                                        i4 = length2;
                                                        arrayMap = arrayMap12;
                                                        map25 = map2;
                                                        map26 = map3;
                                                        map5 = map;
                                                        map27 = arrayMap7;
                                                        hashSet2 = hashSet;
                                                        com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi;
                                                        com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr3;
                                                        str4 = str6;
                                                        com_google_android_gms_measurement_internal_zzaa = new zzaa(str, com_google_android_gms_internal_measurement_zzgi.name, 1, 1, com_google_android_gms_internal_measurement_zzgi.zzaxn.longValue(), 0, null, null, null, null);
                                                    }
                                                    zzjt().zza(zzg);
                                                    j = zzg.zzaih;
                                                    arrayMap8 = arrayMap;
                                                    map6 = (Map) arrayMap8.get(str3);
                                                    if (map6 == null) {
                                                        map6 = zzjt().zzl(str4, str3);
                                                        if (map6 == null) {
                                                            map6 = new ArrayMap();
                                                        }
                                                        arrayMap8.put(str3, map6);
                                                    }
                                                    map7 = map6;
                                                    it = map7.keySet().iterator();
                                                    while (it.hasNext()) {
                                                        intValue2 = ((Integer) it.next()).intValue();
                                                        hashSet3 = hashSet2;
                                                        if (hashSet3.contains(Integer.valueOf(intValue2))) {
                                                            map8 = map27;
                                                            com_google_android_gms_internal_measurement_zzgg = (zzgg) map8.get(Integer.valueOf(intValue2));
                                                            arrayMap11 = map26;
                                                            bitSet = (BitSet) arrayMap11.get(Integer.valueOf(intValue2));
                                                            com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi6;
                                                            map28 = arrayMap8;
                                                            arrayMap8 = map25;
                                                            bitSet2 = (BitSet) arrayMap8.get(Integer.valueOf(intValue2));
                                                            if (zzbd) {
                                                                bitSet10 = bitSet2;
                                                                it6 = it;
                                                                map29 = map4;
                                                                arrayMap7 = map5;
                                                                map9 = null;
                                                                map30 = null;
                                                            } else {
                                                                bitSet10 = bitSet2;
                                                                it6 = it;
                                                                arrayMap7 = map5;
                                                                map30 = (Map) arrayMap7.get(Integer.valueOf(intValue2));
                                                                map29 = map4;
                                                                map9 = (Map) map29.get(Integer.valueOf(intValue2));
                                                            }
                                                            if (com_google_android_gms_internal_measurement_zzgg != null) {
                                                                map31 = map9;
                                                                map10 = map29;
                                                                bitSet2 = bitSet10;
                                                                arrayMap2 = map30;
                                                                map29 = map31;
                                                            } else {
                                                                com_google_android_gms_internal_measurement_zzgg = new zzgg();
                                                                map8.put(Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzgg);
                                                                com_google_android_gms_internal_measurement_zzgg.zzaxh = Boolean.valueOf(z);
                                                                bitSet = new BitSet();
                                                                arrayMap11.put(Integer.valueOf(intValue2), bitSet);
                                                                bitSet3 = new BitSet();
                                                                map31 = map9;
                                                                arrayMap8.put(Integer.valueOf(intValue2), bitSet3);
                                                                if (zzbd) {
                                                                    map10 = map29;
                                                                    arrayMap2 = map30;
                                                                    map29 = map31;
                                                                    bitSet2 = bitSet3;
                                                                } else {
                                                                    arrayMap3 = new ArrayMap();
                                                                    bitSet4 = bitSet3;
                                                                    arrayMap7.put(Integer.valueOf(intValue2), arrayMap3);
                                                                    arrayMap4 = new ArrayMap();
                                                                    arrayMap5 = arrayMap3;
                                                                    map29.put(Integer.valueOf(intValue2), arrayMap4);
                                                                    map10 = map29;
                                                                    bitSet2 = bitSet4;
                                                                    arrayMap2 = arrayMap5;
                                                                    map29 = arrayMap4;
                                                                }
                                                            }
                                                            for (zzfy com_google_android_gms_internal_measurement_zzfy322 : (List) r11.get(Integer.valueOf(intValue2))) {
                                                                bitSet5 = bitSet2;
                                                                map11 = map7;
                                                                if (zzgt().isLoggable(2)) {
                                                                    map12 = arrayMap11;
                                                                    map13 = arrayMap7;
                                                                } else {
                                                                    map12 = arrayMap11;
                                                                    map13 = arrayMap7;
                                                                    zzgt().zzjo().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy322.zzavx, zzgq().zzbv(com_google_android_gms_internal_measurement_zzfy322.zzavy));
                                                                    zzgt().zzjo().zzg("Filter definition", zzjr().zza(com_google_android_gms_internal_measurement_zzfy322));
                                                                }
                                                                if (com_google_android_gms_internal_measurement_zzfy322.zzavx != null) {
                                                                    if (com_google_android_gms_internal_measurement_zzfy322.zzavx.intValue() > 256) {
                                                                        if (zzbd) {
                                                                            str5 = str3;
                                                                            com_google_android_gms_internal_measurement_zzfy2 = com_google_android_gms_internal_measurement_zzfy322;
                                                                            map14 = arrayMap8;
                                                                            map15 = map8;
                                                                            com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                                                            arrayMap7 = arrayMap2;
                                                                            bitSet6 = bitSet5;
                                                                            map16 = map12;
                                                                            bitSet11 = bitSet;
                                                                            if (bitSet11.get(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue())) {
                                                                                zza = zza(com_google_android_gms_internal_measurement_zzfy2, str5, com_google_android_gms_internal_measurement_zzgjArr3, j);
                                                                                if (zza != null) {
                                                                                }
                                                                                zzgt().zzjo().zzg("Event filter result", zza != null ? "null" : zza);
                                                                                if (zza != null) {
                                                                                    bitSet6.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                                    if (zza.booleanValue()) {
                                                                                        bitSet11.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                                    }
                                                                                } else {
                                                                                    hashSet3.add(Integer.valueOf(intValue2));
                                                                                }
                                                                            } else {
                                                                                zzgt().zzjo().zze("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy2.zzavx);
                                                                            }
                                                                        } else {
                                                                            if (com_google_android_gms_internal_measurement_zzfy322 == null) {
                                                                            }
                                                                            if (com_google_android_gms_internal_measurement_zzfy322 == null) {
                                                                            }
                                                                            if (!bitSet.get(com_google_android_gms_internal_measurement_zzfy322.zzavx.intValue())) {
                                                                            }
                                                                            com_google_android_gms_internal_measurement_zzfy = com_google_android_gms_internal_measurement_zzfy322;
                                                                            bitSet6 = bitSet5;
                                                                            map14 = arrayMap8;
                                                                            map15 = map8;
                                                                            com_google_android_gms_internal_measurement_zzgoArr2 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                            com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                                                            bitSet11 = bitSet;
                                                                            str5 = str3;
                                                                            com_google_android_gms_internal_measurement_zzfy2 = com_google_android_gms_internal_measurement_zzfy;
                                                                            arrayMap6 = arrayMap2;
                                                                            map16 = map12;
                                                                            zza = zza(com_google_android_gms_internal_measurement_zzfy, str3, com_google_android_gms_internal_measurement_zzgjArr3, j);
                                                                            if (zza != null) {
                                                                            }
                                                                            zzgt().zzjo().zzg("Event filter result", zza != null ? "null" : zza);
                                                                            if (zza != null) {
                                                                                bitSet6.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                                if (zza.booleanValue()) {
                                                                                    bitSet11.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                                    if (obj3 == null) {
                                                                                        arrayMap7 = arrayMap6;
                                                                                        zza(arrayMap7, com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgi4.zzaxn.longValue());
                                                                                    } else {
                                                                                        zzb(map29, com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgi4.zzaxn.longValue());
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                hashSet3.add(Integer.valueOf(intValue2));
                                                                            }
                                                                            bitSet = bitSet11;
                                                                            bitSet2 = bitSet6;
                                                                            com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                                                            map7 = map11;
                                                                            arrayMap7 = map13;
                                                                            arrayMap8 = map14;
                                                                            map8 = map15;
                                                                            arrayMap11 = map16;
                                                                            str3 = str5;
                                                                            arrayMap2 = arrayMap6;
                                                                            com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                            str4 = str;
                                                                        }
                                                                        bitSet = bitSet11;
                                                                        bitSet2 = bitSet6;
                                                                        arrayMap2 = arrayMap7;
                                                                        com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                                                        map7 = map11;
                                                                        arrayMap7 = map13;
                                                                        arrayMap8 = map14;
                                                                        map8 = map15;
                                                                        arrayMap11 = map16;
                                                                        str3 = str5;
                                                                        com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                        str4 = str;
                                                                    }
                                                                }
                                                                str5 = str3;
                                                                map14 = arrayMap8;
                                                                map15 = map8;
                                                                com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                                                arrayMap7 = arrayMap2;
                                                                bitSet6 = bitSet5;
                                                                map16 = map12;
                                                                bitSet11 = bitSet;
                                                                zzgt().zzjj().zze("Invalid event filter ID. appId, id", zzaq.zzby(str), String.valueOf(com_google_android_gms_internal_measurement_zzfy322.zzavx));
                                                                bitSet = bitSet11;
                                                                bitSet2 = bitSet6;
                                                                arrayMap2 = arrayMap7;
                                                                com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                                                map7 = map11;
                                                                arrayMap7 = map13;
                                                                arrayMap8 = map14;
                                                                map8 = map15;
                                                                arrayMap11 = map16;
                                                                str3 = str5;
                                                                com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                                str4 = str;
                                                            }
                                                            map26 = arrayMap11;
                                                            map25 = arrayMap8;
                                                            map5 = arrayMap7;
                                                            hashSet2 = hashSet3;
                                                            com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi7;
                                                            arrayMap8 = map28;
                                                            it = it6;
                                                            map4 = map10;
                                                            map27 = map8;
                                                            com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                            str4 = str;
                                                            com_google_android_gms_measurement_internal_zzk = this;
                                                        } else {
                                                            zzgt().zzjo().zzg("Skipping failed audience ID", Integer.valueOf(intValue2));
                                                            hashSet2 = hashSet3;
                                                        }
                                                    }
                                                    arrayMap13 = arrayMap8;
                                                    map10 = map4;
                                                    map14 = map25;
                                                    map16 = map26;
                                                    hashSet3 = hashSet2;
                                                    map15 = map27;
                                                    map13 = map5;
                                                    l3 = l2;
                                                    j2 = j3;
                                                    com_google_android_gms_internal_measurement_zzgi5 = com_google_android_gms_internal_measurement_zzgi3;
                                                    i5 = i6 + 1;
                                                    com_google_android_gms_internal_measurement_zzgiArr2 = com_google_android_gms_internal_measurement_zzgiArr;
                                                    hashSet = hashSet3;
                                                    length2 = i4;
                                                    arrayMap12 = arrayMap13;
                                                    arrayMap11 = map10;
                                                    map = map13;
                                                    map2 = map14;
                                                    arrayMap7 = map15;
                                                    map3 = map16;
                                                    com_google_android_gms_measurement_internal_zzk = this;
                                                    com_google_android_gms_internal_measurement_zzgoArr3 = com_google_android_gms_internal_measurement_zzgoArr;
                                                    str6 = str;
                                                }
                                                com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                                                map4 = arrayMap11;
                                                i6 = i7;
                                                com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi10;
                                                z = true;
                                            } else {
                                                com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                                                i6 = i7;
                                                com_google_android_gms_internal_measurement_zzgi11 = com_google_android_gms_internal_measurement_zzgi9;
                                                z = true;
                                                map4 = arrayMap11;
                                                zzjt().zza(str, l5, j5, com_google_android_gms_internal_measurement_zzgi11);
                                                com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi11;
                                            }
                                            com_google_android_gms_internal_measurement_zzgjArr = new zzgj[(com_google_android_gms_internal_measurement_zzgi2.zzaxm.length + com_google_android_gms_internal_measurement_zzgjArr4.length)];
                                            com_google_android_gms_internal_measurement_zzgjArr2 = com_google_android_gms_internal_measurement_zzgi2.zzaxm;
                                            length = com_google_android_gms_internal_measurement_zzgjArr2.length;
                                            i5 = 0;
                                            i2 = 0;
                                            while (i5 < length) {
                                                com_google_android_gms_internal_measurement_zzgj = com_google_android_gms_internal_measurement_zzgjArr2[i5];
                                                zzjr();
                                                com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                                if (zzfq.zza(com_google_android_gms_internal_measurement_zzgi, com_google_android_gms_internal_measurement_zzgj.name) != null) {
                                                    i = i2 + 1;
                                                    com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgj;
                                                    i2 = i;
                                                }
                                                i5++;
                                                com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi3;
                                            }
                                            com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                            if (i2 > 0) {
                                                i = com_google_android_gms_internal_measurement_zzgjArr4.length;
                                                intValue = 0;
                                                while (intValue < i) {
                                                    i5 = i2 + 1;
                                                    com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgjArr4[intValue];
                                                    intValue++;
                                                    i2 = i5;
                                                }
                                                if (i2 == com_google_android_gms_internal_measurement_zzgjArr.length) {
                                                    com_google_android_gms_internal_measurement_zzgjArr = (zzgj[]) Arrays.copyOf(com_google_android_gms_internal_measurement_zzgjArr, i2);
                                                }
                                                com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr;
                                                str3 = str2;
                                            } else {
                                                zzgt().zzjj().zzg("No unique parameters in main event. eventName", str2);
                                                str3 = str2;
                                                com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr4;
                                            }
                                            l2 = l;
                                            j3 = j5;
                                        }
                                    }
                                    zza2 = zzjt().zza(str6, l5);
                                    if (zza2 != null) {
                                        if (zza2.first == null) {
                                            com_google_android_gms_internal_measurement_zzgi5 = (zzgi) zza2.first;
                                            j4 = ((Long) zza2.second).longValue();
                                            zzjr();
                                            l = (Long) zzfq.zzb(com_google_android_gms_internal_measurement_zzgi5, "_eid");
                                            com_google_android_gms_internal_measurement_zzgi9 = com_google_android_gms_internal_measurement_zzgi5;
                                            j5 = j4 - 1;
                                            if (j5 > 0) {
                                                com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                                                i6 = i7;
                                                com_google_android_gms_internal_measurement_zzgi11 = com_google_android_gms_internal_measurement_zzgi9;
                                                z = true;
                                                map4 = arrayMap11;
                                                zzjt().zza(str, l5, j5, com_google_android_gms_internal_measurement_zzgi11);
                                                com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi11;
                                            } else {
                                                zzjt = zzjt();
                                                zzjt.zzaf();
                                                zzjt.zzgt().zzjo().zzg("Clearing complex main event info. appId", str6);
                                                writableDatabase = zzjt.getWritableDatabase();
                                                str7 = "delete from main_event_params where app_id=?";
                                                com_google_android_gms_internal_measurement_zzgi10 = com_google_android_gms_internal_measurement_zzgi9;
                                                strArr = new String[1];
                                                strArr[0] = str6;
                                                writableDatabase.execSQL(str7, strArr);
                                                com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                                                map4 = arrayMap11;
                                                i6 = i7;
                                                com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi10;
                                                z = true;
                                            }
                                            com_google_android_gms_internal_measurement_zzgjArr = new zzgj[(com_google_android_gms_internal_measurement_zzgi2.zzaxm.length + com_google_android_gms_internal_measurement_zzgjArr4.length)];
                                            com_google_android_gms_internal_measurement_zzgjArr2 = com_google_android_gms_internal_measurement_zzgi2.zzaxm;
                                            length = com_google_android_gms_internal_measurement_zzgjArr2.length;
                                            i5 = 0;
                                            i2 = 0;
                                            while (i5 < length) {
                                                com_google_android_gms_internal_measurement_zzgj = com_google_android_gms_internal_measurement_zzgjArr2[i5];
                                                zzjr();
                                                com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                                if (zzfq.zza(com_google_android_gms_internal_measurement_zzgi, com_google_android_gms_internal_measurement_zzgj.name) != null) {
                                                    i = i2 + 1;
                                                    com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgj;
                                                    i2 = i;
                                                }
                                                i5++;
                                                com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi3;
                                            }
                                            com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                            if (i2 > 0) {
                                                zzgt().zzjj().zzg("No unique parameters in main event. eventName", str2);
                                                str3 = str2;
                                                com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr4;
                                            } else {
                                                i = com_google_android_gms_internal_measurement_zzgjArr4.length;
                                                intValue = 0;
                                                while (intValue < i) {
                                                    i5 = i2 + 1;
                                                    com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgjArr4[intValue];
                                                    intValue++;
                                                    i2 = i5;
                                                }
                                                if (i2 == com_google_android_gms_internal_measurement_zzgjArr.length) {
                                                    com_google_android_gms_internal_measurement_zzgjArr = (zzgj[]) Arrays.copyOf(com_google_android_gms_internal_measurement_zzgjArr, i2);
                                                }
                                                com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr;
                                                str3 = str2;
                                            }
                                            l2 = l;
                                            j3 = j5;
                                        }
                                    }
                                    map4 = arrayMap11;
                                    i6 = i7;
                                    zzgt().zzjg().zze("Extra parameter without existing main event. eventName, eventId", str2, l5);
                                } else {
                                    zzgt().zzjg().zzg("Extra parameter without an event name. eventId", l5);
                                    map4 = arrayMap11;
                                    i6 = i7;
                                }
                                i4 = length2;
                                arrayMap13 = arrayMap12;
                                map15 = arrayMap7;
                                hashSet3 = hashSet;
                                map13 = map;
                                map14 = map2;
                                map16 = map3;
                                j2 = j3;
                                map10 = map4;
                                i5 = i6 + 1;
                                com_google_android_gms_internal_measurement_zzgiArr2 = com_google_android_gms_internal_measurement_zzgiArr;
                                hashSet = hashSet3;
                                length2 = i4;
                                arrayMap12 = arrayMap13;
                                arrayMap11 = map10;
                                map = map13;
                                map2 = map14;
                                arrayMap7 = map15;
                                map3 = map16;
                                com_google_android_gms_measurement_internal_zzk = this;
                                com_google_android_gms_internal_measurement_zzgoArr3 = com_google_android_gms_internal_measurement_zzgoArr;
                                str6 = str;
                            } else {
                                com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                                map4 = arrayMap11;
                                i6 = i7;
                                z = true;
                                if (obj5 != null) {
                                    zzjr();
                                    l3 = Long.valueOf(0);
                                    e = zzfq.zzb(com_google_android_gms_internal_measurement_zzgi, "_epc");
                                    if (e == null) {
                                        e = l3;
                                    }
                                    longValue = ((Long) e).longValue();
                                    if (longValue > 0) {
                                        zzgt().zzjj().zzg("Complex event with zero extra param count. eventName", str2);
                                        l4 = l5;
                                    } else {
                                        l4 = l5;
                                        zzjt().zza(str, l5, longValue, com_google_android_gms_internal_measurement_zzgi);
                                    }
                                    l2 = l4;
                                    str3 = str2;
                                    com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr4;
                                    com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi;
                                    j3 = longValue;
                                }
                            }
                            zzg = zzjt().zzg(str6, com_google_android_gms_internal_measurement_zzgi.name);
                            if (zzg != null) {
                                zzgt().zzjj().zze("Event aggregate wasn't created during raw event logging. appId, event", zzaq.zzby(str), zzgq().zzbv(str3));
                                i4 = length2;
                                arrayMap = arrayMap12;
                                map25 = map2;
                                map26 = map3;
                                map5 = map;
                                map27 = arrayMap7;
                                hashSet2 = hashSet;
                                com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi;
                                com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr3;
                                str4 = str6;
                                com_google_android_gms_measurement_internal_zzaa = new zzaa(str, com_google_android_gms_internal_measurement_zzgi.name, 1, 1, com_google_android_gms_internal_measurement_zzgi.zzaxn.longValue(), 0, null, null, null, null);
                            } else {
                                i4 = length2;
                                arrayMap = arrayMap12;
                                map27 = arrayMap7;
                                com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi;
                                com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr3;
                                str4 = str6;
                                hashSet2 = hashSet;
                                map5 = map;
                                map25 = map2;
                                map26 = map3;
                                zzg = new zzaa(zzg.zztt, zzg.name, zzg.zzaih + 1, zzg.zzaii + 1, zzg.zzaij, zzg.zzaik, zzg.zzail, zzg.zzaim, zzg.zzain, zzg.zzaio);
                            }
                            zzjt().zza(zzg);
                            j = zzg.zzaih;
                            arrayMap8 = arrayMap;
                            map6 = (Map) arrayMap8.get(str3);
                            if (map6 == null) {
                                map6 = zzjt().zzl(str4, str3);
                                if (map6 == null) {
                                    map6 = new ArrayMap();
                                }
                                arrayMap8.put(str3, map6);
                            }
                            map7 = map6;
                            it = map7.keySet().iterator();
                            while (it.hasNext()) {
                                intValue2 = ((Integer) it.next()).intValue();
                                hashSet3 = hashSet2;
                                if (hashSet3.contains(Integer.valueOf(intValue2))) {
                                    zzgt().zzjo().zzg("Skipping failed audience ID", Integer.valueOf(intValue2));
                                    hashSet2 = hashSet3;
                                } else {
                                    map8 = map27;
                                    com_google_android_gms_internal_measurement_zzgg = (zzgg) map8.get(Integer.valueOf(intValue2));
                                    arrayMap11 = map26;
                                    bitSet = (BitSet) arrayMap11.get(Integer.valueOf(intValue2));
                                    com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi6;
                                    map28 = arrayMap8;
                                    arrayMap8 = map25;
                                    bitSet2 = (BitSet) arrayMap8.get(Integer.valueOf(intValue2));
                                    if (zzbd) {
                                        bitSet10 = bitSet2;
                                        it6 = it;
                                        arrayMap7 = map5;
                                        map30 = (Map) arrayMap7.get(Integer.valueOf(intValue2));
                                        map29 = map4;
                                        map9 = (Map) map29.get(Integer.valueOf(intValue2));
                                    } else {
                                        bitSet10 = bitSet2;
                                        it6 = it;
                                        map29 = map4;
                                        arrayMap7 = map5;
                                        map9 = null;
                                        map30 = null;
                                    }
                                    if (com_google_android_gms_internal_measurement_zzgg != null) {
                                        com_google_android_gms_internal_measurement_zzgg = new zzgg();
                                        map8.put(Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzgg);
                                        com_google_android_gms_internal_measurement_zzgg.zzaxh = Boolean.valueOf(z);
                                        bitSet = new BitSet();
                                        arrayMap11.put(Integer.valueOf(intValue2), bitSet);
                                        bitSet3 = new BitSet();
                                        map31 = map9;
                                        arrayMap8.put(Integer.valueOf(intValue2), bitSet3);
                                        if (zzbd) {
                                            arrayMap3 = new ArrayMap();
                                            bitSet4 = bitSet3;
                                            arrayMap7.put(Integer.valueOf(intValue2), arrayMap3);
                                            arrayMap4 = new ArrayMap();
                                            arrayMap5 = arrayMap3;
                                            map29.put(Integer.valueOf(intValue2), arrayMap4);
                                            map10 = map29;
                                            bitSet2 = bitSet4;
                                            arrayMap2 = arrayMap5;
                                            map29 = arrayMap4;
                                        } else {
                                            map10 = map29;
                                            arrayMap2 = map30;
                                            map29 = map31;
                                            bitSet2 = bitSet3;
                                        }
                                    } else {
                                        map31 = map9;
                                        map10 = map29;
                                        bitSet2 = bitSet10;
                                        arrayMap2 = map30;
                                        map29 = map31;
                                    }
                                    for (zzfy com_google_android_gms_internal_measurement_zzfy3222 : (List) r11.get(Integer.valueOf(intValue2))) {
                                        bitSet5 = bitSet2;
                                        map11 = map7;
                                        if (zzgt().isLoggable(2)) {
                                            map12 = arrayMap11;
                                            map13 = arrayMap7;
                                            zzgt().zzjo().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy3222.zzavx, zzgq().zzbv(com_google_android_gms_internal_measurement_zzfy3222.zzavy));
                                            zzgt().zzjo().zzg("Filter definition", zzjr().zza(com_google_android_gms_internal_measurement_zzfy3222));
                                        } else {
                                            map12 = arrayMap11;
                                            map13 = arrayMap7;
                                        }
                                        if (com_google_android_gms_internal_measurement_zzfy3222.zzavx != null) {
                                            if (com_google_android_gms_internal_measurement_zzfy3222.zzavx.intValue() > 256) {
                                                if (zzbd) {
                                                    obj = (com_google_android_gms_internal_measurement_zzfy3222 == null && com_google_android_gms_internal_measurement_zzfy3222.zzavu != null && com_google_android_gms_internal_measurement_zzfy3222.zzavu.booleanValue()) ? 1 : null;
                                                    obj3 = (com_google_android_gms_internal_measurement_zzfy3222 == null && com_google_android_gms_internal_measurement_zzfy3222.zzavv != null && com_google_android_gms_internal_measurement_zzfy3222.zzavv.booleanValue()) ? 1 : null;
                                                    if (!bitSet.get(com_google_android_gms_internal_measurement_zzfy3222.zzavx.intValue()) && obj == null && obj3 == null) {
                                                        zzgt().zzjo().zze("Event filter already evaluated true and it is not associated with a dynamic audience. audience ID, filter ID", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy3222.zzavx);
                                                        bitSet2 = bitSet5;
                                                        map7 = map11;
                                                        arrayMap11 = map12;
                                                        arrayMap7 = map13;
                                                        com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                        str4 = str;
                                                    } else {
                                                        com_google_android_gms_internal_measurement_zzfy = com_google_android_gms_internal_measurement_zzfy3222;
                                                        bitSet6 = bitSet5;
                                                        map14 = arrayMap8;
                                                        map15 = map8;
                                                        com_google_android_gms_internal_measurement_zzgoArr2 = com_google_android_gms_internal_measurement_zzgoArr;
                                                        com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                                        bitSet11 = bitSet;
                                                        str5 = str3;
                                                        com_google_android_gms_internal_measurement_zzfy2 = com_google_android_gms_internal_measurement_zzfy;
                                                        arrayMap6 = arrayMap2;
                                                        map16 = map12;
                                                        zza = zza(com_google_android_gms_internal_measurement_zzfy, str3, com_google_android_gms_internal_measurement_zzgjArr3, j);
                                                        if (zza != null) {
                                                        }
                                                        zzgt().zzjo().zzg("Event filter result", zza != null ? "null" : zza);
                                                        if (zza != null) {
                                                            hashSet3.add(Integer.valueOf(intValue2));
                                                        } else {
                                                            bitSet6.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                            if (zza.booleanValue()) {
                                                                bitSet11.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                                if (!((obj == null && obj3 == null) || com_google_android_gms_internal_measurement_zzgi4.zzaxn == null)) {
                                                                    if (obj3 == null) {
                                                                        zzb(map29, com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgi4.zzaxn.longValue());
                                                                    } else {
                                                                        arrayMap7 = arrayMap6;
                                                                        zza(arrayMap7, com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgi4.zzaxn.longValue());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        bitSet = bitSet11;
                                                        bitSet2 = bitSet6;
                                                        com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                                        map7 = map11;
                                                        arrayMap7 = map13;
                                                        arrayMap8 = map14;
                                                        map8 = map15;
                                                        arrayMap11 = map16;
                                                        str3 = str5;
                                                        arrayMap2 = arrayMap6;
                                                        com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                        str4 = str;
                                                    }
                                                } else {
                                                    str5 = str3;
                                                    com_google_android_gms_internal_measurement_zzfy2 = com_google_android_gms_internal_measurement_zzfy3222;
                                                    map14 = arrayMap8;
                                                    map15 = map8;
                                                    com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                                    arrayMap7 = arrayMap2;
                                                    bitSet6 = bitSet5;
                                                    map16 = map12;
                                                    bitSet11 = bitSet;
                                                    if (bitSet11.get(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue())) {
                                                        zzgt().zzjo().zze("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy2.zzavx);
                                                    } else {
                                                        zza = zza(com_google_android_gms_internal_measurement_zzfy2, str5, com_google_android_gms_internal_measurement_zzgjArr3, j);
                                                        if (zza != null) {
                                                        }
                                                        zzgt().zzjo().zzg("Event filter result", zza != null ? "null" : zza);
                                                        if (zza != null) {
                                                            hashSet3.add(Integer.valueOf(intValue2));
                                                        } else {
                                                            bitSet6.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                            if (zza.booleanValue()) {
                                                                bitSet11.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                            }
                                                        }
                                                    }
                                                }
                                                bitSet = bitSet11;
                                                bitSet2 = bitSet6;
                                                arrayMap2 = arrayMap7;
                                                com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                                map7 = map11;
                                                arrayMap7 = map13;
                                                arrayMap8 = map14;
                                                map8 = map15;
                                                arrayMap11 = map16;
                                                str3 = str5;
                                                com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                                str4 = str;
                                            }
                                        }
                                        str5 = str3;
                                        map14 = arrayMap8;
                                        map15 = map8;
                                        com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                        arrayMap7 = arrayMap2;
                                        bitSet6 = bitSet5;
                                        map16 = map12;
                                        bitSet11 = bitSet;
                                        zzgt().zzjj().zze("Invalid event filter ID. appId, id", zzaq.zzby(str), String.valueOf(com_google_android_gms_internal_measurement_zzfy3222.zzavx));
                                        bitSet = bitSet11;
                                        bitSet2 = bitSet6;
                                        arrayMap2 = arrayMap7;
                                        com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                        map7 = map11;
                                        arrayMap7 = map13;
                                        arrayMap8 = map14;
                                        map8 = map15;
                                        arrayMap11 = map16;
                                        str3 = str5;
                                        com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                        str4 = str;
                                    }
                                    map26 = arrayMap11;
                                    map25 = arrayMap8;
                                    map5 = arrayMap7;
                                    hashSet2 = hashSet3;
                                    com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi7;
                                    arrayMap8 = map28;
                                    it = it6;
                                    map4 = map10;
                                    map27 = map8;
                                    com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                    str4 = str;
                                    com_google_android_gms_measurement_internal_zzk = this;
                                }
                            }
                            arrayMap13 = arrayMap8;
                            map10 = map4;
                            map14 = map25;
                            map16 = map26;
                            hashSet3 = hashSet2;
                            map15 = map27;
                            map13 = map5;
                            l3 = l2;
                            j2 = j3;
                            com_google_android_gms_internal_measurement_zzgi5 = com_google_android_gms_internal_measurement_zzgi3;
                            i5 = i6 + 1;
                            com_google_android_gms_internal_measurement_zzgiArr2 = com_google_android_gms_internal_measurement_zzgiArr;
                            hashSet = hashSet3;
                            length2 = i4;
                            arrayMap12 = arrayMap13;
                            arrayMap11 = map10;
                            map = map13;
                            map2 = map14;
                            arrayMap7 = map15;
                            map3 = map16;
                            com_google_android_gms_measurement_internal_zzk = this;
                            com_google_android_gms_internal_measurement_zzgoArr3 = com_google_android_gms_internal_measurement_zzgoArr;
                            str6 = str;
                        }
                    } else {
                        i7 = i5;
                    }
                    obj4 = null;
                    if (obj4 == null) {
                        com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                        map4 = arrayMap11;
                        i6 = i7;
                        z = true;
                        if (obj5 != null) {
                            zzjr();
                            l3 = Long.valueOf(0);
                            e = zzfq.zzb(com_google_android_gms_internal_measurement_zzgi, "_epc");
                            if (e == null) {
                                e = l3;
                            }
                            longValue = ((Long) e).longValue();
                            if (longValue > 0) {
                                l4 = l5;
                                zzjt().zza(str, l5, longValue, com_google_android_gms_internal_measurement_zzgi);
                            } else {
                                zzgt().zzjj().zzg("Complex event with zero extra param count. eventName", str2);
                                l4 = l5;
                            }
                            l2 = l4;
                            str3 = str2;
                            com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr4;
                            com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi;
                            j3 = longValue;
                        }
                    } else {
                        zzjr();
                        str2 = (String) zzfq.zzb(com_google_android_gms_internal_measurement_zzgi8, "_en");
                        if (!TextUtils.isEmpty(str2)) {
                            zzgt().zzjg().zzg("Extra parameter without an event name. eventId", l5);
                            map4 = arrayMap11;
                            i6 = i7;
                        } else if (l5.longValue() != l3.longValue()) {
                            com_google_android_gms_internal_measurement_zzgi9 = com_google_android_gms_internal_measurement_zzgi5;
                            l = l3;
                            j4 = j3;
                            j5 = j4 - 1;
                            if (j5 > 0) {
                                zzjt = zzjt();
                                zzjt.zzaf();
                                zzjt.zzgt().zzjo().zzg("Clearing complex main event info. appId", str6);
                                writableDatabase = zzjt.getWritableDatabase();
                                str7 = "delete from main_event_params where app_id=?";
                                com_google_android_gms_internal_measurement_zzgi10 = com_google_android_gms_internal_measurement_zzgi9;
                                strArr = new String[1];
                                strArr[0] = str6;
                                writableDatabase.execSQL(str7, strArr);
                                com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                                map4 = arrayMap11;
                                i6 = i7;
                                com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi10;
                                z = true;
                            } else {
                                com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                                i6 = i7;
                                com_google_android_gms_internal_measurement_zzgi11 = com_google_android_gms_internal_measurement_zzgi9;
                                z = true;
                                map4 = arrayMap11;
                                zzjt().zza(str, l5, j5, com_google_android_gms_internal_measurement_zzgi11);
                                com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi11;
                            }
                            com_google_android_gms_internal_measurement_zzgjArr = new zzgj[(com_google_android_gms_internal_measurement_zzgi2.zzaxm.length + com_google_android_gms_internal_measurement_zzgjArr4.length)];
                            com_google_android_gms_internal_measurement_zzgjArr2 = com_google_android_gms_internal_measurement_zzgi2.zzaxm;
                            length = com_google_android_gms_internal_measurement_zzgjArr2.length;
                            i5 = 0;
                            i2 = 0;
                            while (i5 < length) {
                                com_google_android_gms_internal_measurement_zzgj = com_google_android_gms_internal_measurement_zzgjArr2[i5];
                                zzjr();
                                com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                if (zzfq.zza(com_google_android_gms_internal_measurement_zzgi, com_google_android_gms_internal_measurement_zzgj.name) != null) {
                                    i = i2 + 1;
                                    com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgj;
                                    i2 = i;
                                }
                                i5++;
                                com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi3;
                            }
                            com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                            if (i2 > 0) {
                                i = com_google_android_gms_internal_measurement_zzgjArr4.length;
                                intValue = 0;
                                while (intValue < i) {
                                    i5 = i2 + 1;
                                    com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgjArr4[intValue];
                                    intValue++;
                                    i2 = i5;
                                }
                                if (i2 == com_google_android_gms_internal_measurement_zzgjArr.length) {
                                    com_google_android_gms_internal_measurement_zzgjArr = (zzgj[]) Arrays.copyOf(com_google_android_gms_internal_measurement_zzgjArr, i2);
                                }
                                com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr;
                                str3 = str2;
                            } else {
                                zzgt().zzjj().zzg("No unique parameters in main event. eventName", str2);
                                str3 = str2;
                                com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr4;
                            }
                            l2 = l;
                            j3 = j5;
                        } else {
                            zza2 = zzjt().zza(str6, l5);
                            if (zza2 != null) {
                                if (zza2.first == null) {
                                    com_google_android_gms_internal_measurement_zzgi5 = (zzgi) zza2.first;
                                    j4 = ((Long) zza2.second).longValue();
                                    zzjr();
                                    l = (Long) zzfq.zzb(com_google_android_gms_internal_measurement_zzgi5, "_eid");
                                    com_google_android_gms_internal_measurement_zzgi9 = com_google_android_gms_internal_measurement_zzgi5;
                                    j5 = j4 - 1;
                                    if (j5 > 0) {
                                        com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                                        i6 = i7;
                                        com_google_android_gms_internal_measurement_zzgi11 = com_google_android_gms_internal_measurement_zzgi9;
                                        z = true;
                                        map4 = arrayMap11;
                                        zzjt().zza(str, l5, j5, com_google_android_gms_internal_measurement_zzgi11);
                                        com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi11;
                                    } else {
                                        zzjt = zzjt();
                                        zzjt.zzaf();
                                        zzjt.zzgt().zzjo().zzg("Clearing complex main event info. appId", str6);
                                        writableDatabase = zzjt.getWritableDatabase();
                                        str7 = "delete from main_event_params where app_id=?";
                                        com_google_android_gms_internal_measurement_zzgi10 = com_google_android_gms_internal_measurement_zzgi9;
                                        strArr = new String[1];
                                        strArr[0] = str6;
                                        writableDatabase.execSQL(str7, strArr);
                                        com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                                        map4 = arrayMap11;
                                        i6 = i7;
                                        com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi10;
                                        z = true;
                                    }
                                    com_google_android_gms_internal_measurement_zzgjArr = new zzgj[(com_google_android_gms_internal_measurement_zzgi2.zzaxm.length + com_google_android_gms_internal_measurement_zzgjArr4.length)];
                                    com_google_android_gms_internal_measurement_zzgjArr2 = com_google_android_gms_internal_measurement_zzgi2.zzaxm;
                                    length = com_google_android_gms_internal_measurement_zzgjArr2.length;
                                    i5 = 0;
                                    i2 = 0;
                                    while (i5 < length) {
                                        com_google_android_gms_internal_measurement_zzgj = com_google_android_gms_internal_measurement_zzgjArr2[i5];
                                        zzjr();
                                        com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                        if (zzfq.zza(com_google_android_gms_internal_measurement_zzgi, com_google_android_gms_internal_measurement_zzgj.name) != null) {
                                            i = i2 + 1;
                                            com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgj;
                                            i2 = i;
                                        }
                                        i5++;
                                        com_google_android_gms_internal_measurement_zzgi2 = com_google_android_gms_internal_measurement_zzgi3;
                                    }
                                    com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi2;
                                    if (i2 > 0) {
                                        zzgt().zzjj().zzg("No unique parameters in main event. eventName", str2);
                                        str3 = str2;
                                        com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr4;
                                    } else {
                                        i = com_google_android_gms_internal_measurement_zzgjArr4.length;
                                        intValue = 0;
                                        while (intValue < i) {
                                            i5 = i2 + 1;
                                            com_google_android_gms_internal_measurement_zzgjArr[i2] = com_google_android_gms_internal_measurement_zzgjArr4[intValue];
                                            intValue++;
                                            i2 = i5;
                                        }
                                        if (i2 == com_google_android_gms_internal_measurement_zzgjArr.length) {
                                            com_google_android_gms_internal_measurement_zzgjArr = (zzgj[]) Arrays.copyOf(com_google_android_gms_internal_measurement_zzgjArr, i2);
                                        }
                                        com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr;
                                        str3 = str2;
                                    }
                                    l2 = l;
                                    j3 = j5;
                                }
                            }
                            map4 = arrayMap11;
                            i6 = i7;
                            zzgt().zzjg().zze("Extra parameter without existing main event. eventName, eventId", str2, l5);
                        }
                        i4 = length2;
                        arrayMap13 = arrayMap12;
                        map15 = arrayMap7;
                        hashSet3 = hashSet;
                        map13 = map;
                        map14 = map2;
                        map16 = map3;
                        j2 = j3;
                        map10 = map4;
                        i5 = i6 + 1;
                        com_google_android_gms_internal_measurement_zzgiArr2 = com_google_android_gms_internal_measurement_zzgiArr;
                        hashSet = hashSet3;
                        length2 = i4;
                        arrayMap12 = arrayMap13;
                        arrayMap11 = map10;
                        map = map13;
                        map2 = map14;
                        arrayMap7 = map15;
                        map3 = map16;
                        com_google_android_gms_measurement_internal_zzk = this;
                        com_google_android_gms_internal_measurement_zzgoArr3 = com_google_android_gms_internal_measurement_zzgoArr;
                        str6 = str;
                    }
                    zzg = zzjt().zzg(str6, com_google_android_gms_internal_measurement_zzgi.name);
                    if (zzg != null) {
                        i4 = length2;
                        arrayMap = arrayMap12;
                        map27 = arrayMap7;
                        com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi;
                        com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr3;
                        str4 = str6;
                        hashSet2 = hashSet;
                        map5 = map;
                        map25 = map2;
                        map26 = map3;
                        zzg = new zzaa(zzg.zztt, zzg.name, zzg.zzaih + 1, zzg.zzaii + 1, zzg.zzaij, zzg.zzaik, zzg.zzail, zzg.zzaim, zzg.zzain, zzg.zzaio);
                    } else {
                        zzgt().zzjj().zze("Event aggregate wasn't created during raw event logging. appId, event", zzaq.zzby(str), zzgq().zzbv(str3));
                        i4 = length2;
                        arrayMap = arrayMap12;
                        map25 = map2;
                        map26 = map3;
                        map5 = map;
                        map27 = arrayMap7;
                        hashSet2 = hashSet;
                        com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi;
                        com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr3;
                        str4 = str6;
                        com_google_android_gms_measurement_internal_zzaa = new zzaa(str, com_google_android_gms_internal_measurement_zzgi.name, 1, 1, com_google_android_gms_internal_measurement_zzgi.zzaxn.longValue(), 0, null, null, null, null);
                    }
                    zzjt().zza(zzg);
                    j = zzg.zzaih;
                    arrayMap8 = arrayMap;
                    map6 = (Map) arrayMap8.get(str3);
                    if (map6 == null) {
                        map6 = zzjt().zzl(str4, str3);
                        if (map6 == null) {
                            map6 = new ArrayMap();
                        }
                        arrayMap8.put(str3, map6);
                    }
                    map7 = map6;
                    it = map7.keySet().iterator();
                    while (it.hasNext()) {
                        intValue2 = ((Integer) it.next()).intValue();
                        hashSet3 = hashSet2;
                        if (hashSet3.contains(Integer.valueOf(intValue2))) {
                            map8 = map27;
                            com_google_android_gms_internal_measurement_zzgg = (zzgg) map8.get(Integer.valueOf(intValue2));
                            arrayMap11 = map26;
                            bitSet = (BitSet) arrayMap11.get(Integer.valueOf(intValue2));
                            com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi6;
                            map28 = arrayMap8;
                            arrayMap8 = map25;
                            bitSet2 = (BitSet) arrayMap8.get(Integer.valueOf(intValue2));
                            if (zzbd) {
                                bitSet10 = bitSet2;
                                it6 = it;
                                map29 = map4;
                                arrayMap7 = map5;
                                map9 = null;
                                map30 = null;
                            } else {
                                bitSet10 = bitSet2;
                                it6 = it;
                                arrayMap7 = map5;
                                map30 = (Map) arrayMap7.get(Integer.valueOf(intValue2));
                                map29 = map4;
                                map9 = (Map) map29.get(Integer.valueOf(intValue2));
                            }
                            if (com_google_android_gms_internal_measurement_zzgg != null) {
                                map31 = map9;
                                map10 = map29;
                                bitSet2 = bitSet10;
                                arrayMap2 = map30;
                                map29 = map31;
                            } else {
                                com_google_android_gms_internal_measurement_zzgg = new zzgg();
                                map8.put(Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzgg);
                                com_google_android_gms_internal_measurement_zzgg.zzaxh = Boolean.valueOf(z);
                                bitSet = new BitSet();
                                arrayMap11.put(Integer.valueOf(intValue2), bitSet);
                                bitSet3 = new BitSet();
                                map31 = map9;
                                arrayMap8.put(Integer.valueOf(intValue2), bitSet3);
                                if (zzbd) {
                                    map10 = map29;
                                    arrayMap2 = map30;
                                    map29 = map31;
                                    bitSet2 = bitSet3;
                                } else {
                                    arrayMap3 = new ArrayMap();
                                    bitSet4 = bitSet3;
                                    arrayMap7.put(Integer.valueOf(intValue2), arrayMap3);
                                    arrayMap4 = new ArrayMap();
                                    arrayMap5 = arrayMap3;
                                    map29.put(Integer.valueOf(intValue2), arrayMap4);
                                    map10 = map29;
                                    bitSet2 = bitSet4;
                                    arrayMap2 = arrayMap5;
                                    map29 = arrayMap4;
                                }
                            }
                            for (zzfy com_google_android_gms_internal_measurement_zzfy32222 : (List) r11.get(Integer.valueOf(intValue2))) {
                                bitSet5 = bitSet2;
                                map11 = map7;
                                if (zzgt().isLoggable(2)) {
                                    map12 = arrayMap11;
                                    map13 = arrayMap7;
                                } else {
                                    map12 = arrayMap11;
                                    map13 = arrayMap7;
                                    zzgt().zzjo().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy32222.zzavx, zzgq().zzbv(com_google_android_gms_internal_measurement_zzfy32222.zzavy));
                                    zzgt().zzjo().zzg("Filter definition", zzjr().zza(com_google_android_gms_internal_measurement_zzfy32222));
                                }
                                if (com_google_android_gms_internal_measurement_zzfy32222.zzavx != null) {
                                    if (com_google_android_gms_internal_measurement_zzfy32222.zzavx.intValue() > 256) {
                                        if (zzbd) {
                                            str5 = str3;
                                            com_google_android_gms_internal_measurement_zzfy2 = com_google_android_gms_internal_measurement_zzfy32222;
                                            map14 = arrayMap8;
                                            map15 = map8;
                                            com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                            arrayMap7 = arrayMap2;
                                            bitSet6 = bitSet5;
                                            map16 = map12;
                                            bitSet11 = bitSet;
                                            if (bitSet11.get(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue())) {
                                                zza = zza(com_google_android_gms_internal_measurement_zzfy2, str5, com_google_android_gms_internal_measurement_zzgjArr3, j);
                                                if (zza != null) {
                                                }
                                                zzgt().zzjo().zzg("Event filter result", zza != null ? "null" : zza);
                                                if (zza != null) {
                                                    bitSet6.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                    if (zza.booleanValue()) {
                                                        bitSet11.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                    }
                                                } else {
                                                    hashSet3.add(Integer.valueOf(intValue2));
                                                }
                                            } else {
                                                zzgt().zzjo().zze("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy2.zzavx);
                                            }
                                        } else {
                                            if (com_google_android_gms_internal_measurement_zzfy32222 == null) {
                                            }
                                            if (com_google_android_gms_internal_measurement_zzfy32222 == null) {
                                            }
                                            if (!bitSet.get(com_google_android_gms_internal_measurement_zzfy32222.zzavx.intValue())) {
                                            }
                                            com_google_android_gms_internal_measurement_zzfy = com_google_android_gms_internal_measurement_zzfy32222;
                                            bitSet6 = bitSet5;
                                            map14 = arrayMap8;
                                            map15 = map8;
                                            com_google_android_gms_internal_measurement_zzgoArr2 = com_google_android_gms_internal_measurement_zzgoArr;
                                            com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                            bitSet11 = bitSet;
                                            str5 = str3;
                                            com_google_android_gms_internal_measurement_zzfy2 = com_google_android_gms_internal_measurement_zzfy;
                                            arrayMap6 = arrayMap2;
                                            map16 = map12;
                                            zza = zza(com_google_android_gms_internal_measurement_zzfy, str3, com_google_android_gms_internal_measurement_zzgjArr3, j);
                                            if (zza != null) {
                                            }
                                            zzgt().zzjo().zzg("Event filter result", zza != null ? "null" : zza);
                                            if (zza != null) {
                                                bitSet6.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                if (zza.booleanValue()) {
                                                    bitSet11.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                    if (obj3 == null) {
                                                        arrayMap7 = arrayMap6;
                                                        zza(arrayMap7, com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgi4.zzaxn.longValue());
                                                    } else {
                                                        zzb(map29, com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgi4.zzaxn.longValue());
                                                    }
                                                }
                                            } else {
                                                hashSet3.add(Integer.valueOf(intValue2));
                                            }
                                            bitSet = bitSet11;
                                            bitSet2 = bitSet6;
                                            com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                            map7 = map11;
                                            arrayMap7 = map13;
                                            arrayMap8 = map14;
                                            map8 = map15;
                                            arrayMap11 = map16;
                                            str3 = str5;
                                            arrayMap2 = arrayMap6;
                                            com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                            str4 = str;
                                        }
                                        bitSet = bitSet11;
                                        bitSet2 = bitSet6;
                                        arrayMap2 = arrayMap7;
                                        com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                        map7 = map11;
                                        arrayMap7 = map13;
                                        arrayMap8 = map14;
                                        map8 = map15;
                                        arrayMap11 = map16;
                                        str3 = str5;
                                        com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                        str4 = str;
                                    }
                                }
                                str5 = str3;
                                map14 = arrayMap8;
                                map15 = map8;
                                com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                arrayMap7 = arrayMap2;
                                bitSet6 = bitSet5;
                                map16 = map12;
                                bitSet11 = bitSet;
                                zzgt().zzjj().zze("Invalid event filter ID. appId, id", zzaq.zzby(str), String.valueOf(com_google_android_gms_internal_measurement_zzfy32222.zzavx));
                                bitSet = bitSet11;
                                bitSet2 = bitSet6;
                                arrayMap2 = arrayMap7;
                                com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                map7 = map11;
                                arrayMap7 = map13;
                                arrayMap8 = map14;
                                map8 = map15;
                                arrayMap11 = map16;
                                str3 = str5;
                                com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                str4 = str;
                            }
                            map26 = arrayMap11;
                            map25 = arrayMap8;
                            map5 = arrayMap7;
                            hashSet2 = hashSet3;
                            com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi7;
                            arrayMap8 = map28;
                            it = it6;
                            map4 = map10;
                            map27 = map8;
                            com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                            str4 = str;
                            com_google_android_gms_measurement_internal_zzk = this;
                        } else {
                            zzgt().zzjo().zzg("Skipping failed audience ID", Integer.valueOf(intValue2));
                            hashSet2 = hashSet3;
                        }
                    }
                    arrayMap13 = arrayMap8;
                    map10 = map4;
                    map14 = map25;
                    map16 = map26;
                    hashSet3 = hashSet2;
                    map15 = map27;
                    map13 = map5;
                    l3 = l2;
                    j2 = j3;
                    com_google_android_gms_internal_measurement_zzgi5 = com_google_android_gms_internal_measurement_zzgi3;
                    i5 = i6 + 1;
                    com_google_android_gms_internal_measurement_zzgiArr2 = com_google_android_gms_internal_measurement_zzgiArr;
                    hashSet = hashSet3;
                    length2 = i4;
                    arrayMap12 = arrayMap13;
                    arrayMap11 = map10;
                    map = map13;
                    map2 = map14;
                    arrayMap7 = map15;
                    map3 = map16;
                    com_google_android_gms_measurement_internal_zzk = this;
                    com_google_android_gms_internal_measurement_zzgoArr3 = com_google_android_gms_internal_measurement_zzgoArr;
                    str6 = str;
                } else {
                    i6 = i5;
                    com_google_android_gms_internal_measurement_zzgi = com_google_android_gms_internal_measurement_zzgi8;
                    map4 = arrayMap11;
                    z = true;
                }
                com_google_android_gms_internal_measurement_zzgi3 = com_google_android_gms_internal_measurement_zzgi5;
                l2 = l3;
                str3 = str2;
                com_google_android_gms_internal_measurement_zzgjArr3 = com_google_android_gms_internal_measurement_zzgjArr4;
                zzg = zzjt().zzg(str6, com_google_android_gms_internal_measurement_zzgi.name);
                if (zzg != null) {
                    zzgt().zzjj().zze("Event aggregate wasn't created during raw event logging. appId, event", zzaq.zzby(str), zzgq().zzbv(str3));
                    i4 = length2;
                    arrayMap = arrayMap12;
                    map25 = map2;
                    map26 = map3;
                    map5 = map;
                    map27 = arrayMap7;
                    hashSet2 = hashSet;
                    com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi;
                    com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr3;
                    str4 = str6;
                    com_google_android_gms_measurement_internal_zzaa = new zzaa(str, com_google_android_gms_internal_measurement_zzgi.name, 1, 1, com_google_android_gms_internal_measurement_zzgi.zzaxn.longValue(), 0, null, null, null, null);
                } else {
                    i4 = length2;
                    arrayMap = arrayMap12;
                    map27 = arrayMap7;
                    com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi;
                    com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr3;
                    str4 = str6;
                    hashSet2 = hashSet;
                    map5 = map;
                    map25 = map2;
                    map26 = map3;
                    zzg = new zzaa(zzg.zztt, zzg.name, zzg.zzaih + 1, zzg.zzaii + 1, zzg.zzaij, zzg.zzaik, zzg.zzail, zzg.zzaim, zzg.zzain, zzg.zzaio);
                }
                zzjt().zza(zzg);
                j = zzg.zzaih;
                arrayMap8 = arrayMap;
                map6 = (Map) arrayMap8.get(str3);
                if (map6 == null) {
                    map6 = zzjt().zzl(str4, str3);
                    if (map6 == null) {
                        map6 = new ArrayMap();
                    }
                    arrayMap8.put(str3, map6);
                }
                map7 = map6;
                it = map7.keySet().iterator();
                while (it.hasNext()) {
                    intValue2 = ((Integer) it.next()).intValue();
                    hashSet3 = hashSet2;
                    if (hashSet3.contains(Integer.valueOf(intValue2))) {
                        zzgt().zzjo().zzg("Skipping failed audience ID", Integer.valueOf(intValue2));
                        hashSet2 = hashSet3;
                    } else {
                        map8 = map27;
                        com_google_android_gms_internal_measurement_zzgg = (zzgg) map8.get(Integer.valueOf(intValue2));
                        arrayMap11 = map26;
                        bitSet = (BitSet) arrayMap11.get(Integer.valueOf(intValue2));
                        com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi6;
                        map28 = arrayMap8;
                        arrayMap8 = map25;
                        bitSet2 = (BitSet) arrayMap8.get(Integer.valueOf(intValue2));
                        if (zzbd) {
                            bitSet10 = bitSet2;
                            it6 = it;
                            arrayMap7 = map5;
                            map30 = (Map) arrayMap7.get(Integer.valueOf(intValue2));
                            map29 = map4;
                            map9 = (Map) map29.get(Integer.valueOf(intValue2));
                        } else {
                            bitSet10 = bitSet2;
                            it6 = it;
                            map29 = map4;
                            arrayMap7 = map5;
                            map9 = null;
                            map30 = null;
                        }
                        if (com_google_android_gms_internal_measurement_zzgg != null) {
                            com_google_android_gms_internal_measurement_zzgg = new zzgg();
                            map8.put(Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzgg);
                            com_google_android_gms_internal_measurement_zzgg.zzaxh = Boolean.valueOf(z);
                            bitSet = new BitSet();
                            arrayMap11.put(Integer.valueOf(intValue2), bitSet);
                            bitSet3 = new BitSet();
                            map31 = map9;
                            arrayMap8.put(Integer.valueOf(intValue2), bitSet3);
                            if (zzbd) {
                                arrayMap3 = new ArrayMap();
                                bitSet4 = bitSet3;
                                arrayMap7.put(Integer.valueOf(intValue2), arrayMap3);
                                arrayMap4 = new ArrayMap();
                                arrayMap5 = arrayMap3;
                                map29.put(Integer.valueOf(intValue2), arrayMap4);
                                map10 = map29;
                                bitSet2 = bitSet4;
                                arrayMap2 = arrayMap5;
                                map29 = arrayMap4;
                            } else {
                                map10 = map29;
                                arrayMap2 = map30;
                                map29 = map31;
                                bitSet2 = bitSet3;
                            }
                        } else {
                            map31 = map9;
                            map10 = map29;
                            bitSet2 = bitSet10;
                            arrayMap2 = map30;
                            map29 = map31;
                        }
                        for (zzfy com_google_android_gms_internal_measurement_zzfy322222 : (List) r11.get(Integer.valueOf(intValue2))) {
                            bitSet5 = bitSet2;
                            map11 = map7;
                            if (zzgt().isLoggable(2)) {
                                map12 = arrayMap11;
                                map13 = arrayMap7;
                                zzgt().zzjo().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy322222.zzavx, zzgq().zzbv(com_google_android_gms_internal_measurement_zzfy322222.zzavy));
                                zzgt().zzjo().zzg("Filter definition", zzjr().zza(com_google_android_gms_internal_measurement_zzfy322222));
                            } else {
                                map12 = arrayMap11;
                                map13 = arrayMap7;
                            }
                            if (com_google_android_gms_internal_measurement_zzfy322222.zzavx != null) {
                                if (com_google_android_gms_internal_measurement_zzfy322222.zzavx.intValue() > 256) {
                                    if (zzbd) {
                                        if (com_google_android_gms_internal_measurement_zzfy322222 == null) {
                                        }
                                        if (com_google_android_gms_internal_measurement_zzfy322222 == null) {
                                        }
                                        if (!bitSet.get(com_google_android_gms_internal_measurement_zzfy322222.zzavx.intValue())) {
                                        }
                                        com_google_android_gms_internal_measurement_zzfy = com_google_android_gms_internal_measurement_zzfy322222;
                                        bitSet6 = bitSet5;
                                        map14 = arrayMap8;
                                        map15 = map8;
                                        com_google_android_gms_internal_measurement_zzgoArr2 = com_google_android_gms_internal_measurement_zzgoArr;
                                        com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                        bitSet11 = bitSet;
                                        str5 = str3;
                                        com_google_android_gms_internal_measurement_zzfy2 = com_google_android_gms_internal_measurement_zzfy;
                                        arrayMap6 = arrayMap2;
                                        map16 = map12;
                                        zza = zza(com_google_android_gms_internal_measurement_zzfy, str3, com_google_android_gms_internal_measurement_zzgjArr3, j);
                                        if (zza != null) {
                                        }
                                        zzgt().zzjo().zzg("Event filter result", zza != null ? "null" : zza);
                                        if (zza != null) {
                                            hashSet3.add(Integer.valueOf(intValue2));
                                        } else {
                                            bitSet6.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                            if (zza.booleanValue()) {
                                                bitSet11.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                if (obj3 == null) {
                                                    zzb(map29, com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgi4.zzaxn.longValue());
                                                } else {
                                                    arrayMap7 = arrayMap6;
                                                    zza(arrayMap7, com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgi4.zzaxn.longValue());
                                                }
                                            }
                                        }
                                        bitSet = bitSet11;
                                        bitSet2 = bitSet6;
                                        com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                        map7 = map11;
                                        arrayMap7 = map13;
                                        arrayMap8 = map14;
                                        map8 = map15;
                                        arrayMap11 = map16;
                                        str3 = str5;
                                        arrayMap2 = arrayMap6;
                                        com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                        str4 = str;
                                    } else {
                                        str5 = str3;
                                        com_google_android_gms_internal_measurement_zzfy2 = com_google_android_gms_internal_measurement_zzfy322222;
                                        map14 = arrayMap8;
                                        map15 = map8;
                                        com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                                        arrayMap7 = arrayMap2;
                                        bitSet6 = bitSet5;
                                        map16 = map12;
                                        bitSet11 = bitSet;
                                        if (bitSet11.get(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue())) {
                                            zzgt().zzjo().zze("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), com_google_android_gms_internal_measurement_zzfy2.zzavx);
                                        } else {
                                            zza = zza(com_google_android_gms_internal_measurement_zzfy2, str5, com_google_android_gms_internal_measurement_zzgjArr3, j);
                                            if (zza != null) {
                                            }
                                            zzgt().zzjo().zzg("Event filter result", zza != null ? "null" : zza);
                                            if (zza != null) {
                                                hashSet3.add(Integer.valueOf(intValue2));
                                            } else {
                                                bitSet6.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                if (zza.booleanValue()) {
                                                    bitSet11.set(com_google_android_gms_internal_measurement_zzfy2.zzavx.intValue());
                                                }
                                            }
                                        }
                                    }
                                    bitSet = bitSet11;
                                    bitSet2 = bitSet6;
                                    arrayMap2 = arrayMap7;
                                    com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                                    map7 = map11;
                                    arrayMap7 = map13;
                                    arrayMap8 = map14;
                                    map8 = map15;
                                    arrayMap11 = map16;
                                    str3 = str5;
                                    com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                                    str4 = str;
                                }
                            }
                            str5 = str3;
                            map14 = arrayMap8;
                            map15 = map8;
                            com_google_android_gms_internal_measurement_zzgi4 = com_google_android_gms_internal_measurement_zzgi7;
                            arrayMap7 = arrayMap2;
                            bitSet6 = bitSet5;
                            map16 = map12;
                            bitSet11 = bitSet;
                            zzgt().zzjj().zze("Invalid event filter ID. appId, id", zzaq.zzby(str), String.valueOf(com_google_android_gms_internal_measurement_zzfy322222.zzavx));
                            bitSet = bitSet11;
                            bitSet2 = bitSet6;
                            arrayMap2 = arrayMap7;
                            com_google_android_gms_internal_measurement_zzgi7 = com_google_android_gms_internal_measurement_zzgi4;
                            map7 = map11;
                            arrayMap7 = map13;
                            arrayMap8 = map14;
                            map8 = map15;
                            arrayMap11 = map16;
                            str3 = str5;
                            com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                            str4 = str;
                        }
                        map26 = arrayMap11;
                        map25 = arrayMap8;
                        map5 = arrayMap7;
                        hashSet2 = hashSet3;
                        com_google_android_gms_internal_measurement_zzgi6 = com_google_android_gms_internal_measurement_zzgi7;
                        arrayMap8 = map28;
                        it = it6;
                        map4 = map10;
                        map27 = map8;
                        com_google_android_gms_internal_measurement_zzgoArr4 = com_google_android_gms_internal_measurement_zzgoArr;
                        str4 = str;
                        com_google_android_gms_measurement_internal_zzk = this;
                    }
                }
                arrayMap13 = arrayMap8;
                map10 = map4;
                map14 = map25;
                map16 = map26;
                hashSet3 = hashSet2;
                map15 = map27;
                map13 = map5;
                l3 = l2;
                j2 = j3;
                com_google_android_gms_internal_measurement_zzgi5 = com_google_android_gms_internal_measurement_zzgi3;
                i5 = i6 + 1;
                com_google_android_gms_internal_measurement_zzgiArr2 = com_google_android_gms_internal_measurement_zzgiArr;
                hashSet = hashSet3;
                length2 = i4;
                arrayMap12 = arrayMap13;
                arrayMap11 = map10;
                map = map13;
                map2 = map14;
                arrayMap7 = map15;
                map3 = map16;
                com_google_android_gms_measurement_internal_zzk = this;
                com_google_android_gms_internal_measurement_zzgoArr3 = com_google_android_gms_internal_measurement_zzgoArr;
                str6 = str;
            }
        }
        map10 = arrayMap11;
        map15 = arrayMap7;
        hashSet3 = hashSet;
        map13 = map;
        map14 = map2;
        map16 = map3;
        zzgo[] com_google_android_gms_internal_measurement_zzgoArr5 = com_google_android_gms_internal_measurement_zzgoArr;
        if (com_google_android_gms_internal_measurement_zzgoArr5 != null) {
            zzbr = new ArrayMap();
            intValue = com_google_android_gms_internal_measurement_zzgoArr5.length;
            length = 0;
            while (length < intValue) {
                String str8;
                Map map32;
                int i8;
                zzgo com_google_android_gms_internal_measurement_zzgo = com_google_android_gms_internal_measurement_zzgoArr5[length];
                map17 = (Map) zzbr.get(com_google_android_gms_internal_measurement_zzgo.name);
                if (map17 == null) {
                    map17 = zzjt().zzm(str, com_google_android_gms_internal_measurement_zzgo.name);
                    if (map17 == null) {
                        map17 = new ArrayMap();
                    }
                    zzbr.put(com_google_android_gms_internal_measurement_zzgo.name, map17);
                } else {
                    str8 = str;
                }
                Iterator it7 = map17.keySet().iterator();
                while (it7.hasNext()) {
                    length2 = ((Integer) it7.next()).intValue();
                    if (hashSet3.contains(Integer.valueOf(length2))) {
                        zzgt().zzjo().zzg("Skipping failed audience ID", Integer.valueOf(length2));
                    } else {
                        Map map33;
                        Map map34;
                        Iterator it8;
                        zzgb com_google_android_gms_internal_measurement_zzgb;
                        Map map35;
                        Iterator it9;
                        Object obj6;
                        Object obj7;
                        Boolean zza3;
                        zzas zzjo;
                        arrayMap8 = map15;
                        zzgg com_google_android_gms_internal_measurement_zzgg2 = (zzgg) arrayMap8.get(Integer.valueOf(length2));
                        arrayMap7 = map16;
                        bitSet6 = (BitSet) arrayMap7.get(Integer.valueOf(length2));
                        map8 = map14;
                        BitSet bitSet12 = (BitSet) map8.get(Integer.valueOf(length2));
                        if (zzbd) {
                            map32 = zzbr;
                            map6 = map13;
                            map33 = (Map) map6.get(Integer.valueOf(length2));
                            i8 = intValue;
                            map9 = map10;
                            zzbr = (Map) map9.get(Integer.valueOf(length2));
                        } else {
                            map32 = zzbr;
                            i8 = intValue;
                            map9 = map10;
                            map6 = map13;
                            zzbr = null;
                            map33 = null;
                        }
                        if (com_google_android_gms_internal_measurement_zzgg2 == null) {
                            com_google_android_gms_internal_measurement_zzgg2 = new zzgg();
                            arrayMap8.put(Integer.valueOf(length2), com_google_android_gms_internal_measurement_zzgg2);
                            com_google_android_gms_internal_measurement_zzgg2.zzaxh = Boolean.valueOf(true);
                            bitSet6 = new BitSet();
                            arrayMap7.put(Integer.valueOf(length2), bitSet6);
                            bitSet12 = new BitSet();
                            map8.put(Integer.valueOf(length2), bitSet12);
                            if (zzbd) {
                                ArrayMap arrayMap14 = new ArrayMap();
                                map6.put(Integer.valueOf(length2), arrayMap14);
                                arrayMap9 = new ArrayMap();
                                ArrayMap arrayMap15 = arrayMap14;
                                map9.put(Integer.valueOf(length2), arrayMap9);
                                map34 = map9;
                                zzbr = arrayMap15;
                                it2 = ((List) map17.get(Integer.valueOf(length2))).iterator();
                                while (it2.hasNext()) {
                                    it8 = it2;
                                    com_google_android_gms_internal_measurement_zzgb = (zzgb) it2.next();
                                    map35 = map17;
                                    it9 = it7;
                                    if (zzgt().isLoggable(2)) {
                                        map18 = map6;
                                        map19 = arrayMap8;
                                        map20 = map8;
                                    } else {
                                        map18 = map6;
                                        map20 = map8;
                                        map19 = arrayMap8;
                                        zzgt().zzjo().zzd("Evaluating filter. audience, filter, property", Integer.valueOf(length2), com_google_android_gms_internal_measurement_zzgb.zzavx, zzgq().zzbx(com_google_android_gms_internal_measurement_zzgb.zzawn));
                                        zzgt().zzjo().zzg("Filter definition", zzjr().zza(com_google_android_gms_internal_measurement_zzgb));
                                    }
                                    if (com_google_android_gms_internal_measurement_zzgb.zzavx == null) {
                                        if (com_google_android_gms_internal_measurement_zzgb.zzavx.intValue() > 256) {
                                            if (zzbd) {
                                                map21 = arrayMap7;
                                                map22 = map34;
                                                com_google_android_gms_measurement_internal_zzk = this;
                                                if (bitSet6.get(com_google_android_gms_internal_measurement_zzgb.zzavx.intValue())) {
                                                    zza = zza(com_google_android_gms_internal_measurement_zzgb, com_google_android_gms_internal_measurement_zzgo);
                                                    zzgt().zzjo().zzg("Property filter result", zza != null ? "null" : zza);
                                                    if (zza != null) {
                                                        hashSet3.add(Integer.valueOf(length2));
                                                    } else {
                                                        bitSet12.set(com_google_android_gms_internal_measurement_zzgb.zzavx.intValue());
                                                        if (zza.booleanValue()) {
                                                            bitSet6.set(com_google_android_gms_internal_measurement_zzgb.zzavx.intValue());
                                                        }
                                                    }
                                                } else {
                                                    zzgt().zzjo().zze("Property filter already evaluated true. audience ID, filter ID", Integer.valueOf(length2), com_google_android_gms_internal_measurement_zzgb.zzavx);
                                                }
                                            } else {
                                                obj6 = (com_google_android_gms_internal_measurement_zzgb == null && com_google_android_gms_internal_measurement_zzgb.zzavu != null && com_google_android_gms_internal_measurement_zzgb.zzavu.booleanValue()) ? 1 : null;
                                                obj7 = (com_google_android_gms_internal_measurement_zzgb == null && com_google_android_gms_internal_measurement_zzgb.zzavv != null && com_google_android_gms_internal_measurement_zzgb.zzavv.booleanValue()) ? 1 : null;
                                                if (!bitSet6.get(com_google_android_gms_internal_measurement_zzgb.zzavx.intValue()) && obj6 == null && obj7 == null) {
                                                    zzgt().zzjo().zze("Property filter already evaluated true and it is not associated with a dynamic audience. audience ID, filter ID", Integer.valueOf(length2), com_google_android_gms_internal_measurement_zzgb.zzavx);
                                                    it2 = it8;
                                                    map17 = map35;
                                                    it7 = it9;
                                                    map6 = map18;
                                                    map8 = map20;
                                                    arrayMap8 = map19;
                                                    str8 = str;
                                                } else {
                                                    arrayMap8 = map34;
                                                    zza3 = zza(com_google_android_gms_internal_measurement_zzgb, com_google_android_gms_internal_measurement_zzgo);
                                                    zzjo = zzgt().zzjo();
                                                    map22 = arrayMap8;
                                                    str2 = "Property filter result";
                                                    if (zza3 != null) {
                                                        map21 = arrayMap7;
                                                        obj = "null";
                                                    } else {
                                                        map21 = arrayMap7;
                                                        obj = zza3;
                                                    }
                                                    zzjo.zzg(str2, obj);
                                                    if (zza3 != null) {
                                                        hashSet3.add(Integer.valueOf(length2));
                                                    } else {
                                                        bitSet12.set(com_google_android_gms_internal_measurement_zzgb.zzavx.intValue());
                                                        bitSet6.set(com_google_android_gms_internal_measurement_zzgb.zzavx.intValue(), zza3.booleanValue());
                                                        if (zza3.booleanValue() && !((obj6 == null && obj7 == null) || com_google_android_gms_internal_measurement_zzgo.zzazg == null)) {
                                                            if (obj7 == null) {
                                                                zzb(arrayMap9, com_google_android_gms_internal_measurement_zzgb.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgo.zzazg.longValue());
                                                            } else {
                                                                zza(zzbr, com_google_android_gms_internal_measurement_zzgb.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgo.zzazg.longValue());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            it2 = it8;
                                            map17 = map35;
                                            it7 = it9;
                                            map6 = map18;
                                            map8 = map20;
                                            arrayMap8 = map19;
                                            map34 = map22;
                                            arrayMap7 = map21;
                                            str8 = str;
                                        }
                                    }
                                    map21 = arrayMap7;
                                    map22 = map34;
                                    com_google_android_gms_measurement_internal_zzk = this;
                                    zzgt().zzjj().zze("Invalid property filter ID. appId, id", zzaq.zzby(str), String.valueOf(com_google_android_gms_internal_measurement_zzgb.zzavx));
                                    hashSet3.add(Integer.valueOf(length2));
                                    zzbr = map32;
                                    intValue = i8;
                                    map17 = map35;
                                    it7 = it9;
                                    map13 = map18;
                                    map14 = map20;
                                    map15 = map19;
                                    map10 = map22;
                                    map16 = map21;
                                }
                                com_google_android_gms_measurement_internal_zzk = this;
                                map13 = map6;
                                map15 = arrayMap8;
                                map16 = arrayMap7;
                                map14 = map8;
                                zzbr = map32;
                                intValue = i8;
                                map10 = map34;
                                com_google_android_gms_internal_measurement_zzgoArr5 = com_google_android_gms_internal_measurement_zzgoArr;
                                str8 = str;
                            }
                        }
                        arrayMap9 = zzbr;
                        map34 = map9;
                        zzbr = map33;
                        it2 = ((List) map17.get(Integer.valueOf(length2))).iterator();
                        while (it2.hasNext()) {
                            it8 = it2;
                            com_google_android_gms_internal_measurement_zzgb = (zzgb) it2.next();
                            map35 = map17;
                            it9 = it7;
                            if (zzgt().isLoggable(2)) {
                                map18 = map6;
                                map19 = arrayMap8;
                                map20 = map8;
                            } else {
                                map18 = map6;
                                map20 = map8;
                                map19 = arrayMap8;
                                zzgt().zzjo().zzd("Evaluating filter. audience, filter, property", Integer.valueOf(length2), com_google_android_gms_internal_measurement_zzgb.zzavx, zzgq().zzbx(com_google_android_gms_internal_measurement_zzgb.zzawn));
                                zzgt().zzjo().zzg("Filter definition", zzjr().zza(com_google_android_gms_internal_measurement_zzgb));
                            }
                            if (com_google_android_gms_internal_measurement_zzgb.zzavx == null) {
                                if (com_google_android_gms_internal_measurement_zzgb.zzavx.intValue() > 256) {
                                    if (zzbd) {
                                        map21 = arrayMap7;
                                        map22 = map34;
                                        com_google_android_gms_measurement_internal_zzk = this;
                                        if (bitSet6.get(com_google_android_gms_internal_measurement_zzgb.zzavx.intValue())) {
                                            zza = zza(com_google_android_gms_internal_measurement_zzgb, com_google_android_gms_internal_measurement_zzgo);
                                            if (zza != null) {
                                            }
                                            zzgt().zzjo().zzg("Property filter result", zza != null ? "null" : zza);
                                            if (zza != null) {
                                                bitSet12.set(com_google_android_gms_internal_measurement_zzgb.zzavx.intValue());
                                                if (zza.booleanValue()) {
                                                    bitSet6.set(com_google_android_gms_internal_measurement_zzgb.zzavx.intValue());
                                                }
                                            } else {
                                                hashSet3.add(Integer.valueOf(length2));
                                            }
                                        } else {
                                            zzgt().zzjo().zze("Property filter already evaluated true. audience ID, filter ID", Integer.valueOf(length2), com_google_android_gms_internal_measurement_zzgb.zzavx);
                                        }
                                    } else {
                                        if (com_google_android_gms_internal_measurement_zzgb == null) {
                                        }
                                        if (com_google_android_gms_internal_measurement_zzgb == null) {
                                        }
                                        if (!bitSet6.get(com_google_android_gms_internal_measurement_zzgb.zzavx.intValue())) {
                                        }
                                        arrayMap8 = map34;
                                        zza3 = zza(com_google_android_gms_internal_measurement_zzgb, com_google_android_gms_internal_measurement_zzgo);
                                        zzjo = zzgt().zzjo();
                                        map22 = arrayMap8;
                                        str2 = "Property filter result";
                                        if (zza3 != null) {
                                            map21 = arrayMap7;
                                            obj = zza3;
                                        } else {
                                            map21 = arrayMap7;
                                            obj = "null";
                                        }
                                        zzjo.zzg(str2, obj);
                                        if (zza3 != null) {
                                            bitSet12.set(com_google_android_gms_internal_measurement_zzgb.zzavx.intValue());
                                            bitSet6.set(com_google_android_gms_internal_measurement_zzgb.zzavx.intValue(), zza3.booleanValue());
                                            if (obj7 == null) {
                                                zza(zzbr, com_google_android_gms_internal_measurement_zzgb.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgo.zzazg.longValue());
                                            } else {
                                                zzb(arrayMap9, com_google_android_gms_internal_measurement_zzgb.zzavx.intValue(), com_google_android_gms_internal_measurement_zzgo.zzazg.longValue());
                                            }
                                        } else {
                                            hashSet3.add(Integer.valueOf(length2));
                                        }
                                    }
                                    it2 = it8;
                                    map17 = map35;
                                    it7 = it9;
                                    map6 = map18;
                                    map8 = map20;
                                    arrayMap8 = map19;
                                    map34 = map22;
                                    arrayMap7 = map21;
                                    str8 = str;
                                }
                            }
                            map21 = arrayMap7;
                            map22 = map34;
                            com_google_android_gms_measurement_internal_zzk = this;
                            zzgt().zzjj().zze("Invalid property filter ID. appId, id", zzaq.zzby(str), String.valueOf(com_google_android_gms_internal_measurement_zzgb.zzavx));
                            hashSet3.add(Integer.valueOf(length2));
                            zzbr = map32;
                            intValue = i8;
                            map17 = map35;
                            it7 = it9;
                            map13 = map18;
                            map14 = map20;
                            map15 = map19;
                            map10 = map22;
                            map16 = map21;
                        }
                        com_google_android_gms_measurement_internal_zzk = this;
                        map13 = map6;
                        map15 = arrayMap8;
                        map16 = arrayMap7;
                        map14 = map8;
                        zzbr = map32;
                        intValue = i8;
                        map10 = map34;
                        com_google_android_gms_internal_measurement_zzgoArr5 = com_google_android_gms_internal_measurement_zzgoArr;
                        str8 = str;
                    }
                }
                map32 = zzbr;
                i8 = intValue;
                map22 = map10;
                map18 = map13;
                map20 = map14;
                map19 = map15;
                map21 = map16;
                com_google_android_gms_measurement_internal_zzk = this;
                length++;
                com_google_android_gms_internal_measurement_zzgoArr5 = com_google_android_gms_internal_measurement_zzgoArr;
            }
        }
        map22 = map10;
        map18 = map13;
        map20 = map14;
        map19 = map15;
        map21 = map16;
        com_google_android_gms_measurement_internal_zzk = this;
        zzgg[] com_google_android_gms_internal_measurement_zzggArr = new zzgg[map21.size()];
        it2 = map21.keySet().iterator();
        length = 0;
        while (it2.hasNext()) {
            int intValue3 = ((Integer) it2.next()).intValue();
            String str9;
            if (hashSet3.contains(Integer.valueOf(intValue3))) {
                str9 = str;
            } else {
                map17 = map19;
                zzgg com_google_android_gms_internal_measurement_zzgg3 = (zzgg) map17.get(Integer.valueOf(intValue3));
                if (com_google_android_gms_internal_measurement_zzgg3 == null) {
                    com_google_android_gms_internal_measurement_zzgg3 = new zzgg();
                }
                int i9 = length + 1;
                com_google_android_gms_internal_measurement_zzggArr[length] = com_google_android_gms_internal_measurement_zzgg3;
                com_google_android_gms_internal_measurement_zzgg3.zzavr = Integer.valueOf(intValue3);
                com_google_android_gms_internal_measurement_zzgg3.zzaxf = new zzgm();
                arrayMap9 = map21;
                com_google_android_gms_internal_measurement_zzgg3.zzaxf.zzaza = zzfq.zza((BitSet) arrayMap9.get(Integer.valueOf(intValue3)));
                arrayMap8 = map20;
                com_google_android_gms_internal_measurement_zzgg3.zzaxf.zzayz = zzfq.zza((BitSet) arrayMap8.get(Integer.valueOf(intValue3)));
                if (zzbd) {
                    zzgn[] com_google_android_gms_internal_measurement_zzgnArr;
                    map7 = map18;
                    com_google_android_gms_internal_measurement_zzgg3.zzaxf.zzazb = zzd((Map) map7.get(Integer.valueOf(intValue3)));
                    zzgm com_google_android_gms_internal_measurement_zzgm2 = com_google_android_gms_internal_measurement_zzgg3.zzaxf;
                    arrayMap7 = map22;
                    arrayMap10 = (Map) arrayMap7.get(Integer.valueOf(intValue3));
                    if (arrayMap10 == null) {
                        it3 = it2;
                        map23 = map17;
                        com_google_android_gms_internal_measurement_zzgnArr = new zzgn[0];
                    } else {
                        com_google_android_gms_internal_measurement_zzgnArr = new zzgn[arrayMap10.size()];
                        int i10 = 0;
                        for (Integer num : arrayMap10.keySet()) {
                            it3 = it2;
                            zzgn com_google_android_gms_internal_measurement_zzgn = new zzgn();
                            com_google_android_gms_internal_measurement_zzgn.zzaxj = num;
                            List<Long> list = (List) arrayMap10.get(num);
                            if (list != null) {
                                Collections.sort(list);
                                map23 = map17;
                                long[] jArr = new long[list.size()];
                                int i11 = 0;
                                for (Long longValue2 : list) {
                                    int i12 = i11 + 1;
                                    jArr[i11] = longValue2.longValue();
                                    i11 = i12;
                                }
                                com_google_android_gms_internal_measurement_zzgn.zzaze = jArr;
                            } else {
                                map23 = map17;
                            }
                            i2 = i10 + 1;
                            com_google_android_gms_internal_measurement_zzgnArr[i10] = com_google_android_gms_internal_measurement_zzgn;
                            i10 = i2;
                            it2 = it3;
                            map17 = map23;
                        }
                        it3 = it2;
                        map23 = map17;
                    }
                    com_google_android_gms_internal_measurement_zzgm2.zzazc = com_google_android_gms_internal_measurement_zzgnArr;
                } else {
                    it3 = it2;
                    map23 = map17;
                    map7 = map18;
                    arrayMap7 = map22;
                }
                zzcp zzjt2 = zzjt();
                zzzr com_google_android_gms_internal_measurement_zzzr = com_google_android_gms_internal_measurement_zzgg3.zzaxf;
                zzjt2.zzcl();
                zzjt2.zzaf();
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzzr);
                try {
                    byte[] bArr = new byte[com_google_android_gms_internal_measurement_zzzr.zzwe()];
                    try {
                        zzzj zzk = zzzj.zzk(bArr, 0, bArr.length);
                        com_google_android_gms_internal_measurement_zzzr.zza(zzk);
                        zzk.zzzh();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("app_id", str);
                        contentValues.put("audience_id", Integer.valueOf(intValue3));
                        contentValues.put("current_results", bArr);
                        try {
                            try {
                                if (zzjt2.getWritableDatabase().insertWithOnConflict("audience_filter_values", null, contentValues, 5) == -1) {
                                    zzjt2.zzgt().zzjg().zzg("Failed to insert filter results (got -1). appId", zzaq.zzby(str));
                                }
                            } catch (SQLiteException e5) {
                                e = e5;
                                zzjt2.zzgt().zzjg().zze("Error storing filter results. appId", zzaq.zzby(str), e);
                                length = i9;
                                map21 = arrayMap9;
                                map20 = arrayMap8;
                                map18 = map7;
                                map22 = arrayMap7;
                                it2 = it3;
                                map19 = map23;
                            }
                        } catch (SQLiteException e6) {
                            e = e6;
                            zzjt2.zzgt().zzjg().zze("Error storing filter results. appId", zzaq.zzby(str), e);
                            length = i9;
                            map21 = arrayMap9;
                            map20 = arrayMap8;
                            map18 = map7;
                            map22 = arrayMap7;
                            it2 = it3;
                            map19 = map23;
                        }
                    } catch (IOException e7) {
                        e = e7;
                        str9 = str;
                        zzjt2.zzgt().zzjg().zze("Configuration loss. Failed to serialize filter results. appId", zzaq.zzby(str), e);
                        length = i9;
                        map21 = arrayMap9;
                        map20 = arrayMap8;
                        map18 = map7;
                        map22 = arrayMap7;
                        it2 = it3;
                        map19 = map23;
                    }
                } catch (IOException e8) {
                    e = e8;
                    str9 = str;
                    zzjt2.zzgt().zzjg().zze("Configuration loss. Failed to serialize filter results. appId", zzaq.zzby(str), e);
                    length = i9;
                    map21 = arrayMap9;
                    map20 = arrayMap8;
                    map18 = map7;
                    map22 = arrayMap7;
                    it2 = it3;
                    map19 = map23;
                }
                length = i9;
                map21 = arrayMap9;
                map20 = arrayMap8;
                map18 = map7;
                map22 = arrayMap7;
                it2 = it3;
                map19 = map23;
            }
        }
        return (zzgg[]) Arrays.copyOf(com_google_android_gms_internal_measurement_zzggArr, length);
    }

    private final Boolean zza(zzfy com_google_android_gms_internal_measurement_zzfy, String str, zzgj[] com_google_android_gms_internal_measurement_zzgjArr, long j) {
        if (com_google_android_gms_internal_measurement_zzfy.zzawb != null) {
            j = zza(j, com_google_android_gms_internal_measurement_zzfy.zzawb);
            if (j == null) {
                return null;
            }
            if (j.booleanValue() == null) {
                return Boolean.valueOf(false);
            }
        }
        j = new HashSet();
        for (zzfz com_google_android_gms_internal_measurement_zzfz : com_google_android_gms_internal_measurement_zzfy.zzavz) {
            if (TextUtils.isEmpty(com_google_android_gms_internal_measurement_zzfz.zzawg)) {
                zzgt().zzjj().zzg("null or empty param name in filter. event", zzgq().zzbv(str));
                return null;
            }
            j.add(com_google_android_gms_internal_measurement_zzfz.zzawg);
        }
        Map arrayMap = new ArrayMap();
        for (zzgj com_google_android_gms_internal_measurement_zzgj : com_google_android_gms_internal_measurement_zzgjArr) {
            if (j.contains(com_google_android_gms_internal_measurement_zzgj.name)) {
                if (com_google_android_gms_internal_measurement_zzgj.zzaxq != null) {
                    arrayMap.put(com_google_android_gms_internal_measurement_zzgj.name, com_google_android_gms_internal_measurement_zzgj.zzaxq);
                } else if (com_google_android_gms_internal_measurement_zzgj.zzava != null) {
                    arrayMap.put(com_google_android_gms_internal_measurement_zzgj.name, com_google_android_gms_internal_measurement_zzgj.zzava);
                } else if (com_google_android_gms_internal_measurement_zzgj.zzamw != null) {
                    arrayMap.put(com_google_android_gms_internal_measurement_zzgj.name, com_google_android_gms_internal_measurement_zzgj.zzamw);
                } else {
                    zzgt().zzjj().zze("Unknown value for param. event, param", zzgq().zzbv(str), zzgq().zzbw(com_google_android_gms_internal_measurement_zzgj.name));
                    return null;
                }
            }
        }
        for (zzfz com_google_android_gms_internal_measurement_zzfz2 : com_google_android_gms_internal_measurement_zzfy.zzavz) {
            boolean equals = Boolean.TRUE.equals(com_google_android_gms_internal_measurement_zzfz2.zzawf);
            String str2 = com_google_android_gms_internal_measurement_zzfz2.zzawg;
            if (TextUtils.isEmpty(str2)) {
                zzgt().zzjj().zzg("Event has empty param name. event", zzgq().zzbv(str));
                return null;
            }
            Object obj = arrayMap.get(str2);
            Boolean zza;
            if (obj instanceof Long) {
                if (com_google_android_gms_internal_measurement_zzfz2.zzawe == null) {
                    zzgt().zzjj().zze("No number filter for long param. event, param", zzgq().zzbv(str), zzgq().zzbw(str2));
                    return null;
                }
                zza = zza(((Long) obj).longValue(), com_google_android_gms_internal_measurement_zzfz2.zzawe);
                if (zza == null) {
                    return null;
                }
                if (((1 ^ zza.booleanValue()) ^ equals) != 0) {
                    return Boolean.valueOf(false);
                }
            } else if (obj instanceof Double) {
                if (com_google_android_gms_internal_measurement_zzfz2.zzawe == null) {
                    zzgt().zzjj().zze("No number filter for double param. event, param", zzgq().zzbv(str), zzgq().zzbw(str2));
                    return null;
                }
                zza = zza(((Double) obj).doubleValue(), com_google_android_gms_internal_measurement_zzfz2.zzawe);
                if (zza == null) {
                    return null;
                }
                if (((1 ^ zza.booleanValue()) ^ equals) != 0) {
                    return Boolean.valueOf(false);
                }
            } else if (obj instanceof String) {
                if (com_google_android_gms_internal_measurement_zzfz2.zzawd != null) {
                    zza = zza((String) obj, com_google_android_gms_internal_measurement_zzfz2.zzawd);
                } else if (com_google_android_gms_internal_measurement_zzfz2.zzawe != null) {
                    String str3 = (String) obj;
                    if (zzfq.zzcu(str3)) {
                        zza = zza(str3, com_google_android_gms_internal_measurement_zzfz2.zzawe);
                    } else {
                        zzgt().zzjj().zze("Invalid param value for number filter. event, param", zzgq().zzbv(str), zzgq().zzbw(str2));
                        return null;
                    }
                } else {
                    zzgt().zzjj().zze("No filter for String param. event, param", zzgq().zzbv(str), zzgq().zzbw(str2));
                    return null;
                }
                if (zza == null) {
                    return null;
                }
                if (((1 ^ zza.booleanValue()) ^ equals) != 0) {
                    return Boolean.valueOf(false);
                }
            } else if (obj == null) {
                zzgt().zzjo().zze("Missing param for filter. event, param", zzgq().zzbv(str), zzgq().zzbw(str2));
                return Boolean.valueOf(false);
            } else {
                zzgt().zzjj().zze("Unknown param type. event, param", zzgq().zzbv(str), zzgq().zzbw(str2));
                return null;
            }
        }
        return Boolean.valueOf(true);
    }

    private final Boolean zza(zzgb com_google_android_gms_internal_measurement_zzgb, zzgo com_google_android_gms_internal_measurement_zzgo) {
        com_google_android_gms_internal_measurement_zzgb = com_google_android_gms_internal_measurement_zzgb.zzawo;
        if (com_google_android_gms_internal_measurement_zzgb == null) {
            zzgt().zzjj().zzg("Missing property filter. property", zzgq().zzbx(com_google_android_gms_internal_measurement_zzgo.name));
            return null;
        }
        boolean equals = Boolean.TRUE.equals(com_google_android_gms_internal_measurement_zzgb.zzawf);
        if (com_google_android_gms_internal_measurement_zzgo.zzaxq != null) {
            if (com_google_android_gms_internal_measurement_zzgb.zzawe != null) {
                return zza(zza(com_google_android_gms_internal_measurement_zzgo.zzaxq.longValue(), com_google_android_gms_internal_measurement_zzgb.zzawe), equals);
            }
            zzgt().zzjj().zzg("No number filter for long property. property", zzgq().zzbx(com_google_android_gms_internal_measurement_zzgo.name));
            return null;
        } else if (com_google_android_gms_internal_measurement_zzgo.zzava != null) {
            if (com_google_android_gms_internal_measurement_zzgb.zzawe != null) {
                return zza(zza(com_google_android_gms_internal_measurement_zzgo.zzava.doubleValue(), com_google_android_gms_internal_measurement_zzgb.zzawe), equals);
            }
            zzgt().zzjj().zzg("No number filter for double property. property", zzgq().zzbx(com_google_android_gms_internal_measurement_zzgo.name));
            return null;
        } else if (com_google_android_gms_internal_measurement_zzgo.zzamw == null) {
            zzgt().zzjj().zzg("User property has no value, property", zzgq().zzbx(com_google_android_gms_internal_measurement_zzgo.name));
            return null;
        } else if (com_google_android_gms_internal_measurement_zzgb.zzawd != null) {
            return zza(zza(com_google_android_gms_internal_measurement_zzgo.zzamw, com_google_android_gms_internal_measurement_zzgb.zzawd), equals);
        } else {
            if (com_google_android_gms_internal_measurement_zzgb.zzawe == null) {
                zzgt().zzjj().zzg("No string or number filter defined. property", zzgq().zzbx(com_google_android_gms_internal_measurement_zzgo.name));
            } else if (zzfq.zzcu(com_google_android_gms_internal_measurement_zzgo.zzamw)) {
                return zza(zza(com_google_android_gms_internal_measurement_zzgo.zzamw, com_google_android_gms_internal_measurement_zzgb.zzawe), equals);
            } else {
                zzgt().zzjj().zze("Invalid user property value for Numeric number filter. property, value", zzgq().zzbx(com_google_android_gms_internal_measurement_zzgo.name), com_google_android_gms_internal_measurement_zzgo.zzamw);
            }
            return null;
        }
    }

    @VisibleForTesting
    private static Boolean zza(Boolean bool, boolean z) {
        return bool == null ? null : Boolean.valueOf(bool.booleanValue() ^ z);
    }

    @VisibleForTesting
    private final Boolean zza(String str, zzgc com_google_android_gms_internal_measurement_zzgc) {
        Preconditions.checkNotNull(com_google_android_gms_internal_measurement_zzgc);
        if (!(str == null || com_google_android_gms_internal_measurement_zzgc.zzawp == null)) {
            if (com_google_android_gms_internal_measurement_zzgc.zzawp.intValue() != 0) {
                String toUpperCase;
                String str2;
                List list;
                List arrayList;
                if (com_google_android_gms_internal_measurement_zzgc.zzawp.intValue() == 6) {
                    if (com_google_android_gms_internal_measurement_zzgc.zzaws == null || com_google_android_gms_internal_measurement_zzgc.zzaws.length == 0) {
                        return null;
                    }
                } else if (com_google_android_gms_internal_measurement_zzgc.zzawq == null) {
                    return null;
                }
                int intValue = com_google_android_gms_internal_measurement_zzgc.zzawp.intValue();
                boolean z = com_google_android_gms_internal_measurement_zzgc.zzawr != null && com_google_android_gms_internal_measurement_zzgc.zzawr.booleanValue();
                if (!(z || intValue == 1)) {
                    if (intValue != 6) {
                        toUpperCase = com_google_android_gms_internal_measurement_zzgc.zzawq.toUpperCase(Locale.ENGLISH);
                        str2 = toUpperCase;
                        if (com_google_android_gms_internal_measurement_zzgc.zzaws != null) {
                            list = null;
                        } else {
                            com_google_android_gms_internal_measurement_zzgc = com_google_android_gms_internal_measurement_zzgc.zzaws;
                            if (z) {
                                arrayList = new ArrayList();
                                for (String toUpperCase2 : com_google_android_gms_internal_measurement_zzgc) {
                                    arrayList.add(toUpperCase2.toUpperCase(Locale.ENGLISH));
                                }
                                list = arrayList;
                            } else {
                                list = Arrays.asList(com_google_android_gms_internal_measurement_zzgc);
                            }
                        }
                        return zza(str, intValue, z, str2, list, intValue != 1 ? str2 : null);
                    }
                }
                toUpperCase = com_google_android_gms_internal_measurement_zzgc.zzawq;
                str2 = toUpperCase;
                if (com_google_android_gms_internal_measurement_zzgc.zzaws != null) {
                    com_google_android_gms_internal_measurement_zzgc = com_google_android_gms_internal_measurement_zzgc.zzaws;
                    if (z) {
                        arrayList = new ArrayList();
                        while (r3 < r2) {
                            arrayList.add(toUpperCase2.toUpperCase(Locale.ENGLISH));
                        }
                        list = arrayList;
                    } else {
                        list = Arrays.asList(com_google_android_gms_internal_measurement_zzgc);
                    }
                } else {
                    list = null;
                }
                if (intValue != 1) {
                }
                return zza(str, intValue, z, str2, list, intValue != 1 ? str2 : null);
            }
        }
        return null;
    }

    private final java.lang.Boolean zza(java.lang.String r3, int r4, boolean r5, java.lang.String r6, java.util.List<java.lang.String> r7, java.lang.String r8) {
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
        r0 = 0;
        if (r3 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = 6;
        if (r4 != r1) goto L_0x0010;
    L_0x0007:
        if (r7 == 0) goto L_0x000f;
    L_0x0009:
        r1 = r7.size();
        if (r1 != 0) goto L_0x0013;
    L_0x000f:
        return r0;
    L_0x0010:
        if (r6 != 0) goto L_0x0013;
    L_0x0012:
        return r0;
    L_0x0013:
        if (r5 != 0) goto L_0x001f;
    L_0x0015:
        r1 = 1;
        if (r4 != r1) goto L_0x0019;
    L_0x0018:
        goto L_0x001f;
    L_0x0019:
        r1 = java.util.Locale.ENGLISH;
        r3 = r3.toUpperCase(r1);
    L_0x001f:
        switch(r4) {
            case 1: goto L_0x0050;
            case 2: goto L_0x0047;
            case 3: goto L_0x003e;
            case 4: goto L_0x0035;
            case 5: goto L_0x002c;
            case 6: goto L_0x0023;
            default: goto L_0x0022;
        };
    L_0x0022:
        return r0;
    L_0x0023:
        r3 = r7.contains(r3);
        r3 = java.lang.Boolean.valueOf(r3);
        return r3;
    L_0x002c:
        r3 = r3.equals(r6);
        r3 = java.lang.Boolean.valueOf(r3);
        return r3;
    L_0x0035:
        r3 = r3.contains(r6);
        r3 = java.lang.Boolean.valueOf(r3);
        return r3;
    L_0x003e:
        r3 = r3.endsWith(r6);
        r3 = java.lang.Boolean.valueOf(r3);
        return r3;
    L_0x0047:
        r3 = r3.startsWith(r6);
        r3 = java.lang.Boolean.valueOf(r3);
        return r3;
    L_0x0050:
        if (r5 == 0) goto L_0x0054;
    L_0x0052:
        r4 = 0;
        goto L_0x0056;
    L_0x0054:
        r4 = 66;
    L_0x0056:
        r4 = java.util.regex.Pattern.compile(r8, r4);	 Catch:{ PatternSyntaxException -> 0x0067 }
        r3 = r4.matcher(r3);	 Catch:{ PatternSyntaxException -> 0x0067 }
        r3 = r3.matches();	 Catch:{ PatternSyntaxException -> 0x0067 }
        r3 = java.lang.Boolean.valueOf(r3);	 Catch:{ PatternSyntaxException -> 0x0067 }
        return r3;
    L_0x0067:
        r3 = r2.zzgt();
        r3 = r3.zzjj();
        r4 = "Invalid regular expression in REGEXP audience filter. expression";
        r3.zzg(r4, r8);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzk.zza(java.lang.String, int, boolean, java.lang.String, java.util.List, java.lang.String):java.lang.Boolean");
    }

    private final java.lang.Boolean zza(long r2, com.google.android.gms.internal.measurement.zzga r4) {
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
        r1 = this;
        r0 = new java.math.BigDecimal;	 Catch:{ NumberFormatException -> 0x000c }
        r0.<init>(r2);	 Catch:{ NumberFormatException -> 0x000c }
        r2 = 0;	 Catch:{ NumberFormatException -> 0x000c }
        r2 = zza(r0, r4, r2);	 Catch:{ NumberFormatException -> 0x000c }
        return r2;
    L_0x000c:
        r2 = 0;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzk.zza(long, com.google.android.gms.internal.measurement.zzga):java.lang.Boolean");
    }

    private final java.lang.Boolean zza(double r2, com.google.android.gms.internal.measurement.zzga r4) {
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
        r1 = this;
        r0 = new java.math.BigDecimal;	 Catch:{ NumberFormatException -> 0x000e }
        r0.<init>(r2);	 Catch:{ NumberFormatException -> 0x000e }
        r2 = java.lang.Math.ulp(r2);	 Catch:{ NumberFormatException -> 0x000e }
        r2 = zza(r0, r4, r2);	 Catch:{ NumberFormatException -> 0x000e }
        return r2;
    L_0x000e:
        r2 = 0;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzk.zza(double, com.google.android.gms.internal.measurement.zzga):java.lang.Boolean");
    }

    private final java.lang.Boolean zza(java.lang.String r5, com.google.android.gms.internal.measurement.zzga r6) {
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
        r4 = this;
        r0 = com.google.android.gms.measurement.internal.zzfq.zzcu(r5);
        r1 = 0;
        if (r0 != 0) goto L_0x0008;
    L_0x0007:
        return r1;
    L_0x0008:
        r0 = new java.math.BigDecimal;	 Catch:{ NumberFormatException -> 0x0014 }
        r0.<init>(r5);	 Catch:{ NumberFormatException -> 0x0014 }
        r2 = 0;	 Catch:{ NumberFormatException -> 0x0014 }
        r5 = zza(r0, r6, r2);	 Catch:{ NumberFormatException -> 0x0014 }
        return r5;
    L_0x0014:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzk.zza(java.lang.String, com.google.android.gms.internal.measurement.zzga):java.lang.Boolean");
    }

    @com.google.android.gms.common.util.VisibleForTesting
    private static java.lang.Boolean zza(java.math.BigDecimal r7, com.google.android.gms.internal.measurement.zzga r8, double r9) {
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
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r8);
        r0 = r8.zzawh;
        r1 = 0;
        if (r0 == 0) goto L_0x00f0;
    L_0x0008:
        r0 = r8.zzawh;
        r0 = r0.intValue();
        if (r0 != 0) goto L_0x0012;
    L_0x0010:
        goto L_0x00f0;
    L_0x0012:
        r0 = r8.zzawh;
        r0 = r0.intValue();
        r2 = 4;
        if (r0 != r2) goto L_0x0024;
    L_0x001b:
        r0 = r8.zzawk;
        if (r0 == 0) goto L_0x0023;
    L_0x001f:
        r0 = r8.zzawl;
        if (r0 != 0) goto L_0x0029;
    L_0x0023:
        return r1;
    L_0x0024:
        r0 = r8.zzawj;
        if (r0 != 0) goto L_0x0029;
    L_0x0028:
        return r1;
    L_0x0029:
        r0 = r8.zzawh;
        r0 = r0.intValue();
        r3 = r8.zzawh;
        r3 = r3.intValue();
        if (r3 != r2) goto L_0x005b;
    L_0x0037:
        r3 = r8.zzawk;
        r3 = com.google.android.gms.measurement.internal.zzfq.zzcu(r3);
        if (r3 == 0) goto L_0x005a;
    L_0x003f:
        r3 = r8.zzawl;
        r3 = com.google.android.gms.measurement.internal.zzfq.zzcu(r3);
        if (r3 != 0) goto L_0x0048;
    L_0x0047:
        goto L_0x005a;
    L_0x0048:
        r3 = new java.math.BigDecimal;	 Catch:{ NumberFormatException -> 0x0059 }
        r4 = r8.zzawk;	 Catch:{ NumberFormatException -> 0x0059 }
        r3.<init>(r4);	 Catch:{ NumberFormatException -> 0x0059 }
        r4 = new java.math.BigDecimal;	 Catch:{ NumberFormatException -> 0x0059 }
        r8 = r8.zzawl;	 Catch:{ NumberFormatException -> 0x0059 }
        r4.<init>(r8);	 Catch:{ NumberFormatException -> 0x0059 }
        r8 = r3;
        r3 = r1;
        goto L_0x006d;
    L_0x0059:
        return r1;
    L_0x005a:
        return r1;
    L_0x005b:
        r3 = r8.zzawj;
        r3 = com.google.android.gms.measurement.internal.zzfq.zzcu(r3);
        if (r3 != 0) goto L_0x0064;
    L_0x0063:
        return r1;
    L_0x0064:
        r3 = new java.math.BigDecimal;	 Catch:{ NumberFormatException -> 0x00ef }
        r8 = r8.zzawj;	 Catch:{ NumberFormatException -> 0x00ef }
        r3.<init>(r8);	 Catch:{ NumberFormatException -> 0x00ef }
        r8 = r1;
        r4 = r8;
    L_0x006d:
        if (r0 != r2) goto L_0x0072;
    L_0x006f:
        if (r8 != 0) goto L_0x0074;
    L_0x0071:
        return r1;
    L_0x0072:
        if (r3 == 0) goto L_0x00ee;
    L_0x0074:
        r2 = -1;
        r5 = 0;
        r6 = 1;
        switch(r0) {
            case 1: goto L_0x00e2;
            case 2: goto L_0x00d6;
            case 3: goto L_0x008d;
            case 4: goto L_0x007b;
            default: goto L_0x007a;
        };
    L_0x007a:
        goto L_0x00ee;
    L_0x007b:
        r8 = r7.compareTo(r8);
        if (r8 == r2) goto L_0x0088;
    L_0x0081:
        r7 = r7.compareTo(r4);
        if (r7 == r6) goto L_0x0088;
    L_0x0087:
        r5 = 1;
    L_0x0088:
        r7 = java.lang.Boolean.valueOf(r5);
        return r7;
    L_0x008d:
        r0 = 0;
        r8 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1));
        if (r8 == 0) goto L_0x00ca;
    L_0x0093:
        r8 = new java.math.BigDecimal;
        r8.<init>(r9);
        r0 = new java.math.BigDecimal;
        r1 = 2;
        r0.<init>(r1);
        r8 = r8.multiply(r0);
        r8 = r3.subtract(r8);
        r8 = r7.compareTo(r8);
        if (r8 != r6) goto L_0x00c5;
    L_0x00ac:
        r8 = new java.math.BigDecimal;
        r8.<init>(r9);
        r9 = new java.math.BigDecimal;
        r9.<init>(r1);
        r8 = r8.multiply(r9);
        r8 = r3.add(r8);
        r7 = r7.compareTo(r8);
        if (r7 != r2) goto L_0x00c5;
    L_0x00c4:
        r5 = 1;
    L_0x00c5:
        r7 = java.lang.Boolean.valueOf(r5);
        return r7;
    L_0x00ca:
        r7 = r7.compareTo(r3);
        if (r7 != 0) goto L_0x00d1;
    L_0x00d0:
        r5 = 1;
    L_0x00d1:
        r7 = java.lang.Boolean.valueOf(r5);
        return r7;
    L_0x00d6:
        r7 = r7.compareTo(r3);
        if (r7 != r6) goto L_0x00dd;
    L_0x00dc:
        r5 = 1;
    L_0x00dd:
        r7 = java.lang.Boolean.valueOf(r5);
        return r7;
    L_0x00e2:
        r7 = r7.compareTo(r3);
        if (r7 != r2) goto L_0x00e9;
    L_0x00e8:
        r5 = 1;
    L_0x00e9:
        r7 = java.lang.Boolean.valueOf(r5);
        return r7;
    L_0x00ee:
        return r1;
    L_0x00ef:
        return r1;
    L_0x00f0:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzk.zza(java.math.BigDecimal, com.google.android.gms.internal.measurement.zzga, double):java.lang.Boolean");
    }

    private static zzgh[] zzd(Map<Integer, Long> map) {
        if (map == null) {
            return null;
        }
        int i = 0;
        zzgh[] com_google_android_gms_internal_measurement_zzghArr = new zzgh[map.size()];
        for (Integer num : map.keySet()) {
            zzgh com_google_android_gms_internal_measurement_zzgh = new zzgh();
            com_google_android_gms_internal_measurement_zzgh.zzaxj = num;
            com_google_android_gms_internal_measurement_zzgh.zzaxk = (Long) map.get(num);
            int i2 = i + 1;
            com_google_android_gms_internal_measurement_zzghArr[i] = com_google_android_gms_internal_measurement_zzgh;
            i = i2;
        }
        return com_google_android_gms_internal_measurement_zzghArr;
    }

    private static void zza(Map<Integer, Long> map, int i, long j) {
        Long l = (Long) map.get(Integer.valueOf(i));
        j /= 1000;
        if (l == null || j > l.longValue()) {
            map.put(Integer.valueOf(i), Long.valueOf(j));
        }
    }

    private static void zzb(Map<Integer, List<Long>> map, int i, long j) {
        List list = (List) map.get(Integer.valueOf(i));
        if (list == null) {
            list = new ArrayList();
            map.put(Integer.valueOf(i), list);
        }
        list.add(Long.valueOf(j / 1000));
    }
}
