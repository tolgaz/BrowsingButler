package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;

public class SelectionFirstElement extends ScriptSelection {
    private static String title = App.getResourses().getString(R.string.script_selection_first_elements_title);

    public SelectionFirstElement() {
        super(title, null);
    }

    @Override
    public void execute() {
        super.execute();
    }
}
