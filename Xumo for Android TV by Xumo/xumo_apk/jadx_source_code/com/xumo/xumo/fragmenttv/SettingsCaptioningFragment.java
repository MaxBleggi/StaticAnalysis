package com.xumo.xumo.fragmenttv;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.xumo.xumo.repository.UserPreferences;
import com.xumo.xumo.tv.R;
import com.xumo.xumo.widget.XumoTextView;
import java.util.Timer;
import java.util.TimerTask;

public class SettingsCaptioningFragment extends BaseFragment {
    private static final int FINISH = 1;
    public static final String TAG_SETTINGS_CAPTIONING = "com.xumo.xumo.fragmenttv.SettingsCaptioningFragment";
    private static PageListener mPageListener;
    private int finish;
    @SuppressLint({"HandlerLeak"})
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 1) {
                SettingsCaptioningFragment.this.finish = SettingsCaptioningFragment.this.finish + 1;
                if (SettingsCaptioningFragment.this.finish > 3) {
                    UserPreferences.getInstance().setTextColor(SettingsCaptioningFragment.this.mInTextColorIndex);
                    UserPreferences.getInstance().setTextOpacity(SettingsCaptioningFragment.this.mInTextOpacityIndex);
                    UserPreferences.getInstance().setTextStyle(SettingsCaptioningFragment.this.mInTextStyleIndex);
                    UserPreferences.getInstance().setTextSize(SettingsCaptioningFragment.this.mInTextSizeIndex);
                    UserPreferences.getInstance().setEdgeType(SettingsCaptioningFragment.this.mInEdgeTypeIndex);
                    UserPreferences.getInstance().setEdgeColor(SettingsCaptioningFragment.this.mInEdgeColorIndex);
                    UserPreferences.getInstance().setBackgroundColor(SettingsCaptioningFragment.this.mInBackgroundColorIndex);
                    UserPreferences.getInstance().setBackgroundOpacity(SettingsCaptioningFragment.this.mInBackgroundOpacityIndex);
                    SettingsCaptioningFragment.this.finish(SettingsCaptioningFragment.mPageListener);
                } else if (SettingsCaptioningFragment.mPageListener != null) {
                    SettingsCaptioningFragment.mPageListener.getSettingsCaptioningPageState();
                }
            }
        }
    };
    private TextView mApplyTv;
    private LinearLayout mBackLy;
    private TextView mBackgroundColorBlackTv;
    private TextView mBackgroundColorBlueTv;
    private TextView mBackgroundColorContentTv;
    private TextView mBackgroundColorGreenTv;
    private int mBackgroundColorIndex = 1;
    private LinearLayout mBackgroundColorLy;
    private TextView mBackgroundColorRedTv;
    private TextView mBackgroundColorTitleTv;
    private View mBackgroundColorView;
    private TextView mBackgroundColorWhiteTv;
    private TextView mBackgroundColorYellowTv;
    private TextView mBackgroundOpacityContentTv;
    private TextView mBackgroundOpacityHighTv;
    private int mBackgroundOpacityIndex = 1;
    private TextView mBackgroundOpacityLowTv;
    private LinearLayout mBackgroundOpacityLy;
    private TextView mBackgroundOpacityMediumTv;
    private TextView mBackgroundOpacityNoneTv;
    private TextView mBackgroundOpacitySolidTv;
    private TextView mBackgroundOpacityTitleTv;
    private View mBackgroundOpacityView;
    private int mControlIndex = 1;
    private TextView mDefaultTv;
    private TextView mEdgeColorBlackTv;
    private TextView mEdgeColorBlueTv;
    private TextView mEdgeColorContentTv;
    private TextView mEdgeColorGreenTv;
    private int mEdgeColorIndex = 1;
    private LinearLayout mEdgeColorLy;
    private TextView mEdgeColorRedTv;
    private TextView mEdgeColorTitleTv;
    private View mEdgeColorView;
    private TextView mEdgeColorWhiteTv;
    private TextView mEdgeColorYellowTv;
    private TextView mEdgeTypeContentTv;
    private TextView mEdgeTypeDropShadowTv;
    private int mEdgeTypeIndex = 1;
    private LinearLayout mEdgeTypeLy;
    private TextView mEdgeTypeNoneTv;
    private TextView mEdgeTypeOutlineTv;
    private TextView mEdgeTypeTitleTv;
    private View mEdgeTypeView;
    private int mInBackgroundColorIndex = 2;
    private int mInBackgroundOpacityIndex = 4;
    private int mInEdgeColorIndex = 6;
    private int mInEdgeTypeIndex = 1;
    private int mInTextColorIndex = 1;
    private int mInTextOpacityIndex = 4;
    private int mInTextSizeIndex = 2;
    private int mInTextStyleIndex = 1;
    private XumoTextView mShowTv;
    private int mTableSelectIndex = 1;
    private TextView mTextColorBlackTv;
    private TextView mTextColorBlueTv;
    private TextView mTextColorContentTv;
    private TextView mTextColorGreenTv;
    private int mTextColorIndex = 1;
    private LinearLayout mTextColorLy;
    private TextView mTextColorRedTv;
    private TextView mTextColorTitleTv;
    private View mTextColorView;
    private TextView mTextColorWhiteTv;
    private TextView mTextColorYellowTv;
    private TextView mTextOpacityContentTv;
    private TextView mTextOpacityHighTv;
    private int mTextOpacityIndex = 1;
    private TextView mTextOpacityLowTv;
    private LinearLayout mTextOpacityLy;
    private TextView mTextOpacityMediumTv;
    private TextView mTextOpacitySolidTv;
    private TextView mTextOpacityTitleTv;
    private View mTextOpacityView;
    private TextView mTextSizeContentTv;
    private int mTextSizeIndex = 1;
    private TextView mTextSizeLargeTv;
    private LinearLayout mTextSizeLy;
    private TextView mTextSizeMediumTv;
    private TextView mTextSizeSmallTv;
    private TextView mTextSizeTitleTv;
    private View mTextSizeView;
    private TextView mTextStyleContentTv;
    private int mTextStyleIndex = 1;
    private LinearLayout mTextStyleLy;
    private TextView mTextStyleOneTv;
    private TextView mTextStyleThreeTv;
    private TextView mTextStyleTitleTv;
    private TextView mTextStyleTwoTv;
    private View mTextStyleView;
    private Timer mTimer;
    private TimerTask mTimerTask;

    interface PageListener {
        void changeExoPlayerSubtitle();

        void getSettingsCaptioningPageState();

        void onPressKeyBackInSettingsCaptioningPage();
    }

    public static SettingsCaptioningFragment newInstance(PageListener pageListener) {
        if (pageListener != null) {
            mPageListener = pageListener;
        }
        return new SettingsCaptioningFragment();
    }

    private SettingsCaptioningFragment() {
    }

    public void onFinish() {
        finish(null);
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        return layoutInflater.inflate(R.layout.tv_fragment_captions_appearance, viewGroup, false);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(view);
        setItemDefaultBackgroundColor();
        this.mInTextColorIndex = UserPreferences.getInstance().getTextColor();
        this.mInTextOpacityIndex = UserPreferences.getInstance().getTextOpacity();
        this.mInTextStyleIndex = UserPreferences.getInstance().getTextStyle();
        this.mInTextSizeIndex = UserPreferences.getInstance().getTextSize();
        this.mInEdgeTypeIndex = UserPreferences.getInstance().getEdgeType();
        this.mInEdgeColorIndex = UserPreferences.getInstance().getEdgeColor();
        this.mInBackgroundColorIndex = UserPreferences.getInstance().getBackgroundColor();
        this.mInBackgroundOpacityIndex = UserPreferences.getInstance().getBackgroundOpacity();
        this.mBackLy.setBackgroundResource(R.drawable.closed_caption_background);
        this.mTextColorWhiteTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
        initTimer();
    }

    public void onStop() {
        super.onStop();
        this.mTimer.cancel();
        this.mTimerTask.cancel();
    }

    private void initView(View view) {
        this.mBackLy = (LinearLayout) view.findViewById(R.id.back_ly);
        this.mTextColorView = view.findViewById(R.id.text_color_view);
        this.mTextColorTitleTv = (TextView) view.findViewById(R.id.text_color_title_tv);
        this.mTextColorContentTv = (TextView) view.findViewById(R.id.text_color_content_tv);
        this.mTextColorLy = (LinearLayout) view.findViewById(R.id.text_color_ly);
        this.mTextColorWhiteTv = (TextView) view.findViewById(R.id.text_color_white_tv);
        this.mTextColorBlackTv = (TextView) view.findViewById(R.id.text_color_black_tv);
        this.mTextColorYellowTv = (TextView) view.findViewById(R.id.text_color_yellow_tv);
        this.mTextColorGreenTv = (TextView) view.findViewById(R.id.text_color_green_tv);
        this.mTextColorRedTv = (TextView) view.findViewById(R.id.text_color_red_tv);
        this.mTextColorBlueTv = (TextView) view.findViewById(R.id.text_color_blue_tv);
        this.mTextOpacityView = view.findViewById(R.id.text_opacity_view);
        this.mTextOpacityTitleTv = (TextView) view.findViewById(R.id.text_opacity_title_tv);
        this.mTextOpacityContentTv = (TextView) view.findViewById(R.id.text_opacity_content_tv);
        this.mTextOpacityLy = (LinearLayout) view.findViewById(R.id.text_opacity_ly);
        this.mTextOpacityLowTv = (TextView) view.findViewById(R.id.text_opacity_low_tv);
        this.mTextOpacityMediumTv = (TextView) view.findViewById(R.id.text_opacity_medium_tv);
        this.mTextOpacityHighTv = (TextView) view.findViewById(R.id.text_opacity_high_tv);
        this.mTextOpacitySolidTv = (TextView) view.findViewById(R.id.text_opacity_solid_tv);
        this.mTextStyleView = view.findViewById(R.id.text_style_view);
        this.mTextStyleTitleTv = (TextView) view.findViewById(R.id.text_style_title_tv);
        this.mTextStyleContentTv = (TextView) view.findViewById(R.id.text_style_content_tv);
        this.mTextStyleLy = (LinearLayout) view.findViewById(R.id.text_style_ly);
        this.mTextStyleOneTv = (TextView) view.findViewById(R.id.text_style_one_tv);
        this.mTextStyleTwoTv = (TextView) view.findViewById(R.id.text_style_two_tv);
        this.mTextStyleThreeTv = (TextView) view.findViewById(R.id.text_style_three_tv);
        this.mTextSizeView = view.findViewById(R.id.text_size_view);
        this.mTextSizeTitleTv = (TextView) view.findViewById(R.id.text_size_title_tv);
        this.mTextSizeContentTv = (TextView) view.findViewById(R.id.text_size_content_tv);
        this.mTextSizeLy = (LinearLayout) view.findViewById(R.id.text_size_ly);
        this.mTextSizeSmallTv = (TextView) view.findViewById(R.id.text_size_small_tv);
        this.mTextSizeMediumTv = (TextView) view.findViewById(R.id.text_size_medium_tv);
        this.mTextSizeLargeTv = (TextView) view.findViewById(R.id.text_size_large_tv);
        this.mEdgeTypeView = view.findViewById(R.id.edge_type_view);
        this.mEdgeTypeTitleTv = (TextView) view.findViewById(R.id.edge_type_title_tv);
        this.mEdgeTypeContentTv = (TextView) view.findViewById(R.id.edge_type_content_tv);
        this.mEdgeTypeLy = (LinearLayout) view.findViewById(R.id.edge_type_ly);
        this.mEdgeTypeNoneTv = (TextView) view.findViewById(R.id.edge_type_none_tv);
        this.mEdgeTypeOutlineTv = (TextView) view.findViewById(R.id.edge_type_outline_tv);
        this.mEdgeTypeDropShadowTv = (TextView) view.findViewById(R.id.edge_type_drop_shadow_tv);
        this.mEdgeColorView = view.findViewById(R.id.edge_color_view);
        this.mEdgeColorTitleTv = (TextView) view.findViewById(R.id.edge_color_title_tv);
        this.mEdgeColorContentTv = (TextView) view.findViewById(R.id.edge_color_content_tv);
        this.mEdgeColorLy = (LinearLayout) view.findViewById(R.id.edge_color_ly);
        this.mEdgeColorWhiteTv = (TextView) view.findViewById(R.id.edge_color_white_tv);
        this.mEdgeColorBlackTv = (TextView) view.findViewById(R.id.edge_color_black_tv);
        this.mEdgeColorYellowTv = (TextView) view.findViewById(R.id.edge_color_yellow_tv);
        this.mEdgeColorGreenTv = (TextView) view.findViewById(R.id.edge_color_green_tv);
        this.mEdgeColorRedTv = (TextView) view.findViewById(R.id.edge_color_red_tv);
        this.mEdgeColorBlueTv = (TextView) view.findViewById(R.id.edge_color_blue_tv);
        this.mBackgroundColorView = view.findViewById(R.id.background_color_view);
        this.mBackgroundColorTitleTv = (TextView) view.findViewById(R.id.background_color_title_tv);
        this.mBackgroundColorContentTv = (TextView) view.findViewById(R.id.background_color_content_tv);
        this.mBackgroundColorLy = (LinearLayout) view.findViewById(R.id.background_color_ly);
        this.mBackgroundColorWhiteTv = (TextView) view.findViewById(R.id.background_color_white_tv);
        this.mBackgroundColorBlackTv = (TextView) view.findViewById(R.id.background_color_black_tv);
        this.mBackgroundColorYellowTv = (TextView) view.findViewById(R.id.background_color_yellow_tv);
        this.mBackgroundColorGreenTv = (TextView) view.findViewById(R.id.background_color_green_tv);
        this.mBackgroundColorRedTv = (TextView) view.findViewById(R.id.background_color_red_tv);
        this.mBackgroundColorBlueTv = (TextView) view.findViewById(R.id.background_color_blue_tv);
        this.mBackgroundOpacityView = view.findViewById(R.id.background_opacity_view);
        this.mBackgroundOpacityTitleTv = (TextView) view.findViewById(R.id.background_opacity_title_tv);
        this.mBackgroundOpacityContentTv = (TextView) view.findViewById(R.id.background_opacity_content_tv);
        this.mBackgroundOpacityLy = (LinearLayout) view.findViewById(R.id.background_opacity_ly);
        this.mBackgroundOpacityNoneTv = (TextView) view.findViewById(R.id.background_opacity_none_tv);
        this.mBackgroundOpacityLowTv = (TextView) view.findViewById(R.id.background_opacity_low_tv);
        this.mBackgroundOpacityMediumTv = (TextView) view.findViewById(R.id.background_opacity_medium_tv);
        this.mBackgroundOpacityHighTv = (TextView) view.findViewById(R.id.background_opacity_high_tv);
        this.mBackgroundOpacitySolidTv = (TextView) view.findViewById(R.id.background_opacity_solid_tv);
        this.mApplyTv = (TextView) view.findViewById(R.id.apply_tv);
        this.mDefaultTv = (TextView) view.findViewById(R.id.default_tv);
        this.mShowTv = (XumoTextView) view.findViewById(R.id.show_tv);
    }

    private void hideView() {
        this.mTextColorView.setVisibility(8);
        this.mTextColorContentTv.setVisibility(0);
        this.mTextColorLy.setVisibility(8);
        this.mTextOpacityView.setVisibility(8);
        this.mTextOpacityContentTv.setVisibility(0);
        this.mTextOpacityLy.setVisibility(8);
        this.mTextStyleView.setVisibility(8);
        this.mTextStyleContentTv.setVisibility(0);
        this.mTextStyleLy.setVisibility(8);
        this.mTextSizeView.setVisibility(8);
        this.mTextSizeContentTv.setVisibility(0);
        this.mTextSizeLy.setVisibility(8);
        this.mEdgeTypeView.setVisibility(8);
        this.mEdgeTypeContentTv.setVisibility(0);
        this.mEdgeTypeLy.setVisibility(8);
        this.mEdgeColorView.setVisibility(8);
        this.mEdgeColorContentTv.setVisibility(0);
        this.mEdgeColorLy.setVisibility(8);
        this.mBackgroundColorView.setVisibility(8);
        this.mBackgroundColorContentTv.setVisibility(0);
        this.mBackgroundColorLy.setVisibility(8);
        this.mBackgroundOpacityView.setVisibility(8);
        this.mBackgroundOpacityContentTv.setVisibility(0);
        this.mBackgroundOpacityLy.setVisibility(8);
    }

    private void setItemDefaultBackgroundColor() {
        this.mTextColorWhiteTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        this.mTextColorBlackTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        this.mTextColorYellowTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        this.mTextColorGreenTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        this.mTextColorRedTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        this.mTextColorBlueTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        switch (UserPreferences.getInstance().getTextColor()) {
            case 1:
                r0.mTextColorContentTv.setText("WHITE");
                r0.mTextColorWhiteTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setTextColor(-1);
                break;
            case 2:
                r0.mTextColorContentTv.setText("BLACK");
                r0.mTextColorBlackTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                break;
            case 3:
                r0.mTextColorContentTv.setText("YELLOW");
                r0.mTextColorYellowTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setTextColor(InputDeviceCompat.SOURCE_ANY);
                break;
            case 4:
                r0.mTextColorContentTv.setText("GREEN");
                r0.mTextColorGreenTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setTextColor(-16711936);
                break;
            case 5:
                r0.mTextColorContentTv.setText("RED");
                r0.mTextColorRedTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setTextColor(SupportMenu.CATEGORY_MASK);
                break;
            case 6:
                r0.mTextColorContentTv.setText("BLUE");
                r0.mTextColorBlueTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setTextColor(-16776961);
                break;
            default:
                break;
        }
        r0.mTextOpacityLowTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mTextOpacityMediumTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mTextOpacityHighTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mTextOpacitySolidTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        switch (UserPreferences.getInstance().getTextOpacity()) {
            case 1:
                r0.mTextOpacityContentTv.setText("LOW");
                r0.mTextOpacityLowTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getTextColor()) {
                    case 1:
                        r0.mShowTv.setTextColor(Color.argb(102, 255, 255, 255));
                        break;
                    case 2:
                        r0.mShowTv.setTextColor(Color.argb(102, 0, 0, 0));
                        break;
                    case 3:
                        r0.mShowTv.setTextColor(Color.argb(102, 255, 255, 0));
                        break;
                    case 4:
                        r0.mShowTv.setTextColor(Color.argb(102, 0, 255, 0));
                        break;
                    case 5:
                        r0.mShowTv.setTextColor(Color.argb(102, 255, 0, 0));
                        break;
                    case 6:
                        r0.mShowTv.setTextColor(Color.argb(102, 0, 0, 255));
                        break;
                    default:
                        break;
                }
            case 2:
                r0.mTextOpacityContentTv.setText("MEDIUM");
                r0.mTextOpacityMediumTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getTextColor()) {
                    case 1:
                        r0.mShowTv.setTextColor(Color.argb(153, 255, 255, 255));
                        break;
                    case 2:
                        r0.mShowTv.setTextColor(Color.argb(153, 0, 0, 0));
                        break;
                    case 3:
                        r0.mShowTv.setTextColor(Color.argb(153, 255, 255, 0));
                        break;
                    case 4:
                        r0.mShowTv.setTextColor(Color.argb(153, 0, 255, 0));
                        break;
                    case 5:
                        r0.mShowTv.setTextColor(Color.argb(153, 255, 0, 0));
                        break;
                    case 6:
                        r0.mShowTv.setTextColor(Color.argb(153, 0, 0, 255));
                        break;
                    default:
                        break;
                }
            case 3:
                r0.mTextOpacityContentTv.setText("HIGH");
                r0.mTextOpacityHighTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getTextColor()) {
                    case 1:
                        r0.mShowTv.setTextColor(Color.argb(204, 255, 255, 255));
                        break;
                    case 2:
                        r0.mShowTv.setTextColor(Color.argb(204, 0, 0, 0));
                        break;
                    case 3:
                        r0.mShowTv.setTextColor(Color.argb(204, 255, 255, 0));
                        break;
                    case 4:
                        r0.mShowTv.setTextColor(Color.argb(204, 0, 255, 0));
                        break;
                    case 5:
                        r0.mShowTv.setTextColor(Color.argb(204, 255, 0, 0));
                        break;
                    case 6:
                        r0.mShowTv.setTextColor(Color.argb(204, 0, 0, 255));
                        break;
                    default:
                        break;
                }
            case 4:
                r0.mTextOpacityContentTv.setText("SOLID");
                r0.mTextOpacitySolidTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getTextColor()) {
                    case 1:
                        r0.mShowTv.setTextColor(Color.argb(255, 255, 255, 255));
                        break;
                    case 2:
                        r0.mShowTv.setTextColor(Color.argb(255, 0, 0, 0));
                        break;
                    case 3:
                        r0.mShowTv.setTextColor(Color.argb(255, 255, 255, 0));
                        break;
                    case 4:
                        r0.mShowTv.setTextColor(Color.argb(255, 0, 255, 0));
                        break;
                    case 5:
                        r0.mShowTv.setTextColor(Color.argb(255, 255, 0, 0));
                        break;
                    case 6:
                        r0.mShowTv.setTextColor(Color.argb(255, 0, 0, 255));
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
        r0.mTextStyleOneTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mTextStyleTwoTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mTextStyleThreeTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        switch (UserPreferences.getInstance().getTextStyle()) {
            case 1:
                r0.mTextStyleContentTv.setText("ONE");
                r0.mTextStyleOneTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Regular.ttf"));
                break;
            case 2:
                r0.mTextStyleContentTv.setText("TWO");
                r0.mTextStyleTwoTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Regular.ttf"));
                break;
            case 3:
                r0.mTextStyleContentTv.setText("THREE");
                r0.mTextStyleThreeTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Bold.ttf"));
                break;
            default:
                break;
        }
        r0.mTextSizeSmallTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mTextSizeMediumTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mTextSizeLargeTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        switch (UserPreferences.getInstance().getTextSize()) {
            case 1:
                r0.mTextSizeContentTv.setText("SMALL");
                r0.mTextSizeSmallTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setTextSize(20.0f);
                break;
            case 2:
                r0.mTextSizeContentTv.setText("MEDIUM");
                r0.mTextSizeMediumTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setTextSize(24.0f);
                break;
            case 3:
                r0.mTextSizeContentTv.setText("LARGE");
                r0.mTextSizeLargeTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setTextSize(28.0f);
                break;
            default:
                break;
        }
        r0.mEdgeTypeNoneTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mEdgeTypeOutlineTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mEdgeTypeDropShadowTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        switch (UserPreferences.getInstance().getEdgeType()) {
            case 1:
                r0.mEdgeTypeContentTv.setText("NONE");
                r0.mEdgeTypeNoneTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getEdgeColor()) {
                    case 1:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, -1);
                        break;
                    case 2:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
                        break;
                    case 3:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, InputDeviceCompat.SOURCE_ANY);
                        break;
                    case 4:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, -16711936);
                        break;
                    case 5:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, SupportMenu.CATEGORY_MASK);
                        break;
                    case 6:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, -16776961);
                        break;
                    default:
                        break;
                }
            case 2:
                r0.mEdgeTypeContentTv.setText("OUTLINE");
                r0.mEdgeTypeOutlineTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getEdgeColor()) {
                    case 1:
                        r0.mShowTv.setColor(true, -1);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, -1);
                        break;
                    case 2:
                        r0.mShowTv.setColor(true, ViewCompat.MEASURED_STATE_MASK);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
                        break;
                    case 3:
                        r0.mShowTv.setColor(true, InputDeviceCompat.SOURCE_ANY);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, InputDeviceCompat.SOURCE_ANY);
                        break;
                    case 4:
                        r0.mShowTv.setColor(true, -16711936);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, -16711936);
                        break;
                    case 5:
                        r0.mShowTv.setColor(true, SupportMenu.CATEGORY_MASK);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, SupportMenu.CATEGORY_MASK);
                        break;
                    case 6:
                        r0.mShowTv.setColor(true, -16776961);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, -16776961);
                        break;
                    default:
                        break;
                }
            case 3:
                r0.mEdgeTypeContentTv.setText("DROP SHADOW");
                r0.mEdgeTypeDropShadowTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getEdgeColor()) {
                    case 1:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(1.0f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, -1);
                        break;
                    case 2:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(1.0f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ViewCompat.MEASURED_STATE_MASK);
                        break;
                    case 3:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(1.0f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, InputDeviceCompat.SOURCE_ANY);
                        break;
                    case 4:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(1.0f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, -16711936);
                        break;
                    case 5:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(1.0f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, SupportMenu.CATEGORY_MASK);
                        break;
                    case 6:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(1.0f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, -16776961);
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
        r0.mEdgeColorWhiteTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mEdgeColorBlackTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mEdgeColorYellowTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mEdgeColorGreenTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mEdgeColorRedTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mEdgeColorBlueTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        switch (UserPreferences.getInstance().getEdgeColor()) {
            case 1:
                r0.mEdgeColorContentTv.setText("WHITE");
                r0.mEdgeColorWhiteTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getEdgeType()) {
                    case 1:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, -1);
                        break;
                    case 2:
                        r0.mShowTv.setColor(true, -1);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, -1);
                        break;
                    case 3:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(1.0f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, -1);
                        break;
                    default:
                        break;
                }
            case 2:
                r0.mEdgeColorContentTv.setText("BLACK");
                r0.mEdgeColorBlackTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getEdgeType()) {
                    case 1:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
                        break;
                    case 2:
                        r0.mShowTv.setColor(true, ViewCompat.MEASURED_STATE_MASK);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
                        break;
                    case 3:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(1.0f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ViewCompat.MEASURED_STATE_MASK);
                        break;
                    default:
                        break;
                }
            case 3:
                r0.mEdgeColorContentTv.setText("YELLOW");
                r0.mEdgeColorYellowTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getEdgeType()) {
                    case 1:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, InputDeviceCompat.SOURCE_ANY);
                        break;
                    case 2:
                        r0.mShowTv.setColor(true, InputDeviceCompat.SOURCE_ANY);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, InputDeviceCompat.SOURCE_ANY);
                        break;
                    case 3:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(1.0f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, InputDeviceCompat.SOURCE_ANY);
                        break;
                    default:
                        break;
                }
            case 4:
                r0.mEdgeColorContentTv.setText("GREEN");
                r0.mEdgeColorGreenTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getEdgeType()) {
                    case 1:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, -16711936);
                        break;
                    case 2:
                        r0.mShowTv.setColor(true, -16711936);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, -16711936);
                        break;
                    case 3:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(1.0f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, -16711936);
                        break;
                    default:
                        break;
                }
            case 5:
                r0.mEdgeColorContentTv.setText("RED");
                r0.mEdgeColorRedTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getEdgeType()) {
                    case 1:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, SupportMenu.CATEGORY_MASK);
                        break;
                    case 2:
                        r0.mShowTv.setColor(true, SupportMenu.CATEGORY_MASK);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, SupportMenu.CATEGORY_MASK);
                        break;
                    case 3:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(1.0f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, SupportMenu.CATEGORY_MASK);
                        break;
                    default:
                        break;
                }
            case 6:
                r0.mEdgeColorContentTv.setText("BLUE");
                r0.mEdgeColorBlueTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getEdgeType()) {
                    case 1:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, -16776961);
                        break;
                    case 2:
                        r0.mShowTv.setColor(true, -16776961);
                        r0.mShowTv.setShadowLayer(0.0f, 0.0f, 0.0f, -16776961);
                        break;
                    case 3:
                        r0.mShowTv.setColor(false, 0);
                        r0.mShowTv.setShadowLayer(1.0f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT, -16776961);
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
        r0.mBackgroundColorWhiteTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mBackgroundColorBlackTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mBackgroundColorYellowTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mBackgroundColorGreenTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mBackgroundColorRedTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mBackgroundColorBlueTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        switch (UserPreferences.getInstance().getBackgroundColor()) {
            case 1:
                r0.mBackgroundColorContentTv.setText("WHITE");
                r0.mBackgroundColorWhiteTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setBackgroundColor(-1);
                break;
            case 2:
                r0.mBackgroundColorContentTv.setText("BLACK");
                r0.mBackgroundColorBlackTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                break;
            case 3:
                r0.mBackgroundColorContentTv.setText("YELLOW");
                r0.mBackgroundColorYellowTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setBackgroundColor(InputDeviceCompat.SOURCE_ANY);
                break;
            case 4:
                r0.mBackgroundColorContentTv.setText("GREEN");
                r0.mBackgroundColorGreenTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setBackgroundColor(-16711936);
                break;
            case 5:
                r0.mBackgroundColorContentTv.setText("RED");
                r0.mBackgroundColorRedTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setBackgroundColor(SupportMenu.CATEGORY_MASK);
                break;
            case 6:
                r0.mBackgroundColorContentTv.setText("BLUE");
                r0.mBackgroundColorBlueTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setBackgroundColor(-16776961);
                break;
            default:
                break;
        }
        r0.mBackgroundOpacityNoneTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mBackgroundOpacityLowTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mBackgroundOpacityMediumTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mBackgroundOpacityHighTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        r0.mBackgroundOpacitySolidTv.setBackgroundResource(R.drawable.shape_gray_for_setting);
        switch (UserPreferences.getInstance().getBackgroundOpacity()) {
            case 1:
                r0.mBackgroundOpacityContentTv.setText("NONE");
                r0.mBackgroundOpacityNoneTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                r0.mShowTv.setBackgroundColor(Color.argb(0, 255, 255, 255));
                break;
            case 2:
                r0.mBackgroundOpacityContentTv.setText("LOW");
                r0.mBackgroundOpacityLowTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getBackgroundColor()) {
                    case 1:
                        r0.mShowTv.setBackgroundColor(Color.argb(50, 255, 255, 255));
                        break;
                    case 2:
                        r0.mShowTv.setBackgroundColor(Color.argb(50, 0, 0, 0));
                        break;
                    case 3:
                        r0.mShowTv.setBackgroundColor(Color.argb(50, 255, 255, 0));
                        break;
                    case 4:
                        r0.mShowTv.setBackgroundColor(Color.argb(50, 0, 255, 0));
                        break;
                    case 5:
                        r0.mShowTv.setBackgroundColor(Color.argb(50, 255, 0, 0));
                        break;
                    case 6:
                        r0.mShowTv.setBackgroundColor(Color.argb(50, 0, 0, 255));
                        break;
                    default:
                        break;
                }
            case 3:
                r0.mBackgroundOpacityContentTv.setText("MEDIUM");
                r0.mBackgroundOpacityMediumTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getBackgroundColor()) {
                    case 1:
                        r0.mShowTv.setBackgroundColor(Color.argb(100, 255, 255, 255));
                        break;
                    case 2:
                        r0.mShowTv.setBackgroundColor(Color.argb(100, 0, 0, 0));
                        break;
                    case 3:
                        r0.mShowTv.setBackgroundColor(Color.argb(100, 255, 255, 0));
                        break;
                    case 4:
                        r0.mShowTv.setBackgroundColor(Color.argb(100, 0, 255, 0));
                        break;
                    case 5:
                        r0.mShowTv.setBackgroundColor(Color.argb(100, 255, 0, 0));
                        break;
                    case 6:
                        r0.mShowTv.setBackgroundColor(Color.argb(100, 0, 0, 255));
                        break;
                    default:
                        break;
                }
            case 4:
                r0.mBackgroundOpacityContentTv.setText("HIGH");
                r0.mBackgroundOpacityHighTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getBackgroundColor()) {
                    case 1:
                        r0.mShowTv.setBackgroundColor(Color.argb(150, 255, 255, 255));
                        break;
                    case 2:
                        r0.mShowTv.setBackgroundColor(Color.argb(150, 0, 0, 0));
                        break;
                    case 3:
                        r0.mShowTv.setBackgroundColor(Color.argb(150, 255, 255, 0));
                        break;
                    case 4:
                        r0.mShowTv.setBackgroundColor(Color.argb(150, 0, 255, 0));
                        break;
                    case 5:
                        r0.mShowTv.setBackgroundColor(Color.argb(150, 255, 0, 0));
                        break;
                    case 6:
                        r0.mShowTv.setBackgroundColor(Color.argb(150, 0, 0, 255));
                        break;
                    default:
                        break;
                }
            case 5:
                r0.mBackgroundOpacityContentTv.setText("SOLID");
                r0.mBackgroundOpacitySolidTv.setBackgroundResource(R.drawable.shape_select_blue_for_setting);
                switch (UserPreferences.getInstance().getBackgroundColor()) {
                    case 1:
                        r0.mShowTv.setBackgroundColor(Color.argb(255, 255, 255, 255));
                        break;
                    case 2:
                        r0.mShowTv.setBackgroundColor(Color.argb(255, 0, 0, 0));
                        break;
                    case 3:
                        r0.mShowTv.setBackgroundColor(Color.argb(255, 255, 255, 0));
                        break;
                    case 4:
                        r0.mShowTv.setBackgroundColor(Color.argb(255, 0, 255, 0));
                        break;
                    case 5:
                        r0.mShowTv.setBackgroundColor(Color.argb(255, 255, 0, 0));
                        break;
                    case 6:
                        r0.mShowTv.setBackgroundColor(Color.argb(255, 0, 0, 255));
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
        r0.mApplyTv.setBackgroundResource(R.drawable.shape_white_for_setting);
        r0.mDefaultTv.setBackgroundResource(R.drawable.shape_white_for_setting);
    }

    private void initTimer() {
        if (mPageListener != null) {
            mPageListener.getSettingsCaptioningPageState();
        }
        if (!(this.mTimer == null || this.mTimerTask == null)) {
            this.mTimer.cancel();
            this.mTimerTask.cancel();
        }
        this.finish = 1;
        this.mTimer = new Timer();
        this.mTimerTask = new TimerTask() {
            public void run() {
                SettingsCaptioningFragment.this.handler.sendEmptyMessage(1);
            }
        };
        this.mTimer.schedule(this.mTimerTask, 0, NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void onKeyDown(int r1, android.view.KeyEvent r2) {
        /*
        r0 = this;
        r0.initTimer();
        r2 = 4;
        if (r1 == r2) goto L_0x0019;
    L_0x0006:
        switch(r1) {
            case 19: goto L_0x0015;
            case 20: goto L_0x0015;
            case 21: goto L_0x0011;
            case 22: goto L_0x0011;
            case 23: goto L_0x000d;
            default: goto L_0x0009;
        };
    L_0x0009:
        switch(r1) {
            case 96: goto L_0x000d;
            case 97: goto L_0x0019;
            default: goto L_0x000c;
        };
    L_0x000c:
        goto L_0x0066;
    L_0x000d:
        r0.dealCenterKey(r1);
        goto L_0x0066;
    L_0x0011:
        r0.dealLeftOrRightKey(r1);
        goto L_0x0066;
    L_0x0015:
        r0.dealUpOrDownKey(r1);
        goto L_0x0066;
    L_0x0019:
        r1 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r2 = r0.mInTextColorIndex;
        r1.setTextColor(r2);
        r1 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r2 = r0.mInTextOpacityIndex;
        r1.setTextOpacity(r2);
        r1 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r2 = r0.mInTextStyleIndex;
        r1.setTextStyle(r2);
        r1 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r2 = r0.mInTextSizeIndex;
        r1.setTextSize(r2);
        r1 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r2 = r0.mInEdgeTypeIndex;
        r1.setEdgeType(r2);
        r1 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r2 = r0.mInEdgeColorIndex;
        r1.setEdgeColor(r2);
        r1 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r2 = r0.mInBackgroundColorIndex;
        r1.setBackgroundColor(r2);
        r1 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r2 = r0.mInBackgroundOpacityIndex;
        r1.setBackgroundOpacity(r2);
        r1 = mPageListener;
        r0.finish(r1);
    L_0x0066:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.fragmenttv.SettingsCaptioningFragment.onKeyDown(int, android.view.KeyEvent):void");
    }

    private void dealUpOrDownKey(int i) {
        hideView();
        setItemDefaultBackgroundColor();
        if (i == 19) {
            if (this.mTableSelectIndex > 1) {
                this.mTableSelectIndex--;
            }
        } else if (i == 20 && this.mTableSelectIndex < 9) {
            this.mTableSelectIndex++;
        }
        switch (this.mTableSelectIndex) {
            case 1:
                this.mTextColorView.setVisibility(0);
                this.mTextColorContentTv.setVisibility(8);
                this.mTextColorLy.setVisibility(0);
                this.mTextColorIndex = 1;
                this.mTextColorWhiteTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                return;
            case 2:
                this.mTextOpacityView.setVisibility(0);
                this.mTextOpacityContentTv.setVisibility(8);
                this.mTextOpacityLy.setVisibility(0);
                this.mTextOpacityIndex = 1;
                this.mTextOpacityLowTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                return;
            case 3:
                this.mTextStyleView.setVisibility(0);
                this.mTextStyleContentTv.setVisibility(8);
                this.mTextStyleLy.setVisibility(0);
                this.mTextStyleIndex = 1;
                this.mTextStyleOneTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                return;
            case 4:
                this.mTextSizeView.setVisibility(0);
                this.mTextSizeContentTv.setVisibility(8);
                this.mTextSizeLy.setVisibility(0);
                this.mTextSizeIndex = 1;
                this.mTextSizeSmallTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                return;
            case 5:
                this.mEdgeTypeView.setVisibility(0);
                this.mEdgeTypeContentTv.setVisibility(8);
                this.mEdgeTypeLy.setVisibility(0);
                this.mEdgeTypeIndex = 1;
                this.mEdgeTypeNoneTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                return;
            case 6:
                this.mEdgeColorView.setVisibility(0);
                this.mEdgeColorContentTv.setVisibility(8);
                this.mEdgeColorLy.setVisibility(0);
                this.mEdgeColorIndex = 1;
                this.mEdgeColorWhiteTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                return;
            case 7:
                this.mBackgroundColorView.setVisibility(0);
                this.mBackgroundColorContentTv.setVisibility(8);
                this.mBackgroundColorLy.setVisibility(0);
                this.mBackgroundColorIndex = 1;
                this.mBackgroundColorWhiteTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                return;
            case 8:
                this.mBackgroundOpacityView.setVisibility(0);
                this.mBackgroundOpacityContentTv.setVisibility(8);
                this.mBackgroundOpacityLy.setVisibility(0);
                this.mBackgroundOpacityIndex = 1;
                this.mBackgroundOpacityNoneTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                return;
            case 9:
                this.mControlIndex = 1;
                this.mApplyTv.setBackgroundResource(R.drawable.shape_blue_for_setting);
                return;
            default:
                return;
        }
    }

    private void dealLeftOrRightKey(int i) {
        setItemDefaultBackgroundColor();
        switch (this.mTableSelectIndex) {
            case 1:
                if (i == 21) {
                    if (this.mTextColorIndex > 1) {
                        this.mTextColorIndex--;
                    }
                } else if (i == 22 && this.mTextColorIndex < 6) {
                    this.mTextColorIndex++;
                }
                switch (this.mTextColorIndex) {
                    case 1:
                        this.mTextColorWhiteTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 2:
                        this.mTextColorBlackTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 3:
                        this.mTextColorYellowTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 4:
                        this.mTextColorGreenTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 5:
                        this.mTextColorRedTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 6:
                        this.mTextColorBlueTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    default:
                        return;
                }
            case 2:
                if (i == 21) {
                    if (this.mTextOpacityIndex > 1) {
                        this.mTextOpacityIndex--;
                    }
                } else if (i == 22 && this.mTextOpacityIndex < 4) {
                    this.mTextOpacityIndex++;
                }
                switch (this.mTextOpacityIndex) {
                    case 1:
                        this.mTextOpacityLowTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 2:
                        this.mTextOpacityMediumTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 3:
                        this.mTextOpacityHighTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 4:
                        this.mTextOpacitySolidTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    default:
                        return;
                }
            case 3:
                if (i == 21) {
                    if (this.mTextStyleIndex > 1) {
                        this.mTextStyleIndex--;
                    }
                } else if (i == 22 && this.mTextStyleIndex < 3) {
                    this.mTextStyleIndex++;
                }
                switch (this.mTextStyleIndex) {
                    case 1:
                        this.mTextStyleOneTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 2:
                        this.mTextStyleTwoTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 3:
                        this.mTextStyleThreeTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    default:
                        return;
                }
            case 4:
                if (i == 21) {
                    if (this.mTextSizeIndex > 1) {
                        this.mTextSizeIndex--;
                    }
                } else if (i == 22 && this.mTextSizeIndex < 3) {
                    this.mTextSizeIndex++;
                }
                switch (this.mTextSizeIndex) {
                    case 1:
                        this.mTextSizeSmallTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 2:
                        this.mTextSizeMediumTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 3:
                        this.mTextSizeLargeTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    default:
                        return;
                }
            case 5:
                if (i == 21) {
                    if (this.mEdgeTypeIndex > 1) {
                        this.mEdgeTypeIndex--;
                    }
                } else if (i == 22 && this.mEdgeTypeIndex < 3) {
                    this.mEdgeTypeIndex++;
                }
                switch (this.mEdgeTypeIndex) {
                    case 1:
                        this.mEdgeTypeNoneTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 2:
                        this.mEdgeTypeOutlineTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 3:
                        this.mEdgeTypeDropShadowTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    default:
                        return;
                }
            case 6:
                if (i == 21) {
                    if (this.mEdgeColorIndex > 1) {
                        this.mEdgeColorIndex--;
                    }
                } else if (i == 22 && this.mEdgeColorIndex < 6) {
                    this.mEdgeColorIndex++;
                }
                switch (this.mEdgeColorIndex) {
                    case 1:
                        this.mEdgeColorWhiteTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 2:
                        this.mEdgeColorBlackTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 3:
                        this.mEdgeColorYellowTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 4:
                        this.mEdgeColorGreenTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 5:
                        this.mEdgeColorRedTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 6:
                        this.mEdgeColorBlueTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    default:
                        return;
                }
            case 7:
                if (i == 21) {
                    if (this.mBackgroundColorIndex > 1) {
                        this.mBackgroundColorIndex--;
                    }
                } else if (i == 22 && this.mBackgroundColorIndex < 6) {
                    this.mBackgroundColorIndex++;
                }
                switch (this.mBackgroundColorIndex) {
                    case 1:
                        this.mBackgroundColorWhiteTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 2:
                        this.mBackgroundColorBlackTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 3:
                        this.mBackgroundColorYellowTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 4:
                        this.mBackgroundColorGreenTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 5:
                        this.mBackgroundColorRedTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 6:
                        this.mBackgroundColorBlueTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    default:
                        return;
                }
            case 8:
                if (i == 21) {
                    if (this.mBackgroundOpacityIndex > 1) {
                        this.mBackgroundOpacityIndex--;
                    }
                } else if (i == 22 && this.mBackgroundOpacityIndex < 5) {
                    this.mBackgroundOpacityIndex++;
                }
                switch (this.mBackgroundOpacityIndex) {
                    case 1:
                        this.mBackgroundOpacityNoneTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 2:
                        this.mBackgroundOpacityLowTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 3:
                        this.mBackgroundOpacityMediumTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 4:
                        this.mBackgroundOpacityHighTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    case 5:
                        this.mBackgroundOpacitySolidTv.setBackgroundResource(R.drawable.shape_move_blue_for_setting);
                        return;
                    default:
                        return;
                }
            case 9:
                if (i == 21) {
                    if (this.mControlIndex > 1) {
                        this.mControlIndex--;
                    }
                } else if (i == 22 && this.mControlIndex < 2) {
                    this.mControlIndex++;
                }
                switch (this.mControlIndex) {
                    case 1:
                        this.mApplyTv.setBackgroundResource(R.drawable.shape_blue_for_setting);
                        return;
                    case 2:
                        this.mDefaultTv.setBackgroundResource(R.drawable.shape_blue_for_setting);
                        return;
                    default:
                        return;
                }
            default:
                return;
        }
    }

    private void dealCenterKey(int i) {
        switch (this.mTableSelectIndex) {
            case 1:
                UserPreferences.getInstance().setTextColor(this.mTextColorIndex);
                break;
            case 2:
                UserPreferences.getInstance().setTextOpacity(this.mTextOpacityIndex);
                break;
            case 3:
                UserPreferences.getInstance().setTextStyle(this.mTextStyleIndex);
                break;
            case 4:
                UserPreferences.getInstance().setTextSize(this.mTextSizeIndex);
                break;
            case 5:
                UserPreferences.getInstance().setEdgeType(this.mEdgeTypeIndex);
                break;
            case 6:
                UserPreferences.getInstance().setEdgeColor(this.mEdgeColorIndex);
                break;
            case 7:
                UserPreferences.getInstance().setBackgroundColor(this.mBackgroundColorIndex);
                break;
            case 8:
                UserPreferences.getInstance().setBackgroundOpacity(this.mBackgroundOpacityIndex);
                break;
            case 9:
                switch (this.mControlIndex) {
                    case 1:
                        if (mPageListener != null) {
                            mPageListener.changeExoPlayerSubtitle();
                        }
                        finish(null);
                        break;
                    case 2:
                        UserPreferences.getInstance().removeCaptionsAppearance();
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
        setItemDefaultBackgroundColor();
        dealLeftOrRightKey(i);
    }

    private void finish(PageListener pageListener) {
        if (pageListener != null) {
            pageListener.onPressKeyBackInSettingsCaptioningPage();
        }
        pageListener = getFragmentManager();
        Fragment findFragmentByTag = pageListener.findFragmentByTag(TAG_SETTINGS_CAPTIONING);
        pageListener = pageListener.beginTransaction();
        pageListener.remove(findFragmentByTag);
        pageListener.commitAllowingStateLoss();
    }
}
