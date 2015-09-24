package org.mmaug.mae.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by yemyatthu on 9/20/15.
 */
public class PotratitImageView extends ImageView {
  public PotratitImageView(Context context) {
    super(context);
  }

  public PotratitImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public PotratitImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = View.MeasureSpec.getSize(widthMeasureSpec);
    int height = (int) (width * 1.3);
    setMeasuredDimension(width, height);
  }
}
