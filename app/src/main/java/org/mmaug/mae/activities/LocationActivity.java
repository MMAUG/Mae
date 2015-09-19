package org.mmaug.mae.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
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
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.models.Geo;
import org.mmaug.mae.models.GeoReturnObject;
import org.mmaug.mae.rest.RESTClient;
import org.mmaug.mae.utils.MixUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by yemyatthu on 9/18/15.
 */
public class LocationActivity extends BaseActivity{
  @Bind(R.id.location_name) TextView mLocationName;
  @Bind(R.id.month_day_left) TextView monthDayLef;

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
    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
        R.id.location_detail_map)).getMap();
    if (mMap != null) {
      mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(16.8000, 96.1500), 4));
    }
    Map<String,String> params = new HashMap<>();

    // TODO: 9/18/15 Insert Choosen DT_PCODE here
    params.put("dt_pcode", "MMR017D002");
    params.put("token","3db8827d-2521-57be-987a-e9e366874d4b");
    final Call<GeoReturnObject> geoCall = RESTClient.getMPSService().getLocationList(params);
    geoCall.enqueue(new Callback<GeoReturnObject>() {
      @Override public void onResponse(Response<GeoReturnObject> response) {
        Geo geo = response.body().getData().get(0);
        setUpMap(LocationActivity.this,geo);
      }

      @Override public void onFailure(Throwable t) {

      }
    });
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
      JsonArray jsonElements = geo.getGeometry().getCoordinates().getAsJsonArray();
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
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 8));

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
}
