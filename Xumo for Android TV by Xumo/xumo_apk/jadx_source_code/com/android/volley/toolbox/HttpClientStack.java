package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

@Deprecated
public class HttpClientStack implements HttpStack {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    protected final HttpClient mClient;

    public static final class HttpPatch extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "PATCH";

        public String getMethod() {
            return METHOD_NAME;
        }

        public HttpPatch(URI uri) {
            setURI(uri);
        }

        public HttpPatch(String str) {
            setURI(URI.create(str));
        }
    }

    protected void onPrepareRequest(HttpUriRequest httpUriRequest) throws IOException {
    }

    public HttpClientStack(HttpClient httpClient) {
        this.mClient = httpClient;
    }

    private static void addHeaders(HttpUriRequest httpUriRequest, Map<String, String> map) {
        for (String str : map.keySet()) {
            httpUriRequest.setHeader(str, (String) map.get(str));
        }
    }

    private static List<NameValuePair> getPostParameterPairs(Map<String, String> map) {
        List<NameValuePair> arrayList = new ArrayList(map.size());
        for (String str : map.keySet()) {
            arrayList.add(new BasicNameValuePair(str, (String) map.get(str)));
        }
        return arrayList;
    }

    public HttpResponse performRequest(Request<?> request, Map<String, String> map) throws IOException, AuthFailureError {
        HttpUriRequest createHttpRequest = createHttpRequest(request, map);
        addHeaders(createHttpRequest, map);
        addHeaders(createHttpRequest, request.getHeaders());
        onPrepareRequest(createHttpRequest);
        map = createHttpRequest.getParams();
        request = request.getTimeoutMs();
        HttpConnectionParams.setConnectionTimeout(map, 5000);
        HttpConnectionParams.setSoTimeout(map, request);
        return this.mClient.execute(createHttpRequest);
    }

    static HttpUriRequest createHttpRequest(Request<?> request, Map<String, String> map) throws AuthFailureError {
        switch (request.getMethod()) {
            case -1:
                map = request.getPostBody();
                if (map == null) {
                    return new HttpGet(request.getUrl());
                }
                HttpUriRequest httpPost = new HttpPost(request.getUrl());
                httpPost.addHeader("Content-Type", request.getPostBodyContentType());
                httpPost.setEntity(new ByteArrayEntity(map));
                return httpPost;
            case null:
                return new HttpGet(request.getUrl());
            case 1:
                map = new HttpPost(request.getUrl());
                map.addHeader("Content-Type", request.getBodyContentType());
                setEntityIfNonEmptyBody(map, request);
                return map;
            case 2:
                map = new HttpPut(request.getUrl());
                map.addHeader("Content-Type", request.getBodyContentType());
                setEntityIfNonEmptyBody(map, request);
                return map;
            case 3:
                return new HttpDelete(request.getUrl());
            case 4:
                return new HttpHead(request.getUrl());
            case 5:
                return new HttpOptions(request.getUrl());
            case 6:
                return new HttpTrace(request.getUrl());
            case 7:
                map = new HttpPatch(request.getUrl());
                map.addHeader("Content-Type", request.getBodyContentType());
                setEntityIfNonEmptyBody(map, request);
                return map;
            default:
                throw new IllegalStateException("Unknown request method.");
        }
    }

    private static void setEntityIfNonEmptyBody(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, Request<?> request) throws AuthFailureError {
        request = request.getBody();
        if (request != null) {
            httpEntityEnclosingRequestBase.setEntity(new ByteArrayEntity(request));
        }
    }
}
