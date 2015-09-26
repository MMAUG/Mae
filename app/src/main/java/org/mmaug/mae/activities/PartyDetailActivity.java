package org.mmaug.mae.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.florent37.glidepalette.GlidePalette;
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
import org.mmaug.mae.utils.FontCache;
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
  @Bind(R.id.party_phone) TextView mPartyPhone;
  @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;
  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.party_policy_book_card) CardView mPartyPolicyCard;
  @Bind(R.id.mPartyLeaderTitle) TextView mPartyLeaderTitle;
  @Bind(R.id.mContactTitle) TextView mContactTitle;
  @Bind(R.id.candidateTotalCountLast) TextView candidateTotalCountLast;
  @Bind(R.id.candidateTotalCountCurrent) TextView candidateTotalCountCurrent;
  private RESTService partyDetailRestService;
  private int currentAmyotharCount;
  private int currentPyithuHlutaw;
  private int currentTineDaythaHlutaw;
  private int prevAmyotharCount;
  private int prevPyithuHlutaw;
  private int prevTineDaythaHlutaw;
  private CurrentCollection mAmyothaCurrentCollection;
  private CurrentCollection mPyithuCurrentCollection;
  private CurrentCollection mTineDayThaGyiCurrentCollection;
  private Map<String, Integer> currentCandidateCount = new HashMap<>();
  private ToyFigurePagerAdapter prevPagerAdapter;
  private ToyFigurePagerAdapter currentPagerAdapter;
  private  int colorFilter = -1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_party_detail);
    ButterKnife.bind(this);

    final Party party = (Party) getIntent().getSerializableExtra("party");
    mCollapsingToolbarLayout.setTitle(party.getPartyName());
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mCollapsingToolbarLayout.setExpandedTitleColor(
        getResources().getColor(android.R.color.transparent));
    setTypeFace();
    mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
    Typeface typefacelight = FontCache.get("pyidaungsu.ttf", this);
    Glide.with(this)
        .load(party.getPartyFlag())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .listener(GlidePalette.with(party.getPartyFlag())
            .intoCallBack(new GlidePalette.CallBack() {
              @Override public void onPaletteLoaded(Palette palette) {
                //specific task
                colorFilter = palette.getMutedColor(getResources().getColor(R.color.primary));
                Log.d("Run","pallet Run");
                if (currentPagerAdapter != null) {
                  currentPagerAdapter.setFilterColor(colorFilter);
                }
                if (prevPagerAdapter != null) {
                  prevPagerAdapter.setFilterColor(colorFilter);
                }
              }
            }))
        .into(party_image);
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
    mPartyHeadQuarter.setTypeface(typefacelight);
    List<String> contacts = party.getContact();
    for (String contact : contacts) {
      if (contacts.indexOf(contact) + 1 == contacts.size()) {
        mPartyPhone.append(contact);
      } else {
        mPartyPhone.append(contact + "\n");
      }
    }

    mPartyPolicyCard.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(party.getPolicy()));
        try {
          startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
          Toast.makeText(PartyDetailActivity.this, "Install a browser!", Toast.LENGTH_LONG).show();
        }
      }
    });

    partyDetailRestService = RESTClient.getService(this);
    Call<JsonObject> candidateCountCall =
        partyDetailRestService.getCandidateCount(party.getPartyId());
    System.out.println("PARTY ID   " + party.getPartyId());
    final Call<JsonObject> currentCount = partyDetailRestService.getCurrentCount();
    candidateCountCall.enqueue(new Callback<JsonObject>() {
      @Override public void onResponse(Response<JsonObject> response) {
        if (response.isSuccess()) {
          currentAmyotharCount = response.body().get("upper_house").getAsInt();
          currentPyithuHlutaw = response.body().get("lower_house").getAsInt();
          currentTineDaythaHlutaw = response.body().get("state_region").getAsInt();
          currentCount.enqueue(new Callback<JsonObject>() {
            @Override public void onResponse(Response<JsonObject> response) {
              if (response.isSuccess()) {
                Gson gson = new GsonBuilder().create();
                mAmyothaCurrentCollection =
                    gson.fromJson(response.body().get("amyotha").getAsJsonObject(),
                        CurrentCollection.class);
                mPyithuCurrentCollection =
                    gson.fromJson(response.body().get("pyithu").getAsJsonObject(),
                        CurrentCollection.class);
                mTineDayThaGyiCurrentCollection = gson.fromJson(response.body().get("state_region").getAsJsonObject(),
                    CurrentCollection.class);
                currentCandidateCount.clear();

                currentPagerAdapter = new ToyFigurePagerAdapter(PartyDetailActivity.this);
                if (currentAmyotharCount > 0) {

                  currentCandidateCount.put(Config.AMYOTHAE_HLUTTAW,
                      (int) ((((double) currentAmyotharCount)
                          / mAmyothaCurrentCollection.getSeats()) * 10));
                } else {
                  currentCandidateCount.put(Config.AMYOTHAE_HLUTTAW, 0);
                }
                if (currentPyithuHlutaw > 0) {
                  currentCandidateCount.put(Config.PYITHU_HLUTTAW,
                      (int) (((double) currentPyithuHlutaw / mPyithuCurrentCollection.getSeats())
                          * 10));
                } else {
                  currentCandidateCount.put(Config.PYITHU_HLUTTAW, 0);
                }if (currentTineDaythaHlutaw >0){
                  currentCandidateCount.put(Config.TINEDAYTHA_HLUTTAW,(int) (((double) currentTineDaythaHlutaw / mTineDayThaGyiCurrentCollection.getSeats())
                      * 10));
                }
                currentCandidateCount.put(Config.AMYOTHAR_REAL_COUNT, currentAmyotharCount);
                currentCandidateCount.put(Config.AMYOTHAR_SEAT_COUNT,
                    mAmyothaCurrentCollection.getSeats());
                currentCandidateCount.put(Config.PYITHU_REAL_COUNT, currentPyithuHlutaw);
                currentCandidateCount.put(Config.PYITHU_SEAT_COUNT,
                    mPyithuCurrentCollection.getSeats());
                currentCandidateCount.put(Config.TINE_DAYTHA_REAL_COUNT, currentTineDaythaHlutaw);
                currentCandidateCount.put(Config.TINE_DAYTHA_SEAT_COUNT,
                    mTineDayThaGyiCurrentCollection.getSeats());
                currentPagerAdapter.setItems(currentCandidateCount);
                mCurrentViewPager.setAdapter(currentPagerAdapter);
                mCurrentViewPager.setCurrentItem(0);
                mCurrentTabLayout.setTabMode(TabLayout.MODE_FIXED);
                mCurrentTabLayout.setupWithViewPager(mCurrentViewPager);


                prevPagerAdapter = new ToyFigurePagerAdapter(PartyDetailActivity.this);
                JsonObject amyotharMembers = mAmyothaCurrentCollection.getMembers();
                if(amyotharMembers.has(party.getPartyId())){
                  prevAmyotharCount = amyotharMembers.get(party.getPartyId()).getAsInt();
                }else{
                  prevAmyotharCount = 0;
                }

                JsonObject pyithuMembers = mPyithuCurrentCollection.getMembers();
                if(pyithuMembers.has(party.getPartyId())){
                  prevPyithuHlutaw = pyithuMembers.get(party.getPartyId()).getAsInt();
                }else{
                  prevPyithuHlutaw = 0;
                }
                JsonObject tinedaythaMembers = mTineDayThaGyiCurrentCollection.getMembers();
                if(tinedaythaMembers.has(party.getPartyId())){
                  prevTineDaythaHlutaw = tinedaythaMembers.get(party.getPartyId()).getAsInt();
                }else{
                  prevTineDaythaHlutaw = 0;
                }
                Map<String, Integer> prevCandidateCount = new HashMap<>();
                prevCandidateCount.put(Config.AMYOTHAE_HLUTTAW,(int) (((double) prevAmyotharCount / mAmyothaCurrentCollection.getSeats())
                    * 10));
                prevCandidateCount.put(Config.PYITHU_HLUTTAW,(int) (((double) prevPyithuHlutaw / mPyithuCurrentCollection.getSeats())
                    * 10));
                prevCandidateCount.put(Config.TINEDAYTHA_HLUTTAW, (int) (((double) prevTineDaythaHlutaw / mTineDayThaGyiCurrentCollection.getSeats())
                    * 10));
                prevCandidateCount.put(Config.AMYOTHAR_REAL_COUNT, prevAmyotharCount);
                prevCandidateCount.put(Config.AMYOTHAR_SEAT_COUNT, mAmyothaCurrentCollection.getSeats());
                prevCandidateCount.put(Config.PYITHU_REAL_COUNT,prevPyithuHlutaw);
                prevCandidateCount.put(Config.PYITHU_SEAT_COUNT, mPyithuCurrentCollection.getSeats());
                prevCandidateCount.put(Config.TINE_DAYTHA_REAL_COUNT,prevTineDaythaHlutaw);
                prevCandidateCount.put(Config.TINE_DAYTHA_SEAT_COUNT, mTineDayThaGyiCurrentCollection.getSeats());
                prevPagerAdapter.setItems(prevCandidateCount);
                if(colorFilter!=-1){
                  prevPagerAdapter.setFilterColor(colorFilter);
                  currentPagerAdapter.setFilterColor(colorFilter);
                }
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

  void setTypeFace() {
    Typeface typefaceTitle = FontCache.get("MyanmarAngoun.ttf", this);
    Typeface typefacelight = FontCache.get("pyidaungsu.ttf", this);
    mPartyLeaderTitle.setTypeface(typefaceTitle);
    mPartyCount.setTypeface(typefacelight);
    mPartyDate.setTypeface(typefacelight);
    mPartyNumber.setTypeface(typefacelight);
    mPartyName.setTypeface(typefaceTitle);
    mConstituency.setTypeface(typefaceTitle);
    mPartyHeadQuarter.setTypeface(typefacelight);
    mPartyLeader.setTypeface(typefacelight);
    mPartyPhone.setTypeface(typefacelight);
    mContactTitle.setTypeface(typefaceTitle);
    candidateTotalCountLast.setTypeface(typefaceTitle);
    candidateTotalCountCurrent.setTypeface(typefaceTitle);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      super.onBackPressed();
      return true;
    }
    return false;
  }
}
