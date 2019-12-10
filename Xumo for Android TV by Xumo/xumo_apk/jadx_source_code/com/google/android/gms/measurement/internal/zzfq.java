package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzfy;
import com.google.android.gms.internal.measurement.zzfz;
import com.google.android.gms.internal.measurement.zzga;
import com.google.android.gms.internal.measurement.zzgb;
import com.google.android.gms.internal.measurement.zzgc;
import com.google.android.gms.internal.measurement.zzgg;
import com.google.android.gms.internal.measurement.zzgh;
import com.google.android.gms.internal.measurement.zzgi;
import com.google.android.gms.internal.measurement.zzgj;
import com.google.android.gms.internal.measurement.zzgk;
import com.google.android.gms.internal.measurement.zzgl;
import com.google.android.gms.internal.measurement.zzgm;
import com.google.android.gms.internal.measurement.zzgn;
import com.google.android.gms.internal.measurement.zzgo;
import com.google.android.gms.internal.measurement.zzzj;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class zzfq extends zzfj {
    zzfq(zzfk com_google_android_gms_measurement_internal_zzfk) {
        super(com_google_android_gms_measurement_internal_zzfk);
    }

    protected final boolean zzgy() {
        return false;
    }

    final void zza(zzgo com_google_android_gms_internal_measurement_zzgo, Object obj) {
        Preconditions.checkNotNull(obj);
        com_google_android_gms_internal_measurement_zzgo.zzamw = null;
        com_google_android_gms_internal_measurement_zzgo.zzaxq = null;
        com_google_android_gms_internal_measurement_zzgo.zzava = null;
        if (obj instanceof String) {
            com_google_android_gms_internal_measurement_zzgo.zzamw = (String) obj;
        } else if (obj instanceof Long) {
            com_google_android_gms_internal_measurement_zzgo.zzaxq = (Long) obj;
        } else if (obj instanceof Double) {
            com_google_android_gms_internal_measurement_zzgo.zzava = (Double) obj;
        } else {
            zzgt().zzjg().zzg("Ignoring invalid (type) user attribute value", obj);
        }
    }

    final void zza(zzgj com_google_android_gms_internal_measurement_zzgj, Object obj) {
        Preconditions.checkNotNull(obj);
        com_google_android_gms_internal_measurement_zzgj.zzamw = null;
        com_google_android_gms_internal_measurement_zzgj.zzaxq = null;
        com_google_android_gms_internal_measurement_zzgj.zzava = null;
        if (obj instanceof String) {
            com_google_android_gms_internal_measurement_zzgj.zzamw = (String) obj;
        } else if (obj instanceof Long) {
            com_google_android_gms_internal_measurement_zzgj.zzaxq = (Long) obj;
        } else if (obj instanceof Double) {
            com_google_android_gms_internal_measurement_zzgj.zzava = (Double) obj;
        } else {
            zzgt().zzjg().zzg("Ignoring invalid (type) event param value", obj);
        }
    }

    final byte[] zza(zzgk com_google_android_gms_internal_measurement_zzgk) {
        try {
            byte[] bArr = new byte[com_google_android_gms_internal_measurement_zzgk.zzwe()];
            zzzj zzk = zzzj.zzk(bArr, 0, bArr.length);
            com_google_android_gms_internal_measurement_zzgk.zza(zzk);
            zzk.zzzh();
            return bArr;
        } catch (zzgk com_google_android_gms_internal_measurement_zzgk2) {
            zzgt().zzjg().zzg("Data loss. Failed to serialize batch", com_google_android_gms_internal_measurement_zzgk2);
            return null;
        }
    }

    static zzgj zza(zzgi com_google_android_gms_internal_measurement_zzgi, String str) {
        for (zzgj com_google_android_gms_internal_measurement_zzgj : com_google_android_gms_internal_measurement_zzgi.zzaxm) {
            if (com_google_android_gms_internal_measurement_zzgj.name.equals(str)) {
                return com_google_android_gms_internal_measurement_zzgj;
            }
        }
        return null;
    }

    static Object zzb(zzgi com_google_android_gms_internal_measurement_zzgi, String str) {
        com_google_android_gms_internal_measurement_zzgi = zza(com_google_android_gms_internal_measurement_zzgi, str);
        if (com_google_android_gms_internal_measurement_zzgi != null) {
            if (com_google_android_gms_internal_measurement_zzgi.zzamw != null) {
                return com_google_android_gms_internal_measurement_zzgi.zzamw;
            }
            if (com_google_android_gms_internal_measurement_zzgi.zzaxq != null) {
                return com_google_android_gms_internal_measurement_zzgi.zzaxq;
            }
            if (com_google_android_gms_internal_measurement_zzgi.zzava != null) {
                return com_google_android_gms_internal_measurement_zzgi.zzava;
            }
        }
        return null;
    }

    static zzgj[] zza(zzgj[] com_google_android_gms_internal_measurement_zzgjArr, String str, Object obj) {
        for (zzgj com_google_android_gms_internal_measurement_zzgj : com_google_android_gms_internal_measurement_zzgjArr) {
            if (str.equals(com_google_android_gms_internal_measurement_zzgj.name)) {
                com_google_android_gms_internal_measurement_zzgj.zzaxq = null;
                com_google_android_gms_internal_measurement_zzgj.zzamw = null;
                com_google_android_gms_internal_measurement_zzgj.zzava = null;
                if ((obj instanceof Long) != null) {
                    com_google_android_gms_internal_measurement_zzgj.zzaxq = (Long) obj;
                } else if ((obj instanceof String) != null) {
                    com_google_android_gms_internal_measurement_zzgj.zzamw = (String) obj;
                } else if ((obj instanceof Double) != null) {
                    com_google_android_gms_internal_measurement_zzgj.zzava = (Double) obj;
                }
                return com_google_android_gms_internal_measurement_zzgjArr;
            }
        }
        Object obj2 = new zzgj[(com_google_android_gms_internal_measurement_zzgjArr.length + 1)];
        System.arraycopy(com_google_android_gms_internal_measurement_zzgjArr, 0, obj2, 0, com_google_android_gms_internal_measurement_zzgjArr.length);
        zzgj com_google_android_gms_internal_measurement_zzgj2 = new zzgj();
        com_google_android_gms_internal_measurement_zzgj2.name = str;
        if ((obj instanceof Long) != null) {
            com_google_android_gms_internal_measurement_zzgj2.zzaxq = (Long) obj;
        } else if ((obj instanceof String) != null) {
            com_google_android_gms_internal_measurement_zzgj2.zzamw = (String) obj;
        } else if ((obj instanceof Double) != null) {
            com_google_android_gms_internal_measurement_zzgj2.zzava = (Double) obj;
        }
        obj2[com_google_android_gms_internal_measurement_zzgjArr.length] = com_google_android_gms_internal_measurement_zzgj2;
        return obj2;
    }

    final String zzb(zzgk com_google_android_gms_internal_measurement_zzgk) {
        zzgk com_google_android_gms_internal_measurement_zzgk2 = com_google_android_gms_internal_measurement_zzgk;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nbatch {\n");
        if (com_google_android_gms_internal_measurement_zzgk2.zzaxr != null) {
            for (zzgl com_google_android_gms_internal_measurement_zzgl : com_google_android_gms_internal_measurement_zzgk2.zzaxr) {
                if (!(com_google_android_gms_internal_measurement_zzgl == null || com_google_android_gms_internal_measurement_zzgl == null)) {
                    int length;
                    int i;
                    zza(stringBuilder, 1);
                    stringBuilder.append("bundle {\n");
                    zza(stringBuilder, 1, "protocol_version", com_google_android_gms_internal_measurement_zzgl.zzaxt);
                    zza(stringBuilder, 1, "platform", com_google_android_gms_internal_measurement_zzgl.zzayb);
                    zza(stringBuilder, 1, "gmp_version", com_google_android_gms_internal_measurement_zzgl.zzayf);
                    zza(stringBuilder, 1, "uploading_gmp_version", com_google_android_gms_internal_measurement_zzgl.zzayg);
                    zza(stringBuilder, 1, "config_version", com_google_android_gms_internal_measurement_zzgl.zzayr);
                    zza(stringBuilder, 1, "gmp_app_id", com_google_android_gms_internal_measurement_zzgl.zzafx);
                    zza(stringBuilder, 1, "admob_app_id", com_google_android_gms_internal_measurement_zzgl.zzaxc);
                    zza(stringBuilder, 1, "app_id", com_google_android_gms_internal_measurement_zzgl.zztt);
                    zza(stringBuilder, 1, "app_version", com_google_android_gms_internal_measurement_zzgl.zzts);
                    zza(stringBuilder, 1, "app_version_major", com_google_android_gms_internal_measurement_zzgl.zzayn);
                    zza(stringBuilder, 1, "firebase_instance_id", com_google_android_gms_internal_measurement_zzgl.zzafz);
                    zza(stringBuilder, 1, "dev_cert_hash", com_google_android_gms_internal_measurement_zzgl.zzayj);
                    zza(stringBuilder, 1, "app_store", com_google_android_gms_internal_measurement_zzgl.zzage);
                    zza(stringBuilder, 1, "upload_timestamp_millis", com_google_android_gms_internal_measurement_zzgl.zzaxw);
                    zza(stringBuilder, 1, "start_timestamp_millis", com_google_android_gms_internal_measurement_zzgl.zzaxx);
                    zza(stringBuilder, 1, "end_timestamp_millis", com_google_android_gms_internal_measurement_zzgl.zzaxy);
                    zza(stringBuilder, 1, "previous_bundle_start_timestamp_millis", com_google_android_gms_internal_measurement_zzgl.zzaxz);
                    zza(stringBuilder, 1, "previous_bundle_end_timestamp_millis", com_google_android_gms_internal_measurement_zzgl.zzaya);
                    zza(stringBuilder, 1, "app_instance_id", com_google_android_gms_internal_measurement_zzgl.zzafw);
                    zza(stringBuilder, 1, "resettable_device_id", com_google_android_gms_internal_measurement_zzgl.zzayh);
                    zza(stringBuilder, 1, "device_id", com_google_android_gms_internal_measurement_zzgl.zzayq);
                    zza(stringBuilder, 1, "ds_id", com_google_android_gms_internal_measurement_zzgl.zzayt);
                    zza(stringBuilder, 1, "limited_ad_tracking", com_google_android_gms_internal_measurement_zzgl.zzayi);
                    zza(stringBuilder, 1, "os_version", com_google_android_gms_internal_measurement_zzgl.zzayc);
                    zza(stringBuilder, 1, "device_model", com_google_android_gms_internal_measurement_zzgl.zzayd);
                    zza(stringBuilder, 1, "user_default_language", com_google_android_gms_internal_measurement_zzgl.zzaid);
                    zza(stringBuilder, 1, "time_zone_offset_minutes", com_google_android_gms_internal_measurement_zzgl.zzaye);
                    zza(stringBuilder, 1, "bundle_sequential_index", com_google_android_gms_internal_measurement_zzgl.zzayk);
                    zza(stringBuilder, 1, "service_upload", com_google_android_gms_internal_measurement_zzgl.zzayl);
                    zza(stringBuilder, 1, "health_monitor", com_google_android_gms_internal_measurement_zzgl.zzagy);
                    if (!(com_google_android_gms_internal_measurement_zzgl.zzays == null || com_google_android_gms_internal_measurement_zzgl.zzays.longValue() == 0)) {
                        zza(stringBuilder, 1, "android_id", com_google_android_gms_internal_measurement_zzgl.zzays);
                    }
                    if (com_google_android_gms_internal_measurement_zzgl.zzayv != null) {
                        zza(stringBuilder, 1, "retry_counter", com_google_android_gms_internal_measurement_zzgl.zzayv);
                    }
                    zzgo[] com_google_android_gms_internal_measurement_zzgoArr = com_google_android_gms_internal_measurement_zzgl.zzaxv;
                    if (com_google_android_gms_internal_measurement_zzgoArr != null) {
                        for (zzgo com_google_android_gms_internal_measurement_zzgo : com_google_android_gms_internal_measurement_zzgoArr) {
                            if (com_google_android_gms_internal_measurement_zzgo != null) {
                                zza(stringBuilder, 2);
                                stringBuilder.append("user_property {\n");
                                zza(stringBuilder, 2, "set_timestamp_millis", com_google_android_gms_internal_measurement_zzgo.zzazg);
                                zza(stringBuilder, 2, JsonKey.NAME, zzgq().zzbx(com_google_android_gms_internal_measurement_zzgo.name));
                                zza(stringBuilder, 2, "string_value", com_google_android_gms_internal_measurement_zzgo.zzamw);
                                zza(stringBuilder, 2, "int_value", com_google_android_gms_internal_measurement_zzgo.zzaxq);
                                zza(stringBuilder, 2, "double_value", com_google_android_gms_internal_measurement_zzgo.zzava);
                                zza(stringBuilder, 2);
                                stringBuilder.append("}\n");
                            }
                        }
                    }
                    zzgg[] com_google_android_gms_internal_measurement_zzggArr = com_google_android_gms_internal_measurement_zzgl.zzaym;
                    String str = com_google_android_gms_internal_measurement_zzgl.zztt;
                    if (com_google_android_gms_internal_measurement_zzggArr != null) {
                        length = com_google_android_gms_internal_measurement_zzggArr.length;
                        i = 0;
                        while (i < length) {
                            int i2;
                            int i3;
                            zzgg com_google_android_gms_internal_measurement_zzgg = com_google_android_gms_internal_measurement_zzggArr[i];
                            if (com_google_android_gms_internal_measurement_zzgg != null) {
                                zza(stringBuilder, 2);
                                stringBuilder.append("audience_membership {\n");
                                zza(stringBuilder, 2, "audience_id", com_google_android_gms_internal_measurement_zzgg.zzavr);
                                zza(stringBuilder, 2, "new_audience", com_google_android_gms_internal_measurement_zzgg.zzaxh);
                                StringBuilder stringBuilder2 = stringBuilder;
                                zzgg com_google_android_gms_internal_measurement_zzgg2 = com_google_android_gms_internal_measurement_zzgg;
                                i2 = i;
                                i3 = length;
                                String str2 = str;
                                zza(stringBuilder2, 2, "current_data", com_google_android_gms_internal_measurement_zzgg.zzaxf, str2);
                                zza(stringBuilder2, 2, "previous_data", com_google_android_gms_internal_measurement_zzgg2.zzaxg, str2);
                                zza(stringBuilder, 2);
                                stringBuilder.append("}\n");
                            } else {
                                i2 = i;
                                i3 = length;
                            }
                            i = i2 + 1;
                            length = i3;
                        }
                    }
                    zzgi[] com_google_android_gms_internal_measurement_zzgiArr = com_google_android_gms_internal_measurement_zzgl.zzaxu;
                    if (com_google_android_gms_internal_measurement_zzgiArr != null) {
                        for (zzgi com_google_android_gms_internal_measurement_zzgi : com_google_android_gms_internal_measurement_zzgiArr) {
                            if (com_google_android_gms_internal_measurement_zzgi != null) {
                                zza(stringBuilder, 2);
                                stringBuilder.append("event {\n");
                                zza(stringBuilder, 2, JsonKey.NAME, zzgq().zzbv(com_google_android_gms_internal_measurement_zzgi.name));
                                zza(stringBuilder, 2, "timestamp_millis", com_google_android_gms_internal_measurement_zzgi.zzaxn);
                                zza(stringBuilder, 2, "previous_timestamp_millis", com_google_android_gms_internal_measurement_zzgi.zzaxo);
                                zza(stringBuilder, 2, "count", com_google_android_gms_internal_measurement_zzgi.count);
                                zzgj[] com_google_android_gms_internal_measurement_zzgjArr = com_google_android_gms_internal_measurement_zzgi.zzaxm;
                                if (com_google_android_gms_internal_measurement_zzgjArr != null) {
                                    for (zzgj com_google_android_gms_internal_measurement_zzgj : com_google_android_gms_internal_measurement_zzgjArr) {
                                        if (com_google_android_gms_internal_measurement_zzgj != null) {
                                            zza(stringBuilder, 3);
                                            stringBuilder.append("param {\n");
                                            zza(stringBuilder, 3, JsonKey.NAME, zzgq().zzbw(com_google_android_gms_internal_measurement_zzgj.name));
                                            zza(stringBuilder, 3, "string_value", com_google_android_gms_internal_measurement_zzgj.zzamw);
                                            zza(stringBuilder, 3, "int_value", com_google_android_gms_internal_measurement_zzgj.zzaxq);
                                            zza(stringBuilder, 3, "double_value", com_google_android_gms_internal_measurement_zzgj.zzava);
                                            zza(stringBuilder, 3);
                                            stringBuilder.append("}\n");
                                        }
                                    }
                                }
                                zza(stringBuilder, 2);
                                stringBuilder.append("}\n");
                            }
                        }
                    }
                    zza(stringBuilder, 1);
                    stringBuilder.append("}\n");
                }
            }
        }
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    final String zza(zzfy com_google_android_gms_internal_measurement_zzfy) {
        if (com_google_android_gms_internal_measurement_zzfy == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nevent_filter {\n");
        int i = 0;
        zza(stringBuilder, 0, "filter_id", com_google_android_gms_internal_measurement_zzfy.zzavx);
        zza(stringBuilder, 0, "event_name", zzgq().zzbv(com_google_android_gms_internal_measurement_zzfy.zzavy));
        zza(stringBuilder, 1, "event_count_filter", com_google_android_gms_internal_measurement_zzfy.zzawb);
        stringBuilder.append("  filters {\n");
        com_google_android_gms_internal_measurement_zzfy = com_google_android_gms_internal_measurement_zzfy.zzavz;
        int length = com_google_android_gms_internal_measurement_zzfy.length;
        while (i < length) {
            zza(stringBuilder, 2, com_google_android_gms_internal_measurement_zzfy[i]);
            i++;
        }
        zza(stringBuilder, 1);
        stringBuilder.append("}\n}\n");
        return stringBuilder.toString();
    }

    final String zza(zzgb com_google_android_gms_internal_measurement_zzgb) {
        if (com_google_android_gms_internal_measurement_zzgb == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nproperty_filter {\n");
        zza(stringBuilder, 0, "filter_id", com_google_android_gms_internal_measurement_zzgb.zzavx);
        zza(stringBuilder, 0, "property_name", zzgq().zzbx(com_google_android_gms_internal_measurement_zzgb.zzawn));
        zza(stringBuilder, 1, com_google_android_gms_internal_measurement_zzgb.zzawo);
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    private final void zza(StringBuilder stringBuilder, int i, String str, zzgm com_google_android_gms_internal_measurement_zzgm, String str2) {
        if (com_google_android_gms_internal_measurement_zzgm != null) {
            int length;
            int i2;
            int i3;
            Long valueOf;
            int i4;
            zza(stringBuilder, 3);
            stringBuilder.append(str);
            stringBuilder.append(" {\n");
            if (com_google_android_gms_internal_measurement_zzgm.zzaza != null) {
                zza(stringBuilder, 4);
                stringBuilder.append("results: ");
                str = com_google_android_gms_internal_measurement_zzgm.zzaza;
                length = str.length;
                i2 = 0;
                i3 = 0;
                while (i2 < length) {
                    valueOf = Long.valueOf(str[i2]);
                    i4 = i3 + 1;
                    if (i3 != 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(valueOf);
                    i2++;
                    i3 = i4;
                }
                stringBuilder.append('\n');
            }
            if (com_google_android_gms_internal_measurement_zzgm.zzayz != null) {
                zza(stringBuilder, 4);
                stringBuilder.append("status: ");
                str = com_google_android_gms_internal_measurement_zzgm.zzayz;
                length = str.length;
                i2 = 0;
                i3 = 0;
                while (i2 < length) {
                    valueOf = Long.valueOf(str[i2]);
                    i4 = i3 + 1;
                    if (i3 != 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(valueOf);
                    i2++;
                    i3 = i4;
                }
                stringBuilder.append('\n');
            }
            if (zzgv().zzbd(str2) != null) {
                int i5;
                if (com_google_android_gms_internal_measurement_zzgm.zzazb != null) {
                    zza(stringBuilder, 4);
                    stringBuilder.append("dynamic_filter_timestamps: {");
                    str = com_google_android_gms_internal_measurement_zzgm.zzazb;
                    str2 = str.length;
                    i5 = 0;
                    length = 0;
                    while (i5 < str2) {
                        zzgh com_google_android_gms_internal_measurement_zzgh = str[i5];
                        i3 = length + 1;
                        if (length != 0) {
                            stringBuilder.append(", ");
                        }
                        stringBuilder.append(com_google_android_gms_internal_measurement_zzgh.zzaxj);
                        stringBuilder.append(":");
                        stringBuilder.append(com_google_android_gms_internal_measurement_zzgh.zzaxk);
                        i5++;
                        length = i3;
                    }
                    stringBuilder.append("}\n");
                }
                if (com_google_android_gms_internal_measurement_zzgm.zzazc != null) {
                    zza(stringBuilder, 4);
                    stringBuilder.append("sequence_filter_timestamps: {");
                    str = com_google_android_gms_internal_measurement_zzgm.zzazc;
                    String length2 = str.length;
                    str2 = null;
                    i5 = 0;
                    while (str2 < length2) {
                        zzgn com_google_android_gms_internal_measurement_zzgn = str[str2];
                        length = i5 + 1;
                        if (i5 != 0) {
                            stringBuilder.append(", ");
                        }
                        stringBuilder.append(com_google_android_gms_internal_measurement_zzgn.zzaxj);
                        stringBuilder.append(": [");
                        long[] jArr = com_google_android_gms_internal_measurement_zzgn.zzaze;
                        int length3 = jArr.length;
                        i2 = 0;
                        i3 = 0;
                        while (i2 < length3) {
                            long j = jArr[i2];
                            int i6 = i3 + 1;
                            if (i3 != 0) {
                                stringBuilder.append(", ");
                            }
                            stringBuilder.append(j);
                            i2++;
                            i3 = i6;
                        }
                        stringBuilder.append("]");
                        str2++;
                        i5 = length;
                    }
                    stringBuilder.append("}\n");
                }
            }
            zza(stringBuilder, 3);
            stringBuilder.append("}\n");
        }
    }

    private final void zza(StringBuilder stringBuilder, int i, String str, zzga com_google_android_gms_internal_measurement_zzga) {
        if (com_google_android_gms_internal_measurement_zzga != null) {
            zza(stringBuilder, i);
            stringBuilder.append(str);
            stringBuilder.append(" {\n");
            if (com_google_android_gms_internal_measurement_zzga.zzawh != null) {
                Object obj = "UNKNOWN_COMPARISON_TYPE";
                switch (com_google_android_gms_internal_measurement_zzga.zzawh.intValue()) {
                    case 1:
                        obj = "LESS_THAN";
                        break;
                    case 2:
                        obj = "GREATER_THAN";
                        break;
                    case 3:
                        obj = "EQUAL";
                        break;
                    case 4:
                        obj = "BETWEEN";
                        break;
                    default:
                        break;
                }
                zza(stringBuilder, i, "comparison_type", obj);
            }
            zza(stringBuilder, i, "match_as_float", com_google_android_gms_internal_measurement_zzga.zzawi);
            zza(stringBuilder, i, "comparison_value", com_google_android_gms_internal_measurement_zzga.zzawj);
            zza(stringBuilder, i, "min_comparison_value", com_google_android_gms_internal_measurement_zzga.zzawk);
            zza(stringBuilder, i, "max_comparison_value", com_google_android_gms_internal_measurement_zzga.zzawl);
            zza(stringBuilder, i);
            stringBuilder.append("}\n");
        }
    }

    private final void zza(StringBuilder stringBuilder, int i, zzfz com_google_android_gms_internal_measurement_zzfz) {
        if (com_google_android_gms_internal_measurement_zzfz != null) {
            zza(stringBuilder, i);
            stringBuilder.append("filter {\n");
            zza(stringBuilder, i, "complement", com_google_android_gms_internal_measurement_zzfz.zzawf);
            zza(stringBuilder, i, "param_name", zzgq().zzbw(com_google_android_gms_internal_measurement_zzfz.zzawg));
            int i2 = i + 1;
            String str = "string_filter";
            zzgc com_google_android_gms_internal_measurement_zzgc = com_google_android_gms_internal_measurement_zzfz.zzawd;
            if (com_google_android_gms_internal_measurement_zzgc != null) {
                zza(stringBuilder, i2);
                stringBuilder.append(str);
                stringBuilder.append(" {\n");
                if (com_google_android_gms_internal_measurement_zzgc.zzawp != null) {
                    Object obj = "UNKNOWN_MATCH_TYPE";
                    switch (com_google_android_gms_internal_measurement_zzgc.zzawp.intValue()) {
                        case 1:
                            obj = "REGEXP";
                            break;
                        case 2:
                            obj = "BEGINS_WITH";
                            break;
                        case 3:
                            obj = "ENDS_WITH";
                            break;
                        case 4:
                            obj = "PARTIAL";
                            break;
                        case 5:
                            obj = "EXACT";
                            break;
                        case 6:
                            obj = "IN_LIST";
                            break;
                        default:
                            break;
                    }
                    zza(stringBuilder, i2, "match_type", obj);
                }
                zza(stringBuilder, i2, "expression", com_google_android_gms_internal_measurement_zzgc.zzawq);
                zza(stringBuilder, i2, "case_sensitive", com_google_android_gms_internal_measurement_zzgc.zzawr);
                if (com_google_android_gms_internal_measurement_zzgc.zzaws.length > 0) {
                    zza(stringBuilder, i2 + 1);
                    stringBuilder.append("expression_list {\n");
                    for (String str2 : com_google_android_gms_internal_measurement_zzgc.zzaws) {
                        zza(stringBuilder, i2 + 2);
                        stringBuilder.append(str2);
                        stringBuilder.append("\n");
                    }
                    stringBuilder.append("}\n");
                }
                zza(stringBuilder, i2);
                stringBuilder.append("}\n");
            }
            zza(stringBuilder, i2, "number_filter", com_google_android_gms_internal_measurement_zzfz.zzawe);
            zza(stringBuilder, i);
            stringBuilder.append("}\n");
        }
    }

    private static void zza(StringBuilder stringBuilder, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            stringBuilder.append("  ");
        }
    }

    private static void zza(StringBuilder stringBuilder, int i, String str, Object obj) {
        if (obj != null) {
            zza(stringBuilder, i + 1);
            stringBuilder.append(str);
            stringBuilder.append(": ");
            stringBuilder.append(obj);
            stringBuilder.append(10);
        }
    }

    final <T extends android.os.Parcelable> T zza(byte[] r5, android.os.Parcelable.Creator<T> r6) {
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
        r0 = 0;
        if (r5 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = android.os.Parcel.obtain();
        r2 = r5.length;	 Catch:{ ParseException -> 0x001c }
        r3 = 0;	 Catch:{ ParseException -> 0x001c }
        r1.unmarshall(r5, r3, r2);	 Catch:{ ParseException -> 0x001c }
        r1.setDataPosition(r3);	 Catch:{ ParseException -> 0x001c }
        r5 = r6.createFromParcel(r1);	 Catch:{ ParseException -> 0x001c }
        r5 = (android.os.Parcelable) r5;	 Catch:{ ParseException -> 0x001c }
        r1.recycle();
        return r5;
    L_0x001a:
        r5 = move-exception;
        goto L_0x002d;
    L_0x001c:
        r5 = r4.zzgt();	 Catch:{ all -> 0x001a }
        r5 = r5.zzjg();	 Catch:{ all -> 0x001a }
        r6 = "Failed to load parcelable from buffer";	 Catch:{ all -> 0x001a }
        r5.zzca(r6);	 Catch:{ all -> 0x001a }
        r1.recycle();
        return r0;
    L_0x002d:
        r1.recycle();
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfq.zza(byte[], android.os.Parcelable$Creator):T");
    }

    @WorkerThread
    final boolean zze(zzae com_google_android_gms_measurement_internal_zzae, zzi com_google_android_gms_measurement_internal_zzi) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzae);
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzi);
        if (TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzafx) == null || TextUtils.isEmpty(com_google_android_gms_measurement_internal_zzi.zzagk) == null) {
            return true;
        }
        zzgw();
        return null;
    }

    static boolean zzcu(String str) {
        return (str == null || !str.matches("([+-])?([0-9]+\\.?[0-9]*|[0-9]*\\.?[0-9]+)") || str.length() > 310) ? null : true;
    }

    static boolean zza(long[] jArr, int i) {
        if (i >= (jArr.length << 6)) {
            return false;
        }
        if (((1 << (i % 64)) & jArr[i / 64]) != 0) {
            return 1;
        }
        return false;
    }

    static long[] zza(BitSet bitSet) {
        int length = (bitSet.length() + 63) / 64;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = 0;
            for (int i2 = 0; i2 < 64; i2++) {
                int i3 = (i << 6) + i2;
                if (i3 >= bitSet.length()) {
                    break;
                }
                if (bitSet.get(i3)) {
                    jArr[i] = jArr[i] | (1 << i2);
                }
            }
        }
        return jArr;
    }

    final boolean zzb(long j, long j2) {
        if (j != 0) {
            if (j2 > 0) {
                return Math.abs(zzbx().currentTimeMillis() - j) > j2 ? true : 0;
            }
        }
        return true;
    }

    final byte[] zza(byte[] bArr) throws IOException {
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            bArr = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[1024];
            while (true) {
                int read = bArr.read(bArr2);
                if (read > 0) {
                    byteArrayOutputStream.write(bArr2, 0, read);
                } else {
                    bArr.close();
                    byteArrayInputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (byte[] bArr3) {
            zzgt().zzjg().zzg("Failed to ungzip content", bArr3);
            throw bArr3;
        }
    }

    final byte[] zzb(byte[] bArr) throws IOException {
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (byte[] bArr2) {
            zzgt().zzjg().zzg("Failed to gzip content", bArr2);
            throw bArr2;
        }
    }

    @Nullable
    final int[] zzmi() {
        Map zzm = zzag.zzm(this.zzang.getContext());
        if (zzm != null) {
            if (zzm.size() != 0) {
                int parseInt;
                List arrayList = new ArrayList();
                int intValue = ((Integer) zzag.zzaks.get()).intValue();
                for (Entry entry : zzm.entrySet()) {
                    if (((String) entry.getKey()).startsWith("measurement.id.")) {
                        try {
                            parseInt = Integer.parseInt((String) entry.getValue());
                            if (parseInt != 0) {
                                arrayList.add(Integer.valueOf(parseInt));
                                if (arrayList.size() >= intValue) {
                                    zzgt().zzjj().zzg("Too many experiment IDs. Number of IDs", Integer.valueOf(arrayList.size()));
                                    break;
                                }
                            } else {
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            zzgt().zzjj().zzg("Experiment ID NumberFormatException", e);
                        }
                    }
                }
                if (arrayList.size() == 0) {
                    return null;
                }
                int[] iArr = new int[arrayList.size()];
                ArrayList arrayList2 = (ArrayList) arrayList;
                int size = arrayList2.size();
                intValue = 0;
                parseInt = 0;
                while (intValue < size) {
                    Object obj = arrayList2.get(intValue);
                    intValue++;
                    int i = parseInt + 1;
                    iArr[parseInt] = ((Integer) obj).intValue();
                    parseInt = i;
                }
                return iArr;
            }
        }
        return null;
    }

    public final /* bridge */ /* synthetic */ zzfq zzjr() {
        return super.zzjr();
    }

    public final /* bridge */ /* synthetic */ zzk zzjs() {
        return super.zzjs();
    }

    public final /* bridge */ /* synthetic */ zzr zzjt() {
        return super.zzjt();
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
