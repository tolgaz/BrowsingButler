package com.master.browsingbutler.models;

import java.util.List;

public class ScriptAction extends ScriptOption {
    private static List<ScriptOption> scriptActions;
    private String title;
    private String description;

    public ScriptAction(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static List<ScriptOption> getScriptActions() {
        return scriptActions;
    }

    public static void setScriptActions(List<ScriptOption> scriptActions) {
        ScriptAction.scriptActions = scriptActions;
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

    @Override
    public Script.Option getOptionType() {
        return Script.Option.ACTION;
    }
}
