package com.master.snapshotwizard.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.utils.Log;

import java.net.MalformedURLException;

public class CompressResizeActivity extends ActivityWithSwitchHandler {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_compressresize);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this, "onResume");
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_compress_resize_screen);
        WebpageRetrieverActivity.configuration.configureList(this, "CompressResize");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void switchHandler(View view, int position) throws MalformedURLException {
        Log.d(this, "switchHandler");
        Log.d(this, "switchHandler done");
    }
}
