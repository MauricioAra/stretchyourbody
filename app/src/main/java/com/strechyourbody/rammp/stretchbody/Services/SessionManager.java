package com.strechyourbody.rammp.stretchbody.Services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.strechyourbody.rammp.stretchbody.Activities.LoginActivity;
import com.strechyourbody.rammp.stretchbody.Entities.UserSession;

public class SessionManager {

    private SharedPreferences pref;
    private Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "SYBPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    //UserId
    private static final String KEY_USER_ID = "userId";

    //Username
    public static final String KEY_USER_NAME = "username";

    // JWT Token
    public static final String KEY_TOKEN = "jwtToken";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Creates a new session
    public void createLoginSession(Long userId, String username, String token) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putLong(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, username);
        editor.putString(KEY_TOKEN, token);
        // commit changes
        editor.commit();
    }

    //Creates a new session only with Token
    public void createSessionWithTokenOnly(String token) {
        editor.putString(KEY_TOKEN, token);
        // commit changes
        editor.commit();
    }

    //Returns the user's session
    public UserSession getUserDetails(){
        UserSession userSession = new UserSession();
        userSession.setUserId(pref.getLong(KEY_USER_ID, 0));
        userSession.setUsername(pref.getString(KEY_USER_NAME, null));
        userSession.setToken(pref.getString(KEY_TOKEN, null));

        return userSession;
    }

    public String getJWTToken() {
        String token;
        token = pref.getString(KEY_TOKEN, null);
        return token;
    }

    //Logout
    //Redirects user to Login Page
    public void logOut(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}