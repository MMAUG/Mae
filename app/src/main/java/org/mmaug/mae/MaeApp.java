package org.mmaug.mae;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by poepoe on 30/9/15.
 */
public class MaeApp extends Application {
  @Override public void onCreate() {
    super.onCreate();

    //Timber
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      Timber.plant(new CrashReportingTree());
    }

    //Crashlytics
    CrashlyticsCore.Builder crashCoreBuilder = new CrashlyticsCore.Builder();
    crashCoreBuilder.disabled(BuildConfig.DEBUG);
    Fabric.with(this, new Crashlytics.Builder().core(crashCoreBuilder.build()).build());
  }

  private static class CrashReportingTree extends Timber.HollowTree {

    @Override public void i(String message, Object... args) {
      Crashlytics.log(String.format(message, args));
    }

    @Override public void i(Throwable t, String message, Object... args) {
      i(message, args); // Just add to the log.
    }

    @Override public void e(String message, Object... args) {
      i("ERROR: " + message, args); // Just add to the log.
    }

    @Override public void e(Throwable t, String message, Object... args) {
      e(message, args);
      Crashlytics.logException(t);
    }
  }
}
