package com.master.snapshotwizard.components;

import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.master.snapshotwizard.utils.Log;

public class WebViewClient extends android.webkit.WebViewClient {
    private String mainUrl;

    public WebViewClient(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.d(this, "MainURL: " + mainUrl + ", request: " + request.getUrl());
        String url = request.getUrl().toString();
        if (url.equals(mainUrl)) {
            view.loadUrl(url);
        }
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        String js = "javascript: " +
                /* Adds border around clicked elem */
                "        document.addEventListener(\'click\', function (e) {\n"+
                "           e.stopPropagation();\n"+
                "           e = e || window.event;\n" +
                "           var target = e.target || e.srcElement; \n" +
                            /* Set target.style preemptively */
                "           target.style = \"border-color: red; border-style: solid; border-width: 2px; box-sizing: border-box;\" \n" +
                "           const processedValue = JSInterface.processSelectedElement(target.outerHTML); \n" +
                "           if(!processedValue) target.style = \"border-color: transparent; border-width: 0; \" " +
                "        }, true); \n";

        view.evaluateJavascript(js, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.d(this, "value:  " + value);
            }
        });
    }
}