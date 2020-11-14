package io.fmc.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

import io.fmc.data.models.User;
import io.fmc.ui.users.login.LoginActivity;


/**
 * Created by Kevin Phillips and Sunday Akinsete on 7/15/17.
 */

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	SharedPreferences.Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "AndroidHivePref";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "name";

	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "email";


	public static final String DEFAULT_CIRCLE = "default_circle";

	private static final String TOKEN_SENT = "token_Sent";

	private static final String FCM_SENT = "fcm_token";
	
	private static final String PROFILE_UPDATED = "profile_updated";
	
	private static final String LAST_LOCATION = "last_location";

	private static final String FAMILY_NAME = "family_name";


	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

    public void setLoginUser(User loginUser) {
		setUserLogin();

    }


    public void saveFamilyName(String token) {
		editor.putString(FAMILY_NAME, token);
		editor.commit();
	}

	public String getFamilyName() {
		return pref.getString(FAMILY_NAME, "");
	}



	public void setTokenSaved() {
		editor.putBoolean(TOKEN_SENT, true);
		editor.commit();
	}

	public boolean isTokenSaved(){
		return pref.getBoolean(TOKEN_SENT, false);
	}

	/**
	 * Set user login status
	 */
	public void setUserLogin(){
		editor.putBoolean(IS_LOGIN, true);
		editor.commit();
	}
	
	/**
	 * Set user login status
	 */
	public void setProfileUpdated(){
		editor.putBoolean(PROFILE_UPDATED, true);
		editor.commit();
	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isProfileUpdated(){
		return pref.getBoolean(PROFILE_UPDATED, false);
	}
	
	
	
	/**
	 * Create login session
	 * */
	public void createLoginSession(String name, String email){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

		// Storing name in pref
		editor.putString(KEY_NAME, name);

		// Storing email in pref
		editor.putString(KEY_EMAIL, email);

		// commit changes
		editor.commit();
	}



	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
	public void checkLogin(){
		// Check login status
		if(!this.isLoggedIn()){
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);
		}

	}



	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();

		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, io.fmc.ui.users.login.LoginActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		FirebaseAuth.getInstance().signOut();

		// Staring Login Activity
		_context.startActivity(i);
	}

	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}


}
