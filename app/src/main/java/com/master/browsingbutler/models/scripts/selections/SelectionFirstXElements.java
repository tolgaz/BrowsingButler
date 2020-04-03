package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.models.scripts.Script;

public class SelectionFirstXElements extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_first_x_elements_title);
    private static int ID = 3;

    public SelectionFirstXElements() {
        super(title, null, ID);
    }

    @Override
    public void execute(Script script) {
        super.execute(script);
    }
}
