package org.mmaug.mae.rest;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import static org.mmaug.mae.Config.BASE_URL;

/**
 * Created by poepoe on 12/9/15.
 */
public class RESTClient {
  private static RESTClient instance;
  private RESTService mService;

  public RESTClient() {
    final Retrofit restAdapter =
        new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).
            build();
    mService = restAdapter.create(RESTService.class);
  }

  public static RESTClient getInstance() {
    if (instance == null) {
      instance = new RESTClient();
    }
    return instance;
  }

  public RESTService getService() {
    return mService;
  }
}
