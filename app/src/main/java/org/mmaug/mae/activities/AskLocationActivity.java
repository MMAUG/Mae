package org.mmaug.mae.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.TownshipAdapter;
import org.mmaug.mae.adapter.WardAdapter;
import org.mmaug.mae.base.BaseActivity;

/**
 * Created by poepoe on 20/10/15.
 */
public class AskLocationActivity extends BaseActivity
    implements View.OnTouchListener, AdapterView.OnItemClickListener {

  @Bind(R.id.et_search_township) EditText searchTownship;
  @Bind(R.id.rv_search_township) RecyclerView mTownshipList;
  @Bind(R.id.searchFragment) FrameLayout searchView;
  @Bind(R.id.ll_tsp_input_ivew) LinearLayout mainView;

  @Bind(R.id.tv_choose_loc) TextView tvChoose;
  @Bind(R.id.tv_tsp) TextView tvTownship;
  @Bind(R.id.tv_vw) TextView tvVW;
  @Bind(R.id.tv_save_location) TextView tvSaveLocation;
  @Bind(R.id.progressBar) ProgressBar progressBar;

  //private ArrayList<DataUtils.Township> townships = new ArrayList<>();
  //private ArrayList<DataUtils.Township> found = new ArrayList<>();
  //private ArrayList<DataUtils.VillageOrWard> wards = new ArrayList<>();
  //private ArrayList<DataUtils.VillageOrWard> foundWards = new ArrayList<>();

  private TownshipAdapter townshipAdapter;
  private WardAdapter wardAdapter;
  private String tspCode;
  private String townshipGson, wardGson;

  private int searchFlag = 0; //flag to decide which adapter and hint to use

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //ButterKnife.bind(this);
    //
    //String townshipStr = UserPrefUtils.getInstance(this).getTownship();
    //if (!townshipStr.equals("")) {
    //  DataUtils.Township township = new Gson().fromJson(townshipStr, DataUtils.Township.class);
    //  tvTownship.setTextColor(getResources().getColor(R.color.primary_text_color));
    //  tvTownship.setText(township.getTowhshipNameBurmese());
    //  tspCode = township.getTSPcode();
    //}
    //
    //if (tspCode != null) loadWards();
    //loadTownships();
    //
    //setFont();
    //initRecyclerView();
    //initEditText();
    //tvTownship.setOnTouchListener(this);
    //tvVW.setOnTouchListener(this);
  }

  //private void setFont() {
  //  if (isUnicode()) {
  //    tvChoose.setTypeface(getTypefaceLight());
  //    tvTownship.setTypeface(getTypefaceLight());
  //    tvVW.setTypeface(getTypefaceLight());
  //    tvSaveLocation.setTypeface(getTypefaceTitle());
  //  } else {
  //    MMTextUtils.getInstance(this)
  //        .prepareMultipleViews(tvChoose, tvTownship, tvSaveLocation, tvVW);
  //  }
  //}
  //
  //private void initRecyclerView() {
  //  wardAdapter = new WardAdapter(wards);
  //  townshipAdapter = new TownshipAdapter(townships);
  //  mTownshipList.setLayoutManager(
  //      new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
  //  townshipAdapter.setOnItemClickListener(this);
  //  wardAdapter.setOnItemClickListener(this);
  //  mTownshipList.setAdapter(townshipAdapter);
  //}

  @Override protected int getLayoutResource() {
    return R.layout.activity_ask_location;
  }

  @Override protected boolean getHomeUpEnabled() {
    return false;
  }

  @Override protected boolean needToolbar() {
    return false;
  }

  @Override protected String getToolbarText() {
    return null;
  }

  @Override public boolean onTouch(View v, MotionEvent event) {
    //if (v.getId() == tvTownship.getId()) {
    //  searchFlag = 1;
    //  showHidSearchView(false);
    //  mTownshipList.setAdapter(townshipAdapter);
    //  return true;
    //} else if (v.getId() == tvVW.getId()) {
    //  if (tspCode != null) {
    //    searchFlag = 2;
    //    showHidSearchView(false);
    //    if (wardAdapter.getItemCount() == 0) progressBar.setVisibility(View.VISIBLE);
    //    mTownshipList.setAdapter(wardAdapter);
    //  } else {
    //    Toast toast = new Toast(this);
    //    TextView textView = new TextView(this);
    //    textView.setText("မ\u103Cို့နယ\u103A အရင\u103Aရ\u103Dေးပ\u102B");
    //    if (isUnicode()) {
    //      textView.setTypeface(getTypefaceLight());
    //    } else {
    //      MMTextUtils.getInstance(this).prepareSingleView(textView);
    //    }
    //    textView.setPadding(16, 16, 16, 16);
    //    textView.setTextColor(Color.WHITE);
    //    textView.setBackgroundColor(getResources().getColor(R.color.accent_color));
    //    toast.setView(textView);
    //    toast.setGravity(Gravity.CENTER, 0, 10);
    //    toast.show();
    //  }
    //  return true;
    //}

    return false;
  }

  //private void initEditText() {
  //  searchTownship.addTextChangedListener(new TextWatcher() {
  //    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  //
  //    }
  //
  //    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
  //
  //    }
  //
  //    @Override public void afterTextChanged(Editable s) {
  //
  //      onSearch(s.toString());
  //    }
  //  });
  //}

  //private void onSearch(String s) {
  //  if (searchFlag == 1) {
  //    if (s.length() == 0) {
  //      found = townships;
  //      townshipAdapter.setTownships(townships);
  //    } else {
  //      searchTownship(s.toString().toLowerCase(), townships);
  //    }
  //  } else if (searchFlag == 2 && tspCode != null) {
  //    if (s.length() == 0) {
  //      foundWards = wards;
  //      wardAdapter.setWards(wards);
  //    } else {
  //      searchWards(s.toString().toLowerCase(), wards);
  //    }
  //  }
  //}
  //
  //private void searchWards(String ward, ArrayList<DataUtils.VillageOrWard> listToSearch) {
  //  final Pattern pattern = Pattern.compile("^[A-Za-z, ]++$");
  //  if (pattern.matcher(ward).matches()) {
  //    foundWards = searchWardInEng(ward, listToSearch);
  //  } else {
  //    foundWards = searchWardMya(ward, listToSearch);
  //  }
  //  wardAdapter.setWards(foundWards);
  //}
  //
  //private ArrayList<DataUtils.VillageOrWard> searchWardInEng(String input,
  //    ArrayList<DataUtils.VillageOrWard> listToSearch) {
  //  ArrayList<DataUtils.VillageOrWard> found = new ArrayList<>();
  //
  //  for (DataUtils.VillageOrWard villageOrWard : listToSearch) {
  //    if (villageOrWard.getVWName().toLowerCase().startsWith(input)) {
  //      found.add(villageOrWard);
  //    }
  //  }
  //  return found;
  //}
  //
  //private ArrayList<DataUtils.VillageOrWard> searchWardMya(String input,
  //    ArrayList<DataUtils.VillageOrWard> listToSearch) {
  //
  //  String unicode = MMTextUtils.getInstance(this).zgToUni(input);
  //  ArrayList<DataUtils.VillageOrWard> found = new ArrayList<>();
  //  for (DataUtils.VillageOrWard ward : listToSearch) {
  //    if (ward.getVWNameBurmese().startsWith(unicode)) {
  //      found.add(ward);
  //    }
  //  }
  //  return found;
  //}
  //
  //private void searchTownship(String township, ArrayList<DataUtils.Township> listToSearch) {
  //  final Pattern pattern = Pattern.compile("^[A-Za-z, ]++$");
  //  if (pattern.matcher(township).matches()) {
  //    found = searchTownshipInEng(township, listToSearch);
  //  } else {
  //    found = searchTownshipMya(township, listToSearch);
  //  }
  //  townshipAdapter.setTownships(found);
  //}
  //
  //private ArrayList<DataUtils.Township> searchTownshipInEng(String input,
  //    ArrayList<DataUtils.Township> listToSearch) {
  //  ArrayList<DataUtils.Township> found = new ArrayList<>();
  //
  //  for (DataUtils.Township township : listToSearch) {
  //    if (township.getTownshipName().toLowerCase().startsWith(input)) {
  //      found.add(township);
  //    }
  //  }
  //  return found;
  //}
  //
  //private ArrayList<DataUtils.Township> searchTownshipMya(String input,
  //    ArrayList<DataUtils.Township> listToSearch) {
  //
  //  String unicode = MMTextUtils.getInstance(this).zgToUni(input);
  //  ArrayList<DataUtils.Township> found = new ArrayList<>();
  //  for (DataUtils.Township township : listToSearch) {
  //    if (township.getTowhshipNameBurmese().startsWith(unicode)) {
  //      found.add(township);
  //    }
  //  }
  //  return found;
  //}
  //
  //private void showHidSearchView(boolean visibility) {
  //  mainView.setVisibility(visibility ? View.VISIBLE : View.GONE);
  //  searchView.setVisibility(visibility ? View.GONE : View.VISIBLE);
  //}

  @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //if (searchFlag == 1) {
    //  showHidSearchView(true);
    //  tvTownship.setText(found.get(position).getTowhshipNameBurmese());
    //  tvTownship.setTextColor(getResources().getColor(R.color.primary_text_color));
    //  if (isUnicode()) {
    //    tvTownship.setTypeface(getTypefaceLight());
    //  } else {
    //    MMTextUtils.getInstance(this).prepareSingleView(tvTownship);
    //  }
    //  tspCode = found.get(position).getTSPcode();
    //  wards.clear();
    //  foundWards.clear();
    //  townshipGson = new Gson().toJson(found.get(position));
    //  loadWards();
    //} else if (searchFlag == 2) {
    //  showHidSearchView(true);
    //  tvVW.setText(foundWards.get(position).getVWNameBurmese());
    //  tvVW.setTextColor(getResources().getColor(R.color.primary_text_color));
    //  if (isUnicode()) {
    //    tvVW.setTypeface(getTypefaceLight());
    //  } else {
    //    MMTextUtils.getInstance(this).prepareSingleView(tvVW);
    //  }
    //  wardGson = new Gson().toJson(foundWards.get(position));
    //}
  }

  //private void loadWards() {
  //  progressBar.setVisibility(View.VISIBLE);
  //  DataUtils.getInstance(this)
  //      .loadVWByTownship(tspCode)
  //      .subscribeOn(Schedulers.io())
  //      .subscribe(new LoadVWByTownship(AskLocationActivity.this));
  //}
  //
  //private void loadTownships() {
  //  progressBar.setVisibility(View.VISIBLE);
  //  DataUtils.getInstance(this)
  //      .loadObsTownship()
  //      .subscribeOn(Schedulers.io())
  //      .subscribe(new LoadTownship(AskLocationActivity.this));
  //}
  //
  //private static class LoadVWByTownship extends Subscriber<ArrayList<DataUtils.VillageOrWard>> {
  //
  //  private WeakReference<AskLocationActivity> ref;
  //
  //  public LoadVWByTownship(AskLocationActivity activity) {
  //    ref = new WeakReference<>(activity);
  //  }
  //
  //  @Override public void onCompleted() {
  //
  //  }
  //
  //  @Override public void onError(Throwable e) {
  //    e.printStackTrace();
  //  }
  //
  //  @Override public void onNext(final ArrayList<DataUtils.VillageOrWard> villageOrWards) {
  //    if (ref != null) {
  //      final AskLocationActivity activity = ref.get();
  //      new Handler(activity.getMainLooper()).post(new Runnable() {
  //        @Override public void run() {
  //          activity.wards = villageOrWards;
  //          activity.foundWards = villageOrWards;
  //          activity.wardAdapter.setWards(villageOrWards);
  //          activity.progressBar.setVisibility(View.GONE);
  //        }
  //      });
  //    }
  //  }
  //}
  //
  //private static class LoadTownship extends Subscriber<ArrayList<DataUtils.Township>> {
  //
  //  private WeakReference<AskLocationActivity> ref;
  //
  //  public LoadTownship(AskLocationActivity activity) {
  //    ref = new WeakReference<>(activity);
  //  }
  //
  //  @Override public void onCompleted() {
  //
  //  }
  //
  //  @Override public void onError(Throwable e) {
  //    e.printStackTrace();
  //  }
  //
  //  @Override public void onNext(final ArrayList<DataUtils.Township> townships) {
  //    if (ref != null) {
  //      final AskLocationActivity activity = ref.get();
  //      new Handler(activity.getMainLooper()).post(new Runnable() {
  //        @Override public void run() {
  //          activity.townships = townships;
  //          activity.found = townships;
  //          activity.townshipAdapter.setTownships(townships);
  //          activity.progressBar.setVisibility(View.GONE);
  //        }
  //      });
  //    }
  //  }
  //}
  //
  //@OnClick(R.id.cardview_save_location) void saveLocation() {
  //  if (townshipGson != null && wardGson != null) {
  //    UserPrefUtils.getInstance(this).saveTownShip(townshipGson);
  //    UserPrefUtils.getInstance(this).saveWard(wardGson);
  //    startActivity(new Intent(this, MainActivity.class));
  //    finish();
  //  } else {
  //    Toast toast = new Toast(this);
  //    TextView textView = new TextView(this);
  //    textView.setText("အချက်အလက်များကို ပြည့်စုံစွာဖြည့်စွက်ပေးပါ");
  //    if (isUnicode()) {
  //      textView.setTypeface(getTypefaceLight());
  //    } else {
  //      MMTextUtils.getInstance(this).prepareSingleView(textView);
  //    }
  //    textView.setPadding(16, 16, 16, 16);
  //    textView.setTextColor(Color.WHITE);
  //    textView.setBackgroundColor(getResources().getColor(R.color.accent_color));
  //    toast.setView(textView);
  //    toast.setGravity(Gravity.CENTER, 0, 10);
  //    toast.show();
  //  }
  //
  //}
  //
  //@Override public void onBackPressed() {
  //
  //  if (searchView.getVisibility() == View.VISIBLE) {
  //    showHidSearchView(true);
  //  } else {
  //    super.onBackPressed();
  //  }
  //}
}
