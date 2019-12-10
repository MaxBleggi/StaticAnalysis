package com.xumo.xumo.service;

import android.content.Context;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$XumoFirebaseMessagingHandler$em-vQsoD5j4BpycyKfCKAndkrrU implements OnCompleteListener {
    private final /* synthetic */ XumoFirebaseMessagingHandler f$0;
    private final /* synthetic */ Context f$1;
    private final /* synthetic */ Map f$2;
    private final /* synthetic */ OnFinish f$3;

    public /* synthetic */ -$$Lambda$XumoFirebaseMessagingHandler$em-vQsoD5j4BpycyKfCKAndkrrU(XumoFirebaseMessagingHandler xumoFirebaseMessagingHandler, Context context, Map map, OnFinish onFinish) {
        this.f$0 = xumoFirebaseMessagingHandler;
        this.f$1 = context;
        this.f$2 = map;
        this.f$3 = onFinish;
    }

    public final void onComplete(Task task) {
        XumoFirebaseMessagingHandler.lambda$handleFirebaseMessaging$0(this.f$0, this.f$1, this.f$2, this.f$3, task);
    }
}
