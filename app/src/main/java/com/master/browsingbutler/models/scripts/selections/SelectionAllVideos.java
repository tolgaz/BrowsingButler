package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.models.ElementWrapper;

public class SelectionAllVideos extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_all_videos_title);
    private static int ID = 4;
    private static String MimeType = "video";
    private static String HTMLType = "video";

    public SelectionAllVideos() {
        super(title, null, ID);
    }

    @Override
    public boolean getSelection(ElementWrapper elementWrapper) {
        return super.getSelection(elementWrapper, MimeType, HTMLType);
    }
}
