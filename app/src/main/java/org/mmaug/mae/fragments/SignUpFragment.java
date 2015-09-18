package org.mmaug.mae.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
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
import org.mmaug.mae.utils.MixUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignUpFragment extends Fragment
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
  int defaultYear;
  int defaultMonth;
  int defaultDate;
  String DATE_TAG = "Datepickerdialog";

  @OnClick(R.id.sign_up_card) void checkVote() {

    //// FIXME: 9/15/15 If the server return proper api response,uncomment below codes <YE MYAT THU>

    /**
     * DELETE THIS CODES
     */
    final String voterName = "ရဲမြတ်သူ";
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("၁၂");
    stringBuilder.append("/");
    stringBuilder.append("အစန");
    stringBuilder.append("(နိုင်)");
    stringBuilder.append("၂၀၈၅၇၃");
    String voterNrc = stringBuilder.toString();
    Map<String, String> params = new HashMap<>();
    params.put(Config.VOTER_NAME, voterName);
    params.put(Config.DATE_OF_BIRTH, "1990-01-31");
    params.put(Config.NRC, voterNrc);
    params.put(Config.FATHER_NAME, "ဦးအောင်ကျော်မြင့်");
    params.put(Config.TOWNSHIP, "အင်းစိန်");

    main_view.setVisibility(View.GONE);
    MixUtils.makeSlide(contenFragment);
    contenFragment.setVisibility(View.VISIBLE);
    HomeFragment homeFragment = new HomeFragment();
    FragmentManager fm = getActivity().getSupportFragmentManager();
    FragmentTransaction transaction = fm.beginTransaction();
    transaction.replace(R.id.contentFragment, homeFragment);
    transaction.commit();

    /***
     * UNCOMMENT THIS CODES
     */
    //StringBuilder stringBuilder = new StringBuilder();
    //stringBuilder.append(mNrcNo.getText().toString());
    //stringBuilder.append("/");
    //stringBuilder.append(mNrcTownShip.getText().toString());
    //stringBuilder.append("(နိုင်)");
    //stringBuilder.append(mNrcValue.getText());
    //String voterNrc = stringBuilder.toString();
    //Map<String, String> params = new HashMap<>();
    //params.put(Config.VOTER_NAME, mUserName.getText().toString());
    //params.put(Config.DATE_OF_BIRTH, mDateOfBirth.getText().toString());
    //params.put(Config.NRC, voterNrc);
    //params.put(Config.FATHER_NAME, mFatherName.getText().toString());
    //params.put(Config.TOWNSHIP, mTownship.getText().toString());

    //final Call<User> registerUser = RESTClient.getService().registerUser(params);
    //registerUser.enqueue(new Callback<User>() {
    //  @Override public void onResponse(Response<User> response) {

    // if (response.code() == 201) {
    //main_view.setVisibility(View.GONE);
    //contenFragment.setVisibility(View.VISIBLE);
    //HomeFragment homeFragment = new HomeFragment();
    //FragmentManager fm = getActivity().getSupportFragmentManager();
    //FragmentTransaction transaction = fm.beginTransaction();
    //transaction.replace(R.id.contentFragment, homeFragment);
    //      transaction.commit();
    //    }
    //    Log.e("Response", response.code() + " " + response.message());
    //  }
    //
    //  @Override public void onFailure(Throwable t) {
    //
    //  }
    //});
    //}
  }

  @OnClick(R.id.date_of_birth) void DatePicker() {
    now = Calendar.getInstance();
    com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog;

    datePickerDialog =
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(this, defaultYear,
            defaultMonth, defaultDate);
    datePickerDialog.show(getActivity().getFragmentManager(), DATE_TAG);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
    ButterKnife.bind(this, rootView);
    main_view.setVisibility(View.VISIBLE);
    now = Calendar.getInstance();
    defaultYear = now.get(Calendar.YEAR) - maxAgeforVote;
    defaultMonth = now.get(Calendar.MONTH);
    defaultDate = now.get(Calendar.DAY_OF_MONTH);
    return rootView;
  }

  @Override
  public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year,
      int monthOfYear, int dayOfMonth) {
    defaultYear = year;
    defaultDate = dayOfMonth;
    defaultMonth = monthOfYear;
    if ((now.get(Calendar.YEAR) - defaultYear) >= 18) {
      mDateOfBirth.setText(defaultYear + "-" + defaultMonth + "-" + defaultDate);
    }
  }
}
