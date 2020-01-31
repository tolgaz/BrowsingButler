package com.master.snapshotwizard.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.components.JavaScriptInterface;
import com.master.snapshotwizard.components.WebViewClient;
import com.master.snapshotwizard.utils.Configuration;
import com.master.snapshotwizard.utils.Log;

public class WebpageRetrieverActivity extends AppCompatActivity {
    /* TODO: Grab from intent */
    private final String URL = "https://no.pinterest.com";
    public static Configuration configuration = new Configuration();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_webpageretriever);
        configuration.configureToolbar(this, R.string.toolbar_main);
        loadWebpage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
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
        webView.addJavascriptInterface(new JavaScriptInterface(this), "JSInterface");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d(this, "console.log(): Line: " + consoleMessage.lineNumber() + ", message: " + consoleMessage.message());
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

    /* Button operations */
    public void startOperationActivity(View view) {
        startActivity(new Intent(this, OperationActivity.class));
    }

    public void makeMagicWandButtonVisible(){
        Log.d(this, "turning button visible");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.magic_button).setVisibility(View.VISIBLE);
            }
        });
    }

    public void makeMagicWandButtonInvisible(){
        Log.d(this, "turning button INvisible");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.magic_button).setVisibility(View.INVISIBLE);
            }
        });
    }
    /* Button opeartions */
}





