package com.xumo.xumo.service;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$-GYCPM_cnAjiQZptV6lOI8SiyWs implements OnFinish {
    private final /* synthetic */ XumoFcmAlarmService f$0;

    public /* synthetic */ -$$Lambda$-GYCPM_cnAjiQZptV6lOI8SiyWs(XumoFcmAlarmService xumoFcmAlarmService) {
        this.f$0 = xumoFcmAlarmService;
    }

    public final void finished() {
        this.f$0.stopSelf();
    }
}
