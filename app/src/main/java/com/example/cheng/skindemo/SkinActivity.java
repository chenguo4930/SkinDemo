package com.example.cheng.skindemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;

public class SkinActivity extends Activity {

    SkinFactory skinFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //布局监听器
        skinFactory = new SkinFactory();
        LayoutInflaterCompat.setFactory(getLayoutInflater(), skinFactory);
        SkinManager.getInstance().init(this);
    }

    public void changeSkin() {
        skinFactory.apply();
    }
}
