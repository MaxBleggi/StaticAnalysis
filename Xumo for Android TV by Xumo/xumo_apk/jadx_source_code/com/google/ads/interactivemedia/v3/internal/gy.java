package com.google.ads.interactivemedia.v3.internal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/* compiled from: IMASDK */
public final class gy {
    private final Map<Type, gb<?>> a;

    public gy(Map<Type, gb<?>> map) {
        this.a = map;
    }

    public <T> hd<T> a(hw<T> hwVar) {
        final Type b = hwVar.b();
        Class a = hwVar.a();
        final gb gbVar = (gb) this.a.get(b);
        if (gbVar != null) {
            return new hd<T>(this) {
                final /* synthetic */ gy c;

                public T a() {
                    return gbVar.a(b);
                }
            };
        }
        gbVar = (gb) this.a.get(a);
        if (gbVar != null) {
            return new hd<T>(this) {
                final /* synthetic */ gy c;

                public T a() {
                    return gbVar.a(b);
                }
            };
        }
        hd<T> a2 = a(a);
        if (a2 != null) {
            return a2;
        }
        a2 = a(b, a);
        if (a2 != null) {
            return a2;
        }
        return b(b, a);
    }

    private <T> com.google.ads.interactivemedia.v3.internal.hd<T> a(java.lang.Class<? super T> r2) {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r1 = this;
        r0 = 0;
        r0 = new java.lang.Class[r0];	 Catch:{ NoSuchMethodException -> 0x0017 }
        r2 = r2.getDeclaredConstructor(r0);	 Catch:{ NoSuchMethodException -> 0x0017 }
        r0 = r2.isAccessible();	 Catch:{ NoSuchMethodException -> 0x0017 }
        if (r0 != 0) goto L_0x0011;	 Catch:{ NoSuchMethodException -> 0x0017 }
    L_0x000d:
        r0 = 1;	 Catch:{ NoSuchMethodException -> 0x0017 }
        r2.setAccessible(r0);	 Catch:{ NoSuchMethodException -> 0x0017 }
    L_0x0011:
        r0 = new com.google.ads.interactivemedia.v3.internal.gy$8;	 Catch:{ NoSuchMethodException -> 0x0017 }
        r0.<init>(r1, r2);	 Catch:{ NoSuchMethodException -> 0x0017 }
        return r0;
    L_0x0017:
        r2 = 0;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.gy.a(java.lang.Class):com.google.ads.interactivemedia.v3.internal.hd<T>");
    }

    private <T> hd<T> a(final Type type, Class<? super T> cls) {
        if (Collection.class.isAssignableFrom(cls)) {
            if (SortedSet.class.isAssignableFrom(cls)) {
                return new hd<T>(this) {
                    final /* synthetic */ gy a;

                    {
                        this.a = r1;
                    }

                    public T a() {
                        return new TreeSet();
                    }
                };
            }
            if (EnumSet.class.isAssignableFrom(cls)) {
                return new hd<T>(this) {
                    final /* synthetic */ gy b;

                    public T a() {
                        if (type instanceof ParameterizedType) {
                            Type type = ((ParameterizedType) type).getActualTypeArguments()[0];
                            if (type instanceof Class) {
                                return EnumSet.noneOf((Class) type);
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Invalid EnumSet type: ");
                            stringBuilder.append(type.toString());
                            throw new gg(stringBuilder.toString());
                        }
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid EnumSet type: ");
                        stringBuilder.append(type.toString());
                        throw new gg(stringBuilder.toString());
                    }
                };
            }
            if (Set.class.isAssignableFrom(cls) != null) {
                return new hd<T>(this) {
                    final /* synthetic */ gy a;

                    {
                        this.a = r1;
                    }

                    public T a() {
                        return new LinkedHashSet();
                    }
                };
            }
            if (Queue.class.isAssignableFrom(cls) != null) {
                return new hd<T>(this) {
                    final /* synthetic */ gy a;

                    {
                        this.a = r1;
                    }

                    public T a() {
                        return new ArrayDeque();
                    }
                };
            }
            return new hd<T>(this) {
                final /* synthetic */ gy a;

                {
                    this.a = r1;
                }

                public T a() {
                    return new ArrayList();
                }
            };
        } else if (!Map.class.isAssignableFrom(cls)) {
            return null;
        } else {
            if (ConcurrentNavigableMap.class.isAssignableFrom(cls)) {
                return new hd<T>(this) {
                    final /* synthetic */ gy a;

                    {
                        this.a = r1;
                    }

                    public T a() {
                        return new ConcurrentSkipListMap();
                    }
                };
            }
            if (ConcurrentMap.class.isAssignableFrom(cls)) {
                return new hd<T>(this) {
                    final /* synthetic */ gy a;

                    {
                        this.a = r1;
                    }

                    public T a() {
                        return new ConcurrentHashMap();
                    }
                };
            }
            if (SortedMap.class.isAssignableFrom(cls) != null) {
                return new hd<T>(this) {
                    final /* synthetic */ gy a;

                    {
                        this.a = r1;
                    }

                    public T a() {
                        return new TreeMap();
                    }
                };
            }
            if ((type instanceof ParameterizedType) == null || String.class.isAssignableFrom(hw.a(((ParameterizedType) type).getActualTypeArguments()[0]).a()) != null) {
                return new hd<T>(this) {
                    final /* synthetic */ gy a;

                    {
                        this.a = r1;
                    }

                    public T a() {
                        return new hc();
                    }
                };
            }
            return new hd<T>(this) {
                final /* synthetic */ gy a;

                {
                    this.a = r1;
                }

                public T a() {
                    return new LinkedHashMap();
                }
            };
        }
    }

    private <T> hd<T> b(final Type type, final Class<? super T> cls) {
        return new hd<T>(this) {
            final /* synthetic */ gy c;
            private final hg d = hg.a();

            public T a() {
                try {
                    return this.d.a(cls);
                } catch (Throwable e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to invoke no-args constructor for ");
                    stringBuilder.append(type);
                    stringBuilder.append(". ");
                    stringBuilder.append("Register an InstanceCreator with Gson for this type may fix this problem.");
                    throw new RuntimeException(stringBuilder.toString(), e);
                }
            }
        };
    }

    public String toString() {
        return this.a.toString();
    }
}
