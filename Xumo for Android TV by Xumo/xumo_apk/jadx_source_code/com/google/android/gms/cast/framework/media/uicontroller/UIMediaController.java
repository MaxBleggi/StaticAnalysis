package com.google.android.gms.cast.framework.media.uicontroller;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.ImageHints;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener;
import com.google.android.gms.cast.framework.media.TracksChooserDialogFragment;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.cast.zzao;
import com.google.android.gms.internal.cast.zzap;
import com.google.android.gms.internal.cast.zzar;
import com.google.android.gms.internal.cast.zzat;
import com.google.android.gms.internal.cast.zzaw;
import com.google.android.gms.internal.cast.zzax;
import com.google.android.gms.internal.cast.zzay;
import com.google.android.gms.internal.cast.zzaz;
import com.google.android.gms.internal.cast.zzbb;
import com.google.android.gms.internal.cast.zzbc;
import com.google.android.gms.internal.cast.zzbd;
import com.google.android.gms.internal.cast.zzbe;
import com.google.android.gms.internal.cast.zzbf;
import com.google.android.gms.internal.cast.zzbg;
import com.google.android.gms.internal.cast.zzbh;
import com.google.android.gms.internal.cast.zzbi;
import com.google.android.gms.internal.cast.zzbj;
import com.google.android.gms.internal.cast.zzbk;
import com.google.android.gms.internal.cast.zzbl;
import com.google.android.gms.internal.cast.zzdh;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UIMediaController implements SessionManagerListener<CastSession>, Listener {
    private static final zzdh zzbe = new zzdh("UIMediaController");
    private final SessionManager zzhj;
    private RemoteMediaClient zzig;
    private final Activity zzim;
    private final Map<View, List<UIController>> zzrd = new HashMap();
    private final Set<zzbj> zzre = new HashSet();
    private Listener zzrf;

    public UIMediaController(Activity activity) {
        this.zzim = activity;
        CastContext zzb = CastContext.zzb(activity);
        this.zzhj = zzb != null ? zzb.getSessionManager() : null;
        if (this.zzhj != null) {
            activity = CastContext.getSharedInstance(activity).getSessionManager();
            activity.addSessionManagerListener(this, CastSession.class);
            zza(activity.getCurrentCastSession());
        }
    }

    public void onSessionEnding(CastSession castSession) {
    }

    public void onSessionResuming(CastSession castSession, String str) {
    }

    public void onSessionStarting(CastSession castSession) {
    }

    public void onSessionSuspended(CastSession castSession, int i) {
    }

    public boolean isActive() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzig != null;
    }

    public RemoteMediaClient getRemoteMediaClient() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzig;
    }

    public void setPostRemoteMediaClientListener(Listener listener) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        this.zzrf = listener;
    }

    public void dispose() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zzcn();
        this.zzrd.clear();
        if (this.zzhj != null) {
            this.zzhj.removeSessionManagerListener(this, CastSession.class);
        }
        this.zzrf = null;
    }

    public void bindImageViewToPlayPauseToggle(@NonNull ImageView imageView, @NonNull Drawable drawable, @NonNull Drawable drawable2, Drawable drawable3, View view, boolean z) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        imageView.setOnClickListener(new zza(this));
        zza(imageView, new zzbb(imageView, this.zzim, drawable, drawable2, drawable3, view, z));
    }

    public void bindViewToSkipNext(View view, int i) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        view.setOnClickListener(new zzb(this));
        zza(view, new zzbf(view, i));
    }

    public void bindViewToSkipPrev(View view, int i) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        view.setOnClickListener(new zzc(this));
        zza(view, new zzbg(view, i));
    }

    public void bindViewToForward(View view, long j) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        view.setOnClickListener(new zzd(this, j));
        zza(view, new zzbe(view));
    }

    public void bindViewToRewind(View view, long j) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        view.setOnClickListener(new zze(this, j));
        zza(view, new zzbe(view));
    }

    public void bindViewToLoadingIndicator(View view) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(view, new zzaw(view));
    }

    public void bindProgressBar(ProgressBar progressBar) {
        bindProgressBar(progressBar, 1000);
    }

    public void bindProgressBar(ProgressBar progressBar, long j) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(progressBar, new zzbc(progressBar, j));
    }

    public void bindSeekBar(SeekBar seekBar) {
        bindSeekBar(seekBar, 1000);
    }

    public void bindSeekBar(SeekBar seekBar, long j) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        seekBar.setOnSeekBarChangeListener(new zzf(this));
        zza(seekBar, new zzbd(seekBar, j));
    }

    public void bindTextViewToStreamPosition(TextView textView, boolean z) {
        bindTextViewToStreamPosition(textView, z, 1000);
    }

    public void bindTextViewToStreamPosition(TextView textView, boolean z, long j) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        UIController com_google_android_gms_internal_cast_zzbj = new zzbj(textView, j, this.zzim.getString(R.string.cast_invalid_stream_position_text));
        if (z) {
            this.zzre.add(com_google_android_gms_internal_cast_zzbj);
        }
        zza(textView, com_google_android_gms_internal_cast_zzbj);
    }

    public void bindTextViewToStreamDuration(TextView textView) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(textView, new zzbi(textView, this.zzim.getString(R.string.cast_invalid_stream_duration_text), null));
    }

    public void bindTextViewToStreamDuration(TextView textView, View view) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(textView, new zzbi(textView, this.zzim.getString(R.string.cast_invalid_stream_duration_text), view));
    }

    public void bindViewToLaunchExpandedController(View view) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        view.setOnClickListener(new zzg(this));
        zza(view, new zzat(view));
    }

    public void bindViewToClosedCaption(View view) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        view.setOnClickListener(new zzh(this));
        zza(view, new zzao(view, this.zzim));
    }

    public void bindImageViewToMuteToggle(ImageView imageView) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        imageView.setOnClickListener(new zzi(this));
        zza(imageView, new zzaz(imageView, this.zzim));
    }

    public void bindTextViewToMetadataOfCurrentItem(TextView textView, String str) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        bindTextViewToMetadataOfCurrentItem(textView, Collections.singletonList(str));
    }

    public void bindTextViewToMetadataOfCurrentItem(TextView textView, List<String> list) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(textView, new zzay(textView, list));
    }

    @Deprecated
    public void bindImageViewToImageOfCurrentItem(ImageView imageView, int i, @DrawableRes int i2) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(imageView, new zzar(imageView, this.zzim, new ImageHints(i, 0, 0), i2, null));
    }

    @Deprecated
    public void bindImageViewToImageOfCurrentItem(ImageView imageView, int i, View view) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(imageView, new zzar(imageView, this.zzim, new ImageHints(i, 0, 0), 0, view));
    }

    public void bindImageViewToImageOfCurrentItem(ImageView imageView, @NonNull ImageHints imageHints, @DrawableRes int i) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(imageView, new zzar(imageView, this.zzim, imageHints, i, null));
    }

    public void bindImageViewToImageOfCurrentItem(ImageView imageView, @NonNull ImageHints imageHints, View view) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(imageView, new zzar(imageView, this.zzim, imageHints, 0, view));
    }

    public void bindViewVisibilityToMediaSession(View view, int i) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(view, new zzbl(view, i));
    }

    public void bindTextViewToMetadataOfPreloadedItem(TextView textView, String str) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        bindTextViewToMetadataOfPreloadedItem(textView, Collections.singletonList(str));
    }

    public void bindTextViewToMetadataOfPreloadedItem(TextView textView, List<String> list) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(textView, new zzax(textView, list));
    }

    @Deprecated
    public void bindImageViewToImageOfPreloadedItem(ImageView imageView, int i, @DrawableRes int i2) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(imageView, new zzap(imageView, this.zzim, new ImageHints(i, 0, 0), i2));
    }

    public void bindImageViewToImageOfPreloadedItem(ImageView imageView, @NonNull ImageHints imageHints, @DrawableRes int i) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(imageView, new zzap(imageView, this.zzim, imageHints, i));
    }

    public void bindViewVisibilityToPreloadingEvent(View view, int i) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(view, new zzbk(view, i));
    }

    public void bindViewToUIController(View view, UIController uIController) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(view, uIController);
    }

    public void bindTextViewToSmartSubtitle(TextView textView) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        zza(textView, new zzbh(textView));
    }

    protected void onMuteToggleClicked(ImageView imageView) {
        imageView = CastContext.getSharedInstance(this.zzim.getApplicationContext()).getSessionManager().getCurrentCastSession();
        if (imageView != null) {
            if (imageView.isConnected()) {
                try {
                    imageView.setMute(imageView.isMute() ^ true);
                } catch (ImageView imageView2) {
                    zzbe.e("Unable to call CastSession.setMute(boolean).", imageView2);
                }
            }
        }
    }

    protected void onPlayPauseToggleClicked(ImageView imageView) {
        imageView = getRemoteMediaClient();
        if (imageView != null && imageView.hasMediaSession()) {
            imageView.togglePlayback();
        }
    }

    protected void onSeekBarStopTrackingTouch(SeekBar seekBar) {
        if (this.zzrd.containsKey(seekBar)) {
            for (UIController uIController : (List) this.zzrd.get(seekBar)) {
                if (uIController instanceof zzbd) {
                    ((zzbd) uIController).zzk(true);
                }
            }
        }
        for (zzbj zzk : this.zzre) {
            zzk.zzk(true);
        }
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null && remoteMediaClient.hasMediaSession()) {
            remoteMediaClient.seek((long) seekBar.getProgress());
        }
    }

    protected void onSeekBarStartTrackingTouch(SeekBar seekBar) {
        if (this.zzrd.containsKey(seekBar)) {
            for (UIController uIController : (List) this.zzrd.get(seekBar)) {
                if (uIController instanceof zzbd) {
                    ((zzbd) uIController).zzk(false);
                }
            }
        }
        for (zzbj zzk : this.zzre) {
            zzk.zzk(false);
        }
    }

    protected void onSeekBarProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            for (zzbj zzc : this.zzre) {
                zzc.zzc((long) i);
            }
        }
    }

    protected void onClosedCaptionClicked(View view) {
        view = getRemoteMediaClient();
        if (view != null) {
            if (view.hasMediaSession()) {
                if (this.zzim instanceof FragmentActivity) {
                    TracksChooserDialogFragment newInstance = TracksChooserDialogFragment.newInstance();
                    FragmentActivity fragmentActivity = (FragmentActivity) this.zzim;
                    FragmentTransaction beginTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                    Fragment findFragmentByTag = fragmentActivity.getSupportFragmentManager().findFragmentByTag("TRACKS_CHOOSER_DIALOG_TAG");
                    if (findFragmentByTag != null) {
                        beginTransaction.remove(findFragmentByTag);
                    }
                    view.getMediaInfo();
                    view.getMediaStatus().getActiveTrackIds();
                    newInstance.show(beginTransaction, "TRACKS_CHOOSER_DIALOG_TAG");
                }
            }
        }
    }

    protected void onForwardClicked(View view, long j) {
        view = getRemoteMediaClient();
        if (view != null && view.hasMediaSession()) {
            view.seek(view.getApproximateStreamPosition() + j);
        }
    }

    protected void onLaunchExpandedControllerClicked(View view) {
        view = CastContext.getSharedInstance(this.zzim).getCastOptions().getCastMediaOptions();
        if (view != null) {
            if (!TextUtils.isEmpty(view.getExpandedControllerActivityClassName())) {
                ComponentName componentName = new ComponentName(this.zzim.getApplicationContext(), view.getExpandedControllerActivityClassName());
                view = new Intent();
                view.setComponent(componentName);
                this.zzim.startActivity(view);
            }
        }
    }

    protected void onRewindClicked(View view, long j) {
        view = getRemoteMediaClient();
        if (view != null && view.hasMediaSession()) {
            view.seek(view.getApproximateStreamPosition() - j);
        }
    }

    protected void onSkipNextClicked(View view) {
        view = getRemoteMediaClient();
        if (view != null && view.hasMediaSession()) {
            view.queueNext(null);
        }
    }

    protected void onSkipPrevClicked(View view) {
        view = getRemoteMediaClient();
        if (view != null && view.hasMediaSession()) {
            view.queuePrev(null);
        }
    }

    public void onSessionStarted(CastSession castSession, String str) {
        zza(castSession);
    }

    public void onSessionResumed(CastSession castSession, boolean z) {
        zza(castSession);
    }

    public void onSessionStartFailed(CastSession castSession, int i) {
        zzcn();
    }

    public void onSessionEnded(CastSession castSession, int i) {
        zzcn();
    }

    public void onSessionResumeFailed(CastSession castSession, int i) {
        zzcn();
    }

    public void onStatusUpdated() {
        zzco();
        if (this.zzrf != null) {
            this.zzrf.onStatusUpdated();
        }
    }

    public void onMetadataUpdated() {
        zzco();
        if (this.zzrf != null) {
            this.zzrf.onMetadataUpdated();
        }
    }

    public void onQueueStatusUpdated() {
        zzco();
        if (this.zzrf != null) {
            this.zzrf.onQueueStatusUpdated();
        }
    }

    public void onPreloadStatusUpdated() {
        zzco();
        if (this.zzrf != null) {
            this.zzrf.onPreloadStatusUpdated();
        }
    }

    public void onAdBreakStatusUpdated() {
        zzco();
        if (this.zzrf != null) {
            this.zzrf.onAdBreakStatusUpdated();
        }
    }

    public void onSendingRemoteMediaRequest() {
        for (List<UIController> it : this.zzrd.values()) {
            for (UIController onSendingRemoteMediaRequest : it) {
                onSendingRemoteMediaRequest.onSendingRemoteMediaRequest();
            }
        }
        if (this.zzrf != null) {
            this.zzrf.onSendingRemoteMediaRequest();
        }
    }

    private final void zza(Session session) {
        if (!isActive() && (session instanceof CastSession)) {
            if (session.isConnected()) {
                CastSession castSession = (CastSession) session;
                this.zzig = castSession.getRemoteMediaClient();
                if (this.zzig != null) {
                    this.zzig.addListener(this);
                    for (List<UIController> it : this.zzrd.values()) {
                        for (UIController onSessionConnected : it) {
                            onSessionConnected.onSessionConnected(castSession);
                        }
                    }
                    zzco();
                }
            }
        }
    }

    private final void zzcn() {
        if (isActive()) {
            for (List<UIController> it : this.zzrd.values()) {
                for (UIController onSessionEnded : it) {
                    onSessionEnded.onSessionEnded();
                }
            }
            this.zzig.removeListener(this);
            this.zzig = null;
        }
    }

    private final void zza(View view, UIController uIController) {
        if (this.zzhj != null) {
            List list = (List) this.zzrd.get(view);
            if (list == null) {
                list = new ArrayList();
                this.zzrd.put(view, list);
            }
            list.add(uIController);
            if (isActive() != null) {
                uIController.onSessionConnected(this.zzhj.getCurrentCastSession());
                zzco();
            }
        }
    }

    private final void zzco() {
        for (List<UIController> it : this.zzrd.values()) {
            for (UIController onMediaStatusUpdated : it) {
                onMediaStatusUpdated.onMediaStatusUpdated();
            }
        }
    }
}
