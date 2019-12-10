package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast.ApplicationConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.VisibleForTesting;

@VisibleForTesting
final class zzcp implements ApplicationConnectionResult {
    private final Status zzgt;
    private final ApplicationMetadata zzxc;
    private final String zzxd;
    private final String zzxe;
    private final boolean zzxf;

    public zzcp(Status status, ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
        this.zzgt = status;
        this.zzxc = applicationMetadata;
        this.zzxd = str;
        this.zzxe = str2;
        this.zzxf = z;
    }

    public zzcp(Status status) {
        this(status, null, null, null, false);
    }

    public final Status getStatus() {
        return this.zzgt;
    }

    public final ApplicationMetadata getApplicationMetadata() {
        return this.zzxc;
    }

    public final String getApplicationStatus() {
        return this.zzxd;
    }

    public final String getSessionId() {
        return this.zzxe;
    }

    public final boolean getWasLaunched() {
        return this.zzxf;
    }
}
