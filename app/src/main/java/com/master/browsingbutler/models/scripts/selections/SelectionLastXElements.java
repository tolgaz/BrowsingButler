package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;

public class SelectionLastXElements extends ScriptSelection {
    private static String title = App.getResourses().getString(R.string.script_selection_last_x_elements_title);

    public SelectionLastXElements() {
        super(title, null);
    }

    @Override
    public void execute() {
        super.execute();
    }
}
