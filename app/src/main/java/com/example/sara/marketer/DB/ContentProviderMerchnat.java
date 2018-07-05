package com.example.sara.marketer.DB;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Raad on 10/28/17.
 */

public class ContentProviderMerchnat extends ContentProvider {
    static final String PROVIDER_NAME = "com.raadsense.raadmarketer.DB.ContentProviderMerchnat";
    static final String URL = "content://" + PROVIDER_NAME + "/" + MerchantTable.TABLE_NAME_MERCHANT;
    public static final Uri CONTENT_URI = Uri.parse(URL);

    private static HashMap<String, String> MERCHANTS_PROJECTION_MAP;

    static final int MERCHANTS = 1;
    static final int MERCHANT_ID = 2;

    private SQLiteDatabase db;
    DbOpenHelper dbHelper;
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, MerchantTable.TABLE_NAME_MERCHANT, MERCHANTS);
        uriMatcher.addURI(PROVIDER_NAME, MerchantTable.TABLE_NAME_MERCHANT+"/#", MERCHANT_ID);
    }
    @Override
    public boolean onCreate() {
        Context context = getContext();
         dbHelper = new DbOpenHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */

        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(MerchantTable.TABLE_NAME_MERCHANT);

        switch (uriMatcher.match(uri)) {
            case MERCHANTS:
                qb.setProjectionMap(MERCHANTS_PROJECTION_MAP);
                break;

            case MERCHANT_ID:
                qb.appendWhere( DbOpenHelper.MERCAHANTID + "=" + uri.getPathSegments().get(1));
                break;

            default:
        }

        if (sortOrder == null || sortOrder == ""){
            /**
             * By default sort on student names
             */
            sortOrder = MerchantTable.MERCHANT_FIRST_NAME;
        }

        Cursor c = qb.query(db,	projection,	selection,
                selectionArgs,null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all student records
             */
            case MERCHANTS:
                return "vnd.android.cursor.dir/vnd.example.students";
            /**
             * Get a particular student
             */
            case MERCHANT_ID:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
//        long rowID = db.insert(	MerchantTable.TABLE_NAME_MERCHANT, "", values);

        /**
         * If record is added successfully
         */

        int uriType = uriMatcher.match(uri);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        switch (uriType) {

            case MERCHANTS:
                id = sqlDB.insert(MerchantTable.TABLE_NAME_MERCHANT, null, values);
//                try {
//
//                    sqlDB.execSQL("");
//                }catch (Exception e){
//
//                }
//                break;

            break;


            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
//        return Uri.parse(BASE_PATH_QUICK_ACCESS_CATEGORIES_THREAD + "/" + id);
        return ContentUris.withAppendedId(uri, id);


//
//        if (rowID > 0) {
//            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
//            getContext().getContentResolver().notifyChange(_uri, null);
//            return _uri;
//        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case MERCHANTS:
                count = db.delete(MerchantTable.TABLE_NAME_MERCHANT, selection, selectionArgs);
                break;

            case MERCHANT_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( MerchantTable.TABLE_NAME_MERCHANT, DbOpenHelper.MERCAHANTID +  " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

            int count = 0;
            switch (uriMatcher.match(uri)) {
                case MERCHANTS:
                    count = db.update(MerchantTable.TABLE_NAME_MERCHANT, values, selection, selectionArgs);
                    break;

                case MERCHANT_ID:
                    count = db.update(MerchantTable.TABLE_NAME_MERCHANT, values,
                            DbOpenHelper.MERCAHANTID + " = " + uri.getPathSegments().get(1) +
                                    (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri );
            }

            getContext().getContentResolver().notifyChange(uri, null);
            return count;
    }

}


