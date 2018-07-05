package com.example.sara.marketer.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;


import com.example.sara.marketer.model.Merchant;
import com.example.sara.marketer.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raad on 10/28/17.
 */

public class MerchantTable {
    public static final String TABLE_NAME_MERCHANT = "merchants";


    public static String _ID = "_id";
    public static String ACCOUNT_ID = "accountId";
    public static String MERCHANT_FIRST_NAME = "firstName";
    private static String MERCHANT_LAST_NAME = "lastName";
    private static String MERCHANT_STORE_NAME = "storeName";
    private static String MERCHANT_Address = "address";
    private static String MERCHANT_NATIONAL_CODE = "nationalCode";
    private static String MERCHANT_SHEBA_NUMBER = "shebaNumber";
    private static String MERCHANT_DESCRIPTION = "description";
    private static String MERCHANT_MOBILE_PHONE = "cellNo";
    private static String MERCHANT_SENT_STAUS = "sentStatus";
    private static String MERCHANT_DATE = "merchantDate";
    private static String MERCHANT_BANK_NAME = "bankname";
    private static String MERCHANT_BRANCH_NAME = "branchname";
    private static String MERCHANT_BRANCH_CODE = "branchcode";
    private static String MERCHANT_BIRTH_DATE = "bithdate";
    private static String MERCHANT_BIRTH_CITY_CODE = "cityofbirtcode";
    private static String MERCHANT_FATHER = "fathename";
    private static String MERCHANT_WEBSITE = "website";
    private static String MERCHANT_EMAIL = "email";
    private static String MERCHANT_ACCOUNT_TYPE = "accounttype";
    private static String MERCHANT_ACCOUNT_NO = "accountno";
    private static String MERCHANT_POSTAL_CODE = "zipcode";
    private static String MERCHANT_PHONE_NO = "phonenumeber";
    private static String MERCHANT_IDENTIFICATION_NO = "idno";
    private static String MERCHANT_STATUS = "status";
    private static String MERCHANT_LOCATION_LATITUDE = "lat";
    private static String MERCHANT_LOCATION_LONGITUDE= "long";
    private static String MERCHANT_BARCODE= "barcode";

    private static DbOpenHelper mInstance;

    public static final String CREATE_TABLE_MERCHANT =
            " CREATE TABLE " + TABLE_NAME_MERCHANT +
                    "("
                    + DbOpenHelper.MERCAHANTID + " INTEGER PRIMARY KEY , " +
                    _ID + " INTEGER , " +
                    ACCOUNT_ID + " INTEGER , " +
                    MERCHANT_FIRST_NAME + " TEXT ," +
                    MERCHANT_LAST_NAME + " TEXT ," +
                    MERCHANT_NATIONAL_CODE + " TEXT ," +
                    MERCHANT_SHEBA_NUMBER + " TEXT ," +
                    MERCHANT_Address + " TEXT ," +
                    MERCHANT_DESCRIPTION + " TEXT ," +
                    MERCHANT_MOBILE_PHONE + " TEXT ," +
                    MERCHANT_STORE_NAME + " TEXT ," +
                    MERCHANT_SENT_STAUS + " TEXT ," +
                    MERCHANT_DATE + " TEXT ," +
//                    MERCHANT_SERVER_SIDE_ID + " TEXT ," +
//                    MERCHANT_BUSINESS_LICENSE + " TEXT ," +
                    MERCHANT_ACCOUNT_NO + " TEXT ," +
                    MERCHANT_ACCOUNT_TYPE + " TEXT ," +
                    MERCHANT_BANK_NAME + " TEXT ," +
                    MERCHANT_BRANCH_CODE + " TEXT ," +
                    MERCHANT_BRANCH_NAME + " TEXT ," +
                    MERCHANT_EMAIL + " TEXT ," +
                    MERCHANT_WEBSITE + " TEXT ," +
                    MERCHANT_IDENTIFICATION_NO + " TEXT ," +
                    MERCHANT_BIRTH_DATE + " TEXT ," +
                    MERCHANT_BIRTH_CITY_CODE + " TEXT ," +
//                    MERCHANT_BIRTH_CITY_1 + " TEXT ," +
                    MERCHANT_POSTAL_CODE + " TEXT ," +
                    MERCHANT_PHONE_NO + " TEXT ," +
                    MERCHANT_FATHER + " TEXT ," +
                    MERCHANT_BARCODE + " TEXT ," +
                    MERCHANT_LOCATION_LONGITUDE + " REAL ," +
                    MERCHANT_LOCATION_LATITUDE + " REAL ," +
//                    MERCHANT_NATIONAL_CARD + " TEXT ," +
                    MERCHANT_STATUS + " TEXT );";

