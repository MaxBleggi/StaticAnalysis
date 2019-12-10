package com.xumo.xumo.fragmenttv;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.xumo.xumo.model.Category;
import com.xumo.xumo.model.Channel;
import com.xumo.xumo.model.OnNowLive;
import com.xumo.xumo.model.VideoAsset;
import com.xumo.xumo.model.XumoDataSync;
import com.xumo.xumo.repository.UserPreferences;
import com.xumo.xumo.service.XumoWebService;
import com.xumo.xumo.service.XumoWebService.Listener;
import com.xumo.xumo.tv.R;
import com.xumo.xumo.util.LogUtil;
import com.xumo.xumo.util.XumoImageUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

public class TvBrandPageFragment extends BaseFragment {
    private static final int FINISH = 2;
    private static final int GETTVBRANDPAGESTATE = 3;
    public static final String TAG_BRAND_PAGE = "com.xumo.xumo.fragmenttv.TvBrandPageFragment";
    private static final int UPDATE_DISPLAY = 1;
    private static final int UPDATE_INTERVAL = 10000;
    private static PageListener mPageListener;
    private Channel channel;
    private String channelId;
    @SuppressLint({"HandlerLeak"})
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    TvBrandPageFragment.this.onUpdateDisplay();
                    return;
                case 2:
                    TvBrandPageFragment.this.finish(TvBrandPageFragment.mPageListener);
                    return;
                case 3:
                    if (TvBrandPageFragment.mPageListener != null) {
                        TvBrandPageFragment.mPageListener.getTvBrandPageState();
                        TvBrandPageFragment.this.setTvBrandPageState();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private boolean isSelectLike = false;
    private ArrayList<Category> mCategoryList = new ArrayList();
    private CategoryListAdapter mCategoryListAdapter;
    private RecyclerView mCategoryRv;
    private ContentChildListAdapter mContentChildListAdapter;
    private ImageView mDownIv;
    private ArrayList<String> mGenreList = new ArrayList();
    private int mHeadIndex = 1;
    private int mLeftMoveIndex = 0;
    private int mLeftSelectIndex = 0;
    private ImageView mLikeIv;
    private LinearLayout mLikeLy;
    private TextView mLikeTv;
    private OnNowLive mOnNowLive = null;
    private List<Integer> mRightSelectList = new ArrayList();
    private Timer mTimer = new Timer();
    private TimerTask mTimerTask = new TimerTask() {
        public void run() {
            TvBrandPageFragment.this.handler.sendEmptyMessage(1);
        }
    };
    private ImageView mTitleIv;
    private ImageView mUpIv;
    private VideoAssetListAdapter mVideoAssetListAdapter;
    private RecyclerView mVideoAssetRv;
    private Map<String, ArrayList<VideoAsset>> map = new HashMap();
    private boolean onNowPlaying = false;
    private String videoAssetId;

    interface PageListener {
        void getTvBrandPageState();

        void onFavoriteStateChanged(String str, boolean z);

        void onPlayOnNowLive(String str);

        void onPlayVideoAsset(Channel channel, VideoAsset videoAsset);

        void onPressKeyBackInBrandPage();
    }

    private class CategoryListAdapter extends Adapter<ViewHolder> {
        private ArrayList<String> data;
        private int moveItemIndex;
        private int selectItemIndex;

        class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            View titleSelectedView;
            TextView titleTv;

            ViewHolder(View view) {
                super(view);
                this.titleSelectedView = view.findViewById(R.id.channels_title_selected_view);
                this.titleTv = (TextView) view.findViewById(R.id.channels_title_tv);
            }
        }

        private CategoryListAdapter() {
            this.moveItemIndex = 0;
            this.selectItemIndex = 0;
        }

        private void setMoveItemIndex(int i) {
            this.moveItemIndex = i;
        }

        private void setSelectItemIndex(int i) {
            this.selectItemIndex = i;
        }

        private void setData(ArrayList<String> arrayList) {
            this.data = arrayList;
            notifyDataSetChanged();
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(TvBrandPageFragment.this.getContext()).inflate(R.layout.tv_list_item_channels_title, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            if (i == this.moveItemIndex) {
                viewHolder.titleSelectedView.setVisibility(0);
                viewHolder.titleTv.setTypeface(Typeface.createFromAsset(TvBrandPageFragment.this.getContext().getAssets(), "fonts/OpenSans-Bold.ttf"));
                viewHolder.titleTv.setTextSize(15.0f);
                viewHolder.titleTv.setTextColor(-1);
            } else {
                viewHolder.titleSelectedView.setVisibility(8);
                viewHolder.titleTv.setTypeface(Typeface.createFromAsset(TvBrandPageFragment.this.getContext().getAssets(), "fonts/OpenSans-Semibold.ttf"));
                viewHolder.titleTv.setTextSize(13.0f);
                viewHolder.titleTv.setTextColor(TvBrandPageFragment.this.getResources().getColor(R.color.brand_page_gray));
            }
            if (i == this.selectItemIndex) {
                viewHolder.titleTv.setTextColor(TvBrandPageFragment.this.getResources().getColor(R.color.xumoBlue));
            } else {
                viewHolder.titleTv.setTextColor(-1);
            }
            viewHolder.titleTv.setText((CharSequence) this.data.get(i));
        }

        public int getItemCount() {
            return this.data == null ? 0 : this.data.size();
        }
    }

    private class ContentChildListAdapter extends Adapter<ViewHolder> {
        private String category;
        private ArrayList<VideoAsset> data;
        private boolean flag;
        private boolean loadMoreFlag;
        private int selectItemIndex;

        class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            LinearLayout backLy;
            LinearLayout bottomLy;
            TextView contentTv;
            TextView detailTimeTv;
            LinearLayout fatherLy;
            LinearLayout haveContentLy;
            ImageView noContentIv;
            RelativeLayout noContentRl;
            TextView noContentTv;
            TextView nowPlayingTv;
            ImageView pictureIv;
            TextView timeTv;
            TextView titleTv;

            ViewHolder(View view) {
                super(view);
                this.fatherLy = (LinearLayout) view.findViewById(R.id.father_ly);
                this.backLy = (LinearLayout) view.findViewById(R.id.back_ly);
                this.haveContentLy = (LinearLayout) view.findViewById(R.id.have_content_ly);
                this.pictureIv = (ImageView) view.findViewById(R.id.picture_iv);
                this.nowPlayingTv = (TextView) view.findViewById(R.id.now_playing_tv);
                this.detailTimeTv = (TextView) view.findViewById(R.id.detail_time_tv);
                this.bottomLy = (LinearLayout) view.findViewById(R.id.bottom_ly);
                this.titleTv = (TextView) view.findViewById(R.id.title_tv);
                this.contentTv = (TextView) view.findViewById(R.id.content_tv);
                this.timeTv = (TextView) view.findViewById(R.id.time_tv);
                this.noContentRl = (RelativeLayout) view.findViewById(R.id.no_content_rl);
                this.noContentIv = (ImageView) view.findViewById(R.id.no_content_iv);
                this.noContentTv = (TextView) view.findViewById(R.id.no_content_tv);
            }
        }

        private ContentChildListAdapter() {
            this.selectItemIndex = -1;
            this.loadMoreFlag = null;
        }

        private void setSelectItemIndex(int i) {
            this.selectItemIndex = i;
        }

        private void setFlag(boolean z) {
            this.flag = z;
        }

        public void setLoadMoreFlag(boolean z) {
            this.loadMoreFlag = z;
        }

        private void setData(String str, ArrayList<VideoAsset> arrayList) {
            this.category = str;
            this.data = arrayList;
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(TvBrandPageFragment.this.getContext()).inflate(R.layout.tv_list_item_brand_page_content_child, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            if (((VideoAsset) this.data.get(i)).getAssetId().isEmpty()) {
                viewHolder.haveContentLy.setVisibility(8);
                viewHolder.noContentRl.setVisibility(0);
                viewHolder.noContentTv.setText(R.string.load_more_videos);
                viewHolder.noContentIv.setImageResource(R.drawable.icon_loading_3);
                if (i == 0 || this.loadMoreFlag) {
                    viewHolder.noContentTv.setText(R.string.loading_now);
                    viewHolder.noContentIv.setImageResource(R.drawable.icon_loading_1);
                    viewHolder.noContentIv.startAnimation(AnimationUtils.loadAnimation(TvBrandPageFragment.this.getContext(), R.anim.loading_rotate_001));
                }
            } else {
                viewHolder.haveContentLy.setVisibility(0);
                viewHolder.noContentRl.setVisibility(8);
            }
            XumoImageUtil.setAssetThumbnailImage(TvBrandPageFragment.this.getContext(), ((VideoAsset) this.data.get(i)).getAssetId(), ((VideoAsset) this.data.get(i)).getChannelId(), viewHolder.pictureIv, new int[0]);
            if (((VideoAsset) this.data.get(i)).isNowPlaying()) {
                viewHolder.nowPlayingTv.setVisibility(0);
            } else {
                viewHolder.nowPlayingTv.setVisibility(8);
            }
            viewHolder.detailTimeTv.setText(((VideoAsset) this.data.get(i)).getRunTimeString());
            viewHolder.titleTv.setText(this.category);
            viewHolder.contentTv.setText(((VideoAsset) this.data.get(i)).getTitle());
            viewHolder.timeTv.setText(((VideoAsset) this.data.get(i)).getVideoAssetAge(TvBrandPageFragment.this.getContext()));
            if (!this.flag) {
                viewHolder.backLy.setBackgroundColor(0);
                viewHolder.bottomLy.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                viewHolder.titleTv.setTextColor(TvBrandPageFragment.this.getResources().getColor(R.color.xumoGray));
                viewHolder.contentTv.setTextColor(-1);
                viewHolder.timeTv.setTextColor(-1);
            } else if (i == this.selectItemIndex) {
                viewHolder.backLy.setBackgroundColor(TvBrandPageFragment.this.getResources().getColor(R.color.xumoBlue));
                viewHolder.bottomLy.setBackgroundColor(-1);
                viewHolder.titleTv.setTextColor(TvBrandPageFragment.this.getResources().getColor(R.color.xumoBlue));
                viewHolder.contentTv.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                viewHolder.timeTv.setTextColor(TvBrandPageFragment.this.getResources().getColor(R.color.xumoGray));
                viewHolder.noContentIv.setImageResource(R.drawable.icon_loading_1);
            } else {
                viewHolder.backLy.setBackgroundColor(0);
                viewHolder.bottomLy.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                viewHolder.titleTv.setTextColor(TvBrandPageFragment.this.getResources().getColor(R.color.xumoGray));
                viewHolder.contentTv.setTextColor(-1);
                viewHolder.timeTv.setTextColor(-1);
            }
        }

        public void onViewRecycled(@NonNull ViewHolder viewHolder) {
            ImageContainer imageContainer = (ImageContainer) viewHolder.pictureIv.getTag();
            if (imageContainer != null) {
                imageContainer.cancelRequest();
            }
            viewHolder.pictureIv.setImageDrawable(null);
        }

        public int getItemCount() {
            return this.data == null ? 0 : this.data.size();
        }
    }

    private class VideoAssetListAdapter extends Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {
        private ArrayList<String> data;
        private OnNowLive onNowLive;
        private int selectChildItemIndex;
        private int selectItemIndex;

        class HeadViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            LinearLayout backBottomLy;
            LinearLayout backLy;
            View leftView;
            TextView onNowChannelTv;
            TextView onNowContentTv;
            ImageView onNowIv;
            TextView onNowPlayingTv;
            TextView onNowTimeTv;
            TextView onNowTitleTv;
            View rightView;
            TextView upNextChannelTv;
            TextView upNextContentTv;
            ImageView upNextIv;
            TextView upNextTimeTv;
            TextView upNextTitleTv;

            HeadViewHolder(View view) {
                super(view);
                this.backLy = (LinearLayout) view.findViewById(R.id.back_ly);
                this.backBottomLy = (LinearLayout) view.findViewById(R.id.back_bottom_ly);
                this.onNowPlayingTv = (TextView) view.findViewById(R.id.on_now_playing_tv);
                this.onNowIv = (ImageView) view.findViewById(R.id.on_now_iv);
                this.onNowTimeTv = (TextView) view.findViewById(R.id.on_now_time_tv);
                this.onNowChannelTv = (TextView) view.findViewById(R.id.on_now_channel_tv);
                this.onNowTitleTv = (TextView) view.findViewById(R.id.on_now_title_tv);
                this.onNowContentTv = (TextView) view.findViewById(R.id.on_now_content_tv);
                this.upNextIv = (ImageView) view.findViewById(R.id.up_next_iv);
                this.upNextTimeTv = (TextView) view.findViewById(R.id.up_next_time_tv);
                this.upNextChannelTv = (TextView) view.findViewById(R.id.up_next_channel_tv);
                this.upNextTitleTv = (TextView) view.findViewById(R.id.up_next_title_tv);
                this.upNextContentTv = (TextView) view.findViewById(R.id.up_next_content_tv);
                this.leftView = view.findViewById(R.id.left_view);
                this.rightView = view.findViewById(R.id.right_view);
            }
        }

        class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            TextView numberTv;
            RecyclerView videoAssetDetailRv;
            TextView videoAssetNameTv;

            ViewHolder(View view) {
                super(view);
                this.videoAssetNameTv = (TextView) view.findViewById(R.id.video_asset_name_tv);
                this.numberTv = (TextView) view.findViewById(R.id.number_tv);
                this.videoAssetDetailRv = (RecyclerView) view.findViewById(R.id.video_asset_detail_rv);
            }
        }

