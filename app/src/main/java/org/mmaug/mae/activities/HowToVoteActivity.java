package org.mmaug.mae.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;

/**
 * Created by poepoe on 22/9/15.
 */
public class HowToVoteActivity extends BaseActivity {

  @Bind(R.id.rv_how_to_vote) RecyclerView mRecyclerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    mRecyclerView.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

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
