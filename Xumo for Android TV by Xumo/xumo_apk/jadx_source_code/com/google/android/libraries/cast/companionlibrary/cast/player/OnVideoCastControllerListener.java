package com.google.android.libraries.cast.companionlibrary.cast.player;

import android.view.View;
import android.widget.SeekBar;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.CastException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.tracks.OnTracksSelectedListener;

public interface OnVideoCastControllerListener extends OnTracksSelectedListener {
    void onConfigurationChanged();

    void onPlayPauseClicked(View view) throws CastException, TransientNetworkDisconnectionException, NoConnectionException;

    void onProgressChanged(SeekBar seekBar, int i, boolean z);

    void onSkipNextClicked(View view) throws TransientNetworkDisconnectionException, NoConnectionException;

    void onSkipPreviousClicked(View view) throws TransientNetworkDisconnectionException, NoConnectionException;

    void onStartTrackingTouch(SeekBar seekBar);

    void onStopTrackingTouch(SeekBar seekBar);
}
