package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.Ad;
import com.google.ads.interactivemedia.v3.api.AdEvent;
import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventType;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: IMASDK */
public class ie implements AdEvent {
    private AdEventType a;
    private Ad b;
    private Map<String, String> c;

    ie(AdEventType adEventType, Ad ad, Map<String, String> map) {
        this.a = adEventType;
        this.b = ad;
        this.c = map;
    }

    public AdEventType getType() {
        return this.a;
    }

    public Ad getAd() {
        return this.b;
    }

    public Map<String, String> getAdData() {
        return this.c;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ie)) {
            return false;
        }
        ie ieVar = (ie) obj;
        return this.a == ieVar.a && ks.a(this.b, ieVar.b) && ks.a(this.c, ieVar.c) != null;
    }

    public int hashCode() {
        return ks.a(this.a, this.b, this.c);
    }

    public String toString() {
        Object obj;
        String valueOf = String.valueOf(String.format("AdEvent[type=%s, ad=%s", new Object[]{this.a, this.b}));
        if (this.c == null) {
            obj = "]";
        } else {
            obj = String.format(", adData=%s]", new Object[]{a()});
        }
        String valueOf2 = String.valueOf(obj);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private String a() {
        if (this.c == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("{");
        Iterator it = this.c.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            stringBuilder.append((String) entry.getKey());
            stringBuilder.append(": ");
            stringBuilder.append((String) entry.getValue());
            if (it.hasNext()) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
