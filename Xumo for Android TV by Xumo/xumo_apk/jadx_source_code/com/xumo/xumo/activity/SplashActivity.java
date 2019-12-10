package com.xumo.xumo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import com.xumo.xumo.application.XumoApplication;
import com.xumo.xumo.model.DeepLinkKey;
import com.xumo.xumo.model.DeviceIdList;
import com.xumo.xumo.repository.UserPreferences;
import com.xumo.xumo.service.XumoWebService;
import com.xumo.xumo.service.XumoWebService.DeviceSettingsListener;
import com.xumo.xumo.tv.R;
import com.xumo.xumo.util.BeaconUtil;
import com.xumo.xumo.util.BeaconUtil.ImpressionBeaconEvent;
import com.xumo.xumo.util.LogUtil;
import com.xumo.xumo.util.XumoUtil;

public class SplashActivity extends AppCompatActivity {
    private ImageView geoBlockMessageImageView;
    private AsyncTask mAsyncTask = null;
    private ImageView splashImageView;

    public void onBackPressed() {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (((XumoApplication) getApplication()).getmAppStatus() == 2 && UserPreferences.error == null) {
            finish();
            UserPreferences.error = false;
            return;
        }
        UserPreferences.error = false;
        supportRequestWindowFeature(1);
        setContentView((int) R.layout.activity_splash);
        bundle = getResources().getBoolean(R.bool.is_tablet);
        this.splashImageView = (ImageView) findViewById(R.id.splash);
        if (bundle != null) {
            this.splashImageView.setImageResource(R.drawable.splash_tablet);
        } else {
            this.splashImageView.setImageResource(R.drawable.splash);
        }
        this.geoBlockMessageImageView = (ImageView) findViewById(R.id.geo_block_message);
        this.geoBlockMessageImageView.setVisibility(8);
    }

    protected void onPause() {
        super.onPause();
        if (this.mAsyncTask != null && this.mAsyncTask.getStatus() == Status.RUNNING) {
            this.mAsyncTask.cancel(true);
        }
    }

    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashActivity.this.mAsyncTask = new AsyncTask<Void, Void, Integer>() {
                    protected Integer doInBackground(Void... voidArr) {
                        return Integer.valueOf(XumoUtil.checkGeoBlock());
                    }

                    protected void onPostExecute(Integer num) {
                        super.onPostExecute(num);
                        if (Callback.DEFAULT_DRAG_ANIMATION_DURATION <= num.intValue() && num.intValue() < 300) {
                            SplashActivity.this.getDeviceSettings();
                        } else if (num.intValue() == 403) {
                            SplashActivity.this.splashImageView.setVisibility(8);
                            SplashActivity.this.geoBlockMessageImageView.setVisibility(0);
                        } else {
                            BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{String.valueOf(num), XumoUtil.urlGeo});
                            SplashActivity.this.getDeviceSettings();
                        }
                    }
                }.execute(new Void[0]);
            }
        }, 1000);
    }

    private void getDeviceSettings() {
        LogUtil.d(" - Method start.");
        if (XumoUtil.isTempDeviceId(UserPreferences.getInstance().getDeviceId())) {
            LogUtil.d("getDeviceSettings - Romove Temp Device Id.");
            UserPreferences.getInstance().removeDeviceId();
        }
        this.mAsyncTask = new AsyncTask<Void, Void, Void>() {
            boolean processing = false;
            boolean result = false;

            protected Void doInBackground(Void... voidArr) {
                this.processing = 1;
                XumoWebService.getInstance().getDeviceSettings(new DeviceSettingsListener() {
                    public void onCompletion(String str, DeviceIdList deviceIdList) {
                        AnonymousClass2.this.result = true;
                        AnonymousClass2.this.processing = null;
                    }

                    public void onError() {
                        AnonymousClass2.this.result = false;
                        AnonymousClass2.this.processing = false;
                    }
                });
                while (this.processing != null) {
                    try {
                        LogUtil.d("getDeviceSettings.doInBackground - processing");
                        Thread.sleep(50);
                    } catch (Void[] voidArr2) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("getDeviceSettings.doInBackground: ");
                        stringBuilder.append(voidArr2.getStackTrace());
                        LogUtil.e(stringBuilder.toString());
                    }
                }
                return null;
            }

            protected void onPostExecute(Void voidR) {
                voidR = new StringBuilder();
                voidR.append("getDeviceSettings.onPostExecute: result = ");
                voidR.append(this.result);
                LogUtil.d(voidR.toString());
                if (this.result != null) {
                    SplashActivity.this.startMainActivity();
                }
            }
        }.execute(new Void[0]);
    }

    private void startMainActivity() {
        Intent intent = getIntent();
        Intent intent2 = new Intent(getApplicationContext(), MainTvActivity.class);
        intent2.setFlags(335544320);
        if (intent != null) {
            if (intent.getExtras() != null) {
                intent2.setAction("android.intent.action.VIEW");
                for (String str : intent.getExtras().keySet()) {
                    intent2.putExtra(str, intent.getStringExtra(str));
                }
            } else {
                String action = intent.getAction();
                if (action != null && action.equals("android.intent.action.VIEW")) {
                    Uri data = intent.getData();
                    if (data != null) {
                        action = data.getQueryParameter("channelId");
                        String queryParameter = data.getQueryParameter(DeepLinkKey.TRACKING_ID);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("channelId=");
                        stringBuilder.append(action);
                        stringBuilder.append(", trackingId=");
                        stringBuilder.append(queryParameter);
                        LogUtil.d("DeepLink", stringBuilder.toString());
                        intent2.putExtra("channelId", action);
                        intent2.putExtra(DeepLinkKey.TRACKING_ID, queryParameter);
                    }
                }
            }
        }
        startActivity(intent2);
        finish();
    }
}
