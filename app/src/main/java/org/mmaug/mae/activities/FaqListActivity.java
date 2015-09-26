package org.mmaug.mae.activities;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;
import org.maepaysoh.maepaysoh.MaePaySoh;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.adapters.EndlessRecyclerViewAdapter;
import org.maepaysoh.maepaysoh.adapters.FaqAdapter;
import org.maepaysoh.maepaysoh.utils.InternetUtils;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import org.maepaysoh.maepaysohsdk.FAQAPIHelper;
import org.maepaysoh.maepaysohsdk.MaePaySohApiWrapper;
import org.maepaysoh.maepaysohsdk.models.FAQ;
import org.maepaysoh.maepaysohsdk.utils.FaqAPIProperties;
import org.maepaysoh.maepaysohsdk.utils.FaqAPIPropertiesMap;
import org.mmaug.mae.base.BaseActivity;

import static org.maepaysoh.maepaysoh.utils.Logger.LOGD;
import static org.maepaysoh.maepaysoh.utils.Logger.makeLogTag;

/**
 * Created by Ye Lin Aung on 15/08/06.
 */
public class FaqListActivity extends BaseActivity
    implements FaqAdapter.ClickInterface, android.support.v7.widget.SearchView.OnQueryTextListener {

  private static String TAG = "FAQ_LIST_ACTIVITY";
  private RecyclerView mFaqListRecyclerView;
  private ProgressBar mProgressView;
  private View mErrorView;
  private Button mRetryBtn;
  private ViewUtils viewUtils;
  private LinearLayoutManager mLayoutManager;
  private FaqAdapter mFaqAdapter;
  private EndlessRecyclerViewAdapter mEndlessRecyclerViewAdapter;
  private int mCurrentPage = 1;
  private List<FAQ> mFaqDatas;
  private android.support.v7.widget.SearchView mSearchView;
  private MenuItem mSearchMenu;
  private FAQAPIHelper mFAQAPIHelper;
  private MaePaySohApiWrapper mMaePaySohApiWrapper;
  private DownFaqListAsync mDownFaqListAsync;
  private SearchFAQAsync mSearchFAQAsync;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_faq_list);

    Toolbar mToolbar = (Toolbar) findViewById(R.id.faq_list_toolbar);
    View mToolbarShadow = findViewById(R.id.faq_list_toolbar_shadow);

    mErrorView = findViewById(R.id.faq_list_error_view);
    mFaqListRecyclerView = (RecyclerView) findViewById(R.id.faq_list_recycler_view);
    mProgressView = (ProgressBar) findViewById(R.id.faq_list_progress_bar);
    mRetryBtn = (Button) mErrorView.findViewById(R.id.error_view_retry_btn);
    mProgressView.getIndeterminateDrawable()
        .setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);

    mToolbar.setTitle(getString(R.string.FaqList));
    hideToolBarShadowForLollipop(mToolbar, mToolbarShadow);

    setSupportActionBar(mToolbar);

    ActionBar mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      // Showing Back Arrow  <-
      mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    viewUtils = new ViewUtils(this);
    mLayoutManager = new LinearLayoutManager(this);
    mFaqListRecyclerView.setLayoutManager(mLayoutManager);
    mFaqAdapter = new FaqAdapter();
    mFaqAdapter.setOnItemClickListener(this);
    mMaePaySohApiWrapper = MaePaySoh.getMaePaySohWrapper();
    mFAQAPIHelper = mMaePaySohApiWrapper.getFaqApiHelper();
    mEndlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(FaqListActivity.this, mFaqAdapter,
        new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
          @Override public void onLoadMoreRequested() {
            loadFaqData(null);
          }
        });
    mFaqListRecyclerView.setAdapter(mEndlessRecyclerViewAdapter);
    if (InternetUtils.isNetworkAvailable(this)) {
      loadFaqData(null);
    } else {
      loadFromCache();
    }
    mRetryBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        loadFaqData(null);
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_faq, menu);
    mSearchMenu = menu.findItem(R.id.menu_search);
    mSearchView = (android.support.v7.widget.SearchView) mSearchMenu.getActionView();
    mSearchView.setOnQueryTextListener(this);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  private void loadFaqData(@Nullable String query) {
    TextView errorText = (TextView) mErrorView.findViewById(R.id.error_view_error_text);
    errorText.setText(getString(R.string.PleaseCheckNetworkAndTryAgain));
    mRetryBtn.setVisibility(View.VISIBLE);
    if (mErrorView.getVisibility() == View.VISIBLE) {
      mErrorView.setVisibility(View.GONE);
    }
    if (mCurrentPage == 1) {
      viewUtils.showProgress(mFaqListRecyclerView, mProgressView, true);
    }
    if (query != null && query.length() > 0) {
      mSearchFAQAsync = new SearchFAQAsync();
      mSearchFAQAsync.execute(query);
    } else {
      mDownFaqListAsync = new DownFaqListAsync();
      mDownFaqListAsync.execute(mCurrentPage);
    }
  }

  @Override public void onItemClick(View view, int position) {
    Intent goToFaqDetailIntent = new Intent();
    goToFaqDetailIntent.setClass(FaqListActivity.this, FaqDetailActivity.class);
    goToFaqDetailIntent.putExtra(FaqDetailActivity.FAQ_CONSTANT, mFaqDatas.get(position));
    startActivity(goToFaqDetailIntent);
  }

  @Override public boolean onQueryTextSubmit(String query) {
    return true;
  }

  @Override public boolean onQueryTextChange(String newText) {
    mCurrentPage = 1;
    if (InternetUtils.isNetworkAvailable(this)) {
      loadFaqData(newText);
    } else {
      searchFaqFromCache(newText);
    }
    LOGD(TAG, "searching");
    return true;
  }

  private void loadFromCache() {
    //Disable pagination in cache
    mEndlessRecyclerViewAdapter.onDataReady(false);
    try {
      mFaqDatas = mFAQAPIHelper.getFaqsFromCache();
      if (mFaqDatas != null && mFaqDatas.size() > 0) {
        viewUtils.showProgress(mFaqListRecyclerView, mProgressView, false);
        mFaqAdapter.setFaqs(mFaqDatas);
        mFaqAdapter.setOnItemClickListener(FaqListActivity.this);
      } else {
        viewUtils.showProgress(mFaqListRecyclerView, mProgressView, false);
        mErrorView.setVisibility(View.VISIBLE);
      }
    } catch (SQLException e) {
      viewUtils.showProgress(mFaqListRecyclerView, mProgressView, false);
      mErrorView.setVisibility(View.VISIBLE);
      e.printStackTrace();
    }
  }

  private void searchFaqFromCache(String keyword) {
    TextView errorText = (TextView) mErrorView.findViewById(R.id.error_view_error_text);
    errorText.setText(getString(R.string.PleaseCheckNetworkAndTryAgain));
    mRetryBtn.setVisibility(View.VISIBLE);
    if (mErrorView.getVisibility() == View.VISIBLE) {
      mErrorView.setVisibility(View.GONE);
    }

    if (keyword.length() > 0) {
      mFaqDatas = mFAQAPIHelper.searchFaqFromCache(keyword);
      if (mFaqDatas != null && mFaqDatas.size() > 0) {
        mFaqListRecyclerView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        mFaqAdapter.setFaqs(mFaqDatas);
        mFaqAdapter.setOnItemClickListener(FaqListActivity.this);
      } else {
        mFaqListRecyclerView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mRetryBtn.setVisibility(View.GONE);
        errorText.setText(R.string.search_not_found);
      }
    } else {
      loadFromCache();
    }
  }

  @Override protected void onPause() {
    super.onPause();
    if (mDownFaqListAsync != null) {
      mDownFaqListAsync.cancel(true);
    }
    if (mSearchFAQAsync != null) {
      mSearchFAQAsync.cancel(true);
    }
  }

  class DownFaqListAsync extends AsyncTask<Integer, Void, List<FAQ>> {

    @Override protected List<FAQ> doInBackground(Integer... integer) {
      mCurrentPage = integer[0];
      FaqAPIPropertiesMap faqAPIPropertiesMap = new FaqAPIPropertiesMap();
      faqAPIPropertiesMap.put(FaqAPIProperties.FIRST_PAGE, mCurrentPage);
      return mFAQAPIHelper.getFaqs(faqAPIPropertiesMap);
    }

    @Override protected void onPostExecute(List<FAQ> faqs) {
      super.onPostExecute(faqs);
      TextView errorText = (TextView) mErrorView.findViewById(R.id.error_view_error_text);
      errorText.setText(getString(R.string.PleaseCheckNetworkAndTryAgain));
      mRetryBtn.setVisibility(View.VISIBLE);
      if (mErrorView.getVisibility() == View.VISIBLE) {
        mErrorView.setVisibility(View.GONE);
      }
      if (faqs.size() > 0) {
        if (mCurrentPage == 1) {
          viewUtils.showProgress(mFaqListRecyclerView, mProgressView, false);
          mFaqDatas = faqs;
        } else {
          mFaqDatas.addAll(faqs);
        }
        mFaqAdapter.setFaqs(mFaqDatas);
        mEndlessRecyclerViewAdapter.onDataReady(true);
        mCurrentPage++;
      } else {
        if (mCurrentPage == 1) {
          loadFromCache();
        }
        mEndlessRecyclerViewAdapter.onDataReady(false);
      }
    }
  }

  class SearchFAQAsync extends AsyncTask<String, Void, List<FAQ>> {

    @Override protected List<FAQ> doInBackground(String... strings) {
      return mFAQAPIHelper.searchFaq(strings[0]);
    }

    @Override protected void onPostExecute(List<FAQ> faqs) {
      super.onPostExecute(faqs);
      //Hide Progress on success
      viewUtils.showProgress(mFaqListRecyclerView, mProgressView, false);
      if (faqs.size() > 0) {
        mFaqListRecyclerView.setVisibility(View.VISIBLE);
        if (mCurrentPage == 1) {
          mFaqDatas = faqs;
        } else {
          mFaqDatas.addAll(faqs);
        }
        mFaqAdapter.setFaqs(mFaqDatas);
        mEndlessRecyclerViewAdapter.onDataReady(true);
        mCurrentPage++;
      } else {
        mFaqListRecyclerView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        TextView errorText = (TextView) mErrorView.findViewById(R.id.error_view_error_text);
        errorText.setText(R.string.search_not_found);
        mRetryBtn.setVisibility(View.GONE);
      }
    }
  }
}
