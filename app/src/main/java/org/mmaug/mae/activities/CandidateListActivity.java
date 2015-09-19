package org.mmaug.mae.activities;

import android.os.Bundle;
import butterknife.ButterKnife;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;

public class CandidateListActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_candidate;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return "အမတ်လောင်းများ";
  }
}
