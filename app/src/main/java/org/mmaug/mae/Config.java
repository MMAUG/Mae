package org.mmaug.mae;

/**
 * Created by poepoe on 12/9/15.
 */
public class Config {

  public static final String BASE_URL = "http://mae.mmaug.org/";
  public static final String MYANMARANGOUN = "MyanmarAngoun.ttf";
  public static final String PYIDAUNGSU = "pyidaungsu.ttf";

  public static final String API_V1 = "api/v1/";
  public static final String APP_VERSIONS = "app-versions";
  /**
   * Check Voter API
   **/

  public static final String REGISTER = API_V1 + "voter-check";

  //param
  public static final String VOTER_NAME = "name";
  public static final String DATE_OF_BIRTH = "dob";
  public static final String TOWNSHIP = "township";
  public static final String FATHER_NAME = "father_name";
  public static final String NRC = "nrc";

  /**
   * MaePaySoh API
   **/

  public static final String MPS_BASE_URL = API_V1 + "mps/";
  //param
  public static final String PAGE = "page";
  public static final String WITH = "_with";
  public static final String PER_PAGE = "per_page";
  public static final String FONT = "font";
  public static final String ZAWGYI = "zawgyi";
  public static final String UNICODE = "unicode";

  public static final String LEGISLATURE = "legislature";
  public static final String PYITHU_HLUTTAW = "ပြည်သူ့လွှတ်တော်";
  public static final String AMYOTHAE_HLUTTAW = "အမျိုးသားလွှတ်တော်";
  public static final String TINEDAYTHA_HLUTTAW = "တိုင်းဒေသကြီး/ပြည်နယ် လွှတ်တော်";

  public static final String CONSTITUENCY_ST_PCODE = "constituency_st_pcode";
  public static final String CONSTITUENCY_DT_PCODE = "constituency_dt_pcode";
  public static final String CONSTITUENCY_TS_PCODE = "constituency_ts_pcode";

  public static final String RELIGION = "religion";
  public static final String BUDDHISM = "buddhism";
  public static final String HINDUISM = "hinduism";
  public static final String ISLAM = "islam";
  public static final String CHRISTIANITY = "christianity";

  public static final String GENDER = "gender";
  public static final String FEMALE = "F";
  public static final String MALE = "M";

  //http://api.maepaysoh.org/party?token=user-token-key
  public static final String PARTY = "party";
  public static final String CANDIDATE = "candidate";
  public static final String CANDIDATE_COMPARE = "comparecandidate";

  public static final String FAQ = "faq";
  public static final String LIST = "list";

  public static final String OBJECT_ID = "object_id";
  public static final String ST_NAME = "st_name";
  public static final String DT_NAME = "dt_name";
  public static final String ST_PCODE = "st_pcode";
  public static final String TS_PCODE = "ts_pcode";
  public static final String DT_PCODE = "dt_pcode";
  public static final String LNG = "lon";
  public static final String LAT = "lat";

  //candidate endpoints
  public static final String CANDIDATE_URL = MPS_BASE_URL + CANDIDATE;
  public static final String CANDIDATE_LIST_URL = CANDIDATE_URL + "/" + LIST;
  public static final String CANDIDTE_AUTO_SEARCH = BASE_URL + API_V1 + "autocomplete/candidate";

  //party endpoints
  public static final String PARTY_LIST_URL = MPS_BASE_URL + PARTY;

  // faq endpoints
  public static final String FAQ_URL = MPS_BASE_URL + FAQ;
  public static final String FAQ_LIST_URL = FAQ_URL + "/" + LIST;
  public static final String FAQ_SEARCH = FAQ_URL + "/search";

  //geo location
  public static final String GEO_LOCATION_URL = MPS_BASE_URL + "geo/district";
  public static final String GEO_LOCATION_SEARCH = GEO_LOCATION_URL + "/find";
  public static final String MOTION_DETAIL_URL = API_V1 + "history/motions";
  public static final String MOTION_COUNT = API_V1 + "history/motion_count";
  public static final String QUESTION_DETAIL_URL = API_V1 + "history/questions";
  public static final String QUESTION_COUNT = API_V1 + "history/question_count";
  public static final String QUESTION_MOTION = API_V1 + "history/questions-and-motions";

  //candidatecompare
  public static final String COMPARE_QUESTION = API_V1 + "compare";

  //candidate count
  public static final String CANDIDATE_COUNT = MPS_BASE_URL + "candidate-count";

  //current count
  public static final String CURRENT_COUNT = API_V1 + "collection/current";

  //static final for party detail compare
  public static final String AMYOTHAR_REAL_COUNT = "amyothar_real_count";
  public static final String PYITHU_REAL_COUNT = "pyithu_real_count";
  public static final String TINE_DAYTHA_REAL_COUNT = "tinedaytha_real_count";

  public static final String AMYOTHAR_SEAT_COUNT = "amyothar_seat_count";
  public static final String PYITHU_SEAT_COUNT = "pyithu_seat_count";
  public static final String TINE_DAYTHA_SEAT_COUNT = "tinedaytha_seat_count";

  /**
   * GA event trackers
   */
  //categories
  public static final String CATEGORY_CANDIDATE = "candidate";
  public static final String ACTION_CANDIDATE = "candidate detail click";
  public static final String CATEGORY_PARTY = "party";
  public static final String ACTION_PARTY = "party detail click";
}
