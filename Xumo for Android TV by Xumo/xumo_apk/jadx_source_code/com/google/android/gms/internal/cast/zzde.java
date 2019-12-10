package com.google.android.gms.internal.cast;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.cast.zzbt;
import com.google.android.gms.common.api.internal.IStatusCallback;
import java.util.List;

public interface zzde extends IInterface {
    void zza(IStatusCallback iStatusCallback, String[] strArr, String str, List<zzbt> list) throws RemoteException;
}
