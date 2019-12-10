package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ParseException;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@KeepForSdk
@Class(creator = "SafeParcelResponseCreator")
@VisibleForTesting
public class SafeParcelResponse extends FastSafeParcelableJsonResponse {
    @KeepForSdk
    public static final Creator<SafeParcelResponse> CREATOR = new zap();
    private final String mClassName;
    @VersionField(getter = "getVersionCode", id = 1)
    private final int zale;
    @Field(getter = "getFieldMappingDictionary", id = 3)
    private final zak zapy;
    @Field(getter = "getParcel", id = 2)
    private final Parcel zara;
    private final int zarb;
    private int zarc;
    private int zard;

    public SafeParcelResponse(zak com_google_android_gms_common_server_response_zak, String str) {
        this.zale = 1;
        this.zara = Parcel.obtain();
        this.zarb = 0;
        this.zapy = (zak) Preconditions.checkNotNull(com_google_android_gms_common_server_response_zak);
        this.mClassName = (String) Preconditions.checkNotNull(str);
        this.zarc = 0;
    }

    private SafeParcelResponse(SafeParcelable safeParcelable, zak com_google_android_gms_common_server_response_zak, String str) {
        this.zale = 1;
        this.zara = Parcel.obtain();
        safeParcelable.writeToParcel(this.zara, 0);
        this.zarb = 1;
        this.zapy = (zak) Preconditions.checkNotNull(com_google_android_gms_common_server_response_zak);
        this.mClassName = (String) Preconditions.checkNotNull(str);
        this.zarc = 2;
    }

    @KeepForSdk
    public static <T extends FastJsonResponse & SafeParcelable> SafeParcelResponse from(T t) {
        String canonicalName = t.getClass().getCanonicalName();
        zak com_google_android_gms_common_server_response_zak = new zak(t.getClass());
        zaa(com_google_android_gms_common_server_response_zak, t);
        com_google_android_gms_common_server_response_zak.zacs();
        com_google_android_gms_common_server_response_zak.zacr();
        return new SafeParcelResponse((SafeParcelable) t, com_google_android_gms_common_server_response_zak, canonicalName);
    }

    private static void zaa(zak com_google_android_gms_common_server_response_zak, FastJsonResponse fastJsonResponse) {
        String valueOf;
        String str;
        Class cls = fastJsonResponse.getClass();
        if (!com_google_android_gms_common_server_response_zak.zaa(cls)) {
            fastJsonResponse = fastJsonResponse.getFieldMappings();
            com_google_android_gms_common_server_response_zak.zaa(cls, fastJsonResponse);
            for (String valueOf2 : fastJsonResponse.keySet()) {
                FastJsonResponse.Field field = (FastJsonResponse.Field) fastJsonResponse.get(valueOf2);
                Class cls2 = field.zapw;
                if (cls2 != null) {
                    try {
                        zaa(com_google_android_gms_common_server_response_zak, (FastJsonResponse) cls2.newInstance());
                    } catch (zak com_google_android_gms_common_server_response_zak2) {
                        str = "Could not instantiate an object of type ";
                        valueOf2 = String.valueOf(field.zapw.getCanonicalName());
                        throw new IllegalStateException(valueOf2.length() != 0 ? str.concat(valueOf2) : new String(str), com_google_android_gms_common_server_response_zak2);
                    } catch (zak com_google_android_gms_common_server_response_zak22) {
                        str = "Could not access object of type ";
                        valueOf2 = String.valueOf(field.zapw.getCanonicalName());
                        throw new IllegalStateException(valueOf2.length() != 0 ? str.concat(valueOf2) : new String(str), com_google_android_gms_common_server_response_zak22);
                    }
                }
            }
        }
    }

