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
package org.mmaug.mae.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;
import org.mmaug.mae.R;

/**
 * A simple view that wraps an avatar.
 */
public class AvatarView extends ImageView implements Checkable {

  private boolean mChecked;

  public AvatarView(Context context) {
    this(context, null);
  }

  public AvatarView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AvatarView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override public boolean isChecked() {
    return mChecked;
  }

  @Override public void setChecked(boolean b) {
    mChecked = b;
    invalidate();
  }

  @Override public void toggle() {
    setChecked(!mChecked);
  }

  @Override protected void onDraw(@NonNull Canvas canvas) {
    super.onDraw(canvas);
    if (mChecked) {
      Drawable border;
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        border = getResources().getDrawable(R.drawable.selector_avatar, null);
      } else {
        border = getResources().getDrawable(R.drawable.selector_avatar);
      }
      assert border != null;
      border.setBounds(0, 0, getWidth(), getHeight());
      border.draw(canvas);
    }
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
  }
}
