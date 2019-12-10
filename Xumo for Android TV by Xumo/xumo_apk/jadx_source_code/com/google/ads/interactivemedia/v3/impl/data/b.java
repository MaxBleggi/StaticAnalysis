package com.google.ads.interactivemedia.v3.impl.data;

import com.google.ads.interactivemedia.v3.api.Ad;
import com.google.ads.interactivemedia.v3.api.AdPodInfo;
import com.google.ads.interactivemedia.v3.api.CompanionAd;
import com.google.ads.interactivemedia.v3.api.UiElement;
import com.google.ads.interactivemedia.v3.internal.mb;
import com.google.ads.interactivemedia.v3.internal.mc;
import com.google.ads.interactivemedia.v3.internal.md;
import com.google.ads.interactivemedia.v3.internal.me;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/* compiled from: IMASDK */
public class b implements Ad {
    private String adId;
    @mc
    @me
    private c adPodInfo = new c();
    private String adSystem;
    @mc
    @me
    private String[] adWrapperCreativeIds;
    @mc
    @me
    private String[] adWrapperIds;
    @mc
    @me
    private String[] adWrapperSystems;
    private String advertiserName;
    private String clickThroughUrl;
    @mc
    @me
    private List<Object> companions;
    private String contentType;
    private String creativeAdId;
    private String creativeId;
    private String dealId;
    private String description;
    private boolean disableUi;
    private double duration;
    private int height;
    private boolean isUiDisabled_ = false;
    private boolean linear;
    private double skipTimeOffset = -1.0d;
    private boolean skippable;
    private String surveyUrl;
    private String title;
    private String traffickingParameters;
    @mc
    @me
    private Set<UiElement> uiElements;
    private String universalAdIdRegistry;
    private String universalAdIdValue;
    private int width;

    public String getAdId() {
        return this.adId;
    }

    public void setAdId(String str) {
        this.adId = str;
    }

    public String getCreativeId() {
        return this.creativeId;
    }

    public void setCreativeId(String str) {
        this.creativeId = str;
    }

    public String getCreativeAdId() {
        return this.creativeAdId;
    }

    public void setCreativeAdId(String str) {
        this.creativeAdId = str;
    }

    public String getUniversalAdIdValue() {
        return this.universalAdIdValue;
    }

    public void setUniversalAdIdValue(String str) {
        this.universalAdIdValue = str;
    }

    public String getUniversalAdIdRegistry() {
        return this.universalAdIdRegistry;
    }

    public void setUniversalAdIdRegistry(String str) {
        this.universalAdIdRegistry = str;
    }

    public String getAdSystem() {
        return this.adSystem;
    }

    public void setAdSystem(String str) {
        this.adSystem = str;
    }

    public String[] getAdWrapperIds() {
        return this.adWrapperIds;
    }

    public void setAdWrapperIds(String[] strArr) {
        this.adWrapperIds = strArr;
    }

    public String[] getAdWrapperSystems() {
        return this.adWrapperSystems;
    }

    public void setAdWrapperSystems(String[] strArr) {
        this.adWrapperSystems = strArr;
    }

    public String[] getAdWrapperCreativeIds() {
        return this.adWrapperCreativeIds;
    }

    public void setAdWrapperCreativeIds(String[] strArr) {
        this.adWrapperCreativeIds = strArr;
    }

    public boolean isLinear() {
        return this.linear;
    }

    public void setLinear(boolean z) {
        this.linear = z;
    }

    public boolean isSkippable() {
        return this.skippable;
    }

    public void setSkippable(boolean z) {
        this.skippable = z;
    }

    public double getSkipTimeOffset() {
        return this.skipTimeOffset;
    }

    public void setSkipTimeOffset(double d) {
        this.skipTimeOffset = d;
    }

    public boolean isUiDisabled() {
        return this.isUiDisabled_;
    }

    public void setUiDisabled(boolean z) {
        this.isUiDisabled_ = z;
    }

    public boolean canDisableUi() {
        return this.disableUi;
    }

    public void setCanDisableUi(boolean z) {
        this.disableUi = z;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String str) {
        this.contentType = str;
    }

    public String getAdvertiserName() {
        return this.advertiserName;
    }

    public void setAdvertiserName(String str) {
        this.advertiserName = str;
    }

    public String getSurveyUrl() {
        return this.surveyUrl;
    }

    public void setSurveyUrl(String str) {
        this.surveyUrl = str;
    }

    public String getDealId() {
        return this.dealId;
    }

