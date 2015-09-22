package org.mmaug.mae.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Set;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.rest.RESTClient;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class CandidateCompareActivity extends BaseActivity {
  @Bind(R.id.question_showcase) LinearLayout question_showcase;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    Call<JsonElement> compareQuestionCall =
        RESTClient.getService().getCompareQuestion("LWMP-01-0063", "LWMP-01-0064");
    compareQuestionCall.enqueue(new Callback<JsonElement>() {
      @Override public void onResponse(Response<JsonElement> response) {
        JsonObject question = response.body().getAsJsonObject().get("questions").getAsJsonObject();

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
          roundCornerProgressBar.setProgressColor(Color.RED);
          roundCornerProgressBar.setRotation(180);

          roundCornerProgressBarTwo.setProgress(PercentageTwo);
          roundCornerProgressBarTwo.setProgressColor(Color.GREEN);
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
}
