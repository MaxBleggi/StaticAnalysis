package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.List;

final class zzvc implements zzxt {
    private int tag;
    private final zzuz zzbvm;
    private int zzbvn;
    private int zzbvo = 0;

    public static zzvc zza(zzuz com_google_android_gms_internal_measurement_zzuz) {
        if (com_google_android_gms_internal_measurement_zzuz.zzbvf != null) {
            return com_google_android_gms_internal_measurement_zzuz.zzbvf;
        }
        return new zzvc(com_google_android_gms_internal_measurement_zzuz);
    }

    private zzvc(zzuz com_google_android_gms_internal_measurement_zzuz) {
        this.zzbvm = (zzuz) zzvz.zza(com_google_android_gms_internal_measurement_zzuz, "input");
        this.zzbvm.zzbvf = this;
    }

    public final int zzvo() throws IOException {
        if (this.zzbvo != 0) {
            this.tag = this.zzbvo;
            this.zzbvo = 0;
        } else {
            this.tag = this.zzbvm.zzuq();
        }
        if (this.tag != 0) {
            if (this.tag != this.zzbvn) {
                return this.tag >>> 3;
            }
        }
        return Integer.MAX_VALUE;
    }

    public final int getTag() {
        return this.tag;
    }

    public final boolean zzvp() throws IOException {
        if (!this.zzbvm.zzvg()) {
            if (this.tag != this.zzbvn) {
                return this.zzbvm.zzap(this.tag);
            }
        }
        return false;
    }

    private final void zzau(int i) throws IOException {
        if ((this.tag & 7) != i) {
            throw zzwe.zzxb();
        }
    }

    public final double readDouble() throws IOException {
        zzau(1);
        return this.zzbvm.readDouble();
    }

    public final float readFloat() throws IOException {
        zzau(5);
        return this.zzbvm.readFloat();
    }

    public final long zzur() throws IOException {
        zzau(0);
        return this.zzbvm.zzur();
    }

    public final long zzus() throws IOException {
        zzau(0);
        return this.zzbvm.zzus();
    }

    public final int zzut() throws IOException {
        zzau(0);
        return this.zzbvm.zzut();
    }

    public final long zzuu() throws IOException {
        zzau(1);
        return this.zzbvm.zzuu();
    }

    public final int zzuv() throws IOException {
        zzau(5);
        return this.zzbvm.zzuv();
    }

    public final boolean zzuw() throws IOException {
        zzau(0);
        return this.zzbvm.zzuw();
    }

    public final String readString() throws IOException {
        zzau(2);
        return this.zzbvm.readString();
    }

    public final String zzux() throws IOException {
        zzau(2);
        return this.zzbvm.zzux();
    }

    public final <T> T zza(zzxu<T> com_google_android_gms_internal_measurement_zzxu_T, zzvk com_google_android_gms_internal_measurement_zzvk) throws IOException {
        zzau(2);
        return zzc(com_google_android_gms_internal_measurement_zzxu_T, com_google_android_gms_internal_measurement_zzvk);
    }

    public final <T> T zzb(zzxu<T> com_google_android_gms_internal_measurement_zzxu_T, zzvk com_google_android_gms_internal_measurement_zzvk) throws IOException {
        zzau(3);
        return zzd(com_google_android_gms_internal_measurement_zzxu_T, com_google_android_gms_internal_measurement_zzvk);
    }

    private final <T> T zzc(zzxu<T> com_google_android_gms_internal_measurement_zzxu_T, zzvk com_google_android_gms_internal_measurement_zzvk) throws IOException {
        int zzuz = this.zzbvm.zzuz();
        if (this.zzbvm.zzbvc < this.zzbvm.zzbvd) {
            zzuz = this.zzbvm.zzar(zzuz);
            T newInstance = com_google_android_gms_internal_measurement_zzxu_T.newInstance();
            zzuz com_google_android_gms_internal_measurement_zzuz = this.zzbvm;
            com_google_android_gms_internal_measurement_zzuz.zzbvc++;
            com_google_android_gms_internal_measurement_zzxu_T.zza(newInstance, this, com_google_android_gms_internal_measurement_zzvk);
            com_google_android_gms_internal_measurement_zzxu_T.zzy(newInstance);
            this.zzbvm.zzao(null);
            com_google_android_gms_internal_measurement_zzxu_T = this.zzbvm;
            com_google_android_gms_internal_measurement_zzxu_T.zzbvc--;
            this.zzbvm.zzas(zzuz);
            return newInstance;
        }
        throw zzwe.zzxc();
    }

