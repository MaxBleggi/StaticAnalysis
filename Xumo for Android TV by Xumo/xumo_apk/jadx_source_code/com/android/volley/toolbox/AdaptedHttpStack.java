package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;

class AdaptedHttpStack extends BaseHttpStack {
    private final HttpStack mHttpStack;

    AdaptedHttpStack(HttpStack httpStack) {
        this.mHttpStack = httpStack;
    }

    public HttpResponse executeRequest(Request<?> request, Map<String, String> map) throws IOException, AuthFailureError {
        try {
            request = this.mHttpStack.performRequest(request, map);
            map = request.getStatusLine().getStatusCode();
            Header[] allHeaders = request.getAllHeaders();
            List arrayList = new ArrayList(allHeaders.length);
            for (Header header : allHeaders) {
                arrayList.add(new com.android.volley.Header(header.getName(), header.getValue()));
            }
            if (request.getEntity() == null) {
                return new HttpResponse(map, arrayList);
            }
            long contentLength = request.getEntity().getContentLength();
            if (((long) ((int) contentLength)) == contentLength) {
                return new HttpResponse(map, arrayList, (int) request.getEntity().getContentLength(), request.getEntity().getContent());
            }
            map = new StringBuilder();
            map.append("Response too large: ");
            map.append(contentLength);
            throw new IOException(map.toString());
        } catch (Request<?> request2) {
            throw new SocketTimeoutException(request2.getMessage());
        }
    }
}