    @Constructor
    SafeParcelResponse(@Param(id = 1) int i, @Param(id = 2) Parcel parcel, @Param(id = 3) zak com_google_android_gms_common_server_response_zak) {
        this.zale = i;
        this.zara = (Parcel) Preconditions.checkNotNull(parcel);
        this.zarb = 2;
        this.zapy = com_google_android_gms_common_server_response_zak;
        if (this.zapy == null) {
            this.mClassName = null;
        } else {
            this.mClassName = this.zapy.zact();
        }
        this.zarc = 2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcelable parcelable;
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zale);
        SafeParcelWriter.writeParcel(parcel, 2, zacu(), false);
        switch (this.zarb) {
            case 0:
                parcelable = null;
                break;
            case 1:
                parcelable = this.zapy;
                break;
            case 2:
                parcelable = this.zapy;
                break;
            default:
                i = this.zarb;
                StringBuilder stringBuilder = new StringBuilder(34);
                stringBuilder.append("Invalid creation type: ");
                stringBuilder.append(i);
                throw new IllegalStateException(stringBuilder.toString());
        }
        SafeParcelWriter.writeParcelable(parcel, 3, parcelable, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    private final Parcel zacu() {
        switch (this.zarc) {
            case 0:
                this.zard = SafeParcelWriter.beginObjectHeader(this.zara);
                break;
            case 1:
                break;
            default:
                break;
        }
        SafeParcelWriter.finishObjectHeader(this.zara, this.zard);
        this.zarc = 2;
        return this.zara;
    }

    public Map<String, FastJsonResponse.Field<?, ?>> getFieldMappings() {
        if (this.zapy == null) {
            return null;
        }
        return this.zapy.zai(this.mClassName);
    }

    public Object getValueObject(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public boolean isPrimitiveFieldSet(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    private final void zab(FastJsonResponse.Field<?, ?> field) {
        if ((field.zapv != -1 ? true : null) == null) {
            throw new IllegalStateException("Field does not have a valid safe parcelable field id.");
        } else if (this.zara != null) {
            switch (this.zarc) {
                case null:
                    this.zard = SafeParcelWriter.beginObjectHeader(this.zara);
                    this.zarc = 1;
                    return;
                case 1:
                    return;
                case 2:
                    throw new IllegalStateException("Attempted to parse JSON with a SafeParcelResponse object that is already filled with data.");
                default:
                    throw new IllegalStateException("Unknown parse state in SafeParcelResponse.");
            }
        } else {
            throw new IllegalStateException("Internal Parcel object is null.");
        }
    }

    protected void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, int i) {
        zab(field);
        SafeParcelWriter.writeInt(this.zara, field.getSafeParcelableFieldId(), i);
    }

    protected final void zaa(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Integer> arrayList) {
        zab(field);
        str = arrayList.size();
        int[] iArr = new int[str];
        for (int i = 0; i < str; i++) {
            iArr[i] = ((Integer) arrayList.get(i)).intValue();
        }
        SafeParcelWriter.writeIntArray(this.zara, field.getSafeParcelableFieldId(), iArr, true);
    }

    protected final void zaa(FastJsonResponse.Field<?, ?> field, String str, BigInteger bigInteger) {
        zab(field);
        SafeParcelWriter.writeBigInteger(this.zara, field.getSafeParcelableFieldId(), bigInteger, true);
    }

    protected final void zab(FastJsonResponse.Field<?, ?> field, String str, ArrayList<BigInteger> arrayList) {
        zab(field);
        str = arrayList.size();
        BigInteger[] bigIntegerArr = new BigInteger[str];
        for (int i = 0; i < str; i++) {
            bigIntegerArr[i] = (BigInteger) arrayList.get(i);
        }
        SafeParcelWriter.writeBigIntegerArray(this.zara, field.getSafeParcelableFieldId(), bigIntegerArr, true);
    }

    protected void setLongInternal(FastJsonResponse.Field<?, ?> field, String str, long j) {
        zab(field);
        SafeParcelWriter.writeLong(this.zara, field.getSafeParcelableFieldId(), j);
    }

    protected final void zac(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Long> arrayList) {
        zab(field);
        str = arrayList.size();
        long[] jArr = new long[str];
        for (int i = 0; i < str; i++) {
            jArr[i] = ((Long) arrayList.get(i)).longValue();
        }
        SafeParcelWriter.writeLongArray(this.zara, field.getSafeParcelableFieldId(), jArr, true);
    }

    protected final void zaa(FastJsonResponse.Field<?, ?> field, String str, float f) {
        zab(field);
        SafeParcelWriter.writeFloat(this.zara, field.getSafeParcelableFieldId(), f);
    }

    protected final void zad(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Float> arrayList) {
        zab(field);
        str = arrayList.size();
        float[] fArr = new float[str];
        for (int i = 0; i < str; i++) {
            fArr[i] = ((Float) arrayList.get(i)).floatValue();
        }
        SafeParcelWriter.writeFloatArray(this.zara, field.getSafeParcelableFieldId(), fArr, true);
    }

    protected final void zaa(FastJsonResponse.Field<?, ?> field, String str, double d) {
        zab(field);
        SafeParcelWriter.writeDouble(this.zara, field.getSafeParcelableFieldId(), d);
    }

    protected final void zae(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Double> arrayList) {
        zab(field);
        str = arrayList.size();
        double[] dArr = new double[str];
        for (int i = 0; i < str; i++) {
            dArr[i] = ((Double) arrayList.get(i)).doubleValue();
        }
        SafeParcelWriter.writeDoubleArray(this.zara, field.getSafeParcelableFieldId(), dArr, true);
    }

    protected final void zaa(FastJsonResponse.Field<?, ?> field, String str, BigDecimal bigDecimal) {
        zab(field);
        SafeParcelWriter.writeBigDecimal(this.zara, field.getSafeParcelableFieldId(), bigDecimal, true);
    }

    protected final void zaf(FastJsonResponse.Field<?, ?> field, String str, ArrayList<BigDecimal> arrayList) {
        zab(field);
        str = arrayList.size();
        BigDecimal[] bigDecimalArr = new BigDecimal[str];
        for (int i = 0; i < str; i++) {
            bigDecimalArr[i] = (BigDecimal) arrayList.get(i);
        }
        SafeParcelWriter.writeBigDecimalArray(this.zara, field.getSafeParcelableFieldId(), bigDecimalArr, true);
    }

    protected void setBooleanInternal(FastJsonResponse.Field<?, ?> field, String str, boolean z) {
        zab(field);
        SafeParcelWriter.writeBoolean(this.zara, field.getSafeParcelableFieldId(), z);
    }

    protected final void zag(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Boolean> arrayList) {
        zab(field);
        str = arrayList.size();
        boolean[] zArr = new boolean[str];
        for (int i = 0; i < str; i++) {
            zArr[i] = ((Boolean) arrayList.get(i)).booleanValue();
        }
        SafeParcelWriter.writeBooleanArray(this.zara, field.getSafeParcelableFieldId(), zArr, true);
    }

    protected void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
        zab(field);
        SafeParcelWriter.writeString(this.zara, field.getSafeParcelableFieldId(), str2, true);
    }

    protected void setStringsInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<String> arrayList) {
        zab(field);
        str = arrayList.size();
        String[] strArr = new String[str];
        for (int i = 0; i < str; i++) {
            strArr[i] = (String) arrayList.get(i);
        }
        SafeParcelWriter.writeStringArray(this.zara, field.getSafeParcelableFieldId(), strArr, true);
    }

