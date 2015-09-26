package org.mmaug.mae.rest;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import static org.mmaug.mae.Config.BASE_URL;
import static org.mmaug.mae.Config.MPS_BASE_URL;

/**
 * Created by poepoe on 12/9/15.
 */
public class RESTClient {
  private static RESTClient instance;
  private RESTService mService;
  private RESTService mMPSService;
  private Context mContext;
  public RESTClient(Context context) {
    OkHttpClient client = new OkHttpClient();
    client.interceptors().add(new LoggingInterceptor());
    mContext = context;
    OkHttpClient mspClient = new OkHttpClient();
    mspClient.interceptors().add(new TokenInterceptor());
    final Retrofit restAdapter =
        new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).
            client(client).build();

    final Retrofit mpsRestAdapter = new Retrofit.Builder().baseUrl(MPS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(mspClient)
        .build();

    mService = restAdapter.create(RESTService.class);
    mMPSService = mpsRestAdapter.create(RESTService.class);
  }

  public static RESTClient getInstance(Context context) {
    if (instance == null) {
      instance = new RESTClient(context);
    }
    return instance;
  }

  public static synchronized RESTService getService(Context context) {
    return getInstance(context).mService;
  }

  public static synchronized RESTService getMPSService(Context context) {
    return getInstance(context).mMPSService;
  }

  private class LoggingInterceptor implements Interceptor {
    private final String TAG = LoggingInterceptor.class.getSimpleName();

    @Override public Response intercept(Chain chain) throws IOException {

      Request request = chain.request();

      Request.Builder builder = request.newBuilder();
      builder.header("X-API-KEY", "pRGKrLV8pKgReysQ27lORJsFbuJi4eAx");
      builder.header("X-API-SECRET", "r3pcXrYDvsTIRikBBG4SzdzAwgSsdIYU");
      builder.header("Accept", "application/json");
      request = builder.build();

      long t1 = System.nanoTime();
      Log.i(TAG, String.format("Sending request %s on %s%n%s", request.url(), chain.connection(),
          request.body()));

      Response response = chain.proceed(request);

      long t2 = System.nanoTime();
      Log.i(TAG, String.format("Received response for %s in %.1fms%n%s", response.request().url(),
          (t2 - t1) / 1e6d, response.body()));

      return response;
    }
  }

  private class TokenInterceptor implements Interceptor {
    private final String TAG = LoggingInterceptor.class.getSimpleName();

    @Override public Response intercept(Chain chain) throws IOException {
      TelephonyManager tManager = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
      String uuid = tManager.getDeviceId();
      Request request = chain.request();
      String url = request.urlString();
      Request.Builder builder = request.newBuilder();
      builder.url(url + "&uuid="+uuid);
      request = builder.build();

      long t1 = System.nanoTime();
      Log.i(TAG, String.format("Sending request %s on %s%n%s", request.url(), chain.connection(),
          request.headers()));

      Response response = chain.proceed(request);

      long t2 = System.nanoTime();
      Log.i(TAG, String.format("Received response for %s in %.1fms%n%s", response.request().url(),
          (t2 - t1) / 1e6d, response.headers()));

      return response;
    }
  }
}
