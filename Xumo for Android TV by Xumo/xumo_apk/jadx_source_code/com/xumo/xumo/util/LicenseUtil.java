package com.xumo.xumo.util;

import android.content.Context;
import android.os.AsyncTask;

public class LicenseUtil {
    private static final String HTML_BEGIN_HTML = "<html style=\"font-family: Raleway-Regular;\"><body style=\"margin: 20px;\">";
    private static final String HTML_END_HTML = "</body></html>";
    private static final String LICENSE_DESCRIPTION_HTML = "<div style=\"font-size: 1.25rem; color: rgba(80,80,80,1); margin-bottom: 15px;\">The following third party libraries are contained in Xumo application.</div>";
    private static final String LICENSE_DETAIL_BEGIN_HTML = "<div style=\"overflow: hidden;\"><pre id=\"%s\" style=\"display: none; padding: 0.8rem; border: 1px solid rgba(232,232,232,1); background-color: rgba(230,230,230,1); overflow-x: scroll;\">";
    private static final String LICENSE_DETAIL_END_HTML = "</pre></div>";
    private static final String LICENSE_DIRECTORY_NAME = "licenses";
    private static final String LICENSE_LIST_BEGIN_HTML = "<ul style=\"padding: 0; list-style-type: none;\">";
    private static final String LICENSE_LIST_END_HTML = "</ul>";
    private static final String LICENSE_LIST_ITEM_BEGIN_HTML = "<li style=\"display: block; margin: 5px;\">";
    private static final String LICENSE_LIST_ITEM_END_HTML = "</li>";
    private static final String LICENSE_NAME_BEGIN_HTML = "<a style=\"font-size: 20px; color: rgba(0,122,184,1)\" onClick=\"%s\" onmouseover=\"this.style.textDecoration='underline';\" onmouseout=\"this.style.textDecoration='none';\">";
    private static final String LICENSE_NAME_END_HTML = "</a>";
    private static final String LICENSE_TITME_HTML = "<div style=\"font-size: 2rem; color: rgba(80,80,80,1); margin-bottom: 10px;\">Libraries We Use</div>";
    private Context mContext;
    private LicenseListListener mListener;

    public interface LicenseListListener {
        void onFinish(String str);
    }

    private class LicenseLoadAsyncTask extends AsyncTask<Void, Void, String> {
        private LicenseLoadAsyncTask() {
        }

        protected String doInBackground(Void... voidArr) {
            voidArr = loadLicenseFileList();
            if (voidArr != null) {
                if (voidArr.length != 0) {
                    StringBuilder stringBuilder = new StringBuilder("");
                    stringBuilder.append(LicenseUtil.HTML_BEGIN_HTML);
                    stringBuilder.append(LicenseUtil.LICENSE_TITME_HTML);
                    stringBuilder.append(LicenseUtil.LICENSE_DESCRIPTION_HTML);
                    stringBuilder.append(LicenseUtil.LICENSE_LIST_BEGIN_HTML);
                    for (String str : voidArr) {
                        String loadLicenseFileData = loadLicenseFileData(str);
                        if (loadLicenseFileData != null) {
                            if (loadLicenseFileData.length() > 0) {
                                StringBuilder stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("var el = document.getElementById('");
                                stringBuilder2.append(str);
                                stringBuilder2.append("'); var disp = 'none'; if (el.style.display == 'none') disp = 'block'; el.style.display = disp;");
                                String stringBuilder3 = stringBuilder2.toString();
                                stringBuilder.append(LicenseUtil.LICENSE_LIST_ITEM_BEGIN_HTML);
                                stringBuilder.append(String.format(LicenseUtil.LICENSE_NAME_BEGIN_HTML, new Object[]{stringBuilder3}));
                                stringBuilder.append(str);
                                stringBuilder.append(LicenseUtil.LICENSE_NAME_END_HTML);
                                stringBuilder.append(String.format(LicenseUtil.LICENSE_DETAIL_BEGIN_HTML, new Object[]{str}));
                                stringBuilder.append(loadLicenseFileData);
                                stringBuilder.append(LicenseUtil.LICENSE_DETAIL_END_HTML);
                                stringBuilder.append(LicenseUtil.LICENSE_LIST_ITEM_END_HTML);
                            }
                        }
                    }
                    stringBuilder.append(LicenseUtil.LICENSE_LIST_END_HTML);
                    stringBuilder.append(LicenseUtil.HTML_END_HTML);
                    return stringBuilder.toString();
                }
            }
            return "";
        }

