package org.mmaug.mae.activities;

import android.graphics.Typeface;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonGeometry;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPointStyle;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;
import com.michael.easydialog.EasyDialog;
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
import org.mmaug.mae.utils.FontCache;
import org.mmaug.mae.utils.MixUtils;
import org.mmaug.mae.utils.UserPrefUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by yemyatthu on 9/18/15.
 */
public class LocationActivity extends BaseActivity implements AdapterView.OnItemClickListener {
  @Bind(R.id.location_name) TextView mLocationName;
  @Bind(R.id.month_day_left) TextView monthDayLef;
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
    // TODO: 9/18/15 Insert Choosen Township Name Here
    return "Location";
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
    }
    if (myTownShip == null) {
      showHidSearchView(false);
      initEditText();
      initRecyclerView();
    } else {
      // TODO: 9/18/15 Insert Choosen DT_PCODE here
      params.put("dt_pcode", myTownShip.getDPcode());
      final Call<GeoReturnObject> geoCall = RESTClient.getMPSService(this).getLocationList(params);
      geoCall.enqueue(new Callback<GeoReturnObject>() {
        @Override public void onResponse(Response<GeoReturnObject> response) {
          Geo geo = response.body().getData().get(0);
          setUpMap(LocationActivity.this, geo);
        }

        @Override public void onFailure(Throwable t) {

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
    Typeface typefaceTitle = FontCache.get("MyanmarAngoun.ttf", this);
    Typeface typefacelight = FontCache.get("pyidaungsu.ttf", this);
    mLocationName.setTypeface(typefacelight);
    monthDayLef.setTypeface(typefaceTitle);
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

      LatLngBounds.Builder builder = new LatLngBounds.Builder();
      for (JsonElement element : jsonElements) {
        JsonArray latlng = element.getAsJsonArray();
        for (JsonElement element1 : latlng) {
          builder.include(new LatLng(element1.getAsJsonArray().get(1).getAsDouble(),
              element1.getAsJsonArray().get(0).getAsDouble()));
        }
      }

      if (mMap == null) {
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
            R.id.location_detail_map)).getMap();
      }

      final LatLngBounds bounds = builder.build();
      int padding = (int) getResources().getDimension(R.dimen.spacing_minor);
      CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
      mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
        @Override public void onMapClick(LatLng latLng) {
          if (bounds.contains(latLng)) {
            new EasyDialog(LocationActivity.this).setLayoutResourceId(
                R.layout.tip_layout)//layout resource id
                .setBackgroundColor(LocationActivity.this.getResources().getColor(R.color.primary))
                .setLocationByAttachedView(hiddenView)
                .setAnimationAlphaShow(1000, 0.0f, 1.0f)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(false)
                .show();
          }
        }
      });
      mMap.moveCamera(cu);
      layer.addLayerToMap();
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
    tvToolbarTitle.setText(found.get(i).getTowhshipNameBurmese());
    Map<String, String> pyithuParams = new HashMap<>();
    //Probably, there won't be much more than 200 candidates for a township for the same legislature
    pyithuParams.put(Config.PER_PAGE, "200");
    showHidSearchView(true);
    mProgressBar.setVisibility(View.VISIBLE);
    //TODO remove hardcoded PCODE
    Log.e("DTPCODE", "" + found.get(i).getDPcode());
    pyithuParams.put(Config.DT_PCODE, found.get(i).getDPcode());
    final Call<GeoReturnObject> geoCall = RESTClient.getMPSService(this).getLocationList(pyithuParams);
    geoCall.enqueue(new Callback<GeoReturnObject>() {
      @Override public void onResponse(Response<GeoReturnObject> response) {
        Geo geo = response.body().getData().get(0);
        Log.e("Get Data", response.body().getData().get(0).getProperties().getDT());
        Log.e("Geo", "" + geo.getProperties().getDTPCODE());
        setUpMap(LocationActivity.this, geo);
        mProgressBar.setVisibility(View.GONE);
      }

      @Override public void onFailure(Throwable t) {

      }
    });
  }
}
