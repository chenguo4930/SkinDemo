package com.example.cheng.skindemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 */

class ActivityManager {
    private static final ActivityManager ourInstance = new ActivityManager();

    static ActivityManager getInstance() {
        return ourInstance;
    }

    private ActivityManager() {
    }

    public List<SkinActivity> activities = new ArrayList<>();


    public void changeSkin() {
        for (SkinActivity activity : activities) {
            activity.skinFactory.apply();
        }
    }
}
