package com.master.browsingbutler.models.scripts.interfaces;

import com.master.browsingbutler.models.ElementWrapper;

public interface Selectable {
    boolean getSelection(int index, int size);

    boolean getSelection(ElementWrapper elementWrapper);

    Type getType();

    enum Type {
        INDICES,
        FILETYPES,
    }
}