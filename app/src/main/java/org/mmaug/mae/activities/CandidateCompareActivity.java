package org.mmaug.mae.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Set;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.models.Candidate;
import org.mmaug.mae.rest.RESTClient;
import org.mmaug.mae.view.AspectRatioImageView;
import org.mmaug.mae.view.RoundCornerProgressBar;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class CandidateCompareActivity extends BaseActivity {
  @Bind(R.id.question_showcase) LinearLayout question_showcase;
  @Bind(R.id.motion_view) LinearLayout motion_view;
  @Bind(R.id.candidateOne_Name) TextView candidateOne;
  @Bind(R.id.candidateTwoName) TextView candidateTwo;
  @Bind(R.id.party_flag_one) AspectRatioImageView party_flag_one;
  @Bind(R.id.party_flag_two) AspectRatioImageView party_flag_two;
  @Bind(R.id.candidate_one) ImageView candidateOneView;
  @Bind(R.id.candidate_two) ImageView candidateTwoView;
  Candidate candidate;
  Candidate candidateCompare;
  float[] vibrantOne;
  float[] varantTwo;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    //GET candidate value
    setupHeader();

    Call<JsonElement> compareQuestionCall =
        RESTClient.getService().getCompareQuestion("LWMP-01-0063", "LWMP-01-0064");
    compareQuestionCall.enqueue(new Callback<JsonElement>() {
      @Override public void onResponse(Response<JsonElement> response) {
        JsonObject question = response.body().getAsJsonObject().get("questions").getAsJsonObject();

        JsonObject motion =
            response.body().getAsJsonObject().getAsJsonObject("motions").getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> entriesMotions = motion.entrySet();//

        for (Map.Entry<String, JsonElement> entry : entriesMotions) {
          {
            int obtainScrollOne = entry.getValue().getAsJsonObject().get("LWMP-01-0064").getAsInt();
            int obtainScrollTwo = entry.getValue().getAsJsonObject().get("LWMP-01-0063").getAsInt();

            Float PercentageOne;
            Float PercentageTwo;
            int TotalScore =
                entry.getValue().getAsJsonObject().get("LWMP-01-0064").getAsInt() + entry.getValue()
                    .getAsJsonObject()
                    .get("LWMP-01-0063")
                    .getAsInt();
            PercentageOne = Float.valueOf((obtainScrollOne * 100 / TotalScore));
            PercentageTwo = Float.valueOf((obtainScrollTwo * 100 / TotalScore));
            View question_indicator =
                getLayoutInflater().inflate(R.layout.question_compare_layout, motion_view, false);
            TextView questionText = (TextView) question_indicator.findViewById(R.id.question_title);
            RoundCornerProgressBar roundCornerProgressBar =
                (RoundCornerProgressBar) question_indicator.findViewById(R.id.candidate1);
            RoundCornerProgressBar roundCornerProgressBarTwo =
                (RoundCornerProgressBar) question_indicator.findViewById(R.id.candidate2);
            roundCornerProgressBar.setProgress(PercentageOne);
            roundCornerProgressBar.setProgressColor(Color.HSVToColor(vibrantOne));
            roundCornerProgressBar.setRotation(180);

            roundCornerProgressBarTwo.setProgress(PercentageTwo);
            roundCornerProgressBarTwo.setProgressColor(Color.HSVToColor(varantTwo));
            questionText.setText(entry.getKey());
            motion_view.addView(question_indicator);
          }
        }

        Set<Map.Entry<String, JsonElement>> entries =
            question.entrySet();//will return members of your object
        for (Map.Entry<String, JsonElement> entry : entries) {

          int obtainScrollOne = entry.getValue().getAsJsonObject().get("LWMP-01-0064").getAsInt();
          int obtainScrollTwo = entry.getValue().getAsJsonObject().get("LWMP-01-0063").getAsInt();

          Float PercentageOne;
          Float PercentageTwo;
          int TotalScore = entry.getValue().getAsJsonObject().get("LWMP-01-0064").getAsInt() + entry
              .getValue()
              .getAsJsonObject()
              .get("LWMP-01-0063")
              .getAsInt();
          PercentageOne = Float.valueOf((obtainScrollOne * 100 / TotalScore));
          PercentageTwo = Float.valueOf((obtainScrollTwo * 100 / TotalScore));
          View question_indicator =
              getLayoutInflater().inflate(R.layout.question_compare_layout, question_showcase,
                  false);
          TextView questionText = (TextView) question_indicator.findViewById(R.id.question_title);
          RoundCornerProgressBar roundCornerProgressBar =
              (RoundCornerProgressBar) question_indicator.findViewById(R.id.candidate1);
          RoundCornerProgressBar roundCornerProgressBarTwo =
              (RoundCornerProgressBar) question_indicator.findViewById(R.id.candidate2);
          roundCornerProgressBar.setProgress(PercentageOne);
          roundCornerProgressBar.setProgressColor(Color.HSVToColor(vibrantOne));
          roundCornerProgressBar.setRotation(180);
          roundCornerProgressBar.setBackgroundColor(Color.WHITE);

          roundCornerProgressBarTwo.setProgress(PercentageTwo);
          roundCornerProgressBarTwo.setProgressColor(Color.HSVToColor(varantTwo));
          questionText.setText(entry.getKey());
          question_showcase.addView(question_indicator);
        }
      }

      @Override public void onFailure(Throwable t) {

      }
    });
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

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      super.onBackPressed();
      return true;
    }
    return false;
  }

  private void setupHeader() {
    candidate = (Candidate) getIntent().getSerializableExtra(Config.CANDIDATE);
    candidateCompare = (Candidate) getIntent().getSerializableExtra(Config.CANDIDATE_COMPARE);
    candidateOne.setText(candidateCompare.getName());
    candidateTwo.setText(candidate.getName());

    Glide.with(this)
        .load(candidateCompare.getPhotoUrl())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .bitmapTransform(new CropCircleTransformation(this))
        .into(candidateOneView);

    Glide.with(this)
        .load(candidate.getPhotoUrl())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .bitmapTransform(new CropCircleTransformation(this))
        .into(candidateTwoView);

    Glide.with(this)
        .load(candidateCompare.getParty().getPartyFlag())
        .asBitmap()
        .into(new BitmapImageViewTarget(party_flag_one) {
          @Override protected void setResource(Bitmap resource) {
            // Do bitmap magic here
            super.setResource(resource);
            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
              public void onGenerated(Palette p) {
                // Use generated instance
                Palette.Swatch vibrantSwatch = p.getVibrantSwatch();
                vibrantOne = vibrantSwatch.getHsl();
              }
            });
          }
        });

    Glide.with(this)
        .load(candidate.getParty().getPartyFlag())
        .asBitmap()
        .into(new BitmapImageViewTarget(party_flag_two) {
          @Override protected void setResource(Bitmap resource) {
            // Do bitmap magic here
            super.setResource(resource);
            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
              public void onGenerated(Palette p) {
                // Use generated instance
                Palette.Swatch vibrantSwatch = p.getVibrantSwatch();
                varantTwo = vibrantSwatch.getHsl();
              }
            });
          }
        });
  }
}
