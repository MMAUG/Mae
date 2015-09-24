package org.mmaug.mae.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by indexer on 9/20/15.
 */
public class AspectRatioImageView extends ImageView {

  public AspectRatioImageView(Context context) {
    super(context);
  }

  public AspectRatioImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AspectRatioImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = View.MeasureSpec.getSize(widthMeasureSpec);
    int height = width / 2;
    setMeasuredDimension(width, height);
  }
}