    private final <T> T zzd(zzxu<T> com_google_android_gms_internal_measurement_zzxu_T, zzvk com_google_android_gms_internal_measurement_zzvk) throws IOException {
        int i = this.zzbvn;
        T t = ((this.tag >>> 3) << 3) | 4;
        this.zzbvn = t;
        try {
            t = com_google_android_gms_internal_measurement_zzxu_T.newInstance();
            com_google_android_gms_internal_measurement_zzxu_T.zza(t, this, com_google_android_gms_internal_measurement_zzvk);
            com_google_android_gms_internal_measurement_zzxu_T.zzy(t);
            if (this.tag == this.zzbvn) {
                return t;
            }
            throw zzwe.zzxd();
        } finally {
            this.zzbvn = i;
        }
    }

    public final zzun zzuy() throws IOException {
        zzau(2);
        return this.zzbvm.zzuy();
    }

    public final int zzuz() throws IOException {
        zzau(0);
        return this.zzbvm.zzuz();
    }

    public final int zzva() throws IOException {
        zzau(0);
        return this.zzbvm.zzva();
    }

    public final int zzvb() throws IOException {
        zzau(5);
        return this.zzbvm.zzvb();
    }

    public final long zzvc() throws IOException {
        zzau(1);
        return this.zzbvm.zzvc();
    }

    public final int zzvd() throws IOException {
        zzau(0);
        return this.zzbvm.zzvd();
    }

    public final long zzve() throws IOException {
        zzau(0);
        return this.zzbvm.zzve();
    }

