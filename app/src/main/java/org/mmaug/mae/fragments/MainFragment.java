package org.mmaug.mae.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.AvatarAdapter;
import org.mmaug.mae.models.Avatar;
import org.mmaug.mae.models.Voter;
import org.mmaug.mae.rest.RESTClient;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment
    implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

  @Bind(R.id.avatars) GridView mAvatarGrid;
  @Bind(R.id.date_of_birth) TextView mDateOfBirth;
  Calendar now;
  int maxAgeforVote = 18;
  String DATE_TAG = "Datepickerdialog";
  private Avatar mSelectedAvatar = Avatar.ONE;
  private View mSelectedAvatarView;

  public MainFragment() {
  }

  private void setUpGridView() {
    mAvatarGrid.setAdapter(new AvatarAdapter(getActivity()));
    mAvatarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mSelectedAvatarView = view;
        mSelectedAvatar = Avatar.values()[position];
      }
    });
    mAvatarGrid.setNumColumns(4);
    mAvatarGrid.setItemChecked(mSelectedAvatar.ordinal(), true);
  }

  @OnClick(R.id.post_fab) void checkVote() {
    Map<String, String> params = new HashMap<>();
    params.put("dateofbirth", "1945-06-19");
    params.put("nrc", "၁၂/ဗဟန(နိုင်)၁၀၉၄၅၈");
    params.put("father_name", "ဦးအောင်ဆန်း");

    Call<Voter> voterCall =
        RESTClient.getInstance().getService().searchVoter("အောင်ဆန်းစုကြည်", params);
    voterCall.enqueue(new Callback<Voter>() {
      @Override public void onResponse(Response<Voter> response) {
        Log.e("Response", response.body().getVoterName());
        //TODO check null value return  Log.e("Voter", "" + response.body());
      }

      @Override public void onFailure(Throwable t) {

      }
    });
  }

  @OnClick(R.id.date_of_birth) void DatePicker() {
    now = Calendar.getInstance();
    com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog =
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(this,
            now.get(Calendar.YEAR) - maxAgeforVote, now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH));
    datePickerDialog.show(getActivity().getFragmentManager(), DATE_TAG);
  }

  //TODO reenable
  private int calculateSpanCount() {
    int avatarSize = getResources().getDimensionPixelSize(R.dimen.size_fab);
    int avatarPadding = getResources().getDimensionPixelSize(R.dimen.spacing_micro);
    return mAvatarGrid.getWidth() / (avatarSize + avatarPadding);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    ButterKnife.bind(this, rootView);
    setUpGridView();
    return rootView;
  }

  @Override
  public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year,
      int monthOfYear, int dayOfMonth) {
    now = Calendar.getInstance();
    if ((now.get(Calendar.YEAR) - year) >= 18) {
      mDateOfBirth.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
    }
  }
}
