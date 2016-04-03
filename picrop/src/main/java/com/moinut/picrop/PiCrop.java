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

    public PiCrop(Activity activity) {
        this.mActivity = activity;
    }

    public PiCrop(Fragment fragment) {
        this.mFragment = fragment;
    }

    public PiCrop(android.support.v4.app.Fragment fragmentV4) {
        this.mFragmentV4 = fragmentV4;
    }

    public void get(int type, OnCropListener listener) {
        this.mListener = listener;
        Log.i(TAG, "Start getting the picture.");
        mListener.onStart();
        switch (type) {
            case FROM_ALBUM:
                if (mActivity != null) {
                    picUri = new CropIntent.Builder().start(mActivity);
                } else if (mFragment != null) {
                    picUri = new CropIntent.Builder().start(mFragment);
                } else if (mFragmentV4 != null) {
                    picUri = new CropIntent.Builder().start(mFragmentV4);
                } else {
                    throw new NullPointerException("Context is null");
                }
                break;
            case FROM_CAMERA:
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (mListener == null) {
            Log.e(TAG, "Listener can't be null.");
            return;
        }
        switch (requestCode) {
            case CropIntent.CHOOSE_BIG_PICTURE:
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
