package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import com.google.android.gms.common.sqlite.CursorWrapper;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@KeepName
@KeepForSdk
@Class(creator = "DataHolderCreator", validate = true)
public final class DataHolder extends AbstractSafeParcelable implements Closeable {
    @KeepForSdk
    public static final Creator<DataHolder> CREATOR = new zac();
    private static final Builder zalx = new zab(new String[0], null);
    private boolean mClosed;
    @VersionField(id = 1000)
    private final int zale;
    @Field(getter = "getColumns", id = 1)
    private final String[] zalp;
    private Bundle zalq;
    @Field(getter = "getWindows", id = 2)
    private final CursorWindow[] zalr;
    @Field(getter = "getStatusCode", id = 3)
    private final int zals;
    @Field(getter = "getMetadata", id = 4)
    private final Bundle zalt;
    private int[] zalu;
    private int zalv;
    private boolean zalw;

    @KeepForSdk
    public static class Builder {
        private final String[] zalp;
        private final ArrayList<HashMap<String, Object>> zaly;
        private final String zalz;
        private final HashMap<Object, Integer> zama;
        private boolean zamb;
        private String zamc;

        private Builder(String[] strArr, String str) {
            this.zalp = (String[]) Preconditions.checkNotNull(strArr);
            this.zaly = new ArrayList();
            this.zalz = str;
            this.zama = new HashMap();
            this.zamb = null;
            this.zamc = null;
        }

        public Builder zaa(HashMap<String, Object> hashMap) {
            int intValue;
            Asserts.checkNotNull(hashMap);
            if (this.zalz != null) {
                Object obj = hashMap.get(this.zalz);
                if (obj != null) {
                    Integer num = (Integer) this.zama.get(obj);
                    if (num == null) {
                        this.zama.put(obj, Integer.valueOf(this.zaly.size()));
                    } else {
                        intValue = num.intValue();
                        if (intValue != -1) {
                            this.zaly.add(hashMap);
                        } else {
                            this.zaly.remove(intValue);
                            this.zaly.add(intValue, hashMap);
                        }
                        this.zamb = null;
                        return this;
                    }
                }
            }
            intValue = -1;
            if (intValue != -1) {
                this.zaly.remove(intValue);
                this.zaly.add(intValue, hashMap);
            } else {
                this.zaly.add(hashMap);
            }
            this.zamb = null;
            return this;
        }

        @KeepForSdk
        public Builder withRow(ContentValues contentValues) {
            Asserts.checkNotNull(contentValues);
            HashMap hashMap = new HashMap(contentValues.size());
            for (Entry entry : contentValues.valueSet()) {
                hashMap.put((String) entry.getKey(), entry.getValue());
            }
            return zaa(hashMap);
        }

        @KeepForSdk
        public DataHolder build(int i) {
            return new DataHolder(this, i);
        }

        @KeepForSdk
        public DataHolder build(int i, Bundle bundle) {
            return new DataHolder(this, i, bundle);
        }
    }

    public static class zaa extends RuntimeException {
        public zaa(String str) {
            super(str);
        }
    }

    @Constructor
    DataHolder(@Param(id = 1000) int i, @Param(id = 1) String[] strArr, @Param(id = 2) CursorWindow[] cursorWindowArr, @Param(id = 3) int i2, @Param(id = 4) Bundle bundle) {
        this.mClosed = false;
        this.zalw = true;
        this.zale = i;
        this.zalp = strArr;
        this.zalr = cursorWindowArr;
        this.zals = i2;
        this.zalt = bundle;
    }