    final static String MERCHANT_DROP = "DROP TABLE " + TABLE_NAME_MERCHANT;

    public static void updateMerchantsToTable(final Context context, final Merchant merchantAccount, final ResultCountReceiver resultReceiver) {


        @SuppressLint("StaticFieldLeak") AsyncTask<Merchant, Void, Integer> asyncTask =
                new AsyncTask<Merchant, Void, Integer>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Integer doInBackground(Merchant... params) {
                        Merchant merchant = params[0];
                        int mRowsUpdated = 0;
//
                        Log.i("", "doInBackground: " + String.valueOf(merchant.getId()) + " date " + merchant.getModifiedDate());

//                        mRowsUpdated = context.getContentResolver().update(ContentProviderMerchnat.CONTENT_URI, contentValues, MERCHANT_ID + "=?", new String[]{String.valueOf(merchant.getId())});
                        String searchQuery = _ID + "='" + String.valueOf(merchant.getId()) + "'";

                        Cursor cursor = context.getContentResolver().query(ContentProviderMerchnat.CONTENT_URI, null, searchQuery, null, null);
                        if (cursor.getCount() == 1) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(MERCHANT_DATE, merchant.getModifiedDate());
                            contentValues.put(MERCHANT_STATUS, String.valueOf(merchant.getState()));

                            mRowsUpdated = context.getContentResolver().update(ContentProviderMerchnat.CONTENT_URI, contentValues, _ID + "=?", new String[]{String.valueOf(merchant.getId())});
                        } else {
                            ContentValues contentValues = new ContentValues();

//
                            Log.i("", "doInBackground: setMerchantsToTable" + merchant.toString());
                            Uri uri = context.getContentResolver().insert(ContentProviderMerchnat.CONTENT_URI, fillData(contentValues, merchant));

                        }
//if ()
                        Log.i("", "doInBackground: " + mRowsUpdated);
                        return mRowsUpdated;
                    }

