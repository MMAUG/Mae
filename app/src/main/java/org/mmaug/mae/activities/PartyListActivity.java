package org.mmaug.mae.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.EndlessRecyclerViewAdapter;
import org.mmaug.mae.adapter.PartyAdapter;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.models.Party;
import org.mmaug.mae.models.PartyReturnObject;
import org.mmaug.mae.rest.RESTClient;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by indexer on 9/19/15.
 */

public class PartyListActivity extends BaseActivity
    implements EndlessRecyclerViewAdapter.RequestToLoadMoreListener {
  String TAG_TOOLBAR = "Party";
  Integer page = 0;

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
    mPartyAdapter = new PartyAdapter();
    mPartyAdapter.setParties(mParties);
    mPartyListRecyclerView.setHasFixedSize(true);
    mPartyListRecyclerView.setLayoutManager(new LinearLayoutManager(PartyListActivity.this));
    endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(this, mPartyAdapter, this);
    mPartyListRecyclerView.setAdapter(endlessRecyclerViewAdapter);
  }

  private void fetchParties() {
    Map<String, String> params = new HashMap<>();
    params.put("page", page.toString());
    params.put("per_page", "20");
    params.put("token", "3db8827d-2521-57be-987a-e9e366874d4b");
    final Call<PartyReturnObject> partyCall = RESTClient.getMPSService().getPartyList(params);
    partyCall.enqueue(new Callback<PartyReturnObject>() {
      @Override public void onResponse(Response<PartyReturnObject> response) {
        if (response.body().getData().size() > 0) {
          if (mPartiesDemo.size() > 0) {
            mPartiesDemo.clear();
          }
          mPartiesDemo = response.body().getData();
          mParties = new ArrayList<Party>();
          mParties.addAll(mPartiesDemo);
          mPartyAdapter.setParties(mParties);
          if (mPartyAdapter.getItemCount() >= 20) {
            // load 100 items for demo only
            endlessRecyclerViewAdapter.onDataReady(false);
          } else {
            // notify the data is ready
            endlessRecyclerViewAdapter.onDataReady(true);
          }
        }
      }

      @Override public void onFailure(Throwable t) {

      }
    });
  }

  @Override public void onLoadMoreRequested() {
    page = page + 1;
    fetchParties();
  }
}
