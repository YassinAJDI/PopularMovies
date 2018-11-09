package com.ajdi.yassin.popularmoviespart1.ui.moviedetails;

import android.util.Log;

import com.ajdi.yassin.popularmoviespart1.R;
import com.ajdi.yassin.popularmoviespart1.data.MovieRepository;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMovieDetailsResult;
import com.ajdi.yassin.popularmoviespart1.data.remote.api.NetworkState;
import com.ajdi.yassin.popularmoviespart1.utils.SnackbarMessage;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

/**
 * @author Yassin Ajdi.
 */
public class MovieDetailsViewModel extends ViewModel {

    private final MovieRepository repository;

    private LiveData<RepoMovieDetailsResult> resultLiveData;

    private MutableLiveData<Long> movieIdLiveData = new MutableLiveData<>();

    private LiveData<Movie> movieLiveData;

    private LiveData<NetworkState> networkState;

    private final SnackbarMessage mSnackbarText = new SnackbarMessage();

    private boolean isFavorite;

    public MovieDetailsViewModel(final MovieRepository repository) {
        this.repository = repository;
    }

    public void init(long movieId) {
        if (movieLiveData != null) {
            return; // trigger loading movie details, only once the activity created
        }
        Log.d(getClass().getSimpleName(), "Initializing viewModel");

        resultLiveData = Transformations.map(movieIdLiveData, new Function<Long, RepoMovieDetailsResult>() {
            @Override
            public RepoMovieDetailsResult apply(Long movieId) {
                return repository.loadMovie(movieId);
            }
        });
        movieLiveData = Transformations.switchMap(resultLiveData,
                new Function<RepoMovieDetailsResult, LiveData<Movie>>() {
                    @Override
                    public LiveData<Movie> apply(RepoMovieDetailsResult result) {
                        if (result.data.getValue() != null) {
                            isFavorite = result.data.getValue().isFavorite();
                        }
                        return result.data;
                    }
                });
        networkState = Transformations.switchMap(resultLiveData,
                new Function<RepoMovieDetailsResult, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(RepoMovieDetailsResult result) {
                        return result.networkState;
                    }
                });

        setMovieIdLiveData(movieId);
    }

    public LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public SnackbarMessage getSnackbarMessage() {
        return mSnackbarText;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    private void setMovieIdLiveData(long movieId) {
        movieIdLiveData.setValue(movieId);
    }

    public void retry(long movieId) {
        setMovieIdLiveData(movieId);
    }

    public void onFavoriteClicked() {
        Movie movie = movieLiveData.getValue();
        if (!isFavorite) {
            repository.favoriteMovie(movie);
            isFavorite = true;
            showSnackbarMessage(R.string.movie_added_successfully);
        } else {
            repository.unfavoriteMovie(movie);
            isFavorite = false;
            showSnackbarMessage(R.string.movie_removed_successfully);
        }
    }

    private void showSnackbarMessage(Integer message) {
        mSnackbarText.setValue(message);
    }
}
