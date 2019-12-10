package com.google.android.gms.internal.cast;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.MediaMetadataCompat.Builder;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompat.Callback;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.cast.framework.ReconnectionService;
import com.google.android.gms.cast.framework.media.MediaNotificationService;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.util.PlatformVersion;

public final class zzai implements Listener {
    private final Handler handler;
    private CastDevice zzai;
    private final Context zzhh;
    private RemoteMediaClient zzig;
    private final zzw zzjg;
    private boolean zzmq;
    private final CastOptions zzqr;
    private final ComponentName zzqs;
    private final zzx zzqt;
    private final zzx zzqu;
    private final Runnable zzqv;
    private MediaSessionCompat zzqw;
    private Callback zzqx;

    public zzai(Context context, CastOptions castOptions, zzw com_google_android_gms_internal_cast_zzw) {
        this.zzhh = context;
        this.zzqr = castOptions;
        this.zzjg = com_google_android_gms_internal_cast_zzw;
        if (this.zzqr.getCastMediaOptions() == null || TextUtils.isEmpty(this.zzqr.getCastMediaOptions().getExpandedControllerActivityClassName()) != null) {
            this.zzqs = null;
        } else {
            this.zzqs = new ComponentName(this.zzhh, this.zzqr.getCastMediaOptions().getExpandedControllerActivityClassName());
        }
        this.zzqt = new zzx(this.zzhh);
        this.zzqt.zza(new zzak(this));
        this.zzqu = new zzx(this.zzhh);
        this.zzqu.zza(new zzal(this));
        this.handler = new zzek(Looper.getMainLooper());
        this.zzqv = new zzaj(this);
    }

    public final void onSendingRemoteMediaRequest() {
    }

    public final void zza(RemoteMediaClient remoteMediaClient, CastDevice castDevice) {
        if (!(this.zzmq || this.zzqr == null || this.zzqr.getCastMediaOptions() == null || remoteMediaClient == null)) {
            if (castDevice != null) {
                this.zzig = remoteMediaClient;
                this.zzig.addListener(this);
                this.zzai = castDevice;
                if (PlatformVersion.isAtLeastLollipop() == null) {
                    ((AudioManager) this.zzhh.getSystemService(MimeTypes.BASE_TYPE_AUDIO)).requestAudioFocus(null, 3, 3);
                }
                remoteMediaClient = new ComponentName(this.zzhh, this.zzqr.getCastMediaOptions().getMediaIntentReceiverClassName());
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                intent.setComponent(remoteMediaClient);
                this.zzqw = new MediaSessionCompat(this.zzhh, "CastMediaSession", remoteMediaClient, PendingIntent.getBroadcast(this.zzhh, 0, intent, 0));
                this.zzqw.setFlags(3);
                zza(0, null);
                if (this.zzai != null && TextUtils.isEmpty(this.zzai.getFriendlyName()) == null) {
                    this.zzqw.setMetadata(new Builder().putString("android.media.metadata.ALBUM_ARTIST", this.zzhh.getResources().getString(R.string.cast_casting_to_device, new Object[]{this.zzai.getFriendlyName()})).build());
                }
                this.zzqx = new zzam(this);
                this.zzqw.setCallback(this.zzqx);
                this.zzqw.setActive(true);
                this.zzjg.setMediaSessionCompat(this.zzqw);
                this.zzmq = true;
                zzg(false);
            }
        }
    }

    public final void zzi(int i) {
        if (this.zzmq) {
            this.zzmq = false;
            if (this.zzig != null) {
                this.zzig.removeListener(this);
            }
            if (!PlatformVersion.isAtLeastLollipop()) {
                ((AudioManager) this.zzhh.getSystemService(MimeTypes.BASE_TYPE_AUDIO)).abandonAudioFocus(null);
            }
            this.zzjg.setMediaSessionCompat(null);
            if (this.zzqt != null) {
                this.zzqt.clear();
            }
            if (this.zzqu != null) {
                this.zzqu.clear();
            }
            if (this.zzqw != null) {
                this.zzqw.setSessionActivity(null);
                this.zzqw.setCallback(null);
                this.zzqw.setMetadata(new Builder().build());
                zza(0, null);
                this.zzqw.setActive(false);
                this.zzqw.release();
                this.zzqw = null;
            }
            this.zzig = null;
            this.zzai = null;
            this.zzqx = null;
            zzck();
            if (i == 0) {
                zzcl();
            }
        }
    }

