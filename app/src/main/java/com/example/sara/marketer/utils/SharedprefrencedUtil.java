package com.example.sara.marketer.utils;

import android.content.SharedPreferences;

import com.example.sara.marketer.MyApplication;
import com.example.sara.marketer.model.User;
import com.google.gson.Gson;

/**
 * Created by  on 1/31/18.
 */

public class SharedprefrencedUtil {


    public final static String PREFS_NAME = "MarketerAppSharedPrefs";
    public final static String USER_KEY = "MarketerUserSharedPrefsKey";
    public static final String DEVICE_TOKEN = "MarketerDeviceToken";
    public static final String LAST_TIME = "MarketerTimeStamp";


    public static void saveShareprefrenced(String filedName, Object object) {

        Gson gson = new Gson();
        if (object instanceof String) {
            SharedPreferences.Editor editor = MyApplication.MY_SHARED_PREFRENCES.edit();
            editor.putString(filedName, (String) object);
            editor.commit();
        }
        if (object instanceof Long) {
            SharedPreferences.Editor editor = MyApplication.MY_SHARED_PREFRENCES.edit();
            editor.putLong(filedName, (Long) object);
            editor.commit();

        }
        if (object instanceof User) {
            String user = gson.toJson(object);
            SharedPreferences.Editor editor = MyApplication.MY_SHARED_PREFRENCES.edit();
            editor.putString(USER_KEY, user).apply();
        }
    }

    public  static  void deleteUser() {
        MyApplication.MY_SHARED_PREFRENCES.edit().remove(USER_KEY).apply();
    }

    public static  void deleteDeviceToken() {
        MyApplication.MY_SHARED_PREFRENCES.edit().remove(DEVICE_TOKEN).apply();
    }

}
