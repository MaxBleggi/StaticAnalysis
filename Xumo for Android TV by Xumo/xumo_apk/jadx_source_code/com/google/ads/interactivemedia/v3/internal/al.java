package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.internal.am.b;
import java.util.HashSet;
import org.json.JSONObject;

/* compiled from: IMASDK */
public abstract class al extends am {
    protected final HashSet<String> a;
    protected final JSONObject b;
    protected final double c;

    public al(b bVar, HashSet<String> hashSet, JSONObject jSONObject, double d) {
        super(bVar);
        this.a = new HashSet(hashSet);
        this.b = jSONObject;
        this.c = d;
    }
}
