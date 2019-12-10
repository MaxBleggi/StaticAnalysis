package com.google.android.gms.cast.framework.media;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat.Action;
import androidx.core.app.NotificationCompat.Builder;
import androidx.media.app.NotificationCompat.MediaStyle;
import com.google.android.gms.cast.framework.AppVisibilityListener;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.cast.zzdh;
import com.google.android.gms.internal.cast.zzx;
import java.util.ArrayList;
import java.util.List;

public class MediaNotificationService extends Service {
    public static final String ACTION_UPDATE_NOTIFICATION = "com.google.android.gms.cast.framework.action.UPDATE_NOTIFICATION";
    private static final zzdh zzbe = new zzdh("MediaNotificationService");
    private Notification zzbu;
    private NotificationOptions zzll;
    private ImagePicker zzln;
    private ComponentName zzls;
    private ComponentName zzlt;
    private List<String> zzlu = new ArrayList();
    private int[] zzlv;
    private zzd zzlw;
    private long zzlx;
    private zzx zzly;
    private ImageHints zzlz;
    private Resources zzma;
    private AppVisibilityListener zzmb;
    private zza zzmc;
    private zzb zzmd;
    private CastContext zzme;
    private final BroadcastReceiver zzmf = new zzh(this);

    private static class zza {
        public final int streamType;
        public final String zzf;
        public final Token zzmi;
        public final boolean zzmj;
        public final String zzmk;
        public final boolean zzml;
        public final boolean zzmm;

        public zza(boolean z, int i, String str, String str2, Token token, boolean z2, boolean z3) {
            this.zzmj = z;
            this.streamType = i;
            this.zzf = str;
            this.zzmk = str2;
            this.zzmi = token;
            this.zzml = z2;
            this.zzmm = z3;
        }
    }

    private static class zzb {
        public final Uri zzmn;
        public Bitmap zzmo;

