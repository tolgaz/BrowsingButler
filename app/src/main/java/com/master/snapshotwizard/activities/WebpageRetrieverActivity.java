package com.master.snapshotwizard.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.components.JavaScriptInterface;
import com.master.snapshotwizard.components.WebViewClient;
import com.master.snapshotwizard.components.Configuration;
import com.master.snapshotwizard.utils.Log;

public class WebpageRetrieverActivity extends AppCompatActivity {
    /* TODO: Grab from intent */
    public static String URL = null;
    public static Configuration configuration = new Configuration();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        Bundle intentExtras = getIntent().getExtras();
        if(intentExtras  != null) URL = intentExtras.getString("android.intent.extra.TEXT");
        if (URL == null) URL = getResources().getString(R.string.main_url);

        setContentView(R.layout.activity_webpageretriever);
        ImageButton magicButton = findViewById(R.id.magic_button);
        magicButton.setOnClickListener(v -> startOperationActivity());

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
    public void startOperationActivity() {
        startActivity(new Intent(this, OperationActivity.class));
    }

    public void makeMagicWandButtonVisible(){
        Log.d(this, "turning button visible");
        runOnUiThread(() -> findViewById(R.id.magic_button).setVisibility(View.VISIBLE));
    }

    public void makeMagicWandButtonInvisible(){
        Log.d(this, "turning button INvisible");
        runOnUiThread(() -> findViewById(R.id.magic_button).setVisibility(View.INVISIBLE));
    }
    /* Button opeartions */
}