    protected void setDecodedBytesInternal(FastJsonResponse.Field<?, ?> field, String str, byte[] bArr) {
        zab(field);
        SafeParcelWriter.writeByteArray(this.zara, field.getSafeParcelableFieldId(), bArr, true);
    }

    protected final void zaa(FastJsonResponse.Field<?, ?> field, String str, Map<String, String> map) {
        zab(field);
        str = new Bundle();
        for (String str2 : map.keySet()) {
            str.putString(str2, (String) map.get(str2));
        }
        SafeParcelWriter.writeBundle(this.zara, field.getSafeParcelableFieldId(), str, true);
    }

    public <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> field, String str, T t) {
        zab(field);
        SafeParcelWriter.writeParcel(this.zara, field.getSafeParcelableFieldId(), ((SafeParcelResponse) t).zacu(), true);
    }

    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<T> arrayList) {
        zab(field);
        str = new ArrayList();
        arrayList.size();
        arrayList = arrayList;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            str.add(((SafeParcelResponse) ((FastJsonResponse) obj)).zacu());
        }
        SafeParcelWriter.writeParcelList(this.zara, field.getSafeParcelableFieldId(), str, true);
    }

    public String toString() {
        Preconditions.checkNotNull(this.zapy, "Cannot convert to JSON on client side.");
        Parcel zacu = zacu();
        zacu.setDataPosition(0);
        StringBuilder stringBuilder = new StringBuilder(100);
        zaa(stringBuilder, this.zapy.zai(this.mClassName), zacu);
        return stringBuilder.toString();
    }

    private final void zaa(StringBuilder stringBuilder, Map<String, FastJsonResponse.Field<?, ?>> map, Parcel parcel) {
        SparseArray sparseArray = new SparseArray();
        map = map.entrySet().iterator();
        while (map.hasNext()) {
            Entry entry = (Entry) map.next();
            sparseArray.put(((FastJsonResponse.Field) entry.getValue()).getSafeParcelableFieldId(), entry);
        }
        stringBuilder.append('{');
        map = SafeParcelReader.validateObjectHeader(parcel);
        Object obj = null;
        while (parcel.dataPosition() < map) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            Entry entry2 = (Entry) sparseArray.get(SafeParcelReader.getFieldId(readHeader));
            if (entry2 != null) {
                if (obj != null) {
                    stringBuilder.append(",");
                }
                String str = (String) entry2.getKey();
                FastJsonResponse.Field field = (FastJsonResponse.Field) entry2.getValue();
                stringBuilder.append("\"");
                stringBuilder.append(str);
                stringBuilder.append("\":");
                Bundle createBundle;
                if (field.zacn()) {
                    switch (field.zaps) {
                        case 0:
                            zab(stringBuilder, field, FastJsonResponse.zab(field, (Object) Integer.valueOf(SafeParcelReader.readInt(parcel, readHeader))));
                            break;
                        case 1:
                            zab(stringBuilder, field, FastJsonResponse.zab(field, (Object) SafeParcelReader.createBigInteger(parcel, readHeader)));
                            break;
                        case 2:
                            zab(stringBuilder, field, FastJsonResponse.zab(field, (Object) Long.valueOf(SafeParcelReader.readLong(parcel, readHeader))));
                            break;
                        case 3:
                            zab(stringBuilder, field, FastJsonResponse.zab(field, (Object) Float.valueOf(SafeParcelReader.readFloat(parcel, readHeader))));
                            break;
                        case 4:
                            zab(stringBuilder, field, FastJsonResponse.zab(field, (Object) Double.valueOf(SafeParcelReader.readDouble(parcel, readHeader))));
                            break;
                        case 5:
                            zab(stringBuilder, field, FastJsonResponse.zab(field, (Object) SafeParcelReader.createBigDecimal(parcel, readHeader)));
                            break;
                        case 6:
                            zab(stringBuilder, field, FastJsonResponse.zab(field, (Object) Boolean.valueOf(SafeParcelReader.readBoolean(parcel, readHeader))));
                            break;
                        case 7:
                            zab(stringBuilder, field, FastJsonResponse.zab(field, (Object) SafeParcelReader.createString(parcel, readHeader)));
                            break;
                        case 8:
                        case 9:
                            zab(stringBuilder, field, FastJsonResponse.zab(field, SafeParcelReader.createByteArray(parcel, readHeader)));
                            break;
                        case 10:
                            createBundle = SafeParcelReader.createBundle(parcel, readHeader);
                            HashMap hashMap = new HashMap();
                            for (String str2 : createBundle.keySet()) {
                                hashMap.put(str2, createBundle.getString(str2));
                            }
                            zab(stringBuilder, field, FastJsonResponse.zab(field, (Object) hashMap));
                            break;
                        case 11:
                            throw new IllegalArgumentException("Method does not accept concrete type.");
                        default:
                            map = field.zaps;
                            StringBuilder stringBuilder2 = new StringBuilder(36);
                            stringBuilder2.append("Unknown field out type = ");
                            stringBuilder2.append(map);
                            throw new IllegalArgumentException(stringBuilder2.toString());
                    }
                } else if (field.zapt) {
                    stringBuilder.append("[");
                    switch (field.zaps) {
                        case 0:
                            ArrayUtils.writeArray(stringBuilder, SafeParcelReader.createIntArray(parcel, readHeader));
                            break;
                        case 1:
                            ArrayUtils.writeArray(stringBuilder, SafeParcelReader.createBigIntegerArray(parcel, readHeader));
                            break;
                        case 2:
                            ArrayUtils.writeArray(stringBuilder, SafeParcelReader.createLongArray(parcel, readHeader));
                            break;
                        case 3:
                            ArrayUtils.writeArray(stringBuilder, SafeParcelReader.createFloatArray(parcel, readHeader));
                            break;
                        case 4:
                            ArrayUtils.writeArray(stringBuilder, SafeParcelReader.createDoubleArray(parcel, readHeader));
                            break;
                        case 5:
                            ArrayUtils.writeArray(stringBuilder, SafeParcelReader.createBigDecimalArray(parcel, readHeader));
                            break;
                        case 6:
                            ArrayUtils.writeArray(stringBuilder, SafeParcelReader.createBooleanArray(parcel, readHeader));
                            break;
                        case 7:
                            ArrayUtils.writeStringArray(stringBuilder, SafeParcelReader.createStringArray(parcel, readHeader));
                            break;
                        case 8:
                        case 9:
                        case 10:
                            throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                        case 11:
                            Parcel[] createParcelArray = SafeParcelReader.createParcelArray(parcel, readHeader);
                            readHeader = createParcelArray.length;
                            for (int i = 0; i < readHeader; i++) {
                                if (i > 0) {
                                    stringBuilder.append(",");
                                }
                                createParcelArray[i].setDataPosition(0);
                                zaa(stringBuilder, field.zacq(), createParcelArray[i]);
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unknown field type out.");
                    }
                    stringBuilder.append("]");
                } else {
                    byte[] createByteArray;
                    switch (field.zaps) {
                        case 0:
                            stringBuilder.append(SafeParcelReader.readInt(parcel, readHeader));
                            break;
                        case 1:
                            stringBuilder.append(SafeParcelReader.createBigInteger(parcel, readHeader));
                            break;
                        case 2:
                            stringBuilder.append(SafeParcelReader.readLong(parcel, readHeader));
                            break;
                        case 3:
                            stringBuilder.append(SafeParcelReader.readFloat(parcel, readHeader));
                            break;
                        case 4:
                            stringBuilder.append(SafeParcelReader.readDouble(parcel, readHeader));
                            break;
                        case 5:
                            stringBuilder.append(SafeParcelReader.createBigDecimal(parcel, readHeader));
                            break;
                        case 6:
                            stringBuilder.append(SafeParcelReader.readBoolean(parcel, readHeader));
                            break;
                        case 7:
                            str = SafeParcelReader.createString(parcel, readHeader);
                            stringBuilder.append("\"");
                            stringBuilder.append(JsonUtils.escapeString(str));
                            stringBuilder.append("\"");
                            break;
                        case 8:
                            createByteArray = SafeParcelReader.createByteArray(parcel, readHeader);
                            stringBuilder.append("\"");
                            stringBuilder.append(Base64Utils.encode(createByteArray));
                            stringBuilder.append("\"");
                            break;
                        case 9:
                            createByteArray = SafeParcelReader.createByteArray(parcel, readHeader);
                            stringBuilder.append("\"");
                            stringBuilder.append(Base64Utils.encodeUrlSafe(createByteArray));
                            stringBuilder.append("\"");
                            break;
                        case 10:
                            createBundle = SafeParcelReader.createBundle(parcel, readHeader);
                            Set<String> keySet = createBundle.keySet();
                            keySet.size();
                            stringBuilder.append("{");
                            Object obj2 = 1;
                            for (String str3 : keySet) {
                                if (obj2 == null) {
                                    stringBuilder.append(",");
                                }
                                stringBuilder.append("\"");
                                stringBuilder.append(str3);
                                stringBuilder.append("\"");
                                stringBuilder.append(":");
                                stringBuilder.append("\"");
                                stringBuilder.append(JsonUtils.escapeString(createBundle.getString(str3)));
                                stringBuilder.append("\"");
                                obj2 = null;
                            }
                            stringBuilder.append("}");
                            break;
                        case 11:
                            Parcel createParcel = SafeParcelReader.createParcel(parcel, readHeader);
                            createParcel.setDataPosition(0);
                            zaa(stringBuilder, field.zacq(), createParcel);
                            break;
                        default:
                            throw new IllegalStateException("Unknown field type out");
                    }
                }
                obj = 1;
            }
        }
        if (parcel.dataPosition() == map) {
            stringBuilder.append('}');
            return;
        }
        StringBuilder stringBuilder3 = new StringBuilder(37);
        stringBuilder3.append("Overread allowed size end=");
        stringBuilder3.append(map);
        throw new ParseException(stringBuilder3.toString(), parcel);
    }

    private final void zab(StringBuilder stringBuilder, FastJsonResponse.Field<?, ?> field, Object obj) {
        if (field.zapr) {
            ArrayList arrayList = (ArrayList) obj;
            stringBuilder.append("[");
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    stringBuilder.append(",");
                }
                zaa(stringBuilder, field.zapq, arrayList.get(i));
            }
            stringBuilder.append("]");
            return;
        }
        zaa(stringBuilder, field.zapq, obj);
    }

    private static void zaa(StringBuilder stringBuilder, int i, Object obj) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                stringBuilder.append(obj);
                return;
            case 7:
                stringBuilder.append("\"");
                stringBuilder.append(JsonUtils.escapeString(obj.toString()));
                stringBuilder.append("\"");
                return;
            case 8:
                stringBuilder.append("\"");
                stringBuilder.append(Base64Utils.encode((byte[]) obj));
                stringBuilder.append("\"");
                return;
            case 9:
                stringBuilder.append("\"");
                stringBuilder.append(Base64Utils.encodeUrlSafe((byte[]) obj));
                stringBuilder.append("\"");
                return;
            case 10:
                MapUtils.writeStringMapToJson(stringBuilder, (HashMap) obj);
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                StringBuilder stringBuilder2 = new StringBuilder(26);
                stringBuilder2.append("Unknown type = ");
                stringBuilder2.append(i);
                throw new IllegalArgumentException(stringBuilder2.toString());
        }
    }
}
