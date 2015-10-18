package org.mmaug.mae.utils;

import retrofit.Callback;

/**
 * Created by poepoe on 2/10/15.
 */
public abstract class RestCallback<T> implements Callback<T> {

  /**
   * Invoked when a network or unexpected exception occurred during the HTTP request.
   */
  @Override public void onFailure(Throwable t) {
    t.printStackTrace();
  }
}
