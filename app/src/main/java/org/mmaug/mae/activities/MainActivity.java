package org.mmaug.mae.activities;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.gson.JsonObject;
import org.mmaug.mae.BuildConfig;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.fragments.SignUpFragment;
import org.mmaug.mae.rest.RESTClient;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class MainActivity extends BaseActivity {

  String currentFrag;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    checkUpdate();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_main;
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

  @Override public void onBackPressed() {
    SignUpFragment signUp =
        (SignUpFragment) getSupportFragmentManager().findFragmentByTag("SignUp");
    if (signUp.isAdded()) {
      boolean intercepted = signUp.interceptOnBackPressed();
      if (!intercepted) {
        super.onBackPressed();
      }
    }else {
      super.onBackPressed();
    }
  }

  private void checkUpdate() {

    final Call<JsonObject> checkUpdate = RESTClient.getService(this).checkUpdate();
    checkUpdate.enqueue(new Callback<JsonObject>() {
      @Override public void onResponse(Response<JsonObject> response) {
        if (response.code() == 200) {
          final JsonObject object = response.body();
          if (object.get("v").getAsInt() > BuildConfig.VERSION_CODE) {
            new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle).
                setTitle("Update Available!")
                .setMessage("Please download latest version to get updated features.")
                .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                  @Override public void onClick(DialogInterface dialog, int which) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(
                        Uri.parse(object.get("direct_download_url").getAsString()));
                    try {
                      startActivity(browserIntent);
                    } catch (ActivityNotFoundException e) {
                      Toast.makeText(MainActivity.this, "Install a browser!", Toast.LENGTH_LONG)
                          .show();
                    }
                    dialog.dismiss();
                  }
                })
                .show();
          }
        }
      }

      @Override public void onFailure(Throwable t) {

      }
    });
  }
}
