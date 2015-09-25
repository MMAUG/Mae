package org.mmaug.mae.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.ToyFigurePagerAdapter;
import org.mmaug.mae.models.CurrentCollection;
import org.mmaug.mae.models.Party;
import org.mmaug.mae.rest.RESTClient;
import org.mmaug.mae.rest.RESTService;
import org.mmaug.mae.utils.MixUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class PartyDetailActivity extends AppCompatActivity {
  @Bind(R.id.backdrop) ImageView party_image;
  @Bind(R.id.party_count) TextView mPartyCount;
  @Bind(R.id.party_date) TextView mPartyDate;
  @Bind(R.id.party_number) TextView mPartyNumber;
  @Bind(R.id.party_name) TextView mPartyName;
  @Bind(R.id.constituency) TextView mConstituency;
  @Bind(R.id.party_headquarter) TextView mPartyHeadQuarter;
  @Bind(R.id.party_leader) TextView mPartyLeader;
  @Bind(R.id.current_year_tab) TabLayout mCurrentTabLayout;
  @Bind(R.id.current_year_view_pager) ViewPager mCurrentViewPager;
  @Bind(R.id.prev_year_tab) TabLayout mPrevTabLayout;
  @Bind(R.id.prev_year_view_pager) ViewPager mPrevViewPager;

  private RESTService partyDetailRestService;
  private int currentAmyotharCount;
  private int currentPyithuHlutaw;
  private int currentTineDaythaHlutaw;
  private CurrentCollection mAmyothaCurrentCollection;
  private CurrentCollection mPyithuCurrentCollection;
  private  Map<String,Integer> currentCandidateCount = new HashMap<>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_party_detail);
    ButterKnife.bind(this);

    final Party party = (Party) getIntent().getSerializableExtra("party");
    Glide.with(this).load(party.getPartyFlag()).into(party_image);
    if (party.getMemberCount() != null && party.getMemberCount().length() > 0) {
      mPartyCount.setText(MixUtils.convertToBurmese(String.valueOf(party.getMemberCount())));
    } else {
      mPartyCount.setText("-");
    }
    mPartyDate.setText(MixUtils.convertToBurmese(party.getEstablishmentDateString()));
    mPartyNumber.setText(MixUtils.convertToBurmese(party.getApprovedPartyNumber()));
    mPartyName.setText(party.getPartyName());
    mConstituency.setText(party.getRegion());
    List<String> leaders = party.getLeadership();
    for (String leader : leaders) {
      if (leaders.indexOf(leader) + 1 == leaders.size()) {
        mPartyLeader.append(leader);
      } else {
        mPartyLeader.append(leader + "\n");
      }
    }
    mPartyHeadQuarter.setText(party.getHeadquarters());
    List<String> contacts = party.getContact();
    for (String contact : contacts) {
      if (contacts.indexOf(contact) + 1 == contacts.size()) {
        mPartyLeader.append(contact);
      } else {
        mPartyLeader.append(contact + "\n");
      }
    }

    partyDetailRestService = RESTClient.getService();
    Call<JsonObject> candidateCountCall = partyDetailRestService.getCandidateCount(party.getPartyId());
    System.out.println("PARTY ID   "+party.getPartyId());
    final Call<JsonObject> currentCount = partyDetailRestService.getCurrentCount();
    candidateCountCall.enqueue(new Callback<JsonObject>() {
      @Override public void onResponse(Response<JsonObject> response) {
        if(response.isSuccess()){
          currentAmyotharCount = response.body().get("upper_house").getAsInt();
          currentPyithuHlutaw = response.body().get("lower_house").getAsInt();
          currentTineDaythaHlutaw = response.body().get("state_region").getAsInt();
          currentCount.enqueue(new Callback<JsonObject>() {
            @Override public void onResponse(Response<JsonObject> response) {
              if(response.isSuccess()){
                Gson gson = new GsonBuilder().create();
                mAmyothaCurrentCollection = gson.fromJson(response.body().get("amyotha").getAsJsonObject(),CurrentCollection.class);
                mPyithuCurrentCollection = gson.fromJson(response.body().get("pyithu").getAsJsonObject(),CurrentCollection.class);
                currentCandidateCount.clear();
                final ToyFigurePagerAdapter currentPagerAdapter = new ToyFigurePagerAdapter();
                if(currentAmyotharCount>0) {
                  currentCandidateCount.put(Config.AMYOTHAE_HLUTTAW,
                      (currentAmyotharCount/mAmyothaCurrentCollection.getSeats())*100);
                }else{
                  currentCandidateCount.put(Config.AMYOTHAE_HLUTTAW,0);
                }
                if(currentPyithuHlutaw>0) {
                  currentCandidateCount.put(Config.PYITHU_HLUTTAW,
                      (currentPyithuHlutaw/mPyithuCurrentCollection.getSeats())*100);
                }else{
                  currentCandidateCount.put(Config.PYITHU_HLUTTAW,0);
                }
                currentCandidateCount.put(Config.TINEDAYTHA_HLUTTAW, 9);
                currentCandidateCount.put(Config.AMYOTHAR_REAL_COUNT,currentAmyotharCount);
                currentCandidateCount.put(Config.AMYOTHAR_SEAT_COUNT,mAmyothaCurrentCollection.getSeats());
                currentCandidateCount.put(Config.PYITHU_REAL_COUNT,currentPyithuHlutaw);
                currentCandidateCount.put(Config.PYITHU_SEAT_COUNT,mPyithuCurrentCollection.getSeats());
                currentCandidateCount.put(Config.TINE_DAYTHA_REAL_COUNT,0);
                currentCandidateCount.put(Config.TINE_DAYTHA_SEAT_COUNT,10);
                currentPagerAdapter.setItems(currentCandidateCount);
                mCurrentViewPager.setAdapter(currentPagerAdapter);
                mCurrentViewPager.setCurrentItem(0);
                mCurrentTabLayout.setTabMode(TabLayout.MODE_FIXED);
                mCurrentTabLayout.setupWithViewPager(mCurrentViewPager);

                ToyFigurePagerAdapter prevPagerAdapter = new ToyFigurePagerAdapter();
                Map<String,Integer> prevCandidateCount = new HashMap<>();
                prevCandidateCount.put(Config.AMYOTHAE_HLUTTAW,7);
                prevCandidateCount.put(Config.PYITHU_HLUTTAW,5);
                prevCandidateCount.put(Config.TINEDAYTHA_HLUTTAW, 4);
                prevCandidateCount.put(Config.AMYOTHAR_REAL_COUNT,12);
                prevCandidateCount.put(Config.AMYOTHAR_SEAT_COUNT,10);
                prevCandidateCount.put(Config.PYITHU_REAL_COUNT,13);
                prevCandidateCount.put(Config.PYITHU_SEAT_COUNT,20);
                prevCandidateCount.put(Config.TINE_DAYTHA_REAL_COUNT,0);
                prevCandidateCount.put(Config.TINE_DAYTHA_SEAT_COUNT,10);
                prevPagerAdapter.setItems(prevCandidateCount);
                mPrevViewPager.setAdapter(prevPagerAdapter);
                mPrevViewPager.setCurrentItem(0);
                mPrevTabLayout.setTabMode(TabLayout.MODE_FIXED);
                mPrevTabLayout.setupWithViewPager(mPrevViewPager);

              }
            }

            @Override public void onFailure(Throwable t) {

            }
          });
        }
      }

      @Override public void onFailure(Throwable t) {

      }
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      super.onBackPressed();
      return true;
    }
    return false;
  }
}
