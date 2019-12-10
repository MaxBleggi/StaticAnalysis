package com.google.firebase.messaging;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.Map.Entry;

@Class(creator = "RemoteMessageCreator")
@Reserved({1})
public final class RemoteMessage extends AbstractSafeParcelable {
    public static final Creator<RemoteMessage> CREATOR = new zzc();
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_UNKNOWN = 0;
    @Field(id = 2)
    Bundle zzds;
    private Map<String, String> zzdt;
    private Notification zzdu;

    public static class Builder {
        private final Bundle zzds = new Bundle();
        private final Map<String, String> zzdt = new ArrayMap();

        public Builder(String str) {
            if (TextUtils.isEmpty(str)) {
                String str2 = "Invalid to: ";
                str = String.valueOf(str);
                throw new IllegalArgumentException(str.length() != 0 ? str2.concat(str) : new String(str2));
            }
            this.zzds.putString("google.to", str);
        }

        public RemoteMessage build() {
            Bundle bundle = new Bundle();
            for (Entry entry : this.zzdt.entrySet()) {
                bundle.putString((String) entry.getKey(), (String) entry.getValue());
            }
            bundle.putAll(this.zzds);
            this.zzds.remove(JsonKey.FROM);
            return new RemoteMessage(bundle);
        }

        public Builder addData(String str, String str2) {
            this.zzdt.put(str, str2);
            return this;
        }

        public Builder setData(Map<String, String> map) {
            this.zzdt.clear();
            this.zzdt.putAll(map);
            return this;
        }

        public Builder clearData() {
            this.zzdt.clear();
            return this;
        }

        public Builder setMessageId(String str) {
            this.zzds.putString("google.message_id", str);
            return this;
        }

        public Builder setMessageType(String str) {
            this.zzds.putString("message_type", str);
            return this;
        }

        public Builder setTtl(@IntRange(from = 0, to = 86400) int i) {
            this.zzds.putString("google.ttl", String.valueOf(i));
            return this;
        }

