package org.mmaug.mae.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by yemyatthu on 9/26/15.
 */
public class UserPrefUtils {
  private Context mContext;
  public UserPrefUtils(Context context){
    mContext = context;
  }
  private SharedPreferences mSharedPreferences;

  private SharedPreferences getDefaultSharePreferences(Context context){
    if(mSharedPreferences==null){
      mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    return mSharedPreferences;
  }

  
}
