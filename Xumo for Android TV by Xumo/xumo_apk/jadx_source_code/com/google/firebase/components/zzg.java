package com.google.firebase.components;

import java.util.HashSet;
import java.util.Set;

/* compiled from: com.google.firebase:firebase-common@@16.0.2 */
final class zzg {
    private final Component<?> zza;
    private final Set<zzg> zzb = new HashSet();
    private final Set<zzg> zzc = new HashSet();

    zzg(Component<?> component) {
        this.zza = component;
    }

    final void zza(zzg com_google_firebase_components_zzg) {
        this.zzb.add(com_google_firebase_components_zzg);
    }

    final void zzb(zzg com_google_firebase_components_zzg) {
        this.zzc.add(com_google_firebase_components_zzg);
    }

    final Set<zzg> zza() {
        return this.zzb;
    }

    final void zzc(zzg com_google_firebase_components_zzg) {
        this.zzc.remove(com_google_firebase_components_zzg);
    }

    final Component<?> zzb() {
        return this.zza;
    }

    final boolean zzc() {
        return this.zzc.isEmpty();
    }

    final boolean zzd() {
        return this.zzb.isEmpty();
    }
}