        protected void onPostExecute(String str) {
            LicenseUtil.this.mListener.onFinish(str);
        }

        private java.lang.String[] loadLicenseFileList() {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r2 = this;
            r0 = com.xumo.xumo.util.LicenseUtil.this;
            r0 = r0.mContext;
            r0 = r0.getAssets();
            r1 = "licenses";	 Catch:{ Exception -> 0x0011 }
            r0 = r0.list(r1);	 Catch:{ Exception -> 0x0011 }
            goto L_0x0012;
        L_0x0011:
            r0 = 0;
        L_0x0012:
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.util.LicenseUtil.LicenseLoadAsyncTask.loadLicenseFileList():java.lang.String[]");
        }

        private java.lang.String loadLicenseFileData(java.lang.String r6) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r5 = this;
            r0 = "";
            r1 = 0;
            r2 = com.xumo.xumo.util.LicenseUtil.this;	 Catch:{ all -> 0x0055 }
            r2 = r2.mContext;	 Catch:{ all -> 0x0055 }
            r2 = r2.getAssets();	 Catch:{ all -> 0x0055 }
            r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0055 }
            r3.<init>();	 Catch:{ all -> 0x0055 }
            r4 = "licenses/";	 Catch:{ all -> 0x0055 }
            r3.append(r4);	 Catch:{ all -> 0x0055 }
            r3.append(r6);	 Catch:{ all -> 0x0055 }
            r6 = r3.toString();	 Catch:{ all -> 0x0055 }
            r6 = r2.open(r6);	 Catch:{ all -> 0x0055 }
            r2 = new java.io.BufferedReader;	 Catch:{ all -> 0x0053 }
            r3 = new java.io.InputStreamReader;	 Catch:{ all -> 0x0053 }
            r3.<init>(r6);	 Catch:{ all -> 0x0053 }
            r2.<init>(r3);	 Catch:{ all -> 0x0053 }
        L_0x002c:
            r1 = r2.readLine();	 Catch:{ all -> 0x0050 }
            if (r1 == 0) goto L_0x0047;	 Catch:{ all -> 0x0050 }
        L_0x0032:
            r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0050 }
            r3.<init>();	 Catch:{ all -> 0x0050 }
            r3.append(r0);	 Catch:{ all -> 0x0050 }
            r3.append(r1);	 Catch:{ all -> 0x0050 }
            r0 = "</br>";	 Catch:{ all -> 0x0050 }
            r3.append(r0);	 Catch:{ all -> 0x0050 }
            r0 = r3.toString();	 Catch:{ all -> 0x0050 }
            goto L_0x002c;
        L_0x0047:
            if (r6 == 0) goto L_0x004c;
        L_0x0049:
            r6.close();	 Catch:{ Exception -> 0x0062 }
        L_0x004c:
            r2.close();	 Catch:{ Exception -> 0x0062 }
            goto L_0x0064;	 Catch:{ Exception -> 0x0062 }
        L_0x0050:
            r0 = move-exception;	 Catch:{ Exception -> 0x0062 }
            r1 = r2;	 Catch:{ Exception -> 0x0062 }
            goto L_0x0057;	 Catch:{ Exception -> 0x0062 }
        L_0x0053:
            r0 = move-exception;	 Catch:{ Exception -> 0x0062 }
            goto L_0x0057;	 Catch:{ Exception -> 0x0062 }
        L_0x0055:
            r0 = move-exception;	 Catch:{ Exception -> 0x0062 }
            r6 = r1;	 Catch:{ Exception -> 0x0062 }
        L_0x0057:
            if (r6 == 0) goto L_0x005c;	 Catch:{ Exception -> 0x0062 }
        L_0x0059:
            r6.close();	 Catch:{ Exception -> 0x0062 }
        L_0x005c:
            if (r1 == 0) goto L_0x0061;	 Catch:{ Exception -> 0x0062 }
        L_0x005e:
            r1.close();	 Catch:{ Exception -> 0x0062 }
        L_0x0061:
            throw r0;	 Catch:{ Exception -> 0x0062 }
        L_0x0062:
            r0 = "";
        L_0x0064:
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.util.LicenseUtil.LicenseLoadAsyncTask.loadLicenseFileData(java.lang.String):java.lang.String");
        }
    }

    public LicenseUtil(Context context) {
        this.mContext = context;
    }

    public void startLicenseList(LicenseListListener licenseListListener) {
        this.mListener = licenseListListener;
        new LicenseLoadAsyncTask().execute(new Void[0]);
    }
}
