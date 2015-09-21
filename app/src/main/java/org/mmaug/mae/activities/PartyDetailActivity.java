package org.mmaug.mae.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import jp.wasabeef.glide.transformations.BlurTransformation;
import org.mmaug.mae.R;
import org.mmaug.mae.models.Party;
import org.mmaug.mae.view.ZoomAspectRatioImageView;

public class PartyDetailActivity extends AppCompatActivity {
  @Bind(R.id.party_image_demo) ZoomAspectRatioImageView party_image;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_party_detail);
    ButterKnife.bind(this);

    final Party party = (Party) getIntent().getSerializableExtra("party");
    Glide.with(this)
        .load(party.getPartyFlag())
        .bitmapTransform(new BlurTransformation(getApplicationContext(), 8, 1))
        .into(party_image);

    Glide.with(this)
        .load(party.getPartyFlag())
        .bitmapTransform(new BlurTransformation(getApplicationContext(), 8, 1))
        .into(party_image);
  }
}
