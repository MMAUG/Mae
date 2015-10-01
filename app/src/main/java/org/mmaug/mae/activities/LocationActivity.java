package org.mmaug.mae.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonGeometry;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPointStyle;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.adapter.TownshipAdapter;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.models.Geo;
import org.mmaug.mae.models.GeoReturnObject;
import org.mmaug.mae.rest.RESTClient;
import org.mmaug.mae.utils.DataUtils;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.MixUtils;
import org.mmaug.mae.utils.UserPrefUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import timber.log.Timber;

/**
 * Created by yemyatthu on 9/18/15.
 */
public class LocationActivity extends BaseActivity implements AdapterView.OnItemClickListener {
  @Bind(R.id.location_name) TextView mLocationName;
  @Bind(R.id.month_day_left) TextView monthDayLef;
  @Bind(R.id.hour_minute_left) TextView tvHourLeft;
  @Bind(R.id.to_vote) TextView tvToVote;
  @Bind(R.id.hidden_view) View hiddenView;
  @Bind(R.id.rv_search_township) RecyclerView mTownshipList;
  @Bind(R.id.et_search_township) EditText searchTownship;
  @Bind(R.id.searchFragment) FrameLayout searchView;
  @Bind(R.id.progressBar) ProgressBar mProgressBar;
  private DataUtils.Township myTownShip;
  private ArrayList<DataUtils.Township> townships;
  private ArrayList<DataUtils.Township> found = new ArrayList<>();
  private TownshipAdapter adapter;
  private GoogleMap mMap;

