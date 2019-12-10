package com.xumo.xumo.model;

import com.xumo.xumo.util.XumoUtil;
import java.util.Locale;

public class OnNowLive {
    private static final String ON_NOW_TIME_MIN_LEFT = "%d Min Left";
    private static final String UP_NEXT_TIME_START_MIN = "Starts in %s Min";
    private boolean hasVod = false;
    private String mChannelDescription = "";
    private String mChannelGenreName = "";
    private String mChannelId = "";
    private int mChannelIndex = -1;
    private int mChannelNumber = 0;
    private String mChannelTitle = "";
    private String mDescriptionText = "";
    private boolean mEmptyFavorite = false;
    private long mEnd = 0;
    private boolean mFirstItemOfGenre = false;
    private String mGenre = "";
    private int mGenreId = 0;
    private String mId = "";
    private boolean mIsCategoryHeader = false;
    private boolean mIsNew = false;
    private String mNextDescriptionText = "";
    private long mNextEnd = 0;
    private String mNextId = "";
    private long mNextStart = 0;
    private String mNextTitle = "";
    private boolean mPopular = false;
    private long mStart = 0;
    private String mTitle = "";

    public String getId() {
        return this.mId;
    }

    public void setId(String str) {
        this.mId = str;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public String getDescriptionText() {
        return this.mDescriptionText;
    }

    public void setDescriptionText(String str) {
        this.mDescriptionText = str;
    }

    public String getChannelId() {
        return this.mChannelId;
    }

    public void setChannelId(String str) {
        this.mChannelId = str;
    }

    public int getChannelNumber() {
        return this.mChannelNumber;
    }

    public void setChannelNumber(int i) {
        this.mChannelNumber = i;
    }

    public String getChannelGenreName() {
        return this.mChannelGenreName;
    }

    public void setChannelGenreName(String str) {
        this.mChannelGenreName = str;
    }

    public String getChannelTitle() {
        return this.mChannelTitle;
    }

    public void setChannelTitle(String str) {
        this.mChannelTitle = str;
    }

    public String getChannelDescription() {
        return this.mChannelDescription;
    }

    public void setChannelDescription(String str) {
        this.mChannelDescription = str;
    }

    public long getStart() {
        return this.mStart;
    }

    public void setStart(long j) {
        this.mStart = j;
    }

    public long getEnd() {
        return this.mEnd;
    }

    public void setEnd(long j) {
        this.mEnd = j;
    }

    public String getNextId() {
        return this.mNextId;
    }

    public void setNextId(String str) {
        this.mNextId = str;
    }

    public String getNextTitle() {
        return this.mNextTitle;
    }

    public void setNextTitle(String str) {
        this.mNextTitle = str;
    }

    public long getNextStart() {
        return this.mNextStart;
    }

    public void setNextDescriptionText(String str) {
        this.mNextDescriptionText = str;
    }

    public String getNextDescriptionText() {
        return this.mNextDescriptionText;
    }

    public void setNextStart(long j) {
        this.mNextStart = j;
    }

    public long getNextEnd() {
        return this.mNextEnd;
    }

    public void setNextEnd(long j) {
        this.mNextEnd = j;
    }

    public boolean isNew() {
        return this.mIsNew;
    }

    public void setNew(boolean z) {
        this.mIsNew = z;
    }

    public String getLiveTimeString() {
        return XumoUtil.getLiveTimeString(this.mStart, this.mEnd);
    }

    public String getProgramNumberString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CH ");
        stringBuilder.append(this.mChannelNumber);
        return stringBuilder.toString();
    }

    public long getEndTimeSinceNow() {
        return (this.mEnd - XumoUtil.getCurrentTimeMillisUTC()) / 1000;
    }

    public int getProgress() {
        return (int) ((((float) (XumoUtil.getCurrentTimeMillisUTC() - this.mStart)) / ((float) (this.mEnd - this.mStart))) * 100.0f);
    }

    public String getEndTimeSinceNowString() {
        if (getEndTimeSinceNow() / 60 <= 0) {
            return null;
        }
        return String.format(Locale.getDefault(), ON_NOW_TIME_MIN_LEFT, new Object[]{Long.valueOf(r0)});
    }

