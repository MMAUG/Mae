package org.mmaug.mae.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.models.Candidate;
import org.mmaug.mae.rest.RESTClient;
import org.mmaug.mae.rest.RESTService;
import org.mmaug.mae.view.ZoomAspectRatioImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class CandidateDetailActivity extends AppCompatActivity {

  @Bind(R.id.backdrop) ZoomAspectRatioImageView partyImage;
  @Bind(R.id.candidate_avatar) ImageView candidateImage;
  @Bind(R.id.candidate_name) TextView candidateName;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.candidate_detail_constituency_pl) TextView mCandidateConstituency;
  @Bind(R.id.candidate_detail_dob) TextView mCandidateDateOfBirth;
  @Bind(R.id.candidate_detail_education) TextView mCandidateEducation;
  @Bind(R.id.candidate_detail_mother) TextView mCandidateMother;
  @Bind(R.id.candidate_detail_father) TextView mCandidateFather;
  @Bind(R.id.candidate_detail_occupation)TextView mCandidateOccupation;
  @Bind(R.id.candidate_detail_race) TextView mCandidateRace;
  @Bind(R.id.candidate_detail_religion)TextView mCandidateReligion;
  @Bind(R.id.candidate_detail_party_flag) ImageView mCandidatePartyFlag;
  @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingAvatarToolbar;
  @Bind(R.id.candidate_card) CardView cardView;
  @Bind(R.id.legalslature)  TextView mCandidateLegalSlature;
  @Bind(R.id.appbar) AppBarLayout appbar;
  @Bind(R.id.motion_count) TextView mMotionCount;
  @Bind(R.id.question_pie_cont) LinearLayout questionPieCont;
  AppBarLayout.OnOffsetChangedListener mListener;
  private RESTService mMotionService;
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
    candidateName.setText(candidate.getName());

    //Glide.with(this)
    //    .load(candidate.getPhotoUrl())
    //    .asBitmap()
    //    .into(new BitmapImageViewTarget(candidateImage) {
    //      @Override protected void setResource(Bitmap resource) {
    //        // Do bitmap magic here
    //        super.setResource(resource);
    //        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
    //          public void onGenerated(Palette p) {
    //            // Use generated instance
    //            Palette.Swatch vibrantSwatch = p.getVibrantSwatch();
    //            float[] vibrant = vibrantSwatch.getHsl();
    //            Log.e("Color", "code" + Color.HSVToColor(vibrant));
    //            candidateName.setTextColor(Color.HSVToColor(vibrant));
    //          }
    //        });
    //      }
    //    });
    Glide.with(this)
        .load(candidate.getPhotoUrl())
        .bitmapTransform(new CropCircleTransformation(this))
        .into(candidateImage);
    candidateName.setText(candidate.getName());

    Glide.with(this)
        .load(candidate.getParty().getPartyFlag())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(mCandidatePartyFlag);

    mCandidateConstituency.setText(candidate.getConstituency().getName());
    mCandidateDateOfBirth.setText(String.valueOf(candidate.getBirthDateString()));
    mCandidateEducation.setText(candidate.getEducation());
    mCandidateFather.setText(candidate.getFather().getName());
    mCandidateMother.setText(candidate.getMother().getName());
    mCandidateOccupation.setText(candidate.getOccupation());
    mCandidateRace.setText(candidate.getEthnicity());
    mCandidateReligion.setText(candidate.getReligion());
    mCandidateLegalSlature.setText(candidate.getLegislature());
    mMotionService = RESTClient.getService();
    //if(candidate.getMpid()!=null) {
      // TODO: 9/21/15 REMOVE HARDCODED MPID
      Call<JsonObject> motionCountCall = mMotionService.getMotionCount("UPMP-01-0142");
      motionCountCall.enqueue(new Callback<JsonObject>() {
        @Override public void onResponse(Response<JsonObject> response) {
          int motionCount = response.body().get("count").getAsInt();
          mMotionCount.setText(String.valueOf(motionCount) + "\nIssues ");
        }

        @Override public void onFailure(Throwable t) {

        }
      });
      Call<JsonObject> motionCall = mMotionService.getMotionDetail("UPMP-01-0142");
      motionCall.enqueue(new Callback<JsonObject>() {
        @Override public void onResponse(Response<JsonObject> response) {
          JsonArray datas = response.body().get("data").getAsJsonArray();
          List<String> titles = new ArrayList<String>();
          for (JsonElement element:datas){
            String title = element.getAsJsonObject().get("issue").getAsString();
            titles.add(title);
          }
          Set<String> unique = new HashSet<String>(titles);
          PieChart mPieChart = (PieChart) findViewById(R.id.motion_piechart);
          for (String key : unique) {
            System.out.println(key + ": " + Collections.frequency(titles, key));
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            int count = Collections.frequency(titles, key);
            PieModel pieModel = new PieModel(key,count,color);
            mPieChart.addPieSlice(pieModel);
            View piechartLegend = getLayoutInflater().inflate(R.layout.piechart_legend_layout,questionPieCont,false);
            View piechartIndicator =piechartLegend.findViewById(R.id.legend_indicator);
            piechartIndicator.setBackgroundColor(color);
            TextView piechartText = (TextView) piechartLegend.findViewById(R.id.legend_text);
            piechartText.setText(key);
            questionPieCont.addView(piechartLegend);
          }
          mPieChart.startAnimation();
        }

        @Override public void onFailure(Throwable t) {

        }
      });

    //}else{
    //
    //}
  }
}
