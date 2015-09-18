package org.mmaug.mae.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.TownshipAdapter;
import org.mmaug.mae.utils.DataUtils;
import org.mmaug.mae.utils.MixUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignUpFragment extends Fragment
    implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener,
    TownshipAdapter.OnItemClick {

  @Bind(R.id.date_of_birth) TextView mDateOfBirth;
  @Bind(R.id.user_name) EditText mUserName;
  @Bind(R.id.nrc_no) EditText mNrcNo;
  @Bind(R.id.nrc_township) EditText mNrcTownShip;
  @Bind(R.id.nrc_value) EditText mNrcValue;
  @Bind(R.id.township) TextView mTownship;
  @Bind(R.id.father_name) EditText mFatherName;
  @Bind(R.id.contentFragment) FrameLayout contenFragment;
  @Bind(R.id.main_fragment) NestedScrollView mainView;

  @Bind(R.id.et_search_township) EditText searchTownship;
  @Bind(R.id.rv_search_township) RecyclerView mTownshipList;
  @Bind(R.id.searchFragment) FrameLayout searchView;

  private TownshipAdapter adapter;
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

    mainView.setVisibility(View.GONE);
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
    //mainView.setVisibility(View.GONE);
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
    initRecyclerView();
    mainView.setVisibility(View.VISIBLE);
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

  @OnClick(R.id.township) void chooseTownship() {
    showHidSearchView(false);
  }

  private void initRecyclerView() {
    ArrayList<DataUtils.Township> townships = DataUtils.getInstance(getContext()).loadTownship();
    mTownshipList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    adapter = new TownshipAdapter(townships);
    adapter.setOnItemClick(this);
    mTownshipList.setAdapter(adapter);
  }

  //township onitem click
  @Override public void onItemClick(DataUtils.Township township) {
    showHidSearchView(true);
  }

  private void showHidSearchView(boolean visiblity) {
    mainView.setVisibility(visiblity ? View.VISIBLE : View.GONE);
    searchView.setVisibility(visiblity ? View.GONE : View.VISIBLE);
  }

  private void searchTownship(String township) {
    final Pattern pattern = Pattern.compile("^[A-Za-z, ]++$");
    if (pattern.matcher(township).matches()) {
      searchTownshipInEng();
    } else {
      searchTownshipMya();
    }
  }

  private void searchTownshipInEng() {

  }

  private void searchTownshipMya() {

  }
}
