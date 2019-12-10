package com.google.ads.interactivemedia.v3.internal;

import android.os.AsyncTask;
import java.util.concurrent.ThreadPoolExecutor;
import org.json.JSONObject;

/* compiled from: IMASDK */
public abstract class am extends AsyncTask<Object, Void, String> {
    private a a;
    protected final b d;

    /* compiled from: IMASDK */
    public interface a {
        void a(am amVar);
    }

    /* compiled from: IMASDK */
    public interface b {
        void a(JSONObject jSONObject);

        JSONObject b();
    }

    public am(b bVar) {
        this.d = bVar;
    }

    public void a(a aVar) {
        this.a = aVar;
    }

    public void a(ThreadPoolExecutor threadPoolExecutor) {
        executeOnExecutor(threadPoolExecutor, new Object[0]);
    }

    protected void a(String str) {
        if (this.a != null) {
            this.a.a(this);
        }
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        a((String) obj);
    }
}
