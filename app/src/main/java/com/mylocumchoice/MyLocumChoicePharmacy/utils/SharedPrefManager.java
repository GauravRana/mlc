package com.mylocumchoice.MyLocumChoicePharmacy.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShift;

import static com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.SortActivity.openShift;


public class SharedPrefManager {
    private static final String PREF_NAME = "SharedPref";
    private static final String ACCOUNT_PREF_NAME = "ACCOUNT_PREF_NAME";
    private static final String INTENT_PREF_NAME = "INTENT_PREF_NAME";
    private static final String AUTHENTICATION_TOKEN = "authentication_token";

    private static final String EMAIL = "email";
    private static final String GOC = "goc";
    private static final String EXPIRY = "expiry";
    private static final String PASSWORD = "password";
    private static final String MOBILE = "mobile";
    private static final String NAME = "name";
    private static final String NAMEV = "namev";
    private static final String RECEIVE = "receive";


    private static final String UNIQUE = "unique";
    private static final String CHECKED_PREF_NAME = "CHECKED_PREF_NAME";
    private static final String CHECKED = "checked";
    private static final String PACE_PREFS = "PACE_PREFS";
    private static final String PACE = "pace";


    private static final String BASIC_DETAIL_ID_PREFS = "BASIC_DETAIL_ID_PREFS";
    private static final String BASIC_DETAIL_ID = "BASIC_DETAIL_ID";


    private static final String UNIQUEIDPREF = "UNIQUE_ID_PREF";
    private static final String UNIQUE_ID = "UNQUE_ID";


    private static final String FCM_TOKEN = "FCM_TOKEN";
    private static final String FCM = "FCM";

    private static final String FIRST_OPEN = "first_open";
    private static final String NOTIFY = "notify";


    private static final String ADDRESS_PREFERENCE = "address_pref";
    private static final String LOC_PREFERENCE = "loc_pref";
    private static final String CURRENT_LOC_PREFERENCE = "current_loc_pref";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String CURRENT_LONGITUDE = "current_longitude";
    private static final String CURRENT_LATITUDE = "current_latitude";
    private static final String ADDRESS = "address";

    private static final String scroll="scroll";
    private static final String scrollPref="scrollPref";
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String VALUE_PREF = "value_pref";
    private static final String VALUE = "value";
    private static final String SHIFT_SAVED_DATA = "shift_saved_data";


    private static final String DateDataPref = "dateDataPref";
    private static final String DateData = "dateData";

    private static final int notificationCount = 0;
    private static final String NOTIFICATION_PREFS = "notification_prefs";
    private static final String NOTIFICATION = "notification";


    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveAuthToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AUTHENTICATION_TOKEN, token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getAuthToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AUTHENTICATION_TOKEN, null);
    }


    public boolean setEmail(String email) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, email);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL, null);
    }

    public void clearIdPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }


    public boolean setData(String gphc, String expiry, String name, String email, String pwd, String mob) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(ACCOUNT_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, gphc);
        editor.putString(EXPIRY, expiry);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, pwd);
        editor.putString(MOBILE, mob);
        editor.apply();
        return true;
    }


    public String getAccountEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL, null);
    }

    public String getAccountName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NAME, null);
    }

    public String getAccountExpiry() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EXPIRY, null);
    }

    public String getAccountGoc() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(GOC, null);
    }

    public String getAccountPwd() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSWORD, null);
    }

    public String getAccountMob() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MOBILE, null);
    }


    public boolean setIntentData(String gphc, String name, boolean receive, String expiry) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GOC, gphc);
        editor.putString(NAMEV, name);
        editor.putBoolean(RECEIVE, receive);
        editor.putString(EXPIRY, expiry);
        editor.apply();
        return true;
    }


    public String getGOC() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(GOC, null);
    }

    public String getAccountNameV() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NAMEV, null);
    }

    public boolean getReceiveV() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(RECEIVE, true);
    }

    public String getEXpiryV() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EXPIRY, null);
    }

    public boolean getPwdV() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(PASSWORD, true);
    }

    public String getMobileV() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MOBILE, null);
    }


    public boolean setEditData(String name, String email, String pwd, String mobile) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, pwd);
        editor.putString(MOBILE, mobile);
        editor.apply();
        return true;
    }


    public String getNAMEE() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL, null);
    }

    public String getEmailE() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL, null);
    }

    public String getPwdE() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSWORD, null);
    }

    public String getMobE() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MOBILE, null);
    }


