package com.google.firebase.iid;

import android.os.Bundle;
import com.google.android.exoplayer2.upstream.DataSchemeDataSource;

final class zzam extends zzak<Bundle> {
    zzam(int i, int i2, Bundle bundle) {
        super(i, 1, bundle);
    }

    final boolean zzab() {
        return false;
    }

    final void zzb(Bundle bundle) {
        bundle = bundle.getBundle(DataSchemeDataSource.SCHEME_DATA);
        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        finish(bundle);
    }
}