    public void setDealId(String str) {
        this.dealId = str;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public String getTraffickingParameters() {
        return this.traffickingParameters;
    }

    public void setTraffickingParameters(String str) {
        this.traffickingParameters = str;
    }

    public String getClickThruUrl() {
        return this.clickThroughUrl;
    }

    public void setClickThruUrl(String str) {
        this.clickThroughUrl = str;
    }

    public double getDuration() {
        return this.duration;
    }

    public void setDuration(double d) {
        this.duration = d;
    }

    public AdPodInfo getAdPodInfo() {
        return this.adPodInfo;
    }

    public void setAdPodInfo(c cVar) {
        this.adPodInfo = cVar;
    }

    public Set<UiElement> getUiElements() {
        return this.uiElements;
    }

    public void setUiElements(Set<UiElement> set) {
        this.uiElements = set;
    }

    public List<CompanionAd> getCompanionAds() {
        List arrayList = new ArrayList();
        for (CompanionAd add : this.companions) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public void setCompanionAds(List<Object> list) {
        this.companions = list;
    }

    public int hashCode() {
        return md.a(this, new String[0]);
    }

    public boolean equals(Object obj) {
        return mb.a((Object) this, obj, new String[0]);
    }

    public String toString() {
        String str = this.adId;
        String str2 = this.creativeId;
        String str3 = this.creativeAdId;
        String str4 = this.universalAdIdValue;
        String str5 = this.universalAdIdRegistry;
        String str6 = this.title;
        String str7 = this.description;
        String str8 = this.contentType;
        String arrays = Arrays.toString(this.adWrapperIds);
        String arrays2 = Arrays.toString(this.adWrapperSystems);
        String arrays3 = Arrays.toString(this.adWrapperCreativeIds);
        String str9 = this.adSystem;
        String str10 = this.advertiserName;
        String str11 = this.surveyUrl;
        String str12 = this.dealId;
        boolean z = this.linear;
        boolean z2 = this.skippable;
        int i = this.width;
        int i2 = this.height;
        String str13 = this.traffickingParameters;
        String str14 = str11;
        String str15 = this.clickThroughUrl;
        double d = this.duration;
        str11 = String.valueOf(this.adPodInfo);
        String valueOf = String.valueOf(this.uiElements);
        String str16 = str11;
        boolean z3 = this.disableUi;
        double d2 = this.skipTimeOffset;
        StringBuilder stringBuilder = new StringBuilder(((((((((((((((((((String.valueOf(str).length() + 455) + String.valueOf(str2).length()) + String.valueOf(str3).length()) + String.valueOf(str4).length()) + String.valueOf(str5).length()) + String.valueOf(str6).length()) + String.valueOf(str7).length()) + String.valueOf(str8).length()) + String.valueOf(arrays).length()) + String.valueOf(arrays2).length()) + String.valueOf(arrays3).length()) + String.valueOf(str9).length()) + String.valueOf(str10).length()) + String.valueOf(str14).length()) + String.valueOf(str12).length()) + String.valueOf(str13).length()) + String.valueOf(str15).length()) + String.valueOf(str16).length()) + String.valueOf(valueOf).length());
        stringBuilder.append("Ad [adId=");
        stringBuilder.append(str);
        stringBuilder.append(", creativeId=");
        stringBuilder.append(str2);
        stringBuilder.append(", creativeAdId=");
        stringBuilder.append(str3);
        stringBuilder.append(", universalAdIdValue=");
        stringBuilder.append(str4);
        stringBuilder.append(", universalAdIdRegistry=");
        stringBuilder.append(str5);
        stringBuilder.append(", title=");
        stringBuilder.append(str6);
        stringBuilder.append(", description=");
        stringBuilder.append(str7);
        stringBuilder.append(", contentType=");
        stringBuilder.append(str8);
        stringBuilder.append(", adWrapperIds=");
        stringBuilder.append(arrays);
        stringBuilder.append(", adWrapperSystems=");
        stringBuilder.append(arrays2);
        stringBuilder.append(", adWrapperCreativeIds=");
        stringBuilder.append(arrays3);
        stringBuilder.append(", adSystem=");
        stringBuilder.append(str9);
        stringBuilder.append(", advertiserName=");
        stringBuilder.append(str10);
        stringBuilder.append(", surveyUrl=");
        stringBuilder.append(str14);
        stringBuilder.append(", dealId=");
        stringBuilder.append(str12);
        stringBuilder.append(", linear=");
        stringBuilder.append(z);
        stringBuilder.append(", skippable=");
        stringBuilder.append(z2);
        stringBuilder.append(", width=");
        stringBuilder.append(i);
        stringBuilder.append(", height=");
        stringBuilder.append(i2);
        stringBuilder.append(", traffickingParameters=");
        stringBuilder.append(str13);
        stringBuilder.append(", clickThroughUrl=");
        stringBuilder.append(str15);
        stringBuilder.append(", duration=");
        stringBuilder.append(d);
        stringBuilder.append(", adPodInfo=");
        stringBuilder.append(str16);
        stringBuilder.append(", uiElements=");
        stringBuilder.append(valueOf);
        stringBuilder.append(", disableUi=");
        stringBuilder.append(z3);
        stringBuilder.append(", skipTimeOffset=");
        stringBuilder.append(d2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
