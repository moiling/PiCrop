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
package com.moinut.picrop.contextType;

import android.app.Fragment;
import android.content.Intent;

import com.moinut.picrop.CropIntent;
import com.moinut.picrop.config.Const;

public class FragmentType implements ContextType {
    private Fragment mFragment;

    public FragmentType(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public void startAlbum(Intent intent) {
        mFragment.startActivityForResult(intent, Const.CHOOSE_PICTURE_FROM_ALBUM);
    }

    @Override
    public void startCamera(Intent intent) {
        mFragment.startActivityForResult(intent, Const.TAKE_PHOTO_FROM_CAMERA);
    }

    @Override
    public void startCrop(CropIntent.Builder cropIntent, Intent data) {
        cropIntent.start(mFragment, data);
    }
}
