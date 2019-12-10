package com.google.firebase.dynamiclinks;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.gms.internal.measurement.zzts;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;

public final class DynamicLink {
    private final Bundle zzbtu;

    public static final class AndroidParameters {
        final Bundle zzbtv;

        public static final class Builder {
            private final Bundle zzbtv;

            public Builder() {
                if (FirebaseApp.getInstance() != null) {
                    this.zzbtv = new Bundle();
                    this.zzbtv.putString("apn", FirebaseApp.getInstance().getApplicationContext().getPackageName());
                    return;
                }
                throw new IllegalStateException("FirebaseApp not initialized.");
            }

            public Builder(@NonNull String str) {
                this.zzbtv = new Bundle();
                this.zzbtv.putString("apn", str);
            }

            public final Builder setFallbackUrl(Uri uri) {
                this.zzbtv.putParcelable("afl", uri);
                return this;
            }

            public final Builder setMinimumVersion(int i) {
                this.zzbtv.putInt("amv", i);
                return this;
            }

            public final AndroidParameters build() {
                return new AndroidParameters(this.zzbtv);
            }
        }

        private AndroidParameters(Bundle bundle) {
            this.zzbtv = bundle;
        }
    }

    public static final class Builder {
        private final Bundle zzbtu = new Bundle();
        private final zzts zzbtw;
        private final Bundle zzbtx;

        public Builder(zzts com_google_android_gms_internal_measurement_zzts) {
            this.zzbtw = com_google_android_gms_internal_measurement_zzts;
            if (FirebaseApp.getInstance() != null) {
                this.zzbtu.putString("apiKey", FirebaseApp.getInstance().getOptions().getApiKey());
            }
            this.zzbtx = new Bundle();
            this.zzbtu.putBundle("parameters", this.zzbtx);
        }

        public final Builder setLongLink(@NonNull Uri uri) {
            this.zzbtu.putParcelable("dynamicLink", uri);
            return this;
        }

        public final Builder setLink(@NonNull Uri uri) {
            this.zzbtx.putParcelable("link", uri);
            return this;
        }

        @Deprecated
        public final Builder setDynamicLinkDomain(@NonNull String str) {
            if (!str.matches("(https:\\/\\/)?[a-z0-9]{3,}\\.app\\.goo\\.gl$")) {
                if (!str.matches("(https:\\/\\/)?[a-z0-9]{3,}\\.page\\.link$")) {
                    throw new IllegalArgumentException("Use setDomainUriPrefix() instead, setDynamicLinkDomain() is only applicable for *.page.link and *.app.goo.gl domains.");
                }
            }
            this.zzbtu.putString("domain", str);
            Bundle bundle = this.zzbtu;
            String str2 = "domainUriPrefix";
            String valueOf = String.valueOf("https://");
            str = String.valueOf(str);
            bundle.putString(str2, str.length() != 0 ? valueOf.concat(str) : new String(valueOf));
            return this;
        }

        public final Builder setDomainUriPrefix(@NonNull String str) {
            if (str.matches("(https:\\/\\/)?[a-z0-9]{3,}\\.app\\.goo\\.gl$") || str.matches("(https:\\/\\/)?[a-z0-9]{3,}\\.page\\.link$")) {
                this.zzbtu.putString("domain", str.replace("https://", ""));
            }
            this.zzbtu.putString("domainUriPrefix", str);
            return this;
        }

        public final Builder setAndroidParameters(AndroidParameters androidParameters) {
            this.zzbtx.putAll(androidParameters.zzbtv);
            return this;
        }

        public final Builder setIosParameters(IosParameters iosParameters) {
            this.zzbtx.putAll(iosParameters.zzbtv);
            return this;
        }

        public final Builder setGoogleAnalyticsParameters(GoogleAnalyticsParameters googleAnalyticsParameters) {
            this.zzbtx.putAll(googleAnalyticsParameters.zzbtv);
            return this;
        }

        public final Builder setItunesConnectAnalyticsParameters(ItunesConnectAnalyticsParameters itunesConnectAnalyticsParameters) {
            this.zzbtx.putAll(itunesConnectAnalyticsParameters.zzbtv);
            return this;
        }

        public final Builder setSocialMetaTagParameters(SocialMetaTagParameters socialMetaTagParameters) {
            this.zzbtx.putAll(socialMetaTagParameters.zzbtv);
            return this;
        }

        public final Builder setNavigationInfoParameters(NavigationInfoParameters navigationInfoParameters) {
            this.zzbtx.putAll(navigationInfoParameters.zzbtv);
            return this;
        }

        public final DynamicLink buildDynamicLink() {
            zzts.zzi(this.zzbtu);
            return new DynamicLink(this.zzbtu);
        }

        public final Task<ShortDynamicLink> buildShortDynamicLink() {
            zztw();
            return this.zzbtw.zzh(this.zzbtu);
        }

        public final Task<ShortDynamicLink> buildShortDynamicLink(int i) {
            zztw();
            this.zzbtu.putInt("suffix", i);
            return this.zzbtw.zzh(this.zzbtu);
        }

        private final void zztw() {
            if (this.zzbtu.getString("apiKey") == null) {
                throw new IllegalArgumentException("Missing API key. Set with setApiKey().");
            }
        }
    }

