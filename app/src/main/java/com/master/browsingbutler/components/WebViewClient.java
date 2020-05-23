package com.master.browsingbutler.components;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.master.browsingbutler.utils.Log;

public class WebViewClient extends android.webkit.WebViewClient {
    private String mainUrl;

    public WebViewClient(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.d(this, "MainURL: " + this.mainUrl + ", request: " + request.getUrl());
        Uri uri = request.getUrl();
        String url = this.removeKeywords(uri.toString());
        return !url.equals(this.mainUrl);
    }

    private String removeKeywords(String url) {
        return url.replace("//m.", "//");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        String js = "javascript: " +
                /* Adds border around clicked elem */
                "        document.addEventListener(\'click\', function (e) {\n" +
                "                   console.log(\"IN EVENT LISTENER!!\");\n" +
                "           e = e || window.event;\n" +
                "           var target = e.target || e.srcElement; \n" +
                /* Set target.style preemptively */
                "           if(!(target.outerHTML.onclick && target.outerHTML.onclick.includes(\"setAndSaveAllConsent\")))  {\n" +
                "                   e.preventDefault();\n" +
                /* IF video wwe also have disable propagation to the controls */
                "               if (target.outerHTML.includes(\"video\"))   {\n" +
                "                   e.stopPropagation();\n" +
                "               } \n" +
                "           }\n" +
                "           target.style = \"border-color: red; border-style: solid; border-width: 3px; box-sizing: border-box;\" \n" +
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