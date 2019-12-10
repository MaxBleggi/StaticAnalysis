package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.google.ads.interactivemedia.v3.api.CompanionAdSlot.ClickListener;
import com.google.ads.interactivemedia.v3.impl.data.CompanionData;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/* compiled from: IMASDK */
public class jb extends ImageView implements OnClickListener {
    private final CompanionData a;
    private final jd b;
    private final String c;
    private final List<ClickListener> d;

    public jb(Context context, jd jdVar, CompanionData companionData, String str, List<ClickListener> list) {
        super(context);
        this.b = jdVar;
        this.a = companionData;
        this.c = str;
        this.d = list;
        setOnClickListener(this);
    }

    Bitmap a(String str) throws IOException {
        return BitmapFactory.decodeStream(new URL(str).openConnection().getInputStream());
    }

    public void a() {
        new AsyncTask<Void, Void, Bitmap>(this) {
            Exception a = null;
            final /* synthetic */ jb b;

            {
                this.b = r1;
            }

            protected Bitmap a(Void... voidArr) {
                try {
                    return this.b.a(this.b.a.src());
                } catch (Void[] voidArr2) {
                    this.a = voidArr2;
                    return null;
                }
            }

            protected void a(Bitmap bitmap) {
                if (bitmap == null) {
                    String src = this.b.a.src();
                    String valueOf = String.valueOf(this.a);
                    StringBuilder stringBuilder = new StringBuilder((String.valueOf(src).length() + 33) + String.valueOf(valueOf).length());
                    stringBuilder.append("Loading image companion ");
                    stringBuilder.append(src);
                    stringBuilder.append(" failed: ");
                    stringBuilder.append(valueOf);
                    Log.e("IMASDK", stringBuilder.toString());
                    return;
                }
                this.b.b();
                this.b.setImageBitmap(bitmap);
            }

            protected /* synthetic */ void onPostExecute(Object obj) {
                a((Bitmap) obj);
            }

            protected /* synthetic */ Object doInBackground(Object[] objArr) {
                return a((Void[]) objArr);
            }
        }.execute(new Void[0]);
    }

    private void b() {
        this.b.a(this.a.companionId(), this.c);
    }

    public void onClick(View view) {
        for (ClickListener onCompanionAdClick : this.d) {
            onCompanionAdClick.onCompanionAdClick();
        }
        this.b.d(this.a.clickThroughUrl());
    }
}
