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
import android.net.Uri;
import android.util.Log;

public class PiCrop {

    public static final String TAG = "PiCrop";
    public static final int FROM_ALBUM = 0;
    public static final int FROM_CAMERA = 1;

    private Activity mActivity;
    private Fragment mFragment;
    private android.support.v4.app.Fragment mFragmentV4;

    private Uri picUri;
    private OnCropListener mListener;
    private CropIntent.Builder cropIntent;

    public PiCrop(Activity activity) {
        this.mActivity = activity;
    }

    public PiCrop(Fragment fragment) {
        this.mFragment = fragment;
    }

    public PiCrop(android.support.v4.app.Fragment fragmentV4) {
        this.mFragmentV4 = fragmentV4;
    }

    public void getSquare(int type, OnCropListener listener) {
        get(type, 1, 1, 0, 0, CropIntent.DEFAULT_FILE_NAME, listener);
    }

    public void get(int type, OnCropListener listener) {
        get(type, CropIntent.DEFAULT_FILE_NAME, listener);
    }

    public void get(int type, String fileName, OnCropListener listener) {
        get(type, 0, 0, 0, 0, fileName, listener);
    }

    public void get(int type, int aspectX, int aspectY, int outputX, int outputY, String fileName, OnCropListener listener) {
        get(type, aspectX, aspectY, outputX, outputY, Uri.parse(CropIntent.DEFAULT_IMAGE_LOCATION + fileName), listener);
    }

    public void get(int type, int aspectX, int aspectY, int outputX, int outputY, Uri saveUri, OnCropListener listener) {
        this.mListener = listener;
        Log.i(TAG, "Start getting the picture.");
        // Get crop intent
        cropIntent = new CropIntent.Builder()
                .setAspectX(aspectX)
                .setAspectY(aspectY)
                .setOutputX(outputX)
                .setOutputY(outputY)
                .setSaveUri(saveUri);
        switch (type) {
            case FROM_ALBUM:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(picUri, "image/*");
                if (mActivity != null) {
                    mActivity.startActivityForResult(intent, FROM_ALBUM);
                } else if (mFragment != null) {
                    mFragment.startActivityForResult(intent, FROM_ALBUM);
                } else if (mFragmentV4 != null) {
                    mFragmentV4.startActivityForResult(intent, FROM_ALBUM);
                }
                break;
            case FROM_CAMERA:
                break;
        }
    }

    // Set your own crop intent
    public void setCropIntent(CropIntent.Builder cropIntent) {
        this.cropIntent = cropIntent;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (mListener == null) {
            Log.e(TAG, "Listener can't be null.");
            return;
        }
        switch (requestCode) {
            case FROM_ALBUM:
                if (mActivity != null) {
                    picUri = cropIntent.start(mActivity, data);
                } else if (mFragment != null) {
                    picUri = cropIntent.start(mFragment, data);
                } else if (mFragmentV4 != null) {
                    picUri = cropIntent.start(mFragmentV4, data);
                } else {
                    throw new NullPointerException("Context is null");
                }
            case CropIntent.CHOOSE_PICTURE_FROM_ALBUM:
                mListener.onStart();
                if (picUri != null) {
                    Log.i(TAG, "Picture crop success.");
                    mListener.onSuccess(picUri);
                } else {
                    Log.i(TAG, "Something wrong here. The picture uri is null.");
                    mListener.onError("Something wrong here. The picture uri is null.");
                }
                break;
            default:
                break;
        }
    }
}
