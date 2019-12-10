package com.xumo.xumo.model;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.xumo.xumo.service.XumoWebService;
import com.xumo.xumo.service.XumoWebService.Listener;
import com.xumo.xumo.util.LogUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class XumoOnNowScheduler {
    private static final int UPDATE_INTERVAL = 6000;
    private static final int UPDATE_ON_NOW = 1;
    private static final int UPDATE_RETRY_COUNT = 3;
    private static XumoOnNowScheduler sInstance;
    private ArrayList<OnNowLive> mOnNowLives;
    private int mRetryCount = 0;
    private Handler mUpdateOnNowHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (message.what == 1) {
                XumoOnNowScheduler.this.onUpdate();
            }
        }
    };

    public static synchronized XumoOnNowScheduler getInstance() {
        XumoOnNowScheduler xumoOnNowScheduler;
        synchronized (XumoOnNowScheduler.class) {
            if (sInstance == null) {
                sInstance = new XumoOnNowScheduler();
            }
            xumoOnNowScheduler = sInstance;
        }
        return xumoOnNowScheduler;
    }

    private XumoOnNowScheduler() {
    }

    public void getAllOnNow(final Listener listener) {
        if (this.mOnNowLives != null) {
            listener.onCompletion(this.mOnNowLives);
        } else {
            initializeOnNowLives(new Listener() {
                public void onCompletion(Object obj) {
                    listener.onCompletion(obj);
                }

                public void onError() {
                    listener.onError();
                }
            });
        }
    }

    public void getOnNow(final String str, final Listener listener) {
        if (str != null) {
            if (!str.isEmpty()) {
                if (this.mOnNowLives != null) {
                    Iterator it = this.mOnNowLives.iterator();
                    while (it.hasNext()) {
                        OnNowLive onNowLive = (OnNowLive) it.next();
                        if (str.equals(onNowLive.getChannelId())) {
                            listener.onCompletion(onNowLive);
                            return;
                        }
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("not found onnow channelId=");
                    stringBuilder.append(str);
                    LogUtil.d(stringBuilder.toString());
                    listener.onError();
                    return;
                }
                initializeOnNowLives(new Listener() {
                    public void onCompletion(Object obj) {
                        obj = ((ArrayList) obj).iterator();
                        while (obj.hasNext()) {
                            OnNowLive onNowLive = (OnNowLive) obj.next();
                            if (str.equals(onNowLive.getChannelId())) {
                                listener.onCompletion(onNowLive);
                                return;
                            }
                        }
                        listener.onError();
                    }

                    public void onError() {
                        listener.onError();
                    }
                });
                return;
            }
        }
        listener.onError();
    }

    public void stopScheduler() {
        if (this.mUpdateOnNowHandler != null) {
            this.mUpdateOnNowHandler.removeMessages(1);
            if (this.mOnNowLives != null) {
                this.mOnNowLives.clear();
                this.mOnNowLives = null;
            }
        }
    }

    private void onUpdate() {
        updateOnNowLives(new Listener() {
            public void onCompletion(Object obj) {
                XumoOnNowScheduler.this.mRetryCount = 0;
                XumoOnNowScheduler.this.mOnNowLives = (ArrayList) obj;
                XumoOnNowScheduler.this.updateSchedule();
            }

            public void onError() {
                if (XumoOnNowScheduler.this.mRetryCount < 3) {
                    XumoOnNowScheduler.this.mRetryCount = XumoOnNowScheduler.this.mRetryCount + 1;
                    XumoOnNowScheduler.this.onUpdate();
                    return;
                }
                XumoOnNowScheduler.this.mRetryCount = 0;
                XumoOnNowScheduler.this.updateSchedule();
            }
        });
    }

    private void initializeOnNowLives(final Listener listener) {
        this.mUpdateOnNowHandler.removeMessages(1);
        updateOnNowLives(new Listener() {
            public void onCompletion(Object obj) {
                XumoOnNowScheduler.this.mOnNowLives = (ArrayList) obj;
                XumoOnNowScheduler.this.updateSchedule();
                listener.onCompletion(XumoOnNowScheduler.this.mOnNowLives);
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    private void updateOnNowLives(final Listener listener) {
        XumoDataSync.getInstance().getAllChannels(new Listener() {
            public void onCompletion(Object obj) {
                final ArrayList arrayList = (ArrayList) obj;
                XumoWebService.getInstance().getOnNowLives(new Listener() {
                    public void onCompletion(Object obj) {
                        ArrayList arrayList = (ArrayList) obj;
                        List arrayList2 = new ArrayList();
                        Iterator it = arrayList.iterator();
                        int i = 0;
                        while (it.hasNext()) {
                            Channel channel = (Channel) it.next();
                            if (channel.isLive()) {
                                OnNowLive onNowLive = new OnNowLive();
                                onNowLive.setChannelId(channel.getChannelId());
                                onNowLive.setChannelNumber(channel.getChannelNumber());
                                onNowLive.setChannelGenreName(channel.getGenreName());
                                onNowLive.setChannelTitle(channel.getChannelTitle());
                                onNowLive.setChannelDescription(channel.getDescriptionText());
                                onNowLive.setNew(channel.isNew());
                                onNowLive.setHasVod(channel.isHasVod());
                                if (i < 12) {
                                    onNowLive.setChannelIndex(i);
                                    onNowLive.setIsPopular(true);
                                } else {
                                    onNowLive.setIsPopular(false);
                                }
                                Iterator it2 = arrayList.iterator();
                                while (it2.hasNext()) {
                                    OnNowLive onNowLive2 = (OnNowLive) it2.next();
                                    if (channel.getChannelId().equals(onNowLive2.getChannelId())) {
                                        onNowLive.setId(onNowLive2.getId());
                                        onNowLive.setTitle(onNowLive2.getTitle());
                                        onNowLive.setStart(onNowLive2.getStart());
                                        onNowLive.setEnd(onNowLive2.getEnd());
                                        onNowLive.setDescriptionText(onNowLive2.getDescriptionText());
                                        onNowLive.setNextId(onNowLive2.getNextId());
                                        onNowLive.setNextTitle(onNowLive2.getNextTitle());
                                        onNowLive.setNextDescriptionText(onNowLive2.getNextDescriptionText());
                                        onNowLive.setNextStart(onNowLive2.getNextStart());
                                        onNowLive.setNextEnd(onNowLive2.getNextEnd());
                                        break;
                                    }
                                }
                                onNowLive.setGenre(channel.getGenreName());
                                onNowLive.setGenreId(channel.getGenreId());
                                arrayList2.add(onNowLive);
                            }
                            i++;
                        }
                        Collections.sort(arrayList2, new Comparator<OnNowLive>() {
                            public int compare(OnNowLive onNowLive, OnNowLive onNowLive2) {
                                if (onNowLive != null) {
                                    if (onNowLive2 != null) {
                                        return onNowLive.getChannelNumber() - onNowLive2.getChannelNumber();
                                    }
                                }
                                return null;
                            }
                        });
                        listener.onCompletion(arrayList2);
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

    private void updateSchedule() {
        this.mUpdateOnNowHandler.removeMessages(1);
        this.mUpdateOnNowHandler.sendEmptyMessageDelayed(1, 6000);
    }
}
