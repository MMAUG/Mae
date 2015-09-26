package org.mmaug.mae.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import org.mmaug.mae.R;
import org.mmaug.mae.models.FAQ;
import org.mmaug.mae.utils.FontCache;

/**
 * Created by yemyatthu on 8/6/15.
 */
public class FaqDetailActivity extends AppCompatActivity {
  public static final String FAQ_CONSTANT = "org.maepaesoh.maepaesoh.ui.FaqDetailActivity";

  // Ui elements
  private Toolbar mToolbar;
  private View mToolbarShadow;
  private TextView mQuestion;
  private TextView mAnswer;

  private FAQ mFAQ;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_faq_detail);
    mToolbar = (Toolbar) findViewById(R.id.faq_detail_toolbar);

    mQuestion = (TextView) findViewById(R.id.faq_question);
    mAnswer = (TextView) findViewById(R.id.faq_answer);
    setSupportActionBar(mToolbar);
    ActionBar mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      // Showing Back Arrow  <-
      mActionBar.setDisplayHomeAsUpEnabled(true);
    }
    mFAQ = (FAQ) getIntent().getSerializableExtra(FAQ_CONSTANT);
    if (mFAQ != null) {
      mActionBar.setTitle(mFAQ.getQuestion());
      Typeface typefaceTitle = FontCache.get("MyanmarAngoun.ttf", this);
      Typeface typefacelight = FontCache.get("pyidaungsu.ttf", this);
      mQuestion.setText(mFAQ.getQuestion());
      mAnswer.setText(mFAQ.getAnswer());
      mQuestion.setTypeface(typefaceTitle);
      mAnswer.setTypeface(typefacelight);
    }
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
