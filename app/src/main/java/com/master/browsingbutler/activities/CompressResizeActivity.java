package com.master.browsingbutler.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.master.browsingbutler.components.JavaScriptInterface;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import id.zelory.compressor.Compressor;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CompressResizeActivity extends ActivityWithSwitchHandler {

    private static final String TAG = "CompressResizeActivity";

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

        EditText widthEditText = this.findViewById(R.id.resize_width_input);
        EditText heightEditText = this.findViewById(R.id.resize_height_input);

        Button applyButton = this.findViewById(R.id.apply_compress_resize_button);
        IndicatorSeekBar indicatorSeekBar = this.findViewById(R.id.seekBar);
        applyButton.setOnClickListener(v -> {
            applyCompressResize(indicatorSeekBar.getProgress(), widthEditText.getText().toString(), heightEditText.getText().toString(), false);
            ActivityUtils.engageActivityComplete(this, "Media has been successfully compressed/resized and saved!");
        });

        CheckBox resizeCheckbox = this.findViewById(R.id.resize_chooser_checkbox);
        resizeCheckbox.setOnClickListener(v -> changeApplyButtonAppearance(applyButton, flipVisibilityResizeContainer(this)));

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

    private static void changeApplyButtonAppearance(Button applyButton, boolean shouldBeClickable) {
        if (shouldBeClickable) {
            ViewCompat.setBackgroundTintList(applyButton, null);
            applyButton.setClickable(true);
        } else {
            ViewCompat.setBackgroundTintList(applyButton, ColorStateList.valueOf(App.getResourses().getColor(R.color.DarkGray, null)));
            applyButton.setClickable(false);
        }
    }

    public static void applyCompressResize(int quality, String width, String height, boolean script) {
        Log.d(TAG, "applyCompressResize");
        try {
            // Try to grab int values
            AtomicInteger resizeWidth = new AtomicInteger(Integer.parseInt(width));
            AtomicInteger resizeHeight = new AtomicInteger(Integer.parseInt(height));
            Log.d(TAG, "compressQuality: " + quality + ", resizwidth: " + resizeWidth + ", resizehieght: " + resizeHeight);
            /* Compress and stuff */
            getElements(script)
                    .stream()
                    /* if script say all match predicate */
                    .filter(elementWrapper -> script ? elementWrapper.getSatisfiesSelection() : elementWrapper.getChosen())
                    .forEach(elementWrapper -> {
                        File file = elementWrapper.getFile();
                        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath()));
                        /* TODO: support for viddeos */
                        Log.d(TAG, "old size:" + Integer.parseInt(String.valueOf(file.length() / 1024)));
                        if (type == null) {
                            Log.d(TAG, "ERROR: type is null!! " + file.toString());
                            return;
                        }
                        if (type.contains("video")) {
                            if (resizeWidth.get() != 0 && resizeHeight.get() != 0) {
                                String extension = FileUtils.getExtensionFromSrc(file.getName());
                                String outputFileName = String.format("%s/%s_compressed%s", file.getParent(), FileUtils.getFilenameFromSrc(file.getName()), extension);
                                String cmd = String.format(Locale.getDefault(), "-y -i %s -vf scale=%d:%d -c:a copy %s", file.getAbsolutePath(), resizeWidth.get(), resizeHeight.get(), outputFileName);
                                evaluateFFmpegReturnValue(FFmpeg.execute(cmd));
                            }
                        } else {
                            if (resizeWidth.get() == 0 || resizeHeight.get() == 0) {
                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                resizeWidth.set(bitmap.getWidth());
                                resizeHeight.set(bitmap.getHeight());
                            }
                            File compressedFile = compressFile(resizeWidth.get(), resizeHeight.get(), quality, file);
                            /* Move and overwrite original image */
                            if (compressedFile != null) {
                                moveAndOverwriteFile(compressedFile.toPath(), file.toPath());
                            }
                        }
                    });
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        Log.d(TAG, "applyCompressResize done");
    }

    private static List<ElementWrapper> getElements(boolean script) {
        return script ? JavaScriptInterface.getSelectedElements() : WebpageRetrieverActivity.configuration.getImagePickerRecycleViewAdapter().getFileDataset();
    }

    private static void moveAndOverwriteFile(Path source, Path target) {
        try {
            Files.move(source, target, REPLACE_EXISTING);
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private static File compressFile(int resizeWidth, int resizeHeight, int progressBarValue, File file) {
        /* Compress and resize image, save in tmp cache */
        try {
            return new Compressor(App.getInstance())
                    .setMaxWidth(resizeWidth)
                    .setMaxHeight(resizeHeight)
                    .setQuality(progressBarValue)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(file);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return null;
        }
    }

    private static void evaluateFFmpegReturnValue(int rc) {
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
