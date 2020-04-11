package com.master.browsingbutler.activities;

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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.master.browsingbutler.R;
import com.master.browsingbutler.components.Configuration;
import com.master.browsingbutler.components.JavaScriptInterface;
import com.master.browsingbutler.components.Scripts;
import com.master.browsingbutler.components.WebViewClient;
import com.master.browsingbutler.utils.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class WebpageRetrieverActivity extends AppCompatActivity {
    /* TODO: Grab from intent */
    public static String URL = null;
    public static Configuration configuration = new Configuration();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        this.setContentView(R.layout.activity_webpageretriever);
        if (this.evaluateIntentExtras()) {
            return;
        }

        ImageButton magicButton = this.findViewById(R.id.magic_button);
        magicButton.setOnClickListener(v -> this.startOperationActivity());

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Scripts.initScripts();
        this.loadWebpage();
    }

    private boolean evaluateIntentExtras() {
        Bundle intentExtras = this.getIntent().getExtras();
        if (intentExtras != null) {
            URL = intentExtras.getString("android.intent.extra.TEXT");
            try {
                // Invalid URL
                new URL(URL);
            } catch (MalformedURLException e) {
                this.setError(this.getString(R.string.error_invalid_url));
                return true;
            }
        } else {
            // URL = this.getResources().getString(R.string.main_url);
            this.setError(this.getString(R.string.error_no_url));
            return true;
        }
        JavaScriptInterface.resetSelectedElements();
        return false;
    }

    private void setError(String message) {
        this.setContentView(R.layout.custom_webpageretriever);
        TextView errorMessage = this.findViewById(R.id.textview_webpageretriever);
        errorMessage.setText(message);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this);
        OperationActivity.firstRun = true;
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_main);
        this.evaluateIntentExtras();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @SuppressLint("JavascriptInterface")
    private void loadWebpage() {
        Log.d(this);
        WebView webView = this.findViewById(R.id.webviewer);
        this.createAndConfigureWebView(webView);
        webView.loadUrl(URL);
    }

    private void createAndConfigureWebView(WebView webView) {
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
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                WebView.HitTestResult hr = ((WebView) v).getHitTestResult();
                Log.d(this, "getExtra = " + hr.getExtra() + "\t\t Type=" + hr.getType());
                return false;
            }
        });
    }

    /* Button operations */
    public void startOperationActivity() {
        this.startActivity(new Intent(this, OperationActivity.class));
    }

    public void makeMagicWandButtonVisible() {
        Log.d(this, "turning button visible");
        this.runOnUiThread(() -> this.findViewById(R.id.magic_button).setVisibility(View.VISIBLE));
    }

    public void makeMagicWandButtonInvisible() {
        Log.d(this, "turning button INvisible");
        this.runOnUiThread(() -> this.findViewById(R.id.magic_button).setVisibility(View.INVISIBLE));
    }
    /* Button opeartions */

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}





