package com.xumo.xumo.util;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.exoplayer2.offline.ActionFile;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.offline.DownloadAction.Deserializer;
import com.google.android.exoplayer2.offline.DownloadHelper;
import com.google.android.exoplayer2.offline.DownloadHelper.Callback;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadManager.TaskState;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.offline.ProgressiveDownloadHelper;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.offline.TrackKey;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.offline.DashDownloadHelper;
import com.google.android.exoplayer2.source.hls.offline.HlsDownloadHelper;
import com.google.android.exoplayer2.source.smoothstreaming.offline.SsDownloadHelper;
import com.google.android.exoplayer2.ui.DefaultTrackNameProvider;
import com.google.android.exoplayer2.ui.TrackNameProvider;
import com.google.android.exoplayer2.upstream.DataSource.Factory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.xumo.xumo.tv.R;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class DownloadTracker implements com.google.android.exoplayer2.offline.DownloadManager.Listener {
    private static final String TAG = "DownloadTracker";
    private final ActionFile actionFile;
    private final Handler actionFileWriteHandler;
    private final Context context;
    private final Factory dataSourceFactory;
    private final CopyOnWriteArraySet<Listener> listeners = new CopyOnWriteArraySet();
    private final TrackNameProvider trackNameProvider;
    private final HashMap<Uri, DownloadAction> trackedDownloadStates = new HashMap();

    public interface Listener {
        void onDownloadsChanged();
    }

    private final class StartDownloadDialogHelper implements Callback, OnClickListener {
        private final Builder builder;
        private final View dialogView = LayoutInflater.from(this.builder.getContext()).inflate(R.layout.start_download_dialog, null);
        private final DownloadHelper downloadHelper;
        private final String name;
        private final ListView representationList = ((ListView) this.dialogView.findViewById(R.id.representation_list));
        private final List<TrackKey> trackKeys = new ArrayList();
        private final ArrayAdapter<String> trackTitles = new ArrayAdapter(this.builder.getContext(), 17367056);

        public StartDownloadDialogHelper(Activity activity, DownloadHelper downloadHelper, String str) {
            this.downloadHelper = downloadHelper;
            this.name = str;
            this.builder = new Builder(activity).setTitle(R.string.exo_download_description).setPositiveButton(17039370, this).setNegativeButton(17039360, null);
            this.representationList.setChoiceMode(2);
            this.representationList.setAdapter(this.trackTitles);
        }

        public void prepare() {
            this.downloadHelper.prepare(this);
        }

        public void onPrepared(DownloadHelper downloadHelper) {
            for (int i = 0; i < this.downloadHelper.getPeriodCount(); i++) {
                TrackGroupArray trackGroups = this.downloadHelper.getTrackGroups(i);
                for (int i2 = 0; i2 < trackGroups.length; i2++) {
                    TrackGroup trackGroup = trackGroups.get(i2);
                    for (int i3 = 0; i3 < trackGroup.length; i3++) {
                        this.trackKeys.add(new TrackKey(i, i2, i3));
                        this.trackTitles.add(DownloadTracker.this.trackNameProvider.getTrackName(trackGroup.getFormat(i3)));
                    }
                }
            }
            if (this.trackKeys.isEmpty() == null) {
                this.builder.setView(this.dialogView);
            }
            this.builder.create().show();
        }

        public void onPrepareError(DownloadHelper downloadHelper, IOException iOException) {
            Log.e(DownloadTracker.TAG, "Failed to start download", iOException);
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface = new ArrayList();
            for (i = 0; i < this.representationList.getChildCount(); i++) {
                if (this.representationList.isItemChecked(i)) {
                    dialogInterface.add(this.trackKeys.get(i));
                }
            }
            if (dialogInterface.isEmpty() == 0 || this.trackKeys.isEmpty() != 0) {
                DownloadTracker.this.startDownload(this.downloadHelper.getDownloadAction(Util.getUtf8Bytes(this.name), dialogInterface));
            }
        }
    }

    public void onIdle(DownloadManager downloadManager) {
    }

    public void onInitialized(DownloadManager downloadManager) {
    }

    public DownloadTracker(Context context, Factory factory, File file, Deserializer... deserializerArr) {
        this.context = context.getApplicationContext();
        this.dataSourceFactory = factory;
        this.actionFile = new ActionFile(file);
        this.trackNameProvider = new DefaultTrackNameProvider(context.getResources());
        context = new HandlerThread(TAG);
        context.start();
        this.actionFileWriteHandler = new Handler(context.getLooper());
        if (deserializerArr.length <= null) {
            deserializerArr = DownloadAction.getDefaultDeserializers();
        }
        loadTrackedActions(deserializerArr);
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public boolean isDownloaded(Uri uri) {
        return this.trackedDownloadStates.containsKey(uri);
    }

    public List<StreamKey> getOfflineStreamKeys(Uri uri) {
        if (this.trackedDownloadStates.containsKey(uri)) {
            return ((DownloadAction) this.trackedDownloadStates.get(uri)).getKeys();
        }
        return Collections.emptyList();
    }

    public void toggleDownload(Activity activity, String str, Uri uri, String str2) {
        if (isDownloaded(uri)) {
            startServiceWithAction(getDownloadHelper(uri, str2).getRemoveAction(Util.getUtf8Bytes(str)));
        } else {
            new StartDownloadDialogHelper(activity, getDownloadHelper(uri, str2), str).prepare();
        }
    }

    public void onTaskStateChanged(DownloadManager downloadManager, TaskState taskState) {
        downloadManager = taskState.action;
        Uri uri = downloadManager.uri;
        if (((downloadManager.isRemoveAction && taskState.state == 2) || (downloadManager.isRemoveAction == null && taskState.state == 4)) && this.trackedDownloadStates.remove(uri) != null) {
            handleTrackedDownloadStatesChanged();
        }
    }

    private void loadTrackedActions(Deserializer[] deserializerArr) {
        try {
            for (DownloadAction downloadAction : this.actionFile.load(deserializerArr)) {
                this.trackedDownloadStates.put(downloadAction.uri, downloadAction);
            }
        } catch (Deserializer[] deserializerArr2) {
            Log.e(TAG, "Failed to load tracked actions", deserializerArr2);
        }
    }

    private void handleTrackedDownloadStatesChanged() {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((Listener) it.next()).onDownloadsChanged();
        }
        this.actionFileWriteHandler.post(new -$$Lambda$DownloadTracker$bzokEdj9PSB55m-U0EMSfNRGwtE(this, (DownloadAction[]) this.trackedDownloadStates.values().toArray(new DownloadAction[0])));
    }

    public static /* synthetic */ void lambda$handleTrackedDownloadStatesChanged$0(DownloadTracker downloadTracker, DownloadAction[] downloadActionArr) {
        try {
            downloadTracker.actionFile.store(downloadActionArr);
        } catch (DownloadAction[] downloadActionArr2) {
            Log.e(TAG, "Failed to store tracked actions", downloadActionArr2);
        }
    }

    private void startDownload(DownloadAction downloadAction) {
        if (!this.trackedDownloadStates.containsKey(downloadAction.uri)) {
            this.trackedDownloadStates.put(downloadAction.uri, downloadAction);
            handleTrackedDownloadStatesChanged();
            startServiceWithAction(downloadAction);
        }
    }

    private void startServiceWithAction(DownloadAction downloadAction) {
        DownloadService.startWithAction(this.context, DemoDownloadService.class, downloadAction, false);
    }

    private DownloadHelper getDownloadHelper(Uri uri, String str) {
        str = Util.inferContentType(uri, str);
        switch (str) {
            case null:
                return new DashDownloadHelper(uri, this.dataSourceFactory);
            case 1:
                return new SsDownloadHelper(uri, this.dataSourceFactory);
            case 2:
                return new HlsDownloadHelper(uri, this.dataSourceFactory);
            case 3:
                return new ProgressiveDownloadHelper(uri);
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported type: ");
                stringBuilder.append(str);
                throw new IllegalStateException(stringBuilder.toString());
        }
    }
}
