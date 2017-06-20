package com.example.cheng.skindemo;

import android.app.Application;

/**
 * Created by Administrator on 2017/6/20.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
        String sourcePath = SharedPreferencesHelper.getApkPath(this);
        if (!sourcePath.isEmpty()) {
            SkinManager.getInstance().loadSkin(sourcePath);
        }
    }
}