//         etName.setText(SharedPrefManager.getInstance(AccountDetActivity.this).getNa());
//                etEmail.setText(SharedPrefManager.getInstance(AccountDetActivity.this).getEXpiryV());
//                etPwd.setText(SharedPrefManager.getInstance(AccountDetActivity.this).getEXpiryV());
//                etMob.setText(SharedPrefManager.getInstance(AccountDetActivity.this).getMobileV());


    public boolean setChecked(String checked) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(CHECKED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CHECKED, checked);
        editor.apply();
        return true;
    }


    public String getChecked() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(CHECKED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CHECKED, "");
    }

    public void clearCheckedPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(CHECKED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public boolean setPace(String pace) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PACE_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PACE, pace);
        editor.apply();
        return true;
    }


    public String getPace() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PACE_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PACE, "");
    }


    public void clearPacePref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PACE_PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }


    public boolean setDate(String date) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(DateDataPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DateData, date);
        editor.apply();
        return true;
    }


    public int getOverallScroll(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(scrollPref, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(scroll, 0);
    }

    public void setOverallScroll(int y){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(scrollPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(scroll, y);
        editor.apply();
    }


    public String getDate() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(DateDataPref, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DateData, "");
    }

    public void setCountyId(String id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(BASIC_DETAIL_ID_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BASIC_DETAIL_ID, id);
        editor.apply();
    }


    public String getCountyId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(BASIC_DETAIL_ID_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(BASIC_DETAIL_ID, "");
    }


    public boolean setUniqueID(String id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(UNIQUEIDPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UNIQUE, id);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getUniqueID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(UNIQUEIDPREF, Context.MODE_PRIVATE);
        return sharedPreferences.getString(UNIQUE, "");
    }


    public boolean setFCMToken(String fcm_token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FCM_TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FCM, fcm_token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getFCMToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FCM_TOKEN, Context.MODE_PRIVATE);
        return sharedPreferences.getString(FCM, "");
    }

    public boolean setLatLng(String latitude,String longitude) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(LOC_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LATITUDE, latitude);
        editor.putString(LONGITUDE, longitude);
        editor.apply();
        return true;
    }

    public boolean setCurrentLatLng(String latitude,String longitude) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(CURRENT_LOC_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENT_LATITUDE, latitude);
        editor.putString(CURRENT_LONGITUDE, longitude);
        editor.apply();
        return true;
    }

    //this method will fetch the default latitude from shared preferences
    public String getLat() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(LOC_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LATITUDE, "0.0");
    }

    //this method will fetch the default latitude from shared preferences
    public String getLong() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(LOC_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LONGITUDE, "0.0");
    }


    //this method will fetch the default latitude from shared preferences
    public String getCurrentLat() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(CURRENT_LOC_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CURRENT_LATITUDE, "0.0");
    }

    //this method will fetch the default latitude from shared preferences
    public String getCurrentLong() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(CURRENT_LOC_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CURRENT_LONGITUDE, "0.0");
    }


    public boolean setAddress(String address) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(ADDRESS_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ADDRESS, address);
        editor.apply();
        return true;
    }

    //this method will fetch the default address from shared preferences
    public String getAddress() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(ADDRESS_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ADDRESS, "");
    }


    /**
     * Method to set either the app is opened for first time or not
     *
     * @param isOpen
     */
    public void setFirstOpen(String isOpen) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FIRST_OPEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIRST_OPEN, isOpen);
        editor.apply();
        editor.commit();
    }


    /**
     * Method to return either the app is opened first time or not
     *
     * @return
     */
    public String getFirstOpen() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FIRST_OPEN, Context.MODE_PRIVATE);
        return sharedPreferences.getString(FIRST_OPEN, "");
    }


    /**
     * Method to set preferences for red dot on notification icon
     *
     * @param isNotify
     */
    public void setNotifyDot(boolean isNotify) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(NOTIFY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NOTIFY, isNotify);
        editor.apply();
        editor.commit();
    }


    /**
     * Method to return preferences for red dot on notification icon
     *
     * @return
     */
    public boolean getNotifyDot() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(NOTIFY, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(NOTIFY, false);
    }


    public boolean setFieldValue(String value) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(VALUE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(VALUE, value);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getFieldValue() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(VALUE_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getString(VALUE, "");
    }

    /**
     * Method to save selected data for open shifts
     *
     * @param openShift
     */
    public void setShiftData(OpenShift openShift) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHIFT_SAVED_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(openShift);
        editor.putString(SHIFT_SAVED_DATA, json);
        editor.commit();
    }


    /**
     * Method to return open shift saved data
     *
     * @return
     */
    public OpenShift getOpenShiftData() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHIFT_SAVED_DATA, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SHIFT_SAVED_DATA, "");
        OpenShift openShift = gson.fromJson(json, OpenShift.class);
        return openShift;
    }

    /**
     * Method to clear preferences except fcm token
     */
    public void clearAllPreferences() {
        clearIdPref();
        clearIntentPref();
        clearCheckedPref();
        clearPacePref();
        clearBasicDetailPref();
        clearUniqueIdPref();
        clearValuePref();
        clearShiftPref();
        clearDate();
        clearLatLongPref();
        clearAddressPref();
    }

    public void clearLatLongPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(LOC_PREFERENCE, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public void clearAddressPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(ADDRESS_PREFERENCE, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public void clearIntentPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(INTENT_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }


    public void clearBasicDetailPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(BASIC_DETAIL_ID_PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }


    public void clearUniqueIdPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(UNIQUEIDPREF, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public void clearValuePref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(VALUE_PREF, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public void clearShiftPref() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHIFT_SAVED_DATA, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }


    public void clearDate() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(DateDataPref, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }


    public void setNotificationCount(int notify) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(NOTIFICATION_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NOTIFICATION, notify);
        editor.apply();
        editor.commit();
    }

    public int getNotificationCount() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(NOTIFICATION_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(NOTIFICATION, 0);
    }
}

