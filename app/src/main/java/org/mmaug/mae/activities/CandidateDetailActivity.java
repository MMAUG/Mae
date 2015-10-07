package org.mmaug.mae.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
import org.mmaug.mae.utils.FontCache;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.MixUtils;
import org.mmaug.mae.utils.RestCallback;
import org.mmaug.mae.utils.UserPrefUtils;
import org.mmaug.mae.utils.mmtext;
import org.mmaug.mae.view.CircleView;
import org.mmaug.mae.view.ZoomAspectRatioImageView;
import retrofit.Call;
import retrofit.Response;

public class CandidateDetailActivity extends AppCompatActivity {

  @Bind(R.id.backdrop) ZoomAspectRatioImageView partyImage;
  @Bind(R.id.candidate_avatar) ImageView candidateImage;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.candidate_detail_party_flag) ImageView mCandidatePartyFlag;
  @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingAvatarToolbar;
  @Bind(R.id.candidate_card) CardView cardView;
  @Bind(R.id.appbar) AppBarLayout appbar;
  @Bind(R.id.question_pie_cont) LinearLayout mQuestionPieCont;
  @Bind(R.id.compare_candidate_card) CardView mCompareCandidate;
  @Bind(R.id.candate_question_card) CardView mCandidateQuestionCard;
  @Bind(R.id.candate_motion_card) CardView mCandidateMotionCard;
  @Bind(R.id.motion_pie_cont) LinearLayout motionPieCont;

  @Bind(R.id.candidate_name) TextView candidateName;
  @Bind(R.id.candidate_detail_constituency_pl) TextView mCandidateConstituency;
  @Bind(R.id.candidate_detail_dob) TextView mCandidateDateOfBirth;
  @Bind(R.id.candidate_detail_education) TextView mCandidateEducation;
  @Bind(R.id.candidate_detail_mother) TextView mCandidateMother;
  @Bind(R.id.candidate_detail_father) TextView mCandidateFather;
  @Bind(R.id.candidate_detail_occupation) TextView mCandidateOccupation;
  @Bind(R.id.candidate_detail_race) TextView mCandidateRace;
  @Bind(R.id.candidate_detail_religion) TextView mCandidateReligion;
  @Bind(R.id.legalslature) TextView mCandidateLegalSlature;
  @Bind(R.id.motion_count) TextView mMotionCount;
  @Bind(R.id.motion_middle_text) TextView motionMiddleText;
  @Bind(R.id.question_middle_text) TextView mQuestionMiddleText;
  @Bind(R.id.question_count) TextView mQuestionCount;
  @Bind(R.id.candidate_detail_party) TextView mCandidateParty;
  @Bind(R.id.candidate_detail_address) TextView mCandidateAddress;
  @Bind(R.id.mCandidateCompareResult) TextView mCandidateCompareResult;
  @Bind(R.id.question_header_tv) TextView mQuestionHeaderTv;
  @Bind(R.id.motion_header_tv) TextView mMotionHeaderTv;
  @Bind(R.id.upper_house_view) LinearLayout upperHouseView;
  @Bind(R.id.upper_house_label) TextView upperHouseLabel;
  @Bind(R.id.upper_house) TextView upperHouse;
  AppBarLayout.OnOffsetChangedListener mListener;
  Candidate candidate;
  private String[] colorHexes = new String[] {
      "#F44336", "#E91E63", "#9C27B0", "#673AB7", "#2196F3", "#009688", "#4CAF50", "#FFC107",
      "#FF5722"
  };

  private Typeface typefaceTitle, typefacelight;
  private boolean isUnicode;

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

    typefaceTitle = FontCache.getTypefaceTitle(this);
    typefacelight = FontCache.getTypefaceTitle(this);
    isUnicode = UserPrefUtils.getInstance(this).getTextPref().equals(Config.UNICODE);

    mListener = new AppBarLayout.OnOffsetChangedListener() {
      @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (collapsingAvatarToolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(
            collapsingAvatarToolbar)) {
          MixUtils.toggleVisibilityWithAnim(cardView, 1000, false);
        } else {
          MixUtils.toggleVisibilityWithAnim(cardView, 1000, true);
        }
      }
    };

    appbar.addOnOffsetChangedListener(mListener);

    candidate = (Candidate) getIntent().getSerializableExtra(Config.CANDIDATE);
    Glide.with(this).load(candidate.getParty().getPartyFlag()).
        bitmapTransform(new BlurTransformation(getApplicationContext(), 8, 1)).into(partyImage);

    Glide.with(this)
        .load(candidate.getPhotoUrl())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .bitmapTransform(new CropCircleTransformation(this))
        .placeholder(R.drawable.profile_placeholder)
        .into(candidateImage);
    candidateName.setText(candidate.getName());

    Glide.with(this)
        .load(candidate.getParty().getPartyFlag())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(mCandidatePartyFlag);

    if (candidate.getConstituency().getAMPCODE() == null) {
      upperHouseView.setVisibility(View.GONE);
    } else {
      upperHouseView.setVisibility(View.VISIBLE);
      upperHouse.setText(MixUtils.amConstituencyName(candidate.getConstituency().getName(),
          candidate.getConstituency().getNumber()));
    }

    mCandidateConstituency.setText(candidate.getConstituency().getName());
    mCandidateDateOfBirth.setText(
        MixUtils.convertToBurmese(String.valueOf(candidate.getBirthDateString())));
    mCandidateEducation.setText(candidate.getEducation());
    mCandidateFather.setText(candidate.getFather().getName());
    mCandidateMother.setText(candidate.getMother().getName());
    mCandidateOccupation.setText(candidate.getOccupation());
    mCandidateRace.setText(candidate.getEthnicity());
    mCandidateReligion.setText(candidate.getReligion());
    mCandidateLegalSlature.setText(candidate.getLegislature());
    mCandidateParty.setText(candidate.getParty().getPartyName());
    mCandidateAddress.setText(candidate.getWardVillage());
    RESTService mRESTService = RESTClient.getService(this);
    if (candidate.getMpid() == null) {
      //mCompareCandidate.setCardBackgroundColor(getResources().getColor(R.color.accent_color_error));
      //mCandidateCompareResult.setText(getResources().getString(R.string.first_time_candidate));
      mQuestionHeaderTv.setVisibility(View.GONE);
      mMotionHeaderTv.setVisibility(View.GONE);
      mCandidateQuestionCard.setVisibility(View.GONE);
      mCandidateMotionCard.setVisibility(View.GONE);
    } else {

      Call<JsonObject> questionMotionCall = mRESTService.getQuestionAndMotion(candidate.getMpid());
      questionMotionCall.enqueue(new RestCallback<JsonObject>() {
        @Override public void onResponse(Response<JsonObject> response) {
          if (response.code() == 200) {
            int motionCount = response.body().get("motions_count").getAsInt();
            JsonArray motions = response.body().get("motions").getAsJsonArray();

            int questionCount = response.body().get("questions_count").getAsInt();
            JsonArray questions = response.body().get("questions").getAsJsonArray();

            makeMotionChart(motionCount, motions);
            makeQuestionChart(questionCount, questions);
          }
        }
      });
    }

    mCompareCandidate.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
          Intent i = new Intent(CandidateDetailActivity.this, CandidateListActivity.class);
          i.putExtra(Config.CANDIDATE, candidate);
          startActivity(i);
      }
    });

    setTypeFace();
  }

  @OnClick(R.id.candidate_detail_party_flag) void toCandidateParty() {
    Intent intent = new Intent();
    intent.setClass(this, PartyDetailActivity.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable("party", candidate.getParty());
    intent.putExtras(bundle);
    startActivity(intent);
  }

  protected void makeMotionChart(int motionCount, JsonArray datas) {
    motionMiddleText.setText("အဆို");
    mMotionCount.setText(MixUtils.convertToBurmese(String.valueOf(motionCount)) + " ခု");

    PieChart mPieChart = (PieChart) findViewById(R.id.motion_piechart);

    makePieChart(datas, mPieChart, motionPieCont);
  }

  protected void makeQuestionChart(int questionCount, JsonArray datas) {
    mQuestionMiddleText.setText("ကဏ္ဍ");
    mQuestionCount.setText(MixUtils.convertToBurmese(String.valueOf(questionCount)) + " ခု");

    PieChart mPieChart = (PieChart) findViewById(R.id.question_piechart);
    makePieChart(datas, mPieChart, mQuestionPieCont);

    if (isUnicode) {
      mQuestionMiddleText.setTypeface(typefacelight);
      mQuestionCount.setTypeface(typefaceTitle);
    } else {
      MMTextUtils.getInstance(this).prepareMultipleViews(mQuestionMiddleText, mQuestionCount);
    }
  }

  protected void makePieChart(JsonArray datas, PieChart mPieChart, LinearLayout mPieCount) {
    List<String> titles = new ArrayList<String>();
    for (JsonElement element : datas) {
      String title = element.getAsJsonObject().get("issue").getAsString();
      titles.add(title);
    }
    Set<String> unique = new HashSet<String>(titles);

    for (String key : unique) {
      System.out.println(key + ": " + Collections.frequency(titles, key));
      Random rnd = new Random();
      String hexes = colorHexes[rnd.nextInt(colorHexes.length)];
      int color = Color.parseColor(hexes);
      int count = Collections.frequency(titles, key);
      PieModel pieModel = new PieModel(key, count, color);
      mPieChart.addPieSlice(pieModel);
      View piechartLegend =
          getLayoutInflater().inflate(R.layout.piechart_legend_layout, mPieCount, false);
      CircleView piechartIndicator =
          (CircleView) piechartLegend.findViewById(R.id.legend_indicator);
      piechartIndicator.setColorHex(color);
      TextView piechartText = (TextView) piechartLegend.findViewById(R.id.legend_text);
      piechartText.setText(key);
      TextView piechartCount = (TextView) piechartLegend.findViewById(R.id.legend_count);
      piechartCount.setText(MixUtils.convertToBurmese(String.valueOf(count)));

      if (isUnicode) {
        piechartText.setTypeface(typefacelight);
        piechartCount.setTypeface(typefacelight);
      } else {
        MMTextUtils.getInstance(this).prepareMultipleViews(piechartText, piechartCount);
      }

      mPieCount.addView(piechartLegend);
    }
    mPieChart.startAnimation();
  }

  void setTypeFace() {
    if (isUnicode) {
      candidateName.setTypeface(typefaceTitle);
      mCandidateConstituency.setTypeface(typefacelight);
      mCandidateCompareResult.setTypeface(typefaceTitle);
      mCandidateFather.setTypeface(typefacelight);
      mCandidateAddress.setTypeface(typefacelight);
      mCandidateOccupation.setTypeface(typefacelight);
      mCandidateEducation.setTypeface(typefacelight);
      mCandidateMother.setTypeface(typefacelight);
      mCandidateParty.setTypeface(typefacelight);
      mCandidateDateOfBirth.setTypeface(typefacelight);
      mCandidateRace.setTypeface(typefacelight);
      mCandidateReligion.setTypeface(typefacelight);
      mCandidateLegalSlature.setTypeface(typefacelight);
      mMotionCount.setTypeface(typefacelight);
      motionMiddleText.setTypeface(typefaceTitle);
      mQuestionMiddleText.setTypeface(typefaceTitle);
      mQuestionCount.setTypeface(typefacelight);
      mQuestionHeaderTv.setTypeface(typefacelight);
      mMotionHeaderTv.setTypeface(typefacelight);
      upperHouse.setTypeface(typefacelight);
      upperHouseLabel.setTypeface(typefacelight);
    } else {
      mmtext.prepareActivity(this, mmtext.TEXT_UNICODE, true, true);
    }
  }

  @Override
  protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_candidate, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
      case R.id.party_detail_action_share:
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setData(Uri.parse("http://mae.mmaug.org/share/" + candidate.getId()));
        startActivity(Intent.createChooser(i, "Share Via"));
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, candidate.getName());
        i.putExtra(Intent.EXTRA_TEXT, "http://mae.mmaug.org/share/" + candidate.getId());
        startActivity(Intent.createChooser(i, "Share Via"));
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
