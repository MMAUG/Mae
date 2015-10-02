package org.mmaug.mae.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import org.mmaug.mae.BuildConfig;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.utils.mmtext;

/**
 * Created by poepoe on 2/10/15.
 */
public class AboutActivity extends BaseActivity {

  @Bind(R.id.tv_version) TextView version;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    version.setText(getString(R.string.version, BuildConfig.VERSION_NAME));
    mmtext.prepareActivity(this, mmtext.TEXT_UNICODE, true, true);
  }

  @OnClick(R.id.rate_us) void rateApp() {
    final String appPackageName = getPackageName();
    try {
      startActivity(
          new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
    } catch (ActivityNotFoundException anfe) {
      startActivity(new Intent(Intent.ACTION_VIEW,
          Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
    }
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_about;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return "ရွေးကောက်ပွဲ လမ်းညွှန်  − မဲ";
  }
}
