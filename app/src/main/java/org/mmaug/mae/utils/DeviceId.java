package org.mmaug.mae.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.mmaug.mae.BuildConfig;

import java.util.UUID;

/**
 * Created by lynn on 10/19/15.
 */
public class DeviceId {

    private static DeviceId mInstance;

    private Context mContext;

    private SharedPreferences mSharedPreferences;

    private static final String KEY_NAME = "mae_uuid";

    public DeviceId(Context context) {
        mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static DeviceId getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DeviceId(context);
        }
        return mInstance;
    }

    public String get()
    {
        String uuid = mSharedPreferences.getString(KEY_NAME, "");

        // Return uuid from cache
        if (uuid.length() > 0) {
            Log.d("DeviceID Util Class", "Old UUID is " + uuid);
            return uuid;
        }

        saveUniqueId();


        uuid = mSharedPreferences.getString(KEY_NAME, "no-way-to-find-device-id");

        if (BuildConfig.DEBUG) {
            Log.d("DeviceID Util Class", "Created UUID is " +uuid);
        }

        return uuid;
    }


    private void saveUniqueId() {
        String uuid = UUID.randomUUID().toString();

        Log.d("DeviceId Unique ID", "Unique ID is " + uuid);
        // Save to cache
        mSharedPreferences.edit().putString(KEY_NAME, uuid).apply();
    }
}
