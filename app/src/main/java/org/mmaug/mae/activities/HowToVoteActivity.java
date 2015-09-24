package org.mmaug.mae.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.HowToVoteTimelineAdapter;
import org.mmaug.mae.base.BaseActivity;

/**
 * Created by poepoe on 22/9/15.
 */
public class HowToVoteActivity extends BaseActivity implements AdapterView.OnItemClickListener {

  @Bind(R.id.rv_how_to_vote) RecyclerView mRecyclerView;
  private HowToVoteTimelineAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    mRecyclerView.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    adapter = new HowToVoteTimelineAdapter();
    adapter.setOnItemClickListener(this);
    mRecyclerView.setAdapter(adapter);
    fakeData();

  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_how_to_vote;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return "မဲပေးရာတွင်သိသင့်သည်များ";
  }

  private void fakeData() {
    ArrayList<HowToVoteTimelineAdapter.HTVObject> objects = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      HowToVoteTimelineAdapter.HTVObject object = new HowToVoteTimelineAdapter.HTVObject();
      object.setTitle("မဲရုံဖွင့်ချိန်");
      object.setMessage("နံနက်၆နာရီ−ညနေ၆နာရီ");
      object.setWarning(
          "မဲရုံပိတ\u103Aခ\u103Bိန\u103A မတိုင\u103Aမီ မဲရုံသို့ သ\u103Dားရောက\u103A မဲပေးရန\u103A လိုအပ\u103Aပ\u102Bသည\u103A။");
      objects.add(object);
    }
    adapter.setHtvObjectList(objects);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      super.onBackPressed();
      return true;
    }
    return false;
  }

  @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    startActivity(new Intent(this, VoteGameActivity.class));
  }
}
