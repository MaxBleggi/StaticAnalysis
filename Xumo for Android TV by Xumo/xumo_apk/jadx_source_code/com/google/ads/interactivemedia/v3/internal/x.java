package com.google.ads.interactivemedia.v3.internal;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import com.google.ads.interactivemedia.v3.internal.u.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

/* compiled from: IMASDK */
public class x implements u {
    private final int[] a = new int[2];

    public JSONObject a(View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        view.getLocationOnScreen(this.a);
        return ac.a(this.a[0], this.a[1], width, height);
    }

    public void a(View view, JSONObject jSONObject, a aVar, boolean z) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (z) {
                if (VERSION.SDK_INT >= true) {
                    b(viewGroup, jSONObject, aVar);
                }
            }
            a(viewGroup, jSONObject, aVar);
        }
    }

    private void a(ViewGroup viewGroup, JSONObject jSONObject, a aVar) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            aVar.a(viewGroup.getChildAt(i), this, jSONObject);
        }
    }

    @TargetApi(21)
    private void b(ViewGroup viewGroup, JSONObject jSONObject, a aVar) {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            ArrayList arrayList = (ArrayList) hashMap.get(Float.valueOf(childAt.getZ()));
            if (arrayList == null) {
                arrayList = new ArrayList();
                hashMap.put(Float.valueOf(childAt.getZ()), arrayList);
            }
            arrayList.add(childAt);
        }
        viewGroup = new ArrayList(hashMap.keySet());
        Collections.sort(viewGroup);
        viewGroup = viewGroup.iterator();
        while (viewGroup.hasNext()) {
            Iterator it = ((ArrayList) hashMap.get((Float) viewGroup.next())).iterator();
            while (it.hasNext()) {
                aVar.a((View) it.next(), this, jSONObject);
            }
        }
    }
}
