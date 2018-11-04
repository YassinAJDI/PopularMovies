package com.ajdi.yassin.popularmoviespart1.ui.moviedetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ajdi.yassin.popularmoviespart1.R;
import com.ajdi.yassin.popularmoviespart1.data.api.NetworkState;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.Trailer;
import com.ajdi.yassin.popularmoviespart1.databinding.ActivityDetailsBinding;
import com.ajdi.yassin.popularmoviespart1.utils.Constants;
import com.ajdi.yassin.popularmoviespart1.utils.GlideApp;
import com.ajdi.yassin.popularmoviespart1.utils.Injection;
import com.ajdi.yassin.popularmoviespart1.utils.UiUtils;
import com.ajdi.yassin.popularmoviespart1.utils.ViewModelFactory;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.ajdi.yassin.popularmoviespart1.data.api.Status.FAILED;
import static com.ajdi.yassin.popularmoviespart1.data.api.Status.RUNNING;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    private static final int DEFAULT_ID = -1;

    private ActivityDetailsBinding mBinding;

    private MovieDetailsViewModel mViewModel;

    private Movie mMovie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        final long movieId = intent.getLongExtra(EXTRA_MOVIE_ID, DEFAULT_ID);
        if (movieId == DEFAULT_ID) {
            closeOnError();
        }

        setupToolbar();
        mViewModel = obtainViewModel();
        if (savedInstanceState == null) {
            // trigger loading movie details, only once the activity created
            mViewModel.setMovieId(movieId);
        }
        // observe network state
        mViewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                handleNetworkState(networkState);
            }
        });
        // observe movie data
        mViewModel.getMovieLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                mMovie = movie;
                updateUi(movie);
            }
        });
        // handle retry event in case of network failure
        mBinding.networkState.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.retry(movieId);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setSubject(mMovie.getTitle() + " movie trailer")
                        .setText("Check out " + mMovie.getTitle() + " movie trailer at " +
                                Uri.parse(Constants.YOUTUBE_WEB_URL +
                                        mMovie.getTrailersResponse().getTrailers().get(0).getKey())
                        )
                        .createChooserIntent();

                int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
                if (Build.VERSION.SDK_INT >= 21)
                    flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;

                shareIntent.addFlags(flags);
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(shareIntent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUi(Movie movie) {
        // movie backdrop
        GlideApp.with(this)
                .load(Constants.BACKDROP_URL + movie.getBackdropPath())
                .placeholder(R.color.md_grey_200)
                .into(mBinding.imageMovieBackdrop);
        // movie poster
        GlideApp.with(this)
                .load(Constants.IMAGE_URL + movie.getPosterPath())
                .placeholder(R.color.md_grey_200)
                .into(mBinding.movieDetailsInfo.imagePoster);
        // movie title
        mBinding.movieDetailsInfo.textTitle.setText(movie.getTitle());
        // movie release date
        mBinding.movieDetailsInfo.textReleaseDate.setText(movie.getReleaseDate());
        // vote average
        mBinding.movieDetailsInfo.textVote.setText(String.valueOf(movie.getVoteAverage()));
        // movie overview
        mBinding.movieDetailsInfo.textOverview.setText(movie.getOverview());
        // movie trailers
        setupTrailersAdapter(movie.getTrailersResponse().getTrailers());

        mBinding.executePendingBindings();
    }

    private void setupTrailersAdapter(List<Trailer> trailers) {
        RecyclerView listTrailers = mBinding.movieDetailsInfo.listTrailers;
        listTrailers.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        listTrailers.setHasFixedSize(true);
        listTrailers.setAdapter(new TrailersAdapter(trailers, GlideApp.with(this)));
    }

    private MovieDetailsViewModel obtainViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(Injection.provideMovieRepository());
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
                            mViewModel.getMovieLiveData().getValue().getTitle());
                    isShow = true;
                } else if (isShow) {
                    //display an empty string when toolbar is expanded
                    mBinding.collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void handleNetworkState(NetworkState networkState) {
        boolean isLoaded = networkState == NetworkState.LOADED;
        mBinding.appbar.setVisibility(isVisible(isLoaded));
        mBinding.movieDetails.setVisibility(isVisible(isLoaded));
        mBinding.networkState.progressBar.setVisibility(
                isVisible(networkState.getStatus() == RUNNING));
        mBinding.networkState.retryButton.setVisibility(
                isVisible(networkState.getStatus() == FAILED));
        mBinding.networkState.errorMsg.setVisibility(
                isVisible(networkState.getMsg() != null));
        mBinding.networkState.errorMsg.setText(networkState.getMsg());
    }

    private int isVisible(boolean condition) {
        if (condition)
            return View.VISIBLE;
        else
            return View.GONE;
    }
}
