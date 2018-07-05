package com.example.sara.marketer.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Raad on 9/2/17.
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME_MERCHANT = "dbmerchantsT";
    private static final int DATABASE_VERSION = 2;

    public static String MERCAHANTID = "merchantId";

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME_MERCHANT, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MerchantTable.CREATE_TABLE_MERCHANT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            Log.i("", "onUpgrade: ");
            db.execSQL(MerchantTable.MERCHANT_DROP);
            onCreate(db);
        }
    }

}
