package org.mmaug.mae.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * TODO: document your custom view class.
 */
public class SquareImageView extends ImageView {

  public SquareImageView(Context context) {
    super(context);
  }

  public SquareImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    Drawable drawable = getDrawable();
    int imageWidth = drawable.getIntrinsicWidth();
    int imageHeight = drawable.getIntrinsicHeight();
    int width = getMeasuredWidth();
    setMeasuredDimension(width, width * imageHeight / imageWidth);
  }
}
