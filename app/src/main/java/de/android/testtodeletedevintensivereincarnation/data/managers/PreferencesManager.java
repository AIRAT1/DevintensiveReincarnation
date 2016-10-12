package de.android.testtodeletedevintensivereincarnation.data.managers;

import android.content.SharedPreferences;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import de.android.testtodeletedevintensivereincarnation.utils.ConstantManager;
import de.android.testtodeletedevintensivereincarnation.utils.DevintensiveApplication;

public class PreferencesManager {
    private SharedPreferences sharedPreferences;

    private static final String[] USER_FIELDS = {
            ConstantManager.USER_PHONE_KEY,
            ConstantManager.USER_MAIL_KEY,
            ConstantManager.USER_VK_KEY,
            ConstantManager.USER_GIT_KEY,
            ConstantManager.USER_BIO_KEY};
    private static final String[] USER_VALUES = {
            ConstantManager.USER_RATING_VALUES,
            ConstantManager.USER_CODE_LINES_VALUES,
            ConstantManager.USER_PROJECT_VALUES
    };

    public PreferencesManager() {
        this.sharedPreferences = DevintensiveApplication.getSharedPreferences();
    }
    public void saveUserProfileData(List<String> userFields) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < USER_FIELDS.length; i++) {
            editor.putString(USER_FIELDS[i], userFields.get(i));
        }
        editor.apply();
    }
    public List<String> loadUserProfileData() {
        List<String> userList = new ArrayList<>();

        for (int i = 0; i < USER_FIELDS.length; i++) {
            userList.add(sharedPreferences.getString(USER_FIELDS[i], null));
        }

        return userList;
    }
    public void saveUserPhoto(Uri uri) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConstantManager.USER_PHOTO_KEY, uri.toString());
        editor.apply();
    }
    public List<String> LoadUserProfileValues() {
        List<String> userValues = new ArrayList<>();
        userValues.add(sharedPreferences.getString(ConstantManager.USER_RATING_VALUES, "0"));
        userValues.add(sharedPreferences.getString(ConstantManager.USER_CODE_LINES_VALUES, "0"));
        userValues.add(sharedPreferences.getString(ConstantManager.USER_PROJECT_VALUES, "0"));
        return userValues;
    }
    public void saveUserProfileValues(int[] userValues) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < USER_VALUES.length; i++) {
            editor.putString(USER_VALUES[i], String.valueOf(userValues[i]));
        }
        editor.apply();
    }
    public Uri loadUserPhoto() {
        return Uri.parse(sharedPreferences.getString(ConstantManager.USER_PHOTO_KEY,
                "android.resource://de.testtodeletedevintensivereincarnation/drawable/nicole_kidman_getty"));
    }
    public void saveAuthToken(String authToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConstantManager.AUTH_TOKEN_KEY, authToken);
        editor.apply();
    }
    public String getAuthToken() {
        return sharedPreferences.getString(ConstantManager.AUTH_TOKEN_KEY, "null");
    }
    public void saveUserId(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConstantManager.USER_ID_KEY, userId);
        editor.apply();
    }
    public String getUserId() {
        return sharedPreferences.getString(ConstantManager.USER_ID_KEY, "null");
    }
}