    public long getNextStartTimeSinceNow() {
        return (this.mNextStart - XumoUtil.getCurrentTimeMillisUTC()) / 1000;
    }

    public String getNextStartTimeSinceNowString() {
        if (getNextStartTimeSinceNow() / 60 <= 0) {
            return null;
        }
        return String.format(Locale.getDefault(), UP_NEXT_TIME_START_MIN, new Object[]{Long.valueOf(r0)});
    }

    public boolean isEmptyFavorite() {
        return this.mEmptyFavorite;
    }

    public void setmIsEmptyFavorite(boolean z) {
        this.mEmptyFavorite = z;
    }

    public boolean isIsCategoryHeader() {
        return this.mIsCategoryHeader;
    }

    public void setIsCategoryHeader(boolean z) {
        this.mIsCategoryHeader = z;
    }

    public boolean isPopular() {
        return this.mPopular;
    }

    public void setIsPopular(boolean z) {
        this.mPopular = z;
    }

    public boolean isFirstItemOfGenre() {
        return this.mFirstItemOfGenre;
    }

    public void setIsFirstItemOfGenre(boolean z) {
        this.mFirstItemOfGenre = z;
    }

    public String getGenre() {
        return this.mGenre;
    }

    public void setGenre(String str) {
        this.mGenre = str;
    }

    public int getGenreId() {
        return this.mGenreId;
    }

    public void setGenreId(int i) {
        this.mGenreId = i;
    }

    public int getChannelIndex() {
        return this.mChannelIndex;
    }

    public void setChannelIndex(int i) {
        this.mChannelIndex = i;
    }

    public boolean isHasVod() {
        return this.hasVod;
    }

    public void setHasVod(boolean z) {
        this.hasVod = z;
    }

    public com.xumo.xumo.model.OnNowLive clone() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r3 = this;
        r0 = super.clone();	 Catch:{ CloneNotSupportedException -> 0x0007 }
        r0 = (com.xumo.xumo.model.OnNowLive) r0;	 Catch:{ CloneNotSupportedException -> 0x0007 }
        goto L_0x000c;
    L_0x0007:
        r0 = new com.xumo.xumo.model.OnNowLive;
        r0.<init>();
    L_0x000c:
        r1 = r3.mId;
        r0.mId = r1;
        r1 = r3.mTitle;
        r0.mTitle = r1;
        r1 = r3.mDescriptionText;
        r0.mDescriptionText = r1;
        r1 = r3.mChannelId;
        r0.mChannelId = r1;
        r1 = r3.mChannelNumber;
        r0.mChannelNumber = r1;
        r1 = r3.mChannelGenreName;
        r0.mChannelGenreName = r1;
        r1 = r3.mChannelTitle;
        r0.mChannelTitle = r1;
        r1 = r3.mChannelDescription;
        r0.mChannelDescription = r1;
        r1 = r3.mStart;
        r0.mStart = r1;
        r1 = r3.mEnd;
        r0.mEnd = r1;
        r1 = r3.mNextTitle;
        r0.mNextTitle = r1;
        r1 = r3.mNextDescriptionText;
        r0.mNextDescriptionText = r1;
        r1 = r3.mNextStart;
        r0.mNextStart = r1;
        r1 = r3.mNextEnd;
        r0.mNextEnd = r1;
        r1 = r3.mIsNew;
        r0.mIsNew = r1;
        r1 = r3.mEmptyFavorite;
        r0.mEmptyFavorite = r1;
        r1 = r3.mIsCategoryHeader;
        r0.mIsCategoryHeader = r1;
        r1 = r3.mPopular;
        r0.mPopular = r1;
        r1 = r3.mGenre;
        r0.mGenre = r1;
        r1 = r3.mGenreId;
        r0.mGenreId = r1;
        r1 = r3.mChannelIndex;
        r0.mChannelIndex = r1;
        r1 = r3.hasVod;
        r0.hasVod = r1;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.model.OnNowLive.clone():com.xumo.xumo.model.OnNowLive");
    }
}
