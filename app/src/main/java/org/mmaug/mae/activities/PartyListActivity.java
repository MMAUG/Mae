package org.mmaug.mae.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
  private List<Party> mParties = new ArrayList<>();
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
    mPartyListRecyclerView.setHasFixedSize(true);
    mPartyListRecyclerView.setLayoutManager(new GridLayoutManager(PartyListActivity.this, 2));
    endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(this, mPartyAdapter,
        new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
          @Override public void onLoadMoreRequested() {
            fetchParties();
          }
        });
    mPartyListRecyclerView.setAdapter(endlessRecyclerViewAdapter);
  }

  private void fetchParties() {
    Map<String, String> params = new HashMap<>();
    params.put(Config.PAGE, currentPage.toString());
    params.put(Config.PER_PAGE, "20");
    final Call<PartyReturnObject> partyCall = RESTClient.getMPSService().getPartyList(params);
    partyCall.enqueue(new Callback<PartyReturnObject>() {
                        @Override public void onResponse(Response<PartyReturnObject> response) {
                          totalPageCount = response.body().getMeta().getTotal_pages();
                          Log.e("Size", "" + response.body().getData().size());
                          if (response.body().getData().size() > 0) {
                            if (currentPage == 1) {
                              mParties = response.body().getData();
                            } else {
                              mParties.addAll(response.body().getData());
                            }
                            mPartyAdapter.setParties(mParties);
                            endlessRecyclerViewAdapter.onDataReady(true);
                            currentPage++;
                          } else {
                            endlessRecyclerViewAdapter.onDataReady(false);
                          }
                        }

                        @Override public void onFailure(Throwable t) {

                        }
                      }

    );
  }
}
