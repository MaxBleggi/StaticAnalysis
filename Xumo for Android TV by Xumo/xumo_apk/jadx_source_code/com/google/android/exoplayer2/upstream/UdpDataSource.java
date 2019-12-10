package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

public final class UdpDataSource extends BaseDataSource {
    public static final int DEFAULT_MAX_PACKET_SIZE = 2000;
    public static final int DEFAULT_SOCKET_TIMEOUT_MILLIS = 8000;
    @Nullable
    private InetAddress address;
    @Nullable
    private MulticastSocket multicastSocket;
    private boolean opened;
    private final DatagramPacket packet;
    private final byte[] packetBuffer;
    private int packetRemaining;
    @Nullable
    private DatagramSocket socket;
    @Nullable
    private InetSocketAddress socketAddress;
    private final int socketTimeoutMillis;
    @Nullable
    private Uri uri;

    public static final class UdpDataSourceException extends IOException {
        public UdpDataSourceException(IOException iOException) {
            super(iOException);
        }
    }

    public UdpDataSource() {
        this(2000);
    }

    public UdpDataSource(int i) {
        this(i, 8000);
    }

    public UdpDataSource(int i, int i2) {
        super(true);
        this.socketTimeoutMillis = i2;
        this.packetBuffer = new byte[i];
        this.packet = new DatagramPacket(this.packetBuffer, 0, i);
    }

    @Deprecated
    public UdpDataSource(@Nullable TransferListener transferListener) {
        this(transferListener, 2000);
    }

    @Deprecated
    public UdpDataSource(@Nullable TransferListener transferListener, int i) {
        this(transferListener, i, 8000);
    }

    @Deprecated
    public UdpDataSource(@Nullable TransferListener transferListener, int i, int i2) {
        this(i, i2);
        if (transferListener != null) {
            addTransferListener(transferListener);
        }
    }

    public long open(DataSpec dataSpec) throws UdpDataSourceException {
        this.uri = dataSpec.uri;
        String host = this.uri.getHost();
        int port = this.uri.getPort();
        transferInitializing(dataSpec);
        try {
            this.address = InetAddress.getByName(host);
            this.socketAddress = new InetSocketAddress(this.address, port);
            if (this.address.isMulticastAddress()) {
                this.multicastSocket = new MulticastSocket(this.socketAddress);
                this.multicastSocket.joinGroup(this.address);
                this.socket = this.multicastSocket;
            } else {
                this.socket = new DatagramSocket(this.socketAddress);
            }
            try {
                this.socket.setSoTimeout(this.socketTimeoutMillis);
                this.opened = true;
                transferStarted(dataSpec);
                return -1;
            } catch (DataSpec dataSpec2) {
                throw new UdpDataSourceException(dataSpec2);
            }
        } catch (DataSpec dataSpec22) {
            throw new UdpDataSourceException(dataSpec22);
        }
    }

    public int read(byte[] bArr, int i, int i2) throws UdpDataSourceException {
        if (i2 == 0) {
            return null;
        }
        if (this.packetRemaining == 0) {
            try {
                this.socket.receive(this.packet);
                this.packetRemaining = this.packet.getLength();
                bytesTransferred(this.packetRemaining);
            } catch (byte[] bArr2) {
                throw new UdpDataSourceException(bArr2);
            }
        }
        int length = this.packet.getLength() - this.packetRemaining;
        i2 = Math.min(this.packetRemaining, i2);
        System.arraycopy(this.packetBuffer, length, bArr2, i, i2);
        this.packetRemaining -= i2;
        return i2;
    }

    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    public void close() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r3 = this;
        r0 = 0;
        r3.uri = r0;
        r1 = r3.multicastSocket;
        if (r1 == 0) goto L_0x0010;
    L_0x0007:
        r1 = r3.multicastSocket;	 Catch:{ IOException -> 0x000e }
        r2 = r3.address;	 Catch:{ IOException -> 0x000e }
        r1.leaveGroup(r2);	 Catch:{ IOException -> 0x000e }
    L_0x000e:
        r3.multicastSocket = r0;
    L_0x0010:
        r1 = r3.socket;
        if (r1 == 0) goto L_0x001b;
    L_0x0014:
        r1 = r3.socket;
        r1.close();
        r3.socket = r0;
    L_0x001b:
        r3.address = r0;
        r3.socketAddress = r0;
        r0 = 0;
        r3.packetRemaining = r0;
        r1 = r3.opened;
        if (r1 == 0) goto L_0x002b;
    L_0x0026:
        r3.opened = r0;
        r3.transferEnded();
    L_0x002b:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.UdpDataSource.close():void");
    }
}
