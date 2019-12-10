package com.xumo.xumo.model;

import com.xumo.xumo.service.XumoWebService;
import com.xumo.xumo.service.XumoWebService.Listener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class XumoDataSync {
    private static XumoDataSync sInstance;
    private ArrayList<Channel> mAllChannels = new ArrayList();
    private ArrayList<Genre> mAllGenres = new ArrayList();
    private PlayerProvider mPlayerProvider;
    private ArrayList<Provider> mProviders = new ArrayList();

    public static synchronized XumoDataSync getInstance() {
        XumoDataSync xumoDataSync;
        synchronized (XumoDataSync.class) {
            if (sInstance == null) {
                sInstance = new XumoDataSync();
            }
            xumoDataSync = sInstance;
        }
        return xumoDataSync;
    }

    private XumoDataSync() {
    }

    public void initializeChannels(final Listener listener) {
        XumoWebService.getInstance().getAllChannels(new Listener() {
            public void onCompletion(Object obj) {
                XumoDataSync.this.mAllChannels = (ArrayList) obj;
                listener.onCompletion(Boolean.valueOf(true));
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    public void getAllChannels(final Listener listener) {
        if (this.mAllChannels.size() > 1) {
            listener.onCompletion(this.mAllChannels);
        } else {
            initializeChannels(new Listener() {
                public void onCompletion(Object obj) {
                    if (((Boolean) obj).booleanValue() != null) {
                        listener.onCompletion(XumoDataSync.this.mAllChannels);
                    } else {
                        listener.onError();
                    }
                }

                public void onError() {
                    listener.onError();
                }
            });
        }
    }

    public void clearAllChannels() {
        this.mAllChannels.clear();
    }

    public void getAllOnNow(final Listener listener) {
        XumoOnNowScheduler.getInstance().getAllOnNow(new Listener() {
            public void onCompletion(Object obj) {
                listener.onCompletion(obj);
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    public void getOnNow(String str, final Listener listener) {
        XumoOnNowScheduler.getInstance().getOnNow(str, new Listener() {
            public void onCompletion(Object obj) {
                listener.onCompletion(obj);
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    public void getGenre(final Listener listener) {
        getAllChannels(new Listener() {
            public void onCompletion(Object obj) {
                if (XumoDataSync.this.mAllGenres.size() > 1) {
                    listener.onCompletion(XumoDataSync.this.mAllGenres);
                } else {
                    XumoWebService.getInstance().getGenres(new Listener() {
                        public void onCompletion(Object obj) {
                            ArrayList arrayList = (ArrayList) obj;
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                Genre genre = (Genre) it.next();
                                ArrayList arrayList2 = new ArrayList();
                                Iterator it2 = XumoDataSync.this.mAllChannels.iterator();
                                while (it2.hasNext()) {
                                    Channel channel = (Channel) it2.next();
                                    if (genre.getValue().equals(channel.getGenreName())) {
                                        arrayList2.add(channel.getChannelId());
                                    }
                                }
                                genre.setChannelIdList(arrayList2);
                            }
                            XumoDataSync.this.mAllGenres = arrayList;
                            listener.onCompletion(XumoDataSync.this.mAllGenres);
                        }

                        public void onError() {
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

    public void getPlayerProvider(final Listener listener) {
        if (this.mPlayerProvider != null) {
            listener.onCompletion(this.mPlayerProvider);
        } else {
            initializeProviders(new Listener() {
                public void onCompletion(Object obj) {
                    listener.onCompletion(XumoDataSync.this.mPlayerProvider);
                }

                public void onError() {
                    listener.onError();
                }
            });
        }
    }

    public void initializeProviders(final Listener listener) {
        XumoWebService.getInstance().getProviders(new Listener() {
            public void onCompletion(Object obj) {
                XumoDataSync.this.mPlayerProvider = (PlayerProvider) obj;
                XumoDataSync.this.mProviders = XumoDataSync.this.mPlayerProvider.getProviders();
                listener.onCompletion(Boolean.valueOf(true));
            }

            public void onError() {
                listener.onCompletion(Boolean.valueOf(false));
            }
        });
    }

    public void getProvider(final int i, final Listener listener) {
        if (this.mProviders.size() > 1) {
            Iterator it = this.mProviders.iterator();
            while (it.hasNext()) {
                Provider provider = (Provider) it.next();
                if (provider.getId() == i) {
                    listener.onCompletion(provider);
                    return;
                }
            }
            listener.onError();
            return;
        }
        initializeProviders(new Listener() {
            public void onError() {
            }

            public void onCompletion(Object obj) {
                if (((Boolean) obj).booleanValue() != null) {
                    obj = XumoDataSync.this.mProviders.iterator();
                    while (obj.hasNext()) {
                        Provider provider = (Provider) obj.next();
                        if (provider.getId() == i) {
                            listener.onCompletion(provider);
                            return;
                        }
                    }
                }
                listener.onError();
            }
        });
    }

    public void getChannelInfoForChannelId(final String str, final Listener listener) {
        getAllChannels(new Listener() {
            public void onCompletion(Object obj) {
                Channel channel;
                obj = XumoDataSync.this.mAllChannels.iterator();
                while (obj.hasNext()) {
                    channel = (Channel) obj.next();
                    if (channel.getChannelId().equals(str)) {
                        break;
                    }
                }
                channel = null;
                if (channel == null) {
                    listener.onError();
                } else if (channel.isHasVod() == null) {
                    channel.setCategories(new ArrayList());
                    listener.onCompletion(channel);
                } else {
                    if (channel.getCategories() == null) {
                        XumoWebService.getInstance().getChannelCategories(str, new Listener() {
                            public void onCompletion(Object obj) {
                                channel.setCategories((ArrayList) obj);
                                XumoDataSync.this.getAssetsInfoForChanel(channel, 0, new Listener() {
                                    public void onError() {
                                    }

                                    public void onCompletion(Object obj) {
                                        if (((Boolean) obj).booleanValue() != null) {
                                            listener.onCompletion(channel);
                                        } else {
                                            listener.onError();
                                        }
                                    }
                                });
                            }

                            public void onError() {
                                listener.onError();
                            }
                        });
                    } else {
                        XumoDataSync.this.getAssetsInfoForChanel(channel, 0, new Listener() {
                            public void onError() {
                            }

                            public void onCompletion(Object obj) {
                                listener.onCompletion(channel);
                            }
                        });
                    }
                }
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    public void getNextLiveAsset(String str, final Listener listener) {
        XumoLiveScheduler.getInstance().getNextAsset(str, new Listener() {
            public void onCompletion(Object obj) {
                listener.onCompletion(obj);
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    public void getLiveAsset(Date date, String str, final Listener listener) {
        XumoLiveScheduler.getInstance().getAsset(date, str, new Listener() {
            public void onCompletion(Object obj) {
                listener.onCompletion(obj);
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    public void searchVideos(String[] strArr, final Listener listener) {
        XumoWebService.getInstance().searchAssets(strArr, new Listener() {
            public void onCompletion(Object obj) {
                listener.onCompletion(obj);
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    public Channel getChannel(String str) {
        Iterator it = this.mAllChannels.iterator();
        while (it.hasNext()) {
            Channel channel = (Channel) it.next();
            if (channel.getChannelId().equals(str)) {
                return channel;
            }
        }
        return null;
    }

    private void getAssetsInfoForChanel(Channel channel, int i, Listener listener) {
        if (channel != null) {
            if (channel.getCategories() != null) {
                Category category = (Category) channel.getCategories().get(i);
                final Category category2 = category;
                final int i2 = i;
                final Channel channel2 = channel;
                final Listener listener2 = listener;
                XumoWebService.getInstance().getAssetsInCategories(category, new Listener() {
                    public void onCompletion(Object obj) {
                        category2.setVideoAssets((ArrayList) obj);
                        obj = i2 + 1;
                        if (obj < channel2.getCategories().size()) {
                            XumoDataSync.this.getAssetsInfoForChanel(channel2, obj, listener2);
                            return;
                        }
                        obj = new ArrayList();
                        Iterator it = channel2.getCategories().iterator();
                        while (it.hasNext()) {
                            Category category = (Category) it.next();
                            if (category.getVideoAssets().size() > 0) {
                                obj.add(category);
                            }
                        }
                        channel2.setCategories(obj);
                        listener2.onCompletion(Boolean.valueOf(true));
                    }

                    public void onError() {
                        listener2.onCompletion(Boolean.valueOf(false));
                    }
                });
                return;
            }
        }
        listener.onCompletion(Boolean.valueOf(null));
    }
}
