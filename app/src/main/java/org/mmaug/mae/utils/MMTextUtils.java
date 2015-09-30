package org.mmaug.mae.utils;

import android.content.Context;
import android.view.View;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mm.technomation.tmmtextutilities.mmtext;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public class MMTextUtils {

  private Context mContext;
  private static MMTextUtils mmTextUtils;

  public static MMTextUtils getInstance(Context context) {
    if (mmTextUtils == null) {
      mmTextUtils = new MMTextUtils(context);
    }
    return mmTextUtils;
  }

  public MMTextUtils(Context context) {
    this.mContext = context;
  }

  /**
   * Copied From Kanaung https://github.com/MMAUG/Kanaung
   * https://goo.gl/jjm4ct
   *
   * @return 0 for non - Myanmar
   * 1 for Uni
   * 2 for Zg
   */
  public int detector(String string) {
    String isMyanmar = "[က-အ]+";
    Pattern isMyanmarPattern = Pattern.compile(isMyanmar);
    Matcher isMyanmarMatcher = isMyanmarPattern.matcher(string);
    if (!isMyanmarMatcher.find()) {
      return 0;
    }

    String uni =
        "[ဃငဆဇဈဉညတဋဌဍဎဏဒဓနဘရဝဟဠအ]်|ျ[က-အ]ါ|ျ[ါ-း]|[^\\1031]စ် |\\u103e|\\u103f|\\u1031[^\\u1000-\\u1021\\u103b\\u1040\\u106a\\u106b\\u107e-\\u1084\\u108f\\u1090]|\\u1031$|\\u100b\\u1039|\\u1031[က-အ]\\u1032|\\u1025\\u102f|\\u103c\\u103d[\\u1000-\\u1001]";
    Pattern pattern = Pattern.compile(uni);
    Matcher matcher = pattern.matcher(string);
    if (matcher.find()) {
      return 1;
    }
    return 2;
  }

  public void prepareMultipleViews(String content, View... textViews) {
    for (View textView : textViews) {
      switch (detector(content)) {
        case 0:
          // We do nothing
          break;
        case 1:
          mmtext.prepareView(mContext, textView, mmtext.TEXT_UNICODE, true, true);
          break;
        case 2:
          mmtext.prepareView(mContext, textView, mmtext.TEXT_ZAWGYI, true, true);
      }
    }
  }

  public void prepareMultipleViews(View... textViews) {
    for (View textView : textViews) {
      mmtext.prepareView(mContext, textView, mmtext.TEXT_UNICODE, true, true);
    }
  }

  public void prepareSingleView(View textView) {
    mmtext.prepareView(mContext, textView, mmtext.TEXT_UNICODE, true, true);
  }

  public void prepareSingleView(String content, View textView) {
    switch (detector(content)) {
      case 0:
        // We do nothing
        break;
      case 1:
        mmtext.prepareView(mContext, textView, mmtext.TEXT_UNICODE, true, true);
        break;
      case 2:
        mmtext.prepareView(mContext, textView, mmtext.TEXT_ZAWGYI, true, true);
    }
  }
}
