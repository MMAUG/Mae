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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.ToyFigurePagerAdapter;
import org.mmaug.mae.models.Party;
import org.mmaug.mae.utils.MixUtils;

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
      if (leaders.indexOf(contact) + 1 == leaders.size()) {
        mPartyLeader.append(contact);
      } else {
        mPartyLeader.append(contact + "\n");
      }
    }
    ToyFigurePagerAdapter currentPagerAdapter = new ToyFigurePagerAdapter();
    Map<String,Integer> currentCandidateCount = new HashMap<>();
    currentCandidateCount.put(Config.AMYOTHAE_HLUTTAW,5);
    currentCandidateCount.put(Config.PYITHU_HLUTTAW,3);
    currentCandidateCount.put(Config.TINEDAYTHA_HLUTTAW, 6);
    currentPagerAdapter.setItems(currentCandidateCount);
    mCurrentViewPager.setAdapter(currentPagerAdapter);
    mCurrentViewPager.setCurrentItem(0);
    mCurrentTabLayout.setTabMode(TabLayout.MODE_FIXED);
    mCurrentTabLayout.setupWithViewPager(mCurrentViewPager);

    ToyFigurePagerAdapter prevPagerAdapter = new ToyFigurePagerAdapter();
    mPrevViewPager.setAdapter(prevPagerAdapter);
    Map<String,Integer> prevCandidateCount = new HashMap<>();
    prevCandidateCount.put(Config.AMYOTHAE_HLUTTAW,7);
    prevCandidateCount.put(Config.PYITHU_HLUTTAW,5);
    prevCandidateCount.put(Config.TINEDAYTHA_HLUTTAW, 4);
    prevPagerAdapter.setItems(prevCandidateCount);
    mPrevViewPager.setCurrentItem(0);
    mPrevTabLayout.setTabMode(TabLayout.MODE_FIXED);
    mPrevTabLayout.setupWithViewPager(mPrevViewPager);

  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      super.onBackPressed();
      return true;
    }
    return false;
  }
}
