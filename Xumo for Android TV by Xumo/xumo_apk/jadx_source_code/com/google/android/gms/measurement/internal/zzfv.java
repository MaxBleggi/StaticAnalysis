package com.google.android.gms.measurement.internal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

final class zzfv extends SSLSocketFactory {
    private final SSLSocketFactory zzavf;

    zzfv() {
        this(HttpsURLConnection.getDefaultSSLSocketFactory());
    }

    private zzfv(SSLSocketFactory sSLSocketFactory) {
        this.zzavf = sSLSocketFactory;
    }

    public final Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        return zza((SSLSocket) this.zzavf.createSocket(socket, str, i, z));
    }

    public final String[] getDefaultCipherSuites() {
        return this.zzavf.getDefaultCipherSuites();
    }

    public final String[] getSupportedCipherSuites() {
        return this.zzavf.getSupportedCipherSuites();
    }

    public final Socket createSocket(String str, int i) throws IOException {
        return zza((SSLSocket) this.zzavf.createSocket(str, i));
    }

    public final Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return zza((SSLSocket) this.zzavf.createSocket(inetAddress, i));
    }

    public final Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
        return zza((SSLSocket) this.zzavf.createSocket(str, i, inetAddress, i2));
    }

    public final Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        return zza((SSLSocket) this.zzavf.createSocket(inetAddress, i, inetAddress2, i2));
    }

    public final Socket createSocket() throws IOException {
        return zza((SSLSocket) this.zzavf.createSocket());
    }

    private final SSLSocket zza(SSLSocket sSLSocket) {
        return new zzfw(this, sSLSocket);
    }
}
