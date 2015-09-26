package org.mmaug.mae.utils;

import android.content.Context;
import android.graphics.Typeface;
import java.util.Hashtable;

/**
 * Created by indexer on 3/17/15.
 */
public class FontCache {
  private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

  public static Typeface get(String name, Context context) {
    Typeface tf = fontCache.get(name);
    if (tf == null) {
      try {
        tf = Typeface.createFromAsset(context.getAssets(), "font/" + name);
      } catch (Exception e) {
        return null;
      }
      fontCache.put(name, tf);
    }
    return tf;
  }
}
