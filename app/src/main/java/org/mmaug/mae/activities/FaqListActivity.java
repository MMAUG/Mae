package org.mmaug.mae.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.EndlessRecyclerViewAdapter;
import org.mmaug.mae.adapter.FaqAdapter;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.models.FAQ;
import org.mmaug.mae.models.FAQListReturnObject;
import org.mmaug.mae.rest.RESTClient;
import org.mmaug.mae.rest.RESTService;
import org.mmaug.mae.utils.ConnectionManager;
import org.mmaug.mae.utils.ViewUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Ye Lin Aung on 15/08/06.
 */
public class FaqListActivity extends BaseActivity
    implements FaqAdapter.ClickInterface, android.support.v7.widget.SearchView.OnQueryTextListener {

  private static String TAG = "FAQ_LIST_ACTIVITY";
  @Bind(R.id.faq_list_recycler_view) RecyclerView mFaqListRecyclerView;
  @Bind(R.id.faq_list_progress_bar) ProgressBar mProgressView;
  private ViewUtils viewUtils;
  private LinearLayoutManager mLayoutManager;
  private FaqAdapter mFaqAdapter;
  private EndlessRecyclerViewAdapter mEndlessRecyclerViewAdapter;
  private int mCurrentPage = 1;
  private List<FAQ> mFaqDatas;
  private android.support.v7.widget.SearchView mSearchView;
  private MenuItem mSearchMenu;
  private RESTService mRESTService;


  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    mFaqListRecyclerView = (RecyclerView) findViewById(R.id.faq_list_recycler_view);
    mProgressView = (ProgressBar) findViewById(R.id.faq_list_progress_bar);

    //mProgressView.getIndeterminateDrawable()
    //    .setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);

    mRESTService = RESTClient.getService(this);
    viewUtils = new ViewUtils(this);
    mLayoutManager = new LinearLayoutManager(this);
    mFaqListRecyclerView.setLayoutManager(mLayoutManager);
    mFaqAdapter = new FaqAdapter();
    mFaqAdapter.setOnItemClickListener(this);
    mEndlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(FaqListActivity.this, mFaqAdapter,
        new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
          @Override public void onLoadMoreRequested() {
            loadFaqData(null);
          }
        });
    mFaqListRecyclerView.setAdapter(mEndlessRecyclerViewAdapter);
    if (ConnectionManager.isConnected(this)) {
      loadFaqData(null);
    }
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_faq_list;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return getString(R.string.FaqList);
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
    if (mCurrentPage == 1) {
      viewUtils.showProgress(mFaqListRecyclerView, mProgressView, true);
    }
    Map<String,String> tokenParams = new HashMap<>();
    tokenParams.put("font","unicode");
    tokenParams.put("page",String.valueOf(mCurrentPage));
    tokenParams.put("per_page",String.valueOf(1000));
    if (query != null && query.length() > 0) {
      Call<FAQListReturnObject> callback = mRESTService.searchFaq(query,tokenParams);
      callback.enqueue(new Callback<FAQListReturnObject>() {
        @Override public void onResponse(Response<FAQListReturnObject> response) {
          List<FAQ> faqs = response.body().getData();
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
          }

        }

        @Override public void onFailure(Throwable t) {

        }
      });
    } else {
      Call<FAQListReturnObject> callback = mRESTService.listFaqs(tokenParams);
      callback.enqueue(new Callback<FAQListReturnObject>() {
        @Override public void onResponse(Response<FAQListReturnObject> response) {
          if(response.isSuccess()) {
            List<FAQ> faqs = response.body().getData();
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
            }
          }
          mEndlessRecyclerViewAdapter.onDataReady(false);
        }

        @Override public void onFailure(Throwable t) {

        }
      });

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
    if (ConnectionManager.isConnected(this)) {
      loadFaqData(newText);
    }
    return true;
  }

}
