package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.util.Util;

public final class TrackSelectorResult {
    public final Object info;
    public final int length;
    public final RendererConfiguration[] rendererConfigurations;
    public final TrackSelectionArray selections;

    public TrackSelectorResult(RendererConfiguration[] rendererConfigurationArr, TrackSelection[] trackSelectionArr, Object obj) {
        this.rendererConfigurations = rendererConfigurationArr;
        this.selections = new TrackSelectionArray(trackSelectionArr);
        this.info = obj;
        this.length = rendererConfigurationArr.length;
    }

    public boolean isRendererEnabled(int i) {
        return this.rendererConfigurations[i] != 0;
    }

    public boolean isEquivalent(TrackSelectorResult trackSelectorResult) {
        if (trackSelectorResult != null) {
            if (trackSelectorResult.selections.length == this.selections.length) {
                for (int i = 0; i < this.selections.length; i++) {
                    if (!isEquivalent(trackSelectorResult, i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean isEquivalent(TrackSelectorResult trackSelectorResult, int i) {
        boolean z = false;
        if (trackSelectorResult == null) {
            return false;
        }
        if (Util.areEqual(this.rendererConfigurations[i], trackSelectorResult.rendererConfigurations[i]) && Util.areEqual(this.selections.get(i), trackSelectorResult.selections.get(i)) != null) {
            z = true;
        }
        return z;
    }
}
