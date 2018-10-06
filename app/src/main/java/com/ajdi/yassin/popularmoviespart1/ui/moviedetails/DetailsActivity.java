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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    private static final int DEFAULT_ID = -1;

    private ActivityDetailsBinding mBinding;

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
        MovieDetailsViewModel viewModel = obtainViewModel();

        if (savedInstanceState == null) {
            // trigger loading movie details, only once the activity created
            viewModel.setMovieId(movieId);
        }
        viewModel.getMovieLiveData().observe(this, new Observer<Movie>() {
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
            getSupportActionBar().setDisplayShowTitleEnabled(false);
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

}
