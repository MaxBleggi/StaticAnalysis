package com.google.android.gms.cast;

import android.text.TextUtils;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.cast.zzdr;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public final class zzam {
    private int zzdz;
    private List<MediaMetadata> zzea;
    private List<WebImage> zzeb;
    private double zzec;
    private String zzf;

    public final void zze(JSONObject jSONObject) {
        if (jSONObject != null) {
            String optString = jSONObject.optString("containerType", "");
            Object obj = -1;
            int hashCode = optString.hashCode();
            int i = 0;
            if (hashCode != 6924225) {
                if (hashCode == 828666841) {
                    if (optString.equals("GENERIC_CONTAINER")) {
                        obj = null;
                    }
                }
            } else if (optString.equals("AUDIOBOOK_CONTAINER")) {
                obj = 1;
            }
            switch (obj) {
                case null:
                    this.zzdz = 0;
                    break;
                case 1:
                    this.zzdz = 1;
                    break;
                default:
                    break;
            }
            this.zzf = jSONObject.optString("title", null);
            JSONArray optJSONArray = jSONObject.optJSONArray("sections");
            if (optJSONArray != null) {
                this.zzea = new ArrayList();
                while (i < optJSONArray.length()) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        MediaMetadata mediaMetadata = new MediaMetadata();
                        mediaMetadata.zze(optJSONObject);
                        this.zzea.add(mediaMetadata);
                    }
                    i++;
                }
            }
            optJSONArray = jSONObject.optJSONArray("containerImages");
            if (optJSONArray != null) {
                this.zzeb = new ArrayList();
                zzdr.zza(this.zzeb, optJSONArray);
            }
            this.zzec = jSONObject.optDouble("containerDuration", this.zzec);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzam)) {
            return false;
        }
        zzam com_google_android_gms_cast_zzam = (zzam) obj;
        return this.zzdz == com_google_android_gms_cast_zzam.zzdz && TextUtils.equals(this.zzf, com_google_android_gms_cast_zzam.zzf) && Objects.equal(this.zzea, com_google_android_gms_cast_zzam.zzea) && Objects.equal(this.zzeb, com_google_android_gms_cast_zzam.zzeb) && this.zzec == com_google_android_gms_cast_zzam.zzec;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzdz), this.zzf, this.zzea, this.zzeb, Double.valueOf(this.zzec));
    }
}
