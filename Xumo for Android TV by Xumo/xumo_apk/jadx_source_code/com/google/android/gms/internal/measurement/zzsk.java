package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

final class zzsk extends ContentObserver {
    zzsk(Handler handler) {
        super(null);
    }

    public final void onChange(boolean z) {
        zzsj.zzbrb.set(true);
    }
}
