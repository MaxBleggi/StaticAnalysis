package com.google.android.gms.measurement.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

final class zzfw extends SSLSocket {
    private final SSLSocket zzavg;

    zzfw(zzfv com_google_android_gms_measurement_internal_zzfv, SSLSocket sSLSocket) {
        this.zzavg = sSLSocket;
    }

    public final void setEnabledProtocols(String[] strArr) {
        if (strArr != null && Arrays.asList(strArr).contains("SSLv3")) {
            strArr = new ArrayList(Arrays.asList(this.zzavg.getEnabledProtocols()));
            if (strArr.size() > 1) {
                strArr.remove("SSLv3");
            }
            strArr = (String[]) strArr.toArray(new String[strArr.size()]);
        }
        this.zzavg.setEnabledProtocols(strArr);
    }

    public final String[] getSupportedCipherSuites() {
        return this.zzavg.getSupportedCipherSuites();
    }

    public final String[] getEnabledCipherSuites() {
        return this.zzavg.getEnabledCipherSuites();
    }

    public final void setEnabledCipherSuites(String[] strArr) {
        this.zzavg.setEnabledCipherSuites(strArr);
    }

    public final String[] getSupportedProtocols() {
        return this.zzavg.getSupportedProtocols();
    }

    public final String[] getEnabledProtocols() {
        return this.zzavg.getEnabledProtocols();
    }

    public final SSLSession getSession() {
        return this.zzavg.getSession();
    }

    public final void addHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        this.zzavg.addHandshakeCompletedListener(handshakeCompletedListener);
    }

    public final void removeHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        this.zzavg.removeHandshakeCompletedListener(handshakeCompletedListener);
    }

    public final void startHandshake() throws IOException {
        this.zzavg.startHandshake();
    }

    public final void setUseClientMode(boolean z) {
        this.zzavg.setUseClientMode(z);
    }

    public final boolean getUseClientMode() {
        return this.zzavg.getUseClientMode();
    }

    public final void setNeedClientAuth(boolean z) {
        this.zzavg.setNeedClientAuth(z);
    }

    public final void setWantClientAuth(boolean z) {
        this.zzavg.setWantClientAuth(z);
    }

    public final boolean getNeedClientAuth() {
        return this.zzavg.getNeedClientAuth();
    }

    public final boolean getWantClientAuth() {
        return this.zzavg.getWantClientAuth();
    }

    public final void setEnableSessionCreation(boolean z) {
        this.zzavg.setEnableSessionCreation(z);
    }

    public final boolean getEnableSessionCreation() {
        return this.zzavg.getEnableSessionCreation();
    }

    public final void bind(SocketAddress socketAddress) throws IOException {
        this.zzavg.bind(socketAddress);
    }

    public final synchronized void close() throws IOException {
        this.zzavg.close();
    }

    public final void connect(SocketAddress socketAddress) throws IOException {
        this.zzavg.connect(socketAddress);
    }

    public final void connect(SocketAddress socketAddress, int i) throws IOException {
        this.zzavg.connect(socketAddress, i);
    }

    public final SocketChannel getChannel() {
        return this.zzavg.getChannel();
    }

    public final InetAddress getInetAddress() {
        return this.zzavg.getInetAddress();
    }

    public final InputStream getInputStream() throws IOException {
        return this.zzavg.getInputStream();
    }

    public final boolean getKeepAlive() throws SocketException {
        return this.zzavg.getKeepAlive();
    }

    public final InetAddress getLocalAddress() {
        return this.zzavg.getLocalAddress();
    }

    public final int getLocalPort() {
        return this.zzavg.getLocalPort();
    }

    public final SocketAddress getLocalSocketAddress() {
        return this.zzavg.getLocalSocketAddress();
    }

    public final boolean getOOBInline() throws SocketException {
        return this.zzavg.getOOBInline();
    }

    public final OutputStream getOutputStream() throws IOException {
        return this.zzavg.getOutputStream();
    }

    public final int getPort() {
        return this.zzavg.getPort();
    }

    public final synchronized int getReceiveBufferSize() throws SocketException {
        return this.zzavg.getReceiveBufferSize();
    }

    public final SocketAddress getRemoteSocketAddress() {
        return this.zzavg.getRemoteSocketAddress();
    }

    public final boolean getReuseAddress() throws SocketException {
        return this.zzavg.getReuseAddress();
    }

    public final synchronized int getSendBufferSize() throws SocketException {
        return this.zzavg.getSendBufferSize();
    }

    public final int getSoLinger() throws SocketException {
        return this.zzavg.getSoLinger();
    }

    public final synchronized int getSoTimeout() throws SocketException {
        return this.zzavg.getSoTimeout();
    }

    public final boolean getTcpNoDelay() throws SocketException {
        return this.zzavg.getTcpNoDelay();
    }

    public final int getTrafficClass() throws SocketException {
        return this.zzavg.getTrafficClass();
    }

    public final boolean isBound() {
        return this.zzavg.isBound();
    }

    public final boolean isClosed() {
        return this.zzavg.isClosed();
    }

    public final boolean isConnected() {
        return this.zzavg.isConnected();
    }

    public final boolean isInputShutdown() {
        return this.zzavg.isInputShutdown();
    }

    public final boolean isOutputShutdown() {
        return this.zzavg.isOutputShutdown();
    }

    public final void sendUrgentData(int i) throws IOException {
        this.zzavg.sendUrgentData(i);
    }

    public final void setKeepAlive(boolean z) throws SocketException {
        this.zzavg.setKeepAlive(z);
    }

    public final void setOOBInline(boolean z) throws SocketException {
        this.zzavg.setOOBInline(z);
    }

    public final void setPerformancePreferences(int i, int i2, int i3) {
        this.zzavg.setPerformancePreferences(i, i2, i3);
    }

    public final synchronized void setReceiveBufferSize(int i) throws SocketException {
        this.zzavg.setReceiveBufferSize(i);
    }

    public final void setReuseAddress(boolean z) throws SocketException {
        this.zzavg.setReuseAddress(z);
    }

    public final synchronized void setSendBufferSize(int i) throws SocketException {
        this.zzavg.setSendBufferSize(i);
    }

    public final void setSoLinger(boolean z, int i) throws SocketException {
        this.zzavg.setSoLinger(z, i);
    }

    public final synchronized void setSoTimeout(int i) throws SocketException {
        this.zzavg.setSoTimeout(i);
    }

    public final void setTcpNoDelay(boolean z) throws SocketException {
        this.zzavg.setTcpNoDelay(z);
    }

    public final void setTrafficClass(int i) throws SocketException {
        this.zzavg.setTrafficClass(i);
    }

    public final void shutdownInput() throws IOException {
        this.zzavg.shutdownInput();
    }

    public final void shutdownOutput() throws IOException {
        this.zzavg.shutdownOutput();
    }

    public final String toString() {
        return this.zzavg.toString();
    }

    public final boolean equals(Object obj) {
        return this.zzavg.equals(obj);
    }
}
