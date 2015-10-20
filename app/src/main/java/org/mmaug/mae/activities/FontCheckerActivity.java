package org.mmaug.mae.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

  String font = null;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    MMTextUtils.getInstance(this).prepareMultipleViews(tvDes, tvSaveFont);
    rgFont.setOnCheckedChangeListener(this);

  }

  @Override protected void onResume() {
    super.onResume();
    if (UserPrefUtils.getInstance(this).isFristTime()) {
      UserPrefUtils.getInstance(this).noLongerFirstTime();
    } else {
      if (isUnicode()) {
        rgFont.check(R.id.rb_uni);
      } else {
        rgFont.check(R.id.rb_zg);
      }
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
    if (font != null) {
      UserPrefUtils.getInstance(this).saveTextPref(font);
      startActivity(new Intent(this, MainActivity.class));
      finish();
    } else {
      Toast toast = new Toast(this);
      TextView textView = new TextView(this);
      textView.setText("ဖောင်အား ရွေးချယ်ပေးပါ။");
      MMTextUtils.getInstance(this).prepareSingleView(textView);
      textView.setPadding(16, 16, 16, 16);
      textView.setTextColor(Color.WHITE);
      textView.setBackgroundColor(getResources().getColor(R.color.accent_color));
      toast.setView(textView);
      toast.setGravity(Gravity.CENTER, 0, 10);
      toast.show();
    }
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
