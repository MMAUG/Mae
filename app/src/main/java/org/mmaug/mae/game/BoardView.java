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

/**
 * Created by poepoe on 24/9/15.
 */
public class BoardView extends View {

  boolean reset;
  Resources res;
  Canvas canvas;
  Paint background = new Paint();
  Paint gridPaint = new Paint();
  ArrayList<Rect> rects;

  public BoardView(Context context, AttributeSet attrs) {
    super(context, attrs);
    res = getResources();
    reset = true;
    setFocusable(true);
    setFocusableInTouchMode(true);
  }

  //spacing between rows
  private int getSmallCellHeight(int y) {
    int ratio = y / 30;
    return ratio / 2;
  }

  //height of the row
  private int getBigCellHeight(int y) {
    int ratio = y / 30;
    int cellHeight = y - ratio;
    return cellHeight / 3;
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

    //get height and widht of the canvas
    int x = canvas.getWidth();
    int y = canvas.getHeight();

    //this is the top point of the rectangle and it will need to be recalculated
    //when rows are added
    int top = 0;

    for (int i = 0; i < 3; i++) {
      switch (i) {
        case 0:
          top = 0;
          break;
        case 1:
          //first row's height plus spacing between rows
          top = getBigCellHeight(y) + getSmallCellHeight(y);
          break;
        case 2:
          //first and second row's height plus spacing between rows
          top = (getBigCellHeight(y) * 2) + (getSmallCellHeight(y) * 2);
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
    return super.onTouchEvent(event);
  }

  private void addRow(int topStart, int x, int y) {
    int left, right, top, bottom;
    top = topStart;
    bottom = top + getBigCellHeight(y);

    for (int j = 0; j < 3; j++) {
      if (j == 0) {
        left = 0;
        right = getBigCellWith(x);
      } else if (j == 1) {
        left = getBigCellWith(x);
        right = left + getSmallCellWidth(x);
      } else {
        left = getBigCellWith(x) + getSmallCellWidth(x);
        right = left + getSmallCellWidth(x);
      }

      Rect rect = new Rect(left, top, right, bottom);
      rects.add(rect);
      canvas.drawRect(rect, gridPaint);
    }
  }
}
