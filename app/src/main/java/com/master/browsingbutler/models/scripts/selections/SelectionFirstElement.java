package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;

import static com.master.browsingbutler.models.scripts.interfaces.Selectable.Type.INDICES;

public class SelectionFirstElement extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_first_elements_title);
    private static int ID = 1;
    private static Type TYPE = INDICES;

    SelectionFirstElement() {
        super(title, null, ID);
    }

    @Override
    public boolean getSelection(int index, int size) {
        return index == 0;
    }

    @Override
    public Type getType() {
        return TYPE;
    }
}
