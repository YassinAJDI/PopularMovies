package com.ajdi.yassin.popularmoviespart1.ui.moviedetails;

import android.content.Intent;
import android.os.Bundle;

import com.ajdi.yassin.popularmoviespart1.R;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.databinding.ActivityDetailsBinding;
import com.ajdi.yassin.popularmoviespart1.utils.Constants;
import com.ajdi.yassin.popularmoviespart1.utils.GlideApp;
import com.ajdi.yassin.popularmoviespart1.utils.Injection;
import com.ajdi.yassin.popularmoviespart1.utils.ViewModelFactory;
import com.google.android.material.appbar.AppBarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    private static final int DEFAULT_ID = -1;

    private ActivityDetailsBinding mBinding;

    private MovieDetailsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        long movieId = intent.getLongExtra(EXTRA_MOVIE_ID, DEFAULT_ID);
        if (movieId == DEFAULT_ID) {
            closeOnError();
        }

        setupToolbar();
        mViewModel = obtainViewModel();

        if (savedInstanceState == null) {
            // trigger loading movie details, only once the activity created
            mViewModel.setMovieId(movieId);
        }
        mViewModel.getMovieLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                updateUi(movie);
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

    private void updateUi(Movie movie) {
        // movie backdrop
        GlideApp.with(this)
                .load(Constants.BACKDROP_URL + movie.getBackdrop())
                .into(mBinding.imageMovieBackdrop);

        // movie poster
        GlideApp.with(this)
                .load(Constants.IMAGE_URL + movie.getImageUrl())
                .into(mBinding.imagePoster);

        // movie title
        mBinding.textTitle.setText(movie.getTitle());

        // movie release date
        mBinding.textReleaseDate.setText(movie.getReleaseDate());

        // vote average
        mBinding.textVote.setText(String.valueOf(movie.getUserRating()));

        // movie overview
        mBinding.textOverview.setText(movie.getOverview());
    }

    private MovieDetailsViewModel obtainViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(Injection.provideMovieRepository());
        return ViewModelProviders.of(this, factory).get(MovieDetailsViewModel.class);
    }

    private void closeOnError() {
        throw new RuntimeException("Access denied.");
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

}
