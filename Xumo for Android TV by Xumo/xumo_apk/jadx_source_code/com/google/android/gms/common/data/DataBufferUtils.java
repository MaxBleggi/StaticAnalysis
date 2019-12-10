package com.google.android.gms.common.data;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.ArrayList;

public final class DataBufferUtils {
    @KeepForSdk
    public static final String KEY_NEXT_PAGE_TOKEN = "next_page_token";
    @KeepForSdk
    public static final String KEY_PREV_PAGE_TOKEN = "prev_page_token";

    private DataBufferUtils() {
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeAndClose(DataBuffer<E> dataBuffer) {
        ArrayList<T> arrayList = new ArrayList(dataBuffer.getCount());
        try {
            for (E freeze : dataBuffer) {
                arrayList.add(freeze.freeze());
            }
            return arrayList;
        } finally {
            dataBuffer.close();
        }
    }

    public static boolean hasNextPage(DataBuffer<?> dataBuffer) {
        dataBuffer = dataBuffer.getMetadata();
        return (dataBuffer == null || dataBuffer.getString(KEY_NEXT_PAGE_TOKEN) == null) ? null : true;
    }

    public static boolean hasPrevPage(DataBuffer<?> dataBuffer) {
        dataBuffer = dataBuffer.getMetadata();
        return (dataBuffer == null || dataBuffer.getString(KEY_PREV_PAGE_TOKEN) == null) ? null : true;
    }

    public static boolean hasData(DataBuffer<?> dataBuffer) {
        return (dataBuffer == null || dataBuffer.getCount() <= null) ? null : true;
    }
}
