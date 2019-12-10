package com.google.firebase.analytics;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Keep;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.Size;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.internal.zzbu;
import com.google.android.gms.measurement.internal.zzl;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.iid.FirebaseInstanceId;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;

public final class FirebaseAnalytics {
    private static volatile FirebaseAnalytics zzbsv;
    private final zzbu zzadp;
    private String zzbsw;
    private long zzbsx;
    private final Object zzbsy = new Object();

    public static class Event {
        public static final String ADD_PAYMENT_INFO = "add_payment_info";
        public static final String ADD_TO_CART = "add_to_cart";
        public static final String ADD_TO_WISHLIST = "add_to_wishlist";
        public static final String APP_OPEN = "app_open";
        public static final String BEGIN_CHECKOUT = "begin_checkout";
        public static final String CAMPAIGN_DETAILS = "campaign_details";
        public static final String CHECKOUT_PROGRESS = "checkout_progress";
        public static final String EARN_VIRTUAL_CURRENCY = "earn_virtual_currency";
        public static final String ECOMMERCE_PURCHASE = "ecommerce_purchase";
        public static final String GENERATE_LEAD = "generate_lead";
        public static final String JOIN_GROUP = "join_group";
        public static final String LEVEL_END = "level_end";
        public static final String LEVEL_START = "level_start";
        public static final String LEVEL_UP = "level_up";
        public static final String LOGIN = "login";
        public static final String POST_SCORE = "post_score";
        public static final String PRESENT_OFFER = "present_offer";
        public static final String PURCHASE_REFUND = "purchase_refund";
        public static final String REMOVE_FROM_CART = "remove_from_cart";
        public static final String SEARCH = "search";
        public static final String SELECT_CONTENT = "select_content";
        public static final String SET_CHECKOUT_OPTION = "set_checkout_option";
        public static final String SHARE = "share";
        public static final String SIGN_UP = "sign_up";
        public static final String SPEND_VIRTUAL_CURRENCY = "spend_virtual_currency";
        public static final String TUTORIAL_BEGIN = "tutorial_begin";
        public static final String TUTORIAL_COMPLETE = "tutorial_complete";
        public static final String UNLOCK_ACHIEVEMENT = "unlock_achievement";
        public static final String VIEW_ITEM = "view_item";
        public static final String VIEW_ITEM_LIST = "view_item_list";
        public static final String VIEW_SEARCH_RESULTS = "view_search_results";

        protected Event() {
        }
    }

    public static class Param {
        public static final String ACHIEVEMENT_ID = "achievement_id";
        public static final String ACLID = "aclid";
        public static final String AFFILIATION = "affiliation";
        public static final String CAMPAIGN = "campaign";
        public static final String CHARACTER = "character";
        public static final String CHECKOUT_OPTION = "checkout_option";
        public static final String CHECKOUT_STEP = "checkout_step";
        public static final String CONTENT = "content";
        public static final String CONTENT_TYPE = "content_type";
        public static final String COUPON = "coupon";
        public static final String CP1 = "cp1";
        public static final String CREATIVE_NAME = "creative_name";
        public static final String CREATIVE_SLOT = "creative_slot";
        public static final String CURRENCY = "currency";
        public static final String DESTINATION = "destination";
        public static final String END_DATE = "end_date";
        public static final String FLIGHT_NUMBER = "flight_number";
        public static final String GROUP_ID = "group_id";
        public static final String INDEX = "index";
        public static final String ITEM_BRAND = "item_brand";
        public static final String ITEM_CATEGORY = "item_category";
        public static final String ITEM_ID = "item_id";
        public static final String ITEM_LIST = "item_list";
        public static final String ITEM_LOCATION_ID = "item_location_id";
        public static final String ITEM_NAME = "item_name";
        public static final String ITEM_VARIANT = "item_variant";
        public static final String LEVEL = "level";
        public static final String LEVEL_NAME = "level_name";
        public static final String LOCATION = "location";
        public static final String MEDIUM = "medium";
        public static final String METHOD = "method";
        public static final String NUMBER_OF_NIGHTS = "number_of_nights";
        public static final String NUMBER_OF_PASSENGERS = "number_of_passengers";
        public static final String NUMBER_OF_ROOMS = "number_of_rooms";
        public static final String ORIGIN = "origin";
        public static final String PRICE = "price";
        public static final String QUANTITY = "quantity";
        public static final String SCORE = "score";
        public static final String SEARCH_TERM = "search_term";
        public static final String SHIPPING = "shipping";
        @Deprecated
        public static final String SIGN_UP_METHOD = "sign_up_method";
        public static final String SOURCE = "source";
        public static final String START_DATE = "start_date";
        public static final String SUCCESS = "success";
        public static final String TAX = "tax";
        public static final String TERM = "term";
        public static final String TRANSACTION_ID = "transaction_id";
        public static final String TRAVEL_CLASS = "travel_class";
        public static final String VALUE = "value";
        public static final String VIRTUAL_CURRENCY_NAME = "virtual_currency_name";

