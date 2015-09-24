package org.mmaug.mae.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import java.util.List;
import org.mmaug.mae.R;
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
  @Bind(R.id.party_phone) TextView mPartyPhone;
  @Bind(R.id.this_year_toys) LinearLayout mThisYearToys;

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
    // TODO: 9/24/15 REMOVE HARDCODED VALUE
    colorFilterImageViews(mThisYearToys, 1400 / 400);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      super.onBackPressed();
      return true;
    }
    return false;
  }

  private void colorFilterImageViews(ViewGroup parent, int countToColor) {
    for (int i = 0; i < parent.getChildCount(); i++) {
      if (i < countToColor - 1) {
        ((ImageView) parent.getChildAt(i)).setColorFilter(getResources().getColor(R.color.red));
      } else {
        ((ImageView) parent.getChildAt(i)).setColorFilter(
            getResources().getColor(R.color.mdtp_light_gray));
      }
    }
  }
}
