package org.mmaug.mae.activities;

import android.os.Bundle;
import android.view.MenuItem;
import butterknife.ButterKnife;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;

/**
 * Created by poepoe on 24/9/15.
 */
public class VoteGameActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_vote_game;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return "ခိုင်လုံမဲဖြစ်စေရန်";
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      super.onBackPressed();
      return true;
    }
    return false;
  }
}