        public int getItemViewType(int i) {
            return i;
        }

        private VideoAssetListAdapter() {
            this.selectItemIndex = -1;
            this.selectChildItemIndex = -1;
        }

        private void setSelectItemIndex(int i) {
            this.selectItemIndex = i;
        }

        private void setSelectChildItemIndex(int i) {
            this.selectChildItemIndex = i;
        }

        private void setData(ArrayList<String> arrayList) {
            this.data = arrayList;
            notifyDataSetChanged();
        }

        private void setOnNowLive(OnNowLive onNowLive) {
            this.onNowLive = onNowLive;
            notifyDataSetChanged();
        }

        @NonNull
        public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if (i == 0) {
                return new HeadViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tv_list_item_video_asset_head, viewGroup, false));
            }
            return new ViewHolder(LayoutInflater.from(TvBrandPageFragment.this.getContext()).inflate(R.layout.tv_list_item_video_asset_detail, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder instanceof HeadViewHolder) {
                HeadViewHolder headViewHolder = (HeadViewHolder) viewHolder;
                if (TvBrandPageFragment.this.onNowPlaying) {
                    headViewHolder.onNowPlayingTv.setVisibility(0);
                } else {
                    headViewHolder.onNowPlayingTv.setVisibility(8);
                }
                if (this.onNowLive != null) {
                    XumoImageUtil.setAssetThumbnailImage(TvBrandPageFragment.this.getContext(), this.onNowLive.getId(), this.onNowLive.getChannelId(), headViewHolder.onNowIv, 320, 180);
                    headViewHolder.onNowTimeTv.setText(this.onNowLive.getEndTimeSinceNowString());
                    headViewHolder.leftView.setLayoutParams(new LayoutParams(0, -2, (float) this.onNowLive.getProgress()));
                    headViewHolder.leftView.setBackgroundResource(R.color.selected_on_now_bg);
                    headViewHolder.rightView.setLayoutParams(new LayoutParams(0, -2, (float) (100 - this.onNowLive.getProgress())));
                    headViewHolder.rightView.setBackgroundColor(0);
                    headViewHolder.onNowChannelTv.setText(this.onNowLive.getProgramNumberString());
                    headViewHolder.onNowTitleTv.setText(this.onNowLive.getTitle());
                    headViewHolder.onNowContentTv.setText(this.onNowLive.getDescriptionText());
                    XumoImageUtil.setAssetThumbnailImage(TvBrandPageFragment.this.getContext(), this.onNowLive.getNextId(), this.onNowLive.getChannelId(), headViewHolder.upNextIv, 320, 180);
                    headViewHolder.upNextTimeTv.setText(this.onNowLive.getNextStartTimeSinceNowString());
                    headViewHolder.upNextChannelTv.setText(this.onNowLive.getProgramNumberString());
                    headViewHolder.upNextTitleTv.setText(this.onNowLive.getNextTitle());
                    headViewHolder.upNextContentTv.setText(this.onNowLive.getNextDescriptionText());
                    if (this.selectItemIndex == i) {
                        headViewHolder.backLy.setBackgroundResource(R.drawable.shape_selected_blue);
                        headViewHolder.backBottomLy.setBackgroundResource(R.color.selected_on_now_bg);
                        headViewHolder.leftView.setVisibility(0);
                        headViewHolder.rightView.setVisibility(0);
                        headViewHolder.onNowChannelTv.setTextColor(-1);
                        headViewHolder.onNowTitleTv.setTextColor(-1);
                        headViewHolder.onNowContentTv.setTextColor(-1);
                        return;
                    }
                    headViewHolder.backLy.setBackgroundColor(0);
                    headViewHolder.backBottomLy.setBackgroundResource(R.color.main_menu_background_color);
                    headViewHolder.leftView.setVisibility(8);
                    headViewHolder.rightView.setVisibility(8);
                    headViewHolder.onNowChannelTv.setTextColor(-1);
                    headViewHolder.onNowTitleTv.setTextColor(-1);
                    headViewHolder.onNowContentTv.setTextColor(TvBrandPageFragment.this.getResources().getColor(R.color.xumoGrayFourth));
                    return;
                }
                return;
            }
            ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            viewHolder2.videoAssetNameTv.setText((CharSequence) this.data.get(i));
            if (TvBrandPageFragment.this.map.get(this.data.get(i)) != null) {
                TextView textView = viewHolder2.numberTv;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.selectChildItemIndex + 1);
                stringBuilder.append("|");
                stringBuilder.append(((VideoAsset) ((ArrayList) TvBrandPageFragment.this.map.get(this.data.get(i))).get(0)).getmHits());
                textView.setText(stringBuilder.toString());
            }
            if (this.selectItemIndex == i) {
                viewHolder2.numberTv.setVisibility(0);
                TvBrandPageFragment.this.mContentChildListAdapter = new ContentChildListAdapter();
                TvBrandPageFragment.this.mContentChildListAdapter.setData((String) this.data.get(i), (ArrayList) TvBrandPageFragment.this.map.get(this.data.get(i)));
                TvBrandPageFragment.this.mContentChildListAdapter.setFlag(true);
                TvBrandPageFragment.this.mContentChildListAdapter.setLoadMoreFlag(((VideoAsset) ((ArrayList) TvBrandPageFragment.this.map.get(this.data.get(i))).get(((ArrayList) TvBrandPageFragment.this.map.get(this.data.get(i))).size() - 1)).isLoadMoreFlag());
                TvBrandPageFragment.this.mContentChildListAdapter.setSelectItemIndex(this.selectChildItemIndex);
                viewHolder2.videoAssetDetailRv.setLayoutManager(new LinearLayoutManager(TvBrandPageFragment.this.getContext(), 0, false));
                viewHolder2.videoAssetDetailRv.setAdapter(TvBrandPageFragment.this.mContentChildListAdapter);
                viewHolder2.videoAssetDetailRv.scrollToPosition(this.selectChildItemIndex);
                return;
            }
            viewHolder2.numberTv.setVisibility(8);
            TvBrandPageFragment.this.mContentChildListAdapter = new ContentChildListAdapter();
            TvBrandPageFragment.this.mContentChildListAdapter.setData((String) this.data.get(i), (ArrayList) TvBrandPageFragment.this.map.get(this.data.get(i)));
            TvBrandPageFragment.this.mContentChildListAdapter.setFlag(false);
            TvBrandPageFragment.this.mContentChildListAdapter.setLoadMoreFlag(false);
            TvBrandPageFragment.this.mContentChildListAdapter.setSelectItemIndex(-1);
            viewHolder2.videoAssetDetailRv.setLayoutManager(new LinearLayoutManager(TvBrandPageFragment.this.getContext(), 0, false));
            viewHolder2.videoAssetDetailRv.setAdapter(TvBrandPageFragment.this.mContentChildListAdapter);
            viewHolder2.videoAssetDetailRv.scrollToPosition(0);
        }

        public int getItemCount() {
            return this.data == null ? 0 : this.data.size();
        }
    }

    public void onFinish() {
        finish(null);
    }

    public void setChannelId(String str) {
        this.channel = new Channel(str);
        this.channelId = str;
    }

    public void setVideoAssetId(String str) {
        this.videoAssetId = str;
    }

    public void setOnNowPlaying(boolean z) {
        this.onNowPlaying = z;
    }

    public static TvBrandPageFragment newInstance(PageListener pageListener) {
        if (pageListener != null) {
            mPageListener = pageListener;
        }
        return new TvBrandPageFragment();
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        return layoutInflater.inflate(R.layout.tv_brand_detail_page, viewGroup, false);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mTitleIv = (ImageView) view.findViewById(R.id.title_iv);
        XumoImageUtil.setChannelImage(getContext(), this.channelId, this.mTitleIv, 272, 174);
        this.mLikeLy = (LinearLayout) view.findViewById(R.id.like_ly);
        this.mLikeIv = (ImageView) view.findViewById(R.id.like_iv);
        this.mLikeTv = (TextView) view.findViewById(R.id.like_tv);
        if (UserPreferences.getInstance().isFavorited(this.channelId) != null) {
            this.mLikeLy.setBackgroundResource(R.color.black_30);
            this.mLikeIv.setImageResource(R.drawable.brand_page_like_white);
            this.mLikeTv.setText("FAVORITED");
            this.mLikeTv.setTextColor(-1);
        } else {
            this.mLikeLy.setBackgroundResource(R.color.black_30);
            this.mLikeIv.setImageResource(R.drawable.brand_page_like_gray);
            this.mLikeTv.setText("ADD TO FAVORITE");
            this.mLikeTv.setTextColor(getResources().getColor(R.color.xumoGrayFourth));
        }
        this.mUpIv = (ImageView) view.findViewById(R.id.up_iv);
        this.mDownIv = (ImageView) view.findViewById(R.id.down_iv);
        this.mCategoryRv = (RecyclerView) view.findViewById(R.id.category_rv);
        this.mCategoryList.add(0, null);
        this.mGenreList.add(0, "On Now");
        this.mRightSelectList.add(Integer.valueOf(0));
        this.mCategoryListAdapter = new CategoryListAdapter();
        this.mCategoryRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mCategoryListAdapter.setData(this.mGenreList);
        this.mCategoryRv.setAdapter(this.mCategoryListAdapter);
        this.mVideoAssetRv = (RecyclerView) view.findViewById(R.id.video_asset_rv);
        this.mVideoAssetListAdapter = new VideoAssetListAdapter();
        this.mVideoAssetRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mVideoAssetListAdapter.setData(this.mGenreList);
        this.mVideoAssetRv.setAdapter(this.mVideoAssetListAdapter);
        this.mTimer.schedule(this.mTimerTask, 0, NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
        initTimer();
        getChannelInformation(this.channelId);
    }

    public void onStop() {
        super.onStop();
        this.mTimer.cancel();
        this.mTimerTask.cancel();
        this.handler.removeMessages(2);
        this.handler.removeMessages(3);
    }

    private void onScrolledAllChannelsGenre() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.mCategoryRv.getLayoutManager();
        int findFirstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        int findLastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        if (findFirstCompletelyVisibleItemPosition != 0) {
            this.mUpIv.setVisibility(0);
        } else {
            this.mUpIv.setVisibility(8);
        }
        if (findLastCompletelyVisibleItemPosition != this.mGenreList.size() - 1) {
            this.mDownIv.setVisibility(0);
        } else {
            this.mDownIv.setVisibility(8);
        }
    }

    private void getChannelInformation(String str) {
        XumoWebService.getInstance().getChannelCategories(str, new Listener() {
            public void onCompletion(Object obj) {
                if (TvBrandPageFragment.this.getHost() != null) {
                    Category category;
                    ArrayList arrayList = (ArrayList) obj;
                    TvBrandPageFragment.this.channel.setCategories(arrayList);
                    obj = arrayList.iterator();
                    while (obj.hasNext()) {
                        category = (Category) obj.next();
                        TvBrandPageFragment.this.mGenreList.add(category.getTitle());
                        TvBrandPageFragment.this.mCategoryList.add(category);
                        TvBrandPageFragment.this.mRightSelectList.add(Integer.valueOf(0));
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(new VideoAsset("", "", ""));
                        TvBrandPageFragment.this.map.put(category.getTitle(), arrayList2);
                    }
                    TvBrandPageFragment.this.mCategoryListAdapter.setData(TvBrandPageFragment.this.mGenreList);
                    TvBrandPageFragment.this.mVideoAssetListAdapter.setData(TvBrandPageFragment.this.mGenreList);
                    new Handler().post(new -$$Lambda$TvBrandPageFragment$3$WNwa2baiCMSUNOlszqGsOhOf9H4());
                    obj = TvBrandPageFragment.this.channel.getCategories().iterator();
                    while (obj.hasNext()) {
                        category = (Category) obj.next();
                        XumoWebService.getInstance().getAssetsInCategories(category, new Listener() {
                            public void onCompletion(Object obj) {
                                ArrayList arrayList = (ArrayList) obj;
                                category.setVideoAssets(arrayList);
                                if (TvBrandPageFragment.this.videoAssetId != null && !TvBrandPageFragment.this.videoAssetId.isEmpty()) {
                                    Iterator it = arrayList.iterator();
                                    while (it.hasNext()) {
                                        VideoAsset videoAsset = (VideoAsset) it.next();
                                        if (videoAsset.getAssetId().equals(TvBrandPageFragment.this.videoAssetId)) {
                                            videoAsset.setNowPlaying(true);
                                            break;
                                        }
                                    }
                                }
                                if (arrayList.size() < ((VideoAsset) arrayList.get(0)).getmHits()) {
                                    arrayList.add(new VideoAsset("", "", ""));
                                }
                                TvBrandPageFragment.this.map.put(category.getTitle(), arrayList);
                                TvBrandPageFragment.this.mVideoAssetListAdapter.setData(TvBrandPageFragment.this.mGenreList);
                            }

                            public void onError() {
                                LogUtil.d("getAssetsInCategories getAssetsInCategories failed.");
                            }
                        });
                    }
                }
            }

            public void onError() {
                LogUtil.d("getChannelInformation getChannelInformation failed.");
            }
        });
    }

    private void getMoreVideoAsset(final Category category, final int i) {
        XumoWebService.getInstance().getAssetsInCategories(category, i, new Listener() {
            public void onCompletion(Object obj) {
                Iterator it;
                ((ArrayList) TvBrandPageFragment.this.map.get(category.getTitle())).remove(i);
                ArrayList arrayList = (ArrayList) obj;
                if (TvBrandPageFragment.this.videoAssetId != null && !TvBrandPageFragment.this.videoAssetId.isEmpty()) {
                    it = arrayList.iterator();
                    while (it.hasNext()) {
                        VideoAsset videoAsset = (VideoAsset) it.next();
                        if (videoAsset.getAssetId().equals(TvBrandPageFragment.this.videoAssetId)) {
                            videoAsset.setNowPlaying(true);
                            break;
                        }
                    }
                }
                if (((ArrayList) TvBrandPageFragment.this.map.get(category.getTitle())).size() + arrayList.size() < ((VideoAsset) arrayList.get(0)).getmHits()) {
                    arrayList.add(new VideoAsset("", "", ""));
                }
                it = TvBrandPageFragment.this.channel.getCategories().iterator();
                while (it.hasNext()) {
                    Category category = (Category) it.next();
                    if (category.getTitle().equals(category.getTitle())) {
                        category.getVideoAssets().addAll(arrayList);
                    }
                }
                TvBrandPageFragment.this.mVideoAssetListAdapter.setData(TvBrandPageFragment.this.mGenreList);
            }

            public void onError() {
                LogUtil.d("getAssetsInCategories getAssetsInCategories failed.");
            }
        });
    }

    private void onUpdateDisplay() {
        XumoDataSync.getInstance().getOnNow(this.channelId, new Listener() {
            public void onCompletion(Object obj) {
                if (TvBrandPageFragment.this.getHost() != null) {
                    TvBrandPageFragment.this.mOnNowLive = (OnNowLive) obj;
                    if (TvBrandPageFragment.this.mVideoAssetListAdapter != null) {
                        TvBrandPageFragment.this.mVideoAssetListAdapter.setOnNowLive(TvBrandPageFragment.this.mOnNowLive);
                    }
                }
            }

            public void onError() {
                LogUtil.d("onUpdateDisplay getOnNow failed.");
            }
        });
    }

    private void initTimer() {
        if (mPageListener != null) {
            mPageListener.getTvBrandPageState();
        }
        this.handler.removeMessages(2);
        this.handler.sendEmptyMessageDelayed(2, 30000);
        setTvBrandPageState();
    }

    private void setTvBrandPageState() {
        this.handler.removeMessages(3);
        this.handler.sendEmptyMessageDelayed(3, NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void onKeyDown(int r1, android.view.KeyEvent r2) {
        /*
        r0 = this;
        r0.initTimer();
        r2 = 4;
        if (r1 == r2) goto L_0x0021;
    L_0x0006:
        switch(r1) {
            case 19: goto L_0x001d;
            case 20: goto L_0x0019;
            case 21: goto L_0x0015;
            case 22: goto L_0x0011;
            case 23: goto L_0x000d;
            default: goto L_0x0009;
        };
    L_0x0009:
        switch(r1) {
            case 96: goto L_0x000d;
            case 97: goto L_0x0021;
            default: goto L_0x000c;
        };
    L_0x000c:
        goto L_0x0026;
    L_0x000d:
        r0.dealCenterKey();
        goto L_0x0026;
    L_0x0011:
        r0.dealRightKey();
        goto L_0x0026;
    L_0x0015:
        r0.dealLeftKey();
        goto L_0x0026;
    L_0x0019:
        r0.dealDownKey();
        goto L_0x0026;
    L_0x001d:
        r0.dealUpKey();
        goto L_0x0026;
    L_0x0021:
        r1 = mPageListener;
        r0.finish(r1);
    L_0x0026:
        r1 = new android.os.Handler;
        r1.<init>();
        r2 = new com.xumo.xumo.fragmenttv.-$$Lambda$TvBrandPageFragment$-4f5RlCIRW6Ted9Ay2XyJkd2M7w;
        r2.<init>(r0);
        r1.post(r2);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.fragmenttv.TvBrandPageFragment.onKeyDown(int, android.view.KeyEvent):void");
    }

    private void dealUpKey() {
        if (this.mHeadIndex == 1) {
            if (this.mLeftMoveIndex > 0) {
                this.mLeftMoveIndex--;
            }
            this.mCategoryRv.scrollToPosition(this.mLeftMoveIndex);
            this.mCategoryListAdapter.setMoveItemIndex(this.mLeftMoveIndex);
            this.mCategoryListAdapter.notifyDataSetChanged();
        } else if (this.mHeadIndex != 2) {
        } else {
            if (this.mLeftSelectIndex > 0) {
                this.mLeftSelectIndex--;
                if (this.mLeftSelectIndex == 0) {
                    this.mLikeLy.setVisibility(0);
                }
                this.mCategoryRv.scrollToPosition(this.mLeftSelectIndex);
                this.mCategoryListAdapter.setSelectItemIndex(this.mLeftSelectIndex);
                this.mCategoryListAdapter.notifyDataSetChanged();
                this.mVideoAssetListAdapter.setSelectItemIndex(this.mLeftSelectIndex);
                this.mVideoAssetListAdapter.setSelectChildItemIndex(((Integer) this.mRightSelectList.get(this.mLeftSelectIndex)).intValue());
                this.mVideoAssetListAdapter.notifyDataSetChanged();
                ((LinearLayoutManager) this.mVideoAssetRv.getLayoutManager()).scrollToPositionWithOffset(this.mLeftSelectIndex, 0);
                return;
            }
            this.isSelectLike = true;
            if (UserPreferences.getInstance().isFavorited(this.channelId)) {
                this.mLikeLy.setBackgroundResource(R.drawable.shape_blue_black30);
                this.mLikeIv.setImageResource(R.drawable.brand_page_like_blue);
                this.mLikeTv.setText("FAVORITED");
                this.mLikeTv.setTextColor(getResources().getColor(R.color.xumoBlue));
            } else {
                this.mLikeLy.setBackgroundResource(R.drawable.shape_blue_black30);
                this.mLikeIv.setImageResource(R.drawable.brand_page_like_blue);
                this.mLikeTv.setText("FAVORITE CHANNEL");
                this.mLikeTv.setTextColor(getResources().getColor(R.color.xumoBlue));
            }
            this.mVideoAssetListAdapter.setSelectItemIndex(-1);
            this.mVideoAssetListAdapter.setSelectChildItemIndex(-1);
            this.mVideoAssetListAdapter.notifyDataSetChanged();
        }
    }

    private void dealDownKey() {
        if (this.mHeadIndex == 1) {
            if (this.mLeftMoveIndex < this.mGenreList.size() - 1) {
                this.mLeftMoveIndex++;
            }
            this.mCategoryRv.scrollToPosition(this.mLeftMoveIndex);
            this.mCategoryListAdapter.setMoveItemIndex(this.mLeftMoveIndex);
            this.mCategoryListAdapter.notifyDataSetChanged();
        } else if (this.mHeadIndex != 2) {
        } else {
            if (this.isSelectLike) {
                this.isSelectLike = false;
                if (UserPreferences.getInstance().isFavorited(this.channelId)) {
                    this.mLikeLy.setBackgroundResource(R.color.black_30);
                    this.mLikeIv.setImageResource(R.drawable.brand_page_like_white);
                    this.mLikeTv.setText("FAVORITED");
                    this.mLikeTv.setTextColor(-1);
                } else {
                    this.mLikeLy.setBackgroundResource(R.color.black_30);
                    this.mLikeIv.setImageResource(R.drawable.brand_page_like_gray);
                    this.mLikeTv.setText("ADD TO FAVORITE");
                    this.mLikeTv.setTextColor(getResources().getColor(R.color.xumoGrayFourth));
                }
                this.mVideoAssetListAdapter.setSelectItemIndex(this.mLeftSelectIndex);
                this.mVideoAssetListAdapter.setSelectChildItemIndex(((Integer) this.mRightSelectList.get(this.mLeftSelectIndex)).intValue());
                this.mVideoAssetListAdapter.notifyDataSetChanged();
                return;
            }
            if (this.mLeftSelectIndex < this.mGenreList.size() - 1) {
                this.mLeftSelectIndex++;
            }
            if (this.mLeftSelectIndex > 0) {
                this.mLikeLy.setVisibility(8);
            }
            this.mCategoryRv.scrollToPosition(this.mLeftSelectIndex);
            this.mCategoryListAdapter.setSelectItemIndex(this.mLeftSelectIndex);
            this.mCategoryListAdapter.notifyDataSetChanged();
            this.mVideoAssetListAdapter.setSelectItemIndex(this.mLeftSelectIndex);
            this.mVideoAssetListAdapter.setSelectChildItemIndex(((Integer) this.mRightSelectList.get(this.mLeftSelectIndex)).intValue());
            this.mVideoAssetListAdapter.notifyDataSetChanged();
            ((LinearLayoutManager) this.mVideoAssetRv.getLayoutManager()).scrollToPositionWithOffset(this.mLeftSelectIndex, 0);
        }
    }

    private void dealLeftKey() {
        if (!this.isSelectLike && this.mHeadIndex == 2) {
            if (((Integer) this.mRightSelectList.get(this.mLeftSelectIndex)).intValue() == 0) {
                this.mHeadIndex = 1;
                this.mLeftMoveIndex = this.mLeftSelectIndex;
                this.mCategoryListAdapter.setMoveItemIndex(this.mLeftMoveIndex);
                this.mCategoryListAdapter.setSelectItemIndex(this.mLeftSelectIndex);
                this.mCategoryListAdapter.notifyDataSetChanged();
                this.mVideoAssetListAdapter.setSelectItemIndex(-1);
                this.mVideoAssetListAdapter.notifyDataSetChanged();
                return;
            }
            this.mRightSelectList.set(this.mLeftSelectIndex, Integer.valueOf(((Integer) this.mRightSelectList.get(this.mLeftSelectIndex)).intValue() - 1));
            this.mVideoAssetListAdapter.setSelectItemIndex(this.mLeftSelectIndex);
            this.mVideoAssetListAdapter.setSelectChildItemIndex(((Integer) this.mRightSelectList.get(this.mLeftSelectIndex)).intValue());
            this.mVideoAssetListAdapter.notifyDataSetChanged();
        }
    }

    private void dealRightKey() {
        if (!this.isSelectLike) {
            if (this.mHeadIndex == 1) {
                if (this.mLeftSelectIndex == 0 || (this.map.get(this.mGenreList.get(this.mLeftSelectIndex)) != null && ((ArrayList) this.map.get(this.mGenreList.get(this.mLeftSelectIndex))).size() > 0)) {
                    this.mHeadIndex++;
                }
            } else if (this.mHeadIndex == 2) {
                if (this.mLeftSelectIndex == 0) {
                    this.mRightSelectList.set(this.mLeftSelectIndex, Integer.valueOf(0));
                } else if (((Integer) this.mRightSelectList.get(this.mLeftSelectIndex)).intValue() < ((ArrayList) this.map.get(this.mGenreList.get(this.mLeftSelectIndex))).size() - 1) {
                    this.mRightSelectList.set(this.mLeftSelectIndex, Integer.valueOf(((Integer) this.mRightSelectList.get(this.mLeftSelectIndex)).intValue() + 1));
                }
            }
            this.mCategoryListAdapter.setMoveItemIndex(-1);
            this.mCategoryListAdapter.setSelectItemIndex(this.mLeftSelectIndex);
            this.mCategoryListAdapter.notifyDataSetChanged();
            this.mVideoAssetListAdapter.setSelectItemIndex(this.mLeftSelectIndex);
            this.mVideoAssetListAdapter.setSelectChildItemIndex(((Integer) this.mRightSelectList.get(this.mLeftSelectIndex)).intValue());
            this.mVideoAssetListAdapter.notifyDataSetChanged();
        }
    }

    private void dealCenterKey() {
        if (this.isSelectLike) {
            if (UserPreferences.getInstance().isFavorited(this.channelId)) {
                this.mLikeTv.setText("FAVORITE CHANNEL");
                UserPreferences.getInstance().removeChannelFromFavorites(this.channelId);
            } else {
                this.mLikeTv.setText("FAVORITED");
                UserPreferences.getInstance().addChannelToFavorites(this.channelId);
            }
            if (mPageListener != null) {
                mPageListener.onFavoriteStateChanged(this.channelId, UserPreferences.getInstance().isFavorited(this.channelId));
            }
        } else if (this.mHeadIndex == 1) {
            this.mLeftSelectIndex = this.mLeftMoveIndex;
            if (this.mRightSelectList != null && this.mRightSelectList.size() == this.mGenreList.size()) {
                this.mRightSelectList.set(this.mLeftSelectIndex, Integer.valueOf(0));
            }
            if (this.mLeftSelectIndex == 0) {
                this.mLikeLy.setVisibility(0);
            } else {
                this.mLikeLy.setVisibility(8);
            }
            this.mCategoryListAdapter.setMoveItemIndex(this.mLeftMoveIndex);
            this.mCategoryListAdapter.setSelectItemIndex(this.mLeftSelectIndex);
            this.mCategoryListAdapter.notifyDataSetChanged();
            this.mVideoAssetListAdapter.setSelectItemIndex(-1);
            this.mVideoAssetListAdapter.setSelectChildItemIndex(-1);
            this.mVideoAssetListAdapter.notifyDataSetChanged();
            ((LinearLayoutManager) this.mVideoAssetRv.getLayoutManager()).scrollToPositionWithOffset(this.mLeftSelectIndex, 0);
        } else if (this.mHeadIndex != 2) {
        } else {
            if (this.mLeftSelectIndex == 0) {
                if (mPageListener != null) {
                    mPageListener.onPlayOnNowLive(this.mOnNowLive.getChannelId());
                }
                finish(mPageListener);
                return;
            }
            VideoAsset videoAsset = (VideoAsset) ((ArrayList) this.map.get(this.mGenreList.get(this.mLeftSelectIndex))).get(((Integer) this.mRightSelectList.get(this.mLeftSelectIndex)).intValue());
            if (videoAsset != null && !videoAsset.getAssetId().isEmpty()) {
                if (mPageListener != null) {
                    OnNowPlayerFragment.BRAND_PAGE_CHANNEL_ID = this.channelId;
                    mPageListener.onPlayVideoAsset(this.channel, videoAsset);
                }
                hide();
            } else if (((Integer) this.mRightSelectList.get(this.mLeftSelectIndex)).intValue() != 0) {
                ((VideoAsset) ((ArrayList) this.map.get(this.mGenreList.get(this.mLeftSelectIndex))).get(((ArrayList) this.map.get(this.mGenreList.get(this.mLeftSelectIndex))).size() - 1)).setLoadMoreFlag(true);
                this.mVideoAssetListAdapter.setData(this.mGenreList);
                getMoreVideoAsset((Category) this.mCategoryList.get(this.mLeftSelectIndex), ((Integer) this.mRightSelectList.get(this.mLeftSelectIndex)).intValue());
            }
        }
    }

    private void finish(PageListener pageListener) {
        if (pageListener != null) {
            pageListener.onPressKeyBackInBrandPage();
        }
        pageListener = getFragmentManager();
        Fragment findFragmentByTag = pageListener.findFragmentByTag(TAG_BRAND_PAGE);
        pageListener = pageListener.beginTransaction();
        pageListener.remove(findFragmentByTag);
        pageListener.commitAllowingStateLoss();
    }

    private void hide() {
        this.handler.removeMessages(2);
        this.handler.removeMessages(3);
        FragmentManager fragmentManager = getFragmentManager();
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(TAG_BRAND_PAGE);
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.hide(findFragmentByTag);
        beginTransaction.commitAllowingStateLoss();
    }

    public void show() {
        initTimer();
        if (this.mLeftSelectIndex > 0) {
            for (Entry value : this.map.entrySet()) {
                Iterator it = ((ArrayList) value.getValue()).iterator();
                while (it.hasNext()) {
                    VideoAsset videoAsset = (VideoAsset) it.next();
                    if (videoAsset.getAssetId().equals(this.videoAssetId)) {
                        videoAsset.setNowPlaying(true);
                    } else {
                        videoAsset.setNowPlaying(false);
                    }
                }
            }
            this.mVideoAssetListAdapter.notifyDataSetChanged();
        }
        FragmentManager fragmentManager = getFragmentManager();
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(TAG_BRAND_PAGE);
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.show(findFragmentByTag);
        beginTransaction.commitAllowingStateLoss();
    }
}
