package com.google.android.gms.internal.measurement;

public abstract class zzuh<MessageType extends zzug<MessageType, BuilderType>, BuilderType extends zzuh<MessageType, BuilderType>> implements zzxf {
    protected abstract BuilderType zza(MessageType messageType);

    public abstract BuilderType zzuf();

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzuf();
    }

    public final /* synthetic */ zzxf zza(zzxe com_google_android_gms_internal_measurement_zzxe) {
        if (zzwq().getClass().isInstance(com_google_android_gms_internal_measurement_zzxe)) {
            return zza((zzug) com_google_android_gms_internal_measurement_zzxe);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }
}
