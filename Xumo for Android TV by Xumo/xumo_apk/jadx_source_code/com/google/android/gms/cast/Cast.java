package com.google.android.gms.cast;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.cast.LaunchOptions.Builder;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.cast.zzcg;
import com.google.android.gms.internal.cast.zzco;
import com.google.android.gms.internal.cast.zzdg;
import java.io.IOException;

public final class Cast {
    public static final int ACTIVE_INPUT_STATE_NO = 0;
    public static final int ACTIVE_INPUT_STATE_UNKNOWN = -1;
    public static final int ACTIVE_INPUT_STATE_YES = 1;
    public static final Api<CastOptions> API = new Api("Cast.API", CLIENT_BUILDER, zzdg.zzxo);
    @VisibleForTesting
    private static final AbstractClientBuilder<zzco, CastOptions> CLIENT_BUILDER = new zze();
    public static final CastApi CastApi = new zza();
    public static final String EXTRA_APP_NO_LONGER_RUNNING = "com.google.android.gms.cast.EXTRA_APP_NO_LONGER_RUNNING";
    public static final int MAX_MESSAGE_LENGTH = 65536;
    public static final int MAX_NAMESPACE_LENGTH = 128;
    public static final int STANDBY_STATE_NO = 0;
    public static final int STANDBY_STATE_UNKNOWN = -1;
    public static final int STANDBY_STATE_YES = 1;

    @Deprecated
    public interface CastApi {

        public static final class zza implements CastApi {
            public final void requestStatus(com.google.android.gms.common.api.GoogleApiClient r2) throws java.io.IOException, java.lang.IllegalStateException {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r1 = this;
                r0 = com.google.android.gms.internal.cast.zzdg.zzxo;	 Catch:{ RemoteException -> 0x000c }
                r2 = r2.getClient(r0);	 Catch:{ RemoteException -> 0x000c }
                r2 = (com.google.android.gms.internal.cast.zzco) r2;	 Catch:{ RemoteException -> 0x000c }
                r2.requestStatus();	 Catch:{ RemoteException -> 0x000c }
                return;
            L_0x000c:
                r2 = new java.io.IOException;
                r0 = "service error";
                r2.<init>(r0);
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.Cast.CastApi.zza.requestStatus(com.google.android.gms.common.api.GoogleApiClient):void");
            }

            public final PendingResult<Status> sendMessage(GoogleApiClient googleApiClient, String str, String str2) {
                return googleApiClient.execute(new zzf(this, googleApiClient, str, str2));
            }

            public final PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str) {
                return googleApiClient.execute(new zzg(this, googleApiClient, str));
            }

            public final PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, LaunchOptions launchOptions) {
                return googleApiClient.execute(new zzh(this, googleApiClient, str, launchOptions));
            }

