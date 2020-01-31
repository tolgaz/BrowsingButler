package com.master.snapshotwizard.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.utils.Configuration;
import com.master.snapshotwizard.utils.Log;

public class OperationActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_operation);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_operation_screen);
        WebpageRetrieverActivity.configuration.configureList(this, "Operations");
  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
}
