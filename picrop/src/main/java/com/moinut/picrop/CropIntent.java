/*
 * PiCrop library for Android
 * Copyright (c) 2016 MOILING (http://github.com/moiling).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moinut.picrop;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.moinut.picrop.config.Const;

public class CropIntent {

    /*
     * intent params
     */
    public static final String ASPECT_X = "aspectX";
    public static final String ASPECT_Y = "aspectY";
    public static final String OUTPUT_X = "outputX";
    public static final String OUTPUT_Y = "outputY";
    public static final String RETURN_DATA = "return-data";
    public static final String SCALE = "scale";
    public static final String NO_FACE_DETECTION = "noFaceDetection";
    public static final String OUTPUT_FORMAT = "outputFormat";
    public static final String OUTPUT_QUALITY = "outputQuality";

    /*
     * file uri
     */
    public static final String DEFAULT_IMAGE_LOCATION = "file://"
            + Environment.getExternalStorageDirectory().getPath()
            + "/";
    public static final String DEFAULT_FILE_NAME = "temp";

    /*
     * init params
     */
    private int aspectX = 0;
    private int aspectY = 0;
    private int outputX = 0;
    private int outputY = 0;
    private boolean scale = true;
    private boolean returnData = false;
    private String outputFormat = Bitmap.CompressFormat.JPEG.toString();
    private boolean noFaceDetection = true;
    private int outputQuality = 100;
    private Bitmap bitmap;
    private Uri saveUri = Uri.parse(DEFAULT_IMAGE_LOCATION + DEFAULT_FILE_NAME);

    /**
     * Use params to create an intent.
     * @param data The image from other activity back.
     * @return crop image intent
     */
    public Intent getIntent(Intent data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (data != null) {
            intent.setDataAndType(data.getData(), "image/*");
        } else {
            intent.setDataAndType(saveUri, "image/*"); // camera don't return data.
        }

        intent.putExtra("crop", "true");
        if (aspectX != 0) intent.putExtra(ASPECT_X, aspectX);
        if (aspectY != 0) intent.putExtra(ASPECT_Y, aspectY);
        if (outputX != 0) intent.putExtra(OUTPUT_X, outputX);
        if (outputY != 0) intent.putExtra(OUTPUT_Y, outputY);
        intent.putExtra(SCALE, scale);
        intent.putExtra(RETURN_DATA, returnData);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);
        intent.putExtra(OUTPUT_FORMAT, outputFormat);
        intent.putExtra(NO_FACE_DETECTION, noFaceDetection);
        return intent;
    }

    public static class Builder {
        private CropIntent mCropIntent = new CropIntent();

        public Builder() {

        }

        public Uri start(Activity activity, Intent data) {
            return start(activity, data, Const.CROP_PICTURE);
        }

        public Uri start(Fragment fragment, Intent data) {
            return start(fragment, data, Const.CROP_PICTURE);
        }

        public Uri start(android.support.v4.app.Fragment fragment, Intent data) {
            return start(fragment, data, Const.CROP_PICTURE);
        }

        public Uri start(android.support.v4.app.Fragment fragment, Intent data, int type) {
            fragment.startActivityForResult(mCropIntent.getIntent(data), type);
            return mCropIntent.saveUri;
        }

        public Uri start(Fragment fragment, Intent data, int type) {
            fragment.startActivityForResult(mCropIntent.getIntent(data), type);
            return mCropIntent.saveUri;
        }

        public Uri start(Activity activity, Intent data, int type) {
            activity.startActivityForResult(mCropIntent.getIntent(data), type);
            return mCropIntent.saveUri;
        }

        public CropIntent build() {
            return mCropIntent;
        }

        public Builder setAspectX(int aspectX) {
            mCropIntent.aspectX = aspectX;
            return this;
        }

        public Builder setAspectY(int aspectY) {
            mCropIntent.aspectY = aspectY;
            return this;
        }

        public Builder setNoFaceDetection(boolean noFaceDetection) {
            mCropIntent.noFaceDetection = noFaceDetection;
            return this;
        }

        public Builder setOutputFormat(String outputFormat) {
            mCropIntent.outputFormat = outputFormat;
            return this;
        }

        public Builder setOutputQuality(int outputQuality) {
            mCropIntent.outputQuality = outputQuality;
            return this;
        }

        public Builder setOutputX(int outputX) {
            mCropIntent.outputX = outputX;
            return this;
        }

        public Builder setOutputY(int outputY) {
            mCropIntent.outputY = outputY;
            return this;
        }

        public Builder setSaveUri(Uri saveUri) {
            if (saveUri != null) {
                mCropIntent.saveUri = saveUri;
            }
            return this;
        }

        public Builder setScale(boolean scale) {
            mCropIntent.scale = scale;
            return this;
        }

        public Builder setReturnData(boolean returnData) {
            mCropIntent.returnData = returnData;
            return this;
        }
    }
}
