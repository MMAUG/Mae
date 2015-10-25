package org.mmaug.mae.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.UserPrefUtils;

/**
 * Created by poepoe on 30/9/15.
 */
public class FontCheckerActivity extends BaseActivity
    implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
  TextView tvDes;
  TextView tvSaveFont;
  RadioGroup rgFont;
  CardView saveFont;

  String font = null;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    tvDes = (TextView) findViewById(R.id.tv_choose_des);
    tvSaveFont = (TextView) findViewById(R.id.tv_save_font);
    rgFont = (RadioGroup) findViewById(R.id.rg_font);
    saveFont = (CardView) findViewById(R.id.cardview_save_font);

    MMTextUtils.getInstance(this).prepareMultipleViews(tvDes, tvSaveFont);
    rgFont.setOnCheckedChangeListener(this);
    saveFont.setOnClickListener(this);
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

  private void saveFont() {
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

  /**
   * Called when a view has been clicked.
   *
   * @param v The view that was clicked.
   */
  @Override public void onClick(View v) {
    saveFont();
  }
}
