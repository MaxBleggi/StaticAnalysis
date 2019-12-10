package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
public final class hk implements gq {
    private final gy a;

    public hk(gy gyVar) {
        this.a = gyVar;
    }

    public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
        gs gsVar = (gs) hwVar.a().getAnnotation(gs.class);
        if (gsVar == null) {
            return null;
        }
        return a(this.a, fzVar, hwVar, gsVar);
    }

    gp<?> a(gy gyVar, fz fzVar, hw<?> hwVar, gs gsVar) {
        gyVar = gyVar.a(hw.b(gsVar.a())).a();
        if ((gyVar instanceof gp) != null) {
            gyVar = (gp) gyVar;
        } else if ((gyVar instanceof gq) != null) {
            gyVar = ((gq) gyVar).a(fzVar, hwVar);
        } else {
            gsVar = gyVar instanceof gm;
            if (gsVar == null) {
                if (!(gyVar instanceof ge)) {
                    throw new IllegalArgumentException("@JsonAdapter value must be TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer reference.");
                }
            }
            ge geVar = null;
            gm gmVar = gsVar != null ? (gm) gyVar : null;
            if ((gyVar instanceof ge) != null) {
                geVar = (ge) gyVar;
            }
            gy hsVar = new hs(gmVar, geVar, fzVar, hwVar, null);
        }
        return gyVar != null ? gyVar.nullSafe() : gyVar;
    }
}
