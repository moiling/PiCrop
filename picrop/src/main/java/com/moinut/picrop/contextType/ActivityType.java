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

import android.app.Activity;
import android.content.Intent;

import com.moinut.picrop.CropIntent;
import com.moinut.picrop.config.Const;

public class ActivityType implements ContextType {

    private Activity mActivity;

    public ActivityType(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void startAlbum(Intent intent) {
        mActivity.startActivityForResult(intent, Const.CHOOSE_PICTURE_FROM_ALBUM);
    }

    @Override
    public void startCamera() {

    }

    @Override
    public void startCrop(CropIntent.Builder cropIntent, Intent data) {
        cropIntent.start(mActivity, data);
    }
}
