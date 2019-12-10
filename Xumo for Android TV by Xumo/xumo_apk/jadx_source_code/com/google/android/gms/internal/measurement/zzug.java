package com.google.android.gms.internal.measurement;

public abstract class zzug<MessageType extends zzug<MessageType, BuilderType>, BuilderType extends zzuh<MessageType, BuilderType>> implements zzxe {
    private static boolean zzbun = false;
    protected int zzbum = 0;

    public final zzun zzud() {
        try {
            zzuv zzan = zzun.zzan(zzwe());
            zzb(zzan.zzup());
            return zzan.zzuo();
        } catch (Throwable e) {
            String str = "ByteString";
            String name = getClass().getName();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(name).length() + 62) + String.valueOf(str).length());
            stringBuilder.append("Serializing ");
            stringBuilder.append(name);
            stringBuilder.append(" to a ");
            stringBuilder.append(str);
            stringBuilder.append(" threw an IOException (should never happen).");
            throw new RuntimeException(stringBuilder.toString(), e);
        }
    }

    int zzue() {
        throw new UnsupportedOperationException();
    }

    void zzah(int i) {
        throw new UnsupportedOperationException();
    }
}
