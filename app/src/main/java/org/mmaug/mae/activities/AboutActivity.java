package org.mmaug.mae.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.mmaug.mae.BuildConfig;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.mmtext;

/**
 * Created by poepoe on 2/10/15.
 */
public class AboutActivity extends BaseActivity {

  @Bind(R.id.tv_version) TextView version;
  @Bind(R.id.tv_app_des) TextView appDes;
  @Bind(R.id.ssygm) TextView ssgym;
  @Bind(R.id.tv_terms) TextView term;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    version.setText(getString(R.string.version, BuildConfig.VERSION_NAME));
    if (!isUnicode()) {
      mmtext.prepareActivity(this, mmtext.TEXT_UNICODE, true, true);
    } else {
      appDes.setTypeface(getTypefaceTitle());
      ssgym.setTypeface(getTypefaceLight());
      term.setTypeface(getTypefaceTitle());
    }
  }

  @OnClick(R.id.rate_us) void rateApp() {
    final String appPackageName = getPackageName();
    try {
      startActivity(
          new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
    } catch (Exception anfe) {
      startActivity(new Intent(Intent.ACTION_VIEW,
          Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
    }
  }

  @OnClick(R.id.terms) void showPolicy() {
    AlertDialog.Builder db = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

    View view = getLayoutInflater().inflate(R.layout.dialog_terms, null);
    TextView title = (TextView) view.findViewById(R.id.tv_policy_title);
    TextView body = (TextView) view.findViewById(R.id.tv_terms);

    if (isUnicode()) {
      title.setTypeface(getTypefaceTitle());
      body.setTypeface(getTypefaceLight());
    } else {
      MMTextUtils.getInstance(this).prepareMultipleViews(title, body);
    }
    db.setView(view);
    db.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    });
    AlertDialog dialog = db.create();
    dialog.show();
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
