package org.mmaug.mae.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
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
  @Bind(R.id.tv_app_des) TextView appDes;
  @Bind(R.id.ssygm) TextView ssgym;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    version.setText(getString(R.string.version, BuildConfig.VERSION_NAME));
    if (!isUnicode()) {
      mmtext.prepareActivity(this, mmtext.TEXT_UNICODE, true, true);
    } else {
      appDes.setTypeface(getTypefaceTitle());
      ssgym.setTypeface(getTypefaceLight());
    }
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

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      super.onBackPressed();
      return true;
    }
    return false;
  }

}
