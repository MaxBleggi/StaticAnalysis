package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.ImaSdkSettings;

/* compiled from: IMASDK */
public final class ja implements ImaSdkSettings {
    private boolean autoPlayAdBreaks = true;
    private boolean debugMode = false;
    private boolean enableOmidExperimentally = false;
    private transient String language = "en";
    private int numRedirects = 4;
    private final boolean onScreenDetection = true;
    private String playerType;
    private String playerVersion;
    private String ppid;
    private transient boolean restrictToCustomPlayer;

    public String getPpid() {
        return this.ppid;
    }

    public void setPpid(String str) {
        this.ppid = str;
    }

    public int getMaxRedirects() {
        return this.numRedirects;
    }

    public void setMaxRedirects(int i) {
        this.numRedirects = i;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public boolean doesRestrictToCustomPlayer() {
        return this.restrictToCustomPlayer;
    }

    public void setRestrictToCustomPlayer(boolean z) {
        this.restrictToCustomPlayer = z;
    }

    public String getPlayerType() {
        return this.playerType;
    }

    public void setPlayerType(String str) {
        this.playerType = str;
    }

    public String getPlayerVersion() {
        return this.playerVersion;
    }

    public void setPlayerVersion(String str) {
        this.playerVersion = str;
    }

    public boolean getAutoPlayAdBreaks() {
        return this.autoPlayAdBreaks;
    }

    public void setAutoPlayAdBreaks(boolean z) {
        this.autoPlayAdBreaks = z;
    }

    public boolean getEnableOmidExperimentally() {
        return this.enableOmidExperimentally;
    }

    public void setEnableOmidExperimentally(boolean z) {
        this.enableOmidExperimentally = z;
    }

    public void setDebugMode(boolean z) {
        this.debugMode = z;
    }

    public boolean isDebugMode() {
        return this.debugMode;
    }

    public String toString() {
        String str = this.ppid;
        int i = this.numRedirects;
        String str2 = this.playerType;
        String str3 = this.playerVersion;
        String str4 = this.language;
        boolean z = this.restrictToCustomPlayer;
        boolean z2 = this.autoPlayAdBreaks;
        StringBuilder stringBuilder = new StringBuilder((((String.valueOf(str).length() + 160) + String.valueOf(str2).length()) + String.valueOf(str3).length()) + String.valueOf(str4).length());
        stringBuilder.append("ImaSdkSettings [ppid=");
        stringBuilder.append(str);
        stringBuilder.append(", numRedirects=");
        stringBuilder.append(i);
        stringBuilder.append(", playerType=");
        stringBuilder.append(str2);
        stringBuilder.append(", playerVersion=");
        stringBuilder.append(str3);
        stringBuilder.append(", onScreenDetection=");
        stringBuilder.append(true);
        stringBuilder.append(", language=");
        stringBuilder.append(str4);
        stringBuilder.append(", restrictToCustom=");
        stringBuilder.append(z);
        stringBuilder.append(", autoPlayAdBreaks=");
        stringBuilder.append(z2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
