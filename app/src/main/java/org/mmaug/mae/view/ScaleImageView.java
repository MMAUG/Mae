package org.mmaug.mae.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by indexer on 9/20/15.
 */
public class ScaleImageView extends ImageView {
  private final float[] mMatrixValues = new float[9];
  String TAG = "ScaleImageView";
  private Context mContext;
  private float MAX_SCALE = 2f;
  private Matrix mMatrix;
  // display width height.
  private int mWidth;
  private int mHeight;
  private int mIntrinsicWidth;
  private int mIntrinsicHeight;
  private float mScale;
  private float mMinScale;
  private float mPrevDistance;
  private boolean isScaling;
  private int mPrevMoveX;
  private int mPrevMoveY;

  public ScaleImageView(Context context, AttributeSet attr) {
    super(context, attr);
    this.mContext = context;
    initialize();
  }

  public ScaleImageView(Context context) {
    super(context);
    this.mContext = context;
    initialize();
  }

  @Override public void setImageBitmap(Bitmap bm) {
    super.setImageBitmap(bm);
    this.initialize();
  }

  @Override public void setImageResource(int resId) {
    super.setImageResource(resId);
    this.initialize();
  }

  private void initialize() {
    this.setScaleType(ScaleType.MATRIX);
    this.mMatrix = new Matrix();
    Drawable d = getDrawable();
    if (d != null) {
      mIntrinsicWidth = d.getIntrinsicWidth();
      mIntrinsicHeight = d.getIntrinsicHeight();
      cutting();
    }
  }

  @Override protected boolean setFrame(int l, int t, int r, int b) {
    mWidth = r - l;
    mHeight = b - t;

    mMatrix.reset();
    int r_norm = r - l;
    mScale = (float) r_norm / (float) mIntrinsicWidth;

    int paddingHeight = 0;
    int paddingWidth = 0;
    // scaling vertical
    if (mScale * mIntrinsicHeight > mHeight) {
      mScale = (float) mHeight / (float) mIntrinsicHeight;
      mMatrix.postScale(mScale, mScale);
      paddingWidth = (r - mWidth) / 2;
      paddingHeight = 0;
      // scaling horizontal
    } else {
      mMatrix.postScale(mScale, mScale);
      paddingHeight = (b - mHeight) / 2;
      paddingWidth = 0;
    }
    mMatrix.postTranslate(paddingWidth, paddingHeight);

    setImageMatrix(mMatrix);
    mMinScale = mScale;
    zoomTo(mScale, mWidth / 2, mHeight / 2);
    cutting();
    return super.setFrame(l, t, r, b);
  }

  protected float getValue(Matrix matrix, int whichValue) {
    matrix.getValues(mMatrixValues);
    return mMatrixValues[whichValue];
  }

  protected float getScale() {
    return getValue(mMatrix, Matrix.MSCALE_X);
  }

  public float getTranslateX() {
    return getValue(mMatrix, Matrix.MTRANS_X);
  }

  protected float getTranslateY() {
    return getValue(mMatrix, Matrix.MTRANS_Y);
  }

  protected void maxZoomTo(int x, int y) {
    if (mMinScale != getScale() && (getScale() - mMinScale) > 0.1f) {
      // threshold 0.1f
      float scale = mMinScale / getScale();
      zoomTo(scale, x, y);
    } else {
      float scale = MAX_SCALE / getScale();
      zoomTo(scale, x, y);
    }
  }

  public void zoomTo(float scale, int x, int y) {
    if (getScale() * scale < mMinScale) {
      return;
    }
    if (scale >= 1 && getScale() * scale > MAX_SCALE) {
      return;
    }
    mMatrix.postScale(scale, scale);
    // move to center
    mMatrix.postTranslate(-(mWidth * scale - mWidth) / 2, -(mHeight * scale - mHeight) / 2);

    // move x and y distance
    mMatrix.postTranslate(-(x - (mWidth / 2)) * scale, 0);
    mMatrix.postTranslate(0, -(y - (mHeight / 2)) * scale);
    setImageMatrix(mMatrix);
  }

  public void cutting() {
    int width = (int) (mIntrinsicWidth * getScale());
    int height = (int) (mIntrinsicHeight * getScale());
    if (getTranslateX() < -(width - mWidth)) {
      mMatrix.postTranslate(-(getTranslateX() + width - mWidth), 0);
    }
    if (getTranslateX() > 0) {
      mMatrix.postTranslate(-getTranslateX(), 0);
    }
    if (getTranslateY() < -(height - mHeight)) {
      mMatrix.postTranslate(0, -(getTranslateY() + height - mHeight));
    }
    if (getTranslateY() > 0) {
      mMatrix.postTranslate(0, -getTranslateY());
    }
    if (width < mWidth) {
      mMatrix.postTranslate((mWidth - width) / 2, 0);
    }
    if (height < mHeight) {
      mMatrix.postTranslate(0, (mHeight - height) / 2);
    }
    setImageMatrix(mMatrix);
  }
}
