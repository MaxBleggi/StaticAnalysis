package com.amazon.device.ads.aftv;

import android.os.Bundle;

public class AdBreakPattern {
    public static final String PS_KEY = "ps";
    public static final String SLOT_KEY = "slot";
    private String adBreakPatternUUID;
    private String jsonString;

    public static class AdBreakPatternBuilder {
        String adBreakPatternUUID;
        String jsonString;

        public AdBreakPatternBuilder withId(String str) {
            this.adBreakPatternUUID = str;
            return this;
        }

        public AdBreakPatternBuilder withJsonString(String str) {
            this.jsonString = str;
            return this;
        }

        public AdBreakPattern build() {
            if (isValid(this)) {
                return new AdBreakPattern();
            }
            throw new RuntimeException("AdBreakPattern object not valid. AdBreakPattern must be set");
        }

        private static boolean isValid(AdBreakPatternBuilder adBreakPatternBuilder) {
            return (adBreakPatternBuilder.adBreakPatternUUID == null || adBreakPatternBuilder.adBreakPatternUUID.isEmpty() != null) ? null : true;
        }
    }

    private AdBreakPattern(AdBreakPatternBuilder adBreakPatternBuilder) {
        this.adBreakPatternUUID = adBreakPatternBuilder.adBreakPatternUUID;
        this.jsonString = adBreakPatternBuilder.jsonString;
    }

    public String getAdBreakPatternUUID() {
        return this.adBreakPatternUUID;
    }

    public String getJsonString() {
        return this.jsonString;
    }

    public Bundle getBundle() {
        Bundle newBundleInstance = BundleFactory.newBundleInstance();
        newBundleInstance.putString(SLOT_KEY, getAdBreakPatternUUID());
        newBundleInstance.putString(PS_KEY, getJsonString());
        return newBundleInstance;
    }

    public static AdBreakPatternBuilder builder() {
        return new AdBreakPatternBuilder();
    }
}
