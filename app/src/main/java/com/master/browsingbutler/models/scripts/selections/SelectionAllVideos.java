package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.models.ElementWrapper;

import static com.master.browsingbutler.models.scripts.interfaces.Selectable.Type.FILETYPES;

public class SelectionAllVideos extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_all_videos_title);
    private static int ID = 4;
    private static String MimeType = "video";
    private static String HTMLType = "video";
    private static Type TYPE = FILETYPES;

    public SelectionAllVideos() {
        super(title, null, ID);
    }

    @Override
    public boolean getSelection(ElementWrapper elementWrapper) {
        return super.getSelection(elementWrapper, MimeType, HTMLType);
    }

    @Override
    public Type getType() {
        return TYPE;
    }
}
