package com.master.browsingbutler.utils;

import com.master.browsingbutler.components.JavaScriptInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    private static final String TAG = "FileUtils";

    public static String getExtensionFromSrc(String name) {
        String filenameWithextension = name.substring(name.lastIndexOf('/') + 1);
        return filenameWithextension.substring(filenameWithextension.lastIndexOf('.'));
    }

    public static String getFilenameFromSrc(String name) {
        String filenameWithextension = name.substring(name.lastIndexOf('/') + 1);
        return filenameWithextension.substring(0, filenameWithextension.lastIndexOf('.'));
    }

    public static File createZIPFromDownloadedFiles() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd-HH.mm", Locale.ENGLISH).format(Calendar.getInstance().getTime());
        String zipFilename = ElementGrabber.BROWSING_BUTLER_FOLDER + "/" + timeStamp + ".zip";
        try {
            final ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFilename));
            JavaScriptInterface.getSelectedElements().forEach(elementWrapper -> {
                File file = elementWrapper.getFile();
                try {
                    outputStream.putNextEntry(new ZipEntry(file.toPath().getFileName().toString()));
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    outputStream.write(bytes, 0, bytes.length);
                    outputStream.closeEntry();
                } catch (IOException e) {
                    Log.d(TAG, Arrays.toString(e.getStackTrace()));
                }
            });
            outputStream.close();
            return new File(zipFilename);
        } catch (IOException e) {
            Log.d(TAG, Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
