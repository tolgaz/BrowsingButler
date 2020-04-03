package com.master.browsingbutler.models.scripts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.master.browsingbutler.components.Scripts;
import com.master.browsingbutler.models.scripts.actions.ActionDownload;
import com.master.browsingbutler.models.scripts.actions.ScriptAction;
import com.master.browsingbutler.models.scripts.selections.ScriptSelection;
import com.master.browsingbutler.utils.ActivityUtils;
import com.master.browsingbutler.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class Script {

    private String title;
    private String description;
    private boolean isPremade;
    private int ID;
    private List<ScriptAction> actions = new ArrayList<>();
    private List<ScriptSelection> selections = new ArrayList<>();

    private boolean saved = false;
    private transient AppCompatActivity activity = null;

    public Script(String title, String description, boolean isPremade) {
        this.title = title;
        this.description = description;
        this.isPremade = isPremade;
        this.ID = Scripts.getAllScripts().size();
    }

    public Script(String title, String description) {
        this(title, description, false);
    }

    public Script() {
        this(null, null);
    }

    public Script(String title, String description, boolean isPremade, List<ScriptAction> actions, List<ScriptSelection> selections) {
        this(title, description, isPremade);
        this.actions = actions;
        this.selections = selections;
    }

    public void startExecution(AppCompatActivity activity) {
        Log.d(this, "Executing script: " + this.toString());
        this.saved = false;
        this.activity = activity;
        this.actions.forEach(action -> {
            if (action.getID() == ActionDownload.getStaticID()) {
                this.saved = true;
            }
            action.execute(this);
        });
        ActivityUtils.displayToast("Script applied successfully");
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPremade() {
        return this.isPremade;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setActions(List<ScriptAction> actions) {
        this.actions = actions;
    }

    public List<ScriptAction> getActions() {
        return this.actions;
    }

    public List<ScriptSelection> getSelections() {
        return this.selections;
    }

    public void setSelections(List<ScriptSelection> selections) {
        this.selections = selections;
    }

    public AppCompatActivity getActivity() {
        return this.activity;
    }

    public boolean isSaved() {
        return this.saved;
    }

    public enum Type {
        PREMADE, CUSTOM
    }

    public enum Option {
        ACTION, SELECTION
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SCRIPT. ID: ").append(this.ID).append(". Title: ").append(this.title).append(", description: ").append(this.description)
                .append(". actions: ").append(this.actions.size()).append(". selections: ").append(this.selections.size());
        builder.append("\nAction info: \n");
        this.getActions().forEach(action -> builder.append(action.toString()).append("\n"));
        builder.append("\nSelection info: \n");
        this.getSelections().forEach(selection -> builder.append(selection.toString()).append("\n"));
        return String.valueOf(builder);
    }
}
