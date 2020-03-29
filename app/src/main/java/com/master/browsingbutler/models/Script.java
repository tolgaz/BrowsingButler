package com.master.browsingbutler.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Script {

    public enum Type {
        PREMADE, CUSTOM
    }

    public enum Option {
        ACTION, SELECTION
    }

    private static int scriptCounter = 0;

    private String title;
    private String description;
    private boolean isPremade;
    private int ID;
    private List<ScriptAction> actions = new ArrayList<>();
    private List<ScriptSelection> selections = new ArrayList<>();

    public Script(String title, String description, boolean isPremade) {
        this.title = title;
        this.description = description;
        this.isPremade = isPremade;
        this.ID = scriptCounter++;
    }

    public Script(String title, String description) {
        this(title, description, false);
    }

    public Script() {
        this(null, null);
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

    @NonNull
    @Override
    public String toString() {
        return "SCRIPT. Title: " + this.title + ", description: " + this.description + ". actions: " + this.actions.size() + ". selections: " + this.selections.size();
    }
}