    @KeepForSdk
    public DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.mClosed = false;
        this.zalw = true;
        this.zale = 1;
        this.zalp = (String[]) Preconditions.checkNotNull(strArr);
        this.zalr = (CursorWindow[]) Preconditions.checkNotNull(cursorWindowArr);
        this.zals = i;
        this.zalt = bundle;
        zaca();
    }

    private DataHolder(CursorWrapper cursorWrapper, int i, Bundle bundle) {
        this(cursorWrapper.getColumnNames(), zaa(cursorWrapper), i, bundle);
    }

    @KeepForSdk
    public DataHolder(Cursor cursor, int i, Bundle bundle) {
        this(new CursorWrapper(cursor), i, bundle);
    }

    private DataHolder(Builder builder, int i, Bundle bundle) {
        this(builder.zalp, zaa(builder, -1), i, null);
    }

    private DataHolder(Builder builder, int i, Bundle bundle, int i2) {
        this(builder.zalp, zaa(builder, -1), i, bundle);
    }

    public final void zaca() {
        int i;
        this.zalq = new Bundle();
        for (i = 0; i < this.zalp.length; i++) {
            this.zalq.putInt(this.zalp[i], i);
        }
        this.zalu = new int[this.zalr.length];
        i = 0;
        for (int i2 = 0; i2 < this.zalr.length; i2++) {
            this.zalu[i2] = i;
            i += this.zalr[i2].getNumRows() - (i - this.zalr[i2].getStartPosition());
        }
        this.zalv = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeStringArray(parcel, 1, this.zalp, false);
        SafeParcelWriter.writeTypedArray(parcel, 2, this.zalr, i, false);
        SafeParcelWriter.writeInt(parcel, 3, getStatusCode());
        SafeParcelWriter.writeBundle(parcel, 4, getMetadata(), false);
        SafeParcelWriter.writeInt(parcel, 1000, this.zale);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        if ((i & 1) != null) {
            close();
        }
    }

    @KeepForSdk
    public final int getStatusCode() {
        return this.zals;
    }

    @KeepForSdk
    public final Bundle getMetadata() {
        return this.zalt;
    }

    private static CursorWindow[] zaa(CursorWrapper cursorWrapper) {
        ArrayList arrayList = new ArrayList();
        try {
            int i;
            int count = cursorWrapper.getCount();
            CursorWindow window = cursorWrapper.getWindow();
            if (window == null || window.getStartPosition() != 0) {
                i = 0;
            } else {
                window.acquireReference();
                cursorWrapper.setWindow(null);
                arrayList.add(window);
                i = window.getNumRows();
            }
            while (i < count && cursorWrapper.moveToPosition(i)) {
                CursorWindow window2 = cursorWrapper.getWindow();
                if (window2 != null) {
                    window2.acquireReference();
                    cursorWrapper.setWindow(null);
                } else {
                    window2 = new CursorWindow(false);
                    window2.setStartPosition(i);
                    cursorWrapper.fillWindow(i, window2);
                }
                if (window2.getNumRows() == 0) {
                    break;
                }
                arrayList.add(window2);
                i = window2.getStartPosition() + window2.getNumRows();
            }
            cursorWrapper.close();
            return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
        } catch (Throwable th) {
            cursorWrapper.close();
        }
    }

    private static CursorWindow[] zaa(Builder builder, int i) {
        int i2 = 0;
        if (builder.zalp.length == 0) {
            return new CursorWindow[0];
        }
        int size;
        CursorWindow cursorWindow;
        ArrayList arrayList;
        CursorWindow cursorWindow2;
        int i3;
        Object obj;
        Map map;
        boolean z;
        int i4;
        String str;
        Object obj2;
        if (i >= 0) {
            if (i < builder.zaly.size()) {
                i = builder.zaly.subList(0, i);
                size = i.size();
                cursorWindow = new CursorWindow(false);
                arrayList = new ArrayList();
                arrayList.add(cursorWindow);
                cursorWindow.setNumColumns(builder.zalp.length);
                cursorWindow2 = cursorWindow;
                i3 = 0;
                obj = null;
                while (i3 < size) {
                    try {
                        if (!cursorWindow2.allocRow()) {
                            StringBuilder stringBuilder = new StringBuilder(72);
                            stringBuilder.append("Allocating additional cursor window for large data set (row ");
                            stringBuilder.append(i3);
                            stringBuilder.append(")");
                            Log.d("DataHolder", stringBuilder.toString());
                            cursorWindow2 = new CursorWindow(false);
                            cursorWindow2.setStartPosition(i3);
                            cursorWindow2.setNumColumns(builder.zalp.length);
                            arrayList.add(cursorWindow2);
                            if (!cursorWindow2.allocRow()) {
                                Log.e("DataHolder", "Unable to allocate row to hold data.");
                                arrayList.remove(cursorWindow2);
                                return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                            }
                        }
                        map = (Map) i.get(i3);
                        z = true;
                        for (i4 = 0; i4 < builder.zalp.length && z; i4++) {
                            str = builder.zalp[i4];
                            obj2 = map.get(str);
                            if (obj2 == null) {
                                z = cursorWindow2.putNull(i3, i4);
                            } else if (obj2 instanceof String) {
                                z = cursorWindow2.putString((String) obj2, i3, i4);
                            } else if (obj2 instanceof Long) {
                                z = cursorWindow2.putLong(((Long) obj2).longValue(), i3, i4);
                            } else if (obj2 instanceof Integer) {
                                z = cursorWindow2.putLong((long) ((Integer) obj2).intValue(), i3, i4);
                            } else if (obj2 instanceof Boolean) {
                                z = cursorWindow2.putLong(((Boolean) obj2).booleanValue() ? 1 : 0, i3, i4);
                            } else if (obj2 instanceof byte[]) {
                                z = cursorWindow2.putBlob((byte[]) obj2, i3, i4);
                            } else if (obj2 instanceof Double) {
                                z = cursorWindow2.putDouble(((Double) obj2).doubleValue(), i3, i4);
                            } else if (obj2 instanceof Float) {
                                i = String.valueOf(obj2);
                                StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(str).length() + 32) + String.valueOf(i).length());
                                stringBuilder2.append("Unsupported object for column ");
                                stringBuilder2.append(str);
                                stringBuilder2.append(": ");
                                stringBuilder2.append(i);
                                throw new IllegalArgumentException(stringBuilder2.toString());
                            } else {
                                z = cursorWindow2.putDouble((double) ((Float) obj2).floatValue(), i3, i4);
                            }
                        }
                        if (!z) {
                            obj = null;
                        } else if (obj != null) {
                            StringBuilder stringBuilder3 = new StringBuilder(74);
                            stringBuilder3.append("Couldn't populate window data for row ");
                            stringBuilder3.append(i3);
                            stringBuilder3.append(" - allocating new window.");
                            Log.d("DataHolder", stringBuilder3.toString());
                            cursorWindow2.freeLastRow();
                            cursorWindow2 = new CursorWindow(false);
                            cursorWindow2.setStartPosition(i3);
                            cursorWindow2.setNumColumns(builder.zalp.length);
                            arrayList.add(cursorWindow2);
                            i3--;
                            obj = 1;
                        } else {
                            throw new zaa("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                        }
                        i3++;
                    } catch (Builder builder2) {
                        i = arrayList.size();
                        while (i2 < i) {
                            ((CursorWindow) arrayList.get(i2)).close();
                            i2++;
                        }
                        throw builder2;
                    }
                }
                return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
            }
        }
        i = builder2.zaly;
        size = i.size();
        cursorWindow = new CursorWindow(false);
        arrayList = new ArrayList();
        arrayList.add(cursorWindow);
        cursorWindow.setNumColumns(builder2.zalp.length);
        cursorWindow2 = cursorWindow;
        i3 = 0;
        obj = null;
        while (i3 < size) {
            if (cursorWindow2.allocRow()) {
                StringBuilder stringBuilder4 = new StringBuilder(72);
                stringBuilder4.append("Allocating additional cursor window for large data set (row ");
                stringBuilder4.append(i3);
                stringBuilder4.append(")");
                Log.d("DataHolder", stringBuilder4.toString());
                cursorWindow2 = new CursorWindow(false);
                cursorWindow2.setStartPosition(i3);
                cursorWindow2.setNumColumns(builder2.zalp.length);
                arrayList.add(cursorWindow2);
                if (cursorWindow2.allocRow()) {
                    Log.e("DataHolder", "Unable to allocate row to hold data.");
                    arrayList.remove(cursorWindow2);
                    return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                }
            }
            map = (Map) i.get(i3);
            z = true;
            for (i4 = 0; i4 < builder2.zalp.length; i4++) {
                str = builder2.zalp[i4];
                obj2 = map.get(str);
                if (obj2 == null) {
                    z = cursorWindow2.putNull(i3, i4);
                } else if (obj2 instanceof String) {
                    z = cursorWindow2.putString((String) obj2, i3, i4);
                } else if (obj2 instanceof Long) {
                    z = cursorWindow2.putLong(((Long) obj2).longValue(), i3, i4);
                } else if (obj2 instanceof Integer) {
                    z = cursorWindow2.putLong((long) ((Integer) obj2).intValue(), i3, i4);
                } else if (obj2 instanceof Boolean) {
                    if (((Boolean) obj2).booleanValue()) {
                    }
                    z = cursorWindow2.putLong(((Boolean) obj2).booleanValue() ? 1 : 0, i3, i4);
                } else if (obj2 instanceof byte[]) {
                    z = cursorWindow2.putBlob((byte[]) obj2, i3, i4);
                } else if (obj2 instanceof Double) {
                    z = cursorWindow2.putDouble(((Double) obj2).doubleValue(), i3, i4);
                } else if (obj2 instanceof Float) {
                    i = String.valueOf(obj2);
                    StringBuilder stringBuilder22 = new StringBuilder((String.valueOf(str).length() + 32) + String.valueOf(i).length());
                    stringBuilder22.append("Unsupported object for column ");
                    stringBuilder22.append(str);
                    stringBuilder22.append(": ");
                    stringBuilder22.append(i);
                    throw new IllegalArgumentException(stringBuilder22.toString());
                } else {
                    z = cursorWindow2.putDouble((double) ((Float) obj2).floatValue(), i3, i4);
                }
            }
            if (!z) {
                obj = null;
            } else if (obj != null) {
                throw new zaa("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
            } else {
                StringBuilder stringBuilder32 = new StringBuilder(74);
                stringBuilder32.append("Couldn't populate window data for row ");
                stringBuilder32.append(i3);
                stringBuilder32.append(" - allocating new window.");
                Log.d("DataHolder", stringBuilder32.toString());
                cursorWindow2.freeLastRow();
                cursorWindow2 = new CursorWindow(false);
                cursorWindow2.setStartPosition(i3);
                cursorWindow2.setNumColumns(builder2.zalp.length);
                arrayList.add(cursorWindow2);
                i3--;
                obj = 1;
            }
            i3++;
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    private final void zaa(String str, int i) {
        if (this.zalq != null) {
            if (this.zalq.containsKey(str)) {
                if (isClosed() != null) {
                    throw new IllegalArgumentException("Buffer is closed.");
                } else if (i < 0 || i >= this.zalv) {
                    throw new CursorIndexOutOfBoundsException(i, this.zalv);
                } else {
                    return;
                }
            }
        }
        String str2 = "No such column: ";
        str = String.valueOf(str);
        throw new IllegalArgumentException(str.length() != 0 ? str2.concat(str) : new String(str2));
    }

    @KeepForSdk
    public final boolean hasColumn(String str) {
        return this.zalq.containsKey(str);
    }

    @KeepForSdk
    public final long getLong(String str, int i, int i2) {
        zaa(str, i);
        return this.zalr[i2].getLong(i, this.zalq.getInt(str));
    }

    @KeepForSdk
    public final int getInteger(String str, int i, int i2) {
        zaa(str, i);
        return this.zalr[i2].getInt(i, this.zalq.getInt(str));
    }

    @KeepForSdk
    public final String getString(String str, int i, int i2) {
        zaa(str, i);
        return this.zalr[i2].getString(i, this.zalq.getInt(str));
    }

    @KeepForSdk
    public final boolean getBoolean(String str, int i, int i2) {
        zaa(str, i);
        return Long.valueOf(this.zalr[i2].getLong(i, this.zalq.getInt(str))).longValue() == 1 ? true : null;
    }

    public final float zaa(String str, int i, int i2) {
        zaa(str, i);
        return this.zalr[i2].getFloat(i, this.zalq.getInt(str));
    }

    public final double zab(String str, int i, int i2) {
        zaa(str, i);
        return this.zalr[i2].getDouble(i, this.zalq.getInt(str));
    }

    @KeepForSdk
    public final byte[] getByteArray(String str, int i, int i2) {
        zaa(str, i);
        return this.zalr[i2].getBlob(i, this.zalq.getInt(str));
    }

    public final void zaa(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        zaa(str, i);
        this.zalr[i2].copyStringToBuffer(i, this.zalq.getInt(str), charArrayBuffer);
    }

    @KeepForSdk
    public final boolean hasNull(String str, int i, int i2) {
        zaa(str, i);
        return this.zalr[i2].isNull(i, this.zalq.getInt(str));
    }

    @KeepForSdk
    public final int getCount() {
        return this.zalv;
    }

    @KeepForSdk
    public final int getWindowIndex(int i) {
        int i2 = 0;
        boolean z = i >= 0 && i < this.zalv;
        Preconditions.checkState(z);
        while (i2 < this.zalu.length) {
            if (i < this.zalu[i2]) {
                i2--;
                break;
            }
            i2++;
        }
        return i2 == this.zalu.length ? i2 - 1 : i2;
    }

    @KeepForSdk
    public final boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    @KeepForSdk
    public final void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (CursorWindow close : this.zalr) {
                    close.close();
                }
            }
        }
    }

    protected final void finalize() throws Throwable {
        try {
            if (this.zalw && this.zalr.length > 0 && !isClosed()) {
                close();
                String obj = toString();
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(obj).length() + 178);
                stringBuilder.append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ");
                stringBuilder.append(obj);
                stringBuilder.append(")");
                Log.e("DataBuffer", stringBuilder.toString());
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    @KeepForSdk
    public static Builder builder(String[] strArr) {
        return new Builder(strArr);
    }

    @KeepForSdk
    public static DataHolder empty(int i) {
        return new DataHolder(zalx, i, null);
    }
}