                    @Override
                    protected void onPostExecute(Integer i) {
                        super.onPostExecute(i);
//                        if (i == 0) {
                        resultReceiver.onResult(i);
//                        }
                    }
                };

        asyncTask.execute(merchantAccount);


    }

    private static ContentValues fillData(ContentValues contentValues, Merchant merchant) {


        contentValues.put(_ID, merchant.getId());
        contentValues.put(MERCHANT_FIRST_NAME, merchant.getFirstName());
        contentValues.put(MERCHANT_LAST_NAME, merchant.getLastName());
        contentValues.put(MERCHANT_Address, merchant.getAddress());

        contentValues.put(MERCHANT_NATIONAL_CODE, merchant.getNationalCode());
        contentValues.put(MERCHANT_SHEBA_NUMBER, merchant.getShebaNumber());
        contentValues.put(MERCHANT_DESCRIPTION, merchant.getDescription());
        contentValues.put(MERCHANT_MOBILE_PHONE, merchant.getCellNo());

        contentValues.put(MERCHANT_STORE_NAME, merchant.getStoreName());
        contentValues.put(MERCHANT_STATUS, merchant.getState());
        contentValues.put(MERCHANT_ACCOUNT_NO, merchant.getAccountNo());
        contentValues.put(MERCHANT_ACCOUNT_TYPE, merchant.getAccountType());

        contentValues.put(MERCHANT_BANK_NAME, merchant.getBankName());
        contentValues.put(MERCHANT_BRANCH_NAME, merchant.getBranchName());
        contentValues.put(MERCHANT_BRANCH_CODE, merchant.getBranchCode());

        contentValues.put(MERCHANT_BIRTH_CITY_CODE, merchant.getCityOfBirthCode());
        contentValues.put(MERCHANT_POSTAL_CODE, merchant.getPostalCode());
        contentValues.put(MERCHANT_FATHER, merchant.getFathername());

        contentValues.put(MERCHANT_EMAIL, merchant.getEmail());
        contentValues.put(MERCHANT_WEBSITE, merchant.getWebSite());
        contentValues.put(MERCHANT_PHONE_NO, merchant.getPhoneNumer());

        contentValues.put(MERCHANT_BIRTH_DATE, merchant.getBirthdate());
        contentValues.put(MERCHANT_DATE, merchant.getModifiedDate());
        contentValues.put(MERCHANT_LOCATION_LATITUDE, merchant.getLatitude());
        contentValues.put(MERCHANT_LOCATION_LONGITUDE, merchant.getLastName());

        contentValues.put(MERCHANT_BARCODE, merchant.getBarcode());


        return contentValues;
    }

    @SuppressLint("StaticFieldLeak")
    public static void updateMerchantsSyncToTable(final Context context, final Merchant merchant) {

        AsyncTask<Merchant, Void, Merchant> asyncTask;
        asyncTask = new AsyncTask<Merchant, Void, Merchant>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Merchant doInBackground(Merchant... params) {
                Merchant merchant = params[0];
                ContentValues contentValues = new ContentValues();
                contentValues.put(_ID, merchant.getId());
                contentValues.put(MERCHANT_STATUS, String.valueOf(merchant.getState()));
                contentValues.put(MERCHANT_DATE, merchant.getModifiedDate());

                context.getContentResolver().update(ContentProviderMerchnat.CONTENT_URI, contentValues, DbOpenHelper.MERCAHANTID + "=?", new String[]{String.valueOf(merchant.getMerchandId())});

//                        context.getContentResolver().update(ContentProviderMerchnat.CONTENT_URI, contentValues, DbOpenHelper.ID + "=?", new String[]{String.valueOf(merchant.getMerchandId())});
                return params[0];
            }


        };
        asyncTask.execute(merchant);


    }

    public static void setMerchantsToTable(final Context context, final Merchant merchant, final ResultURIReceiver resultReceiver) {
        Log.i("", "setMerchantsToTable: ");

        @SuppressLint("StaticFieldLeak") AsyncTask<Merchant, Void, Uri> asyncTask =
                new AsyncTask<Merchant, Void, Uri>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Uri doInBackground(Merchant... params) {
                        Merchant merchant = params[0];

                        ContentValues contentValues = new ContentValues();
//
                        Log.i("", "doInBackground: setMerchantsToTable" + merchant.toString());
                        Uri uri = context.getContentResolver().insert(ContentProviderMerchnat.CONTENT_URI, fillData(contentValues, merchant));

                        return uri;
                    }

                    @Override
                    protected void onPostExecute(Uri uri) {
                        super.onPostExecute(uri);
                        if (uri != null)
                            resultReceiver.onResult(uri);

                    }
                };

        asyncTask.execute(merchant);


    }


    public static void setEditMerchantsToTable(final Context context, final Merchant merchant, final ResultCountReceiver resultReceiver) {
        Log.i("", "setMerchantsToTable: ");

        @SuppressLint("StaticFieldLeak") AsyncTask<Merchant, Void, Integer> asyncTask =
                new AsyncTask<Merchant, Void, Integer>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Integer doInBackground(Merchant... params) {
                        Merchant merchant = params[0];

                        ContentValues contentValues = new ContentValues();
                        Log.i("", "doInBackground: setMerchantsToTable" + merchant.toString());
                        int mRowsUpdated = context.getContentResolver().update(ContentProviderMerchnat.CONTENT_URI, fillData(contentValues, merchant), DbOpenHelper.MERCAHANTID + "=?", new String[]{String.valueOf(merchant.getMerchandId())});

                        return mRowsUpdated;
                    }

                    @Override
                    protected void onPostExecute(Integer i) {
                        super.onPostExecute(i);
//                        if (i == 0)
                        resultReceiver.onResult(i);

                    }
                };

        asyncTask.execute(merchant);


    }


    public static void getMerchants(final Context context, final Integer type, final ResultReceiver resultReceiver) {

        Log.i("raadmarketer", "getMerchants: " + type);
        @SuppressLint("StaticFieldLeak") AsyncTask<List<Merchant>, Void, List<Merchant>> asyncTask = new AsyncTask<List<Merchant>, Void, List<Merchant>>() {

            @SafeVarargs
            @Override
            protected final List<Merchant> doInBackground(List<Merchant>... params) {
                List<Merchant> items = params[0];
                if (items == null) {
                    items = new ArrayList<>();
                }
                Uri uri = Uri.parse(ContentProviderMerchnat.URL);
                Cursor cursor;
                if (type == AppConstant.ALL) {
                    Log.i("", "doInBackground: all");
                    String order = MERCHANT_DATE + " DESC ";

                    cursor = context.getContentResolver().query(uri, null, null, null, order);
                } else {
                    String searchQuery = MERCHANT_STATUS + "='" + type.toString() + "'";
                    String order = MERCHANT_DATE + " DESC ";
                    Log.i("", "doInBackground: " + searchQuery);
                    cursor = context.getContentResolver().query(uri, null, searchQuery, null, order);
//                    cursor = context.getContentResolver().query(uri, null, searchQuery, null, null);
                }

                assert cursor != null;
                int i = 0;

                if (cursor != null && cursor.moveToFirst())

                {
                    Log.i("", "cursor: ");
                    do {
                        setData(items, cursor);
                        i++;

                    } while (cursor.moveToNext());
                }
                if (cursor != null) {

                    cursor.close();
                }


                return items;


            }

            @Override
            protected void onPostExecute(List<Merchant> merchants) {
                super.onPostExecute(merchants);
                Log.i("", "onPostExecute: " + merchants.size());
                if (merchants == null || merchants.size() < 1) {
                    resultReceiver.onResult(null);
                    return;

                }
                resultReceiver.onResult(merchants);

            }
        };

        List<Merchant> merchants = new ArrayList<>();
        asyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, merchants);

    }

    public static void getLastDate(final Context context, final ResultReceiver resultReceiver) {

        @SuppressLint("StaticFieldLeak") AsyncTask<List<Merchant>, Void, List<Merchant>> asyncTask = new AsyncTask<List<Merchant>, Void, List<Merchant>>() {

            @Override
            protected List<Merchant> doInBackground(List<Merchant>... params) {
                List<Merchant> items = params[0];
                if (items == null) {
                    items = new ArrayList<>();
                }
                Uri uri = Uri.parse(ContentProviderMerchnat.URL);
                Cursor cursor;
                String order = MERCHANT_DATE + " DESC  LIMIT 1";
                cursor = context.getContentResolver().query(uri, null, null, null, order);


                assert cursor != null;
                int i = 0;

                if (cursor != null && cursor.moveToFirst())

                {
                    Log.i("", "cursor: ");
                    do {
                        setData(items, cursor);
                        i++;

                    } while (cursor.moveToNext());
                }
                if (cursor != null) {

                    cursor.close();
                }


                return items;


            }

            @Override
            protected void onPostExecute(List<Merchant> merchants) {
                super.onPostExecute(merchants);
                if (merchants == null || merchants.size() < 1) {
                    resultReceiver.onResult(null);
                    return;

                }
                resultReceiver.onResult(merchants);

            }
        };

        List<Merchant> merchants = new ArrayList<>();
        asyncTask.execute(merchants);

    }


    private static void setData(List<Merchant> items, Cursor cursor) {
        Log.i("", "setData: ");
        Merchant merchant = new Merchant();

        merchant.setMerchandId(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.MERCAHANTID)));

        merchant.setId(cursor.getString(cursor.getColumnIndex(_ID)));
        merchant.setFirstName(cursor.getString(cursor.getColumnIndex(MERCHANT_FIRST_NAME)));
        merchant.setLastName(cursor.getString(cursor.getColumnIndex(MERCHANT_LAST_NAME)));
        merchant.setAddress(cursor.getString(cursor.getColumnIndex(MERCHANT_Address)));

        merchant.setNationalCode(cursor.getString(cursor.getColumnIndex(MERCHANT_NATIONAL_CODE)));
        merchant.setShebaNumber(cursor.getString(cursor.getColumnIndex(MERCHANT_SHEBA_NUMBER)));
        merchant.setDescription(cursor.getString(cursor.getColumnIndex(MERCHANT_DESCRIPTION)));
        merchant.setCellNo(cursor.getString(cursor.getColumnIndex(MERCHANT_MOBILE_PHONE)));

        merchant.setStoreName(cursor.getString(cursor.getColumnIndex(MERCHANT_STORE_NAME)));
        merchant.setState(cursor.getInt(cursor.getColumnIndex(MERCHANT_STATUS)));
        merchant.setAccountNo(cursor.getString(cursor.getColumnIndex(MERCHANT_ACCOUNT_NO)));
        merchant.setAccountType(cursor.getString(cursor.getColumnIndex(MERCHANT_ACCOUNT_TYPE)));

