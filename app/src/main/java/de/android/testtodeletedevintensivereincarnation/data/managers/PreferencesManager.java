package de.android.testtodeletedevintensivereincarnation.data.managers;

import android.content.SharedPreferences;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import de.android.testtodeletedevintensivereincarnation.utils.ConstantManager;
import de.android.testtodeletedevintensivereincarnation.utils.DevintensiveApplication;

public class PreferencesManager {
    private SharedPreferences sharedPreferences;

    private static final String[] USER_FIELDS = {ConstantManager.USER_PHONE_KEY,
    ConstantManager.USER_MAIL_KEY, ConstantManager.USER_VK_KEY,
    ConstantManager.USER_GIT_KEY, ConstantManager.USER_BIO_KEY};

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

//        userList.add(sharedPreferences.getString(ConstantManager.USER_PHONE_KEY, "null"));
//        userList.add(sharedPreferences.getString(ConstantManager.USER_MAIL_KEY, "null"));
//        userList.add(sharedPreferences.getString(ConstantManager.USER_VK_KEY, "null"));
//        userList.add(sharedPreferences.getString(ConstantManager.USER_GIT_KEY, "null"));
//        userList.add(sharedPreferences.getString(ConstantManager.USER_BIO_KEY, "null"));

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
    public Uri loadUserPhoto() {
        return Uri.parse(sharedPreferences.getString(ConstantManager.USER_PHOTO_KEY,
                "android.resource://de.testtodeletedevintensivereincarnation/drawable/nicole_kidman_getty"));
    }
}
