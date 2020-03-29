package com.master.browsingbutler.models;

import java.util.List;

public class ScriptSelection implements ScriptOption {
    private static int selectionCounter = 0;
    private int ID;
    private static List<ScriptSelection> scriptSelections;
    private String title;
    private String description;

    public ScriptSelection(String title) {
        this.title = title;
        this.description = null;
        this.ID = selectionCounter++;
    }

    public ScriptSelection(String title, String description) {
        this(title);
        this.description = description;
    }

    public static List<ScriptSelection> getScriptSelections() {
        return scriptSelections;
    }

    public static void setScriptSelections(List<ScriptSelection> scriptSelections) {
        ScriptSelection.scriptSelections = scriptSelections;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Script.Option getOptionType() {
        return Script.Option.SELECTION;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}

