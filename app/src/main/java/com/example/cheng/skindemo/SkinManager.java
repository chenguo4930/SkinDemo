package com.example.cheng.skindemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by cheng on 2017/6/18.
 */

class SkinManager {

    /**
     * 代表 外置卡app 的resource
     */
    private Resources skinResource;
    //上下文
    private Context context;
    //皮肤apk的包名
    private String skinPackage;

    public void init(Context context) {
        this.context = context.getApplicationContext();
    }

    private static final SkinManager ourInstance = new SkinManager();

    static SkinManager getInstance() {
        return ourInstance;
    }

    private SkinManager() {
    }

    /**
     * 外置卡app 路劲
     *
     * @param path
     */
    public void loadSkin(String path) {

        PackageManager packageManager = context.getPackageManager();
//        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        //拿到外置卡皮肤apk的包名
        skinPackage = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES).packageName;
        Log.e("seven", "-------------packageName=" + skinPackage);
//        if (packageInfo != null) {
//            skinPackage = packageInfo.packageName;
//        }

        try {

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssertPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssertPath.invoke(assetManager, path);

            skinResource = new Resources(assetManager, context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());

            SharedPreferencesHelper.saveApkPath(context,path);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Drawable getDrawable(int resId) {
        //skinResource 中介
        /**
         * 如果皮肤资源不存在
         */
        if (skinResource == null) {
            return ContextCompat.getDrawable(context, resId);
        }

        String resName = context.getResources().getResourceEntryName(resId);
        int skinId = skinResource.getIdentifier(resName, "drawable", skinPackage);
        return skinResource.getDrawable(skinId);
    }

    public int getColor(int resId) {
        //skinResource 中介
        /**
         * 如果皮肤资源不存在
         */
        if (skinResource == null) {
            return context.getResources().getColor(resId);
        }

        String resName = context.getResources().getResourceEntryName(resId);
        int skinId = skinResource.getIdentifier(resName, "color", skinPackage);
        if (skinId == 0) {
            return resId;
        }

        return skinResource.getColor(skinId);
    }
}
