package com.master.browsingbutler.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.master.browsingbutler.R;
import com.master.browsingbutler.components.MultiSpinner;
import com.master.browsingbutler.components.MultiSpinner.MultiSpinnerListener;
import com.master.browsingbutler.components.Scripts;
import com.master.browsingbutler.models.Script;
import com.master.browsingbutler.models.ScriptAction;
import com.master.browsingbutler.models.ScriptOption;
import com.master.browsingbutler.models.ScriptSelection;
import com.master.browsingbutler.utils.ActivityUtils;
import com.master.browsingbutler.utils.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScriptOptionsActivity extends AppCompatActivity implements MultiSpinnerListener {

    Script script;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        this.setContentView(R.layout.activity_script_settings);
        this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_script_screen);

        int scriptID = (int) this.getIntent().getExtras().get("SCRIPT-ID");
        this.script = Scripts.getAllScripts().get(scriptID);
        Log.d("ScriptOptionsActivity", this.script.toString());
        this.fillAllScriptFields();

        if (ScriptAction.getScriptActions() == null) this.initScriptActions();
        if (ScriptSelection.getScriptSelections() == null) this.initScriptSelections();

        /* text view config */
        this.configureOptionTextView(this.script.getActions(), R.id.script_actions);
        this.configureOptionTextView(this.script.getSelections(), R.id.script_selections);

        /* spinner actions and selection config */
        MultiSpinner actionSpinner = this.findViewById(R.id.script_action_spinner);
        actionSpinner.setItems(ScriptAction.getScriptActions(), this.script.getActions(), Script.Option.ACTION, this);

        MultiSpinner selectionSpinner = this.findViewById(R.id.script_selection_spinner);
        selectionSpinner.setItems(ScriptSelection.getScriptSelections(), this.script.getSelections(), Script.Option.SELECTION, this);

        ImageButton scriptActionButton = this.findViewById(R.id.script_action_button);
        ImageButton scriptSelectionButton = this.findViewById(R.id.script_selection_button);

        if (this.script.isPremade()) {
            scriptActionButton.setVisibility(View.GONE);
            scriptSelectionButton.setVisibility(View.GONE);
        } else {
            scriptActionButton.setOnClickListener(v -> actionSpinner.performClick());
            scriptSelectionButton.setOnClickListener(v -> selectionSpinner.performClick());
        }
    }

    private void configureOptionTextView(List<ScriptOption> options, int textViewId) {
        TextView textView = this.findViewById(textViewId);

        if (options.isEmpty()) {
            textView.setText(null);
        } else {
            textView.setTextColor(Color.BLACK);
            StringBuilder stringListOfOptions = new StringBuilder();
            options.forEach(option -> stringListOfOptions.append(option.getTitle()).append(". "));
            textView.setText(stringListOfOptions);
            textView.setMaxLines(3);
        }

        if (this.script.isPremade()) {
            textView.setInputType(EditorInfo.TYPE_NULL);
            textView.setEnabled(false);
        }
    }


    private void fillAllScriptFields() {
        /* Name and desc */
        EditText title = this.findViewById(R.id.input_script_name);
        title.setText(this.script.getTitle());

        EditText desc = this.findViewById(R.id.input_script_desc);
        desc.setText(this.script.getDescription());

        if (this.script.isPremade()) {
            title.setInputType(EditorInfo.TYPE_NULL);
            title.setEnabled(false);

            desc.setInputType(EditorInfo.TYPE_NULL);
            desc.setEnabled(false);

            return;
        }
        this.createAndAddTextWatchers(title, desc);
    }

    private void createAndAddTextWatchers(TextView title, TextView desc) {
        TextWatcher titleTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ScriptOptionsActivity.this.script.setTitle(s.toString());
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
                ScriptOptionsActivity.this.script.setDescription(s.toString());
            }
        };

        title.addTextChangedListener(titleTextWatcher);
        desc.addTextChangedListener(descriptionTextWatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this, "onResume");
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
    public void onItemsSelected(boolean[] selected, Script.Option optionType) {
        Log.d("spinner onitemselectd", Arrays.toString(selected));

        ArrayList<ScriptOption> actuallySelectedOptions = new ArrayList<>();
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                actuallySelectedOptions.add(optionType == Script.Option.ACTION ? ScriptAction.getScriptActions().get(i) : ScriptSelection.getScriptSelections().get(i));
            }
        }

        if (optionType == Script.Option.ACTION) {
            this.script.setActions(actuallySelectedOptions);
            this.configureOptionTextView(this.script.getActions(), R.id.script_actions);
        } else {
            this.script.setSelections(actuallySelectedOptions);
            this.configureOptionTextView(this.script.getSelections(), R.id.script_selections);
        }
    }

    private void initScriptActions() {
        ArrayList<ScriptOption> scriptActions = new ArrayList<>();
        scriptActions.add(new ScriptAction(this.getString(R.string.script_action_download_title), this.getString(R.string.script_action_download_desc)));
        scriptActions.add(new ScriptAction(this.getString(R.string.script_action_compress_title), this.getString(R.string.script_action_compress_desc)));
        scriptActions.add(new ScriptAction(this.getString(R.string.script_action_file_creator_title), this.getString(R.string.script_action_file_creator_desc)));
        scriptActions.add(new ScriptAction(this.getString(R.string.script_action_share_title), this.getString(R.string.script_action_share_desc)));
        ScriptAction.setScriptActions(scriptActions);
    }

    private void initScriptSelections() {
        ArrayList<ScriptOption> scriptSelections = new ArrayList<>();
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_all_elements_title)));
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_first_elements_title)));
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_last_elements_title)));
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_first_x_elements_title)));
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_last_x_elements_title)));
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_all_pictures_title)));
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_all_text_title)));
        ScriptSelection.setScriptSelections(scriptSelections);
    }
}
