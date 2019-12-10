package com.google.android.gms.cast.framework.media;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.DialogFragment;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.MediaTrack.Builder;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.cast.framework.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TracksChooserDialogFragment extends DialogFragment {
    private long[] zzdp;
    private RemoteMediaClient zzig;
    @VisibleForTesting
    private boolean zzpw;
    @VisibleForTesting
    private List<MediaTrack> zzpx;
    @VisibleForTesting
    private List<MediaTrack> zzpy;
    private Dialog zzpz;
    private MediaInfo zzqa;
    private long[] zzqb;

    private TracksChooserDialogFragment(MediaInfo mediaInfo, long[] jArr) {
        this.zzqa = mediaInfo;
        this.zzqb = jArr;
    }

    @Deprecated
    public static TracksChooserDialogFragment newInstance(MediaInfo mediaInfo, long[] jArr) {
        return new TracksChooserDialogFragment(mediaInfo, jArr);
    }

    @NonNull
    public static TracksChooserDialogFragment newInstance() {
        return new TracksChooserDialogFragment();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzpw = true;
        this.zzpy = new ArrayList();
        this.zzpx = new ArrayList();
        this.zzdp = new long[0];
        Session currentCastSession = CastContext.getSharedInstance(getContext()).getSessionManager().getCurrentCastSession();
        if (currentCastSession != null) {
            if (currentCastSession.isConnected()) {
                this.zzig = currentCastSession.getRemoteMediaClient();
                if (this.zzig != null && this.zzig.hasMediaSession()) {
                    if (this.zzig.getMediaInfo() != null) {
                        if (this.zzqb != null) {
                            this.zzdp = this.zzqb;
                        } else {
                            MediaStatus mediaStatus = this.zzig.getMediaStatus();
                            if (mediaStatus != null) {
                                this.zzdp = mediaStatus.getActiveTrackIds();
                            }
                        }
                        MediaInfo mediaInfo = this.zzqa != null ? this.zzqa : this.zzig.getMediaInfo();
                        if (mediaInfo == null) {
                            this.zzpw = false;
                            return;
                        }
                        List mediaTracks = mediaInfo.getMediaTracks();
                        if (mediaTracks == null) {
                            this.zzpw = false;
                            return;
                        }
                        this.zzpy = zza(mediaTracks, 2);
                        this.zzpx = zza(mediaTracks, 1);
                        if (!this.zzpx.isEmpty()) {
                            this.zzpx.add(0, new Builder(-1, 1).setName(getActivity().getString(R.string.cast_tracks_chooser_dialog_none)).setSubtype(2).setContentId("").build());
                        }
                        return;
                    }
                }
                this.zzpw = false;
                return;
            }
        }
        this.zzpw = false;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        bundle = zza(this.zzpx, this.zzdp, 0);
        int zza = zza(this.zzpy, this.zzdp, -1);
        Object com_google_android_gms_cast_framework_media_zzbb = new zzbb(getActivity(), this.zzpx, bundle);
        bundle = new zzbb(getActivity(), this.zzpy, zza);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.cast_tracks_chooser_dialog_layout, null);
        ListView listView = (ListView) inflate.findViewById(R.id.text_list_view);
        ListView listView2 = (ListView) inflate.findViewById(R.id.audio_list_view);
        TabHost tabHost = (TabHost) inflate.findViewById(R.id.tab_host);
        tabHost.setup();
        if (com_google_android_gms_cast_framework_media_zzbb.getCount() == 0) {
            listView.setVisibility(4);
        } else {
            listView.setAdapter(com_google_android_gms_cast_framework_media_zzbb);
            TabSpec newTabSpec = tabHost.newTabSpec("textTab");
            newTabSpec.setContent(R.id.text_list_view);
            newTabSpec.setIndicator(getActivity().getString(R.string.cast_tracks_chooser_dialog_subtitles));
            tabHost.addTab(newTabSpec);
        }
        if (bundle.getCount() <= 1) {
            listView2.setVisibility(4);
        } else {
            listView2.setAdapter(bundle);
            newTabSpec = tabHost.newTabSpec("audioTab");
            newTabSpec.setContent(R.id.audio_list_view);
            newTabSpec.setIndicator(getActivity().getString(R.string.cast_tracks_chooser_dialog_audio));
            tabHost.addTab(newTabSpec);
        }
        builder.setView(inflate).setPositiveButton(getActivity().getString(R.string.cast_tracks_chooser_dialog_ok), new zzba(this, com_google_android_gms_cast_framework_media_zzbb, bundle)).setNegativeButton(R.string.cast_tracks_chooser_dialog_cancel, new zzaz(this));
        if (this.zzpz != null) {
            this.zzpz.cancel();
            this.zzpz = null;
        }
        this.zzpz = builder.create();
        return this.zzpz;
    }

    private final void zza(zzbb com_google_android_gms_cast_framework_media_zzbb, zzbb com_google_android_gms_cast_framework_media_zzbb2) {
        if (this.zzpw) {
            if (this.zzig.hasMediaSession()) {
                List arrayList = new ArrayList();
                com_google_android_gms_cast_framework_media_zzbb = com_google_android_gms_cast_framework_media_zzbb.zzci();
                if (!(com_google_android_gms_cast_framework_media_zzbb == null || com_google_android_gms_cast_framework_media_zzbb.getId() == -1)) {
                    arrayList.add(Long.valueOf(com_google_android_gms_cast_framework_media_zzbb.getId()));
                }
                com_google_android_gms_cast_framework_media_zzbb = com_google_android_gms_cast_framework_media_zzbb2.zzci();
                if (com_google_android_gms_cast_framework_media_zzbb != null) {
                    arrayList.add(Long.valueOf(com_google_android_gms_cast_framework_media_zzbb.getId()));
                }
                if (this.zzdp != null && this.zzdp.length > null) {
                    com_google_android_gms_cast_framework_media_zzbb = new HashSet();
                    for (MediaTrack id : this.zzpy) {
                        com_google_android_gms_cast_framework_media_zzbb.add(Long.valueOf(id.getId()));
                    }
                    for (MediaTrack id2 : this.zzpx) {
                        com_google_android_gms_cast_framework_media_zzbb.add(Long.valueOf(id2.getId()));
                    }
                    for (long j : this.zzdp) {
                        if (!com_google_android_gms_cast_framework_media_zzbb.contains(Long.valueOf(j))) {
                            arrayList.add(Long.valueOf(j));
                        }
                    }
                }
                com_google_android_gms_cast_framework_media_zzbb = new long[arrayList.size()];
                for (com_google_android_gms_cast_framework_media_zzbb2 = null; com_google_android_gms_cast_framework_media_zzbb2 < arrayList.size(); com_google_android_gms_cast_framework_media_zzbb2++) {
                    com_google_android_gms_cast_framework_media_zzbb[com_google_android_gms_cast_framework_media_zzbb2] = ((Long) arrayList.get(com_google_android_gms_cast_framework_media_zzbb2)).longValue();
                }
                Arrays.sort(com_google_android_gms_cast_framework_media_zzbb);
                this.zzig.setActiveMediaTracks(com_google_android_gms_cast_framework_media_zzbb);
                zzch();
                return;
            }
        }
        zzch();
    }

    private final void zzch() {
        if (this.zzpz != null) {
            this.zzpz.cancel();
            this.zzpz = null;
        }
    }

    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @NonNull
    private static ArrayList<MediaTrack> zza(List<MediaTrack> list, int i) {
        ArrayList<MediaTrack> arrayList = new ArrayList();
        if (list != null) {
            for (MediaTrack mediaTrack : list) {
                if (mediaTrack.getType() == i) {
                    arrayList.add(mediaTrack);
                }
            }
        }
        return arrayList;
    }

    private static int zza(List<MediaTrack> list, long[] jArr, int i) {
        if (jArr != null) {
            if (list != null) {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    for (long j : jArr) {
                        if (j == ((MediaTrack) list.get(i2)).getId()) {
                            return i2;
                        }
                    }
                }
                return i;
            }
        }
        return i;
    }
}
