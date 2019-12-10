package uk.co.chrisjenx.calligraphy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import java.lang.reflect.Method;

class CalligraphyFactory {
    private static final String ACTION_BAR_SUBTITLE = "action_bar_subtitle";
    private static final String ACTION_BAR_TITLE = "action_bar_title";
    private final int[] mAttributeId;

    protected static int[] getStyleForTextView(TextView textView) {
        int[] iArr = new int[]{-1, -1};
        if (isActionBarTitle(textView)) {
            iArr[0] = 16843470;
            iArr[1] = 16843512;
        } else if (isActionBarSubTitle(textView)) {
            iArr[0] = 16843470;
            iArr[1] = 16843513;
        }
        if (iArr[0] == -1) {
            iArr[0] = CalligraphyConfig.get().getClassStyles().containsKey(textView.getClass()) ? ((Integer) CalligraphyConfig.get().getClassStyles().get(textView.getClass())).intValue() : 16842804;
        }
        return iArr;
    }

    @SuppressLint({"NewApi"})
    protected static boolean isActionBarTitle(TextView textView) {
        if (matchesResourceIdName(textView, ACTION_BAR_TITLE)) {
            return true;
        }
        return parentIsToolbarV7(textView) ? TextUtils.equals(((Toolbar) textView.getParent()).getTitle(), textView.getText()) : null;
    }

    @SuppressLint({"NewApi"})
    protected static boolean isActionBarSubTitle(TextView textView) {
        if (matchesResourceIdName(textView, ACTION_BAR_SUBTITLE)) {
            return true;
        }
        return parentIsToolbarV7(textView) ? TextUtils.equals(((Toolbar) textView.getParent()).getSubtitle(), textView.getText()) : null;
    }

    protected static boolean parentIsToolbarV7(View view) {
        return (!CalligraphyUtils.canCheckForV7Toolbar() || view.getParent() == null || (view.getParent() instanceof Toolbar) == null) ? null : true;
    }

    protected static boolean matchesResourceIdName(View view, String str) {
        if (view.getId() == -1) {
            return null;
        }
        return view.getResources().getResourceEntryName(view.getId()).equalsIgnoreCase(str);
    }

    public CalligraphyFactory(int i) {
        this.mAttributeId = new int[]{i};
    }

    public View onViewCreated(View view, Context context, AttributeSet attributeSet) {
        if (!(view == null || view.getTag(R.id.calligraphy_tag_id) == Boolean.TRUE)) {
            onViewCreatedInternal(view, context, attributeSet);
            view.setTag(R.id.calligraphy_tag_id, Boolean.TRUE);
        }
        return view;
    }

    void onViewCreatedInternal(View view, Context context, AttributeSet attributeSet) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            if (!TypefaceUtils.isLoaded(textView.getTypeface())) {
                boolean z;
                String resolveFontPath = resolveFontPath(context, attributeSet);
                if (TextUtils.isEmpty(resolveFontPath)) {
                    int[] styleForTextView = getStyleForTextView(textView);
                    if (styleForTextView[1] != -1) {
                        resolveFontPath = CalligraphyUtils.pullFontPathFromTheme(context, styleForTextView[0], styleForTextView[1], this.mAttributeId);
                    } else {
                        resolveFontPath = CalligraphyUtils.pullFontPathFromTheme(context, styleForTextView[0], this.mAttributeId);
                    }
                }
                if (!matchesResourceIdName(view, ACTION_BAR_TITLE)) {
                    if (!matchesResourceIdName(view, ACTION_BAR_SUBTITLE)) {
                        z = false;
                        CalligraphyUtils.applyFontToTextView(context, textView, CalligraphyConfig.get(), resolveFontPath, z);
                    }
                }
                z = true;
                CalligraphyUtils.applyFontToTextView(context, textView, CalligraphyConfig.get(), resolveFontPath, z);
            } else {
                return;
            }
        }
        if (CalligraphyUtils.canCheckForV7Toolbar() && (view instanceof Toolbar)) {
            applyFontToToolbar((Toolbar) view);
        }
        if (view instanceof HasTypeface) {
            context = getDefaultTypeface(context, resolveFontPath(context, attributeSet));
            if (context != null) {
                ((HasTypeface) view).setTypeface(context);
            }
        } else if (CalligraphyConfig.get().isCustomViewTypefaceSupport() && CalligraphyConfig.get().isCustomViewHasTypeface(view)) {
            Method method = ReflectionUtils.getMethod(view.getClass(), "setTypeface");
            context = getDefaultTypeface(context, resolveFontPath(context, attributeSet));
            if (!(method == null || context == null)) {
                ReflectionUtils.invokeMethod(view, method, new Object[]{context});
            }
        }
    }

    private Typeface getDefaultTypeface(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            str = CalligraphyConfig.get().getFontPath();
        }
        return !TextUtils.isEmpty(str) ? TypefaceUtils.load(context.getAssets(), str) : null;
    }

    private String resolveFontPath(Context context, AttributeSet attributeSet) {
        String pullFontPathFromView = CalligraphyUtils.pullFontPathFromView(context, attributeSet, this.mAttributeId);
        if (TextUtils.isEmpty(pullFontPathFromView)) {
            pullFontPathFromView = CalligraphyUtils.pullFontPathFromStyle(context, attributeSet, this.mAttributeId);
        }
        return TextUtils.isEmpty(pullFontPathFromView) ? CalligraphyUtils.pullFontPathFromTextAppearance(context, attributeSet, this.mAttributeId) : pullFontPathFromView;
    }

    private void applyFontToToolbar(Toolbar toolbar) {
        CharSequence title = toolbar.getTitle();
        CharSequence subtitle = toolbar.getSubtitle();
        toolbar.setTitle((CharSequence) " ");
        toolbar.setSubtitle((CharSequence) " ");
        int childCount = toolbar.getChildCount();
        for (int i = 0; i < childCount; i++) {
            onViewCreated(toolbar.getChildAt(i), toolbar.getContext(), null);
        }
        toolbar.setTitle(title);
        toolbar.setSubtitle(subtitle);
    }
}
