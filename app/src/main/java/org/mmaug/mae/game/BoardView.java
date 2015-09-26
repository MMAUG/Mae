package org.mmaug.mae.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import org.mmaug.mae.R;
import org.mmaug.mae.utils.MixUtils;

/**
 * Created by poepoe on 24/9/15.
 */
public class BoardView extends View {

  private Context mContext;
  private Resources res;
  private Canvas canvas;
  private Paint background = new Paint();
  private Paint gridPaint = new Paint();
  private TextPaint textPaint = new TextPaint();
  private Paint partyFlagPaint = new Paint();
  private Bitmap stamp;
  private int stampX, stampY;
  private boolean alredyDrawn = false;

  private ArrayList<Rect> rects; //List of rectangles where the touch of the user needs to be
  // checked
  private int margin;
  private int padding;
  private int marginSmall; //boundaries of table
  private boolean touchMode; //control touch mode by button
  private GameListener listener;
  private String[] candidateName;
  private int[] color = new int[3];

  int normalTextSize;

  public BoardView(Context context, AttributeSet attrs) {
    super(context, attrs);

    mContext = context;
    res = getResources();

    candidateName = res.getStringArray(R.array.candidate_name);
    color[0] = res.getColor(R.color.red);
    color[1] = res.getColor(R.color.accent_color);
    color[2] = res.getColor(R.color.geojson_background_color);

    margin = (int) MixUtils.convertDpToPixel(context, 16);
    marginSmall = (int) MixUtils.convertDpToPixel(context, 8);
    padding = (int) MixUtils.convertDpToPixel(context, 4);

    normalTextSize = (int) MixUtils.convertDpToPixel(context, 10);
    //background rect paint
    background.setColor(res.getColor(R.color.board_background));

    //cell rect paint
    gridPaint.setColor(res.getColor(R.color.grey));
    gridPaint.setStyle(Paint.Style.STROKE);
    gridPaint.setStrokeWidth(4);

    //text paint
    textPaint.setColor(res.getColor(R.color.grey));
    textPaint.setAntiAlias(true);
    textPaint.setStyle(Paint.Style.FILL);

    //flag paint
    partyFlagPaint.setAntiAlias(true);
    partyFlagPaint.setStyle(Paint.Style.FILL);
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
    //list of rectangles drawn on canvas, we will use their boundary to check the stamp is valid
    // or not
    rects = new ArrayList<>();
    //align left for the top text
    textPaint.setTextAlign(Paint.Align.LEFT);
    //draw bg
    canvas.drawRect(0, 0, getWidth(), getHeight(), background);
    //draw boundary
    canvas.drawRect(0, 0, getWidth(), getHeight(), gridPaint);

    //set height and width of table
    int x = canvas.getWidth() - (margin * 2);
    int y = getSmallCellWidth(x) * 3;//square cell

    int textAreaHeight = y + (margin * 3); // table area and margin are subtracted from canvas
    // height to write text

    int marginTop = getHeight() - textAreaHeight; // Y coordinate point where table will be drawn

    int titleHeight = marginTop / 4;
    int paraHeight = 3 * (marginTop / 4);
    int paraMargin;

    if (stamp == null) stamp = getStamp(getSmallCellWidth(x), getBigCellHeight(y));

    int signatureTextSize;
    int smallTextSize, titleTextSize;
    if (paraHeight < 130) {
      smallTextSize = 8;
      titleTextSize = 11;
      titleHeight = titleHeight + padding;
      paraMargin = titleHeight + marginSmall + padding;
      signatureTextSize = marginSmall;
    } else if (paraHeight < 350) {
      smallTextSize = 10;
      signatureTextSize = normalTextSize;
      titleTextSize = 12;
      paraMargin = titleHeight + marginSmall;
    } else {
      smallTextSize = 12;
      signatureTextSize = marginSmall + padding;
      titleTextSize = 16;
      paraMargin = titleHeight + marginSmall;
    }

    String title = res.getString(R.string.example_state_legislature);
    drawTextOnCanvas(title, marginSmall, titleTextSize, x, titleHeight);

    char c = '\u2713';
    String votingMessage = res.getString(R.string.example_voting_step_1, c, c);
    drawTextOnCanvas(votingMessage, paraMargin, smallTextSize, canvas.getWidth() - margin,
        paraHeight);

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
      addRow(top, x, y, i);
    }

