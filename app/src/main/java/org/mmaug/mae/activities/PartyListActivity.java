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

public class PartyListActivity extends BaseActivity {
  String TAG_TOOLBAR = "Party";

  @Bind(R.id.party_list_recycler_view) RecyclerView mPartyListRecyclerView;
  PartyAdapter mPartyAdapter;
  private List<Party> mParties;

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
    fetchParties();
  }

  private void fetchParties() {
    Map<String, String> params = new HashMap<>();
    params.put("token", "3db8827d-2521-57be-987a-e9e366874d4b");
    final Call<PartyReturnObject> partyCall = RESTClient.getMPSService().getPartyList(params);
    partyCall.enqueue(new Callback<PartyReturnObject>() {
      @Override public void onResponse(Response<PartyReturnObject> response) {
        if (response.body().getData().size() > 0) {
          mParties = response.body().getData();
          mPartyAdapter = new PartyAdapter();
          mPartyAdapter.setParties(mParties);
          mPartyListRecyclerView.setHasFixedSize(true);
          mPartyListRecyclerView.setLayoutManager(new GridLayoutManager(PartyListActivity.this, 2));
          mPartyListRecyclerView.setAdapter(mPartyAdapter);
        }
    }

    @Override public void onFailure (Throwable t){

    }
  }

  );
}
}
