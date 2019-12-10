package com.amazon.device.ads.aftv;

import java.util.List;

public class AmazonFireTVAdResponse {
    private List<AmazonFireTVAdsKeyValuePair> adServerTargetingParams;
    private boolean containsBids;
    private String reasonString;

    public void setReasonString(String str) {
        this.reasonString = str;
    }

    public String getReasonString() {
        return this.reasonString;
    }

    public List<AmazonFireTVAdsKeyValuePair> getAdServerTargetingParams() {
        return this.adServerTargetingParams;
    }

    public void setAdServerTargetingParams(List<AmazonFireTVAdsKeyValuePair> list) {
        this.adServerTargetingParams = list;
    }

    public void setContainsBids(boolean z) {
        this.containsBids = z;
    }

    public boolean hasResults() {
        return this.containsBids;
    }
}
