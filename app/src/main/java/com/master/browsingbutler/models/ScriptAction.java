package com.master.browsingbutler.models;

import java.util.List;

public class ScriptAction implements ScriptOption {
    private static int actionCounter = 0;
    private int ID;
    private static List<ScriptAction> scriptActions;
    private String title;
    private String description;

    public ScriptAction(String title, String description) {
        this.title = title;
        this.description = description;
        this.ID = actionCounter++;
    }

    public static List<ScriptAction> getScriptActions() {
        return scriptActions;
    }

    public static void setScriptActions(List<ScriptAction> scriptActions) {
        ScriptAction.scriptActions = scriptActions;
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
        return Script.Option.ACTION;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
