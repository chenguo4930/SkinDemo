package com.example.cheng.skindemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;

public class TwoActivity extends SkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
    }

    /**
     * 换肤
     *
     * @param view
     */
    public void change(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "skin.apk");
        SkinManager.getInstance().loadSkin(file.getAbsolutePath());
    }

    public void startActivityTwo(View view) {
        startActivity(new Intent(this,ThreeActivity.class));
    }

}