    public final void onQueueStatusUpdated() {
        zzg(false);
    }

    public final void onStatusUpdated() {
        zzg(false);
    }

    public final void onMetadataUpdated() {
        zzg(false);
    }

    public final void onPreloadStatusUpdated() {
        zzg(false);
    }

    public final void onAdBreakStatusUpdated() {
        zzg(false);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzg(boolean r12) {
        /*
        r11 = this;
        r0 = r11.zzig;
        if (r0 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r0 = r11.zzig;
        r0 = r0.getMediaStatus();
        r1 = 0;
        if (r0 != 0) goto L_0x0010;
    L_0x000e:
        r2 = r1;
        goto L_0x0014;
    L_0x0010:
        r2 = r0.getMediaInfo();
    L_0x0014:
        if (r2 != 0) goto L_0x0018;
    L_0x0016:
        r3 = r1;
        goto L_0x001c;
    L_0x0018:
        r3 = r2.getMetadata();
    L_0x001c:
        r4 = 6;
        r5 = 3;
        r6 = 2;
        r7 = 0;
        r8 = 1;
        if (r0 == 0) goto L_0x0066;
    L_0x0023:
        if (r2 == 0) goto L_0x0066;
    L_0x0025:
        if (r3 != 0) goto L_0x0028;
    L_0x0027:
        goto L_0x0066;
    L_0x0028:
        r3 = r11.zzig;
        r3 = r3.getPlayerState();
        switch(r3) {
            case 1: goto L_0x003a;
            case 2: goto L_0x0037;
            case 3: goto L_0x0034;
            case 4: goto L_0x0032;
            default: goto L_0x0031;
        };
    L_0x0031:
        goto L_0x0066;
    L_0x0032:
        r3 = 0;
        goto L_0x0068;
    L_0x0034:
        r3 = 0;
    L_0x0035:
        r4 = 2;
        goto L_0x0068;
    L_0x0037:
        r3 = 0;
        r4 = 3;
        goto L_0x0068;
    L_0x003a:
        r3 = r0.getIdleReason();
        r9 = r11.zzig;
        r9 = r9.isLiveStream();
        if (r9 == 0) goto L_0x004a;
    L_0x0046:
        if (r3 != r6) goto L_0x004a;
    L_0x0048:
        r9 = 1;
        goto L_0x004b;
    L_0x004a:
        r9 = 0;
    L_0x004b:
        r10 = r0.getLoadingItemId();
        if (r10 == 0) goto L_0x0057;
    L_0x0051:
        if (r3 == r8) goto L_0x0055;
    L_0x0053:
        if (r3 != r5) goto L_0x0057;
    L_0x0055:
        r3 = 1;
        goto L_0x0058;
    L_0x0057:
        r3 = 0;
    L_0x0058:
        if (r9 == 0) goto L_0x005b;
    L_0x005a:
        goto L_0x0035;
    L_0x005b:
        r0 = r0.getQueueItemById(r10);
        if (r0 == 0) goto L_0x0067;
    L_0x0061:
        r2 = r0.getMedia();
        goto L_0x0068;
    L_0x0066:
        r3 = 0;
    L_0x0067:
        r4 = 0;
    L_0x0068:
        r11.zza(r4, r2);
        if (r4 != 0) goto L_0x0074;
    L_0x006d:
        r11.zzck();
        r11.zzcl();
        return;
    L_0x0074:
        r0 = r11.zzqr;
        r0 = r0.getCastMediaOptions();
        r0 = r0.getNotificationOptions();
        if (r0 == 0) goto L_0x010e;
    L_0x0080:
        r0 = r11.zzig;
        if (r0 == 0) goto L_0x010e;
    L_0x0084:
        r0 = new android.content.Intent;
        r2 = r11.zzhh;
        r4 = com.google.android.gms.cast.framework.media.MediaNotificationService.class;
        r0.<init>(r2, r4);
        r2 = "extra_media_notification_force_update";
        r0.putExtra(r2, r12);
        r12 = r11.zzhh;
        r12 = r12.getPackageName();
        r0.setPackage(r12);
        r12 = "com.google.android.gms.cast.framework.action.UPDATE_NOTIFICATION";
        r0.setAction(r12);
        r12 = "extra_media_info";
        r2 = r11.zzig;
        r2 = r2.getMediaInfo();
        r0.putExtra(r12, r2);
        r12 = "extra_remote_media_client_player_state";
        r2 = r11.zzig;
        r2 = r2.getPlayerState();
        r0.putExtra(r12, r2);
        r12 = "extra_cast_device";
        r2 = r11.zzai;
        r0.putExtra(r12, r2);
        r12 = "extra_media_session_token";
        r2 = r11.zzqw;
        if (r2 != 0) goto L_0x00c4;
    L_0x00c3:
        goto L_0x00ca;
    L_0x00c4:
        r1 = r11.zzqw;
        r1 = r1.getSessionToken();
    L_0x00ca:
        r0.putExtra(r12, r1);
        r12 = r11.zzig;
        r12 = r12.getMediaStatus();
        if (r12 == 0) goto L_0x0109;
    L_0x00d5:
        r1 = r12.getQueueRepeatMode();
        switch(r1) {
            case 1: goto L_0x00ee;
            case 2: goto L_0x00ee;
            case 3: goto L_0x00ee;
            default: goto L_0x00dc;
        };
    L_0x00dc:
        r1 = r12.getCurrentItemId();
        r1 = r12.getIndexById(r1);
        if (r1 == 0) goto L_0x00fe;
    L_0x00e6:
        r2 = r1.intValue();
        if (r2 <= 0) goto L_0x00f1;
    L_0x00ec:
        r2 = 1;
        goto L_0x00f2;
    L_0x00ee:
        r2 = 1;
    L_0x00ef:
        r7 = 1;
        goto L_0x00ff;
    L_0x00f1:
        r2 = 0;
    L_0x00f2:
        r1 = r1.intValue();
        r12 = r12.getQueueItemCount();
        r12 = r12 - r8;
        if (r1 >= r12) goto L_0x00ff;
    L_0x00fd:
        goto L_0x00ef;
    L_0x00fe:
        r2 = 0;
    L_0x00ff:
        r12 = "extra_can_skip_next";
        r0.putExtra(r12, r7);
        r12 = "extra_can_skip_prev";
        r0.putExtra(r12, r2);
    L_0x0109:
        r12 = r11.zzhh;
        r12.startService(r0);
    L_0x010e:
        if (r3 != 0) goto L_0x0113;
    L_0x0110:
        r11.zzh(r8);
    L_0x0113:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzai.zzg(boolean):void");
    }

    private final void zza(int i, MediaInfo mediaInfo) {
        if (i == 0) {
            this.zzqw.setPlaybackState(new PlaybackStateCompat.Builder().setState(0, 0, 1.0f).build());
            this.zzqw.setMetadata(new Builder().build());
            return;
        }
        PendingIntent pendingIntent;
        this.zzqw.setPlaybackState(new PlaybackStateCompat.Builder().setState(i, 0, 1.0f).setActions(mediaInfo.getStreamType() == 2 ? 5 : 512).build());
        i = this.zzqw;
        if (this.zzqs == null) {
            pendingIntent = null;
        } else {
            Intent intent = new Intent();
            intent.setComponent(this.zzqs);
            pendingIntent = PendingIntent.getActivity(this.zzhh, 0, intent, 134217728);
        }
        i.setSessionActivity(pendingIntent);
        MediaMetadata metadata = mediaInfo.getMetadata();
        this.zzqw.setMetadata(zzcj().putString("android.media.metadata.TITLE", metadata.getString(MediaMetadata.KEY_TITLE)).putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, metadata.getString(MediaMetadata.KEY_TITLE)).putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, metadata.getString(MediaMetadata.KEY_SUBTITLE)).putLong("android.media.metadata.DURATION", mediaInfo.getStreamDuration()).build());
        Uri zza = zza(metadata, 0);
        if (zza != null) {
            this.zzqt.zza(zza);
        } else {
            zza(null, 0);
        }
        Uri zza2 = zza(metadata, 3);
        if (zza2 != 0) {
            this.zzqu.zza(zza2);
        } else {
            zza(null, 3);
        }
    }

    private final void zza(Bitmap bitmap, int i) {
        if (i != 0) {
            if (i == 3) {
                this.zzqw.setMetadata(zzcj().putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, bitmap).build());
            }
        } else if (bitmap != null) {
            this.zzqw.setMetadata(zzcj().putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, bitmap).build());
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Config.ARGB_8888);
            bitmap.eraseColor(0);
            this.zzqw.setMetadata(zzcj().putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, bitmap).build());
        }
    }

    private final Uri zza(MediaMetadata mediaMetadata, int i) {
        mediaMetadata = this.zzqr.getCastMediaOptions().getImagePicker() != null ? this.zzqr.getCastMediaOptions().getImagePicker().onPickImage(mediaMetadata, i) : mediaMetadata.hasImages() != 0 ? (WebImage) mediaMetadata.getImages().get(0) : null;
        if (mediaMetadata == null) {
            return null;
        }
        return mediaMetadata.getUrl();
    }

    private final Builder zzcj() {
        MediaMetadataCompat metadata = this.zzqw.getController().getMetadata();
        if (metadata == null) {
            return new Builder();
        }
        return new Builder(metadata);
    }

    private final void zzck() {
        if (this.zzqr.getCastMediaOptions().getNotificationOptions() != null) {
            Intent intent = new Intent(this.zzhh, MediaNotificationService.class);
            intent.setPackage(this.zzhh.getPackageName());
            intent.setAction(MediaNotificationService.ACTION_UPDATE_NOTIFICATION);
            this.zzhh.stopService(intent);
        }
    }

    private final void zzh(boolean r4) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r3 = this;
        r0 = r3.zzqr;
        r0 = r0.getEnableReconnectionService();
        if (r0 != 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r0 = r3.handler;
        r1 = r3.zzqv;
        r0.removeCallbacks(r1);
        r0 = new android.content.Intent;
        r1 = r3.zzhh;
        r2 = com.google.android.gms.cast.framework.ReconnectionService.class;
        r0.<init>(r1, r2);
        r1 = r3.zzhh;
        r1 = r1.getPackageName();
        r0.setPackage(r1);
        r1 = r3.zzhh;	 Catch:{ IllegalStateException -> 0x0028 }
        r1.startService(r0);	 Catch:{ IllegalStateException -> 0x0028 }
        return;
    L_0x0028:
        if (r4 == 0) goto L_0x0033;
    L_0x002a:
        r4 = r3.handler;
        r0 = r3.zzqv;
        r1 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4.postDelayed(r0, r1);
    L_0x0033:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzai.zzh(boolean):void");
    }

    private final void zzcl() {
        if (this.zzqr.getEnableReconnectionService()) {
            this.handler.removeCallbacks(this.zzqv);
            Intent intent = new Intent(this.zzhh, ReconnectionService.class);
            intent.setPackage(this.zzhh.getPackageName());
            this.zzhh.stopService(intent);
        }
    }

    final /* synthetic */ void zzcm() {
        zzh(false);
    }
}
