package com.master.browsingbutler.components;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.master.browsingbutler.models.ElementWrapper;
import com.master.browsingbutler.utils.ElementGrabber;
import com.master.browsingbutler.utils.Log;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ShareViaOpenWithHandler {
    public static void share(AppCompatActivity activity, boolean script) {
        List<ElementWrapper> elements = JavaScriptInterface.getSelectedElements();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String title = "Share these media elements with";
        StringBuilder allValues = new StringBuilder();
        elements.stream()
                .filter(elementWrapper -> script ? elementWrapper.getSatisfiesSelection() : true)
                .forEach(elementWrapper -> {
                    try {
                        allValues.append(Uri.parse(ElementGrabber.trimURL(elementWrapper))).append(" ");
                    } catch (MalformedURLException e) {
                        Log.d("handleClick", e.getMessage());
                    }
                });
        intent.putExtra(Intent.EXTRA_TEXT, allValues.toString());
        Intent chooser = Intent.createChooser(intent, title);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(chooser);
        }
    }

    public static void shareSavedElements(AppCompatActivity activity, boolean script) {
        List<ElementWrapper> elements = JavaScriptInterface.getSelectedElements();
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        String title = "Share these media elements with";
        ArrayList<Uri> allValues = new ArrayList<>();
        elements.stream()
                .filter(elementWrapper -> script ? elementWrapper.getSatisfiesSelection() : true)
                .map(ElementWrapper::getFile).forEach(file -> {
            Uri mediaUri = FileProvider.getUriForFile(activity, "com.master.browsingbutler.provider", file);
            allValues.add(mediaUri);
        });
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, allValues);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        Intent chooser = Intent.createChooser(intent, title);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(chooser);
        }
    }
}
