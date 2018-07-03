package com.example.sara.marketer.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Sara
 * on 5/25/2017.
 */

public class Utils {

    public static float getPx(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }


    public static float getDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String timePassed(String created) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //        2016-02-23 12:31:20"

        try {

            Date creationDate = simpleDateFormat.parse(created);
            Calendar calendar = Calendar.getInstance();

            Log.i("", "timePassed: "+calendar.getTime());

            TimeZone tz = TimeZone.getDefault();
            Date now = new Date();
            int offsetFromUtc = tz.getOffset(now.getTime());

            long msPassed = calendar.getTime().getTime() - offsetFromUtc - creationDate.getTime();
            msPassed = msPassed / 1000;
            if (msPassed < 60)
                return msPassed + "s";
//                return msPassed + " " + (msPassed == 1 ? "second" : "seconds") + " ago";
            else if (msPassed < 3600) {
                msPassed = msPassed / 60;
                return msPassed + "m";
//                return msPassed + " " + (msPassed == 1 ? "minute" : "minutes") + " ago";
            } else if (msPassed < 3600 * 24) {
                msPassed = msPassed / 3600;
                return msPassed + "h";
//                return msPassed + " " + (msPassed == 1 ? "hour" : "hours") + " ago";
            } else {
                msPassed = msPassed / (3600 * 24);
                return msPassed + "d";
//                return msPassed + " " + (msPassed == 1 ? "day" : "days") + " ago";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}
