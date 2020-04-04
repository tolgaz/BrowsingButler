package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;

import java.util.Collections;
import java.util.List;

public class SelectionFirstElement extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_first_elements_title);
    private static int ID = 1;

    SelectionFirstElement() {
        super(title, null, ID);
    }

    @Override
    public List<Integer> getSelection(int length) {
        return Collections.singletonList(0);
    }
}
