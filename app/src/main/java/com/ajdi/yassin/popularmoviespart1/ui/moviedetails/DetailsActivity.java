package com.ajdi.yassin.popularmoviespart1.ui.moviedetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ajdi.yassin.popularmoviespart1.R;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.Resource;
import com.ajdi.yassin.popularmoviespart1.databinding.ActivityDetailsBinding;
import com.ajdi.yassin.popularmoviespart1.ui.moviedetails.trailers.TrailersAdapter;
import com.ajdi.yassin.popularmoviespart1.utils.Constants;
import com.ajdi.yassin.popularmoviespart1.utils.Injection;
import com.ajdi.yassin.popularmoviespart1.utils.UiUtils;
import com.ajdi.yassin.popularmoviespart1.utils.ViewModelFactory;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    private static final int DEFAULT_ID = -1;

    private ActivityDetailsBinding mBinding;

    private MovieDetailsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final long movieId = getIntent().getLongExtra(EXTRA_MOVIE_ID, DEFAULT_ID);
        if (movieId == DEFAULT_ID) {
            closeOnError();
            return;
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        mBinding.setLifecycleOwner(this);

        mViewModel = obtainViewModel();
        mViewModel.init(movieId);
        setupToolbar();
        setupTrailersAdapter();
        // observe result
        mViewModel.getResult().observe(this, new Observer<Resource<Movie>>() {
            @Override
            public void onChanged(Resource<Movie> movieResource) {
                if (movieResource.data != null) {
                    mViewModel.setFavorite(movieResource.data.isFavorite());
                }
                mBinding.setMovieResource(movieResource);
                mBinding.setMovie(movieResource.data);
            }
        });
        // handle retry event in case of network failure
        mBinding.networkState.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.retry(movieId);
            }
        });
        // Observe snackbar messages
        mViewModel.getSnackbarMessage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer message) {
                Snackbar.make(mBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            handleCollapsedToolbarTitle();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_details, menu);
        UiUtils.tintMenuIcon(this, menu.findItem(R.id.action_share), R.color.md_white_1000);

        MenuItem favoriteItem = menu.findItem(R.id.action_favorite);
        if (mViewModel.isFavorite()) {
            favoriteItem.setIcon(R.drawable.ic_favorite_black_24dp)
                    .setTitle(R.string.action_remove_from_favorites);
        } else {
            favoriteItem.setIcon(R.drawable.ic_favorite_border_black_24dp)
                    .setTitle(R.string.action_favorite);
        }
        UiUtils.tintMenuIcon(this, favoriteItem, R.color.md_white_1000);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share: {
                Movie movie = mViewModel.getResult().getValue().data;
                Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setSubject(movie.getTitle() + " movie trailer")
                        .setText("Check out " + movie.getTitle() + " movie trailer at " +
                                Uri.parse(Constants.YOUTUBE_WEB_URL +
                                        movie.getTrailersResponse().getTrailers().get(0).getKey())
                        )
                        .createChooserIntent();

                int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
                if (Build.VERSION.SDK_INT >= 21)
                    flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;

                shareIntent.addFlags(flags);
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(shareIntent);
                }
                return true;
            }
            case R.id.action_favorite: {
                mViewModel.onFavoriteClicked();
                invalidateOptionsMenu();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupTrailersAdapter() {
        RecyclerView listTrailers = mBinding.movieDetailsInfo.listTrailers;
        listTrailers.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        listTrailers.setHasFixedSize(true);
        listTrailers.setAdapter(new TrailersAdapter());
    }

    private MovieDetailsViewModel obtainViewModel() {
        ViewModelFactory factory = Injection.provideViewModelFactory(this);
        return ViewModelProviders.of(this, factory).get(MovieDetailsViewModel.class);
    }

    private void closeOnError() {
        throw new IllegalArgumentException("Access denied.");
    }

    /**
     * sets the title on the toolbar only if the toolbar is collapsed
     */
    private void handleCollapsedToolbarTitle() {
        mBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                //verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    mBinding.collapsingToolbar.setTitle(
                            mViewModel.getResult().getValue().data.getTitle());
                    isShow = true;
                } else if (isShow) {
                    //display an empty string when toolbar is expanded
                    mBinding.collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
