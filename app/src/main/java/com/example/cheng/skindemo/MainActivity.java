package com.example.cheng.skindemo;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;

public class MainActivity extends SkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 换肤
     *
     * @param view
     */
    public void change(View view) {

        File file = new File(Environment.getExternalStorageDirectory(), "skin.apk");
        boolean exists = file.exists();

        SkinManager.getInstance().loadSkin(file.getAbsolutePath());
        changeSkin();
    }
}
