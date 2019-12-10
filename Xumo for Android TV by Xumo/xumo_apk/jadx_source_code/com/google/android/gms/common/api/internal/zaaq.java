package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.api.Api.Client;
import java.util.ArrayList;

final class zaaq extends zaau {
    private final /* synthetic */ zaak zagi;
    private final ArrayList<Client> zago;

    public zaaq(zaak com_google_android_gms_common_api_internal_zaak, ArrayList<Client> arrayList) {
        this.zagi = com_google_android_gms_common_api_internal_zaak;
        super(com_google_android_gms_common_api_internal_zaak);
        this.zago = arrayList;
    }

    @WorkerThread
    public final void zaan() {
        this.zagi.zafs.zaed.zagz = this.zagi.zaat();
        ArrayList arrayList = this.zago;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Client) obj).getRemoteService(this.zagi.zage, this.zagi.zafs.zaed.zagz);
        }
    }
}
