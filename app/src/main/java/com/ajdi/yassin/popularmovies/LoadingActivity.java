package com.ajdi.yassin.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


import com.bumptech.glide.Glide;


public class LoadingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ImageView imageView=findViewById(R.id.image_loading);
        Glide.with(getBaseContext()).load(R.raw.marvel).into(imageView);
        startLoading();
    }
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 4600);
    }
}