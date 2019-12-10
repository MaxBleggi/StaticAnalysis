package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.internal.am.b;
import java.util.HashSet;
import org.json.JSONObject;

/* compiled from: IMASDK */
public class ap extends al {
    public ap(b bVar, HashSet<String> hashSet, JSONObject jSONObject, double d) {
        super(bVar, hashSet, jSONObject, d);
    }

    protected String a(Object... objArr) {
        return this.b.toString();
    }

    protected void a(String str) {
        b(str);
        super.a(str);
    }

    private void b(String str) {
        p a = p.a();
        if (a != null) {
            for (g gVar : a.b()) {
                if (this.a.contains(gVar.f())) {
                    gVar.e().b(str, this.c);
                }
            }
        }
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        a((String) obj);
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return a(objArr);
    }
}
