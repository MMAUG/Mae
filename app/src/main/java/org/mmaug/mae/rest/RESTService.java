package org.mmaug.mae.rest;

import com.google.gson.JsonObject;
import java.util.Map;
import org.mmaug.mae.Config;
import org.mmaug.mae.models.User;
import org.mmaug.mae.models.Voter;
import retrofit.Call;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by poepoe on 12/9/15.
 */
public interface RESTService {

  /**
   * Check Voter Services
   **/

  @GET(Config.VOTER_CHECK) Call<Voter> searchVoter(@Query(Config.VOTER_NAME) String voterName,
      @QueryMap Map<String, String> optionalQueries);

  @FormUrlEncoded @POST(Config.REGISTER) Call<User> registerUser(
      @FieldMap Map<String, String> body);

  /**
   * Maepaysoh services
   **/

  //candidate
  @GET(Config.CANDIDATE_LIST_URL) Call<JsonObject> getCandidateList(
      @QueryMap Map<String, String> optionalQueries);

  @GET(Config.CANDIDATE_URL + "{/id}") Call<JsonObject> getCandidate(@Path("id") String id,
      @QueryMap Map<String, String> optionalQueries);

  //faq
  @GET(Config.FAQ_LIST_URL) Call<JsonObject> getFaqList(
      @QueryMap Map<String, String> optionalQueries);

  @GET(Config.FAQ_URL) Call<JsonObject> getFaq(@QueryMap Map<String, String> optionalQueries);

  @GET(Config.FAQ_SEARCH) Call<JsonObject> serchFaq(@QueryMap Map<String, String> optionalQueries);

  //geo location
  @GET(Config.GEO_LOCATION_URL) Call<JsonObject> getLocationList(
      @QueryMap Map<String, String> optionalQueries);

  @GET(Config.GEO_LOCATION_SEARCH) Call<JsonObject> searchLocation(
      @QueryMap Map<String, String> optionalQueries);

  //party
  @GET(Config.PARTY_LIST_URL) Call<JsonObject> getPartyList();

  @GET(Config.PARTY_URL + "{/id}") Call<JsonObject> getPartyDetail(@Path("id") String id);
}
