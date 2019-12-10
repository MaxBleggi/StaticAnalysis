package androidx.media;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(21)
class MediaSessionManagerImplApi21 extends MediaSessionManagerImplBase {
    MediaSessionManagerImplApi21(Context context) {
        super(context);
        this.mContext = context;
    }

    public boolean isTrustedForMediaControl(@NonNull RemoteUserInfoImpl remoteUserInfoImpl) {
        if (!hasMediaControlPermission(remoteUserInfoImpl)) {
            if (super.isTrustedForMediaControl(remoteUserInfoImpl) == null) {
                return null;
            }
        }
        return true;
    }

    private boolean hasMediaControlPermission(@NonNull RemoteUserInfoImpl remoteUserInfoImpl) {
        return getContext().checkPermission("android.permission.MEDIA_CONTENT_CONTROL", remoteUserInfoImpl.getPid(), remoteUserInfoImpl.getUid()) == null ? true : null;
    }
}
