package org.mmaug.mae.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by yemyatthu on 9/26/15.
 */
public class UserPrefUtils {
  private static final String USER_NAME = "username";
  private static final String FATHER_NAME = "fathername";
  private static final String BIRTH_DATE = "birthdate";
  private static final String NRC = "nrc";
  private static final String TOWNSHIP = "township";
  private static final String WARD = "ward";
  private static final String IS_VALID = "valid";
  private static final String SKIP_VALID = "skip";
  private static final String TEXT_PREF = "font";
  private static final String FIRST_TIME = "first time";

  private SharedPreferences mSharedPreferences;
  private static UserPrefUtils userPrefUtils;

  public UserPrefUtils(Context context) {
    Context mContext = context;
    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
  }

  public static UserPrefUtils getInstance(Context context) {
    if (userPrefUtils == null) {
      userPrefUtils = new UserPrefUtils(context);
    }
    return userPrefUtils;
  }

  public void saveSkip(boolean isvalid) {
    mSharedPreferences.edit().putBoolean(SKIP_VALID, isvalid).apply();
  }

  public void saveUserName(String userName) {
    mSharedPreferences.edit().putString(USER_NAME, userName).apply();
  }

  public void saveFatherName(String fatherName) {
    mSharedPreferences.edit().putString(FATHER_NAME, fatherName).apply();
  }

  public void saveBirthDate(String birthDate) {
    mSharedPreferences.edit().putString(BIRTH_DATE, birthDate).apply();
  }

  public void saveNRC(String nrc) {
    mSharedPreferences.edit().putString(NRC, nrc).apply();
  }

  public void saveTownShip(String township) {
    mSharedPreferences.edit().putString(TOWNSHIP, township).apply();
  }

  public void saveWard(String ward) {
    mSharedPreferences.edit().putString(WARD, ward).apply();
  }

  public void saveTextPref(String textPref) {
    mSharedPreferences.edit().putString(TEXT_PREF, textPref).apply();
  }

  public String getWard() {
    return mSharedPreferences.getString(WARD, "");
  }

  public String getTextPref() {
    return mSharedPreferences.getString(TEXT_PREF, "");
  }

  public String getUserName() {
    return mSharedPreferences.getString(USER_NAME, "");
  }

  public String getFatherName() {
    return mSharedPreferences.getString(FATHER_NAME, "");
  }

  public String getBirthDate() {
    return mSharedPreferences.getString(BIRTH_DATE, "");
  }

  public String getNrc() {
    return mSharedPreferences.getString(NRC, "");
  }

  public String getTownship() {
    return mSharedPreferences.getString(TOWNSHIP, "");
  }

  public boolean isValid() {
    return mSharedPreferences.getBoolean(IS_VALID, false);
  }

  public void setValid(boolean valid) {
    mSharedPreferences.edit().putBoolean(IS_VALID, valid).apply();
  }

  public boolean isSKIP() {
    return mSharedPreferences.getBoolean(SKIP_VALID, false);
  }

  public void setSKIP(boolean skip) {
    mSharedPreferences.edit().putBoolean(SKIP_VALID, skip).apply();
  }

  public boolean isFristTime() {
    return mSharedPreferences.getBoolean(FIRST_TIME, true);
  }

  public void noLongerFirstTime() {
    mSharedPreferences.edit().putBoolean(FIRST_TIME, false).apply();
  }
}
