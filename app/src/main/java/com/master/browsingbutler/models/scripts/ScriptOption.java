package com.master.browsingbutler.models.scripts;

public interface ScriptOption {
    Script.Option getOptionType();

    String getTitle();

    String getDescription();

    int getID();

}
