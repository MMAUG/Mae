package org.mmaug.mae.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

public class CircleView extends View {

  private int color = Color.parseColor("#dfdfdf");
  private final Paint drawPaint;
  private float size;

  public void setColorHex(int color) {
    this.color = color;
    drawPaint.setColor(this.color);
    drawPaint.setAntiAlias(true);
    invalidate();
  }

  public CircleView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    drawPaint = new Paint();
    drawPaint.setColor(color);
    drawPaint.setAntiAlias(true);
    setOnMeasureCallback();
  }

  @Override protected void onDraw(final Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawCircle(size, size, size, drawPaint);
  }

  private void setOnMeasureCallback() {
    ViewTreeObserver vto = getViewTreeObserver();
    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override public void onGlobalLayout() {
        removeOnGlobalLayoutListener(this);
        size = getMeasuredWidth() / 2;
      }
    });
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  private void removeOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener) {
    if (Build.VERSION.SDK_INT < 16) {
      getViewTreeObserver().removeGlobalOnLayoutListener(listener);
    } else {
      getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }
  }
}