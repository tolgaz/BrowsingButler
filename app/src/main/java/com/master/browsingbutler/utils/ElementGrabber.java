package com.master.browsingbutler.utils;

import android.os.Environment;
import android.widget.Toast;

import com.master.browsingbutler.App;
import com.master.browsingbutler.activities.WebpageRetrieverActivity;
import com.master.browsingbutler.components.JavaScriptInterface;
import com.master.browsingbutler.models.ElementWrapper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ElementGrabber {
    private static String TAG = "ElementGrabber";
    static File BROWSING_BUTLER_FOLDER = null;

    public static void grabElements(boolean script) {
        List<ElementWrapper> elements = JavaScriptInterface.getSelectedElements();
        if (script) {
            elements = elements.stream().filter(ElementWrapper::getSatisfiesSelection).collect(Collectors.toList());
        }
        try {
            getElements(elements);
        } catch (MalformedURLException e) {
            Log.d(TAG, Arrays.toString(e.getStackTrace()));
        }
    }

    private static void getElements(List<ElementWrapper> elements) throws MalformedURLException {
        Log.d(TAG, "getElements");
        File folder = createDirectory();

        for (final ElementWrapper elementWrapper : elements) {
            if (!elementWrapper.isNotText()) {
                String name = UUID.randomUUID().toString() + ".txt";
                File newFile = createAndSetFileInElementWrapper(elementWrapper, folder, name);
                if (newFile != null) {
                    writeToFile(elementWrapper, folder, name);
                }
            } else {
                //Open a URL Stream
            /*
                Imgur links are like: https://i.imgur.com/yj9Xp7Q_d.jpg?maxwidth=520&shape=thumb&fidelity=high
                We want to remove the query params. And trim the _d from the end of the url.
             */
                URL trimmedURL = trimURL(elementWrapper);
                try (InputStream inputStream = trimmedURL.openStream()) {
                    String name = getFilenameFromSrc(trimmedURL.getPath(), true);
                    if (name != null) {
                        File file = createAndSetFileInElementWrapper(elementWrapper, folder, name);
                        if (file != null) {
                            writeToFile(inputStream, folder, name);
                        }
                    }
                } catch (IOException e) {
                    Log.d(TAG, Arrays.toString(e.getStackTrace()));
                }
            }
        }
    }

    private static File createAndSetFileInElementWrapper(ElementWrapper elementWrapper, File folder, String name) {
        try {
            /* if newFile is null it already esxists */
            File file = new File(folder, name);

            if (elementWrapper.getFile() == null) {
                elementWrapper.setFile(file);
            }

            if (!file.createNewFile()) {
                Log.d(TAG, "createNewFile, File already exists!");
                return null;
            }
            return file;
        } catch (IOException e) {
            Log.d(TAG, Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public static URL trimURL(ElementWrapper elementWrapper) throws MalformedURLException {
        String src = getSourceLink(elementWrapper);
        /* If URL contains host and everything we are good, else we can try adding it on */
        URL url;
        try {
            url = new URL(src);
        } catch (MalformedURLException e) {
            src = WebpageRetrieverActivity.URL + src;
            url = new URL(src);
        }
        String filename = getFilenameFromSrc(url.getPath(), false);
        if (filename == null) {
            return null;
        }
        String extension = getExtensionFromSrc(url.getPath());
        /* Check if tag is img for now. Then replace it with JPG: TODO: what is the tag og gifs, m4v, mp4, videos */
        /* imgur picks are in .webp which is a lower quality .jpg  */
        if (elementWrapper.getNormalName().equals("img") && extension.equals(".webp")) {
            extension = ".jpg";
        }
        String file = url.getFile();
        return new URL(url.getProtocol(), url.getHost(), url.getPort(), file.substring(1, file.lastIndexOf(filename)) + filename + extension);
    }

    private static String getExtensionFromSrc(String path) {
        String filenameWithextension = path.substring(path.lastIndexOf('/') + 1);
        return filenameWithextension.substring(filenameWithextension.lastIndexOf('.'));
    }

    private static void writeToFile(InputStream inputStream, File folder, String name) {
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(folder.getAbsolutePath() + "/" + name))) {
            for (int b; (b = inputStream.read()) != -1; ) {
                out.write(b);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private static void writeToFile(ElementWrapper elementWrapper, File folder, String name) {
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(folder.getAbsolutePath() + "/" + name))) {
            byte[] bytes = elementWrapper.getText().getBytes();
            for (byte aByte : bytes) {
                out.write(aByte);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private static String getFilenameFromSrc(String path, boolean withExtension) {
        String filenameWithextension = path.substring(path.lastIndexOf('/') + 1);
        /* Remove _d From file */
        try {
            String filename = filenameWithextension.substring(0, filenameWithextension.lastIndexOf('.'));
            return removeUnderscoreDIfExists(filename) + (withExtension ? path.substring(path.lastIndexOf('.')) : "");
        } catch (Exception e) {
            Log.d(TAG, "error on filename: " + filenameWithextension);
            Toast.makeText(App.getInstance(), "Error when attempting to save media!", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private static String removeUnderscoreDIfExists(String filename) {
        if (filename.length() > 2 && filename.endsWith("_d"))
            return filename.substring(0, filename.length() - 2);
        return filename;
    }

    private static File createDirectory() throws MalformedURLException {
        Log.d(TAG, "createFileAndDirectory started");
        BROWSING_BUTLER_FOLDER = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/BrowsingButler/" + new URL(WebpageRetrieverActivity.URL).getHost());
        if (!BROWSING_BUTLER_FOLDER.mkdirs()) {
            Log.d("ElementGrabber", "mkDir faileD");
        }
        Log.d(TAG, "createFileAndDirectory ended");
        return BROWSING_BUTLER_FOLDER;
    }

    private static String getSourceLink(ElementWrapper elementWrapper) {
        Log.d(TAG, "getSourceLink started");
        return elementWrapper.getElement().attr("src");
    }
}
