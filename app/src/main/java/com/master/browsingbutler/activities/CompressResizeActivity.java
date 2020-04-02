package com.master.browsingbutler.activities;

import android.content.Intent;
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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.models.ElementWrapper;
import com.master.browsingbutler.utils.ActivityUtils;
import com.master.browsingbutler.utils.FileUtils;
import com.master.browsingbutler.utils.Log;
import com.warkiz.widget.IndicatorSeekBar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import id.zelory.compressor.Compressor;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CompressResizeActivity extends ActivityWithSwitchHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        this.setContentView(R.layout.activity_compressresize);
        this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    private void handleOnlyVideoElementWrappers() {
        List<ElementWrapper> nonVideoElementWrappers = WebpageRetrieverActivity.configuration.getImagePickerRecycleViewAdapter()
                .getFileDataset()
                .stream()
                .filter(ElementWrapper::getChosen)
                .filter(eW -> {
                    File file = eW.getFile();
                    String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath()));
                    return !type.contains("video");
                }).collect(Collectors.toList());
        /* if no video ew dont show quality - show resize */
        if (nonVideoElementWrappers.isEmpty()) {
            CheckBox checkBox = this.findViewById(R.id.resize_chooser_checkbox);
            this.findViewById(R.id.quality_wrapper).setVisibility(View.GONE);
            checkBox.callOnClick();
            checkBox.setChecked(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this, "onResume");
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_compress_resize_screen);

        Button applyButton = this.findViewById(R.id.apply_compress_resize_button);
        applyButton.setOnClickListener(v -> this.applyCompressResize());

        CheckBox resizeCheckbox = this.findViewById(R.id.resize_chooser_checkbox);
        resizeCheckbox.setOnClickListener(v -> changeApplyButtonApperance(applyButton, flipVisibilityResizeContainer(this)));

        EditText widthEditText = this.findViewById(R.id.resize_width_input);
        EditText heightEditText = this.findViewById(R.id.resize_height_input);

        TextWatcher myTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (widthEditText.getText().toString().equals("") || heightEditText.getText().toString().equals("")) {
                    ViewCompat.setBackgroundTintList(applyButton, ColorStateList.valueOf(CompressResizeActivity.this.getColor(R.color.DarkGray)));
                    applyButton.setClickable(false);
                } else {
                    ViewCompat.setBackgroundTintList(applyButton, null);
                    applyButton.setClickable(true);
                }
            }
        };

        widthEditText.addTextChangedListener(myTextWatcher);
        heightEditText.addTextChangedListener(myTextWatcher);

        this.handleOnlyVideoElementWrappers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    public static boolean flipVisibilityResizeContainer(ActivityWithSwitchHandler activity) {
        Log.d(activity, "flipVisibilityResizeContainer clicked");
        ConstraintLayout constraintLayout = activity.findViewById(R.id.container_resize);
        if (constraintLayout.getVisibility() == View.VISIBLE) {
            ((TextView) activity.findViewById(R.id.resize_width_input)).setText(null);
            ((TextView) activity.findViewById(R.id.resize_height_input)).setText(null);
            constraintLayout.setVisibility(View.GONE);
            return true;
        } else {
            constraintLayout.setVisibility(View.VISIBLE);
            return false;
        }
    }

    private static void changeApplyButtonApperance(Button applyButton, boolean shouldBeClickable) {
        if (shouldBeClickable) {
            ViewCompat.setBackgroundTintList(applyButton, null);
            applyButton.setClickable(true);
        } else {
            ViewCompat.setBackgroundTintList(applyButton, ColorStateList.valueOf(App.getResourses().getColor(R.color.DarkGray, null)));
            applyButton.setClickable(false);
        }
    }

    private void applyCompressResize() {
        Log.d(this, "applyCompressResize");
        IndicatorSeekBar seekBar = this.findViewById(R.id.seekBar);
        int progressBarValue = seekBar.getProgress();

        EditText resizeWidthEditText = this.findViewById(R.id.resize_width_input);
        EditText resizeHeightEditText = this.findViewById(R.id.resize_height_input);
        Log.d(this, "compressQuality: " + progressBarValue);
        try {
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
                        Log.d(this, "old size:" + Integer.parseInt(String.valueOf(file.length() / 1024)));
                        if (type == null) {
                            Log.d(this, "ERROR: type is null!! " + file.toString());
                            return;
                        }
                        if (type.contains("video")) {
                            String extension = FileUtils.getExtensionFromSrc(file.getName());
                            String outputFileName = file.getParent() + "/" + FileUtils.getFilenameFromSrc(file.getName()) + "_compressed" + extension;
                            String cmd = String.format(Locale.getDefault(), "-y -i %s -vf scale=%d:%d -c:a copy %s", file.getAbsolutePath(), resizeWidth, resizeHeight, outputFileName);
                            this.evaluateFFmpegReturnValue(FFmpeg.execute(cmd));
                        } else {
                            File compressedFile = this.compressFile(resizeWidth, resizeHeight, progressBarValue, file);
                            /* Move and overwrite original image */
                            if (compressedFile != null)
                                this.moveAndOverwriteFile(compressedFile.toPath(), file.toPath());
                        }
                    });
        } catch (Exception e) {
            Log.d(this, e.getMessage());
        }
        Log.d(this, "applyCompressResize done");
        ActivityUtils.engageActivityComplete("Media has been successfully compressed/resized and saved!");
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
        try {
            return new Compressor(this)
                    .setMaxWidth(resizeWidth)
                    .setMaxHeight(resizeHeight)
                    .setQuality(progressBarValue)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(file);
        } catch (Exception e) {
            Log.d(this, e.getMessage());
            return null;
        }
    }

    private void evaluateFFmpegReturnValue(int rc) {
        if (rc == RETURN_CODE_SUCCESS) {
            Log.d(Config.TAG, "Command exeecution completed successfully.");
        } else if (rc == RETURN_CODE_CANCEL) {
            Log.d(Config.TAG, "Command execution cancelled by user.");
        } else {
            Log.d(Config.TAG, String.format("Command execution failed with rc=%d and the output below.", rc));
            Config.printLastCommandOutput(4);
        }
    }

    @Override
    public void switchHandler(View view, int position) {
        Log.d(this, "switchHandler");
        Log.d(this, "switchHandler done");
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