        protected Param() {
        }
    }

    public static class UserProperty {
        public static final String SIGN_UP_METHOD = "sign_up_method";

        protected UserProperty() {
        }
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    @NonNull
    @Keep
    public static FirebaseAnalytics getInstance(@NonNull Context context) {
        if (zzbsv == null) {
            synchronized (FirebaseAnalytics.class) {
                if (zzbsv == null) {
                    zzbsv = new FirebaseAnalytics(zzbu.zza(context, null));
                }
            }
        }
        return zzbsv;
    }

    public final void logEvent(@Size(max = 40, min = 1) @NonNull String str, @Nullable Bundle bundle) {
        this.zzadp.zzkm().logEvent(str, bundle);
    }

    public final void setUserProperty(@Size(max = 24, min = 1) @NonNull String str, @Size(max = 36) @Nullable String str2) {
        this.zzadp.zzkm().setUserProperty(str, str2);
    }

    @MainThread
    @Keep
    public final void setCurrentScreen(@NonNull Activity activity, @Size(max = 36, min = 1) @Nullable String str, @Size(max = 36, min = 1) @Nullable String str2) {
        if (zzl.isMainThread()) {
            this.zzadp.zzgm().setCurrentScreen(activity, str, str2);
        } else {
            this.zzadp.zzgt().zzjj().zzca("setCurrentScreen must be called from the main thread");
        }
    }

    public final void setAnalyticsCollectionEnabled(boolean z) {
        this.zzadp.zzkm().setMeasurementEnabled(z);
    }

    public final void setUserId(@Nullable String str) {
        this.zzadp.zzkm().setUserPropertyInternal(SettingsJsonConstants.APP_KEY, "_id", str);
    }

    public final void setMinimumSessionDuration(long j) {
        this.zzadp.zzkm().setMinimumSessionDuration(j);
    }

    public final void setSessionTimeoutDuration(long j) {
        this.zzadp.zzkm().setSessionTimeoutDuration(j);
    }

    @NonNull
    public final Task<String> getAppInstanceId() {
        try {
            String zzgc = zzgc();
            if (zzgc != null) {
                return Tasks.forResult(zzgc);
            }
            return Tasks.call(this.zzadp.zzgs().zzkg(), new zza(this));
        } catch (Exception e) {
            this.zzadp.zzgt().zzjj().zzca("Failed to schedule task for getAppInstanceId");
            return Tasks.forException(e);
        }
    }

    private FirebaseAnalytics(zzbu com_google_android_gms_measurement_internal_zzbu) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzbu);
        this.zzadp = com_google_android_gms_measurement_internal_zzbu;
    }

    public final void resetAnalyticsData() {
        zzcr(null);
        this.zzadp.zzgj().resetAnalyticsData(this.zzadp.zzbx().currentTimeMillis());
    }

    @Keep
    public final String getFirebaseInstanceId() {
        return FirebaseInstanceId.getInstance().getId();
    }

    private final void zzcr(String str) {
        synchronized (this.zzbsy) {
            this.zzbsw = str;
            this.zzbsx = this.zzadp.zzbx().elapsedRealtime();
        }
    }

    private final String zzgc() {
        synchronized (this.zzbsy) {
            if (Math.abs(this.zzadp.zzbx().elapsedRealtime() - this.zzbsx) < 1000) {
                String str = this.zzbsw;
                return str;
            }
            return null;
        }
    }
}
