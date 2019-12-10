package com.google.android.gms.cast.framework.media;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.TaskStackBuilder;
import com.google.android.exoplayer2.C;

final class zzh extends BroadcastReceiver {
    private final /* synthetic */ MediaNotificationService zzmg;

    zzh(MediaNotificationService mediaNotificationService) {
        this.zzmg = mediaNotificationService;
    }

    public final void onReceive(Context context, Intent intent) {
        ComponentName componentName = (ComponentName) intent.getParcelableExtra("targetActivity");
        Intent intent2 = new Intent();
        intent2.setComponent(componentName);
        if (this.zzmg.zzme.zzq()) {
            intent2.setFlags(603979776);
            intent = PendingIntent.getActivity(context, 1, intent2, 134217728);
        } else {
            TaskStackBuilder create = TaskStackBuilder.create(this.zzmg);
            create.addParentStack(componentName);
            create.addNextIntent(intent2);
            intent = create.getPendingIntent(1, 134217728);
        }
        try {
            intent.send(context, 1, new Intent().setFlags(C.ENCODING_PCM_MU_LAW));
        } catch (Context context2) {
            MediaNotificationService.zzbe.zza(context2, "Sending PendingIntent failed", new Object[0]);
        }
    }
}
