package com.google.android.gms.measurement.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

@WorkerThread
final class zzay implements Runnable {
    private final String packageName;
    private final URL url;
    private final byte[] zzanc;
    private final zzaw zzand;
    private final Map<String, String> zzane;
    private final /* synthetic */ zzau zzanf;

    public zzay(zzau com_google_android_gms_measurement_internal_zzau, String str, URL url, byte[] bArr, Map<String, String> map, zzaw com_google_android_gms_measurement_internal_zzaw) {
        this.zzanf = com_google_android_gms_measurement_internal_zzau;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzaw);
        this.url = url;
        this.zzanc = bArr;
        this.zzand = com_google_android_gms_measurement_internal_zzaw;
        this.packageName = str;
        this.zzane = map;
    }

    public final void run() {
        Map map;
        Throwable th;
        Throwable e;
        int i;
        this.zzanf.zzgh();
        OutputStream outputStream = null;
        HttpURLConnection zzb;
        try {
            zzb = this.zzanf.zzb(this.url);
            try {
                if (this.zzane != null) {
                    for (Entry entry : this.zzane.entrySet()) {
                        zzb.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                if (this.zzanc != null) {
                    byte[] zzb2 = this.zzanf.zzjr().zzb(this.zzanc);
                    this.zzanf.zzgt().zzjo().zzg("Uploading data. size", Integer.valueOf(zzb2.length));
                    zzb.setDoOutput(true);
                    zzb.addRequestProperty(HttpRequest.HEADER_CONTENT_ENCODING, HttpRequest.ENCODING_GZIP);
                    zzb.setFixedLengthStreamingMode(zzb2.length);
                    zzb.connect();
                    OutputStream outputStream2 = zzb.getOutputStream();
                    try {
                        outputStream2.write(zzb2);
                        outputStream2.close();
                    } catch (Throwable e2) {
                        map = null;
                        th = e2;
                        outputStream = outputStream2;
                        i = 0;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e3) {
                                this.zzanf.zzgt().zzjg().zze("Error closing HTTP compressed POST connection output stream. appId", zzaq.zzby(this.packageName), e3);
                            }
                        }
                        if (zzb != null) {
                            zzb.disconnect();
                        }
                        this.zzanf.zzgs().zzc(new zzax(this.packageName, this.zzand, i, th, null, map));
                    } catch (Throwable th2) {
                        e2 = th2;
                        map = null;
                        outputStream = outputStream2;
                        i = 0;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e32) {
                                this.zzanf.zzgt().zzjg().zze("Error closing HTTP compressed POST connection output stream. appId", zzaq.zzby(this.packageName), e32);
                            }
                        }
                        if (zzb != null) {
                            zzb.disconnect();
                        }
                        this.zzanf.zzgs().zzc(new zzax(this.packageName, this.zzand, i, null, null, map));
                        throw e2;
                    }
                }
                i = zzb.getResponseCode();
            } catch (IOException e4) {
                e2 = e4;
                map = null;
                th = e2;
                i = 0;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zzb != null) {
                    zzb.disconnect();
                }
                this.zzanf.zzgs().zzc(new zzax(this.packageName, this.zzand, i, th, null, map));
            } catch (Throwable th3) {
                e2 = th3;
                map = null;
                i = 0;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zzb != null) {
                    zzb.disconnect();
                }
                this.zzanf.zzgs().zzc(new zzax(this.packageName, this.zzand, i, null, null, map));
                throw e2;
            }
            try {
                map = zzb.getHeaderFields();
                try {
                    byte[] zza = zzau.zzb(zzb);
                    if (zzb != null) {
                        zzb.disconnect();
                    }
                    this.zzanf.zzgs().zzc(new zzax(this.packageName, this.zzand, i, null, zza, map));
                } catch (IOException e5) {
                    e2 = e5;
                    th = e2;
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (zzb != null) {
                        zzb.disconnect();
                    }
                    this.zzanf.zzgs().zzc(new zzax(this.packageName, this.zzand, i, th, null, map));
                } catch (Throwable th4) {
                    e2 = th4;
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (zzb != null) {
                        zzb.disconnect();
                    }
                    this.zzanf.zzgs().zzc(new zzax(this.packageName, this.zzand, i, null, null, map));
                    throw e2;
                }
            } catch (IOException e6) {
                e2 = e6;
                map = null;
                th = e2;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zzb != null) {
                    zzb.disconnect();
                }
                this.zzanf.zzgs().zzc(new zzax(this.packageName, this.zzand, i, th, null, map));
            } catch (Throwable th5) {
                e2 = th5;
                map = null;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zzb != null) {
                    zzb.disconnect();
                }
                this.zzanf.zzgs().zzc(new zzax(this.packageName, this.zzand, i, null, null, map));
                throw e2;
            }
        } catch (IOException e7) {
            e2 = e7;
            zzb = null;
            map = zzb;
            th = e2;
            i = 0;
            if (outputStream != null) {
                outputStream.close();
            }
            if (zzb != null) {
                zzb.disconnect();
            }
            this.zzanf.zzgs().zzc(new zzax(this.packageName, this.zzand, i, th, null, map));
        } catch (Throwable th6) {
            e2 = th6;
            zzb = null;
            map = zzb;
            i = 0;
            if (outputStream != null) {
                outputStream.close();
            }
            if (zzb != null) {
                zzb.disconnect();
            }
            this.zzanf.zzgs().zzc(new zzax(this.packageName, this.zzand, i, null, null, map));
            throw e2;
        }
    }
}
