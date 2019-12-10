package com.amazon.device.ads.aftv;

public class AmazonFireTVAdsKeyValuePair {
    private String key;
    private String value;

    public AmazonFireTVAdsKeyValuePair(String str, String str2) {
        this.key = str;
        this.value = str2;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{key:");
        stringBuilder.append(this.key);
        stringBuilder.append(",value:");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
