package com.master.snapshotwizard.utils;

import android.os.Environment;

import com.master.snapshotwizard.components.ElementWrapper;
import com.master.snapshotwizard.components.JavaScriptInterface;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ElementGrabber {
    private static String TAG = "ElementGrabber";

    public static void grabElements() {
        ArrayList<ElementWrapper> elements = JavaScriptInterface.selectedElements;
        getImages(elements);
    }

    private static void getImages(ArrayList<ElementWrapper> elements) {
        Log.d(TAG, "getImages");
        ArrayList<String> sources = getSourceLinks(elements);
        File folder = createDirectory();

        for (final String src : sources) {
            //Open a URL Stream
            try (InputStream inputStream = new URL(src).openStream()) {
                String name = getFileNameFromSrc(src);
                createNewFile(folder, name);
                writeToFile(inputStream, folder, name);
            } catch (IOException e) {
                Log.d(TAG, Arrays.toString(e.getStackTrace()));
            }
        }
    }

    private static void writeToFile(InputStream inputStream, File folder, String name) {
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(folder.getAbsolutePath() + name))) {
            for (int b; (b = inputStream.read()) != -1; ) {
                out.write(b);
            }
        } catch (Exception e) {
            Log.d(TAG, Arrays.toString(e.getStackTrace()));
        }
    }

    private static void createNewFile(File folder, String name) throws IOException {
        File file = new File(folder, name);
        if (!file.createNewFile()) {
            Log.d(TAG, "createNewFile, File already exists!");
        }
    }

    private static String getFileNameFromSrc(String src) {
        Log.d(TAG, "getFileNameFromSrc started");
        int indexname = "/".lastIndexOf(src);
        String newSrc = src;
        /* If the file ends iwth  / */
        if (indexname == src.length()) {
            newSrc = src.substring(1, indexname);
        }
        indexname = "/".lastIndexOf(newSrc);
        Log.d(TAG, "getFileNameFromSrc ended");
        return newSrc.substring(indexname);
    }

    private static File createDirectory() {
        Log.d(TAG, "createFileAndDirectory started");
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/SnapshotWizard/");
        if (!folder.mkdirs()) Log.d("ElementGrabber", "mkDir faileD");
        Log.d(TAG, "createFileAndDirectory ended");
        return folder;
    }

    private static ArrayList<String> getSourceLinks(ArrayList<ElementWrapper> elements) {
        Log.d(TAG, "getSourceLinks started");
        ArrayList<String> sources = new ArrayList<>();
        for (ElementWrapper elementWrapper : elements) {
            sources.add(elementWrapper.getElement().attr("src"));
            Log.d(TAG, "inLoop" + elementWrapper);
        }
        Log.d(TAG, "getSourceLinks ended");
        return sources;
    }
}