            @Deprecated
            public final PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, boolean z) {
                return launchApplication(googleApiClient, str, new Builder().setRelaunchIfRunning(z).build());
            }

            private final PendingResult<ApplicationConnectionResult> zza(GoogleApiClient googleApiClient, String str, String str2, zzag com_google_android_gms_cast_zzag) {
                return googleApiClient.execute(new zzi(this, googleApiClient, str, str2, null));
            }

            public final PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str, String str2) {
                return zza(googleApiClient, str, str2, null);
            }

            public final PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str) {
                return zza(googleApiClient, str, null, null);
            }

            public final PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient) {
                return zza(googleApiClient, null, null, null);
            }

            public final PendingResult<Status> leaveApplication(GoogleApiClient googleApiClient) {
                return googleApiClient.execute(new zzj(this, googleApiClient));
            }

            public final PendingResult<Status> stopApplication(GoogleApiClient googleApiClient) {
                return googleApiClient.execute(new zzk(this, googleApiClient));
            }

            public final PendingResult<Status> stopApplication(GoogleApiClient googleApiClient, String str) {
                return googleApiClient.execute(new zzl(this, googleApiClient, str));
            }

            public final void setVolume(com.google.android.gms.common.api.GoogleApiClient r2, double r3) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r1 = this;
                r0 = com.google.android.gms.internal.cast.zzdg.zzxo;	 Catch:{ RemoteException -> 0x000c }
                r2 = r2.getClient(r0);	 Catch:{ RemoteException -> 0x000c }
                r2 = (com.google.android.gms.internal.cast.zzco) r2;	 Catch:{ RemoteException -> 0x000c }
                r2.setVolume(r3);	 Catch:{ RemoteException -> 0x000c }
                return;
            L_0x000c:
                r2 = new java.io.IOException;
                r3 = "service error";
                r2.<init>(r3);
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.Cast.CastApi.zza.setVolume(com.google.android.gms.common.api.GoogleApiClient, double):void");
            }

            public final double getVolume(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((zzco) googleApiClient.getClient(zzdg.zzxo)).getVolume();
            }

            public final void setMute(com.google.android.gms.common.api.GoogleApiClient r2, boolean r3) throws java.io.IOException, java.lang.IllegalStateException {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r1 = this;
                r0 = com.google.android.gms.internal.cast.zzdg.zzxo;	 Catch:{ RemoteException -> 0x000c }
                r2 = r2.getClient(r0);	 Catch:{ RemoteException -> 0x000c }
                r2 = (com.google.android.gms.internal.cast.zzco) r2;	 Catch:{ RemoteException -> 0x000c }
                r2.setMute(r3);	 Catch:{ RemoteException -> 0x000c }
                return;
            L_0x000c:
                r2 = new java.io.IOException;
                r3 = "service error";
                r2.<init>(r3);
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.Cast.CastApi.zza.setMute(com.google.android.gms.common.api.GoogleApiClient, boolean):void");
            }

            public final boolean isMute(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((zzco) googleApiClient.getClient(zzdg.zzxo)).isMute();
            }

            public final int getActiveInputState(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((zzco) googleApiClient.getClient(zzdg.zzxo)).getActiveInputState();
            }

            public final int getStandbyState(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((zzco) googleApiClient.getClient(zzdg.zzxo)).getStandbyState();
            }

            public final ApplicationMetadata getApplicationMetadata(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((zzco) googleApiClient.getClient(zzdg.zzxo)).getApplicationMetadata();
            }

            public final String getApplicationStatus(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((zzco) googleApiClient.getClient(zzdg.zzxo)).getApplicationStatus();
            }

            public final void setMessageReceivedCallbacks(com.google.android.gms.common.api.GoogleApiClient r2, java.lang.String r3, com.google.android.gms.cast.Cast.MessageReceivedCallback r4) throws java.io.IOException, java.lang.IllegalStateException {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r1 = this;
                r0 = com.google.android.gms.internal.cast.zzdg.zzxo;	 Catch:{ RemoteException -> 0x000c }
                r2 = r2.getClient(r0);	 Catch:{ RemoteException -> 0x000c }
                r2 = (com.google.android.gms.internal.cast.zzco) r2;	 Catch:{ RemoteException -> 0x000c }
                r2.setMessageReceivedCallbacks(r3, r4);	 Catch:{ RemoteException -> 0x000c }
                return;
            L_0x000c:
                r2 = new java.io.IOException;
                r3 = "service error";
                r2.<init>(r3);
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.Cast.CastApi.zza.setMessageReceivedCallbacks(com.google.android.gms.common.api.GoogleApiClient, java.lang.String, com.google.android.gms.cast.Cast$MessageReceivedCallback):void");
            }

            public final void removeMessageReceivedCallbacks(com.google.android.gms.common.api.GoogleApiClient r2, java.lang.String r3) throws java.io.IOException, java.lang.IllegalArgumentException {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r1 = this;
                r0 = com.google.android.gms.internal.cast.zzdg.zzxo;	 Catch:{ RemoteException -> 0x000c }
                r2 = r2.getClient(r0);	 Catch:{ RemoteException -> 0x000c }
                r2 = (com.google.android.gms.internal.cast.zzco) r2;	 Catch:{ RemoteException -> 0x000c }
                r2.removeMessageReceivedCallbacks(r3);	 Catch:{ RemoteException -> 0x000c }
                return;
            L_0x000c:
                r2 = new java.io.IOException;
                r3 = "service error";
                r2.<init>(r3);
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.Cast.CastApi.zza.removeMessageReceivedCallbacks(com.google.android.gms.common.api.GoogleApiClient, java.lang.String):void");
            }
        }

        int getActiveInputState(GoogleApiClient googleApiClient) throws IllegalStateException;

        ApplicationMetadata getApplicationMetadata(GoogleApiClient googleApiClient) throws IllegalStateException;

        String getApplicationStatus(GoogleApiClient googleApiClient) throws IllegalStateException;

        int getStandbyState(GoogleApiClient googleApiClient) throws IllegalStateException;

        double getVolume(GoogleApiClient googleApiClient) throws IllegalStateException;

        boolean isMute(GoogleApiClient googleApiClient) throws IllegalStateException;

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient);

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str);

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str, String str2);

        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str);

        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, LaunchOptions launchOptions);

        @Deprecated
        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, boolean z);

        PendingResult<Status> leaveApplication(GoogleApiClient googleApiClient);

        void removeMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str) throws IOException, IllegalArgumentException;

        void requestStatus(GoogleApiClient googleApiClient) throws IOException, IllegalStateException;

        PendingResult<Status> sendMessage(GoogleApiClient googleApiClient, String str, String str2);

        void setMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str, MessageReceivedCallback messageReceivedCallback) throws IOException, IllegalStateException;

        void setMute(GoogleApiClient googleApiClient, boolean z) throws IOException, IllegalStateException;

        void setVolume(GoogleApiClient googleApiClient, double d) throws IOException, IllegalArgumentException, IllegalStateException;

        PendingResult<Status> stopApplication(GoogleApiClient googleApiClient);

        PendingResult<Status> stopApplication(GoogleApiClient googleApiClient, String str);
    }

    public static class Listener {
        public void onActiveInputStateChanged(int i) {
        }

        public void onApplicationDisconnected(int i) {
        }

        public void onApplicationMetadataChanged(ApplicationMetadata applicationMetadata) {
        }

        public void onApplicationStatusChanged() {
        }

        public void onStandbyStateChanged(int i) {
        }

        public void onVolumeChanged() {
        }
    }

    public interface MessageReceivedCallback {
        void onMessageReceived(CastDevice castDevice, String str, String str2);
    }

    public interface ApplicationConnectionResult extends Result {
        ApplicationMetadata getApplicationMetadata();

        String getApplicationStatus();

        String getSessionId();

        boolean getWasLaunched();
    }

    public static final class CastOptions implements HasOptions {
        final Bundle extras;
        final CastDevice zzai;
        final Listener zzaj;
        private final int zzak;

        public static final class Builder {
            private Bundle extras;
            CastDevice zzai;
            Listener zzaj;
            private int zzak = null;

            public Builder(CastDevice castDevice, Listener listener) {
                Preconditions.checkNotNull(castDevice, "CastDevice parameter cannot be null");
                Preconditions.checkNotNull(listener, "CastListener parameter cannot be null");
                this.zzai = castDevice;
                this.zzaj = listener;
            }

            public final Builder setVerboseLoggingEnabled(boolean z) {
                if (z) {
                    this.zzak |= 1;
                } else {
                    this.zzak &= -2;
                }
                return this;
            }

            public final Builder zza(Bundle bundle) {
                this.extras = bundle;
                return this;
            }

            public final CastOptions build() {
                return new CastOptions();
            }
        }

        private CastOptions(Builder builder) {
            this.zzai = builder.zzai;
            this.zzaj = builder.zzaj;
            this.zzak = builder.zzak;
            this.extras = builder.extras;
        }

        @Deprecated
        public static Builder builder(CastDevice castDevice, Listener listener) {
            return new Builder(castDevice, listener);
        }
    }

    @VisibleForTesting
    static abstract class zza extends zzcg<ApplicationConnectionResult> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public void zza(zzco com_google_android_gms_internal_cast_zzco) throws RemoteException {
        }

        public /* synthetic */ void doExecute(AnyClient anyClient) throws RemoteException {
            zza((zzco) anyClient);
        }

        public /* synthetic */ Result createFailedResult(Status status) {
            return new zzm(this, status);
        }
    }

    private Cast() {
    }
}
