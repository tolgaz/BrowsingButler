package com.master.browsingbutler.models.scripts.interfaces;

import com.master.browsingbutler.models.ElementWrapper;

import java.util.List;

public interface Selectable {
    List<Integer> getSelection(int length);

    boolean getSelection(ElementWrapper elementWrapper);
}