  @Override protected int getLayoutResource() {
    return R.layout.activity_location;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return "မဲရုံတည်နေရာ";
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    setTypeFace();
    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
        R.id.location_detail_map)).getMap();
    if (mMap != null) {
      mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(16.8000, 96.1500), 4));
    }
    Map<String, String> params = new HashMap<>();
    UserPrefUtils userPrefUtils = new UserPrefUtils(this);
    String townShipString = userPrefUtils.getTownship();
    if (townShipString != null && townShipString.length() > 0) {
      myTownShip = new Gson().fromJson(townShipString, DataUtils.Township.class);
      setToolbarTitle(myTownShip.getTowhshipNameBurmese());
    }
    if (myTownShip == null) {
      showHidSearchView(false);
      initEditText();
      initRecyclerView();
    } else {
      params.put("dt_pcode", myTownShip.getDPcode());
      final Call<GeoReturnObject> geoCall = RESTClient.getService(this).getLocationList(params);
      geoCall.enqueue(new Callback<GeoReturnObject>() {
        @Override public void onResponse(Response<GeoReturnObject> response) {
          Geo geo = response.body().getData().get(0);
          try {
            setUpMap(LocationActivity.this, geo);
          }catch (Exception e){

          }
        }

        @Override public void onFailure(Throwable t) {
          Timber.e(t.getMessage());
        }
      });
    }
  }

  private void initRecyclerView() {
    townships = DataUtils.getInstance(this).loadTownship();
    found = townships;
    mTownshipList.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    adapter = new TownshipAdapter(found);
    adapter.setOnItemClickListener(this);
    mTownshipList.setAdapter(adapter);
  }

  private void initEditText() {
    searchTownship.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {

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
    ArrayList<DataUtils.Township> found = new ArrayList<>();

    for (DataUtils.Township township : listToSearch) {
      if (township.getTowhshipNameBurmese().startsWith(input)) {
        found.add(township);
      }
    }
    return found;
  }

  void setTypeFace() {
    if (isUnicode()) {
      mLocationName.setTypeface(getTypefaceLight());
      monthDayLef.setTypeface(getTypefaceTitle());
      tvHourLeft.setTypeface(getTypefaceLight());
      tvToVote.setTypeface(getTypefaceLight());
    } else {
      MMTextUtils.getInstance(this)
          .prepareMultipleViews(mLocationName, monthDayLef, tvHourLeft, tvToVote);
    }

  }

  private void setUpMap(AppCompatActivity activity, Geo geo) {
    Gson gson = new GsonBuilder().create();
    String object = gson.toJson(geo);
    mLocationName.setVisibility(View.VISIBLE);
    mLocationName.setText(geo.getProperties().getDT());
    try {
      GeoJsonLayer layer = new GeoJsonLayer(mMap, new JSONObject(object));
      GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
      GeoJsonFeature geoJsonFeature = null;
      for (GeoJsonFeature feature : layer.getFeatures()) {
        GeoJsonGeometry geoJsonGeometry = feature.getGeometry();
        geoJsonFeature = new GeoJsonFeature(geoJsonGeometry, null, null, null);
        pointStyle = feature.getPointStyle();
      }
      GeoJsonPolygonStyle geoJsonPolygonStyle = layer.getDefaultPolygonStyle();
      geoJsonPolygonStyle.setFillColor(
          activity.getResources().getColor(R.color.geojson_background_color));
      geoJsonPolygonStyle.setStrokeColor(
          activity.getResources().getColor(R.color.geojson_stroke_color));
      geoJsonPolygonStyle.setStrokeWidth(2);

      pointStyle.setTitle(geo.getProperties().getDT());
      pointStyle.setSnippet(geo.getProperties().getDT());

      if (geoJsonFeature != null) {
        geoJsonFeature.setPointStyle(pointStyle);
        geoJsonFeature.setPolygonStyle(geoJsonPolygonStyle);
        layer.addFeature(geoJsonFeature);
      }
      JsonArray jsonElements = geo.
          getGeometry().getCoordinates().getAsJsonArray();
      JsonArray latLangArray =
          jsonElements.getAsJsonArray().get(0).getAsJsonArray().get(0).getAsJsonArray();
      double lon;
      double lat;
      try {

        lat = latLangArray.get(1).getAsDouble();
        lon = latLangArray.get(0).getAsDouble();
      } catch (IllegalStateException e) {
        lat = latLangArray.get(0).getAsJsonArray().get(1).getAsDouble();
        lon = latLangArray.get(0).getAsJsonArray().get(0).getAsDouble();
      }
      if (mMap == null) {
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
            R.id.location_detail_map)).getMap();
      }
      try {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 8));
      }catch (Exception e){

      }

      layer.addLayerToMap();
      //double lat;
      //double lon;
      //LatLngBounds.Builder builder = new LatLngBounds.Builder();
      //for (JsonElement element : jsonElements) {
      //  JsonArray latlng = element.getAsJsonArray();
      //  for (JsonElement element1 : latlng) {
      //    try {
      //
      //      lat = element1.getAsDouble();
      //      lon = element1.getAsDouble();
      //      builder.include(new LatLng(lat,
      //          lon));
      //    } catch (IllegalStateException e) {
      //      for(JsonElement element2:element1.getAsJsonArray()) {
      //        lat = element2.getAsDouble();
      //        lon = element2.getAsDouble();
      //        builder.include(new LatLng(lat,
      //            lon));
      //      }
      //    }
      //
      //  }
      //}
      //
      //
      //if (mMap == null) {
      //  mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
      //      R.id.location_detail_map)).getMap();
      //}
      //
      //final LatLngBounds bounds = builder.build();
      //int padding = (int) getResources().getDimension(R.dimen.spacing_minor);
      //CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
      //mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
      //  @Override public void onMapClick(LatLng latLng) {
      //    if (bounds.contains(latLng)) {
      //      new EasyDialog(LocationActivity.this).setLayoutResourceId(
      //          R.layout.tip_layout)//layout resource id
      //          .setBackgroundColor(LocationActivity.this.getResources().getColor(R.color.primary))
      //          .setLocationByAttachedView(hiddenView)
      //          .setAnimationAlphaShow(1000, 0.0f, 1.0f)
      //          .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
      //          .setTouchOutsideDismiss(true)
      //          .setMatchParent(false)
      //          .show();
      //    }
      //  }
      //});
      //mMap.moveCamera(cu);
      //layer.addLayerToMap();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override protected void onResume() {
    super.onResume();
    HashMap<String, String> values = null;
    try {
      values = MixUtils.formatTime(MixUtils.calculateTimeLeftToVote());
      String monthDay = MixUtils.convertToBurmese(values.get("month_day"));
      monthDayLef.setText(monthDay);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      super.onBackPressed();
      return true;
    }
    return false;
  }

  @OnClick(R.id.tv_toolbar_title) void searchTown() {
    showHidSearchView(false);
    mProgressBar.setVisibility(View.GONE);
    initEditText();
    initRecyclerView();
  }

  @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    showHidSearchView(true);
    UserPrefUtils prefUtils = new UserPrefUtils(LocationActivity.this);
    prefUtils.saveTownShip(new Gson().toJson(found.get(i)));
    setToolbarTitle(found.get(i).getTowhshipNameBurmese());
    Map<String, String> pyithuParams = new HashMap<>();
    //Probably, there won't be much more than 200 candidates for a township for the same legislature
    pyithuParams.put(Config.PER_PAGE, "200");
    showHidSearchView(true);
    mProgressBar.setVisibility(View.VISIBLE);
    //TODO remove hardcoded PCODE
    Timber.i("DTPCODE", "" + found.get(i).getDPcode());
    pyithuParams.put(Config.DT_PCODE, found.get(i).getDPcode());
    final Call<GeoReturnObject> geoCall = RESTClient.getService(this).getLocationList(pyithuParams);
    geoCall.enqueue(new Callback<GeoReturnObject>() {
      @Override public void onResponse(Response<GeoReturnObject> response) {
        Geo geo = response.body().getData().get(0);
        Log.e("Get Data", response.body().getData().get(0).getProperties().getDT());
        Log.e("Geo", "" + geo.getProperties().getDTPCODE());
        try {
          setUpMap(LocationActivity.this, geo);
        }catch (Exception e){

        }
        mProgressBar.setVisibility(View.GONE);
      }

      @Override public void onFailure(Throwable t) {

      }
    });
  }
}
