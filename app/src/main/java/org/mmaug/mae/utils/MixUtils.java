package org.mmaug.mae.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import org.mmaug.mae.R;

/**
 * Created by poepoe on 13/9/15.
 */
public class MixUtils {

  public static final Drawable getImageDrawable(Resources res) {
    Drawable border;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      border = res.getDrawable(R.drawable.selector_avatar, null);
    } else {
      border = res.getDrawable(R.drawable.selector_avatar);
    }
    return border;
  }
}
