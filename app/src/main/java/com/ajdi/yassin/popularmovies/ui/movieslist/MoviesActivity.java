package com.ajdi.yassin.popularmovies.ui.movieslist;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ajdi.yassin.popularmovies.R;
import com.ajdi.yassin.popularmovies.ui.movieslist.discover.DiscoverMoviesFragment;
import com.ajdi.yassin.popularmovies.ui.movieslist.favorites.FavoritesFragment;
import com.ajdi.yassin.popularmovies.utils.ActivityUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Timer;
import java.util.TimerTask;

public class MoviesActivity extends AppCompatActivity {

    ImageView imageView;
    ProgressDialog progressDlg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=(ImageView)findViewById(R.id.image);
        if (savedInstanceState == null) {
            // Glide.with(getBaseContext()).load(R.raw.marvle).into(imageView);

            progressDlg = new ProgressDialog(this);
            progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDlg.setMax(100);
            progressDlg.setProgress(0);
            new Thread(){
                public void run() {
                    for(int i=0;i<=100;i++) {
                        progressDlg.setProgress(i);
                        if(i==100)
                            progressDlg.dismiss();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    progressDlg.dismiss();
                }
            }.start();

            setupViewFragment();
        }
        setupToolbar();
        setupBottomNavigation();
        initView();
    }

    private void setupViewFragment() {
        // show discover movies fragment by default
        DiscoverMoviesFragment discoverMoviesFragment = DiscoverMoviesFragment.newInstance();
        ActivityUtils.replaceFragmentInActivity(
                getSupportFragmentManager(), discoverMoviesFragment, R.id.fragment_container);
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_discover:
                        ActivityUtils.replaceFragmentInActivity(
                                getSupportFragmentManager(), DiscoverMoviesFragment.newInstance(),
                                R.id.fragment_container);
                        return true;
                    case R.id.action_favorites:
                        ActivityUtils.replaceFragmentInActivity(
                                getSupportFragmentManager(), FavoritesFragment.newInstance(),
                                R.id.fragment_container);
                        return true;
                }
                return false;
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void initView(){

    }
}
