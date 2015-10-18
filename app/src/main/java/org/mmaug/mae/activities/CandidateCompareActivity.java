package org.mmaug.mae.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.GlidePalette;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.models.Candidate;
import org.mmaug.mae.rest.RESTClient;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.MixUtils;
import org.mmaug.mae.utils.mmtext;
import org.mmaug.mae.view.AspectRatioImageView;
import org.mmaug.mae.view.AutofitTextView;
import org.mmaug.mae.view.CircularImageView;
import org.mmaug.mae.view.RoundCornerProgressBar;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import timber.log.Timber;

public class CandidateCompareActivity extends BaseActivity {
  @Bind(R.id.question_showcase) LinearLayout question_showcase;
  @Bind(R.id.motion_view) LinearLayout motion_view;
  @Bind(R.id.candidateOne_Name) TextView candidateOne;
  @Bind(R.id.candidateTwoName) TextView candidateTwo;
  @Bind(R.id.party_flag_one) AspectRatioImageView party_flag_one;
  @Bind(R.id.party_flag_two) AspectRatioImageView party_flag_two;
  @Bind(R.id.candidate_one) CircularImageView candidateOneView;
  @Bind(R.id.candidate_two) CircularImageView candidateTwoView;
  @Bind(R.id.party_edu_one) AutofitTextView candidateEduOne;
  @Bind(R.id.party_edu_two) AutofitTextView candidateEduTwo;
  @Bind(R.id.compre_title) TextView compareTitle;
  @Bind(R.id.party_job_one) TextView partyJobOne;
  @Bind(R.id.party_job_two) TextView partyJobTwo;
  @Bind(R.id.no_flag_candidateOne) AutofitTextView no_flag_candidateOne;
  @Bind(R.id.no_flag_candidateTwo) AutofitTextView no_flag_candidateTwo;
  @Bind(R.id.personal_view) CardView personalView;
  Candidate candidate;
  Candidate candidateCompare;
  String file_path;
  float[] hslValues = new float[3];
  float[] darkValue = new float[3];

  private Typeface typefaceTitle, typefacelight;
  private boolean isUnicode;
  private Bitmap bitmap;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    typefacelight = getTypefaceLight();
    typefaceTitle = getTypefaceTitle();
    isUnicode = isUnicode();

