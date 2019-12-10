package com.google.android.gms.common.server.response;

import com.google.android.exoplayer2.C;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@ShowFirstParty
@KeepForSdk
public class FastParser<T extends FastJsonResponse> {
    private static final char[] zaqf = new char[]{'u', 'l', 'l'};
    private static final char[] zaqg = new char[]{'r', 'u', 'e'};
    private static final char[] zaqh = new char[]{'r', 'u', 'e', '\"'};
    private static final char[] zaqi = new char[]{'a', 'l', 's', 'e'};
    private static final char[] zaqj = new char[]{'a', 'l', 's', 'e', '\"'};
    private static final char[] zaqk = new char[]{'\n'};
    private static final zaa<Integer> zaqm = new zaa();
    private static final zaa<Long> zaqn = new zab();
    private static final zaa<Float> zaqo = new zac();
    private static final zaa<Double> zaqp = new zad();
    private static final zaa<Boolean> zaqq = new zae();
    private static final zaa<String> zaqr = new zaf();
    private static final zaa<BigInteger> zaqs = new zag();
    private static final zaa<BigDecimal> zaqt = new zah();
    private final char[] zaqa = new char[1];
    private final char[] zaqb = new char[32];
    private final char[] zaqc = new char[1024];
    private final StringBuilder zaqd = new StringBuilder(32);
    private final StringBuilder zaqe = new StringBuilder(1024);
    private final Stack<Integer> zaql = new Stack();

    @ShowFirstParty
    @KeepForSdk
    public static class ParseException extends Exception {
        public ParseException(String str) {
            super(str);
        }

        public ParseException(String str, Throwable th) {
            super(str, th);
        }

        public ParseException(Throwable th) {
            super(th);
        }
    }

    private interface zaa<O> {
        O zah(FastParser fastParser, BufferedReader bufferedReader) throws ParseException, IOException;
    }

