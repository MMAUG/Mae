package org.mmaug.mae.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import org.mmaug.mae.Config;
import org.mmaug.mae.models.CandidateListReturnObject;
import org.mmaug.mae.models.GeoReturnObject;
import org.mmaug.mae.models.PartyReturnObject;
import org.mmaug.mae.models.User;
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

  //TODO to check this is work or not
  @FormUrlEncoded @POST(Config.REGISTER) Call<User> registerUser(
      @FieldMap Map<String, String> body);

  /**
   * Maepaysoh services
   **/

  //candidate
  @GET(Config.CANDIDATE_LIST_URL) Call<CandidateListReturnObject> getCandidateList(
      @QueryMap Map<String, String> optionalQueries);

  @GET(Config.CANDIDATE_URL + "{/id}") Call<JsonObject> getCandidate(@Path("id") String id,
      @QueryMap Map<String, String> optionalQueries);

  //faq
  @GET(Config.FAQ_LIST_URL) Call<JsonObject> getFaqList(
      @QueryMap Map<String, String> optionalQueries);

  @GET(Config.FAQ_URL) Call<JsonObject> getFaq(@QueryMap Map<String, String> optionalQueries);

  @GET(Config.FAQ_SEARCH) Call<JsonObject> serchFaq(@QueryMap Map<String, String> optionalQueries);

  //geo location
  @GET(Config.GEO_LOCATION_URL) Call<GeoReturnObject> getLocationList(
      @QueryMap Map<String, String> optionalQueries);

  @GET(Config.GEO_LOCATION_SEARCH) Call<JsonObject> searchLocation(
      @QueryMap Map<String, String> optionalQueries);

  //party
  @GET(Config.PARTY_LIST_URL) Call<PartyReturnObject> getPartyList(
      @QueryMap Map<String, String> optionalQueries);

  @GET(Config.PARTY_LIST_URL + "{/id}") Call<JsonObject> getPartyDetail(@Path("id") String id);

  //OMI service
  @GET(Config.MOTION_DETAIL_URL) Call<JsonObject> getMotionDetail(@Query("mpid") String mpId);

  @GET(Config.MOTION_COUNT) Call<JsonObject> getMotionCount(@Query("mpid") String mpId);

  @GET(Config.QUESTION_DETAIL_URL) Call<JsonObject> getQuestionDetail(@Query("mpid") String mpId);

  @GET(Config.QUESTION_COUNT) Call<JsonObject> getQuestionCount(@Query("mpid") String mpId);

  @GET(Config.COMPARE_QUESTION) Call<JsonElement> getCompareQuestion(
      @Query("first") String first_candidate_id, @Query("second") String second_candidate_id);

  @GET(Config.CANDIDATE_COUNT) Call<JsonObject> getCandidateCount(
      @Query("party") String party_id);

  @GET(Config.CURRENT_COUNT) Call<JsonObject> getCurrentCount();
}
