package org.mmaug.mae.rest;

import java.util.Map;
import org.mmaug.mae.models.Voter;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by poepoe on 12/9/15.
 */
public interface RESTService {

  @GET("api") Voter searchVoter(@Query("voter_name") String voterName,
      @QueryMap Map<String, String> optionalQueries);
}
