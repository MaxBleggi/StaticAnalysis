package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;

public abstract class zzcg<R extends Result> extends ApiMethodImpl<R, zzco> {
    public zzcg(GoogleApiClient googleApiClient) {
        super(Cast.API, googleApiClient);
    }

    public final void zzk(int i) {
        setResult(createFailedResult(new Status(CastStatusCodes.INVALID_REQUEST)));
    }
}
