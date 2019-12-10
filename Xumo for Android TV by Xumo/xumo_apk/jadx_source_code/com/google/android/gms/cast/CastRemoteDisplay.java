package com.google.android.gms.cast;

import android.content.Context;
import android.view.Display;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.cast.zzcy;
import com.google.android.gms.internal.cast.zzdg;
import com.google.android.gms.internal.cast.zzds;
import com.google.android.gms.internal.cast.zzec;

public final class CastRemoteDisplay {
    @Deprecated
    public static final Api<CastRemoteDisplayOptions> API = new Api("CastRemoteDisplay.API", CLIENT_BUILDER, zzdg.zzxp);
    private static final AbstractClientBuilder<zzec, CastRemoteDisplayOptions> CLIENT_BUILDER = new zzo();
    public static final int CONFIGURATION_INTERACTIVE_NONREALTIME = 2;
    public static final int CONFIGURATION_INTERACTIVE_REALTIME = 1;
    public static final int CONFIGURATION_NONINTERACTIVE = 3;
    @Deprecated
    public static final CastRemoteDisplayApi CastRemoteDisplayApi = new zzds(API);
    public static final String EXTRA_INT_SESSION_ENDED_STATUS_CODE = "extra_int_session_ended_status_code";

    @Deprecated
    public interface CastRemoteDisplaySessionCallbacks {
        void onRemoteDisplayEnded(Status status);
    }

    public @interface Configuration {
    }

    @Deprecated
    public interface CastRemoteDisplaySessionResult extends Result {
        Display getPresentationDisplay();
    }

    @Deprecated
    public static final class CastRemoteDisplayOptions implements HasOptions {
        final CastDevice zzai;
        final CastRemoteDisplaySessionCallbacks zzbb;
        final int zzbc;

        @Deprecated
        public static final class Builder {
            CastDevice zzai;
            int zzbc = 2;
            CastRemoteDisplaySessionCallbacks zzbd;

            public Builder(CastDevice castDevice, CastRemoteDisplaySessionCallbacks castRemoteDisplaySessionCallbacks) {
                Preconditions.checkNotNull(castDevice, "CastDevice parameter cannot be null");
                this.zzai = castDevice;
                this.zzbd = castRemoteDisplaySessionCallbacks;
            }

            public final Builder setConfigPreset(@Configuration int i) {
                this.zzbc = i;
                return this;
            }

            public final CastRemoteDisplayOptions build() {
                return new CastRemoteDisplayOptions();
            }
        }

        private CastRemoteDisplayOptions(Builder builder) {
            this.zzai = builder.zzai;
            this.zzbb = builder.zzbd;
            this.zzbc = builder.zzbc;
        }
    }

    public static final boolean isRemoteDisplaySdkSupported(Context context) {
        zzcy.initialize(context);
        return ((Boolean) zzcy.zzxn.get()).booleanValue();
    }

    private CastRemoteDisplay() {
    }

    public static CastRemoteDisplayClient getClient(@NonNull Context context) {
        return new CastRemoteDisplayClient(context);
    }
}
