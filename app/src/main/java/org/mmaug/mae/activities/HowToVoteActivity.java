package org.mmaug.mae.activities;

import android.os.Bundle;
import butterknife.ButterKnife;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;

/**
 * Created by poepoe on 22/9/15.
 */
public class HowToVoteActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_how_to_vote;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return "မဲပေးရာတွင်သိသင့်သည်များ";
  }
}
