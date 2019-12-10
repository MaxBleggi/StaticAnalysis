package androidx.preference;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceManager.OnNavigateToScreenListener;

public final class PreferenceScreen extends PreferenceGroup {
    private boolean mShouldUseGeneratedIds = true;

    protected boolean isOnSameScreenAsChildren() {
        return false;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public PreferenceScreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.preferenceScreenStyle, 16842891));
    }

    protected void onClick() {
        if (getIntent() == null && getFragment() == null) {
            if (getPreferenceCount() != 0) {
                OnNavigateToScreenListener onNavigateToScreenListener = getPreferenceManager().getOnNavigateToScreenListener();
                if (onNavigateToScreenListener != null) {
                    onNavigateToScreenListener.onNavigateToScreen(this);
                }
            }
        }
    }

    public boolean shouldUseGeneratedIds() {
        return this.mShouldUseGeneratedIds;
    }

    public void setShouldUseGeneratedIds(boolean z) {
        if (isAttached()) {
            throw new IllegalStateException("Cannot change the usage of generated IDs while attached to the preference hierarchy");
        }
        this.mShouldUseGeneratedIds = z;
    }
}
