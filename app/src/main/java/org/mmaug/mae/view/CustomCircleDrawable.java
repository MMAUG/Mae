package org.mmaug.mae.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;

/**
 * Created by poepoe on 23/9/15.
 */
public class CustomCircleDrawable extends ShapeDrawable {
  private final Paint textPaint;
  private final int width;
  private final int height;
  private final String text;
  private final int fontSize;
  private final int color;

  private CustomCircleDrawable(Builder builder) {
    super(builder.shape);
    width = builder.width;
    height = builder.height;
    text = builder.text;
    fontSize = builder.fontSize;
    color = builder.color;

    //text style
    textPaint = new Paint();
    textPaint.setColor(builder.textColor);
    textPaint.setAntiAlias(true);
    textPaint.setStyle(Paint.Style.FILL);
    textPaint.setTypeface(builder.font);
    textPaint.setTextAlign(Align.CENTER);

    //background
    Paint paint = getPaint();
    paint.setColor(color);
  }

  @Override public int getIntrinsicWidth() {
    return width;
  }

  @Override public int getIntrinsicHeight() {
    return height;
  }

  @Override public void draw(Canvas canvas) {
    super.draw(canvas);

    Rect r = getBounds();

    int count = canvas.save();
    canvas.translate(r.left, r.top);

    // set default width and height
    int width = this.width < 0 ? r.width() : this.width;

    int height = this.height < 0 ? r.height() : this.height;

    // set default font size based on width and height
    int fontSize = this.fontSize < 0 ? (Math.min(width, height) / 2) : this.fontSize;
    textPaint.setTextSize(fontSize);

    // draw text
    canvas.drawText(text, width / 2, height / 2 - ((textPaint.descent() + textPaint.ascent()) / 2),
        textPaint);
    canvas.restoreToCount(count);
  }

  public static class Builder {
    private int width = -1;
    private int height = -1;
    private String text;
    private int textColor = Color.WHITE;
    private int fontSize = -1;
    private Typeface font = Typeface.create("sans-serif", Typeface.NORMAL);
    private int color;
    private RectShape shape = new OvalShape();

    public Builder(String text, int color) {
      this.text = text;
      this.color = color;
    }

    public void setFont(Typeface font) {
      this.font = font;
    }

    public void setFontSize(int fontSize) {
      this.fontSize = fontSize;
    }

    public void setHeight(int height) {
      this.height = height;
    }

    public void setWidth(int width) {
      this.width = width;
    }

    public void setTextColor(int textColor) {
      this.textColor = textColor;
    }

    public CustomCircleDrawable buildRect() {
      shape = new RectShape();
      return new CustomCircleDrawable(this);
    }

    public CustomCircleDrawable build() {
      return new CustomCircleDrawable(this);
    }
  }
}
