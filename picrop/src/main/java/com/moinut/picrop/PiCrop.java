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
import android.provider.MediaStore;
import android.util.Log;

import com.moinut.picrop.callback.OnCropListener;
import com.moinut.picrop.config.Const;
import com.moinut.picrop.contextType.ActivityType;
import com.moinut.picrop.contextType.ContextType;
import com.moinut.picrop.contextType.FragmentType;
import com.moinut.picrop.contextType.FragmentV4Type;

public class PiCrop {

    public static final String TAG = "PiCrop";
    public static final int FROM_ALBUM = 0;
    public static final int FROM_CAMERA = 1;

    private ContextType mContext;

    private Uri picUri;
    private OnCropListener mListener;
    private CropIntent.Builder cropIntent;

    public PiCrop(Activity activity) {
        this.mContext = new ActivityType(activity);
    }

    public PiCrop(Fragment fragment) {
        this.mContext = new FragmentType(fragment);
    }

    public PiCrop(android.support.v4.app.Fragment fragmentV4) {
        this.mContext = new FragmentV4Type(fragmentV4);
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
        this.picUri = cropIntent == null ? saveUri : cropIntent.getUri();
        Log.i(TAG, "Start getting the picture.");
        // Get crop intent
        if (cropIntent == null) {
            cropIntent = new CropIntent.Builder()
                    .setAspectX(aspectX)
                    .setAspectY(aspectY)
                    .setOutputX(outputX)
                    .setOutputY(outputY)
                    .setSaveUri(saveUri);
        }
        switch (type) {
            case FROM_ALBUM:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(picUri, "image/*");
                mContext.startAlbum(intent);
                break;
            case FROM_CAMERA:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                mContext.startCamera(cameraIntent);
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
            case Const.TAKE_PHOTO_FROM_CAMERA:
                Log.i(TAG, "onActivityResult(TAKE_PHOTO_FROM_CAMERA): data -> " + data);
            case Const.CHOOSE_PICTURE_FROM_ALBUM:
                Log.i(TAG, "onActivityResult(CHOOSE_PICTURE_FROM_ALBUM): data -> " + data);
                mContext.startCrop(cropIntent, data);
                break;
            case Const.CROP_PICTURE:
                Log.i(TAG, "onActivityResult(CROP_PICTURE): data -> " + data);
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
