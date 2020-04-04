package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class SelectionAllElements extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_all_elements_title);
    private static int ID = 0;

    public SelectionAllElements() {
        super(title, null, ID);
    }

    @Override
    public List<Integer> getSelection(int length) {
        return IntStream.range(0, length).boxed().collect(toList());
    }
}
