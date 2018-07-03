package com.example.sara.marketer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.sara.marketer.model.User;


/**
 * Created by Raad on 8/28/17.
 */

public class MyApplication extends Application {
    final static String TAG = MyApplication.class.getSimpleName();



    public final static String PREFS_NAME = "MarketerAppSharedPrefs";
    public final static String USER_KEY = "MarketerUserSharedPrefsKey";
    public static final String DEVICE_TOKEN = "MarketerDeviceToken";
    public static final String LAST_TIME = "MarketerTimeStamp";

    private User currentUser;
    private String deviceToken;
    private Long lastSyncTimeStamp;

    public static Context myApplicationContext;

    public static SharedPreferences MY_SHARED_PREFRENCES;


    @Override
    public void onCreate() {
        super.onCreate();
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath(AppConstant.IRAN_LIGHT)
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );


//        MY_SHARED_PREFRENCES = getSharedPreferences(SharedprefrencedUtil.PREFS_NAME, Context.MODE_PRIVATE);



    }



}
