package com.google.android.gms.signin.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

public interface zad extends IInterface {
    void zaa(ConnectionResult connectionResult, zaa com_google_android_gms_signin_internal_zaa) throws RemoteException;

    void zaa(Status status, GoogleSignInAccount googleSignInAccount) throws RemoteException;

    void zab(zaj com_google_android_gms_signin_internal_zaj) throws RemoteException;

    void zag(Status status) throws RemoteException;

    void zah(Status status) throws RemoteException;
}
