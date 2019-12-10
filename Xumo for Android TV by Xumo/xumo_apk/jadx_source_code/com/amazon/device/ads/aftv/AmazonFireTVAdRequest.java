package com.amazon.device.ads.aftv;

import android.content.Context;
import android.os.Parcelable;
import java.util.ArrayList;

public class AmazonFireTVAdRequest {
    private AdBreakPattern adBreakPattern;
    private String appId;
    private AmazonFireTVAdCallback callback;
    private Context context;
    private String jsonString;
    private Boolean testFlag;
    private Long timeout;

    public static class AmazonFireTVAdRequestBuilder {
        AdBreakPattern adBreakPattern;
        String appId;
        AmazonFireTVAdCallback callback;
        Context context;
        String jsonString;
        Boolean testFlag;
        Long timeOut;

        public AmazonFireTVAdRequestBuilder withAdBreakPattern(AdBreakPattern adBreakPattern) {
            this.adBreakPattern = adBreakPattern;
            return this;
        }

        public AmazonFireTVAdRequestBuilder withAppID(String str) {
            this.appId = str;
            return this;
        }

        public AmazonFireTVAdRequestBuilder withJSONString(String str) {
            this.jsonString = str;
            return this;
        }

        public AmazonFireTVAdRequestBuilder withTimeOut(Long l) {
            this.timeOut = l;
            return this;
        }

        public AmazonFireTVAdRequestBuilder withCallback(AmazonFireTVAdCallback amazonFireTVAdCallback) {
            this.callback = amazonFireTVAdCallback;
            return this;
        }

        public AmazonFireTVAdRequestBuilder withContext(Context context) {
            this.context = context;
            return this;
        }

        public AmazonFireTVAdRequestBuilder withTestFlag(boolean z) {
            this.testFlag = Boolean.valueOf(z);
            return this;
        }

        public AmazonFireTVAdRequest build() {
            if (isValid(this)) {
                return new AmazonFireTVAdRequest();
            }
            throw new IllegalArgumentException("appId and slot list cannot be null/empty");
        }

        private static boolean isValid(AmazonFireTVAdRequestBuilder amazonFireTVAdRequestBuilder) {
            return (amazonFireTVAdRequestBuilder == null || amazonFireTVAdRequestBuilder.appId == null || amazonFireTVAdRequestBuilder.adBreakPattern == null || amazonFireTVAdRequestBuilder.callback == null) ? null : true;
        }
    }

    private AmazonFireTVAdRequest(AmazonFireTVAdRequestBuilder amazonFireTVAdRequestBuilder) {
        this.context = amazonFireTVAdRequestBuilder.context;
        this.callback = amazonFireTVAdRequestBuilder.callback;
        this.adBreakPattern = amazonFireTVAdRequestBuilder.adBreakPattern;
        this.appId = amazonFireTVAdRequestBuilder.appId;
        this.jsonString = amazonFireTVAdRequestBuilder.jsonString;
        this.timeout = amazonFireTVAdRequestBuilder.timeOut;
        this.testFlag = amazonFireTVAdRequestBuilder.testFlag;
    }

    public Context getContext() {
        return this.context;
    }

    public AmazonFireTVAdCallback getCallback() {
        return this.callback;
    }

    ArrayList<Parcelable> getSlots() {
        ArrayList<Parcelable> arrayList = new ArrayList();
        arrayList.add(this.adBreakPattern.getBundle());
        return arrayList;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getJsonString() {
        return this.jsonString;
    }

    public Long getTimeout() {
        return this.timeout;
    }

    public Boolean getTestFlag() {
        return this.testFlag;
    }

    public void executeRequest() {
        new KSOServiceBinder().getAds(this);
    }

    public static AmazonFireTVAdRequestBuilder builder() {
        return new AmazonFireTVAdRequestBuilder();
    }
}
