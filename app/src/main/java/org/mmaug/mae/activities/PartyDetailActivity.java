package org.mmaug.mae.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import jp.wasabeef.glide.transformations.BlurTransformation;
import org.mmaug.mae.R;
import org.mmaug.mae.models.Party;

public class PartyDetailActivity extends AppCompatActivity {
  @Bind(R.id.party_image_demo) ImageView party_image;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_party_detail);
    ButterKnife.bind(this);
    Party party = (Party) getIntent().getSerializableExtra("party");
    Glide.with(this)
        .load(party.getPartyFlag())
        .bitmapTransform(new BlurTransformation(getApplicationContext(), 8, 1))
        .into(party_image);
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        long startMs = System.currentTimeMillis();
        Log.d(getString(R.string.app_name),
            "TIME " + String.valueOf(System.currentTimeMillis() - startMs) + "ms");
      }
    });
  }
}
