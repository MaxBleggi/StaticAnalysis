package androidx.appcompat.app;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.widget.ImageView;
import java.lang.reflect.Method;

class ActionBarDrawerToggleHoneycomb {
    private static final String TAG = "ActionBarDrawerToggleHC";
    private static final int[] THEME_ATTRS = null;

    static class SetIndicatorInfo {
        public Method setHomeActionContentDescription;
        public Method setHomeAsUpIndicator;
        public ImageView upIndicatorView;

        SetIndicatorInfo(android.app.Activity r1) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: androidx.appcompat.app.ActionBarDrawerToggleHoneycomb.SetIndicatorInfo.<init>(android.app.Activity):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 6 more
*/
            /*
            r0 = this;
            r6.<init>();
            r0 = 0;
            r1 = 1;
            r2 = android.app.ActionBar.class;
            r3 = "setHomeAsUpIndicator";
            r4 = new java.lang.Class[r1];
            r5 = android.graphics.drawable.Drawable.class;
            r4[r0] = r5;
            r2 = r2.getDeclaredMethod(r3, r4);
            r6.setHomeAsUpIndicator = r2;
            r2 = android.app.ActionBar.class;
            r3 = "setHomeActionContentDescription";
            r4 = new java.lang.Class[r1];
            r5 = java.lang.Integer.TYPE;
            r4[r0] = r5;
            r2 = r2.getDeclaredMethod(r3, r4);
            r6.setHomeActionContentDescription = r2;
            return;
            r2 = 16908332; // 0x102002c float:2.3877352E-38 double:8.353826E-317;
            r7 = r7.findViewById(r2);
            if (r7 != 0) goto L_0x0031;
            return;
            r7 = r7.getParent();
            r7 = (android.view.ViewGroup) r7;
            r3 = r7.getChildCount();
            r4 = 2;
            if (r3 == r4) goto L_0x003f;
            return;
            r0 = r7.getChildAt(r0);
            r7 = r7.getChildAt(r1);
            r1 = r0.getId();
            if (r1 != r2) goto L_0x004e;
            goto L_0x004f;
            r7 = r0;
            r0 = r7 instanceof android.widget.ImageView;
            if (r0 == 0) goto L_0x0057;
            r7 = (android.widget.ImageView) r7;
            r6.upIndicatorView = r7;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.ActionBarDrawerToggleHoneycomb.SetIndicatorInfo.<init>(android.app.Activity):void");
        }
    }

    static {
        THEME_ATTRS = new int[]{16843531};
    }

    public static SetIndicatorInfo setActionBarUpIndicator(SetIndicatorInfo setIndicatorInfo, Activity activity, Drawable drawable, int i) {
        setIndicatorInfo = new SetIndicatorInfo(activity);
        if (setIndicatorInfo.setHomeAsUpIndicator != null) {
            try {
                activity = activity.getActionBar();
                setIndicatorInfo.setHomeAsUpIndicator.invoke(activity, new Object[]{drawable});
                setIndicatorInfo.setHomeActionContentDescription.invoke(activity, new Object[]{Integer.valueOf(i)});
            } catch (Activity activity2) {
                Log.w(TAG, "Couldn't set home-as-up indicator via JB-MR2 API", activity2);
            }
        } else if (setIndicatorInfo.upIndicatorView != null) {
            setIndicatorInfo.upIndicatorView.setImageDrawable(drawable);
        } else {
            Log.w(TAG, "Couldn't set home-as-up indicator");
        }
        return setIndicatorInfo;
    }

    public static SetIndicatorInfo setActionBarDescription(SetIndicatorInfo setIndicatorInfo, Activity activity, int i) {
        if (setIndicatorInfo == null) {
            setIndicatorInfo = new SetIndicatorInfo(activity);
        }
        if (setIndicatorInfo.setHomeAsUpIndicator != null) {
            try {
                activity = activity.getActionBar();
                setIndicatorInfo.setHomeActionContentDescription.invoke(activity, new Object[]{Integer.valueOf(i)});
                if (VERSION.SDK_INT <= 19) {
                    activity.setSubtitle(activity.getSubtitle());
                }
            } catch (Activity activity2) {
                Log.w(TAG, "Couldn't set content description via JB-MR2 API", activity2);
            }
        }
        return setIndicatorInfo;
    }

    public static Drawable getThemeUpIndicator(Activity activity) {
        activity = activity.obtainStyledAttributes(THEME_ATTRS);
        Drawable drawable = activity.getDrawable(0);
        activity.recycle();
        return drawable;
    }

    private ActionBarDrawerToggleHoneycomb() {
    }
}
