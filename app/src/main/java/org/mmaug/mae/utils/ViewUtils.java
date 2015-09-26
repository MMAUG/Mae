package org.mmaug.mae.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public class ViewUtils {

  private Context mContext;

  public ViewUtils(Context context) {
    this.mContext = context;
  }

  /**
   * Helper to show/hide the view with an alpha animation for API 13 and above
   *
   * @param firstView first view to show/hide
   * @param secondView second view to show/hide
   * @param show flag to determine show/hide
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2) public void showProgress(final View firstView,
      final View secondView, final boolean show) {
    // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
    // for very easy animations. If available, use these APIs to fade-in
    // the progress spinner.

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      int shortAnimTime =
          mContext.getResources().getInteger(android.R.integer.config_shortAnimTime);

      firstView.setVisibility(show ? View.GONE : View.VISIBLE);
      firstView.animate()
          .setDuration(shortAnimTime)
          .alpha(show ? 0 : 1)
          .setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
              firstView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
          });

      secondView.setVisibility(show ? View.VISIBLE : View.GONE);
      secondView.animate()
          .setDuration(shortAnimTime)
          .alpha(show ? 1 : 0)
          .setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
              secondView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
          });
    } else {
      // The ViewPropertyAnimator APIs are not available, so simply show
      // and hide the relevant UI components.
      secondView.setVisibility(show ? View.VISIBLE : View.GONE);
      firstView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
  }
}
