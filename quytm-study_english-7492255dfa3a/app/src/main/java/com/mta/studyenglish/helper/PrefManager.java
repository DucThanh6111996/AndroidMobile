package com.mta.studyenglish.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Khởi tạo đối tượng Shared preference
 * Lưu các giá trị đánh dấu lần đầu login và trạng thái đã login hay chưa?
 */

public class PrefManager {

    private static String TAG = PrefManager.class.getSimpleName();
    private static int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "study_english_session";

    private static final String KEY_IS_LOGGED_IN = "is_login";
    private static final String KEY_IS_FIRST_LOGGED_IN = "is_first_login";
    private static final String KEY_USERNAME = "username";

    private static SharedPreferences pref = null;

    private static SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        if (pref == null) {
            pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }
    }

    public static void setIsFirstLogin(boolean isFirstLogin) {
        editor.putBoolean(KEY_IS_FIRST_LOGGED_IN, isFirstLogin);
        editor.commit();
    }

    public static void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public static boolean isFirstLoggedIn() {
        return pref.getBoolean(KEY_IS_FIRST_LOGGED_IN, true);
    }

    public static boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static void setUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }
    public static String getUsername() {
        return pref.getString(KEY_USERNAME, "username");
    }
}
