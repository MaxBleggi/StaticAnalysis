package com.google.android.gms.internal.measurement;

import java.util.logging.Logger;

abstract class zzvv<T extends zzvk> {
    private static final Logger logger = Logger.getLogger(zzve.class.getName());
    private static String zzbzf = "com.google.protobuf.BlazeGeneratedExtensionRegistryLiteLoader";

    zzvv() {
    }

    protected abstract T zzwk();

    static <T extends com.google.android.gms.internal.measurement.zzvk> T zzd(java.lang.Class<T> r12) {
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
        r0 = com.google.android.gms.internal.measurement.zzvv.class;
        r0 = r0.getClassLoader();
        r1 = com.google.android.gms.internal.measurement.zzvk.class;
        r1 = r12.equals(r1);
        r2 = 1;
        r3 = 0;
        if (r1 == 0) goto L_0x0013;
    L_0x0010:
        r1 = zzbzf;
        goto L_0x003c;
    L_0x0013:
        r1 = r12.getPackage();
        r4 = com.google.android.gms.internal.measurement.zzvv.class;
        r4 = r4.getPackage();
        r1 = r1.equals(r4);
        if (r1 == 0) goto L_0x0108;
    L_0x0023:
        r1 = "%s.BlazeGenerated%sLoader";
        r4 = 2;
        r4 = new java.lang.Object[r4];
        r5 = r12.getPackage();
        r5 = r5.getName();
        r4[r3] = r5;
        r5 = r12.getSimpleName();
        r4[r2] = r5;
        r1 = java.lang.String.format(r1, r4);
    L_0x003c:
        r1 = java.lang.Class.forName(r1, r2, r0);	 Catch:{ ClassNotFoundException -> 0x0075 }
        r4 = new java.lang.Class[r3];	 Catch:{ NoSuchMethodException -> 0x006e, InstantiationException -> 0x0067, IllegalAccessException -> 0x0060, InvocationTargetException -> 0x0059 }
        r1 = r1.getConstructor(r4);	 Catch:{ NoSuchMethodException -> 0x006e, InstantiationException -> 0x0067, IllegalAccessException -> 0x0060, InvocationTargetException -> 0x0059 }
        r4 = new java.lang.Object[r3];	 Catch:{ NoSuchMethodException -> 0x006e, InstantiationException -> 0x0067, IllegalAccessException -> 0x0060, InvocationTargetException -> 0x0059 }
        r1 = r1.newInstance(r4);	 Catch:{ NoSuchMethodException -> 0x006e, InstantiationException -> 0x0067, IllegalAccessException -> 0x0060, InvocationTargetException -> 0x0059 }
        r1 = (com.google.android.gms.internal.measurement.zzvv) r1;	 Catch:{ NoSuchMethodException -> 0x006e, InstantiationException -> 0x0067, IllegalAccessException -> 0x0060, InvocationTargetException -> 0x0059 }
        r1 = r1.zzwk();	 Catch:{ ClassNotFoundException -> 0x0075 }
        r1 = r12.cast(r1);	 Catch:{ ClassNotFoundException -> 0x0075 }
        r1 = (com.google.android.gms.internal.measurement.zzvk) r1;	 Catch:{ ClassNotFoundException -> 0x0075 }
        return r1;	 Catch:{ ClassNotFoundException -> 0x0075 }
    L_0x0059:
        r1 = move-exception;	 Catch:{ ClassNotFoundException -> 0x0075 }
        r4 = new java.lang.IllegalStateException;	 Catch:{ ClassNotFoundException -> 0x0075 }
        r4.<init>(r1);	 Catch:{ ClassNotFoundException -> 0x0075 }
        throw r4;	 Catch:{ ClassNotFoundException -> 0x0075 }
    L_0x0060:
        r1 = move-exception;	 Catch:{ ClassNotFoundException -> 0x0075 }
        r4 = new java.lang.IllegalStateException;	 Catch:{ ClassNotFoundException -> 0x0075 }
        r4.<init>(r1);	 Catch:{ ClassNotFoundException -> 0x0075 }
        throw r4;	 Catch:{ ClassNotFoundException -> 0x0075 }
    L_0x0067:
        r1 = move-exception;	 Catch:{ ClassNotFoundException -> 0x0075 }
        r4 = new java.lang.IllegalStateException;	 Catch:{ ClassNotFoundException -> 0x0075 }
        r4.<init>(r1);	 Catch:{ ClassNotFoundException -> 0x0075 }
        throw r4;	 Catch:{ ClassNotFoundException -> 0x0075 }
    L_0x006e:
        r1 = move-exception;	 Catch:{ ClassNotFoundException -> 0x0075 }
        r4 = new java.lang.IllegalStateException;	 Catch:{ ClassNotFoundException -> 0x0075 }
        r4.<init>(r1);	 Catch:{ ClassNotFoundException -> 0x0075 }
        throw r4;	 Catch:{ ClassNotFoundException -> 0x0075 }
    L_0x0075:
        r1 = com.google.android.gms.internal.measurement.zzvv.class;
        r0 = java.util.ServiceLoader.load(r1, r0);
        r0 = r0.iterator();
        r1 = new java.util.ArrayList;
        r1.<init>();
    L_0x0084:
        r4 = r0.hasNext();
        if (r4 == 0) goto L_0x00c7;
    L_0x008a:
        r4 = r0.next();	 Catch:{ ServiceConfigurationError -> 0x009e }
        r4 = (com.google.android.gms.internal.measurement.zzvv) r4;	 Catch:{ ServiceConfigurationError -> 0x009e }
        r4 = r4.zzwk();	 Catch:{ ServiceConfigurationError -> 0x009e }
        r4 = r12.cast(r4);	 Catch:{ ServiceConfigurationError -> 0x009e }
        r4 = (com.google.android.gms.internal.measurement.zzvk) r4;	 Catch:{ ServiceConfigurationError -> 0x009e }
        r1.add(r4);	 Catch:{ ServiceConfigurationError -> 0x009e }
        goto L_0x0084;
    L_0x009e:
        r4 = move-exception;
        r10 = r4;
        r5 = logger;
        r6 = java.util.logging.Level.SEVERE;
        r7 = "com.google.protobuf.GeneratedExtensionRegistryLoader";
        r8 = "load";
        r4 = "Unable to load ";
        r9 = r12.getSimpleName();
        r9 = java.lang.String.valueOf(r9);
        r11 = r9.length();
        if (r11 == 0) goto L_0x00be;
    L_0x00b8:
        r4 = r4.concat(r9);
        r9 = r4;
        goto L_0x00c3;
    L_0x00be:
        r9 = new java.lang.String;
        r9.<init>(r4);
    L_0x00c3:
        r5.logp(r6, r7, r8, r9, r10);
        goto L_0x0084;
    L_0x00c7:
        r0 = r1.size();
        if (r0 != r2) goto L_0x00d4;
    L_0x00cd:
        r12 = r1.get(r3);
        r12 = (com.google.android.gms.internal.measurement.zzvk) r12;
        return r12;
    L_0x00d4:
        r0 = r1.size();
        r4 = 0;
        if (r0 != 0) goto L_0x00dc;
    L_0x00db:
        return r4;
    L_0x00dc:
        r0 = "combine";	 Catch:{ NoSuchMethodException -> 0x0101, IllegalAccessException -> 0x00fa, InvocationTargetException -> 0x00f3 }
        r5 = new java.lang.Class[r2];	 Catch:{ NoSuchMethodException -> 0x0101, IllegalAccessException -> 0x00fa, InvocationTargetException -> 0x00f3 }
        r6 = java.util.Collection.class;	 Catch:{ NoSuchMethodException -> 0x0101, IllegalAccessException -> 0x00fa, InvocationTargetException -> 0x00f3 }
        r5[r3] = r6;	 Catch:{ NoSuchMethodException -> 0x0101, IllegalAccessException -> 0x00fa, InvocationTargetException -> 0x00f3 }
        r12 = r12.getMethod(r0, r5);	 Catch:{ NoSuchMethodException -> 0x0101, IllegalAccessException -> 0x00fa, InvocationTargetException -> 0x00f3 }
        r0 = new java.lang.Object[r2];	 Catch:{ NoSuchMethodException -> 0x0101, IllegalAccessException -> 0x00fa, InvocationTargetException -> 0x00f3 }
        r0[r3] = r1;	 Catch:{ NoSuchMethodException -> 0x0101, IllegalAccessException -> 0x00fa, InvocationTargetException -> 0x00f3 }
        r12 = r12.invoke(r4, r0);	 Catch:{ NoSuchMethodException -> 0x0101, IllegalAccessException -> 0x00fa, InvocationTargetException -> 0x00f3 }
        r12 = (com.google.android.gms.internal.measurement.zzvk) r12;	 Catch:{ NoSuchMethodException -> 0x0101, IllegalAccessException -> 0x00fa, InvocationTargetException -> 0x00f3 }
        return r12;
    L_0x00f3:
        r12 = move-exception;
        r0 = new java.lang.IllegalStateException;
        r0.<init>(r12);
        throw r0;
    L_0x00fa:
        r12 = move-exception;
        r0 = new java.lang.IllegalStateException;
        r0.<init>(r12);
        throw r0;
    L_0x0101:
        r12 = move-exception;
        r0 = new java.lang.IllegalStateException;
        r0.<init>(r12);
        throw r0;
    L_0x0108:
        r0 = new java.lang.IllegalArgumentException;
        r12 = r12.getName();
        r0.<init>(r12);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzvv.zzd(java.lang.Class):T");
    }
}
