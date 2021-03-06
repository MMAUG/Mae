package org.mmaug.mae.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.CandidateSearchAdapter;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.models.CandidateSearchResult;
import org.mmaug.mae.rest.RESTClient;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.MixUtils;
import org.mmaug.mae.utils.RestCallback;
import org.mmaug.mae.view.SpacesItemDecoration;
import retrofit.Call;
import retrofit.Response;
import tr.xip.errorview.ErrorView;

public class SearchListCandidateActivity extends BaseActivity
    implements ErrorView.RetryListener, AdapterView.OnItemClickListener {

  @Bind(R.id.rv_search) RecyclerView mCandidateListRecyclerView;
  @Bind(R.id.progressBar) ProgressBar mProgressBar;
  @Bind(R.id.empty_search_result_view) ErrorView mErrorView;
  String searchWord;
  ArrayList<CandidateSearchResult> candidateSearchResults = new ArrayList<>();
  private CandidateSearchAdapter candidateSearchAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    mErrorView.setOnRetryListener(this);
    mProgressBar.setVisibility(View.VISIBLE);
    searchWord = getIntent().getStringExtra("searchWord");
    String unicode =
        MMTextUtils.getInstance(SearchListCandidateActivity.this).zgToUni(searchWord.toString());
    inflateCandidateSearchAdapter(unicode.toString());
    MixUtils.toggleVisibilityWithAnim(mCandidateListRecyclerView, false);
    MixUtils.toggleVisibilityWithAnim(mErrorView, false);
    mCandidateListRecyclerView.setLayoutManager(
        new LinearLayoutManager(SearchListCandidateActivity.this, LinearLayoutManager.VERTICAL,
            false));
    candidateSearchAdapter = new CandidateSearchAdapter();
    candidateSearchAdapter.setOnItemClickListener(SearchListCandidateActivity.this);
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_search_candidate;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return "အမတ်လောင်းရှာဖွေမှုရလဒ်";
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      super.onBackPressed();
      return true;
    }
    return false;
  }

  private void inflateCandidateSearchAdapter(String searchName) {
    Map<String, Integer> limitParams = new HashMap<>();
    limitParams.put("limit", 20);
    Call<ArrayList<CandidateSearchResult>> candidateAutoSearch =
        RESTClient.getService(this).searchCandidate(searchName, limitParams);
    candidateAutoSearch.enqueue(new RestCallback<ArrayList<CandidateSearchResult>>() {
      @Override public void onResponse(Response<ArrayList<CandidateSearchResult>> response) {
        candidateSearchResults.clear();
        candidateSearchResults.addAll(response.body());
        candidateSearchAdapter.setCandidates(candidateSearchResults);
        candidateSearchAdapter.notifyDataSetChanged();

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_micro);
        mCandidateListRecyclerView.setHasFixedSize(true);
        mCandidateListRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mCandidateListRecyclerView.setAdapter(candidateSearchAdapter);
        MixUtils.toggleVisibilityWithAnim(mCandidateListRecyclerView, true);
        mProgressBar.setVisibility(View.GONE);
        if (candidateSearchResults.size() == 0) {
          MixUtils.toggleVisibilityWithAnim(mErrorView, true);
        } else {
          MixUtils.toggleVisibilityWithAnim(mErrorView, false);
        }
      }
    });
  }

  @Override public void onRetry() {

  }

  @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    Intent intenttoCandidateDetail =
        new Intent(SearchListCandidateActivity.this, CandidateDetailActivity.class);
    intenttoCandidateDetail.putExtra("searchResult", candidateSearchResults.get(i));
    startActivity(intenttoCandidateDetail);
  }
}
