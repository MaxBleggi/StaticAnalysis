package androidx.core.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.OperationCanceledException;
import androidx.core.os.CancellationSignal;

public final class ContentResolverCompat {
    private ContentResolverCompat() {
    }

    public static Cursor query(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) {
        if (VERSION.SDK_INT >= 16) {
            if (cancellationSignal != null) {
                try {
                    cancellationSignal = cancellationSignal.getCancellationSignalObject();
                } catch (ContentResolver contentResolver2) {
                    if ((contentResolver2 instanceof OperationCanceledException) != null) {
                        throw new androidx.core.os.OperationCanceledException();
                    }
                    throw contentResolver2;
                }
            }
            cancellationSignal = null;
            return contentResolver2.query(uri, strArr, str, strArr2, str2, (android.os.CancellationSignal) cancellationSignal);
        }
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        return contentResolver2.query(uri, strArr, str, strArr2, str2);
    }
}
