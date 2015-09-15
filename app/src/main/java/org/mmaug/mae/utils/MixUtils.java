package org.mmaug.mae.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.mmaug.mae.R;

/**
 * Created by poepoe on 13/9/15.
 */
public class MixUtils {

  public static void makeSlide(View rootView) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      TransitionManager.beginDelayedTransition((ViewGroup) rootView, new Slide());
    } else {
    }
  }

  public static final Drawable getImageDrawable(Resources res) {
    Drawable border;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      border = res.getDrawable(R.drawable.selector_avatar, null);
    } else {
      border = res.getDrawable(R.drawable.selector_avatar);
    }
    return border;
  }

  public static long calculateTimeLeftToVote() throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
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
}