    //GET candidate value
    setupHeader();
    if (candidateCompare.getMpid() != null && candidate.getMpid() != null) {
      Call<JsonElement> compareQuestionCall = RESTClient.getService(this)
          .getCompareQuestion(candidateCompare.getMpid(), candidate.getMpid());
      compareQuestionCall.enqueue(new Callback<JsonElement>() {
        @Override public void onResponse(Response<JsonElement> response) {

          if (response.body().getAsJsonObject().get("questions").isJsonObject()) {
            JsonObject question =
                response.body().getAsJsonObject().get("questions").getAsJsonObject();

            JsonObject motion =
                response.body().getAsJsonObject().getAsJsonObject("motions").getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entriesMotions = motion.entrySet();//

            for (Map.Entry<String, JsonElement> entry : entriesMotions) {
              {

                int obtainScrollOne =
                    entry.getValue().getAsJsonObject().get(candidateCompare.getMpid()).getAsInt();
                int obtainScrollTwo =
                    entry.getValue().getAsJsonObject().get(candidate.getMpid()).getAsInt();
                Float PercentageOne;
                Float PercentageTwo;
                int TotalScore =
                    entry.getValue().getAsJsonObject().get(candidateCompare.getMpid()).getAsInt()
                        + entry.getValue().getAsJsonObject().get(candidate.getMpid()).getAsInt();
                PercentageOne = Float.valueOf((obtainScrollOne * 100 / TotalScore));
                PercentageTwo = Float.valueOf((obtainScrollTwo * 100 / TotalScore));
                View question_indicator =
                    getLayoutInflater().inflate(R.layout.question_compare_layout, motion_view,
                        false);
                TextView questionText =
                    (TextView) question_indicator.findViewById(R.id.question_title);
                TextView leftText = (TextView) question_indicator.findViewById(R.id.left_text);
                TextView rightText = (TextView) question_indicator.findViewById(R.id.right_text);
                leftText.setText(MixUtils.convertToBurmese(String.valueOf(obtainScrollOne)));
                rightText.setText(MixUtils.convertToBurmese(String.valueOf(obtainScrollTwo)));
                questionText.setTypeface(typefacelight);
                RoundCornerProgressBar roundCornerProgressBar =
                    (RoundCornerProgressBar) question_indicator.findViewById(R.id.candidate1);
                RoundCornerProgressBar roundCornerProgressBarTwo =
                    (RoundCornerProgressBar) question_indicator.findViewById(R.id.candidate2);
                roundCornerProgressBar.setProgress(PercentageOne);
                roundCornerProgressBar.setProgressColor(Color.parseColor("#9C27B0"));
                roundCornerProgressBar.setRotation(180);
                roundCornerProgressBar.setBackgroundColor(Color.WHITE);
                roundCornerProgressBarTwo.setProgress(PercentageTwo);
                roundCornerProgressBarTwo.setProgressColor(Color.parseColor("#009688"));
                questionText.setText(entry.getKey());
                motion_view.addView(question_indicator);
                if (isUnicode) {
                  questionText.setTypeface(typefacelight);
                } else {
                  MMTextUtils.getInstance(CandidateCompareActivity.this)
                      .prepareMultipleViews(questionText, leftText, rightText);
                }
              }
            }

            Set<Map.Entry<String, JsonElement>> entries =
                question.entrySet();//will return members of your object
            for (Map.Entry<String, JsonElement> entry : entries) {
              int obtainScrollOne =
                  entry.getValue().getAsJsonObject().get(candidateCompare.getMpid()).getAsInt();
              int obtainScrollTwo =
                  entry.getValue().getAsJsonObject().get(candidate.getMpid()).getAsInt();
              Float PercentageOne;
              Float PercentageTwo;
              //"#9C27B0", "#673AB7"
              int TotalScore =
                  entry.getValue().getAsJsonObject().get(candidateCompare.getMpid()).getAsInt()
                      + entry.getValue().getAsJsonObject().get(candidate.getMpid()).getAsInt();
              PercentageOne = Float.valueOf((obtainScrollOne * 100 / TotalScore));
              PercentageTwo = Float.valueOf((obtainScrollTwo * 100 / TotalScore));
              View question_indicator =
                  getLayoutInflater().inflate(R.layout.question_compare_layout, question_showcase,
                      false);
              TextView questionText =
                  (TextView) question_indicator.findViewById(R.id.question_title);
              TextView leftText = (TextView) question_indicator.findViewById(R.id.left_text);
              TextView rightText = (TextView) question_indicator.findViewById(R.id.right_text);
              leftText.setText(MixUtils.convertToBurmese(String.valueOf(obtainScrollOne)));
              rightText.setText(MixUtils.convertToBurmese(String.valueOf(obtainScrollTwo)));

              RoundCornerProgressBar roundCornerProgressBar =
                  (RoundCornerProgressBar) question_indicator.findViewById(R.id.candidate1);
              RoundCornerProgressBar roundCornerProgressBarTwo =
                  (RoundCornerProgressBar) question_indicator.findViewById(R.id.candidate2);
              roundCornerProgressBar.setProgress(PercentageOne);
              roundCornerProgressBar.setProgressColor(Color.parseColor("#9C27B0"));
              roundCornerProgressBar.setRotation(180);
              roundCornerProgressBar.setBackgroundColor(Color.WHITE);
              roundCornerProgressBarTwo.setProgress(PercentageTwo);
              roundCornerProgressBarTwo.setProgressColor(Color.parseColor("#009688"));
              questionText.setText(entry.getKey());
              question_showcase.addView(question_indicator);
              if (isUnicode) {
                questionText.setTypeface(typefacelight);
              } else {
                MMTextUtils.getInstance(CandidateCompareActivity.this)
                    .prepareMultipleViews(questionText, leftText, rightText);
              }
            }
          }
        }

        @Override public void onFailure(Throwable t) {
          Timber.e(t.getMessage());
        }
      });
    } else {
      findViewById(R.id.title_question).setVisibility(View.GONE);
      findViewById(R.id.title_motions).setVisibility(View.GONE);
      findViewById(R.id.question_view).setVisibility(View.GONE);
      findViewById(R.id.motion_view_card).setVisibility(View.GONE);
    }

