package org.mmaug.mae.utils;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import org.mmaug.mae.R;

public class AnalyticsManager {
  private static Context sAppContext = null;
  private static Tracker mTracker;

  public static void sendScreenView(String screenName) {
    if (canSend()) {
      mTracker.setScreenName(screenName);
      mTracker.send(new HitBuilders.AppViewBuilder().build());
    }
  }

  private static boolean canSend() {
    return sAppContext != null && mTracker != null;
  }

  public static void sendEvent(String category, String action, String label) {
    sendEvent(category, action, label, 0);
  }

  public static void sendEvent(String category, String action, String label, long value) {
    if (canSend()) {
      mTracker.send(new HitBuilders.EventBuilder().setCategory(category)
          .setAction(action)
          .setLabel(label)
          .setValue(value)
          .build());
    }
  }

  public static synchronized void initializeAnalyticsTracker(Context context) {
    sAppContext = context;
    if (mTracker == null) {
      int useProfile;
      useProfile = R.xml.analytics_release;
      mTracker = GoogleAnalytics.getInstance(context).newTracker(useProfile);

      // To prevent from not showing the crash logs but "Shutting Down VM" message
      // http://stackoverflow.com/a/31259916/2438460
      mTracker.enableExceptionReporting(false);
      mTracker.enableAdvertisingIdCollection(true);
      mTracker.enableAutoActivityTracking(true);
    }
  }

  public Tracker getTracker() {
    return mTracker;
  }

  public static synchronized void setTracker(Tracker tracker) {
    mTracker = tracker;
  }
}
