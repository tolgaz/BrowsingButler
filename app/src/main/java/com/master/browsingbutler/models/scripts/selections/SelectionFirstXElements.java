package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;

public class SelectionFirstXElements extends ScriptSelection {
    private static String title = App.getResourses().getString(R.string.script_selection_first_x_elements_title);

    public SelectionFirstXElements() {
        super(title, null);
    }
}
