package org.mmaug.mae.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import org.mmaug.mae.R;
import org.mmaug.mae.utils.MixUtils;

/**
 * Created by poepoe on 24/9/15.
 */
public class BoardView extends View {

  Resources res;
  Canvas canvas;
  Paint background = new Paint();
  Paint gridPaint = new Paint();
  ArrayList<Rect> rects;
  int marginLeft, marginTop, padding;

  boolean touchMode;

  public BoardView(Context context, AttributeSet attrs) {
    super(context, attrs);
    res = getResources();
    marginLeft = (int) MixUtils.convertDpToPixel(context, 16);
    marginTop = (int) MixUtils.convertDpToPixel(context, 130);
    padding = (int) MixUtils.convertDpToPixel(context, 4);

  }

  public void enableTouch(boolean touchMode) {
    this.touchMode = touchMode;
    setFocusable(touchMode);
    setFocusableInTouchMode(touchMode);
  }

  //height of the row
  private int getBigCellHeight(int y) {
    return y / 3;
  }

  // first column width for candidate name
  private int getBigCellWith(int x) {
    int ratio = x / 4;
    return ratio * 2;
  }

  //small column for party flag and stamp
  private int getSmallCellWidth(int x) {
    int ratio = x / 4;
    return ratio;
  }

  //draw grid table
  private void drawBoard() {
    super.invalidate();

    //list of rectangles drawn on canvas, we will use their boundary to check the stamp is valid
    // or not
    rects = new ArrayList<>();

    //background rect paint
    background.setColor(res.getColor(R.color.board_background));
    canvas.drawRect(0, 0, getWidth(), getHeight(), background);

    //rect paint
    gridPaint.setColor(res.getColor(R.color.secondary_text_color));
    gridPaint.setStyle(Paint.Style.STROKE);
    gridPaint.setStrokeWidth(4);
    canvas.drawRect(0, 0, getWidth(), getHeight(), gridPaint);

    //set height and width of table
    int x = canvas.getWidth() - (marginLeft * 2);
    int y = getSmallCellWidth(x) * 3; // to get square for second & third cell

    //this is the top point of the rectangle and it will need to be recalculated
    //when rows are added
    int top = 0;
    for (int i = 0; i < 3; i++) {
      switch (i) {
        case 0:
          top = marginTop;
          break;
        case 1:
          //first row's height plus spacing between rows
          top = marginTop + getBigCellHeight(y) + padding;
          break;
        case 2:
          //first and second row's height plus spacing between rows
          top = marginTop + (getBigCellHeight(y) * 2) + (padding * 2);
          break;
      }
      addRow(top, x, y);
    }
  }

  @Override protected void onDraw(Canvas canvas) {
    this.canvas = canvas;
    drawBoard();
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    if (touchMode) {
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          enableTouch(false);
          int x = (int) event.getX();
          int y = (int) event.getY();
          for (Rect rect : rects) {
            if (rect.contains(x, y)) return true;
          }
          return true;
        default:
          return super.onTouchEvent(event);
      }
    }
    return super.onTouchEvent(event);
  }

  private void addRow(int topStart, int x, int y) {
    int left, right, top, bottom;
    top = topStart;
    bottom = top + getBigCellHeight(y);

    for (int j = 0; j < 3; j++) {
      if (j == 0) {
        left = marginLeft;
        right = left + getBigCellWith(x);
      } else if (j == 1) {
        left = marginLeft + getBigCellWith(x);
        right = left + getSmallCellWidth(x);
      } else {
        left = marginLeft + getBigCellWith(x) + getSmallCellWidth(x);
        right = left + getSmallCellWidth(x);
      }

      Rect rect = new Rect(left, top, right, bottom);
      if (j == 2) {
        rects.add(rect);
      }
      canvas.drawRect(rect, gridPaint);
    }
  }
}
