package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;

public class SelectionAllElements extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_all_elements_title);
    private static int ID = 0;

    public SelectionAllElements() {
        super(title, null, ID);
    }

    @Override
    public void execute() {
        super.execute();
    }
}
