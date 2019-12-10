package com.google.android.gms.internal.cast;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.cast.zzbt;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.Settings;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.Task;
import java.util.List;

public final class zzci extends GoogleApi<NoOptions> {
    private static final Api<NoOptions> API = new Api("CastApi.API", CLIENT_BUILDER, CLIENT_KEY);
    private static final AbstractClientBuilder<zzcm, NoOptions> CLIENT_BUILDER = new zzcj();
    private static final ClientKey<zzcm> CLIENT_KEY = new ClientKey();

    public zzci(@NonNull Context context) {
        super(context, API, null, Settings.DEFAULT_SETTINGS);
    }

    public final Task<Void> zza(@NonNull String[] strArr, String str, List<zzbt> list) {
        return doWrite((TaskApiCall) new zzck(this, strArr, str, null));
    }
}
