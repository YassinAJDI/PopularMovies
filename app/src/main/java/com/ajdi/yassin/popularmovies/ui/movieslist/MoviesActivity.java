package com.ajdi.yassin.popularmovies.ui.movieslist;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.ajdi.yassin.popularmovies.LoadingActivity;
import com.ajdi.yassin.popularmovies.R;
import com.ajdi.yassin.popularmovies.ui.movieslist.discover.DiscoverMoviesFragment;
import com.ajdi.yassin.popularmovies.ui.movieslist.favorites.FavoritesFragment;
import com.ajdi.yassin.popularmovies.utils.ActivityUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
public class MoviesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
            setupViewFragment();
        }
        setupToolbar();
        setupBottomNavigation();
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

}
