package com.master.browsingbutler.utils;

public class FileUtils {
    public static String getExtensionFromSrc(String name) {
        String filenameWithextension = name.substring(name.lastIndexOf('/') + 1);
        return filenameWithextension.substring(filenameWithextension.lastIndexOf('.'));
    }

    public static String getFilenameFromSrc(String name) {
        String filenameWithextension = name.substring(name.lastIndexOf('/') + 1);
        return filenameWithextension.substring(0, filenameWithextension.lastIndexOf('.'));
    }
}
