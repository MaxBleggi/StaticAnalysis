package com.google.android.libraries.cast.companionlibrary.cast.tracks;

import com.google.android.gms.cast.MediaTrack;
import java.util.List;

public interface OnTracksSelectedListener {
    void onTracksSelected(List<MediaTrack> list);
}