        public Builder setCollapseKey(String str) {
            this.zzds.putString("collapse_key", str);
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MessagePriority {
    }

    public static class Notification {
        private final String tag;
        private final String zzdv;
        private final String zzdw;
        private final String[] zzdx;
        private final String zzdy;
        private final String zzdz;
        private final String[] zzea;
        private final String zzeb;
        private final String zzec;
        private final String zzed;
        private final String zzee;
        private final Uri zzef;

        private Notification(Bundle bundle) {
            this.zzdv = zza.zza(bundle, "gcm.n.title");
            this.zzdw = zza.zzb(bundle, "gcm.n.title");
            this.zzdx = zze(bundle, "gcm.n.title");
            this.zzdy = zza.zza(bundle, "gcm.n.body");
            this.zzdz = zza.zzb(bundle, "gcm.n.body");
            this.zzea = zze(bundle, "gcm.n.body");
            this.zzeb = zza.zza(bundle, "gcm.n.icon");
            this.zzec = zza.zzi(bundle);
            this.tag = zza.zza(bundle, "gcm.n.tag");
            this.zzed = zza.zza(bundle, "gcm.n.color");
            this.zzee = zza.zza(bundle, "gcm.n.click_action");
            this.zzef = zza.zzg(bundle);
        }

        private static String[] zze(Bundle bundle, String str) {
            bundle = zza.zzc(bundle, str);
            if (bundle == null) {
                return null;
            }
            str = new String[bundle.length];
            for (int i = 0; i < bundle.length; i++) {
                str[i] = String.valueOf(bundle[i]);
            }
            return str;
        }

        @Nullable
        public String getTitle() {
            return this.zzdv;
        }

        @Nullable
        public String getTitleLocalizationKey() {
            return this.zzdw;
        }

        @Nullable
        public String[] getTitleLocalizationArgs() {
            return this.zzdx;
        }

        @Nullable
        public String getBody() {
            return this.zzdy;
        }

        @Nullable
        public String getBodyLocalizationKey() {
            return this.zzdz;
        }

        @Nullable
        public String[] getBodyLocalizationArgs() {
            return this.zzea;
        }

        @Nullable
        public String getIcon() {
            return this.zzeb;
        }

        @Nullable
        public String getSound() {
            return this.zzec;
        }

        @Nullable
        public String getTag() {
            return this.tag;
        }

        @Nullable
        public String getColor() {
            return this.zzed;
        }

        @Nullable
        public String getClickAction() {
            return this.zzee;
        }

        @Nullable
        public Uri getLink() {
            return this.zzef;
        }
    }

    @Constructor
    public RemoteMessage(@Param(id = 2) Bundle bundle) {
        this.zzds = bundle;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, this.zzds, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    @Nullable
    public final String getFrom() {
        return this.zzds.getString(JsonKey.FROM);
    }

    @Nullable
    public final String getTo() {
        return this.zzds.getString("google.to");
    }

    public final Map<String, String> getData() {
        if (this.zzdt == null) {
            Bundle bundle = this.zzds;
            Map arrayMap = new ArrayMap();
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (obj instanceof String) {
                    String str2 = (String) obj;
                    if (!(str.startsWith("google.") || str.startsWith("gcm.") || str.equals(JsonKey.FROM) || str.equals("message_type") || str.equals("collapse_key"))) {
                        arrayMap.put(str, str2);
                    }
                }
            }
            this.zzdt = arrayMap;
        }
        return this.zzdt;
    }

    @Nullable
    public final String getCollapseKey() {
        return this.zzds.getString("collapse_key");
    }

    @Nullable
    public final String getMessageId() {
        String string = this.zzds.getString("google.message_id");
        return string == null ? this.zzds.getString("message_id") : string;
    }

    @Nullable
    public final String getMessageType() {
        return this.zzds.getString("message_type");
    }

    public final long getSentTime() {
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
        r0 = r4.zzds;
        r1 = "google.sent_time";
        r0 = r0.get(r1);
        r1 = r0 instanceof java.lang.Long;
        if (r1 == 0) goto L_0x0013;
    L_0x000c:
        r0 = (java.lang.Long) r0;
        r0 = r0.longValue();
        return r0;
    L_0x0013:
        r1 = r0 instanceof java.lang.String;
        if (r1 == 0) goto L_0x0043;
    L_0x0017:
        r1 = r0;	 Catch:{ NumberFormatException -> 0x001f }
        r1 = (java.lang.String) r1;	 Catch:{ NumberFormatException -> 0x001f }
        r1 = java.lang.Long.parseLong(r1);	 Catch:{ NumberFormatException -> 0x001f }
        return r1;
    L_0x001f:
        r1 = "FirebaseMessaging";
        r0 = java.lang.String.valueOf(r0);
        r2 = java.lang.String.valueOf(r0);
        r2 = r2.length();
        r2 = r2 + 19;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "Invalid sent time: ";
        r3.append(r2);
        r3.append(r0);
        r0 = r3.toString();
        android.util.Log.w(r1, r0);
    L_0x0043:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.RemoteMessage.getSentTime():long");
    }

    public final int getTtl() {
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
        r0 = r4.zzds;
        r1 = "google.ttl";
        r0 = r0.get(r1);
        r1 = r0 instanceof java.lang.Integer;
        if (r1 == 0) goto L_0x0013;
    L_0x000c:
        r0 = (java.lang.Integer) r0;
        r0 = r0.intValue();
        return r0;
    L_0x0013:
        r1 = r0 instanceof java.lang.String;
        if (r1 == 0) goto L_0x0043;
    L_0x0017:
        r1 = r0;	 Catch:{ NumberFormatException -> 0x001f }
        r1 = (java.lang.String) r1;	 Catch:{ NumberFormatException -> 0x001f }
        r1 = java.lang.Integer.parseInt(r1);	 Catch:{ NumberFormatException -> 0x001f }
        return r1;
    L_0x001f:
        r1 = "FirebaseMessaging";
        r0 = java.lang.String.valueOf(r0);
        r2 = java.lang.String.valueOf(r0);
        r2 = r2.length();
        r2 = r2 + 13;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "Invalid TTL: ";
        r3.append(r2);
        r3.append(r0);
        r0 = r3.toString();
        android.util.Log.w(r1, r0);
    L_0x0043:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.RemoteMessage.getTtl():int");
    }

    public final int getOriginalPriority() {
        String string = this.zzds.getString("google.original_priority");
        if (string == null) {
            string = this.zzds.getString("google.priority");
        }
        return zzm(string);
    }

    public final int getPriority() {
        String string = this.zzds.getString("google.delivered_priority");
        if (string == null) {
            if ("1".equals(this.zzds.getString("google.priority_reduced"))) {
                return 2;
            }
            string = this.zzds.getString("google.priority");
        }
        return zzm(string);
    }

    private static int zzm(String str) {
        if ("high".equals(str)) {
            return 1;
        }
        return "normal".equals(str) != null ? 2 : null;
    }

    @Nullable
    public final Notification getNotification() {
        if (this.zzdu == null && zza.zzf(this.zzds)) {
            this.zzdu = new Notification(this.zzds);
        }
        return this.zzdu;
    }

    @KeepForSdk
    public final Intent toIntent() {
        Intent intent = new Intent();
        intent.putExtras(this.zzds);
        return intent;
    }
}
