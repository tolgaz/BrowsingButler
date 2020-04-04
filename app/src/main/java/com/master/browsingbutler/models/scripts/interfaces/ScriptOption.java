package com.master.browsingbutler.models.scripts.interfaces;

import com.master.browsingbutler.models.scripts.Script;

public interface ScriptOption {
    Script.Option getOptionType();

    ScriptOption clone(ScriptOption scriptOption);

    String getTitle();

    String getDescription();

    int getID();
}
