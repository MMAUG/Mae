package org.mmaug.mae.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by indexer on 9/20/15.
 */
public class ZoomAspectRatioImageView extends ImageView {

  public ZoomAspectRatioImageView(Context context) {
    super(context);
  }

  public ZoomAspectRatioImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ZoomAspectRatioImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (getDrawable() != null) {
      int width = MeasureSpec.getSize(widthMeasureSpec);
      int height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
      setMeasuredDimension(width * 2, height);
    } else {
      setMeasuredDimension(widthMeasureSpec * 2, heightMeasureSpec);
    }
  }
}
