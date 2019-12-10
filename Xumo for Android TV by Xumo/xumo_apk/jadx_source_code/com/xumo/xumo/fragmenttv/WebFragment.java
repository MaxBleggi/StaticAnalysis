package com.xumo.xumo.fragmenttv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import com.xumo.xumo.tv.R;

public class WebFragment extends BaseFragment {
    public static final String TAG_WEB = "com.xumo.xumo.fragmenttv.WebFragment";
    private WebView mWebView;

    public WebView getWebView() {
        return this.mWebView;
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        return layoutInflater.inflate(R.layout.fragment_web, viewGroup, false);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mWebView = (WebView) view.findViewById(R.id.web_view);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebView.requestFocus();
        this.mWebView.setWebViewClient(new WebViewClient());
        this.mWebView.loadUrl("file:///android_asset/licenses.html");
    }
}
