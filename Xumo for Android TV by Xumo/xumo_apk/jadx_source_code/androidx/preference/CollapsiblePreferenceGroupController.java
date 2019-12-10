package androidx.preference;

import android.content.Context;
import android.text.TextUtils;
import androidx.preference.Preference.OnPreferenceClickListener;
import java.util.ArrayList;
import java.util.List;

final class CollapsiblePreferenceGroupController {
    private final Context mContext;
    private boolean mHasExpandablePreference = false;
    final PreferenceGroupAdapter mPreferenceGroupAdapter;

    static class ExpandButton extends Preference {
        private long mId;

        ExpandButton(Context context, List<Preference> list, long j) {
            super(context);
            initLayout();
            setSummary(list);
            this.mId = j + 1000000;
        }

        private void initLayout() {
            setLayoutResource(R.layout.expand_button);
            setIcon(R.drawable.ic_arrow_down_24dp);
            setTitle(R.string.expand_button_title);
            setOrder(999);
        }

        private void setSummary(List<Preference> list) {
            List arrayList = new ArrayList();
            CharSequence charSequence = null;
            for (Preference preference : list) {
                CharSequence title = preference.getTitle();
                boolean z = preference instanceof PreferenceGroup;
                if (z && !TextUtils.isEmpty(title)) {
                    arrayList.add((PreferenceGroup) preference);
                }
                if (arrayList.contains(preference.getParent())) {
                    if (z) {
                        arrayList.add((PreferenceGroup) preference);
                    }
                } else if (!TextUtils.isEmpty(title)) {
                    if (charSequence == null) {
                        charSequence = title;
                    } else {
                        charSequence = getContext().getString(R.string.summary_collapsed_preference_list, new Object[]{charSequence, title});
                    }
                }
            }
            setSummary(charSequence);
        }

        public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            preferenceViewHolder.setDividerAllowedAbove(false);
        }

        public long getId() {
            return this.mId;
        }
    }

    CollapsiblePreferenceGroupController(PreferenceGroup preferenceGroup, PreferenceGroupAdapter preferenceGroupAdapter) {
        this.mPreferenceGroupAdapter = preferenceGroupAdapter;
        this.mContext = preferenceGroup.getContext();
    }

    public List<Preference> createVisiblePreferencesList(PreferenceGroup preferenceGroup) {
        return createInnerVisiblePreferencesList(preferenceGroup);
    }

    private List<Preference> createInnerVisiblePreferencesList(PreferenceGroup preferenceGroup) {
        int i = 0;
        this.mHasExpandablePreference = false;
        int i2 = preferenceGroup.getInitialExpandedChildrenCount() != Integer.MAX_VALUE ? 1 : 0;
        List<Preference> arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        int preferenceCount = preferenceGroup.getPreferenceCount();
        int i3 = 0;
        while (i < preferenceCount) {
            Preference preference = preferenceGroup.getPreference(i);
            if (preference.isVisible()) {
                PreferenceGroup preferenceGroup2;
                List<Preference> createInnerVisiblePreferencesList;
                if (i2 != 0) {
                    if (i3 >= preferenceGroup.getInitialExpandedChildrenCount()) {
                        arrayList2.add(preference);
                        if (preference instanceof PreferenceGroup) {
                            i3++;
                        } else {
                            preferenceGroup2 = (PreferenceGroup) preference;
                            if (!preferenceGroup2.isOnSameScreenAsChildren()) {
                                createInnerVisiblePreferencesList = createInnerVisiblePreferencesList(preferenceGroup2);
                                if (i2 != 0) {
                                    if (!this.mHasExpandablePreference) {
                                        throw new IllegalArgumentException("Nested expand buttons are not supported!");
                                    }
                                }
                                for (Preference preference2 : createInnerVisiblePreferencesList) {
                                    if (i2 != 0) {
                                        if (i3 < preferenceGroup.getInitialExpandedChildrenCount()) {
                                            arrayList2.add(preference2);
                                            i3++;
                                        }
                                    }
                                    arrayList.add(preference2);
                                    i3++;
                                }
                            }
                        }
                    }
                }
                arrayList.add(preference);
                if (preference instanceof PreferenceGroup) {
                    preferenceGroup2 = (PreferenceGroup) preference;
                    if (!preferenceGroup2.isOnSameScreenAsChildren()) {
                        createInnerVisiblePreferencesList = createInnerVisiblePreferencesList(preferenceGroup2);
                        if (i2 != 0) {
                            if (!this.mHasExpandablePreference) {
                                throw new IllegalArgumentException("Nested expand buttons are not supported!");
                            }
                        }
                        for (Preference preference22 : createInnerVisiblePreferencesList) {
                            if (i2 != 0) {
                                if (i3 < preferenceGroup.getInitialExpandedChildrenCount()) {
                                    arrayList2.add(preference22);
                                    i3++;
                                }
                            }
                            arrayList.add(preference22);
                            i3++;
                        }
                    }
                } else {
                    i3++;
                }
            }
            i++;
        }
        if (i2 != 0 && i3 > preferenceGroup.getInitialExpandedChildrenCount()) {
            arrayList.add(createExpandButton(preferenceGroup, arrayList2));
        }
        this.mHasExpandablePreference |= i2;
        return arrayList;
    }

    public boolean onPreferenceVisibilityChange(Preference preference) {
        if (!(preference instanceof PreferenceGroup)) {
            if (!this.mHasExpandablePreference) {
                return null;
            }
        }
        this.mPreferenceGroupAdapter.onPreferenceHierarchyChange(preference);
        return true;
    }

    private ExpandButton createExpandButton(final PreferenceGroup preferenceGroup, List<Preference> list) {
        ExpandButton expandButton = new ExpandButton(this.mContext, list, preferenceGroup.getId());
        expandButton.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                preferenceGroup.setInitialExpandedChildrenCount(Integer.MAX_VALUE);
                CollapsiblePreferenceGroupController.this.mPreferenceGroupAdapter.onPreferenceHierarchyChange(preference);
                preference = preferenceGroup.getOnExpandButtonClickListener();
                if (preference != null) {
                    preference.onExpandButtonClick();
                }
                return true;
            }
        });
        return expandButton;
    }
}
