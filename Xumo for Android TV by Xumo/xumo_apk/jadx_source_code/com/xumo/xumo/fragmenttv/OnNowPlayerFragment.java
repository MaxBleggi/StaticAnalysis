package com.xumo.xumo.fragmenttv;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.google.android.exoplayer2.ui.PlayerControlView.VisibilityListener;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.xumo.xumo.BuildConfig;
import com.xumo.xumo.activity.MainTvActivity;
import com.xumo.xumo.application.XumoApplication;
import com.xumo.xumo.model.Category;
import com.xumo.xumo.model.Channel;
import com.xumo.xumo.model.Genre;
import com.xumo.xumo.model.LiveAsset;
import com.xumo.xumo.model.Movies;
import com.xumo.xumo.model.OnNowLive;
import com.xumo.xumo.model.VideoAsset;
import com.xumo.xumo.model.VideoPlaylist;
import com.xumo.xumo.model.XumoDataSync;
import com.xumo.xumo.player.XumoExoPlayer.OnKeyPressListener;
import com.xumo.xumo.player.XumoExoPlayer.XumoTvExoPlayerControllerListener;
import com.xumo.xumo.repository.UserPreferences;
import com.xumo.xumo.service.XumoWebService;
import com.xumo.xumo.service.XumoWebService.Listener;
import com.xumo.xumo.service.XumoWebService.NoResponseListener;
import com.xumo.xumo.tv.R;
import com.xumo.xumo.util.BeaconUtil;
import com.xumo.xumo.util.BeaconUtil.ImpressionBeaconEvent;
import com.xumo.xumo.util.LogUtil;
import com.xumo.xumo.util.XumoImageUtil;
import com.xumo.xumo.util.XumoUtil;
import com.xumo.xumo.widget.ExitDialog;
import com.xumo.xumo.widget.XumoTvRecyclerView;
import com.xumo.xumo.widget.XumoTvRecyclerView.ListAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class OnNowPlayerFragment extends XumoPlayerBaseFragment implements OnKeyPressListener, XumoTvExoPlayerControllerListener, PageListener, PageListener, VisibilityListener, PageListener, OnSharedPreferenceChangeListener {
    private static final int ASSET_THUMBNAIL_HEIGHT = 375;
    private static final int ASSET_THUMBNAIL_WIDTH = 250;
    public static String BRAND_PAGE_CHANNEL_ID = "";
    private static double DOUBLE_CLICK_TIME = 0.0d;
    private static final int FADE_OUT_ANIMATION_DELAY = 30000;
    private static final String GENRE_FAVORITES = "Favorites";
    private static final int GENRE_ID_FAVORITES = -2;
    private static final int GENRE_ID_MOST_POPULAR = -1;
    private static final String GENRE_MOST_POPULAR = "Most Popular";
    private static final int MAX_RELOAD_ON_NOW_LIVES_COUNTS = 15;
    private static final int UPDATE_DISPLAY = 1;
    private static final int UPDATE_INTERVAL = 10000;
    public static String mCurrentChannelId = "";
    public static ArrayList<OnNowLive> mOnNowLives = new ArrayList();
    private DebugFragment debugFragment;
    private RecyclerView genreRecyclerView;
    private TextView genreSelectorTitle;
    private LinearLayout guideOkLinearLayout;
    private ImageView guideSelectGenreImageView;
    private LinearLayout guideSelectGenreLinearLayout;
    private boolean isInputModel = false;
    private boolean isLicenses = false;
    private boolean isResetMainNavigationDisplay = false;
    private ArrayList<Channel> mAllChannels = new ArrayList();
    private AllChannelsContentListAdapter mAllChannelsContentListAdapter;
    private ArrayList<Genre> mAllChannelsGenreList = new ArrayList();
    private AllChannelsListAdapter mAllChannelsListAdapter;
    private LinearLayout mAllChannelsLy;
    private boolean mAnimateOutDetailsStart = false;
    private boolean mAnimateOutStart = false;
    private TextView mAppTv;
    private TextView mAppVersionTv;
    private TextView mBackToChannelInfoTv;
    private boolean mCancelAnimation = false;
    private boolean mCancelAnimationDetails = false;
    private XumoTvRecyclerView mChannelInfoRecyclerView;
    private RelativeLayout mChannelInfoTitle;
    private RecyclerView mChannelsContentRv;
    private RecyclerView mChannelsTitleRv;
    private TextView mClearLocalStorageTv;
    private TextView mClientIdTv;
    private View mControlGuideGenreSelector;
    private View mControlGuideOnNow;
    private LiveAsset mCurrentLiveAsset;
    private OnNowLive mCurrentOnNowLive;
    private int mCurrentOnNowLiveIndex = 0;
    private int mCurrentSelectedStatus = 3;
    private VideoAsset mCurrentVideoAsset = null;
    private TextView mDebugModeCode1Tv;
    private TextView mDebugModeCode2Tv;
    private LinearLayout mDebugModeLy;
    private TextView mDebugModeSeparatorTv;
    private Switch mDefaultSwitch;
    private TextView mDefaultTv;
    private TextView mEnableDebugLogsTv;
    private TextView mEnableStagingPlayersTv;
    private TextView mEnableTestChannelsTv;
    private ExitDialog mExitDialog;
    private ImageView mExoPlayerImage;
    List<String> mFavoritesViewed = new ArrayList();
    List<String> mFeaturedViewed = new ArrayList();
    private ArrayList<Genre> mGenreList = new ArrayList();
    private GenreListAdapter mGenreListAdapter;
    private GenreSelectorListAdapter mGenreSelectorAdapter;
    private XumoTvRecyclerView mGenreSelectorList;
    private View mGenreSelectorListLayout;
    private TextView mGenreTitleTv;
    boolean mGenresAvailable = false;
    private boolean mHasCaption = false;
    private List<Integer> mInputModelKeyList = new ArrayList();
    private List<Integer> mInputModelList = new ArrayList();
    private boolean mIsTransionBrandPage = false;
    private int mItemHigh;
    private TextView mLicensesTv;
    private int mListItemIndexX = -1;
    private int mListItemIndexY = 0;
    List<String> mLivesViewed = new ArrayList();
    private LinearLayout mLlMovies;
    private boolean mLoadAssetFlag = true;
    private MainTvActivity mMainActivity = null;
    private LinearLayout mMainMenuBottomLy;
    private TextView mMainMenuSelectContentTv;
    private ImageView mMainMenuSelectIv;
    private LinearLayout mMainMenuSelectLy;
    private TextView mMainMenuSelectTitleTv;
    private MainNavigationPageSelectState mMainNavigationPageSelectState = MainNavigationPageSelectState.OnNow;
    private LinearLayout mMoreFromBottomLy;
    private int mMoreFromIndex = 0;
    private LinearLayout mMoreFromLy;
    private TextView mMoreFromSelectContentTv;
    private ImageView mMoreFromSelectIv;
    private LinearLayout mMoreFromSelectLy;
    private TextView mMoreFromSelectTitleTv;
    private int mMoveChannelsTitleIndex = 0;
    private Channel mMovieChannel = new Channel(MOVIES_CHANNEL_ID);
    private ArrayList<Movies> mMoviesListInfo;
    private ImageButton mMoviesNextVodButton;
    private ImageButton mMoviesPauseButton;
    private ImageButton mMoviesPlayButton;
    private ImageButton mMoviesPreVodButton;
    private ImageButton mMoviesSkipBackButton;
    private TextView mMoviesSkipBackText;
    private ImageButton mMoviesSkipFwdButton;
    private TextView mMoviesSkipFwdText;
    private int mMoviesVideoAssetListIndex;
    private View mNavigationContainer;
    private ImageView mOnNowCcButton;
    private ImageView mOnNowGenreDown;
    private ImageView mOnNowGenreUp;
    private PlaySourceCategory mPlaySourceCategory = PlaySourceCategory.OnNowLive;
    private int mPlayVodCount = 0;
    private View mPlayerContainerView;
    private String mResumeVideoAssetId;
    private long mResumeVideoAssetStartTime;
    private Button mRlchannel;
    private TextView mRlchannelText;
    private Button mRlexit;
    private TextView mRlexitText;
    private Button mRlmovies;
    private TextView mRlmoviesText;
    private Button mRlonNow;
    private TextView mRlonNowText;
    private Button mRlsearch;
    private TextView mRlsearchText;
    private Button mRlsettings;
    private TextView mRlsettingsText;
    private int mSelectChannelsContentIndex = -1;
    private int mSelectChannelsIndex = 0;
    private SelectChannelsListAdapter mSelectChannelsListAdapter;
    private int mSelectChannelsTitleIndex = 0;
    private int mSettingIndex = 0;
    private LinearLayout mSettingLy;
    private SettingsCaptioningFragment mSettingsCaptioningFragment;
    private boolean mShowOnNowDetailsPrompt = false;
    private boolean mTvConfigEnabled;
    private Handler mUpdateDisplayHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (message.what == 1) {
                OnNowPlayerFragment.this.onUpdateDisplay();
            }
        }
    };
    private UserPreferences mUserPreferences;
    private VideoPlaylist mVideoPlaylist = null;
    private TextView mViewboosterStagingTv;
    private TextView mVodBackToChannelInfoTv;
    private ImageView mVodCcButton;
    private boolean mVodHasCcButton = false;
    private boolean mVodHasNextButton = false;
    private boolean mVodHasPreButton = false;
    private LinearLayout mVodMainMenuBottomLy;
    private TextView mVodMainMenuContentTv;
    private ImageView mVodMainMenuIv;
    private LinearLayout mVodMainMenuLy;
    private TextView mVodMainMenuTitleTv;
    private LinearLayout mVodMoreFromBottomLy;
    private TextView mVodMoreFromContentTv;
    private int mVodMoreFromIndex = -1;
    private ImageView mVodMoreFromIv;
    private LinearLayout mVodMoreFromLy;
    private LinearLayout mVodMoreFromSelectLy;
    private LinearLayout mVodMoreFromTileLy;
    private TextView mVodMoreFromTitleTv;
    private int mVodPlayerControlsSelectedStatus = 3;
    private RelativeLayout mVodRl;
    private LinearLayout mVodUpNextBottomLy;
    private TextView mVodUpNextContentTv;
    private LinearLayout mVodUpNextFatherLy;
    private ImageView mVodUpNextIv;
    private LinearLayout mVodUpNextLy;
    private TextView mVodUpNextTimeTv;
    private TextView mVodUpNextTitleTv;
    private XumoDataSync mXumoDataSync;
    private ImageView mallChannelsGenreDown;
    private ImageView mallChannelsGenreUp;
    private MoviesUpNextPageFragment moviesUpNextPageFragment;
    private LinearLayout noFavoritesView;
    private KeyEvent oldKeyEvent;
    private LinearLayout onNowDetails;
    private TextView onNowDetailsDescription;
    private ImageView onNowDetailsImage;
    private RelativeLayout onNowDetailsInfo;
    private TextView onNowDetailsNumbner;
    private LinearLayout onNowDetailsPrompt;
    private RelativeLayout onNowDetailsRl;
    private TextView onNowDetailsTitle;
    private TextView promptActionText;
    private String reloadChannelId = null;
    private int reloadOnNowLivesCounts = 0;
    private int tabSelectIndex = 0;
    private TvBrandPageFragment tvBrandPageFragment;
    private TextView tvLogo;
    private boolean tvVodPlayEndFlag = false;
    private RelativeLayout vodDetailsInfo;
    private RelativeLayout vodDetailsPrompt;
    private WebFragment webFragment;

    private enum DisplayView {
        Player,
        PlayerControlsPageForLive,
        PlayerControlsPageForVod,
        MoviesUpNextPage,
        MainNavigationPage,
        GenreListPage,
        SettingsCaptioningPage,
        BrandPage,
        WebPage,
        ExitDialog
    }

    private enum MainNavigationPageSelectState {
        NavigationBar,
        OnNow,
        Movies,
        AllChannels,
        Settings
    }

    interface OnGenreSelected {
        void selectGenre(String str);
    }

    private enum PlaySourceCategory {
        OnNowLive,
        MoviesVideo,
        BrandPageVideo
    }

    private class AllChannelsContentListAdapter extends Adapter<ViewHolder> {
        private int selectItemIndex;

        public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            RecyclerView contentRv;
            LinearLayout noFavoritesAllchannels;
            TextView titleTv;

            public ViewHolder(View view) {
                super(view);
                this.titleTv = (TextView) view.findViewById(R.id.title_tv);
                this.contentRv = (RecyclerView) view.findViewById(R.id.video_asset_rv);
                this.noFavoritesAllchannels = (LinearLayout) view.findViewById(R.id.no_favorites_allchannels_view);
            }
        }

        private AllChannelsContentListAdapter() {
            this.selectItemIndex = -1;
        }

        public void setSelectItemIndex(int i) {
            this.selectItemIndex = i;
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(OnNowPlayerFragment.this.getContext()).inflate(R.layout.tv_list_item_channels_content, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.titleTv.setText(((Genre) OnNowPlayerFragment.this.mAllChannelsGenreList.get(i)).getValue());
            if (i == 0 && (((Genre) OnNowPlayerFragment.this.mAllChannelsGenreList.get(i)).getChannelIdList().size() == 0 || ((Genre) OnNowPlayerFragment.this.mAllChannelsGenreList.get(i)).getChannelIdList() == null)) {
                viewHolder.noFavoritesAllchannels.setVisibility(0);
                if (this.selectItemIndex == i) {
                    viewHolder.noFavoritesAllchannels.setBackground(OnNowPlayerFragment.this.getResources().getDrawable(R.drawable.shape_selected_blue));
                } else {
                    viewHolder.noFavoritesAllchannels.setBackground(OnNowPlayerFragment.this.getResources().getDrawable(R.drawable.shape_selected_white));
                }
            } else {
                viewHolder.noFavoritesAllchannels.setVisibility(8);
                if (this.selectItemIndex != i) {
                    viewHolder.noFavoritesAllchannels.setBackground(OnNowPlayerFragment.this.getResources().getDrawable(R.drawable.shape_selected_white));
                }
            }
            if (this.selectItemIndex == i) {
                OnNowPlayerFragment.this.mSelectChannelsListAdapter = new SelectChannelsListAdapter();
                OnNowPlayerFragment.this.mSelectChannelsListAdapter.setData(((Genre) OnNowPlayerFragment.this.mAllChannelsGenreList.get(i)).getChannelIdList());
                OnNowPlayerFragment.this.mSelectChannelsListAdapter.setFlag(true);
                viewHolder.contentRv.setLayoutManager(new GridLayoutManager(OnNowPlayerFragment.this.getContext(), 3));
                viewHolder.contentRv.setAdapter(OnNowPlayerFragment.this.mSelectChannelsListAdapter);
                return;
            }
            OnNowPlayerFragment.this.mSelectChannelsListAdapter = new SelectChannelsListAdapter();
            OnNowPlayerFragment.this.mSelectChannelsListAdapter.setData(((Genre) OnNowPlayerFragment.this.mAllChannelsGenreList.get(i)).getChannelIdList());
            OnNowPlayerFragment.this.mSelectChannelsListAdapter.setFlag(false);
            viewHolder.contentRv.setLayoutManager(new GridLayoutManager(OnNowPlayerFragment.this.getContext(), 3));
            viewHolder.contentRv.setAdapter(OnNowPlayerFragment.this.mSelectChannelsListAdapter);
        }

        public int getItemCount() {
            return OnNowPlayerFragment.this.mAllChannelsGenreList.size();
        }
    }

    private class AllChannelsListAdapter extends Adapter<ViewHolder> {
        private int moveItemIndex;
        private int selectItemIndex;

        public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            View channelsTitleSelectedView;
            TextView channelsTitleTv;

            public ViewHolder(View view) {
                super(view);
                this.channelsTitleSelectedView = view.findViewById(R.id.channels_title_selected_view);
                this.channelsTitleTv = (TextView) view.findViewById(R.id.channels_title_tv);
            }
        }

        private AllChannelsListAdapter() {
            this.moveItemIndex = -1;
            this.selectItemIndex = null;
        }

        public void setMoveItemIndex(int i) {
            this.moveItemIndex = i;
        }

        public void setSelectItemIndex(int i) {
            this.selectItemIndex = i;
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(OnNowPlayerFragment.this.getContext()).inflate(R.layout.tv_list_item_channels_title, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            if (i == this.moveItemIndex) {
                viewHolder.channelsTitleSelectedView.setVisibility(0);
                viewHolder.channelsTitleTv.setTypeface(Typeface.createFromAsset(OnNowPlayerFragment.this.getContext().getAssets(), "fonts/OpenSans-Bold.ttf"));
                viewHolder.channelsTitleTv.setTextSize(15.0f);
            } else {
                viewHolder.channelsTitleSelectedView.setVisibility(8);
                viewHolder.channelsTitleTv.setTypeface(Typeface.createFromAsset(OnNowPlayerFragment.this.getContext().getAssets(), "fonts/OpenSans-Semibold.ttf"));
                viewHolder.channelsTitleTv.setTextSize(13.0f);
            }
            if (i == this.selectItemIndex) {
                viewHolder.channelsTitleTv.setTextColor(OnNowPlayerFragment.this.getResources().getColor(R.color.xumoBlue));
            } else {
                viewHolder.channelsTitleTv.setTextColor(-1);
            }
            viewHolder.channelsTitleTv.setText(((Genre) OnNowPlayerFragment.this.mAllChannelsGenreList.get(i)).getValue());
        }

        public int getItemCount() {
            return OnNowPlayerFragment.this.mAllChannelsGenreList.size();
        }
    }

    private class ChannelListAdapter extends Adapter<ViewHolder> {
        private boolean loadMoreFlag = false;
        private LayoutInflater mLayoutInflater;
        private ArrayList<VideoAsset> mVideoAssetList;

        class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            private ImageView channelLogoImageView;
            private LinearLayout listItemChannel;
            private LinearLayout listItemChannelText;
            private TextView movieLimitLevelAndTime;
            private TextView movieLimitTitle;
            private TextView movieLoadingText;
            private TextView movieRuntime;
            private RelativeLayout moviesBgRl;
            private ImageView noContentIv;
            private RelativeLayout noContentRl;

            private ViewHolder(View view) {
                super(view);
                this.listItemChannel = (LinearLayout) view.findViewById(R.id.list_item_channel_id);
                this.channelLogoImageView = (ImageView) view.findViewById(R.id.channel_logo);
                this.movieRuntime = (TextView) view.findViewById(R.id.movie_runtime);
                this.movieLimitLevelAndTime = (TextView) view.findViewById(R.id.movie_limit_level_time);
                this.movieLimitTitle = (TextView) view.findViewById(R.id.movie_title);
                this.movieLoadingText = (TextView) view.findViewById(R.id.movie_loading_text);
                this.moviesBgRl = (RelativeLayout) view.findViewById(R.id.movies_bg_rl);
                this.listItemChannelText = (LinearLayout) view.findViewById(R.id.list_item_channel_text);
                this.noContentRl = (RelativeLayout) view.findViewById(R.id.no_content_rl);
                this.noContentIv = (ImageView) view.findViewById(R.id.no_content_iv_movies);
            }
        }

        ChannelListAdapter(ArrayList<VideoAsset> arrayList) {
            this.mLayoutInflater = LayoutInflater.from(OnNowPlayerFragment.this.getContext());
            this.mVideoAssetList = arrayList;
        }

        public void setLoadMoreFlag(boolean z) {
            this.loadMoreFlag = z;
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(this.mLayoutInflater.inflate(R.layout.tv_list_item_channel, viewGroup, false));
        }

        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder != null) {
                ImageContainer imageContainer = (ImageContainer) viewHolder.channelLogoImageView.getTag();
                if (imageContainer != null) {
                    imageContainer.cancelRequest();
                }
                viewHolder.channelLogoImageView.setImageDrawable(null);
            }
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (this.mVideoAssetList != null) {
                if (this.mVideoAssetList.size() != 0) {
                    if (((VideoAsset) this.mVideoAssetList.get(i)).getAssetId().isEmpty()) {
                        viewHolder.moviesBgRl.setVisibility(8);
                        viewHolder.noContentRl.setVisibility(0);
                        viewHolder.movieLoadingText.setText(R.string.loading_more_movies);
                        viewHolder.noContentIv.setBackground(OnNowPlayerFragment.this.getResources().getDrawable(R.drawable.icon_loading_3));
                        if (i == 0 || this.loadMoreFlag) {
                            viewHolder.movieLoadingText.setText(R.string.loading_now);
                            viewHolder.noContentIv.setBackground(OnNowPlayerFragment.this.getResources().getDrawable(R.drawable.icon_loading_1));
                            viewHolder.noContentIv.startAnimation(AnimationUtils.loadAnimation(OnNowPlayerFragment.this.getContext(), R.anim.loading_rotate_001));
                        }
                    } else {
                        viewHolder.moviesBgRl.setVisibility(0);
                        viewHolder.noContentRl.setVisibility(8);
                    }
                    if (OnNowPlayerFragment.this.mListItemIndexY > ((VideoAsset) this.mVideoAssetList.get(i)).getAxisY() + 1) {
                        OnNowPlayerFragment.this.mListItemIndexY = ((VideoAsset) this.mVideoAssetList.get(i)).getAxisY() + 1;
                    }
                    ((VideoAsset) this.mVideoAssetList.get(i)).getChannelId();
                    String assetId = ((VideoAsset) this.mVideoAssetList.get(i)).getAssetId();
                    if (((VideoAsset) this.mVideoAssetList.get(i)).getmRatings() != null && ((VideoAsset) this.mVideoAssetList.get(i)).getmRatings() != "" && ((VideoAsset) this.mVideoAssetList.get(i)).getmReleaseYear() > 0) {
                        TextView access$6400 = viewHolder.movieLimitLevelAndTime;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(((VideoAsset) this.mVideoAssetList.get(i)).getmReleaseYear());
                        stringBuilder.append(" | ");
                        stringBuilder.append(((VideoAsset) this.mVideoAssetList.get(i)).getmRatings());
                        access$6400.setText(stringBuilder.toString());
                    } else if (((VideoAsset) this.mVideoAssetList.get(i)).getmRatings() != null && ((VideoAsset) this.mVideoAssetList.get(i)).getmRatings() != "") {
                        viewHolder.movieLimitLevelAndTime.setText(((VideoAsset) this.mVideoAssetList.get(i)).getmRatings());
                    } else if (((VideoAsset) this.mVideoAssetList.get(i)).getmReleaseYear() > 0) {
                        viewHolder.movieLimitLevelAndTime.setText(String.valueOf(((VideoAsset) this.mVideoAssetList.get(i)).getmReleaseYear()));
                    }
                    LayoutParams layoutParams = viewHolder.movieLimitLevelAndTime.getLayoutParams();
                    if (((VideoAsset) this.mVideoAssetList.get(i)).getAxisY() == OnNowPlayerFragment.this.mListItemIndexY && i == OnNowPlayerFragment.this.mListItemIndexX) {
                        viewHolder.listItemChannel.requestFocus();
                        viewHolder.listItemChannel.setBackground(OnNowPlayerFragment.this.getResources().getDrawable(R.drawable.shape));
                        viewHolder.listItemChannelText.setBackgroundColor(OnNowPlayerFragment.this.getResources().getColor(R.color.xumoWhite));
                        viewHolder.movieLimitLevelAndTime.setTextColor(OnNowPlayerFragment.this.getResources().getColor(R.color.xumoBlue));
                        viewHolder.movieLimitTitle.setTextColor(OnNowPlayerFragment.this.getResources().getColor(R.color.genre_selector_list_layout));
                        layoutParams.height = OnNowPlayerFragment.this.getResources().getDimensionPixelSize(R.dimen.show_rating_height);
                        viewHolder.movieLimitTitle.setTypeface(Typeface.createFromAsset(OnNowPlayerFragment.this.getContext().getAssets(), "fonts/OpenSans-Semibold.ttf"));
                        viewHolder.noContentIv.setBackground(OnNowPlayerFragment.this.getResources().getDrawable(R.drawable.icon_loading_1));
                    } else {
                        viewHolder.movieLimitLevelAndTime.setText("");
                        layoutParams.height = OnNowPlayerFragment.this.getResources().getDimensionPixelSize(R.dimen.hidden_rating_height);
                        viewHolder.movieLimitTitle.setTypeface(Typeface.createFromAsset(OnNowPlayerFragment.this.getContext().getAssets(), "fonts/OpenSans-Regular.ttf"));
                    }
                    viewHolder.movieLimitLevelAndTime.setLayoutParams(layoutParams);
                    viewHolder.movieRuntime.setText(XumoUtil.formatMoviesTime(((VideoAsset) this.mVideoAssetList.get(i)).getRunTimeString()));
                    viewHolder.movieLimitTitle.setText(((VideoAsset) this.mVideoAssetList.get(i)).getTitle());
                    if (((VideoAsset) this.mVideoAssetList.get(i)).getAssetId().isEmpty() != 0) {
                        viewHolder.movieLimitTitle.setText(R.string.loading_more);
                    }
                    XumoImageUtil.setAssetThumbnailImage(OnNowPlayerFragment.this.getContext(), assetId, null, viewHolder.channelLogoImageView, 250, OnNowPlayerFragment.ASSET_THUMBNAIL_HEIGHT);
                    return;
                }
            }
            LogUtil.w("ChannelIdList null or size is 0.");
        }

        public int getItemCount() {
            return this.mVideoAssetList != null ? this.mVideoAssetList.size() : 0;
        }

        public VideoAsset getItem(int i) {
            return (this.mVideoAssetList == null || i < 0 || i >= this.mVideoAssetList.size()) ? 0 : (VideoAsset) this.mVideoAssetList.get(i);
        }
    }

    private class GenreListAdapter extends Adapter<ViewHolder> {
        private int clickPosition = 0;
        private ChannelListAdapter mChannelListAdapter;
        private int mGenreNumber = 0;
        private LayoutInflater mLayoutInflater;

        class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            RecyclerView mChannelRecyclerView;
            TextView mGenreNumberTextView;
            TextView mGenreTitleTextView;

            private ViewHolder(View view) {
                super(view);
                this.mGenreTitleTextView = (TextView) view.findViewById(R.id.genre_title);
                this.mChannelRecyclerView = (RecyclerView) view.findViewById(R.id.channel_list);
                this.mGenreNumberTextView = (TextView) view.findViewById(R.id.genre_number);
            }
        }

        GenreListAdapter() {
            this.mLayoutInflater = LayoutInflater.from(OnNowPlayerFragment.this.getContext());
        }

        public void setGenreNumberText(int i, int i2) {
            this.mGenreNumber = i;
            this.clickPosition = i2;
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(this.mLayoutInflater.inflate(R.layout.tv_list_item_genre, viewGroup, false));
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (OnNowPlayerFragment.this.mMovieChannel != null) {
                if (OnNowPlayerFragment.this.mMovieChannel.getCategories() != null) {
                    Category category = (Category) OnNowPlayerFragment.this.mMovieChannel.getCategories().get(i);
                    if (OnNowPlayerFragment.this.mMoviesListInfo == null) {
                        OnNowPlayerFragment.this.mMoviesListInfo = new ArrayList(OnNowPlayerFragment.this.mMovieChannel.getCategories().size());
                        for (int i2 = 0; i2 < OnNowPlayerFragment.this.mMovieChannel.getCategories().size(); i2++) {
                            OnNowPlayerFragment.this.mMoviesListInfo.add(new Movies());
                        }
                    }
                    viewHolder.mGenreTitleTextView.setText(category.getTitle());
                    ArrayList arrayList = new ArrayList();
                    Iterator it = category.getVideoAssets().iterator();
                    while (it.hasNext()) {
                        VideoAsset videoAsset = (VideoAsset) it.next();
                        videoAsset.setAxisY(i);
                        arrayList.add(videoAsset);
                    }
                    viewHolder.mChannelRecyclerView.setLayoutManager(new LinearLayoutManager(OnNowPlayerFragment.this.getContext(), 0, false));
                    this.mChannelListAdapter = new ChannelListAdapter(arrayList);
                    this.mChannelListAdapter.setLoadMoreFlag(((Movies) OnNowPlayerFragment.this.mMoviesListInfo.get(i)).ismLoadingNow());
                    viewHolder.mChannelRecyclerView.setAdapter(this.mChannelListAdapter);
                    if (i == OnNowPlayerFragment.this.mListItemIndexY) {
                        int i3 = 1;
                        if (arrayList.size() > 0 && OnNowPlayerFragment.this.mListItemIndexX > arrayList.size() - 1) {
                            OnNowPlayerFragment.this.mListItemIndexX = arrayList.size() - 1;
                        }
                        viewHolder.mChannelRecyclerView.scrollToPosition(OnNowPlayerFragment.this.mListItemIndexX);
                        if (OnNowPlayerFragment.this.mListItemIndexX + 1 > 0) {
                            i3 = 1 + OnNowPlayerFragment.this.mListItemIndexX;
                        }
                        int i4 = ((VideoAsset) arrayList.get(0)).getmHits();
                        if (this.mGenreNumber != 0 && i == this.clickPosition) {
                            i4 = this.mGenreNumber;
                        }
                        viewHolder = viewHolder.mGenreNumberTextView;
                        i = new StringBuilder();
                        i.append(i3);
                        i.append("|");
                        i.append(i4);
                        viewHolder.setText(i.toString());
                    } else {
                        viewHolder.mGenreNumberTextView.setText("");
                    }
                    return;
                }
            }
            LogUtil.w("GenreList null or size is 0.");
        }

        public int getItemCount() {
            return (OnNowPlayerFragment.this.mMovieChannel == null || OnNowPlayerFragment.this.mMovieChannel.getCategories() == null) ? 0 : OnNowPlayerFragment.this.mMovieChannel.getCategories().size();
        }
    }

    private class SelectChannelsListAdapter extends Adapter<ViewHolder> {
        private ArrayList<String> channelIdList;
        private boolean flag;

        public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            RelativeLayout backRl;
            ImageView contentIv;
            LinearLayout fatherLy;
            ImageView newIv;

            public ViewHolder(View view) {
                super(view);
                this.fatherLy = (LinearLayout) view.findViewById(R.id.father_ly);
                this.backRl = (RelativeLayout) view.findViewById(R.id.back_rl);
                this.newIv = (ImageView) view.findViewById(R.id.new_iv);
                this.contentIv = (ImageView) view.findViewById(R.id.content_iv);
            }
        }

        private SelectChannelsListAdapter() {
            this.flag = null;
        }

        private void setData(ArrayList<String> arrayList) {
            this.channelIdList = arrayList;
        }

        private void setFlag(boolean z) {
            this.flag = z;
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(OnNowPlayerFragment.this.getContext()).inflate(R.layout.tv_list_item_channels_content_child, viewGroup, false));
        }

        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder != null) {
                ImageContainer imageContainer = (ImageContainer) viewHolder.contentIv.getTag();
                if (imageContainer != null) {
                    imageContainer.cancelRequest();
                }
                viewHolder.contentIv.setImageDrawable(null);
            }
        }

        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
            if (!this.flag) {
                viewHolder.backRl.setBackgroundColor(0);
            } else if (i == OnNowPlayerFragment.this.mSelectChannelsContentIndex) {
                viewHolder.backRl.setBackgroundColor(OnNowPlayerFragment.this.getResources().getColor(R.color.xumoBlue));
            } else {
                viewHolder.backRl.setBackgroundColor(0);
            }
            viewHolder.fatherLy.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    viewHolder.fatherLy.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    OnNowPlayerFragment.this.mItemHigh = viewHolder.fatherLy.getHeight();
                }
            });
            XumoImageUtil.setChannelTitleImage(OnNowPlayerFragment.this.getContext(), (String) this.channelIdList.get(i), viewHolder.contentIv, 344, 194);
            viewHolder.newIv.setVisibility(4);
            Iterator it = OnNowPlayerFragment.this.mAllChannels.iterator();
            while (it.hasNext()) {
                Channel channel = (Channel) it.next();
                if (((String) this.channelIdList.get(i)).equals(channel.getChannelId())) {
                    if (channel.isNew() != 0) {
                        viewHolder.newIv.setVisibility(0);
                        return;
                    } else {
                        viewHolder.newIv.setVisibility(4);
                        return;
                    }
                }
            }
        }

        public int getItemCount() {
            return this.channelIdList.size();
        }
    }

    private class ChannelInfoListAdapter extends ListAdapter<OnNowLive, ViewHolder> {
        private boolean isUpdateControlGuideOnNow = true;
        private LayoutInflater mLayoutInflater;
        private int selectedColor;
        private int unSelectedColor;

        class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            ImageView channelFavorite;
            ImageView channelFavoriteSelector;
            LinearLayout channelInfoListId;
            ImageView channelLogoImageView;
            TextView channelNumberTextView;
            RelativeLayout channelViewLayout;
            ImageView isNewImageView;
            TextView onNowDescriptionTextView;
            LinearLayout onNowListId;
            TextView onNowMinLeftTextView;
            ImageView onNowPlayImageView;
            ImageView onNowPlayingIv;
            TextView onNowTitleTextView;
            RelativeLayout onNowViewLayout;
            View parentView;
            View playView;
            TextView titleTv;
            View unPlayView;
            TextView upNextDescriptionTextView;
            LinearLayout upNextListId;
            TextView upNextTitleTextView;
            RelativeLayout upNextViewLayout;

            private ViewHolder(View view) {
                super(view);
                this.playView = view.findViewById(R.id.play_view);
                this.unPlayView = view.findViewById(R.id.unPlay_view);
                this.titleTv = (TextView) view.findViewById(R.id.title_tv);
                this.channelInfoListId = (LinearLayout) view.findViewById(R.id.channel_info_list_id);
                this.channelViewLayout = (RelativeLayout) view.findViewById(R.id.channel_layout);
                this.isNewImageView = (ImageView) view.findViewById(R.id.is_new);
                this.channelLogoImageView = (ImageView) view.findViewById(R.id.channel_logo);
                this.channelNumberTextView = (TextView) view.findViewById(R.id.channel_number);
                this.channelFavorite = (ImageView) view.findViewById(R.id.channel_favorite);
                this.channelFavoriteSelector = (ImageView) view.findViewById(R.id.channel_favorite_selector);
                this.onNowListId = (LinearLayout) view.findViewById(R.id.on_now_list_id);
                this.onNowViewLayout = (RelativeLayout) view.findViewById(R.id.on_now_layout);
                this.onNowTitleTextView = (TextView) view.findViewById(R.id.on_now_title);
                this.onNowDescriptionTextView = (TextView) view.findViewById(R.id.on_now_description);
                this.onNowMinLeftTextView = (TextView) view.findViewById(R.id.on_now_min_left);
                this.onNowPlayImageView = (ImageView) view.findViewById(R.id.on_now_play);
                this.onNowPlayingIv = (ImageView) view.findViewById(R.id.on_now_playing);
                this.upNextListId = (LinearLayout) view.findViewById(R.id.up_next_list_id);
                this.upNextViewLayout = (RelativeLayout) view.findViewById(R.id.up_next_layout);
                this.upNextTitleTextView = (TextView) view.findViewById(R.id.up_next_title);
                this.upNextDescriptionTextView = (TextView) view.findViewById(R.id.up_next_description);
            }
        }

        ChannelInfoListAdapter() {
            this.mLayoutInflater = LayoutInflater.from(OnNowPlayerFragment.this.getContext());
            this.selectedColor = ContextCompat.getColor(OnNowPlayerFragment.this.getContext(), R.color.selected_color);
            this.unSelectedColor = ContextCompat.getColor(OnNowPlayerFragment.this.getContext(), R.color.un_selected_color);
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(this.mLayoutInflater.inflate(R.layout.tv_list_item_channel_logo, viewGroup, false));
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (getItemCount() == 0) {
                LogUtil.w("OnNowLiveList null or size is 0.");
                return;
            }
            OnNowLive onNowLive = (OnNowLive) getItem(i);
            if (!onNowLive.isFirstItemOfGenre() || i == getSelectedItemIndex()) {
                viewHolder.titleTv.setVisibility(8);
            } else {
                viewHolder.titleTv.setVisibility(0);
                viewHolder.titleTv.setText(onNowLive.getGenre());
            }
            viewHolder.channelNumberTextView.setText(onNowLive.getProgramNumberString());
            if (onNowLive.isNew()) {
                viewHolder.isNewImageView.setVisibility(0);
            } else {
                viewHolder.isNewImageView.setVisibility(4);
            }
            if (UserPreferences.getInstance().isFavorited(onNowLive.getChannelId())) {
                viewHolder.channelFavorite.setImageDrawable(OnNowPlayerFragment.this.getResources().getDrawable(R.drawable.favorited_blue));
            } else {
                viewHolder.channelFavorite.setImageDrawable(OnNowPlayerFragment.this.getResources().getDrawable(R.drawable.favorite_white));
            }
            XumoImageUtil.setChannelImage(OnNowPlayerFragment.this.getContext(), onNowLive.getChannelId(), viewHolder.channelLogoImageView, new int[0]);
            viewHolder.onNowTitleTextView.setText(onNowLive.getTitle());
            viewHolder.onNowDescriptionTextView.setText(onNowLive.getDescriptionText());
            CharSequence endTimeSinceNowString = onNowLive.getEndTimeSinceNowString();
            if (TextUtils.isEmpty(endTimeSinceNowString)) {
                endTimeSinceNowString = OnNowPlayerFragment.this.getString(R.string.on_now_less_than);
            }
            viewHolder.onNowMinLeftTextView.setText(endTimeSinceNowString);
            if (OnNowPlayerFragment.this.mPlaySourceCategory == PlaySourceCategory.OnNowLive && !TextUtils.isEmpty(OnNowPlayerFragment.mCurrentChannelId) && OnNowPlayerFragment.mCurrentChannelId.equals(onNowLive.getChannelId())) {
                viewHolder.onNowPlayingIv.setVisibility(0);
            } else {
                viewHolder.onNowPlayingIv.setVisibility(8);
            }
            viewHolder.upNextTitleTextView.setText(onNowLive.getNextTitle());
            viewHolder.upNextDescriptionTextView.setText(onNowLive.getNextDescriptionText());
            if (TextUtils.isEmpty(onNowLive.getNextStartTimeSinceNowString())) {
                OnNowPlayerFragment.this.getString(R.string.up_next_starting_shortly);
            }
            LayoutParams layoutParams = viewHolder.channelInfoListId.getLayoutParams();
            if (i != getSelectedItemIndex() || OnNowPlayerFragment.this.mCurrentSelectedStatus <= 0) {
                viewHolder.playView.setVisibility(8);
                viewHolder.unPlayView.setVisibility(8);
                viewHolder.onNowTitleTextView.setTypeface(Typeface.createFromAsset(OnNowPlayerFragment.this.getContext().getAssets(), "fonts/OpenSans-Regular.ttf"));
                viewHolder.onNowDescriptionTextView.setTypeface(Typeface.createFromAsset(OnNowPlayerFragment.this.getContext().getAssets(), "fonts/OpenSans-Regular.ttf"));
                viewHolder.upNextTitleTextView.setTypeface(Typeface.createFromAsset(OnNowPlayerFragment.this.getContext().getAssets(), "fonts/OpenSans-Regular.ttf"));
                viewHolder.channelFavoriteSelector.setVisibility(8);
                viewHolder.channelLogoImageView.setBackgroundColor(0);
                viewHolder.onNowViewLayout.setBackgroundColor(OnNowPlayerFragment.this.getResources().getColor(R.color.un_selected_color));
                viewHolder.onNowMinLeftTextView.setVisibility(4);
                viewHolder.onNowDescriptionTextView.setVisibility(4);
                viewHolder.upNextViewLayout.setBackgroundColor(OnNowPlayerFragment.this.getResources().getColor(R.color.un_selected_color));
                viewHolder.upNextDescriptionTextView.setVisibility(4);
                layoutParams.height = OnNowPlayerFragment.this.getResources().getDimensionPixelSize(R.dimen.on_now_list_height);
                if (i == 0 && (((OnNowLive) OnNowPlayerFragment.mOnNowLives.get(0)).getChannelId() == 0 || ((OnNowLive) OnNowPlayerFragment.mOnNowLives.get(0)).getChannelId().isEmpty() != 0)) {
                    layoutParams.height = 0;
                    OnNowPlayerFragment.this.noFavoritesView.setBackgroundDrawable(OnNowPlayerFragment.this.getResources().getDrawable(R.drawable.shape_selected_white));
                }
            } else {
                if (this.isUpdateControlGuideOnNow != 0) {
                    OnNowPlayerFragment.this.updateControlGuideOnNow();
                    this.isUpdateControlGuideOnNow = false;
                }
                viewHolder.playView.setVisibility(0);
                viewHolder.unPlayView.setVisibility(0);
                viewHolder.onNowTitleTextView.setTypeface(Typeface.createFromAsset(OnNowPlayerFragment.this.getContext().getAssets(), "fonts/OpenSans-Semibold.ttf"));
                viewHolder.onNowDescriptionTextView.setTypeface(Typeface.createFromAsset(OnNowPlayerFragment.this.getContext().getAssets(), "fonts/OpenSans-Semibold.ttf"));
                viewHolder.upNextTitleTextView.setTypeface(Typeface.createFromAsset(OnNowPlayerFragment.this.getContext().getAssets(), "fonts/OpenSans-Semibold.ttf"));
                if (1 == OnNowPlayerFragment.this.mCurrentSelectedStatus) {
                    viewHolder.channelFavoriteSelector.setVisibility(0);
                } else {
                    viewHolder.channelFavoriteSelector.setVisibility(8);
                }
                if (2 == OnNowPlayerFragment.this.mCurrentSelectedStatus) {
                    viewHolder.channelLogoImageView.setBackgroundResource(R.drawable.shape_rectangle_blue);
                } else {
                    viewHolder.channelLogoImageView.setBackgroundColor(0);
                }
                if (3 == OnNowPlayerFragment.this.mCurrentSelectedStatus) {
                    viewHolder.playView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, (float) onNowLive.getProgress()));
                    viewHolder.playView.setBackgroundColor(OnNowPlayerFragment.this.getResources().getColor(R.color.selected_on_now_bg));
                    viewHolder.unPlayView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, (float) (100 - onNowLive.getProgress())));
                    viewHolder.unPlayView.setBackgroundColor(0);
                    viewHolder.onNowViewLayout.setBackground(OnNowPlayerFragment.this.getResources().getDrawable(R.drawable.shape_selected_blue));
                } else {
                    viewHolder.playView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, (float) onNowLive.getProgress()));
                    viewHolder.playView.setBackgroundColor(OnNowPlayerFragment.this.getResources().getColor(R.color.selected_color));
                    viewHolder.unPlayView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, (float) (100 - onNowLive.getProgress())));
                    viewHolder.unPlayView.setBackgroundColor(0);
                    viewHolder.onNowViewLayout.setBackgroundColor(OnNowPlayerFragment.this.getResources().getColor(R.color.selected_color));
                }
                viewHolder.onNowMinLeftTextView.setVisibility(0);
                viewHolder.onNowDescriptionTextView.setVisibility(0);
                viewHolder.upNextViewLayout.setBackgroundColor(OnNowPlayerFragment.this.getResources().getColor(R.color.selected_color));
                viewHolder.upNextDescriptionTextView.setVisibility(0);
                layoutParams.height = OnNowPlayerFragment.this.getResources().getDimensionPixelSize(R.dimen.on_now_list_now_height);
                if (getSelectedItemIndex() != 0 || (((OnNowLive) OnNowPlayerFragment.mOnNowLives.get(0)).getChannelId() != 0 && ((OnNowLive) OnNowPlayerFragment.mOnNowLives.get(0)).getChannelId().isEmpty() == 0)) {
                    OnNowPlayerFragment.this.isEmptyFavorite(false);
                } else {
                    OnNowPlayerFragment.this.isEmptyFavorite(true);
                    layoutParams.height = 0;
                    OnNowPlayerFragment.this.noFavoritesView.setBackgroundDrawable(OnNowPlayerFragment.this.getResources().getDrawable(R.drawable.shape_selected_blue));
                }
            }
            viewHolder.channelInfoListId.setLayoutParams(layoutParams);
        }
    }

    private class GenreSelectorListAdapter extends ListAdapter<String, ViewHolder> {
        private LayoutInflater mLayoutInflater;
        OnGenreSelected mOnGenreSelected;
        private int selectItemIndex = 0;

        class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            View genreSelectedView;
            TextView genreTitleView;

            public ViewHolder(View view) {
                super(view);
                this.genreTitleView = (TextView) view.findViewById(R.id.genre_title_view);
                this.genreSelectedView = view.findViewById(R.id.genre_selected_view);
            }
        }

        GenreSelectorListAdapter() {
            super.setSelectedItemIndex(0);
            this.mLayoutInflater = LayoutInflater.from(OnNowPlayerFragment.this.getContext());
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(this.mLayoutInflater.inflate(R.layout.tv_list_item_genre_selector, viewGroup, false));
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            StringBuilder stringBuilder;
            if (i == getSelectedItemIndex()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Item ");
                stringBuilder.append(i);
                stringBuilder.append(" text set as bold");
                LogUtil.d(stringBuilder.toString());
                viewHolder.genreSelectedView.setVisibility(0);
                viewHolder.genreTitleView.setTypeface(Typeface.createFromAsset(OnNowPlayerFragment.this.getContext().getAssets(), "fonts/OpenSans-Bold.ttf"));
                viewHolder.genreTitleView.setTextSize(15.0f);
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Item ");
                stringBuilder.append(i);
                stringBuilder.append(" text set as normal");
                LogUtil.d(stringBuilder.toString());
                viewHolder.genreSelectedView.setVisibility(8);
                viewHolder.genreTitleView.setTypeface(Typeface.createFromAsset(OnNowPlayerFragment.this.getContext().getAssets(), "fonts/OpenSans-Semibold.ttf"));
                viewHolder.genreTitleView.setTextSize(13.0f);
            }
            if (i == this.selectItemIndex) {
                viewHolder.genreTitleView.setTextColor(OnNowPlayerFragment.this.getResources().getColor(R.color.xumoBlue));
            } else {
                viewHolder.genreTitleView.setTextColor(-1);
            }
            viewHolder.genreTitleView.setText((CharSequence) super.getItem(i));
        }

        void updateGenres(List<String> list) {
            super.update(list);
        }

        public void setSelectedGenreIndex(String str) {
            if (str != null) {
                str = super.getItemIndex(str);
                if (str > -1) {
                    setSelectedItemIndex(str);
                    this.selectItemIndex = str;
                    notifyDataSetChanged();
                }
            }
        }

        void navigateToSelectedGenre() {
            if (this.mOnGenreSelected != null) {
                this.mOnGenreSelected.selectGenre((String) super.getItem(super.getSelectedItemIndex()));
                OnNowPlayerFragment.this.genreSelectorTitle.setText((CharSequence) super.getItem(super.getSelectedItemIndex()));
            }
        }
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.mXumoDataSync = XumoDataSync.getInstance();
        this.mUserPreferences = UserPreferences.getInstance();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        return layoutInflater.inflate(R.layout.tv_fragment_on_now_player, viewGroup, false);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if ((getActivity() instanceof MainTvActivity) != null) {
            this.mMainActivity = (MainTvActivity) getActivity();
            if (this.mMainActivity.isVizioDeepLink() != null) {
                handleVizioDeepLink();
            } else if (this.mMainActivity.isPushNotificationDeepLink() != null) {
                handlePushNotificationDeepLink();
            } else if (UserPreferences.getInstance().getViewBoost() != null) {
                mCurrentChannelId = this.mUserPreferences.getViewBoostChannelId();
            } else {
                mCurrentChannelId = this.mUserPreferences.getLastPlayedChannelId();
            }
        }
        this.genreSelectorTitle = (TextView) view.findViewById(R.id.genre_selector_title);
        this.promptActionText = (TextView) view.findViewById(R.id.prompt_action_text);
        this.guideOkLinearLayout = (LinearLayout) view.findViewById(R.id.guide_ok);
        this.guideSelectGenreLinearLayout = (LinearLayout) view.findViewById(R.id.ly_guide_select_genre);
        this.guideSelectGenreImageView = (ImageView) view.findViewById(R.id.img_guide_select_genre);
        this.guideSelectGenreImageView.setImageResource(R.drawable.hinting_icon_right);
        this.noFavoritesView = (LinearLayout) view.findViewById(R.id.no_favorites_view);
        this.mChannelInfoTitle = (RelativeLayout) view.findViewById(R.id.on_now_tab);
        this.mChannelInfoRecyclerView = (XumoTvRecyclerView) view.findViewById(R.id.channel_info_list);
        bundle = new LinearLayoutManager(getActivity());
        bundle.setOrientation(1);
        this.mChannelInfoRecyclerView.setLayoutManager(bundle);
        this.mChannelInfoRecyclerView.setListAdapter(new ChannelInfoListAdapter());
        this.mRlonNow = (Button) view.findViewById(R.id.rl_on_now);
        this.mRlchannel = (Button) view.findViewById(R.id.rl_channel);
        this.mRlmovies = (Button) view.findViewById(R.id.rl_movies);
        this.mRlsearch = (Button) view.findViewById(R.id.rl_search);
        this.mRlsettings = (Button) view.findViewById(R.id.rl_settings);
        this.mRlexit = (Button) view.findViewById(R.id.rl_exit);
        this.mRlonNowText = (TextView) view.findViewById(R.id.rl_on_now_text);
        this.mRlchannelText = (TextView) view.findViewById(R.id.rl_channel_text);
        this.mRlmoviesText = (TextView) view.findViewById(R.id.rl_movies_text);
        this.mRlsearchText = (TextView) view.findViewById(R.id.rl_search_text);
        this.mRlsettingsText = (TextView) view.findViewById(R.id.rl_settings_text);
        this.mRlexitText = (TextView) view.findViewById(R.id.rl_exit_text);
        this.mOnNowGenreUp = (ImageView) view.findViewById(R.id.on_now_genre_up);
        this.mOnNowGenreDown = (ImageView) view.findViewById(R.id.on_now_genre_down);
        this.onNowDetails = (LinearLayout) view.findViewById(R.id.on_now_details);
        this.onNowDetailsRl = (RelativeLayout) view.findViewById(R.id.on_now_details_rl);
        this.onNowDetailsPrompt = (LinearLayout) view.findViewById(R.id.on_now_details_prompt);
        this.onNowDetailsInfo = (RelativeLayout) view.findViewById(R.id.on_now_details_info);
        this.onNowDetailsImage = (ImageView) view.findViewById(R.id.on_now_details_image);
        this.onNowDetailsNumbner = (TextView) view.findViewById(R.id.on_now_details_numbner);
        this.onNowDetailsTitle = (TextView) view.findViewById(R.id.on_now_details_title);
        this.onNowDetailsDescription = (TextView) view.findViewById(R.id.on_now_details_description);
        this.mOnNowCcButton = (ImageView) view.findViewById(R.id.on_now_cc_id);
        this.mTvConfigEnabled = false;
        this.mNavigationContainer = view.findViewById(R.id.navigation_container);
        this.mPlayerContainerView = view.findViewById(R.id.player_layout);
        this.mGenreSelectorListLayout = view.findViewById(R.id.genre_selector_list_layout);
        this.mGenreSelectorList = (XumoTvRecyclerView) view.findViewById(R.id.genre_selector_list);
        this.mGenreSelectorAdapter = new GenreSelectorListAdapter();
        this.mGenreSelectorAdapter.mOnGenreSelected = new -$$Lambda$OnNowPlayerFragment$fJzii_UwwMeLSbpYTWiAeZbyhhw();
        this.mGenreSelectorList.setAdapter(this.mGenreSelectorAdapter);
        this.mGenreSelectorList.setHasFixedSize(true);
        this.mGenreSelectorList.setItemAnimator(null);
        this.tvLogo = (TextView) view.findViewById(R.id.tv_logo);
        this.mLlMovies = (LinearLayout) view.findViewById(R.id.movies);
        this.genreRecyclerView = (RecyclerView) view.findViewById(R.id.genre_list);
        this.genreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mGenreListAdapter = new GenreListAdapter();
        this.genreRecyclerView.setAdapter(this.mGenreListAdapter);
        this.mMoviesPlayButton = (ImageButton) view.findViewById(R.id.play);
        this.mMoviesPauseButton = (ImageButton) view.findViewById(R.id.pause);
        this.mMoviesSkipBackButton = (ImageButton) view.findViewById(R.id.skip_back);
        this.mMoviesSkipFwdButton = (ImageButton) view.findViewById(R.id.skip_fwd);
        this.mMoviesNextVodButton = (ImageButton) view.findViewById(R.id.next_vod_button);
        this.mMoviesPreVodButton = (ImageButton) view.findViewById(R.id.pre_vod_button);
        this.mVodCcButton = (ImageView) view.findViewById(R.id.vod_cc_id);
        this.mMoviesSkipFwdText = (TextView) view.findViewById(R.id.skip_fwd_text);
        this.mMoviesSkipBackText = (TextView) view.findViewById(R.id.skip_back_text);
        this.mAllChannelsLy = (LinearLayout) view.findViewById(R.id.all_channels_ly);
        this.mChannelsTitleRv = (RecyclerView) view.findViewById(R.id.channels_title_rv);
        this.mGenreTitleTv = (TextView) view.findViewById(R.id.genre_title_tv);
        this.mChannelsContentRv = (RecyclerView) view.findViewById(R.id.channels_content_rv);
        this.mallChannelsGenreUp = (ImageView) view.findViewById(R.id.all_channels_genre_up);
        this.mallChannelsGenreDown = (ImageView) view.findViewById(R.id.all_channels_genre_down);
        this.mExoPlayerImage = (ImageView) view.findViewById(R.id.exo_playback_image);
        this.mAllChannelsListAdapter = new AllChannelsListAdapter();
        this.mChannelsTitleRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mChannelsTitleRv.setAdapter(this.mAllChannelsListAdapter);
        this.mAllChannelsContentListAdapter = new AllChannelsContentListAdapter();
        this.mChannelsContentRv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mChannelsContentRv.setAdapter(this.mAllChannelsContentListAdapter);
        this.mSettingLy = (LinearLayout) view.findViewById(R.id.setting_ly);
        this.mDefaultTv = (TextView) view.findViewById(R.id.default_tv);
        this.mDefaultSwitch = (Switch) view.findViewById(R.id.default_switch);
        this.mClientIdTv = (TextView) view.findViewById(R.id.clientId_tv);
        UserPreferences.getInstance().registerOnSharedPreferenceChangeListener(this);
        updateXumoClientId();
        this.mAppVersionTv = (TextView) view.findViewById(R.id.appVersion_tv);
        TextView textView = this.mAppVersionTv;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.settings_version_name, BuildConfig.VERSION_NAME));
        stringBuilder.append(".");
        stringBuilder.append(34);
        textView.setText(stringBuilder.toString());
        this.mAppTv = (TextView) view.findViewById(R.id.app_tv);
        this.mLicensesTv = (TextView) view.findViewById(R.id.licenses_tv);
        this.mDefaultSwitch.setChecked(UserPreferences.getInstance().getSubtitleSwitch());
        if (this.mDefaultSwitch.isChecked()) {
            this.mAppTv.setTextColor(-1);
        } else {
            this.mAppTv.setTextColor(getResources().getColor(R.color.tab_noselect));
        }
        this.mDebugModeLy = (LinearLayout) view.findViewById(R.id.debug_mode_tv);
        this.mDebugModeCode1Tv = (TextView) view.findViewById(R.id.debug_mode_code_1_tv);
        this.mDebugModeCode2Tv = (TextView) view.findViewById(R.id.debug_mode_code_2_tv);
        this.mDebugModeSeparatorTv = (TextView) view.findViewById(R.id.debug_mode_separator);
        this.mEnableDebugLogsTv = (TextView) view.findViewById(R.id.enable_debug_logs_tv);
        if (UserPreferences.getInstance().getUseDebugLogs()) {
            this.mEnableDebugLogsTv.setVisibility(0);
            showDebugFragment();
        }
        this.mEnableTestChannelsTv = (TextView) view.findViewById(R.id.enable_test_channels_tv);
        if (UserPreferences.getInstance().getUseTestChannels()) {
            this.mEnableTestChannelsTv.setVisibility(0);
        }
        this.mEnableStagingPlayersTv = (TextView) view.findViewById(R.id.enable_staging_players_tv);
        if (UserPreferences.getInstance().getUseStagingPlayers()) {
            this.mEnableStagingPlayersTv.setVisibility(0);
        }
        this.mViewboosterStagingTv = (TextView) view.findViewById(R.id.viewbooster_staging_tv);
        if (UserPreferences.getInstance().getUseStagingViewBooster()) {
            this.mViewboosterStagingTv.setVisibility(0);
        }
        this.mClearLocalStorageTv = (TextView) view.findViewById(R.id.clear_local_storage_tv);
        this.mMoreFromLy = (LinearLayout) view.findViewById(R.id.more_from_ly);
        this.mBackToChannelInfoTv = (TextView) view.findViewById(R.id.back_to_channel_info_tv);
        this.mMoreFromSelectLy = (LinearLayout) view.findViewById(R.id.more_from_select_ly);
        this.mMoreFromSelectIv = (ImageView) view.findViewById(R.id.more_from_select_iv);
        this.mMoreFromBottomLy = (LinearLayout) view.findViewById(R.id.more_from_bottom_ly);
        this.mMoreFromSelectTitleTv = (TextView) view.findViewById(R.id.more_from_select_title_tv);
        this.mMoreFromSelectContentTv = (TextView) view.findViewById(R.id.more_from_select_content_tv);
        this.mMainMenuSelectLy = (LinearLayout) view.findViewById(R.id.main_menu_select_ly);
        this.mMainMenuSelectIv = (ImageView) view.findViewById(R.id.main_menu_select_iv);
        this.mMainMenuBottomLy = (LinearLayout) view.findViewById(R.id.main_menu_bottom_ly);
        this.mMainMenuSelectTitleTv = (TextView) view.findViewById(R.id.main_menu_select_title_tv);
        this.mMainMenuSelectContentTv = (TextView) view.findViewById(R.id.main_menu_select_content_tv);
        this.mVodRl = (RelativeLayout) view.findViewById(R.id.vod_rl);
        this.mVodMoreFromLy = (LinearLayout) view.findViewById(R.id.vod_more_from_ly);
        this.vodDetailsInfo = (RelativeLayout) view.findViewById(R.id.vod_details_info);
        this.vodDetailsPrompt = (RelativeLayout) view.findViewById(R.id.vod_details_prompt);
        this.mVodBackToChannelInfoTv = (TextView) view.findViewById(R.id.vod_back_to_channel_info_tv);
        this.mVodUpNextFatherLy = (LinearLayout) view.findViewById(R.id.vod_up_next_father_ly);
        this.mVodMoreFromTileLy = (LinearLayout) view.findViewById(R.id.vod_more_from_tile_ly);
        this.mVodUpNextLy = (LinearLayout) view.findViewById(R.id.vod_up_next_ly);
        this.mVodUpNextIv = (ImageView) view.findViewById(R.id.vod_up_next_iv);
        this.mVodUpNextTimeTv = (TextView) view.findViewById(R.id.vod_up_next_time_tv);
        this.mVodUpNextBottomLy = (LinearLayout) view.findViewById(R.id.vod_up_next_bottom_ly);
        this.mVodUpNextTitleTv = (TextView) view.findViewById(R.id.vod_up_next_title_tv);
        this.mVodUpNextContentTv = (TextView) view.findViewById(R.id.vod_up_next_content_tv);
        this.mVodMoreFromSelectLy = (LinearLayout) view.findViewById(R.id.vod_more_from_select_ly);
        this.mVodMoreFromIv = (ImageView) view.findViewById(R.id.vod_more_from_iv);
        this.mVodMoreFromBottomLy = (LinearLayout) view.findViewById(R.id.vod_more_from_bottom_ly);
        this.mVodMoreFromTitleTv = (TextView) view.findViewById(R.id.vod_more_from_title_tv);
        this.mVodMoreFromContentTv = (TextView) view.findViewById(R.id.vod_more_from_content_tv);
        this.mVodMainMenuLy = (LinearLayout) view.findViewById(R.id.vod_main_menu_ly);
        this.mVodMainMenuIv = (ImageView) view.findViewById(R.id.vod_main_menu_iv);
        this.mVodMainMenuBottomLy = (LinearLayout) view.findViewById(R.id.vod_main_menu_bottom_ly);
        this.mVodMainMenuTitleTv = (TextView) view.findViewById(R.id.vod_main_menu_title_tv);
        this.mVodMainMenuContentTv = (TextView) view.findViewById(R.id.vod_main_menu_content_tv);
        this.mControlGuideOnNow = view.findViewById(R.id.control_guide_on_now);
        this.mControlGuideGenreSelector = view.findViewById(R.id.control_guide_genre_selector);
        initData();
    }

    private void initData() {
        getAllChannels();
        if (!(this.mPlayerView == null || this.mPlayerContainerView == null)) {
            this.mTvConfigEnabled = true;
            this.mPlayerContainerView.requestFocus();
        }
        this.mCurrentOnNowLive = this.mXumoExoPlayer.getOnNowLive();
        if (this.mCurrentOnNowLive == null || TextUtils.isEmpty(mCurrentChannelId) || !mCurrentChannelId.equals(this.mCurrentOnNowLive.getChannelId())) {
            this.mXumoExoPlayer.setOnNowLive(null);
            this.mXumoExoPlayer.setVideoAsset(null);
            clearPlayer();
        } else {
            this.mXumoExoPlayer.setTitle(this.mCurrentOnNowLive.getTitle());
            scrollToOnNowCurrentPosition();
        }
        if (this.mXumoExoPlayer.getVideoAsset() instanceof LiveAsset) {
            this.mCurrentLiveAsset = (LiveAsset) this.mXumoExoPlayer.getVideoAsset();
        } else {
            if (this.mXumoExoPlayer.isLive() && this.mXumoExoPlayer.ismIsAdDisplayedCopy()) {
                this.mXumoExoPlayer.play();
                this.mXumoExoPlayer.updatePlayerController(true);
            } else {
                this.mXumoExoPlayer.stop();
            }
            this.mCurrentLiveAsset = null;
        }
        this.mXumoExoPlayer.updatePlayerController(true);
        this.mCurrentVideoAsset = this.mXumoExoPlayer.getVideoAsset();
        getGenreSelectorList();
        this.mUpdateDisplayHandler.sendEmptyMessageDelayed(1, NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
        this.mXumoExoPlayer.setOnKeyPressListener(this);
        this.mXumoExoPlayer.setTvControllerListener(this);
        if (UserPreferences.getInstance().getViewBoost()) {
            UserPreferences.getInstance().setToBrandScreen();
            return;
        }
        animateIn();
        UserPreferences.getInstance().setToGuideScreen();
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        validateViewLayout();
        if (((XumoApplication) getActivity().getApplication()).getmAppStatus() == 2 && this.mPlaySourceCategory == PlaySourceCategory.OnNowLive) {
            resumeOnNowWhenError();
        } else if (UserPreferences.getInstance().getViewBoost()) {
            this.mXumoExoPlayer.rePlay();
        } else if (this.mCurrentVideoAsset == null) {
        } else {
            if (this.mPlaySourceCategory == PlaySourceCategory.BrandPageVideo) {
                final Channel channel = new Channel(this.mCurrentVideoAsset.getChannelId());
                this.mXumoDataSync.getChannelInfoForChannelId(this.mCurrentVideoAsset.getChannelId(), new Listener() {
                    public void onCompletion(Object obj) {
                        if (OnNowPlayerFragment.this.getHost() != null) {
                            Channel channel = (Channel) obj;
                            channel.setCategories(channel.getCategories());
                            channel.setGenreId(channel.getGenreId());
                            channel.setGenreName(channel.getGenreName());
                            channel.setChannelTitle(channel.getChannelTitle());
                            channel.setDescriptionText(channel.getDescriptionText());
                            OnNowPlayerFragment.this.createVideoPlaylistForFromBack(channel);
                            OnNowPlayerFragment.this.mXumoExoPlayer.rePlay();
                        }
                    }

                    public void onError() {
                        OnNowPlayerFragment.this.isResetMainNavigationDisplay = true;
                        OnNowPlayerFragment.this.mMainNavigationPageSelectState = MainNavigationPageSelectState.OnNow;
                        OnNowPlayerFragment.this.initData();
                    }
                });
            } else if (this.mPlaySourceCategory == PlaySourceCategory.MoviesVideo) {
                getMoviesChannelInfomationForFromBack();
            }
        }
    }

    public void onPause() {
        super.onPause();
        this.mXumoExoPlayer.pause();
        this.mXumoExoPlayer.hideController();
    }

    public void onStop() {
        super.onStop();
        this.mUpdateDisplayHandler.removeMessages(1);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void onPlayerStateChanged(boolean z, int i) {
        LogUtil.d(" - Method start.");
        if (z && i == true) {
            if (!this.mCurrentVideoAsset) {
                LogUtil.d("onPlayerStateChanged() mCurrentVideoAsset is null.");
            } else if (this.mXumoExoPlayer.ismIsAdDisplayedCopy()) {
                LogUtil.d("onPlayerStateChanged() ismIsAdDisplayedCopy.");
            } else if (this.mCurrentVideoAsset.getAssetType()) {
                loadLiveAssetPlayer(this.mCurrentOnNowLive, null, Boolean.valueOf(false));
            } else if (this.mCurrentVideoAsset.getAssetType()) {
                if (this.mPlaySourceCategory == PlaySourceCategory.MoviesVideo && this.mVideoPlaylist) {
                    z = this.mVideoPlaylist.getNextVideoAsset();
                    z.getAssetMetaData(new NoResponseListener() {
                        public void onCompletion() {
                            if (OnNowPlayerFragment.this.getHost() != null && OnNowPlayerFragment.this.tvVodPlayEndFlag) {
                                OnNowPlayerFragment.this.tvVodPlayEndFlag = false;
                                OnNowPlayerFragment.this.showPage(DisplayView.MoviesUpNextPage, z);
                                OnNowPlayerFragment.this.pause();
                            }
                        }

                        public void onError() {
                            if (OnNowPlayerFragment.this.getHost() != null) {
                                LogUtil.d(MediaRouteProviderProtocol.SERVICE_DATA_ERROR);
                                OnNowPlayerFragment.this.showPlayerErrorMessage(2);
                            }
                        }
                    });
                } else {
                    loadNextVideoAsset();
                }
            } else if (this.mPlaySourceCategory == PlaySourceCategory.OnNowLive) {
                loadLiveAssetPlayer(this.mCurrentOnNowLive, null, Boolean.valueOf(false));
            } else {
                loadNextVideoAsset();
            }
        }
    }

    public void onPlayerError(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("errorType = ");
        stringBuilder.append(i);
        LogUtil.d(stringBuilder.toString());
        if (this.mXumoExoPlayer.getVideoAsset() instanceof LiveAsset) {
            if (i == 0) {
                showNetworkErrorMessage(3);
            } else {
                showPlayerErrorMessage(1);
            }
            this.mUserPreferences.removeLastPlayedChannelId();
        } else if (i == 0) {
            showNetworkErrorMessage(4);
        } else {
            showPlayerErrorMessage(2);
        }
    }

    public void onHandlePushNotificationDeepLink() {
        animateIn();
        handlePushNotificationDeepLink();
        scrollToOnNowCurrentPosition();
    }

    private void handleVizioDeepLink() {
        Object deepLinkChannelId = this.mMainActivity.getDeepLinkChannelId();
        CharSequence deepLinkTrackingId = this.mMainActivity.getDeepLinkTrackingId();
        if (TextUtils.isEmpty(deepLinkChannelId) || TextUtils.isEmpty(deepLinkTrackingId)) {
            mCurrentChannelId = this.mUserPreferences.getLastPlayedChannelId();
            return;
        }
        mCurrentChannelId = deepLinkChannelId;
        this.mUserPreferences.setLastPlayedChannelId(mCurrentChannelId);
    }

    private void handlePushNotificationDeepLink() {
        LogUtil.d(" - Method start.");
        Object deepLinkNotificationId = this.mMainActivity.getDeepLinkNotificationId();
        String deepLinkChannelId = this.mMainActivity.getDeepLinkChannelId();
        String deepLinkLiveChannelId = this.mMainActivity.getDeepLinkLiveChannelId();
        CharSequence deepLinkAssetId = this.mMainActivity.getDeepLinkAssetId();
        if (!TextUtils.isEmpty(deepLinkChannelId)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("deepLinkChannelId=");
            stringBuilder.append(deepLinkChannelId);
            LogUtil.d(stringBuilder.toString());
            this.mIsTransionBrandPage = true;
            mCurrentChannelId = deepLinkChannelId;
            this.mUserPreferences.setLastPlayedChannelId(mCurrentChannelId);
            if (this.mMainActivity.isLaunch()) {
                this.mUpdateDisplayHandler.removeMessages(1);
                this.mXumoExoPlayer.removePlayerView();
                this.mIsTransionBrandPage = false;
            }
        } else if (!TextUtils.isEmpty(deepLinkLiveChannelId)) {
            r1 = new StringBuilder();
            r1.append("deepLinkLiveChannelId=");
            r1.append(deepLinkLiveChannelId);
            LogUtil.d(r1.toString());
            if (!TextUtils.isEmpty(deepLinkNotificationId)) {
                r1 = new StringBuilder();
                r1.append("notificationId=");
                r1.append(deepLinkNotificationId);
                LogUtil.d(r1.toString());
            }
            hidePlayerErrorMessage();
            if (TextUtils.isEmpty(mCurrentChannelId) || !mCurrentChannelId.equals(deepLinkLiveChannelId)) {
                mCurrentChannelId = deepLinkLiveChannelId;
                this.mUserPreferences.setLastPlayedChannelId(mCurrentChannelId);
                clearPlayer();
                loadOnNowDisplay();
            } else {
                LogUtil.d("currentChannelId = deepLinkLiveChannelId.");
                resumeOnNow();
            }
            this.mMainActivity.clearDeepLinkInfo();
        } else if (!TextUtils.isEmpty(deepLinkAssetId)) {
            if (!TextUtils.isEmpty(deepLinkNotificationId)) {
                r1 = new StringBuilder();
                r1.append("notificationId=");
                r1.append(deepLinkNotificationId);
                LogUtil.d(r1.toString());
            }
            if (this.mUpdateDisplayHandler != null) {
                this.mUpdateDisplayHandler.removeMessages(1);
            }
            clearPlayer();
            this.mMainActivity.clearDeepLinkInfo();
        } else if (TextUtils.isEmpty(deepLinkNotificationId)) {
            LogUtil.w("notification data is invalid.");
            this.mMainActivity.clearDeepLinkInfo();
        } else {
            r1 = new StringBuilder();
            r1.append("notificationId=");
            r1.append(deepLinkNotificationId);
            LogUtil.d(r1.toString());
            this.mMainActivity.clearDeepLinkInfo();
        }
    }

    private void playViewBoosterAsset() {
        this.mPlaySourceCategory = PlaySourceCategory.BrandPageVideo;
        this.mVodPlayerControlsSelectedStatus = 3;
        updateVodPlayerControlsSelectedStatus(this.mVodPlayerControlsSelectedStatus);
        String viewBoostChannelId = UserPreferences.getInstance().getViewBoostChannelId();
        String str = "";
        String viewBoostAssetId = UserPreferences.getInstance().getViewBoostAssetId();
        Channel channel = new Channel(viewBoostChannelId);
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new Category(viewBoostChannelId, str));
        ArrayList arrayList2 = new ArrayList(1);
        arrayList2.add(new VideoAsset(viewBoostAssetId, str, viewBoostChannelId));
        ((Category) arrayList.get(0)).setVideoAssets(arrayList2);
        channel.setCategories(arrayList);
        playVideoForBrandPage(channel, UserPreferences.getInstance().getViewBoostAssetId());
    }

    private void resumeOnNow() {
        LogUtil.d(" - Method start.");
        if (this.mXumoExoPlayer.getVideoAsset() instanceof LiveAsset) {
            this.mCurrentLiveAsset = (LiveAsset) this.mXumoExoPlayer.getVideoAsset();
        } else {
            this.mCurrentLiveAsset = null;
        }
        this.mCurrentOnNowLive = this.mXumoExoPlayer.getOnNowLive();
        if (this.mCurrentOnNowLive == null || TextUtils.isEmpty(mCurrentChannelId) || !mCurrentChannelId.equals(this.mCurrentOnNowLive.getChannelId())) {
            clearPlayer();
            loadOnNowDisplay();
        } else if (this.mCurrentLiveAsset == null) {
            this.mXumoExoPlayer.stop();
            playOnNowLive(this.mCurrentOnNowLive, this.mCurrentOnNowLiveIndex, new Date());
        } else {
            this.mCurrentOnNowLiveIndex = mOnNowLives.indexOf(this.mCurrentOnNowLive);
            this.mXumoExoPlayer.setTitle(this.mCurrentOnNowLive.getTitle());
            this.mXumoExoPlayer.play();
            this.mXumoExoPlayer.updatePlayerController(true);
        }
    }

    protected void resumeOnNowWhenError() {
        LogUtil.d(" - Method start.");
        this.mUserPreferences.removeLastPlayedChannelId();
        if (TextUtils.isEmpty(this.reloadChannelId) || !this.reloadChannelId.equals(mCurrentChannelId)) {
            this.reloadOnNowLivesCounts = 0;
            this.reloadChannelId = mCurrentChannelId;
        }
        this.reloadOnNowLivesCounts++;
        if (this.reloadOnNowLivesCounts > 15) {
            this.reloadOnNowLivesCounts = 0;
            if (mOnNowLives != null) {
                int size = mOnNowLives.size();
                int i = 0;
                while (i < size) {
                    String channelId = ((OnNowLive) mOnNowLives.get(i)).getChannelId();
                    if (TextUtils.isEmpty(mCurrentChannelId)) {
                        mCurrentChannelId = channelId;
                        break;
                    } else if (!mCurrentChannelId.equals(channelId)) {
                        i++;
                    } else if (i < mOnNowLives.size() - 1) {
                        mCurrentChannelId = ((OnNowLive) mOnNowLives.get(i + 1)).getChannelId();
                    } else {
                        mCurrentChannelId = ((OnNowLive) mOnNowLives.get(0)).getChannelId();
                    }
                }
            }
        }
        if (!TextUtils.isEmpty(mCurrentChannelId)) {
            if (mOnNowLives != null) {
                if (this.mCurrentOnNowLive == null) {
                    loadOnNowDisplay();
                    return;
                } else {
                    loadLiveAssetPlayer(this.mCurrentOnNowLive, new Date(), Boolean.valueOf(false));
                    return;
                }
            }
        }
        loadOnNowLives(true);
    }

    protected void resumeVideoAssetWhenError() {
        LogUtil.d(" - Method start.");
        if (this.mPlaySourceCategory != PlaySourceCategory.MoviesVideo || this.mVideoPlaylist == null) {
            loadNextVideoAsset();
        } else {
            resumeVideoAssetWhenNetworkError();
        }
    }

    protected void resumeVideoAssetWhenNetworkError() {
        LogUtil.d(" - Method start.");
        if (!(this.mXumoExoPlayer == null || this.mVideoPlaylist == null)) {
            if (this.mVideoPlaylist.getPlayingVideoAsset() != null) {
                this.mCurrentVideoAsset = this.mVideoPlaylist.getPlayingVideoAsset();
                if (!this.mCurrentVideoAsset.getAssetId().equals(this.mResumeVideoAssetId)) {
                    this.mResumeVideoAssetId = this.mCurrentVideoAsset.getAssetId();
                    this.mResumeVideoAssetStartTime = 0;
                }
                if (!(this.mXumoExoPlayer.getCurrentTimeline() == null || this.mXumoExoPlayer.getCurrentTimeline().isEmpty())) {
                    this.mResumeVideoAssetStartTime = this.mXumoExoPlayer.getCurrentPosition();
                }
                this.mCurrentVideoAsset.getAssetMetaData(new NoResponseListener() {
                    public void onCompletion() {
                        if (OnNowPlayerFragment.this.getHost() != null) {
                            if (OnNowPlayerFragment.this.mCurrentVideoAsset != null) {
                                OnNowPlayerFragment.this.mXumoExoPlayer.setUseController(false);
                                OnNowPlayerFragment.this.mXumoExoPlayer.prepare(OnNowPlayerFragment.this.mCurrentVideoAsset, OnNowPlayerFragment.this.mResumeVideoAssetStartTime);
                                OnNowPlayerFragment.this.mXumoExoPlayer.play();
                            }
                        }
                    }

                    public void onError() {
                        LogUtil.d("getAssetMetaData error.");
                        OnNowPlayerFragment.this.showNetworkErrorMessage(4);
                    }
                });
            }
        }
    }

    private void getGenreSelectorList() {
        XumoDataSync.getInstance().getGenre(new Listener() {
            public void onCompletion(Object obj) {
                OnNowPlayerFragment.this.mGenreList = (ArrayList) obj;
                if (UserPreferences.getInstance().getViewBoost() != null) {
                    OnNowPlayerFragment.this.playViewBoosterAsset();
                } else {
                    OnNowPlayerFragment.this.loadOnNowLives(true);
                }
            }

            public void onError() {
                LogUtil.w("getGenresList failed.");
                if (UserPreferences.getInstance().getViewBoost()) {
                    OnNowPlayerFragment.this.playViewBoosterAsset();
                } else {
                    OnNowPlayerFragment.this.loadOnNowLives(true);
                }
            }
        });
    }

    private void onUpdateDisplay() {
        LogUtil.d("onUpdateDisplay");
        this.mUpdateDisplayHandler.removeMessages(1);
        if (getHost() != null) {
            loadOnNowLives(false);
            this.mUpdateDisplayHandler.sendEmptyMessageDelayed(1, NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
        }
    }

    private Map<String, List<OnNowLive>> createOnNowLivesByGenreGroups(List<OnNowLive> list) {
        Map<String, List<OnNowLive>> hashMap = new HashMap();
        Map map = null;
        if (list != null) {
            for (OnNowLive onNowLive : list) {
                if (!(onNowLive == null || onNowLive.getChannelId() == null)) {
                    if (!onNowLive.getChannelId().isEmpty()) {
                        OnNowLive clone;
                        if (UserPreferences.getInstance().isFavorited(onNowLive.getChannelId())) {
                            clone = onNowLive.clone();
                            clone.setGenre(GENRE_FAVORITES);
                            clone.setGenreId(-2);
                            if (map == null) {
                                map = new HashMap();
                            }
                            map.put(clone.getChannelId(), clone);
                        }
                        if (onNowLive.isPopular()) {
                            clone = onNowLive.clone();
                            clone.setGenre(GENRE_MOST_POPULAR);
                            clone.setGenreId(-1);
                            if (hashMap.get(GENRE_MOST_POPULAR) == null) {
                                hashMap.put(GENRE_MOST_POPULAR, new ArrayList());
                                OnNowLive onNowLive2 = new OnNowLive();
                                for (int i = 0; i < 12; i++) {
                                    ((List) hashMap.get(GENRE_MOST_POPULAR)).add(onNowLive2);
                                }
                            }
                            if (clone.getChannelIndex() == 0) {
                                clone.setIsFirstItemOfGenre(true);
                            }
                            ((List) hashMap.get(GENRE_MOST_POPULAR)).set(clone.getChannelIndex(), clone);
                        }
                        String genre = onNowLive.getGenre();
                        if (!(genre == null || genre.isEmpty() || hashMap.containsKey(genre))) {
                            hashMap.put(genre, new ArrayList());
                        }
                        if (hashMap.get(genre) != null) {
                            if (((List) hashMap.get(genre)).size() == 0) {
                                onNowLive.setIsFirstItemOfGenre(true);
                            }
                            ((List) hashMap.get(genre)).add(onNowLive);
                        }
                    }
                }
            }
        }
        hashMap.put(GENRE_FAVORITES, new ArrayList());
        if (map != null) {
            list = UserPreferences.getInstance().getFavoriteChannelIds();
            if (list != null) {
                list = list.iterator();
                while (list.hasNext()) {
                    String str = (String) list.next();
                    if (map.containsKey(str)) {
                        ((List) hashMap.get(GENRE_FAVORITES)).add(map.get(str));
                    }
                }
            }
        }
        if (((List) hashMap.get(GENRE_FAVORITES)).size() == null) {
            ((List) hashMap.get(GENRE_FAVORITES)).add(createEmptyFavorite());
        }
        return hashMap;
    }

    private void updateOnNowLivesAndGenres(Map<String, List<OnNowLive>> map) {
        List arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        List list = (List) map.get(GENRE_FAVORITES);
        arrayList.add(GENRE_FAVORITES);
        arrayList2.addAll(list);
        list = (List) map.get(GENRE_MOST_POPULAR);
        if (list != null && list.size() > 0) {
            arrayList.add(GENRE_MOST_POPULAR);
            arrayList2.addAll(list);
        }
        Iterator it;
        if (this.mGenreList == null || this.mGenreList.size() <= 0) {
            for (String str : map.keySet()) {
                if (!GENRE_FAVORITES.equals(str)) {
                    if (!GENRE_MOST_POPULAR.equals(str)) {
                        List list2 = (List) map.get(str);
                        if (list2 != null && list2.size() > 0) {
                            arrayList.add(str);
                            arrayList2.addAll(list2);
                        }
                    }
                }
            }
        } else {
            it = this.mGenreList.iterator();
            while (it.hasNext()) {
                Genre genre = (Genre) it.next();
                String value = genre.getValue();
                List list3 = (List) map.get(genre.getValue());
                if (!(value == null || value.isEmpty() || list3 == null || list3.size() <= 0)) {
                    arrayList.add(value);
                    arrayList2.addAll(list3);
                }
            }
        }
        mOnNowLives = arrayList2;
        this.mChannelInfoRecyclerView.getListAdapter().update(mOnNowLives);
        if (this.mCurrentOnNowLive != null) {
            map = mOnNowLives.iterator();
            while (map.hasNext()) {
                OnNowLive onNowLive = (OnNowLive) map.next();
                if (this.mCurrentOnNowLive.getChannelId().equals(onNowLive.getChannelId())) {
                    if (this.mCurrentOnNowLive.getId().equals(onNowLive.getId()) == null) {
                        this.mCurrentOnNowLive = onNowLive.clone();
                    }
                }
            }
        }
        if (this.mGenreSelectorAdapter != null) {
            this.mGenreSelectorAdapter.updateGenres(arrayList);
        }
    }

    private void addOrDeleteOnNowLivesFavorites(int i, boolean z, boolean z2) {
        int size = mOnNowLives.size();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < size) {
            if (!GENRE_FAVORITES.equals(((OnNowLive) mOnNowLives.get(i2)).getGenre())) {
                break;
            }
            if (!(((OnNowLive) mOnNowLives.get(i2)).getChannelId() == null || ((OnNowLive) mOnNowLives.get(i2)).getChannelId().isEmpty())) {
                i3++;
            }
            if (!z && ((OnNowLive) mOnNowLives.get(i2)).getChannelId() == ((OnNowLive) mOnNowLives.get(i)).getChannelId()) {
                i4 = i2;
            }
            i2++;
        }
        if (z) {
            if (!z2) {
                UserPreferences.getInstance().addChannelToFavorites(((OnNowLive) mOnNowLives.get(i)).getChannelId());
                this.promptActionText.setText(true);
            }
            i = ((OnNowLive) mOnNowLives.get(i)).clone();
            i.setGenre(GENRE_FAVORITES);
            i.setGenreId(true);
            if (i3 == 0) {
                mOnNowLives.set(0, i);
                this.mChannelInfoRecyclerView.getListAdapter().set(0, i);
            } else {
                mOnNowLives.add(0, i);
                this.mChannelInfoRecyclerView.getListAdapter().add(0, i);
            }
        } else {
            if (!z2) {
                UserPreferences.getInstance().removeChannelFromFavorites(((OnNowLive) mOnNowLives.get(i)).getChannelId());
                this.promptActionText.setText(true);
            }
            if (i3 == 1) {
                mOnNowLives.set(0, createEmptyFavorite());
                this.mChannelInfoRecyclerView.getListAdapter().set(0, createEmptyFavorite());
            } else {
                mOnNowLives.remove(i4);
                this.mChannelInfoRecyclerView.getListAdapter().remove(i4);
            }
        }
        this.mChannelInfoRecyclerView.updateSelectedItem();
    }

    private OnNowLive createEmptyFavorite() {
        OnNowLive onNowLive = new OnNowLive();
        onNowLive.setGenre(GENRE_FAVORITES);
        onNowLive.setGenreId(-2);
        onNowLive.setChannelId("");
        return onNowLive;
    }

    private void loadOnNowLives(final boolean z) {
        LogUtil.d(" - Method start.");
        this.mXumoDataSync.getAllOnNow(new Listener() {
            public void onCompletion(Object obj) {
                if (OnNowPlayerFragment.this.getHost() != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("OnNowPlayerFragment mCurrentChannelId=");
                    stringBuilder.append(OnNowPlayerFragment.mCurrentChannelId);
                    LogUtil.d(stringBuilder.toString());
                    OnNowPlayerFragment.this.mGenresAvailable = false;
                    OnNowPlayerFragment.this.updateOnNowLivesAndGenres(OnNowPlayerFragment.this.createOnNowLivesByGenreGroups((ArrayList) obj));
                    OnNowPlayerFragment.this.mGenresAvailable = true;
                    if (z != null) {
                        obj = OnNowPlayerFragment.this.isOnNowLiveChannel(OnNowPlayerFragment.mCurrentChannelId);
                        if (!(obj != null || TextUtils.isEmpty(OnNowPlayerFragment.this.mUserPreferences.getLastPlayedChannelId()) || OnNowPlayerFragment.this.mUserPreferences.getLastPlayedChannelId().equals(OnNowPlayerFragment.mCurrentChannelId))) {
                            OnNowPlayerFragment.mCurrentChannelId = OnNowPlayerFragment.this.mUserPreferences.getLastPlayedChannelId();
                            obj = OnNowPlayerFragment.this.isOnNowLiveChannel(OnNowPlayerFragment.mCurrentChannelId);
                        }
                        if (obj == null && OnNowPlayerFragment.this.mIsTransionBrandPage == null) {
                            LogUtil.d("OnNowPlayerFragment mCurrentChannelId is not found.");
                            OnNowPlayerFragment.mCurrentChannelId = "";
                            OnNowPlayerFragment.this.mUserPreferences.removeLastPlayedChannelId();
                        }
                        if (OnNowPlayerFragment.this.mIsTransionBrandPage != null) {
                            new Handler().post(new Runnable() {
                                public void run() {
                                    OnNowPlayerFragment.this.mUpdateDisplayHandler.removeMessages(1);
                                    OnNowPlayerFragment.this.mXumoExoPlayer.removePlayerView();
                                    if (TextUtils.isEmpty(OnNowPlayerFragment.mCurrentChannelId)) {
                                        LogUtil.d("push notiication channelId is not found.");
                                    } else {
                                        OnNowPlayerFragment.this.mUserPreferences.setLastPlayedChannelId(OnNowPlayerFragment.mCurrentChannelId);
                                    }
                                    OnNowPlayerFragment.this.mIsTransionBrandPage = false;
                                }
                            });
                        } else {
                            OnNowPlayerFragment.this.loadOnNowDisplay();
                        }
                    }
                }
            }

            public void onError() {
                LogUtil.w("loadOnNowLives failed.");
            }
        });
    }

    private boolean isOnNowLiveChannel(String str) {
        if (!(TextUtils.isEmpty(str) || mOnNowLives == null)) {
            Iterator it = mOnNowLives.iterator();
            while (it.hasNext()) {
                if (str.equals(((OnNowLive) it.next()).getChannelId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void loadOnNowDisplay() {
        LogUtil.d(" - Method start.");
        if (!TextUtils.isEmpty(mCurrentChannelId)) {
            this.mXumoDataSync.getOnNow(mCurrentChannelId, new Listener() {
                public void onCompletion(Object obj) {
                    if (!(OnNowPlayerFragment.this.getHost() == null || OnNowPlayerFragment.this.isShowPlayerErrorMessage())) {
                        OnNowPlayerFragment.this.mCurrentOnNowLive = (OnNowLive) obj;
                        if (OnNowPlayerFragment.this.mCurrentLiveAsset == null && OnNowPlayerFragment.this.mXumoExoPlayer.ismIsAdDisplayedCopy() == null) {
                            OnNowPlayerFragment.this.loadLiveAssetPlayer(OnNowPlayerFragment.this.mCurrentOnNowLive, new Date(), Boolean.valueOf(false));
                        } else if (OnNowPlayerFragment.this.mCurrentOnNowLive != null) {
                            OnNowPlayerFragment.this.mXumoExoPlayer.setTitle(OnNowPlayerFragment.this.mCurrentOnNowLive.getTitle());
                        }
                    }
                }

                public void onError() {
                    LogUtil.d("getOnNow onError.");
                    if (OnNowPlayerFragment.this.getHost() != null) {
                        OnNowPlayerFragment.this.clearPlayer();
                        OnNowPlayerFragment.this.showPlayerErrorMessage(1);
                        if (OnNowPlayerFragment.this.mMainActivity != null) {
                            OnNowPlayerFragment.this.mMainActivity.clearDeepLinkInfo();
                        }
                        OnNowPlayerFragment.this.mUserPreferences.removeLastPlayedChannelId();
                    }
                }
            });
            if (this.mMainActivity != null && this.mMainActivity.isVizioDeepLink()) {
                Iterator it = mOnNowLives.iterator();
                while (it.hasNext()) {
                    if (((OnNowLive) it.next()).getChannelId().equals(mCurrentChannelId)) {
                        this.mMainActivity.clearDeepLinkInfo();
                        return;
                    }
                }
                LogUtil.d("vizio deeplink error. no channel.");
                clearPlayer();
                showPlayerErrorMessage(1);
                this.mMainActivity.clearDeepLinkInfo();
            }
        } else if (!(isShowPlayerErrorMessage() || mOnNowLives == null)) {
            int i = 0;
            int size = mOnNowLives.size();
            while (i < size) {
                if (((OnNowLive) mOnNowLives.get(i)).getChannelId().isEmpty()) {
                    i++;
                } else {
                    playOnNowLive((OnNowLive) mOnNowLives.get(i), i);
                    if (this.mChannelInfoRecyclerView != null) {
                        this.mChannelInfoRecyclerView.getListAdapter().setSelectedItemIndex(i);
                        this.mChannelInfoRecyclerView.updateSelectedItem();
                    }
                }
            }
        }
    }

    public void onKeyPress(int i, KeyEvent keyEvent) {
        if (this.oldKeyEvent == null || keyEvent.getKeyCode() != 4 || this.oldKeyEvent.getKeyCode() != 4 || keyEvent.getAction() != 0 || this.oldKeyEvent.getAction() != 0) {
            this.oldKeyEvent = keyEvent;
            if (isDebugPageVisible()) {
                this.debugFragment.setKeyCode(i);
            }
            if (!this.mAnimateOutStart) {
                if (!this.mAnimateOutDetailsStart) {
                    if (isWebPageVisible()) {
                        LogUtil.d("onKeyPress() for WebPage.");
                        if (keyEvent.getAction() == 1 && (i == 4 || i == 97)) {
                            if (this.webFragment.getWebView().canGoBack() != 0) {
                                this.webFragment.getWebView().goBack();
                            } else {
                                getChildFragmentManager().beginTransaction().remove(this.webFragment).addToBackStack(null).commitAllowingStateLoss();
                            }
                        }
                        return;
                    } else if (isMoviesUpNextPageVisible()) {
                        if (keyEvent.getAction() == 0) {
                            LogUtil.d("onKeyPress() for MoviesUpNextPage.");
                            this.moviesUpNextPageFragment.onKeyDown(i, keyEvent);
                        }
                        return;
                    } else if (isBrandPageVisible()) {
                        if (keyEvent.getAction() == 0) {
                            LogUtil.d("onKeyPress() for BrandPage.");
                            if (isMainNavigationPageVisible()) {
                                animateIn(1.0f);
                            }
                            this.tvBrandPageFragment.onKeyDown(i, keyEvent);
                        }
                        return;
                    } else if (isSettingsCaptioningPageVisible()) {
                        if (keyEvent.getAction() == 0) {
                            LogUtil.d("onKeyPress() for SettingsCaptioningPage.");
                            animateIn();
                            this.mSettingsCaptioningFragment.onKeyDown(i, keyEvent);
                        }
                        return;
                    } else if (isGenreListPageVisible() != 0) {
                        if (keyEvent.getAction() == 0) {
                            LogUtil.d("onKeyPress() for GenreListPage.");
                            animateIn();
                            onKeyPressForGenreListPage(keyEvent);
                        }
                        return;
                    } else if (isMainNavigationPageVisible() != 0) {
                        if (keyEvent.getAction() == 0) {
                            animateIn();
                            switch (this.mMainNavigationPageSelectState) {
                                case NavigationBar:
                                    LogUtil.d("onKeyPress() for NavigationBar.");
                                    onKeyPressForNavigationBar(keyEvent);
                                    break;
                                case OnNow:
                                    LogUtil.d("onKeyPress() for OnNow.");
                                    onKeyPressForOnNow(keyEvent);
                                    break;
                                case Movies:
                                    LogUtil.d("onKeyPress() for Movies.");
                                    onKeyPressForMovies(keyEvent);
                                    break;
                                case AllChannels:
                                    LogUtil.d("onKeyPress() for OnDemand.");
                                    onKeyPressForAllChannels(keyEvent);
                                    break;
                                case Settings:
                                    LogUtil.d("onKeyPress() for Settings.");
                                    if (this.isInputModel != 0) {
                                        onKeyPressForSettingsDebugMode(keyEvent);
                                        break;
                                    } else {
                                        onKeyPressForSettings(keyEvent);
                                        break;
                                    }
                                default:
                                    break;
                            }
                        }
                        return;
                    } else if (isPlayerControlsPageForLiveVisible() != 0) {
                        if (keyEvent.getAction() == 0) {
                            LogUtil.d("onKeyPress() for LivePlayerControlsPage.");
                            animateInDetails();
                            onKeyPressForLivePlayerControlsPage(keyEvent);
                        }
                        return;
                    } else if (isPlayerControlsPageForVodVisible() != 0) {
                        LogUtil.d("onKeyPress() for VodPlayerControlsPage.");
                        onKeyPressForVodPlayerControlsPage(keyEvent);
                        return;
                    } else {
                        if (this.mXumoExoPlayer == 0 || this.mXumoExoPlayer.ismIsAdDisplayedCopy() == 0) {
                            LogUtil.d("onKeyPress() for Player.");
                            onKeyPressForPlayer(keyEvent);
                        } else {
                            LogUtil.d("onKeyPress() is adPlaying.");
                        }
                        return;
                    }
                }
            }
            LogUtil.d("onKeyPress() mAnimateOutStart or mAnimateOutDetailsStart is true.");
        }
    }

    public boolean isShowNextAsset() {
        return this.mVideoPlaylist != null && this.mVideoPlaylist.hasNextVideo() && this.mVodHasPreButton;
    }

    public boolean isShowPreAsset() {
        return this.mVideoPlaylist != null && this.mVideoPlaylist.hasPrevVideo() && this.mVodHasNextButton;
    }

    public boolean isVodCcButton() {
        return this.mVodHasCcButton;
    }

    private void showPage(DisplayView displayView, Object... objArr) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" - Method start. displayView = ");
        stringBuilder.append(displayView);
        LogUtil.d(stringBuilder.toString());
        switch (displayView) {
            case PlayerControlsPageForLive:
                if (isPlayerControlsPageForVodVisible() != null) {
                    hidePage(DisplayView.PlayerControlsPageForVod);
                }
                if (this.mXumoExoPlayer == null || this.mXumoExoPlayer.ismIsAdDisplayedCopy() == null) {
                    if (this.onNowDetails != null) {
                        this.onNowDetails.setVisibility(0);
                        this.onNowDetails.requestFocus();
                    }
                    if (this.onNowDetailsRl != null) {
                        if (this.mShowOnNowDetailsPrompt != null) {
                            this.mShowOnNowDetailsPrompt = false;
                            this.onNowDetailsPrompt.setVisibility(0);
                            this.onNowDetailsInfo.setVisibility(8);
                            this.mMoreFromLy.setVisibility(8);
                        } else {
                            this.onNowDetailsPrompt.setVisibility(8);
                            this.onNowDetailsInfo.setVisibility(0);
                            this.mMoreFromLy.setVisibility(0);
                        }
                        this.onNowDetailsRl.setVisibility(0);
                        this.mBackToChannelInfoTv.setVisibility(8);
                        this.mMoreFromSelectLy.setBackgroundColor(0);
                        this.mMoreFromBottomLy.setBackgroundResource(R.color.black70);
                        this.mMoreFromSelectTitleTv.setTextColor(getResources().getColor(R.color.xumoGray));
                        this.mMoreFromSelectContentTv.setTextColor(-1);
                        this.mMainMenuSelectLy.setBackgroundColor(0);
                        this.mMainMenuBottomLy.setBackgroundResource(R.color.black70);
                        this.mMainMenuSelectTitleTv.setTextColor(getResources().getColor(R.color.xumoGray));
                        this.mMainMenuSelectContentTv.setTextColor(-1);
                    }
                    setCcButtonVisibility(true);
                    break;
                }
                return;
                break;
            case PlayerControlsPageForVod:
                if (isPlayerControlsPageForLiveVisible() != null) {
                    hidePage(DisplayView.PlayerControlsPageForLive);
                }
                if (this.mXumoExoPlayer == null || this.mXumoExoPlayer.ismIsAdDisplayedCopy() == null) {
                    this.mVodPlayerControlsSelectedStatus = 3;
                    updateVodPlayerControlsSelectedStatus(this.mVodPlayerControlsSelectedStatus);
                    if (this.mXumoExoPlayer.isControllerVisible() == null) {
                        this.mXumoExoPlayer.showController();
                    }
                    this.vodDetailsPrompt.setVisibility(8);
                    this.vodDetailsInfo.setVisibility(0);
                    this.mVodMoreFromLy.setVisibility(0);
                    setCcButtonVisibility(false);
                    break;
                }
                return;
            case MainNavigationPage:
                if (this.isResetMainNavigationDisplay != null) {
                    this.isResetMainNavigationDisplay = false;
                    if (isBrandPageVisible() != null) {
                        hidePage(DisplayView.BrandPage);
                    }
                    this.mCurrentSelectedStatus = 0;
                    if (this.mMainNavigationPageSelectState == MainNavigationPageSelectState.OnNow) {
                        this.tabSelectIndex = 0;
                        this.mCurrentSelectedStatus = 3;
                        showOnNow();
                        scrollToOnNowCurrentPosition();
                    }
                    if (this.mMainNavigationPageSelectState == MainNavigationPageSelectState.Movies) {
                        this.tabSelectIndex = 1;
                        showMovies();
                    } else {
                        this.mListItemIndexX = -1;
                    }
                    this.mSelectChannelsIndex = 0;
                    this.mMoveChannelsTitleIndex = -1;
                    this.mSelectChannelsTitleIndex = 0;
                    this.mSelectChannelsContentIndex = -1;
                    this.mGenreTitleTv.setVisibility(8);
                    if (this.mMainNavigationPageSelectState == MainNavigationPageSelectState.AllChannels) {
                        this.tabSelectIndex = 2;
                        this.mSelectChannelsIndex = 2;
                        for (displayView = null; displayView < this.mAllChannelsGenreList.size(); displayView++) {
                            objArr = null;
                            while (objArr < ((Genre) this.mAllChannelsGenreList.get(displayView)).getChannelIdList().size()) {
                                if (((String) ((Genre) this.mAllChannelsGenreList.get(displayView)).getChannelIdList().get(objArr)).equals(this.mCurrentVideoAsset.getChannelId())) {
                                    this.mSelectChannelsTitleIndex = displayView;
                                    this.mSelectChannelsContentIndex = objArr;
                                } else {
                                    objArr++;
                                }
                            }
                        }
                        showAllChannels(false);
                    }
                    if (this.mAllChannelsListAdapter != null) {
                        this.mAllChannelsListAdapter.setMoveItemIndex(this.mMoveChannelsTitleIndex);
                        this.mAllChannelsListAdapter.setSelectItemIndex(this.mSelectChannelsTitleIndex);
                    }
                    if (!(this.mChannelsTitleRv == null || this.mChannelsTitleRv.getAdapter() == null)) {
                        this.mChannelsTitleRv.getAdapter().notifyDataSetChanged();
                    }
                    if (this.mChannelsContentRv != null) {
                        this.mChannelsContentRv.scrollToPosition(this.mSelectChannelsTitleIndex);
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.mChannelsContentRv.getLayoutManager();
                        if (linearLayoutManager != null) {
                            if (this.mSelectChannelsContentIndex > 2) {
                                objArr = ((this.mSelectChannelsContentIndex / 3) * (-this.mItemHigh)) - 90;
                            } else {
                                objArr = (this.mSelectChannelsContentIndex / 3) * (-this.mItemHigh);
                            }
                            if (this.mSelectChannelsTitleIndex == this.mAllChannelsGenreList.size() - 1) {
                                this.mGenreTitleTv.setVisibility(0);
                                this.mGenreTitleTv.setText(((Genre) this.mAllChannelsGenreList.get(this.mSelectChannelsTitleIndex - 1)).getValue());
                            } else if (this.mSelectChannelsContentIndex < 3) {
                                this.mGenreTitleTv.setVisibility(8);
                            } else {
                                this.mGenreTitleTv.setVisibility(0);
                                this.mGenreTitleTv.setText(((Genre) this.mAllChannelsGenreList.get(this.mSelectChannelsTitleIndex)).getValue());
                            }
                            linearLayoutManager.scrollToPositionWithOffset(this.mSelectChannelsTitleIndex, objArr);
                        }
                        if (this.mChannelsContentRv.getAdapter() != null) {
                            this.mChannelsContentRv.getAdapter().notifyDataSetChanged();
                        }
                    }
                    if (this.mAllChannelsContentListAdapter != null) {
                        this.mAllChannelsContentListAdapter.setSelectItemIndex(-1);
                        if (this.mMainNavigationPageSelectState == MainNavigationPageSelectState.AllChannels) {
                            this.mAllChannelsContentListAdapter.setSelectItemIndex(this.mSelectChannelsTitleIndex);
                        }
                    }
                    this.mSettingIndex = 0;
                    this.isLicenses = false;
                }
                if (this.mNavigationContainer != null) {
                    this.mNavigationContainer.setVisibility(0);
                    this.mNavigationContainer.requestFocus();
                }
                updateControlGuideOnNow();
                break;
            case GenreListPage:
                if (this.mGenresAvailable != null) {
                    if (this.mGenreSelectorListLayout != null) {
                        this.mGenreSelectorListLayout.setVisibility(0);
                        new Handler().post(new Runnable() {
                            public void run() {
                                OnNowPlayerFragment.this.onScrolledAllChannelsGenre(OnNowPlayerFragment.this.mGenreSelectorList);
                            }
                        });
                    }
                    if (this.mControlGuideOnNow != null) {
                        this.mControlGuideOnNow.setVisibility(8);
                    }
                    if (this.mGenreSelectorList != null) {
                        this.mGenreSelectorList.requestFocus();
                        break;
                    }
                }
                break;
            case SettingsCaptioningPage:
                this.mSettingsCaptioningFragment = SettingsCaptioningFragment.newInstance(this);
                getChildFragmentManager().beginTransaction().add(R.id.web_playerview_frame, this.mSettingsCaptioningFragment, SettingsCaptioningFragment.TAG_SETTINGS_CAPTIONING).addToBackStack(null).commitAllowingStateLoss();
                break;
            case BrandPage:
                if (this.tvBrandPageFragment != null) {
                    if (this.mVideoPlaylist != null) {
                        this.tvBrandPageFragment.setVideoAssetId(this.mVideoPlaylist.getPlayingVideoAssetId());
                    }
                    this.tvBrandPageFragment.show();
                } else if (objArr != null && objArr.length > null && objArr[0] != null && TextUtils.isEmpty(objArr[0].toString()) == null) {
                    this.tvBrandPageFragment = TvBrandPageFragment.newInstance(this);
                    this.tvBrandPageFragment.setChannelId(objArr[0].toString());
                    if (!(this.mVideoPlaylist == null || this.mVideoPlaylist.getChannelId().equals(objArr[0].toString()) == null)) {
                        this.tvBrandPageFragment.setVideoAssetId(this.mVideoPlaylist.getPlayingVideoAssetId());
                    }
                    if (!(this.mXumoExoPlayer.isLive() == null || this.mXumoExoPlayer.getOnNowLive() == null || this.mXumoExoPlayer.getOnNowLive().getChannelId().equals(objArr[0].toString()) == null)) {
                        this.tvBrandPageFragment.setOnNowPlaying(true);
                    }
                    getChildFragmentManager().beginTransaction().add(R.id.web_playerview_frame, this.tvBrandPageFragment, TvBrandPageFragment.TAG_BRAND_PAGE).addToBackStack(null).commitAllowingStateLoss();
                }
                animateIn(0.01f);
                UserPreferences.getInstance().setToBrandScreen();
                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.PageView, null, null, this.mCurrentOnNowLive);
                break;
            case WebPage:
                this.webFragment = new WebFragment();
                getChildFragmentManager().beginTransaction().add(2131362534, this.webFragment, WebFragment.TAG_WEB).addToBackStack(null).commitAllowingStateLoss();
                break;
            case ExitDialog:
                if (this.mExitDialog == null) {
                    this.mExitDialog = new ExitDialog(getContext());
                    this.mExitDialog.setOnDismissListener(new OnDismissListener() {
                        public void onDismiss(DialogInterface dialogInterface) {
                            OnNowPlayerFragment.this.setBackgroundToGetFocus();
                        }
                    });
                }
                this.mExitDialog.show();
                setDefaultBackGdColor();
                break;
            case MoviesUpNextPage:
                if (objArr != null && objArr.length > null && objArr[0] != null && TextUtils.isEmpty(objArr[0].toString()) == null) {
                    this.moviesUpNextPageFragment = MoviesUpNextPageFragment.newInstance(this, (VideoAsset) objArr[0]);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.web_playerview_frame, this.moviesUpNextPageFragment, MoviesUpNextPageFragment.TAG_VOD_NEXT_PAGE).addToBackStack(null).commitAllowingStateLoss();
                }
                if (isBrandPageVisible() != null) {
                    hidePage(DisplayView.BrandPage);
                }
                if (isMainNavigationPageVisible() != null) {
                    hidePage(DisplayView.MainNavigationPage);
                }
                if (isPlayerControlsPageForVodVisible() != null) {
                    hidePage(DisplayView.PlayerControlsPageForVod);
                    break;
                }
                break;
            default:
                break;
        }
    }

    private void hidePage(DisplayView displayView) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" - Method start. displayView = ");
        stringBuilder.append(displayView);
        LogUtil.d(stringBuilder.toString());
        switch (displayView) {
            case PlayerControlsPageForLive:
                if (this.onNowDetails != null) {
                    this.onNowDetails.setVisibility(8);
                }
                if (this.onNowDetails != null) {
                    this.onNowDetails.clearFocus();
                }
                if (this.mPlayerContainerView != null) {
                    this.mPlayerContainerView.requestFocus();
                    return;
                }
                return;
            case PlayerControlsPageForVod:
                if (isPlayerControlsPageForVodVisible() != null) {
                    this.mXumoExoPlayer.hideController();
                    return;
                }
                return;
            case MainNavigationPage:
                if (isExitDialogVisible() != null) {
                    hidePage(DisplayView.ExitDialog);
                }
                if (isSettingsCaptioningPageVisible() != null) {
                    hidePage(DisplayView.SettingsCaptioningPage);
                }
                if (isGenreListPageVisible() != null) {
                    hidePage(DisplayView.GenreListPage);
                }
                if (isMainNavigationPageVisible() != null) {
                    if (this.mNavigationContainer != null) {
                        this.mNavigationContainer.setVisibility(8);
                        this.mNavigationContainer.clearFocus();
                    }
                    if (this.mPlayerContainerView != null) {
                        this.mPlayerContainerView.requestFocus();
                        return;
                    }
                    return;
                }
                return;
            case GenreListPage:
                if (this.mGenreSelectorListLayout != null) {
                    this.mGenreSelectorListLayout.setVisibility(8);
                }
                if (this.mGenreSelectorList != null) {
                    this.mGenreSelectorList.clearFocus();
                }
                updateControlGuideOnNow();
                return;
            case SettingsCaptioningPage:
                if (this.mSettingsCaptioningFragment != null) {
                    this.mSettingsCaptioningFragment.onFinish();
                    this.mSettingsCaptioningFragment = null;
                    return;
                }
                return;
            case BrandPage:
                if (this.tvBrandPageFragment != null) {
                    this.tvBrandPageFragment.onFinish();
                    this.tvBrandPageFragment = null;
                    return;
                }
                return;
            case ExitDialog:
                if (this.mExitDialog != null) {
                    this.mExitDialog.dismiss();
                    return;
                }
                return;
            case MoviesUpNextPage:
                if (this.moviesUpNextPageFragment != null) {
                    this.moviesUpNextPageFragment.onFinish();
                    this.moviesUpNextPageFragment = null;
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void isEmptyFavorite(boolean z) {
        if (z) {
            this.noFavoritesView.setVisibility(0);
            ((RelativeLayout.LayoutParams) this.mChannelInfoRecyclerView.getLayoutParams()).addRule(3, R.id.no_favorites_view);
            return;
        }
        this.noFavoritesView.setVisibility(8);
        ((RelativeLayout.LayoutParams) this.mChannelInfoRecyclerView.getLayoutParams()).addRule(3, R.id.on_now_tab);
    }

    private void createVideoPlaylist(Channel channel) {
        if (!(channel == null || channel.getChannelId() == null)) {
            if (channel.getCategories() != null) {
                ArrayList arrayList = new ArrayList();
                Iterator it = channel.getCategories().iterator();
                while (it.hasNext()) {
                    Category category = (Category) it.next();
                    Iterator it2 = category.getVideoAssets().iterator();
                    while (it2.hasNext()) {
                        VideoAsset videoAsset = (VideoAsset) it2.next();
                        if (!TextUtils.isEmpty(videoAsset.getAssetId())) {
                            videoAsset.setCategoryTitle(category.getTitle());
                            arrayList.add(videoAsset);
                        }
                    }
                }
                this.mVideoPlaylist = new VideoPlaylist(arrayList);
                if (UserPreferences.getInstance().getViewBoost()) {
                    this.mVideoPlaylist.setPlayMode(0);
                }
                this.mVideoPlaylist.setGenreId(channel.getGenreId());
                this.mVideoPlaylist.setGenreName(channel.getGenreName());
                this.mVideoPlaylist.setChannelId(channel.getChannelId());
                this.mVideoPlaylist.setChannelTitle(channel.getChannelTitle());
                this.mVideoPlaylist.setChannelDescription(channel.getDescriptionText());
            }
        }
    }

    private void createVideoPlaylistForFromBack(Channel channel) {
        if (!(channel == null || channel.getChannelId() == null)) {
            if (channel.getCategories() != null) {
                ArrayList arrayList = new ArrayList();
                Iterator it = channel.getCategories().iterator();
                Object obj = null;
                while (it.hasNext()) {
                    Category category = (Category) it.next();
                    Iterator it2 = category.getVideoAssets().iterator();
                    while (it2.hasNext()) {
                        VideoAsset videoAsset = (VideoAsset) it2.next();
                        if (!TextUtils.isEmpty(videoAsset.getAssetId())) {
                            if (this.mCurrentVideoAsset.getAssetId().equals(videoAsset.getAssetId())) {
                                obj = 1;
                            }
                            videoAsset.setCategoryTitle(category.getTitle());
                            arrayList.add(videoAsset);
                        }
                    }
                }
                if (obj == null) {
                    arrayList.add(0, this.mCurrentVideoAsset);
                }
                this.mVideoPlaylist = new VideoPlaylist(arrayList);
                if (UserPreferences.getInstance().getViewBoost()) {
                    this.mVideoPlaylist.setPlayMode(0);
                }
                this.mVideoPlaylist.setGenreId(channel.getGenreId());
                this.mVideoPlaylist.setGenreName(channel.getGenreName());
                this.mVideoPlaylist.setChannelId(channel.getChannelId());
                this.mVideoPlaylist.setChannelTitle(channel.getChannelTitle());
                this.mVideoPlaylist.setChannelDescription(channel.getDescriptionText());
            }
        }
    }

    private void onScrolledAllChannelsGenre(RecyclerView recyclerView) {
        ImageView imageView;
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int findFirstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        int findLastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        int itemCount = linearLayoutManager.getItemCount();
        if (recyclerView == this.mGenreSelectorList) {
            recyclerView = this.mOnNowGenreUp;
            imageView = this.mOnNowGenreDown;
        } else {
            recyclerView = this.mallChannelsGenreUp;
            imageView = this.mallChannelsGenreDown;
        }
        if (findFirstCompletelyVisibleItemPosition != 0) {
            recyclerView.setVisibility(0);
        } else {
            recyclerView.setVisibility(4);
        }
        if (findLastCompletelyVisibleItemPosition != itemCount - 1) {
            imageView.setVisibility(0);
        } else {
            imageView.setVisibility(4);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onKeyPressForPlayer(android.view.KeyEvent r6) {
        /*
        r5 = this;
        r0 = r6.getKeyCode();
        r1 = 4;
        r2 = 1;
        r3 = 0;
        if (r0 == r1) goto L_0x0120;
    L_0x0009:
        r4 = 85;
        if (r0 == r4) goto L_0x00dd;
    L_0x000d:
        switch(r0) {
            case 19: goto L_0x00b4;
            case 20: goto L_0x00b4;
            case 21: goto L_0x008b;
            case 22: goto L_0x008b;
            case 23: goto L_0x008b;
            default: goto L_0x0010;
        };
    L_0x0010:
        switch(r0) {
            case 89: goto L_0x0051;
            case 90: goto L_0x0018;
            default: goto L_0x0013;
        };
    L_0x0013:
        switch(r0) {
            case 96: goto L_0x008b;
            case 97: goto L_0x0120;
            default: goto L_0x0016;
        };
    L_0x0016:
        goto L_0x0177;
    L_0x0018:
        r0 = r5.isPlayerErrorMessageVisibility();
        if (r0 == 0) goto L_0x001f;
    L_0x001e:
        return;
    L_0x001f:
        r6 = r6.getAction();
        if (r6 != r2) goto L_0x0177;
    L_0x0025:
        r6 = r5.mXumoExoPlayer;
        r6 = r6.isLive();
        if (r6 != 0) goto L_0x0177;
    L_0x002d:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForVod;
        r0 = new java.lang.Object[r3];
        r5.showPage(r6, r0);
        r6 = r5.mHasCaption;
        if (r6 == 0) goto L_0x0043;
    L_0x0038:
        r6 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r6 = r6.getSubtitleSwitch();
        r5.checkVodCcButtonStyle(r3, r6);
    L_0x0043:
        r6 = r5.mXumoExoPlayer;
        r6.setVodBackOrFwd(r3);
        r5.mVodPlayerControlsSelectedStatus = r1;
        r6 = r5.mVodPlayerControlsSelectedStatus;
        r5.updateVodPlayerControlsSelectedStatus(r6);
        goto L_0x0177;
    L_0x0051:
        r0 = r5.isPlayerErrorMessageVisibility();
        if (r0 == 0) goto L_0x0058;
    L_0x0057:
        return;
    L_0x0058:
        r6 = r6.getAction();
        if (r6 != r2) goto L_0x0177;
    L_0x005e:
        r6 = r5.mXumoExoPlayer;
        r6 = r6.isLive();
        if (r6 != 0) goto L_0x0177;
    L_0x0066:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForVod;
        r0 = new java.lang.Object[r3];
        r5.showPage(r6, r0);
        r6 = r5.mHasCaption;
        if (r6 == 0) goto L_0x007c;
    L_0x0071:
        r6 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r6 = r6.getSubtitleSwitch();
        r5.checkVodCcButtonStyle(r3, r6);
    L_0x007c:
        r6 = r5.mXumoExoPlayer;
        r6.setVodBackOrFwd(r2);
        r6 = 2;
        r5.mVodPlayerControlsSelectedStatus = r6;
        r6 = r5.mVodPlayerControlsSelectedStatus;
        r5.updateVodPlayerControlsSelectedStatus(r6);
        goto L_0x0177;
    L_0x008b:
        r0 = r5.isPlayerErrorMessageVisibility();
        if (r0 == 0) goto L_0x0092;
    L_0x0091:
        return;
    L_0x0092:
        r6 = r6.getAction();
        if (r6 != 0) goto L_0x0177;
    L_0x0098:
        r6 = r5.mXumoExoPlayer;
        r6 = r6.isLive();
        if (r6 == 0) goto L_0x00ab;
    L_0x00a0:
        r6 = r5.mXumoExoPlayer;
        r6 = r6.getOnNowLive();
        r5.openOnNowDetailsPage(r6);
        goto L_0x0177;
    L_0x00ab:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForVod;
        r0 = new java.lang.Object[r3];
        r5.showPage(r6, r0);
        goto L_0x0177;
    L_0x00b4:
        r0 = r5.isPlayerErrorMessageVisibility();
        if (r0 == 0) goto L_0x00bb;
    L_0x00ba:
        return;
    L_0x00bb:
        r6 = r6.getAction();
        if (r6 != 0) goto L_0x0177;
    L_0x00c1:
        r6 = r5.mXumoExoPlayer;
        r6 = r6.isLive();
        if (r6 == 0) goto L_0x00d4;
    L_0x00c9:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.OnNow;
        r5.mMainNavigationPageSelectState = r6;
        r5.isResetMainNavigationDisplay = r2;
        r5.animateIn();
        goto L_0x0177;
    L_0x00d4:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForVod;
        r0 = new java.lang.Object[r3];
        r5.showPage(r6, r0);
        goto L_0x0177;
    L_0x00dd:
        r0 = r5.isPlayerErrorMessageVisibility();
        if (r0 == 0) goto L_0x00e4;
    L_0x00e3:
        return;
    L_0x00e4:
        r6 = r6.getAction();
        if (r6 != r2) goto L_0x0177;
    L_0x00ea:
        r6 = r5.mXumoExoPlayer;
        r6 = r6.isLive();
        if (r6 != 0) goto L_0x0177;
    L_0x00f2:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForVod;
        r0 = new java.lang.Object[r3];
        r5.showPage(r6, r0);
        r6 = r5.mHasCaption;
        if (r6 == 0) goto L_0x0108;
    L_0x00fd:
        r6 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r6 = r6.getSubtitleSwitch();
        r5.checkVodCcButtonStyle(r3, r6);
    L_0x0108:
        r6 = 3;
        r5.mVodPlayerControlsSelectedStatus = r6;
        r6 = r5.mXumoExoPlayer;
        r6 = r6.getPlayWhenReady();
        if (r6 == 0) goto L_0x0117;
    L_0x0113:
        r5.pause();
        goto L_0x011a;
    L_0x0117:
        r5.play();
    L_0x011a:
        r6 = r5.mVodPlayerControlsSelectedStatus;
        r5.updateVodPlayerControlsSelectedStatus(r6);
        goto L_0x0177;
    L_0x0120:
        r6 = r6.getAction();
        if (r6 != 0) goto L_0x0177;
    L_0x0126:
        r6 = r5.mPlaySourceCategory;
        r0 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.PlaySourceCategory.OnNowLive;
        if (r6 != r0) goto L_0x0136;
    L_0x012c:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.OnNow;
        r5.mMainNavigationPageSelectState = r6;
        r5.isResetMainNavigationDisplay = r2;
        r5.animateIn();
        goto L_0x0177;
    L_0x0136:
        r6 = r5.mPlaySourceCategory;
        r0 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.PlaySourceCategory.MoviesVideo;
        if (r6 != r0) goto L_0x0146;
    L_0x013c:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.Movies;
        r5.mMainNavigationPageSelectState = r6;
        r5.isResetMainNavigationDisplay = r2;
        r5.animateIn();
        goto L_0x0177;
    L_0x0146:
        r6 = r5.tvBrandPageFragment;
        if (r6 != 0) goto L_0x0170;
    L_0x014a:
        r6 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r6 = r6.getViewBoost();
        if (r6 == 0) goto L_0x0166;
    L_0x0154:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.BrandPage;
        r0 = new java.lang.Object[r2];
        r1 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r1 = r1.getViewBoostChannelId();
        r0[r3] = r1;
        r5.showPage(r6, r0);
        goto L_0x0177;
    L_0x0166:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.AllChannels;
        r5.mMainNavigationPageSelectState = r6;
        r5.isResetMainNavigationDisplay = r2;
        r5.animateIn();
        goto L_0x0177;
    L_0x0170:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.BrandPage;
        r0 = new java.lang.Object[r3];
        r5.showPage(r6, r0);
    L_0x0177:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.fragmenttv.OnNowPlayerFragment.onKeyPressForPlayer(android.view.KeyEvent):void");
    }

    private void loadLiveAssetPlayer(final OnNowLive onNowLive, Date date, Boolean bool) {
        LogUtil.d(" - Method start.");
        if (mOnNowLives != null) {
            if (mOnNowLives.size() != null) {
                if (date != null) {
                    bool = onNowLive.getChannelId();
                    int i = 0;
                    int size = mOnNowLives.size();
                    while (i < size) {
                        if (bool.equals(((OnNowLive) mOnNowLives.get(i)).getChannelId())) {
                            playOnNowLive(onNowLive, i, date);
                            if (isMainNavigationPageOnNowVisible() == null) {
                                scrollToOnNowCurrentPosition();
                            }
                        } else {
                            i++;
                        }
                    }
                } else {
                    this.mXumoDataSync.getNextLiveAsset(onNowLive.getChannelId(), new Listener() {
                        public void onCompletion(Object obj) {
                            if (OnNowPlayerFragment.this.getHost() != null) {
                                OnNowPlayerFragment.this.playLiveAsset(onNowLive, (LiveAsset) obj, null);
                            }
                        }

                        public void onError() {
                            LogUtil.d("getNextLiveAsset onError.");
                            if (OnNowPlayerFragment.this.getHost() != null) {
                                OnNowPlayerFragment.this.clearPlayer();
                                OnNowPlayerFragment.this.showPlayerErrorMessage(1);
                                OnNowPlayerFragment.this.mUserPreferences.removeLastPlayedChannelId();
                            }
                        }
                    });
                }
                return;
            }
        }
        LogUtil.d("onNowLives is empty.");
    }

    private void sendClickBeaconMethod(OnNowLive onNowLive, int i) {
        if (UserPreferences.getInstance().getFavoriteChannelIds() != null) {
            int size = UserPreferences.getInstance().getFavoriteChannelIds().size();
            if (i >= 0 && i < size) {
                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.FavoriteChannelClicked, null, null, onNowLive);
            } else if (size > i || i >= size + 5) {
                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.LiveChannelClicked, null, null, onNowLive);
            } else {
                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.FeaturedChannelClicked, null, null, onNowLive);
            }
        }
    }

    private void playOnNowLive(OnNowLive onNowLive, int i) {
        LogUtil.d(" - Method start.");
        sendClickBeaconMethod(onNowLive, i);
        if (isShowPlayerErrorMessage() || !this.mXumoExoPlayer.isLive() || TextUtils.isEmpty(mCurrentChannelId) || !mCurrentChannelId.equals(onNowLive.getChannelId())) {
            this.mPlaySourceCategory = PlaySourceCategory.OnNowLive;
            this.mUserPreferences.setLastPlayedChannelId(onNowLive.getChannelId());
            this.mShowOnNowDetailsPrompt = true;
            this.mXumoExoPlayer.stop();
            playOnNowLive(onNowLive, i, null);
            this.mXumoExoPlayer.updatePlayerController(true);
            return;
        }
        LogUtil.d("same channelId.");
    }

    private void playOnNowLive(final OnNowLive onNowLive, int i, Date date) {
        LogUtil.d(" - Method start.");
        if (onNowLive != null) {
            this.mCurrentOnNowLive = onNowLive;
            this.mCurrentOnNowLiveIndex = i;
            if (isMainNavigationPageOnNowVisible() == 0) {
                this.mChannelInfoRecyclerView.setSelectedItemIndex(this.mCurrentOnNowLiveIndex);
                this.genreSelectorTitle.setText(((OnNowLive) mOnNowLives.get(this.mCurrentOnNowLiveIndex)).getGenre());
            }
            mCurrentChannelId = this.mCurrentOnNowLive.getChannelId();
            this.mChannelInfoRecyclerView.getListAdapter().notifyDataSetChanged();
            i = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            if (date == null) {
                i.setTime(new Date());
            } else {
                i.setTime(date);
            }
            this.mXumoDataSync.getLiveAsset(i.getTime(), onNowLive.getChannelId(), new Listener() {
                public void onCompletion(Object obj) {
                    if (OnNowPlayerFragment.this.getHost() != null) {
                        LiveAsset liveAsset = (LiveAsset) obj;
                        OnNowPlayerFragment.this.mHasCaption = liveAsset.ismHasCaption();
                        OnNowPlayerFragment.this.setCcButtonVisibility(true);
                        OnNowPlayerFragment.this.playLiveAsset(onNowLive, liveAsset, i.getTime());
                    }
                }

                public void onError() {
                    LogUtil.d("loadOnNowDisplay failed.");
                    if (OnNowPlayerFragment.this.getHost() != null) {
                        OnNowPlayerFragment.this.clearPlayer();
                        OnNowPlayerFragment.this.showPlayerErrorMessage(1);
                        OnNowPlayerFragment.this.mUserPreferences.removeLastPlayedChannelId();
                    }
                }
            });
        }
    }

    public void loadVideoAssetPlayer(VideoAsset videoAsset) {
        if (!(getHost() == null || this.mXumoExoPlayer == null)) {
            if (videoAsset != null) {
                this.mLoadAssetFlag = false;
                this.vodDetailsPrompt.setVisibility(0);
                this.vodDetailsInfo.setVisibility(8);
                this.mVodMoreFromLy.setVisibility(8);
                if (this.mXumoExoPlayer.isLive()) {
                    clearPlayer();
                } else {
                    this.mXumoExoPlayer.stop();
                }
                this.mXumoExoPlayer.setControllerVisibilityListener(this);
                this.mCurrentVideoAsset = videoAsset;
                hidePlayerErrorMessage();
                videoAsset.getAssetMetaData(new NoResponseListener() {
                    public void onCompletion() {
                        if (OnNowPlayerFragment.this.getHost() != null) {
                            if (OnNowPlayerFragment.this.mCurrentVideoAsset != null) {
                                OnNowPlayerFragment.this.mHasCaption = OnNowPlayerFragment.this.mCurrentVideoAsset.ismHasCaption();
                                OnNowPlayerFragment.this.setCcButtonVisibility(false);
                                if (OnNowPlayerFragment.this.mPlaySourceCategory == PlaySourceCategory.BrandPageVideo) {
                                    OnNowPlayerFragment.this.mXumoExoPlayer.setTitle(OnNowPlayerFragment.this.mCurrentVideoAsset.getTitle());
                                    OnNowPlayerFragment.this.mExoPlayerImage.setVisibility(0);
                                    XumoImageUtil.setChannelImage(OnNowPlayerFragment.this.getContext(), OnNowPlayerFragment.this.mCurrentVideoAsset.getChannelId(), OnNowPlayerFragment.this.mExoPlayerImage, 320, 180);
                                } else {
                                    OnNowPlayerFragment.this.mXumoExoPlayer.setMoviesTitle(OnNowPlayerFragment.this.mCurrentVideoAsset.getTitle());
                                    OnNowPlayerFragment.this.mExoPlayerImage.setVisibility(8);
                                }
                                OnNowPlayerFragment.this.mXumoExoPlayer.setDescription(OnNowPlayerFragment.this.mCurrentVideoAsset.getDescriptionText());
                                OnNowPlayerFragment.this.mXumoExoPlayer.prepare(OnNowPlayerFragment.this.mCurrentVideoAsset);
                                OnNowPlayerFragment.this.mXumoExoPlayer.play();
                                OnNowPlayerFragment.this.mXumoExoPlayer.updatePlayerController(false);
                                OnNowPlayerFragment.this.tvVodPlayEndFlag = true;
                                OnNowPlayerFragment.this.mVodPlayerControlsSelectedStatus = 3;
                                OnNowPlayerFragment.this.updateVodPlayerControlsSelectedStatus(OnNowPlayerFragment.this.mVodPlayerControlsSelectedStatus);
                                OnNowPlayerFragment.this.mLoadAssetFlag = true;
                                if (OnNowPlayerFragment.this.debugFragment != null) {
                                    OnNowPlayerFragment.this.debugFragment.initData();
                                }
                            }
                        }
                    }

                    public void onError() {
                        if (OnNowPlayerFragment.this.getHost() != null) {
                            LogUtil.d(MediaRouteProviderProtocol.SERVICE_DATA_ERROR);
                            OnNowPlayerFragment.this.showPlayerErrorMessage(2);
                            OnNowPlayerFragment.this.tvVodPlayEndFlag = true;
                            OnNowPlayerFragment.this.mLoadAssetFlag = true;
                        }
                    }
                });
                if (this.mVideoPlaylist == null || !this.mVideoPlaylist.hasNextVideo()) {
                    this.mVodUpNextFatherLy.setVisibility(8);
                } else {
                    this.mVodUpNextFatherLy.setVisibility(0);
                    VideoAsset nextVideoAsset = this.mVideoPlaylist.getNextVideoAsset();
                    XumoImageUtil.setAssetThumbnailImage(getContext(), nextVideoAsset.getAssetId(), videoAsset.getChannelId(), this.mVodUpNextIv, 320, 180);
                    this.mVodUpNextTimeTv.setText(nextVideoAsset.getRunTimeString());
                    if (TextUtils.isEmpty(nextVideoAsset.getCategoryTitle()) == null) {
                        this.mVodUpNextTitleTv.setText(nextVideoAsset.getCategoryTitle());
                    }
                    if (TextUtils.isEmpty(nextVideoAsset.getTitle()) == null) {
                        this.mVodUpNextContentTv.setText(nextVideoAsset.getTitle());
                    }
                }
                if (this.mPlaySourceCategory != PlaySourceCategory.MoviesVideo) {
                    this.mVodMoreFromTileLy.setVisibility(0);
                    if (this.mCurrentVideoAsset != null) {
                        XumoImageUtil.setChannelTitleImage(getContext(), this.mCurrentVideoAsset.getChannelId(), this.mVodMoreFromIv, 320, 180);
                        if (this.mVideoPlaylist == null || TextUtils.isEmpty(this.mVideoPlaylist.getGenreName()) != null) {
                            this.mVodMoreFromTitleTv.setText("NO GENRE");
                        } else {
                            this.mVodMoreFromTitleTv.setText(this.mVideoPlaylist.getGenreName());
                        }
                        if (this.mVideoPlaylist == null || TextUtils.isEmpty(this.mVideoPlaylist.getChannelDescription()) != null) {
                            this.mVodMoreFromContentTv.setText("NO DESCRIPTIONS");
                        } else {
                            this.mVodMoreFromContentTv.setText(this.mVideoPlaylist.getChannelDescription());
                        }
                    }
                } else {
                    this.mVodMoreFromTileLy.setVisibility(8);
                }
                if (this.mPlaySourceCategory == PlaySourceCategory.MoviesVideo) {
                    this.mVodMainMenuIv.setImageResource(R.drawable.morefrom_movies);
                    this.mVodMainMenuTitleTv.setText("MOVIES");
                } else if (this.mPlaySourceCategory == PlaySourceCategory.BrandPageVideo) {
                    this.mVodMainMenuIv.setImageResource(R.drawable.morefrom_channels);
                    this.mVodMainMenuTitleTv.setText("ON DEMAND");
                }
            }
        }
    }

    private void playLiveAsset(OnNowLive onNowLive, LiveAsset liveAsset, Date date) {
        LogUtil.d(" - Method start.");
        sendClickBeaconMethod(onNowLive, mOnNowLives.indexOf(onNowLive));
        this.mVideoPlaylist = null;
        this.mCurrentVideoAsset = liveAsset;
        hidePlayerErrorMessage();
        this.mXumoExoPlayer.setControllerVisibilityListener(null);
        this.mCurrentOnNowLive = onNowLive;
        this.mCurrentLiveAsset = liveAsset;
        if (date != null) {
            long startTimeDiff = XumoUtil.getStartTimeDiff(date, liveAsset);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("date.getTime()=");
            stringBuilder.append(date.getTime());
            LogUtil.d(stringBuilder.toString());
            date = new StringBuilder();
            date.append("liveAsset start=");
            date.append(liveAsset.getStart());
            LogUtil.d(date.toString());
            date = new StringBuilder();
            date.append("liveAsset end=");
            date.append(liveAsset.getEnd());
            LogUtil.d(date.toString());
            date = new StringBuilder();
            date.append("liveAsset startTime=");
            date.append(startTimeDiff);
            LogUtil.d(date.toString());
            this.mXumoExoPlayer.prepare(liveAsset, startTimeDiff);
        } else {
            this.mXumoExoPlayer.prepare(liveAsset);
        }
        date = new StringBuilder();
        date.append("liveAsset url=");
        date.append(liveAsset.getUrl());
        LogUtil.d(date.toString());
        this.mXumoExoPlayer.setTitle(onNowLive.getTitle());
        this.mXumoExoPlayer.setOnNowLive(onNowLive);
        this.mXumoExoPlayer.play();
        this.mXumoExoPlayer.updatePlayerController(true);
        this.mUserPreferences.setLastPlayedChannelId(onNowLive.getChannelId());
        if (this.debugFragment != null) {
            this.debugFragment.initData();
        }
    }

    private void loadNextVideoAsset() {
        if (this.mVideoPlaylist != null && this.mVideoPlaylist.notifyNextVideo() && this.mLoadAssetFlag) {
            loadVideoAssetPlayer(this.mVideoPlaylist.getPlayingVideoAsset());
            return;
        }
        if (UserPreferences.getInstance().getViewBoost()) {
            UserPreferences.getInstance().setViewBoost(false);
            this.mMainNavigationPageSelectState = MainNavigationPageSelectState.OnNow;
            this.isResetMainNavigationDisplay = true;
            animateIn();
        }
        this.mPlaySourceCategory = PlaySourceCategory.OnNowLive;
        loadOnNowLives(true);
    }

    private void loadPreVideoAsset() {
        if (this.mVideoPlaylist != null && this.mVideoPlaylist.notifyPrevVideo() && this.mLoadAssetFlag) {
            loadVideoAssetPlayer(this.mVideoPlaylist.getPlayingVideoAsset());
        }
    }

    private void clearPlayer() {
        this.mCurrentLiveAsset = null;
        this.mCurrentVideoAsset = null;
        this.mPlayVodCount = 0;
        if (!isMainNavigationPageOnNowVisible()) {
            this.mChannelInfoRecyclerView.setSelectedItemIndex(0);
            if (mOnNowLives != null && mOnNowLives.size() > 0) {
                this.genreSelectorTitle.setText(((OnNowLive) mOnNowLives.get(0)).getGenre());
            }
        }
        if (!isShowPlayerErrorMessage()) {
            this.mXumoExoPlayer.stop();
            this.mXumoExoPlayer.setTitle("");
        }
    }

    private void animateOut(int i) {
        if (this.mPlayerContainerView != null) {
            if (isMainNavigationPageVisible()) {
                this.mNavigationContainer.animate().alpha(0.0f).setDuration(0).setStartDelay((long) i).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animator) {
                        if (OnNowPlayerFragment.this.mCancelAnimation == null) {
                            OnNowPlayerFragment.this.mAnimateOutStart = true;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (OnNowPlayerFragment.this.mCancelAnimation == null) {
                            OnNowPlayerFragment.this.mCancelAnimation = true;
                            OnNowPlayerFragment.this.hidePage(DisplayView.MainNavigationPage);
                            OnNowPlayerFragment.this.mAnimateOutStart = false;
                        }
                    }
                });
            }
        }
    }

    private void animateIn() {
        animateIn(1.0f);
    }

    private void animateIn(float f) {
        if (this.mNavigationContainer != null) {
            if (this.mPlayerContainerView != null) {
                if (!this.mAnimateOutStart) {
                    this.mCancelAnimation = true;
                    this.mNavigationContainer.setAlpha(f);
                    if (isMainNavigationPageVisible()) {
                        this.mNavigationContainer.animate().cancel();
                        this.mCancelAnimation = false;
                        animateOut(4.2039E-41f);
                        return;
                    }
                    showPage(DisplayView.MainNavigationPage, new Object[0]);
                    this.mNavigationContainer.animate().alpha(f).setDuration(0).setStartDelay(0).setListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            OnNowPlayerFragment.this.mCancelAnimation = false;
                            OnNowPlayerFragment.this.animateOut(OnNowPlayerFragment.FADE_OUT_ANIMATION_DELAY);
                        }
                    });
                }
            }
        }
    }

    private boolean isMainNavigationPageVisible() {
        return this.mNavigationContainer != null && this.mNavigationContainer.getVisibility() == 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onKeyPressForNavigationBar(android.view.KeyEvent r6) {
        /*
        r5 = this;
        r6 = r6.getKeyCode();
        r0 = 4;
        r1 = 0;
        if (r6 == r0) goto L_0x0188;
    L_0x0008:
        r2 = 3;
        r3 = 1;
        switch(r6) {
            case 19: goto L_0x017b;
            case 20: goto L_0x016e;
            case 21: goto L_0x018b;
            case 22: goto L_0x005e;
            case 23: goto L_0x0012;
            default: goto L_0x000d;
        };
    L_0x000d:
        switch(r6) {
            case 96: goto L_0x0012;
            case 97: goto L_0x0188;
            default: goto L_0x0010;
        };
    L_0x0010:
        goto L_0x018b;
    L_0x0012:
        r6 = r5.tabSelectIndex;
        if (r6 != 0) goto L_0x001b;
    L_0x0016:
        r5.showOnNow();
        goto L_0x018b;
    L_0x001b:
        r6 = r5.tabSelectIndex;
        if (r6 != r3) goto L_0x003e;
    L_0x001f:
        r5.mListItemIndexY = r1;
        r5.mListItemIndexX = r1;
        r6 = 0;
        r5.mMoviesListInfo = r6;
        r5.showMovies();
        r6 = new com.xumo.xumo.model.Channel;
        r0 = MOVIES_CHANNEL_ID;
        r6.<init>(r0);
        r5.mMovieChannel = r6;
        r5.getMoviesChannelInfomation();
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.Movies;
        r5.mMainNavigationPageSelectState = r6;
        r5.setBackgroundToGetFocus();
        goto L_0x018b;
    L_0x003e:
        r6 = r5.tabSelectIndex;
        r4 = 2;
        if (r6 != r4) goto L_0x0048;
    L_0x0043:
        r5.showAllChannels(r3);
        goto L_0x018b;
    L_0x0048:
        r6 = r5.tabSelectIndex;
        if (r6 != r2) goto L_0x0051;
    L_0x004c:
        r5.showSetting();
        goto L_0x018b;
    L_0x0051:
        r6 = r5.tabSelectIndex;
        if (r6 != r0) goto L_0x018b;
    L_0x0055:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.ExitDialog;
        r0 = new java.lang.Object[r1];
        r5.showPage(r6, r0);
        goto L_0x018b;
    L_0x005e:
        r6 = r5.mChannelInfoRecyclerView;
        r6 = r6.getVisibility();
        if (r6 != 0) goto L_0x008d;
    L_0x0066:
        r6 = mOnNowLives;
        if (r6 == 0) goto L_0x018b;
    L_0x006a:
        r6 = mOnNowLives;
        r6 = r6.size();
        if (r6 <= 0) goto L_0x018b;
    L_0x0072:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.OnNow;
        r5.mMainNavigationPageSelectState = r6;
        r6 = r5.mCurrentSelectedStatus;
        if (r6 != 0) goto L_0x0085;
    L_0x007a:
        r5.mCurrentSelectedStatus = r2;
        r6 = r5.mChannelInfoRecyclerView;
        r6 = r6.getListAdapter();
        r6.notifySelectedItemChanged();
    L_0x0085:
        r5.setDefaultBackGdColor();
        r5.updateControlGuideOnNow();
        goto L_0x018b;
    L_0x008d:
        r6 = r5.mLlMovies;
        r6 = r6.getVisibility();
        if (r6 != 0) goto L_0x00e0;
    L_0x0095:
        r6 = r5.mMoviesListInfo;
        if (r6 == 0) goto L_0x018b;
    L_0x0099:
        r6 = r5.mMovieChannel;
        r6 = r6.getCategories();
        if (r6 == 0) goto L_0x018b;
    L_0x00a1:
        r6 = r5.mMovieChannel;
        r6 = r6.getCategories();
        r6 = r6.get(r1);
        r6 = (com.xumo.xumo.model.Category) r6;
        r6 = r6.getVideoAssets();
        if (r6 == 0) goto L_0x018b;
    L_0x00b3:
        r6 = r5.mMovieChannel;
        r6 = r6.getCategories();
        r6 = r6.get(r1);
        r6 = (com.xumo.xumo.model.Category) r6;
        r6 = r6.getVideoAssets();
        r6 = r6.size();
        if (r6 <= 0) goto L_0x018b;
    L_0x00c9:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.Movies;
        r5.mMainNavigationPageSelectState = r6;
        r6 = r5.mListItemIndexX;
        r6 = r6 + r3;
        r5.mListItemIndexX = r6;
        r5.setDefaultBackGdColor();
        r6 = r5.genreRecyclerView;
        r6 = r6.getAdapter();
        r6.notifyDataSetChanged();
        goto L_0x018b;
    L_0x00e0:
        r6 = r5.mAllChannelsLy;
        r6 = r6.getVisibility();
        if (r6 != 0) goto L_0x012f;
    L_0x00e8:
        r6 = r5.mAllChannelsGenreList;
        if (r6 == 0) goto L_0x018b;
    L_0x00ec:
        r6 = r5.mAllChannelsGenreList;
        r6 = r6.size();
        if (r6 <= 0) goto L_0x018b;
    L_0x00f4:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.AllChannels;
        r5.mMainNavigationPageSelectState = r6;
        r5.setDefaultBackGdColor();
        r5.mSelectChannelsIndex = r3;
        r6 = r5.mMoveChannelsTitleIndex;
        if (r6 >= 0) goto L_0x0103;
    L_0x0101:
        r5.mMoveChannelsTitleIndex = r1;
    L_0x0103:
        r6 = r5.mAllChannelsListAdapter;
        r0 = r5.mMoveChannelsTitleIndex;
        r6.setMoveItemIndex(r0);
        r6 = r5.mAllChannelsListAdapter;
        r0 = r5.mSelectChannelsTitleIndex;
        r6.setSelectItemIndex(r0);
        r6 = r5.mChannelsTitleRv;
        r0 = r5.mSelectChannelsTitleIndex;
        r6.scrollToPosition(r0);
        r6 = new android.os.Handler;
        r6.<init>();
        r0 = new com.xumo.xumo.fragmenttv.OnNowPlayerFragment$15;
        r0.<init>();
        r6.post(r0);
        r6 = r5.mChannelsTitleRv;
        r6 = r6.getAdapter();
        r6.notifyDataSetChanged();
        goto L_0x018b;
    L_0x012f:
        r6 = r5.mSettingLy;
        r6 = r6.getVisibility();
        if (r6 != 0) goto L_0x018b;
    L_0x0137:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.Settings;
        r5.mMainNavigationPageSelectState = r6;
        r5.setDefaultBackGdColor();
        r6 = r5.mDefaultTv;
        r0 = r5.getResources();
        r1 = 2131099913; // 0x7f060109 float:1.7812193E38 double:1.052903255E-314;
        r0 = r0.getColor(r1);
        r6.setTextColor(r0);
        r6 = r5.mDefaultSwitch;
        r6 = r6.isChecked();
        if (r6 == 0) goto L_0x015d;
    L_0x0156:
        r6 = r5.mAppTv;
        r0 = -1;
        r6.setTextColor(r0);
        goto L_0x018b;
    L_0x015d:
        r6 = r5.mAppTv;
        r0 = r5.getResources();
        r1 = 2131099890; // 0x7f0600f2 float:1.7812146E38 double:1.0529032435E-314;
        r0 = r0.getColor(r1);
        r6.setTextColor(r0);
        goto L_0x018b;
    L_0x016e:
        r6 = r5.tabSelectIndex;
        if (r6 >= r0) goto L_0x018b;
    L_0x0172:
        r6 = r5.tabSelectIndex;
        r6 = r6 + r3;
        r5.tabSelectIndex = r6;
        r5.setBackgroundToGetFocus();
        goto L_0x018b;
    L_0x017b:
        r6 = r5.tabSelectIndex;
        if (r6 <= 0) goto L_0x018b;
    L_0x017f:
        r6 = r5.tabSelectIndex;
        r6 = r6 - r3;
        r5.tabSelectIndex = r6;
        r5.setBackgroundToGetFocus();
        goto L_0x018b;
    L_0x0188:
        r5.animateOut(r1);
    L_0x018b:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.fragmenttv.OnNowPlayerFragment.onKeyPressForNavigationBar(android.view.KeyEvent):void");
    }

    private void setDefaultBackGdColor() {
        this.mRlonNow.setBackgroundResource(R.drawable.main_icon_guide_normal);
        this.mRlchannel.setBackgroundResource(R.drawable.main_icon_channels_normal);
        this.mRlmovies.setBackgroundResource(R.drawable.main_icon_movies_normal);
        this.mRlsearch.setBackgroundResource(R.drawable.search_icon_off);
        this.mRlsettings.setBackgroundResource(R.drawable.main_icon_settings_normal);
        this.mRlexit.setBackgroundResource(R.drawable.main_icon_exit_normal);
        this.mRlonNowText.setTextColor(getResources().getColor(R.color.xumoWhite));
        this.mRlmoviesText.setTextColor(getResources().getColor(R.color.xumoWhite));
        this.mRlchannelText.setTextColor(getResources().getColor(R.color.xumoWhite));
        this.mRlsearchText.setTextColor(getResources().getColor(R.color.xumoWhite));
        this.mRlsettingsText.setTextColor(getResources().getColor(R.color.xumoWhite));
        this.mRlexitText.setTextColor(getResources().getColor(R.color.xumoWhite));
        if (this.tabSelectIndex == 4 && isExitDialogVisible()) {
            this.mRlexit.setBackgroundResource(R.drawable.main_icon_exit_selected);
            this.mRlexitText.setTextColor(getResources().getColor(R.color.xumoBlue));
        }
        if (this.mChannelInfoRecyclerView.getVisibility() == 0) {
            this.mRlonNow.setBackgroundResource(R.drawable.main_icon_guide_selected);
            this.mRlonNowText.setTextColor(getResources().getColor(R.color.xumoBlue));
        } else if (this.mLlMovies.getVisibility() == 0) {
            this.mRlmovies.setBackgroundResource(R.drawable.main_icon_movies_selected);
            this.mRlmoviesText.setTextColor(getResources().getColor(R.color.xumoBlue));
        } else if (this.mAllChannelsLy.getVisibility() == 0) {
            this.mRlchannel.setBackgroundResource(R.drawable.main_icon_channels_selected);
            this.mRlchannelText.setTextColor(getResources().getColor(R.color.xumoBlue));
        } else if (this.mSettingLy.getVisibility() == 0) {
            this.mRlsettings.setBackgroundResource(R.drawable.main_icon_settings_selected);
            this.mRlsettingsText.setTextColor(getResources().getColor(R.color.xumoBlue));
        }
    }

    private void setBackgroundToGetFocus() {
        setDefaultBackGdColor();
        switch (this.tabSelectIndex) {
            case 0:
                if (this.mCurrentSelectedStatus == 0 && this.mMainNavigationPageSelectState == MainNavigationPageSelectState.NavigationBar) {
                    this.mRlonNow.setBackgroundResource(R.drawable.main_icon_guide_hover);
                    this.mRlonNowText.setTextColor(getResources().getColor(R.color.xumoBlue));
                    return;
                }
                return;
            case 1:
                if (this.mListItemIndexX == -1 && this.mMainNavigationPageSelectState == MainNavigationPageSelectState.NavigationBar) {
                    this.mRlmovies.setBackgroundResource(R.drawable.main_icon_movies_hover);
                    this.mRlmoviesText.setTextColor(getResources().getColor(R.color.xumoBlue));
                    return;
                }
                return;
            case 2:
                if (this.mSelectChannelsIndex == 0 && this.mMainNavigationPageSelectState == MainNavigationPageSelectState.NavigationBar) {
                    this.mRlchannel.setBackgroundResource(R.drawable.main_icon_channels_hover);
                    this.mRlchannelText.setTextColor(getResources().getColor(R.color.xumoBlue));
                    return;
                }
                return;
            case 3:
                if (this.mMainNavigationPageSelectState == MainNavigationPageSelectState.NavigationBar) {
                    this.mRlsettings.setBackgroundResource(R.drawable.main_icon_settings_hover);
                    this.mRlsettingsText.setTextColor(getResources().getColor(R.color.xumoBlue));
                    return;
                }
                return;
            case 4:
                if (this.mMainNavigationPageSelectState == MainNavigationPageSelectState.NavigationBar) {
                    this.mRlexit.setBackgroundResource(R.drawable.main_icon_exit_hover);
                    this.mRlexitText.setTextColor(getResources().getColor(R.color.xumoBlue));
                    return;
                }
                return;
            default:
                return;
        }
    }

    private boolean isMainNavigationPageOnNowVisible() {
        return this.mNavigationContainer != null && this.mNavigationContainer.getVisibility() == 0 && this.mChannelInfoRecyclerView != null && this.mChannelInfoRecyclerView.getVisibility() == 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onKeyPressForOnNow(android.view.KeyEvent r6) {
        /*
        r5 = this;
        r6 = r6.getKeyCode();
        r0 = 4;
        r1 = 0;
        if (r6 == r0) goto L_0x02b5;
    L_0x0008:
        r0 = 82;
        if (r6 == r0) goto L_0x0276;
    L_0x000c:
        r0 = 100;
        if (r6 == r0) goto L_0x0276;
    L_0x0010:
        r0 = -1;
        r2 = 3;
        r3 = 1;
        switch(r6) {
            case 19: goto L_0x0255;
            case 20: goto L_0x0234;
            case 21: goto L_0x01d9;
            case 22: goto L_0x0176;
            case 23: goto L_0x001b;
            default: goto L_0x0016;
        };
    L_0x0016:
        switch(r6) {
            case 96: goto L_0x001b;
            case 97: goto L_0x02b5;
            default: goto L_0x0019;
        };
    L_0x0019:
        goto L_0x02b8;
    L_0x001b:
        r6 = mOnNowLives;
        if (r6 == 0) goto L_0x02b8;
    L_0x001f:
        r6 = mOnNowLives;
        r6 = r6.size();
        if (r6 <= 0) goto L_0x02b8;
    L_0x0027:
        r6 = r5.mCurrentSelectedStatus;
        if (r6 != r3) goto L_0x0094;
    L_0x002b:
        r6 = r5.mChannelInfoRecyclerView;
        r6 = r6.getSelectedItemIndex();
        r0 = "Favorites";
        r2 = mOnNowLives;
        r2 = r2.get(r6);
        r2 = (com.xumo.xumo.model.OnNowLive) r2;
        r2 = r2.getGenre();
        r0 = r0.equals(r2);
        if (r0 == 0) goto L_0x0058;
    L_0x0045:
        r0 = mOnNowLives;
        r0 = r0.get(r6);
        r0 = (com.xumo.xumo.model.OnNowLive) r0;
        r0 = r0.getChannelId();
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x0058;
    L_0x0057:
        return;
    L_0x0058:
        r0 = mOnNowLives;
        r0 = r0.get(r6);
        r0 = (com.xumo.xumo.model.OnNowLive) r0;
        r0 = r0.getGenre();
        r2 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r4 = mOnNowLives;
        r4 = r4.get(r6);
        r4 = (com.xumo.xumo.model.OnNowLive) r4;
        r4 = r4.getChannelId();
        r2 = r2.isFavorited(r4);
        if (r2 == 0) goto L_0x007e;
    L_0x007a:
        r5.addOrDeleteOnNowLivesFavorites(r6, r1, r1);
        goto L_0x0081;
    L_0x007e:
        r5.addOrDeleteOnNowLivesFavorites(r6, r3, r1);
    L_0x0081:
        r5.setFavoritesAndMostPopularGenre();
        r6 = r5.mChannelsContentRv;
        r6 = r6.getAdapter();
        r6.notifyDataSetChanged();
        r6 = r5.genreSelectorTitle;
        r6.setText(r0);
        goto L_0x02b8;
    L_0x0094:
        r6 = r5.mCurrentSelectedStatus;
        r0 = 2;
        if (r6 != r0) goto L_0x00e5;
    L_0x0099:
        r6 = r5.mChannelInfoRecyclerView;
        r6 = r6.getSelectedItemIndex();
        r0 = "Favorites";
        r2 = mOnNowLives;
        r2 = r2.get(r6);
        r2 = (com.xumo.xumo.model.OnNowLive) r2;
        r2 = r2.getGenre();
        r0 = r0.equals(r2);
        if (r0 == 0) goto L_0x00c6;
    L_0x00b3:
        r0 = mOnNowLives;
        r0 = r0.get(r6);
        r0 = (com.xumo.xumo.model.OnNowLive) r0;
        r0 = r0.getChannelId();
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x00c6;
    L_0x00c5:
        return;
    L_0x00c6:
        r0 = mOnNowLives;
        r0 = r0.get(r6);
        if (r0 == 0) goto L_0x02b8;
    L_0x00ce:
        r0 = mOnNowLives;
        r6 = r0.get(r6);
        r6 = (com.xumo.xumo.model.OnNowLive) r6;
        r6 = r6.getChannelId();
        r0 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.BrandPage;
        r2 = new java.lang.Object[r3];
        r2[r1] = r6;
        r5.showPage(r0, r2);
        goto L_0x02b8;
    L_0x00e5:
        r6 = r5.mCurrentSelectedStatus;
        if (r6 != r2) goto L_0x02b8;
    L_0x00e9:
        r6 = r5.mChannelInfoRecyclerView;
        r6 = r6.getSelectedItemIndex();
        r0 = "Favorites";
        r2 = mOnNowLives;
        r2 = r2.get(r6);
        r2 = (com.xumo.xumo.model.OnNowLive) r2;
        r2 = r2.getGenre();
        r0 = r0.equals(r2);
        if (r0 == 0) goto L_0x0116;
    L_0x0103:
        r0 = mOnNowLives;
        r0 = r0.get(r6);
        r0 = (com.xumo.xumo.model.OnNowLive) r0;
        r0 = r0.getChannelId();
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x0116;
    L_0x0115:
        return;
    L_0x0116:
        r0 = mOnNowLives;
        r0 = r0.get(r6);
        if (r0 == 0) goto L_0x02b8;
    L_0x011e:
        r5.mCurrentOnNowLiveIndex = r6;
        r0 = mOnNowLives;
        r0 = r0.get(r6);
        r0 = (com.xumo.xumo.model.OnNowLive) r0;
        r0 = r0.getChannelId();
        r2 = mCurrentChannelId;
        r0 = r0.equals(r2);
        if (r0 == 0) goto L_0x0144;
    L_0x0134:
        r0 = r5.mXumoExoPlayer;
        r0 = r0.isLive();
        if (r0 != 0) goto L_0x013d;
    L_0x013c:
        goto L_0x0144;
    L_0x013d:
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.MainNavigationPage;
        r5.hidePage(r6);
        goto L_0x02b8;
    L_0x0144:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getViewBoost();
        if (r0 == 0) goto L_0x0155;
    L_0x014e:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0.setViewBoost(r1);
    L_0x0155:
        r0 = mOnNowLives;
        r1 = r5.mCurrentOnNowLiveIndex;
        r0 = r0.get(r1);
        r0 = (com.xumo.xumo.model.OnNowLive) r0;
        r1 = r5.mCurrentOnNowLiveIndex;
        r5.playOnNowLive(r0, r1);
        r0 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.MainNavigationPage;
        r5.hidePage(r0);
        r0 = mOnNowLives;
        r6 = r0.get(r6);
        r6 = (com.xumo.xumo.model.OnNowLive) r6;
        r5.openOnNowDetailsPage(r6);
        goto L_0x02b8;
    L_0x0176:
        r6 = r5.mCurrentSelectedStatus;
        if (r6 >= r2) goto L_0x018d;
    L_0x017a:
        r6 = r5.mCurrentSelectedStatus;
        r6 = r6 + r3;
        r5.mCurrentSelectedStatus = r6;
        r6 = r5.mChannelInfoRecyclerView;
        r6 = r6.getListAdapter();
        r6.notifySelectedItemChanged();
        r5.updateControlGuideOnNow();
        goto L_0x02b8;
    L_0x018d:
        r6 = mOnNowLives;
        if (r6 == 0) goto L_0x02b8;
    L_0x0191:
        r6 = mOnNowLives;
        r6 = r6.size();
        if (r6 <= 0) goto L_0x02b8;
    L_0x0199:
        r6 = r5.mChannelInfoRecyclerView;
        if (r6 == 0) goto L_0x02b8;
    L_0x019d:
        r6 = r5.mChannelInfoRecyclerView;
        r6 = r6.getSelectedItemIndex();
        if (r6 < 0) goto L_0x02b8;
    L_0x01a5:
        r6 = r5.mGenreSelectorAdapter;
        r0 = mOnNowLives;
        r2 = r5.mChannelInfoRecyclerView;
        r2 = r2.getSelectedItemIndex();
        r0 = r0.get(r2);
        r0 = (com.xumo.xumo.model.OnNowLive) r0;
        r0 = r0.getGenre();
        r6.setSelectedGenreIndex(r0);
        r6 = r5.mGenreSelectorList;
        r0 = r5.mGenreSelectorList;
        r0 = r0.getSelectedItemIndex();
        r6.scrollToPosition(r0);
        r6 = r5.mGenreSelectorList;
        r6 = r6.getAdapter();
        r6.notifyDataSetChanged();
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.GenreListPage;
        r0 = new java.lang.Object[r1];
        r5.showPage(r6, r0);
        goto L_0x02b8;
    L_0x01d9:
        r6 = r5.mCurrentSelectedStatus;
        if (r6 <= 0) goto L_0x0222;
    L_0x01dd:
        r6 = mOnNowLives;
        if (r6 == 0) goto L_0x0214;
    L_0x01e1:
        r6 = mOnNowLives;
        r6 = r6.size();
        if (r6 <= 0) goto L_0x0214;
    L_0x01e9:
        r6 = r5.mChannelInfoRecyclerView;
        r6 = r6.getSelectedItemIndex();
        if (r6 != 0) goto L_0x0214;
    L_0x01f1:
        r6 = mOnNowLives;
        r6 = r6.get(r1);
        r6 = (com.xumo.xumo.model.OnNowLive) r6;
        r6 = r6.getChannelId();
        if (r6 == 0) goto L_0x0211;
    L_0x01ff:
        r6 = mOnNowLives;
        r6 = r6.get(r1);
        r6 = (com.xumo.xumo.model.OnNowLive) r6;
        r6 = r6.getChannelId();
        r6 = r6.isEmpty();
        if (r6 == 0) goto L_0x0214;
    L_0x0211:
        r5.mCurrentSelectedStatus = r1;
        goto L_0x0219;
    L_0x0214:
        r6 = r5.mCurrentSelectedStatus;
        r6 = r6 - r3;
        r5.mCurrentSelectedStatus = r6;
    L_0x0219:
        r6 = r5.mChannelInfoRecyclerView;
        r6 = r6.getListAdapter();
        r6.notifySelectedItemChanged();
    L_0x0222:
        r6 = r5.mCurrentSelectedStatus;
        if (r6 != 0) goto L_0x022f;
    L_0x0226:
        r6 = r5.mMainNavigationPageSelectState;
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.NavigationBar;
        r5.mMainNavigationPageSelectState = r6;
        r5.setBackgroundToGetFocus();
    L_0x022f:
        r5.updateControlGuideOnNow();
        goto L_0x02b8;
    L_0x0234:
        r6 = r5.mChannelInfoRecyclerView;
        if (r6 == 0) goto L_0x0251;
    L_0x0238:
        r6 = r5.mChannelInfoRecyclerView;
        r6 = r6.selectNextItem();
        if (r6 <= r0) goto L_0x0251;
    L_0x0240:
        r0 = r5.genreSelectorTitle;
        r1 = mOnNowLives;
        r6 = r1.get(r6);
        r6 = (com.xumo.xumo.model.OnNowLive) r6;
        r6 = r6.getGenre();
        r0.setText(r6);
    L_0x0251:
        r5.updateControlGuideOnNow();
        goto L_0x02b8;
    L_0x0255:
        r6 = r5.mChannelInfoRecyclerView;
        if (r6 == 0) goto L_0x0272;
    L_0x0259:
        r6 = r5.mChannelInfoRecyclerView;
        r6 = r6.selectPrevItem();
        if (r6 <= r0) goto L_0x0272;
    L_0x0261:
        r0 = r5.genreSelectorTitle;
        r1 = mOnNowLives;
        r6 = r1.get(r6);
        r6 = (com.xumo.xumo.model.OnNowLive) r6;
        r6 = r6.getGenre();
        r0.setText(r6);
    L_0x0272:
        r5.updateControlGuideOnNow();
        goto L_0x02b8;
    L_0x0276:
        r6 = mOnNowLives;
        if (r6 == 0) goto L_0x02b8;
    L_0x027a:
        r6 = mOnNowLives;
        r6 = r6.size();
        if (r6 <= 0) goto L_0x02b8;
    L_0x0282:
        r6 = r5.mGenreSelectorAdapter;
        r0 = mOnNowLives;
        r2 = r5.mChannelInfoRecyclerView;
        r2 = r2.getSelectedItemIndex();
        r0 = r0.get(r2);
        r0 = (com.xumo.xumo.model.OnNowLive) r0;
        r0 = r0.getGenre();
        r6.setSelectedGenreIndex(r0);
        r6 = r5.mGenreSelectorList;
        r0 = r5.mGenreSelectorList;
        r0 = r0.getSelectedItemIndex();
        r6.scrollToPosition(r0);
        r6 = r5.mGenreSelectorList;
        r6 = r6.getAdapter();
        r6.notifyDataSetChanged();
        r6 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.GenreListPage;
        r0 = new java.lang.Object[r1];
        r5.showPage(r6, r0);
        goto L_0x02b8;
    L_0x02b5:
        r5.animateOut(r1);
    L_0x02b8:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.fragmenttv.OnNowPlayerFragment.onKeyPressForOnNow(android.view.KeyEvent):void");
    }

    private void showOnNow() {
        if (this.mChannelInfoRecyclerView.getVisibility() == 8) {
            this.mAllChannelsLy.setVisibility(8);
            this.mLlMovies.setVisibility(8);
            this.mSettingLy.setVisibility(8);
            this.tvLogo.setText(R.string.guide_logo);
            this.mChannelInfoTitle.setVisibility(0);
            this.mNavigationContainer.setBackgroundColor(getResources().getColor(R.color.main_menu_background_color));
            this.mChannelInfoRecyclerView.setVisibility(0);
        }
        if (mOnNowLives != null && mOnNowLives.size() > 0) {
            if (this.mChannelInfoRecyclerView.getSelectedItemIndex() == 0 && (((OnNowLive) mOnNowLives.get(0)).getChannelId() == null || ((OnNowLive) mOnNowLives.get(0)).getChannelId().isEmpty())) {
                isEmptyFavorite(true);
                this.noFavoritesView.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_selected_white));
            } else {
                isEmptyFavorite(false);
            }
        }
        if (mOnNowLives == null || mOnNowLives.size() <= 0) {
            setBackgroundToGetFocus();
            updateControlGuideOnNow();
        } else {
            this.mMainNavigationPageSelectState = MainNavigationPageSelectState.OnNow;
            if (this.mCurrentSelectedStatus == 0) {
                this.mCurrentSelectedStatus = 3;
                this.mChannelInfoRecyclerView.getListAdapter().notifySelectedItemChanged();
            }
            setDefaultBackGdColor();
            updateControlGuideOnNow();
        }
        this.mChannelInfoRecyclerView.updateSelectedItem();
        this.mUpdateDisplayHandler.sendEmptyMessage(1);
        UserPreferences.getInstance().setToGuideScreen();
        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.PageView, null, null, this.mCurrentOnNowLive);
    }

    private void updateControlGuideOnNow() {
        if (!(mOnNowLives == null || mOnNowLives.size() == 0 || this.mChannelInfoRecyclerView == null)) {
            if (this.mChannelInfoRecyclerView.getSelectedItemIndex() >= 0) {
                switch (this.mCurrentSelectedStatus) {
                    case 0:
                        this.mControlGuideOnNow.setVisibility(8);
                        break;
                    case 1:
                        this.mControlGuideOnNow.setVisibility(0);
                        this.guideOkLinearLayout.setVisibility(0);
                        String str = "";
                        if (!(mOnNowLives == null || mOnNowLives.size() == 0)) {
                            str = ((OnNowLive) mOnNowLives.get(this.mChannelInfoRecyclerView.getSelectedItemIndex())).getChannelId();
                        }
                        if (UserPreferences.getInstance().isFavorited(str)) {
                            this.promptActionText.setText(R.string.control_guide_unfavorite);
                        } else {
                            this.promptActionText.setText(R.string.control_guide_favorite);
                        }
                        this.guideSelectGenreLinearLayout.setVisibility(4);
                        break;
                    case 2:
                        this.mControlGuideOnNow.setVisibility(0);
                        this.guideOkLinearLayout.setVisibility(0);
                        this.promptActionText.setText("Channel Library");
                        this.guideSelectGenreLinearLayout.setVisibility(4);
                        break;
                    case 3:
                        this.mControlGuideOnNow.setVisibility(0);
                        if (GENRE_FAVORITES.equals(((OnNowLive) mOnNowLives.get(this.mChannelInfoRecyclerView.getSelectedItemIndex())).getGenre()) && ((OnNowLive) mOnNowLives.get(this.mChannelInfoRecyclerView.getSelectedItemIndex())).getChannelId().isEmpty()) {
                            this.guideOkLinearLayout.setVisibility(4);
                        } else {
                            this.guideOkLinearLayout.setVisibility(0);
                            this.promptActionText.setText(R.string.control_guide_watch_now);
                        }
                        this.guideSelectGenreLinearLayout.setVisibility(0);
                        break;
                    default:
                        break;
                }
                return;
            }
        }
        this.mControlGuideOnNow.setVisibility(8);
    }

    private void scrollToOnNowCurrentPosition() {
        LogUtil.d(" - Method start.");
        if (this.mChannelInfoRecyclerView != null) {
            Object obj = null;
            if (mOnNowLives != null && this.mCurrentOnNowLiveIndex >= 0 && this.mCurrentOnNowLiveIndex < mOnNowLives.size()) {
                obj = ((OnNowLive) mOnNowLives.get(this.mCurrentOnNowLiveIndex)).getChannelId();
                this.genreSelectorTitle.setText(((OnNowLive) mOnNowLives.get(this.mCurrentOnNowLiveIndex)).getGenre());
            }
            if (!TextUtils.isEmpty(mCurrentChannelId) && !mCurrentChannelId.equals(r0)) {
                int size = mOnNowLives.size();
                for (int i = 0; i < size; i++) {
                    if (mCurrentChannelId.equals(((OnNowLive) mOnNowLives.get(i)).getChannelId())) {
                        this.mCurrentOnNowLiveIndex = i;
                        this.genreSelectorTitle.setText(((OnNowLive) mOnNowLives.get(this.mCurrentOnNowLiveIndex)).getGenre());
                        break;
                    }
                }
            }
            this.mChannelInfoRecyclerView.selectItem(this.mCurrentOnNowLiveIndex);
        }
    }

    private void validateViewLayout() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.mChannelInfoRecyclerView.getLayoutManager();
        int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        if (findFirstVisibleItemPosition == -1 && findLastVisibleItemPosition == -1) {
            this.mChannelInfoRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    OnNowPlayerFragment.this.mChannelInfoRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    OnNowPlayerFragment.this.scrollToOnNowCurrentPosition();
                    OnNowPlayerFragment.this.validateViewLayout();
                }
            });
        } else {
            validateViewLayout(findFirstVisibleItemPosition, findLastVisibleItemPosition);
        }
    }

    private void validateViewLayout(int i, int i2) {
        if (i < i2 && i2 < mOnNowLives.size()) {
            this.mFavoritesViewed.clear();
            this.mFeaturedViewed.clear();
            this.mLivesViewed.clear();
            int size = UserPreferences.getInstance().getFavoriteChannelIds() != null ? UserPreferences.getInstance().getFavoriteChannelIds().size() : 0;
            while (i < i2) {
                if (!((OnNowLive) mOnNowLives.get(i)).isEmptyFavorite()) {
                    if (i < size) {
                        this.mFavoritesViewed.add(((OnNowLive) mOnNowLives.get(i)).getChannelId());
                    } else if (size > i || i >= size + 5) {
                        this.mLivesViewed.add(((OnNowLive) mOnNowLives.get(i)).getChannelId());
                    } else {
                        this.mFeaturedViewed.add(((OnNowLive) mOnNowLives.get(i)).getChannelId());
                    }
                }
                i++;
            }
            if (this.mFavoritesViewed.isEmpty() == 0) {
                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.FavoriteChannelsViewed, null, (String[]) this.mFavoritesViewed.toArray(new String[0]));
            }
            if (this.mFeaturedViewed.isEmpty() == 0) {
                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.FeaturedChannelsViewed, null, (String[]) this.mFeaturedViewed.toArray(new String[0]));
            }
            if (this.mLivesViewed.isEmpty() == 0) {
                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.LiveChannelsViewed, null, (String[]) this.mLivesViewed.toArray(new String[0]));
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onKeyPressForMovies(android.view.KeyEvent r5) {
        /*
        r4 = this;
        r5 = r5.getKeyCode();
        r0 = 4;
        r1 = 0;
        if (r5 == r0) goto L_0x0278;
    L_0x0008:
        r0 = 1;
        switch(r5) {
            case 19: goto L_0x0223;
            case 20: goto L_0x0182;
            case 21: goto L_0x014d;
            case 22: goto L_0x012a;
            case 23: goto L_0x0011;
            default: goto L_0x000c;
        };
    L_0x000c:
        switch(r5) {
            case 96: goto L_0x0011;
            case 97: goto L_0x0278;
            default: goto L_0x000f;
        };
    L_0x000f:
        goto L_0x027b;
    L_0x0011:
        r5 = r4.mMovieChannel;
        if (r5 == 0) goto L_0x027b;
    L_0x0015:
        r5 = r4.mMovieChannel;
        r5 = r5.getCategories();
        if (r5 == 0) goto L_0x027b;
    L_0x001d:
        r5 = r4.mListItemIndexY;
        if (r5 < 0) goto L_0x027b;
    L_0x0021:
        r5 = r4.mListItemIndexY;
        r2 = r4.mMovieChannel;
        r2 = r2.getCategories();
        r2 = r2.size();
        if (r5 >= r2) goto L_0x027b;
    L_0x002f:
        r5 = r4.mMovieChannel;
        r5 = r5.getCategories();
        r2 = r4.mListItemIndexY;
        r5 = r5.get(r2);
        r5 = (com.xumo.xumo.model.Category) r5;
        if (r5 == 0) goto L_0x00c5;
    L_0x003f:
        r2 = r5.getVideoAssets();
        if (r2 == 0) goto L_0x00c5;
    L_0x0045:
        r2 = r5.getVideoAssets();
        r2 = r2.size();
        r3 = r4.mListItemIndexX;
        if (r2 <= r3) goto L_0x00c5;
    L_0x0051:
        r2 = r5.getVideoAssets();
        r3 = r4.mListItemIndexX;
        r2 = r2.get(r3);
        r2 = (com.xumo.xumo.model.VideoAsset) r2;
        r2 = r2.getAssetId();
        r2 = r2.isEmpty();
        if (r2 != 0) goto L_0x00c5;
    L_0x0067:
        r2 = r4.mListItemIndexX;
        if (r2 < 0) goto L_0x00c5;
    L_0x006b:
        r2 = r4.mListItemIndexX;
        r3 = r5.getVideoAssets();
        r3 = r3.size();
        if (r2 >= r3) goto L_0x00c5;
    L_0x0077:
        r0 = 3;
        r4.mVodPlayerControlsSelectedStatus = r0;
        r5 = r5.getVideoAssets();
        r0 = r4.mListItemIndexX;
        r5 = r5.get(r0);
        r5 = (com.xumo.xumo.model.VideoAsset) r5;
        r0 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.PlaySourceCategory.MoviesVideo;
        r4.mPlaySourceCategory = r0;
        r0 = r4.mMovieChannel;
        r4.createVideoPlaylist(r0);
        r0 = r4.mVideoPlaylist;
        if (r0 == 0) goto L_0x00b9;
    L_0x0093:
        r0 = r4.mVideoPlaylist;
        r5 = r5.getAssetId();
        r5 = r0.notifyStartVideo(r5);
        if (r5 == 0) goto L_0x00b9;
    L_0x009f:
        r5 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r5 = r5.getViewBoost();
        if (r5 == 0) goto L_0x00b0;
    L_0x00a9:
        r5 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r5.setViewBoost(r1);
    L_0x00b0:
        r5 = r4.mVideoPlaylist;
        r5 = r5.getPlayingVideoAsset();
        r4.loadVideoAssetPlayer(r5);
    L_0x00b9:
        r5 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.MainNavigationPage;
        r4.hidePage(r5);
        r5 = r4.mVodPlayerControlsSelectedStatus;
        r4.updateVodPlayerControlsSelectedStatus(r5);
        goto L_0x027b;
    L_0x00c5:
        if (r5 == 0) goto L_0x027b;
    L_0x00c7:
        r1 = r5.getVideoAssets();
        if (r1 == 0) goto L_0x027b;
    L_0x00cd:
        r1 = r5.getVideoAssets();
        r1 = r1.size();
        r2 = r4.mListItemIndexX;
        if (r1 <= r2) goto L_0x027b;
    L_0x00d9:
        r1 = r5.getVideoAssets();
        r2 = r4.mListItemIndexX;
        r1 = r1.get(r2);
        r1 = (com.xumo.xumo.model.VideoAsset) r1;
        r1 = r1.getAssetId();
        r1 = r1.isEmpty();
        if (r1 == 0) goto L_0x027b;
    L_0x00ef:
        r1 = r4.mListItemIndexX;
        if (r1 < 0) goto L_0x027b;
    L_0x00f3:
        r1 = r4.mListItemIndexX;
        r2 = r5.getVideoAssets();
        r2 = r2.size();
        if (r1 >= r2) goto L_0x027b;
    L_0x00ff:
        r1 = r4.mMoviesListInfo;
        r2 = r4.mListItemIndexY;
        r1 = r1.get(r2);
        r1 = (com.xumo.xumo.model.Movies) r1;
        r1 = r1.ismLoadingNow();
        if (r1 != 0) goto L_0x027b;
    L_0x010f:
        r1 = r4.mMoviesListInfo;
        r2 = r4.mListItemIndexY;
        r1 = r1.get(r2);
        r1 = (com.xumo.xumo.model.Movies) r1;
        r1.setmLoadingNow(r0);
        r0 = r4.mGenreListAdapter;
        r0.notifyDataSetChanged();
        r0 = r4.mListItemIndexX;
        r1 = r4.mListItemIndexY;
        r4.getMoviesMoreChannelInfomation(r5, r0, r1);
        goto L_0x027b;
    L_0x012a:
        r5 = r4.mListItemIndexX;
        r5 = r5 + r0;
        r4.mListItemIndexX = r5;
        r5 = r4.mMoviesListInfo;
        if (r5 == 0) goto L_0x0142;
    L_0x0133:
        r5 = r4.mMoviesListInfo;
        r0 = r4.mListItemIndexY;
        r5 = r5.get(r0);
        r5 = (com.xumo.xumo.model.Movies) r5;
        r0 = r4.mListItemIndexX;
        r5.setmListItemIndexX(r0);
    L_0x0142:
        r5 = r4.genreRecyclerView;
        r5 = r5.getAdapter();
        r5.notifyDataSetChanged();
        goto L_0x027b;
    L_0x014d:
        r5 = r4.mListItemIndexX;
        if (r5 < 0) goto L_0x027b;
    L_0x0151:
        r5 = r4.mListItemIndexX;
        r5 = r5 - r0;
        r4.mListItemIndexX = r5;
        r5 = r4.mMoviesListInfo;
        if (r5 == 0) goto L_0x0169;
    L_0x015a:
        r5 = r4.mMoviesListInfo;
        r0 = r4.mListItemIndexY;
        r5 = r5.get(r0);
        r5 = (com.xumo.xumo.model.Movies) r5;
        r0 = r4.mListItemIndexX;
        r5.setmListItemIndexX(r0);
    L_0x0169:
        r5 = r4.mListItemIndexX;
        r0 = -1;
        if (r5 != r0) goto L_0x0177;
    L_0x016e:
        r5 = r4.mMainNavigationPageSelectState;
        r5 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.NavigationBar;
        r4.mMainNavigationPageSelectState = r5;
        r4.setBackgroundToGetFocus();
    L_0x0177:
        r5 = r4.genreRecyclerView;
        r5 = r5.getAdapter();
        r5.notifyDataSetChanged();
        goto L_0x027b;
    L_0x0182:
        r5 = r4.mMovieChannel;
        r5 = r5.getCategories();
        if (r5 == 0) goto L_0x027b;
    L_0x018a:
        r5 = r4.mMovieChannel;
        r5 = r5.getCategories();
        r5 = r5.size();
        if (r5 <= 0) goto L_0x027b;
    L_0x0196:
        r5 = r4.mMovieChannel;
        r5 = r5.getCategories();
        r5 = r5.size();
        r2 = r4.mListItemIndexY;
        r2 = r2 + r0;
        if (r5 <= r2) goto L_0x027b;
    L_0x01a5:
        r5 = r4.mMovieChannel;
        r5 = r5.getCategories();
        r2 = r4.mListItemIndexY;
        r2 = r2 + r0;
        r5 = r5.get(r2);
        r5 = (com.xumo.xumo.model.Category) r5;
        r5 = r5.getVideoAssets();
        r5 = r5.size();
        if (r5 == 0) goto L_0x027b;
    L_0x01be:
        r5 = r4.mListItemIndexX;
        if (r5 < 0) goto L_0x021d;
    L_0x01c2:
        r5 = r4.mMoviesListInfo;
        if (r5 == 0) goto L_0x01d6;
    L_0x01c6:
        r5 = r4.mListItemIndexY;
        r2 = r4.mMoviesListInfo;
        r2 = r2.size();
        r2 = r2 - r0;
        if (r5 >= r2) goto L_0x01d6;
    L_0x01d1:
        r5 = r4.mListItemIndexY;
        r5 = r5 + r0;
        r4.mListItemIndexY = r5;
    L_0x01d6:
        r5 = r4.mMoviesListInfo;
        if (r5 == 0) goto L_0x01fb;
    L_0x01da:
        r5 = r4.mMoviesListInfo;
        r0 = r4.mListItemIndexY;
        r5 = r5.get(r0);
        r5 = (com.xumo.xumo.model.Movies) r5;
        r5 = r5.getmListItemIndexX();
        if (r5 < 0) goto L_0x01fb;
    L_0x01ea:
        r5 = r4.mMoviesListInfo;
        r0 = r4.mListItemIndexY;
        r5 = r5.get(r0);
        r5 = (com.xumo.xumo.model.Movies) r5;
        r5 = r5.getmListItemIndexX();
        r4.mListItemIndexX = r5;
        goto L_0x01fd;
    L_0x01fb:
        r4.mListItemIndexX = r1;
    L_0x01fd:
        r5 = r4.genreRecyclerView;
        r5 = r5.getLayoutManager();
        r5 = r5 instanceof androidx.recyclerview.widget.LinearLayoutManager;
        if (r5 == 0) goto L_0x0214;
    L_0x0207:
        r5 = r4.genreRecyclerView;
        r5 = r5.getLayoutManager();
        r5 = (androidx.recyclerview.widget.LinearLayoutManager) r5;
        r0 = r4.mListItemIndexY;
        r5.scrollToPositionWithOffset(r0, r1);
    L_0x0214:
        r5 = r4.genreRecyclerView;
        r5 = r5.getAdapter();
        r5.notifyDataSetChanged();
    L_0x021d:
        r5 = r4.mGenreListAdapter;
        r5.notifyDataSetChanged();
        goto L_0x027b;
    L_0x0223:
        r5 = r4.mListItemIndexX;
        if (r5 < 0) goto L_0x027b;
    L_0x0227:
        r5 = r4.mListItemIndexY;
        if (r5 <= 0) goto L_0x027b;
    L_0x022b:
        r5 = r4.mListItemIndexY;
        r5 = r5 - r0;
        r4.mListItemIndexY = r5;
        r5 = r4.mMoviesListInfo;
        if (r5 == 0) goto L_0x0255;
    L_0x0234:
        r5 = r4.mMoviesListInfo;
        r0 = r4.mListItemIndexY;
        r5 = r5.get(r0);
        r5 = (com.xumo.xumo.model.Movies) r5;
        r5 = r5.getmListItemIndexX();
        if (r5 < 0) goto L_0x0255;
    L_0x0244:
        r5 = r4.mMoviesListInfo;
        r0 = r4.mListItemIndexY;
        r5 = r5.get(r0);
        r5 = (com.xumo.xumo.model.Movies) r5;
        r5 = r5.getmListItemIndexX();
        r4.mListItemIndexX = r5;
        goto L_0x0257;
    L_0x0255:
        r4.mListItemIndexX = r1;
    L_0x0257:
        r5 = r4.genreRecyclerView;
        r5 = r5.getLayoutManager();
        r5 = r5 instanceof androidx.recyclerview.widget.LinearLayoutManager;
        if (r5 == 0) goto L_0x026e;
    L_0x0261:
        r5 = r4.genreRecyclerView;
        r5 = r5.getLayoutManager();
        r5 = (androidx.recyclerview.widget.LinearLayoutManager) r5;
        r0 = r4.mListItemIndexY;
        r5.scrollToPositionWithOffset(r0, r1);
    L_0x026e:
        r5 = r4.genreRecyclerView;
        r5 = r5.getAdapter();
        r5.notifyDataSetChanged();
        goto L_0x027b;
    L_0x0278:
        r4.animateOut(r1);
    L_0x027b:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.fragmenttv.OnNowPlayerFragment.onKeyPressForMovies(android.view.KeyEvent):void");
    }

    private void showMovies() {
        int i = 0;
        if (this.mLlMovies.getVisibility() == 8) {
            this.mAllChannelsLy.setVisibility(8);
            this.mSettingLy.setVisibility(8);
            this.mChannelInfoTitle.setVisibility(8);
            this.mChannelInfoRecyclerView.setVisibility(8);
            this.tvLogo.setText(R.string.movies_logo);
            this.mNavigationContainer.setBackgroundColor(getResources().getColor(R.color.main_menu_background_color));
            this.mLlMovies.setVisibility(0);
        }
        isEmptyFavorite(false);
        if (this.mUpdateDisplayHandler != null) {
            this.mUpdateDisplayHandler.removeMessages(1);
        }
        if (this.mListItemIndexX != -1) {
            i = this.mListItemIndexX;
        }
        this.mListItemIndexX = i;
        setBackgroundToGetFocus();
        this.mGenreListAdapter.notifyDataSetChanged();
        UserPreferences.getInstance().setToMoviesScreen();
        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.PageView, null, null, this.mCurrentOnNowLive);
    }

    private void getMoviesChannelInfomation() {
        XumoWebService.getInstance().getChannelCategories(MOVIES_CHANNEL_ID, new Listener() {
            public void onCompletion(Object obj) {
                if (OnNowPlayerFragment.this.getHost() != null) {
                    ArrayList arrayList = (ArrayList) obj;
                    OnNowPlayerFragment.this.mMovieChannel.setCategories(arrayList);
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(new VideoAsset("", "", ""));
                    Iterator it = OnNowPlayerFragment.this.mMovieChannel.getCategories().iterator();
                    while (it.hasNext()) {
                        ((Category) it.next()).setVideoAssets(arrayList2);
                    }
                    OnNowPlayerFragment.this.mGenreListAdapter.notifyDataSetChanged();
                    for (int i = 0; i < arrayList.size(); i++) {
                        XumoWebService.getInstance().getAssetsInCategories((Category) arrayList.get(i), new Listener() {
                            public void onCompletion(Object obj) {
                                ArrayList arrayList = (ArrayList) obj;
                                if (OnNowPlayerFragment.this.mMovieChannel.getCategories() != null) {
                                    int i = 0;
                                    while (i < OnNowPlayerFragment.this.mMovieChannel.getCategories().size()) {
                                        if (arrayList != null && arrayList.size() > 0 && ((VideoAsset) arrayList.get(0)).getCategoryId().equals(((Category) OnNowPlayerFragment.this.mMovieChannel.getCategories().get(i)).getCategoryId())) {
                                            if (arrayList.size() < ((VideoAsset) arrayList.get(0)).getmHits()) {
                                                arrayList.add(new VideoAsset("", "", ""));
                                            }
                                            ((Category) OnNowPlayerFragment.this.mMovieChannel.getCategories().get(i)).setVideoAssets(arrayList);
                                        }
                                        i++;
                                    }
                                    if (OnNowPlayerFragment.this.genreRecyclerView != null) {
                                        if ((OnNowPlayerFragment.this.genreRecyclerView.getLayoutManager() instanceof LinearLayoutManager) != null) {
                                            ((LinearLayoutManager) OnNowPlayerFragment.this.genreRecyclerView.getLayoutManager()).scrollToPositionWithOffset(OnNowPlayerFragment.this.mListItemIndexY, 0);
                                        }
                                        if (OnNowPlayerFragment.this.genreRecyclerView.getAdapter() != null) {
                                            OnNowPlayerFragment.this.genreRecyclerView.getAdapter().notifyDataSetChanged();
                                        }
                                    }
                                    OnNowPlayerFragment.this.mGenreListAdapter.notifyDataSetChanged();
                                }
                            }

                            public void onError() {
                                LogUtil.d("getAssetsInCategories getAssetsInCategories failed.");
                            }
                        });
                    }
                }
            }

            public void onError() {
                LogUtil.d("getMoviesChannelInfomation getChannelInfoForChannelId failed.");
            }
        });
    }

    private void getMoviesChannelInfomationForFromBack() {
        XumoWebService.getInstance().getChannelCategories(MOVIES_CHANNEL_ID, new Listener() {
            public void onCompletion(Object obj) {
                if (OnNowPlayerFragment.this.getHost() != null) {
                    ArrayList arrayList = (ArrayList) obj;
                    OnNowPlayerFragment.this.mMovieChannel.setCategories(arrayList);
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(new VideoAsset("", "", ""));
                    Iterator it = OnNowPlayerFragment.this.mMovieChannel.getCategories().iterator();
                    while (it.hasNext()) {
                        ((Category) it.next()).setVideoAssets(arrayList2);
                    }
                    OnNowPlayerFragment.this.mGenreListAdapter.notifyDataSetChanged();
                    for (int i = 0; i < arrayList.size(); i++) {
                        XumoWebService.getInstance().getAssetsInCategories((Category) arrayList.get(i), new Listener() {
                            public void onCompletion(Object obj) {
                                ArrayList arrayList = (ArrayList) obj;
                                if (OnNowPlayerFragment.this.mMovieChannel.getCategories() != null) {
                                    int i = 0;
                                    while (i < OnNowPlayerFragment.this.mMovieChannel.getCategories().size()) {
                                        if (arrayList != null && arrayList.size() > 0 && ((VideoAsset) arrayList.get(0)).getCategoryId().equals(((Category) OnNowPlayerFragment.this.mMovieChannel.getCategories().get(i)).getCategoryId())) {
                                            if (arrayList.size() < ((VideoAsset) arrayList.get(0)).getmHits()) {
                                                arrayList.add(new VideoAsset("", "", ""));
                                            }
                                            ((Category) OnNowPlayerFragment.this.mMovieChannel.getCategories().get(i)).setVideoAssets(arrayList);
                                        }
                                        i++;
                                    }
                                    if (OnNowPlayerFragment.this.genreRecyclerView != null) {
                                        if ((OnNowPlayerFragment.this.genreRecyclerView.getLayoutManager() instanceof LinearLayoutManager) != null) {
                                            ((LinearLayoutManager) OnNowPlayerFragment.this.genreRecyclerView.getLayoutManager()).scrollToPositionWithOffset(OnNowPlayerFragment.this.mListItemIndexY, 0);
                                        }
                                        if (OnNowPlayerFragment.this.genreRecyclerView.getAdapter() != null) {
                                            OnNowPlayerFragment.this.genreRecyclerView.getAdapter().notifyDataSetChanged();
                                        }
                                    }
                                    OnNowPlayerFragment.this.mGenreListAdapter.notifyDataSetChanged();
                                    OnNowPlayerFragment.this.createVideoPlaylistForFromBack(OnNowPlayerFragment.this.mMovieChannel);
                                    OnNowPlayerFragment.this.mXumoExoPlayer.rePlay();
                                }
                            }

                            public void onError() {
                                OnNowPlayerFragment.this.isResetMainNavigationDisplay = true;
                                OnNowPlayerFragment.this.mMainNavigationPageSelectState = MainNavigationPageSelectState.OnNow;
                                OnNowPlayerFragment.this.initData();
                            }
                        });
                    }
                }
            }

            public void onError() {
                OnNowPlayerFragment.this.isResetMainNavigationDisplay = true;
                OnNowPlayerFragment.this.mMainNavigationPageSelectState = MainNavigationPageSelectState.OnNow;
                OnNowPlayerFragment.this.initData();
            }
        });
    }

    private void getMoviesMoreChannelInfomation(Category category, final int i, final int i2) {
        XumoWebService.getInstance().getAssetsInCategories(category, i, new Listener() {
            public void onCompletion(Object obj) {
                ((Category) OnNowPlayerFragment.this.mMovieChannel.getCategories().get(i2)).getVideoAssets().remove(i);
                ((Movies) OnNowPlayerFragment.this.mMoviesListInfo.get(i2)).setmLoadingNow(false);
                ArrayList arrayList = (ArrayList) obj;
                OnNowPlayerFragment.this.mGenreListAdapter.setGenreNumberText(((VideoAsset) arrayList.get(0)).getmHits(), i2);
                if (arrayList != null && arrayList.size() > 0) {
                    if (((Category) OnNowPlayerFragment.this.mMovieChannel.getCategories().get(i2)).getVideoAssets().size() + arrayList.size() < ((VideoAsset) arrayList.get(0)).getmHits()) {
                        arrayList.add(new VideoAsset("", "", ""));
                    }
                    ((Category) OnNowPlayerFragment.this.mMovieChannel.getCategories().get(i2)).getVideoAssets().addAll(arrayList);
                }
                OnNowPlayerFragment.this.mGenreListAdapter.notifyDataSetChanged();
                ((Movies) OnNowPlayerFragment.this.mMoviesListInfo.get(i2)).setmListItemIndexX(i);
            }

            public void onError() {
                ((Movies) OnNowPlayerFragment.this.mMoviesListInfo.get(i2)).setmLoadingNow(false);
                LogUtil.d("getAssetsInCategories getAssetsInCategories failed.");
            }
        });
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onKeyPressForAllChannels(android.view.KeyEvent r8) {
        /*
        r7 = this;
        r8 = r8.getKeyCode();
        r0 = 4;
        r1 = 0;
        if (r8 == r0) goto L_0x05e4;
    L_0x0008:
        r0 = 8;
        r2 = -1;
        r3 = 2;
        r4 = 3;
        r5 = 1;
        switch(r8) {
            case 19: goto L_0x044d;
            case 20: goto L_0x020f;
            case 21: goto L_0x015c;
            case 22: goto L_0x00df;
            case 23: goto L_0x0016;
            default: goto L_0x0011;
        };
    L_0x0011:
        switch(r8) {
            case 96: goto L_0x0016;
            case 97: goto L_0x05e4;
            default: goto L_0x0014;
        };
    L_0x0014:
        goto L_0x05e7;
    L_0x0016:
        r8 = r7.mSelectChannelsIndex;
        if (r8 != r5) goto L_0x008a;
    L_0x001a:
        r8 = r7.mMoveChannelsTitleIndex;
        r7.mSelectChannelsTitleIndex = r8;
        r8 = r7.mSelectChannelsIndex;
        r8 = r8 + r5;
        r7.mSelectChannelsIndex = r8;
        r7.mSelectChannelsContentIndex = r1;
        r8 = r7.mSelectChannelsTitleIndex;
        r3 = r7.mAllChannelsGenreList;
        r3 = r3.size();
        r3 = r3 - r5;
        if (r8 != r3) goto L_0x004a;
    L_0x0030:
        r8 = r7.mGenreTitleTv;
        r8.setVisibility(r1);
        r8 = r7.mGenreTitleTv;
        r0 = r7.mAllChannelsGenreList;
        r3 = r7.mSelectChannelsTitleIndex;
        r3 = r3 - r5;
        r0 = r0.get(r3);
        r0 = (com.xumo.xumo.model.Genre) r0;
        r0 = r0.getValue();
        r8.setText(r0);
        goto L_0x004f;
    L_0x004a:
        r8 = r7.mGenreTitleTv;
        r8.setVisibility(r0);
    L_0x004f:
        r8 = r7.mAllChannelsListAdapter;
        r8.setMoveItemIndex(r2);
        r8 = r7.mAllChannelsListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsTitleRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        r8 = r7.mAllChannelsContentListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        r8 = r7.mChannelsContentRv;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPosition(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getLayoutManager();
        r8 = (androidx.recyclerview.widget.LinearLayoutManager) r8;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPositionWithOffset(r0, r1);
        goto L_0x05e7;
    L_0x008a:
        r8 = r7.mSelectChannelsIndex;
        if (r8 != r3) goto L_0x05e7;
    L_0x008e:
        r8 = r7.mAllChannelsGenreList;
        if (r8 == 0) goto L_0x05e7;
    L_0x0092:
        r8 = r7.mAllChannelsGenreList;
        r8 = r8.size();
        if (r8 <= 0) goto L_0x05e7;
    L_0x009a:
        r8 = r7.mAllChannelsGenreList;
        r0 = r7.mSelectChannelsTitleIndex;
        r8 = r8.get(r0);
        r8 = (com.xumo.xumo.model.Genre) r8;
        r8 = r8.getChannelIdList();
        if (r8 == 0) goto L_0x05e7;
    L_0x00aa:
        r8 = r7.mAllChannelsGenreList;
        r0 = r7.mSelectChannelsTitleIndex;
        r8 = r8.get(r0);
        r8 = (com.xumo.xumo.model.Genre) r8;
        r8 = r8.getChannelIdList();
        r8 = r8.size();
        if (r8 == 0) goto L_0x05e7;
    L_0x00be:
        r8 = r7.mAllChannelsGenreList;
        r0 = r7.mSelectChannelsTitleIndex;
        r8 = r8.get(r0);
        r8 = (com.xumo.xumo.model.Genre) r8;
        r8 = r8.getChannelIdList();
        r0 = r7.mSelectChannelsContentIndex;
        r8 = r8.get(r0);
        r8 = (java.lang.String) r8;
        r0 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.BrandPage;
        r2 = new java.lang.Object[r5];
        r2[r1] = r8;
        r7.showPage(r0, r2);
        goto L_0x05e7;
    L_0x00df:
        r8 = r7.mSelectChannelsIndex;
        if (r8 >= r3) goto L_0x00ee;
    L_0x00e3:
        r8 = r7.mSelectChannelsIndex;
        r8 = r8 + r5;
        r7.mSelectChannelsIndex = r8;
        r8 = r7.mSelectChannelsIndex;
        if (r8 != r3) goto L_0x00ee;
    L_0x00ec:
        r8 = 1;
        goto L_0x00ef;
    L_0x00ee:
        r8 = 0;
    L_0x00ef:
        r0 = r7.mSelectChannelsIndex;
        if (r0 != r3) goto L_0x05e7;
    L_0x00f3:
        r0 = r7.mAllChannelsListAdapter;
        r0.setMoveItemIndex(r2);
        r0 = r7.mAllChannelsListAdapter;
        r3 = r7.mSelectChannelsTitleIndex;
        r0.setSelectItemIndex(r3);
        r0 = r7.mChannelsTitleRv;
        r0 = r0.getAdapter();
        r0.notifyDataSetChanged();
        if (r8 != 0) goto L_0x0144;
    L_0x010a:
        r8 = r7.mSelectChannelsContentIndex;
        if (r8 != r2) goto L_0x0111;
    L_0x010e:
        r7.mSelectChannelsContentIndex = r1;
        goto L_0x014a;
    L_0x0111:
        r8 = r7.mAllChannelsGenreList;
        if (r8 == 0) goto L_0x014a;
    L_0x0115:
        r8 = r7.mAllChannelsGenreList;
        r8 = r8.size();
        if (r8 <= 0) goto L_0x014a;
    L_0x011d:
        r8 = r7.mSelectChannelsContentIndex;
        r0 = r7.mAllChannelsGenreList;
        r1 = r7.mSelectChannelsTitleIndex;
        r0 = r0.get(r1);
        r0 = (com.xumo.xumo.model.Genre) r0;
        r0 = r0.getChannelIdList();
        r0 = r0.size();
        r0 = r0 - r5;
        if (r8 >= r0) goto L_0x014a;
    L_0x0134:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 % r4;
        if (r8 == 0) goto L_0x013e;
    L_0x0139:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 % r4;
        if (r8 != r5) goto L_0x014a;
    L_0x013e:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 + r5;
        r7.mSelectChannelsContentIndex = r8;
        goto L_0x014a;
    L_0x0144:
        r8 = r7.mSelectChannelsContentIndex;
        if (r8 != r2) goto L_0x014a;
    L_0x0148:
        r7.mSelectChannelsContentIndex = r1;
    L_0x014a:
        r8 = r7.mAllChannelsContentListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        goto L_0x05e7;
    L_0x015c:
        r8 = r7.mSelectChannelsIndex;
        if (r8 != r5) goto L_0x0186;
    L_0x0160:
        r7.mSelectChannelsIndex = r1;
        r8 = r7.mMainNavigationPageSelectState;
        r8 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.NavigationBar;
        r7.mMainNavigationPageSelectState = r8;
        r7.setBackgroundToGetFocus();
        r8 = r7.mSelectChannelsTitleIndex;
        r7.mMoveChannelsTitleIndex = r8;
        r8 = r7.mAllChannelsListAdapter;
        r8.setMoveItemIndex(r2);
        r8 = r7.mAllChannelsListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsTitleRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        goto L_0x0200;
    L_0x0186:
        r8 = r7.mSelectChannelsIndex;
        if (r8 != r3) goto L_0x0200;
    L_0x018a:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 % r4;
        if (r8 == 0) goto L_0x01ce;
    L_0x018f:
        r8 = r7.mSelectChannelsTitleIndex;
        if (r8 != 0) goto L_0x01b8;
    L_0x0193:
        r8 = r7.mAllChannelsGenreList;
        r0 = r7.mSelectChannelsTitleIndex;
        r8 = r8.get(r0);
        r8 = (com.xumo.xumo.model.Genre) r8;
        r8 = r8.getChannelIdList();
        r8 = r8.size();
        if (r8 == 0) goto L_0x01ce;
    L_0x01a7:
        r8 = r7.mAllChannelsGenreList;
        r0 = r7.mSelectChannelsTitleIndex;
        r8 = r8.get(r0);
        r8 = (com.xumo.xumo.model.Genre) r8;
        r8 = r8.getChannelIdList();
        if (r8 != 0) goto L_0x01b8;
    L_0x01b7:
        goto L_0x01ce;
    L_0x01b8:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 - r5;
        r7.mSelectChannelsContentIndex = r8;
        r8 = r7.mAllChannelsContentListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        goto L_0x01f9;
    L_0x01ce:
        r7.mSelectChannelsIndex = r5;
        r8 = r7.mAllChannelsContentListAdapter;
        r8.setSelectItemIndex(r2);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        r8 = r7.mSelectChannelsTitleIndex;
        r7.mMoveChannelsTitleIndex = r8;
        r8 = r7.mAllChannelsListAdapter;
        r0 = r7.mMoveChannelsTitleIndex;
        r8.setMoveItemIndex(r0);
        r8 = r7.mAllChannelsListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsTitleRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
    L_0x01f9:
        r8 = r7.mChannelsTitleRv;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPosition(r0);
    L_0x0200:
        r8 = new android.os.Handler;
        r8.<init>();
        r0 = new com.xumo.xumo.fragmenttv.OnNowPlayerFragment$22;
        r0.<init>();
        r8.post(r0);
        goto L_0x05e7;
    L_0x020f:
        r8 = r7.mSelectChannelsIndex;
        if (r8 != r5) goto L_0x023c;
    L_0x0213:
        r8 = r7.mMoveChannelsTitleIndex;
        r0 = r7.mAllChannelsGenreList;
        r0 = r0.size();
        r0 = r0 - r5;
        if (r8 >= r0) goto L_0x0223;
    L_0x021e:
        r8 = r7.mMoveChannelsTitleIndex;
        r8 = r8 + r5;
        r7.mMoveChannelsTitleIndex = r8;
    L_0x0223:
        r8 = r7.mAllChannelsListAdapter;
        r0 = r7.mMoveChannelsTitleIndex;
        r8.setMoveItemIndex(r0);
        r8 = r7.mChannelsTitleRv;
        r0 = r7.mMoveChannelsTitleIndex;
        r8.scrollToPosition(r0);
        r8 = r7.mChannelsTitleRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        goto L_0x043e;
    L_0x023c:
        r8 = r7.mSelectChannelsIndex;
        if (r8 != r3) goto L_0x043e;
    L_0x0240:
        r8 = r7.mAllChannelsGenreList;
        if (r8 == 0) goto L_0x0398;
    L_0x0244:
        r8 = r7.mAllChannelsGenreList;
        r8 = r8.size();
        if (r8 <= 0) goto L_0x0398;
    L_0x024c:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 / r4;
        r8 = r8 + r5;
        r2 = r7.mAllChannelsGenreList;
        r6 = r7.mSelectChannelsTitleIndex;
        r2 = r2.get(r6);
        r2 = (com.xumo.xumo.model.Genre) r2;
        r2 = r2.getChannelIdList();
        r2 = r2.size();
        r2 = r2 - r5;
        r2 = r2 / r4;
        r2 = r2 + r5;
        if (r8 == r2) goto L_0x0398;
    L_0x0267:
        r8 = r7.mSelectChannelsTitleIndex;
        r0 = r7.mAllChannelsGenreList;
        r0 = r0.size();
        r0 = r0 - r5;
        if (r8 == r0) goto L_0x028b;
    L_0x0272:
        r8 = r7.mGenreTitleTv;
        r8.setVisibility(r1);
        r8 = r7.mGenreTitleTv;
        r0 = r7.mAllChannelsGenreList;
        r1 = r7.mSelectChannelsTitleIndex;
        r0 = r0.get(r1);
        r0 = (com.xumo.xumo.model.Genre) r0;
        r0 = r0.getValue();
        r8.setText(r0);
        goto L_0x02a4;
    L_0x028b:
        r8 = r7.mGenreTitleTv;
        r8.setVisibility(r1);
        r8 = r7.mGenreTitleTv;
        r0 = r7.mAllChannelsGenreList;
        r1 = r7.mSelectChannelsTitleIndex;
        r1 = r1 - r5;
        r0 = r0.get(r1);
        r0 = (com.xumo.xumo.model.Genre) r0;
        r0 = r0.getValue();
        r8.setText(r0);
    L_0x02a4:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 + r4;
        r0 = r7.mAllChannelsGenreList;
        r1 = r7.mSelectChannelsTitleIndex;
        r0 = r0.get(r1);
        r0 = (com.xumo.xumo.model.Genre) r0;
        r0 = r0.getChannelIdList();
        r0 = r0.size();
        if (r8 >= r0) goto L_0x02f0;
    L_0x02bb:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 + r4;
        r7.mSelectChannelsContentIndex = r8;
        r8 = r7.mAllChannelsContentListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        r8 = r7.mChannelsContentRv;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPosition(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getLayoutManager();
        r8 = (androidx.recyclerview.widget.LinearLayoutManager) r8;
        r0 = r7.mSelectChannelsContentIndex;
        r0 = r0 / r4;
        r1 = r7.mItemHigh;
        r1 = -r1;
        r0 = r0 * r1;
        r0 = r0 + -90;
        r1 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPositionWithOffset(r1, r0);
        goto L_0x043e;
    L_0x02f0:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 + r3;
        r0 = r7.mAllChannelsGenreList;
        r1 = r7.mSelectChannelsTitleIndex;
        r0 = r0.get(r1);
        r0 = (com.xumo.xumo.model.Genre) r0;
        r0 = r0.getChannelIdList();
        r0 = r0.size();
        if (r8 >= r0) goto L_0x034c;
    L_0x0307:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 % r4;
        if (r8 != r5) goto L_0x0312;
    L_0x030c:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 + r3;
        r7.mSelectChannelsContentIndex = r8;
        goto L_0x031c;
    L_0x0312:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 % r4;
        if (r8 != r3) goto L_0x031c;
    L_0x0317:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 + r5;
        r7.mSelectChannelsContentIndex = r8;
    L_0x031c:
        r8 = r7.mAllChannelsContentListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        r8 = r7.mChannelsContentRv;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPosition(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getLayoutManager();
        r8 = (androidx.recyclerview.widget.LinearLayoutManager) r8;
        r0 = r7.mSelectChannelsContentIndex;
        r0 = r0 / r4;
        r1 = r7.mItemHigh;
        r1 = -r1;
        r0 = r0 * r1;
        r0 = r0 + -90;
        r1 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPositionWithOffset(r1, r0);
        goto L_0x043e;
    L_0x034c:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 + r5;
        r0 = r7.mAllChannelsGenreList;
        r1 = r7.mSelectChannelsTitleIndex;
        r0 = r0.get(r1);
        r0 = (com.xumo.xumo.model.Genre) r0;
        r0 = r0.getChannelIdList();
        r0 = r0.size();
        if (r8 >= r0) goto L_0x043e;
    L_0x0363:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 + r5;
        r7.mSelectChannelsContentIndex = r8;
        r8 = r7.mAllChannelsContentListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        r8 = r7.mChannelsContentRv;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPosition(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getLayoutManager();
        r8 = (androidx.recyclerview.widget.LinearLayoutManager) r8;
        r0 = r7.mSelectChannelsContentIndex;
        r0 = r0 / r4;
        r1 = r7.mItemHigh;
        r1 = -r1;
        r0 = r0 * r1;
        r0 = r0 + -90;
        r1 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPositionWithOffset(r1, r0);
        goto L_0x043e;
    L_0x0398:
        r8 = r7.mSelectChannelsTitleIndex;
        r2 = r7.mAllChannelsGenreList;
        r2 = r2.size();
        r2 = r2 - r5;
        if (r8 >= r2) goto L_0x043e;
    L_0x03a3:
        r8 = r7.mSelectChannelsTitleIndex;
        r8 = r8 + r5;
        r7.mSelectChannelsTitleIndex = r8;
        r8 = r7.mSelectChannelsTitleIndex;
        r2 = r7.mAllChannelsGenreList;
        r2 = r2.size();
        r2 = r2 - r5;
        if (r8 != r2) goto L_0x03cd;
    L_0x03b3:
        r8 = r7.mGenreTitleTv;
        r8.setVisibility(r1);
        r8 = r7.mGenreTitleTv;
        r0 = r7.mAllChannelsGenreList;
        r2 = r7.mSelectChannelsTitleIndex;
        r2 = r2 - r5;
        r0 = r0.get(r2);
        r0 = (com.xumo.xumo.model.Genre) r0;
        r0 = r0.getValue();
        r8.setText(r0);
        goto L_0x03d2;
    L_0x03cd:
        r8 = r7.mGenreTitleTv;
        r8.setVisibility(r0);
    L_0x03d2:
        r8 = r7.mAllChannelsGenreList;
        r0 = r7.mSelectChannelsTitleIndex;
        r8 = r8.get(r0);
        r8 = (com.xumo.xumo.model.Genre) r8;
        r8 = r8.getChannelIdList();
        r8 = r8.size();
        r0 = r7.mSelectChannelsContentIndex;
        r0 = r0 % r4;
        if (r0 != 0) goto L_0x03ec;
    L_0x03e9:
        r7.mSelectChannelsContentIndex = r1;
        goto L_0x040a;
    L_0x03ec:
        r0 = r7.mSelectChannelsContentIndex;
        r0 = r0 % r4;
        if (r0 != r5) goto L_0x03f9;
    L_0x03f1:
        if (r8 < r3) goto L_0x03f6;
    L_0x03f3:
        r7.mSelectChannelsContentIndex = r5;
        goto L_0x040a;
    L_0x03f6:
        r7.mSelectChannelsContentIndex = r1;
        goto L_0x040a;
    L_0x03f9:
        r0 = r7.mSelectChannelsContentIndex;
        r0 = r0 % r4;
        if (r0 != r3) goto L_0x040a;
    L_0x03fe:
        if (r8 < r4) goto L_0x0403;
    L_0x0400:
        r7.mSelectChannelsContentIndex = r3;
        goto L_0x040a;
    L_0x0403:
        if (r8 < r3) goto L_0x0408;
    L_0x0405:
        r7.mSelectChannelsContentIndex = r5;
        goto L_0x040a;
    L_0x0408:
        r7.mSelectChannelsContentIndex = r1;
    L_0x040a:
        r8 = r7.mChannelsTitleRv;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPosition(r0);
        r8 = r7.mAllChannelsListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsTitleRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        r8 = r7.mAllChannelsContentListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        r8 = r7.mChannelsContentRv;
        r8 = r8.getLayoutManager();
        r8 = (androidx.recyclerview.widget.LinearLayoutManager) r8;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPositionWithOffset(r0, r1);
    L_0x043e:
        r8 = new android.os.Handler;
        r8.<init>();
        r0 = new com.xumo.xumo.fragmenttv.OnNowPlayerFragment$21;
        r0.<init>();
        r8.post(r0);
        goto L_0x05e7;
    L_0x044d:
        r8 = r7.mSelectChannelsIndex;
        if (r8 != r5) goto L_0x0473;
    L_0x0451:
        r8 = r7.mMoveChannelsTitleIndex;
        if (r8 <= 0) goto L_0x045a;
    L_0x0455:
        r8 = r7.mMoveChannelsTitleIndex;
        r8 = r8 - r5;
        r7.mMoveChannelsTitleIndex = r8;
    L_0x045a:
        r8 = r7.mAllChannelsListAdapter;
        r0 = r7.mMoveChannelsTitleIndex;
        r8.setMoveItemIndex(r0);
        r8 = r7.mChannelsTitleRv;
        r0 = r7.mMoveChannelsTitleIndex;
        r8.scrollToPosition(r0);
        r8 = r7.mChannelsTitleRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        goto L_0x05d6;
    L_0x0473:
        r8 = r7.mSelectChannelsIndex;
        if (r8 != r3) goto L_0x05d6;
    L_0x0477:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 - r4;
        if (r8 < 0) goto L_0x04fe;
    L_0x047c:
        r8 = r7.mSelectChannelsContentIndex;
        r8 = r8 - r4;
        r7.mSelectChannelsContentIndex = r8;
        r8 = r7.mSelectChannelsTitleIndex;
        r2 = r7.mAllChannelsGenreList;
        r2 = r2.size();
        r2 = r2 - r5;
        if (r8 != r2) goto L_0x04a6;
    L_0x048c:
        r8 = r7.mGenreTitleTv;
        r8.setVisibility(r1);
        r8 = r7.mGenreTitleTv;
        r0 = r7.mAllChannelsGenreList;
        r1 = r7.mSelectChannelsTitleIndex;
        r1 = r1 - r5;
        r0 = r0.get(r1);
        r0 = (com.xumo.xumo.model.Genre) r0;
        r0 = r0.getValue();
        r8.setText(r0);
        goto L_0x04c8;
    L_0x04a6:
        r8 = r7.mSelectChannelsContentIndex;
        if (r8 >= r4) goto L_0x04b0;
    L_0x04aa:
        r8 = r7.mGenreTitleTv;
        r8.setVisibility(r0);
        goto L_0x04c8;
    L_0x04b0:
        r8 = r7.mGenreTitleTv;
        r8.setVisibility(r1);
        r8 = r7.mGenreTitleTv;
        r0 = r7.mAllChannelsGenreList;
        r1 = r7.mSelectChannelsTitleIndex;
        r0 = r0.get(r1);
        r0 = (com.xumo.xumo.model.Genre) r0;
        r0 = r0.getValue();
        r8.setText(r0);
    L_0x04c8:
        r8 = r7.mAllChannelsContentListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        r8 = r7.mChannelsContentRv;
        r8 = r8.getLayoutManager();
        r8 = (androidx.recyclerview.widget.LinearLayoutManager) r8;
        r0 = r7.mSelectChannelsContentIndex;
        if (r0 <= r3) goto L_0x04ef;
    L_0x04e4:
        r0 = r7.mSelectChannelsContentIndex;
        r0 = r0 / r4;
        r1 = r7.mItemHigh;
        r1 = -r1;
        r0 = r0 * r1;
        r0 = r0 + -90;
        goto L_0x04f7;
    L_0x04ef:
        r0 = r7.mSelectChannelsContentIndex;
        r0 = r0 / r4;
        r1 = r7.mItemHigh;
        r1 = -r1;
        r0 = r0 * r1;
    L_0x04f7:
        r1 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPositionWithOffset(r1, r0);
        goto L_0x05d6;
    L_0x04fe:
        r8 = r7.mSelectChannelsTitleIndex;
        if (r8 <= 0) goto L_0x05d6;
    L_0x0502:
        r8 = r7.mSelectChannelsTitleIndex;
        r8 = r8 - r5;
        r7.mSelectChannelsTitleIndex = r8;
        r8 = r7.mAllChannelsGenreList;
        r2 = r7.mSelectChannelsTitleIndex;
        r8 = r8.get(r2);
        r8 = (com.xumo.xumo.model.Genre) r8;
        r8 = r8.getChannelIdList();
        r8 = r8.size();
        r8 = r8 - r5;
        if (r8 >= 0) goto L_0x0524;
    L_0x051c:
        r8 = r7.mGenreTitleTv;
        r8.setVisibility(r0);
        r7.mSelectChannelsContentIndex = r1;
        goto L_0x0579;
    L_0x0524:
        r2 = r7.mSelectChannelsTitleIndex;
        if (r2 == 0) goto L_0x0541;
    L_0x0528:
        r0 = r7.mGenreTitleTv;
        r0.setVisibility(r1);
        r0 = r7.mGenreTitleTv;
        r1 = r7.mAllChannelsGenreList;
        r2 = r7.mSelectChannelsTitleIndex;
        r1 = r1.get(r2);
        r1 = (com.xumo.xumo.model.Genre) r1;
        r1 = r1.getValue();
        r0.setText(r1);
        goto L_0x0546;
    L_0x0541:
        r1 = r7.mGenreTitleTv;
        r1.setVisibility(r0);
    L_0x0546:
        r0 = r7.mSelectChannelsContentIndex;
        if (r0 != 0) goto L_0x055d;
    L_0x054a:
        r0 = r8 % 3;
        if (r0 != r3) goto L_0x0552;
    L_0x054e:
        r8 = r8 - r3;
        r7.mSelectChannelsContentIndex = r8;
        goto L_0x0579;
    L_0x0552:
        if (r0 != r5) goto L_0x0558;
    L_0x0554:
        r8 = r8 - r5;
        r7.mSelectChannelsContentIndex = r8;
        goto L_0x0579;
    L_0x0558:
        if (r0 != 0) goto L_0x0579;
    L_0x055a:
        r7.mSelectChannelsContentIndex = r8;
        goto L_0x0579;
    L_0x055d:
        r0 = r7.mSelectChannelsContentIndex;
        if (r0 != r5) goto L_0x0573;
    L_0x0561:
        r0 = r8 % 3;
        if (r0 != r3) goto L_0x0569;
    L_0x0565:
        r8 = r8 - r5;
        r7.mSelectChannelsContentIndex = r8;
        goto L_0x0579;
    L_0x0569:
        if (r0 != r5) goto L_0x056e;
    L_0x056b:
        r7.mSelectChannelsContentIndex = r8;
        goto L_0x0579;
    L_0x056e:
        if (r0 != 0) goto L_0x0579;
    L_0x0570:
        r7.mSelectChannelsContentIndex = r8;
        goto L_0x0579;
    L_0x0573:
        r0 = r7.mSelectChannelsContentIndex;
        if (r0 != r3) goto L_0x0579;
    L_0x0577:
        r7.mSelectChannelsContentIndex = r8;
    L_0x0579:
        r8 = r7.mChannelsTitleRv;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPosition(r0);
        r8 = r7.mAllChannelsListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsTitleRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        r8 = r7.mAllChannelsContentListAdapter;
        r0 = r7.mSelectChannelsTitleIndex;
        r8.setSelectItemIndex(r0);
        r8 = r7.mChannelsContentRv;
        r8 = r8.getAdapter();
        r8.notifyDataSetChanged();
        r8 = r7.mChannelsContentRv;
        r8 = r8.getLayoutManager();
        r8 = (androidx.recyclerview.widget.LinearLayoutManager) r8;
        r0 = r7.mSelectChannelsContentIndex;
        if (r0 <= 0) goto L_0x05c9;
    L_0x05ac:
        r0 = r7.mSelectChannelsTitleIndex;
        if (r0 != 0) goto L_0x05be;
    L_0x05b0:
        r0 = r7.mSelectChannelsContentIndex;
        r0 = r0 / r4;
        if (r0 != 0) goto L_0x05be;
    L_0x05b5:
        r0 = r7.mSelectChannelsContentIndex;
        r0 = r0 / r4;
        r1 = r7.mItemHigh;
        r1 = -r1;
        r0 = r0 * r1;
        goto L_0x05d1;
    L_0x05be:
        r0 = r7.mSelectChannelsContentIndex;
        r0 = r0 / r4;
        r1 = r7.mItemHigh;
        r1 = -r1;
        r0 = r0 * r1;
        r0 = r0 + -90;
        goto L_0x05d1;
    L_0x05c9:
        r0 = r7.mSelectChannelsContentIndex;
        r0 = r0 / r4;
        r1 = r7.mItemHigh;
        r1 = -r1;
        r0 = r0 * r1;
    L_0x05d1:
        r1 = r7.mSelectChannelsTitleIndex;
        r8.scrollToPositionWithOffset(r1, r0);
    L_0x05d6:
        r8 = new android.os.Handler;
        r8.<init>();
        r0 = new com.xumo.xumo.fragmenttv.OnNowPlayerFragment$20;
        r0.<init>();
        r8.post(r0);
        goto L_0x05e7;
    L_0x05e4:
        r7.animateOut(r1);
    L_0x05e7:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.fragmenttv.OnNowPlayerFragment.onKeyPressForAllChannels(android.view.KeyEvent):void");
    }

    private void showAllChannels(boolean z) {
        this.mSettingLy.setVisibility(8);
        this.mLlMovies.setVisibility(8);
        this.mChannelInfoTitle.setVisibility(8);
        this.mChannelInfoRecyclerView.setVisibility(8);
        this.mAllChannelsLy.setVisibility(0);
        this.tvLogo.setText(R.string.all_channels_logo);
        if (z) {
            this.mMainNavigationPageSelectState = MainNavigationPageSelectState.AllChannels;
            setDefaultBackGdColor();
            int i;
            if (UserPreferences.getInstance().getViewBoost()) {
                for (z = false; z < this.mAllChannelsGenreList.size(); z++) {
                    for (i = 0; i < ((Genre) this.mAllChannelsGenreList.get(z)).getChannelIdList().size(); i++) {
                        if (((String) ((Genre) this.mAllChannelsGenreList.get(z)).getChannelIdList().get(i)).equals(UserPreferences.getInstance().getViewBoostChannelId())) {
                            this.mMoveChannelsTitleIndex = z;
                            this.mSelectChannelsTitleIndex = z;
                            this.mSelectChannelsContentIndex = i;
                            break;
                        }
                    }
                }
                this.tabSelectIndex = 2;
                this.mCurrentSelectedStatus = 0;
                this.mSelectChannelsIndex = 2;
                this.mAllChannelsListAdapter.setMoveItemIndex(-1);
                this.mAllChannelsListAdapter.setSelectItemIndex(this.mSelectChannelsTitleIndex);
                this.mAllChannelsContentListAdapter.setSelectItemIndex(this.mSelectChannelsTitleIndex);
                this.mChannelsTitleRv.scrollToPosition(this.mSelectChannelsTitleIndex);
                this.mChannelsContentRv.scrollToPosition(this.mSelectChannelsTitleIndex);
                new Handler().post(new Runnable() {
                    public void run() {
                        OnNowPlayerFragment.this.onScrolledAllChannelsGenre(OnNowPlayerFragment.this.mChannelsTitleRv);
                    }
                });
                this.mChannelsTitleRv.getAdapter().notifyDataSetChanged();
                this.mAllChannelsContentListAdapter.notifyDataSetChanged();
                this.mGenreTitleTv.setVisibility(8);
            } else if (this.mPlaySourceCategory == PlaySourceCategory.BrandPageVideo) {
                this.mSelectChannelsIndex = 2;
                this.mMoveChannelsTitleIndex = -1;
                this.mSelectChannelsTitleIndex = 0;
                this.mSelectChannelsContentIndex = 0;
                if (!BRAND_PAGE_CHANNEL_ID.isEmpty()) {
                    for (z = false; z < this.mAllChannelsGenreList.size(); z++) {
                        for (i = 0; i < ((Genre) this.mAllChannelsGenreList.get(z)).getChannelIdList().size(); i++) {
                            if (((String) ((Genre) this.mAllChannelsGenreList.get(z)).getChannelIdList().get(i)).equals(BRAND_PAGE_CHANNEL_ID)) {
                                this.mSelectChannelsTitleIndex = z;
                                this.mSelectChannelsContentIndex = i;
                                break;
                            }
                        }
                    }
                }
                if (this.mAllChannelsListAdapter) {
                    this.mAllChannelsListAdapter.setMoveItemIndex(-1);
                    this.mAllChannelsListAdapter.setSelectItemIndex(this.mSelectChannelsTitleIndex);
                }
                if (this.mChannelsTitleRv && this.mChannelsTitleRv.getAdapter()) {
                    this.mChannelsTitleRv.getAdapter().notifyDataSetChanged();
                }
                if (this.mChannelsContentRv) {
                    this.mChannelsContentRv.scrollToPosition(this.mSelectChannelsTitleIndex);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.mChannelsContentRv.getLayoutManager();
                    if (linearLayoutManager != null) {
                        int i2;
                        if (this.mSelectChannelsContentIndex > 2) {
                            i2 = ((this.mSelectChannelsContentIndex / 3) * (-this.mItemHigh)) - 90;
                        } else {
                            i2 = (this.mSelectChannelsContentIndex / 3) * (-this.mItemHigh);
                        }
                        if (this.mSelectChannelsTitleIndex == this.mAllChannelsGenreList.size() - 1) {
                            this.mGenreTitleTv.setVisibility(0);
                            this.mGenreTitleTv.setText(((Genre) this.mAllChannelsGenreList.get(this.mSelectChannelsTitleIndex - 1)).getValue());
                        } else if (this.mSelectChannelsContentIndex < 3) {
                            this.mGenreTitleTv.setVisibility(8);
                        } else {
                            this.mGenreTitleTv.setVisibility(0);
                            this.mGenreTitleTv.setText(((Genre) this.mAllChannelsGenreList.get(this.mSelectChannelsTitleIndex)).getValue());
                        }
                        linearLayoutManager.scrollToPositionWithOffset(this.mSelectChannelsTitleIndex, i2);
                    }
                    if (this.mChannelsContentRv.getAdapter()) {
                        this.mChannelsContentRv.getAdapter().notifyDataSetChanged();
                    }
                }
                if (this.mAllChannelsContentListAdapter) {
                    this.mAllChannelsContentListAdapter.setSelectItemIndex(this.mSelectChannelsTitleIndex);
                }
            } else {
                this.mSelectChannelsIndex = 1;
                if (this.mMoveChannelsTitleIndex >= false) {
                    this.mMoveChannelsTitleIndex = 0;
                }
                this.mAllChannelsListAdapter.setMoveItemIndex(this.mMoveChannelsTitleIndex);
                this.mAllChannelsListAdapter.setSelectItemIndex(this.mSelectChannelsTitleIndex);
                this.mChannelsTitleRv.scrollToPosition(this.mSelectChannelsTitleIndex);
                new Handler().post(new Runnable() {
                    public void run() {
                        OnNowPlayerFragment.this.onScrolledAllChannelsGenre(OnNowPlayerFragment.this.mChannelsTitleRv);
                    }
                });
                this.mChannelsTitleRv.getAdapter().notifyDataSetChanged();
            }
        } else {
            this.mNavigationContainer.setBackgroundColor(getResources().getColor(R.color.main_menu_background_color));
        }
        isEmptyFavorite(false);
        if (this.mUpdateDisplayHandler) {
            this.mUpdateDisplayHandler.removeMessages(1);
        }
        setBackgroundToGetFocus();
        UserPreferences.getInstance().setToChannelsScreen();
        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.PageView, null, null, this.mCurrentOnNowLive);
    }

    private void getAllChannels() {
        if (this.mGenreList == null || this.mGenreList.size() == 0) {
            XumoDataSync xumoDataSync = this.mXumoDataSync;
            XumoDataSync.getInstance().getGenre(new Listener() {
                public void onCompletion(Object obj) {
                    if (OnNowPlayerFragment.this.getHost() != null) {
                        OnNowPlayerFragment.this.mGenreList = (ArrayList) obj;
                        OnNowPlayerFragment.this.mXumoDataSync.getAllChannels(new Listener() {
                            public void onCompletion(Object obj) {
                                if (OnNowPlayerFragment.this.getHost() != null) {
                                    OnNowPlayerFragment.this.mAllChannels = (ArrayList) obj;
                                    OnNowPlayerFragment.this.setFavoritesAndMostPopularGenre();
                                    OnNowPlayerFragment.this.mGenreListAdapter.notifyDataSetChanged();
                                }
                            }

                            public void onError() {
                                LogUtil.d("ChannelsFragment getAllChannels onError.");
                            }
                        });
                    }
                }

                public void onError() {
                    LogUtil.d("ChannelsFragment getGenre onError. ");
                }
            });
        }
    }

    private void setFavoritesAndMostPopularGenre() {
        Iterator it;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (this.mAllChannels != null) {
            it = this.mAllChannels.iterator();
            while (it.hasNext()) {
                Channel channel = (Channel) it.next();
                if (!(channel == null || channel.getChannelId() == null)) {
                    if (!channel.getChannelId().isEmpty()) {
                        if (UserPreferences.getInstance().isFavorited(channel.getChannelId())) {
                            arrayList.add(channel.getChannelId());
                        }
                        if (channel.isHasVod() && arrayList2.size() < 12) {
                            arrayList2.add(channel.getChannelId());
                        }
                    }
                }
            }
        }
        this.mAllChannelsGenreList.clear();
        it = this.mGenreList.iterator();
        while (it.hasNext()) {
            this.mAllChannelsGenreList.add((Genre) it.next());
        }
        it = this.mAllChannelsGenreList.iterator();
        Object obj = null;
        Object obj2 = null;
        while (it.hasNext()) {
            Genre genre = (Genre) it.next();
            if (GENRE_FAVORITES.equals(genre.getValue())) {
                genre.setChannelIdList(arrayList);
                obj = 1;
            }
            if (GENRE_MOST_POPULAR.equals(genre.getValue())) {
                genre.setChannelIdList(arrayList2);
                obj2 = 1;
            }
        }
        if (obj == null) {
            this.mAllChannelsGenreList.add(0, new Genre(-1, GENRE_FAVORITES, ""));
            ((Genre) this.mAllChannelsGenreList.get(0)).setChannelIdList(arrayList);
        }
        if (obj2 == null) {
            this.mAllChannelsGenreList.add(1, new Genre(-2, GENRE_MOST_POPULAR, ""));
            ((Genre) this.mAllChannelsGenreList.get(1)).setChannelIdList(arrayList2);
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        if (TextUtils.equals(str, UserPreferences.DEVICE_ID) != null) {
            updateXumoClientId();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onKeyPressForSettings(android.view.KeyEvent r12) {
        /*
        r11 = this;
        r0 = r12.getKeyCode();
        r1 = 4;
        r2 = 0;
        if (r0 == r1) goto L_0x0238;
    L_0x0008:
        r3 = 2131099890; // 0x7f0600f2 float:1.7812146E38 double:1.0529032435E-314;
        r4 = 2131099913; // 0x7f060109 float:1.7812193E38 double:1.052903255E-314;
        r5 = -1;
        r6 = 1;
        switch(r0) {
            case 19: goto L_0x020e;
            case 20: goto L_0x01dc;
            case 21: goto L_0x0172;
            case 22: goto L_0x0084;
            case 23: goto L_0x0018;
            default: goto L_0x0013;
        };
    L_0x0013:
        switch(r0) {
            case 96: goto L_0x0018;
            case 97: goto L_0x0238;
            default: goto L_0x0016;
        };
    L_0x0016:
        goto L_0x0240;
    L_0x0018:
        r0 = r11.mInputModelList;
        r12 = r12.getKeyCode();
        r12 = java.lang.Integer.valueOf(r12);
        r0.add(r12);
        r12 = r11.isLicenses;
        if (r12 != 0) goto L_0x0076;
    L_0x0029:
        r12 = r11.mSettingIndex;
        if (r12 != 0) goto L_0x0069;
    L_0x002d:
        r12 = r11.mDefaultSwitch;
        r0 = r11.mDefaultSwitch;
        r0 = r0.isChecked();
        r0 = r0 ^ r6;
        r12.setChecked(r0);
        r12 = r11.mDefaultSwitch;
        r12 = r12.isChecked();
        if (r12 == 0) goto L_0x004e;
    L_0x0041:
        r12 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r12.setSubtitleSwitch(r6);
        r12 = r11.mAppTv;
        r12.setTextColor(r5);
        goto L_0x0062;
    L_0x004e:
        r12 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r12.setSubtitleSwitch(r2);
        r12 = r11.mAppTv;
        r0 = r11.getResources();
        r0 = r0.getColor(r3);
        r12.setTextColor(r0);
    L_0x0062:
        r12 = r11.mXumoExoPlayer;
        r12.setSwitchSubtitle();
        goto L_0x0240;
    L_0x0069:
        r12 = r11.mSettingIndex;
        if (r12 != r6) goto L_0x0240;
    L_0x006d:
        r12 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.SettingsCaptioningPage;
        r0 = new java.lang.Object[r2];
        r11.showPage(r12, r0);
        goto L_0x0240;
    L_0x0076:
        r12 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.MainNavigationPage;
        r11.hidePage(r12);
        r12 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.WebPage;
        r0 = new java.lang.Object[r2];
        r11.showPage(r12, r0);
        goto L_0x0240;
    L_0x0084:
        r0 = r11.mInputModelList;
        r12 = r12.getKeyCode();
        r12 = java.lang.Integer.valueOf(r12);
        r0.add(r12);
        r12 = r11.mInputModelList;
        r12 = r12.size();
        r0 = 6;
        if (r12 < r0) goto L_0x013c;
    L_0x009a:
        r12 = r11.mInputModelList;
        r7 = r11.mInputModelList;
        r7 = r7.size();
        r7 = r7 - r6;
        r12 = r12.get(r7);
        r12 = (java.lang.Integer) r12;
        r12 = r12.intValue();
        r7 = 22;
        if (r12 != r7) goto L_0x013c;
    L_0x00b1:
        r12 = r11.mInputModelList;
        r8 = r11.mInputModelList;
        r8 = r8.size();
        r8 = r8 + -2;
        r12 = r12.get(r8);
        r12 = (java.lang.Integer) r12;
        r12 = r12.intValue();
        r8 = 20;
        if (r12 != r8) goto L_0x013c;
    L_0x00c9:
        r12 = r11.mInputModelList;
        r9 = r11.mInputModelList;
        r9 = r9.size();
        r9 = r9 + -3;
        r12 = r12.get(r9);
        r12 = (java.lang.Integer) r12;
        r12 = r12.intValue();
        r9 = 19;
        if (r12 != r9) goto L_0x013c;
    L_0x00e1:
        r12 = r11.mInputModelList;
        r10 = r11.mInputModelList;
        r10 = r10.size();
        r10 = r10 - r1;
        r12 = r12.get(r10);
        r12 = (java.lang.Integer) r12;
        r12 = r12.intValue();
        if (r12 != r7) goto L_0x013c;
    L_0x00f6:
        r12 = r11.mInputModelList;
        r1 = r11.mInputModelList;
        r1 = r1.size();
        r1 = r1 + -5;
        r12 = r12.get(r1);
        r12 = (java.lang.Integer) r12;
        r12 = r12.intValue();
        if (r12 != r8) goto L_0x013c;
    L_0x010c:
        r12 = r11.mInputModelList;
        r1 = r11.mInputModelList;
        r1 = r1.size();
        r1 = r1 - r0;
        r12 = r12.get(r1);
        r12 = (java.lang.Integer) r12;
        r12 = r12.intValue();
        if (r12 != r9) goto L_0x013c;
    L_0x0121:
        r11.isInputModel = r6;
        r12 = r11.mInputModelKeyList;
        if (r12 == 0) goto L_0x0134;
    L_0x0127:
        r12 = r11.mInputModelKeyList;
        r12 = r12.size();
        if (r12 <= 0) goto L_0x0134;
    L_0x012f:
        r12 = r11.mInputModelKeyList;
        r12.clear();
    L_0x0134:
        r11.showKeyCodeText();
        r12 = r11.mDebugModeLy;
        r12.setVisibility(r2);
    L_0x013c:
        r12 = r11.isLicenses;
        if (r12 != 0) goto L_0x0240;
    L_0x0140:
        r11.isLicenses = r6;
        r12 = r11.mLicensesTv;
        r0 = r11.getResources();
        r0 = r0.getColor(r4);
        r12.setTextColor(r0);
        r12 = r11.mDefaultTv;
        r12.setTextColor(r5);
        r12 = r11.mDefaultSwitch;
        r12 = r12.isChecked();
        if (r12 == 0) goto L_0x0163;
    L_0x015c:
        r12 = r11.mAppTv;
        r12.setTextColor(r5);
        goto L_0x0240;
    L_0x0163:
        r12 = r11.mAppTv;
        r0 = r11.getResources();
        r0 = r0.getColor(r3);
        r12.setTextColor(r0);
        goto L_0x0240;
    L_0x0172:
        r0 = r11.mInputModelList;
        r12 = r12.getKeyCode();
        r12 = java.lang.Integer.valueOf(r12);
        r0.add(r12);
        r12 = r11.isLicenses;
        if (r12 != 0) goto L_0x01b0;
    L_0x0183:
        r11.mSettingIndex = r2;
        r12 = r11.mDefaultTv;
        r12.setTextColor(r5);
        r12 = r11.mDefaultSwitch;
        r12 = r12.isChecked();
        if (r12 == 0) goto L_0x0198;
    L_0x0192:
        r12 = r11.mAppTv;
        r12.setTextColor(r5);
        goto L_0x01a5;
    L_0x0198:
        r12 = r11.mAppTv;
        r0 = r11.getResources();
        r0 = r0.getColor(r3);
        r12.setTextColor(r0);
    L_0x01a5:
        r12 = r11.mMainNavigationPageSelectState;
        r12 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.NavigationBar;
        r11.mMainNavigationPageSelectState = r12;
        r11.setBackgroundToGetFocus();
        goto L_0x0240;
    L_0x01b0:
        r11.isLicenses = r2;
        r12 = r11.mLicensesTv;
        r12.setTextColor(r5);
        r12 = r11.mSettingIndex;
        if (r12 != 0) goto L_0x01ca;
    L_0x01bb:
        r12 = r11.mDefaultTv;
        r0 = r11.getResources();
        r0 = r0.getColor(r4);
        r12.setTextColor(r0);
        goto L_0x0240;
    L_0x01ca:
        r12 = r11.mSettingIndex;
        if (r12 != r6) goto L_0x0240;
    L_0x01ce:
        r12 = r11.mAppTv;
        r0 = r11.getResources();
        r0 = r0.getColor(r4);
        r12.setTextColor(r0);
        goto L_0x0240;
    L_0x01dc:
        r0 = r11.mInputModelList;
        r12 = r12.getKeyCode();
        r12 = java.lang.Integer.valueOf(r12);
        r0.add(r12);
        r12 = r11.isLicenses;
        if (r12 != 0) goto L_0x0240;
    L_0x01ed:
        r12 = r11.mSettingIndex;
        if (r12 != 0) goto L_0x0240;
    L_0x01f1:
        r12 = r11.mDefaultSwitch;
        r12 = r12.isChecked();
        if (r12 == 0) goto L_0x0240;
    L_0x01f9:
        r11.mSettingIndex = r6;
        r12 = r11.mAppTv;
        r0 = r11.getResources();
        r0 = r0.getColor(r4);
        r12.setTextColor(r0);
        r12 = r11.mDefaultTv;
        r12.setTextColor(r5);
        goto L_0x0240;
    L_0x020e:
        r0 = r11.mInputModelList;
        r12 = r12.getKeyCode();
        r12 = java.lang.Integer.valueOf(r12);
        r0.add(r12);
        r12 = r11.isLicenses;
        if (r12 != 0) goto L_0x0240;
    L_0x021f:
        r12 = r11.mSettingIndex;
        if (r12 != r6) goto L_0x0240;
    L_0x0223:
        r11.mSettingIndex = r2;
        r12 = r11.mDefaultTv;
        r0 = r11.getResources();
        r0 = r0.getColor(r4);
        r12.setTextColor(r0);
        r12 = r11.mAppTv;
        r12.setTextColor(r5);
        goto L_0x0240;
    L_0x0238:
        r12 = r11.mInputModelList;
        r12.clear();
        r11.animateOut(r2);
    L_0x0240:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.fragmenttv.OnNowPlayerFragment.onKeyPressForSettings(android.view.KeyEvent):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onKeyPressForSettingsDebugMode(android.view.KeyEvent r3) {
        /*
        r2 = this;
        r0 = r3.getKeyCode();
        r1 = 4;
        if (r0 == r1) goto L_0x0038;
    L_0x0007:
        switch(r0) {
            case 19: goto L_0x000e;
            case 20: goto L_0x000e;
            case 21: goto L_0x000e;
            case 22: goto L_0x000e;
            case 23: goto L_0x000e;
            default: goto L_0x000a;
        };
    L_0x000a:
        switch(r0) {
            case 96: goto L_0x000e;
            case 97: goto L_0x0038;
            default: goto L_0x000d;
        };
    L_0x000d:
        goto L_0x004a;
    L_0x000e:
        r0 = r2.mInputModelKeyList;
        r0 = r0.size();
        r1 = 2;
        if (r0 < r1) goto L_0x001c;
    L_0x0017:
        r0 = r2.mInputModelKeyList;
        r0.clear();
    L_0x001c:
        r0 = r2.mInputModelKeyList;
        r3 = r3.getKeyCode();
        r3 = java.lang.Integer.valueOf(r3);
        r0.add(r3);
        r2.showKeyCodeText();
        r3 = r2.mInputModelKeyList;
        r3 = r3.size();
        if (r3 != r1) goto L_0x004a;
    L_0x0034:
        r2.processKeyCode();
        goto L_0x004a;
    L_0x0038:
        r3 = 0;
        r2.isInputModel = r3;
        r3 = r2.mInputModelList;
        r3.clear();
        r3 = r2.mInputModelKeyList;
        r3.clear();
        r3 = r2.mDebugModeLy;
        r3.setVisibility(r1);
    L_0x004a:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.fragmenttv.OnNowPlayerFragment.onKeyPressForSettingsDebugMode(android.view.KeyEvent):void");
    }

    private void showKeyCodeText() {
        if (this.mInputModelKeyList == null || this.mInputModelKeyList.size() <= 0) {
            this.mDebugModeCode1Tv.setText("");
            this.mDebugModeSeparatorTv.setVisibility(8);
        } else {
            this.mDebugModeCode1Tv.setText(XumoUtil.getKeyCodeText(((Integer) this.mInputModelKeyList.get(0)).intValue()));
            this.mDebugModeSeparatorTv.setVisibility(0);
        }
        if (this.mInputModelKeyList == null || this.mInputModelKeyList.size() <= 1) {
            this.mDebugModeCode2Tv.setText("");
        } else {
            this.mDebugModeCode2Tv.setText(XumoUtil.getKeyCodeText(((Integer) this.mInputModelKeyList.get(1)).intValue()));
        }
    }

    private void processKeyCode() {
        if (this.mInputModelKeyList != null && this.mInputModelKeyList.size() == 2) {
            if (((Integer) this.mInputModelKeyList.get(0)).intValue() == 19 && ((Integer) this.mInputModelKeyList.get(1)).intValue() == 19) {
                this.mEnableDebugLogsTv.setVisibility(0);
                UserPreferences.getInstance().setUseDebugLogs(true);
                showDebugFragment();
            } else if (((Integer) this.mInputModelKeyList.get(0)).intValue() == 19 && ((Integer) this.mInputModelKeyList.get(1)).intValue() == 20) {
                this.mEnableTestChannelsTv.setVisibility(0);
                UserPreferences.getInstance().setUseTestChannels(true);
            } else if (((Integer) this.mInputModelKeyList.get(0)).intValue() == 19 && ((Integer) this.mInputModelKeyList.get(1)).intValue() == 22) {
                this.mEnableStagingPlayersTv.setVisibility(0);
                UserPreferences.getInstance().setUseStagingPlayers(true);
            } else if (((Integer) this.mInputModelKeyList.get(0)).intValue() == 19 && ((Integer) this.mInputModelKeyList.get(1)).intValue() == 21) {
                this.mViewboosterStagingTv.setVisibility(0);
                UserPreferences.getInstance().setUseStagingViewBooster(true);
            } else if (((Integer) this.mInputModelKeyList.get(0)).intValue() == 22 && ((Integer) this.mInputModelKeyList.get(1)).intValue() == 22) {
                this.mEnableDebugLogsTv.setVisibility(4);
                this.mEnableTestChannelsTv.setVisibility(4);
                this.mEnableStagingPlayersTv.setVisibility(4);
                this.mViewboosterStagingTv.setVisibility(4);
                this.mClearLocalStorageTv.setVisibility(0);
                UserPreferences.getInstance().clear();
                if (this.debugFragment != null) {
                    hideDebugFragment();
                }
            } else if (((Integer) this.mInputModelKeyList.get(0)).intValue() == 20 && ((Integer) this.mInputModelKeyList.get(1)).intValue() == 20) {
                this.mEnableDebugLogsTv.setVisibility(4);
                this.mEnableTestChannelsTv.setVisibility(4);
                this.mEnableStagingPlayersTv.setVisibility(4);
                this.mViewboosterStagingTv.setVisibility(4);
                UserPreferences.getInstance().disableAllDebugModes();
                if (this.debugFragment != null) {
                    hideDebugFragment();
                }
            }
        }
    }

    private void showSetting() {
        this.mAllChannelsLy.setVisibility(8);
        this.mLlMovies.setVisibility(8);
        this.mChannelInfoTitle.setVisibility(8);
        this.mChannelInfoRecyclerView.setVisibility(8);
        isEmptyFavorite(false);
        if (this.mUpdateDisplayHandler != null) {
            this.mUpdateDisplayHandler.removeMessages(1);
        }
        this.mSettingLy.setVisibility(0);
        this.mMainNavigationPageSelectState = MainNavigationPageSelectState.Settings;
        setDefaultBackGdColor();
        this.mDefaultTv.setTextColor(getResources().getColor(R.color.xumoBlue));
        if (this.mDefaultSwitch.isChecked()) {
            this.mAppTv.setTextColor(-1);
        } else {
            this.mAppTv.setTextColor(getResources().getColor(R.color.tab_noselect));
        }
        this.isLicenses = false;
        this.mLicensesTv.setTextColor(-1);
        this.isInputModel = false;
        this.mInputModelList.clear();
        this.mInputModelKeyList.clear();
        this.mDebugModeLy.setVisibility(4);
        this.mClearLocalStorageTv.setVisibility(4);
        this.tvLogo.setText(R.string.setting_logo);
        UserPreferences.getInstance().setToSettingsScreen();
        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.PageView, null, null, this.mCurrentOnNowLive);
    }

    private void updateXumoClientId() {
        CharSequence charSequence = "N/A";
        String deviceId = UserPreferences.getInstance().getDeviceId();
        if (deviceId != null) {
            String[] split = deviceId.split("-");
            String str = split[split.length - 1];
            charSequence = str.length() > 0 ? str.toUpperCase(Locale.ENGLISH) : deviceId;
        }
        this.mClientIdTv.setText(charSequence);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onKeyPressForLivePlayerControlsPage(android.view.KeyEvent r11) {
        /*
        r10 = this;
        r11 = r11.getKeyCode();
        r0 = 4;
        if (r11 == r0) goto L_0x01f4;
    L_0x0007:
        r0 = 2;
        r1 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r2 = 2131099913; // 0x7f060109 float:1.7812193E38 double:1.052903255E-314;
        r3 = 2131231424; // 0x7f0802c0 float:1.8078929E38 double:1.05296823E-314;
        r4 = 2131099915; // 0x7f06010b float:1.7812197E38 double:1.052903256E-314;
        r5 = 2131099680; // 0x7f060020 float:1.781172E38 double:1.0529031398E-314;
        r6 = 8;
        r7 = -1;
        r8 = 1;
        r9 = 0;
        switch(r11) {
            case 19: goto L_0x019a;
            case 20: goto L_0x0151;
            case 21: goto L_0x010d;
            case 22: goto L_0x00c9;
            case 23: goto L_0x0023;
            default: goto L_0x001e;
        };
    L_0x001e:
        switch(r11) {
            case 96: goto L_0x0023;
            case 97: goto L_0x01f4;
            default: goto L_0x0021;
        };
    L_0x0021:
        goto L_0x01f9;
    L_0x0023:
        r11 = r10.onNowDetailsPrompt;
        r11 = r11.getVisibility();
        if (r11 != 0) goto L_0x0041;
    L_0x002b:
        r11 = r10.onNowDetailsPrompt;
        r11.setVisibility(r6);
        r11 = r10.onNowDetailsInfo;
        r11.setVisibility(r9);
        r11 = r10.mMoreFromLy;
        r11.setVisibility(r9);
        r11 = r10.mCurrentOnNowLive;
        r10.openOnNowDetailsPage(r11);
        goto L_0x01f9;
    L_0x0041:
        r11 = r10.onNowDetailsRl;
        r11 = r11.getVisibility();
        if (r11 != 0) goto L_0x008f;
    L_0x0049:
        r11 = r10.mHasCaption;
        if (r11 == 0) goto L_0x01f9;
    L_0x004d:
        r11 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r11 = r11.getSubtitleSwitch();
        if (r11 == 0) goto L_0x0074;
    L_0x0057:
        r11 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r11.setSubtitleSwitch(r9);
        r11 = r10.mDefaultSwitch;
        r11.setChecked(r9);
        r11 = r10.mAppTv;
        r0 = r10.getResources();
        r1 = 2131099890; // 0x7f0600f2 float:1.7812146E38 double:1.0529032435E-314;
        r0 = r0.getColor(r1);
        r11.setTextColor(r0);
        goto L_0x0085;
    L_0x0074:
        r11 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r11.setSubtitleSwitch(r8);
        r11 = r10.mDefaultSwitch;
        r11.setChecked(r8);
        r11 = r10.mAppTv;
        r11.setTextColor(r7);
    L_0x0085:
        r11 = r10.mXumoExoPlayer;
        r11.setSwitchSubtitle();
        r10.setCcButtonVisibility(r8);
        goto L_0x01f9;
    L_0x008f:
        r11 = r10.mMoreFromIndex;
        if (r11 != r8) goto L_0x00b5;
    L_0x0093:
        r11 = r10.mCurrentOnNowLive;
        if (r11 == 0) goto L_0x01f9;
    L_0x0097:
        r11 = r10.mCurrentOnNowLive;
        r11 = r11.getChannelId();
        if (r11 == 0) goto L_0x01f9;
    L_0x009f:
        r11 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.BrandPage;
        r0 = new java.lang.Object[r8];
        r1 = r10.mCurrentOnNowLive;
        r1 = r1.getChannelId();
        r0[r9] = r1;
        r10.showPage(r11, r0);
        r11 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForLive;
        r10.hidePage(r11);
        goto L_0x01f9;
    L_0x00b5:
        r11 = r10.mMoreFromIndex;
        if (r11 != r0) goto L_0x01f9;
    L_0x00b9:
        r11 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.OnNow;
        r10.mMainNavigationPageSelectState = r11;
        r10.isResetMainNavigationDisplay = r8;
        r10.animateIn();
        r11 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForLive;
        r10.hidePage(r11);
        goto L_0x01f9;
    L_0x00c9:
        r11 = r10.onNowDetailsRl;
        r11 = r11.getVisibility();
        if (r11 != r6) goto L_0x01f9;
    L_0x00d1:
        r10.mMoreFromIndex = r0;
        r11 = r10.mMoreFromSelectLy;
        r11.setBackgroundColor(r9);
        r11 = r10.mMoreFromBottomLy;
        r11.setBackgroundResource(r5);
        r11 = r10.mMoreFromSelectTitleTv;
        r0 = r10.getResources();
        r0 = r0.getColor(r4);
        r11.setTextColor(r0);
        r11 = r10.mMoreFromSelectContentTv;
        r11.setTextColor(r7);
        r11 = r10.mMainMenuSelectLy;
        r11.setBackgroundResource(r3);
        r11 = r10.mMainMenuBottomLy;
        r11.setBackgroundColor(r7);
        r11 = r10.mMainMenuSelectTitleTv;
        r0 = r10.getResources();
        r0 = r0.getColor(r2);
        r11.setTextColor(r0);
        r11 = r10.mMainMenuSelectContentTv;
        r11.setTextColor(r1);
        goto L_0x01f9;
    L_0x010d:
        r11 = r10.onNowDetailsRl;
        r11 = r11.getVisibility();
        if (r11 != r6) goto L_0x01f9;
    L_0x0115:
        r10.mMoreFromIndex = r8;
        r11 = r10.mMoreFromSelectLy;
        r11.setBackgroundResource(r3);
        r11 = r10.mMoreFromBottomLy;
        r11.setBackgroundColor(r7);
        r11 = r10.mMoreFromSelectTitleTv;
        r0 = r10.getResources();
        r0 = r0.getColor(r2);
        r11.setTextColor(r0);
        r11 = r10.mMoreFromSelectContentTv;
        r11.setTextColor(r1);
        r11 = r10.mMainMenuSelectLy;
        r11.setBackgroundColor(r9);
        r11 = r10.mMainMenuBottomLy;
        r11.setBackgroundResource(r5);
        r11 = r10.mMainMenuSelectTitleTv;
        r0 = r10.getResources();
        r0 = r0.getColor(r4);
        r11.setTextColor(r0);
        r11 = r10.mMainMenuSelectContentTv;
        r11.setTextColor(r7);
        goto L_0x01f9;
    L_0x0151:
        r11 = r10.onNowDetailsPrompt;
        r11 = r11.getVisibility();
        if (r11 != 0) goto L_0x0169;
    L_0x0159:
        r11 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.OnNow;
        r10.mMainNavigationPageSelectState = r11;
        r10.isResetMainNavigationDisplay = r8;
        r10.animateIn();
        r11 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForLive;
        r10.hidePage(r11);
        goto L_0x01f9;
    L_0x0169:
        r11 = r10.onNowDetailsRl;
        r11 = r11.getVisibility();
        if (r11 != 0) goto L_0x01f9;
    L_0x0171:
        r10.mMoreFromIndex = r8;
        r11 = r10.onNowDetailsRl;
        r11.setVisibility(r6);
        r11 = r10.mBackToChannelInfoTv;
        r11.setVisibility(r9);
        r11 = r10.mMoreFromSelectLy;
        r11.setBackgroundResource(r3);
        r11 = r10.mMoreFromBottomLy;
        r11.setBackgroundColor(r7);
        r11 = r10.mMoreFromSelectTitleTv;
        r0 = r10.getResources();
        r0 = r0.getColor(r2);
        r11.setTextColor(r0);
        r11 = r10.mMoreFromSelectContentTv;
        r11.setTextColor(r1);
        goto L_0x01f9;
    L_0x019a:
        r11 = r10.onNowDetailsPrompt;
        r11 = r11.getVisibility();
        if (r11 != 0) goto L_0x01b1;
    L_0x01a2:
        r11 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.OnNow;
        r10.mMainNavigationPageSelectState = r11;
        r10.isResetMainNavigationDisplay = r8;
        r10.animateIn();
        r11 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForLive;
        r10.hidePage(r11);
        goto L_0x01f9;
    L_0x01b1:
        r11 = r10.onNowDetailsRl;
        r11.setVisibility(r9);
        r11 = r10.mBackToChannelInfoTv;
        r11.setVisibility(r6);
        r11 = r10.mMoreFromSelectLy;
        r11.setBackgroundColor(r9);
        r11 = r10.mMoreFromBottomLy;
        r11.setBackgroundResource(r5);
        r11 = r10.mMoreFromSelectTitleTv;
        r0 = r10.getResources();
        r0 = r0.getColor(r4);
        r11.setTextColor(r0);
        r11 = r10.mMoreFromSelectContentTv;
        r11.setTextColor(r7);
        r11 = r10.mMainMenuSelectLy;
        r11.setBackgroundColor(r9);
        r11 = r10.mMainMenuBottomLy;
        r11.setBackgroundResource(r5);
        r11 = r10.mMainMenuSelectTitleTv;
        r0 = r10.getResources();
        r0 = r0.getColor(r4);
        r11.setTextColor(r0);
        r11 = r10.mMainMenuSelectContentTv;
        r11.setTextColor(r7);
        goto L_0x01f9;
    L_0x01f4:
        r11 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForLive;
        r10.hidePage(r11);
    L_0x01f9:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.fragmenttv.OnNowPlayerFragment.onKeyPressForLivePlayerControlsPage(android.view.KeyEvent):void");
    }

    private void openOnNowDetailsPage(OnNowLive onNowLive) {
        if (onNowLive != null) {
            XumoImageUtil.setChannelImage(getContext(), onNowLive.getChannelId(), this.onNowDetailsImage, 320, 180);
            this.onNowDetailsNumbner.setText(onNowLive.getProgramNumberString());
            Iterator it = mOnNowLives.iterator();
            while (it.hasNext()) {
                OnNowLive onNowLive2 = (OnNowLive) it.next();
                if (mCurrentChannelId.equals(onNowLive2.getChannelId())) {
                    this.onNowDetailsTitle.setText(onNowLive2.getTitle());
                    this.onNowDetailsDescription.setText(onNowLive2.getDescriptionText());
                }
            }
            XumoImageUtil.setChannelTitleImage(getContext(), onNowLive.getChannelId(), this.mMoreFromSelectIv, 320, 180);
            if (TextUtils.isEmpty(onNowLive.getChannelGenreName())) {
                this.mMoreFromSelectTitleTv.setText("NO GENRE");
            } else {
                this.mMoreFromSelectTitleTv.setText(onNowLive.getChannelGenreName());
            }
            if (TextUtils.isEmpty(onNowLive.getChannelDescription())) {
                this.mMoreFromSelectContentTv.setText("NO DESCRIPTIONS");
            } else {
                this.mMoreFromSelectContentTv.setText(onNowLive.getChannelDescription());
            }
        }
        animateInDetails();
    }

    private void animateOutDetails(int i) {
        if (this.onNowDetails != null) {
            if (isPlayerControlsPageForLiveVisible()) {
                this.onNowDetails.animate().alpha(0.0f).setDuration(0).setStartDelay((long) i).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animator) {
                        if (OnNowPlayerFragment.this.mCancelAnimationDetails == null) {
                            OnNowPlayerFragment.this.mAnimateOutDetailsStart = true;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (OnNowPlayerFragment.this.mCancelAnimationDetails == null) {
                            OnNowPlayerFragment.this.mCancelAnimationDetails = true;
                            OnNowPlayerFragment.this.hidePage(DisplayView.PlayerControlsPageForLive);
                            OnNowPlayerFragment.this.mAnimateOutDetailsStart = false;
                        }
                    }
                });
            }
        }
    }

    private void animateInDetails() {
        if (this.onNowDetails != null && !this.mAnimateOutDetailsStart) {
            this.mCancelAnimationDetails = true;
            if (this.onNowDetails.getVisibility() == 0) {
                this.onNowDetails.animate().cancel();
                this.mCancelAnimationDetails = false;
                animateOutDetails(5000);
                return;
            }
            showPage(DisplayView.PlayerControlsPageForLive, new Object[0]);
            this.onNowDetails.setAlpha(1.0f);
            this.onNowDetails.animate().alpha(1.0f).setDuration(0).setStartDelay(0).setListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    OnNowPlayerFragment.this.mCancelAnimationDetails = false;
                    OnNowPlayerFragment.this.animateOutDetails(5000);
                }
            });
        }
    }

    private boolean isPlayerControlsPageForLiveVisible() {
        return this.onNowDetails != null && this.onNowDetails.getVisibility() == 0;
    }

    public void onVisibilityChange(int i) {
        if (i == 0) {
            if (isMainNavigationPageVisible() != 0 || isBrandPageVisible() != 0 || isMoviesUpNextPageVisible() != 0) {
                this.mXumoExoPlayer.hideController();
            }
        } else if (this.mVodRl != 0) {
            this.mVodRl.setVisibility(0);
            this.mVodBackToChannelInfoTv.setVisibility(8);
            this.mVodMoreFromIndex = -1;
            setMoreFromColor();
        }
    }

    private boolean isPlayerControlsPageForVodVisible() {
        return this.mXumoExoPlayer != null && this.mXumoExoPlayer.isControllerVisible();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onKeyPressForVodPlayerControlsPage(android.view.KeyEvent r13) {
        /*
        r12 = this;
        r0 = r12.mXumoExoPlayer;
        r0.showController();
        r0 = r12.mHasCaption;
        r1 = 0;
        if (r0 == 0) goto L_0x001b;
    L_0x000a:
        r0 = r13.getAction();
        if (r0 != 0) goto L_0x001b;
    L_0x0010:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getSubtitleSwitch();
        r12.checkVodCcButtonStyle(r1, r0);
    L_0x001b:
        r0 = r12.vodDetailsPrompt;
        r0 = r0.getVisibility();
        r2 = 85;
        r3 = 4;
        if (r0 != 0) goto L_0x004f;
    L_0x0026:
        r0 = r13.getAction();
        if (r0 != 0) goto L_0x03c4;
    L_0x002c:
        r13 = r13.getKeyCode();
        if (r13 == r3) goto L_0x0048;
    L_0x0032:
        if (r13 == r2) goto L_0x003f;
    L_0x0034:
        switch(r13) {
            case 19: goto L_0x003f;
            case 20: goto L_0x003f;
            case 21: goto L_0x003f;
            case 22: goto L_0x003f;
            case 23: goto L_0x003f;
            default: goto L_0x0037;
        };
    L_0x0037:
        switch(r13) {
            case 89: goto L_0x003f;
            case 90: goto L_0x003f;
            default: goto L_0x003a;
        };
    L_0x003a:
        switch(r13) {
            case 96: goto L_0x003f;
            case 97: goto L_0x0048;
            default: goto L_0x003d;
        };
    L_0x003d:
        goto L_0x03c4;
    L_0x003f:
        r13 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForVod;
        r0 = new java.lang.Object[r1];
        r12.showPage(r13, r0);
        goto L_0x03c4;
    L_0x0048:
        r13 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForVod;
        r12.hidePage(r13);
        goto L_0x03c4;
    L_0x004f:
        r0 = r13.getKeyCode();
        if (r0 == r3) goto L_0x03b9;
    L_0x0055:
        r4 = 3;
        r5 = 1;
        if (r0 == r2) goto L_0x0399;
    L_0x0059:
        r2 = 5;
        r6 = -1;
        r7 = 8;
        r8 = 6;
        r9 = 2;
        switch(r0) {
            case 19: goto L_0x0362;
            case 20: goto L_0x0331;
            case 21: goto L_0x028b;
            case 22: goto L_0x01ec;
            case 23: goto L_0x0098;
            default: goto L_0x0062;
        };
    L_0x0062:
        switch(r0) {
            case 89: goto L_0x0081;
            case 90: goto L_0x006a;
            default: goto L_0x0065;
        };
    L_0x0065:
        switch(r0) {
            case 96: goto L_0x0098;
            case 97: goto L_0x03b9;
            default: goto L_0x0068;
        };
    L_0x0068:
        goto L_0x03c4;
    L_0x006a:
        r13 = r13.getAction();
        if (r13 != r5) goto L_0x03c4;
    L_0x0070:
        r13 = r12.mVodRl;
        r13 = r13.getVisibility();
        if (r13 != 0) goto L_0x03c4;
    L_0x0078:
        r12.mVodPlayerControlsSelectedStatus = r3;
        r13 = r12.mXumoExoPlayer;
        r13.setVodBackOrFwd(r1);
        goto L_0x03c4;
    L_0x0081:
        r13 = r13.getAction();
        if (r13 != r5) goto L_0x03c4;
    L_0x0087:
        r13 = r12.mVodRl;
        r13 = r13.getVisibility();
        if (r13 != 0) goto L_0x03c4;
    L_0x008f:
        r12.mVodPlayerControlsSelectedStatus = r9;
        r13 = r12.mXumoExoPlayer;
        r13.setVodBackOrFwd(r5);
        goto L_0x03c4;
    L_0x0098:
        r0 = r12.mVodRl;
        r0 = r0.getVisibility();
        r10 = 4654311885213007872; // 0x4097700000000000 float:0.0 double:1500.0;
        if (r0 != 0) goto L_0x0180;
    L_0x00a5:
        r0 = r12.mVodPlayerControlsSelectedStatus;
        if (r0 != r5) goto L_0x00ca;
    L_0x00a9:
        r13 = r13.getAction();
        if (r13 != 0) goto L_0x03c4;
    L_0x00af:
        r0 = java.lang.System.currentTimeMillis();
        r0 = (double) r0;
        r2 = DOUBLE_CLICK_TIME;
        java.lang.Double.isNaN(r0);
        r0 = r0 - r2;
        r13 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1));
        if (r13 <= 0) goto L_0x03c4;
    L_0x00be:
        r0 = java.lang.System.currentTimeMillis();
        r0 = (double) r0;
        DOUBLE_CLICK_TIME = r0;
        r12.loadPreVideoAsset();
        goto L_0x03c4;
    L_0x00ca:
        r0 = r12.mVodPlayerControlsSelectedStatus;
        if (r0 != r9) goto L_0x00db;
    L_0x00ce:
        r13 = r13.getAction();
        if (r13 != r5) goto L_0x03c4;
    L_0x00d4:
        r13 = r12.mXumoExoPlayer;
        r13.setVodBackOrFwd(r5);
        goto L_0x03c4;
    L_0x00db:
        r0 = r12.mVodPlayerControlsSelectedStatus;
        if (r0 != r4) goto L_0x00f7;
    L_0x00df:
        r13 = r13.getAction();
        if (r13 != 0) goto L_0x03c4;
    L_0x00e5:
        r13 = r12.mXumoExoPlayer;
        r13 = r13.getPlayWhenReady();
        if (r13 == 0) goto L_0x00f2;
    L_0x00ed:
        r12.pause();
        goto L_0x03c4;
    L_0x00f2:
        r12.play();
        goto L_0x03c4;
    L_0x00f7:
        r0 = r12.mVodPlayerControlsSelectedStatus;
        if (r0 != r3) goto L_0x0108;
    L_0x00fb:
        r13 = r13.getAction();
        if (r13 != r5) goto L_0x03c4;
    L_0x0101:
        r13 = r12.mXumoExoPlayer;
        r13.setVodBackOrFwd(r1);
        goto L_0x03c4;
    L_0x0108:
        r0 = r12.mVodPlayerControlsSelectedStatus;
        if (r0 != r2) goto L_0x012d;
    L_0x010c:
        r13 = r13.getAction();
        if (r13 != 0) goto L_0x03c4;
    L_0x0112:
        r0 = java.lang.System.currentTimeMillis();
        r0 = (double) r0;
        r2 = DOUBLE_CLICK_TIME;
        java.lang.Double.isNaN(r0);
        r0 = r0 - r2;
        r13 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1));
        if (r13 <= 0) goto L_0x03c4;
    L_0x0121:
        r0 = java.lang.System.currentTimeMillis();
        r0 = (double) r0;
        DOUBLE_CLICK_TIME = r0;
        r12.loadNextVideoAsset();
        goto L_0x03c4;
    L_0x012d:
        r0 = r12.mVodPlayerControlsSelectedStatus;
        if (r0 != r8) goto L_0x03c4;
    L_0x0131:
        r0 = r12.mHasCaption;
        if (r0 == 0) goto L_0x03c4;
    L_0x0135:
        r13 = r13.getAction();
        if (r13 != r5) goto L_0x03c4;
    L_0x013b:
        r13 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r13 = r13.getSubtitleSwitch();
        if (r13 == 0) goto L_0x0165;
    L_0x0145:
        r12.checkVodCcButtonStyle(r5, r1);
        r13 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r13.setSubtitleSwitch(r1);
        r13 = r12.mDefaultSwitch;
        r13.setChecked(r1);
        r13 = r12.mAppTv;
        r0 = r12.getResources();
        r1 = 2131099890; // 0x7f0600f2 float:1.7812146E38 double:1.0529032435E-314;
        r0 = r0.getColor(r1);
        r13.setTextColor(r0);
        goto L_0x0179;
    L_0x0165:
        r12.checkVodCcButtonStyle(r5, r5);
        r13 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r13.setSubtitleSwitch(r5);
        r13 = r12.mDefaultSwitch;
        r13.setChecked(r5);
        r13 = r12.mAppTv;
        r13.setTextColor(r6);
    L_0x0179:
        r13 = r12.mXumoExoPlayer;
        r13.setSwitchSubtitle();
        goto L_0x03c4;
    L_0x0180:
        r13 = r13.getAction();
        if (r13 != 0) goto L_0x03c4;
    L_0x0186:
        r13 = r12.mVodMoreFromIndex;
        switch(r13) {
            case 0: goto L_0x01cc;
            case 1: goto L_0x01ae;
            case 2: goto L_0x018d;
            default: goto L_0x018b;
        };
    L_0x018b:
        goto L_0x03c4;
    L_0x018d:
        r13 = r12.mPlaySourceCategory;
        r0 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.PlaySourceCategory.MoviesVideo;
        if (r13 != r0) goto L_0x0198;
    L_0x0193:
        r13 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.Movies;
        r12.mMainNavigationPageSelectState = r13;
        goto L_0x01a2;
    L_0x0198:
        r13 = r12.mPlaySourceCategory;
        r0 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.PlaySourceCategory.BrandPageVideo;
        if (r13 != r0) goto L_0x01a2;
    L_0x019e:
        r13 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.MainNavigationPageSelectState.AllChannels;
        r12.mMainNavigationPageSelectState = r13;
    L_0x01a2:
        r12.isResetMainNavigationDisplay = r5;
        r12.animateIn();
        r13 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForVod;
        r12.hidePage(r13);
        goto L_0x03c4;
    L_0x01ae:
        r13 = r12.mCurrentVideoAsset;
        r13 = r13.getChannelId();
        if (r13 == 0) goto L_0x03c4;
    L_0x01b6:
        r13 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.BrandPage;
        r0 = new java.lang.Object[r5];
        r2 = r12.mCurrentVideoAsset;
        r2 = r2.getChannelId();
        r0[r1] = r2;
        r12.showPage(r13, r0);
        r13 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForVod;
        r12.hidePage(r13);
        goto L_0x03c4;
    L_0x01cc:
        r13 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForVod;
        r12.hidePage(r13);
        r0 = java.lang.System.currentTimeMillis();
        r0 = (double) r0;
        r2 = DOUBLE_CLICK_TIME;
        java.lang.Double.isNaN(r0);
        r0 = r0 - r2;
        r13 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1));
        if (r13 <= 0) goto L_0x03c4;
    L_0x01e0:
        r0 = java.lang.System.currentTimeMillis();
        r0 = (double) r0;
        DOUBLE_CLICK_TIME = r0;
        r12.loadNextVideoAsset();
        goto L_0x03c4;
    L_0x01ec:
        r0 = r13.getAction();
        if (r0 != 0) goto L_0x03c4;
    L_0x01f2:
        r0 = r12.mVodRl;
        r0 = r0.getVisibility();
        if (r0 != 0) goto L_0x026c;
    L_0x01fa:
        r0 = r12.mPlaySourceCategory;
        r4 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.PlaySourceCategory.MoviesVideo;
        if (r0 != r4) goto L_0x0201;
    L_0x0200:
        r2 = 4;
    L_0x0201:
        r0 = r12.mHasCaption;
        if (r0 == 0) goto L_0x0207;
    L_0x0205:
        r2 = r2 + 1;
    L_0x0207:
        r0 = r12.mVodPlayerControlsSelectedStatus;
        if (r0 != 0) goto L_0x0220;
    L_0x020b:
        r0 = r12.mXumoExoPlayer;
        r2 = r13.getKeyCode();
        r0.setTimeBarKeyDown(r2, r13);
        r13 = r12.mXumoExoPlayer;
        r13 = r13.getPlayWhenReady();
        if (r13 != 0) goto L_0x024a;
    L_0x021c:
        r12.play();
        goto L_0x024a;
    L_0x0220:
        r13 = r12.mVodPlayerControlsSelectedStatus;
        if (r13 >= r2) goto L_0x024a;
    L_0x0224:
        r13 = r12.mVodPlayerControlsSelectedStatus;
        if (r13 <= 0) goto L_0x024a;
    L_0x0228:
        r13 = r12.mVodPlayerControlsSelectedStatus;
        if (r13 != r3) goto L_0x0245;
    L_0x022c:
        r13 = r12.mPlaySourceCategory;
        r0 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.PlaySourceCategory.MoviesVideo;
        if (r13 == r0) goto L_0x023e;
    L_0x0232:
        r13 = r12.mVideoPlaylist;
        if (r13 == 0) goto L_0x0245;
    L_0x0236:
        r13 = r12.mVideoPlaylist;
        r13 = r13.hasNextVideo();
        if (r13 != 0) goto L_0x0245;
    L_0x023e:
        r13 = r12.mHasCaption;
        if (r13 == 0) goto L_0x024a;
    L_0x0242:
        r12.mVodPlayerControlsSelectedStatus = r8;
        goto L_0x024a;
    L_0x0245:
        r13 = r12.mVodPlayerControlsSelectedStatus;
        r13 = r13 + r5;
        r12.mVodPlayerControlsSelectedStatus = r13;
    L_0x024a:
        r13 = r12.mVodPlayerControlsSelectedStatus;
        if (r13 != r8) goto L_0x025f;
    L_0x024e:
        r13 = r12.mHasCaption;
        if (r13 == 0) goto L_0x025f;
    L_0x0252:
        r13 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r13 = r13.getSubtitleSwitch();
        r12.checkVodCcButtonStyle(r5, r13);
        goto L_0x03c4;
    L_0x025f:
        r13 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r13 = r13.getSubtitleSwitch();
        r12.checkVodCcButtonStyle(r1, r13);
        goto L_0x03c4;
    L_0x026c:
        r13 = r12.mVodMoreFromIndex;
        if (r13 >= r9) goto L_0x0286;
    L_0x0270:
        r13 = r12.mVodMoreFromIndex;
        r13 = r13 + r5;
        r12.mVodMoreFromIndex = r13;
        r13 = r12.mVodMoreFromIndex;
        if (r13 != r5) goto L_0x0286;
    L_0x0279:
        r13 = r12.mVodMoreFromTileLy;
        r13 = r13.getVisibility();
        if (r13 == 0) goto L_0x0286;
    L_0x0281:
        r13 = r12.mVodMoreFromIndex;
        r13 = r13 + r5;
        r12.mVodMoreFromIndex = r13;
    L_0x0286:
        r12.setMoreFromColor();
        goto L_0x03c4;
    L_0x028b:
        r0 = r13.getAction();
        if (r0 != 0) goto L_0x03c4;
    L_0x0291:
        r0 = r12.mVodRl;
        r0 = r0.getVisibility();
        if (r0 != 0) goto L_0x0300;
    L_0x0299:
        r0 = r12.mVodPlayerControlsSelectedStatus;
        if (r0 != 0) goto L_0x02b3;
    L_0x029d:
        r0 = r12.mXumoExoPlayer;
        r1 = r13.getKeyCode();
        r0.setTimeBarKeyDown(r1, r13);
        r13 = r12.mXumoExoPlayer;
        r13 = r13.getPlayWhenReady();
        if (r13 != 0) goto L_0x03c4;
    L_0x02ae:
        r12.play();
        goto L_0x03c4;
    L_0x02b3:
        r13 = r12.mVodPlayerControlsSelectedStatus;
        if (r13 <= r9) goto L_0x02c9;
    L_0x02b7:
        r13 = r12.mPlaySourceCategory;
        r0 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.PlaySourceCategory.MoviesVideo;
        if (r13 == r0) goto L_0x02df;
    L_0x02bd:
        r13 = r12.mVideoPlaylist;
        if (r13 == 0) goto L_0x02df;
    L_0x02c1:
        r13 = r12.mVideoPlaylist;
        r13 = r13.hasPrevVideo();
        if (r13 == 0) goto L_0x02df;
    L_0x02c9:
        r13 = r12.mVodPlayerControlsSelectedStatus;
        if (r13 <= r5) goto L_0x03c4;
    L_0x02cd:
        r13 = r12.mPlaySourceCategory;
        r0 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.PlaySourceCategory.BrandPageVideo;
        if (r13 != r0) goto L_0x03c4;
    L_0x02d3:
        r13 = r12.mVideoPlaylist;
        if (r13 == 0) goto L_0x03c4;
    L_0x02d7:
        r13 = r12.mVideoPlaylist;
        r13 = r13.hasPrevVideo();
        if (r13 == 0) goto L_0x03c4;
    L_0x02df:
        r13 = r12.mVodPlayerControlsSelectedStatus;
        if (r13 != r8) goto L_0x02f9;
    L_0x02e3:
        r13 = r12.mPlaySourceCategory;
        r0 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.PlaySourceCategory.MoviesVideo;
        if (r13 == r0) goto L_0x02f5;
    L_0x02e9:
        r13 = r12.mVideoPlaylist;
        if (r13 == 0) goto L_0x02f5;
    L_0x02ed:
        r13 = r12.mVideoPlaylist;
        r13 = r13.hasNextVideo();
        if (r13 != 0) goto L_0x02f9;
    L_0x02f5:
        r12.mVodPlayerControlsSelectedStatus = r3;
        goto L_0x03c4;
    L_0x02f9:
        r13 = r12.mVodPlayerControlsSelectedStatus;
        r13 = r13 - r5;
        r12.mVodPlayerControlsSelectedStatus = r13;
        goto L_0x03c4;
    L_0x0300:
        r13 = r12.mVodUpNextFatherLy;
        r13 = r13.getVisibility();
        if (r13 != 0) goto L_0x0323;
    L_0x0308:
        r13 = r12.mVodMoreFromIndex;
        if (r13 <= 0) goto L_0x032c;
    L_0x030c:
        r13 = r12.mVodMoreFromIndex;
        r13 = r13 - r5;
        r12.mVodMoreFromIndex = r13;
        r13 = r12.mVodMoreFromIndex;
        if (r13 != r5) goto L_0x032c;
    L_0x0315:
        r13 = r12.mVodMoreFromTileLy;
        r13 = r13.getVisibility();
        if (r13 == 0) goto L_0x032c;
    L_0x031d:
        r13 = r12.mVodMoreFromIndex;
        r13 = r13 - r5;
        r12.mVodMoreFromIndex = r13;
        goto L_0x032c;
    L_0x0323:
        r13 = r12.mVodMoreFromIndex;
        if (r13 <= r5) goto L_0x032c;
    L_0x0327:
        r13 = r12.mVodMoreFromIndex;
        r13 = r13 - r5;
        r12.mVodMoreFromIndex = r13;
    L_0x032c:
        r12.setMoreFromColor();
        goto L_0x03c4;
    L_0x0331:
        r13 = r13.getAction();
        if (r13 != 0) goto L_0x03c4;
    L_0x0337:
        r13 = r12.mVodPlayerControlsSelectedStatus;
        if (r13 != 0) goto L_0x033f;
    L_0x033b:
        r12.mVodPlayerControlsSelectedStatus = r4;
        goto L_0x03c4;
    L_0x033f:
        r13 = r12.mVodRl;
        r13 = r13.getVisibility();
        if (r13 != 0) goto L_0x03c4;
    L_0x0347:
        r13 = r12.mVodRl;
        r13.setVisibility(r7);
        r13 = r12.mVodBackToChannelInfoTv;
        r13.setVisibility(r1);
        r13 = r12.mVodUpNextFatherLy;
        r13 = r13.getVisibility();
        if (r13 != 0) goto L_0x035c;
    L_0x0359:
        r12.mVodMoreFromIndex = r1;
        goto L_0x035e;
    L_0x035c:
        r12.mVodMoreFromIndex = r5;
    L_0x035e:
        r12.setMoreFromColor();
        goto L_0x03c4;
    L_0x0362:
        r13 = r13.getAction();
        if (r13 != 0) goto L_0x03c4;
    L_0x0368:
        r13 = r12.mVodRl;
        r13 = r13.getVisibility();
        if (r13 != 0) goto L_0x0387;
    L_0x0370:
        r13 = r12.mXumoExoPlayer;
        r13 = r13.getCurrentTimeline();
        if (r13 == 0) goto L_0x03c4;
    L_0x0378:
        r13 = r12.mXumoExoPlayer;
        r13 = r13.getCurrentTimeline();
        r13 = r13.isEmpty();
        if (r13 != 0) goto L_0x03c4;
    L_0x0384:
        r12.mVodPlayerControlsSelectedStatus = r1;
        goto L_0x03c4;
    L_0x0387:
        r13 = r12.mVodRl;
        r13.setVisibility(r1);
        r13 = r12.mVodBackToChannelInfoTv;
        r13.setVisibility(r7);
        r12.mVodPlayerControlsSelectedStatus = r4;
        r12.mVodMoreFromIndex = r6;
        r12.setMoreFromColor();
        goto L_0x03c4;
    L_0x0399:
        r13 = r13.getAction();
        if (r13 != r5) goto L_0x03c4;
    L_0x039f:
        r13 = r12.mVodRl;
        r13 = r13.getVisibility();
        if (r13 != 0) goto L_0x03c4;
    L_0x03a7:
        r12.mVodPlayerControlsSelectedStatus = r4;
        r13 = r12.mXumoExoPlayer;
        r13 = r13.getPlayWhenReady();
        if (r13 == 0) goto L_0x03b5;
    L_0x03b1:
        r12.pause();
        goto L_0x03c4;
    L_0x03b5:
        r12.play();
        goto L_0x03c4;
    L_0x03b9:
        r13 = r13.getAction();
        if (r13 != 0) goto L_0x03c4;
    L_0x03bf:
        r13 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.PlayerControlsPageForVod;
        r12.hidePage(r13);
    L_0x03c4:
        r13 = r12.mVodPlayerControlsSelectedStatus;
        r12.updateVodPlayerControlsSelectedStatus(r13);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.fragmenttv.OnNowPlayerFragment.onKeyPressForVodPlayerControlsPage(android.view.KeyEvent):void");
    }

    private void setMoreFromColor() {
        this.mVodUpNextLy.setBackgroundColor(0);
        this.mVodUpNextBottomLy.setBackgroundResource(R.color.black70);
        this.mVodUpNextTitleTv.setTextColor(getResources().getColor(R.color.xumoGray));
        this.mVodUpNextContentTv.setTextColor(-1);
        this.mVodMoreFromSelectLy.setBackgroundColor(0);
        this.mVodMoreFromBottomLy.setBackgroundResource(R.color.black70);
        this.mVodMoreFromTitleTv.setTextColor(getResources().getColor(R.color.xumoGray));
        this.mVodMoreFromContentTv.setTextColor(-1);
        this.mVodMainMenuLy.setBackgroundColor(0);
        this.mVodMainMenuBottomLy.setBackgroundResource(R.color.black70);
        this.mVodMainMenuTitleTv.setTextColor(getResources().getColor(R.color.xumoGray));
        this.mVodMainMenuContentTv.setTextColor(-1);
        switch (this.mVodMoreFromIndex) {
            case 0:
                this.mVodUpNextLy.setBackgroundResource(R.drawable.shape_rectangle_blue);
                this.mVodUpNextBottomLy.setBackgroundColor(-1);
                this.mVodUpNextTitleTv.setTextColor(getResources().getColor(R.color.xumoBlue));
                this.mVodUpNextContentTv.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                return;
            case 1:
                this.mVodMoreFromSelectLy.setBackgroundResource(R.drawable.shape_rectangle_blue);
                this.mVodMoreFromBottomLy.setBackgroundColor(-1);
                this.mVodMoreFromTitleTv.setTextColor(getResources().getColor(R.color.xumoBlue));
                this.mVodMoreFromContentTv.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                return;
            case 2:
                this.mVodMainMenuLy.setBackgroundResource(R.drawable.shape_rectangle_blue);
                this.mVodMainMenuBottomLy.setBackgroundColor(-1);
                this.mVodMainMenuTitleTv.setTextColor(getResources().getColor(R.color.xumoBlue));
                this.mVodMainMenuContentTv.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                return;
            default:
                return;
        }
    }

    private void updateVodPlayerControlsSelectedStatus(int i) {
        this.mXumoExoPlayer.setScrubberColor(Color.rgb(255, 255, 255));
        this.mMoviesSkipBackButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_controls_rewind_normal));
        this.mMoviesPlayButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_controls_play_normal));
        this.mMoviesPauseButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_controls_pause_normal));
        this.mMoviesSkipFwdButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_controls_fastforward_normal));
        if (this.mPlaySourceCategory == PlaySourceCategory.MoviesVideo) {
            this.mVodHasPreButton = false;
            this.mVodHasNextButton = false;
        } else {
            this.mVodHasPreButton = true;
            this.mVodHasNextButton = true;
        }
        this.mMoviesPreVodButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_controls_previouse_normal));
        this.mMoviesNextVodButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_controls_next_normal));
        switch (i) {
            case 0:
                this.mXumoExoPlayer.setScrubberColor(Color.rgb(0, 179, 227));
                return;
            case 1:
                this.mMoviesPreVodButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_controls_previouse_hover));
                return;
            case 2:
                this.mMoviesSkipBackButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_controls_rewind_hover));
                return;
            case 3:
                this.mMoviesPlayButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_controls_play_hover));
                this.mMoviesPauseButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_controls_pause_hover));
                return;
            case 4:
                this.mMoviesSkipFwdButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_controls_fastforward_hover));
                return;
            case 5:
                this.mMoviesNextVodButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_controls_next_hover));
                return;
            default:
                return;
        }
    }

    private void checkVodCcButtonStyle(boolean z, boolean z2) {
        if (z) {
            if (z2) {
                this.mVodCcButton.setImageDrawable(getResources().getDrawable(R.drawable.player_btn_cc_on_hover));
            } else {
                this.mVodCcButton.setImageDrawable(getResources().getDrawable(R.drawable.player_btn_cc_off_hover));
            }
        } else if (z2) {
            this.mVodCcButton.setImageDrawable(getResources().getDrawable(R.drawable.player_btn_cc_on_normal));
        } else {
            this.mVodCcButton.setImageDrawable(getResources().getDrawable(R.drawable.player_btn_cc_off_normal));
        }
    }

    private void setCcButtonVisibility(boolean z) {
        if (z) {
            if (this.mHasCaption) {
                this.mOnNowCcButton.setVisibility(0);
                if (UserPreferences.getInstance().getSubtitleSwitch()) {
                    this.mOnNowCcButton.setImageDrawable(getResources().getDrawable(R.drawable.player_btn_cc_on_hover));
                    return;
                } else {
                    this.mOnNowCcButton.setImageDrawable(getResources().getDrawable(R.drawable.player_btn_cc_off_hover));
                    return;
                }
            }
            this.mOnNowCcButton.setVisibility(8);
        } else if (this.mHasCaption) {
            this.mVodHasCcButton = true;
            checkVodCcButtonStyle(false, UserPreferences.getInstance().getSubtitleSwitch());
        } else {
            this.mVodHasCcButton = false;
        }
    }

    private boolean isGenreListPageVisible() {
        return this.mGenreSelectorListLayout != null && this.mGenreSelectorListLayout.getVisibility() == 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onKeyPressForGenreListPage(android.view.KeyEvent r3) {
        /*
        r2 = this;
        r3 = r3.getKeyCode();
        r0 = 4;
        if (r3 == r0) goto L_0x0068;
    L_0x0007:
        r0 = 90;
        if (r3 == r0) goto L_0x0068;
    L_0x000b:
        switch(r3) {
            case 19: goto L_0x0050;
            case 20: goto L_0x0038;
            case 21: goto L_0x006d;
            case 22: goto L_0x006d;
            case 23: goto L_0x0012;
            default: goto L_0x000e;
        };
    L_0x000e:
        switch(r3) {
            case 96: goto L_0x0012;
            case 97: goto L_0x0068;
            default: goto L_0x0011;
        };
    L_0x0011:
        goto L_0x006d;
    L_0x0012:
        r3 = r2.mGenreSelectorAdapter;
        if (r3 == 0) goto L_0x0032;
    L_0x0016:
        r3 = r2.mGenreSelectorAdapter;
        r3.navigateToSelectedGenre();
        r3 = r2.mGenreSelectorAdapter;
        r0 = mOnNowLives;
        r1 = r2.mChannelInfoRecyclerView;
        r1 = r1.getSelectedItemIndex();
        r0 = r0.get(r1);
        r0 = (com.xumo.xumo.model.OnNowLive) r0;
        r0 = r0.getGenre();
        r3.setSelectedGenreIndex(r0);
    L_0x0032:
        r3 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.GenreListPage;
        r2.hidePage(r3);
        goto L_0x006d;
    L_0x0038:
        r3 = r2.mGenreSelectorList;
        if (r3 == 0) goto L_0x006d;
    L_0x003c:
        r3 = r2.mGenreSelectorList;
        r0 = 0;
        r3.selectGenreItem(r0);
        r3 = new android.os.Handler;
        r3.<init>();
        r0 = new com.xumo.xumo.fragmenttv.OnNowPlayerFragment$29;
        r0.<init>();
        r3.post(r0);
        goto L_0x006d;
    L_0x0050:
        r3 = r2.mGenreSelectorList;
        if (r3 == 0) goto L_0x006d;
    L_0x0054:
        r3 = r2.mGenreSelectorList;
        r0 = 1;
        r3.selectGenreItem(r0);
        r3 = new android.os.Handler;
        r3.<init>();
        r0 = new com.xumo.xumo.fragmenttv.OnNowPlayerFragment$28;
        r0.<init>();
        r3.post(r0);
        goto L_0x006d;
    L_0x0068:
        r3 = com.xumo.xumo.fragmenttv.OnNowPlayerFragment.DisplayView.GenreListPage;
        r2.hidePage(r3);
    L_0x006d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.fragmenttv.OnNowPlayerFragment.onKeyPressForGenreListPage(android.view.KeyEvent):void");
    }

    private void navigateToGenre(@NonNull String str) {
        LogUtil.d(" - Method start.");
        if (mOnNowLives != null) {
            if (mOnNowLives.size() != 0) {
                int size = mOnNowLives.size();
                for (int i = 0; i < size; i++) {
                    if (str.equals(((OnNowLive) mOnNowLives.get(i)).getGenre())) {
                        if (this.mChannelInfoRecyclerView != null) {
                            this.mChannelInfoRecyclerView.selectItem(i);
                        }
                    }
                }
            }
        }
    }

    public void onFavoriteStateChanged(String str, boolean z) {
        if (!TextUtils.isEmpty(str) && mOnNowLives != null) {
            int size = mOnNowLives.size();
            for (int i = 0; i < size; i++) {
                if (str.equals(((OnNowLive) mOnNowLives.get(i)).getChannelId())) {
                    addOrDeleteOnNowLivesFavorites(i, z, true);
                    break;
                }
            }
        }
        setFavoritesAndMostPopularGenre();
        if (this.mSelectChannelsTitleIndex == null && !z && this.mSelectChannelsContentIndex >= null) {
            if (((Genre) this.mAllChannelsGenreList.get(this.mSelectChannelsContentIndex)).getChannelIdList().size() > null && this.mSelectChannelsContentIndex >= ((Genre) this.mAllChannelsGenreList.get(0)).getChannelIdList().size()) {
                this.mSelectChannelsContentIndex -= 1;
            }
            this.mChannelsContentRv.scrollToPosition(0);
        }
        if (!(this.mChannelsContentRv == null || this.mChannelsContentRv.getAdapter() == null)) {
            this.mChannelsContentRv.getAdapter().notifyDataSetChanged();
        }
        if (this.mGenreListAdapter != null) {
            this.mGenreListAdapter.notifyDataSetChanged();
        }
    }

    public void onPlayOnNowLive(String str) {
        this.tvBrandPageFragment = null;
        int i = 0;
        if (isMainNavigationPageVisible()) {
            animateOut(0);
        }
        if (TextUtils.isEmpty(str)) {
            LogUtil.d("channelId is empty.");
            return;
        }
        int size = mOnNowLives.size();
        while (i < size) {
            OnNowLive onNowLive = (OnNowLive) mOnNowLives.get(i);
            if (str.equals(onNowLive.getChannelId())) {
                this.mMainNavigationPageSelectState = MainNavigationPageSelectState.OnNow;
                this.isResetMainNavigationDisplay = true;
                playOnNowLive(onNowLive, i);
                return;
            }
            i++;
        }
    }

    public void onPlayVideoAsset(Channel channel, VideoAsset videoAsset) {
        if (isMainNavigationPageVisible()) {
            animateOut(0);
        }
        if (videoAsset == null) {
            LogUtil.d("videoAsset is null.");
            return;
        }
        this.mPlaySourceCategory = PlaySourceCategory.BrandPageVideo;
        this.mVodPlayerControlsSelectedStatus = 3;
        updateVodPlayerControlsSelectedStatus(this.mVodPlayerControlsSelectedStatus);
        if (UserPreferences.getInstance().getViewBoost()) {
            UserPreferences.getInstance().setViewBoost(false);
        }
        playVideoForBrandPage(channel, videoAsset.getAssetId());
    }

    public void onPressKeyBackInBrandPage() {
        if (this.tvBrandPageFragment != null) {
            this.tvBrandPageFragment = null;
        }
        if (isMainNavigationPageVisible()) {
            if (UserPreferences.getInstance().getViewBoost()) {
                showAllChannels(true);
            }
            this.mNavigationContainer.setAlpha(1.0f);
        }
    }

    public void getTvBrandPageState() {
        if (isMainNavigationPageVisible()) {
            animateIn(0.01f);
        }
    }

    private boolean isDebugPageVisible() {
        return this.debugFragment != null && this.debugFragment.isVisible();
    }

    private boolean isBrandPageVisible() {
        return this.tvBrandPageFragment != null && this.tvBrandPageFragment.isVisible();
    }

    private boolean isWebPageVisible() {
        return this.webFragment != null && this.webFragment.isVisible();
    }

    private void playVideoForBrandPage(final Channel channel, final String str) {
        if (channel != null) {
            if (!TextUtils.isEmpty(channel.getChannelId())) {
                this.mXumoDataSync.getChannelInfoForChannelId(channel.getChannelId(), new Listener() {
                    public void onCompletion(Object obj) {
                        if (OnNowPlayerFragment.this.getHost() != null) {
                            Channel channel = (Channel) obj;
                            channel.setGenreId(channel.getGenreId());
                            channel.setGenreName(channel.getGenreName());
                            channel.setChannelTitle(channel.getChannelTitle());
                            channel.setDescriptionText(channel.getDescriptionText());
                            OnNowPlayerFragment.this.createVideoPlaylist(channel);
                            if (!(OnNowPlayerFragment.this.mVideoPlaylist == null || OnNowPlayerFragment.this.mVideoPlaylist.notifyStartVideo(str) == null)) {
                                OnNowPlayerFragment.this.loadVideoAssetPlayer(OnNowPlayerFragment.this.mVideoPlaylist.getPlayingVideoAsset());
                            }
                        }
                    }

                    public void onError() {
                        LogUtil.d("getChannelInfomation getChannelInfoForChannelId failed.");
                        OnNowPlayerFragment.this.createVideoPlaylist(channel);
                        if (OnNowPlayerFragment.this.mVideoPlaylist != null && OnNowPlayerFragment.this.mVideoPlaylist.notifyStartVideo(str)) {
                            OnNowPlayerFragment.this.loadVideoAssetPlayer(OnNowPlayerFragment.this.mVideoPlaylist.getPlayingVideoAsset());
                        }
                    }
                });
                return;
            }
        }
        LogUtil.w("channelId is empty.");
    }

    public void onPressKeyBackInSettingsCaptioningPage() {
        this.mSettingsCaptioningFragment = null;
    }

    public void changeExoPlayerSubtitle() {
        this.mXumoExoPlayer.setSubTitle();
        this.mSettingsCaptioningFragment = null;
    }

    public void getSettingsCaptioningPageState() {
        animateIn();
    }

    private boolean isSettingsCaptioningPageVisible() {
        return this.mSettingsCaptioningFragment != null && this.mSettingsCaptioningFragment.isVisible();
    }

    public void loadNextVodInVodNextPage() {
        this.moviesUpNextPageFragment = null;
        loadNextVideoAsset();
    }

    public void backToMoviesInVodNextPage() {
        this.moviesUpNextPageFragment = null;
        this.mMainNavigationPageSelectState = MainNavigationPageSelectState.Movies;
        this.isResetMainNavigationDisplay = true;
        this.mXumoExoPlayer.hideController();
        animateIn();
    }

    private boolean isMoviesUpNextPageVisible() {
        return this.moviesUpNextPageFragment != null && this.moviesUpNextPageFragment.isVisible();
    }

    private boolean isExitDialogVisible() {
        return this.mExitDialog != null && this.mExitDialog.isShowing();
    }

    private void showDebugFragment() {
        if (!isDebugPageVisible()) {
            this.debugFragment = new DebugFragment();
            this.debugFragment.setXumoExoPlayer(this.mXumoExoPlayer);
            getChildFragmentManager().beginTransaction().replace(R.id.debug_fl, this.debugFragment, DebugFragment.TAG_DEBUG).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    private void hideDebugFragment() {
        getChildFragmentManager().beginTransaction().remove(this.debugFragment).addToBackStack(null).commitAllowingStateLoss();
    }
}
