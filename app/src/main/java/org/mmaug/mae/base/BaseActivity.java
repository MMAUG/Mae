package org.mmaug.mae.base;

import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import org.mmaug.mae.Config;
import org.mmaug.mae.R;
import org.mmaug.mae.utils.FontCache;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.UserPrefUtils;

/**
 * Created by indexer on 9/13/15.
 */
public abstract class BaseActivity extends AppCompatActivity
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
  private Toolbar toolbar;
  private TextView tvToolbarTitle;
  //For Google API
  protected GoogleApiClient mGoogleApiClient;
  protected Location mLastLocation;
  private Typeface typefaceTitle;
  private Typeface typefaceLight;
  private boolean isUnicode;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //typeface
    typefaceTitle = FontCache.getTypefaceTitle(this);
    typefaceLight = FontCache.getTypefaceLight(this);
    isUnicode = UserPrefUtils.getInstance(this).getTextPref().equals(Config.UNICODE);

    //get layout
    setContentView(getLayoutResource());

    if (needToolbar()) { //flag to check activity needs toolbar or not
      toolbar = (Toolbar) findViewById(R.id.toolbar);
      if (toolbar != null) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null && getHomeUpEnabled()) {
          getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (getToolbarText() != null && getSupportActionBar() != null) {
          getSupportActionBar().setTitle("");
          tvToolbarTitle = (TextView) findViewById(R.id.tv_toolbar_title);
          tvToolbarTitle.setText(getToolbarText());
          if (isUnicode) {
            tvToolbarTitle.setTypeface(typefaceTitle);
          } else {
            MMTextUtils.getInstance(this).prepareSingleView(tvToolbarTitle);
          }

        }
      }
    }
  }

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
  }

  @Override public void onConnected(Bundle bundle) {
    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
  }

  @Override public void onConnectionSuspended(int i) {

  }

  @Override public void onConnectionFailed(ConnectionResult connectionResult) {

  }

  /**
   * @param title set toolbar title dynamically from activity
   */
  protected void setToolbarTitle(String title) {
    tvToolbarTitle.setText(title);
    if (isUnicode) {
      tvToolbarTitle.setTypeface(typefaceTitle);
    } else {
      MMTextUtils.getInstance(this).prepareSingleView(tvToolbarTitle);
    }
  }

  public Typeface getTypefaceLight() {
    return typefaceLight;
  }

  public Typeface getTypefaceTitle() {
    return typefaceTitle;
  }

  public boolean isUnicode() {
    return isUnicode;
  }

  protected abstract int getLayoutResource();

  protected abstract boolean getHomeUpEnabled();

  protected abstract boolean needToolbar();

  protected abstract String getToolbarText();
}
