package androidx.mediarouter.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaControllerCompat.Callback;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.util.ObjectsCompat;
import androidx.mediarouter.R;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.MediaRouter.RouteGroup;
import androidx.mediarouter.media.MediaRouter.RouteInfo;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestrictTo({Scope.LIBRARY_GROUP})
public class MediaRouteCastDialog extends AppCompatDialog {
    static final int CONNECTION_TIMEOUT_MILLIS = ((int) TimeUnit.SECONDS.toMillis(30));
    static final int MSG_UPDATE_ROUTES = 1;
    static final String TAG = "MediaRouteCastDialog";
    private static final long UPDATE_ROUTES_DELAY_MS = 300;
    private RecyclerAdapter mAdapter;
    int mArtIconBackgroundColor;
    Bitmap mArtIconBitmap;
    boolean mArtIconIsLoaded;
    Bitmap mArtIconLoadedBitmap;
    Uri mArtIconUri;
    private ImageView mArtView;
    private boolean mAttachedToWindow;
    private final MediaRouterCallback mCallback;
    private ImageButton mCloseButton;
    Context mContext;
    MediaControllerCallback mControllerCallback;
    private boolean mCreated;
    MediaDescriptionCompat mDescription;
    FetchArtTask mFetchArtTask;
    private final Handler mHandler;
    private long mLastUpdateTime;
    MediaControllerCompat mMediaController;
    private RelativeLayout mMetadataLayout;
    private RecyclerView mRecyclerView;
    final RouteInfo mRoute;
    final MediaRouter mRouter;
    final List<RouteInfo> mRoutes;
    private MediaRouteSelector mSelector;
    private Button mStopCastingButton;
    private TextView mSubtitleView;
    private String mTitlePlaceholder;
    private TextView mTitleView;
    VolumeChangeListener mVolumeChangeListener;
    int mVolumeSliderColor;

    private class FetchArtTask extends AsyncTask<Void, Void, Bitmap> {
        private int mBackgroundColor;
        private final Bitmap mIconBitmap;
        private final Uri mIconUri;

        FetchArtTask() {
            Uri uri = null;
            Bitmap iconBitmap = MediaRouteCastDialog.this.mDescription == null ? null : MediaRouteCastDialog.this.mDescription.getIconBitmap();
            if (MediaRouteCastDialog.isBitmapRecycled(iconBitmap)) {
                Log.w(MediaRouteCastDialog.TAG, "Can't fetch the given art bitmap because it's already recycled.");
                iconBitmap = null;
            }
            this.mIconBitmap = iconBitmap;
            if (MediaRouteCastDialog.this.mDescription != null) {
                uri = MediaRouteCastDialog.this.mDescription.getIconUri();
            }
            this.mIconUri = uri;
        }

        public Bitmap getIconBitmap() {
            return this.mIconBitmap;
        }

        public Uri getIconUri() {
            return this.mIconUri;
        }

        protected void onPreExecute() {
            MediaRouteCastDialog.this.clearLoadedBitmap();
        }

