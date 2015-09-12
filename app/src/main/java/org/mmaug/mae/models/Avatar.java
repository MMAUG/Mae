/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mmaug.mae.models;

import android.support.annotation.DrawableRes;
import org.mmaug.mae.R;

/**
 * The available avatars with their corresponding drawable resource ids.
 */
public enum Avatar {

  ONE(R.drawable.avatar_1_raster),
  TWO(R.drawable.avatar_2_raster),
  THREE(R.drawable.avatar_3_raster),
  FOUR(R.drawable.avatar_4_raster),
  FIVE(R.drawable.avatar_5_raster),
  SIX(R.drawable.avatar_6_raster),
  SEVEN(R.drawable.avatar_7_raster),
  EIGHT(R.drawable.avatar_8_raster),
  NINE(R.drawable.avatar_9_raster),
  TEN(R.drawable.avatar_10_raster),
  ELEVEN(R.drawable.avatar_11_raster),
  TWELVE(R.drawable.avatar_12_raster),
  THIRTEEN(R.drawable.avatar_13_raster),
  FOURTEEN(R.drawable.avatar_14_raster),
  FIFTEEN(R.drawable.avatar_15_raster),
  SIXTEEN(R.drawable.avatar_16_raster);

  private static final String TAG = "Avatar";

  private final int mResId;

  Avatar(@DrawableRes final int resId) {
    mResId = resId;
  }

  @DrawableRes public int getDrawableId() {
    return mResId;
  }

  public String getNameForAccessibility() {
    return TAG + " " + ordinal() + 1;
  }
}
