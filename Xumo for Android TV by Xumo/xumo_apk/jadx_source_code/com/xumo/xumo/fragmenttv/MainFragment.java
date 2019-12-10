package com.xumo.xumo.fragmenttv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import com.xumo.xumo.activity.MainTvActivity;
import com.xumo.xumo.activity.MainTvActivity.PushNotificationListener;
import com.xumo.xumo.tv.R;
import com.xumo.xumo.widget.XumoViewPager;

public class MainFragment extends BaseFragment implements PushNotificationListener {
    public static final String TAG_MAIN = "com.xumo.xumo.fragmenttv.MainFragment";
    private OnNowPlayerFragment onNowPlayerFragment;

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        return layoutInflater.inflate(R.layout.tv_fragment_on_now, viewGroup, false);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if ((getActivity() instanceof MainTvActivity) != null) {
            this.onNowPlayerFragment = new OnNowPlayerFragment();
            XumoViewPager xumoViewPager = (XumoViewPager) view.findViewById(R.id.pager);
            xumoViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
                public int getCount() {
                    return 1;
                }

                public Fragment getItem(int i) {
                    return MainFragment.this.onNowPlayerFragment;
                }
            });
            xumoViewPager.setEnabled(null);
        }
    }

    public void onHandlePushNotificationDeepLink() {
        if (this.onNowPlayerFragment != null) {
            this.onNowPlayerFragment.onHandlePushNotificationDeepLink();
        }
    }
}
