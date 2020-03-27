package com.master.browsingbutler.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.master.browsingbutler.R;
import com.master.browsingbutler.components.Scripts;
import com.master.browsingbutler.models.Script;
import com.master.browsingbutler.utils.ActivityUtils;
import com.master.browsingbutler.utils.Log;

import java.net.MalformedURLException;

public class ScriptOptionsActivity extends ActivityWithSwitchHandler {

    Script script;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        this.setContentView(R.layout.activity_script_settings);
        this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        int scriptID = (int) this.getIntent().getExtras().get("SCRIPT-ID");
        this.script = Scripts.allScripts.get(scriptID);
        Log.d("ScriptOptionsActivity", this.script.toString());

        this.fillAllScriptFields(this.script);
    }

    private void fillAllScriptFields(Script script) {
        /* Name and desc */
        TextView title = this.findViewById(R.id.input_script_name);
        title.setText(script.getTitle());

        TextView desc = this.findViewById(R.id.input_script_desc);
        desc.setText(script.getDescription());

        TextWatcher titleTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                script.setTitle(s.toString());
            }
        };

        TextWatcher descriptionTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                script.setDescription(s.toString());
            }
        };

        title.addTextChangedListener(titleTextWatcher);
        desc.addTextChangedListener(descriptionTextWatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this, "onResume");
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_script_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.transitionBack(this);
    }

    @Override
    public void switchHandler(View view, int position) throws MalformedURLException {
        //
    }


}
