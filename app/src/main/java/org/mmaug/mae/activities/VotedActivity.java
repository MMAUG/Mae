package org.mmaug.mae.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.mmaug.mae.R;
import org.mmaug.mae.base.BaseActivity;
import org.mmaug.mae.utils.MMTextUtils;

public class VotedActivity extends BaseActivity {

  private static final String TAG = "CallCamera";
  private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;

  Uri fileUri = null;
  @Bind(R.id.image_frame) ImageView image_frame;
  @Bind(R.id.image) ImageView camera;
  @Bind(R.id.descriptionText) TextView descriptionText;
  @Bind(R.id.voter_check_ok_btn) TextView voteButton;
  @Bind(R.id.mae_title) TextView mae_title;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    camera.setVisibility(View.VISIBLE);
    mae_title.setVisibility(View.VISIBLE);

    if (isUnicode()) {
      mae_title.setTypeface(getTypefaceTitle());
      descriptionText.setTypeface(getTypefaceTitle());
      voteButton.setTypeface(getTypefaceLight());
    } else {
      MMTextUtils.getInstance(this).prepareMultipleViews(mae_title, descriptionText, voteButton);
    }
  }

  @OnClick(R.id.image) void Voted() {
    takeImage();
  }

  private File getOutputPhotoFile() {

    File directory =
        new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            getPackageName());

    if (!directory.exists()) {
      if (!directory.mkdirs()) {
        Log.e(TAG, "Failed to create storage directory.");
        return null;
      }
    }

    String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(new Date());

    return new File(directory.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
  }

  void takeImage() {
    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    fileUri = Uri.fromFile(getOutputPhotoFile());
    i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
    startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ);
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_voted;
  }

  @Override protected boolean getHomeUpEnabled() {
    return true;
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected String getToolbarText() {
    return null;
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
      if (resultCode == RESULT_OK) {
        Uri photoUri = null;
        if (data == null) {
          // A known bug here! The image should have saved in fileUri
          photoUri = fileUri;
        } else {
          photoUri = data.getData();
        }
        showPhoto(photoUri.getPath());
      } else if (resultCode == RESULT_CANCELED) {
      } else {
      }
    }
  }

  @OnClick(R.id.voter_check_ok_btn) void voted() {
    initShareIntent("image/jpeg", "Sample Text");
  }

  private void initShareIntent(String type, String _text) {
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_TEXT, _text);
    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(
        new File(fileUri.getPath())));  //optional//use this when you want to send an image
    shareIntent.setType("image/jpeg");
    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    startActivity(Intent.createChooser(shareIntent, "send"));
  }

  private void showPhoto(String photoUri) {
    File imageFile = new File(photoUri);
    if (imageFile.exists()) {
      Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
      BitmapDrawable drawable = new BitmapDrawable(this.getResources(), bitmap);
      image_frame.setScaleType(ImageView.ScaleType.CENTER_CROP);
      image_frame.setImageDrawable(drawable);
      image_frame.setVisibility(View.VISIBLE);
      descriptionText.setVisibility(View.VISIBLE);
      mae_title.setVisibility(View.GONE);
      voteButton.setVisibility(View.VISIBLE);
      camera.setVisibility(View.GONE);
    }
  }
}
