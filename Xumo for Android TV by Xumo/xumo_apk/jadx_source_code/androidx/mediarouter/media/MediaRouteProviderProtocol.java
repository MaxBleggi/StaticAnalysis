package androidx.mediarouter.media;

abstract class MediaRouteProviderProtocol {
    public static final String CLIENT_DATA_ROUTE_ID = "routeId";
    public static final String CLIENT_DATA_ROUTE_LIBRARY_GROUP = "routeGroupId";
    public static final String CLIENT_DATA_UNSELECT_REASON = "unselectReason";
    public static final String CLIENT_DATA_VOLUME = "volume";
    public static final int CLIENT_MSG_CREATE_ROUTE_CONTROLLER = 3;
    public static final int CLIENT_MSG_REGISTER = 1;
    public static final int CLIENT_MSG_RELEASE_ROUTE_CONTROLLER = 4;
    public static final int CLIENT_MSG_ROUTE_CONTROL_REQUEST = 9;
    public static final int CLIENT_MSG_SELECT_ROUTE = 5;
    public static final int CLIENT_MSG_SET_DISCOVERY_REQUEST = 10;
    public static final int CLIENT_MSG_SET_ROUTE_VOLUME = 7;
    public static final int CLIENT_MSG_UNREGISTER = 2;
    public static final int CLIENT_MSG_UNSELECT_ROUTE = 6;
    public static final int CLIENT_MSG_UPDATE_ROUTE_VOLUME = 8;
    public static final int CLIENT_VERSION_1 = 1;
    public static final int CLIENT_VERSION_2 = 2;
    public static final int CLIENT_VERSION_CURRENT = 2;
    static final int CLIENT_VERSION_START = 1;
    public static final String SERVICE_DATA_ERROR = "error";
    public static final String SERVICE_INTERFACE = "android.media.MediaRouteProviderService";
    public static final int SERVICE_MSG_CONTROL_REQUEST_FAILED = 4;
    public static final int SERVICE_MSG_CONTROL_REQUEST_SUCCEEDED = 3;
    public static final int SERVICE_MSG_DESCRIPTOR_CHANGED = 5;
    public static final int SERVICE_MSG_GENERIC_FAILURE = 0;
    public static final int SERVICE_MSG_GENERIC_SUCCESS = 1;
    public static final int SERVICE_MSG_REGISTERED = 2;
    public static final int SERVICE_VERSION_1 = 1;
    public static final int SERVICE_VERSION_CURRENT = 1;

    public static boolean isValidRemoteMessenger(android.os.Messenger r1) {
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
        r0 = 0;
        if (r1 == 0) goto L_0x000c;
    L_0x0003:
        r1 = r1.getBinder();	 Catch:{ NullPointerException -> 0x000b }
        if (r1 == 0) goto L_0x000c;
    L_0x0009:
        r0 = 1;
        goto L_0x000c;
    L_0x000b:
        return r0;
    L_0x000c:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.media.MediaRouteProviderProtocol.isValidRemoteMessenger(android.os.Messenger):boolean");
    }

    private MediaRouteProviderProtocol() {
    }
}
