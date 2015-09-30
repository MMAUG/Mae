package org.mmaug.mae.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.UserPrefUtils;

/**
 * Created by poepoe on 30/9/15.
 */
public class FontCheckerActivity extends BaseActivity
    implements RadioGroup.OnCheckedChangeListener {
  @Bind(R.id.tv_choose_des) TextView tvDes;
  @Bind(R.id.tv_save_font) TextView tvSaveFont;
  @Bind(R.id.rg_font) RadioGroup rgFont;
  @Bind(R.id.rb_uni) RadioButton rbUni;

  String font = Config.UNICODE;
  MMTextUtils mmTextUtils;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    mmTextUtils = new MMTextUtils(this);
    mmTextUtils.prepareMultipleViews(tvDes, tvSaveFont);
    rgFont.setOnCheckedChangeListener(this);
    rbUni.setChecked(true);
  }

  @Override protected void onResume() {
    super.onResume();
    if (!UserPrefUtils.getInstance(this).isFristTime()) {
      startActivity(new Intent(this, MainActivity.class));
    } else {
      UserPrefUtils.getInstance(this).noLongerFirstTime();
    }
  }

  @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
    if (checkedId == R.id.rb_zg) {
      font = Config.ZAWGYI;
    } else {
      font = Config.UNICODE;
    }
  }

  @OnClick(R.id.cardview_save_font) void saveFont() {
    UserPrefUtils.getInstance(this).saveTextPref(font);
    startActivity(new Intent(this, MainActivity.class));
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_font_checker;
  }

  @Override protected boolean getHomeUpEnabled() {
    return false;
  }

  @Override protected boolean needToolbar() {
    return false;
  }

  @Override protected String getToolbarText() {
    return null;
  }
}
