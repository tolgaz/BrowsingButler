package com.master.browsingbutler.utils;

import android.os.Environment;

import com.master.browsingbutler.activities.WebpageRetrieverActivity;
import com.master.browsingbutler.components.JavaScriptInterface;
import com.master.browsingbutler.models.ElementWrapper;

import org.jsoup.nodes.Element;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ElementGrabber {
    private static String TAG = "ElementGrabber";

    public static void grabElements() throws MalformedURLException {
        ArrayList<ElementWrapper> elements = JavaScriptInterface.getSelectedElements();
        getImages(elements);
    }

    private static void getImages(ArrayList<ElementWrapper> elements) throws MalformedURLException {
        Log.d(TAG, "getImages");
        File folder = createDirectory();

        for (final ElementWrapper elementWrapper : elements) {
            //Open a URL Stream
            /*
                Imgur links are like: https://i.imgur.com/yj9Xp7Q_d.jpg?maxwidth=520&shape=thumb&fidelity=high
                We want to remove the query params. And trim the _d from the end of the url.
             */
            String trimmedURL = trimURL(elementWrapper);

            try (InputStream inputStream = new URL(trimmedURL).openStream()) {
                String name = getFilenameFromSrc(trimmedURL) + getExtensionFromSrc(trimmedURL);
                elementWrapper.setFile(createNewFile(folder, name));
                writeToFile(inputStream, folder, name);
            } catch (IOException e) {
                Log.d(TAG, Arrays.toString(e.getStackTrace()));
            }
        }
    }

    public static String trimURL(ElementWrapper elementWrapper) throws MalformedURLException {
        String src = getSourceLink(elementWrapper);
        URL url = new URL(src);
        String protocol = url.getProtocol();
        String host = url.getHost();
        String filename = getFilenameFromSrc(src);
        String extension = getExtensionFromSrc(src);
        /* Check if tag is img for now. Then replace it with JPG: TODO: what is the tag og gifs, m4v, mp4, videos */
        if (elementWrapper.getNormalName().equals("img")) extension = ".jpg";
        return protocol + "://" + host + "/" + filename + extension;
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

    private static File createNewFile(File folder, String name) throws IOException {
        // TODO: handle overwriting, maybe we dont?
        File file = new File(folder, name);
        if (!file.createNewFile()) {
            Log.d(TAG, "createNewFile, File already exists!");
        }
        return file;
    }

    private static String getExtensionFromSrc(String src) throws MalformedURLException {
        String path = new URL(src).getPath();
        String filenameWithextension = path.substring(path.lastIndexOf('/') + 1);
        return filenameWithextension.substring(filenameWithextension.lastIndexOf('.'));
    }

    private static String getFilenameFromSrc(String src) throws MalformedURLException {
        String path = new URL(src).getPath();
        String filenameWithextension = path.substring(path.lastIndexOf('/') + 1);
        /* Remove _d From file */
        String filename = filenameWithextension.substring(0, filenameWithextension.lastIndexOf('.'));
        return removeUnderscoreDIfExists(filename);
    }

    private static String removeUnderscoreDIfExists(String filename) {
        if (filename.length() > 2 && filename.endsWith("_d"))
            return filename.substring(0, filename.length() - 2);
        return filename;
    }

    private static String trimSrcToHostDomain(String src) throws MalformedURLException {
        String host = new URL(src).getHost();
        return host.substring(host.indexOf(".") + 1, host.lastIndexOf("."));
    }

    private static File createDirectory() throws MalformedURLException {
        Log.d(TAG, "createFileAndDirectory started");
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/BrowsingButler/" + trimSrcToHostDomain(WebpageRetrieverActivity.URL));
        if (!folder.mkdirs()) Log.d("ElementGrabber", "mkDir faileD");
        Log.d(TAG, "createFileAndDirectory ended");
        return folder;
    }

    private static ArrayList<String> getSourceLinks(ArrayList<ElementWrapper> elements) {
        Log.d(TAG, "getSourceLinks started");
        ArrayList<String> sources = new ArrayList<>();
        for (ElementWrapper elementWrapper : elements) {
            Element mediaElement = elementWrapper.getElement();
            if (elementWrapper.getMediaElement() != null) {
                mediaElement = elementWrapper.getMediaElement();
            }
            sources.add(mediaElement.attr("src"));
            Log.d(TAG, "inLoop" + elementWrapper);
        }
        Log.d(TAG, "getSourceLinks ended");
        return sources;
    }

    private static String getSourceLink(ElementWrapper elementWrapper) {
        Log.d(TAG, "getSourceLink started");

        Element mediaElement = elementWrapper.getElement();
        if (elementWrapper.getMediaElement() != null) {
            mediaElement = elementWrapper.getMediaElement();
        }
        Log.d(TAG, "inLoop" + elementWrapper);
        Log.d(TAG, "getSourceLink ended");
        return mediaElement.attr("src");
    }
}