    public static final class GoogleAnalyticsParameters {
        Bundle zzbtv;

        public static final class Builder {
            private final Bundle zzbtv = new Bundle();

            public Builder(String str, String str2, String str3) {
                this.zzbtv.putString("utm_source", str);
                this.zzbtv.putString("utm_medium", str2);
                this.zzbtv.putString("utm_campaign", str3);
            }

            public final Builder setSource(String str) {
                this.zzbtv.putString("utm_source", str);
                return this;
            }

            public final Builder setMedium(String str) {
                this.zzbtv.putString("utm_medium", str);
                return this;
            }

            public final Builder setCampaign(String str) {
                this.zzbtv.putString("utm_campaign", str);
                return this;
            }

            public final Builder setTerm(String str) {
                this.zzbtv.putString("utm_term", str);
                return this;
            }

            public final Builder setContent(String str) {
                this.zzbtv.putString("utm_content", str);
                return this;
            }

            public final GoogleAnalyticsParameters build() {
                return new GoogleAnalyticsParameters(this.zzbtv);
            }
        }

        private GoogleAnalyticsParameters(Bundle bundle) {
            this.zzbtv = bundle;
        }
    }

    public static final class IosParameters {
        final Bundle zzbtv;

        public static final class Builder {
            private final Bundle zzbtv = new Bundle();

            public Builder(@NonNull String str) {
                this.zzbtv.putString("ibi", str);
            }

            public final Builder setFallbackUrl(Uri uri) {
                this.zzbtv.putParcelable("ifl", uri);
                return this;
            }

            public final Builder setCustomScheme(String str) {
                this.zzbtv.putString("ius", str);
                return this;
            }

            public final Builder setIpadFallbackUrl(Uri uri) {
                this.zzbtv.putParcelable("ipfl", uri);
                return this;
            }

            public final Builder setIpadBundleId(String str) {
                this.zzbtv.putString("ipbi", str);
                return this;
            }

            public final Builder setAppStoreId(String str) {
                this.zzbtv.putString("isi", str);
                return this;
            }

            public final Builder setMinimumVersion(String str) {
                this.zzbtv.putString("imv", str);
                return this;
            }

            public final IosParameters build() {
                return new IosParameters(this.zzbtv);
            }
        }

        private IosParameters(Bundle bundle) {
            this.zzbtv = bundle;
        }
    }

    public static final class ItunesConnectAnalyticsParameters {
        final Bundle zzbtv;

        public static final class Builder {
            private final Bundle zzbtv = new Bundle();

            public final Builder setProviderToken(String str) {
                this.zzbtv.putString("pt", str);
                return this;
            }

            public final Builder setAffiliateToken(String str) {
                this.zzbtv.putString("at", str);
                return this;
            }

            public final Builder setCampaignToken(String str) {
                this.zzbtv.putString("ct", str);
                return this;
            }

            public final ItunesConnectAnalyticsParameters build() {
                return new ItunesConnectAnalyticsParameters(this.zzbtv);
            }
        }

        private ItunesConnectAnalyticsParameters(Bundle bundle) {
            this.zzbtv = bundle;
        }
    }

    public static final class NavigationInfoParameters {
        final Bundle zzbtv;

        public static final class Builder {
            private final Bundle zzbtv = new Bundle();

            public final Builder setForcedRedirectEnabled(boolean z) {
                this.zzbtv.putInt("efr", z);
                return this;
            }

            public final NavigationInfoParameters build() {
                return new NavigationInfoParameters(this.zzbtv);
            }
        }

        private NavigationInfoParameters(Bundle bundle) {
            this.zzbtv = bundle;
        }
    }

    public static final class SocialMetaTagParameters {
        final Bundle zzbtv;

        public static final class Builder {
            private final Bundle zzbtv = new Bundle();

            public final Builder setTitle(String str) {
                this.zzbtv.putString("st", str);
                return this;
            }

            public final Builder setDescription(String str) {
                this.zzbtv.putString("sd", str);
                return this;
            }

            public final Builder setImageUrl(Uri uri) {
                this.zzbtv.putParcelable("si", uri);
                return this;
            }

            public final SocialMetaTagParameters build() {
                return new SocialMetaTagParameters(this.zzbtv);
            }
        }

        private SocialMetaTagParameters(Bundle bundle) {
            this.zzbtv = bundle;
        }
    }

    DynamicLink(Bundle bundle) {
        this.zzbtu = bundle;
    }

    public final Uri getUri() {
        Bundle bundle = this.zzbtu;
        zzts.zzi(bundle);
        Uri uri = (Uri) bundle.getParcelable("dynamicLink");
        if (uri != null) {
            return uri;
        }
        android.net.Uri.Builder builder = new android.net.Uri.Builder();
        Uri parse = Uri.parse(bundle.getString("domainUriPrefix"));
        builder.scheme(parse.getScheme());
        builder.authority(parse.getAuthority());
        builder.path(parse.getPath());
        bundle = bundle.getBundle("parameters");
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj != null) {
                builder.appendQueryParameter(str, obj.toString());
            }
        }
        return builder.build();
    }
}
