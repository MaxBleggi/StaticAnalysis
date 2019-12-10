package com.xumo.xumo.fragmenttv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.xumo.xumo.model.VideoAsset;
import com.xumo.xumo.tv.R;

public class MoviesUpNextPageFragment extends BaseFragment {
    public static final String TAG_VOD_NEXT_PAGE = "com.xumo.xumo.fragmenttv.MoviesUpNextPageFragment";
    private static PageListener mPageListener;
    private int mButtonIndex = 0;
    private TextView mVodNextBackButton;
    private RelativeLayout mVodNextBackButtonBg;
    private TextView mVodNextDescription;
    private TextView mVodNextPlayButton;
    private RelativeLayout mVodNextPlayButtonBg;
    private TextView mVodNextTitle;
    private VideoAsset videoAsset;

    interface PageListener {
        void backToMoviesInVodNextPage();

        void loadNextVodInVodNextPage();
    }

    public static MoviesUpNextPageFragment newInstance(PageListener pageListener, VideoAsset videoAsset) {
        MoviesUpNextPageFragment moviesUpNextPageFragment = new MoviesUpNextPageFragment();
        if (pageListener != null) {
            mPageListener = pageListener;
        }
        if (videoAsset != null) {
            pageListener = new Bundle();
            pageListener.putSerializable("videoAsset", videoAsset);
            moviesUpNextPageFragment.setArguments(pageListener);
        }
        return moviesUpNextPageFragment;
    }

    @SuppressLint({"ValidFragment"})
    private MoviesUpNextPageFragment() {
    }

    public void onFinish() {
        finish();
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        return layoutInflater.inflate(R.layout.tv_fragment_vod_next_page, viewGroup, false);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(view);
        if (getArguments() != null) {
            this.videoAsset = (VideoAsset) getArguments().getSerializable("videoAsset");
            this.mVodNextTitle.setText(this.videoAsset.getTitle());
            this.mVodNextDescription.setText(this.videoAsset.getDescriptionText());
        }
    }

    public void onStop() {
        super.onStop();
    }

    private void initView(View view) {
        this.mVodNextTitle = (TextView) view.findViewById(R.id.vod_next_title);
        this.mVodNextDescription = (TextView) view.findViewById(R.id.vod_next_description);
        this.mVodNextPlayButton = (TextView) view.findViewById(R.id.vod_next_play_button);
        this.mVodNextBackButton = (TextView) view.findViewById(R.id.vod_next_back_button);
        this.mVodNextPlayButtonBg = (RelativeLayout) view.findViewById(R.id.vod_next_play_button_bg);
        this.mVodNextBackButtonBg = (RelativeLayout) view.findViewById(R.id.vod_next_back_button_bg);
    }

    void onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 96) {
            switch (i) {
                case 19:
                case 20:
                    return;
                case 21:
                    this.mButtonIndex = 0;
                    this.mVodNextPlayButton.setTextColor(getResources().getColor(R.color.xumoBlue));
                    this.mVodNextBackButton.setTextColor(getResources().getColor(R.color.xumoWhite));
                    this.mVodNextPlayButtonBg.setBackgroundColor(getResources().getColor(R.color.xumoBlue));
                    this.mVodNextBackButtonBg.setBackgroundColor(getResources().getColor(R.color.xumoGray));
                    return;
                case 22:
                    this.mButtonIndex = 1;
                    this.mVodNextPlayButton.setTextColor(getResources().getColor(R.color.xumoWhite));
                    this.mVodNextBackButton.setTextColor(getResources().getColor(R.color.xumoBlue));
                    this.mVodNextPlayButtonBg.setBackgroundColor(getResources().getColor(R.color.xumoGray));
                    this.mVodNextBackButtonBg.setBackgroundColor(getResources().getColor(R.color.xumoBlue));
                    return;
                case 23:
                    break;
                default:
                    return;
            }
        }
        if (this.mButtonIndex == 0) {
            mPageListener.loadNextVodInVodNextPage();
        } else {
            mPageListener.backToMoviesInVodNextPage();
        }
        finish();
    }

    private void finish() {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(TAG_VOD_NEXT_PAGE);
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.remove(findFragmentByTag);
        beginTransaction.commitAllowingStateLoss();
    }
}