    //align left for the signature text
    textPaint.setTextSize(signatureTextSize);
    textPaint.setTextAlign(Paint.Align.RIGHT);
    int signatureTop = top + getBigCellHeight(y) + margin + padding;
    canvas.drawText(res.getString(R.string.signature_line_1), canvas.getWidth() - margin,
        signatureTop, textPaint);
    canvas.drawText(res.getString(R.string.signature_line_2), canvas.getWidth() - margin,
        signatureTop + margin, textPaint);

    if (alredyDrawn) {
      drawStamp(stampX - (stamp.getWidth() / 2), stampY - (stamp.getHeight() / 2));
    }
  }

  public void reset() {
    alredyDrawn = false;
    invalidate();
  }
  @Override protected void onDraw(Canvas canvas) {
    this.canvas = canvas;
    drawBoard();
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    if (touchMode) {
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          alredyDrawn = true;
          enableTouch(false);
          stampX = (int) event.getX();
          stampY = (int) event.getY();
          if (checkWithinBounds(stampX, stampY)) {
            listener.checkValidity(ValidityStatus.valid);
          } else {
            listener.checkValidity(ValidityStatus.invalid);
          }
          invalidate();
          return true;
        default:
          return super.onTouchEvent(event);
      }
    }
    return super.onTouchEvent(event);
  }

  private void addRow(int topStart, int x, int y, int i) {
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
      if (j == 1) {
        partyFlagPaint.setColor(color[i]);
        canvas.drawRect(left + marginSmall, top + margin, right - marginSmall, bottom - margin,
            partyFlagPaint);
      }
      if (j == 0) {
        canvas.drawText(candidateName[i], (rect.width() / 2) - (candidateName[i].length() / 2),
            top + rect.height() / 2, textPaint);
      }
    }
  }

  private boolean checkWithinBounds(int x, int y) {
    for (Rect rect : rects) {
      if (rect.left < (x - (stamp.getWidth() / 2))
          && rect.top < (y - (stamp.getHeight() / 2))
          && rect.right > (x + (stamp.getWidth() / 2))
          && rect.bottom > (y + (stamp.getHeight() / 2))) {
        return true;
      }
    }
    return false;
  }

  public enum ValidityStatus {
    valid, invalid
  }

  public interface GameListener {
    void checkValidity(ValidityStatus status);
  }

  private Bitmap getStamp(int width, int height) {
    Drawable drawable = res.getDrawable(R.drawable.ic_stamp);
    assert drawable != null;
    Bitmap b = ((BitmapDrawable) drawable).getBitmap();
    return Bitmap.createScaledBitmap(b, width - margin, height - margin, false);
  }

  private void drawStamp(int x, int y) {
    canvas.drawBitmap(stamp, x, y, null);
  }

  private void drawTextOnCanvas(String text, int y, int textSize, int width, int height) {
    TextView tv = new TextView(mContext);
    // setup text
    tv.setText(text);
    tv.setTextColor(res.getColor(R.color.grey));
    tv.setTextSize(textSize);
    tv.setSingleLine(false);
    tv.setLineSpacing(0.0f, 1.2f);
    tv.setDrawingCacheEnabled(true);

    // we need to setup how big the view should be..which is exactly as big as the canvas
    tv.measure(MeasureSpec.makeMeasureSpec(width + margin, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    // assign the layout values to the textview
    tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
    // draw the bitmap from the drawingcache to the canvas

    canvas.drawBitmap(tv.getDrawingCache(), margin, y, background);
    // disable drawing cache
    tv.setDrawingCacheEnabled(false);
  }
}
