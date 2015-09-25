package org.mmaug.mae.utils;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by indexer on 9/25/15.
 */
public class NestedScrollHelper implements View.OnTouchListener {

  @Override public boolean onTouch(View v, MotionEvent event) {

    // get the event action
    int action = event.getAction();
    // For each of the action DOWN/UP/MOVE
    // disallow touch intercept to parent
    switch (action) {
      case MotionEvent.ACTION_DOWN:
          Log.e("In The Event", "Event");
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_MOVE:
        // Disallow ScrollView to intercept touch events.
        v.getParent().requestDisallowInterceptTouchEvent(true);
    }

    // Handle childview’s touch events.
    v.onTouchEvent(event);
    return true;
  }
}
