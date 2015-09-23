package org.mmaug.mae.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
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

  public BoardView(Context context, AttributeSet attrs) {
    super(context, attrs);
    res = getResources();
    reset = true;
    setFocusable(true);
    setFocusableInTouchMode(true);
  }

  private int getSmallCellHeight(int y) {
    int ratio = y / 30;
    return ratio / 2;
  }

  private int getBigCellHeight(int y) {
    int ratio = y / 30;
    int cellHeight = y - ratio;
    return cellHeight / 3;
  }

  private int getBigCellWith(int x) {
    return x / 2;
  }

  private int getSmallCellWidth(int x) {
    return x / 4;
  }

  private void drawBoard() {
    super.invalidate();

    //background canvas
    background.setColor(res.getColor(R.color.board_background));
    canvas.drawRect(0, 0, getWidth(), getHeight(), background);

    gridPaint.setColor(res.getColor(R.color.secondary_text_color));
    gridPaint.setStyle(Paint.Style.STROKE);
    gridPaint.setStrokeWidth(2);

    int x = canvas.getWidth();
    int y = canvas.getHeight();
    // 5 row : 3 column row > 1 column row > 3 column row > 1 column row > 3 column row
    int height;
    for (int i = 0; i < 5; i++) {
      switch (i) {
        case 0:
          height = 0;
          addFirstRow(height, x, y);
        case 1:
          height = getBigCellHeight(y);
          addSecondRow(height, x, y);
        case 2:
          height = getBigCellHeight(y) + getSmallCellHeight(y);
          addFirstRow(height, x, y);
        case 3:
          height = (getBigCellHeight(y) * 2) + getSmallCellHeight(y);
          addSecondRow(height, x, y);
        case 4:
          height = (getBigCellHeight(y) * 2) + (getSmallCellHeight(y) * 2);
          addFirstRow(height, x, y);
      }
    }
  }

  @Override protected void onDraw(Canvas canvas) {
    this.canvas = canvas;
    drawBoard();
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    return super.onTouchEvent(event);
  }

  private void addFirstRow(int height, int x, int y) {
    for (int j = 0; j < 3; j++) {
      int left, right, top, bottom;
      top = height;
      bottom = height + getBigCellHeight(y);
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
      canvas.drawRect(left, top, right, bottom, gridPaint);
    }
  }

  private void addSecondRow(int height, int x, int y) {
    int top = height;
    int bottom = top + getSmallCellHeight(y);
    canvas.drawRect(0, top, x, bottom, gridPaint);
  }
}
