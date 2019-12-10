package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.metadata.id3.CommentFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate;
import com.google.android.exoplayer2.metadata.id3.InternalFrame;
import java.util.regex.Pattern;

public final class GaplessInfoHolder {
    private static final Pattern GAPLESS_COMMENT_PATTERN = Pattern.compile("^ [0-9a-fA-F]{8} ([0-9a-fA-F]{8}) ([0-9a-fA-F]{8})");
    private static final String GAPLESS_DESCRIPTION = "iTunSMPB";
    private static final String GAPLESS_DOMAIN = "com.apple.iTunes";
    public static final FramePredicate GAPLESS_INFO_ID3_FRAME_PREDICATE = -$$Lambda$GaplessInfoHolder$aHpHfalaLZjafWnaLsOKkROWrIo.INSTANCE;
    public int encoderDelay = -1;
    public int encoderPadding = -1;

    static /* synthetic */ boolean lambda$static$0(int i, int i2, int i3, int i4, int i5) {
        return i2 == 67 && i3 == 79 && i4 == 77 && (i5 == 77 || i == 2);
    }

    public boolean setFromXingHeaderValue(int i) {
        int i2 = i >> 12;
        i &= 4095;
        if (i2 <= 0) {
            if (i <= 0) {
                return false;
            }
        }
        this.encoderDelay = i2;
        this.encoderPadding = i;
        return true;
    }

    public boolean setFromMetadata(Metadata metadata) {
        for (int i = 0; i < metadata.length(); i++) {
            Entry entry = metadata.get(i);
            if (entry instanceof CommentFrame) {
                CommentFrame commentFrame = (CommentFrame) entry;
                if (GAPLESS_DESCRIPTION.equals(commentFrame.description) && setFromComment(commentFrame.text)) {
                    return true;
                }
            } else if (entry instanceof InternalFrame) {
                InternalFrame internalFrame = (InternalFrame) entry;
                if (GAPLESS_DOMAIN.equals(internalFrame.domain) && GAPLESS_DESCRIPTION.equals(internalFrame.description) && setFromComment(internalFrame.text)) {
                    return true;
                }
            } else {
                continue;
            }
        }
        return false;
    }

    private boolean setFromComment(java.lang.String r5) {
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
        r4 = this;
        r0 = GAPLESS_COMMENT_PATTERN;
        r5 = r0.matcher(r5);
        r0 = r5.find();
        if (r0 == 0) goto L_0x0029;
    L_0x000c:
        r0 = 1;
        r1 = r5.group(r0);	 Catch:{ NumberFormatException -> 0x0029 }
        r2 = 16;	 Catch:{ NumberFormatException -> 0x0029 }
        r1 = java.lang.Integer.parseInt(r1, r2);	 Catch:{ NumberFormatException -> 0x0029 }
        r3 = 2;	 Catch:{ NumberFormatException -> 0x0029 }
        r5 = r5.group(r3);	 Catch:{ NumberFormatException -> 0x0029 }
        r5 = java.lang.Integer.parseInt(r5, r2);	 Catch:{ NumberFormatException -> 0x0029 }
        if (r1 > 0) goto L_0x0024;	 Catch:{ NumberFormatException -> 0x0029 }
    L_0x0022:
        if (r5 <= 0) goto L_0x0029;	 Catch:{ NumberFormatException -> 0x0029 }
    L_0x0024:
        r4.encoderDelay = r1;	 Catch:{ NumberFormatException -> 0x0029 }
        r4.encoderPadding = r5;	 Catch:{ NumberFormatException -> 0x0029 }
        return r0;
    L_0x0029:
        r5 = 0;
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.GaplessInfoHolder.setFromComment(java.lang.String):boolean");
    }

    public boolean hasGaplessInfo() {
        return (this.encoderDelay == -1 || this.encoderPadding == -1) ? false : true;
    }
}
