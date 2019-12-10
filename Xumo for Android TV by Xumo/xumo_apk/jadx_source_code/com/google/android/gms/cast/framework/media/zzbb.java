package com.google.android.gms.cast.framework.media;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.framework.R;
import java.util.ArrayList;
import java.util.List;

public final class zzbb extends ArrayAdapter<MediaTrack> implements OnClickListener {
    private final Context zzja;
    private int zzqf;

    public zzbb(Context context, List<MediaTrack> list, int i) {
        int i2 = R.layout.cast_tracks_chooser_dialog_row_layout;
        if (list == null) {
            list = new ArrayList();
        }
        super(context, i2, list);
        this.zzqf = -1;
        this.zzja = context;
        this.zzqf = i;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater) this.zzja.getSystemService("layout_inflater")).inflate(R.layout.cast_tracks_chooser_dialog_row_layout, viewGroup, false);
            viewGroup = new zzbd(this, (TextView) view.findViewById(R.id.text), (RadioButton) view.findViewById(R.id.radio));
            view.setTag(viewGroup);
        } else {
            viewGroup = (zzbd) view.getTag();
        }
        if (viewGroup == null) {
            return null;
        }
        viewGroup.zzqh.setTag(Integer.valueOf(i));
        viewGroup.zzqh.setChecked(this.zzqf == i);
        view.setOnClickListener(this);
        MediaTrack mediaTrack = (MediaTrack) getItem(i);
        CharSequence name = mediaTrack.getName();
        if (TextUtils.isEmpty(name)) {
            if (mediaTrack.getSubtype() == 2) {
                i = this.zzja.getString(R.string.cast_tracks_chooser_dialog_closed_captions);
            } else {
                if (!TextUtils.isEmpty(mediaTrack.getLanguage())) {
                    CharSequence displayLanguage = MediaUtils.getTrackLanguage(mediaTrack).getDisplayLanguage();
                    if (!TextUtils.isEmpty(displayLanguage)) {
                        i = displayLanguage;
                    }
                }
                name = this.zzja.getString(R.string.cast_tracks_chooser_dialog_default_track_name, new Object[]{Integer.valueOf(i + 1)});
            }
            viewGroup.zzqg.setText(i);
            return view;
        }
        i = name;
        viewGroup.zzqg.setText(i);
        return view;
    }

    public final void onClick(View view) {
        this.zzqf = ((Integer) ((zzbd) view.getTag()).zzqh.getTag()).intValue();
        notifyDataSetChanged();
    }

    public final MediaTrack zzci() {
        return (this.zzqf < 0 || this.zzqf >= getCount()) ? null : (MediaTrack) getItem(this.zzqf);
    }
}
