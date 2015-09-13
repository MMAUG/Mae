package org.mmaug.mae.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.models.User;
import org.mmaug.mae.rest.RESTClient;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment
    implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

  @Bind(R.id.date_of_birth) TextView mDateOfBirth;
  @Bind(R.id.user_name) EditText mUserName;
  @Bind(R.id.nrc_no) EditText mNrcNo;
  @Bind(R.id.nrc_township) EditText mNrcTownShip;
  @Bind(R.id.nrc_value) EditText mNrcValue;
  @Bind(R.id.township) EditText mTownship;
  @Bind(R.id.father_name) EditText mFatherName;
  @Bind(R.id.contentFragment) FrameLayout contenFragment;
  @Bind(R.id.main_fragment) NestedScrollView main_view;
  Calendar now;
  int maxAgeforVote = 18;
  String DATE_TAG = "Datepickerdialog";

  @OnClick(R.id.sign_up_card) void checkVote() {
    //TODO remove hardcoded strings
    //final String voterName = "ပိုးပိုးမြင့်ဆွေ";
    //StringBuilder stringBuilder = new StringBuilder();
    //stringBuilder.append("၉");
    //stringBuilder.append("/");
    //stringBuilder.append("ခအဇ");
    //stringBuilder.append("(နိုင်)");
    //stringBuilder.append("၀၂၂၉၄၂");
    //String voterNrc = stringBuilder.toString();
    //Map<String, String> params = new HashMap<>();
    //params.put(Config.VOTER_NAME, voterName);
    //params.put(Config.DATE_OF_BIRTH, "1988-07-07");
    //params.put(Config.NRC, voterNrc);
    //params.put(Config.FATHER_NAME, "ဦးမြင့်ဆွေ");
    //params.put(Config.TOWNSHIP, "ချမ်းအေးသာစံ");

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(mNrcNo.getText().toString());
    stringBuilder.append("/");
    stringBuilder.append(mNrcTownShip.getText().toString());
    stringBuilder.append("(နိုင်)");
    stringBuilder.append(mNrcValue.getText());
    String voterNrc = stringBuilder.toString();
    Map<String, String> params = new HashMap<>();
    params.put(Config.VOTER_NAME, mUserName.getText().toString());
    params.put(Config.DATE_OF_BIRTH, mDateOfBirth.getText().toString());
    params.put(Config.NRC, voterNrc);
    params.put(Config.FATHER_NAME, mFatherName.getText().toString());
    params.put(Config.TOWNSHIP, mTownship.getText().toString());

    final Call<User> registerUser = RESTClient.getService().registerUser(params);
    registerUser.enqueue(new Callback<User>() {
      @Override public void onResponse(Response<User> response) {
        if (response.code() == 200) {
          main_view.setVisibility(View.GONE);
          contenFragment.setVisibility(View.VISIBLE);
          HomeFragment homeFragment = new HomeFragment();
          FragmentManager fm = getActivity().getSupportFragmentManager();
          FragmentTransaction transaction = fm.beginTransaction();
          transaction.replace(R.id.contentFragment, homeFragment);
          transaction.commit();
        }
        Log.e("Response", response.code() + " " + response.message());
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

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    ButterKnife.bind(this, rootView);
    main_view.setVisibility(View.VISIBLE);
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
