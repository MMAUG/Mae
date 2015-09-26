package org.mmaug.mae.activities;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.aut.utillib.circular.animation.CircularAnimationUtils;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.game.BoardView;
import org.mmaug.mae.utils.FontCache;
import org.mmaug.mae.utils.MixUtils;

/**
 * Created by poepoe on 24/9/15.
 */
public class VoteGameActivity extends BaseActivity implements BoardView.GameListener {

  @Bind(R.id.board) BoardView mBoardView;
  @Bind(R.id.ll_board_frame) LinearLayout mBoardFame;
  @Bind(R.id.scrollView) ScrollView mScrollView;
  @Bind(R.id.tv_start_game) TextView mTextView;

  int y = 0, scrollTo = 1, spacingMajor = 0;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    spacingMajor = (int) MixUtils.convertDpToPixel(this, 16);

    // set height for paper
    ViewGroup.LayoutParams params = mBoardFame.getLayoutParams();

    //top and bottom margin
    int margin = spacingMajor * 3;
    //actionbar size to subtract too
    int actionBarSize = (int) MixUtils.convertDpToPixel(this, 56);

    // subtract margin to fix the screen
    params.height = MixUtils.calculateHeight(getWindowManager()) - (margin + actionBarSize);
    mBoardFame.setLayoutParams(params);

    //listener for game play
    mBoardView.setGameListener(this);

    //to check scrollview is at board
    mScrollView.post(new Runnable() {
      @Override public void run() {
        ViewTreeObserver observer = mScrollView.getViewTreeObserver();
        observer.addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
          @Override public void onScrollChanged() {
            y = mScrollView.getScrollY();
          }
        });
      }
    });
  }

  @OnClick(R.id.cardview_start_game) void startGame() {
    if (y != scrollTo) {
      scrollTo = mBoardFame.getTop() - spacingMajor;
      ObjectAnimator objectAnimator =
          ObjectAnimator.ofInt(mScrollView, "scrollY", y, scrollTo).setDuration(500);
      objectAnimator.start();
    }
    //start the game
    mTextView.setText("ဆန္ဒပြုပါ");
    mBoardView.enableTouch(true);
    mBoardView.reset();
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

  @Override public void checkValidity(BoardView.ValidityStatus status) {
    mTextView.setText(getString(R.string.check));
    final Dialog voteResultDialog =
        new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
    View view;
    if (status == BoardView.ValidityStatus.valid) {
      view = getLayoutInflater().inflate(R.layout.dialog_valid_vote, null);
      View myTargetView = view.findViewById(R.id.circle_full);
      View mySourceView = view.findViewById(R.id.circle_empty);
      View okBtn = view.findViewById(R.id.voter_check_ok_btn);
      Typeface typefaceTitle = FontCache.get("MyanmarAngoun.ttf", this);
      Typeface typefacelight = FontCache.get("pyidaungsu.ttf", this);
      //text message
      TextView myTextView = (TextView) view.findViewById(R.id.tv_vote_message);
      myTextView.setTypeface(typefaceTitle);
      String warning = getString(R.string.how_to_vote_placeholder_text,
          getString(R.string.correct_vote_message));
      myTextView.setText(Html.fromHtml(warning));
      //myTargetView & mySourceView are children in the CircularFrameLayout
      float finalRadius = CircularAnimationUtils.hypo(200, 200);
      ////getCenter computes from 2 view: One is touched, and one will be animated, but you can use anything for center
      //int[] center = CircularAnimationUtils.getCenter(fab, myTargetView);
      ObjectAnimator animator =
          CircularAnimationUtils.createCircularTransform(myTargetView, mySourceView, 1, 2, 0F,
              finalRadius);
      animator.setInterpolator(new AccelerateDecelerateInterpolator());
      animator.setDuration(1500);
      voteResultDialog.setContentView(view);
      voteResultDialog.show();
      animator.start();
      okBtn.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (voteResultDialog.isShowing()) {
            voteResultDialog.dismiss();
          }
        }
      });
    } else {
      view = getLayoutInflater().inflate(R.layout.dialog_invalid_vote, null);
      View okBtn = view.findViewById(R.id.voter_check_ok_btn);
      TextView myTextView = (TextView) view.findViewById(R.id.tv_vote_message);
      String warning =
          getString(R.string.incorrect_vote_message, getString(R.string.correct_vote_message));
      myTextView.setText(warning);
      Typeface typefaceTitle = FontCache.get("MyanmarAngoun.ttf", this);
      Typeface typefacelight = FontCache.get("pyidaungsu.ttf", this);
      myTextView.setTypeface(typefaceTitle);
      okBtn.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (voteResultDialog.isShowing()) {
            voteResultDialog.dismiss();
          }
        }
      });
      voteResultDialog.setContentView(view);
      voteResultDialog.show();
    }
  }
}
