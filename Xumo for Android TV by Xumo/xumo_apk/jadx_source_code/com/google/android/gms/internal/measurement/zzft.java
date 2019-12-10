package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzvx.zze;

public final class zzft {

    public static final class zza extends zzvx<zza, zza> implements zzxg {
        private static final zza zzavj = new zza();
        private static volatile zzxo<zza> zznw;
        private String zzavh = "";
        private long zzavi;
        private int zznr;

        public static final class zza extends com.google.android.gms.internal.measurement.zzvx.zza<zza, zza> implements zzxg {
            private zza() {
                super(zza.zzavj);
            }

            public final zza zzdc(String str) {
                zzwr();
                ((zza) this.zzbzl).setName(str);
                return this;
            }

            public final zza zzar(long j) {
                zzwr();
                ((zza) this.zzbzl).zzaq(j);
                return this;
            }
        }

        private zza() {
        }

        private final void setName(String str) {
            if (str != null) {
                this.zznr |= 1;
                this.zzavh = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzaq(long j) {
            this.zznr |= 2;
            this.zzavi = j;
        }

        public static zza zzmn() {
            return (zza) ((com.google.android.gms.internal.measurement.zzvx.zza) zzavj.zza(zze.zzbzs, null, null));
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzfu.zznq[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new zza();
                case 3:
                    i = new Object[]{"zznr", "zzavh", "zzavi"};
                    return zzvx.zza((zzxe) zzavj, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\b\u0000\u0002\u0002\u0001", (Object[]) i);
                case 4:
                    return zzavj;
                case 5:
                    i = zznw;
                    if (i == 0) {
                        synchronized (zza.class) {
                            i = zznw;
                            if (i == 0) {
                                i = new com.google.android.gms.internal.measurement.zzvx.zzb(zzavj);
                                zznw = i;
                            }
                        }
                    }
                    return i;
                case 6:
                    return Byte.valueOf((byte) 1);
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzvx.zza(zza.class, zzavj);
        }
    }

    public static final class zzb extends zzvx<zzb, zza> implements zzxg {
        private static final zzb zzavm = new zzb();
        private static volatile zzxo<zzb> zznw;
        private int zzavk = 1;
        private zzwd<zza> zzavl = zzvx.zzwn();
        private int zznr;

        public enum zzb implements zzwa {
            RADS(1),
            PROVISIONING(2);
            
            private static final zzwb<zzb> zzoa = null;
            private final int value;

            public final int zzc() {
                return this.value;
            }

            public static zzb zzs(int i) {
                switch (i) {
                    case 1:
                        return RADS;
                    case 2:
                        return PROVISIONING;
                    default:
                        return 0;
                }
            }

            public static zzwc zzd() {
                return zzfw.zzoc;
            }

            private zzb(int i) {
                this.value = i;
            }

            static {
                zzoa = new zzfv();
            }
        }

        public static final class zza extends com.google.android.gms.internal.measurement.zzvx.zza<zzb, zza> implements zzxg {
            private zza() {
                super(zzb.zzavm);
            }

            public final zza zzb(zza com_google_android_gms_internal_measurement_zzft_zza) {
                zzwr();
                ((zzb) this.zzbzl).zza(com_google_android_gms_internal_measurement_zzft_zza);
                return this;
            }
        }

        private zzb() {
        }

        private final void zza(zza com_google_android_gms_internal_measurement_zzft_zza) {
            if (com_google_android_gms_internal_measurement_zzft_zza != null) {
                if (!this.zzavl.zzug()) {
                    zzwd com_google_android_gms_internal_measurement_zzwd = this.zzavl;
                    int size = com_google_android_gms_internal_measurement_zzwd.size();
                    this.zzavl = com_google_android_gms_internal_measurement_zzwd.zzak(size == 0 ? 10 : size << 1);
                }
                this.zzavl.add(com_google_android_gms_internal_measurement_zzft_zza);
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzmp() {
            return (zza) ((com.google.android.gms.internal.measurement.zzvx.zza) zzavm.zza(zze.zzbzs, null, null));
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzfu.zznq[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza();
                case 3:
                    i = new Object[]{"zznr", "zzavk", zzb.zzd(), "zzavl", zza.class};
                    return zzvx.zza((zzxe) zzavm, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\f\u0000\u0002\u001b", (Object[]) i);
                case 4:
                    return zzavm;
                case 5:
                    i = zznw;
                    if (i == 0) {
                        synchronized (zzb.class) {
                            i = zznw;
                            if (i == 0) {
                                i = new com.google.android.gms.internal.measurement.zzvx.zzb(zzavm);
                                zznw = i;
                            }
                        }
                    }
                    return i;
                case 6:
                    return Byte.valueOf((byte) 1);
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzxo<zzb> zza() {
            return (zzxo) zzavm.zza(zze.zzbzu, null, null);
        }

        static {
            zzvx.zza(zzb.class, zzavm);
        }
    }
}
