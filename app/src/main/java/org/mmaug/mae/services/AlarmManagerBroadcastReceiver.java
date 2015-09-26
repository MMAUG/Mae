package org.mmaug.mae.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.Html;
import android.widget.Toast;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.mmaug.mae.R;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

  final public static String ONE_TIME = "onetime";

  @Override public void onReceive(Context context, Intent intent) {
    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
    //Acquire the lock
    wl.acquire();

    //You can do the processing here update the widget/remote views.
    Bundle extras = intent.getExtras();
    StringBuilder msgStr = new StringBuilder();

    if (extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)) {
      msgStr.append("One time Timer : ");
    }
    Format formatter = new SimpleDateFormat("hh:mm:ss a");
    msgStr.append(formatter.format(new Date()));
    //TODO we need to show :D MAE PAY SOH
    Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();
    PendingIntent pIntent = PendingIntent.getActivity(context, 1000 * 3600, intent, 0);
    Notification mNotification = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
      mNotification = new Notification.Builder(context).setContentTitle(
          context.getString(R.string.time_to_vote))
          .setContentText(Html.fromHtml(context.getString(R.string.time_to_vote)))
          .setSmallIcon(R.mipmap.ic_launcher)
          .setContentIntent(pIntent)
          .setAutoCancel(true)
          .build();
    }
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(0, mNotification);
    //Release the lock
    wl.release();
  }

  public void SetAlarm(Context context) {
    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
    intent.putExtra(ONE_TIME, Boolean.FALSE);
    PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
    //After after 30 seconds
    TimeZone defaultTimeZone = TimeZone.getDefault();
    String strDefaultTimeZone = defaultTimeZone.getDisplayName(false, TimeZone.SHORT);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    formatter.setTimeZone(TimeZone.getTimeZone(strDefaultTimeZone));
    String electionTime = "2015-11-08 08:00:00";
    try {
      Date electionDate = formatter.parse(electionTime);
      am.setRepeating(AlarmManager.RTC_WAKEUP, electionDate.getTime(), 1000 * 3600, pi);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public void CancelAlarm(Context context) {
    Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
    PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.cancel(sender);
  }

  public void setOnetimeTimer(Context context) {
    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
    intent.putExtra(ONE_TIME, Boolean.TRUE);
    PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
    am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
  }
}
