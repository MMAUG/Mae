package org.mmaug.mae.base;

import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import org.mmaug.mae.R;

/**
 * Created by indexer on 9/13/15.
 */
public abstract class BaseActivity extends AppCompatActivity
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
  //For Google API
  protected GoogleApiClient mGoogleApiClient;
  protected Location mLastLocation;

  public Toolbar toolbar;
  public TextView tvToolbarTitle;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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

  protected abstract int getLayoutResource();

  protected abstract boolean getHomeUpEnabled();

  protected abstract boolean needToolbar();

  protected abstract String getToolbarText();
}
