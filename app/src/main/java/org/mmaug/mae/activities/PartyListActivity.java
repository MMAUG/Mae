package org.mmaug.mae.activities;

import android.os.Bundle;
import android.util.Log;
import butterknife.ButterKnife;
import java.util.HashMap;
import java.util.Map;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
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
    Map<String, String> params = new HashMap<>();
    params.put("token", "3db8827d-2521-57be-987a-e9e366874d4b");
    final Call<PartyReturnObject> partyCall = RESTClient.getMPSService().getPartyList(params);
    partyCall.enqueue(new Callback<PartyReturnObject>() {
      @Override public void onResponse(Response<PartyReturnObject> response) {
        Log.e("Response", "Value is" + response.body().getData().get(0).getPartyName());
      }

      @Override public void onFailure(Throwable t) {

      }
    });
  }
}