//        merchant.setBusinessLicenceImage(cursor.getString(cursor.getColumnIndex(MERCHANT_BUSINESS_LICENSE)));
//        merchant.setNationalCardImage(cursor.getString(cursor.getColumnIndex(MERCHANT_NATIONAL_CARD)));

        merchant.setCityOfBirthCode(cursor.getString(cursor.getColumnIndex(MERCHANT_BIRTH_CITY_CODE)));
        merchant.setPostalCode(cursor.getString(cursor.getColumnIndex(MERCHANT_POSTAL_CODE)));

        merchant.setBankName(cursor.getString(cursor.getColumnIndex(MERCHANT_BANK_NAME)));
        merchant.setBranchName(cursor.getString(cursor.getColumnIndex(MERCHANT_BRANCH_NAME)));
        merchant.setBranchCode(cursor.getString(cursor.getColumnIndex(MERCHANT_BRANCH_CODE)));

        merchant.setWebSite(cursor.getString(cursor.getColumnIndex(MERCHANT_WEBSITE)));
        merchant.setEmail(cursor.getString(cursor.getColumnIndex(MERCHANT_EMAIL)));
        merchant.setPhoneNumer(cursor.getString(cursor.getColumnIndex(MERCHANT_PHONE_NO)));

        merchant.setFathername(cursor.getString(cursor.getColumnIndex(MERCHANT_FATHER)));
        merchant.setBirthdate(cursor.getString(cursor.getColumnIndex(MERCHANT_BIRTH_DATE)));
        merchant.setModifiedDate(cursor.getString(cursor.getColumnIndex(MERCHANT_DATE)));
        merchant.setLatitude(cursor.getFloat(cursor.getColumnIndex(MERCHANT_LOCATION_LATITUDE)));
        merchant.setLongitude(cursor.getFloat(cursor.getColumnIndex(MERCHANT_LOCATION_LONGITUDE)));

        merchant.setBarcode(cursor.getString(cursor.getColumnIndex(MERCHANT_BARCODE)));

        Log.i("", "setData: " + merchant.getState());
        items.add(merchant);
        Log.i("", "items.add: " + items.size());

    }
}