    setTypeFace();
  }

  void setTypeFace() {
    if (isUnicode) {
      candidateOne.setTypeface(typefaceTitle);
      candidateTwo.setTypeface(typefaceTitle);
      candidateEduOne.setTypeface(typefacelight);
      candidateEduTwo.setTypeface(typefacelight);
      partyJobOne.setTypeface(typefacelight);
      partyJobTwo.setTypeface(typefacelight);
      no_flag_candidateOne.setTypeface(typefacelight);
      no_flag_candidateTwo.setTypeface(typefacelight);
      compareTitle.setTypeface(typefaceTitle);
    } else {
      mmtext.prepareActivity(this, mmtext.TEXT_UNICODE, true, true);
    }
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_candidate_compare;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return getResources().getString(R.string.compare_candidate);
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
        new AsyncTask<Void, Void, Void>() {

          @Override protected void onPreExecute() {
            super.onPreExecute();
          }

          @Override protected Void doInBackground(Void... params) {
            bitmap = screenShot(personalView);
            return null;
          }

          @Override protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            postToFacebook();
          }
        }.execute();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void setupHeader() {
    candidate = (Candidate) getIntent().getSerializableExtra(Config.CANDIDATE);
    candidateCompare = (Candidate) getIntent().getSerializableExtra(Config.CANDIDATE_COMPARE);
    candidateOne.setText(candidateCompare.getName());
    candidateTwo.setText(candidate.getName());
    candidateEduOne.setText(candidateCompare.getEducation());
    candidateEduTwo.setText(candidate.getEducation());
    partyJobOne.setText(candidateCompare.getOccupation());
    partyJobTwo.setText(candidate.getOccupation());

    if (candidateCompare.getParty().getPartyFlag() != null) {
      party_flag_one.setVisibility(View.VISIBLE);
      no_flag_candidateOne.setText(candidateCompare.getParty().getPartyName());
      Glide.with(this)
          .load(candidateCompare.getParty().getPartyFlag())
          .listener(GlidePalette.with(candidateCompare.getParty().getPartyFlag())
              .intoCallBack(new GlidePalette.CallBack() {
                @Override public void onPaletteLoaded(Palette palette) {
                  //specific task
                  Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                  hslValues = vibrantSwatch.getHsl();
                  candidateOneView.setBorderColor(Color.HSVToColor(hslValues));
                }
              }))
          .into(party_flag_one);
    } else {
      no_flag_candidateOne.setText(R.string.single_candidate);
    }
    if (candidate.getParty().getPartyFlag() != null) {
      party_flag_two.setVisibility(View.VISIBLE);
      no_flag_candidateTwo.setText(candidate.getParty().getPartyName());

      Glide.with(this)
          .load(candidate.getParty().getPartyFlag())
          .listener(GlidePalette.with(candidate.getParty().getPartyFlag())
              .intoCallBack(new GlidePalette.CallBack() {
                @Override public void onPaletteLoaded(Palette palette) {
                  //specific task
                  Palette.Swatch darkSwatch = palette.getLightVibrantSwatch();
                  if (darkSwatch != null) {
                    darkValue = darkSwatch.getHsl();
                    candidateTwoView.setBorderColor(Color.HSVToColor(darkValue));
                  }
                }
              }))
          .into(party_flag_two);
    } else {
      no_flag_candidateTwo.setText(R.string.single_candidate);
    }

    Glide.with(this)
        .load(candidate.getPhotoUrl())
        .bitmapTransform(new CropCircleTransformation(this))
        .into(candidateTwoView);
    Glide.with(this)
        .load(candidateCompare.getPhotoUrl())
        .bitmapTransform(new CropCircleTransformation(this))
        .into(candidateOneView);
    candidateOneView.setBorderWidth(5);
    candidateOneView.setMinimumHeight(200);
    candidateOneView.setMinimumWidth(200);
    candidateTwoView.setBorderWidth(5);
    candidateTwoView.setMinimumHeight(200);
    candidateTwoView.setMinimumWidth(200);
  }

  public Bitmap screenShot(View view) {
    view.setDrawingCacheEnabled(true);
    final Bitmap bitmap =
        Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
    file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/share_image.png";
    Canvas canvas = new Canvas(bitmap);
    view.draw(canvas);
    try {
      File file = new File(file_path);
      FileOutputStream fOut = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
      fOut.flush();
      fOut.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    view.setDrawingCacheEnabled(false);
    return bitmap;
  }

  public void postToFacebook() {

    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_TEXT, "compare");
    shareIntent.putExtra(Intent.EXTRA_STREAM,
        Uri.fromFile(new File(file_path)));  //optional//use this when you want to send an image
    shareIntent.setType("image/*");
    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    startActivity(Intent.createChooser(shareIntent, "send"));
  }
}
