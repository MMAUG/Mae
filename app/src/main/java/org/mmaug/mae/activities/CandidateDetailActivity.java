package org.mmaug.mae.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.models.Candidate;
import org.mmaug.mae.view.ZoomAspectRatioImageView;

public class CandidateDetailActivity extends AppCompatActivity {

  @Bind(R.id.backdrop) ZoomAspectRatioImageView partyImage;
  @Bind(R.id.candidate_avatar) ImageView candidateImage;
  @Bind(R.id.candidate_name) TextView candidateName;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingAvatarToolbar;
  @Bind(R.id.candidate_card) CardView cardView;
  @Bind(R.id.appbar) AppBarLayout appbar;
  AppBarLayout.OnOffsetChangedListener mListener;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_candidate_detail);
    ButterKnife.bind(this);

    //actionbar
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle("");

    mListener = new AppBarLayout.OnOffsetChangedListener() {
      @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (collapsingAvatarToolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(
            collapsingAvatarToolbar)) {
          cardView.setVisibility(View.INVISIBLE);
        } else {
          cardView.setVisibility(View.VISIBLE);
        }
      }
    };

    appbar.addOnOffsetChangedListener(mListener);

    Candidate candidate = (Candidate) getIntent().getSerializableExtra(Config.CANDIDATE);
    Glide.with(this).load(candidate.getParty().getPartyFlag()).
        bitmapTransform(new BlurTransformation(getApplicationContext(), 8, 1)).into(partyImage);

    Glide.with(this)
        .load(candidate.getPhotoUrl())
        .bitmapTransform(new CropCircleTransformation(this))
        .into(candidateImage);

    candidateName.setText(candidate.getName());

    Glide.with(this)
        .load(candidate.getPhotoUrl())
        .asBitmap()
        .into(new BitmapImageViewTarget(candidateImage) {
          @Override protected void setResource(Bitmap resource) {
            // Do bitmap magic here
            super.setResource(resource);
            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
              public void onGenerated(Palette p) {
                // Use generated instance
                Palette.Swatch vibrantSwatch = p.getVibrantSwatch();
                float[] vibrant = vibrantSwatch.getHsl();
                Log.e("Color", "code" + Color.HSVToColor(vibrant));
                candidateName.setTextColor(Color.HSVToColor(vibrant));
              }
            });
          }
        });

    Glide.with(this)
        .load(candidate.getPhotoUrl())
        .bitmapTransform(new CropCircleTransformation(this))
        .into(candidateImage);
  }
}
