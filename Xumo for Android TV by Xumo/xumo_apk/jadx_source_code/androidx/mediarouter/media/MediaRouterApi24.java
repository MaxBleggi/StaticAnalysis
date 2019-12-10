package androidx.mediarouter.media;

import androidx.annotation.RequiresApi;

@RequiresApi(24)
final class MediaRouterApi24 {

    public static final class RouteInfo {
        public static int getDeviceType(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getDeviceType();
        }

        private RouteInfo() {
        }
    }

    private MediaRouterApi24() {
    }
}
