package com.example.cheng.skindemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends SkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startActivityTwo(View view) {
        startActivity(new Intent(this,TwoActivity.class));
    }
}
