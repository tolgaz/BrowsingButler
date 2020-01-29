package com.master.snapshotwizard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebpageRetriever extends AppCompatActivity {

    /* TODO: Grab from intent */
    private final String URL = "https://no.pinterest.com";

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
        createAndConfigureWebView(webView);
        webView.loadUrl(URL);
    }

    private void createAndConfigureWebView(WebView webView){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient(URL));
        webView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d(this, "console.log(): " + consoleMessage.message());
                return true;
            }
        });

        webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                WebView.HitTestResult hr = ((WebView) v).getHitTestResult();
                Log.d(this, "getExtra = " + hr.getExtra() + "\t\t Type=" + hr.getType());
                return false;
            }
        });

    }

}

class WebViewClient extends android.webkit.WebViewClient {
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
                "           var target = e.target || e.srcElement, text = target.textContent || target.innerText; \n" +
                "                    target.style = \"border-color: red; border-style: solid; border-width: 2; box-sizing: border-box;\"\n" +
                "           console.log( target.outerHTML); \n" +
                "        }, true); \n";


                /* For pinterest, stops propagation when image is clicked */
        view.evaluateJavascript(js, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.d(this, "value:  " + value);
            }
        });

    }

    public void onData(String value) {
        //.. do something with the data
    }


}

class MyJavaScriptInterface {

    private Context ctx;

    MyJavaScriptInterface(Context ctx) {
        this.ctx = ctx;
    }

    public void showHTML(String html) {
        Log.d(this, "showHTML: "+ html);
        new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
                .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
    }

}

