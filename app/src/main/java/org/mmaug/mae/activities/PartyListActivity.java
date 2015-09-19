package org.mmaug.mae.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.EndlessRecyclerViewAdapter;
import org.mmaug.mae.adapter.PartyAdapter;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.listener.EndlessRecyclerOnScrollListener;
import org.mmaug.mae.models.Party;
import org.mmaug.mae.models.PartyReturnObject;
import org.mmaug.mae.rest.RESTClient;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by indexer on 9/19/15.
 */

public class PartyListActivity extends BaseActivity {
  String TAG_TOOLBAR = "Party";
  Integer currentPage = 1;
  int totalPageCount;

  @Bind(R.id.party_list_recycler_view) RecyclerView mPartyListRecyclerView;
  PartyAdapter mPartyAdapter;
  private EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;
  private List<Party> mParties;
  private List<Party> mPartiesDemo = new ArrayList<>();

  @Override protected int getLayoutResource() {
    return R.layout.activity_party;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return TAG_TOOLBAR;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    mPartyListRecyclerView.setHasFixedSize(true);
    mPartyListRecyclerView.setLayoutManager(new LinearLayoutManager(PartyListActivity.this));
    fetchParties(currentPage.toString());

    mPartyListRecyclerView.addOnScrollListener(
        new EndlessRecyclerOnScrollListener(new LinearLayoutManager(PartyListActivity.this)) {
          @Override public void onLoadMore(int current_page) {
            currentPage = current_page;
            fetchParties(currentPage.toString());
          }
        });
  }

  private void fetchParties(String page) {
    Map<String, String> params = new HashMap<>();
    params.put(Config.PAGE, page);
    params.put(Config.PER_PAGE, "20");
    final Call<PartyReturnObject> partyCall = RESTClient.getMPSService().getPartyList(params);
    partyCall.enqueue(new Callback<PartyReturnObject>() {
      @Override public void onResponse(Response<PartyReturnObject> response) {
        totalPageCount = response.body().getMeta().getTotal_pages();
        if (currentPage < totalPageCount) {
          if (response.body().getData().size() > 0) {
            if (mPartiesDemo.size() > 0) {
              mPartiesDemo.clear();
            }
            mPartiesDemo = response.body().getData();
            mParties = new ArrayList<Party>();
            mParties.addAll(mPartiesDemo);
            mPartyAdapter = new PartyAdapter();
            mPartyAdapter.setParties(mParties);
            mPartyListRecyclerView.setAdapter(mPartyAdapter);
            currentPage++;
          }
        }
      }

      @Override public void onFailure(Throwable t) {

      }
    });
  }
}
