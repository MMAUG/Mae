package org.mmaug.mae.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.activities.MainActivity;
import org.mmaug.mae.adapter.TownshipAdapter;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.models.User;
import org.mmaug.mae.rest.RESTClient;
import org.mmaug.mae.utils.DataUtils;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.RestCallback;
import org.mmaug.mae.utils.UserPrefUtils;
import org.mmaug.mae.utils.VoterCheckDialog;
import retrofit.Call;
import retrofit.Response;

public class SignUpFragment extends Fragment
    implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener,
    AdapterView.OnItemClickListener, VoterCheckDialog.OkButtonClickListener {

  @Bind(R.id.tv_dob_label) TextView mDOBLabel;
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
  @Bind(R.id.to_check_mae) TextView toCheckMae;
  @Bind(R.id.check_button) TextView checkButton;
  @Bind(R.id.myanmarTextPlease) TextView myanmarTextPlease;
  @Bind(R.id.skip_card_button) TextView skip_card_button;
  Calendar now;
  int maxAgeforVote = 18;
  int defaultYear;
  int defaultMonth;
  int defaultDate;

  String DATE_TAG = "Datepickerdialog";
  private ArrayList<DataUtils.Township> townships;
  private ArrayList<DataUtils.Township> found = new ArrayList<>();
  private TownshipAdapter adapter;
  private String townshipGson;
  private boolean isValid;
  private boolean isFirstTimeOrSkip;
  private Typeface typefacelight, typefaceTitle;
  private boolean isUnicode;

  @OnClick(R.id.sign_up_card) void checkVote() {

    if (!checkFieldisValid()) {
      Toast toast = new Toast(getActivity());
      TextView textView = new TextView(getActivity());
      textView.setText("အချက်အလက်များကို ပြည့်စုံစွာဖြည့်စွက်ပေးပါ");
      if (isUnicode) {
        textView.setTypeface(typefacelight);
      } else {
        MMTextUtils.getInstance(getContext()).prepareSingleView(textView);
      }
      textView.setPadding(16, 16, 16, 16);
      textView.setTextColor(Color.WHITE);
      textView.setBackgroundColor(getResources().getColor(R.color.accent_color));
      toast.setView(textView);
      toast.setGravity(Gravity.CENTER, 0, 10);
      toast.show();
    } else {
      MMTextUtils mmTextUtils = MMTextUtils.getInstance(getContext());
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(mmTextUtils.zgToUni(mNrcNo.getText().toString()));
      stringBuilder.append("/");
      stringBuilder.append(mmTextUtils.zgToUni(mNrcTownShip.getText().toString()));
      stringBuilder.append("(နိုင်)");
      stringBuilder.append(mmTextUtils.zgToUni(mNrcValue.getText().toString()));
      String voterNrc = stringBuilder.toString();
      final Map<String, String> params = new HashMap<>();
      params.put(Config.VOTER_NAME, mmTextUtils.zgToUni(mUserName.getText().toString()));
      params.put(Config.DATE_OF_BIRTH, mDateOfBirth.getText().toString());
      params.put(Config.NRC, voterNrc);
      params.put(Config.FATHER_NAME, mmTextUtils.zgToUni(mFatherName.getText().toString()));
      params.put(Config.TOWNSHIP, mTownship.getText().toString());

      final VoterCheckDialog.OkButtonClickListener listener = this;

      final Call<User> registerUser =
          RESTClient.getService(getActivity()).registerUser(params);
      registerUser.enqueue(new RestCallback<User>() {
        @Override public void onResponse(Response<User> response) {
          VoterCheckDialog dialog = new VoterCheckDialog((MainActivity) getActivity(), listener);
          UserPrefUtils userPrefUtils = UserPrefUtils.getInstance(getActivity());

          userPrefUtils.saveSkip(true);
          userPrefUtils.saveUserName(params.get(Config.VOTER_NAME));
          userPrefUtils.saveBirthDate(params.get(Config.DATE_OF_BIRTH));
          userPrefUtils.saveNRC(params.get(Config.NRC));
          userPrefUtils.saveFatherName(params.get(Config.FATHER_NAME));
          userPrefUtils.saveTownShip(townshipGson);

          // Set uni or zg for dialog
          dialog.unicode(isUnicode);

          if (response.code() == 200) {
            userPrefUtils.setValid(true);
            dialog.showValid(getActivity().getLayoutInflater());
          } else {
            userPrefUtils.setValid(false);
            dialog.showInvalid(getActivity().getLayoutInflater());
          }
        }
      });
    }
  }

  private boolean checkFieldisValid() {
    if (TextUtils.isEmpty(mUserName.getText()) || TextUtils.isEmpty(mFatherName.getText())
        || TextUtils.isEmpty(mDateOfBirth.getText()) ||
        TextUtils.isEmpty(mNrcNo.getText()) || TextUtils.isEmpty(mNrcTownShip.getText())
        || TextUtils.isEmpty(mNrcValue.getText()) ||
        TextUtils.isEmpty(mTownship.getText())) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Override the Dialog Box's OK Button Click Event from VoterCheckDialog.
   * @param view Click View
   * @param isValid Voter Check Result
   */
  @Override
  public void onClickOkFromDialog(View view, boolean isValid) {
    mainView.setVisibility(View.GONE);
    contenFragment.setVisibility(View.VISIBLE);
    HomeFragment homeFragment = new HomeFragment();
    FragmentManager fm = getActivity().getSupportFragmentManager();
    FragmentTransaction transaction = fm.beginTransaction();
    transaction.replace(R.id.contentFragment, homeFragment);
    transaction.commit();
  }

  @OnClick(R.id.skip_card_button) void SkipCard() {
    UserPrefUtils userPrefUtils = new UserPrefUtils(getActivity());
    userPrefUtils.saveSkip(true);
    isFirstTimeOrSkip = true;
    mainView.setVisibility(View.GONE);
    contenFragment.setVisibility(View.VISIBLE);
    HomeFragment homeFragment = new HomeFragment();
    FragmentManager fm = getActivity().getSupportFragmentManager();
    FragmentTransaction transaction = fm.beginTransaction();
    transaction.replace(R.id.contentFragment, homeFragment);
    transaction.commit();
  }

  @OnClick(R.id.date_of_birth) void DatePicker() {
    now = Calendar.getInstance();
    com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog;

    datePickerDialog =
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(this, defaultYear,
                defaultMonth, defaultDate);
    datePickerDialog.show(getActivity().getFragmentManager(), DATE_TAG);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    BaseActivity baseActivity = (BaseActivity) getActivity();
    typefaceTitle = baseActivity.getTypefaceTitle();
    typefacelight = baseActivity.getTypefaceLight();
    isUnicode = baseActivity.isUnicode();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
    ButterKnife.bind(this, rootView);

    //search township view
    initRecyclerView();
    initEditText();
    UserPrefUtils userPrefUtils = UserPrefUtils.getInstance(getContext());
    isValid = userPrefUtils.isValid();
    isFirstTimeOrSkip = userPrefUtils.isSKIP();

    mainView.setVisibility(View.VISIBLE);
    now = Calendar.getInstance();
    defaultYear = now.get(Calendar.YEAR) - maxAgeforVote;
    defaultMonth = now.get(Calendar.MONTH);
    defaultDate = now.get(Calendar.DAY_OF_MONTH);

    if (isUnicode) {
      toCheckMae.setTypeface(typefaceTitle);
      checkButton.setTypeface(typefacelight);
      myanmarTextPlease.setTypeface(typefacelight);
      skip_card_button.setTypeface(typefacelight);
      mUserName.setHint(getString(R.string.name_uni));
      mFatherName.setHint(getString(R.string.father_name_uni));
    } else {

      MMTextUtils.getInstance(getContext())
          .prepareMultipleViews(toCheckMae, checkButton, myanmarTextPlease, skip_card_button,
              mDateOfBirth, mDOBLabel, mTownship);
      mUserName.setHint(getString(R.string.name_zg));
      mFatherName.setHint(getString(R.string.father_name_zg));
    }
    if (isFirstTimeOrSkip) {
      mainView.setVisibility(View.GONE);
      contenFragment.setVisibility(View.VISIBLE);
      HomeFragment homeFragment = new HomeFragment();
      FragmentManager fm = getActivity().getSupportFragmentManager();
      FragmentTransaction transaction = fm.beginTransaction();
      transaction.replace(R.id.contentFragment, homeFragment);
      transaction.commit();
    }
    if (userPrefUtils.getTownship() != null && userPrefUtils.getTownship().length() > 0) {
      DataUtils.Township township =
          new Gson().fromJson(userPrefUtils.getTownship(), DataUtils.Township.class);
      mTownship.setText(township.getTowhshipNameBurmese());
      mTownship.setTextColor(getResources().getColor(R.color.primary_text_color));
      if (!isUnicode) MMTextUtils.getInstance(getActivity()).prepareSingleView(mTownship);
    }
    if (userPrefUtils.getUserName() != null && userPrefUtils.getUserName().length() > 0) {
      mUserName.setText(userPrefUtils.getUserName());
      if (!isUnicode) MMTextUtils.getInstance(getActivity()).prepareSingleView(mUserName);
    }
    if (userPrefUtils.getFatherName() != null && userPrefUtils.getFatherName().length() > 0) {
      mFatherName.setText(userPrefUtils.getFatherName());
      if (!isUnicode) MMTextUtils.getInstance(getActivity()).prepareSingleView(mFatherName);
    }
    if (userPrefUtils.getBirthDate() != null && userPrefUtils.getBirthDate().length() > 0) {
      mDateOfBirth.setText(userPrefUtils.getBirthDate());
    }
    if (userPrefUtils.getNrc() != null && userPrefUtils.getNrc().length() > 0) {
      String nrcLong = userPrefUtils.getNrc();
      try {
        String nrc1 = nrcLong.split("/")[0];
        mNrcNo.setText(nrc1);
      } catch (Exception e) {

      }
      try {
        String nrc2 = nrcLong.split("/")[1].split("\\(နိုင်\\)")[0];
        mNrcTownShip.setText(nrc2);
      } catch (Exception e) {

      }
      try {
        String nrc3 = nrcLong.split("/")[1].split("\\(နိုင်\\)")[1];
        mNrcValue.setText(nrc3);
      } catch (Exception e) {

      }
    }
    return rootView;
  }

  @Override
  public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year,
      int monthOfYear, int dayOfMonth) {
    defaultYear = year;
    defaultDate = dayOfMonth;
    defaultMonth = monthOfYear;
    defaultMonth = defaultMonth + 1;
    if ((now.get(Calendar.YEAR) - defaultYear) >= 18) {
      mDateOfBirth.setText(defaultYear + "-" + defaultMonth + "-" + defaultDate);
    }
  }

  @OnClick(R.id.township) void chooseTownship() {
    showHidSearchView(false);
  }

  private void initRecyclerView() {
    townships = DataUtils.getInstance(getContext()).loadTownship();
    found = townships;
    mTownshipList.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    adapter = new TownshipAdapter(found);
    adapter.setOnItemClickListener(this);
    mTownshipList.setAdapter(adapter);
  }

  private void initEditText() {
    searchTownship.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {

        if (s.length() == 0) {
          found = townships;
          adapter.setTownships(townships);
        } else {
          searchTownship(s.toString().toLowerCase(), townships);
        }
      }
    });
  }

  private void showHidSearchView(boolean visibility) {
    mainView.setVisibility(visibility ? View.VISIBLE : View.GONE);
    searchView.setVisibility(visibility ? View.GONE : View.VISIBLE);
  }

  private void searchTownship(String township, ArrayList<DataUtils.Township> listToSearch) {
    final Pattern pattern = Pattern.compile("^[A-Za-z, ]++$");
    if (pattern.matcher(township).matches()) {
      found = searchTownshipInEng(township, listToSearch);
    } else {
      found = searchTownshipMya(township, listToSearch);
    }
    adapter.setTownships(found);
  }

  private ArrayList<DataUtils.Township> searchTownshipInEng(String input,
      ArrayList<DataUtils.Township> listToSearch) {
    ArrayList<DataUtils.Township> found = new ArrayList<>();

    for (DataUtils.Township township : listToSearch) {
      if (township.getTownshipName().toLowerCase().startsWith(input)) {
        found.add(township);
      }
    }
    return found;
  }

  private ArrayList<DataUtils.Township> searchTownshipMya(String input,
      ArrayList<DataUtils.Township> listToSearch) {

    String unicode = MMTextUtils.getInstance(getContext()).zgToUni(input);
    ArrayList<DataUtils.Township> found = new ArrayList<>();
    for (DataUtils.Township township : listToSearch) {
      if (township.getTowhshipNameBurmese().startsWith(unicode)) {
        found.add(township);
      }
    }
    return found;
  }

  @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    showHidSearchView(true);
    mTownship.setText(found.get(position).getTowhshipNameBurmese());
    mTownship.setTextColor(getResources().getColor(R.color.primary_text_color));
    if (isUnicode) {
      mTownship.setTypeface(typefacelight);
    } else {
      MMTextUtils.getInstance(getContext()).prepareSingleView(mTownship);
    }
    townshipGson = new Gson().toJson(found.get(position));
  }

  public boolean interceptOnBackPressed() {
    if (searchView.getVisibility() == View.VISIBLE) {
      showHidSearchView(true);
      return true;
    }
    return false;
  }

}

