package com.google.android.libraries.cast.companionlibrary.cast.tracks.ui;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.libraries.cast.companionlibrary.R;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class TracksChooserDialog extends DialogFragment {
    private static final long TEXT_TRACK_NONE_ID = -1;
    private long[] mActiveTracks = null;
    private List<MediaTrack> mAudioTracks = new ArrayList();
    private TracksListAdapter mAudioVideoAdapter;
    private VideoCastManager mCastManager;
    private MediaInfo mMediaInfo;
    private int mSelectedAudioPosition = -1;
    private int mSelectedTextPosition = 0;
    private TracksListAdapter mTextAdapter;
    private List<MediaTrack> mTextTracks = new ArrayList();
    private List<MediaTrack> mVideoTracks = new ArrayList();

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        bundle = new Builder(getActivity());
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.custom_tracks_dialog_layout, null);
        setUpView(inflate);
        bundle.setView(inflate).setPositiveButton(getString(R.string.ccl_ok), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface = new ArrayList();
                i = TracksChooserDialog.this.mTextAdapter.getSelectedTrack();
                if (i.getId() != -1) {
                    dialogInterface.add(i);
                }
                i = TracksChooserDialog.this.mAudioVideoAdapter.getSelectedTrack();
                if (i != 0) {
                    dialogInterface.add(i);
                }
                if (TracksChooserDialog.this.mVideoTracks.isEmpty() == 0) {
                    Object obj = null;
                    for (MediaTrack mediaTrack : TracksChooserDialog.this.mVideoTracks) {
                        for (long valueOf : TracksChooserDialog.this.mCastManager.getActiveTrackIds()) {
                            if (mediaTrack.getId() == Long.valueOf(valueOf).longValue()) {
                                dialogInterface.add(mediaTrack);
                                obj = 1;
                                continue;
                                break;
                            }
                        }
                        if (obj != null) {
                            break;
                        }
                    }
                }
                TracksChooserDialog.this.mCastManager.notifyTracksSelectedListeners(dialogInterface);
                TracksChooserDialog.this.getDialog().cancel();
            }
        }).setNegativeButton(R.string.ccl_cancel, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                TracksChooserDialog.this.getDialog().cancel();
            }
        }).setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                TracksChooserDialog.this.getDialog().cancel();
            }
        });
        return bundle.create();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
        this.mMediaInfo = Utils.bundleToMediaInfo(getArguments().getBundle(VideoCastManager.EXTRA_MEDIA));
        this.mCastManager = VideoCastManager.getInstance();
        this.mActiveTracks = this.mCastManager.getActiveTrackIds();
        bundle = this.mMediaInfo.getMediaTracks();
        if (bundle == null || bundle.isEmpty() != null) {
            Utils.showToast(getActivity(), R.string.ccl_caption_no_tracks_available);
            dismiss();
        }
    }

    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    private void setUpView(View view) {
        TabSpec newTabSpec;
        ListView listView = (ListView) view.findViewById(R.id.listview1);
        ListView listView2 = (ListView) view.findViewById(R.id.listview2);
        TextView textView = (TextView) view.findViewById(R.id.text_empty_message);
        TextView textView2 = (TextView) view.findViewById(R.id.audio_empty_message);
        partitionTracks();
        this.mTextAdapter = new TracksListAdapter(getActivity(), R.layout.tracks_row_layout, this.mTextTracks, this.mSelectedTextPosition);
        this.mAudioVideoAdapter = new TracksListAdapter(getActivity(), R.layout.tracks_row_layout, this.mAudioTracks, this.mSelectedAudioPosition);
        listView.setAdapter(this.mTextAdapter);
        listView2.setAdapter(this.mAudioVideoAdapter);
        TabHost tabHost = (TabHost) view.findViewById(R.id.tabhost);
        tabHost.setup();
        TabSpec newTabSpec2 = tabHost.newTabSpec("tab1");
        if (this.mTextTracks != null) {
            if (!this.mTextTracks.isEmpty()) {
                textView.setVisibility(4);
                newTabSpec2.setContent(R.id.listview1);
                newTabSpec2.setIndicator(getString(R.string.ccl_caption_subtitles));
                tabHost.addTab(newTabSpec2);
                newTabSpec = tabHost.newTabSpec("tab2");
                if (this.mAudioTracks != null) {
                    if (this.mAudioTracks.isEmpty()) {
                        textView2.setVisibility(4);
                        newTabSpec.setContent(R.id.listview2);
                        newTabSpec.setIndicator(getString(R.string.ccl_caption_audio));
                        tabHost.addTab(newTabSpec);
                    }
                }
                listView2.setVisibility(4);
                newTabSpec.setContent(R.id.audio_empty_message);
                newTabSpec.setIndicator(getString(R.string.ccl_caption_audio));
                tabHost.addTab(newTabSpec);
            }
        }
        listView.setVisibility(4);
        newTabSpec2.setContent(R.id.text_empty_message);
        newTabSpec2.setIndicator(getString(R.string.ccl_caption_subtitles));
        tabHost.addTab(newTabSpec2);
        newTabSpec = tabHost.newTabSpec("tab2");
        if (this.mAudioTracks != null) {
            if (this.mAudioTracks.isEmpty()) {
                textView2.setVisibility(4);
                newTabSpec.setContent(R.id.listview2);
                newTabSpec.setIndicator(getString(R.string.ccl_caption_audio));
                tabHost.addTab(newTabSpec);
            }
        }
        listView2.setVisibility(4);
        newTabSpec.setContent(R.id.audio_empty_message);
        newTabSpec.setIndicator(getString(R.string.ccl_caption_audio));
        tabHost.addTab(newTabSpec);
    }

    private MediaTrack buildNoneTrack() {
        return new MediaTrack.Builder(-1, 1).setName(getString(R.string.ccl_none)).setSubtype(2).setContentId("").build();
    }

    private void partitionTracks() {
        List<MediaTrack> mediaTracks = this.mMediaInfo.getMediaTracks();
        this.mAudioTracks.clear();
        this.mTextTracks.clear();
        this.mVideoTracks.clear();
        this.mTextTracks.add(buildNoneTrack());
        this.mSelectedTextPosition = 0;
        this.mSelectedAudioPosition = -1;
        if (mediaTracks != null) {
            int i = 1;
            int i2 = 0;
            for (MediaTrack mediaTrack : mediaTracks) {
                switch (mediaTrack.getType()) {
                    case 1:
                        this.mTextTracks.add(mediaTrack);
                        if (this.mActiveTracks != null) {
                            for (long j : this.mActiveTracks) {
                                if (j == mediaTrack.getId()) {
                                    this.mSelectedTextPosition = i;
                                }
                            }
                        }
                        i++;
                        break;
                    case 2:
                        this.mAudioTracks.add(mediaTrack);
                        if (this.mActiveTracks != null) {
                            for (long j2 : this.mActiveTracks) {
                                if (j2 == mediaTrack.getId()) {
                                    this.mSelectedAudioPosition = i2;
                                }
                            }
                        }
                        i2++;
                        break;
                    case 3:
                        this.mVideoTracks.add(mediaTrack);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static TracksChooserDialog newInstance(MediaInfo mediaInfo) {
        TracksChooserDialog tracksChooserDialog = new TracksChooserDialog();
        Bundle bundle = new Bundle();
        bundle.putBundle(VideoCastManager.EXTRA_MEDIA, Utils.mediaInfoToBundle(mediaInfo));
        tracksChooserDialog.setArguments(bundle);
        return tracksChooserDialog;
    }
}
