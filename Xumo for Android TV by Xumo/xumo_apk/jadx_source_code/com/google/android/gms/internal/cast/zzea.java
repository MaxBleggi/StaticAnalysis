package com.google.android.gms.internal.cast;

import android.view.Display;
import com.google.android.gms.cast.CastRemoteDisplay.CastRemoteDisplaySessionResult;
import com.google.android.gms.common.api.Status;

final class zzea implements CastRemoteDisplaySessionResult {
    private final Display zzby;
    private final Status zzgt;

    public zzea(Display display) {
        this.zzgt = Status.RESULT_SUCCESS;
        this.zzby = display;
    }

    public zzea(Status status) {
        this.zzgt = status;
        this.zzby = null;
    }

    public final Status getStatus() {
        return this.zzgt;
    }

    public final Display getPresentationDisplay() {
        return this.zzby;
    }
}
