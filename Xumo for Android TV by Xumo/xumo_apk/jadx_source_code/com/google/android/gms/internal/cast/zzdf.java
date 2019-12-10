package com.google.android.gms.internal.cast;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.cast.zzbt;
import com.google.android.gms.common.api.internal.IStatusCallback;
import java.util.List;

public final class zzdf extends zza implements zzde {
    zzdf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.cast.internal.ICastService");
    }

    public final void zza(IStatusCallback iStatusCallback, String[] strArr, String str, List<zzbt> list) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iStatusCallback);
        obtainAndWriteInterfaceToken.writeStringArray(strArr);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeTypedList(list);
        transactOneway(2, obtainAndWriteInterfaceToken);
    }
}
