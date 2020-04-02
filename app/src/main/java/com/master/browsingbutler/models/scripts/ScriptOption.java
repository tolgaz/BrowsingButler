package com.master.browsingbutler.models.scripts;

public interface ScriptOption {
    Script.Option getOptionType();

    ScriptOption clone(ScriptOption scriptOption);

    String getTitle();

    String getDescription();

    int getID();
}
