package com.master.snapshotwizard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebpageRetriever extends AppCompatActivity {

    private final String URL = "https://www.nrk.no";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_webpageretriever);
        loadWebpage();
    }

    @SuppressLint("JavascriptInterface")
    private void loadWebpage() {
        Log.d(this);
        WebView webView = findViewById(R.id.webviewer);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d(this, consoleMessage.message());
                return true;
            }
        });
        WebViewClient webViewClient = new WebViewClient(URL);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(URL);


        WebSettings ws = webView.getSettings();
        webView.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void performClick(String strl){
                Log.d(this, strl);
            }
        }, "ok");

        webView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                WebView.HitTestResult hr = ((WebView)v).getHitTestResult();

                Log.d(this, "getExtra = "+ hr.getExtra() + "\t\t Type=" + hr.getType());
                return false;
            }
        });
    }

}

class WebViewClient extends android.webkit.WebViewClient {
    private String mainUrl;

    public WebViewClient(String mainUrl){
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
        view.loadUrl("javascript: "
                + "Object.prototype.each = function (fn, bind) {\n" +
                "                ;\n" +
                "                for (var i = 0; i < this.length; i++) {\n" +
                "                    if (i in this) {\n" +
                "                        fn.call(bind, this[i], i, this);\n" +
                "                    }\n" +
                "                }\n" +
                "            };\n" +
                "\n" +
                "            var _addListener = document.addEventListener || document.attachEvent,\n" +
                "                _eventClick = window.addEventListener ? 'click' : 'onclick';\n" +
                "\n" +
                "            var elements = document.getElementsByTagName(\"div\");\n" +
                "console.log(elements)\n" +

                "            elements.each(function (el) {\n" +
                "                _addListener.call(el, _eventClick, function () {\n" +
                // todo process the clicked div element
                "                    el.style.cssText = \"border-color:  black;border-style:  dashed;\"\n" +
                "                }, false);\n" +
                "            })");
    }

}

class MyJavaScriptInterface {

    private Context ctx;

    MyJavaScriptInterface(Context ctx) {
        this.ctx = ctx;
    }

    public void showHTML(String html) {
        Log.d(this, html);
        new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
                .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
    }

}

