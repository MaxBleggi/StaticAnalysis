package com.google.android.gms.internal.measurement;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.Settings;
import com.google.android.gms.common.util.VisibleForTesting;

public final class zztp extends GoogleApi<NoOptions> {
    private static final Api<NoOptions> API = new Api("DynamicLinks.API", zzacu, CLIENT_KEY);
    private static final ClientKey<zztr> CLIENT_KEY = new ClientKey();
    private static final AbstractClientBuilder<zztr, NoOptions> zzacu = new zztq();

    @VisibleForTesting
    public zztp(@NonNull Context context) {
        super(context, API, null, Settings.DEFAULT_SETTINGS);
    }
}
