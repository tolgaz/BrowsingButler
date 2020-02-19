package com.master.snapshotwizard.activities;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.components.ElementWrapper;
import com.master.snapshotwizard.utils.Log;
import com.warkiz.widget.IndicatorSeekBar;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;

import id.zelory.compressor.Compressor;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CompressResizeActivity extends ActivityWithSwitchHandler {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_compressresize);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


        Button applyButton = findViewById(R.id.apply_compress_resize_button);
        applyButton.setOnClickListener(v -> applyCompressResize());

        CheckBox resizeCheckbox = findViewById(R.id.resize_chooser_button);
        resizeCheckbox.setOnClickListener(v -> flipVisibilityResizeContainer(applyButton));


        EditText widthEditText = findViewById(R.id.resize_width_input);
        EditText heightEditText = findViewById(R.id.resize_height_input);

        TextWatcher myTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(widthEditText.getText().toString().equals("") || heightEditText.getText().toString().equals("")){
                    ViewCompat.setBackgroundTintList(applyButton, ColorStateList.valueOf(getColor(R.color.DarkGray)));
                    applyButton.setClickable(false);
                } else {
                    ViewCompat.setBackgroundTintList(applyButton, null);
                    applyButton.setClickable(true);
                }
            }
        };

        widthEditText.addTextChangedListener(myTextWatcher);
        heightEditText.addTextChangedListener(myTextWatcher);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this, "onResume");
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_compress_resize_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    private void flipVisibilityResizeContainer(Button applyButton) {
        Log.d(this, "flipVisibilityResizeContainer clicked");
        ConstraintLayout constraintLayout = findViewById(R.id.container_resize);
        int visibility = constraintLayout.getVisibility();
        if(visibility == View.VISIBLE) {
            ((TextView) findViewById(R.id.resize_width_input)).setText(null);
            ((TextView) findViewById(R.id.resize_height_input)).setText(null);
            ViewCompat.setBackgroundTintList(applyButton, null);
            applyButton.setClickable(true);
        } else {
            ViewCompat.setBackgroundTintList(applyButton, ColorStateList.valueOf(getColor(R.color.DarkGray)));
            applyButton.setClickable(false);
        }
        constraintLayout.setVisibility(visibility == View.GONE ? View.VISIBLE : View.GONE);
    }

    private void applyCompressResize() {
        Log.d(this, "applyCompressResize");
        IndicatorSeekBar seekBar = findViewById(R.id.seekBar);
        int progressBarValue = seekBar.getProgress();

        EditText resizeWidthEditText = findViewById(R.id.resize_width_input);
        EditText resizeHeightEditText = findViewById(R.id.resize_height_input);
        Log.d(this, "compressQuality: " + progressBarValue);
        try{
            // Try to grab int values
            int resizeWidth = Integer.parseInt(resizeWidthEditText.getText().toString());
            int resizeHeight = Integer.parseInt(resizeHeightEditText.getText().toString());
            Log.d(this, "compressQuality: " + progressBarValue + ", resizwidth: " + resizeWidth + ", resizehieght: " + resizeHeight);
            /* Compress and stuff */
            WebpageRetrieverActivity.configuration.getImagePickerRecycleViewAdapter()
                    .getFileDataset()
                    .stream()
                    .filter(ElementWrapper::getChosen)
                    .forEach(elementWrapper -> {
                        File file = elementWrapper.getFile();
                        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath()));
                            /* TODO: support for viddeos */
                            if(type.contains("video")){
                            } else {
                                File compressedFile = compressFile(resizeWidth, resizeHeight, progressBarValue, file);
                                /* Move and overwrite original image */
                                if(compressedFile != null) moveAndOverwriteFile(compressedFile.toPath(), file.toPath());
                            }
                    });
        } catch (Exception e){
            Log.d(this, e.getMessage());
            Log.d(this, "compressQuality: " + ", resizwidth: " + resizeWidthEditText + ", resizehieght: " + resizeHeightEditText);
        }
        Log.d(this, "applyCompressResize done");
    }

    private void moveAndOverwriteFile(Path source, Path target) {
        try {
            Files.move(source, target, REPLACE_EXISTING);
        } catch (IOException e) {
            Log.d(this, e.getMessage());
        }
    }

    private File compressFile(int resizeWidth, int resizeHeight, int progressBarValue, File file) {
        /* Compress and resize image, save in tmp cache */
        try{
            return new Compressor(this)
                    .setMaxWidth(resizeWidth)
                    .setMaxHeight(resizeHeight)
                    .setQuality(progressBarValue)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(file);
        } catch (Exception e){
            Log.d(this, e.getMessage());
            return null;
        }
    }

    @Override
    public void switchHandler(View view, int position) {
        Log.d(this, "switchHandler");
        Log.d(this, "switchHandler done");
    }
}