        protected android.graphics.Bitmap doInBackground(java.lang.Void... r8) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r7 = this;
            r8 = r7.mIconBitmap;
            r0 = 0;
            r1 = 1;
            r2 = 0;
            if (r8 == 0) goto L_0x000b;
        L_0x0007:
            r8 = r7.mIconBitmap;
            goto L_0x00e0;
        L_0x000b:
            r8 = r7.mIconUri;
            if (r8 == 0) goto L_0x00df;
        L_0x000f:
            r8 = r7.mIconUri;	 Catch:{ IOException -> 0x00b8, all -> 0x00b5 }
            r8 = r7.openInputStreamByScheme(r8);	 Catch:{ IOException -> 0x00b8, all -> 0x00b5 }
            if (r8 != 0) goto L_0x0038;
        L_0x0017:
            r3 = "MediaRouteCastDialog";	 Catch:{ IOException -> 0x0035 }
            r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0035 }
            r4.<init>();	 Catch:{ IOException -> 0x0035 }
            r5 = "Unable to open: ";	 Catch:{ IOException -> 0x0035 }
            r4.append(r5);	 Catch:{ IOException -> 0x0035 }
            r5 = r7.mIconUri;	 Catch:{ IOException -> 0x0035 }
            r4.append(r5);	 Catch:{ IOException -> 0x0035 }
            r4 = r4.toString();	 Catch:{ IOException -> 0x0035 }
            android.util.Log.w(r3, r4);	 Catch:{ IOException -> 0x0035 }
            if (r8 == 0) goto L_0x0034;
        L_0x0031:
            r8.close();	 Catch:{ IOException -> 0x0034 }
        L_0x0034:
            return r2;
        L_0x0035:
            r3 = move-exception;
            goto L_0x00ba;
        L_0x0038:
            r3 = new android.graphics.BitmapFactory$Options;	 Catch:{ IOException -> 0x0035 }
            r3.<init>();	 Catch:{ IOException -> 0x0035 }
            r3.inJustDecodeBounds = r1;	 Catch:{ IOException -> 0x0035 }
            android.graphics.BitmapFactory.decodeStream(r8, r2, r3);	 Catch:{ IOException -> 0x0035 }
            r4 = r3.outWidth;	 Catch:{ IOException -> 0x0035 }
            if (r4 == 0) goto L_0x00af;	 Catch:{ IOException -> 0x0035 }
        L_0x0046:
            r4 = r3.outHeight;	 Catch:{ IOException -> 0x0035 }
            if (r4 != 0) goto L_0x004b;
        L_0x004a:
            goto L_0x00af;
        L_0x004b:
            r8.reset();	 Catch:{ IOException -> 0x004f }
            goto L_0x007f;
        L_0x004f:
            r8.close();	 Catch:{ IOException -> 0x0035 }
            r4 = r7.mIconUri;	 Catch:{ IOException -> 0x0035 }
            r4 = r7.openInputStreamByScheme(r4);	 Catch:{ IOException -> 0x0035 }
            if (r4 != 0) goto L_0x007e;
        L_0x005a:
            r8 = "MediaRouteCastDialog";	 Catch:{ IOException -> 0x007b, all -> 0x0078 }
            r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x007b, all -> 0x0078 }
            r3.<init>();	 Catch:{ IOException -> 0x007b, all -> 0x0078 }
            r5 = "Unable to open: ";	 Catch:{ IOException -> 0x007b, all -> 0x0078 }
            r3.append(r5);	 Catch:{ IOException -> 0x007b, all -> 0x0078 }
            r5 = r7.mIconUri;	 Catch:{ IOException -> 0x007b, all -> 0x0078 }
            r3.append(r5);	 Catch:{ IOException -> 0x007b, all -> 0x0078 }
            r3 = r3.toString();	 Catch:{ IOException -> 0x007b, all -> 0x0078 }
            android.util.Log.w(r8, r3);	 Catch:{ IOException -> 0x007b, all -> 0x0078 }
            if (r4 == 0) goto L_0x0077;
        L_0x0074:
            r4.close();	 Catch:{ IOException -> 0x0077 }
        L_0x0077:
            return r2;
        L_0x0078:
            r0 = move-exception;
            r8 = r4;
            goto L_0x00d9;
        L_0x007b:
            r3 = move-exception;
            r8 = r4;
            goto L_0x00ba;
        L_0x007e:
            r8 = r4;
        L_0x007f:
            r3.inJustDecodeBounds = r0;	 Catch:{ IOException -> 0x0035 }
            r4 = androidx.mediarouter.app.MediaRouteCastDialog.this;	 Catch:{ IOException -> 0x0035 }
            r5 = r3.outWidth;	 Catch:{ IOException -> 0x0035 }
            r6 = r3.outHeight;	 Catch:{ IOException -> 0x0035 }
            r4 = r4.getDesiredArtHeight(r5, r6);	 Catch:{ IOException -> 0x0035 }
            r5 = r3.outHeight;	 Catch:{ IOException -> 0x0035 }
            r5 = r5 / r4;	 Catch:{ IOException -> 0x0035 }
            r4 = java.lang.Integer.highestOneBit(r5);	 Catch:{ IOException -> 0x0035 }
            r4 = java.lang.Math.max(r1, r4);	 Catch:{ IOException -> 0x0035 }
            r3.inSampleSize = r4;	 Catch:{ IOException -> 0x0035 }
            r4 = r7.isCancelled();	 Catch:{ IOException -> 0x0035 }
            if (r4 == 0) goto L_0x00a4;
        L_0x009e:
            if (r8 == 0) goto L_0x00a3;
        L_0x00a0:
            r8.close();	 Catch:{ IOException -> 0x00a3 }
        L_0x00a3:
            return r2;
        L_0x00a4:
            r3 = android.graphics.BitmapFactory.decodeStream(r8, r2, r3);	 Catch:{ IOException -> 0x0035 }
            if (r8 == 0) goto L_0x00ad;
        L_0x00aa:
            r8.close();	 Catch:{ IOException -> 0x00ad }
        L_0x00ad:
            r8 = r3;
            goto L_0x00e0;
        L_0x00af:
            if (r8 == 0) goto L_0x00b4;
        L_0x00b1:
            r8.close();	 Catch:{ IOException -> 0x00b4 }
        L_0x00b4:
            return r2;
        L_0x00b5:
            r0 = move-exception;
            r8 = r2;
            goto L_0x00d9;
        L_0x00b8:
            r3 = move-exception;
            r8 = r2;
        L_0x00ba:
            r4 = "MediaRouteCastDialog";	 Catch:{ all -> 0x00d8 }
            r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00d8 }
            r5.<init>();	 Catch:{ all -> 0x00d8 }
            r6 = "Unable to open: ";	 Catch:{ all -> 0x00d8 }
            r5.append(r6);	 Catch:{ all -> 0x00d8 }
            r6 = r7.mIconUri;	 Catch:{ all -> 0x00d8 }
            r5.append(r6);	 Catch:{ all -> 0x00d8 }
            r5 = r5.toString();	 Catch:{ all -> 0x00d8 }
            android.util.Log.w(r4, r5, r3);	 Catch:{ all -> 0x00d8 }
            if (r8 == 0) goto L_0x00df;
        L_0x00d4:
            r8.close();	 Catch:{ IOException -> 0x00df }
            goto L_0x00df;
        L_0x00d8:
            r0 = move-exception;
        L_0x00d9:
            if (r8 == 0) goto L_0x00de;
        L_0x00db:
            r8.close();	 Catch:{ IOException -> 0x00de }
        L_0x00de:
            throw r0;
        L_0x00df:
            r8 = r2;
        L_0x00e0:
            r3 = androidx.mediarouter.app.MediaRouteCastDialog.isBitmapRecycled(r8);
            if (r3 == 0) goto L_0x00fd;
        L_0x00e6:
            r0 = "MediaRouteCastDialog";
            r1 = new java.lang.StringBuilder;
            r1.<init>();
            r3 = "Can't use recycled bitmap: ";
            r1.append(r3);
            r1.append(r8);
            r8 = r1.toString();
            android.util.Log.w(r0, r8);
            return r2;
        L_0x00fd:
            if (r8 == 0) goto L_0x0131;
        L_0x00ff:
            r2 = r8.getWidth();
            r3 = r8.getHeight();
            if (r2 >= r3) goto L_0x0131;
        L_0x0109:
            r2 = new androidx.palette.graphics.Palette$Builder;
            r2.<init>(r8);
            r1 = r2.maximumColorCount(r1);
            r1 = r1.generate();
            r2 = r1.getSwatches();
            r2 = r2.isEmpty();
            if (r2 == 0) goto L_0x0121;
        L_0x0120:
            goto L_0x012f;
        L_0x0121:
            r1 = r1.getSwatches();
            r0 = r1.get(r0);
            r0 = (androidx.palette.graphics.Palette.Swatch) r0;
            r0 = r0.getRgb();
        L_0x012f:
            r7.mBackgroundColor = r0;
        L_0x0131:
            return r8;
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteCastDialog.FetchArtTask.doInBackground(java.lang.Void[]):android.graphics.Bitmap");
        }

        protected void onPostExecute(Bitmap bitmap) {
            MediaRouteCastDialog.this.mFetchArtTask = null;
            if (!ObjectsCompat.equals(MediaRouteCastDialog.this.mArtIconBitmap, this.mIconBitmap) || !ObjectsCompat.equals(MediaRouteCastDialog.this.mArtIconUri, this.mIconUri)) {
                MediaRouteCastDialog.this.mArtIconBitmap = this.mIconBitmap;
                MediaRouteCastDialog.this.mArtIconLoadedBitmap = bitmap;
                MediaRouteCastDialog.this.mArtIconUri = this.mIconUri;
                MediaRouteCastDialog.this.mArtIconBackgroundColor = this.mBackgroundColor;
                MediaRouteCastDialog.this.mArtIconIsLoaded = true;
                MediaRouteCastDialog.this.update();
            }
        }

        private InputStream openInputStreamByScheme(Uri uri) throws IOException {
            String toLowerCase = uri.getScheme().toLowerCase();
            if (!("android.resource".equals(toLowerCase) || Param.CONTENT.equals(toLowerCase))) {
                if (!"file".equals(toLowerCase)) {
                    uri = new URL(uri.toString()).openConnection();
                    uri.setConnectTimeout(MediaRouteCastDialog.CONNECTION_TIMEOUT_MILLIS);
                    uri.setReadTimeout(MediaRouteCastDialog.CONNECTION_TIMEOUT_MILLIS);
                    uri = uri.getInputStream();
                    if (uri != null) {
                        return null;
                    }
                    return new BufferedInputStream(uri);
                }
            }
            uri = MediaRouteCastDialog.this.mContext.getContentResolver().openInputStream(uri);
            if (uri != null) {
                return new BufferedInputStream(uri);
            }
            return null;
        }
    }

    private class VolumeChangeListener implements OnSeekBarChangeListener {
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        VolumeChangeListener() {
        }
    }

    private final class MediaControllerCallback extends Callback {
        MediaControllerCallback() {
        }

        public void onSessionDestroyed() {
            if (MediaRouteCastDialog.this.mMediaController != null) {
                MediaRouteCastDialog.this.mMediaController.unregisterCallback(MediaRouteCastDialog.this.mControllerCallback);
                MediaRouteCastDialog.this.mMediaController = null;
            }
        }

        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
            MediaRouteCastDialog.this.mDescription = mediaMetadataCompat == null ? null : mediaMetadataCompat.getDescription();
            MediaRouteCastDialog.this.updateArtIconIfNeeded();
            MediaRouteCastDialog.this.update();
        }
    }

    private final class MediaRouterCallback extends MediaRouter.Callback {
        MediaRouterCallback() {
        }

        public void onRouteAdded(MediaRouter mediaRouter, RouteInfo routeInfo) {
            MediaRouteCastDialog.this.refreshRoutes();
        }

        public void onRouteRemoved(MediaRouter mediaRouter, RouteInfo routeInfo) {
            MediaRouteCastDialog.this.refreshRoutes();
        }

        public void onRouteSelected(MediaRouter mediaRouter, RouteInfo routeInfo) {
            MediaRouteCastDialog.this.update();
        }

        public void onRouteUnselected(MediaRouter mediaRouter, RouteInfo routeInfo) {
            MediaRouteCastDialog.this.update();
        }

        public void onRouteChanged(MediaRouter mediaRouter, RouteInfo routeInfo) {
            MediaRouteCastDialog.this.refreshRoutes();
            MediaRouteCastDialog.this.update();
        }
    }

    private final class RecyclerAdapter extends Adapter<ViewHolder> {
        private static final int ITEM_TYPE_GROUP = 4;
        private static final int ITEM_TYPE_GROUP_VOLUME = 1;
        private static final int ITEM_TYPE_HEADER = 2;
        private static final int ITEM_TYPE_ROUTE = 3;
        private static final String TAG = "RecyclerAdapter";
        private final ArrayList<RouteInfo> mAvailableGroups = new ArrayList();
        private final ArrayList<RouteInfo> mAvailableRoutes = new ArrayList();
        private final Drawable mDefaultIcon;
        private final LayoutInflater mInflater;
        private final ArrayList<Item> mItems = new ArrayList();
        private final Drawable mSpeakerGroupIcon;
        private final Drawable mSpeakerIcon;
        private final Drawable mTvIcon;

        private class Item {
            private final Object mData;
            private final int mType;

            Item(Object obj, int i) {
                this.mData = obj;
                this.mType = i;
            }

            public Object getData() {
                return this.mData;
            }

            public int getType() {
                return this.mType;
            }
        }

        private class GroupViewHolder extends ViewHolder {
            ImageView mImageView;
            TextView mTextView;

            GroupViewHolder(View view) {
                super(view);
                this.mImageView = (ImageView) view.findViewById(R.id.mr_cast_group_icon);
                this.mTextView = (TextView) view.findViewById(R.id.mr_cast_group_name);
            }

            public void bindGroupViewHolder(Item item) {
                RouteInfo routeInfo = (RouteInfo) item.getData();
                this.mImageView.setImageDrawable(RecyclerAdapter.this.getIconDrawable(routeInfo));
                this.mTextView.setText(routeInfo.getName());
            }
        }

        private class GroupVolumeViewHolder extends ViewHolder {
            MediaRouteVolumeSlider mGroupVolumeSlider;
            TextView mTextView;

            GroupVolumeViewHolder(View view) {
                super(view);
                this.mTextView = (TextView) view.findViewById(R.id.mr_group_volume_route_name);
                this.mGroupVolumeSlider = (MediaRouteVolumeSlider) view.findViewById(R.id.mr_group_volume_slider);
            }

            public void bindGroupVolumeView(Item item) {
                RouteInfo routeInfo = (RouteInfo) item.getData();
                this.mTextView.setText(routeInfo.getName().toUpperCase());
                this.mGroupVolumeSlider.setColor(MediaRouteCastDialog.this.mVolumeSliderColor);
                this.mGroupVolumeSlider.setTag(routeInfo);
                this.mGroupVolumeSlider.setProgress(MediaRouteCastDialog.this.mRoute.getVolume());
                this.mGroupVolumeSlider.setOnSeekBarChangeListener(MediaRouteCastDialog.this.mVolumeChangeListener);
            }
        }

        private class HeaderViewHolder extends ViewHolder {
            TextView mTextView;

            HeaderViewHolder(View view) {
                super(view);
                this.mTextView = (TextView) view.findViewById(R.id.mr_dialog_header_name);
            }

            public void bindHeaderViewHolder(Item item) {
                this.mTextView.setText(item.getData().toString().toUpperCase());
            }
        }

        private class RouteViewHolder extends ViewHolder {
            CheckBox mCheckBox;
            ImageView mImageView;
            TextView mTextView;
            MediaRouteVolumeSlider mVolumeSlider;

            RouteViewHolder(View view) {
                super(view);
                this.mImageView = (ImageView) view.findViewById(R.id.mr_cast_route_icon);
                this.mTextView = (TextView) view.findViewById(R.id.mr_cast_route_name);
                this.mCheckBox = (CheckBox) view.findViewById(R.id.mr_cast_checkbox);
                this.mVolumeSlider = (MediaRouteVolumeSlider) view.findViewById(R.id.mr_cast_volume_slider);
            }

            public void bindRouteViewHolder(Item item) {
                RouteInfo routeInfo = (RouteInfo) item.getData();
                this.mImageView.setImageDrawable(RecyclerAdapter.this.getIconDrawable(routeInfo));
                this.mTextView.setText(routeInfo.getName());
                this.mCheckBox.setChecked(RecyclerAdapter.this.isSelectedRoute(routeInfo));
                this.mVolumeSlider.setColor(MediaRouteCastDialog.this.mVolumeSliderColor);
                this.mVolumeSlider.setTag(routeInfo);
                this.mVolumeSlider.setProgress(routeInfo.getVolume());
                this.mVolumeSlider.setOnSeekBarChangeListener(MediaRouteCastDialog.this.mVolumeChangeListener);
            }
        }

        RecyclerAdapter() {
            this.mInflater = LayoutInflater.from(MediaRouteCastDialog.this.mContext);
            this.mDefaultIcon = MediaRouterThemeHelper.getDefaultDrawableIcon(MediaRouteCastDialog.this.mContext);
            this.mTvIcon = MediaRouterThemeHelper.getTvDrawableIcon(MediaRouteCastDialog.this.mContext);
            this.mSpeakerIcon = MediaRouterThemeHelper.getSpeakerDrawableIcon(MediaRouteCastDialog.this.mContext);
            this.mSpeakerGroupIcon = MediaRouterThemeHelper.getSpeakerGropuIcon(MediaRouteCastDialog.this.mContext);
            setItems();
        }

        boolean isSelectedRoute(RouteInfo routeInfo) {
            if (routeInfo.isSelected()) {
                return true;
            }
            if (MediaRouteCastDialog.this.mRoute instanceof RouteGroup) {
                for (RouteInfo id : ((RouteGroup) MediaRouteCastDialog.this.mRoute).getRoutes()) {
                    if (id.getId().equals(routeInfo.getId())) {
                        return true;
                    }
                }
            }
            return null;
        }

        void setItems() {
            Iterator it;
            this.mItems.clear();
            if (MediaRouteCastDialog.this.mRoute instanceof RouteGroup) {
                this.mItems.add(new Item(MediaRouteCastDialog.this.mRoute, 1));
                for (RouteInfo item : ((RouteGroup) MediaRouteCastDialog.this.mRoute).getRoutes()) {
                    this.mItems.add(new Item(item, 3));
                }
            } else {
                this.mItems.add(new Item(MediaRouteCastDialog.this.mRoute, 3));
            }
            this.mAvailableRoutes.clear();
            this.mAvailableGroups.clear();
            for (RouteInfo item2 : MediaRouteCastDialog.this.mRoutes) {
                if (!isSelectedRoute(item2)) {
                    if (item2 instanceof RouteGroup) {
                        this.mAvailableGroups.add(item2);
                    } else {
                        this.mAvailableRoutes.add(item2);
                    }
                }
            }
            if (this.mAvailableRoutes.size() > 0) {
                this.mItems.add(new Item(MediaRouteCastDialog.this.mContext.getString(R.string.mr_dialog_device_header), 2));
                it = this.mAvailableRoutes.iterator();
                while (it.hasNext()) {
                    this.mItems.add(new Item((RouteInfo) it.next(), 3));
                }
            }
            if (this.mAvailableGroups.size() > 0) {
                this.mItems.add(new Item(MediaRouteCastDialog.this.mContext.getString(R.string.mr_dialog_route_header), 2));
                it = this.mAvailableGroups.iterator();
                while (it.hasNext()) {
                    this.mItems.add(new Item((RouteInfo) it.next(), 4));
                }
            }
            notifyDataSetChanged();
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            switch (i) {
                case 1:
                    return new GroupVolumeViewHolder(this.mInflater.inflate(R.layout.mr_cast_group_volume_item, viewGroup, false));
                case 2:
                    return new HeaderViewHolder(this.mInflater.inflate(R.layout.mr_dialog_header_item, viewGroup, false));
                case 3:
                    return new RouteViewHolder(this.mInflater.inflate(R.layout.mr_cast_route_item, viewGroup, false));
                case 4:
                    return new GroupViewHolder(this.mInflater.inflate(R.layout.mr_cast_group_item, viewGroup, false));
                default:
                    Log.w(TAG, "Cannot create ViewHolder because of wrong view type");
                    return null;
            }
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            int itemViewType = getItemViewType(i);
            i = getItem(i);
            switch (itemViewType) {
                case 1:
                    ((GroupVolumeViewHolder) viewHolder).bindGroupVolumeView(i);
                    return;
                case 2:
                    ((HeaderViewHolder) viewHolder).bindHeaderViewHolder(i);
                    return;
                case 3:
                    ((RouteViewHolder) viewHolder).bindRouteViewHolder(i);
                    return;
                case 4:
                    ((GroupViewHolder) viewHolder).bindGroupViewHolder(i);
                    return;
                default:
                    Log.w(TAG, "Cannot bind item to ViewHolder because of wrong view type");
                    return;
            }
        }

        public int getItemCount() {
            return this.mItems.size();
        }

        Drawable getIconDrawable(RouteInfo routeInfo) {
            Uri iconUri = routeInfo.getIconUri();
            if (iconUri != null) {
                try {
                    Drawable createFromStream = Drawable.createFromStream(MediaRouteCastDialog.this.mContext.getContentResolver().openInputStream(iconUri), null);
                    if (createFromStream != null) {
                        return createFromStream;
                    }
                } catch (Throwable e) {
                    String str = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to load ");
                    stringBuilder.append(iconUri);
                    Log.w(str, stringBuilder.toString(), e);
                }
            }
            return getDefaultIconDrawable(routeInfo);
        }

        private Drawable getDefaultIconDrawable(RouteInfo routeInfo) {
            switch (routeInfo.getDeviceType()) {
                case 1:
                    return this.mTvIcon;
                case 2:
                    return this.mSpeakerIcon;
                default:
                    if ((routeInfo instanceof RouteGroup) != null) {
                        return this.mSpeakerGroupIcon;
                    }
                    return this.mDefaultIcon;
            }
        }

        public int getItemViewType(int i) {
            return ((Item) this.mItems.get(i)).getType();
        }

        public Item getItem(int i) {
            return (Item) this.mItems.get(i);
        }
    }

    public MediaRouteCastDialog(Context context) {
        this(context, 0);
    }

    public MediaRouteCastDialog(Context context, int i) {
        context = MediaRouterThemeHelper.createThemedDialogContext(context, i, false);
        super(context, MediaRouterThemeHelper.createThemedDialogStyle(context));
        this.mSelector = MediaRouteSelector.EMPTY;
        this.mRoutes = new ArrayList();
        this.mHandler = new Handler() {
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    MediaRouteCastDialog.this.updateRoutes((List) message.obj);
                }
            }
        };
        this.mContext = getContext();
        this.mRouter = MediaRouter.getInstance(this.mContext);
        this.mCallback = new MediaRouterCallback();
        this.mRoute = this.mRouter.getSelectedRoute();
        this.mControllerCallback = new MediaControllerCallback();
        setMediaSession(this.mRouter.getMediaSessionToken());
    }

    private void setMediaSession(Token token) {
        MediaDescriptionCompat mediaDescriptionCompat = null;
        if (this.mMediaController != null) {
            this.mMediaController.unregisterCallback(this.mControllerCallback);
            this.mMediaController = null;
        }
        if (token != null && this.mAttachedToWindow) {
            try {
                this.mMediaController = new MediaControllerCompat(this.mContext, token);
            } catch (Token token2) {
                Log.e(TAG, "Error creating media controller in setMediaSession.", token2);
            }
            if (this.mMediaController != null) {
                this.mMediaController.registerCallback(this.mControllerCallback);
            }
            if (this.mMediaController == null) {
                token2 = null;
            } else {
                token2 = this.mMediaController.getMetadata();
            }
            if (token2 != null) {
                mediaDescriptionCompat = token2.getDescription();
            }
            this.mDescription = mediaDescriptionCompat;
            updateArtIconIfNeeded();
            update();
        }
    }

    public Token getMediaSession() {
        return this.mMediaController == null ? null : this.mMediaController.getSessionToken();
    }

    @NonNull
    public MediaRouteSelector getRouteSelector() {
        return this.mSelector;
    }

    public void setRouteSelector(@NonNull MediaRouteSelector mediaRouteSelector) {
        if (mediaRouteSelector == null) {
            throw new IllegalArgumentException("selector must not be null");
        } else if (!this.mSelector.equals(mediaRouteSelector)) {
            this.mSelector = mediaRouteSelector;
            if (this.mAttachedToWindow) {
                this.mRouter.removeCallback(this.mCallback);
                this.mRouter.addCallback(mediaRouteSelector, this.mCallback, 1);
            }
            refreshRoutes();
        }
    }

    public void onFilterRoutes(@NonNull List<RouteInfo> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            if (!onFilterRoute((RouteInfo) list.get(size))) {
                list.remove(size);
            }
        }
    }

    public boolean onFilterRoute(@NonNull RouteInfo routeInfo) {
        return (routeInfo.isDefaultOrBluetooth() || !routeInfo.isEnabled() || routeInfo.matchesSelector(this.mSelector) == null) ? null : true;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.mr_cast_dialog);
        this.mCloseButton = (ImageButton) findViewById(R.id.mr_cast_close_button);
        this.mCloseButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MediaRouteCastDialog.this.dismiss();
            }
        });
        this.mStopCastingButton = (Button) findViewById(R.id.mr_cast_stop_button);
        this.mStopCastingButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MediaRouteCastDialog.this.mRoute.isSelected() != null) {
                    MediaRouteCastDialog.this.mRouter.unselect(2);
                }
                MediaRouteCastDialog.this.dismiss();
            }
        });
        this.mAdapter = new RecyclerAdapter();
        this.mRecyclerView = (RecyclerView) findViewById(R.id.mr_cast_list);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mVolumeChangeListener = new VolumeChangeListener();
        this.mVolumeSliderColor = MediaRouterThemeHelper.getControllerColor(this.mContext, 0);
        this.mMetadataLayout = (RelativeLayout) findViewById(R.id.mr_cast_meta);
        this.mArtView = (ImageView) findViewById(R.id.mr_cast_meta_art);
        this.mTitleView = (TextView) findViewById(R.id.mr_cast_meta_title);
        this.mSubtitleView = (TextView) findViewById(R.id.mr_cast_meta_subtitle);
        this.mTitlePlaceholder = this.mContext.getResources().getString(R.string.mr_cast_dialog_title_view_placeholder);
        this.mCreated = true;
        updateLayout();
    }

    void updateLayout() {
        getWindow().setLayout(-1, -1);
        this.mArtIconBitmap = null;
        this.mArtIconUri = null;
        updateArtIconIfNeeded();
        update();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(this.mSelector, this.mCallback, 1);
        refreshRoutes();
        setMediaSession(this.mRouter.getMediaSessionToken());
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
        this.mHandler.removeMessages(1);
        setMediaSession(null);
    }

    void update() {
        if (this.mRoute.isSelected()) {
            if (!this.mRoute.isDefaultOrBluetooth()) {
                if (this.mCreated) {
                    if (this.mArtIconIsLoaded) {
                        if (isBitmapRecycled(this.mArtIconLoadedBitmap)) {
                            this.mArtView.setVisibility(8);
                            String str = TAG;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Can't set artwork image with recycled bitmap: ");
                            stringBuilder.append(this.mArtIconLoadedBitmap);
                            Log.w(str, stringBuilder.toString());
                        } else {
                            this.mArtView.setVisibility(0);
                            this.mArtView.setImageBitmap(this.mArtIconLoadedBitmap);
                            this.mArtView.setBackgroundColor(this.mArtIconBackgroundColor);
                            this.mMetadataLayout.setBackgroundDrawable(new BitmapDrawable(this.mArtIconLoadedBitmap));
                        }
                        clearLoadedBitmap();
                    } else {
                        this.mArtView.setVisibility(8);
                    }
                    updateMetadataLayout();
                    return;
                }
                return;
            }
        }
        dismiss();
    }

    static boolean isBitmapRecycled(Bitmap bitmap) {
        return (bitmap == null || bitmap.isRecycled() == null) ? null : true;
    }

    int getDesiredArtHeight(int i, int i2) {
        return this.mArtView.getHeight();
    }

    void updateArtIconIfNeeded() {
        if (isIconChanged()) {
            if (this.mFetchArtTask != null) {
                this.mFetchArtTask.cancel(true);
            }
            this.mFetchArtTask = new FetchArtTask();
            this.mFetchArtTask.execute(new Void[0]);
        }
    }

    void clearLoadedBitmap() {
        this.mArtIconIsLoaded = false;
        this.mArtIconLoadedBitmap = null;
        this.mArtIconBackgroundColor = 0;
    }

    private boolean isIconChanged() {
        Object obj = null;
        Bitmap iconBitmap = this.mDescription == null ? null : this.mDescription.getIconBitmap();
        if (this.mDescription != null) {
            obj = this.mDescription.getIconUri();
        }
        Bitmap iconBitmap2 = this.mFetchArtTask == null ? this.mArtIconBitmap : this.mFetchArtTask.getIconBitmap();
        Object iconUri = this.mFetchArtTask == null ? this.mArtIconUri : this.mFetchArtTask.getIconUri();
        if (iconBitmap2 != iconBitmap) {
            return true;
        }
        if (iconBitmap2 == null && ObjectsCompat.equals(iconUri, r1)) {
            return true;
        }
        return false;
    }

    private void updateMetadataLayout() {
        CharSequence charSequence = null;
        CharSequence title = this.mDescription == null ? null : this.mDescription.getTitle();
        int isEmpty = TextUtils.isEmpty(title) ^ 1;
        if (this.mDescription != null) {
            charSequence = this.mDescription.getSubtitle();
        }
        int isEmpty2 = TextUtils.isEmpty(charSequence) ^ 1;
        if (isEmpty != 0) {
            this.mTitleView.setText(title);
        } else {
            this.mTitleView.setText(this.mTitlePlaceholder);
        }
        if (isEmpty2 != 0) {
            this.mSubtitleView.setText(charSequence);
            this.mSubtitleView.setVisibility(0);
            return;
        }
        this.mSubtitleView.setVisibility(8);
    }

    public void refreshRoutes() {
        if (this.mAttachedToWindow) {
            List arrayList = new ArrayList(this.mRouter.getRoutes());
            onFilterRoutes(arrayList);
            Collections.sort(arrayList, RouteComparator.sInstance);
            if (SystemClock.uptimeMillis() - this.mLastUpdateTime >= UPDATE_ROUTES_DELAY_MS) {
                updateRoutes(arrayList);
                return;
            }
            this.mHandler.removeMessages(1);
            this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(1, arrayList), this.mLastUpdateTime + UPDATE_ROUTES_DELAY_MS);
        }
    }

    void updateRoutes(List<RouteInfo> list) {
        this.mLastUpdateTime = SystemClock.uptimeMillis();
        this.mRoutes.clear();
        this.mRoutes.addAll(list);
        this.mAdapter.setItems();
    }
}
