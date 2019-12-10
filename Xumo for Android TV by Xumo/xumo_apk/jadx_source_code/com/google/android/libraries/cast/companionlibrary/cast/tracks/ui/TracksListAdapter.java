package com.google.android.libraries.cast.companionlibrary.cast.tracks.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.libraries.cast.companionlibrary.R;
import java.util.ArrayList;
import java.util.List;

public class TracksListAdapter extends ArrayAdapter<MediaTrack> implements OnClickListener {
    private final Context mContext;
    private int mSelectedPosition = -1;
    private final List<MediaTrack> mTracks;

    private class Holder {
        private final TextView label;
        private final RadioButton radio;

        private Holder(TextView textView, RadioButton radioButton) {
            this.label = textView;
            this.radio = radioButton;
        }
    }

    public TracksListAdapter(Context context, int i, List<MediaTrack> list, int i2) {
        super(context, i);
        this.mContext = context;
        this.mTracks = new ArrayList();
        this.mTracks.addAll(list);
        this.mSelectedPosition = i2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        boolean z = false;
        if (view == null) {
            view = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.tracks_row_layout, viewGroup, false);
            viewGroup = new Holder((TextView) view.findViewById(R.id.text), (RadioButton) view.findViewById(R.id.radio));
            view.setTag(viewGroup);
        } else {
            viewGroup = (Holder) view.getTag();
        }
        viewGroup.radio.setTag(Integer.valueOf(i));
        RadioButton access$100 = viewGroup.radio;
        if (this.mSelectedPosition == i) {
            z = true;
        }
        access$100.setChecked(z);
        view.setOnClickListener(this);
        viewGroup.label.setText(((MediaTrack) this.mTracks.get(i)).getName());
        return view;
    }

    public int getCount() {
        return this.mTracks == null ? 0 : this.mTracks.size();
    }

    public void onClick(View view) {
        this.mSelectedPosition = ((Integer) ((Holder) view.getTag()).radio.getTag()).intValue();
        notifyDataSetChanged();
    }

    public MediaTrack getSelectedTrack() {
        return this.mSelectedPosition >= 0 ? (MediaTrack) this.mTracks.get(this.mSelectedPosition) : null;
    }
}
