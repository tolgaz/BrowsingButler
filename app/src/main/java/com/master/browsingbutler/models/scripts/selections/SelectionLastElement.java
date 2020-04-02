package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;

public class SelectionLastElement extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_last_elements_title);
    private static int ID = 2;

    public SelectionLastElement() {
        super(title, null, ID);
    }

    @Override
    public void execute() {
        super.execute();
    }
}
