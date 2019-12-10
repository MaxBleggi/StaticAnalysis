package com.xumo.xumo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.xumo.xumo.player.XumoExoPlayer;
import com.xumo.xumo.util.BeaconUtil;
import com.xumo.xumo.util.BeaconUtil.ImpressionBeaconEvent;
import com.xumo.xumo.util.XumoUtil;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Session implements Parcelable {
    private static final String BACKGROUND_INFIX = "-BACK-";
    public static final Creator<Session> CREATOR = new Creator<Session>() {
        public Session createFromParcel(Parcel parcel) {
            return new Session(parcel);
        }

        public Session[] newArray(int i) {
            return new Session[i];
        }
    };
    private static final String FOREGROUND_INFIX = "-FORE-";
    public String launchId;
    public String playId;
    public String sessionId;
    private String sessionIdSuffix;
    public Date timeCreated;

    public int describeContents() {
        return 0;
    }

    public Session() {
        this.playId = XumoUtil.generateRandomId();
        this.timeCreated = new Date();
        init();
    }

    public Session(Parcel parcel) {
        this.playId = parcel.readString();
        this.timeCreated = (Date) parcel.readSerializable();
        init();
    }

    private void init() {
        this.launchId = XumoUtil.generateRandomShortId();
        generateNewSessionId();
        initSession();
    }

    public void foregroundSession() {
        generateNewSessionId();
        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppForeGrounded, null, null);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.launchId);
        stringBuilder.append(FOREGROUND_INFIX);
        stringBuilder.append(this.sessionIdSuffix);
        this.sessionId = stringBuilder.toString();
    }

    public void backgroundSession() {
        XumoExoPlayer.mPlaySessionId = null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.launchId);
        stringBuilder.append(BACKGROUND_INFIX);
        stringBuilder.append(this.sessionIdSuffix);
        this.sessionId = stringBuilder.toString();
        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppBackGrounded, null, null);
    }

    private void initSession() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.launchId);
        stringBuilder.append(FOREGROUND_INFIX);
        stringBuilder.append(this.sessionIdSuffix);
        this.sessionId = stringBuilder.toString();
    }

    private void generateNewSessionId() {
        this.sessionIdSuffix = XumoUtil.generateRandomShortId();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sessionId);
        parcel.writeString(this.playId);
        parcel.writeSerializable(this.timeCreated);
    }

    public boolean isSessionValid() {
        if (TextUtils.isEmpty(this.sessionId)) {
            return false;
        }
        if (this.timeCreated != null) {
            if (TimeUnit.MICROSECONDS.toSeconds(new Date().getTime() - this.timeCreated.getTime()) > 1800) {
                return false;
            }
        }
        return true;
    }

    public String refreshPlayId() {
        this.playId = XumoUtil.generateRandomId();
        return this.playId;
    }
}
