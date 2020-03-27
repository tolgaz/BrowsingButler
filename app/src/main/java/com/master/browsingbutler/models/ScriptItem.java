package com.master.browsingbutler.models;

public class ScriptItem extends ListItem {

    private Script script;

    public ScriptItem(String title, String description, Script script) {
        super(title, description);
        this.script = script;
    }

    public Script getScript() {
        return this.script;
    }

    public void setScript(Script script) {
        this.script = script;
    }
}
