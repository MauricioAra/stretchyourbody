package com.strechyourbody.rammp.stretchbody.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.strechyourbody.rammp.stretchbody.Entities.JWTToken;
import com.strechyourbody.rammp.stretchbody.Entities.UserCredentials;
import com.strechyourbody.rammp.stretchbody.Entities.UserSession;

import static java.security.AccessController.getContext;

public class DBService extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "stretchLite";

    // Authentication table name
    private static final String AUTH_TABLE = "authentication";

    // Authentication Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_TOKEN = "token";

    public DBService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_AUTHENTICATION_TABLE = "CREATE TABLE " + AUTH_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID + " INTEGER,"
                + KEY_USER_NAME + " TEXT," + KEY_TOKEN + " TEXT" + ")";
        db.execSQL(CREATE_AUTHENTICATION_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + AUTH_TABLE);
        // Create tables again
        onCreate(db);
    }

    /*
     * Saving authentication token
     */
    public Boolean saveAuthToken(JWTToken token, long userId) {

        Boolean inserted = false;
        long id;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_TOKEN, token.getIdToken());
        try {
             id = db.insert(AUTH_TABLE, null, values);
            inserted = true;
        } catch (SQLiteException e) {
            inserted = false;
        }


        return inserted;
    }

    /*
     * Get Authentication token
     */
    public UserSession getSesion(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + AUTH_TABLE + " WHERE "
                + KEY_USER_NAME+ " = " + username;

        Log.e("query:", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        UserSession userSession = new UserSession();
        userSession.setUserId(c.getLong(c.getColumnIndex(KEY_USER_ID)));
        userSession.setToken(c.getString(c.getColumnIndex(KEY_TOKEN)));
        userSession.setUsername(c.getString(c.getColumnIndex(KEY_USER_NAME)));

        return userSession;
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {

        }
        return checkDB != null;
    }
}