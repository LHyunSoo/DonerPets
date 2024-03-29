package kr.hs.emirim.sookhee.donerpets_final;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_EMAIL= "email";
    static final String PREF_USER_NAME= "name";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setEmail(Context ctx, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_EMAIL, email);
        editor.commit();
    }

    public static String getEmail(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_EMAIL, "");
    }

    // 로그아웃
    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}