        public zzb(WebImage webImage) {
            if (webImage == null) {
                webImage = null;
            } else {
                webImage = webImage.getUrl();
            }
            this.zzmn = webImage;
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        this.zzme = CastContext.getSharedInstance(this);
        CastMediaOptions castMediaOptions = this.zzme.getCastOptions().getCastMediaOptions();
        this.zzll = castMediaOptions.getNotificationOptions();
        this.zzln = castMediaOptions.getImagePicker();
        this.zzma = getResources();
        this.zzls = new ComponentName(getApplicationContext(), castMediaOptions.getMediaIntentReceiverClassName());
        if (TextUtils.isEmpty(this.zzll.getTargetActivityClassName())) {
            this.zzlt = null;
        } else {
            this.zzlt = new ComponentName(getApplicationContext(), this.zzll.getTargetActivityClassName());
        }
        this.zzlw = this.zzll.zzby();
        if (this.zzlw == null) {
            this.zzlu.addAll(this.zzll.getActions());
            this.zzlv = (int[]) this.zzll.getCompatActionIndices().clone();
        } else {
            this.zzlv = null;
        }
        this.zzlx = this.zzll.getSkipStepMs();
        int dimensionPixelSize = this.zzma.getDimensionPixelSize(this.zzll.zzbm());
        this.zzlz = new ImageHints(1, dimensionPixelSize, dimensionPixelSize);
        this.zzly = new zzx(getApplicationContext(), this.zzlz);
        this.zzmb = new zzi(this);
        this.zzme.addAppVisibilityListener(this.zzmb);
        if (this.zzlt != null) {
            registerReceiver(this.zzmf, new IntentFilter(this.zzlt.flattenToString()));
        }
        if (PlatformVersion.isAtLeastO()) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            NotificationChannel notificationChannel = new NotificationChannel("cast_media_notification", "Cast", 2);
            notificationChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int onStartCommand(android.content.Intent r17, int r18, int r19) {
        /*
        r16 = this;
        r0 = r16;
        r1 = r17;
        r2 = 1;
        if (r1 == 0) goto L_0x00fd;
    L_0x0007:
        r3 = r17.getAction();
        r4 = "com.google.android.gms.cast.framework.action.UPDATE_NOTIFICATION";
        r3 = r4.equals(r3);
        if (r3 == 0) goto L_0x00fd;
    L_0x0013:
        r3 = "extra_media_info";
        r3 = r1.getParcelableExtra(r3);
        r3 = (com.google.android.gms.cast.MediaInfo) r3;
        r4 = 0;
        if (r3 != 0) goto L_0x0020;
    L_0x001e:
        goto L_0x00f8;
    L_0x0020:
        r5 = r3.getMetadata();
        if (r5 != 0) goto L_0x0028;
    L_0x0026:
        goto L_0x00f8;
    L_0x0028:
        r6 = "extra_remote_media_client_player_state";
        r6 = r1.getIntExtra(r6, r4);
        if (r6 != 0) goto L_0x0032;
    L_0x0030:
        goto L_0x00f8;
    L_0x0032:
        r7 = "extra_cast_device";
        r7 = r1.getParcelableExtra(r7);
        r7 = (com.google.android.gms.cast.CastDevice) r7;
        if (r7 != 0) goto L_0x003e;
    L_0x003c:
        goto L_0x00f8;
    L_0x003e:
        r15 = new com.google.android.gms.cast.framework.media.MediaNotificationService$zza;
        r8 = 2;
        if (r6 != r8) goto L_0x0045;
    L_0x0043:
        r9 = 1;
        goto L_0x0046;
    L_0x0045:
        r9 = 0;
    L_0x0046:
        r10 = r3.getStreamType();
        r3 = "com.google.android.gms.cast.metadata.TITLE";
        r11 = r5.getString(r3);
        r12 = r7.getFriendlyName();
        r3 = "extra_media_session_token";
        r3 = r1.getParcelableExtra(r3);
        r13 = r3;
        r13 = (android.support.v4.media.session.MediaSessionCompat.Token) r13;
        r3 = "extra_can_skip_next";
        r14 = r1.getBooleanExtra(r3, r4);
        r3 = "extra_can_skip_prev";
        r3 = r1.getBooleanExtra(r3, r4);
        r8 = r15;
        r6 = r15;
        r15 = r3;
        r8.<init>(r9, r10, r11, r12, r13, r14, r15);
        r3 = "extra_media_notification_force_update";
        r1 = r1.getBooleanExtra(r3, r4);
        if (r1 != 0) goto L_0x00ac;
    L_0x0077:
        r1 = r0.zzmc;
        if (r1 == 0) goto L_0x00a9;
    L_0x007b:
        r3 = r6.zzmj;
        r7 = r1.zzmj;
        if (r3 != r7) goto L_0x00a9;
    L_0x0081:
        r3 = r6.streamType;
        r7 = r1.streamType;
        if (r3 != r7) goto L_0x00a9;
    L_0x0087:
        r3 = r6.zzf;
        r7 = r1.zzf;
        r3 = com.google.android.gms.internal.cast.zzcv.zza(r3, r7);
        if (r3 == 0) goto L_0x00a9;
    L_0x0091:
        r3 = r6.zzmk;
        r7 = r1.zzmk;
        r3 = com.google.android.gms.internal.cast.zzcv.zza(r3, r7);
        if (r3 == 0) goto L_0x00a9;
    L_0x009b:
        r3 = r6.zzml;
        r7 = r1.zzml;
        if (r3 != r7) goto L_0x00a9;
    L_0x00a1:
        r3 = r6.zzmm;
        r1 = r1.zzmm;
        if (r3 != r1) goto L_0x00a9;
    L_0x00a7:
        r1 = 1;
        goto L_0x00aa;
    L_0x00a9:
        r1 = 0;
    L_0x00aa:
        if (r1 != 0) goto L_0x00b1;
    L_0x00ac:
        r0.zzmc = r6;
        r16.zzaz();
    L_0x00b1:
        r1 = new com.google.android.gms.cast.framework.media.MediaNotificationService$zzb;
        r3 = r0.zzln;
        if (r3 == 0) goto L_0x00c0;
    L_0x00b7:
        r3 = r0.zzln;
        r6 = r0.zzlz;
        r3 = r3.onPickImage(r5, r6);
        goto L_0x00d2;
    L_0x00c0:
        r3 = r5.hasImages();
        if (r3 == 0) goto L_0x00d1;
    L_0x00c6:
        r3 = r5.getImages();
        r3 = r3.get(r4);
        r3 = (com.google.android.gms.common.images.WebImage) r3;
        goto L_0x00d2;
    L_0x00d1:
        r3 = 0;
    L_0x00d2:
        r1.<init>(r3);
        r3 = r0.zzmd;
        if (r3 == 0) goto L_0x00e4;
    L_0x00d9:
        r5 = r1.zzmn;
        r3 = r3.zzmn;
        r3 = com.google.android.gms.internal.cast.zzcv.zza(r5, r3);
        if (r3 == 0) goto L_0x00e4;
    L_0x00e3:
        r4 = 1;
    L_0x00e4:
        if (r4 != 0) goto L_0x00f7;
    L_0x00e6:
        r3 = r0.zzly;
        r4 = new com.google.android.gms.cast.framework.media.zzj;
        r4.<init>(r0, r1);
        r3.zza(r4);
        r3 = r0.zzly;
        r1 = r1.zzmn;
        r3.zza(r1);
    L_0x00f7:
        r4 = 1;
    L_0x00f8:
        if (r4 != 0) goto L_0x00fd;
    L_0x00fa:
        r0.stopForeground(r2);
    L_0x00fd:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.MediaNotificationService.onStartCommand(android.content.Intent, int, int):int");
    }

    public void onDestroy() {
        if (this.zzly != null) {
            this.zzly.clear();
        }
        if (this.zzlt != null) {
            try {
                unregisterReceiver(this.zzmf);
            } catch (Throwable e) {
                zzbe.zzc(e, "Unregistering trampoline BroadcastReceiver failed", new Object[0]);
            }
        }
        ((NotificationManager) getSystemService("notification")).cancel(1);
        this.zzme.removeAppVisibilityListener(this.zzmb);
    }

    private final void zzaz() {
        if (this.zzmc != null) {
            Bitmap bitmap;
            int[] iArr;
            PendingIntent pendingIntent = null;
            if (this.zzmd == null) {
                bitmap = null;
            } else {
                bitmap = this.zzmd.zzmo;
            }
            Builder visibility = new Builder(this, "cast_media_notification").setLargeIcon(bitmap).setSmallIcon(this.zzll.getSmallIconDrawableResId()).setContentTitle(this.zzmc.zzf).setContentText(this.zzma.getString(this.zzll.getCastingToDeviceStringResId(), new Object[]{this.zzmc.zzmk})).setOngoing(true).setShowWhen(false).setVisibility(1);
            if (this.zzlt != null) {
                Intent intent = new Intent();
                intent.putExtra("targetActivity", this.zzlt);
                intent.setAction(this.zzlt.flattenToString());
                pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 134217728);
            }
            if (pendingIntent != null) {
                visibility.setContentIntent(pendingIntent);
            }
            if (this.zzlw != null) {
                zzbe.i("mActionsProvider != null", new Object[0]);
                try {
                    Object obj;
                    int size;
                    int length;
                    int i;
                    int i2;
                    String action;
                    Object obj2;
                    Intent intent2;
                    List<NotificationAction> notificationActions = this.zzlw.getNotificationActions();
                    Object compactViewActionIndices = this.zzlw.getCompactViewActionIndices();
                    if (notificationActions != null) {
                        if (!notificationActions.isEmpty()) {
                            if (notificationActions.size() > 5) {
                                zzbe.e(String.valueOf(NotificationActionsProvider.class.getSimpleName()).concat(" provides more than 5 actions."), new Object[0]);
                                obj = null;
                                if (obj != null) {
                                    size = notificationActions.size();
                                    if (compactViewActionIndices != null) {
                                        if (compactViewActionIndices.length == 0) {
                                            length = compactViewActionIndices.length;
                                            i = 0;
                                            while (i < length) {
                                                i2 = compactViewActionIndices[i];
                                                if (i2 >= 0) {
                                                    if (i2 >= size) {
                                                        i++;
                                                    }
                                                }
                                                zzbe.e(String.valueOf(NotificationActionsProvider.class.getSimpleName()).concat("provides a compact view action whose index is out of bounds."), new Object[0]);
                                                obj = null;
                                                break;
                                            }
                                            obj = 1;
                                            if (obj == null) {
                                                iArr = (int[]) compactViewActionIndices.clone();
                                                for (NotificationAction notificationAction : notificationActions) {
                                                    new NotificationAction.Builder().setAction(notificationAction.getAction());
                                                    action = notificationAction.getAction();
                                                    if (!(action.equals(MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK) || action.equals(MediaIntentReceiver.ACTION_SKIP_NEXT) || action.equals(MediaIntentReceiver.ACTION_SKIP_PREV) || action.equals(MediaIntentReceiver.ACTION_FORWARD) || action.equals(MediaIntentReceiver.ACTION_REWIND))) {
                                                        if (action.equals(MediaIntentReceiver.ACTION_STOP_CASTING)) {
                                                            obj2 = null;
                                                            if (obj2 == null) {
                                                                zza(visibility, notificationAction.getAction());
                                                            } else {
                                                                intent2 = new Intent(notificationAction.getAction());
                                                                intent2.setComponent(this.zzls);
                                                                visibility.addAction(new Action.Builder(notificationAction.getIconResId(), notificationAction.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, 0)).build());
                                                            }
                                                        }
                                                    }
                                                    obj2 = 1;
                                                    if (obj2 == null) {
                                                        intent2 = new Intent(notificationAction.getAction());
                                                        intent2.setComponent(this.zzls);
                                                        visibility.addAction(new Action.Builder(notificationAction.getIconResId(), notificationAction.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, 0)).build());
                                                    } else {
                                                        zza(visibility, notificationAction.getAction());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    zzbe.e(String.valueOf(NotificationActionsProvider.class.getSimpleName()).concat(" doesn't provide any actions for compact view."), new Object[0]);
                                    obj = null;
                                    if (obj == null) {
                                        iArr = (int[]) compactViewActionIndices.clone();
                                        for (NotificationAction notificationAction2 : notificationActions) {
                                            new NotificationAction.Builder().setAction(notificationAction2.getAction());
                                            action = notificationAction2.getAction();
                                            if (action.equals(MediaIntentReceiver.ACTION_STOP_CASTING)) {
                                                obj2 = null;
                                                if (obj2 == null) {
                                                    zza(visibility, notificationAction2.getAction());
                                                } else {
                                                    intent2 = new Intent(notificationAction2.getAction());
                                                    intent2.setComponent(this.zzls);
                                                    visibility.addAction(new Action.Builder(notificationAction2.getIconResId(), notificationAction2.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, 0)).build());
                                                }
                                            } else {
                                                obj2 = 1;
                                                if (obj2 == null) {
                                                    intent2 = new Intent(notificationAction2.getAction());
                                                    intent2.setComponent(this.zzls);
                                                    visibility.addAction(new Action.Builder(notificationAction2.getIconResId(), notificationAction2.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, 0)).build());
                                                } else {
                                                    zza(visibility, notificationAction2.getAction());
                                                }
                                            }
                                        }
                                    }
                                }
                                return;
                            }
                            obj = 1;
                            if (obj != null) {
                                size = notificationActions.size();
                                if (compactViewActionIndices != null) {
                                    if (compactViewActionIndices.length == 0) {
                                        length = compactViewActionIndices.length;
                                        i = 0;
                                        while (i < length) {
                                            i2 = compactViewActionIndices[i];
                                            if (i2 >= 0) {
                                                if (i2 >= size) {
                                                    i++;
                                                }
                                            }
                                            zzbe.e(String.valueOf(NotificationActionsProvider.class.getSimpleName()).concat("provides a compact view action whose index is out of bounds."), new Object[0]);
                                            obj = null;
                                            break;
                                        }
                                        obj = 1;
                                        if (obj == null) {
                                            iArr = (int[]) compactViewActionIndices.clone();
                                            for (NotificationAction notificationAction22 : notificationActions) {
                                                new NotificationAction.Builder().setAction(notificationAction22.getAction());
                                                action = notificationAction22.getAction();
                                                if (action.equals(MediaIntentReceiver.ACTION_STOP_CASTING)) {
                                                    obj2 = 1;
                                                    if (obj2 == null) {
                                                        zza(visibility, notificationAction22.getAction());
                                                    } else {
                                                        intent2 = new Intent(notificationAction22.getAction());
                                                        intent2.setComponent(this.zzls);
                                                        visibility.addAction(new Action.Builder(notificationAction22.getIconResId(), notificationAction22.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, 0)).build());
                                                    }
                                                } else {
                                                    obj2 = null;
                                                    if (obj2 == null) {
                                                        intent2 = new Intent(notificationAction22.getAction());
                                                        intent2.setComponent(this.zzls);
                                                        visibility.addAction(new Action.Builder(notificationAction22.getIconResId(), notificationAction22.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, 0)).build());
                                                    } else {
                                                        zza(visibility, notificationAction22.getAction());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                zzbe.e(String.valueOf(NotificationActionsProvider.class.getSimpleName()).concat(" doesn't provide any actions for compact view."), new Object[0]);
                                obj = null;
                                if (obj == null) {
                                    iArr = (int[]) compactViewActionIndices.clone();
                                    for (NotificationAction notificationAction222 : notificationActions) {
                                        new NotificationAction.Builder().setAction(notificationAction222.getAction());
                                        action = notificationAction222.getAction();
                                        if (action.equals(MediaIntentReceiver.ACTION_STOP_CASTING)) {
                                            obj2 = null;
                                            if (obj2 == null) {
                                                zza(visibility, notificationAction222.getAction());
                                            } else {
                                                intent2 = new Intent(notificationAction222.getAction());
                                                intent2.setComponent(this.zzls);
                                                visibility.addAction(new Action.Builder(notificationAction222.getIconResId(), notificationAction222.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, 0)).build());
                                            }
                                        } else {
                                            obj2 = 1;
                                            if (obj2 == null) {
                                                intent2 = new Intent(notificationAction222.getAction());
                                                intent2.setComponent(this.zzls);
                                                visibility.addAction(new Action.Builder(notificationAction222.getIconResId(), notificationAction222.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, 0)).build());
                                            } else {
                                                zza(visibility, notificationAction222.getAction());
                                            }
                                        }
                                    }
                                }
                            }
                            return;
                        }
                    }
                    zzbe.e(String.valueOf(NotificationActionsProvider.class.getSimpleName()).concat(" doesn't provide any action."), new Object[0]);
                    obj = null;
                    if (obj != null) {
                        size = notificationActions.size();
                        if (compactViewActionIndices != null) {
                            if (compactViewActionIndices.length == 0) {
                                length = compactViewActionIndices.length;
                                i = 0;
                                while (i < length) {
                                    i2 = compactViewActionIndices[i];
                                    if (i2 >= 0) {
                                        if (i2 >= size) {
                                            i++;
                                        }
                                    }
                                    zzbe.e(String.valueOf(NotificationActionsProvider.class.getSimpleName()).concat("provides a compact view action whose index is out of bounds."), new Object[0]);
                                    obj = null;
                                    break;
                                }
                                obj = 1;
                                if (obj == null) {
                                    iArr = (int[]) compactViewActionIndices.clone();
                                    for (NotificationAction notificationAction2222 : notificationActions) {
                                        new NotificationAction.Builder().setAction(notificationAction2222.getAction());
                                        action = notificationAction2222.getAction();
                                        if (action.equals(MediaIntentReceiver.ACTION_STOP_CASTING)) {
                                            obj2 = 1;
                                            if (obj2 == null) {
                                                zza(visibility, notificationAction2222.getAction());
                                            } else {
                                                intent2 = new Intent(notificationAction2222.getAction());
                                                intent2.setComponent(this.zzls);
                                                visibility.addAction(new Action.Builder(notificationAction2222.getIconResId(), notificationAction2222.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, 0)).build());
                                            }
                                        } else {
                                            obj2 = null;
                                            if (obj2 == null) {
                                                intent2 = new Intent(notificationAction2222.getAction());
                                                intent2.setComponent(this.zzls);
                                                visibility.addAction(new Action.Builder(notificationAction2222.getIconResId(), notificationAction2222.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, 0)).build());
                                            } else {
                                                zza(visibility, notificationAction2222.getAction());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        zzbe.e(String.valueOf(NotificationActionsProvider.class.getSimpleName()).concat(" doesn't provide any actions for compact view."), new Object[0]);
                        obj = null;
                        if (obj == null) {
                            iArr = (int[]) compactViewActionIndices.clone();
                            for (NotificationAction notificationAction22222 : notificationActions) {
                                new NotificationAction.Builder().setAction(notificationAction22222.getAction());
                                action = notificationAction22222.getAction();
                                if (action.equals(MediaIntentReceiver.ACTION_STOP_CASTING)) {
                                    obj2 = null;
                                    if (obj2 == null) {
                                        zza(visibility, notificationAction22222.getAction());
                                    } else {
                                        intent2 = new Intent(notificationAction22222.getAction());
                                        intent2.setComponent(this.zzls);
                                        visibility.addAction(new Action.Builder(notificationAction22222.getIconResId(), notificationAction22222.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, 0)).build());
                                    }
                                } else {
                                    obj2 = 1;
                                    if (obj2 == null) {
                                        intent2 = new Intent(notificationAction22222.getAction());
                                        intent2.setComponent(this.zzls);
                                        visibility.addAction(new Action.Builder(notificationAction22222.getIconResId(), notificationAction22222.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, 0)).build());
                                    } else {
                                        zza(visibility, notificationAction22222.getAction());
                                    }
                                }
                            }
                        }
                    }
                    return;
                } catch (Throwable e) {
                    zzbe.zzc(e, "Unable to call %s on %s.", "getNotificationActions", zzd.class.getSimpleName());
                    return;
                }
            }
            for (String zza : this.zzlu) {
                zza(visibility, zza);
            }
            iArr = this.zzlv;
            if (VERSION.SDK_INT >= 21) {
                visibility.setStyle(new MediaStyle().setShowActionsInCompactView(iArr).setMediaSession(this.zzmc.zzmi));
            }
            this.zzbu = visibility.build();
            if (this.zzme.isAppVisible()) {
                stopForeground(true);
            } else {
                startForeground(1, this.zzbu);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zza(androidx.core.app.NotificationCompat.Builder r11, java.lang.String r12) {
        /*
        r10 = this;
        r0 = r12.hashCode();
        r1 = 2;
        r2 = 1;
        r3 = 0;
        switch(r0) {
            case -1699820260: goto L_0x0047;
            case -945151566: goto L_0x003d;
            case -945080078: goto L_0x0033;
            case -668151673: goto L_0x0029;
            case -124479363: goto L_0x001f;
            case 235550565: goto L_0x0015;
            case 1362116196: goto L_0x000b;
            default: goto L_0x000a;
        };
    L_0x000a:
        goto L_0x0051;
    L_0x000b:
        r0 = "com.google.android.gms.cast.framework.action.FORWARD";
        r0 = r12.equals(r0);
        if (r0 == 0) goto L_0x0051;
    L_0x0013:
        r0 = 3;
        goto L_0x0052;
    L_0x0015:
        r0 = "com.google.android.gms.cast.framework.action.TOGGLE_PLAYBACK";
        r0 = r12.equals(r0);
        if (r0 == 0) goto L_0x0051;
    L_0x001d:
        r0 = 0;
        goto L_0x0052;
    L_0x001f:
        r0 = "com.google.android.gms.cast.framework.action.DISCONNECT";
        r0 = r12.equals(r0);
        if (r0 == 0) goto L_0x0051;
    L_0x0027:
        r0 = 6;
        goto L_0x0052;
    L_0x0029:
        r0 = "com.google.android.gms.cast.framework.action.STOP_CASTING";
        r0 = r12.equals(r0);
        if (r0 == 0) goto L_0x0051;
    L_0x0031:
        r0 = 5;
        goto L_0x0052;
    L_0x0033:
        r0 = "com.google.android.gms.cast.framework.action.SKIP_PREV";
        r0 = r12.equals(r0);
        if (r0 == 0) goto L_0x0051;
    L_0x003b:
        r0 = 2;
        goto L_0x0052;
    L_0x003d:
        r0 = "com.google.android.gms.cast.framework.action.SKIP_NEXT";
        r0 = r12.equals(r0);
        if (r0 == 0) goto L_0x0051;
    L_0x0045:
        r0 = 1;
        goto L_0x0052;
    L_0x0047:
        r0 = "com.google.android.gms.cast.framework.action.REWIND";
        r0 = r12.equals(r0);
        if (r0 == 0) goto L_0x0051;
    L_0x004f:
        r0 = 4;
        goto L_0x0052;
    L_0x0051:
        r0 = -1;
    L_0x0052:
        r4 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r6 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r8 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
        r9 = 0;
        switch(r0) {
            case 0: goto L_0x01af;
            case 1: goto L_0x017a;
            case 2: goto L_0x0145;
            case 3: goto L_0x00ee;
            case 4: goto L_0x0097;
            case 5: goto L_0x0068;
            case 6: goto L_0x0068;
            default: goto L_0x005c;
        };
    L_0x005c:
        r11 = zzbe;
        r0 = "Action: %s is not a pre-defined action.";
        r1 = new java.lang.Object[r2];
        r1[r3] = r12;
        r11.e(r0, r1);
        return;
    L_0x0068:
        r12 = new android.content.Intent;
        r0 = "com.google.android.gms.cast.framework.action.STOP_CASTING";
        r12.<init>(r0);
        r0 = r10.zzls;
        r12.setComponent(r0);
        r12 = android.app.PendingIntent.getBroadcast(r10, r3, r12, r3);
        r0 = new androidx.core.app.NotificationCompat$Action$Builder;
        r1 = r10.zzll;
        r1 = r1.getDisconnectDrawableResId();
        r2 = r10.zzma;
        r3 = r10.zzll;
        r3 = r3.zzbx();
        r2 = r2.getString(r3);
        r0.<init>(r1, r2, r12);
        r12 = r0.build();
        r11.addAction(r12);
        return;
    L_0x0097:
        r0 = r10.zzlx;
        r12 = new android.content.Intent;
        r2 = "com.google.android.gms.cast.framework.action.REWIND";
        r12.<init>(r2);
        r2 = r10.zzls;
        r12.setComponent(r2);
        r2 = "googlecast-extra_skip_step_ms";
        r12.putExtra(r2, r0);
        r12 = android.app.PendingIntent.getBroadcast(r10, r3, r12, r8);
        r2 = r10.zzll;
        r2 = r2.getRewindDrawableResId();
        r3 = r10.zzll;
        r3 = r3.zzbu();
        r8 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1));
        if (r8 != 0) goto L_0x00cb;
    L_0x00be:
        r0 = r10.zzll;
        r2 = r0.getRewind10DrawableResId();
        r0 = r10.zzll;
        r3 = r0.zzbv();
        goto L_0x00db;
    L_0x00cb:
        r6 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
        if (r6 != 0) goto L_0x00db;
    L_0x00cf:
        r0 = r10.zzll;
        r2 = r0.getRewind30DrawableResId();
        r0 = r10.zzll;
        r3 = r0.zzbw();
    L_0x00db:
        r0 = new androidx.core.app.NotificationCompat$Action$Builder;
        r1 = r10.zzma;
        r1 = r1.getString(r3);
        r0.<init>(r2, r1, r12);
        r12 = r0.build();
        r11.addAction(r12);
        return;
    L_0x00ee:
        r0 = r10.zzlx;
        r12 = new android.content.Intent;
        r2 = "com.google.android.gms.cast.framework.action.FORWARD";
        r12.<init>(r2);
        r2 = r10.zzls;
        r12.setComponent(r2);
        r2 = "googlecast-extra_skip_step_ms";
        r12.putExtra(r2, r0);
        r12 = android.app.PendingIntent.getBroadcast(r10, r3, r12, r8);
        r2 = r10.zzll;
        r2 = r2.getForwardDrawableResId();
        r3 = r10.zzll;
        r3 = r3.zzbr();
        r8 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1));
        if (r8 != 0) goto L_0x0122;
    L_0x0115:
        r0 = r10.zzll;
        r2 = r0.getForward10DrawableResId();
        r0 = r10.zzll;
        r3 = r0.zzbs();
        goto L_0x0132;
    L_0x0122:
        r6 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
        if (r6 != 0) goto L_0x0132;
    L_0x0126:
        r0 = r10.zzll;
        r2 = r0.getForward30DrawableResId();
        r0 = r10.zzll;
        r3 = r0.zzbt();
    L_0x0132:
        r0 = new androidx.core.app.NotificationCompat$Action$Builder;
        r1 = r10.zzma;
        r1 = r1.getString(r3);
        r0.<init>(r2, r1, r12);
        r12 = r0.build();
        r11.addAction(r12);
        return;
    L_0x0145:
        r12 = r10.zzmc;
        r12 = r12.zzmm;
        if (r12 == 0) goto L_0x015b;
    L_0x014b:
        r12 = new android.content.Intent;
        r0 = "com.google.android.gms.cast.framework.action.SKIP_PREV";
        r12.<init>(r0);
        r0 = r10.zzls;
        r12.setComponent(r0);
        r9 = android.app.PendingIntent.getBroadcast(r10, r3, r12, r3);
    L_0x015b:
        r12 = new androidx.core.app.NotificationCompat$Action$Builder;
        r0 = r10.zzll;
        r0 = r0.getSkipPrevDrawableResId();
        r1 = r10.zzma;
        r2 = r10.zzll;
        r2 = r2.zzbq();
        r1 = r1.getString(r2);
        r12.<init>(r0, r1, r9);
        r12 = r12.build();
        r11.addAction(r12);
        return;
    L_0x017a:
        r12 = r10.zzmc;
        r12 = r12.zzml;
        if (r12 == 0) goto L_0x0190;
    L_0x0180:
        r12 = new android.content.Intent;
        r0 = "com.google.android.gms.cast.framework.action.SKIP_NEXT";
        r12.<init>(r0);
        r0 = r10.zzls;
        r12.setComponent(r0);
        r9 = android.app.PendingIntent.getBroadcast(r10, r3, r12, r3);
    L_0x0190:
        r12 = new androidx.core.app.NotificationCompat$Action$Builder;
        r0 = r10.zzll;
        r0 = r0.getSkipNextDrawableResId();
        r1 = r10.zzma;
        r2 = r10.zzll;
        r2 = r2.zzbp();
        r1 = r1.getString(r2);
        r12.<init>(r0, r1, r9);
        r12 = r12.build();
        r11.addAction(r12);
        return;
    L_0x01af:
        r12 = r10.zzmc;
        r12 = r12.streamType;
        r0 = r10.zzmc;
        r0 = r0.zzmj;
        if (r12 != r1) goto L_0x01c6;
    L_0x01b9:
        r12 = r10.zzll;
        r12 = r12.getStopLiveStreamDrawableResId();
        r1 = r10.zzll;
        r1 = r1.getStopLiveStreamTitleResId();
        goto L_0x01d2;
    L_0x01c6:
        r12 = r10.zzll;
        r12 = r12.getPauseDrawableResId();
        r1 = r10.zzll;
        r1 = r1.zzbn();
    L_0x01d2:
        if (r0 == 0) goto L_0x01d5;
    L_0x01d4:
        goto L_0x01db;
    L_0x01d5:
        r12 = r10.zzll;
        r12 = r12.getPlayDrawableResId();
    L_0x01db:
        if (r0 == 0) goto L_0x01de;
    L_0x01dd:
        goto L_0x01e4;
    L_0x01de:
        r0 = r10.zzll;
        r1 = r0.zzbo();
    L_0x01e4:
        r0 = new android.content.Intent;
        r2 = "com.google.android.gms.cast.framework.action.TOGGLE_PLAYBACK";
        r0.<init>(r2);
        r2 = r10.zzls;
        r0.setComponent(r2);
        r0 = android.app.PendingIntent.getBroadcast(r10, r3, r0, r3);
        r2 = new androidx.core.app.NotificationCompat$Action$Builder;
        r3 = r10.zzma;
        r1 = r3.getString(r1);
        r2.<init>(r12, r1, r0);
        r12 = r2.build();
        r11.addAction(r12);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.MediaNotificationService.zza(androidx.core.app.NotificationCompat$Builder, java.lang.String):void");
    }
}
