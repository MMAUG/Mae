package org.mmaug.mae.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.models.FAQ;
import org.mmaug.mae.utils.MMTextUtils;

/**
 * Created by yemyatthu on 8/6/15.
 */
public class FaqDetailActivity extends BaseActivity {
  public static final String FAQ_CONSTANT = "org.maepaesoh.maepaesoh.ui.FaqDetailActivity";

  @Bind(R.id.faq_question) TextView mQuestion;
  @Bind(R.id.faq_answer) TextView mAnswer;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    FAQ mFAQ = (FAQ) getIntent().getSerializableExtra(FAQ_CONSTANT);
    if (mFAQ != null) {
      setToolbarTitle(mFAQ.getQuestion());
      mQuestion.setText(mFAQ.getQuestion());
      mAnswer.setText(mFAQ.getAnswer());
      if (isUnicode()) {
        mQuestion.setTypeface(getTypefaceTitle());
        mAnswer.setTypeface(getTypefaceLight());
      } else {
        MMTextUtils.getInstance(this).prepareMultipleViews(mQuestion, mAnswer);
      }
    }
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_faq_detail;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return "FAQ";
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
