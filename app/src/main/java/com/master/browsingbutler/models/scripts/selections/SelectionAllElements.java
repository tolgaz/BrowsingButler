package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;

import static com.master.browsingbutler.models.scripts.interfaces.Selectable.Type.INDICES;

public class SelectionAllElements extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_all_elements_title);
    private static int ID = 0;
    private static Type TYPE = INDICES;

    public SelectionAllElements() {
        super(title, null, ID);
    }

    @Override
    public boolean getSelection(int index, int size) {
        return true;
    }

    @Override
    public Type getType() {
        return TYPE;
    }
}
