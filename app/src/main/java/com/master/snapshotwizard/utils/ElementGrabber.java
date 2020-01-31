package com.master.snapshotwizard.utils;


import android.os.Environment;

import com.master.snapshotwizard.components.ElementWrapper;
import com.master.snapshotwizard.components.JavaScriptInterface;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElementGrabber {
    public static void grabElements(){
        ArrayList<ElementWrapper> elements = JavaScriptInterface.selectedElements;
        try {
            getImages(elements);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getImages(ArrayList<ElementWrapper> elements) throws IOException {
        Log.d("ElementGrabber", "getImages");
        ArrayList<String> sources = new ArrayList<>();
        for(ElementWrapper elementWrapper: elements){
            sources.add(elementWrapper.getElement().attr("src"));
            Log.d("ElementGrabber", "inLoop"  + elementWrapper);
        }
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SnapshotWizard/");

        folder.mkdirs();

        for(final String src: sources){
                try {
                    int indexname = src.lastIndexOf("/");
                    String newSrc = src;
                    if (indexname == src.length()) {
                        newSrc = src.substring(1, indexname);
                    }

                    indexname = newSrc.lastIndexOf("/");
                    String name = newSrc.substring(indexname, newSrc.length());
                    if(!name.contains(".jpg")) name += ".jpg";
                    //Open a URL Stream
                    InputStream in = new URL(src).openStream();
                    File file = new File(folder,name);
                    file.createNewFile();
                    OutputStream out = new BufferedOutputStream(new FileOutputStream(folder.getAbsolutePath() + name));

                    for (int b; (b = in.read()) != -1; ) {
                        out.write(b);

                    }
                    out.close();
                    in.close();
                }
                catch (IOException e) {
                        e.printStackTrace();
                    }
            }
    }
}
