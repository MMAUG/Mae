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

  private Resources res;
  private Canvas canvas;
  private Paint background = new Paint();
  private Paint gridPaint = new Paint();
  private Paint textPaint = new Paint();
  private ArrayList<Rect> rects; //List of rectangles where the touch of the user needs to be
  // checked
  private int margin;
  private int padding;
  private int marginSmall; //boundaries of table
  private boolean touchMode; //control touch mode by button
  private GameListener listener;

  private int titleTextSize, normalTextSize;

  public BoardView(Context context, AttributeSet attrs) {
    super(context, attrs);
    res = getResources();

    margin = (int) MixUtils.convertDpToPixel(context, 16);
    marginSmall = (int) MixUtils.convertDpToPixel(context, 12);
    padding = (int) MixUtils.convertDpToPixel(context, 4);

    titleTextSize = (int) MixUtils.convertDpToPixel(context, 14);
    normalTextSize = (int) MixUtils.convertDpToPixel(context, 10);
    //background rect paint
    background.setColor(res.getColor(R.color.board_background));

    //cell rect paint
    gridPaint.setColor(res.getColor(R.color.secondary_text_color));
    gridPaint.setStyle(Paint.Style.STROKE);
    gridPaint.setStrokeWidth(4);

    //text paint
    textPaint.setColor(res.getColor(R.color.secondary_text_color));
    textPaint.setAntiAlias(true);
    textPaint.setStyle(Paint.Style.FILL);
  }

  public void enableTouch(boolean touchMode) {
    this.touchMode = touchMode;
    setFocusable(touchMode);
    setFocusableInTouchMode(touchMode);
  }

  public void setGameListener(GameListener listener) {
    this.listener = listener;
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
    return x / 4;
  }

  //draw grid table
  private void drawBoard() {
    super.invalidate();

    //list of rectangles drawn on canvas, we will use their boundary to check the stamp is valid
    // or not
    rects = new ArrayList<>();

    //align left for the top text
    textPaint.setTextAlign(Paint.Align.LEFT);
    //draw bg
    canvas.drawRect(0, 0, getWidth(), getHeight(), background);
    //draw boundary
    canvas.drawRect(0, 0, getWidth(), getHeight(), gridPaint);

    //draw text
    textPaint.setTextSize(titleTextSize);
    canvas.drawText(res.getString(R.string.example_state_legislature), margin, marginSmall * 2,
        textPaint);
    canvas.drawText(res.getString(R.string.example_township), margin, marginSmall * 4, textPaint);
    textPaint.setTextSize(normalTextSize);
    canvas.drawText(res.getString(R.string.example_voting_step_1), margin, marginSmall * 6,
        textPaint);
    canvas.drawText(res.getString(R.string.example_voting_step_2), margin, marginSmall * 8,
        textPaint);
    canvas.drawText(res.getString(R.string.example_voting_step_3), margin, marginSmall * 10,
        textPaint);
    int marginTop = marginSmall * 11;

    //set height and width of table
    int x = canvas.getWidth() - (margin * 2);
    int y = canvas.getHeight() - (marginTop + (margin * 4));

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

    //align left for the signature text
    textPaint.setTextAlign(Paint.Align.RIGHT);
    int signatureTop = marginTop + (getBigCellHeight(y) * 3) + (padding * 2) + (marginSmall * 2);
    canvas.drawText(res.getString(R.string.signature_line_1), canvas.getWidth() - margin,
        signatureTop, textPaint);
    canvas.drawText(res.getString(R.string.signature_line_2), canvas.getWidth() - margin,
        signatureTop + marginSmall * 2, textPaint);
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
          if (checkWithinBounds(x, y)) {
            listener.checkValidity(ValidityStatus.valid);
          } else {
            listener.checkValidity(ValidityStatus.invalid);
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
        left = margin;
        right = left + getBigCellWith(x);
      } else if (j == 1) {
        left = margin + getBigCellWith(x);
        right = left + getSmallCellWidth(x);
      } else {
        left = margin + getBigCellWith(x) + getSmallCellWidth(x);
        right = left + getSmallCellWidth(x);
      }

      Rect rect = new Rect(left, top, right, bottom);
      if (j == 2) {
        rects.add(rect);
      }
      canvas.drawRect(rect, gridPaint);
    }
  }

  private boolean checkWithinBounds(int x, int y) {
    for (Rect rect : rects) {
      if (rect.contains(x, y)) return true;
    }
    return false;
  }

  public enum ValidityStatus {
    valid, invalid
  }

  public interface GameListener {
    void checkValidity(ValidityStatus status);
  }

}
