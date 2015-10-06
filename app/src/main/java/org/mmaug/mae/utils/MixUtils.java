package org.mmaug.mae.utils;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by poepoe on 13/9/15.
 */
public class MixUtils {

  private static final long TRANSITION_DURATION = 200;

  public static void makeSlide(View rootView) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      TransitionManager.beginDelayedTransition((ViewGroup) rootView, new Slide(Gravity.BOTTOM));
    } else {
    }
  }

  public static long calculateTimeLeftToVote() throws ParseException {
    /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    ;*/
    TimeZone defaultTimeZone = TimeZone.getDefault();
    String strDefaultTimeZone = defaultTimeZone.getDisplayName(false, TimeZone.SHORT);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    formatter.setTimeZone(TimeZone.getTimeZone(strDefaultTimeZone));
    String electionTime = "2015-11-08 08:00:00";
    Date electionDate = formatter.parse(electionTime);
    return electionDate.getTime() - new Date().getTime();
  }

  public static HashMap<String, String> formatTime(long millis) {

    long days = TimeUnit.MILLISECONDS.toDays(millis);
    millis -= TimeUnit.DAYS.toMillis(days);
    long hours = TimeUnit.MILLISECONDS.toHours(millis);
    millis -= TimeUnit.HOURS.toMillis(hours);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
    millis -= TimeUnit.MINUTES.toMillis(minutes);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

    long month = 0;
    if (days > 30) {
      month = 1;
      days = days % 30;
    }

    StringBuilder monthDay = new StringBuilder(64);
    StringBuilder hourMinutes = new StringBuilder(64);

    if (month != 0) {
      monthDay.append(month);
      monthDay.append(" လ ");
    }

    if (days != 0) {
      monthDay.append(days);
      monthDay.append(" ရက် ");
    }

    if (hours != 0) {
      hourMinutes.append(hours);
      hourMinutes.append(" နာရီ ");
    }

    if (!(hours == 0 && minutes == 0)) {
      hourMinutes.append(minutes);
      hourMinutes.append(" မိနစ် ");
    }

    if (hours == 0 && minutes != 0) {
      hourMinutes.append(seconds);
      hourMinutes.append(" စက္ကန့် ");
    }

    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("month_day", monthDay.toString());
    hashMap.put("hour_minute", hourMinutes.toString());

    return hashMap;
  }

  public static String convertToBurmese(String input) {
    if (input != null) {
      input = input.replaceAll("0", "၀");
      input = input.replaceAll("1", "၁");
      input = input.replaceAll("2", "၂");
      input = input.replaceAll("3", "၃");
      input = input.replaceAll("4", "၄");
      input = input.replaceAll("5", "၅");
      input = input.replaceAll("6", "၆");
      input = input.replaceAll("7", "၇");
      input = input.replaceAll("8", "၈");
      input = input.replaceAll("9", "၉");
      return input;
    }
    return "";
  }

  public static float convertDpToPixel(Context context, float dp) {
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    float px = dp * (metrics.densityDpi / 160f);
    return px;
  }

  public static int calculateHeight(WindowManager w) {
    Display d = w.getDefaultDisplay();
    Point point = new Point();
    d.getSize(point);
    return point.y;
  }

  public static void toggleVisibilityWithAnim(View view) {
    boolean shouldDisplay = view.getVisibility() == View.GONE;
    toggleVisibilityWithAnim(view, shouldDisplay);
  }

  public static void toggleVisibilityWithAnim(View view, boolean shouldDisplay) {
    toggleVisibilityWithAnim(view, TRANSITION_DURATION, shouldDisplay);
  }

  public static void toggleVisibilityWithAnim(View view, long duration, boolean shouldDisplay) {

    float alphaVal = (shouldDisplay) ? 1 : 0;
    int visibility = (shouldDisplay) ? View.VISIBLE : View.GONE;

    view.animate()
        .setDuration(duration)
        .setInterpolator(new DecelerateInterpolator())
        .alpha(alphaVal)
        .setListener(new FadeAnimationListener(view, visibility))
        .start();
  }

  public static int dip2px(Context context, float dp) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }

  public static class FadeAnimationListener implements Animator.AnimatorListener {

    private View view;
    private int visibilityAfterAnim;

    public FadeAnimationListener(View view, int visibility) {
      this.view = view;
      visibilityAfterAnim = visibility;
    }

    @Override public void onAnimationStart(Animator animation) {
      if (visibilityAfterAnim == View.VISIBLE) view.setVisibility(View.VISIBLE);
    }

    @Override public void onAnimationEnd(Animator animation) {
      if (visibilityAfterAnim == View.GONE) view.setVisibility(View.GONE);
    }

    @Override public void onAnimationCancel(Animator animation) {
    }

    @Override public void onAnimationRepeat(Animator animation) {
    }
  }
}
