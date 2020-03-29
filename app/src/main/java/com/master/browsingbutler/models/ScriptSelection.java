package com.master.browsingbutler.models;

import java.util.List;

public class ScriptSelection extends ScriptOption {
    private static List<ScriptOption> scriptSelections;
    private String title;
    private String description;

    public ScriptSelection(String title) {
        this.title = title;
        this.description = null;
    }

    public ScriptSelection(String title, String description) {
        this(title);
        this.description = description;
    }

    public static List<ScriptOption> getScriptSelections() {
        return scriptSelections;
    }

    public static void setScriptSelections(List<ScriptOption> scriptSelections) {
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
}

