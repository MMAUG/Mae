package org.mmaug.mae;

/**
 * Created by poepoe on 12/9/15.
 */
public class Config {

  public static final String BASE_URL = "http://188.166.240.34/api/v1/";

  /**
   * Check Voter API
   **/

  //url for voter check
  public static final String VOTER_CHECK = "voter-check";
  public static final String REGISTER = "register";

  //param
  public static final String VOTER_NAME = "name";
  public static final String DATE_OF_BIRTH = "dob";
  public static final String TOWNSHIP = "township";
  public static final String FATHER_NAME = "father_name";
  public static final String NRC = "nrc";

  /**
   * MaePaySoh API
   **/

  //param
  public static final String TOKEN = "token";
  public static final String WITH = "_with";
  public static final String PER_PAGE = "per_page";
  public static final String FONT = "font";
  public static final String ZAWGYI = "zawgyi";
  public static final String UNICODE = "unicode";

  public static final String LEGISLATURE = "legislature";
  public static final String PYITHU_HLUTTAW = "ပ\u103Cည\u103Aသူ့လ\u103D\u103Eတ\u103Aတော\u103A ";
  public static final String AMYOTHAE_HLUTTAW = "အမ\u103Bိုးသားလ\u103D\u103Eတ\u103Aတော\u103A";

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

  public static final String PARTY = "party";
  public static final String CANDIDATE = "candidate";

  public static final String FAQ = "faq";
  public static final String LIST = "list";

  public static final String OBJECT_ID = "object_id";
  public static final String ST_NAME = "st_name";
  public static final String DT_NAME = "dt_name";
  public static final String DT_PCODE = "dt_pcode";
  public static final String LNG = "lon";
  public static final String LAT = "lat";

  //candidate endpoints
  public static final String CANDIDATE_URL = CANDIDATE;
  public static final String CANDIDATE_LIST_URL = CANDIDATE_URL + "/" + LIST;

  //party endpoints
  public static final String PARTY_LIST_URL = PARTY;
  public static final String PARTY_URL = PARTY_LIST_URL + "/detail";

  // faq endpoints
  public static final String FAQ_URL = FAQ;
  public static final String FAQ_LIST_URL = FAQ_URL + "/" + LIST;
  public static final String FAQ_SEARCH = FAQ_URL + "/search";

  //geo location
  public static final String GEO_LOCATION_URL = "geo/district";
  public static final String GEO_LOCATION_SEARCH = GEO_LOCATION_URL + "/find";
}
