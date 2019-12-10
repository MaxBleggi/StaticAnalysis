package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class zzvx<MessageType extends zzvx<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzug<MessageType, BuilderType> {
    private static Map<Object, zzvx<?, ?>> zzbzj = new ConcurrentHashMap();
    protected zzyn zzbzh = zzyn.zzys();
    private int zzbzi = -1;

    public enum zze {
        public static final int zzbzo = 1;
        public static final int zzbzp = 2;
        public static final int zzbzq = 3;
        public static final int zzbzr = 4;
        public static final int zzbzs = 5;
        public static final int zzbzt = 6;
        public static final int zzbzu = 7;
        private static final /* synthetic */ int[] zzbzv = new int[]{zzbzo, zzbzp, zzbzq, zzbzr, zzbzs, zzbzt, zzbzu};
        public static final int zzbzw = 1;
        public static final int zzbzx = 2;
        private static final /* synthetic */ int[] zzbzy = new int[]{zzbzw, zzbzx};
        public static final int zzbzz = 1;
        public static final int zzcaa = 2;
        private static final /* synthetic */ int[] zzcab = new int[]{zzbzz, zzcaa};

        public static int[] zzww() {
            return (int[]) zzbzv.clone();
        }
    }

    public static class zzd<ContainingType extends zzxe, Type> extends zzvi<ContainingType, Type> {
    }

    public static class zzb<T extends zzvx<T, ?>> extends zzui<T> {
        private final T zzbzk;

        public zzb(T t) {
            this.zzbzk = t;
        }

        public final /* synthetic */ Object zza(zzuz com_google_android_gms_internal_measurement_zzuz, zzvk com_google_android_gms_internal_measurement_zzvk) throws zzwe {
            return zzvx.zza(this.zzbzk, com_google_android_gms_internal_measurement_zzuz, com_google_android_gms_internal_measurement_zzvk);
        }
    }

    public static abstract class zza<MessageType extends zzvx<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzuh<MessageType, BuilderType> {
        private final MessageType zzbzk;
        protected MessageType zzbzl;
        private boolean zzbzm = null;

        protected zza(MessageType messageType) {
            this.zzbzk = messageType;
            this.zzbzl = (zzvx) messageType.zza(zze.zzbzr, null, null);
        }

        protected final void zzwr() {
            if (this.zzbzm) {
                zzvx com_google_android_gms_internal_measurement_zzvx = (zzvx) this.zzbzl.zza(zze.zzbzr, null, null);
                zza(com_google_android_gms_internal_measurement_zzvx, this.zzbzl);
                this.zzbzl = com_google_android_gms_internal_measurement_zzvx;
                this.zzbzm = false;
            }
        }

        public final boolean isInitialized() {
            return zzvx.zza(this.zzbzl, false);
        }

        public MessageType zzws() {
            if (this.zzbzm) {
                return this.zzbzl;
            }
            zzvx com_google_android_gms_internal_measurement_zzvx = this.zzbzl;
            zzxq.zzya().zzak(com_google_android_gms_internal_measurement_zzvx).zzy(com_google_android_gms_internal_measurement_zzvx);
            this.zzbzm = true;
            return this.zzbzl;
        }

        public final MessageType zzwt() {
            zzvx com_google_android_gms_internal_measurement_zzvx = (zzvx) zzwu();
            boolean booleanValue = Boolean.TRUE.booleanValue();
            byte byteValue = ((Byte) com_google_android_gms_internal_measurement_zzvx.zza(zze.zzbzo, null, null)).byteValue();
            boolean z = true;
            if (byteValue != (byte) 1) {
                if (byteValue == (byte) 0) {
                    z = false;
                } else {
                    z = zzxq.zzya().zzak(com_google_android_gms_internal_measurement_zzvx).zzaj(com_google_android_gms_internal_measurement_zzvx);
                    if (booleanValue) {
                        com_google_android_gms_internal_measurement_zzvx.zza(zze.zzbzp, z ? com_google_android_gms_internal_measurement_zzvx : null, null);
                    }
                }
            }
            if (z) {
                return com_google_android_gms_internal_measurement_zzvx;
            }
            throw new zzyl(com_google_android_gms_internal_measurement_zzvx);
        }

        public final BuilderType zza(MessageType messageType) {
            zzwr();
            zza(this.zzbzl, messageType);
            return this;
        }

        private static void zza(MessageType messageType, MessageType messageType2) {
            zzxq.zzya().zzak(messageType).zzd(messageType, messageType2);
        }

        protected final /* synthetic */ zzuh zza(zzug com_google_android_gms_internal_measurement_zzug) {
            return zza((zzvx) com_google_android_gms_internal_measurement_zzug);
        }

        public final /* synthetic */ zzuh zzuf() {
            return (zza) clone();
        }

        public /* synthetic */ zzxe zzwu() {
            return zzws();
        }

        public /* synthetic */ zzxe zzwv() {
            return zzwt();
        }

        public final /* synthetic */ zzxe zzwq() {
            return this.zzbzk;
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza com_google_android_gms_internal_measurement_zzvx_zza = (zza) this.zzbzk.zza(zze.zzbzs, null, null);
            com_google_android_gms_internal_measurement_zzvx_zza.zza((zzvx) zzwu());
            return com_google_android_gms_internal_measurement_zzvx_zza;
        }
    }

    public static abstract class zzc<MessageType extends zzc<MessageType, BuilderType>, BuilderType> extends zzvx<MessageType, BuilderType> implements zzxg {
        protected zzvo<Object> zzbzn = zzvo.zzwd();
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    public String toString() {
        return zzxh.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zzbum != 0) {
            return this.zzbum;
        }
        this.zzbum = zzxq.zzya().zzak(this).hashCode(this);
        return this.zzbum;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (((zzvx) zza(zze.zzbzt, null, null)).getClass().isInstance(obj)) {
            return zzxq.zzya().zzak(this).equals(this, (zzvx) obj);
        }
        return null;
    }

    public final boolean isInitialized() {
        boolean booleanValue = Boolean.TRUE.booleanValue();
        byte byteValue = ((Byte) zza(zze.zzbzo, null, null)).byteValue();
        if (byteValue == (byte) 1) {
            return true;
        }
        if (byteValue == (byte) 0) {
            return false;
        }
        boolean zzaj = zzxq.zzya().zzak(this).zzaj(this);
        if (booleanValue) {
            zza(zze.zzbzp, zzaj ? this : null, null);
        }
        return zzaj;
    }

    public final BuilderType zzwm() {
        zza com_google_android_gms_internal_measurement_zzvx_zza = (zza) zza(zze.zzbzs, null, null);
        com_google_android_gms_internal_measurement_zzvx_zza.zza(this);
        return com_google_android_gms_internal_measurement_zzvx_zza;
    }

    final int zzue() {
        return this.zzbzi;
    }

    final void zzah(int i) {
        this.zzbzi = i;
    }

    public final void zzb(zzve com_google_android_gms_internal_measurement_zzve) throws IOException {
        zzxq.zzya().zzi(getClass()).zza(this, zzvg.zza(com_google_android_gms_internal_measurement_zzve));
    }

    public final int zzwe() {
        if (this.zzbzi == -1) {
            this.zzbzi = zzxq.zzya().zzak(this).zzai(this);
        }
        return this.zzbzi;
    }

    static <T extends zzvx<?, ?>> T zzg(Class<T> cls) {
        T t = (zzvx) zzbzj.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (zzvx) zzbzj.get(cls);
            } catch (Class<T> cls2) {
                throw new IllegalStateException("Class initialization cannot fail.", cls2);
            }
        }
        if (t == null) {
            t = (zzvx) ((zzvx) zzys.zzk(cls2)).zza(zze.zzbzt, null, null);
            if (t != null) {
                zzbzj.put(cls2, t);
            } else {
                throw new IllegalStateException();
            }
        }
        return t;
    }

    protected static <T extends zzvx<?, ?>> void zza(Class<T> cls, T t) {
        zzbzj.put(cls, t);
    }

    protected static Object zza(zzxe com_google_android_gms_internal_measurement_zzxe, String str, Object[] objArr) {
        return new zzxs(com_google_android_gms_internal_measurement_zzxe, str, objArr);
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (Method method2) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", method2);
        } catch (Method method22) {
            method22 = method22.getCause();
            if ((method22 instanceof RuntimeException) != null) {
                throw ((RuntimeException) method22);
            } else if ((method22 instanceof Error) != null) {
                throw ((Error) method22);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", method22);
            }
        }
    }

    protected static final <T extends zzvx<T, ?>> boolean zza(T t, boolean z) {
        z = ((Byte) t.zza(zze.zzbzo, null, null)).byteValue();
        if (z) {
            return true;
        }
        if (z) {
            return zzxq.zzya().zzak(t).zzaj(t);
        }
        return null;
    }

    protected static <E> zzwd<E> zzwn() {
        return zzxr.zzyb();
    }

    static <T extends zzvx<T, ?>> T zza(T t, zzuz com_google_android_gms_internal_measurement_zzuz, zzvk com_google_android_gms_internal_measurement_zzvk) throws zzwe {
        zzvx com_google_android_gms_internal_measurement_zzvx = (zzvx) t.zza(zze.zzbzr, null, null);
        try {
            zzxq.zzya().zzak(com_google_android_gms_internal_measurement_zzvx).zza(com_google_android_gms_internal_measurement_zzvx, zzvc.zza(com_google_android_gms_internal_measurement_zzuz), com_google_android_gms_internal_measurement_zzvk);
            zzxq.zzya().zzak(com_google_android_gms_internal_measurement_zzvx).zzy(com_google_android_gms_internal_measurement_zzvx);
            return com_google_android_gms_internal_measurement_zzvx;
        } catch (zzuz com_google_android_gms_internal_measurement_zzuz2) {
            if ((com_google_android_gms_internal_measurement_zzuz2.getCause() instanceof zzwe) != null) {
                throw ((zzwe) com_google_android_gms_internal_measurement_zzuz2.getCause());
            }
            throw new zzwe(com_google_android_gms_internal_measurement_zzuz2.getMessage()).zzg(com_google_android_gms_internal_measurement_zzvx);
        } catch (T t2) {
            if ((t2.getCause() instanceof zzwe) != null) {
                throw ((zzwe) t2.getCause());
            }
            throw t2;
        }
    }

    public final /* synthetic */ zzxf zzwo() {
        zza com_google_android_gms_internal_measurement_zzvx_zza = (zza) zza(zze.zzbzs, null, null);
        com_google_android_gms_internal_measurement_zzvx_zza.zza(this);
        return com_google_android_gms_internal_measurement_zzvx_zza;
    }

    public final /* synthetic */ zzxf zzwp() {
        return (zza) zza(zze.zzbzs, null, null);
    }

    public final /* synthetic */ zzxe zzwq() {
        return (zzvx) zza(zze.zzbzt, null, null);
    }
}
