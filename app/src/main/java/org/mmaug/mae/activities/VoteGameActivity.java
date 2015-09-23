package org.mmaug.mae.activities;

import android.os.Bundle;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.game.BoardView;

/**
 * Created by poepoe on 24/9/15.
 */
public class VoteGameActivity extends BaseActivity {

  @Bind(R.id.board) BoardView mBoardView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.cardview_start_game) void startGame() {
    mBoardView.enableTouch(true);
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
