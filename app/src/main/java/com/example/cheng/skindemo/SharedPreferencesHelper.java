package com.example.cheng.skindemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/6/20.
 */

class SharedPreferencesHelper {

    static String getApkPath(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("resources", Context.MODE_PRIVATE);
        return preferences.getString("apk_path", "");
    }

    static void saveApkPath(Context context, String path) {
        SharedPreferences preferences = context.getSharedPreferences("resources", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("apk_path", path);
        editor.apply();
    }
}
