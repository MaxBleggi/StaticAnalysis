package com.amazon.device.ads.aftv;

public interface AmazonFireTVAdCallback {
    void onFailure(AmazonFireTVAdResponse amazonFireTVAdResponse);

    void onSuccess(AmazonFireTVAdResponse amazonFireTVAdResponse);
}
