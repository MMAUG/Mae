package org.mmaug.mae.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.io.File;
import org.mmaug.mae.R;

public class VotedActivity extends AppCompatActivity {
  @Bind(R.id.image) ImageView imageView;
  @Bind(R.id.image_frame) ImageView imageFrame;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_voted);
    ButterKnife.bind(this);
    imageView.setVisibility(View.VISIBLE);
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 0 && resultCode == RESULT_OK) {
      Bitmap photo = (Bitmap) data.getExtras().get("data");
      imageFrame.setVisibility(View.VISIBLE);
      imageFrame.setImageBitmap(photo);
    }
  }

  @OnClick(R.id.image) void Voted() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
      takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
          Uri.fromFile(new File("sdcard/captured")));
      startActivityForResult(takePictureIntent, 0);
    }
  }
}