    public final void zzh(List<Double> list) throws IOException {
        int zzuz;
        int zzvh;
        if (list instanceof zzvh) {
            zzvh com_google_android_gms_internal_measurement_zzvh = (zzvh) list;
            switch (this.tag & 7) {
                case 1:
                    break;
                case 2:
                    zzuz = this.zzbvm.zzuz();
                    zzav(zzuz);
                    zzvh = this.zzbvm.zzvh() + zzuz;
                    do {
                        com_google_android_gms_internal_measurement_zzvh.zzd(this.zzbvm.readDouble());
                    } while (this.zzbvm.zzvh() < zzvh);
                    return;
                default:
                    throw zzwe.zzxb();
            }
            do {
                com_google_android_gms_internal_measurement_zzvh.zzd(this.zzbvm.readDouble());
                if (!this.zzbvm.zzvg()) {
                    zzuz = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (zzuz == this.tag);
            this.zzbvo = zzuz;
            return;
        }
        switch (this.tag & 7) {
            case 1:
                break;
            case 2:
                zzuz = this.zzbvm.zzuz();
                zzav(zzuz);
                zzvh = this.zzbvm.zzvh() + zzuz;
                do {
                    list.add(Double.valueOf(this.zzbvm.readDouble()));
                } while (this.zzbvm.zzvh() < zzvh);
                return;
            default:
                throw zzwe.zzxb();
        }
        do {
            list.add(Double.valueOf(this.zzbvm.readDouble()));
            if (!this.zzbvm.zzvg()) {
                zzuz = this.zzbvm.zzuq();
            } else {
                return;
            }
        } while (zzuz == this.tag);
        this.zzbvo = zzuz;
    }

    public final void zzi(List<Float> list) throws IOException {
        if (list instanceof zzvu) {
            zzvu com_google_android_gms_internal_measurement_zzvu = (zzvu) list;
            list = this.tag & 7;
            if (list == 2) {
                list = this.zzbvm.zzuz();
                zzaw(list);
                int zzvh = this.zzbvm.zzvh() + list;
                do {
                    com_google_android_gms_internal_measurement_zzvu.zzc(this.zzbvm.readFloat());
                } while (this.zzbvm.zzvh() < zzvh);
                return;
            } else if (list == 5) {
                do {
                    com_google_android_gms_internal_measurement_zzvu.zzc(this.zzbvm.readFloat());
                    if (this.zzbvm.zzvg() == null) {
                        list = this.zzbvm.zzuq();
                    } else {
                        return;
                    }
                } while (list == this.tag);
                this.zzbvo = list;
                return;
            } else {
                throw zzwe.zzxb();
            }
        }
        int i = this.tag & 7;
        if (i == 2) {
            i = this.zzbvm.zzuz();
            zzaw(i);
            int zzvh2 = this.zzbvm.zzvh() + i;
            do {
                list.add(Float.valueOf(this.zzbvm.readFloat()));
            } while (this.zzbvm.zzvh() < zzvh2);
        } else if (i == 5) {
            do {
                list.add(Float.valueOf(this.zzbvm.readFloat()));
                if (!this.zzbvm.zzvg()) {
                    i = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (i == this.tag);
            this.zzbvo = i;
        } else {
            throw zzwe.zzxb();
        }
    }

    public final void zzj(List<Long> list) throws IOException {
        if (list instanceof zzws) {
            zzws com_google_android_gms_internal_measurement_zzws = (zzws) list;
            list = this.tag & 7;
            if (list == null) {
                do {
                    com_google_android_gms_internal_measurement_zzws.zzbj(this.zzbvm.zzur());
                    if (this.zzbvm.zzvg() == null) {
                        list = this.zzbvm.zzuq();
                    } else {
                        return;
                    }
                } while (list == this.tag);
                this.zzbvo = list;
                return;
            } else if (list == 2) {
                int zzvh;
                zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
                do {
                    com_google_android_gms_internal_measurement_zzws.zzbj(this.zzbvm.zzur());
                } while (this.zzbvm.zzvh() < zzvh);
                zzax(zzvh);
                return;
            } else {
                throw zzwe.zzxb();
            }
        }
        int i = this.tag & 7;
        if (i == 0) {
            do {
                list.add(Long.valueOf(this.zzbvm.zzur()));
                if (!this.zzbvm.zzvg()) {
                    i = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (i == this.tag);
            this.zzbvo = i;
        } else if (i == 2) {
            zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
            do {
                list.add(Long.valueOf(this.zzbvm.zzur()));
            } while (this.zzbvm.zzvh() < zzvh);
            zzax(zzvh);
        } else {
            throw zzwe.zzxb();
        }
    }

    public final void zzk(List<Long> list) throws IOException {
        if (list instanceof zzws) {
            zzws com_google_android_gms_internal_measurement_zzws = (zzws) list;
            list = this.tag & 7;
            if (list == null) {
                do {
                    com_google_android_gms_internal_measurement_zzws.zzbj(this.zzbvm.zzus());
                    if (this.zzbvm.zzvg() == null) {
                        list = this.zzbvm.zzuq();
                    } else {
                        return;
                    }
                } while (list == this.tag);
                this.zzbvo = list;
                return;
            } else if (list == 2) {
                int zzvh;
                zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
                do {
                    com_google_android_gms_internal_measurement_zzws.zzbj(this.zzbvm.zzus());
                } while (this.zzbvm.zzvh() < zzvh);
                zzax(zzvh);
                return;
            } else {
                throw zzwe.zzxb();
            }
        }
        int i = this.tag & 7;
        if (i == 0) {
            do {
                list.add(Long.valueOf(this.zzbvm.zzus()));
                if (!this.zzbvm.zzvg()) {
                    i = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (i == this.tag);
            this.zzbvo = i;
        } else if (i == 2) {
            zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
            do {
                list.add(Long.valueOf(this.zzbvm.zzus()));
            } while (this.zzbvm.zzvh() < zzvh);
            zzax(zzvh);
        } else {
            throw zzwe.zzxb();
        }
    }

    public final void zzl(List<Integer> list) throws IOException {
        if (list instanceof zzvy) {
            zzvy com_google_android_gms_internal_measurement_zzvy = (zzvy) list;
            list = this.tag & 7;
            if (list == null) {
                do {
                    com_google_android_gms_internal_measurement_zzvy.zzbn(this.zzbvm.zzut());
                    if (this.zzbvm.zzvg() == null) {
                        list = this.zzbvm.zzuq();
                    } else {
                        return;
                    }
                } while (list == this.tag);
                this.zzbvo = list;
                return;
            } else if (list == 2) {
                int zzvh;
                zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
                do {
                    com_google_android_gms_internal_measurement_zzvy.zzbn(this.zzbvm.zzut());
                } while (this.zzbvm.zzvh() < zzvh);
                zzax(zzvh);
                return;
            } else {
                throw zzwe.zzxb();
            }
        }
        int i = this.tag & 7;
        if (i == 0) {
            do {
                list.add(Integer.valueOf(this.zzbvm.zzut()));
                if (!this.zzbvm.zzvg()) {
                    i = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (i == this.tag);
            this.zzbvo = i;
        } else if (i == 2) {
            zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
            do {
                list.add(Integer.valueOf(this.zzbvm.zzut()));
            } while (this.zzbvm.zzvh() < zzvh);
            zzax(zzvh);
        } else {
            throw zzwe.zzxb();
        }
    }

    public final void zzm(List<Long> list) throws IOException {
        int zzuz;
        int zzvh;
        if (list instanceof zzws) {
            zzws com_google_android_gms_internal_measurement_zzws = (zzws) list;
            switch (this.tag & 7) {
                case 1:
                    break;
                case 2:
                    zzuz = this.zzbvm.zzuz();
                    zzav(zzuz);
                    zzvh = this.zzbvm.zzvh() + zzuz;
                    do {
                        com_google_android_gms_internal_measurement_zzws.zzbj(this.zzbvm.zzuu());
                    } while (this.zzbvm.zzvh() < zzvh);
                    return;
                default:
                    throw zzwe.zzxb();
            }
            do {
                com_google_android_gms_internal_measurement_zzws.zzbj(this.zzbvm.zzuu());
                if (!this.zzbvm.zzvg()) {
                    zzuz = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (zzuz == this.tag);
            this.zzbvo = zzuz;
            return;
        }
        switch (this.tag & 7) {
            case 1:
                break;
            case 2:
                zzuz = this.zzbvm.zzuz();
                zzav(zzuz);
                zzvh = this.zzbvm.zzvh() + zzuz;
                do {
                    list.add(Long.valueOf(this.zzbvm.zzuu()));
                } while (this.zzbvm.zzvh() < zzvh);
                return;
            default:
                throw zzwe.zzxb();
        }
        do {
            list.add(Long.valueOf(this.zzbvm.zzuu()));
            if (!this.zzbvm.zzvg()) {
                zzuz = this.zzbvm.zzuq();
            } else {
                return;
            }
        } while (zzuz == this.tag);
        this.zzbvo = zzuz;
    }

    public final void zzn(List<Integer> list) throws IOException {
        if (list instanceof zzvy) {
            zzvy com_google_android_gms_internal_measurement_zzvy = (zzvy) list;
            list = this.tag & 7;
            if (list == 2) {
                list = this.zzbvm.zzuz();
                zzaw(list);
                int zzvh = this.zzbvm.zzvh() + list;
                do {
                    com_google_android_gms_internal_measurement_zzvy.zzbn(this.zzbvm.zzuv());
                } while (this.zzbvm.zzvh() < zzvh);
                return;
            } else if (list == 5) {
                do {
                    com_google_android_gms_internal_measurement_zzvy.zzbn(this.zzbvm.zzuv());
                    if (this.zzbvm.zzvg() == null) {
                        list = this.zzbvm.zzuq();
                    } else {
                        return;
                    }
                } while (list == this.tag);
                this.zzbvo = list;
                return;
            } else {
                throw zzwe.zzxb();
            }
        }
        int i = this.tag & 7;
        if (i == 2) {
            i = this.zzbvm.zzuz();
            zzaw(i);
            int zzvh2 = this.zzbvm.zzvh() + i;
            do {
                list.add(Integer.valueOf(this.zzbvm.zzuv()));
            } while (this.zzbvm.zzvh() < zzvh2);
        } else if (i == 5) {
            do {
                list.add(Integer.valueOf(this.zzbvm.zzuv()));
                if (!this.zzbvm.zzvg()) {
                    i = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (i == this.tag);
            this.zzbvo = i;
        } else {
            throw zzwe.zzxb();
        }
    }

    public final void zzo(List<Boolean> list) throws IOException {
        if (list instanceof zzul) {
            zzul com_google_android_gms_internal_measurement_zzul = (zzul) list;
            list = this.tag & 7;
            if (list == null) {
                do {
                    com_google_android_gms_internal_measurement_zzul.addBoolean(this.zzbvm.zzuw());
                    if (this.zzbvm.zzvg() == null) {
                        list = this.zzbvm.zzuq();
                    } else {
                        return;
                    }
                } while (list == this.tag);
                this.zzbvo = list;
                return;
            } else if (list == 2) {
                int zzvh;
                zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
                do {
                    com_google_android_gms_internal_measurement_zzul.addBoolean(this.zzbvm.zzuw());
                } while (this.zzbvm.zzvh() < zzvh);
                zzax(zzvh);
                return;
            } else {
                throw zzwe.zzxb();
            }
        }
        int i = this.tag & 7;
        if (i == 0) {
            do {
                list.add(Boolean.valueOf(this.zzbvm.zzuw()));
                if (!this.zzbvm.zzvg()) {
                    i = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (i == this.tag);
            this.zzbvo = i;
        } else if (i == 2) {
            zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
            do {
                list.add(Boolean.valueOf(this.zzbvm.zzuw()));
            } while (this.zzbvm.zzvh() < zzvh);
            zzax(zzvh);
        } else {
            throw zzwe.zzxb();
        }
    }

    public final void readStringList(List<String> list) throws IOException {
        zza((List) list, false);
    }

    public final void zzp(List<String> list) throws IOException {
        zza((List) list, true);
    }

    private final void zza(List<String> list, boolean z) throws IOException {
        if ((this.tag & 7) != 2) {
            throw zzwe.zzxb();
        } else if (!(list instanceof zzwn) || z) {
            int zzuq;
            do {
                list.add(z ? zzux() : readString());
                if (!this.zzbvm.zzvg()) {
                    zzuq = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (zzuq == this.tag);
            this.zzbvo = zzuq;
        } else {
            zzwn com_google_android_gms_internal_measurement_zzwn = (zzwn) list;
            do {
                com_google_android_gms_internal_measurement_zzwn.zzc(zzuy());
                if (this.zzbvm.zzvg() == null) {
                    list = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (list == this.tag);
            this.zzbvo = list;
        }
    }

    public final <T> void zza(List<T> list, zzxu<T> com_google_android_gms_internal_measurement_zzxu_T, zzvk com_google_android_gms_internal_measurement_zzvk) throws IOException {
        if ((this.tag & 7) == 2) {
            int zzuq;
            int i = this.tag;
            do {
                list.add(zzc(com_google_android_gms_internal_measurement_zzxu_T, com_google_android_gms_internal_measurement_zzvk));
                if (!this.zzbvm.zzvg()) {
                    if (this.zzbvo == 0) {
                        zzuq = this.zzbvm.zzuq();
                    }
                }
                return;
            } while (zzuq == i);
            this.zzbvo = zzuq;
            return;
        }
        throw zzwe.zzxb();
    }

    public final <T> void zzb(List<T> list, zzxu<T> com_google_android_gms_internal_measurement_zzxu_T, zzvk com_google_android_gms_internal_measurement_zzvk) throws IOException {
        if ((this.tag & 7) == 3) {
            int zzuq;
            int i = this.tag;
            do {
                list.add(zzd(com_google_android_gms_internal_measurement_zzxu_T, com_google_android_gms_internal_measurement_zzvk));
                if (!this.zzbvm.zzvg()) {
                    if (this.zzbvo == 0) {
                        zzuq = this.zzbvm.zzuq();
                    }
                }
                return;
            } while (zzuq == i);
            this.zzbvo = zzuq;
            return;
        }
        throw zzwe.zzxb();
    }

    public final void zzq(List<zzun> list) throws IOException {
        if ((this.tag & 7) == 2) {
            int zzuq;
            do {
                list.add(zzuy());
                if (!this.zzbvm.zzvg()) {
                    zzuq = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (zzuq == this.tag);
            this.zzbvo = zzuq;
            return;
        }
        throw zzwe.zzxb();
    }

    public final void zzr(List<Integer> list) throws IOException {
        int zzvh;
        if (list instanceof zzvy) {
            zzvy com_google_android_gms_internal_measurement_zzvy = (zzvy) list;
            list = this.tag & 7;
            if (list == null) {
                do {
                    com_google_android_gms_internal_measurement_zzvy.zzbn(this.zzbvm.zzuz());
                    if (this.zzbvm.zzvg() == null) {
                        list = this.zzbvm.zzuq();
                    } else {
                        return;
                    }
                } while (list == this.tag);
                this.zzbvo = list;
                return;
            } else if (list == 2) {
                zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
                do {
                    com_google_android_gms_internal_measurement_zzvy.zzbn(this.zzbvm.zzuz());
                } while (this.zzbvm.zzvh() < zzvh);
                zzax(zzvh);
                return;
            } else {
                throw zzwe.zzxb();
            }
        }
        int i = this.tag & 7;
        if (i == 0) {
            do {
                list.add(Integer.valueOf(this.zzbvm.zzuz()));
                if (!this.zzbvm.zzvg()) {
                    i = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (i == this.tag);
            this.zzbvo = i;
        } else if (i == 2) {
            zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
            do {
                list.add(Integer.valueOf(this.zzbvm.zzuz()));
            } while (this.zzbvm.zzvh() < zzvh);
            zzax(zzvh);
        } else {
            throw zzwe.zzxb();
        }
    }

    public final void zzs(List<Integer> list) throws IOException {
        if (list instanceof zzvy) {
            zzvy com_google_android_gms_internal_measurement_zzvy = (zzvy) list;
            list = this.tag & 7;
            if (list == null) {
                do {
                    com_google_android_gms_internal_measurement_zzvy.zzbn(this.zzbvm.zzva());
                    if (this.zzbvm.zzvg() == null) {
                        list = this.zzbvm.zzuq();
                    } else {
                        return;
                    }
                } while (list == this.tag);
                this.zzbvo = list;
                return;
            } else if (list == 2) {
                int zzvh;
                zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
                do {
                    com_google_android_gms_internal_measurement_zzvy.zzbn(this.zzbvm.zzva());
                } while (this.zzbvm.zzvh() < zzvh);
                zzax(zzvh);
                return;
            } else {
                throw zzwe.zzxb();
            }
        }
        int i = this.tag & 7;
        if (i == 0) {
            do {
                list.add(Integer.valueOf(this.zzbvm.zzva()));
                if (!this.zzbvm.zzvg()) {
                    i = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (i == this.tag);
            this.zzbvo = i;
        } else if (i == 2) {
            zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
            do {
                list.add(Integer.valueOf(this.zzbvm.zzva()));
            } while (this.zzbvm.zzvh() < zzvh);
            zzax(zzvh);
        } else {
            throw zzwe.zzxb();
        }
    }

    public final void zzt(List<Integer> list) throws IOException {
        if (list instanceof zzvy) {
            zzvy com_google_android_gms_internal_measurement_zzvy = (zzvy) list;
            list = this.tag & 7;
            if (list == 2) {
                list = this.zzbvm.zzuz();
                zzaw(list);
                int zzvh = this.zzbvm.zzvh() + list;
                do {
                    com_google_android_gms_internal_measurement_zzvy.zzbn(this.zzbvm.zzvb());
                } while (this.zzbvm.zzvh() < zzvh);
                return;
            } else if (list == 5) {
                do {
                    com_google_android_gms_internal_measurement_zzvy.zzbn(this.zzbvm.zzvb());
                    if (this.zzbvm.zzvg() == null) {
                        list = this.zzbvm.zzuq();
                    } else {
                        return;
                    }
                } while (list == this.tag);
                this.zzbvo = list;
                return;
            } else {
                throw zzwe.zzxb();
            }
        }
        int i = this.tag & 7;
        if (i == 2) {
            i = this.zzbvm.zzuz();
            zzaw(i);
            int zzvh2 = this.zzbvm.zzvh() + i;
            do {
                list.add(Integer.valueOf(this.zzbvm.zzvb()));
            } while (this.zzbvm.zzvh() < zzvh2);
        } else if (i == 5) {
            do {
                list.add(Integer.valueOf(this.zzbvm.zzvb()));
                if (!this.zzbvm.zzvg()) {
                    i = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (i == this.tag);
            this.zzbvo = i;
        } else {
            throw zzwe.zzxb();
        }
    }

    public final void zzu(List<Long> list) throws IOException {
        int zzuz;
        int zzvh;
        if (list instanceof zzws) {
            zzws com_google_android_gms_internal_measurement_zzws = (zzws) list;
            switch (this.tag & 7) {
                case 1:
                    break;
                case 2:
                    zzuz = this.zzbvm.zzuz();
                    zzav(zzuz);
                    zzvh = this.zzbvm.zzvh() + zzuz;
                    do {
                        com_google_android_gms_internal_measurement_zzws.zzbj(this.zzbvm.zzvc());
                    } while (this.zzbvm.zzvh() < zzvh);
                    return;
                default:
                    throw zzwe.zzxb();
            }
            do {
                com_google_android_gms_internal_measurement_zzws.zzbj(this.zzbvm.zzvc());
                if (!this.zzbvm.zzvg()) {
                    zzuz = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (zzuz == this.tag);
            this.zzbvo = zzuz;
            return;
        }
        switch (this.tag & 7) {
            case 1:
                break;
            case 2:
                zzuz = this.zzbvm.zzuz();
                zzav(zzuz);
                zzvh = this.zzbvm.zzvh() + zzuz;
                do {
                    list.add(Long.valueOf(this.zzbvm.zzvc()));
                } while (this.zzbvm.zzvh() < zzvh);
                return;
            default:
                throw zzwe.zzxb();
        }
        do {
            list.add(Long.valueOf(this.zzbvm.zzvc()));
            if (!this.zzbvm.zzvg()) {
                zzuz = this.zzbvm.zzuq();
            } else {
                return;
            }
        } while (zzuz == this.tag);
        this.zzbvo = zzuz;
    }

    public final void zzv(List<Integer> list) throws IOException {
        int zzvh;
        if (list instanceof zzvy) {
            zzvy com_google_android_gms_internal_measurement_zzvy = (zzvy) list;
            list = this.tag & 7;
            if (list == null) {
                do {
                    com_google_android_gms_internal_measurement_zzvy.zzbn(this.zzbvm.zzvd());
                    if (this.zzbvm.zzvg() == null) {
                        list = this.zzbvm.zzuq();
                    } else {
                        return;
                    }
                } while (list == this.tag);
                this.zzbvo = list;
                return;
            } else if (list == 2) {
                zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
                do {
                    com_google_android_gms_internal_measurement_zzvy.zzbn(this.zzbvm.zzvd());
                } while (this.zzbvm.zzvh() < zzvh);
                zzax(zzvh);
                return;
            } else {
                throw zzwe.zzxb();
            }
        }
        int i = this.tag & 7;
        if (i == 0) {
            do {
                list.add(Integer.valueOf(this.zzbvm.zzvd()));
                if (!this.zzbvm.zzvg()) {
                    i = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (i == this.tag);
            this.zzbvo = i;
        } else if (i == 2) {
            zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
            do {
                list.add(Integer.valueOf(this.zzbvm.zzvd()));
            } while (this.zzbvm.zzvh() < zzvh);
            zzax(zzvh);
        } else {
            throw zzwe.zzxb();
        }
    }

    public final void zzw(List<Long> list) throws IOException {
        int zzvh;
        if (list instanceof zzws) {
            zzws com_google_android_gms_internal_measurement_zzws = (zzws) list;
            list = this.tag & 7;
            if (list == null) {
                do {
                    com_google_android_gms_internal_measurement_zzws.zzbj(this.zzbvm.zzve());
                    if (this.zzbvm.zzvg() == null) {
                        list = this.zzbvm.zzuq();
                    } else {
                        return;
                    }
                } while (list == this.tag);
                this.zzbvo = list;
                return;
            } else if (list == 2) {
                zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
                do {
                    com_google_android_gms_internal_measurement_zzws.zzbj(this.zzbvm.zzve());
                } while (this.zzbvm.zzvh() < zzvh);
                zzax(zzvh);
                return;
            } else {
                throw zzwe.zzxb();
            }
        }
        int i = this.tag & 7;
        if (i == 0) {
            do {
                list.add(Long.valueOf(this.zzbvm.zzve()));
                if (!this.zzbvm.zzvg()) {
                    i = this.zzbvm.zzuq();
                } else {
                    return;
                }
            } while (i == this.tag);
            this.zzbvo = i;
        } else if (i == 2) {
            zzvh = this.zzbvm.zzvh() + this.zzbvm.zzuz();
            do {
                list.add(Long.valueOf(this.zzbvm.zzve()));
            } while (this.zzbvm.zzvh() < zzvh);
            zzax(zzvh);
        } else {
            throw zzwe.zzxb();
        }
    }

    private static void zzav(int i) throws IOException {
        if ((i & 7) != 0) {
            throw zzwe.zzxd();
        }
    }

    public final <K, V> void zza(java.util.Map<K, V> r6, com.google.android.gms.internal.measurement.zzwx<K, V> r7, com.google.android.gms.internal.measurement.zzvk r8) throws java.io.IOException {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r5 = this;
        r0 = 2;
        r5.zzau(r0);
        r0 = r5.zzbvm;
        r0 = r0.zzuz();
        r1 = r5.zzbvm;
        r0 = r1.zzar(r0);
        r1 = r7.zzcbn;
        r2 = r7.zzbsa;
    L_0x0014:
        r3 = r5.zzvo();	 Catch:{ all -> 0x0067 }
        r4 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;	 Catch:{ all -> 0x0067 }
        if (r3 == r4) goto L_0x005e;	 Catch:{ all -> 0x0067 }
    L_0x001d:
        r4 = r5.zzbvm;	 Catch:{ all -> 0x0067 }
        r4 = r4.zzvg();	 Catch:{ all -> 0x0067 }
        if (r4 != 0) goto L_0x005e;
    L_0x0025:
        switch(r3) {
            case 1: goto L_0x003b;
            case 2: goto L_0x002d;
            default: goto L_0x0028;
        };
    L_0x0028:
        r3 = r5.zzvp();	 Catch:{ zzwf -> 0x004f }
        goto L_0x0044;	 Catch:{ zzwf -> 0x004f }
    L_0x002d:
        r3 = r7.zzcbo;	 Catch:{ zzwf -> 0x004f }
        r4 = r7.zzbsa;	 Catch:{ zzwf -> 0x004f }
        r4 = r4.getClass();	 Catch:{ zzwf -> 0x004f }
        r3 = r5.zza(r3, r4, r8);	 Catch:{ zzwf -> 0x004f }
        r2 = r3;	 Catch:{ zzwf -> 0x004f }
        goto L_0x0014;	 Catch:{ zzwf -> 0x004f }
    L_0x003b:
        r3 = r7.zzcbm;	 Catch:{ zzwf -> 0x004f }
        r4 = 0;	 Catch:{ zzwf -> 0x004f }
        r3 = r5.zza(r3, r4, r4);	 Catch:{ zzwf -> 0x004f }
        r1 = r3;	 Catch:{ zzwf -> 0x004f }
        goto L_0x0014;	 Catch:{ zzwf -> 0x004f }
    L_0x0044:
        if (r3 == 0) goto L_0x0047;	 Catch:{ zzwf -> 0x004f }
    L_0x0046:
        goto L_0x0014;	 Catch:{ zzwf -> 0x004f }
    L_0x0047:
        r3 = new com.google.android.gms.internal.measurement.zzwe;	 Catch:{ zzwf -> 0x004f }
        r4 = "Unable to parse map entry.";	 Catch:{ zzwf -> 0x004f }
        r3.<init>(r4);	 Catch:{ zzwf -> 0x004f }
        throw r3;	 Catch:{ zzwf -> 0x004f }
    L_0x004f:
        r3 = r5.zzvp();	 Catch:{ all -> 0x0067 }
        if (r3 == 0) goto L_0x0056;	 Catch:{ all -> 0x0067 }
    L_0x0055:
        goto L_0x0014;	 Catch:{ all -> 0x0067 }
    L_0x0056:
        r6 = new com.google.android.gms.internal.measurement.zzwe;	 Catch:{ all -> 0x0067 }
        r7 = "Unable to parse map entry.";	 Catch:{ all -> 0x0067 }
        r6.<init>(r7);	 Catch:{ all -> 0x0067 }
        throw r6;	 Catch:{ all -> 0x0067 }
    L_0x005e:
        r6.put(r1, r2);	 Catch:{ all -> 0x0067 }
        r6 = r5.zzbvm;
        r6.zzas(r0);
        return;
    L_0x0067:
        r6 = move-exception;
        r7 = r5.zzbvm;
        r7.zzas(r0);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzvc.zza(java.util.Map, com.google.android.gms.internal.measurement.zzwx, com.google.android.gms.internal.measurement.zzvk):void");
    }

    private final Object zza(zzzb com_google_android_gms_internal_measurement_zzzb, Class<?> cls, zzvk com_google_android_gms_internal_measurement_zzvk) throws IOException {
        switch (zzvd.zzbvp[com_google_android_gms_internal_measurement_zzzb.ordinal()]) {
            case 1:
                return Boolean.valueOf(zzuw());
            case 2:
                return zzuy();
            case 3:
                return Double.valueOf(readDouble());
            case 4:
                return Integer.valueOf(zzva());
            case 5:
                return Integer.valueOf(zzuv());
            case 6:
                return Long.valueOf(zzuu());
            case 7:
                return Float.valueOf(readFloat());
            case 8:
                return Integer.valueOf(zzut());
            case 9:
                return Long.valueOf(zzus());
            case 10:
                zzau(2);
                return zzc(zzxq.zzya().zzi(cls), com_google_android_gms_internal_measurement_zzvk);
            case 11:
                return Integer.valueOf(zzvb());
            case 12:
                return Long.valueOf(zzvc());
            case 13:
                return Integer.valueOf(zzvd());
            case 14:
                return Long.valueOf(zzve());
            case 15:
                return zzux();
            case 16:
                return Integer.valueOf(zzuz());
            case 17:
                return Long.valueOf(zzur());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private static void zzaw(int i) throws IOException {
        if ((i & 3) != 0) {
            throw zzwe.zzxd();
        }
    }

    private final void zzax(int i) throws IOException {
        if (this.zzbvm.zzvh() != i) {
            throw zzwe.zzwx();
        }
    }
}
