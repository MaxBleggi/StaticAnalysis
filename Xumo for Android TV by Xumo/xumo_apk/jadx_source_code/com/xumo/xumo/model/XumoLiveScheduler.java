package com.xumo.xumo.model;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.xumo.xumo.service.XumoWebService;
import com.xumo.xumo.service.XumoWebService.GetLiveContentListener;
import com.xumo.xumo.service.XumoWebService.Listener;
import com.xumo.xumo.service.XumoWebService.NoResponseListener;
import com.xumo.xumo.util.LogUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

public class XumoLiveScheduler {
    private static final int UPDATE_LIVE_ASSETS = 1;
    private static XumoLiveScheduler sInstance;
    private String mCurrentChannelId = "";
    private int mIndex = 0;
    private ArrayList<LiveAsset> mLiveAssets;
    private Handler mUpdateLiveAssetsHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (message.what == 1) {
                XumoLiveScheduler.this.onUpdate();
            }
        }
    };

    public static synchronized XumoLiveScheduler getInstance() {
        XumoLiveScheduler xumoLiveScheduler;
        synchronized (XumoLiveScheduler.class) {
            if (sInstance == null) {
                sInstance = new XumoLiveScheduler();
            }
            xumoLiveScheduler = sInstance;
        }
        return xumoLiveScheduler;
    }

    private XumoLiveScheduler() {
    }

    public void getNextAsset(String str, final Listener listener) {
        if (this.mCurrentChannelId.equals(str)) {
            this.mIndex++;
            getLiveAssetMetadata(new Listener() {
                public void onCompletion(Object obj) {
                    listener.onCompletion(obj);
                }

                public void onError() {
                    listener.onError();
                }
            });
            return;
        }
        this.mCurrentChannelId = str;
        if (this.mLiveAssets != null) {
            this.mLiveAssets.clear();
            this.mLiveAssets = null;
        }
        this.mIndex = 0;
        updateLiveAssets(str, new NoResponseListener() {
            public void onCompletion() {
                XumoLiveScheduler.this.getLiveAssetMetadata(new Listener() {
                    public void onCompletion(Object obj) {
                        listener.onCompletion(obj);
                    }

                    public void onError() {
                        listener.onError();
                    }
                });
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    public void getAsset(Date date, String str, final Listener listener) {
        if (this.mCurrentChannelId.equals(str) && this.mLiveAssets != null) {
            long time = date.getTime() / 1000;
            Iterator it = this.mLiveAssets.iterator();
            while (it.hasNext()) {
                final LiveAsset liveAsset = (LiveAsset) it.next();
                if (liveAsset.getStart() < time && liveAsset.getEnd() > time) {
                    liveAsset.getAssetMetaData(new NoResponseListener() {
                        public void onError() {
                        }

                        public void onCompletion() {
                            listener.onCompletion(liveAsset);
                        }
                    });
                    return;
                }
            }
        }
        this.mCurrentChannelId = str;
        if (this.mLiveAssets != null) {
            this.mLiveAssets.clear();
            this.mLiveAssets = null;
        }
        this.mIndex = 0;
        XumoWebService.getInstance().getLiveVideosForChannelId(str, date, new GetLiveContentListener() {
            public void onCompletion(ArrayList<LiveAsset> arrayList, long j) {
                XumoLiveScheduler.this.setupPromoted(arrayList);
                XumoLiveScheduler.this.mIndex = 0;
                XumoLiveScheduler.this.setUpdateLiveAssetsHandler(j * 1000);
                if (arrayList != null && arrayList.size() > null) {
                    final LiveAsset liveAsset = (LiveAsset) arrayList.get(0);
                    liveAsset.getAssetMetaData(new NoResponseListener() {
                        public void onCompletion() {
                            listener.onCompletion(liveAsset);
                        }

                        public void onError() {
                            LogUtil.w("No Assets.");
                            listener.onError();
                        }
                    });
                }
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    public void stopScheduler() {
        if (this.mUpdateLiveAssetsHandler != null) {
            this.mUpdateLiveAssetsHandler.removeMessages(1);
            this.mCurrentChannelId = "";
            if (this.mLiveAssets != null) {
                this.mLiveAssets.clear();
                this.mLiveAssets = null;
            }
            this.mIndex = 0;
        }
    }

    private void setUpdateLiveAssetsHandler(long j) {
        this.mUpdateLiveAssetsHandler.removeMessages(1);
        Date date = new Date(j);
        j = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        j.setTime(date);
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        instance.setTime(new Date());
        long time = j.getTime().getTime() - instance.getTime().getTime();
        if (time <= 0) {
            time = NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS;
        }
        j = new StringBuilder();
        j.append("setUpdateLiveAssetsHandler interval=");
        j.append(time);
        LogUtil.d(j.toString());
        this.mUpdateLiveAssetsHandler.sendEmptyMessageDelayed(1, time);
    }

    private void setupPromoted(ArrayList<LiveAsset> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        while (i < arrayList.size()) {
            LiveAsset liveAsset = (LiveAsset) arrayList.get(i);
            arrayList2.add(liveAsset);
            if (i == 0 || i % 4 == 0) {
                LiveAsset liveAsset2 = new LiveAsset(liveAsset.getAssetId(), liveAsset.getChannelId());
                liveAsset2.setAssetType(3);
                arrayList2.add(liveAsset2);
            }
            i++;
        }
        this.mLiveAssets = arrayList2;
    }

    private void updateLiveAssets(String str, final NoResponseListener noResponseListener) {
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        instance.setTime(new Date());
        XumoWebService.getInstance().getLiveVideosForChannelId(str, instance.getTime(), new GetLiveContentListener() {
            public void onCompletion(ArrayList<LiveAsset> arrayList, long j) {
                XumoLiveScheduler.this.setupPromoted(arrayList);
                XumoLiveScheduler.this.mIndex = 0;
                XumoLiveScheduler.this.setUpdateLiveAssetsHandler(j * 1000);
                noResponseListener.onCompletion();
            }

            public void onError() {
                XumoLiveScheduler.this.setUpdateLiveAssetsHandler(0);
                noResponseListener.onError();
            }
        });
    }

    private void getLiveAssetMetadata(final Listener listener) {
        if (this.mLiveAssets == null) {
            LogUtil.w("No Asset");
            listener.onError();
        } else if (this.mIndex >= this.mLiveAssets.size()) {
            LogUtil.w("Played until the last asset.");
            listener.onError();
        } else {
            final LiveAsset liveAsset = (LiveAsset) this.mLiveAssets.get(this.mIndex);
            if (liveAsset.getAssetType() == 3) {
                listener.onCompletion(liveAsset);
                return;
            }
            liveAsset.getAssetMetaData(new NoResponseListener() {
                public void onCompletion() {
                    listener.onCompletion(liveAsset);
                }

                public void onError() {
                    listener.onError();
                }
            });
        }
    }

    private void onUpdate() {
        if (this.mLiveAssets != null && this.mLiveAssets.size() > 0) {
            updateLiveAssets(((LiveAsset) this.mLiveAssets.get(0)).getChannelId(), new NoResponseListener() {
                public void onCompletion() {
                    LogUtil.v("Updated live assets.");
                }

                public void onError() {
                    LogUtil.w("Updated live assets error.");
                }
            });
        }
    }
}