    @com.google.android.gms.common.annotation.KeepForSdk
    public void parse(java.io.InputStream r5, T r6) throws com.google.android.gms.common.server.response.FastParser.ParseException {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4 = this;
        r0 = new java.io.BufferedReader;
        r1 = new java.io.InputStreamReader;
        r1.<init>(r5);
        r5 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0.<init>(r1, r5);
        r5 = r4.zaql;	 Catch:{ IOException -> 0x009d }
        r1 = 0;	 Catch:{ IOException -> 0x009d }
        r2 = java.lang.Integer.valueOf(r1);	 Catch:{ IOException -> 0x009d }
        r5.push(r2);	 Catch:{ IOException -> 0x009d }
        r5 = r4.zaj(r0);	 Catch:{ IOException -> 0x009d }
        if (r5 == 0) goto L_0x0093;	 Catch:{ IOException -> 0x009d }
    L_0x001c:
        r2 = 91;	 Catch:{ IOException -> 0x009d }
        r3 = 1;	 Catch:{ IOException -> 0x009d }
        if (r5 == r2) goto L_0x004b;	 Catch:{ IOException -> 0x009d }
    L_0x0021:
        r2 = 123; // 0x7b float:1.72E-43 double:6.1E-322;	 Catch:{ IOException -> 0x009d }
        if (r5 != r2) goto L_0x0032;	 Catch:{ IOException -> 0x009d }
    L_0x0025:
        r5 = r4.zaql;	 Catch:{ IOException -> 0x009d }
        r2 = java.lang.Integer.valueOf(r3);	 Catch:{ IOException -> 0x009d }
        r5.push(r2);	 Catch:{ IOException -> 0x009d }
        r4.zaa(r0, r6);	 Catch:{ IOException -> 0x009d }
        goto L_0x007c;	 Catch:{ IOException -> 0x009d }
    L_0x0032:
        r6 = new com.google.android.gms.common.server.response.FastParser$ParseException;	 Catch:{ IOException -> 0x009d }
        r1 = 19;	 Catch:{ IOException -> 0x009d }
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x009d }
        r2.<init>(r1);	 Catch:{ IOException -> 0x009d }
        r1 = "Unexpected token: ";	 Catch:{ IOException -> 0x009d }
        r2.append(r1);	 Catch:{ IOException -> 0x009d }
        r2.append(r5);	 Catch:{ IOException -> 0x009d }
        r5 = r2.toString();	 Catch:{ IOException -> 0x009d }
        r6.<init>(r5);	 Catch:{ IOException -> 0x009d }
        throw r6;	 Catch:{ IOException -> 0x009d }
    L_0x004b:
        r5 = r4.zaql;	 Catch:{ IOException -> 0x009d }
        r2 = 5;	 Catch:{ IOException -> 0x009d }
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ IOException -> 0x009d }
        r5.push(r2);	 Catch:{ IOException -> 0x009d }
        r5 = r6.getFieldMappings();	 Catch:{ IOException -> 0x009d }
        r2 = r5.size();	 Catch:{ IOException -> 0x009d }
        if (r2 != r3) goto L_0x008b;	 Catch:{ IOException -> 0x009d }
    L_0x005f:
        r5 = r5.entrySet();	 Catch:{ IOException -> 0x009d }
        r5 = r5.iterator();	 Catch:{ IOException -> 0x009d }
        r5 = r5.next();	 Catch:{ IOException -> 0x009d }
        r5 = (java.util.Map.Entry) r5;	 Catch:{ IOException -> 0x009d }
        r5 = r5.getValue();	 Catch:{ IOException -> 0x009d }
        r5 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r5;	 Catch:{ IOException -> 0x009d }
        r2 = r4.zaa(r0, r5);	 Catch:{ IOException -> 0x009d }
        r3 = r5.zapu;	 Catch:{ IOException -> 0x009d }
        r6.addConcreteTypeArrayInternal(r5, r3, r2);	 Catch:{ IOException -> 0x009d }
    L_0x007c:
        r4.zak(r1);	 Catch:{ IOException -> 0x009d }
        r0.close();	 Catch:{ IOException -> 0x0083 }
        return;
    L_0x0083:
        r5 = "FastParser";
        r6 = "Failed to close reader while parsing.";
        android.util.Log.w(r5, r6);
        return;
    L_0x008b:
        r5 = new com.google.android.gms.common.server.response.FastParser$ParseException;	 Catch:{ IOException -> 0x009d }
        r6 = "Object array response class must have a single Field";	 Catch:{ IOException -> 0x009d }
        r5.<init>(r6);	 Catch:{ IOException -> 0x009d }
        throw r5;	 Catch:{ IOException -> 0x009d }
    L_0x0093:
        r5 = new com.google.android.gms.common.server.response.FastParser$ParseException;	 Catch:{ IOException -> 0x009d }
        r6 = "No data to parse";	 Catch:{ IOException -> 0x009d }
        r5.<init>(r6);	 Catch:{ IOException -> 0x009d }
        throw r5;	 Catch:{ IOException -> 0x009d }
    L_0x009b:
        r5 = move-exception;
        goto L_0x00a4;
    L_0x009d:
        r5 = move-exception;
        r6 = new com.google.android.gms.common.server.response.FastParser$ParseException;	 Catch:{ all -> 0x009b }
        r6.<init>(r5);	 Catch:{ all -> 0x009b }
        throw r6;	 Catch:{ all -> 0x009b }
    L_0x00a4:
        r0.close();	 Catch:{ IOException -> 0x00a8 }
        goto L_0x00af;
    L_0x00a8:
        r6 = "FastParser";
        r0 = "Failed to close reader while parsing.";
        android.util.Log.w(r6, r0);
    L_0x00af:
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.server.response.FastParser.parse(java.io.InputStream, com.google.android.gms.common.server.response.FastJsonResponse):void");
    }

    private final boolean zaa(BufferedReader bufferedReader, FastJsonResponse fastJsonResponse) throws ParseException, IOException {
        Map fieldMappings = fastJsonResponse.getFieldMappings();
        Object zaa = zaa(bufferedReader);
        if (zaa == null) {
            zak(1);
            return false;
        }
        while (zaa != null) {
            Field field = (Field) fieldMappings.get(zaa);
            if (field == null) {
                zaa = zab(bufferedReader);
            } else {
                StringBuilder stringBuilder;
                this.zaql.push(Integer.valueOf(4));
                char zaj;
                switch (field.zapq) {
                    case 0:
                        if (!field.zapr) {
                            fastJsonResponse.zaa(field, zad(bufferedReader));
                            break;
                        }
                        fastJsonResponse.zaa(field, zaa(bufferedReader, zaqm));
                        break;
                    case 1:
                        if (!field.zapr) {
                            fastJsonResponse.zaa(field, zaf(bufferedReader));
                            break;
                        }
                        fastJsonResponse.zab(field, zaa(bufferedReader, zaqs));
                        break;
                    case 2:
                        if (!field.zapr) {
                            fastJsonResponse.zaa(field, zae(bufferedReader));
                            break;
                        }
                        fastJsonResponse.zac(field, zaa(bufferedReader, zaqn));
                        break;
                    case 3:
                        if (!field.zapr) {
                            fastJsonResponse.zaa(field, zag(bufferedReader));
                            break;
                        }
                        fastJsonResponse.zad(field, zaa(bufferedReader, zaqo));
                        break;
                    case 4:
                        if (!field.zapr) {
                            fastJsonResponse.zaa(field, zah(bufferedReader));
                            break;
                        }
                        fastJsonResponse.zae(field, zaa(bufferedReader, zaqp));
                        break;
                    case 5:
                        if (!field.zapr) {
                            fastJsonResponse.zaa(field, zai(bufferedReader));
                            break;
                        }
                        fastJsonResponse.zaf(field, zaa(bufferedReader, zaqt));
                        break;
                    case 6:
                        if (!field.zapr) {
                            fastJsonResponse.zaa(field, zaa(bufferedReader, false));
                            break;
                        }
                        fastJsonResponse.zag(field, zaa(bufferedReader, zaqq));
                        break;
                    case 7:
                        if (!field.zapr) {
                            fastJsonResponse.zaa(field, zac(bufferedReader));
                            break;
                        }
                        fastJsonResponse.zah(field, zaa(bufferedReader, zaqr));
                        break;
                    case 8:
                        fastJsonResponse.zaa(field, Base64Utils.decode(zaa(bufferedReader, this.zaqc, this.zaqe, zaqk)));
                        break;
                    case 9:
                        fastJsonResponse.zaa(field, Base64Utils.decodeUrlSafe(zaa(bufferedReader, this.zaqc, this.zaqe, zaqk)));
                        break;
                    case 10:
                        Map map;
                        zaj = zaj(bufferedReader);
                        if (zaj == 'n') {
                            zab(bufferedReader, zaqf);
                            map = null;
                        } else if (zaj == '{') {
                            this.zaql.push(Integer.valueOf(1));
                            map = new HashMap();
                            while (true) {
                                char zaj2 = zaj(bufferedReader);
                                if (zaj2 == '\u0000') {
                                    throw new ParseException("Unexpected EOF");
                                } else if (zaj2 == '\"') {
                                    String zab = zab(bufferedReader, this.zaqb, this.zaqd, null);
                                    String valueOf;
                                    if (zaj(bufferedReader) != ':') {
                                        fastJsonResponse = "No map value found for key ";
                                        valueOf = String.valueOf(zab);
                                        throw new ParseException(valueOf.length() != 0 ? fastJsonResponse.concat(valueOf) : new String(fastJsonResponse));
                                    } else if (zaj(bufferedReader) != '\"') {
                                        fastJsonResponse = "Expected String value for key ";
                                        valueOf = String.valueOf(zab);
                                        throw new ParseException(valueOf.length() != 0 ? fastJsonResponse.concat(valueOf) : new String(fastJsonResponse));
                                    } else {
                                        map.put(zab, zab(bufferedReader, this.zaqb, this.zaqd, null));
                                        zaj2 = zaj(bufferedReader);
                                        if (zaj2 != ',') {
                                            if (zaj2 == '}') {
                                                zak(1);
                                            } else {
                                                stringBuilder = new StringBuilder(48);
                                                stringBuilder.append("Unexpected character while parsing string map: ");
                                                stringBuilder.append(zaj2);
                                                throw new ParseException(stringBuilder.toString());
                                            }
                                        }
                                    }
                                } else if (zaj2 == '}') {
                                    zak(1);
                                }
                            }
                        } else {
                            throw new ParseException("Expected start of a map object");
                        }
                        fastJsonResponse.zaa(field, map);
                        break;
                    case 11:
                        if (field.zapr) {
                            zaj = zaj(bufferedReader);
                            if (zaj == 'n') {
                                zab(bufferedReader, zaqf);
                                fastJsonResponse.addConcreteTypeArrayInternal(field, field.zapu, null);
                                break;
                            }
                            this.zaql.push(Integer.valueOf(5));
                            if (zaj == '[') {
                                fastJsonResponse.addConcreteTypeArrayInternal(field, field.zapu, zaa(bufferedReader, field));
                                break;
                            }
                            throw new ParseException("Expected array start");
                        }
                        zaj = zaj(bufferedReader);
                        if (zaj == 'n') {
                            zab(bufferedReader, zaqf);
                            fastJsonResponse.addConcreteTypeInternal(field, field.zapu, null);
                            break;
                        }
                        this.zaql.push(Integer.valueOf(1));
                        if (zaj == '{') {
                            try {
                                FastJsonResponse zacp = field.zacp();
                                zaa(bufferedReader, zacp);
                                fastJsonResponse.addConcreteTypeInternal(field, field.zapu, zacp);
                                break;
                            } catch (BufferedReader bufferedReader2) {
                                throw new ParseException("Error instantiating inner object", bufferedReader2);
                            } catch (BufferedReader bufferedReader22) {
                                throw new ParseException("Error instantiating inner object", bufferedReader22);
                            }
                        }
                        throw new ParseException("Expected start of object");
                    default:
                        fastJsonResponse = field.zapq;
                        StringBuilder stringBuilder2 = new StringBuilder(30);
                        stringBuilder2.append("Invalid field type ");
                        stringBuilder2.append(fastJsonResponse);
                        throw new ParseException(stringBuilder2.toString());
                }
                zak(4);
                zak(2);
                char zaj3 = zaj(bufferedReader22);
                if (zaj3 == ',') {
                    zaa = zaa(bufferedReader22);
                } else if (zaj3 == '}') {
                    zaa = null;
                } else {
                    stringBuilder = new StringBuilder(55);
                    stringBuilder.append("Expected end of object or field separator, but found: ");
                    stringBuilder.append(zaj3);
                    throw new ParseException(stringBuilder.toString());
                }
            }
        }
        zak(1);
        return true;
    }

    private final String zaa(BufferedReader bufferedReader) throws ParseException, IOException {
        this.zaql.push(Integer.valueOf(2));
        char zaj = zaj(bufferedReader);
        if (zaj == '\"') {
            this.zaql.push(Integer.valueOf(3));
            String zab = zab(bufferedReader, this.zaqb, this.zaqd, null);
            zak(3);
            if (zaj(bufferedReader) == 58) {
                return zab;
            }
            throw new ParseException("Expected key/value separator");
        } else if (zaj == ']') {
            zak(2);
            zak(1);
            zak(5);
            return null;
        } else if (zaj == '}') {
            zak(2);
            return null;
        } else {
            StringBuilder stringBuilder = new StringBuilder(19);
            stringBuilder.append("Unexpected token: ");
            stringBuilder.append(zaj);
            throw new ParseException(stringBuilder.toString());
        }
    }

    private final String zab(BufferedReader bufferedReader) throws ParseException, IOException {
        bufferedReader.mark(1024);
        char zaj = zaj(bufferedReader);
        if (zaj != '\"') {
            if (zaj != ',') {
                int i = 1;
                if (zaj == '[') {
                    this.zaql.push(Integer.valueOf(5));
                    bufferedReader.mark(32);
                    if (zaj(bufferedReader) == ']') {
                        zak(5);
                    } else {
                        bufferedReader.reset();
                        int i2 = 0;
                        int i3 = 0;
                        while (i > 0) {
                            char zaj2 = zaj(bufferedReader);
                            if (zaj2 == '\u0000') {
                                throw new ParseException("Unexpected EOF while parsing array");
                            } else if (Character.isISOControl(zaj2)) {
                                throw new ParseException("Unexpected control character while reading array");
                            } else {
                                if (zaj2 == '\"' && i2 == 0) {
                                    i3 ^= 1;
                                }
                                if (zaj2 == '[' && i3 == 0) {
                                    i++;
                                }
                                if (zaj2 == ']' && i3 == 0) {
                                    i--;
                                }
                                i2 = (zaj2 != '\\' || i3 == 0) ? 0 : i2 ^ 1;
                            }
                        }
                        zak(5);
                    }
                } else if (zaj != '{') {
                    bufferedReader.reset();
                    zaa(bufferedReader, this.zaqc);
                } else {
                    this.zaql.push(Integer.valueOf(1));
                    bufferedReader.mark(32);
                    zaj = zaj(bufferedReader);
                    if (zaj == '}') {
                        zak(1);
                    } else if (zaj == '\"') {
                        bufferedReader.reset();
                        zaa(bufferedReader);
                        do {
                        } while (zab(bufferedReader) != null);
                        zak(1);
                    } else {
                        StringBuilder stringBuilder = new StringBuilder(18);
                        stringBuilder.append("Unexpected token ");
                        stringBuilder.append(zaj);
                        throw new ParseException(stringBuilder.toString());
                    }
                }
            }
            throw new ParseException("Missing value");
        } else if (bufferedReader.read(this.zaqa) != -1) {
            zaj = this.zaqa[0];
            int i4 = 0;
            while (true) {
                if (zaj == '\"') {
                    if (i4 == 0) {
                        break;
                    }
                }
                i4 = zaj == '\\' ? i4 ^ 1 : 0;
                if (bufferedReader.read(this.zaqa) != -1) {
                    zaj = this.zaqa[0];
                    if (Character.isISOControl(zaj)) {
                        throw new ParseException("Unexpected control character while reading string");
                    }
                } else {
                    throw new ParseException("Unexpected EOF while parsing string");
                }
            }
        } else {
            throw new ParseException("Unexpected EOF while parsing string");
        }
        zaj = zaj(bufferedReader);
        if (zaj == ',') {
            zak(2);
            return zaa(bufferedReader);
        } else if (zaj == '}') {
            zak(2);
            return null;
        } else {
            stringBuilder = new StringBuilder(18);
            stringBuilder.append("Unexpected token ");
            stringBuilder.append(zaj);
            throw new ParseException(stringBuilder.toString());
        }
    }

    private final String zac(BufferedReader bufferedReader) throws ParseException, IOException {
        return zaa(bufferedReader, this.zaqb, this.zaqd, null);
    }

    private final <O> ArrayList<O> zaa(BufferedReader bufferedReader, zaa<O> com_google_android_gms_common_server_response_FastParser_zaa_O) throws ParseException, IOException {
        char zaj = zaj(bufferedReader);
        if (zaj == 'n') {
            zab(bufferedReader, zaqf);
            return null;
        } else if (zaj == '[') {
            this.zaql.push(Integer.valueOf(5));
            ArrayList<O> arrayList = new ArrayList();
            while (true) {
                bufferedReader.mark(1024);
                char zaj2 = zaj(bufferedReader);
                if (zaj2 == '\u0000') {
                    throw new ParseException("Unexpected EOF");
                } else if (zaj2 != ',') {
                    if (zaj2 != ']') {
                        bufferedReader.reset();
                        arrayList.add(com_google_android_gms_common_server_response_FastParser_zaa_O.zah(this, bufferedReader));
                    } else {
                        zak(5);
                        return arrayList;
                    }
                }
            }
        } else {
            throw new ParseException("Expected start of array");
        }
    }

    private final String zaa(BufferedReader bufferedReader, char[] cArr, StringBuilder stringBuilder, char[] cArr2) throws ParseException, IOException {
        char zaj = zaj(bufferedReader);
        if (zaj == '\"') {
            return zab(bufferedReader, cArr, stringBuilder, cArr2);
        }
        if (zaj == 110) {
            zab(bufferedReader, zaqf);
            return null;
        }
        throw new ParseException("Expected string");
    }

    private static String zab(BufferedReader bufferedReader, char[] cArr, StringBuilder stringBuilder, char[] cArr2) throws ParseException, IOException {
        stringBuilder.setLength(0);
        bufferedReader.mark(cArr.length);
        int i = 0;
        Object obj = null;
        loop0:
        while (true) {
            int read = bufferedReader.read(cArr);
            if (read != -1) {
                Object obj2 = obj;
                int i2 = i;
                i = 0;
                while (i < read) {
                    char c = cArr[i];
                    if (Character.isISOControl(c)) {
                        Object obj3;
                        if (cArr2 != null) {
                            for (char c2 : cArr2) {
                                if (c2 == c) {
                                    obj3 = 1;
                                    break;
                                }
                            }
                        }
                        obj3 = null;
                        if (obj3 == null) {
                            throw new ParseException("Unexpected control character while reading string");
                        }
                    }
                    if (c == '\"' && i2 == 0) {
                        break loop0;
                    }
                    if (c == '\\') {
                        i2 ^= 1;
                        obj2 = 1;
                    } else {
                        i2 = 0;
                    }
                    i++;
                }
                stringBuilder.append(cArr, 0, read);
                bufferedReader.mark(cArr.length);
                i = i2;
                obj = obj2;
            } else {
                throw new ParseException("Unexpected EOF while parsing string");
            }
        }
        stringBuilder.append(cArr, 0, i);
        bufferedReader.reset();
        bufferedReader.skip((long) (i + 1));
        if (obj2 != null) {
            return JsonUtils.unescapeString(stringBuilder.toString());
        }
        return stringBuilder.toString();
    }

    private final int zad(BufferedReader bufferedReader) throws ParseException, IOException {
        bufferedReader = zaa(bufferedReader, this.zaqc);
        if (bufferedReader == null) {
            return 0;
        }
        char[] cArr = this.zaqc;
        if (bufferedReader > null) {
            int i;
            Object obj;
            int i2;
            int i3;
            if (cArr[0] == '-') {
                i = 1;
                obj = 1;
                i2 = Integer.MIN_VALUE;
            } else {
                i = 0;
                obj = null;
                i2 = -2147483647;
            }
            if (i < bufferedReader) {
                i3 = i + 1;
                i = Character.digit(cArr[i], 10);
                if (i >= 0) {
                    i = -i;
                } else {
                    throw new ParseException("Unexpected non-digit character");
                }
            }
            i3 = i;
            i = 0;
            while (i3 < bufferedReader) {
                int i4 = i3 + 1;
                i3 = Character.digit(cArr[i3], 10);
                if (i3 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                } else if (i >= -214748364) {
                    i *= 10;
                    if (i >= i2 + i3) {
                        i -= i3;
                        i3 = i4;
                    } else {
                        throw new ParseException("Number too large");
                    }
                } else {
                    throw new ParseException("Number too large");
                }
            }
            if (obj == null) {
                return -i;
            }
            if (i3 > 1) {
                return i;
            }
            throw new ParseException("No digits to parse");
        }
        throw new ParseException("No number to parse");
    }

    private final long zae(BufferedReader bufferedReader) throws ParseException, IOException {
        int zaa = zaa(bufferedReader, this.zaqc);
        if (zaa == 0) {
            return 0;
        }
        char[] cArr = r0.zaqc;
        if (zaa > 0) {
            long j;
            Object obj;
            int i;
            int digit;
            long j2;
            int i2 = 0;
            if (cArr[0] == '-') {
                j = Long.MIN_VALUE;
                i2 = 1;
                obj = 1;
            } else {
                j = C.TIME_UNSET;
                obj = null;
            }
            if (i2 < zaa) {
                i = i2 + 1;
                digit = Character.digit(cArr[i2], 10);
                if (digit >= 0) {
                    j2 = (long) (-digit);
                } else {
                    throw new ParseException("Unexpected non-digit character");
                }
            }
            j2 = 0;
            i = i2;
            while (i < zaa) {
                digit = i + 1;
                i = Character.digit(cArr[i], 10);
                if (i < 0) {
                    throw new ParseException("Unexpected non-digit character");
                } else if (j2 >= -922337203685477580L) {
                    j2 *= 10;
                    long j3 = (long) i;
                    if (j2 >= j + j3) {
                        j2 -= j3;
                        i = digit;
                    } else {
                        throw new ParseException("Number too large");
                    }
                } else {
                    throw new ParseException("Number too large");
                }
            }
            if (obj == null) {
                return -j2;
            }
            if (i > 1) {
                return j2;
            }
            throw new ParseException("No digits to parse");
        }
        throw new ParseException("No number to parse");
    }

    private final BigInteger zaf(BufferedReader bufferedReader) throws ParseException, IOException {
        bufferedReader = zaa(bufferedReader, this.zaqc);
        if (bufferedReader == null) {
            return null;
        }
        return new BigInteger(new String(this.zaqc, 0, bufferedReader));
    }

    private final boolean zaa(BufferedReader bufferedReader, boolean z) throws ParseException, IOException {
        while (true) {
            char zaj = zaj(bufferedReader);
            if (zaj != '\"') {
                break;
            } else if (z) {
                throw new ParseException("No boolean value found in string");
            } else {
                z = true;
            }
        }
        if (zaj == 'f') {
            zab(bufferedReader, z ? zaqj : zaqi);
            return false;
        } else if (zaj == 'n') {
            zab(bufferedReader, zaqf);
            return false;
        } else if (zaj == 't') {
            zab(bufferedReader, z ? zaqh : zaqg);
            return true;
        } else {
            StringBuilder stringBuilder = new StringBuilder(true);
            stringBuilder.append("Unexpected token: ");
            stringBuilder.append(zaj);
            throw new ParseException(stringBuilder.toString());
        }
    }

    private final float zag(BufferedReader bufferedReader) throws ParseException, IOException {
        bufferedReader = zaa(bufferedReader, this.zaqc);
        if (bufferedReader == null) {
            return null;
        }
        return Float.parseFloat(new String(this.zaqc, 0, bufferedReader));
    }

    private final double zah(BufferedReader bufferedReader) throws ParseException, IOException {
        bufferedReader = zaa(bufferedReader, this.zaqc);
        if (bufferedReader == null) {
            return 0.0d;
        }
        return Double.parseDouble(new String(this.zaqc, 0, bufferedReader));
    }

    private final BigDecimal zai(BufferedReader bufferedReader) throws ParseException, IOException {
        bufferedReader = zaa(bufferedReader, this.zaqc);
        if (bufferedReader == null) {
            return null;
        }
        return new BigDecimal(new String(this.zaqc, 0, bufferedReader));
    }

    private final <T extends FastJsonResponse> ArrayList<T> zaa(BufferedReader bufferedReader, Field<?, ?> field) throws ParseException, IOException {
        ArrayList<T> arrayList = new ArrayList();
        char zaj = zaj(bufferedReader);
        if (zaj == ']') {
            zak(5);
            return arrayList;
        } else if (zaj == 'n') {
            zab(bufferedReader, zaqf);
            zak(5);
            return null;
        } else if (zaj == '{') {
            this.zaql.push(Integer.valueOf(1));
            while (true) {
                try {
                    FastJsonResponse zacp = field.zacp();
                    if (!zaa(bufferedReader, zacp)) {
                        return arrayList;
                    }
                    arrayList.add(zacp);
                    zaj = zaj(bufferedReader);
                    if (zaj != ',') {
                        break;
                    } else if (zaj(bufferedReader) == '{') {
                        this.zaql.push(Integer.valueOf(1));
                    } else {
                        throw new ParseException("Expected start of next object in array");
                    }
                } catch (BufferedReader bufferedReader2) {
                    throw new ParseException("Error instantiating inner object", bufferedReader2);
                } catch (BufferedReader bufferedReader22) {
                    throw new ParseException("Error instantiating inner object", bufferedReader22);
                }
            }
            if (zaj == ']') {
                zak(5);
                return arrayList;
            }
            field = new StringBuilder(19);
            field.append("Unexpected token: ");
            field.append(zaj);
            throw new ParseException(field.toString());
        } else {
            field = new StringBuilder(19);
            field.append("Unexpected token: ");
            field.append(zaj);
            throw new ParseException(field.toString());
        }
    }

    private final char zaj(BufferedReader bufferedReader) throws ParseException, IOException {
        if (bufferedReader.read(this.zaqa) == -1) {
            return '\u0000';
        }
        while (Character.isWhitespace(this.zaqa[0])) {
            if (bufferedReader.read(this.zaqa) == -1) {
                return '\u0000';
            }
        }
        return this.zaqa[0];
    }

    private final int zaa(BufferedReader bufferedReader, char[] cArr) throws ParseException, IOException {
        char zaj = zaj(bufferedReader);
        if (zaj == '\u0000') {
            throw new ParseException("Unexpected EOF");
        } else if (zaj == ',') {
            throw new ParseException("Missing value");
        } else if (zaj == 'n') {
            zab(bufferedReader, zaqf);
            return 0;
        } else {
            int i;
            bufferedReader.mark(1024);
            if (zaj == '\"') {
                i = 0;
                int i2 = 0;
                while (i < cArr.length && bufferedReader.read(cArr, i, 1) != -1) {
                    char c = cArr[i];
                    if (Character.isISOControl(c)) {
                        throw new ParseException("Unexpected control character while reading string");
                    } else if (c == '\"' && i2 == 0) {
                        bufferedReader.reset();
                        bufferedReader.skip((long) (i + 1));
                        return i;
                    } else {
                        i2 = c == '\\' ? i2 ^ 1 : 0;
                        i++;
                    }
                }
            } else {
                cArr[0] = zaj;
                i = 1;
                while (i < cArr.length && bufferedReader.read(cArr, i, 1) != -1) {
                    if (!(cArr[i] == '}' || cArr[i] == ',' || Character.isWhitespace(cArr[i]))) {
                        if (cArr[i] != ']') {
                            i++;
                        }
                    }
                    bufferedReader.reset();
                    bufferedReader.skip((long) (i - 1));
                    cArr[i] = '\u0000';
                    return i;
                }
            }
            if (i == cArr.length) {
                throw new ParseException("Absurdly long value");
            }
            throw new ParseException("Unexpected EOF");
        }
    }

    private final void zab(BufferedReader bufferedReader, char[] cArr) throws ParseException, IOException {
        int i = 0;
        while (i < cArr.length) {
            int read = bufferedReader.read(this.zaqb, 0, cArr.length - i);
            if (read != -1) {
                int i2 = 0;
                while (i2 < read) {
                    if (cArr[i2 + i] == this.zaqb[i2]) {
                        i2++;
                    } else {
                        throw new ParseException("Unexpected character");
                    }
                }
                i += read;
            } else {
                throw new ParseException("Unexpected EOF");
            }
        }
    }

    private final void zak(int i) throws ParseException {
        if (this.zaql.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder(46);
            stringBuilder.append("Expected state ");
            stringBuilder.append(i);
            stringBuilder.append(" but had empty stack");
            throw new ParseException(stringBuilder.toString());
        }
        int intValue = ((Integer) this.zaql.pop()).intValue();
        if (intValue != i) {
            StringBuilder stringBuilder2 = new StringBuilder(46);
            stringBuilder2.append("Expected state ");
            stringBuilder2.append(i);
            stringBuilder2.append(" but had ");
            stringBuilder2.append(intValue);
            throw new ParseException(stringBuilder2.toString());
        }
    }
}
