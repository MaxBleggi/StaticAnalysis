package androidx.preference;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class PreferenceInflater {
    private static final HashMap<String, Constructor> CONSTRUCTOR_MAP = new HashMap();
    private static final Class<?>[] CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class};
    private static final String EXTRA_TAG_NAME = "extra";
    private static final String INTENT_TAG_NAME = "intent";
    private static final String TAG = "PreferenceInflater";
    private final Object[] mConstructorArgs = new Object[2];
    private final Context mContext;
    private String[] mDefaultPackages;
    private PreferenceManager mPreferenceManager;

    public PreferenceInflater(Context context, PreferenceManager preferenceManager) {
        this.mContext = context;
        init(preferenceManager);
    }

    private void init(PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
        preferenceManager = new String[2];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Preference.class.getPackage().getName());
        stringBuilder.append(".");
        preferenceManager[0] = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(SwitchPreference.class.getPackage().getName());
        stringBuilder.append(".");
        preferenceManager[1] = stringBuilder.toString();
        setDefaultPackages(preferenceManager);
    }

    public void setDefaultPackages(String[] strArr) {
        this.mDefaultPackages = strArr;
    }

    public String[] getDefaultPackages() {
        return this.mDefaultPackages;
    }

    public Context getContext() {
        return this.mContext;
    }

    public Preference inflate(int i, @Nullable PreferenceGroup preferenceGroup) {
        XmlPullParser xml = getContext().getResources().getXml(i);
        try {
            preferenceGroup = inflate(xml, preferenceGroup);
            return preferenceGroup;
        } finally {
            xml.close();
        }
    }

    public Preference inflate(XmlPullParser xmlPullParser, @Nullable PreferenceGroup preferenceGroup) {
        synchronized (this.mConstructorArgs) {
            AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
            this.mConstructorArgs[0] = this.mContext;
            int next;
            do {
                try {
                    next = xmlPullParser.next();
                    if (next == 2) {
                        break;
                    }
                } catch (XmlPullParser xmlPullParser2) {
                    throw xmlPullParser2;
                } catch (XmlPullParser xmlPullParser22) {
                    preferenceGroup = new InflateException(xmlPullParser22.getMessage());
                    preferenceGroup.initCause(xmlPullParser22);
                    throw preferenceGroup;
                } catch (PreferenceGroup preferenceGroup2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(xmlPullParser22.getPositionDescription());
                    stringBuilder.append(": ");
                    stringBuilder.append(preferenceGroup2.getMessage());
                    InflateException inflateException = new InflateException(stringBuilder.toString());
                    inflateException.initCause(preferenceGroup2);
                    throw inflateException;
                }
            } while (next != 1);
            if (next == 2) {
                preferenceGroup2 = onMergeRoots(preferenceGroup2, (PreferenceGroup) createItemFromTag(xmlPullParser22.getName(), asAttributeSet));
                rInflate(xmlPullParser22, preferenceGroup2, asAttributeSet);
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(xmlPullParser22.getPositionDescription());
                stringBuilder2.append(": No start tag found!");
                throw new InflateException(stringBuilder2.toString());
            }
        }
        return preferenceGroup2;
    }

    @NonNull
    private PreferenceGroup onMergeRoots(PreferenceGroup preferenceGroup, @NonNull PreferenceGroup preferenceGroup2) {
        if (preferenceGroup != null) {
            return preferenceGroup;
        }
        preferenceGroup2.onAttachedToHierarchy(this.mPreferenceManager);
        return preferenceGroup2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private androidx.preference.Preference createItem(@androidx.annotation.NonNull java.lang.String r9, @androidx.annotation.Nullable java.lang.String[] r10, android.util.AttributeSet r11) throws java.lang.ClassNotFoundException, android.view.InflateException {
        /*
        r8 = this;
        r0 = CONSTRUCTOR_MAP;
        r0 = r0.get(r9);
        r0 = (java.lang.reflect.Constructor) r0;
        r1 = 1;
        if (r0 != 0) goto L_0x0072;
    L_0x000b:
        r0 = r8.mContext;	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r0 = r0.getClassLoader();	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        if (r10 == 0) goto L_0x005b;
    L_0x0013:
        r2 = r10.length;	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        if (r2 != 0) goto L_0x0017;
    L_0x0016:
        goto L_0x005b;
    L_0x0017:
        r2 = r10.length;	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r3 = 0;
        r4 = 0;
        r5 = r4;
    L_0x001b:
        if (r3 >= r2) goto L_0x0038;
    L_0x001d:
        r6 = r10[r3];	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r7 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x0034, Exception -> 0x006e }
        r7.<init>();	 Catch:{ ClassNotFoundException -> 0x0034, Exception -> 0x006e }
        r7.append(r6);	 Catch:{ ClassNotFoundException -> 0x0034, Exception -> 0x006e }
        r7.append(r9);	 Catch:{ ClassNotFoundException -> 0x0034, Exception -> 0x006e }
        r6 = r7.toString();	 Catch:{ ClassNotFoundException -> 0x0034, Exception -> 0x006e }
        r6 = r0.loadClass(r6);	 Catch:{ ClassNotFoundException -> 0x0034, Exception -> 0x006e }
        r4 = r6;
        goto L_0x0038;
    L_0x0034:
        r5 = move-exception;
        r3 = r3 + 1;
        goto L_0x001b;
    L_0x0038:
        if (r4 != 0) goto L_0x005f;
    L_0x003a:
        if (r5 != 0) goto L_0x005a;
    L_0x003c:
        r10 = new android.view.InflateException;	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r0 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r0.<init>();	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r1 = r11.getPositionDescription();	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r0.append(r1);	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r1 = ": Error inflating class ";
        r0.append(r1);	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r0.append(r9);	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r0 = r0.toString();	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r10.<init>(r0);	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        throw r10;	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
    L_0x005a:
        throw r5;	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
    L_0x005b:
        r4 = r0.loadClass(r9);	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
    L_0x005f:
        r10 = CONSTRUCTOR_SIGNATURE;	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r0 = r4.getConstructor(r10);	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r0.setAccessible(r1);	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r10 = CONSTRUCTOR_MAP;	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r10.put(r9, r0);	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        goto L_0x0072;
    L_0x006e:
        r10 = move-exception;
        goto L_0x007d;
    L_0x0070:
        r9 = move-exception;
        goto L_0x009e;
    L_0x0072:
        r10 = r8.mConstructorArgs;	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r10[r1] = r11;	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r10 = r0.newInstance(r10);	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        r10 = (androidx.preference.Preference) r10;	 Catch:{ ClassNotFoundException -> 0x0070, Exception -> 0x006e }
        return r10;
    L_0x007d:
        r0 = new android.view.InflateException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r11 = r11.getPositionDescription();
        r1.append(r11);
        r11 = ": Error inflating class ";
        r1.append(r11);
        r1.append(r9);
        r9 = r1.toString();
        r0.<init>(r9);
        r0.initCause(r10);
        throw r0;
    L_0x009e:
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.preference.PreferenceInflater.createItem(java.lang.String, java.lang.String[], android.util.AttributeSet):androidx.preference.Preference");
    }

    protected Preference onCreateItem(String str, AttributeSet attributeSet) throws ClassNotFoundException {
        return createItem(str, this.mDefaultPackages, attributeSet);
    }

    private Preference createItemFromTag(String str, AttributeSet attributeSet) {
        StringBuilder stringBuilder;
        InflateException inflateException;
        try {
            if (-1 == str.indexOf(46)) {
                return onCreateItem(str, attributeSet);
            }
            return createItem(str, null, attributeSet);
        } catch (String str2) {
            throw str2;
        } catch (Throwable e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(attributeSet.getPositionDescription());
            stringBuilder.append(": Error inflating class (not found)");
            stringBuilder.append(str2);
            inflateException = new InflateException(stringBuilder.toString());
            inflateException.initCause(e);
            throw inflateException;
        } catch (Throwable e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(attributeSet.getPositionDescription());
            stringBuilder.append(": Error inflating class ");
            stringBuilder.append(str2);
            inflateException = new InflateException(stringBuilder.toString());
            inflateException.initCause(e2);
            throw inflateException;
        }
    }

    private void rInflate(XmlPullParser xmlPullParser, Preference preference, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                return;
            }
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (INTENT_TAG_NAME.equals(name)) {
                    try {
                        preference.setIntent(Intent.parseIntent(getContext().getResources(), xmlPullParser, attributeSet));
                    } catch (XmlPullParser xmlPullParser2) {
                        preference = new XmlPullParserException("Error parsing preference");
                        preference.initCause(xmlPullParser2);
                        throw preference;
                    }
                } else if (EXTRA_TAG_NAME.equals(name)) {
                    getContext().getResources().parseBundleExtra(EXTRA_TAG_NAME, attributeSet, preference.getExtras());
                    try {
                        skipCurrentTag(xmlPullParser2);
                    } catch (XmlPullParser xmlPullParser22) {
                        preference = new XmlPullParserException("Error parsing preference");
                        preference.initCause(xmlPullParser22);
                        throw preference;
                    }
                } else {
                    Preference createItemFromTag = createItemFromTag(name, attributeSet);
                    ((PreferenceGroup) preference).addItemFromInflater(createItemFromTag);
                    rInflate(xmlPullParser22, createItemFromTag, attributeSet);
                }
            }
        }
    }

    private static void skipCurrentTag(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
        }
    }
}
